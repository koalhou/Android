/**
 * @(#)UsrServiceImpl.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.usr;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.common.UserInfoKey;
import com.neusoft.mid.clwapi.entity.usr.ConsultantInfo;
import com.neusoft.mid.clwapi.entity.usr.ConsultantInfoResp;
import com.neusoft.mid.clwapi.entity.usr.UserBehavior;
import com.neusoft.mid.clwapi.entity.usr.UserModifyPwdReq;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.UsrMapper;
import com.neusoft.mid.clwapi.tools.CheckRequestParam;
import com.neusoft.mid.clwapi.tools.JacksonUtils;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午8:50:21
 */
public class UsrServiceImpl implements UsrService {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	@Autowired
	private UsrMapper usrMapper;
	@Context 
	private MessageContext context;

	/**
	 * 用户旧密码验证.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param oldPwd
	 *            用户旧密码.
	 * @return 密码验证结果.
	 */
	@Override
	public Response checkOldPwd(String token, String oldPwd) {
		logger.info("处理客户端发起的用户原密码验证请求");
		String userId = context.getHttpHeaders().getHeaderString("usr_id");
		
		int userCount = usrMapper.getUserCountByPwd(userId, oldPwd);
		
		if (1 == userCount) {
			logger.info("用户ID:" + userId + "的原密码" + oldPwd + "正确");
			return Response.ok()
					.header(HttpHeaders.CACHE_CONTROL, "no-store")
					.header("Pragma", "no-cache")
					.build();
		} else {
			logger.info("用户ID:" + userId + "的原密码" + oldPwd + "不正确");
			return Response.status(Response.Status.NO_CONTENT)
					.header(HttpHeaders.CACHE_CONTROL, "no-store")
					.header("Pragma", "no-cache")
					.build();
		}
	}


	/**
	 * 终端用户修改密码处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param modifyPwdInfo
	 *            密码修改信息.
	 * @return 密码修改结果.
	 */
	@Override
	public Response modifyPwd(String token, String modifyPwdInfo) {
		logger.info("处理客户端发起的用户密码修改请求");
		
		// 用户密码修改请求信息反序列化
		UserModifyPwdReq reqInfo = JacksonUtils.fromJsonRuntimeException(
				modifyPwdInfo, UserModifyPwdReq.class);
		
		if (null != reqInfo) {
			if (CheckRequestParam.isEmpty((reqInfo.getOldPwd())) || CheckRequestParam.isEmpty((reqInfo.getNewPwd()))) {
				logger.error("用户密码修改请求信息存在非法[参数内容为空]请求参数");
				// 存在参数为空参数，则向客户端返回错误应答
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			} else {

				String userId = context.getHttpHeaders().getHeaderString("usr_id");
				int userCount = usrMapper.getUserCountByPwd(userId, reqInfo.getOldPwd());
				if (1 == userCount) {
					usrMapper.updateUserPwd(userId, reqInfo.getOldPwd(), reqInfo.getNewPwd());
					return Response.ok()
							.header(HttpHeaders.CACHE_CONTROL, "no-store")
							.header("Pragma", "no-cache")
							.build();
				} else {
					logger.info("用户ID:" + userId + "的原密码" + reqInfo.getOldPwd() + "不正确");
					return Response.status(Response.Status.NO_CONTENT)
							.header(HttpHeaders.CACHE_CONTROL, "no-store")
							.header("Pragma", "no-cache")
							.build();
				}
			}
			
		} else {
			logger.error("用户密码修改请求信息非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

	}


	/**
	 * 用户行为信息上报接口.
	 * 
	 * @param behaviorInfo
	 *            终端用户行为信息.
	 * @return 行为结果上报结果.
	 */
	@Override
	public Response behavior(String token, String behaviorInfo) {
		logger.info("处理客户端发起的用户行为信息上报请求");

		// 将客户端上报的用户行为数据转化为对象
		UserBehavior userBehavior = JacksonUtils.fromJsonRuntimeException(
				behaviorInfo, UserBehavior.class);
		// 如成功获取到用户行为信息对象
		if (null != userBehavior) {
			// 检验每个行为参数信息必须为非空
			if (StringUtils.isEmpty(StringUtils.strip(userBehavior
					.getModuleId()))
					|| StringUtils.isEmpty(StringUtils.strip(userBehavior
							.getModuleSonId()))) {
				logger.error("终端上报的用户行为信息存在非法[参数内容为空]请求参数");
				// 存在参数为空参数，则向客户端返回错误应答
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			} else {
				String userId = context.getHttpHeaders().getHeaderString(
						UserInfoKey.USR_ID);
				String enterpriseId = context.getHttpHeaders().getHeaderString(
						UserInfoKey.ENTERPRISE_ID);
				if (StringUtils.isEmpty(userId)
						|| StringUtils.isEmpty(enterpriseId)) {
					logger.info("服务端未正确获取到USER_ID或ENTERPRISE_ID信息");
					// 存在参数为空参数，则向客户端返回错误应答
					throw new ApplicationException(ErrorConstant.ERROR90000,
							Response.Status.INTERNAL_SERVER_ERROR);
				} else {
					userBehavior.setUsrId(userId);
					userBehavior.setEntrepriseId(enterpriseId);
					// 向数据库中添加用户行为信息
					usrMapper.insertUserBehavior(userBehavior);
					return Response.ok().build();
				}
			}
		} else {
			logger.error("用户行为信息上报请求信息非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

	}

	/**
	 * 获取用户所属企业所在地的安芯顾问信息.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @return 用户所属企业所在地的安芯顾问信息.
	 */
	@Override
	public Response consultant(String token) {
		String enterpriseId = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ENTERPRISE_ID);
		if (StringUtils.isEmpty(enterpriseId)) {
			logger.error("未成功获取用户所属企业ID信息");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(ErrorConstant.ERROR90000.toJson())
					.header(HttpHeaders.CACHE_CONTROL, "no-store")
					.header("Pragma", "no-cache").build();
		} else {
			ConsultantInfoResp consultantInfoResp= new ConsultantInfoResp();
			List<ConsultantInfo> consultantList = usrMapper
					.getConsultantInfos(enterpriseId);
			if (null == consultantList || consultantList.size() == 0) {
				logger.info("数据库中不存在与企业ID=" + enterpriseId + "对应的安芯顾问信息");
				return Response.status(Response.Status.NO_CONTENT)
						.header(HttpHeaders.CACHE_CONTROL, "no-store")
						.header("Pragma", "no-cache").build();
			} else {
				consultantInfoResp.setConsultantList(consultantList);
				logger.info("成功获取到企业ID=" + enterpriseId + "对应的安芯顾问信息");
				return Response
						.ok()
						.entity(JacksonUtils
								.toJsonRuntimeException(consultantInfoResp))
						.header(HttpHeaders.CACHE_CONTROL, "no-store")
						.header("Pragma", "no-cache").build();
			}
		}
	}

}
