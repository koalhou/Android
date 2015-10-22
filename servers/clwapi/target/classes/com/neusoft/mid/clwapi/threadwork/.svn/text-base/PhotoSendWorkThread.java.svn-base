/**
 * @(#)PhotoSendWorkThread.java 2013-5-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.threadwork;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.neusoft.mid.clwapi.entity.oauth.MobileUsrAllInfo;
import com.neusoft.mid.clwapi.mapper.MsgMapper;
import com.neusoft.mid.clwapi.process.delivermsg.DeliverMsgResult;
import com.neusoft.mid.clwapi.process.delivermsg.SendCommandInfo;
import com.neusoft.mid.clwapi.process.delivermsg.SendDeliverMsgService;
import com.neusoft.mid.clwapi.tools.RandomNumberUtil;
import com.neusoft.mid.clwapi.tools.TimeUtil;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-5-9 下午12:53:43
 */
public class PhotoSendWorkThread implements Runnable {
	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	private CoreMsgInfo msgInfo;
	private MsgMapper msgMapper;
	private SendDeliverMsgService sendDeliverMsgService;
	private MobileUsrAllInfo usrInfo;

	public PhotoSendWorkThread(CoreMsgInfo msgInfo, MobileUsrAllInfo usrInfo,
			MsgMapper msgMapper, SendDeliverMsgService sendDeliverMsgService) {
		this.msgInfo = msgInfo;
		this.usrInfo = usrInfo;
		this.msgMapper = msgMapper;
		this.sendDeliverMsgService = sendDeliverMsgService;
	}

	public void run() {
		if (null != msgInfo) {

			logger.info("本次请求中VINS信息为:" + msgInfo.getVin());
			String[] vinArray = StringUtils.split(
					StringUtils.strip(msgInfo.getVin()), "|");
			logger.info("本次拍照下发请求中共包含" + vinArray.length + "辆车");

			logger.info("本次请求中拍摄通道为:" + msgInfo.getType());
			String[] channelArray = StringUtils.split(
					StringUtils.strip(msgInfo.getType()), "|");
			logger.info("本次拍照下发请求中共包含" + channelArray.length + "个通道");
			// 产生批次号.
			String batchId = UUID.randomUUID().toString().replaceAll("-", "");
			for (String vinTemp : vinArray) {
				String vin = StringUtils.strip(vinTemp);
				// 判断车辆终端是否具备下发调度消息条件
				try {
					logger.info("开始判断vin:" + vin + "车辆在线情况");
					TerminalViBean terminalInfo = (TerminalViBean) msgMapper
							.getRealVehcileByVin(vin);
					// 判断车辆终端是否具备下发消息条件
					if (isPicPassable(vin, terminalInfo)) {
						logger.info("VIN:" + vin + "具备下发拍照命令前提条件");
						for (String channelTemp : channelArray) {
							String msgId = createMsgId();
							MDC.put("SUB_ID", "[" + msgId + "]");
							String channel = StringUtils.strip(channelTemp);
							SendCommandInfo info = initPicCommandInfo(vin,
									channel, terminalInfo.getSimCardNum(),
									batchId, usrInfo, msgId);
							if (null != info) {
								// 向核心服务发送调度信息命令请求
								DeliverMsgResult respResult = sendDeliverMsgService
										.sendCommandToCoreService(info);
								// 根据返回信息设置下发状态位.
								if (null != respResult
										&& "0".equals(respResult.getCode())) {
									logger.info("VIN:" + vin + ",channel:"
											+ channel + "拍照指令下发成功");
								} else {
									logger.error("核心系统处理VIN:" + vin
											+ ",channel:" + channel + "拍照指令失败");
								}
							} else {
								logger.error("初始化VIN:" + vin + ",channel:"
										+ channel + "拍照指令信息时出错");
							}
						}

					} else {
						logger.info("VIN:" + vin + "车辆终端不具备下发拍照指令先决条件");
					}
				} catch (DataAccessException e) {
					logger.error("从数据库中获取vin:" + vin + "车辆终端状态时出错", e);
				} catch (Exception e) {
					logger.error("vin:" + vin + "车辆下发拍照指令时出错", e);
				}
			}
		}
	}

	/**
	 * 判断车辆终端是否具备下发拍照命令条件.
	 * 
	 * @param vin
	 *            VIN号
	 * @param terminalInfo
	 *            终端状态信息.
	 * @return 是否具备下发拍照命令条件.
	 */
	private boolean isPicPassable(String vin, TerminalViBean terminalInfo) {
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
					} else {
						if ("1".equals(terminalInfo.getAccFlg())) {
							flg = true;
						} else {
							flg = false;
						}
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
	 * 初始化拍照命令信息.
	 * 
	 * @param vin
	 *            车辆VIN码
	 * @param chanle
	 *            拍照通道
	 * @param simCardNum
	 *            SIM卡号
	 * @param batchId
	 *            批次信息.
	 * @return
	 */
	private SendCommandInfo initPicCommandInfo(String vin, String chanle,
			String simCardNum, String batchId, MobileUsrAllInfo usrInfo,
			String msgId) {
		SendCommandInfo sendCommandInfo = new SendCommandInfo();
		sendCommandInfo.setBatchId(batchId);
		sendCommandInfo.setChanleCode(chanle);
		sendCommandInfo.setMsgId(msgId);
		sendCommandInfo.setOperateUserId(usrInfo.getUsrId());
		sendCommandInfo.setSendCommand("0010");
		sendCommandInfo.setSendType("2001");
		sendCommandInfo.setSimCardNumber(simCardNum);
		sendCommandInfo.setSource("1");
		sendCommandInfo.setTerminalId(vin);

		StringBuffer packet = new StringBuffer();
		packet.append("00042001");
		packet.append("0120");
		packet.append(msgId);
		packet.append("02");
		packet.append(format(Integer.toHexString(12), 2));
		packet.append(getThisSecondTime());
		packet.append("0302");
		// pixel
		packet.append(format(Integer.toHexString(1), 2));

		packet.append("0402");
		// image_quality
		packet.append(format(Integer.toHexString(Integer.parseInt("5")), 2));
		packet.append("0502");
		packet.append(format(Integer.toHexString(Integer.parseInt(chanle)), 2));
		/*
		 * 新增参数报文
		 */
		packet.append("0604");
		packet.append("0001");
		packet.append("0703");
		packet.append("000");
		packet.append("0801");
		packet.append("0");
		packet.append("0903");
		// brightness
		packet.append(format(Integer.toHexString(126), 3));
		packet.append("0A03");
		// contrast
		packet.append(format(Integer.toHexString(65), 3));
		packet.append("0B03");
		// saturation
		packet.append(format(Integer.toHexString(65), 3));
		packet.append("0C03");
		// color
		packet.append(format(Integer.toHexString(126), 3));

		String remark = "终端软件下发拍照命令";
		try {
			String base64Remark = Base64Utility
					.encode(remark.getBytes("UTF-8"));
			sendCommandInfo.setRemark(base64Remark);
			logger.info("Base64编码后REMARK:" + base64Remark);
		} catch (UnsupportedEncodingException e) {
			logger.error("将REMARK:" + remark + "转换为UTF-8的Base64编码时出错", e);
			return null;
		}
		// 设置下发报文
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
		sendCommandInfo.setUserId(usrInfo.getUsrId());
		sendCommandInfo.setClientId(usrInfo.getClientId());

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

	/**
	 * 获取当前时间
	 * 
	 * @return 当前时间.格式:yyMMddHHmmss.
	 */
	private static String getThisSecondTime() {
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		return formatter.format(currentTime);
	}
}
