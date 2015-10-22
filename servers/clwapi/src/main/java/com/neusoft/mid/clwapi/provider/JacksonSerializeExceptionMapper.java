/**
 * @(#)JacksonSerializeExceptionMapper.java 2013-4-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.provider;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.HttpConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.exception.common.JacksonSerializeException;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-8 下午2:30:59
 */
@Provider
@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
public class JacksonSerializeExceptionMapper implements
		ExceptionMapper<JacksonSerializeException> {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	@Override
	public Response toResponse(JacksonSerializeException e) {
		logger.error("将实体内容序列化为JSON字符串时出错", e);
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(ErrorConstant.ERROR90000.toJson()).build();
	}
}
