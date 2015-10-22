/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:29:55
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.enums;

/**
 * 推送消息类型
 * 
 * @author zhangzhia 2013-10-21 下午1:16:47
 */
public enum MsgPushType
{
    /**
     * 未知类型,0
     */
    Unknow(0, "00"),
    /**
     * 新闻推送,1
     */
    News(1, "01"),
    /**
     * 厂内提醒推送,2
     */
    FactoryInside(2, "02"),
    /**
     * 厂外提醒推送,3
     */
    FactoryOuter(3, "03"),
    
    /**
     * 意见反馈,5
     */
    Feedback(5, "05");
    
    
    private int _value;

    private String _name;

    private MsgPushType(int value, String name)
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

    public static MsgPushType myValueOf(int value)
    {

        switch (value)
        {
        case 1:
            return News;
        case 2:
            return FactoryInside;
        case 3:
            return FactoryOuter;
        case 5:
            return Feedback;
        default:
            return Unknow;
        }
    }

    public static MsgPushType myNameOf(String name)
    {
        if ("01".equals(name))
        {
            return News;
        }
        else if ("02".equals(name))
        {
            return FactoryInside;
        }
        else if ("03".equals(name))
        {
            return FactoryOuter;
        }
        else if ("05".equals(name))
        {
            return Feedback;
        }
        else
        {
            return Unknow;
        }
    }

}
