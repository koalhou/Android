package com.yutong.clw.ygbclient.common.enums.other;

/**
 * 过滤条件
 * 
 * @author zhangzhia 2013-10-21 下午1:16:47
 */
public enum FilterEnum
{
    /**
     * 0, 一厂厂内，二厂厂内
     */
    Value11002100(0, "1100#2100"),
    /**
     * 1 一厂厂外，二厂厂外
     */
    Value10102010(1, "1010#2010"),
    /**
     * 2 一厂厂间,二厂厂间
     */
    Value10012001(2, "1001#2001");

    private int _value;

    private String _name;

    private FilterEnum(int value, String name)
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

    public static FilterEnum myValueOf(int value)
    {
        switch (value)
        {
        case 0:
            return Value11002100;
        case 1:
            return Value10102010;
        case 2:
            return Value10012001;
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
    }
}
