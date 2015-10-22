package com.neusoft.mid.clwapi.service.video;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.neusoft.mid.clwapi.common.HttpConstant;

public interface VideoService {
	/**
	 * 
	  * 函数介绍：获取查看视频的设置
	  * 参数：
	  * 返回值：
	 */
	@GET
	@Path(value = "watch/rule")
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	public Response getWatchRule();
	
	/**
	 * 
	  * 函数介绍：设置查看视频的规则
	  * 参数：
	  * 返回值：
	 */
	@PUT
	@Path(value = "watch/rule")
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	public Response setWatchRule(String req);
	
	/**
	 * 
	  * 函数介绍：查询可看视频的车辆列表
	  * 参数： HI-海康  DA-大华
	  * 返回值：
	 */
	@POST
	@Path(value = "list")
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	public Response list(String req);
	
	/**
	 * 
	  * 函数介绍：查询指定车辆的视频状态
	  * 参数：车架号
	  * 返回值：
	 */
	@GET
	@Path(value = "status/{vin}")
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	public Response status(@PathParam("vin") String vin);
}
