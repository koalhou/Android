/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 下午6:09:39
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.prefs;

import android.content.Context;

import com.yutong.clw.ygbclient.common.constant.GlobleConstants;
import com.yutong.clw.ygbclient.common.utils.PreferencesUtils;

/**
 * @author zhangzhia 2013-10-25 下午6:09:39
 */
public class SettingPrefsUtils
{

    /** Context对象 */
    private Context context;

    /** Preferences名称 */
    public static final String PREFS_NAME = GlobleConstants.PREFS_NAME_SETTING;

    /**
     * 保存数据到配置文件
     * 
     * @param etag
     */
    public static void putString(Context context, String key, String data)
    {
        PreferencesUtils.putString(context, PREFS_NAME, key, data);
    }

    /**
     * 从配置文件获取数据
     * 
     * @return
     */
    public static String getString(Context context, String key)
    {
        return PreferencesUtils.getString(context, PREFS_NAME, key);
    }

    public static String getString(Context context, String key, String defaultValue)
    {
        return PreferencesUtils.getString(context, PREFS_NAME, key, defaultValue);
    }

    public static boolean getBoolean(Context context, String key)
    {
        return PreferencesUtils.getBoolean(context, PREFS_NAME, key);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue)
    {
        return PreferencesUtils.getBoolean(context, PREFS_NAME, key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value)
    {
        PreferencesUtils.putBoolean(context, PREFS_NAME, key, value);
    }
    
    public static long getLong(Context context, String key, long defaultValue)
    {
        return PreferencesUtils.getLong(context, PREFS_NAME, key, defaultValue);
    }

    public static void putLong(Context context, String key, long value)
    {
        PreferencesUtils.putLong(context, PREFS_NAME, key, value);
    }
    
    public static int getInt(Context context, String key, int defaultValue)
    {
        return PreferencesUtils.getInt(context, PREFS_NAME, key, defaultValue);
    }

    public static void putInt(Context context, String key, int value)
    {
        PreferencesUtils.putInt(context, PREFS_NAME, key, value);
    }
    
    public static void remove(Context context)
    {
        PreferencesUtils.remove(context, PREFS_NAME);
    }
    
    public static void remove(Context context, String module, String exkey)
    {
        PreferencesUtils.remove(context, PREFS_NAME, module + exkey);
    }
}
