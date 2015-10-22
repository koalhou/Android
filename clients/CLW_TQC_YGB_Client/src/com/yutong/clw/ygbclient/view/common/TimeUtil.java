package com.yutong.clw.ygbclient.view.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static String getFormatDateTime(String format){
		SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
		return df.format(new Date());
	}
}
