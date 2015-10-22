/**
 * @(#)NewsMapper.java 2013-4-13
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.entity.news.CheckNewsInfo;
import com.neusoft.mid.clwapi.entity.news.CheckNewsRequ;
import com.neusoft.mid.clwapi.entity.news.NewsInfo;
import com.neusoft.mid.clwapi.entity.news.NewsRequ;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-13 下午12:45:59
 */
public interface NewsMapper {

	/**
	 * 
	 * 获取新闻营销信息列表
	 * 
	 * @param iNewsRequ
	 *            请求参数
	 * @return
	 * @throws DataAccessException
	 */
	List<NewsInfo> getNewsList(NewsRequ iNewsRequ) throws DataAccessException;

	/**
	 * 查询用户推送消息查看记录
	 * 
	 * @param iCheckNewsRequ
	 * @return
	 * @throws DataAccessException
	 */
	CheckNewsInfo getCheckNewsInfo(CheckNewsRequ iCheckNewsRequ)
			throws DataAccessException;

	/**
	 * 更新用户推送消息查看记录
	 * 
	 * @param iCheckNewsInfo
	 * @throws DataAccessException
	 */
	void updateCheckNewsInfo(CheckNewsInfo iCheckNewsInfo)
			throws DataAccessException;

	/**
	 * 新增用户推送消息查看记录
	 * 
	 * @param iCheckNewsRequ
	 * @throws DataAccessException
	 */
	void addCheckNewsInfo(CheckNewsRequ iCheckNewsRequ)
			throws DataAccessException;
}
