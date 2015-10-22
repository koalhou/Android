/**
 * @(#)AdviseServiceImpl.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.advise;

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
import com.neusoft.mid.clwapi.entity.advise.UserAdviseReq;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.AdviseMapper;
import com.neusoft.mid.clwapi.tools.JacksonUtils;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午10:01:08
 */
public class AdviseServiceImpl implements AdviseService {


	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	@Autowired
	private AdviseMapper adviseMapper;
	@Context 
	private MessageContext context;

	/**
	 * 终端用户问题反馈.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param adviseCont
	 *            终端用户反馈内容.
	 * @return 终端用户问题处理结果信息.
	 */
	@Override
	public Response sendTtMsg(String token,
			String adviseCont) {
		
		UserAdviseReq reqInfo = JacksonUtils.fromJsonRuntimeException(
				adviseCont, UserAdviseReq.class);

		if (StringUtils.isEmpty(StringUtils.strip(reqInfo.getAdviseCont()))) {
			logger.info("用户反馈内容为空");
			// 存在参数为空参数，则向客户端返回错误应答
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		} else {
			String userId = context.getHttpHeaders().getHeaderString(UserInfoKey.USR_ID);
			String enterpriseId = context.getHttpHeaders().getHeaderString(UserInfoKey.ENTERPRISE_ID);
			if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(enterpriseId)) {
				logger.info("服务端未正确获取到USER_ID或ENTERPRISE_ID信息");
				// 存在参数为空参数，则向客户端返回错误应答
				throw new ApplicationException(ErrorConstant.ERROR90000,
						Response.Status.INTERNAL_SERVER_ERROR);
			} else {
				// 向数据库中插入信息.
				adviseMapper.insertUserAdviseInfo(userId, enterpriseId, reqInfo.getAdviseCont());
				return Response.ok()
						.header(HttpHeaders.CACHE_CONTROL, "no-store")
						.header("Pragma", "no-cache")
						.build();
			}
		}
	}

}
