/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:08:36
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans.news;

import java.io.Serializable;

import com.yutong.clw.ygbclient.common.enums.news.NewsType;
import com.yutong.clw.ygbclient.common.enums.news.PageFlag;

/**
 * 新闻参数实体类
 * 
 * @author zhangzhia 2013-10-22 下午3:08:36
 */
public class NewsParam implements Serializable {

	/**
	 * @author zhangyongn 2013-11-7 下午6:26:28
	 * 
	 */
	private static final long serialVersionUID = 9040872166906422529L;

	public NewsType newsType;

	public PageFlag pageFlag;

	public String pageTime;

	public int req_num;

	public String last_id;

	public NewsType getNewsType() {
		return newsType;
	}

	public void setNewsType(NewsType newsType) {
		this.newsType = newsType;
	}

	public PageFlag getPageFlag() {
		return pageFlag;
	}

	public void setPageFlag(PageFlag pageFlag) {
		this.pageFlag = pageFlag;
	}

	public String getPageTime() {
		return pageTime;
	}

	public void setPageTime(String pageTime) {
		this.pageTime = pageTime;
	}

	public int getReq_num() {
		return req_num;
	}

	public void setReq_num(int req_num) {
		this.req_num = req_num;
	}

	public String getLast_id() {
		return last_id;
	}

	public void setLast_id(String last_id) {
		this.last_id = last_id;
	}

}
