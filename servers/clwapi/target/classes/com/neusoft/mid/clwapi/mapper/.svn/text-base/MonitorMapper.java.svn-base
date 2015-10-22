/**
 * @(#)MonitorMapper.java 2013-3-28
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.entity.monitor.CarOrgInfo;
import com.neusoft.mid.clwapi.entity.monitor.MonitorResp;
import com.neusoft.mid.clwapi.entity.monitor.RealTimeInfo;

/**
 * @author <a href="mailto:suyingtao@neusoft.com">suyingtao </a>
 * @version $Revision 1.0 $ 2013-3-28 下午1:37:17
 */
public interface MonitorMapper {
	/**
	 * 根据组织ID返回组织下所有车辆实时信息和车辆组织结构.
	 * @param etID 企业ID
	 * @param orgID 组织ID
	 * @return 车辆实时信息和车辆组织结构
	 */
	List <CarOrgInfo> getETCarAngOrg(@Param(value="etID") String etID, @Param(value="orgID") String orgID)throws DataAccessException;
	
	/**
	 * 根据组织ID返回组织车辆总数、行驶数、停驶数.
	 * @param etID 企业ID
	 * @param orgID 组织ID
	 * @return 车辆实时信息和车辆组织结构
	 */
	MonitorResp getETCarsNum(@Param(value="etID") String etID, @Param(value="orgID") String orgID)throws DataAccessException;
	
	/**
	 * 根据车辆VIN返回车辆的实时信息.
	 * @param vin 车辆VIN
	 * @param etID 企业ID
	 * @return 车辆实时信息
	 */
	RealTimeInfo getRealInfobyVIN(@Param(value="etID") String etID, @Param(value="vin") String vin)throws DataAccessException;
	
	/**
	 * 根据车辆VIN返回车上的学生列表.
	 * @param vin 车辆VIN
	 * @return 学生姓名数组
	 */
	String[] getPassengersbyVIN(String vin)throws DataAccessException;		
}
