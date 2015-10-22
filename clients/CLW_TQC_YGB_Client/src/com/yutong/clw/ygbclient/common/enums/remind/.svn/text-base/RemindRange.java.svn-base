package com.yutong.clw.ygbclient.common.enums.remind;

import com.yutong.clw.ygbclient.common.enums.AreaType;

/**
 * 提醒范围
 * 
 * @author zhangzhia 2013-10-21 下午2:09:27
 */
public enum RemindRange
{
	/**
	 * 仅站点提醒,0
	 */
	OnlyStation(0, "仅站点提醒"),
	/**
	 * 站点与车辆提醒,1
	 */
	StationAndVehiche(1, "站点与车辆提醒");

	private int _value;

	private String _name;

	private RemindRange(int value, String name)
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

	public static RemindRange myValueOf(int value)
	{
	    switch (value) {
        case 0:
            return OnlyStation;
        case 1:
            return StationAndVehiche;
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
	}
}
