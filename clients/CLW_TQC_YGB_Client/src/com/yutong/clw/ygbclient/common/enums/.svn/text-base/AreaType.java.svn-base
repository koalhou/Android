package com.yutong.clw.ygbclient.common.enums;

/**
 * 所属厂区
 * 
 * @author zhangzhia 2013-10-21 下午1:16:47
 * 
 */
public enum AreaType
{
    /**
     * 所有厂区,-1
     */
    AllFactory(-1, "所有厂区"),
	/**
	 * 宇通工业园,1
	 */
	FirstFactory(1, "第一工厂"),
	/**
	 * 新能源工厂,2
	 */
	SecondFactory(2, "第二工厂");

	private int _value;

	private String _name;

	private AreaType(int value, String name)
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

	public static AreaType myValueOf(int value)
	{  
		
	    switch (value) {
        case -1:
            return AllFactory;
        case 1:
            return FirstFactory;
        case 2:
            return SecondFactory;
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
	}
}
