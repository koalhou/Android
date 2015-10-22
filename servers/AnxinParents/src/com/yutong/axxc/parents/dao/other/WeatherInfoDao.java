
package com.yutong.axxc.parents.dao.other;

import android.content.Context;

import com.yutong.axxc.parents.common.SharedPreferencesUtil;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 天气信息数据操作类
 * @author zhangzhia 2013-9-2 下午3:04:03
 *
 */
public class WeatherInfoDao {
    /** Context，用于操作数据库 */
    private Context context;

    /** Preferences名称 */
    private static final String PREFS_NAME = SharedPreferencesUtil.PREFS_NAME_CACHE;

    private static final String KEY_PREFIX = "weather_info";
    
    public WeatherInfoDao(Context context) {
        this.context = context;
    }

    /**
     * 缓存天气信息
     * @param userID 用户ID
     * @param weatherInfo 要保存的注册信息对象
     */
    public void cacheWeatherInfo(String weatherInfo) {     
        SharedPreferencesUtil.set(PREFS_NAME, KEY_PREFIX + YtApplication.getInstance().getUid(), weatherInfo);
    }

    /**
     * 获取天气缓存信息
     * @return userID 用户ID
     */
    public String getCachedWeatherInfo() {
   
        return SharedPreferencesUtil.get(PREFS_NAME, KEY_PREFIX + YtApplication.getInstance().getUid());
    }

}
