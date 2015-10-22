/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-24 下午4:46:46
 * @功能描述：
 */
package com.yutong.clw.ygbclient.unit;

import junit.framework.TestCase;

import android.test.AndroidTestCase;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.login.LoginBiz;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.AreaType;

/**
 * @author zhangzhia 2013-10-24 下午4:46:46
 */
public class test extends AndroidTestCase
{
    LoginBiz loginBiz = new LoginBiz(YtApplication.getInstance().getApplicationContext(), "11", "11");

    /**
     * 数据转换
     * 
     * @author lizyi 2013-11-19 下午3:38:31
     * @param cacheItem
     * @return
     */
    private String enum2Str(Enum enumItem)
    {
        return enumItem.toString();
    }

    private Enum str2Enum(Class<? extends Enum> enumClazz, String enum_str) throws InstantiationException, IllegalAccessException
    {
        // return enumClazz.cast(enum_str);
        return Enum.valueOf(enumClazz, enum_str);
    }

    public void testEnum() throws InstantiationException, IllegalAccessException
    {
        String enum2Str = enum2Str(AreaType.SecondFactory);
        System.out.println(enum2Str);
        Enum str2Enum = str2Enum(AreaType.class, enum2Str);
        System.out.println(11);
    }
}
