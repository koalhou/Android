/**
 * @(#)AlarmMapper.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.entity.alarm.AlarmInfo;
import com.neusoft.mid.clwapi.entity.alarm.AlarmRequ;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-9 上午10:30:47
 */
public interface AlarmMapper {
	/**
	 * 获取告警列表
	 * 
	 * @param iAlarmRequ
	 *            参数
	 * @return 告警列表
	 * @throws DataAccessException
	 *             数据库异常
	 */
	List<AlarmInfo> getAlarmList(AlarmRequ iAlarmRequ)
			throws DataAccessException;

	/**
	 * 处理告警信息
	 * 
	 * @param alarmId
	 *            告警信息ID
	 * @param body
	 *            处理意见
	 * @throws DataAccessException
	 *             数据库异常
	 */
	void dealAlarm(@Param(value = "alarmId") String alarmId,
			@Param(value = "body") String body,
			@Param(value = "usrId") String usrId,
			@Param(value = "alarmTime") String alarmTime)
			throws DataAccessException;

	/**
	 * 获取告警处理信息
	 * 
	 * @param iAlarmRequ
	 *            请求参数
	 * @return 告警处理信息
	 */
	AlarmInfo getAlarmDealInfo(AlarmRequ iAlarmRequ) throws DataAccessException;

}
