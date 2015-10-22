package com.yutong.axxc.parents.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * Preferences配置数据工具类
 * @author zhangzhia 2013-9-8 下午3:57:12
 *
 */
public class SharedPreferencesUtil
{
    /** Context对象 */
    private Context context;
    
    /** 缓存Preferences名称 */
    public static final String PREFS_NAME_CACHE = "CachePrefs";

    /** ETag设置Preferences名称 */
    public static final String PREFS_NAME_ETAG = "ETagPrefs";
    
    /** 缓存超时设置Preferences名称 */
    public static final String PREFS_NAME_CACHE_TIMEOUT = "CacheTimeOutPrefs";
    
    /** 本地设置Preferences名称 */
    public static final String PREFS_NAME_SETTING = "SettingPrefs";
    
    /**
     * 标志设置Preferences名称
     */
    public static final String PREFS_NAME_FLAG="FlagPrefs";
    
    /**
     * 获取SharedPreferences配置
     * 
     * @author zhangzhia 2013-9-2 下午3:46:33
     * @param prefs_name Preferences名称
     * @param key 键
     * @param value 值
     */
    public static String get(String prefs_name, String key)
    {
        SharedPreferences sharedPreferences = YtApplication.getInstance().getSharedPreferences(prefs_name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }
    
    /**
     * 获取SharedPreferences配置
     * 
     * @author zhangzhia 2013-9-2 下午3:46:33
     * @param prefs_name Preferences名称
     * @param key 键
     * @param value 值
     */
    public static String get(String prefs_name, String key, String defaultValue)
    {
        SharedPreferences sharedPreferences = YtApplication.getInstance().getSharedPreferences(prefs_name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }
    
    /**
     * 新增SharedPreferences配置
     *@author zhangzhia 2013-9-2 下午3:50:19
     *
     * @param prefs_name  name Preferences名称
     * @param key   键
     * @param value  值
     */
    public static void set(String prefs_name, String key, String value)
    {
        SharedPreferences settings = YtApplication.getInstance().getSharedPreferences(prefs_name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * 清除SharedPreferences信息
     *@author zhangzhia 2013-9-2 下午4:10:26
     *
     * @param prefs_name    Preferences名称
     */
    public static void clear(String prefs_name) {
        SharedPreferences settings = YtApplication.getInstance().getSharedPreferences(prefs_name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

}
