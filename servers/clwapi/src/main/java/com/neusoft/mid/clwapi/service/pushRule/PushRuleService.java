/**
 * @(#)PushRuleService.java 2013-4-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.pushRule;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-8 上午9:28:13
 */
@Path(value = "/")
public interface PushRuleService {

	/**
	 * 获取个人推送规则服务
	 * 
	 * @param token
	 *            用户令牌
	 * @param eTag
	 *            个人推送规则标记
	 * @return 最新个人推送规则标记
	 */
	@GET
	@Path(value = "rule")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Object getRule(@HeaderParam("access_token") String token,
			@HeaderParam("If-None-Match") String eTag);

	/**
	 * 设置个人推送规则服务
	 * 
	 * @param token
	 *            用户令牌
	 * @param content
	 *            个人推送规则消息体
	 * @return 最新个人推送规则标记
	 */
	@PUT
	@Path(value = "rule")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	String setRule(@HeaderParam("access_token") String token, String content);

}
