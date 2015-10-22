/**
 * @(#)WeatherInfoResp.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.homePage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-3-26 下午2:16:36
 */
public class WeatherInfoResp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7374164964200199164L;

	/**
	 * 下次天气更新的时间
	 */
	private String time = "";
	
	/**
	 * 令牌信息可以继续使用的时长,单位ms
	 */
	private int validSeconds; 
	

	/**
	 * 天气信息，参见天气信息数据结构
	 */
	private List<WeatherInfo> info = new ArrayList<WeatherInfo>();

	/**
	 * @return Returns the time.
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time The time to set.
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return Returns the info.
	 */
	public List<WeatherInfo> getInfo() {
		return info;
	}

	/**
	 * @param info The info to set.
	 */
	public void setInfo(List<WeatherInfo> info) {
		this.info = info;
	}

	public int getValidSeconds() {
		return validSeconds;
	}

	public void setValidSeconds(int validSeconds) {
		this.validSeconds = validSeconds;
	}

}
