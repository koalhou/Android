package com.yutong.clw.ygbclient.common.enums;

/**
 * 超时类型
 * 
 * @author zhangzhia 2013-10-21 下午1:16:47
 * 
 */
public enum TimeoutMethod
{
    /**
     * 正常超时,0
     */
    Normal(0, "正常超时"),
	/**
	 * 自然天,1
	 */
    NaturalDay(1, "自然天"),
	/**
	 * 自然月,2
	 */
    NaturalMonth(2, "自然月");

	private int _value;

	private String _name;

	private TimeoutMethod(int value, String name)
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

	public static TimeoutMethod myValueOf(int value)
	{  
	    switch (value) {
        case 0:
            return Normal;
        case 1:
            return NaturalDay;
        case 2:
            return NaturalMonth;
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
	}
}
