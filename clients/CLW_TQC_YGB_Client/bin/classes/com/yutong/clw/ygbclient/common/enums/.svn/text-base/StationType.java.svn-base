package com.yutong.clw.ygbclient.common.enums;

/**
 * 站点类型
 * 
 * @author zhangzhia 2013-10-21 下午1:50:45
 * 
 */
public enum StationType
{
	/**
	 * 起点,1
	 */
	StartStaion(1, "起点"),
	/**
	 * 终点,2
	 */
	EndStaion(2, "终点"),
	/**
	 * 普通站点,3
	 */
	NormalStaion(3, "普通站点");

	private int _value;

	private String _name;

	private StationType(int value, String name)
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

	public static StationType myValueOf(int value)
	{
	    switch (value) {
        case 1:
            return StartStaion;
        case 2:
            return EndStaion;
        case 3:
            return NormalStaion;
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
	}
}
