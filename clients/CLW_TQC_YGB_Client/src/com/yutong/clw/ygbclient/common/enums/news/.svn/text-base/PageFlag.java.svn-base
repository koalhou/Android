package com.yutong.clw.ygbclient.common.enums.news;

import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;

/**
 * 分页标志
 * @author zhangzhia 2013-10-21 下午1:54:56
 *
 */
public enum PageFlag
{
    /**
     * 第一页,0
     */
    First(0, "第一页"),
    /**
     * 向下翻页,1
     */
    Next(1, "向下翻页"),
    /**
     * 向上翻页,2
     */
    Previous(2, "向上翻页");

    private int _value;

    private String _name;

    private PageFlag(int value, String name)
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
    public static PageFlag myValueOf(int value)
	{
        switch (value) {
        case 0:
            return First;
        case 1:
            return Next;
        case 2:
            return Next;    
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
	}
}
