/**
 * @(#)PushRuleMapper.java 2013-4-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.entity.pushRule.PushRuleInfo;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-8 下午4:18:52
 */
public interface PushRuleMapper {

	/**
	 * 由企业ID获取企业推送规则
	 * 
	 * @param enId
	 *            企业ID
	 * @return 推送规则
	 */
	List<PushRuleInfo> getEnterpriseRule(String enId)
			throws DataAccessException;

	/**
	 * 批量插入用户推送规则
	 * 
	 * @param iPushRuleInfo
	 *            多条用户推送规则
	 * @throws DataAccessException
	 */
	void insertPersonalRule(List<PushRuleInfo> iPushRuleInfos)
			throws DataAccessException;

	/**
	 * 更新用户推送规则
	 * 
	 * @param iPushRuleInfo
	 *            推送规则
	 * @throws DataAccessException
	 */
	void updatePersonalRule(List<Map<String, String>> iPushRuleInfo)
			throws DataAccessException;

	/**
	 * 由用户ID获取个人推送规则
	 * 
	 * @param usrId
	 *            用户ID
	 * @return
	 */
	List<PushRuleInfo> getPushRules(@Param(value = "usrId") String usrId,
			@Param(value = "enId") String enId) throws DataAccessException;

}
