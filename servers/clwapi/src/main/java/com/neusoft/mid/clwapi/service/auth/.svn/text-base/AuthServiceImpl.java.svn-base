/**
 * @(#)AuthServiceImpl.java 2013-6-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.auth;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.entity.auth.CancelAuthConfInfo;
import com.neusoft.mid.clwapi.entity.auth.CancelAuthReq;
import com.neusoft.mid.clwapi.mapper.AuthMapper;
import com.neusoft.mid.clwapi.tools.JacksonUtils;
import com.neusoft.mid.clwapi.tools.PropertiesTools;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-6-25 下午1:35:21
 */
public class AuthServiceImpl implements AuthService {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);
	
	@Autowired
	private AuthMapper authMapper;

	/**
	 * 关闭企业使用终端软件权限接口
	 * 
	 * @param enterpriseId
	 *            被取消权限的企业编码
	 * @param userId
	 *            被取消权限的用户ID
	 * @param body
	 *            处理信息
	 */
	@Override
	public Response removeEnterpriseAuth(String enterpriseId,
			String body) {
		logger.info("本次交易为根据企业ID关闭其使用手机客户端软件权限");
		logger.info("被取消权限的企业编码:" + enterpriseId);

		// 解析并校验请求body信息是否有效
		Response checkResp = checkReqBody(body);
		if (null == checkResp) {
			authMapper.removeUserAuthByEnt(enterpriseId);
			logger.info("清除指定企业ID的用户授权信息操作成功");
			return Response.ok().build();
		} else {
			return checkResp;
		}
	}

	/**
	 * 关闭用户使用终端软件权限接口
	 * 
	 * @param enterpriseId
	 *            被取消权限的企业编码
	 * @param userId
	 *            被取消权限的用户ID
	 * @param body
	 *            处理信息
	 */
	@Override
	public Response removeUserAuth(String enterpriseId, String userId,
			String body) {
		logger.info("本次交易为根据用户ID关闭其使用手机客户端软件权限");
		logger.info("被取消权限的企业编码:" + enterpriseId);
		logger.info("被取消权限的用户ID:" + userId);
		// 解析并校验请求body信息是否有效
		Response checkResp = checkReqBody(body);
		if (null == checkResp) {
			// 清除指定企业ID下特定用户ID的用户授权信息
			authMapper.removeUserAuthByUser(enterpriseId, userId);
			logger.info("清除指定企业ID的用户授权信息操作成功");
			return Response.ok().build();
		} else {
			return checkResp;
		}
	}

	/**
	 * 解析并校验请求body信息是否有效.
	 * @param body 请求body信息.
	 * @return 校验结果.
	 */
	private Response checkReqBody(String body) {
		// 解析HTTP协议中BODY内容
		CancelAuthReq reqBody = JacksonUtils.fromJsonRuntimeException(body,
				CancelAuthReq.class);
		
		if (null == reqBody || StringUtils.isEmpty(reqBody.getUserName())
				|| StringUtils.isEmpty(reqBody.getPassword())
				|| StringUtils.isEmpty(reqBody.getOperateUserId())) {
			logger.info("取消使用权限的HTTP请求中BODY内容非法");

			return Response.status(Response.Status.BAD_REQUEST)
					.entity(ErrorConstant.ERROR10001.toJson())
					.header("Content-Type", "application/json;charset=UTF-8")
					.build();
		} else {
			logger.info("访问接口的用户名:" + reqBody.getUserName());
			logger.info("访问接口的用户密码:" + reqBody.getPassword());
			logger.info("设置取消权限的平台管理员用户ID:" + reqBody.getOperateUserId());
			
			return loadAppConfInfo(reqBody);
		}
	}
	
	/**
	 * 加载系统所需资源信息.
	 */
	private Response loadAppConfInfo(CancelAuthReq reqBody) {
		try {
			String appId = PropertiesTools.readValue(
					ModCommonConstant.SYS_CONF_FILE_PATH, "clwapi.auth.appid");
			CancelAuthConfInfo appConf = authMapper.getConfInfo(appId);
			if (null == appConf) {
				logger.error("数据库中不存在APP_ID=" + appId + "的系统配置信息");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(ErrorConstant.ERROR90000.toJson())
						.header("Content-Type", "application/json;charset=UTF-8")
						.build();
			} else {
				logger.info("数据库中存在APP_ID=" + appId + "的系统配置信息");
				logger.debug("数据库中登录用户名:" + appConf.getUserName());
				logger.debug("数据库中登录用户密码:" + appConf.getUserPwd());
				if (reqBody.getUserName().equals(appConf.getUserName())
						&& reqBody.getPassword().equals(appConf.getUserPwd())) {
					logger.info("本次交易中所使用的用户名及密码正确");
					return null;
				} else {
					logger.info("本次交易中所使用的用户名及密码不正确");
					return Response.status(Response.Status.UNAUTHORIZED)
							.entity(ErrorConstant.ERROR_LOGIN_10102.toJson())
							.header("Content-Type", "application/json;charset=UTF-8")
							.build();
				}
			}
		} catch (DataAccessException e) {
			logger.info("从数据库中获取[接入层关闭使用权限API]信息时出错", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(ErrorConstant.ERROR99999.toJson())
					.header("Content-Type", "application/json;charset=UTF-8")
					.build();
		} catch (IOException e) {
			logger.info("读取系统配置文件:" + ModCommonConstant.SYS_CONF_FILE_PATH, e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(ErrorConstant.ERROR90000.toJson())
					.header("Content-Type", "application/json;charset=UTF-8")
					.build();
		}

	}

}
