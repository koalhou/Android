package com.yutong.clw.ygbclient.common.enums;

public enum BaseEnum
{

	/**
	 * 宇通工业园,1
	 */
	SimpleEnum(1, "宇通工业园");
	// 键
	private int _value;
	// 值
	private String _name;

	private BaseEnum(int value, String name)
	{
		_value = value;
		_name = name;
	}

	// 获得实体
	public static BaseEnum valueOf(int value)
	{
	    switch (value) {
        case 1:
            return SimpleEnum;
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
	}
}
