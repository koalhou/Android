package com.yutong.clw.ygbclient.utils;

import com.yutong.clw.ygbclient.YtApplication;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;


public class SystemInfoUtil {
	
	public static String getVersionName() {
		String version = "";
		// 获取packagemanager的实例
		try {
			PackageManager packageManager =  YtApplication.getInstance().getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo;
			packInfo = packageManager.getPackageInfo(YtApplication.getInstance().getPackageName(), 0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
}
