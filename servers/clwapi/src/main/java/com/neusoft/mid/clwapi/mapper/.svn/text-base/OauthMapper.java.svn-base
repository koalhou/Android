/*******************************************************************************
 * @(#)Dao.java 2010-1-17
 *
 * Copyright 2010 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.common.CachedCommon;
import com.neusoft.mid.clwapi.entity.oauth.MobileUsrAllInfo;
import com.neusoft.mid.clwapi.entity.oauth.SoftwareVersion;
import com.neusoft.mid.clwapi.entity.oauth.UsrInfo;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2010-1-17 下午01:44:16
 */
public interface OauthMapper {

	/**
	 * 根据用户ID信息获取终端用户全部管理信息.
	 * 
	 * @param token
	 *            用户访问服务令牌信息.
	 * @return MobileUsrAllInfo 终端用户管理全部
	 */
	@Cacheable(value = CachedCommon.CACHED_NAME, key = "#p0")
	MobileUsrAllInfo getMblUsrInfoByToken(@Param(value = "token") String token)
			throws DataAccessException;

	/**
	 * 根据终端用户登录信息获取校车平台用户基本信息.
	 * 
	 * @param loginName
	 *            登录用户名.
	 * 
	 * @param loginPwd
	 *            登录用户密码.
	 * 
	 * @param enCode
	 *            机构编码.
	 * @return UsrInfo 校车平台用户基本信息.
	 */
	List<UsrInfo> getXcpUsrInfo(@Param(value = "loginName") String loginName,
			@Param(value = "loginPwd") String loginPwd,
			@Param(value = "enCode") String enCode) throws DataAccessException;

	/**
	 * 根据用户ID获取校车平台用户基本信息.
	 * 
	 * @param userId
	 *            用户ID
	 * @return 校车平台用户基本信息.
	 * @throws DataAccessException
	 */
	UsrInfo getXcpUsrInfoByUsrId(@Param(value = "userId") String userId)
			throws DataAccessException;

	/**
	 * 添加终端用户授权信息.
	 * 
	 * @param userId
	 *            用户ID.
	 * 
	 * @param enId
	 *            企业ID.
	 * 
	 * @param accessToken
	 *            token信息.
	 * 
	 * @param refreshToken
	 *            token更新令牌信息.
	 * 
	 * @param expireTime
	 *            token有效时长.
	 */
	void insertMblUserInfo(@Param(value = "userId") String userId,
			@Param(value = "enterpriseId") String enterpriseId,
			@Param(value = "organizationId") String organizationId,
			@Param(value = "accessToken") String accessToken,
			@Param(value = "refreshToken") String refreshToken,
			@Param(value = "expireTime") Long expireTime)
			throws DataAccessException;

	/**
	 * 原有终端用户重新登录.
	 * 
	 * @param enterpriseId
	 *            企业ID.
	 * 
	 * @param token
	 *            token信息.
	 * 
	 * @param refreshToken
	 *            token更新令牌信息.
	 * 
	 * @param expireTime
	 *            token有效时长.
	 * 
	 * @param userId
	 *            用户ID.
	 */
	@CacheEvict(value = CachedCommon.CACHED_NAME, key = "#p0")
	void userLoginOnceMore(String lastToken,
			@Param(value = "token") String token,
			@Param(value = "enterpriseId") String enterpriseId,
			@Param(value = "organizationId") String organizationId,
			@Param(value = "refreshToken") String refreshToken,
			@Param(value = "expireTime") Long expireTime,
			@Param(value = "userId") String userId) throws DataAccessException;
	
	/**
	 * added by houjh 2014-02-25
	 * @param usrId
	 * 
	 * 若非IOS系统登录，则清除IOS推送标识
	 */
	@CacheEvict(value = CachedCommon.CACHED_NAME, key = "#p0")
	void updateIosFlag(String usrId);
	/**
	 * 更新用户token过期时间.
	 * 
	 * @param token
	 *            用户当前使用的token信息.该参数用于更新缓存,不参与数据库操作.
	 * 
	 * @param expireTime
	 *            token有效时长.
	 * 
	 * @param userId
	 *            用户ID.
	 */
	@CacheEvict(value = CachedCommon.CACHED_NAME, key = "#p0")
	void refreshTokenExpireTime(@Param(value = "token") String token,
			@Param(value = "expireTime") Long expireTime,
			@Param(value = "userId") String userId,
			@Param(value = "organizationId") String organizationId) throws DataAccessException;
	
	/**
	 * 更新使用token的其他用户.
	 * 
	 * @param token
	 *            用户当前使用的token信息.该参数用于更新缓存,不参与数据库操作.
	 */
	@CacheEvict(value = CachedCommon.CACHED_NAME, key = "#p0")
	void clearIosTokenByToken(
			@Param(value = "ios_token") String ios_token) throws DataAccessException;
	
