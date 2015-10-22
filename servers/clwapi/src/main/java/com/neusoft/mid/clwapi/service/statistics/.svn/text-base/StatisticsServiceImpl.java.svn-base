/**
 * @(#)StatisticsServiceImpl.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
import com.neusoft.mid.clwapi.common.HttpConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.common.UserInfoKey;
import com.neusoft.mid.clwapi.entity.statistics.BadBehaviorPerp;
import com.neusoft.mid.clwapi.entity.statistics.CarMonthData;
import com.neusoft.mid.clwapi.entity.statistics.CarStatReport;
import com.neusoft.mid.clwapi.entity.statistics.EpCarDtl;
import com.neusoft.mid.clwapi.entity.statistics.EpDtlResp;
import com.neusoft.mid.clwapi.entity.statistics.EpMonthData;
import com.neusoft.mid.clwapi.entity.statistics.EpStatReport;
import com.neusoft.mid.clwapi.entity.statistics.MoldStatInfo;
import com.neusoft.mid.clwapi.entity.statistics.MonthStatInfo;
import com.neusoft.mid.clwapi.entity.statistics.ReportDesc;
import com.neusoft.mid.clwapi.entity.statistics.ReportDescResp;
import com.neusoft.mid.clwapi.entity.statistics.SuperviseInfo;
import com.neusoft.mid.clwapi.entity.statistics.SuperviseProcInfo;
import com.neusoft.mid.clwapi.entity.statistics.SuperviseResp;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.OauthMapper;
import com.neusoft.mid.clwapi.mapper.StatisticsMapper;
import com.neusoft.mid.clwapi.tools.BeanUtil;
import com.neusoft.mid.clwapi.tools.CheckRequestParam;
import com.neusoft.mid.clwapi.tools.JacksonUtils;
import com.neusoft.mid.clwapi.tools.TimeUtil;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午9:39:25
 */
public class StatisticsServiceImpl implements StatisticsService {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	@Autowired
	private StatisticsMapper stMapper;

	@Context
	private MessageContext context;

	@Autowired
	private OauthMapper oauthMapper;

	/**
	 * 监管信息统计.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * 
	 * @param dateTime
	 *            统计时间.
	 * @return 监管信息统计信息.
	 */
	@Override
	public Response getSuperviseInfo(String token, String dateTime) {
		logger.info("接收到监管信息统计请求,请求时间为:" + dateTime);

		SuperviseResp resp = new SuperviseResp();

		if ((null == dateTime) || ("".equals(dateTime))
				|| (dateTime.length() != 8)) {
			logger.info("客户端请求信息不符合协议要求");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		} else {
			try {
				TimeUtil.parseStringToDate(dateTime, "yyyyMMdd");
			} catch (ParseException e) {
				logger.info("将" + dateTime + "转换为具体时间时出错", e);
				logger.info("客户端请求信息时间请求条件不符合规范");
				throw new ApplicationException(ErrorConstant.ERROR10001,
						Response.Status.BAD_REQUEST);
			}
			String sysdate = new SimpleDateFormat("yyyyMMdd").format(Calendar
					.getInstance().getTime());
			logger.info("系统当前时间为:" + sysdate);
			int compareResult = dateTime.compareTo(sysdate);
			String entId = context.getHttpHeaders().getHeaderString(
					UserInfoKey.ENTERPRISE_ID);
			if (compareResult < 0) {
				logger.info("统计时间为过去时间,应答信息将全部从中间数据表中进行统计");
				List<SuperviseInfo> result = stMapper.getPassSuperviseInfo(
						entId, dateTime);
				if (null != result && result.size() != 0) {
					logger.info("检索到平台用户监控统计信息");
					resp.setResultList(result);
				} else {
					logger.error("未检索到企业ID:" + entId + ",时间为:" + dateTime
							+ "平台用户监控行为统计信息");
					return Response.status(Response.Status.NO_CONTENT)
							.header(HttpHeaders.CACHE_CONTROL, "no-store")
							.header("Pragma", "no-cache").build();
				}
			} else if (compareResult == 0) {
				logger.info("统计时间与当前时间为同一天");
				SuperviseProcInfo param = new SuperviseProcInfo();
				param.setDateTime(sysdate);
				stMapper.excuteProcCreatePassSuperviseInfo(param);
				if (1 == param.getResult()) {
					logger.info("存储过程STAFF_BEHV_DAY_STAT_PROC执行成功");
					List<SuperviseInfo> result = stMapper.getPassSuperviseInfo(
							entId, sysdate);
					if (null != result && result.size() != 0) {
						logger.info("检索到平台用户监控统计信息");
						resp.setResultList(result);
					} else {
						logger.error("未检索到企业ID:" + entId + ",时间为:" + sysdate
								+ "平台用户监控行为统计信息");
						return Response.status(Response.Status.NO_CONTENT)
								.header(HttpHeaders.CACHE_CONTROL, "no-store")
								.header("Pragma", "no-cache").build();
					}
				} else {
					logger.info("存储过程STAFF_BEHV_DAY_STAT_PROC执行失败");
					return Response.status(Response.Status.NO_CONTENT)
							.header(HttpHeaders.CACHE_CONTROL, "no-store")
							.header("Pragma", "no-cache").build();
				}

			} else {
				logger.info("统计时间大于当前时间,无相应数据返回");
				return Response.status(Response.Status.NO_CONTENT)
						.header(HttpHeaders.CACHE_CONTROL, "no-store")
						.header("Pragma", "no-cache").build();
			}
		}

		return Response.ok(JacksonUtils.toJsonRuntimeException(resp))
				.header(HttpHeaders.CACHE_CONTROL, "no-store")
				.header("Pragma", "no-cache").build();
	}

