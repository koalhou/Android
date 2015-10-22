/**
 * @(#)HomePageServiceImpl.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.homePage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.neusoft.mid.clwapi.entity.homePage.PageInfoResp;
import com.neusoft.mid.clwapi.entity.homePage.WeatherInfoResp;
import com.neusoft.mid.clwapi.entity.monitor.MonitorResp;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.HomePageMapper;
import com.neusoft.mid.clwapi.memcached.MemcacheCache;
import com.neusoft.mid.clwapi.memcached.MemcacheCacheManager;
import com.neusoft.mid.clwapi.service.common.CarMonitorService;
import com.neusoft.mid.clwapi.service.common.UsrOauthService;
import com.neusoft.mid.clwapi.tools.JacksonUtils;
import com.neusoft.mid.clwapi.tools.TimeUtil;
import com.neusoft.mid.clwapi.tools.WeatherReport;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-3-26 下午12:37:32
 */
public class HomePageServiceImpl implements HomePageService {

	private Logger logger = LoggerFactory.getLogger(HomePageServiceImpl.class);
	@Context
	private MessageContext context;
	@Autowired
	private UsrOauthService usrOauthService;
	@Autowired
	private HomePageMapper homePageMapper;
	@Autowired
	private CarMonitorService carMonitorService;
	@Autowired
	private MemcacheCacheManager memcacheCacheManager;

