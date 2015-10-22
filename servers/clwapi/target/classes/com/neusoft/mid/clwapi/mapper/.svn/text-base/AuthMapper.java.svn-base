/*******************************************************************************
 * @(#)Dao.java 2010-1-17
 *
 * Copyright 2010 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.entity.auth.CancelAuthConfInfo;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2010-1-17 下午01:44:16
 */
public interface AuthMapper {

	/**
	 * 根据配置应用ID获取登录用户名及密码信息.
	 * 
	 * @param appId
	 *            应用ID.
	 * @return CancelAuthConfInfo 登录用户名及密码信息.
	 */
	CancelAuthConfInfo getConfInfo(@Param(value = "appId") String appId)
			throws DataAccessException;

	/**
	 * 获取需要进行清空缓存的用户token信息.
	 * 
	 * @param enterpriseId
	 *            企业ID.
	 * 
	 * @param userId
	 *            用户ID.
	 * @return CancelAuthConfInfo 登录用户名及密码信息.
	 */
	List<String> getUserToken(
			@Param(value = "enterpriseId") String enterpriseId,
			@Param(value = "userId") String userId) throws DataAccessException;
	
	/**
	 * 根据企业ID清除用户使用权限信息
	 * @param enterpriseId
	 * @throws DataAccessException
	 */
	void removeUserAuthByEnt(
			@Param(value = "enterpriseId") String enterpriseId) throws DataAccessException;
	/**
	 * 根据用户ID清除用户使用权限信息
	 * @param enterpriseId
	 * @param userId
	 * @throws DataAccessException
	 */
	void removeUserAuthByUser(
			@Param(value = "enterpriseId") String enterpriseId,
			@Param(value = "userId") String userId) throws DataAccessException;

}