	/**
	 * 单车报告处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param VIN
	 *            车辆VIN
	 * @param month
	 *            统计月份,格式：yyyymm 或者 最近有效月LATEST_MONTH
	 * @return 单车报告数据.
	 */
	@Override
	public Response getCarReport(String token, String VIN, String month) {
		VIN = StringUtils.strip(VIN);
		month = StringUtils.strip(month);
		String epid = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ENTERPRISE_ID);
		logger.info("单车报告-企业ID为:" + epid + ",车辆VIN为:" + VIN + ",统计月份为：" + month);
		
		if (CheckRequestParam.isEmpty(epid)) {
			logger.info("单车报告-实时-缺少企业ID");
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		
		Date reportMonth;
		List<CarMonthData> infos;
		//增加最近有效月分支处理
		if(HttpConstant.LATEST_MONTH.equals(month)){
			infos = stMapper.getValidCarReport(VIN);
			if (CheckRequestParam.isEmpty(infos)) {
				logger.info("单车报告-客户端请求车辆VIN:" + VIN + "的单车报告数据不存在");
				return Response.status(Response.Status.NO_CONTENT)
						.header(HttpHeaders.CACHE_CONTROL, "no-store")
						.header("Pragma", "no-cache").build();
			}
			
			try {
				reportMonth = TimeUtil.parseStringToDate(infos.get(0).getDateTime(),
						HttpConstant.MONTH_FORMAT_1);
			} catch (ParseException e) {
				logger.error("单车报告-统计月份按照yyyyMM格式出错" + e.getMessage());
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}			
		}else{
		//指定月份分支处理
			try {
				reportMonth = TimeUtil.parseStringToDate(month,
						HttpConstant.MONTH_FORMAT);
			} catch (ParseException e) {
				logger.error("单车报告-统计月份按照yyyyMM格式出错" + e.getMessage());
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}

			if (reportMonth.after(TimeUtil.getLastMonthD())) {
				logger.info("单车报告-平台未生成" + month + "统计数据");
				throw new ApplicationException(ErrorConstant.ERROR10004,
						Response.Status.BAD_REQUEST);
			}
			
			infos = stMapper.getCarMonthData(month, VIN);
			if (CheckRequestParam.isEmpty(infos)) {
				logger.info("单车报告-客户端请求车辆VIN:" + VIN + "的单车报告数据不存在");
				return Response.status(Response.Status.NO_CONTENT)
						.header(HttpHeaders.CACHE_CONTROL, "no-store")
						.header("Pragma", "no-cache").build();
			}			
		}

		CarStatReport statInfo = convertCarResp(infos, reportMonth);
		if (CheckRequestParam.isEmpty(statInfo)) {
			logger.info("单车报告-客户端请求车辆VIN:" + VIN + "的单车报告数据不存在");
			return Response.status(Response.Status.NO_CONTENT)
					.header(HttpHeaders.CACHE_CONTROL, "no-store")
					.header("Pragma", "no-cache").build();
		}
		return Response.ok(JacksonUtils.toJsonRuntimeException(statInfo))
				.header(HttpHeaders.CACHE_CONTROL, "no-store")
				.header("Pragma", "no-cache").build();
	}

	/**
	 * 企业报告处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param month
	 *            统计月份,格式：yyyymm
	 * @return 企业报告数据.
	 */
	@Override
	public Response getEntiReport(String token, String month) {
		month = StringUtils.strip(month);
		String epid = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ENTERPRISE_ID);
		logger.info("企业报告-企业ID为:" + epid + ",统计月份为：" + month);

