/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-8 上午11:23:15
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.common;

import java.lang.reflect.Type;

import java.util.List;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.constant.GlobleConstants;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.prefs.SettingPrefsUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;

/**
 * @author zhangzhia 2013-11-8 上午11:23:15
 */
public class PrefDataUtil {
	/**
	 * 设置厂区设置
	 */
	public static void setFactory(Context context, AreaType data) {
		String set = data.value() + "";
		SettingPrefsUtils.putString(context, ProxyManager.getInstance(context)
				.getUserCode() + GlobleConstants.SHARED_FACTORYSET, set);
		
	}

	/**
	 * 获取厂区设置
	 * 
	 * @return
	 */
	public static AreaType getFactory(Context context) {
		try {
			String v = SettingPrefsUtils.getString(context, ProxyManager
					.getInstance(context).getUserCode()
					+ GlobleConstants.SHARED_FACTORYSET);
			if (StringUtil.isBlank(v))
				return AreaType.FirstFactory;
			int data = Integer.parseInt(v);
			return AreaType.myValueOf(data);
		} catch (Exception err) {
			Logger.e(null, err.getMessage());
			return AreaType.FirstFactory;
		}
	}

	/**
	 * 设置站点搜索历史数据
	 */
	public static void setStationSearchHistory(Context context,
			List<StationInfo> stations) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<StationInfo>>() {
		}.getType();

