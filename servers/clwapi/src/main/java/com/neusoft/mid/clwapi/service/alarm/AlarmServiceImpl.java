/**
 * @(#)AlarmServiceImpl.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.alarm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.HttpConstant;
import com.neusoft.mid.clwapi.common.UserInfoKey;
import com.neusoft.mid.clwapi.entity.alarm.AlarmInfo;
import com.neusoft.mid.clwapi.entity.alarm.AlarmRequ;
import com.neusoft.mid.clwapi.entity.alarm.AlarmResp;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.AlarmMapper;
import com.neusoft.mid.clwapi.mapper.CommonMapper;
import com.neusoft.mid.clwapi.tools.JacksonUtils;
import com.neusoft.mid.clwapi.tools.TimeUtil;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-9 上午9:41:30
 */
public class AlarmServiceImpl implements AlarmService {

	private Logger logger = LoggerFactory.getLogger(AlarmServiceImpl.class);

	@Autowired
	private AlarmMapper iAlarmMapper;

	@Autowired
	private CommonMapper iCommonMapper;

	@Context
	private MessageContext context;

	/**
	 * 批量获取告警信息
	 * 
	 * @param token
	 *            用户token
	 * @param body
	 *            消息体
	 * @return
	 */
	@Override
	public Object getAlarmList(String token, String body) {
		logger.info("处理获取告警信息列表请求");

		String organizationId = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ORGANIZATION_ID);

		// 获取参数
		AlarmRequ iAlarmRequ = JacksonUtils.fromJsonRuntimeException(body,
				AlarmRequ.class);
		// 去除前后空格
		iAlarmRequ.setEndTime(StringUtils.strip(iAlarmRequ.getEndTime()));
		iAlarmRequ.setStartTime(StringUtils.strip(iAlarmRequ.getStartTime()));
		iAlarmRequ.setNum(StringUtils.strip(iAlarmRequ.getNum()));
		iAlarmRequ.setOperat(StringUtils.strip(iAlarmRequ.getOperat()));
		iAlarmRequ.setOrganizationId(organizationId);

