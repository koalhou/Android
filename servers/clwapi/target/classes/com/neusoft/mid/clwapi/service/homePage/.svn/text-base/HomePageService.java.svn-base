/**
 * @(#)HomePageService.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.homePage;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-3-26 下午12:34:18
 */
@Path(value = "/")
public interface HomePageService {
	/**
	 * 获取首页信息
	 * 
	 * @param token
	 *            用户令牌
	 * @param eTag
	 *            最后获取到的消息标记信息|最后获取到的告警标记信息
	 * @return 首页信息
	 */
	@POST
	@Path(value = "info")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	String getHomePageInfo(@HeaderParam("access_token") String token,
			String uptime);

	/**
	 * 获取气象信息
	 * 
	 * @param region
	 *            用户所在城市信息，例如：郑州(真实信息使用uri转义)
	 * @return 天气信息
	 */
	@GET
	@Path(value = "weather/{region}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	String getWeather(@HeaderParam("access_token") String token,
			@PathParam("region") String region);
}
