/**
 * @(#)StatisticsMapper.java 2013-4-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.entity.tacks.TackDetailInfo;
import com.neusoft.mid.clwapi.entity.tacks.TackListInfo;
import com.neusoft.mid.clwapi.entity.tacks.TackResp;

/**
 * @author <a href="mailto:suyingtao@neusoft.com">suyingtao </a>
 * @version $Revision 1.0 $ 2013-4-8 上午10:39:57
 */
public interface TacksMapper {

	/**
	 * 返回vin在date的行车记录集合.
	 * @param vin 车辆VIN
	 * @param date 查询日期
	 * @return 行车记录
	 */
	List<TackListInfo> getCarRunRecs(@Param(value="datetime") String datetime, @Param(value="vin") String vin)throws DataAccessException;

	/**
	 * 返回vin在时间段的行车轨迹点集合，包含告警点.
	 * @param vin 车辆VIN
	 * @param sTime 开始时间
	 * @param eTime 结束时间
	 * @return 行车轨迹点
	 */
	List<TackDetailInfo> getTackPonits(@Param(value="sTime") String sTime, @Param(value="eTime") String eTime, @Param(value="vin") String vin)throws DataAccessException;
	
	/**
	 * 返回vin和time对应的行车记录集合.
	 * @param vin 车辆VIN
	 * @param datetime 查询日期
	 * @return 行车记录
	 */
	List<TackResp> getRunRecsByTime(@Param(value="vin") String vin,@Param(value="datetime") String datetime)throws DataAccessException;
}
