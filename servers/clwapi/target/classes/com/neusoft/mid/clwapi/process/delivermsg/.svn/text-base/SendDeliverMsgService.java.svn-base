/**
 * @(#)SendDeliverMsg.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.process.delivermsg;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.entity.coremsg.CoreMsgResp;
import com.neusoft.mid.clwapi.entity.coremsg.TreeObjectValue;
import com.neusoft.mid.clwapi.feature.ClientLoggingFeature;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-9 下午7:44:33
 */
@Service("sendDeliverMsgService")
public class SendDeliverMsgService {
	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	/**
	 * 核心服务返回码KEY值:c.
	 */
	private static String RESP_KEY_C = "c";

	/**
	 * 核心服务错误描述KEY值:m.
	 */
	private static String RESP_KEY_M = "m";

	/**
	 * 接收超时时间:1500ms.
	 */
	private static int RECEIVE_TIMEOUT = 6000;

	/**
	 * 连接超时时间:1500ms
	 */
	private static int CONNECTION_TIMEOUT = 30;
	@Resource
	private CreateDeliverMsg createDeliverMsg;

	/**
	 * 向核心服务发送命令消息.
	 * 
	 * @param sendCommandInfo
	 *            命令消息实体.
	 * @return 处理结果.
	 */
	public DeliverMsgResult sendCommandToCoreService(
			SendCommandInfo sendCommandInfo) {
		// 生成下发命令XML信息.
		String commandMsg = createDeliverMsg
				.createSendCommandReqMsg(sendCommandInfo);
		// 下发消息.
		return sendMsg(ClwapiAccessCoreCommon.sendPath, commandMsg);
	}

	/**
	 * 向核心服务发送终端用户与推送服务间绑定信息.
	 * 
	 * @param userId
	 *            用户ID.
	 * @param clientId
	 *            客户端ID.
	 * @return 处理结果.
	 */
	public DeliverMsgResult sendMobileInfoToCoreService(String userId,
			String clientId) {
		// 生成下发命令XML信息.
		String commandMsg = createDeliverMsg.createMobileInfoReqMsg(
				userId, clientId);
		//
		return sendMsg(ClwapiAccessCoreCommon.sendPath, commandMsg);
	}

	/**
	 * 向核心服务发送终端用户标记的图片超载消息.
	 * 
	 * @param terminalId
	 *            目的终端号码:车辆vin号.
	 * @param alarmTypeId
	 *            告警类型ID.
	 * @param photoId
	 *            照片ID.
	 * @return 处理结果.
	 */
	public DeliverMsgResult sendMarkAlarmToCoreService(String terminalId,
			String alarmTypeId, String photoId, String enterpriseId) {
		// 生成下发命令XML信息.
		String commandMsg = createDeliverMsg.createMarkAlarmReqMsg(
				terminalId, alarmTypeId, photoId, enterpriseId);
		//
		return sendMsg(ClwapiAccessCoreCommon.sendPath, commandMsg);
	}

	/**
	 * 向核心服务发送消息.
	 * 
	 * @param serverUrl
	 *            核心服务的URL
	 * @param msgCont
	 *            消息内容.
	 * @return 核心处理状态.
	 */
	private DeliverMsgResult sendMsg(String serverUrl, String msgCont) {

		DeliverMsgResult result = new DeliverMsgResult();

		JAXRSClientFactoryBean sf = new JAXRSClientFactoryBean();
		sf.setAddress(serverUrl);
		logger.info("核心服务的URL:" + serverUrl);
		ClientLoggingFeature testFeature = new ClientLoggingFeature();
		List<ClientLoggingFeature> features = new ArrayList<ClientLoggingFeature>();
		features.add(testFeature);
		sf.setFeatures(features);

		BindingFactoryManager manager = sf.getBus().getExtension(
				BindingFactoryManager.class);
		JAXRSBindingFactory factory = new JAXRSBindingFactory();
		factory.setBus(sf.getBus());
		manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID,
				factory);

		WebClient client = sf.createWebClient();
		HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
		// 设置超时时间
		conduit.getClient().setConnectionTimeout(CONNECTION_TIMEOUT);
		conduit.getClient().setReceiveTimeout(RECEIVE_TIMEOUT);

		client.accept(MediaType.APPLICATION_XML);
		try {
			Response resp = client.post(msgCont);int respStatus = resp.getStatus();
			logger.info("核心服务HTTP的应答码为:" + respStatus);
			// 应答状态为200且应答body中包含信息内容
			if (Response.Status.OK.getStatusCode() == respStatus
					&& resp.getLength() != 0) {
				try {
					// 将应答信息内容转化为实体信息.
					CoreMsgResp coreMsgResp = resp.readEntity(CoreMsgResp.class);
					List<TreeObjectValue> treeList = coreMsgResp.getFunction()
							.getResult().getTreeObject().getTreeList();
					for (TreeObjectValue temp : treeList) {
						if (RESP_KEY_C.equals(temp.getKey())) {
							if (0 == Integer.parseInt(temp.getValue())) {
								logger.info("核心服务应答结果码:" + temp.getValue());
							} else {
								logger.info("核心服务处理请求失败,结果码:" + temp.getValue());
							}
							result.setCode(temp.getValue());
						} else if (RESP_KEY_M.equals(temp.getKey())) {
							try {
								byte[] decodeMsg = Base64Utility.decode(temp
										.getValue());
								String msg = new String(decodeMsg, "UTF-8");
								logger.info("核心服务处理请求的描述为:" + msg);
								result.setResultMsg(msg);
							} catch (Base64Exception e) {
								// nothing to do
								logger.error("将核心返回的Base64错误描述解码时出错", e);
							}
						} else {
							logger.info("核心服务返回应答信息tree-object中信息非法");
							return null;
						}
					}
				} catch (Exception e) {
					logger.error("将核心返回的应答内容转换为实体信息时出错" + e.getMessage());
					return null;
				}
			} else {
				logger.info("核心服务处理本次请求失败");
				return null;
			}
		} catch (Exception e) {
			logger.error("向核心发送请求信息时出错" + e.getMessage());
			return null;
		}

		
		return result;

	}

}
