/**
 * @(#)SoftwareService.java 2013-4-23
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.software;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-23 下午5:46:31
 */
@Path(value = "/")
public interface SoftwareService {

	/**
	 * 客户端软件新版本检查接口.
	 * 
	 * @param version
	 *            客户端当前软件版本号.
	 * @return 客户端软件新版本检查结果消息.
	 */
	@PUT
	@Consumes({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	@Path(value = "check")
	Response checkSoftwareVersion(String version);
}