		Date reportMonth;
		try {
			reportMonth = TimeUtil.parseStringToDate(month,
					HttpConstant.MONTH_FORMAT);
		} catch (ParseException e) {
			logger.error("企业报告-统计月份按照yyyyMM格式出错" + e.getMessage());
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		if (reportMonth.after(TimeUtil.getLastMonthD())) {
			logger.info("企业报告-平台未生成" + month + "统计数据");
			throw new ApplicationException(ErrorConstant.ERROR10004,
					Response.Status.BAD_REQUEST);
		}

		if (CheckRequestParam.isEmpty(epid)) {
			logger.info("企业报告-实时-缺少企业ID");
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		List<EpMonthData> infos = stMapper.getEpMonthData(month, epid);
		if (CheckRequestParam.isEmpty(infos)) {
			logger.info("企业报告-客户端请求企业ID:" + epid + "的企业报告数据不存在");
			return Response.status(Response.Status.NO_CONTENT)
					.header(HttpHeaders.CACHE_CONTROL, "no-store")
					.header("Pragma", "no-cache").build();
		}

		EpStatReport resp = convertEpResp(infos, reportMonth);
		if (CheckRequestParam.isEmpty(resp)) {
			logger.info("企业报告-客户端请求企业ID:" + epid + "的企业报告数据不存在");
			return Response.status(Response.Status.NO_CONTENT)
					.header(HttpHeaders.CACHE_CONTROL, "no-store")
					.header("Pragma", "no-cache").build();
		}
		return Response.ok(JacksonUtils.toJsonRuntimeException(resp))
				.header(HttpHeaders.CACHE_CONTROL, "no-store")
				.header("Pragma", "no-cache").build();
	}

	/**
	 * 单车报告数据转换处理.
	 * 
	 * @param infos
	 *            车辆5个月的统计数据以及行业数据集合.
	 * @param reportMonth
	 *            统计月份,格式：yyyymm
	 * 
	 * @return 单车报告数据结构.
	 */
	private CarStatReport convertCarResp(List<CarMonthData> infos,
			Date reportMonth) {
		// 车辆月报表对象
		CarStatReport statInfo = new CarStatReport();
		// 超速里程、不良驾驶时长、油耗三种对象
		MoldStatInfo speedMold = new MoldStatInfo();
		MoldStatInfo badMold = new MoldStatInfo();
		MoldStatInfo oilMold = new MoldStatInfo();
		// 前N个月的超速里程、不良驾驶时长、油耗数据
		List<MonthStatInfo> speedMonths = new ArrayList<MonthStatInfo>();
		List<MonthStatInfo> badMonths = new ArrayList<MonthStatInfo>();
		List<MonthStatInfo> oilMonths = new ArrayList<MonthStatInfo>();
		String monthStr = "";
		String monthReport = TimeUtil
				.formatDateToString(reportMonth, "yyyy-MM");
		boolean reportFlag = false;
		// 遍历前N个月的月报表数据
		for (CarMonthData obj : infos) {
			monthStr = obj.getDateTime();
			// 当前统计月的概要数据设置
			if (monthReport.equals(monthStr)) {
				reportFlag = true;
				// 概要数据
				statInfo.setCarLn(obj.getCarLn());
				statInfo.setDriveName(obj.getDriverName());
				statInfo.setDateTime(monthStr);
				statInfo.setLimitNum(obj.getLimitNum());
				statInfo.setTotalMileage(obj.getTotalMileage());
				statInfo.setTotalOil(obj.getTotalOil());
				statInfo.setValidFlag(obj.getValidFlag());				
				if(!HttpConstant.ZERO.equals(obj.getValidFlag())){
					break;
				}
				statInfo.setPreOil(obj.getOilPre());
				statInfo.setPerSpeedMileage(obj.getSpeedMileagePer());
				statInfo.setPerBadHour(obj.getBadHourPer());
				// 超速里程行业数据以及评语
				speedMold.setStMold(HttpConstant.REPORT_SPEED_MILE);
				speedMold
						.setStAvg(Double.toString(obj.getSpeedMileagePerAvg()));
				speedMold.setStDesc(obj.getSpeedMileagePerDesc());
				speedMold.setStCmpPrior(obj.getSpeedMileagePerCmpPrior());
				speedMold.setStCmpAvg(obj.getSpeedMileagePerCmpAvg());
				// 不良驾驶时长行业数据以及评语
				badMold.setStMold(HttpConstant.REPORT_BAD_TIME);
				badMold.setStAvg(Double.toString(obj.getBadHourPerAvg()));
				badMold.setStDesc(obj.getBadHourPerDesc());
				badMold.setStCmpPrior(obj.getBadHourPerCmpPrior());
				badMold.setStCmpAvg(obj.getBadHourPerCmpAvg());
				// 油耗行业数据以及评语
				oilMold.setStMold(HttpConstant.REPORT_OIL_MILE);
				oilMold.setStAvg(Double.toString(obj.getOilPreAvg()));
				oilMold.setStDesc(obj.getOilPreDesc());
				oilMold.setStCmpPrior(obj.getOilPerCmpPrior());
				oilMold.setStCmpAvg(obj.getOilPerCmpAvg());
			}
			// 遍历月的超速里程数据
			MonthStatInfo speedInfo = new MonthStatInfo();
			speedInfo.setStMonth(monthStr);
			speedInfo.setStData(obj.getSpeedMileagePer());
			speedInfo.setStOrder(obj.getSpeedMileagePerOrder());
			speedMonths.add(speedInfo);
			// 遍历月的不良驾驶时长数据
			MonthStatInfo badInfo = new MonthStatInfo();
			badInfo.setStMonth(monthStr);
			badInfo.setStData(obj.getBadHourPer());
			badInfo.setStOrder(obj.getBadHourPerOrder());
			badMonths.add(badInfo);
			// 遍历月的油耗数据
			MonthStatInfo oilInfo = new MonthStatInfo();
			oilInfo.setStMonth(monthStr);
			oilInfo.setStData(obj.getOilPre());
			oilInfo.setStOrder(obj.getOilPreOrder());
			oilMonths.add(oilInfo);
		}

		if (!reportFlag) {
			logger.info("单车报告-客户端请求统计月:" + monthReport + "的单车报告数据不存在");
			return null;
		}
		speedMold.setStMonthInfos(speedMonths);
		badMold.setStMonthInfos(badMonths);
		oilMold.setStMonthInfos(oilMonths);

		List<MoldStatInfo> allMold = new ArrayList<MoldStatInfo>();
		allMold.add(speedMold);
		allMold.add(badMold);
		allMold.add(oilMold);

		statInfo.setDetailInfo(allMold);
		return statInfo;
	}

	/**
	 * 企业报告数据转换处理.
	 * 
	 * @param infos
	 *            企业3个月的统计数据以及行业数据集合.
	 * @param reportMonth
	 *            统计月份,格式：yyyymm
	 * 
	 * @return 企业报告数据结构.
	 */
	private EpStatReport convertEpResp(List<EpMonthData> infos, Date reportMonth) {
		// 企业月报表对象
		EpStatReport statInfo = new EpStatReport();
		// 01-百公里超速里程
		MoldStatInfo speedMileMold = new MoldStatInfo();
		// 04-超速车辆占比
		MoldStatInfo speedPerpMold = new MoldStatInfo();
		// 02-百公里不良驾驶时长
		MoldStatInfo badHourMold = new MoldStatInfo();
		// 05-不良驾驶车辆占比
		MoldStatInfo badPerpMold = new MoldStatInfo();
		// 03-百公里油耗
		MoldStatInfo oilMold = new MoldStatInfo();
		// 06-超油车辆占比
		MoldStatInfo oilPerpMold = new MoldStatInfo();
		// 08-百公里超转时长
		MoldStatInfo rpmHourMold = new MoldStatInfo();
		// 07-百公里超速时长
		MoldStatInfo speedHourMold = new MoldStatInfo();
		// 09-百公里超长怠速时长
		MoldStatInfo longIdleHourMold = new MoldStatInfo();
		// 10-百公里怠速空调时长
		MoldStatInfo airIdleHourMold = new MoldStatInfo();
		// 11-百公里非经济区运行时长
		MoldStatInfo economicRunHourMold = new MoldStatInfo();

		// 前N个月的超速里程、不良驾驶时长、油耗数据
		List<MonthStatInfo> speedMileMonths = new ArrayList<MonthStatInfo>();
		List<MonthStatInfo> badHourMonths = new ArrayList<MonthStatInfo>();
		List<MonthStatInfo> oilMonths = new ArrayList<MonthStatInfo>();
		List<MonthStatInfo> speedPrepMonths = new ArrayList<MonthStatInfo>();
		List<MonthStatInfo> badPrepMonths = new ArrayList<MonthStatInfo>();
		List<MonthStatInfo> oilPrepMonths = new ArrayList<MonthStatInfo>();
		List<MonthStatInfo> speedHourMonths = new ArrayList<MonthStatInfo>();
		List<MonthStatInfo> rpmHourMonths = new ArrayList<MonthStatInfo>();
		List<MonthStatInfo> longIdleHourMonths = new ArrayList<MonthStatInfo>();
		List<MonthStatInfo> airIdleHourMonths = new ArrayList<MonthStatInfo>();
		List<MonthStatInfo> economicRunHourMonths = new ArrayList<MonthStatInfo>();

		// 不良驾驶行为占比
		List<BadBehaviorPerp> badPerp = new ArrayList<BadBehaviorPerp>();

		String monthStr = "";
		String monthReport = TimeUtil
				.formatDateToString(reportMonth, "yyyy-MM");
		boolean reportFlag = false;
		// 遍历前N个月的月报表数据
		for (EpMonthData obj : infos) {
			monthStr = obj.getDateTime();
			// 当前统计月的概要数据设置
			if (monthReport.equals(monthStr)) {
				reportFlag = true;
				// 概要数据
				statInfo.setDateTime(monthStr);
				statInfo.setCarNum(obj.getCarNum());
				statInfo.setTotalMileage(obj.getTotalMileage());
				statInfo.setTotalOil(obj.getTotalOil());
				statInfo.setValidFlag(obj.getValidFlag());
				if(!HttpConstant.ZERO.equals(obj.getValidFlag())){
					break;
				}
				// 超速里程行业数据以及评语
				speedMileMold.setStMold(HttpConstant.REPORT_SPEED_MILE);
				speedMileMold.setStAvg(Double.toString(obj
						.getSpeedMileagePerAvg()));
				speedMileMold.setStDesc(obj.getSpeedMileagePerDesc());
				speedMileMold.setStCmpPrior(obj.getSpeedMileagePerCmpPrior());
				speedMileMold.setStCmpAvg(obj.getSpeedMileagePerCmpAvg());
				// 超速车辆占比行业数据以及评语
				speedPerpMold.setStMold(HttpConstant.REPORT_SPEED_CAR);
				speedPerpMold
						.setStAvg(Double.toString(obj.getSpeedCarPerAvg()));
				speedPerpMold.setStDesc(obj.getSpeedCarPerDesc());
				speedPerpMold.setStCmpPrior(obj.getSpeedCarPerCmpPrior());
				speedPerpMold.setStCmpAvg(obj.getSpeedCarPerCmpAvg());
				// 不良驾驶时长行业数据以及评语
				badHourMold.setStMold(HttpConstant.REPORT_BAD_TIME);
				badHourMold.setStAvg(Double.toString(obj.getBadHourPerAvg()));
				badHourMold.setStDesc(obj.getBadHourPerDesc());
				badHourMold.setStCmpPrior(obj.getBadHourPerCmpPrior());
				badHourMold.setStCmpAvg(obj.getBadHourPerCmpAvg());
				// 不良驾驶车辆占比行业数据以及评语
				badPerpMold.setStMold(HttpConstant.REPORT_BAD_CAR);
				// modify by majch
				badPerpMold.setStAvg(Double.toString(obj.getBadCarPerAvg()));
				badPerpMold.setStDesc(obj.getBadCarPerDesc());
				badPerpMold.setStCmpPrior(obj.getBadCarPerCmpPrior());
				badPerpMold.setStCmpAvg(obj.getBadCarPerCmpAvg());
				// 百公里油耗行业数据以及评语
				oilMold.setStMold(HttpConstant.REPORT_OIL_MILE);
				oilMold.setStAvg(Double.toString(obj.getOilPreAvg()));
				oilMold.setStDesc(obj.getOilPreDesc());
				oilMold.setStCmpPrior(obj.getOilPerCmpPrior());
				oilMold.setStCmpAvg(obj.getOilPerCmpAvg());
				// 超油车辆占比行业数据以及评语
				oilPerpMold.setStMold(HttpConstant.REPORT_OIL_CAR);
				// modify by majch
				oilPerpMold.setStAvg(Double.toString(obj.getOilCarPerAvg()));
				oilPerpMold.setStDesc(obj.getOilCarPerDesc());
				oilPerpMold.setStCmpPrior(obj.getOilCarPerCmpPrior());
				oilPerpMold.setStCmpAvg(obj.getOilCarPerCmpAvg());
				// 百公里超速时长行业数据
				speedHourMold.setStMold(HttpConstant.REPORT_SPEED_TIME);
				speedHourMold
						.setStAvg(Double.toString(obj.getSpeedHourPerAvg()));
				// 百公里超转时长行业数据
				rpmHourMold.setStMold(HttpConstant.REPORT_RPM_TIME);
				rpmHourMold.setStAvg(Double.toString(obj.getRpmHourPerAvg()));
				// 百公里超长怠速时长行业数据
				longIdleHourMold.setStMold(HttpConstant.REPORT_LONGIDLE_TIME);
				longIdleHourMold.setStAvg(Double.toString(obj
						.getLongIdleHourPerAvg()));
				// 百公里怠速空调时长行业数据
				airIdleHourMold.setStMold(HttpConstant.REPORT_AIRIDLE_TIME);
				airIdleHourMold.setStAvg(Double.toString(obj
						.getAirIdleHourPerAvg()));
				// 百公里超经济区运行时长行业数据
				economicRunHourMold
						.setStMold(HttpConstant.REPORT_ECONOMICRUND_TIME);
				economicRunHourMold.setStAvg(Double.toString(obj
						.getEconomicRunHourPerAvg()));

				// 不良驾驶行为车辆占比
				BadBehaviorPerp speedPrep = new BadBehaviorPerp();
				speedPrep.setBaddriveType(HttpConstant.REPORT_QY_SPEED_CAR);
				speedPrep.setBaddrivePrecent(obj.getSpeedCarHourPer());

				BadBehaviorPerp rpmPrep = new BadBehaviorPerp();
				rpmPrep.setBaddriveType(HttpConstant.REPORT_QY_RPM_CAR);
				rpmPrep.setBaddrivePrecent(obj.getRpmCarPer());

				BadBehaviorPerp longIdlePrep = new BadBehaviorPerp();
				longIdlePrep
						.setBaddriveType(HttpConstant.REPORT_QY_LONGIDLE_CAR);
				longIdlePrep.setBaddrivePrecent(obj.getLongIdleCarPer());

				BadBehaviorPerp airIdlePrep = new BadBehaviorPerp();
				airIdlePrep.setBaddriveType(HttpConstant.REPORT_QY_AIRIDLE_CAR);
				airIdlePrep.setBaddrivePrecent(obj.getAirIdleCarPer());

				BadBehaviorPerp economicRunPrep = new BadBehaviorPerp();
				economicRunPrep
						.setBaddriveType(HttpConstant.REPORT_QY_ECONOMICRUND_CAR);
				economicRunPrep.setBaddrivePrecent(obj.getEconomicRunCarPer());

				badPerp.add(speedPrep);
				badPerp.add(rpmPrep);
				badPerp.add(longIdlePrep);
				badPerp.add(airIdlePrep);
				badPerp.add(economicRunPrep);
			}
			// 遍历月的超速里程数据
			MonthStatInfo speedMileInfo = new MonthStatInfo();
			speedMileInfo.setStMonth(monthStr);
			speedMileInfo.setStData(obj.getSpeedMileagePer());
			speedMileMonths.add(speedMileInfo);
			// 遍历月的不良驾驶时长数据
			MonthStatInfo badHourInfo = new MonthStatInfo();
			badHourInfo.setStMonth(monthStr);
			badHourInfo.setStData(obj.getBadHourPer());
			badHourMonths.add(badHourInfo);
			// 遍历月的百公里油耗数据
			MonthStatInfo oilInfo = new MonthStatInfo();
			oilInfo.setStMonth(monthStr);
			oilInfo.setStData(obj.getOilPre());
			oilMonths.add(oilInfo);
			// 遍历月的超速车辆占比数据
			MonthStatInfo speedPrepInfo = new MonthStatInfo();
			speedPrepInfo.setStMonth(monthStr);
			speedPrepInfo.setStData(obj.getSpeedCarPer());
			speedPrepMonths.add(speedPrepInfo);
			// 遍历月的不良驾驶车辆占比数据
			MonthStatInfo badPrepInfo = new MonthStatInfo();
			badPrepInfo.setStMonth(monthStr);
			badPrepInfo.setStData(obj.getBadCarPer());
			badPrepMonths.add(badPrepInfo);
			// 遍历月的超油车辆占比数据
			MonthStatInfo oilPrepInfo = new MonthStatInfo();
			oilPrepInfo.setStMonth(monthStr);
			oilPrepInfo.setStData(obj.getOilCarPer());
			oilPrepMonths.add(oilPrepInfo);
			// 遍历月的百公里超速时长数据
			MonthStatInfo speedHourInfo = new MonthStatInfo();
			speedHourInfo.setStMonth(monthStr);
			speedHourInfo.setStData(obj.getSpeedHourPer());
			speedHourMonths.add(speedHourInfo);
			// 遍历月的百公里超转时长数据
			MonthStatInfo rpmHourInfo = new MonthStatInfo();
			rpmHourInfo.setStMonth(monthStr);
			rpmHourInfo.setStData(obj.getRpmHourPer());
			rpmHourMonths.add(rpmHourInfo);
			// 遍历月的百公里超长怠速时长数据
			MonthStatInfo longIdleHourInfo = new MonthStatInfo();
			longIdleHourInfo.setStMonth(monthStr);
			longIdleHourInfo.setStData(obj.getLongIdleHourPer());
			longIdleHourMonths.add(longIdleHourInfo);
			// 遍历月的百公里空调怠速时长数据
			MonthStatInfo airIdleHourInfo = new MonthStatInfo();
			airIdleHourInfo.setStMonth(monthStr);
			airIdleHourInfo.setStData(obj.getAirIdleHourPer());
			airIdleHourMonths.add(airIdleHourInfo);
			// 遍历月的百公里非经济区运行时长数据
			MonthStatInfo economicRunHourInfo = new MonthStatInfo();
			economicRunHourInfo.setStMonth(monthStr);
			economicRunHourInfo.setStData(obj.getEconomicRunHourPer());
			economicRunHourMonths.add(economicRunHourInfo);
		}

		if (!reportFlag) {
			logger.info("企业报告-客户端请求统计月:" + monthReport + "的企业报告数据不存在");
			return null;
		}

		speedMileMold.setStMonthInfos(speedMileMonths);
		speedPerpMold.setStMonthInfos(speedPrepMonths);
		badHourMold.setStMonthInfos(badHourMonths);
		badPerpMold.setStMonthInfos(badPrepMonths);
		oilMold.setStMonthInfos(oilMonths);
		oilPerpMold.setStMonthInfos(oilPrepMonths);
		speedHourMold.setStMonthInfos(speedHourMonths);
		rpmHourMold.setStMonthInfos(rpmHourMonths);
		longIdleHourMold.setStMonthInfos(longIdleHourMonths);
		airIdleHourMold.setStMonthInfos(airIdleHourMonths);
		economicRunHourMold.setStMonthInfos(economicRunHourMonths);
		List<MoldStatInfo> allMold = new ArrayList<MoldStatInfo>();
		allMold.add(speedMileMold);
		allMold.add(speedPerpMold);
		allMold.add(badHourMold);
		allMold.add(badPerpMold);
		allMold.add(oilMold);
		allMold.add(oilPerpMold);
		allMold.add(speedHourMold);
		allMold.add(rpmHourMold);
		allMold.add(longIdleHourMold);
		allMold.add(airIdleHourMold);
		allMold.add(economicRunHourMold);
		statInfo.setDetailInfo(allMold);
		statInfo.setBadDriveInfo(badPerp);
		return statInfo;
	}

	/**
	 * 企业报告详情处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * @param month
	 *            统计月份,格式：yyyymm
	 * @param rsType
	 *            资源类型,01- 超速车辆详情;02- 不良驾驶车辆详情;03- 超油车辆详情
	 * 
	 * @return 企业报告详情数据.
	 */
	@Override
	public Response getEntiReportDetail(String token, String month,
			String rsType) {
		month = StringUtils.strip(month);
		rsType = StringUtils.strip(rsType);
		String epid = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ENTERPRISE_ID);
		String orgID = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ORGANIZATION_ID);
		logger.info("企业报告详情-企业ID为:" + epid + ",组织ID为：" + orgID + ",统计月份为："
				+ month + ",查询类型为：" + rsType);