	/**
	 * 获取首页信息
	 * 
	 * @param token
	 *            用户令牌
	 * @param eTag
	 *            最后获取到的消息标记信息|最后获取到的告警标记信息
	 * @return 首页信息
	 */
	@Override
	public String getHomePageInfo(String token, String uptime) {
		logger.info("处理获取首页信息请求");
		logger.info("标记：[ " + uptime + " ](消息标记信息|告警标记信息)");

		logger.info("验证参数是否合法");
		// 取up_time值
		try {
			Map<?, ?> map = JacksonUtils.jsonToMap(uptime);
			uptime = (String) map.get(HttpConstant.UP_TIME);
		} catch (Exception e) {
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}
		logger.info("参数合法");

		String usrId = context.getHttpHeaders().getHeaderString(
				UserInfoKey.USR_ID);
		String enId = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ENTERPRISE_ID);
		String organizationId = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ORGANIZATION_ID);

		String alarmParName1 = null;
		String alarmParName2 = null;
		String timea = null;
		String timeb = null;

		// 格式化上传时间
		Date[] etag = getEtag(uptime);
		// 取当天零时
		String yesterday = TimeUtil.getSysdateYesterday();
		String now = TimeUtil.getSysdate();
		Date zeroTime = TimeUtil.getTodayZero();
		// 取分区名
		if (etag[1] == null) {
			alarmParName1 = HttpConstant.ALARM_PARPITION + now;
			alarmParName2 = HttpConstant.ALARM_PARPITION + yesterday;
			timeb = TimeUtil.formatDateToString(etag[1],
					HttpConstant.TIME_FORMAT);
		} else if (zeroTime.compareTo(etag[1]) <= 0) {
			alarmParName1 = HttpConstant.ALARM_PARPITION + now;
			alarmParName2 = null;
			timea = TimeUtil.formatDateToString(etag[1],
					HttpConstant.TIME_FORMAT);
		} else {
			alarmParName1 = HttpConstant.ALARM_PARPITION + now;
			alarmParName2 = HttpConstant.ALARM_PARPITION + yesterday;
			timeb = TimeUtil.formatDateToString(etag[1],
					HttpConstant.TIME_FORMAT);
		}

		String newsNumTime = null;

		// 格式化时间以适应数据库
		newsNumTime = etag[0] == null ? null : TimeUtil.formatDateToString(
				etag[0], HttpConstant.TIME_FORMAT);

		logger.info("开始获取大于给定时间的推送消息数time=" + newsNumTime);
		// 获取大于最新时间且有效的推送消息数
		String newsNum = homePageMapper.getNewsNum(newsNumTime);
		logger.info("成功获取大于给定时间的推送消息数num=" + newsNum);
		logger.info("开始获取大于给定时间的告警数量time=" + timeb);
		// 获取大于给定时间且最近一天的告警数
		String alarmNum = homePageMapper.getAlarmNum(timea, timeb, enId,
				alarmParName1, alarmParName2, organizationId);
		logger.info("成功获取大于给定时间的告警数量num=" + alarmNum);
		logger.info("开始获取用户手机终端下发的照片列表");
		// 获取前推24小时且为手机终端指定下发的照片列表
		List<String> photoList = homePageMapper.getPhotoList(usrId,organizationId);
		logger.debug("数据库成功处理请求");

		if (photoList == null || photoList.size() == 0) {
			logger.info("照片列表为空");
			if (photoList == null) {
				photoList = new ArrayList<String>();
			}
		} else {
			logger.info("成功获取照片列表");
			if (logger.isDebugEnabled()) {
				Iterator<String> it = photoList.iterator();
				while (it.hasNext()) {
					logger.debug(it.next());
				}
			}
		}

		logger.info("根据企业ID返回企业的全部车辆数、行驶车辆数、停驶车辆数");
		// 根据企业ID返回企业的全部车辆数、行驶车辆数、停驶车辆数
		MonitorResp iMonitorResp = carMonitorService.getEpCarNums(enId,organizationId);
		logger.info("成功获取");
		
		logger.info("开始获取企业报告有效月列表");
		List<String> monthList = homePageMapper.getEpReportMonth(enId);
		logger.info("成功获取");
		
		logger.info("开始获取企业报告的数量 ");
		String epNum = homePageMapper.getEpReportNum(enId);
		logger.info("成功获取");		

		// 返回内容
		PageInfoResp iPageInfoResp = new PageInfoResp();
		iPageInfoResp.setMsgNum(newsNum);
		iPageInfoResp.setWarnNum(alarmNum);
		iPageInfoResp.setPhotoContent(photoList.size() == 0 ? null : photoList);
		iPageInfoResp.setTotal(iMonitorResp.getCarNum());
		iPageInfoResp.setRunning(iMonitorResp.getRunNum());
		iPageInfoResp.setStop(iMonitorResp.getStopNum());
		iPageInfoResp.setEpMonth(monthList.size() == 0 ? null : monthList.get(0));
		iPageInfoResp.setServerDate(now);
		iPageInfoResp.setHasReport("0".equals(epNum) ? HttpConstant.ZERO : HttpConstant.ONE);
		logger.info("成功处理获取首页信息请求");

		return JacksonUtils.toJsonRuntimeException(iPageInfoResp);
	}

	/**
	 * 获取气象信息
	 * 
	 * @param region
	 *            用户所在城市信息，例如：郑州(真实信息使用uri转义)
	 * @return 天气信息
	 */
	@Override
	public String getWeather(String token, String region) {
		logger.info("开始处理获取气象信息请求");
		logger.info("终端要求获取[ " + region + " ]的气象信息");

		region = StringUtils.strip(region);

		try {
			region = URLDecoder.decode(region, HttpConstant.CHARSET);
		} catch (UnsupportedEncodingException e) {
			logger.error("URL转义发生异常" + e.getMessage());
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		logger.info("URL转义后：[ " + region + " ]");

		if (region == null || region.equals("null")) {
			logger.error("地理位置信息异常，终端上传的位置信息为NULL");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

		//2014-03-14 by wuxja 将天气预报信息存入缓存
		MemcacheCache cache = (MemcacheCache)memcacheCacheManager.getCache("CLW_API_CACHE");
		WeatherInfoResp iWeatherInfoResp = null;
		logger.info("dd"+cache.get("WEATHER_"+region));
		if(cache.get("WEATHER_"+region)!=null)
			iWeatherInfoResp = (WeatherInfoResp)cache.get("WEATHER_"+region).get();
		else{
			iWeatherInfoResp = WeatherReport.getWeatherInfo(region);
			iWeatherInfoResp.setValidSeconds(18000);
			cache.put("WEATHER_"+region, iWeatherInfoResp);
		}
		logger.info("成功处理获取气象信息请求");
		return JacksonUtils.toJsonRuntimeException(iWeatherInfoResp);
	}

	/**
	 * 获取并验证标记信息
	 * 
	 * @param uptime
	 * @return
	 */
	// 验证参数是否为空
	private Date[] getEtag(String uptime) {
		if (StringUtils.isEmpty(uptime)) {
			logger.error("标志为空");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}
		// 格式化
		String[] s = StringUtils.split(uptime, "|");
		Date[] d = new Date[s.length];

		if (s.length != 2) {
			logger.error("标志格式不符合要求[" + s.length + "]");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		for (int i = 0; i < s.length; i++) {
			s[i] = StringUtils.strip(s[i]);
			if (!s[i].equals(HttpConstant.TIME_ZERO)) {
				try {
					d[i] = TimeUtil.parseStringToDate(s[i],
							HttpConstant.TIME_FORMAT);
				} catch (ParseException e) {
					logger.error("终端上传的时间格式异常" + e.getMessage());
					throw new ApplicationException(ErrorConstant.ERROR10002,
							Response.Status.BAD_REQUEST);
				}
			} else {
				s[i] = null;
			}
		}
		return d;
	}
}
