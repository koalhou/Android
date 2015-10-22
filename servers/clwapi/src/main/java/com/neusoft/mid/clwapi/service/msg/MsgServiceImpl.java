/**
 * @(#)MsgServiceImpl.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.msg;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.HttpConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.common.UserInfoKey;
import com.neusoft.mid.clwapi.entity.msg.CoreMsgInfo;
import com.neusoft.mid.clwapi.entity.msg.DiaoduInfo;
import com.neusoft.mid.clwapi.entity.msg.FindMsgMoldResp;
import com.neusoft.mid.clwapi.entity.msg.MsgMoldInfo;
import com.neusoft.mid.clwapi.entity.msg.MsgMoldReq;
import com.neusoft.mid.clwapi.entity.msg.MsgMoldResp;
import com.neusoft.mid.clwapi.entity.msg.PicCommandInfo;
import com.neusoft.mid.clwapi.entity.msg.PushMsgReceivedInfo;
import com.neusoft.mid.clwapi.entity.oauth.MobileUsrAllInfo;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.MsgMapper;
import com.neusoft.mid.clwapi.mapper.OauthMapper;
import com.neusoft.mid.clwapi.process.delivermsg.SendDeliverMsgService;
import com.neusoft.mid.clwapi.threadpool.MsgSendThreadPool;
import com.neusoft.mid.clwapi.tools.BeanUtil;
import com.neusoft.mid.clwapi.tools.CheckRequestParam;
import com.neusoft.mid.clwapi.tools.JacksonUtils;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午6:39:47
 */
public class MsgServiceImpl implements MsgService {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	@Autowired
	private MsgMapper msgMapper;

	@Autowired
	private OauthMapper oauthMapper;

	@Context
	private MessageContext context;

	@Autowired
	private SendDeliverMsgService sendDeliverMsgService;

	@Resource
	private MsgSendThreadPool msgSendThreadPool;

