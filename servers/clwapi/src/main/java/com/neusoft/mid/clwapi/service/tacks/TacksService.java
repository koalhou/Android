/**
 * @(#)TacksService.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.tacks;

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
 * @version $Revision 1.0 $ 2013-3-25 下午7:43:48
 */
@Path(value = "/")
public interface TacksService {

	/**
	 * 车辆某天的行车记录列表信息处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * 
	 * @param vin
	 *            车辆vin信息.
	 * 
	 * @param date
	 *            时间,格式yyyymmdd.
	 * @return 车辆某天的行车记录列表信息.
	 */
	@GET
	@Path(value = "driverecord/{vin}/{date}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response getVinTacksList(@HeaderParam("access_token") String token, @PathParam("vin") String vin,
			@PathParam("date") String date);

	/**
	 * 车辆指定时段行车记录详细信息处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * 
	 * @param vin
	 *            车辆vin信息.
	 * 
	 * @param startTime
	 *            时间,格式yyyymmddhh24miss.
	 * 
	 * @param endTime
	 *            时间,格式yyyymmddhh24miss.
	 * @return 行车记录详细信息.
	 */
	@GET
	@Path(value = "driverecord/{vin}/{start_time}/{end_time}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response getVinTacksDetail(@HeaderParam("access_token") String token, @PathParam("vin") String vin,
			@PathParam("start_time") String startTime,
			@PathParam("end_time") String endTime);
	
	/**
	 * 根据告警信息查询行车记录.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * 
	 * @param vin
	 *            车辆vin信息.
	 * 
	 * @param alarmTime
	 *            告警时间,格式yyyymmddhh24miss.
	 * 
	 * @param alarmID
	 *            告警ID.
	 * @return 行车记录.
	 */
	@GET
	@Path(value = "getTackByAlarm/{alarm_id}/{vin}/{alarm_time}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response getVinTackByAlarm(@HeaderParam("access_token") String token, @PathParam("alarm_id") String alarmID,
			@PathParam("vin") String vin,
			@PathParam("alarm_time") String alarmTime);
}
