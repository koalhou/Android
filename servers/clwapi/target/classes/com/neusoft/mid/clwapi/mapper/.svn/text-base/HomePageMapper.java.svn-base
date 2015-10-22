/**
 * @(#)HomePageMapper.java 2013-4-2
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-2 上午10:10:00
 */
public interface HomePageMapper {
	/**
	 * 获取大于最新时间且最近一天的告警数
	 * 
	 * @param time
	 *            最新时间
	 * @return
	 * @throws DataAccessException
	 */
	String getAlarmNum(@Param(value = "timea") String timea,
			@Param(value = "timeb") String timeb,
			@Param(value = "enID") String enID,
			@Param(value = "parName1") String parName1,
			@Param(value = "parName2") String parName2,
			@Param(value = "organizationId") String organization_id)
			throws DataAccessException;

	/**
	 * 获取大于最新时间且有效的推送消息数
	 * 
	 * @param time
	 *            最新时间
	 * @return
	 * @throws DataAccessException
	 */
	String getNewsNum(@Param(value = "time") String time)
			throws DataAccessException;

	/**
	 * 获取前推24小时且为手机终端指定下发的照片列表
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	List<String> getPhotoList(@Param(value = "userId") String userId, 
			@Param(value = "organizationId") String organization_id) 
			throws DataAccessException;
	
	/**
	 * 获取企业报告的最新有效月份 
	 *
	 * @param enID
	 *            企业ID
	 * @return
	 * @throws DataAccessException
	 */
	List<String> getEpReportMonth(@Param(value = "enID") String enID) 
			throws DataAccessException;	
	
	/**
	 * 获取企业报告的数量 
	 *
	 * @param enID
	 *            企业ID
	 * @return
	 * @throws DataAccessException
	 */
	String getEpReportNum(@Param(value = "enID") String enID)
			throws DataAccessException;
}
