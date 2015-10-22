/**
 * @(#)HomePageService.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.news;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-3-26 下午12:34:18
 */
@Path(value = "/")
public interface NewsService {
	/**
	 * 获取新闻概要信息
	 * 
	 * @param token
	 *            用户令牌
	 * @param body
	 *            参数
	 * @return
	 */
	@POST
	@Path(value = "summary")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Object getNewsSummaryInfo(@HeaderParam("access_token") String token,
			String body);
	
	/**
	 * 登记推送消息查看记录
	 * 
	 * @param token
	 *            用户令牌
	 * @param body
	 *            参数
	 * @return
	 */
	@POST
	@Path(value = "checkin")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	String checkNewsInfo(@HeaderParam("access_token") String token,
			String body);
}
