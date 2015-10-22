package com.yutong.clw.ygbclient.common.utils;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.ClientInfo;

/**
 * 系统信息获取工具类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public final class SysInfoGetUtil
{
    /**
     * 获取IMSI
     * 
     * @param context
     * @return
     */
    public static String getIMSI(Context context)
    {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    /**
     * 获取本地版本信息
     * 
     * @param context
     * @return
     */
    public static String getPackageInfo(Context context)
    {
        PackageInfo packageInfo = null;
        try
        {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        }
        catch (NameNotFoundException e)
        {
            Logger.e(SysInfoGetUtil.class, "[设备信息获取工具类]：获取当前版本信息异常，详细信息：", e);
        }
        return packageInfo == null ? "1" : packageInfo.versionName;
    }

    /**
     * 获取本地版本号
     * 
     * @param context
     * @return
     */
    public static int getVersionCode(Context context)
    {
        PackageInfo packageInfo = null;
        try
        {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        }
        catch (NameNotFoundException e)
        {
            Logger.e(SysInfoGetUtil.class, "[设备信息获取工具类]：获取当前版本信息异常，详细信息：", e);
        }
        return packageInfo == null ? 0 : packageInfo.versionCode;
    }
    
    /**
     * 获取客户端信息
     *@author zhangzhia 2013-10-21 下午3:41:41
     *
     * @param context
     * @return
     */
    public static ClientInfo getClientInfoBean(Context context)
    {
        ClientInfo clientInfoBean = new ClientInfo();
        clientInfoBean.setApp_version(getPackageInfo(context));
        clientInfoBean.setOs_name("android");

        clientInfoBean.setOs_version(StringUtils.isNotBlank(android.os.Build.VERSION.RELEASE) ? android.os.Build.VERSION.RELEASE : "other");

        return clientInfoBean;
    }
}
