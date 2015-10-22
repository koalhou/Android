/**
 * @(#)MsgService.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.msg;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午6:34:08
 */
@Path(value = "/")
public interface MsgService {

	/**
	 * 车辆调度处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param ttMsgInfo
	 *            车辆调度信息.
	 * @return 车辆调度结果信息.
	 */
	@POST
	@Path(value = "ttmsg/send")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response sendTtMsg(@HeaderParam("access_token") String token,
			String ttMsgInfo);

	/**
	 * 车辆拍照处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param photoInfo
	 *            车辆拍照命令信息.
	 * @return 车辆拍照结果信息.
	 */
	@POST
	@Path(value = "photograph/send")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response sendPhotoMsg(@HeaderParam("access_token") String token,
			String photoInfo);

	/**
	 * 新增调度信息模板处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param remark
	 *            消息模板内容.
	 * @return 调度信息模板信息.
	 */
	@POST
	@Path(value = "ttmsg")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	String addMsgMold(@HeaderParam("access_token") String token, String remark);

	/**
	 * 编辑调度信息模板处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param tempId
	 *            消息模板ID.
	 * @param remark
	 *            消息模板内容.
	 * @return ETag.
	 */
	@PUT
	@Path(value = "ttmsg/{ttmsg_id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	String eidtMsgMold(@HeaderParam("access_token") String token,
			@PathParam("ttmsg_id") String tempId, String remark);

	/**
	 * 删除调度信息模板处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param tempId
	 *            消息模板ID.
	 * @return ETag.
	 */
	@DELETE
	@Path(value = "ttmsg/{ttmsg_id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	String delMsgMold(@HeaderParam("access_token") String token,
			@PathParam("ttmsg_id") String tempId);

	/**
	 * 获取调度模板信息
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param eTag
	 *            更新时间
	 * @return 调度信息模板信息.
	 */
	@GET
	@Path(value = "ttmsg")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Object getMsgMold(@HeaderParam("access_token") String token,
			@HeaderParam("If-None-Match") String eTag);

	/**
	 * 推送消息已到达消息通知接口
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param msg
	 *            成功接收到的消息内容.
	 * @return 服务端处理结果信息.
	 */
	@PUT
	@Path(value = "pushmsg/received")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Object pushMsgReceived(@HeaderParam("access_token") String token,
			String msg);
}
