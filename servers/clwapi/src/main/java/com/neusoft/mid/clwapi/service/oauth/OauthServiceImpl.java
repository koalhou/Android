/**
 * @(#)OauthServiceImpl.java 2013-3-24
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.oauth;

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
import com.neusoft.mid.clwapi.entity.oauth.CheckLoginReq;
import com.neusoft.mid.clwapi.entity.oauth.LoginResp;
import com.neusoft.mid.clwapi.entity.oauth.MobileBindingInfo;
import com.neusoft.mid.clwapi.entity.oauth.UsrInfo;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.OauthMapper;
import com.neusoft.mid.clwapi.process.delivermsg.DeliverMsgResult;
import com.neusoft.mid.clwapi.process.delivermsg.SendDeliverMsgService;
import com.neusoft.mid.clwapi.service.common.UsrOauthService;
import com.neusoft.mid.clwapi.service.software.SoftwareServiceImpl;
import com.neusoft.mid.clwapi.tools.CheckRequestParam;
import com.neusoft.mid.clwapi.tools.JacksonUtils;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-24 上午10:38:49
 */
public class OauthServiceImpl implements OauthService {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);
	@Context
	private MessageContext context;
	@Autowired
	private OauthMapper oauthMapper;
	@Autowired
	private UsrOauthService usrOauthService;
	@Autowired
	private SendDeliverMsgService sendDeliverMsgService;
	@Resource
	private SoftwareServiceImpl softwareService;
	

	/**
	 * AccessToken更新处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param refeshToken
	 *            更新令牌信息.
	 * @return 令牌更新结果信息.
	 */
	@Override
	public Response tokenRefesh(String token, String refeshToken) {

		logger.info("用户访问令牌信息为:" + token);
		logger.info("用户使用的更新令牌信息为:" + refeshToken);

		return Response.ok(Response.Status.ACCEPTED).build();
	}

	/**
	 * 终端用户自动登录处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param version
	 *            终端软件版本.
	 * @return 自动登录结果消息.
	 */
	@Override
	public Response checklogin(String token, String version) {
		logger.info("用户自动登录处理-start");

		String enterpriseId = context.getHttpHeaders().getHeaderString(UserInfoKey.ENTERPRISE_ID);
		String isEnterpriseAllow = oauthMapper.getEnAllowByEnId(enterpriseId);
		if (StringUtils.isEmpty(isEnterpriseAllow)) {
			logger.info("用户所使用的企业ID[" + enterpriseId + "]错误");
			return Response.status(Response.Status.UNAUTHORIZED).entity(ErrorConstant.ERROR_LOGIN_10102.toJson()).build();
		}
		if ("0".equals(isEnterpriseAllow)) {
			logger.info("用户所属企业ID[" + enterpriseId + "]无权使用终端客户端软件登录");
			return Response.status(297).entity(ErrorConstant.ERROR10107.toJson()).build();
		}

		String usrId = context.getHttpHeaders().getHeaderString(UserInfoKey.USR_ID);
		if (CheckRequestParam.isEmpty(usrId)) {
			logger.error("未获取用户ID信息");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ErrorConstant.ERROR90000.toJson()).header(HttpHeaders.CACHE_CONTROL, "no-store").header("Pragma", "no-cache").build();
		}
		UsrInfo xcpUsrInfo = oauthMapper.getXcpUsrInfoByUsrId(usrId);
		if (null == xcpUsrInfo) {
			logger.info("用户[" + usrId + "]不存在有效的登录信息");
			return Response.status(Response.Status.UNAUTHORIZED).entity(ErrorConstant.ERROR_LOGIN_10102.toJson()).build();
		}
		List<String> userLimitList = null;
		Long expireIn = new LoginServiceImpl().getTokenExpireIntervall();

		// 更新终端用户信息
		oauthMapper.refreshTokenExpireTime(token, expireIn, usrId, xcpUsrInfo.getOrgId());

			// userLimitList = oauthMapper.getUserLimit(usrInfo.getUsrId());
			LoginResp loginResp = new LoginResp();
			loginResp.setExpiresIn(expireIn);
			loginResp.setUsrInfo(xcpUsrInfo);
			loginResp.setScope(userLimitList);

			logger.info("用户自动登录处理-end");
			return Response.ok().entity(JacksonUtils.toJsonRuntimeException(loginResp)).build();
	}

	/**
	 * 推送消息绑定处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param clientid
	 *            终端唯一标识.
	 * @return 推送消息绑定处理结果消息.
	 */
	@Override
	public Response pushMsgBinding(String token, String reqCont) {
		logger.info("接收到理推送消息绑定处理请求信息");

		MobileBindingInfo bindInfo = JacksonUtils.fromJsonRuntimeException(
				reqCont, MobileBindingInfo.class);

		if (null == bindInfo
				|| StringUtils
						.isEmpty(StringUtils.strip(bindInfo.getClientid()))) {
			logger.info("客户端同步clientid内容为空");
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(ErrorConstant.ERROR10002.toJson())
					.header("Content-Type", "application/json;charset=UTF-8")
					.build();
		} else {
			logger.info("客户端同步clientid:" + bindInfo.getClientid());
			String userId = context.getHttpHeaders().getHeaderString(
					UserInfoKey.USR_ID);
			if (CheckRequestParam.isEmpty(userId)) {
				logger.error("未获取用户ID信息");
				return Response
						.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(ErrorConstant.ERROR90000.toJson())
						.header("Content-Type",
								"application/json;charset=UTF-8").build();
			}
			DeliverMsgResult result = sendDeliverMsgService
					.sendMobileInfoToCoreService(userId, bindInfo.getClientid());

			if (null != result && "0".equals(result.getCode())) {
				logger.info("核心服务处理本次绑定服务处理成功");
				return Response.ok()
						.header(HttpHeaders.CACHE_CONTROL, "no-store")
						.header("Pragma", "no-cache").build();
			} else {
				logger.info("本次绑定服务处理失败");
				return Response
						.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(ErrorConstant.ERROR90000.toJson())
						.header("Content-Type",
								"application/json;charset=UTF-8").build();
			}
		}

	}

	/**
	 * 用户退出登录接口
	 * 
	 * @param token
	 *            令牌信息
	 */
	@Override
	public String logout(String token) {
		String usrId = context.getHttpHeaders().getHeaderString("usr_id");
		oauthMapper.clearIosToken(usrId);		
		logger.info("用户[ " + usrId + " ](USER_ID)申请登出系统");
		// 更新用户登录状态为登出
		oauthMapper.logoutUsrOauth(token, usrId);
		logger.info("用户[ " + usrId + " ](USER_ID)登出系统成功");
		// 返回登出成功应答
		return HttpConstant.RESP_200;
	}

}
