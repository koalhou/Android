/**
 * @(#)monitorServiceImpl.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.monitor;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.common.UserInfoKey;
import com.neusoft.mid.clwapi.entity.monitor.CarOrgInfo;
import com.neusoft.mid.clwapi.entity.monitor.MonitorResp;
import com.neusoft.mid.clwapi.entity.monitor.RealTimeInfo;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.MonitorMapper;
import com.neusoft.mid.clwapi.tools.BusinessUtil;
import com.neusoft.mid.clwapi.tools.CheckRequestParam;
import com.neusoft.mid.clwapi.tools.JacksonUtils;

/**
 * @author <a href="mailto:suyingtao@neusoft.com">suyingtao </a>
 * @version $Revision 1.0 $ 2013-3-26 下午1:05:36
 */
public class monitorServiceImpl implements monitorService {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	@Autowired
	private MonitorMapper monitorMapper;

	@Context
	private MessageContext context;

	/**
	 * 车辆监控处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @return 企业所有车辆以及组织信息.
	 */
	@Override
	public String getMonitorInfo(String token) {
		String epid = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ENTERPRISE_ID);
		String orgID = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ORGANIZATION_ID);
		logger.info("车辆监控-企业ID为:" + epid + ",组织ID为:" + orgID);

		if (CheckRequestParam.isEmpty(epid) || CheckRequestParam.isEmpty(orgID)) {
			logger.info("车辆监控-缺少企业ID或者组织ID");
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		logger.info("查询组织下的车辆统计数");
		MonitorResp result = monitorMapper.getETCarsNum(epid, orgID);

		logger.info("查询组织下车辆以及组织数据集合");
		List<CarOrgInfo> carinfos = monitorMapper.getETCarAngOrg(epid, orgID);

		result.setCarInfos(carinfos);
		return JacksonUtils.toJsonRuntimeException(result);
	}

	/**
	 * 车辆实时信息处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param vin
	 *            车辆VIN.
	 * @return 车辆的实时信息.
	 */
	@Override
	public Response getRealTimeInfo(String token, String vin) {
		vin = StringUtils.strip(vin);
		String epid = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ENTERPRISE_ID);
		logger.info("车辆监控-实时-企业ID为:" + epid + ",车辆VIN为:" + vin);

		if (CheckRequestParam.isEmpty(epid)) {
			logger.info("车辆监控-实时-缺少企业ID");
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		logger.info("查询车辆实时信息");
		RealTimeInfo car = monitorMapper.getRealInfobyVIN(epid, vin);
		if (CheckRequestParam.isEmpty(car)) {
			logger.info("车辆监控-实时-客户端请求的车辆VIN：" + vin + "不存在");
			return Response.status(Response.Status.NO_CONTENT)
					.header(HttpHeaders.CACHE_CONTROL, "no-store")
					.header("Pragma", "no-cache").build();
		}

		logger.info("对实时告警串解码，转换成告警ID数组");
		car.setAlarmIDs(BusinessUtil.decodeAlarmStr(car.getAlarmStr()));
		car.setAlarmStr(null);

		logger.info("查询乘车学生名单");
		String[] passengers = monitorMapper.getPassengersbyVIN(vin);
		if (!CheckRequestParam.isEmpty(passengers)) {
			car.setPassengers(passengers);
		}
		return Response.ok(JacksonUtils.toJsonRuntimeException(car))
				.header(HttpHeaders.CACHE_CONTROL, "no-store")
				.header("Pragma", "no-cache").build();
	}

}
