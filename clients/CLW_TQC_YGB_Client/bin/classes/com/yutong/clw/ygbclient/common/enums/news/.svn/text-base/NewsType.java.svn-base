package com.yutong.clw.ygbclient.common.enums.news;

/**
 * 新闻类型
 * @author zhangzhia 2013-10-21 下午1:54:56
 *
 */
public enum NewsType
{
    /**
     * 全部类型,0
     */
    All(0, "全部类型"),
    /**
     * 公告消息,1
     */
    Notice(1, "公告消息");

    private int _value;

    private String _name;

    private NewsType(int value, String name)
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
    
    public static NewsType myValueOf(int value)
  	{
        switch (value) {
        case 0:
            return All;
        case 1:
            return Notice;
            
        default:
            throw new IndexOutOfBoundsException("[新闻类型]枚举数组角标越界");
        }
  	}
}
