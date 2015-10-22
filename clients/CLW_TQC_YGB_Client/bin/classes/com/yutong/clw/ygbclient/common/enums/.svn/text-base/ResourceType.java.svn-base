package com.yutong.clw.ygbclient.common.enums;

/**
 * 路由资源类型
 * 
 * @author zhangzhia 2013-10-21 下午1:16:47
 */
public enum ResourceType
{
    /**
     * URL,1
     */
    NetURL(1, "URL"),
    /**
     * 服务器资源id,2
     */
    Resource(2, "resId");

    private int _value;

    private String _name;

    private ResourceType(int value, String name)
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

    public static ResourceType myValueOf(int value)
    {
        switch (value)
        {
        case 1:
            return NetURL;
        case 2:
            return Resource;
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
    }
}
