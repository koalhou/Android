/**
 * @(#)LoginService.java 2013-4-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.oauth;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * 终端用户登录接口定义.
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-8 下午1:06:45
 */
public interface LoginService {

	/**
	 * 终端用户登录处理.
	 * 
	 * @param loginInfo
	 *            登录信息JSON串
	 * @return 服务端登录结果信息或新版终端软件信息.
	 */
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response login(String loginInfo);
}
