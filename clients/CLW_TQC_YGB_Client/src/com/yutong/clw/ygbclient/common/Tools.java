package com.yutong.clw.ygbclient.common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Base64;

/**
 * 系统工具类
 * 
 * @author zhangzhia 2013-9-11 下午5:18:28
 * 
 */
public final class Tools {
	/** 日期格式-yyyyMMdd */
	public static final String TIME_FORMAT = "yyyyMMdd";

	/** 刷新日期格式- MM-dd HH:mm */
	public static final String REFRESH_TIME_FORMAT = "MM-dd HH:mm";

	/** 日期格式-yyyy-MM-dd */
	public static final String TIME_FORMAT_WITH_SPLIT = "yyyy-MM-dd";

	public static final String LONG_TIME_FORMAT = "yyyyMMddHHmmss";

	private Tools() {
	}

	public static boolean isToday(Calendar cal) {
		String nowday = Tools.getFormatTime(Calendar.getInstance(), "yyyyMMdd");
		String curday = Tools.getFormatTime(cal, "yyyyMMdd");
		return nowday.equals(curday);
	}

	/**
	 * 字符串转化成时间
	 * 
	 * @param strDate
	 * @param format
	 *            时间字符串格式。比如：yyyy-MM-dd
	 * @return
	 */
	public static Date strToDate(String strDate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 当前日期
	 * 
	 * @return
	 */
	public static Date getCurDate() {
		Calendar cal = Calendar.getInstance();
		Date d = cal.getTime();
		return d;
	}

	/**
	 * 获取当前日期-yyyyMMdd
	 * 
	 * @return 格式化时间yyyyMMdd
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取当前时间-yyyyMMddHHmmss
	 * 
	 * @return 格式化时间yyyyMMddHHmmss
	 */
	public static String getFullCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取当前时间-yyyyMMddHHmmss
	 * 
	 * @return 格式化时间yyyyMMddHHmmss
	 */
	public static String getFullYesterdayTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR,
				calendar.get(Calendar.DAY_OF_YEAR) - 1);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取时间差-单位秒
	 * 
	 * @author zhangzhia 2013-9-24 下午5:38:34
	 * 
	 * @param startTime
	 *            开始时间yyyyMMddHHmmss
	 * @param endTime
	 *            结束时间yyyyMMddHHmmss
	 * @return
	 * @throws ParseException
	 */
	public static long getDifferenceTime(String startTime, String endTime)
			throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		Date d1 = df.parse(startTime);
		Date d2 = df.parse(endTime);
		long diff = d1.getTime() - d2.getTime();
		return diff/1000;
		// long days = diff / (1000 * 60 * 60 * 24);

		
	}

	/**
	 * 判断时间是否过期
	 * 
	 * @param codetime
	 * @param validSeconds
	 *            有效时间
	 * @return
	 */
	public static boolean checkTimeExpired(Date time, int validSeconds) {
		try {
			if (time == null)
				return false;
			DateFormat timedf = new SimpleDateFormat("yyyyMMddHHmmss");
			String timecheck = timedf.format(time);
			String timenow = timedf.format(Calendar.getInstance().getTime());
			long secdiff = getDifferenceTime(timenow,timecheck);
			return secdiff > validSeconds;
		} catch (Exception e) {
			
			return false;
		}

	}

	/**
	 * 获取当前时间-yyyyMMddHHmmss
	 * 
	 * @return 格式化时间yyyyMMddHHmmss
	 */
	public static String getOneHourAfterCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(calendar.getTimeInMillis() + 3600 * 1000);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取当前时间-yyyyMMddHHmmss
	 * 
	 * @return 格式化时间yyyyMMddHHmmss
	 */
	public static String get3MonthsAgoTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 3);
		return sdf.format(calendar.getTime());
	}

	/**
	 * yyyyMMddHHmmss转为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 *            yyyy-MM-dd HH:mm:ss
	 * @return 格式化时间yyyyMMddHHmmss
	 */
	public static String getFormatTime(String time) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf2.format(sdf1.parse(time));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 时间格式转换
	 * 
	 * @param timevalue
	 *            ，时间值
	 * @param fromformat
	 *            ，时间值对应的格式
	 * @param toformat
	 *            ，转换后的时间值格式。
	 * @return 转换后的时间值。
	 */
	public static String getFormatTime(String timevalue, String fromformat,
			String toformat) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(fromformat);
		SimpleDateFormat sdf2 = new SimpleDateFormat(toformat);
		try {
			return sdf2.format(sdf1.parse(timevalue));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取当前时间-yyyyMMdd
	 * 
	 * @param calendar
	 * @return 格式化时间yyyyMMdd
	 */
	public static String getFormatTime(Calendar calendar) {

		return getFormatTime(calendar, "yyyyMMdd");
	}

	/**
	 * 获取当前月份-yyyyMMdd
	 * 
	 * @param calendar
	 * @return 格式化时间yyyyMMdd
	 */
	public static String getFormatMonth(Calendar calendar) {

		return getFormatTime(calendar, "yyyyMM");
	}

	/**
	 * 获取当前时间
	 * 
	 * @param calendar
	 * @param timeformat
	 *            时间格式字符串.
	 * @return 格式化时间yyyyMMdd
	 */
	public static String getFormatTime(Calendar calendar, String timeformat) {
		SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取当前时间-yyyyMMddHHmmss
	 * 
	 * @param calendar
	 * @return 格式化时间yyyyMMdd
	 */
	// public static String getFormatFullTime(Calendar calendar) {
	//
	// return getFormatTime(calendar,"yyyyMMddHHmmss");
	//
	// }

	/**
	 * 获取当前时间-yyyyMMddHHmmss
	 * 
	 * @param calendar
	 * @return 格式化时间yyyyMMdd
	 */
	public static String getFullFormatTime(Calendar calendar) {

		return getFormatTime(calendar, "yyyyMMddHHmmss");
	}

	/**
	 * 获取当前时间-MM/dd
	 * 
	 * @return 格式化时间MM/dd
	 */
	public static String getTodayDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取明天时间-MM/dd
	 * 
	 * @return 格式化时间MM/dd
	 */
	public static String getTommorrowDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR,
				calendar.get(Calendar.DAY_OF_YEAR) + 1);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取后天时间-MM/dd
	 * 
	 * @return 格式化时间MM/dd
	 */
	public static String getAfterTommorrowDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR,
				calendar.get(Calendar.DAY_OF_YEAR) + 2);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取日期-MM-dd
	 * 
	 * @param dateString
	 *            -yyyyMMddHHmmss
	 * @return
	 */
	public static String getDate(String dateString) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
		try {
			return sdf2.format(sdf1.parse(dateString));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取日期-MM-dd
	 * 
	 * @param dateLong
	 * @return
	 */
	public static String getDate(Long dateLong) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
		return sdf2.format(dateLong);
	}

	/**
	 * 日期是否在给定时间范围内，精确到天
	 * 
	 * @param temp
	 * @param minCal
	 * @param maxCal
	 * @return
	 */
	public static boolean DayBetween(Calendar temp, Calendar minCal,
			Calendar maxCal) {
		int minYear = minCal.get(Calendar.YEAR);
		int minMonth = minCal.get(Calendar.MONTH);
		int minDate = minCal.get(Calendar.DATE);

		int nowYear = maxCal.get(Calendar.YEAR);
		int nowMonth = maxCal.get(Calendar.MONTH);
		int nowDate = maxCal.get(Calendar.DATE);

		int tempYear = temp.get(Calendar.YEAR);
		int tempMonth = temp.get(Calendar.MONTH);
		int tempDate = temp.get(Calendar.DATE);

		if (tempYear < minYear || tempYear > nowYear) {
			return false;
		} else if ((tempYear == minYear && tempMonth < minMonth)
				|| (tempYear == nowYear && tempMonth > nowMonth)) {
			return false;
		} else if ((tempYear == minYear && tempMonth == minMonth && tempDate < minDate)
				|| (tempYear == nowYear && tempMonth == nowMonth && tempDate > nowDate)) {
			return false;
		}
		return true;
	}

	/**
	 * 日期是否在给定时间范围内，精确到天,默认最大日期是当日
	 * 
	 * @param temp
	 * @param minCal
	 * @return
	 */
	public static boolean DayBetween(Calendar temp, Calendar minCal) {
		return DayBetween(temp, minCal, Calendar.getInstance());
	}

	/**
	 * 获取时间-HH:mm
	 * 
	 * @param dateString
	 *            -yyyyMMddHHmmss
	 * @return
	 */
	public static String getTime(String dateString) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		try {
			return sdf2.format(sdf1.parse(dateString));
		} catch (ParseException e) {
			return "";
		}
	}

	/**
	 * 获取字符串的MD5值
	 * 
	 * @param str
	 *            参与计算的字符串
	 * @return
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			Logger.e(Tools.class, e);
		}
		byte[] byteArray = messageDigest.digest();
		StringBuilder md5StrBuidler = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuidler.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5StrBuidler.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}

		return md5StrBuidler.toString().toUpperCase();
	}

	/**
	 * 将长整型数字转换为日期格式的字符串
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String convert2String(long time, String format) {
		if (time > 0l) {
			Date date = new Date(time);

			return new SimpleDateFormat(format).format(date);
		}
		return "";
	}

	/**
	 * 计算文件或目录的大小
	 * 
	 * @param filePath
	 * @return
	 */
	public static long getFileOrDirSize(String filePath)
			throws RuntimeException, IOException {
		File file = new File(filePath);
		long fileSize = 0;
		if (file.exists() && file.isFile()) {
			fileSize = file.length();
			// 文件存在而且是目录，递归遍历文件目录计算文件大小
		} else if (file.exists() && file.isDirectory()) {
			return getFileSize(file);// 递归遍历
		} else {
			throw new RuntimeException("指定文件不存在");
		}
		return fileSize;
	}

	private static long getFileSize(File file) {
		long fileSize = 0;
		File[] fileArray = file.listFiles();
		// 如果文件目录数组不为空或者length!=0,即目录为空目录
		if (fileArray != null && fileArray.length != 0) {
			// 遍历文件对象数组
			for (int i = 0; i < fileArray.length; i++) {
				File fileSI = fileArray[i];
				// 如果是目录递归遍历
				if (fileSI.isDirectory()) {
					// 递归遍历
					fileSize += getFileSize(fileSI);
				}
				// 如果是文件
				if (fileSI.isFile()) {
					fileSize += fileSI.length();
				}
			}
		} else {
			// 如果文件目录数组为空或者length==0,即目录为空目录
			fileSize = 0;
		}
		return fileSize;
	}

	/**
	 * base64编码
	 * 
	 * @author zhangzhia 2013-9-5 下午6:42:32
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToBase64(byte[] bytes) {
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	/**
	 * base64解码
	 * 
	 * @author zhangzhia 2013-9-5 下午6:43:14
	 * 
	 * @param strbase64
	 * @return
	 */
	public static byte[] base64ToBytes(String strbase64) {
		return Base64.decode(strbase64, Base64.DEFAULT);
	}

}
