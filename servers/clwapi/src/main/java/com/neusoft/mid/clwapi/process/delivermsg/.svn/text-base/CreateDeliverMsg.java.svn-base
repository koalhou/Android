/**
 * @(#)CreateDeliverMsg.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.process.delivermsg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.cxf.common.util.Base64Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.entity.coremsg.CoreMsgReq;
import com.neusoft.mid.clwapi.entity.coremsg.CoreMsgReqFunction;
import com.neusoft.mid.clwapi.entity.coremsg.CoreMsgReqParam;
import com.neusoft.mid.clwapi.entity.coremsg.CoreMsgReqTreeOject;
import com.neusoft.mid.clwapi.entity.coremsg.TreeObjectValue;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.MsgMapper;
import com.neusoft.mid.clwapi.tools.PropertiesTools;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-9 下午1:03:14
 */
@Service("createDeliverMsg")
public class CreateDeliverMsg {
	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);
	
	@Autowired
	private MsgMapper msgMapper;

	/**
	 * 组装[终端命令下发]的请求报文.
	 * @param sendCommand 下发命令信息.
	 * @return [终端命令下发]的请求报文.
	 */
	public String createSendCommandReqMsg(SendCommandInfo sendCommandInfo) {
		String msg = "";
		CoreMsgReq req = new CoreMsgReq();
		CoreMsgReqFunction reqFunction = new CoreMsgReqFunction();
		// 设置接口名称
		reqFunction
				.setName(CoreSysAccessProtocolCommon.FUNCTION_NAME_SENDCOMMAND);
		req.setFunction(reqFunction);

		CoreMsgReqParam param = new CoreMsgReqParam();
		// 拼装请求树信息.
		List<TreeObjectValue> treeObjectList = createSendCommandTreeObject(
				sendCommandInfo);

		CoreMsgReqTreeOject treeOjbect = new CoreMsgReqTreeOject();
		treeOjbect.setTreeList(treeObjectList);
		param.setTreeObject(treeOjbect);
		reqFunction.setParam(param);

		// 将实体内容转化为XML请求报文.
		msg = createXmlByJaxb(CoreMsgReq.class, req);
		return msg;
	}

	/**
	 * 组装[同步手机端用户相关信息]的请求报文.
	 * @param userId 用户id.
	 * @param clientId 客户端id.
	 * @return [同步手机端用户相关信息]的请求报文.
	 */
	public String createMobileInfoReqMsg(String userId,
			String clientId) {
		String msg = "";
		CoreMsgReq req = new CoreMsgReq();
		CoreMsgReqFunction reqFunction = new CoreMsgReqFunction();
		// 设置接口名称
		reqFunction.setName(CoreSysAccessProtocolCommon.FUNCTION_NAME_MOBILE);
		req.setFunction(reqFunction);

		CoreMsgReqParam param = new CoreMsgReqParam();
		// 拼装请求树信息.
		List<TreeObjectValue> treeObjectList = createMobileInfoTreeObject(userId,
				clientId);

		CoreMsgReqTreeOject treeOjbect = new CoreMsgReqTreeOject();
		treeOjbect.setTreeList(treeObjectList);
		param.setTreeObject(treeOjbect);
		reqFunction.setParam(param);

		// 将实体内容转化为XML请求报文.
		msg = createXmlByJaxb(CoreMsgReq.class, req);
		return msg;
	}
	
	/**
	 * 组装[手机客户端标记超载告警]的请求报文.
	 * @param terminalId 目的终端号码:车辆vin号.
	 * @param alarmTypeId 告警类型ID.
	 * @param photoId 照片ID.
	 * @return [手机客户端标记超载告警]的请求报文.
	 */
	public String createMarkAlarmReqMsg(String terminalId,
			String alarmTypeId, String photoId, String enterpriseId) {
		String msg = "";
		CoreMsgReq req = new CoreMsgReq();
		CoreMsgReqFunction reqFunction = new CoreMsgReqFunction();
		// 设置接口名称
		reqFunction.setName(CoreSysAccessProtocolCommon.FUNCTION_NAME_MARKALARM);
		req.setFunction(reqFunction);

		CoreMsgReqParam param = new CoreMsgReqParam();
		// 拼装请求树信息.
		List<TreeObjectValue> treeObjectList = createMarkAlarmTreeObject(terminalId,
				alarmTypeId, photoId, enterpriseId);

		CoreMsgReqTreeOject treeOjbect = new CoreMsgReqTreeOject();
		treeOjbect.setTreeList(treeObjectList);
		param.setTreeObject(treeOjbect);
		reqFunction.setParam(param);

		// 将实体内容转化为XML请求报文.
		msg = createXmlByJaxb(CoreMsgReq.class, req);
		return msg;
	}
	
	/**
	 * 创建[终端命令下发]请求参数树信息.
	 * @param sendCommand 下发命令信息.
	 * @return [终端命令下发]请求参数树信息.
	 */
	private List<TreeObjectValue> createSendCommandTreeObject(
			SendCommandInfo sendCommandInfo) {
		List<TreeObjectValue> treeObjectList = new ArrayList<TreeObjectValue>();
		// 设置应用ID信息
		treeObjectList.add(createAppIdTreeObject());
		
		// 设置应用登录密码信息
		treeObjectList.add(createAppPassTreeObject());

		// 下发命令字
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_SEND_COMMAND,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
				sendCommandInfo.getSendCommand()));
		// 下发子类型
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_SEND_TYPE,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
				sendCommandInfo.getSendType()));
		// 目的终端号码 ：车辆vin号
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_TERMINAL_ID,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
				sendCommandInfo.getTerminalId()));
		// SIM卡号
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_SIM_CARD_NUMBER,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
				sendCommandInfo.getSimCardNumber()));
		// 下发消息ID时间戳
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_MSG_ID,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
				sendCommandInfo.getMsgId()));
		// 操作人ID
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_OPERATE_USER_ID,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
				sendCommandInfo.getOperateUserId()));
		// 备注
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_REMARK,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_BSTR,sendCommandInfo.getRemark()));
		// 发送给终端的数据包内容
		treeObjectList
				.add(createTreeObject(
						CoreSysAccessProtocolCommon.KEY_PACKET_CONTENT,
						CoreSysAccessProtocolCommon.PARAMETER_TYPE_BSTR,
						sendCommandInfo.getPacketContent()));
		if (null != sendCommandInfo.getReggrpid()) {
			// 区域组ID
			treeObjectList.add(createTreeObject(
					CoreSysAccessProtocolCommon.KEY_REGGRPID,
					CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
					sendCommandInfo.getReggrpid()));
		}
		if (null != sendCommandInfo.getChanleCode()) {
			// 通道号
			treeObjectList.add(createTreeObject(
					CoreSysAccessProtocolCommon.KEY_CHANLE_CODE,
					CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
					sendCommandInfo.getChanleCode()));
		}
		// 批次号
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_BATCH_ID,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
				sendCommandInfo.getBatchId()));
		// 数据来源标识(1:手机客户端；)，为空时表示平台下发
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_SOURCE,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
				sendCommandInfo.getSource()));
		// 命令下发用户ID
		treeObjectList
				.add(createTreeObject(
						CoreSysAccessProtocolCommon.KEY_USER_ID,
						CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
						sendCommandInfo.getUserId()));
		if (null != sendCommandInfo.getClientId()) {
			// 命令下发客户端ID
			treeObjectList.add(createTreeObject(
					CoreSysAccessProtocolCommon.KEY_CLIENT_ID,
					CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
					sendCommandInfo.getClientId()));
		}

		return treeObjectList;
	}
	
	/**
	 * 创建[手机客户端标记超载告警]请求参数树信息.
	 * @param terminalId 目的终端号码:车辆vin号.
	 * @param alarmTypeId 告警类型ID.
	 * @param photoId 照片ID.
	 * @return [手机客户端标记超载告警]请求参数树信息.
	 */
	private List<TreeObjectValue> createMarkAlarmTreeObject(String terminalId,
			String alarmTypeId, String photoId, String enterpriseId) {
		List<TreeObjectValue> treeObjectList = new ArrayList<TreeObjectValue>();

		// 设置应用ID信息
		treeObjectList.add(createAppIdTreeObject());
		
		// 设置应用登录密码信息
		treeObjectList.add(createAppPassTreeObject());

		// 设置目的终端号码
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_TERMINAL_ID,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR, terminalId));

		// 设置告警类型ID信息
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_ALARM_TYPE_ID,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR, alarmTypeId));

		// 设置照片ID信息
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_PHOTO_ID,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR, photoId));

		// 设置企业id信息
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_ENTERPRISE_ID,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR, enterpriseId));
		return treeObjectList;
	}
	
	/**
	 * 创建[同步手机端用户相关信息]请求参数树信息.
	 * @param appid 应用id.
	 * @param pass 密码 base64的编码.
	 * @param userId 用户id.
	 * @param clientId 客户端id.
	 * @return [同步手机端用户相关信息]请求参数树信息.
	 */
	private List<TreeObjectValue> createMobileInfoTreeObject(String userId,
			String clientId) {
		List<TreeObjectValue> treeObjectList = new ArrayList<TreeObjectValue>();

		// 设置应用ID信息
		treeObjectList.add(createAppIdTreeObject());
		
		// 设置应用登录密码信息
		treeObjectList.add(createAppPassTreeObject());

		// 设置用户ID信息
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_USER_ID,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR, userId));

		// 设置客户端ID信息
		treeObjectList.add(createTreeObject(
				CoreSysAccessProtocolCommon.KEY_CLIENT_ID,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR, clientId));
		return treeObjectList;
	}

	/**
	 * 创建TreeObject.
	 * @param key key值.
	 * @param className class值.
	 * @param value value值.
	 * @return 初始化完毕的TreeObject.
	 */
	private TreeObjectValue createTreeObject(String key, String className, String value) {
		TreeObjectValue treeObject = new TreeObjectValue();
		treeObject.setKey(key);
		treeObject.setClassName(className);
		treeObject.setValue(value);
		return treeObject;
	}
	
	/**
	 * 将拼装完毕的实体内容转换为XML请求报文.
	 * @param typeClass 实体类型.
	 * @param req 实体内容.
	 * @return XML请求报文.
	 */
	private String createXmlByJaxb(Class<?> typeClass, CoreMsgReq req) {
		String reqCont = "";
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(typeClass);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// 去掉xml文件头
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			//是否格式化生成的xml串
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();   
			jaxbMarshaller.marshal(req, baos);
			reqCont = baos.toString();
			baos.close();

		} catch (PropertyException e) {
			logger.error("调整JAXB XML样式时出错", e);
			throw new ApplicationException(ErrorConstant.ERROR99999,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (JAXBException e) {
			logger.error("将实体信通过JAXB转换为XML时出错", e);
			throw new ApplicationException(ErrorConstant.ERROR99999,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			logger.error("关闭ByteArrayOutputStream时出错", e);
			throw new ApplicationException(ErrorConstant.ERROR99999,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		return reqCont;
	}

	/**
	 * 
	 * @return
	 */
	public TreeObjectValue createAppIdTreeObject() {
		// 设置应用ID信息
		if (null == ClwapiAccessCoreCommon.appId
				|| "".equals(ClwapiAccessCoreCommon.appId)) {
			loadAppConfInfo();
		}
		return createTreeObject(CoreSysAccessProtocolCommon.KEY_APPID,
				CoreSysAccessProtocolCommon.PARAMETER_TYPE_STR,
				ClwapiAccessCoreCommon.appId);

	}

	/**
	 * 
	 * @return
	 */
	public TreeObjectValue createAppPassTreeObject() {
		if (null == ClwapiAccessCoreCommon.appPass
				|| "".equals(ClwapiAccessCoreCommon.appPass)) {
			loadAppConfInfo();
		} else {
			try {
				String pass = Base64Utility
						.encode(ClwapiAccessCoreCommon.appPass
								.getBytes("UTF-8"));
				return createTreeObject(CoreSysAccessProtocolCommon.KEY_PASS,
						CoreSysAccessProtocolCommon.PARAMETER_TYPE_BSTR, pass);
			} catch (UnsupportedEncodingException e) {
				logger.error("将CLWAPI访问核心服务登录密码转化为UTF-8格式Base64编码时出错", e);
			}
		}
		return null;
	}
	
	/**
	 * 加载系统所需资源信息.
	 */
	private void loadAppConfInfo() {
		try {
			String appId = PropertiesTools.readValue(ModCommonConstant.SYS_CONF_FILE_PATH, "clwapi.appid");
			ClwapiAccessCoreCommon appConf = msgMapper.getAppConfInfo(appId);
			if (null == appConf) {
				logger.error("数据库中不存在APP_ID=" + appId + "的系统配置信息");
			} else {
				logger.info("appId:" + ClwapiAccessCoreCommon.appId);
				logger.info("appPass:" + ClwapiAccessCoreCommon.appPass);
				logger.info("appname:" + ClwapiAccessCoreCommon.appname);
			} 
		} catch (DataAccessException e) {
			logger.info("从数据库中获取CLWAPI配置信息时出错", e);
		} catch (IOException e) {
			logger.info("读取系统配置文件:" + ModCommonConstant.SYS_CONF_FILE_PATH, e);
		}
	}
}
