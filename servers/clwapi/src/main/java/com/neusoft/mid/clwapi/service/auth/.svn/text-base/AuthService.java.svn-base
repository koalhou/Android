/**
 * @(#)AuthService.java 2013-6-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-6-25 下午1:29:53
 */
@Path(value = "/")
public interface AuthService {
	
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
	@POST
	@Path(value = "cancel/{enterprise_id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response removeEnterpriseAuth(@PathParam("enterprise_id") String enterpriseId,
			String body);
	
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
	@POST
	@Path(value = "cancel/{enterprise_id}/{user_id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response removeUserAuth(@PathParam("enterprise_id") String enterpriseId,
			@PathParam("user_id") String userId, String body);
}