	/**
	 * 更新用户token过期时间.
	 * 
	 * @param token
	 *            用户当前使用的token信息.该参数用于更新缓存,不参与数据库操作.
	 * 
	 * @param expireTime
	 *            token有效时长.
	 * 
	 * @param userId
	 *            用户ID.
	 */
	@CacheEvict(value = CachedCommon.CACHED_NAME, key = "#p0")
	void refreshIosToken(
			@Param(value = "userId") String userId,
			@Param(value = "ios_token") String ios_token) throws DataAccessException;

	/**
	 * 更新用户个人消息模板标记信息.
	 * 
	 * @param token
	 *            用户当前使用的token信息.该参数用于更新缓存,不参与数据库操作.
	 * 
	 * @param templateEtag
	 *            个人消息模板标记信息.
	 * 
	 * @param userId
	 *            用户ID.
	 */
	@CacheEvict(value = CachedCommon.CACHED_NAME, key = "#p0")
	void updateUserTemplateEtag(@Param(value = "token") String token,
			@Param(value = "templateEtag") String templateEtag,
			@Param(value = "userId") String userId) throws DataAccessException;

	/**
	 * 更新用户个人推送规则标记信息.
	 * 
	 * @param token
	 *            用户当前使用的token信息.该参数用于更新缓存,不参与数据库操作.
	 * 
	 * @param ruleEtag
	 *            个人推送规则标记信息.
	 * 
	 * @param userId
	 *            用户ID.
	 */
	@CacheEvict(value = CachedCommon.CACHED_NAME, key = "#p0")
	void updateUserRuleEtag(@Param(value = "token") String token,
			@Param(value = "ruleEtag") String ruleEtag,
			@Param(value = "userId") String userId) throws DataAccessException;

	/**
	 * 更新用户企业报告评语模板标记信息.
	 * 
	 * @param token
	 *            用户当前使用的token信息.该参数用于更新缓存,不参与数据库操作.
	 * 
	 * @param reportETag
	 *            企业报告评语模板标记信息.
	 * 
	 * @param userId
	 *            用户ID.
	 */
	@CacheEvict(value = CachedCommon.CACHED_NAME, key = "#p0")
	void updateUserReportETag(@Param(value = "token") String token,
			@Param(value = "reportETag") String reportETag,
			@Param(value = "userId") String userId) throws DataAccessException;

	/**
	 * 获取终端用户权限列表信息.
	 * 
	 * @param object
	 *            终端用户信息.
	 */
	List<String> getUserLimit(@Param(value = "userId") String userId)
			throws DataAccessException;

	/**
	 * 获取当前最大客户端软件版本信息.
	 * 
	 * @return 客户端软件版本信息.
	 */
	List<SoftwareVersion> getMaxVersion(@Param(value = "version") String version)
			throws DataAccessException;

	/**
	 * added by houjh 兼容IOS版本强制升级
	 * 
	 * 获取当前最大客户端软件版本信息.
	 * 
	 * @return 客户端软件版本信息.
	 */
	List<SoftwareVersion> getMaxVersionIos(@Param(value = "version") String version)
			throws DataAccessException;

	/**
	 * 更新用户信息表状态为登出状态.
	 * 
	 * @param token
	 *            用户当前使用的token信息.该参数用于更新缓存,不参与数据库操作.
	 * 
	 * @param token
	 *            用户访问服务令牌信息.
	 */
	@CacheEvict(value = CachedCommon.CACHED_NAME, key = "#p0")
	void logoutUsrOauth(@Param(value = "token") String token, @Param(value = "userId") String userId) throws DataAccessException;

	/**
	 * 根据用户令牌信息获取有效终端绑定用户信息.
	 * 
	 * @param token
	 *            用户访问服务令牌信息.
	 * @return MobileUsrAllInfo 终端用户管理全部
	 */
	MobileUsrAllInfo getValidUsrBindInfo(@Param(value = "token") String token)
			throws DataAccessException;

	/**
	 * 净新增用户时-更新接收问卷预定用户数+1.
	 */
	void updateQuesUserNum() throws DataAccessException;

	/**
	 * 净新增用户时-更新推送预定用户数+1.
	 */
	void updatePushMsgNum() throws DataAccessException;

	/**
	 * 查询所属企业是否有权使用终端软件(通过机构编码).
	 * 
	 * @param enCode
	 *            企业编码.
	 * @throws DataAccessException
	 */
	String getEnAllowByEnCode(@Param(value = "enCode") String enCode)
			throws DataAccessException;

	/**
	 * 查询所属企业是否有权使用终端软件（通过企业ID）.
	 * 
	 * @param enCode
	 *            企业编码.
	 * @throws DataAccessException
	 */
	String getEnAllowByEnId(@Param(value = "enterpriseId") String enterpriseId)
			throws DataAccessException;
	/**
	 * 退出登录的时候将推送值修改为默认（不推送标识）
	 * 
	 * 
	 */
	void clearIosToken(@Param(value = "userId") String userId)throws DataAccessException;

}