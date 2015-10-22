/**
 * @(#)ResourceService.java 2013-4-11
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-11 下午4:34:19
 */
@Path(value = "/")
public interface ResourceService {
	/**
	 * 终端获取资源API
	 * 
	 * @param token
	 *            用户token
	 * @param type
	 *            资源类型
	 * @param resId
	 *            资源ID
	 * @return
	 */
	@GET
	@Path(value = "getresource/{type}/{resId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Object getResource(@HeaderParam("access_token") String token,
			@PathParam("type") String type, @PathParam("resId") String resId);
}
