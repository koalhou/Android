/**
 * @(#)NewsServiceImpl.java 2013-4-13
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.news;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
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
import com.neusoft.mid.clwapi.entity.news.CheckNewsInfo;
import com.neusoft.mid.clwapi.entity.news.CheckNewsRequ;
import com.neusoft.mid.clwapi.entity.news.NewsInfo;
import com.neusoft.mid.clwapi.entity.news.NewsRequ;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.CommonMapper;
import com.neusoft.mid.clwapi.mapper.NewsMapper;
import com.neusoft.mid.clwapi.tools.JacksonUtils;
import com.neusoft.mid.clwapi.tools.TimeUtil;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-13 上午11:34:16
 */
public class NewsServiceImpl implements NewsService {

	private Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

	@Autowired
	private CommonMapper iCommonMapper;

	@Autowired
	private NewsMapper iNewsMapper;

	@Context
	private MessageContext context;

	/**
	 * 获取新闻概要信息
	 * 
	 * @param token
	 *            用户令牌
	 * @param body
	 *            参数
	 * @return
	 */
	@Override
	public Object getNewsSummaryInfo(String token, String body) {
		logger.info("处理获取新闻概要信息请求");

		// 获取参数
		NewsRequ iNewsRequ = JacksonUtils.fromJsonRuntimeException(body,
				NewsRequ.class);
		// 去除前后空格
		iNewsRequ.setEndTime(StringUtils.strip(iNewsRequ.getEndTime()));
		iNewsRequ.setStartTime(StringUtils.strip(iNewsRequ.getStartTime()));
		iNewsRequ.setNum(StringUtils.strip(iNewsRequ.getNum()));
		iNewsRequ.setType(StringUtils.strip(iNewsRequ.getType()));

		// 验证参数
		logger.info("验证终端请求参数");
		if (StringUtils.isEmpty(iNewsRequ.getType())) {
			logger.error("终端请求参数非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		} else if (StringUtils.isEmpty(iNewsRequ.getStartTime())
				|| StringUtils.isEmpty(iNewsRequ.getEndTime())) {
			logger.error("终端请求参数非法，开始时间与结束时间不能为空");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		} else if (HttpConstant.TIME_15_DAY_AGO.equalsIgnoreCase(iNewsRequ
				.getStartTime())
				&& !HttpConstant.TIME_ZERO.equals(iNewsRequ.getEndTime())) {
			logger.error("终端请求参数非法，当开始时间为-dd15时，结束时间不可以为非0的值");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}
		
		// 如果num值为空则设置Num为null否则验证num值是否是数字
		if (StringUtils.isEmpty(iNewsRequ.getNum())) {
			iNewsRequ.setNum(null);
		} else if (!StringUtils.isNumeric(iNewsRequ.getNum())) {
			logger.error("终端请求参数非法，num值只能是数字");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		// 判断开始时间与结束时间是否符合时间格式
		if (!HttpConstant.TIME_ZERO.equals(iNewsRequ.getStartTime())
				&& !HttpConstant.TIME_15_DAY_AGO.equalsIgnoreCase(iNewsRequ
						.getStartTime())) {
			try {
				TimeUtil.parseStringToDate(iNewsRequ.getStartTime(),
						HttpConstant.TIME_FORMAT);
			} catch (ParseException e) {
				logger.error("开始时间格式不符合要求" + e.getMessage());
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}
		}
		if (!HttpConstant.TIME_ZERO.equals(iNewsRequ.getEndTime())) {
			try {
				TimeUtil.parseStringToDate(iNewsRequ.getEndTime(),
						HttpConstant.TIME_FORMAT);
			} catch (ParseException e) {
				logger.error("结束时间格式不符合要求" + e.getMessage());
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}
		}

		if (HttpConstant.TIME_ZERO.equals(iNewsRequ.getStartTime())) {
			iNewsRequ.setStartTime(null);
		} else if (HttpConstant.TIME_15_DAY_AGO.equalsIgnoreCase(iNewsRequ
				.getStartTime())) {
			Date dBTime = iCommonMapper.getDBTime();
			Date fifteenDayAgo = TimeUtil.get15Ago(dBTime);
			String startTime = TimeUtil.formatDateToString(fifteenDayAgo,
					HttpConstant.TIME_FORMAT);
			iNewsRequ.setStartTime(startTime);
		}
		if (HttpConstant.TIME_ZERO.equals(iNewsRequ.getEndTime())) {
			iNewsRequ.setEndTime(null);
		}

		// 判断Type字段
		if (iNewsRequ.getType().equalsIgnoreCase("-1")) {
			iNewsRequ.setType(null);
		} else if (iNewsRequ.getType().equals("0")
				|| iNewsRequ.getType().equals("1")) {
			// 参数符合要求不做处理
		} else {
			logger.error("终端请求参数非法，type值异常");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		// 当终端上传值为0时

		logger.info("参数验证通过");
		logger.info("开始获取新闻概要信息列表");

		// 获取新闻概要信息列表
		List<NewsInfo> list = iNewsMapper.getNewsList(iNewsRequ);
		logger.debug("list=" + (list == null ? "NULL" : list.size()));

		if (list == null || list.size() == 0) {
			logger.info("未获取到满足条件的有效信息");
			return Response.noContent().build();
		}

		logger.info("成功获取新闻概要信息列表");
		logger.debug("新闻营销概要信息列表内容：");
		if (logger.isDebugEnabled()) {
			Iterator<NewsInfo> it = list.iterator();
			while (it.hasNext()) {
				NewsInfo temp = it.next();
				logger.debug(temp.toString());
			}
		}

		// 拼装返回参数
		Map<String, List<NewsInfo>> map = new HashMap<String, List<NewsInfo>>();
		map.put("news", list);

		logger.info("成功处理获取新闻概要信息请求");
		return JacksonUtils.toJsonRuntimeException(map);
	}

	/**
	 * 登记推送消息查看记录
	 * 
	 * @param token
	 *            用户令牌
	 * @param body
	 *            参数
	 * @return
	 */
	@Override
	public String checkNewsInfo(String token, String body) {
		logger.info("开始处理登记推送消息查看记录请求");
		logger.info("验证终端请求参数");

		CheckNewsRequ iCheckNewsRequ = JacksonUtils.fromJsonRuntimeException(
				body, CheckNewsRequ.class);
		// 提取用户ID
		iCheckNewsRequ.setUsrId(context.getHttpHeaders().getHeaderString(
				UserInfoKey.USR_ID));

		// 验证终端参数
		if (StringUtils.isBlank(iCheckNewsRequ.getNewsId())
				|| StringUtils.isBlank(iCheckNewsRequ.getNewsTime())
				|| StringUtils.isBlank(iCheckNewsRequ.getNewsType())) {
			logger.error("终端参数非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

		// 去除左右空格
		iCheckNewsRequ.setNewsId(StringUtils.strip(iCheckNewsRequ.getNewsId()));
		iCheckNewsRequ.setNewsTime(StringUtils.strip(iCheckNewsRequ
				.getNewsTime()));
		iCheckNewsRequ.setNewsType(StringUtils.strip(iCheckNewsRequ
				.getNewsType()));

		// 打印请求参数
		logger.info("用户ID:" + iCheckNewsRequ.getUsrId());
		logger.info("消息ID:" + iCheckNewsRequ.getNewsId());
		logger.info("消息发布时间:" + iCheckNewsRequ.getNewsTime());
		logger.info("消息类型:" + iCheckNewsRequ.getNewsType());

		logger.info("终端请求参数合法");
		logger.info("开始查找用户的登记信息");

		// 根据用户ID与消息ID查找用户是否登记过查看信息
		CheckNewsInfo iCheckNewsInfo = iNewsMapper
				.getCheckNewsInfo(iCheckNewsRequ);

		// 判断用户是否登记过该条消息的查看信息
		if (iCheckNewsInfo == null) {
			logger.info("用户未登记过该消息的查看信息，执行新增策略");
			iNewsMapper.addCheckNewsInfo(iCheckNewsRequ);
			logger.info("添加成功");
		} else {
			logger.info("用户登记过该条消息的查看信息，执行更新策略");
			// 查看次数增一
			iCheckNewsInfo.setCount(String.valueOf(Integer
					.valueOf(iCheckNewsInfo.getCount()) + 1));
			logger.debug("更新后的信息为：" + iCheckNewsInfo.toString());
			iNewsMapper.updateCheckNewsInfo(iCheckNewsInfo);
			logger.info("更新成功");
		}

		logger.info("成功处理登记推送消息查看记录请求");

		return HttpConstant.RESP_200;
	}
}
