/**
 * @(#)CarMonitorService.java 2013-4-2
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.common;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.entity.monitor.MonitorResp;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.MonitorMapper;

/**
 * @author <a href="mailto:suyingtao@neusoft.com">suyingtao </a>
 * @version $Revision 1.0 $ 2013-4-2 上午8:43:03
 */
@Service("carMonitorService")
public class CarMonitorService {
	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);
	@Autowired
	private MonitorMapper monitorMapper;
	
	/**
	 * 根据企业ID和组织ID返回组织的全部车辆数、行驶车辆数、停驶车辆数
	 * MonitorResp对象的三个属性,
	 * @param etID 企业ID.
	 * @param orgID 组织ID
	 * @return 企业车辆数信息.
	 */
	public MonitorResp getEpCarNums(String etID, String orgID) {
		MonitorResp carNums = null;
		try {
			carNums = monitorMapper.getETCarsNum(etID, orgID);
		} catch (DataAccessException e) {
			logger.error("从数据库中获取企业车辆总数、行驶数、停驶数时出错", e);
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		return carNums;
	}
}
