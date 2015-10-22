/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-1 上午9:46:39
 * @功能描述：
 */
package com.yutong.clw.ygbclient;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

import com.yutong.clw.ygbclient.common.constant.GlobleConstants;
import com.yutong.clw.ygbclient.common.prefs.SettingPrefsUtils;

/**
 * @author zhouzc 2013-11-1 上午9:46:39
 * 
 */
public class AppInitConfigs {

	Context mContext;

	public AppInitConfigs(Context context) {
		mContext = context;
	}

	/**
	 * 初始化屏幕分辨率参数，只调用一次
	 * 
	 * @author zhangzhia 2013-10-29 下午2:24:20
	 * @param display
	 */
	public void getResolution(Display display) {
		// getWindowManager() 为active方法
		// Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);
		// float density = displayMetrics.density; // 得到密度
		float width = displayMetrics.widthPixels;// 得到宽度
		float height = displayMetrics.heightPixels;// 得到高度

		setDisplayHeight((int) width);
		setDisplayWidth((int) height);
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @author zhangzhia 2013-10-29 下午2:23:00
	 * @return
	 */
	public int getDisplayHeight() {
		return Integer.parseInt(SettingPrefsUtils.getString(mContext,
				GlobleConstants.SHARED_DISP_HEIGHT, "1280"));
	}

	/**
	 * 设置屏幕高度
	 * 
	 * @author zhangzhia 2013-10-29 下午2:22:13
	 * @param displayHeight
	 */
	public void setDisplayHeight(int displayHeight) {
		SettingPrefsUtils.putString(mContext,
				GlobleConstants.SHARED_DISP_WIDTH, "" + displayHeight);
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @author zhangzhia 2013-10-29 下午2:22:46
	 * @return
	 */
	public int getDisplayWidth() {
		return Integer.parseInt(SettingPrefsUtils.getString(mContext,
				GlobleConstants.SHARED_DISP_WIDTH, "720"));
	}

	/**
	 * 设置屏幕宽度
	 * 
	 * @author zhangzhia 2013-10-29 下午2:22:32
	 * @param displayWidth
	 */
	public void setDisplayWidth(int displayWidth) {
		SettingPrefsUtils.putString(mContext,
				GlobleConstants.SHARED_DISP_WIDTH, "" + displayWidth);
	}

	public int getAndroidSDKVersion() {
		int version;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return -1;
		}
		return version;
	}
}
