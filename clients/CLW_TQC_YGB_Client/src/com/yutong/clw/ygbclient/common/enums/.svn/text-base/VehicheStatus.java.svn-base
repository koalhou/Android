package com.yutong.clw.ygbclient.common.enums;

import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;

/**
 * 车辆实时状态
 * 
 * @author zhangzhia 2013-10-21 下午2:12:35
 */
public enum VehicheStatus
{
    /**
     * 离线,0
     */
    Offline(0, "离线"),
    /**
     * 在线,1
     */
    Online(1, "在线"),
    /**
     * 停驶,2
     */
    Stop(2, "停驶"),
    /**
     * 行驶,3
     */
    Travel(3, "行驶"),
    /**
     * 点火,4
     */
    Fire(4, "点火"),
    /**
     * 熄火,5
     */
    NoFire(5, "熄火");

    private int _value;

    private String _name;

    private VehicheStatus(int value, String name)
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

    /**
     * 查询车辆状态
     *@author zhangzhia 2013-11-6 下午2:18:50
     *
     * @param value     车辆状态值
     * @param status    待查询状态
     * @return  true:属于当前状态，false：不属于当前状态
     */
    public static boolean IsVehicheStatus(String value, VehicheStatus status)
    {
        final int line_Index = 2;
        final int Travel_Index = 3;
        final int Fire_Index = 4;

        char[] statusArray = value.toCharArray();

        boolean blStauts = false;
        switch (status)
        {
        case Offline:
            blStauts = !DataTypeConvertUtils.int2Boolean(Integer.parseInt(String.valueOf(statusArray[line_Index])));
            break;
        case Online:
            blStauts = DataTypeConvertUtils.int2Boolean(Integer.parseInt(String.valueOf(statusArray[line_Index])));
            break;
        case Stop:
            blStauts = !DataTypeConvertUtils.int2Boolean(Integer.parseInt(String.valueOf(statusArray[Travel_Index])));
            break;
        case Travel:
            blStauts = DataTypeConvertUtils.int2Boolean(Integer.parseInt(String.valueOf(statusArray[Travel_Index])));
            break;
        case Fire:
            blStauts = !DataTypeConvertUtils.int2Boolean(Integer.parseInt(String.valueOf(statusArray[Fire_Index])));
            break;
        case NoFire:
            blStauts = DataTypeConvertUtils.int2Boolean(Integer.parseInt(String.valueOf(statusArray[Fire_Index])));
            break;
        }

        return blStauts;
    }
}
