package com.yutong.clw.ygbclient.common.enums.news;

import com.yutong.clw.ygbclient.common.enums.SexType;

/**
 * 下翻是否还有数据状态
 * 
 * @author zhangzhia 2013-10-21 下午1:54:56
 * 
 */
public enum HasNextStatus
{
	/**
	 * 往下翻页没有数据,0
	 */
	NoData(0, "往下翻页没有数据"),
	/**
	 * 往下翻页表示还有数据,1
	 */
	HasData(1, "往下翻页表示还有数据");

	private int _value;

	private String _name;

	private HasNextStatus(int value, String name)
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

	public static HasNextStatus myValueOf(int value)
	{
	    switch (value) {
        case 0:
            return NoData;
        case 1:
            return HasData;
            
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
	    }
	}

}
