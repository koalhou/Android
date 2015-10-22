/**
 * @(#)PhotoService.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.photo;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-9 下午3:00:03
 */
@Path(value = "/")
public interface PhotoService {

	/**
	 * 批量获取照片API
	 * 
	 * @param token
	 *            用户token
	 * @param body
	 *            消息体
	 * @return
	 */
	@POST
	@Path("getlist")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Object getPhotoList(@HeaderParam("access_token") String token, String body);

	/**
	 * 获取照片标注信息
	 * 
	 * @param token
	 *            用户token
	 * @param body
	 *            消息体
	 * @return
	 */
	@POST
	@Path("getsigninfo")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Object getSignInfo(@HeaderParam("access_token") String token, String body);

	/**
	 * 更新照片的标记信息
	 * 
	 * @param token
	 *            用户token
	 * @param body
	 *            消息体
	 * @return
	 */
	@POST
	@Path("signphoto")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	String signPhoto(@HeaderParam("access_token") String token, String body);
}
