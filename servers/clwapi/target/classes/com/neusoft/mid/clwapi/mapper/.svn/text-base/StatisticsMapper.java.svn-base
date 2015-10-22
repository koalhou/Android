/**
 * @(#)StatisticsMapper.java 2013-4-3
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.entity.statistics.CarMonthData;
import com.neusoft.mid.clwapi.entity.statistics.EpCarDtl;
import com.neusoft.mid.clwapi.entity.statistics.EpMonthData;
import com.neusoft.mid.clwapi.entity.statistics.ReportDesc;
import com.neusoft.mid.clwapi.entity.statistics.SuperviseInfo;
import com.neusoft.mid.clwapi.entity.statistics.SuperviseProcInfo;

/**
 * @author <a href="mailto:suyingtao@neusoft.com">suyingtao </a>
 * @version $Revision 1.0 $ 2013-4-3 上午10:39:57
 */
public interface StatisticsMapper {

	/**
	 * 返回month月的车辆月报表数据.
	 * 
	 * @param vin
	 *            车辆VIN
	 * @param month
	 *            统计月份
	 * @return 车辆月报表数据
	 */
	List<CarMonthData> getCarMonthData(@Param(value = "month") String month,
			@Param(value = "vin") String vin) throws DataAccessException;

	/**
	 * 返回month月的企业月报表数据.
	 * 
	 * @param epid
	 *            企业ID
	 * @param month
	 *            统计月份
	 * @return 企业月报表数据
	 */
	List<EpMonthData> getEpMonthData(@Param(value = "month") String month,
			@Param(value = "epid") String epid) throws DataAccessException;

	/**
	 * 返回指定月的超速占比\不良驾驶车辆占比\超油车辆占比的车辆业务数据.
	 * 
	 * @param epid
	 *            企业ID
	 * @param month
	 *            统计月份
	 * @param rsType
	 *            资源类型
	 * @param orgID           
	 *            组织ID 
	 * @return
	 */
	List<EpCarDtl> getCarMonthDtl(@Param(value = "month") String month,
			@Param(value = "epid") String epid,
			@Param(value = "rsType") String rsType,
			@Param(value = "orgID") String orgID) throws DataAccessException;

	/**
	 * 从平台监控员行为信息统计中间表中查询某企业某天的统计信息
	 * 
	 * @return 平台监控员行为统计信息.
	 * @throws DataAccessException
	 */
	List<SuperviseInfo> getPassSuperviseInfo(
			@Param(value = "entId") String entId,
			@Param(value = "dateTime") String dateTime)
			throws DataAccessException;

	/**
	 * 执行计算监控员行为信息统计存储过程
	 * 
	 * @return 平台监控员行为统计信息.
	 * @throws DataAccessException
	 */
	void excuteProcCreatePassSuperviseInfo(SuperviseProcInfo info)
			throws DataAccessException;

	/**
	 * 获取企业评语模板
	 * 
	 * @param enId
	 *            企业ID
	 * @return
	 * @throws DataAccessException
	 */
	List<ReportDesc> getReportDesc(String enId) throws DataAccessException;
	
	/**
	 * 返回车辆最近一个有效月的月报表数据.
	 * 
	 * @param vin
	 *            车辆VIN
	 * @return 车辆月报表数据
	 */
	List<CarMonthData> getValidCarReport(@Param(value = "vin") String vin) throws DataAccessException;
}
