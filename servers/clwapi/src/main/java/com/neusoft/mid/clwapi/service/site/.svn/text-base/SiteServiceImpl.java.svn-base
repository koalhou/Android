/**
 * @(#)SiteServiceImpl.java 2013-3-24
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.site;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neusoft.mid.clwapi.common.ModCommonConstant;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-24 下午4:22:38
 */
public class SiteServiceImpl implements SiteService {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	/**
	 * 企业站点信息查询处理.
	 * 
	 * @param type
	 *            登录信息JSON串
	 * @return 登录结果消息.
	 */
	@Override
	public Response getEnterpriseSiteInfos(String type) {
		logger.info("站点类型为:" + type);

		return Response.ok(Response.Status.ACCEPTED).build();
	}

}
