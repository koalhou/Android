package com.yutong.axxc.parents.business.common;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.ClientInfoBean;

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

    public static ClientInfoBean getClientInfoBean(Context context)
    {
        ClientInfoBean clientInfoBean = new ClientInfoBean();
        clientInfoBean.setApp_version(getPackageInfo(context));
        clientInfoBean.setOs_name("android");

        clientInfoBean.setOs_version(StringUtils.isNotBlank(android.os.Build.VERSION.RELEASE) ? android.os.Build.VERSION.RELEASE : "other");

        return clientInfoBean;
    }
}