		Date reportMonth;
		try {
			reportMonth = TimeUtil.parseStringToDate(month,
					HttpConstant.MONTH_FORMAT);
		} catch (ParseException e) {
			logger.error("企业报告详情-统计月份按照yyyyMM格式出错" + e.getMessage());
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		if (reportMonth.after(TimeUtil.getLastMonthD())) {
			logger.info("企业报告详情-平台未生成" + month + "统计数据");
			throw new ApplicationException(ErrorConstant.ERROR10004,
					Response.Status.BAD_REQUEST);
		}

		if (!HttpConstant.REPORT_QY_D_SPEED.equals(rsType)
				&& !HttpConstant.REPORT_QY_D_BAD.equals(rsType)
				&& !HttpConstant.REPORT_QY_D_OIL.equals(rsType)) {
			logger.info("企业报告详情-客户端请求参数" + rsType + "不在详情类型[01,02,03]中");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		if (CheckRequestParam.isEmpty(epid) || CheckRequestParam.isEmpty(orgID)) {
			logger.info("企业报告详情-实时-缺少企业ID或者组织ID");
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		List<EpCarDtl> infos = stMapper.getCarMonthDtl(month, epid, rsType,
				orgID);
		if (CheckRequestParam.isEmpty(infos)) {
			logger.info("企业报告详情-客户端请求企业ID:" + epid + "组织ID:" + orgID + "在"
					+ month + "没有" + rsType + "类型数据");
			return Response.status(Response.Status.NO_CONTENT)
					.header(HttpHeaders.CACHE_CONTROL, "no-store")
					.header("Pragma", "no-cache").build();
		}

		EpDtlResp result = new EpDtlResp();
		result.setDetailInfo(infos);
		return Response.ok(JacksonUtils.toJsonRuntimeException(result))
				.header(HttpHeaders.CACHE_CONTROL, "no-store")
				.header("Pragma", "no-cache").build();
	}

	/**
	 * 获取企业报告评语模板
	 * 
	 * @param token
	 *            访问令牌
	 * @param eTag
	 *            模板标记信息
	 * @return 企业评语模板详情数据
	 */
	@Override
	public Object getAllDesc(String token, String eTag) {
		// 验证终端Etag标志
		boolean checkEtagFlag = true;
		// 获取企业ID
		String enId = context.getHttpHeaders().getHeaderString(
				UserInfoKey.USR_ID);
		logger.info("获取企业评语模板列表");

		// 获取企业的所有评语模板
		List<ReportDesc> list = stMapper.getReportDesc(enId);
		logger.info("成功获取企业评语模板列表");
		logger.debug("list = " + (list == null ? "NULL" : list.size()));

		// 评语模板信息表内无数据
		if (list == null || list.size() == 0) {
			logger.error("企业评语模板信息表内无数据");
			throw new ApplicationException(ErrorConstant.ERROR10010,
					Response.Status.NOT_FOUND);
		} else {
			logger.debug("数据：");
			if (logger.isDebugEnabled()) {
				Iterator<ReportDesc> it = list.iterator();
				int i = 0;
				while (it.hasNext()) {
					ReportDesc iReportDesc = it.next();
					logger.debug(++i + iReportDesc.toString());
				}
			}
		}

		// 取数据中最近的更新时间
		String plaETag = list.get(0).getUpdateTime();

		logger.info("处理终端要求获取企业[" + enId + "]的推送规则请求");
		logger.info("终端持有的ETag:" + eTag);
		logger.info("平台持有的ETag:" + plaETag);

		// 终端持有的时间
		Date dateTer = new Date();
		// 平台持有的时间
		Date datePla = new Date();

		if (eTag == null) {
			logger.error("If-None-Match值为空");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

		// 强制更新
		if (!eTag.equals("0")) {
			try {
				dateTer = BeanUtil
						.checkTimeForm(eTag, HttpConstant.TIME_FORMAT);
			} catch (ParseException e) {
				logger.error("终端上传的参数非法", e);
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}
			// 转换平台标志时间
			if (!plaETag.equals("0")) {
				try {
					datePla = BeanUtil.checkTimeForm(plaETag,
							HttpConstant.TIME_FORMAT);
				} catch (ParseException e) {
					logger.error("转换平台标记时间时发生异常", e);
					throw new ApplicationException(ErrorConstant.ERROR90000,
							Response.Status.INTERNAL_SERVER_ERROR);
				}
			}
		} else {
			checkEtagFlag = false;
		}

		// 如果终端时间与平台时间相等则无数据返回
		if (checkEtagFlag && dateTer.compareTo(datePla) == 0) {
			logger.info("终端数据版本已经是最新的，不需更新");
			return Response.notModified().build();
		} else if (checkEtagFlag && dateTer.compareTo(datePla) > 0) {
			logger.error("终端数据版本大于平台数据版本");
			throw new ApplicationException(ErrorConstant.ERROR10103,
					Response.Status.BAD_REQUEST);
		}

		// 下发给客户端的结果列表
		List<ReportDesc> temp = new ArrayList<ReportDesc>();
		// 遍历模板列表，删除模板中已失效的部分
		Iterator<ReportDesc> it = list.iterator();
		while (it.hasNext()) {
			ReportDesc iReportDesc = it.next();
			// 如果模板有效则装入下发列表内
			if (iReportDesc.getDel().equals("0")) {
				temp.add(iReportDesc);
			}
		}

		// 拼装返回参数
		ReportDescResp iReportDescResp = new ReportDescResp();
		iReportDescResp.setEtag(plaETag);
		iReportDescResp.setList(temp);
		return JacksonUtils.toJsonRuntimeException(iReportDescResp);
	}
}
