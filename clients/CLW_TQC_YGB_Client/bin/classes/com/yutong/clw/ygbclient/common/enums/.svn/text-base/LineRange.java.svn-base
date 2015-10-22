package com.yutong.clw.ygbclient.common.enums;

/**
 * 厂区路线范围
 * 
 * @author zhangzhia 2013-10-21 下午1:48:04
 * 
 */
public enum LineRange
{
	/**
	 * 厂内,1
	 */
	FactoryInside(1, "厂内"),
	/**
	 * 厂外,2
	 */
	FactoryOuter(2, "厂外"),
	/**
	 * 厂间,3
	 */
	FactoryBetween(3, "厂间");

	private int _value;

	private String _name;

	private LineRange(int value, String name)
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

	public static LineRange myValueOf(int value)
	{
	    switch (value) {
        case 1:
            return FactoryInside;
        case 2:
            return FactoryOuter;
        case 3:
            return FactoryBetween;
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
	}
}
