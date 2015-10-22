/*******************************************************************************
 * @(#)WeatherIconUtil.java 2013-5-4
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.yutong.axxc.parents.view.common;

import org.apache.commons.lang3.StringUtils;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.common.Logger;

/**
 * 天气图片工具类
 * 
 * @author <a href="mailto:fengzht@neusoft.com">Jason Feng</a>
 * @version $Revision 1.1 $ 2013-5-4 下午4:25:51
 */
public final class WeatherIconUtil {

	/**
	 * 从URL获取本地对应图片ID
	 * 
	 * @param url
	 */
	public static int getWeatherIconID(String url) {
		Logger.d(WeatherIconUtil.class, "[天气图片工具类]:URL:", url);
		if (url == null)
			return R.drawable.nothing;
		if (StringUtils.isNotBlank(url)) {
			String name = url.substring(url.lastIndexOf("/") + 1);
			if ("0.gif".equals(name)) {
				return R.drawable.b_0;
			} else if ("1.gif".equals(name)) {
				return R.drawable.b_1;
			} else if ("2.gif".equals(name)) {
				return R.drawable.b_2;
			} else if ("3.gif".equals(name)) {
				return R.drawable.b_3;
			} else if ("4.gif".equals(name)) {
				return R.drawable.b_4;
			} else if ("5.gif".equals(name)) {
				return R.drawable.b_5;
			} else if ("6.gif".equals(name)) {
				return R.drawable.b_6;
			} else if ("7.gif".equals(name)) {
				return R.drawable.b_7;
			} else if ("8.gif".equals(name)) {
				return R.drawable.b_8;
			} else if ("9.gif".equals(name)) {
				return R.drawable.b_9;
			} else if ("10.gif".equals(name)) {
				return R.drawable.b_10;
			} else if ("11.gif".equals(name)) {
				return R.drawable.b_11;
			} else if ("12.gif".equals(name)) {
				return R.drawable.b_12;
			} else if ("13.gif".equals(name)) {
				return R.drawable.b_13;
			} else if ("14.gif".equals(name)) {
				return R.drawable.b_14;
			} else if ("15.gif".equals(name)) {
				return R.drawable.b_15;
			} else if ("16.gif".equals(name)) {
				return R.drawable.b_16;
			} else if ("17.gif".equals(name)) {
				return R.drawable.b_17;
			} else if ("18.gif".equals(name)) {
				return R.drawable.b_18;
			} else if ("19.gif".equals(name)) {
				return R.drawable.b_19;
			} else if ("20.gif".equals(name)) {
				return R.drawable.b_20;
			} else if ("21.gif".equals(name)) {
				return R.drawable.b_21;
			} else if ("22.gif".equals(name)) {
				return R.drawable.b_22;
			} else if ("23.gif".equals(name)) {
				return R.drawable.b_23;
			} else if ("24.gif".equals(name)) {
				return R.drawable.b_24;
			} else if ("25.gif".equals(name)) {
				return R.drawable.b_25;
			} else if ("26.gif".equals(name)) {
				return R.drawable.b_26;
			} else if ("27.gif".equals(name)) {
				return R.drawable.b_27;
			} else if ("28.gif".equals(name)) {
				return R.drawable.b_28;
			} else if ("29.gif".equals(name)) {
				return R.drawable.b_29;
			} else if ("30.gif".equals(name)) {
				return R.drawable.b_30;
			} else if ("31.gif".equals(name)) {
				return R.drawable.b_31;
			} else
				return R.drawable.nothing;
		}
		return R.drawable.nothing;
	}
}
