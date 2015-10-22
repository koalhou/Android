/**
 * @(#)MtMsgWorkThread.java 2013-5-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.threadwork;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.Base64Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.entity.msg.CoreMsgInfo;
import com.neusoft.mid.clwapi.entity.msg.TerminalViBean;
import com.neusoft.mid.clwapi.mapper.MsgMapper;
import com.neusoft.mid.clwapi.process.delivermsg.SendCommandInfo;
import com.neusoft.mid.clwapi.process.delivermsg.SendDeliverMsgService;
import com.neusoft.mid.clwapi.tools.RandomNumberUtil;
import com.neusoft.mid.clwapi.tools.TimeUtil;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-5-9 上午11:05:39
 */
public class MtMsgSendWorkThread implements Runnable {
	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	private CoreMsgInfo msgInfo;
	private MsgMapper msgMapper;
	private SendDeliverMsgService sendDeliverMsgService;

	public MtMsgSendWorkThread(CoreMsgInfo msgInfo, MsgMapper msgMapper,
			SendDeliverMsgService sendDeliverMsgService) {
		this.msgInfo = msgInfo;
		this.msgMapper = msgMapper;
		this.sendDeliverMsgService = sendDeliverMsgService;
	}

	public void run() {
		if (null != msgInfo) {

			logger.info("本次请求中VINS信息为:" + msgInfo.getVin());
			String[] vinArray = StringUtils.split(
					StringUtils.strip(msgInfo.getVin()), "|");
			logger.info("本次调度消息下发请求中共包含" + vinArray.length + "辆车");
			// 产生批次号.
			String batchId = UUID.randomUUID().toString().replaceAll("-", "");
			for (String vin : vinArray) {
				String msgId = createMsgId();
				MDC.put("SUB_ID", "[" + msgId + "]");
				try {
					logger.info("开始判断vin:" + vin + "车辆在线情况");
					TerminalViBean terminalInfo = (TerminalViBean) msgMapper
							.getRealVehcileByVin(vin);
					// 判断车辆终端是否具备下发消息条件
					if (isMsgPassable(vin, terminalInfo)) {
						logger.info("VIN:" + vin + "具备下发调度信息前提条件");
						SendCommandInfo info = initDiaoduCommandInfo(vin,
								msgInfo.getMsg(), msgInfo.getType(),
								terminalInfo.getSimCardNum(), batchId, msgId,
								msgInfo.getUserId());
						if (null != info) {
							// 向核心服务发送调度信息命令请求
							sendDeliverMsgService
									.sendCommandToCoreService(info);

						} else {
							logger.info("初始调度信息下发内容时异常,调度信息下发内容为空");
						}
					} else {
						logger.info("VIN:" + vin
								+ "车辆终端不具备下发调度消息先决条件");
					}
				} catch (DataAccessException e) {
					logger.error(
							"从数据库中获取vin:" + vin + "车辆终端状态时出错", e);
				} catch (Exception e) {
					logger.error("vin:" + vin + "车辆下发调度消息时出错", e);
				}
				MDC.remove("SUB_ID");
			}
		}

	}

	/**
	 * 判断车辆终端是否具备下发调度信息条件.
	 * 
	 * @param vin
	 *            VIN号
	 * @param terminalInfo
	 *            终端状态信息.
	 * @return 是否具备下发调度信息条件.
	 */
	private boolean isMsgPassable(String vin, TerminalViBean terminalInfo) {
		boolean flg = true;
		if (null != terminalInfo) {
			logger.info("vin:" + vin + ",TERMINAL_TIME:"
					+ terminalInfo.getTerminalTime() + ",STAT_INFO:"
					+ terminalInfo.getStateInfo() + ",ACC:"
					+ terminalInfo.getAccFlg() + ",SECONDS:"
					+ terminalInfo.getSeconds() + ",SIM_CARD_NUMBER:"
					+ terminalInfo.getSimCardNum());
			if (StringUtils.isEmpty(terminalInfo.getSimCardNum())
					|| StringUtils.isEmpty(terminalInfo.getTerminalTime())) {
				flg = false;
			} else {
				if (!StringUtils.isEmpty(terminalInfo.getStateInfo())) {
					if (("1".equals(terminalInfo.getAccFlg()) && (terminalInfo
							.getSeconds() >= 300))
							|| ("0".equals(terminalInfo.getAccFlg()) && (terminalInfo
									.getSeconds() >= 1800))) {
						flg = false;
					}
				} else {
					logger.info("vin:" + vin + "终端状态位码值为空,默认点火状态为开");
					// 为空情况默认点火状态为开
					flg = terminalInfo.getSeconds() < 300;
				}
			}

		} else {
			logger.info("不存在vin:" + vin + "车辆终端信息");
			flg = false;
		}
		return flg;
	}

	/**
	 * 
	 * @param vin
	 * @param msg
	 * @param type
	 * @param simCardNum
	 * @param batchId
	 * @return
	 */
	private SendCommandInfo initDiaoduCommandInfo(String vin, String msg,
			String type, String simCardNum, String batchId, String msgId,
			String userId) {
		SendCommandInfo sendCommandInfo = new SendCommandInfo();
		sendCommandInfo.setBatchId(batchId);
		sendCommandInfo.setChanleCode(null);
		sendCommandInfo.setMsgId(msgId);
		sendCommandInfo.setOperateUserId(userId);
		sendCommandInfo.setSendCommand("0010");
		sendCommandInfo.setSendType("2002");
		sendCommandInfo.setSimCardNumber(simCardNum);
		sendCommandInfo.setSource("1");
		sendCommandInfo.setTerminalId(vin);

		StringBuffer packet = new StringBuffer();
		packet.append("00042002");
		packet.append("0120");
		packet.append(msgId);
		packet.append("0202");
		packet.append(format(Integer.toHexString(Integer.parseInt(type, 2)), 2));
		packet.append("03");
		try {
			packet.append(format(
					Integer.toHexString(msg.getBytes("GBK").length), 4));
			packet.append(msg);
		} catch (Exception e) {
			logger.error(msgId + ",消息内容拼串出错:", e);
			return null;
		}
		try {
			String base64Remark = Base64Utility.encode(msg.getBytes("UTF-8"));
			sendCommandInfo.setRemark(base64Remark);
			logger.info("Base64编码后REMARK:" + base64Remark);
		} catch (UnsupportedEncodingException e) {
			logger.error("将REMARK:" + msg + "转换为UTF-8的Base64编码时出错", e);
			return null;
		}
		try {
			logger.info("即将发送信息中PACKET_CONTENT:" + packet);
			String base64Packet = Base64Utility.encode(packet.toString()
					.getBytes("UTF-8"));
			sendCommandInfo.setPacketContent(base64Packet);
			logger.info("Base64编码后PACKET_CONTENT:" + base64Packet);
		} catch (UnsupportedEncodingException e) {
			logger.error("将PACKET_CONTENT:" + packet + "转换为UTF-8的Base64编码时出错",
					e);
			return null;
		}
		sendCommandInfo.setUserId(userId);

		return sendCommandInfo;
	}

	private String format(String str, int len) {
		while (str.length() < len) {
			str = "0" + str;
		}
		return str;
	}

	/**
	 * 创建32位消息ID.
	 * 
	 * @return 32位消息ID.
	 */
	private String createMsgId() {
		return "clwapi_" + TimeUtil.getSysTime() + "_"
				+ RandomNumberUtil.randomLong(10);
	}

}
