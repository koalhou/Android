/**
 * @(#)UsrService.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.usr;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午8:45:21
 */
@Path(value = "/")
public interface UsrService {

	/**
	 * 用户旧密码验证.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param oldPwd
	 *            用户旧密码.
	 * @return 密码验证结果.
	 */
	@GET
	@Path(value = "pwd/check/{old_pwd}")
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response checkOldPwd(@HeaderParam("access_token") String token, @PathParam("old_pwd") String oldPwd);

	/**
	 * 终端用户修改密码处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param modifyPwdInfo
	 *            密码修改信息.
	 * @return 密码修改结果.
	 */
	@PUT
	@Path(value = "pwd")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response modifyPwd(@HeaderParam("access_token") String token, String modifyPwdInfo);

	/**
	 * 用户行为信息上报接口.
	 * 
	 * @param behaviorInfo
	 *            终端用户行为信息.
	 * @return 行为结果上报结果.
	 */
	@POST
	@Path(value = "behavior")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response behavior(@HeaderParam("access_token") String token, String behaviorInfo);

	/**
	 * 获取用户所属企业所在地的安芯顾问信息.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @return 用户所属企业所在地的安芯顾问信息.
	 */
	@GET
	@Path(value = "consultant")
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response consultant(@HeaderParam("access_token") String token);
}
