/**
 * @(#)Oauth.java 2013-3-24
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.oauth;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * 用户授权服务接口定义.
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-24 上午10:29:09
 */
@Path(value = "/")
public interface OauthService {

	/**
	 * AccessToken更新处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param refeshToken
	 *            更新令牌信息.
	 * @return 令牌更新结果信息.
	 */
	@PUT
	@Path(value = "token/refesh")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response tokenRefesh(@HeaderParam("access_token") String token,
			String refeshToken);

	/**
	 * 终端用户自动登录处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param version
	 *            终端软件版本.
	 * @return 自动登录结果消息.
	 */
	@PUT
	@Path(value = "checklogin")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response checklogin(@HeaderParam("access_token") String token,
			String version);

	/**
	 * 推送消息绑定处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param clientid
	 *            终端唯一标识.
	 * @return 推送消息绑定处理结果消息.
	 */
	@PUT
	@Path(value = "binding")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response pushMsgBinding(@HeaderParam("access_token") String token,
			String clientid);
	
	/**
	 * 用户退出登录接口
	 * 
	 * @param token 令牌信息
	 * 
	 * @return 退出登录成功信息
	 */
	@POST
	@Path(value = "logout")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	String logout(@HeaderParam("access_token") String token);
}
