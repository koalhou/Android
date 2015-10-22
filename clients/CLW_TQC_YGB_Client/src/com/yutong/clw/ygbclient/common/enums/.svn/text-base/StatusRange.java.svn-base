package com.yutong.clw.ygbclient.common.enums;

/**
 * 早班晚班状态
 * 
 * @author zhangzhia 2013-10-21 下午1:47:40
 * 
 */
public enum StatusRange
{
    /**
     * 全部,-1
     */
    AllWork(-1, "全部"),
	/**
	 * 早班,0
	 */
    MorningWork(0, "早班"),
	/**
	 * 晚班,1
	 */
    NightWork(1, "晚班");

	private int _value;

	private String _name;

	private StatusRange(int value, String name)
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

	public static StatusRange myValueOf(int value)
	{
	    switch (value) {
	    case -1:
            return AllWork;
        case 0:
            return MorningWork;
        case 1:
            return NightWork;
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
	}
}
