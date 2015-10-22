/**
 * @(#)StatisticsService.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.statistics;

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
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午9:37:30
 */
@Path(value = "/")
public interface StatisticsService {

	/**
	 * 监管信息统计.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * 
	 * @param dateTime
	 *            统计时间.
	 * @return 监管信息统计信息.
	 */
	@GET
	@Path(value = "supervise/{date_time}")
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response getSuperviseInfo(@HeaderParam("access_token") String token,
			@PathParam("date_time") String dateTime);

	/**
	 * 单车报告处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param VIN
	 *            车辆VIN
	 * @param month
	 *            统计月份,格式：yyyymm
	 * @return 单车报告数据.
	 */
	@GET
	@Path(value = "getCarReport/{vin}/{month}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response getCarReport(@HeaderParam("access_token") String token,
			@PathParam("vin") String VIN, @PathParam("month") String month);

	/**
	 * 企业报告处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param month
	 *            统计月份,格式：yyyymm
	 * 
	 * @return 企业报告数据.
	 */
	@GET
	@Path(value = "getEntiReport/{month}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response getEntiReport(@HeaderParam("access_token") String token,
			@PathParam("month") String month);

	/**
	 * 企业报告详情处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param month
	 *            统计月份,格式：yyyymm
	 * @param rstype
	 *            资源类型,01- 超速车辆详情;02- 不良驾驶车辆详情;03- 超油车辆详情
	 * 
	 * @return 企业报告详情数据.
	 */
	@GET
	@Path(value = "getEntiReportDetail/{month}/{type}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response getEntiReportDetail(@HeaderParam("access_token") String token,
			@PathParam("month") String month, @PathParam("type") String rstype);

	/**
	 * 获取企业报告评语模板
	 * 
	 * @param token
	 *            访问令牌
	 * @param eTag
	 *            模板标记信息
	 * @return 企业评语模板详情数据
	 */
	@GET
	@Path(value = "getAllDesc")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Object getAllDesc(@HeaderParam("access_token") String token,
			@HeaderParam("If-None-Match") String eTag);
}
