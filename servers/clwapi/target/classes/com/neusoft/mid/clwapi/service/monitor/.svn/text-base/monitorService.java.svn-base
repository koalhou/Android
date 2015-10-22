/**
 * @(#)monitorService.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.monitor;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:suyingtao@neusoft.com">suyingtao </a>
 * @version $Revision 1.0 $ 2013-3-26 下午12:57:57
 */
@Path(value = "/")
public interface monitorService {
	/**
	 * 车辆监控处理.
	 * @param token
	 *            访问令牌信息.
	 * @return 企业所有车辆信息.
	 */
	@GET
	@Path(value = "getMonitorInfo")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	String getMonitorInfo(@HeaderParam("access_token") String token);
	
	/**
	 * 车辆实时信息处理.
	 * @param token
	 *            访问令牌信息.
	 * @param vin
	 *            车辆VIN.
	 * @return 车辆的实时信息.
	 */
	@GET
	@Path(value = "getRealTimeInfo/{vin}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response getRealTimeInfo(@HeaderParam("access_token") String token, @PathParam("vin") String vin);
}