	/**
	 * 车辆调度处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param ttMsgInfo
	 *            车辆调度信息.
	 * @return 车辆调度结果信息.
	 */
	@Override
	public Response sendTtMsg(String token, String ttMsgInfo) {
		logger.info("处理车辆调度信息下发请求");

		DiaoduInfo ttMsg = JacksonUtils.fromJsonRuntimeException(ttMsgInfo,
				DiaoduInfo.class);
		if (null == ttMsg
				|| StringUtils.isEmpty(StringUtils.strip(ttMsg.getVins()))
				|| StringUtils.isEmpty(StringUtils.strip(ttMsg.getMsg()))
				|| StringUtils.isEmpty(StringUtils.strip(ttMsg.getType()))) {
			logger.info("客户端同步请求内容非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		} else {
			CoreMsgInfo msgInfo = new CoreMsgInfo();
			msgInfo.setVin(ttMsg.getVins());
			msgInfo.setType(parseTtmsgType(ttMsg.getType()));
			msgInfo.setMsg(ttMsg.getMsg());
			msgInfo.setUserId(context.getHttpHeaders().getHeaderString(
					UserInfoKey.USR_ID));

			// 异步处理调度信息下发流程.
			msgSendThreadPool.executeSendMtMsgThread(msgInfo, msgMapper,
					sendDeliverMsgService);

			// 向终端返回服务端接收状态信息.
			return Response.ok().header(HttpHeaders.CACHE_CONTROL, "no-store")
					.header("Pragma", "no-cache").build();
		}

	}
	
	/**
	 * 解析调度消息下发类型.
	 * 
	 * @param reqTypeCont
	 * @return
	 */
	private String parseTtmsgType(String reqTypeCont) {
		StringBuffer strBuff = new StringBuffer();
		String setinfo0 = "0";
		String setinfo1 = "0";
		String setinfo2 = "0";
		String setinfo3 = "0";
		String[] typeArray = StringUtils.split(StringUtils.strip(reqTypeCont),
				"|");
		for (String set : typeArray) {
			String type = StringUtils.strip(set);
			if ("00".equals(type)) {
				// 00–紧急
				setinfo3 = "1";
			} else if ("01".equals(type)) {
				// 00–语音播报
				setinfo1 = "1";
			} else if ("02".equals(type)) {
				// 00–车载屏
				setinfo0 = "1";
			} else if ("03".equals(type)) {
				// 00–终端显示器
				setinfo2 = "1";
			} else {
				logger.info("终端上报的下发方式值:" + type + "信息非法");
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}
		}
		strBuff.append(setinfo0).append(setinfo1).append(setinfo2)
				.append(setinfo3).append("0000");
		String msgType = strBuff.toString();
		logger.info("拼装后的消息下发类型串为:" + msgType);
		return msgType;
	}


	/**
	 * 车辆拍照处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param photoInfo
	 *            车辆拍照命令信息.
	 * @return 车辆拍照结果信息.
	 */
	@Override
	public Response sendPhotoMsg(String token, String photoInfo) {
		logger.info("处理车辆拍照命令下发请求");

		PicCommandInfo picCommand = JacksonUtils.fromJsonRuntimeException(
				photoInfo, PicCommandInfo.class);
		if (null == picCommand
				|| StringUtils
						.isEmpty(StringUtils.strip((picCommand.getVins())))
				|| StringUtils
						.isEmpty(StringUtils.strip((picCommand.getType())))) {
			logger.info("客户端同步请求内容非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		} else {
			//校验拍照指令通道信息是否正确
			checkPicChannel(picCommand.getType());
			/*根据用户令牌信息获取有效终端绑定用户信息,
			防止用户client_id信息变化后拍照所得照片不能到达指定用户*/
			MobileUsrAllInfo usrInfo = oauthMapper.getValidUsrBindInfo(token);
			if (null == usrInfo) {
				logger.info("本次交易中使用token信息无对应的有效用户信息");
				return Response
						.status(Response.Status.UNAUTHORIZED)
						.entity(ErrorConstant.ERROR20001.toJson())
						.header("Content-Type",
								"application/json;charset=UTF-8").build();
			} else {
				CoreMsgInfo msgInfo = new CoreMsgInfo();
				msgInfo.setVin(picCommand.getVins());
				msgInfo.setType(picCommand.getType());

				// 异步处理调度信息下发流程.
				msgSendThreadPool.executeSendPhotoThread(msgInfo, usrInfo, msgMapper,
						sendDeliverMsgService);
				// 向终端返回服务端接收状态信息.
				return Response.ok().header(HttpHeaders.CACHE_CONTROL, "no-store")
						.header("Pragma", "no-cache").build();
			}
		}
	}
	
	/**
	 * 校验拍照指令通道信息是否正确.
	 * @param reqChannel
	 */
	public void checkPicChannel(String reqChannel) {
		String[] channelArray = StringUtils.split(
				StringUtils.strip(reqChannel), "|");
		for (String channelTemp : channelArray) {
			if (!("1".equals(channelTemp) || "2".equals(channelTemp)
					|| "3".equals(channelTemp) || "4".equals(channelTemp))) {
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}
		}
	}

	/**
	 * 新增调度信息模板处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param remark
	 *            消息模板内容.
	 * @return 调度信息模板信息.
	 */
	@Override
	public String addMsgMold(String token, String remark) {
		MsgMoldReq req = JacksonUtils.fromJsonRuntimeException(remark,
				MsgMoldReq.class);
		try {
			BeanUtil.checkObjectLegal(req);
		} catch (Exception e) {
			logger.error("新增调度信息模板-客户端请求缺少必要参数信息" + e.getMessage());
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

		String userId = context.getHttpHeaders().getHeaderString(UserInfoKey.USR_ID);
		if (CheckRequestParam.isEmpty(userId)) {
			logger.info("新增调度信息模板-缺少用户ID");
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		logger.info("新增调度信息模板-向数据库中插入调度信息模板");
		MsgMoldInfo obj = new MsgMoldInfo();
		obj.setUserId(userId);
		obj.setRemark(req.getTtmsg_remark());
		msgMapper.insertMsgMoldInfo(obj);

		logger.info("新增调度信息模板-查询新入库模板信息");
		obj = msgMapper.getMsgMoldInfo(obj);
		if (CheckRequestParam.isEmpty(obj)) {
			logger.info("新增调度信息模板-insert信息模板不成功");
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		logger.info("新增调度信息模板-更新用户信息表中模板ETag");
		upUserETag(token, userId, obj.getEditT());

		MsgMoldResp result = new MsgMoldResp();
		result.setId(obj.getId());
		result.setEditT(obj.getEditT());
		return JacksonUtils.toJsonRuntimeException(result);

	}

	/**
	 * 编辑调度信息模板处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param tempId
	 *            消息模板ID.
	 * @param remark
	 *            消息模板信息.
	 * @return ETag.
	 */
	@Override
	public String eidtMsgMold(String token, String tempId, String remark) {
		tempId = StringUtils.strip(tempId);

		MsgMoldReq req = JacksonUtils.fromJsonRuntimeException(remark,
				MsgMoldReq.class);
		try {
			BeanUtil.checkObjectLegal(req);
		} catch (Exception e) {
			logger.error("编辑调度信息模板-客户端请求缺少必要参数信息" + e.getMessage());
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

		String userId = context.getHttpHeaders().getHeaderString(UserInfoKey.USR_ID);
		if (CheckRequestParam.isEmpty(userId)) {
			logger.info("编辑调度信息模板-缺少用户ID");
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		int num = msgMapper.getMsgMoldCount(tempId, userId);
		if (num == 0) {
			logger.info("编辑调度信息模板-修改的调度模板" + tempId + "不存在");
			throw new ApplicationException(ErrorConstant.ERROR10010,
					Response.Status.NOT_FOUND);
		}

		logger.info("编辑调度信息模板-修改调度信息模板");
		MsgMoldInfo obj = new MsgMoldInfo();
		obj.setId(tempId);
		obj.setUserId(userId);
		obj.setRemark(req.getTtmsg_remark());
		msgMapper.updateUserMsgMold(obj);

		logger.info("编辑调度信息模板-查询模板最后修改时间");
		obj = msgMapper.getMsgMoldInfo(obj);
		if (CheckRequestParam.isEmpty(obj)
				|| CheckRequestParam.isEmpty(obj.getId())) {
			logger.info("编辑调度信息模板-修改的调度模板" + tempId + "不存在");
			throw new ApplicationException(ErrorConstant.ERROR10010,
					Response.Status.NOT_FOUND);
		}

		logger.info("编辑调度信息模板-更新用户信息表中模板ETag");
		upUserETag(token, userId, obj.getEditT());

		MsgMoldResp result = new MsgMoldResp();
		result.setEditT(obj.getEditT());
		return JacksonUtils.toJsonRuntimeException(result);
	}

	/**
	 * 删除调度信息模板处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param tempId
	 *            消息模板ID.
	 * @return ETag.
	 */
	@Override
	public String delMsgMold(String token, String tempId) {
		tempId = StringUtils.strip(tempId);
		String userId = context.getHttpHeaders().getHeaderString(UserInfoKey.USR_ID);
		if (CheckRequestParam.isEmpty(userId)) {
			logger.info("删除调度信息模板-缺少用户ID");
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		int num = msgMapper.getMsgMoldCount(tempId, userId);
		if (num == 0) {
			logger.info("删除调度信息模板-删除的调度模板" + tempId + "不存在");
			throw new ApplicationException(ErrorConstant.ERROR10010,
					Response.Status.NOT_FOUND);
		}

		logger.info("删除调度信息模板-逻辑删除调度信息模板");
		MsgMoldInfo obj = new MsgMoldInfo();
		obj.setId(tempId);
		obj.setUserId(userId);
		obj.setDelFlag("1");
		msgMapper.updateUserMsgMold(obj);

		logger.info("删除调度信息模板-查询模板的最后修改时间");
		obj = msgMapper.getMsgMoldInfo(obj);
		if (CheckRequestParam.isEmpty(obj)
				|| CheckRequestParam.isEmpty(obj.getId())) {
			logger.info("删除调度信息模板-删除的调度模板" + tempId + "不存在");
			throw new ApplicationException(ErrorConstant.ERROR10010,
					Response.Status.NOT_FOUND);
		}

		logger.info("删除调度信息模板-更新用户信息表中模板ETag");
		upUserETag(token, userId, obj.getEditT());

		MsgMoldResp result = new MsgMoldResp();
		result.setEditT(obj.getEditT());
		return JacksonUtils.toJsonRuntimeException(result);
	}

	/**
	 * 更新用户信息表的模板ETag处理.
	 * 
	 * @param usrID
	 *            用户ID.
	 * @param ETag
	 *            更新时间.
	 * @return
	 */
	/**
	 * 更新用户个人消息模板标记信息. edit by majch,使用mybatis注解传参.
	 * 
	 * @param token
	 *            用户当前使用的令牌信息.
	 * @param userID
	 *            用户ID
	 * @param ETag
	 *            个人消息模板标记信息.
	 */
	private void upUserETag(String token, String userID, String ETag) {
		oauthMapper.updateUserTemplateEtag(token, ETag, userID);
	}

	/**
	 * 获取调度模板信息
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param eTag
	 *            更新时间
	 * @return 调度信息模板信息.
	 */
	@Override
	public Object getMsgMold(String token, String eTag) {
		// 验证终端参数标志
		boolean checkEtag = true;
		// 获取用户ID
		String usrId = context.getHttpHeaders().getHeaderString(
				UserInfoKey.USR_ID);
		String plaETag = context.getHttpHeaders().getHeaderString(
				UserInfoKey.RULE_ETAG);
		logger.info("处理获取调度模板信息请求");
		logger.info("终端持有的ETag:" + eTag);
		logger.info("平台持有的ETag:" + plaETag);
		// 终端持有的时间
		Date dateTer = null;
		// 平台持有的时间
		Date datePla = null;
		// 验证
		if (eTag == null) {
			logger.error("If-None-Match值为空");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

		if (!eTag.equals(HttpConstant.TIME_ZERO)) {
			try {
				dateTer = BeanUtil
						.checkTimeForm(eTag, HttpConstant.TIME_FORMAT);
			} catch (ParseException e) {
				logger.error("终端上传的参数非法" + e.getMessage());
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}
		} else {
			// 不需要验证终端上传的标志
			checkEtag = false;
		}

		// 获取用户调度模板
		List<MsgMoldInfo> list = msgMapper.getMsgMoldInfoWithUsr(usrId);

		// 如果用户个人调度模板表内无数据，直接通知客户端不需更新
		if (list == null || list.size() == 0) {
			logger.info("调度模板表内未检索到该用户的有效模板信息");
			return Response.notModified().build();
		} else {
			// 数据中最新的标记信息
			String e = list.get(0).getEditT();
			// 平台标志异常，执行更正策略
			if (!StringUtils.equals(e, plaETag)) {
				logger.info("平台用户表中的标记异常，执行更正策略");
				// 不相同更新用户表中的ETag值
				upUserETag(token, usrId, e);
				plaETag = e;
			}
		}

		// 转换平台标志时间
		try {
			datePla = BeanUtil.checkTimeForm(plaETag, HttpConstant.TIME_FORMAT);
		} catch (ParseException e) {
			logger.error("转换平台标记时间时发生异常" + e.getMessage());
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		// 用户个人推送模板为空，返回NO_CONTENT
		if ((list == null || list.size() == 0)
				&& !plaETag.equals(HttpConstant.ZERO)) {
			logger.info("用户模板为空，不需更新");
			return Response.noContent().build();
		} else if (checkEtag && dateTer.compareTo(datePla) == 0) {
			// 如果终端时间与平台时间相等则无数据返回
			logger.info("终端数据版本已经是最新的，不需更新");
			return Response.notModified().build();
		} else if (checkEtag && dateTer.compareTo(datePla) > 0) {
			logger.error("终端数据版本大于平台数据版本");
			throw new ApplicationException(ErrorConstant.ERROR10103,
					Response.Status.BAD_REQUEST);
		}

		// 下发给客户端的结果列表
		List<MsgMoldInfo> temp = new ArrayList<MsgMoldInfo>();
		// 遍历模板列表，删除模板中已失效的部分
		Iterator<MsgMoldInfo> it = list.iterator();
		while (it.hasNext()) {
			MsgMoldInfo iMsgMoldInfo = it.next();
			// 如果模板有效则装入下发列表内
			if (iMsgMoldInfo.getDel().equals(HttpConstant.MSGMOLD_DEL_FALSE)) {
				temp.add(iMsgMoldInfo);
			}
		}

		FindMsgMoldResp iFindMsgMoldResp = new FindMsgMoldResp();
		iFindMsgMoldResp.setEtag(plaETag);
		iFindMsgMoldResp.setList(temp);
		return JacksonUtils.toJsonRuntimeException(iFindMsgMoldResp);
	}

	/**
	 * 推送消息已到达消息通知接口
	 * @author majch. 
	 * @param token
	 *            访问令牌信息.
	 * @param msg
	 *            成功接收到的消息内容.
	 * @return 服务端处理结果信息.
	 */
	@Override
	public Object pushMsgReceived(String token, String msg) {
		logger.info("接收到[推送消息已到达消息通知]请求信息");
		// 获取用户ID信息.
		String userId = context.getHttpHeaders().getHeaderString(UserInfoKey.USR_ID);

		PushMsgReceivedInfo msgInfo = JacksonUtils.fromJsonRuntimeException(msg,
				PushMsgReceivedInfo.class);
		if (StringUtils.isEmpty(msgInfo.getClientId())
				|| StringUtils.isEmpty(msgInfo.getMsgType())
				|| StringUtils.isEmpty(msgInfo.getMsgId())) {
			logger.error("请求参数非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		} else {
			if ("01".equals(msgInfo.getMsgType())) {
				logger.info("客户端成功接收到的推送消息类型为:告警信息");
				msgMapper.updateAlarmPushMsgReceived(msgInfo.getMsgId(),
						userId, msgInfo.getClientId());
			} else if ("02".equals(msgInfo.getMsgType())) {
				logger.info("客户端成功接收到的推送消息类型为:照片信息");
				msgMapper.updatePicPushMsgReceived(msgInfo.getMsgId(), userId,
						msgInfo.getClientId());
			} else if ("03".equals(msgInfo.getMsgType())) {
				logger.info("客户端成功接收到的推送消息类型为:新闻");
				msgMapper.updateNewPushMsgReceived(msgInfo.getMsgId(), userId,
						msgInfo.getClientId());
			} else {
				logger.error("请求信息中[msgType]值非法");
				throw new ApplicationException(ErrorConstant.ERROR10001,
						Response.Status.BAD_REQUEST);
			}
		}
		return Response.ok().build();
	}

}
