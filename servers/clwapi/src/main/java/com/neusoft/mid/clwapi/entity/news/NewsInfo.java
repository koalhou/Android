/**
 * @(#)NewsInfo.java 2013-4-13
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.news;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-13 上午11:35:28
 */
public class NewsInfo {

	/**
	 * 消息ID
	 */
	@XmlElement(name = "news_id")
	private String newsId;
	/**
	 * 消息标题
	 */
	@XmlElement(name = "news_title")
	private String newsTitle;
	/**
	 * 消息概述信息
	 */
	@XmlElement(name = "news_summary")
	private String newsSummary;
	/**
	 * 消息发布时间。格式：yyyymmddhh24miss
	 */
	@XmlElement(name = "news_time")
	private String newsTime;
	/**
	 * 消息类型
	 */
	@XmlElement(name = "news_type")
	private String newsType;

	/**
	 * @return Returns the newsId.
	 */
	public String getNewsId() {
		return newsId;
	}

	/**
	 * @param newsId
	 *            The newsId to set.
	 */
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	/**
	 * @return Returns the newsTitle.
	 */
	public String getNewsTitle() {
		return newsTitle;
	}

	/**
	 * @param newsTitle
	 *            The newsTitle to set.
	 */
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	/**
	 * @return Returns the newsSummary.
	 */
	public String getNewsSummary() {
		return newsSummary;
	}

	/**
	 * @param newsSummary
	 *            The newsSummary to set.
	 */
	public void setNewsSummary(String newsSummary) {
		this.newsSummary = newsSummary;
	}

	/**
	 * @return Returns the newsTime.
	 */
	public String getNewsTime() {
		return newsTime;
	}

	/**
	 * @param newsTime
	 *            The newsTime to set.
	 */
	public void setNewsTime(String newsTime) {
		this.newsTime = newsTime;
	}

	/**
	 * @return Returns the newsType.
	 */
	public String getNewsType() {
		return newsType;
	}

	/**
	 * @param newsType
	 *            The newsType to set.
	 */
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NewsInfo [newsId=" + newsId + ", newsTitle=" + newsTitle
				+ ", newsSummary=" + newsSummary + ", newsTime=" + newsTime
				+ ", newsType=" + newsType + "]";
	}
}
