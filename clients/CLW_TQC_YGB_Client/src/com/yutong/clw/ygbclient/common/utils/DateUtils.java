package com.yutong.clw.ygbclient.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期操作类
 * 
 * @author zhangyongn
 */
public class DateUtils
{
    /** 日期格式-yyyyMMdd */
    public static final String TIME_FORMAT = "yyyyMMdd";

    /** 刷新日期格式- MM-dd HH:mm */
    public static final String REFRESH_TIME_FORMAT = "MM-dd HH:mm";

    /** 日期格式-yyyy-MM-dd */
    public static final String TIME_FORMAT_WITH_SPLIT = "yyyy-MM-dd";

    public static final String LONG_TIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * 判断日期是否是今天
     * 
     * @param cal
     * @return
     */
    public static boolean isToday(Date date)
    {
        String nowday = DateUtils.getFormatTime(Calendar.getInstance(), "yyyyMMdd");
        String curday = DateUtils.dateToStr(date, "yyyyMMdd");
        return nowday.equals(curday);
    }

    /**
     * 判断日期是否为昨天
     * 
     * @author zhangzhia 2013-11-12 上午11:31:51
     * @param cal
     * @return
     */
    public static boolean isYesterday(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        Date yesterdaydt = calendar.getTime();

        String yesterday = DateUtils.dateToStr(yesterdaydt, "yyyyMMdd");
        String curday = DateUtils.dateToStr(date, "yyyyMMdd");
        return yesterday.equals(curday);
    }

    /**
     * 获取年份。比如:2013-11-22,得到的：2013
     * 
     * @author zhangyongn 2013-11-14 下午1:12:42
     * @param date
     * @return
     */
    public static int getYear(Date date)
    {
        return date.getYear() + 1900;
    }

    /**
     * 获取月份。比如：2013-11-22，得到：11
     * 
     * @author zhangyongn 2013-11-14 下午1:14:24
     * @param date
     * @return
     */
    public static int getMonth(Date date)
    {
        return date.getMonth() + 1;
    }

    /**
     * 获取月份中的日。比如：2013-11-22,得到：22
     * 
     * @author zhangyongn 2013-11-14 下午1:16:19
     * @param date
     * @return
     */
    public static int getDate(Date date)
    {
        return date.getDate();
    }

    /**
     * 从秒转化为分钟，比如：传入60，得到1.
     * 
     * @author zhangyongn 2013-11-14 下午2:43:05
     * @param seconds 秒数
     * @return
     */
    public static double convert2Minutes(int seconds)
    {
        double ret = seconds / 60;
        return ret;
    }

    /**
     * 日期转字符串
     * 
     * @param calendar
     * @param timeformat 时间格式字符串.
     * @return 格式化时间yyyyMMdd
     */
    public static String getFormatTime(Calendar calendar, String timeformat)
    {
        return getFormatTime(calendar.getTime(), timeformat);
    }

    /**
     * 日期转字符串
     * 
     * @param calendar
     * @param timeformat 时间格式字符串.
     * @return 格式化时间yyyyMMdd
     */
    public static String getFormatTime(Date dt, String timeformat)
    {

        SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
        return sdf.format(dt);
    }

    /**
     * 字符串转化成时间
     * 
     * @param strDate
     * @param format 时间字符串格式。比如：yyyy-MM-dd
     * @return
     */
    public static Date strToDate(String strDate, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 时间转化成字符串
     * 
     * @param strDate
     * @param format 时间字符串格式。比如：yyyy-MM-dd
     * @return
     */
	public static String dateToStr(Date date, String format)
    {
    	if(null==date) return "";
    	try{
        SimpleDateFormat formatter = new SimpleDateFormat(format,Locale.getDefault());
        return formatter.format(date);
    	}catch(Exception e){
    		e.printStackTrace();
    		return "";
    	}
    }

    /**
     * 当前日期
     * 
     * @return
     */
    public static Date getCurDate()
    {
        Calendar cal = Calendar.getInstance();
        Date d = cal.getTime();
        return d;
    }

    /**
     * 当前不同格式的日期日期
     * 
     * @return
     */
    public static Date getCurDate(String formatStr)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
        try
        {
            return sdf.parse(sdf.format(calendar.getTime()));
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断时间是否过期
     * 
     * @param codetime
     * @param validSeconds 有效时间
     * @return
     */
    public static boolean checkTimeExpired(Date time, int validSeconds)
    {
        try
        {
            if (time == null)
                return false;
            DateFormat timedf = new SimpleDateFormat("yyyyMMddHHmmss");
            String timecheck = timedf.format(time);
            String timenow = timedf.format(Calendar.getInstance().getTime());
            long secdiff = getDifferenceTime(timenow, timecheck);
            return secdiff > validSeconds;
        }
        catch (Exception e)
        {

            return false;
        }

    }

    /**
     * 时间格式转换
     * 
     * @param timevalue ，时间值
     * @param fromformat ，时间值对应的格式
     * @param toformat ，转换后的时间值格式。
     * @return 转换后的时间值。
     */
    public static String getFormatTime(String timevalue, String fromformat, String toformat)
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat(fromformat);
        SimpleDateFormat sdf2 = new SimpleDateFormat(toformat);
        try
        {
            return sdf2.format(sdf1.parse(timevalue));
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 日期是否在给定时间范围内，精确到天
     * 
     * @param temp
     * @param minCal
     * @param maxCal
     * @return
     */
    public static boolean DayBetween(Calendar temp, Calendar minCal, Calendar maxCal)
    {
        int minYear = minCal.get(Calendar.YEAR);
        int minMonth = minCal.get(Calendar.MONTH);
        int minDate = minCal.get(Calendar.DATE);

        int nowYear = maxCal.get(Calendar.YEAR);
        int nowMonth = maxCal.get(Calendar.MONTH);
        int nowDate = maxCal.get(Calendar.DATE);

        int tempYear = temp.get(Calendar.YEAR);
        int tempMonth = temp.get(Calendar.MONTH);
        int tempDate = temp.get(Calendar.DATE);

        if (tempYear < minYear || tempYear > nowYear)
        {
            return false;
        }
        else if ((tempYear == minYear && tempMonth < minMonth) || (tempYear == nowYear && tempMonth > nowMonth))
        {
            return false;
        }
        else if ((tempYear == minYear && tempMonth == minMonth && tempDate < minDate)
                || (tempYear == nowYear && tempMonth == nowMonth && tempDate > nowDate))
        {
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
    public static boolean DayBetween(Calendar temp, Calendar minCal)
    {
        return DayBetween(temp, minCal, Calendar.getInstance());
    }

    /**
     * 获取时间差-单位秒
     * 
     * @author zhangzhia 2013-9-24 下午5:38:34
     * @param startTime 开始时间yyyyMMddHHmmss
     * @param endTime 结束时间yyyyMMddHHmmss
     * @return
     * @throws ParseException
     */
    public static long getDifferenceTime(String startTime, String endTime) throws ParseException
    {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

        Date d1 = df.parse(startTime);
        Date d2 = df.parse(endTime);
        long diff = d1.getTime() - d2.getTime();
        return diff / 1000;
        // long days = diff / (1000 * 60 * 60 * 24);

    }
}
