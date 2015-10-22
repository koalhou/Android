/**
 * @(#)OauthTokenCheckInterceptor.java 2013-4-2
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.interceptor;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.common.UserInfoKey;
import com.neusoft.mid.clwapi.entity.oauth.MobileUsrAllInfo;
import com.neusoft.mid.clwapi.mapper.OauthMapper;
import com.neusoft.mid.clwapi.service.common.UsrOauthService;

/**
 * 用户token信息拦截器. 所有需进行token验证的接口服务均需通过该拦截器进行token信息获取及验证.
 * 如token信息有效,系统将与该token信息绑定的终端用户信息添加到本次交易中HTTP协议的Header中供后续服务使用;
 * 如请求中不包含token信息,则向客户端返回状态码为400的HTTP应答信息;
 * 如请求中包含的token消息服务端侧无对应的有效终端用户信息,则向客户端返回状态码为401的HTTP应答消息;
 * 如token信息正确但有效时长已过期,则向客户端返回状态为298的HTTP应答消息.
 * 
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-2 下午4:25:18
 */
public class OauthTokenCheckInterceptor extends
		AbstractPhaseInterceptor<Message> {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	private static final String ACCESS_TOKEN = "access_token";
	@Autowired
	private UsrOauthService usrOauthService;

	@Autowired
	private OauthMapper oauthMapper;

	/**
	 * @param phase
	 */
	public OauthTokenCheckInterceptor(final String phase) {
		super(phase);
	}

	public OauthTokenCheckInterceptor() {
		this(Phase.UNMARSHAL);
	}

	/**
	 * 终端请求中token验证.
	 * 
	 * @param message
	 */
	@Override
	public void handleMessage(Message message) throws Fault {
		logger.info("用户访问的服务需进行授权认证");
		String token = "";
		boolean isExistToken = false;
		Response response = null;
		Map<String, List<String>> reqHeaders = CastUtils
				.cast((Map<?, ?>) message.get(Message.PROTOCOL_HEADERS));
		if (reqHeaders != null) {
			for (Map.Entry<String, List<String>> e : reqHeaders.entrySet()) {
				if (ACCESS_TOKEN.equalsIgnoreCase(e.getKey())) {
					token = e.getValue().get(0);
					isExistToken = true;
				}
			}
		} else {
			logger.info("接收到的HTTP请求信息不包含HEADER信息");
			response = Response.status(Response.Status.BAD_REQUEST)
					.entity(ErrorConstant.ERROR10001.toJson())
					.header("Content-Type", "application/json;charset=UTF-8")
					.build();
		}
		if (!isExistToken || StringUtils.isEmpty(StringUtils.strip(token))) {
			logger.info("接收到的HTTP请求信息HEADER中不包含\"access_token\"信息");
			response = Response.status(Response.Status.BAD_REQUEST)
					.entity(ErrorConstant.ERROR20000.toJson())
					.header("Content-Type", "application/json;charset=UTF-8")
					.build();
		} else {
			logger.info("请求中包含的access_token=" + token);
			if (checkToken(token)) {
				try {
					MobileUsrAllInfo userInfo = usrOauthService
							.getMblUsrInfoByToken(token);

					if (null == userInfo) {
						logger.info("用户所使用的access_token非法");
						response = Response
								.status(Response.Status.UNAUTHORIZED)
								.entity(ErrorConstant.ERROR20001.toJson())
								.header("Content-Type",
										"application/json;charset=UTF-8")
								.build();
					} else {
						if ("0".equals(userInfo.getIsMobileAllow())) {
							logger.info("用户所属企业机构ID[" + userInfo.getEnterpriseId() + "]无权使用终端客户端软件登录");
							response = Response.status(297).entity(ErrorConstant.ERROR10107.toJson())
									.build();
							oauthMapper.logoutUsrOauth(userInfo.getAccessToken(), userInfo.getUsrId());
							logger.info("系统自动调用注销操作成功");
						} else if (userInfo.getValidSeconds() > 0) {
							// 将与token匹配的终端用户信息添加到本次交易的HTTP请求Header中.
							addUserInfo2HttpHeaderInfo(reqHeaders, userInfo);
							message.put(Message.PROTOCOL_HEADERS, reqHeaders);
						} else {
							logger.info("用户access_token信息已过期,需重新登录或申请延长有效期");
							response = Response.status(298).build();
						}
					}
				} catch (Exception e) {
					logger.error("根据用户token获取其基本信息时出错", e);
					response = Response
							.status(Response.Status.INTERNAL_SERVER_ERROR)
							.entity(ErrorConstant.ERROR90000.toJson())
							.header("Content-Type",
									"application/json;charset=UTF-8").build();
				}
			} else {
				logger.info("接收到的HTTP请求信息HEADER中\"access_token\"信息不符合规范");
				response = Response
						.status(Response.Status.BAD_REQUEST)
						.entity(ErrorConstant.ERROR10002.toJson())
						.header("Content-Type",
								"application/json;charset=UTF-8").build();
			}
		}
		message.getExchange().put(Response.class, response);
	}

	/**
	 * 将与token匹配的终端用户信息添加到本次交易的HTTP请求Header中.
	 * 
	 * @param reqHeaders
	 *            HTTP请求Header信息.
	 * @param userInfo
	 *            与token匹配的终端用户信息
	 * @return 添加有终端用户信息的HTTP请求Header信息.
	 */
	private Map<String, List<String>> addUserInfo2HttpHeaderInfo(
			Map<String, List<String>> reqHeaders, MobileUsrAllInfo userInfo) {
		reqHeaders.put(UserInfoKey.USR_ID, Arrays.asList(userInfo.getUsrId()));
		logger.info("向HTTP Header中添加usr_id:" + userInfo.getUsrId());

		reqHeaders.put(UserInfoKey.ENTERPRISE_ID,
				Arrays.asList(userInfo.getEnterpriseId()));
		logger.info("向HTTP Header中添加enterprise_id:"
				+ userInfo.getEnterpriseId());

		reqHeaders.put(UserInfoKey.ORGANIZATION_ID,
				Arrays.asList(userInfo.getOrganizationId()));
		logger.info("向HTTP Header中添加organization_id:"
				+ userInfo.getOrganizationId());

		reqHeaders.put(UserInfoKey.QUES_ETAG,
				Arrays.asList("" + userInfo.getUserQuesEtag()));
		logger.info("向HTTP Header中添加ques_ETag:" + userInfo.getUserQuesEtag());

		reqHeaders.put(UserInfoKey.MSG_ETAG,
				Arrays.asList("" + userInfo.getUserMsgEtag()));
		logger.info("向HTTP Header中添加msg_ETag:" + userInfo.getUserMsgEtag());

		reqHeaders.put(UserInfoKey.TEMPLATE_ETAG,
				Arrays.asList(userInfo.getUserTemplateEtag()));
		logger.info("向HTTP Header中添加template_ETag:"
				+ userInfo.getUserTemplateEtag());

		reqHeaders.put(UserInfoKey.RULE_ETAG,
				Arrays.asList(userInfo.getUserSendRuleEtag()));
		logger.info("向HTTP Header中添加rule_ETag:"
				+ userInfo.getUserSendRuleEtag());

		reqHeaders.put(UserInfoKey.EN_RPT_DSC_ETAG,
				Arrays.asList(userInfo.getEntReportDscEtag()));
		logger.info("向HTTP Header中添加en_rpt_dsc_ETag:"
				+ userInfo.getEntReportDscEtag());
		return reqHeaders;
	}

	/**
	 * 检查token信息是否合法
	 * 
	 * @param token
	 * @return
	 */
	private boolean checkToken(String token) {
		if (token.length() != 16) {
			logger.info("access_token信息长度为:" + token.length() + "非法");
			return false;
		} else {
			try {
				byte[] tokenByteArray = token.getBytes("UTF-8");
				// Validate the key
				for (byte b : tokenByteArray) {
					if (b == ' ' || b == '\n' || b == '\r' || b == 0) {
						logger.info("access_token信息中包含非法字符");
						return false;
					}
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("将token进行UTF-8转码时出错", e);
				return false;
			}
		}
		return true;
	}

}