		SettingPrefsUtils.putString(context, ProxyManager.getInstance(context)
				.getUserCode() + GlobleConstants.SHARED_STATION_SREACH_HISTORY,
				gson.toJson(stations, type));
	}

	/**
	 * 获取站点搜索历史数据
	 * 
	 * @return
	 */
	public static List<StationInfo> getStationSearchHistory(Context context) {
		try {
			String data = SettingPrefsUtils.getString(context, ProxyManager
					.getInstance(context).getUserCode()
					+ GlobleConstants.SHARED_STATION_SREACH_HISTORY);
			if (StringUtil.isBlank(data)) {
				Gson gson = new Gson();
				Type type = new TypeToken<List<StationInfo>>() {
				}.getType();

				return gson.fromJson(data, type);
			} else {
				return null;
			}

		} catch (Exception err) {
			Logger.e(null, err.getMessage());
			return null;
		}
	}

	/**
	 * 设置地图是否自动刷新
	 * 
	 * @author zhouzc 2013-11-13 上午9:11:19
	 * @param context
	 * @param need
	 */
	public static void setAutoMapRefresh(Context context, boolean need) {
		SettingPrefsUtils.putBoolean(context, ProxyManager.getInstance(context)
				.getUserCode() + GlobleConstants.SHARED_MAP_AUTO_FLUSH, need);
	}

	/**
	 * 获取地图是否自动刷新
	 * 
	 * @author zhouzc 2013-11-13 上午9:11:22
	 * @param context
	 * @return
	 */
	public static boolean getAutoMapRefresh(Context context) {
		return SettingPrefsUtils.getBoolean(context,
				ProxyManager.getInstance(context).getUserCode()
						+ GlobleConstants.SHARED_MAP_AUTO_FLUSH, true);
	}

	/**
	 * 设置地图刷新频率
	 * 
	 * @author zhouzc 2013-11-13 上午9:09:07
	 * @param context
	 * @param interval
	 */
	public static void setMapRefreshInterval(Context context, long interval) {
		SettingPrefsUtils.putLong(context, ProxyManager.getInstance(context)
				.getUserCode() + GlobleConstants.SHARED_MAP_FLUSH_TIME,
				interval);
	}

	/**
	 * 获取地图刷新频率
	 * 
	 * @author zhouzc 2013-11-13 上午9:09:20
	 * @param context
	 * @return
	 */
	public static long getMapRefreshInterval(Context context) {
		return SettingPrefsUtils.getLong(context,
				ProxyManager.getInstance(context).getUserCode()
						+ GlobleConstants.SHARED_MAP_FLUSH_TIME, 5);
	}

	/**
	 * 设置提醒铃声名称
	 * 
	 * @author zhouzc 2013-11-13 上午10:07:22
	 * @param context
	 * @param name
	 */
	public static void setRemindRingName(Context context, String name) {
		SettingPrefsUtils.putString(context, ProxyManager.getInstance(context)
				.getUserCode() + GlobleConstants.SHARED_REMIND_RING_NAME, name);
	}

	/**
	 * 获取提醒铃声名称
	 * 
	 * @author zhouzc 2013-11-13 上午10:07:31
	 * @param context
	 * @return
	 */
	public static String getRemindRingName(Context context) {
		return SettingPrefsUtils.getString(context,
				ProxyManager.getInstance(context).getUserCode()
						+ GlobleConstants.SHARED_REMIND_RING_NAME, "默认");
	}

	/**
	 * 设置提醒铃声
	 * 
	 * @author zhouzc 2013-11-13 上午9:07:25
	 * @param context
	 * @param path
	 */
	public static void setRemindRingUri(Context context, Uri path) {
		if (path == null) {
			return;
		}
		SettingPrefsUtils.putString(context, ProxyManager.getInstance(context)
				.getUserCode() + GlobleConstants.SHARED_REMIND_RING_URI,
				path.getPath());
	}

	/**
	 * 获取提醒铃声
	 * 
	 * @author zhouzc 2013-11-13 上午9:07:59
	 * @param context
	 * @return
	 */
	public static Uri getRemindRingUri(Context context) {
		String path = SettingPrefsUtils.getString(context, ProxyManager
				.getInstance(context).getUserCode()
				+ GlobleConstants.SHARED_REMIND_RING_URI);
		if (path == null)
			return null;
		else {
			Uri ss = Uri.parse(path);
			return ss;
		}
	}

	/**
	 * 设置提醒铃声音量
	 * 
	 * @author zhouzc 2013-11-13 上午9:08:11
	 * @param context
	 * @param volume
	 */
	public static void setRemindRingVolume(Context context, int volume) {
		SettingPrefsUtils.putInt(context, ProxyManager.getInstance(context)
				.getUserCode() + GlobleConstants.SHARED_REMIND_RING_VOLUME,
				volume);
	}

	/**
	 * 获取提醒铃声音量
	 * 
	 * @author zhouzc 2013-11-13 上午9:08:26
	 * @param context
	 * @return
	 */
	public static int getRemindRingVolume(Context context) {
		return SettingPrefsUtils.getInt(context,
				ProxyManager.getInstance(context).getUserCode()
						+ GlobleConstants.SHARED_REMIND_RING_VOLUME, 5);
	}

	/**
	 * 设置提醒是否需要震动
	 * 
	 * @author zhouzc 2013-11-13 上午9:08:34
	 * @param context
	 * @param need
	 */
	public static void setRemindVibrate(Context context, boolean need) {
		SettingPrefsUtils.putBoolean(context, ProxyManager.getInstance(context)
				.getUserCode() + GlobleConstants.SHARED_REMIND_VIBERATE, need);
	}

	/**
	 * 获取提醒是否需要震动
	 * 
	 * @author zhouzc 2013-11-13 上午9:08:45
	 * @param context
	 * @return
	 */
	public static boolean getRemindVibrate(Context context) {
		return SettingPrefsUtils.getBoolean(context,
				ProxyManager.getInstance(context).getUserCode()
						+ GlobleConstants.SHARED_REMIND_VIBERATE, false);
	}

	/**
	 * 是否第一次登录app
	 * 
	 * @author zhangyongn 2013-11-15 下午3:14:42
	 * @param context
	 * @return
	 */
	public static boolean getIsFirstLogin(Context context) {
		return SettingPrefsUtils.getBoolean(context,
				GlobleConstants.SHARED_IS_FIRST_USE_SYS, true);
	}

	/**
	 * 设置是否第一次登录app
	 * 
	 * @author zhouzc 2013-11-13 上午9:08:34
	 * @param context
	 * @param isfirstlogin
	 */
	public static void setIsFirstLogin(Context context, boolean isfirstlogin) {
		SettingPrefsUtils.putBoolean(context,
				GlobleConstants.SHARED_IS_FIRST_USE_SYS, isfirstlogin);
	}

	/**
	 * 获取引导页是否显示
	 * 
	 * @author zhouzc 2013-11-28 上午9:53:40
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getCoverHasShown(Context context, String key) {
		return SettingPrefsUtils.getBoolean(context,
				GlobleConstants.SHARED_IS_COVER_HASSHOWN + key);
	}

	/**
	 * 设置引导页是否显示
	 * 
	 * @author zhouzc 2013-11-28 上午9:53:56
	 * 
	 * @param context
	 * @param key
	 * @param hasShown
	 */
	public static void setCoverHasShown(Context context, String key,
			boolean hasShown) {
		SettingPrefsUtils.putBoolean(context,
				GlobleConstants.SHARED_IS_COVER_HASSHOWN + key, hasShown);
	}
}
