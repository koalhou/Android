/**
 * @(#)IssueServiceImpl.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.issue;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.entity.issue.IssueAnswer;
import com.neusoft.mid.clwapi.entity.issue.IssueAnswerComparator;
import com.neusoft.mid.clwapi.entity.issue.IssueAnswerReq;
import com.neusoft.mid.clwapi.entity.issue.IssueInfo;
import com.neusoft.mid.clwapi.entity.issue.IssueResp;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.IssueMapper;
import com.neusoft.mid.clwapi.tools.JacksonUtils;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午9:10:03
 */
public class IssueServiceImpl implements IssueService {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	@Context
	private MessageContext context;

	@Autowired
	private IssueMapper issueMapper;

	/**
	 * 问卷信息查询接口.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param time
	 *            问卷查询时间.
	 * @return 问卷查询结果信息.
	 */
	@Override
	public Response issueInfosQuery(String token) {
		logger.info("接收到用户获取问卷信息请求");
		
		String quesETag = context.getHttpHeaders().getHeaderString("ques_ETag");
		if (null != quesETag || !"".equals(quesETag)) {

			Long quesIdex = Long.parseLong(context.getHttpHeaders().getHeaderString("ques_ETag"));
			
			logger.info("服务端存储的用户的最大问卷消息索引:" + quesIdex);
			
			IssueResp resp = new IssueResp();
			
			List<IssueInfo> resultList = issueMapper.getIssueInfos(quesIdex);
			if (null != resultList && resultList.size() != 0) {
				logger.info("检索到平台用户监控统计信息");
				resp.setResultList(resultList);
				return Response.ok(JacksonUtils.toJsonRuntimeException(resp))
						.header(HttpHeaders.CACHE_CONTROL, "no-store")
						.header("Pragma", "no-cache")
						.build();
			} else {
				logger.error("为检索到用户未答问卷信息");
				return Response.status(Response.Status.NO_CONTENT)
						.header(HttpHeaders.CACHE_CONTROL, "no-store")
						.header("Pragma", "no-cache").build();
			}
		} else {
			logger.info("未获取到用户问卷ETAG信息");
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 用户完成问卷接口.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param replyInfo
	 *            问卷结果消息.
	 * @return 完成问卷处理.
	 */
	@Override
	public Response issueReply(String token,
			String replyInfo) {
		logger.info("接收并处理用户问卷信息完成请求");
		
		if (null != replyInfo || !"".equals(replyInfo)) {

			IssueAnswerReq answer = JacksonUtils.fromJsonRuntimeException(replyInfo,
					IssueAnswerReq.class);
			
			if (null != answer && answer.getIssueAnswer().size() != 0) {
				String userId = context.getHttpHeaders().getHeaderString("usr_id");
				if (null != userId || !"".equals(userId)) {
					List<IssueAnswer> issueAnswer = answer.getIssueAnswer();
					issueMapper.insertIssueReply(userId, issueAnswer);
					issueMapper.updateUserQuesEtag(token, userId, getMaxQuesNum(issueAnswer));
					issueMapper.updateBathQuesInfo(issueAnswer);
					issueMapper.updateBathAnswerInfo(issueAnswer);
					return Response.ok()
							.header(HttpHeaders.CACHE_CONTROL, "no-store")
							.header("Pragma", "no-cache")
							.build();
				} else {
					logger.info("未获取到用户ID信息");
					throw new ApplicationException(ErrorConstant.ERROR90000,
							Response.Status.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("客户端请求信息不符合协议要求");
				throw new ApplicationException(ErrorConstant.ERROR10001,
						Response.Status.BAD_REQUEST);
			}
		} else {
			logger.info("客户端请求信息不符合协议要求");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}
	}
	
	/**
	 * 获取最大问卷ID.
	 * @param issueAnswerList
	 * @return
	 */
	private Long getMaxQuesNum(List<IssueAnswer> issueAnswerList) {
		return Collections.max(issueAnswerList, new IssueAnswerComparator())
				.getIssueId();
	}
}
