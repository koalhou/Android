/**
 * @(#)AlarmService.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.alarm;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-9 上午9:33:16
 */
@Path(value = "/")
public interface AlarmService {
	/**
	 * 批量获取告警信息
	 * 
	 * @param token
	 *            用户token
	 * @param body
	 *            消息体
	 * @return
	 */
	@POST
	@Path(value = "getlist")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Object getAlarmList(@HeaderParam("access_token") String token, String body);

	/**
	 * 处理告警信息API
	 * 
	 * @param token
	 *            用户token
	 * @param warnId
	 *            告警ID
	 * @param body
	 *            处理信息
	 */
	@POST
	@Path(value = "deal/{warn_id}/{warn_time}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	String dealAlarm(@HeaderParam("access_token") String token,
			@PathParam("warn_id") String warnId,
			@PathParam("warn_time") String alarmTime, String body);

	/**
	 * 获取告警处理信息API
	 * 
	 * @param token
	 *            用户令牌
	 * @param body
	 *            请求体
	 * @return
	 * 			处理信息
	 */
	@POST
	@Path(value = "getdealinfo")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Object getAlarmInfo(@HeaderParam("access_token") String token, String body);
}
