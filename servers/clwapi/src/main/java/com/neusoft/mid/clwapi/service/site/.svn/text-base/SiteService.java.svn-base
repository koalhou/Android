/**
 * @(#)SiteService.java 2013-3-24
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.site;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-24 下午4:19:45
 */
@Path(value = "/")
public interface SiteService {

	/**
	 * 企业站点信息查询处理.
	 * 
	 * @param type
	 *            登录信息JSON串
	 * @return 登录结果消息.
	 */
	@GET
	@Path(value = "{type}")
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response getEnterpriseSiteInfos(@PathParam("type") String type);
}
