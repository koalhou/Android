package com.yutong.clw.ygbclient.common.enums;

/**
 * 性别
 * 
 * @author zhangzhia 2013-10-21 下午1:47:53
 */
public enum SexType
{

    /**
     * 未知,-1
     */
    Unknown(-1, "未知"),
    /**
     * 男,0
     */
    Male(0, "男"),
    /**
     * 女,1
     */
    Female(1, "女");
    
    private int _value;

    private String _name;

    private SexType(int value, String name)
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
    
    public static SexType myValueOf(int value)
    {  
        switch (value) {
        case -1:
            return Unknown;
        case 0:
            return Male;
        case 1:
            return Female;
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
    }
}
