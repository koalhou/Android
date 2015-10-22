package com.yutong.clw.ygbclient.common.utils;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.SexType;

/**
 * 数据转换工具类
 * 
 * @author lizyi
 */
public class DataTypeConvertUtils
{
    /**
     * 整形转换为布尔值
     * 
     * @param ret
     * @return
     */
    public static boolean int2Boolean(int ret)
    {
        return ret == 1 ? true : false;
    }

    /**
     * 布尔值转换为整形
     * 
     * @param ret
     * @return
     */
    public static int boolean2Int(boolean ret)
    {
        return ret ? 1 : 0;
    }

    /**
     * 枚举转字符串
     * 
     * @author lizyi 2013-11-19 下午3:38:31
     * @param cacheItem 要转换的枚举对象
     * @return
     */
    private String enum2Str(Enum enumItem)
    {
        return enumItem.toString();
    }

    /**
     * 字符串转枚举
     * 
     * @author lizyi 2013-11-22 下午1:12:54
     * @param enumClazz 要转换的枚举类的字节码
     * @param enum_str 要抓换的枚举字符串
     * @return
     */
    private Enum str2Enum(Class<? extends Enum> enumClazz, String enum_str)
    {
        Enum enumItem = null;
        try
        {
            enumItem = Enum.valueOf(enumClazz, enum_str);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(DataTypeConvertUtils.class, "[数据转换工具类]: 枚举转字符串发生异常");
        }
        return enumItem;
    }

}
