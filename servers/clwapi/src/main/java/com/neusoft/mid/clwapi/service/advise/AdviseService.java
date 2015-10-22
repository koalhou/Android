/**
 * @(#)AdviseService.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.advise;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午9:59:03
 */
@Path(value = "/")
public interface AdviseService {

	/**
	 * 终端用户问题反馈.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param adviseCont
	 *            终端用户反馈内容.
	 * @return 终端用户问题处理结果信息.
	 */
	@POST
	@Path(value = "new")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response sendTtMsg(@HeaderParam("access_token") String token, String adviseCont);

}
