/**
 * @(#)IssueService.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.issue;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.neusoft.mid.clwapi.common.HttpConstant;

/**
 * 问卷信息相关配置接口.
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午9:05:28
 */
@Path(value = "/")
public interface IssueService {

	/**
	 * 问卷信息查询接口.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param time
	 *            问卷查询时间.
	 * @return 问卷查询结果信息.
	 */
	@GET
	@Path(value = "get")
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response issueInfosQuery(@HeaderParam("access_token") String token);

	/**
	 * 用户完成问卷接口.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param replyInfo
	 *            问卷结果消息.
	 * @return 完成问卷处理.
	 */
	@POST
	@Path(value = "reply")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ HttpConstant.MEDIATYPE_JSON_UTF_8 })
	Response issueReply(@HeaderParam("access_token") String token, String replyInfo);
}
