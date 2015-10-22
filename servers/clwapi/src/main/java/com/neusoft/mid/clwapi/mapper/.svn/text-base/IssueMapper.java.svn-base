/**
 * @(#)IssueMapper.java 2013-4-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.common.CachedCommon;
import com.neusoft.mid.clwapi.entity.issue.IssueAnswer;
import com.neusoft.mid.clwapi.entity.issue.IssueInfo;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-17 下午3:55:38
 */
public interface IssueMapper {

	/**
	 * 根据用户的问卷信息索引值获取用户的问卷信息.
	 * 
	 * @param quesIdex
	 *            问卷信息索引值.
	 */
	List<IssueInfo> getIssueInfos(@Param(value = "quesIdex") Long quesIdex)
			throws DataAccessException;

	/**
	 * 记录用户完成的问卷信息.
	 * @param userId 用户ID.
	 * @param issueAnswerList 问卷ID及答案ID.
	 * @throws DataAccessException
	 */
	void insertIssueReply(@Param(value = "userId") String userId,
			@Param(value = "issueAnswerList") List<IssueAnswer> issueAnswerList) throws DataAccessException;

	/**
	 * 更新用户完成问卷ETAG信息.
	 * 
	 * @param token
	 *            用户当前使用的token信息.该参数用于更新缓存,不参与数据库操作.
	 * @param userId 用户ID.
	 * @param quesId 问卷ID.
	 * @throws DataAccessException
	 */
	@CacheEvict(value = CachedCommon.CACHED_NAME, key = "#p0")
	void updateUserQuesEtag(@Param(value = "token") String token,
			@Param(value = "userId") String userId,
			@Param(value = "quesIndex") Long quesIndex) throws DataAccessException;

	/**
	 * 更新问卷信息用户完成情况.
	 * 
	 * @param issueAnswerList
	 *            问卷ID及答案ID.
	 * @throws DataAccessException
	 */
	int updateBathQuesInfo(
			@Param(value = "issueAnswerList") List<IssueAnswer> issueAnswerList)
			throws DataAccessException;

	/**
	 * 更新问卷答案信息用户完成情况.
	 * 
	 * @param issueAnswerList
	 *            问卷ID及答案ID.
	 * @throws DataAccessException
	 */
	int updateBathAnswerInfo(
			@Param(value = "issueAnswerList") List<IssueAnswer> issueAnswerList)
			throws DataAccessException;

}
