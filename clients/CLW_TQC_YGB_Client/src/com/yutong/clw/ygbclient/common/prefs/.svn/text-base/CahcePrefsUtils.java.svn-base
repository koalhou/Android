/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 上午10:53:32
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.prefs;

import android.content.Context;

import com.yutong.clw.ygbclient.common.constant.GlobleConstants;
import com.yutong.clw.ygbclient.common.utils.PreferencesUtils;

/**
 * 缓存Etag标记管理类
 * 
 * @author zhangzhia 2013-9-3 下午7:01:45
 */
public class CahcePrefsUtils
{

    /** Context对象 */
    private Context context;

    /** Preferences名称 */
    public static final String PREFS_NAME = GlobleConstants.PREFS_NAME_CACHE;

    /**
     * 保存数据到配置文件
     */
    public static void putString(Context context, String module, String exkey, String data)
    {
        PreferencesUtils.putString(context, PREFS_NAME, module + exkey, data);
    }

    /**
     * 从配置文件获取数据
     * 
     * @return
     */
    public static String getString(Context context, String module, String exkey)
    {
        return PreferencesUtils.getString(context, PREFS_NAME, module + exkey);
    }

    public static String getString(Context context, String module, String exkey, String defaultValue)
    {
        return PreferencesUtils.getString(context, PREFS_NAME, module + exkey, defaultValue);
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
