/**
 * @(#)MyExceptionMapper.java 2013-4-7
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
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.HttpConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-7 下午5:22:47
 */
@Provider
@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
public class DataAccessExceptionMapper implements
		ExceptionMapper<DataAccessException> {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	@Override
	public Response toResponse(DataAccessException e) {
		logger.error("发生数据异常", e);
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(ErrorConstant.ERROR90000.toJson()).build();
	}

}
