/**
 * @(#)TacksServiceImpl.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.tacks;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.HttpConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.entity.tacks.TackDetailInfo;
import com.neusoft.mid.clwapi.entity.tacks.TackDetailResp;
import com.neusoft.mid.clwapi.entity.tacks.TackListInfo;
import com.neusoft.mid.clwapi.entity.tacks.TackListResp;
import com.neusoft.mid.clwapi.entity.tacks.TackResp;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.TacksMapper;
import com.neusoft.mid.clwapi.tools.BusinessUtil;
import com.neusoft.mid.clwapi.tools.CheckRequestParam;
import com.neusoft.mid.clwapi.tools.JacksonUtils;
import com.neusoft.mid.clwapi.tools.TimeUtil;

/**
 * @author <a href="mailto:suyingtao@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午7:52:17
 */
public class TacksServiceImpl implements TacksService {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	@Autowired
	private TacksMapper tcMapper;

	/**
	 * 车辆某天的行车记录列表信息处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * 
	 * @param vin
	 *            车辆vin信息.
	 * 
	 * @param date
	 *            时间,格式yyyymmdd.
	 * @return 车辆某天的行车记录列表信息.
	 */
	@Override
	public Response getVinTacksList(String token, String vin, String date) {
		vin = StringUtils.strip(vin);
		date = StringUtils.strip(date);
		logger.info("行车记录列表-车辆vin信息:" + vin + ",时间:" + date);

		try {
			TimeUtil.parseStringToDate(date, HttpConstant.DAY_FORMAT);
		} catch (ParseException e) {
			logger.error("行车记录列表-将" + date + "转换为yyyyMMdd格式时间时出错"
					+ e.getMessage());
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		List<TackListInfo> resultList = tcMapper.getCarRunRecs(date, vin);
		if (CheckRequestParam.isEmpty(resultList)) {
			logger.info("行车记录列表-客户端请求车辆VIN:" + vin + "在时间：" + date + "日无行车记录");
			return Response.status(Response.Status.NO_CONTENT)
					.header(HttpHeaders.CACHE_CONTROL, "no-store")
					.header("Pragma", "no-cache").build();
		}

		TackListResp resp = new TackListResp();
		resp.setResultList(resultList);

		return Response.ok(JacksonUtils.toJsonRuntimeException(resp))
				.header(HttpHeaders.CACHE_CONTROL, "no-store")
				.header("Pragma", "no-cache").build();
	}

	/**
	 * 车辆指定时段行车记录详细信息处理.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * 
	 * @param vin
	 *            车辆vin信息.
	 * 
	 * @param startTime
	 *            时间,格式yyyymmddhh24miss.
	 * 
	 * @param endTime
	 *            时间,格式yyyymmddhh24miss.
	 * @return 行车记录详细信息.
	 */
	@Override
	public Response getVinTacksDetail(String token, String vin,
			String startTime, String endTime) {
		vin = StringUtils.strip(vin);
		startTime = StringUtils.strip(startTime);
		endTime = StringUtils.strip(endTime);
		logger.info("行车记录详情-车辆vin信息:" + vin + ",开始时间:" + startTime + ",截止时间:"
				+ endTime);

		Date sTime;
		Date eTime;
		try {
			sTime = TimeUtil.parseStringToDate(startTime,
					HttpConstant.TIME_FORMAT);
			eTime = TimeUtil.parseStringToDate(endTime,
					HttpConstant.TIME_FORMAT);
		} catch (ParseException e) {
			logger.info("行车记录详情-客户端请求信息时间" + startTime + "和" + endTime
					+ "不符合yyyyMMddHHmmss规范");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		if (sTime.after(eTime)) {
			logger.info("行车记录详情-客户端请求信息时间" + startTime + "和" + endTime
					+ ",开始时间大于结束时间");
			throw new ApplicationException(ErrorConstant.ERROR10003,
					Response.Status.BAD_REQUEST);
		}

		List<TackDetailInfo> resultList = getTackPonits(vin, startTime, endTime);
		if (CheckRequestParam.isEmpty(resultList)) {
			logger.info("行车记录详情-客户端请求车辆VIN:" + vin + "在时间：" + startTime + "-"
					+ endTime + "内无行车轨迹点");
			return Response.status(Response.Status.NO_CONTENT)
					.header(HttpHeaders.CACHE_CONTROL, "no-store")
					.header("Pragma", "no-cache").build();
		}
		TackDetailResp resp = new TackDetailResp();
		resp.setResultList(resultList);

		return Response.ok(JacksonUtils.toJsonRuntimeException(resp))
				.header(HttpHeaders.CACHE_CONTROL, "no-store")
				.header("Pragma", "no-cache").build();
	}

	/**
	 * 车辆某段时间内轨迹信息处理.
	 * 
	 * @param vin
	 *            车辆vin信息.
	 * 
	 * @param sTime
	 *            开始时间,格式yyyymmddhh24miss.
	 * 
	 * @param eTime
	 *            结束时间,格式yyyymmddhh24miss.
	 * @return 轨迹点集合.
	 */
	private List<TackDetailInfo> getTackPonits(String vin, String sTime,
			String eTime) {
		List<TackDetailInfo> tempList = tcMapper.getTackPonits(sTime, eTime,
				vin);
		if (CheckRequestParam.isEmpty(tempList)) {
			return null;
		}

		String doorPriorFlag = "0";
		String recordID = "";
		List<TackDetailInfo> resultList = new ArrayList<TackDetailInfo>();
		for (TackDetailInfo obj : tempList) {		
			// 遍历除去异常乘车点的轨迹点，实现告警优先级、开门事件的计算
			if (!HttpConstant.EVENT_TYPE_ST.equals(obj.getAlarmEventType())) {
				//结果集tempList按照告警事件类型升序排列，故后面重复的告警不需要推送给客户端，忽略后面告警
				if(!recordID.equals(obj.getId())){
					String[] state = BusinessUtil.decodeState(obj.getAccFlg());
					String alarmTypeID = obj.getAlarmTypeID();
					
					//无告警或者告警事件类型为车辆故障时，才需要计算开门事件
					if(CheckRequestParam.isEmpty(alarmTypeID) || HttpConstant.EVENT_TYPE_CAR.equals(alarmTypeID)){
						if("1".equals(state[0]) && "0".equals(doorPriorFlag)){
							alarmTypeID = HttpConstant.ALARM_DOOR_PLAT;
							obj.setAlarmTypeID(HttpConstant.ALARM_DOOR_PLAT);
							obj.setAlarmEventType(HttpConstant.EVENT_TYPE_DOOR);								
						}
					}
					//无告警时去掉冗余数据
					if (CheckRequestParam.isEmpty(alarmTypeID)){
						delNeedlessInfo(obj);
					}else{//有告警时转义一些字段
						// 方向
						obj.setDirection(BusinessUtil.decodeDirction(obj.getAngle()));
						// 点火状态
						obj.setAccFlg(state[2]);
						// 定位状态
						obj.setLocState(state[1]);						
					}
					if(!"".equals(state[0])){
						doorPriorFlag = state[0];
					}
					resultList.add(obj);
					recordID = obj.getId();
				}	
			}else{
				resultList.add(obj);
			}
		}
		return resultList;
	}

	/**
	 * 删除非告警点多余属性value.
	 * 
	 * @param obj
	 *            轨迹点
	 * @return 去多余属性的 轨迹点.
	 */
	private TackDetailInfo delNeedlessInfo(TackDetailInfo obj) {
		obj.setSpeed(null);
		obj.setEngSpeed(null);
		obj.setPassengerNumber(null);
		obj.setLimitNumber(null);
		obj.setOil(null);
		obj.setAccFlg(null);
		return obj;
	}

	/**
	 * 拷贝TackDetailInfo对象属性.
	 * 
	 * @param obj
	 *            轨迹点
	 * @return 复制出新的轨迹点.
	 */
	private TackDetailInfo copyInfo(TackDetailInfo obj) {
		TackDetailInfo other = new TackDetailInfo();
		other.setTime(obj.getTime());
		other.setLatitude(obj.getLatitude());
		other.setLongitude(obj.getLongitude());
		other.setLocState(obj.getLocState());
		other.setSpeed(obj.getSpeed());
		other.setAngle(obj.getAngle());
		other.setDirection(obj.getDirection());
		other.setEngSpeed(obj.getEngSpeed());
		other.setPassengerNumber(obj.getPassengerNumber());
		other.setLimitNumber(obj.getLimitNumber());
		other.setOil(obj.getOil());
		other.setAccFlg(obj.getAccFlg());
		return other;
	}

	/**
	 * 根据告警信息查询行车记录信息.
	 * 
	 * @param token
	 *            访问令牌信息.
	 * 
	 * @param vin
	 *            车辆vin信息.
	 * 
	 * @param alarmTime
	 *            告警时间,格式yyyymmddhh24miss.
	 * 
	 * @param alarmID
	 *            告警ID.
	 * @return 行车记录.
	 */
	@Override
	public Response getVinTackByAlarm(String token, String alarmID, String vin,
			String alarmTime) {
		vin = StringUtils.strip(vin);
		alarmID = StringUtils.strip(alarmID);
		alarmTime = StringUtils.strip(alarmTime);
		logger.info("车辆vin信息:" + vin + ",告警时间:" + alarmTime + ",告警ID:"
				+ alarmID);

		try {
			TimeUtil.parseStringToDate(alarmTime, HttpConstant.TIME_FORMAT);
		} catch (ParseException e) {
			logger.info("告警行车记录查询-客户端请求信息时间" + alarmTime
					+ "不符合yyyyMMddHHmmss规范");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		List<TackResp> trackList = tcMapper.getRunRecsByTime(vin, alarmTime);
		if (CheckRequestParam.isEmpty(trackList)) {
			logger.info("告警行车记录查询-客户端请求车辆VIN:" + vin + "在时间：" + alarmTime
					+ "无对应行车记录");
			return Response.status(Response.Status.NO_CONTENT)
					.header(HttpHeaders.CACHE_CONTROL, "no-store")
					.header("Pragma", "no-cache").build();
		}

		TackResp driveRec = trackList.get(0);
		return Response.ok(JacksonUtils.toJsonRuntimeException(driveRec))
				.header(HttpHeaders.CACHE_CONTROL, "no-store")
				.header("Pragma", "no-cache").build();
	}
}
