/*******************************************************************************
 * @(#)AppCrashHandler.java 2012-6-21
 *
 * Copyright 2012 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.yutong.axxc.parents.view.common;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import com.yutong.axxc.parents.common.Logger;

/**
 * 自定义异常类
 * 
 * @author <a href="mailto:wenxw@neusoft.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2012-6-21 上午11:02:10
 */
public final class AppCrashHandler implements UncaughtExceptionHandler {
	// 需求是 整个应用程序 只有一个 MyCrash-Handler
	private static AppCrashHandler appCrashHandler;

	private Context context;

	private SimpleDateFormat dataFormat = new SimpleDateFormat(
			"yyyy-MM-dd-HH-mm-ss");

	private Thread.UncaughtExceptionHandler threadEx;

	// 1.私有化构造方法
	private AppCrashHandler() {
	}

	public static synchronized AppCrashHandler getInstance() {
		if (appCrashHandler == null)
			appCrashHandler = new AppCrashHandler();

		return appCrashHandler;

	}

	public void init(Context context) {
		this.context = context;
		threadEx = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		if (!handleException(arg1) && null != threadEx)
			threadEx.uncaughtException(arg0, arg1);
		else {
			// 干掉当前的程序
			// android.os.Process.killProcess(android.os.Process.myPid());
			// System.exit(GlobleConstants.EXIT_FAG);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Logger.e(getClass(), "error : " + e.getMessage());
			}
			ActivityManager.releaseAllResource();
		}
	}

	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}
		ex.printStackTrace();
		// 1.获取当前程序的版本号. 版本的id
		String versioninfo = getVersionInfo();
		// 2.获取手机的硬件信息.
		String mobileInfo = getMobileInfo();
		// 4.把所有的信息 还有信息对应的时间 提交到服务器
		Logger.e(AppCrashHandler.class, "[自定义异常类]:日期：",
				dataFormat.format(new Date()), "程序版本号：", versioninfo,
				"手机硬件信息：", mobileInfo, "未捕获的异常信息：", ex);
		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(context, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT)
						.show();
				Looper.loop();
			}
		}.start();
		

		return true;
	}

	/**
	 * 获取手机的硬件信息
	 * 
	 * @return
	 */
	private String getMobileInfo() {
		StringBuffer sb = new StringBuffer();
		// 通过反射获取系统的硬件信息
		try {
			Field[] fields = Build.class.getDeclaredFields();
			for (Field field : fields) {
				// 暴力反射 ,获取私有的信息
				field.setAccessible(true);
				String name = field.getName();
				String value = field.get(null).toString();
				sb.append(name + "=" + value);
				sb.append("\n");
			}
		} catch (Exception e) {
			Logger.e(AppCrashHandler.class, e);
		}
		return sb.toString();
	}

	/**
	 * 获取手机的版本信息
	 * 
	 * @return
	 */
	private String getVersionInfo() {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			return "版本号未知";
		}
	}
}
