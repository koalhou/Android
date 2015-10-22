package com.yutong.clw.ygbclient.common.enums;

/**
 * 车辆到达状态
 * 
 * @author zhangzhia 2013-10-21 下午2:12:35
 */
public enum ArriveStatus
{
    /**
     * 未进站,0
     */
    NoArrive(0, "未进站"),
    /**
     * 已进站,1
     */
    Arrive(1, "已进站"),
    /**
     * 已出站,2
     */
    Leave(2, "已出站"),
    /**
     * 未发车,3
     */
    NoStartOff(3, "未发车"),
    // /**
    // * 行程结束,4
    // */
    // TravelEnd(4, "行程结束");
    /**
     * 已发生,5
     */
    StartOff(5, "已发车");

    private int _value;

    private String _name;

    private ArriveStatus(int value, String name)
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

    public static ArriveStatus myValueOf(int value)
    {
        switch (value)
        {
        case 0:
            return NoArrive;
        case 1:
            return Arrive;
        case 2:
            return Leave;
        case 3:
            return NoStartOff;
            // case 4:
            // return Leave;
        case 5:
            return StartOff;
        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
    }
}
