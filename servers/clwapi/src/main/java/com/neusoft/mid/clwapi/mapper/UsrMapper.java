/**
 * @(#)UsrMapper.java 2013-4-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.entity.usr.ConsultantInfo;
import com.neusoft.mid.clwapi.entity.usr.UserBehavior;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-7 上午11:12:02
 */
public interface UsrMapper {

	/**
	 * 添加用户行为信息记录.
	 * 
	 * @param behaviorInfo
	 *            用户行为信息.
	 */
	void insertUserBehavior(UserBehavior behaviorInfo)
			throws DataAccessException;

	/**
	 * 根据用户旧密码获取用户数
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	int getUserCountByPwd(@Param(value = "userId") String userId,
			@Param(value = "pwd") String pwd) throws DataAccessException;

	/**
	 * 根据用户旧密码获取用户数
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	void updateUserPwd(@Param(value = "userId") String userId,
			@Param(value = "oldPwd") String oldPwd,
			@Param(value = "newPwd") String newPwd) throws DataAccessException;

	/**
	 * 获取用户所属企业所在地安芯顾问信息
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	List<ConsultantInfo> getConsultantInfos(@Param(value = "enterpriseId") String enterpriseId)
			throws DataAccessException;
}