		// 验证参数
		logger.info("验证终端请求参数");
		if (StringUtils.isEmpty(iAlarmRequ.getOperat())
				|| StringUtils.isEmpty(iAlarmRequ.getNum())
				|| StringUtils.isEmpty(iAlarmRequ.getType())) {
			logger.error("终端请求参数非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		} else if (StringUtils.isEmpty(iAlarmRequ.getStartTime())
				|| StringUtils.isEmpty(iAlarmRequ.getEndTime())) {
			logger.error("终端请求参数非法，开始时间与结束时间不能为空");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		} else if (HttpConstant.TIME_ZERO.equals(iAlarmRequ.getStartTime())
				&& HttpConstant.TIME_ZERO.equals(iAlarmRequ.getEndTime())) {
			logger.error("终端请求参数非法，开始时间与结束时间不能同时为0");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		} else if (HttpConstant.TIME_24_HOURS_AGO.equalsIgnoreCase(iAlarmRequ
				.getStartTime())
				&& !HttpConstant.TIME_ZERO.equals(iAlarmRequ.getEndTime())) {
			logger.error("终端请求参数非法，当开始时间为-hh24时，结束时间不可以为非0的值");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		} else if (!StringUtils.isNumeric(iAlarmRequ.getNum())) {
			logger.error("终端请求参数非法，num值只能是数字");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		} else if (!StringUtils.isEmpty(iAlarmRequ.getPage())
				&& !StringUtils.isNumeric(iAlarmRequ.getPage())) {
			logger.error("终端请求参数非法，page值只能是数字");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}
		// 纠正page值，为空则转换为0
		iAlarmRequ
				.setPage(StringUtils.isEmpty(iAlarmRequ.getPage()) ? HttpConstant.ZERO
						: iAlarmRequ.getPage());

		// 获取页数与需获取的元素数量
		int page = Integer.valueOf(iAlarmRequ.getPage());
		int num = Integer.valueOf(iAlarmRequ.getNum());

		// 验证页数是否符合要求
		if (page < 0) {
			logger.error("终端请求参数非法，page值只能大于等于0");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		// 组装查询条件
		// 判断查询类型
		if (iAlarmRequ.getOperat().equals(
				HttpConstant.ALARMPORT_OPERATE_UNTREATED)) {
			iAlarmRequ.setOperat(HttpConstant.ALARMPORT_OPERATE_UNTREATED_DB);
		} else if (iAlarmRequ.getOperat().equals(
				HttpConstant.ALARMPORT_OPERATE_TREATED)) {
			iAlarmRequ.setOperat(HttpConstant.ALARMPORT_OPERATE_TREATED_DB);
		} else if (iAlarmRequ.getOperat().equals(
				HttpConstant.ALARMPORT_OPERATE_ALL)) {
			iAlarmRequ.setOperat(HttpConstant.ALARMPORT_OPERATE_ALL_DB);
		} else {
			logger.error("终端请求参数非法，参数operat只能为0、1、2中的一个值");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		if (iAlarmRequ.getType().equals(HttpConstant.ALARMPORT_TYPE_OVERSPEED)) {
			iAlarmRequ.setSpeedFlag(true);
			iAlarmRequ.setOverLoadFlag(null);
		} else if (iAlarmRequ.getType().equals(
				HttpConstant.ALARMPORT_TYPE_OVERLOAD)) {
			iAlarmRequ.setSpeedFlag(null);
			iAlarmRequ.setOverLoadFlag(true);
		} else if (iAlarmRequ.getType().equals(HttpConstant.ALARMPORT_TYPE_ALL)) {
			iAlarmRequ.setSpeedFlag(null);
			iAlarmRequ.setOverLoadFlag(null);
		} else {
			logger.error("终端请求参数非法，参数type只能为0、1、2中的一个值");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		// 判断开始时间与结束时间是否符合时间格式
		if (!HttpConstant.TIME_ZERO.equals(iAlarmRequ.getStartTime())
				&& !HttpConstant.TIME_24_HOURS_AGO.equalsIgnoreCase(iAlarmRequ
						.getStartTime())) {
			try {
				TimeUtil.parseStringToDate(iAlarmRequ.getStartTime(),
						HttpConstant.TIME_FORMAT);
			} catch (ParseException e) {
				logger.error("开始时间格式不符合要求" + e.getMessage());
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}
		}
		if (!HttpConstant.TIME_ZERO.equals(iAlarmRequ.getEndTime())) {
			try {
				TimeUtil.parseStringToDate(iAlarmRequ.getEndTime(),
						HttpConstant.TIME_FORMAT);
			} catch (ParseException e) {
				logger.error("结束时间格式不符合要求" + e.getMessage());
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}
		}

		logger.info("终端请求参数符合要求");

		// 设置开始时间与结束时间
		if (HttpConstant.TIME_ZERO.equals(iAlarmRequ.getStartTime())) {
			iAlarmRequ.setStartTime(null);
		} else if (HttpConstant.TIME_24_HOURS_AGO.equalsIgnoreCase(iAlarmRequ
				.getStartTime())) {
			Date dBTime = iCommonMapper.getDBTime();
			Date oneDayAgo = TimeUtil.get24Ago(dBTime);
			String startTime = TimeUtil.formatDateToString(oneDayAgo,
					HttpConstant.TIME_FORMAT);
			iAlarmRequ.setStartTime(startTime);
		}

		if (HttpConstant.TIME_ZERO.equals(iAlarmRequ.getEndTime())) {
			iAlarmRequ.setEndTime(null);
		}

		iAlarmRequ.setEnId(context.getHttpHeaders().getHeaderString(
				UserInfoKey.ENTERPRISE_ID));

		// 页数转换为行数
		if (page != 0) {
			iAlarmRequ.setStartRow(String.valueOf(page * num - num));
			iAlarmRequ.setEndRow(String.valueOf(page * num));
		} else if (num != 0) {
			iAlarmRequ.setStartRow(HttpConstant.ZERO);
			iAlarmRequ.setEndRow(String.valueOf(num));
		} else {
			iAlarmRequ.setStartRow(null);
			iAlarmRequ.setEndRow(null);
		}

		logger.info("开始向数据库发送请求");
		// 获取列表
		List<AlarmInfo> temp = iAlarmMapper.getAlarmList(iAlarmRequ);
		logger.info("数据库执行成功返回");

		// 判断返回结果
		if (temp == null || temp.size() == 0) {
			logger.info("未找到满足条件的告警信息");
			return Response.noContent().build();
		}
		logger.info("成功获取告警列表");

		if (logger.isDebugEnabled()) {
			logger.debug("List size:" + temp.size());
			Iterator<AlarmInfo> it = temp.iterator();
			while (it.hasNext()) {
				logger.debug(it.next().toString());
			}
		}

		// 拼装告警内容
		List<AlarmInfo> content = new ArrayList<AlarmInfo>();
		Iterator<AlarmInfo> it = temp.iterator();
		while (it.hasNext()) {
			AlarmInfo a = it.next();
			if (!StringUtils.isEmpty(a.getSpeeding())) {
				a.setAlarmCont(a.getSpeeding());
			} else if (!StringUtils.isEmpty(a.getPhotoId())) {
				a.setAlarmCont(a.getPhotoId());
			} else {
				a.setAlarmCont(null);
			}
			content.add(a);
		}

		// 拼装返回体
		AlarmResp iAlarmResp = new AlarmResp();
		iAlarmResp.setContent(content);

		logger.info("成功处理获取告警信息列表请求");

		return JacksonUtils.toJsonRuntimeException(iAlarmResp);
	}

	/**
	 * 处理告警信息API
	 * 
	 * @param token
	 *            用户token
	 * @param warnId
	 *            告警ID
	 * @param body
	 *            处理信息
	 */
	@Override
	public String dealAlarm(String token, String warnId, String alarmTime,
			String body) {
		logger.info("开始处理告警信息请求");

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) JacksonUtils
				.jsonToMapRuntimeException(body);

		logger.info("告警ID：" + warnId);
		logger.info("告警发生时间：" + alarmTime);
		logger.info("处理信息：[" + map.get(HttpConstant.ALARMPORT_DEAL_KEY) + "]");

		// 验证参数是否合法
		if (StringUtils.isEmpty(warnId) || StringUtils.isEmpty(alarmTime)) {
			logger.error("参数非法");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		String usrId = context.getHttpHeaders().getHeaderString(
				UserInfoKey.USR_ID);
		logger.info("用户ID：" + usrId);

		logger.info("终端请求参数合法");

		logger.info("开始向数据库发送请求");
		// 处理告警信息
		iAlarmMapper.dealAlarm(warnId,
				map.get(HttpConstant.ALARMPORT_DEAL_KEY), usrId, alarmTime);
		logger.info("数据库执行成功返回");

		logger.info("成功处理告警信息请求");

		return HttpConstant.RESP_200;
	}

	/**
	 * 获取告警处理信息API
	 * 
	 * @param token
	 *            用户令牌
	 * @param body
	 *            请求体
	 * @return 处理信息
	 */
	@Override
	public Object getAlarmInfo(String token, String body) {

		logger.info("开始处理获取告警处理信息请求");

		AlarmRequ iAlarmRequ = JacksonUtils.fromJsonRuntimeException(body,
				AlarmRequ.class);

		// 去除前后空格
		iAlarmRequ.setAlarmId(StringUtils.strip(iAlarmRequ.getAlarmId()));
		iAlarmRequ.setConfTime(StringUtils.strip(iAlarmRequ.getConfTime()));

		// 校验参数
		if (StringUtils.isEmpty(iAlarmRequ.getAlarmId())
				|| StringUtils.isEmpty(iAlarmRequ.getConfTime())
				|| StringUtils.isEmpty(iAlarmRequ.getAlarmTime())) {
			logger.error("终端请求参数不合法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

		// 如果为零则忽略处理时间信息
		if (iAlarmRequ.getConfTime().equals(HttpConstant.TIME_ZERO)) {
			iAlarmRequ.setConfTime(null);
		}

		try {
			if (iAlarmRequ.getConfTime() != null) {
				TimeUtil.parseStringToDate(iAlarmRequ.getConfTime(),
						HttpConstant.TIME_FORMAT);
			}
			TimeUtil.parseStringToDate(iAlarmRequ.getAlarmTime(),
					HttpConstant.TIME_FORMAT);
		} catch (ParseException e) {
			logger.error("参数格式异常，不是时间串");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		logger.info("请求参数符合要求");

		logger.info("开始获取告警处理信息");
		// 获取告警处理信息
		AlarmInfo iAlarmInfo = iAlarmMapper.getAlarmDealInfo(iAlarmRequ);
		logger.info("数据库执行成功返回");

		if (iAlarmInfo == null) {
			logger.error("未找到符合条件的告警处理信息");
			return Response.notModified().build();
		}
		logger.info("成功获取告警处理信息");

		// 打印获取到的告警处理信息
		logger.debug(iAlarmInfo.toString());

		logger.info("返回应答");
		logger.info("成功处理获取告警处理信息请求");
		return JacksonUtils.toJsonRuntimeException(iAlarmInfo);
	}
}
