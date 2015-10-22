package com.yutong.clw.ygbclient.common.enums.remind;

/**
 * 提醒方式
 * 
 * @author zhangzhia 2013-10-21 下午2:03:04
 */
public enum RemindType
{
	/**
	 * 按时间提醒,0
	 */
	Date(0, "按时间提醒"),
	/**
	 * 按距离提醒,1
	 */
	Distance(1, "按距离提醒"),
	/**
	 * 按之前站数提醒,2
	 */
	StationNum(2, "按之前站数提醒");

	private int _value;

	private String _name;

	private RemindType(int value, String name)
	{
		_value = value;
		_name = name;
	}

	public int value()
	{
		return _value;
	}

	public String getName()
	{
		return _name;
	}

	public static RemindType myValueOf(int value)
	{
	    switch (value) {
        case 0:
            return Date;
        case 1:
            return Distance;
        case 2:
            return StationNum;
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
	}
}
