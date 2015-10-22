package com.yutong.clw.ygbclient.view.common;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Vector;

import android.os.Process;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.view.pages.BaseActivity;
import com.yutong.clw.ygbclient.view.pages.login.LoginActivity;
import com.yutong.clw.ygbclient.view.pages.main.betweenFactory.BetweenFactoryActivity;
import com.yutong.clw.ygbclient.view.pages.main.inFactory.InFactoryActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.OutOfFactoryActivity;
import com.yutong.clw.ygbclient.view.pages.setting.SettingActivity;

/**
 * Activity管理类
 * 
 * @author
 * @version $Revision 1.1 $ 2012-6-21 下午12:20:35
 */
public final class ActivityManager {

	private static final List<WeakReference<BaseActivity>> references = new Vector<WeakReference<BaseActivity>>();

	private ActivityManager() {

	}

	/**
	 * 添加Activity到容器中
	 * 
	 * @param activity
	 */
	public static void addActivity(WeakReference<BaseActivity> reference) {
		references.add(reference);
	}

	public static boolean hasLoadMainActivity() {

		for (WeakReference<BaseActivity> reference : references) {
			if (reference.get() == null)
				continue;
			if (reference.get().getClass().equals(InFactoryActivity.class)
					|| reference.get().getClass()
							.equals(BetweenFactoryActivity.class)
					|| reference.get().getClass()
							.equals(OutOfFactoryActivity.class)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 添加Activity到容器中
	 * 
	 * @param activity
	 */
	public static BaseActivity getOneActivity() {
		for (WeakReference<BaseActivity> reference : references) {
			if (reference.get() != null) {
				return reference.get();
			}
		}
		return null;
	}

	/**
	 * 遍历所有Activity并finish
	 */
	public static void exit() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				releaseAllResource();
			}
		}).start();

	}

	/**
	 * 遍历所有Activity并finish
	 */
	public static void exitApp() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				closeAllActivity();
				clearReciverAndNotification();
				// new LogoutBiz().logout();
				killProcess();
			}
		}).start();
	}

	/**
	 * 释放所有资源
	 */
	public static void releaseAllResource() {
		closeAllActivity();
		clearReciverAndNotification();
		killProcess();
	}

	/**
	 * 杀进程
	 */
	private static void killProcess() {
		// MessageManager.getInstance().stopService(YtApplication.getInstance());
		Process.killProcess(Process.myPid());
	}

	/**
	 * 清理广播接收和提示
	 */
	public static void clearReciverAndNotification() {
		// PushNotificationUtil.clearAllNotification();
		// ReceiverUtil.unRegisterPushReceiver();
	}

	/**
	 * 关闭所有的activity
	 */
	private static void closeAllActivity() {
		try {
			Logger.i(Logger.class, "[Activity管理类]:references size:",
					references.size());
			for (WeakReference<BaseActivity> reference : references) {
				if (reference.get() != null) {
					Logger.i(Logger.class,
							"[Activity管理类]:finsh activity class:", reference
									.get().getClass());
					reference.get().finish();
				}
			}
		} catch (Exception e) {
			Logger.d(Logger.class, "[Activity管理类]:finsh activity exception :",
					e);
		}

	}

	/**
	 * 关闭所有的activity
	 */
	public static void closeOtherActivityExpectSetting() {
		try {
			Logger.i(Logger.class, "[Activity管理类]:references size:",
					references.size());
			for (WeakReference<BaseActivity> reference : references) {
				if (reference.get() != null
						&& !(reference.get() instanceof SettingActivity)) {
					Logger.i(Logger.class,
							"[Activity管理类]:finsh activity class:", reference
									.get().getClass());
					reference.get().finish();
				}
			}
		} catch (Exception e) {
			Logger.d(Logger.class, "[Activity管理类]:finsh activity exception :",
					e);
		}

	}

	public static void closeOtherActivityExpectLogin() {
		try {
			Logger.i(Logger.class, "[Activity管理类]:references size:",
					references.size());
			for (WeakReference<BaseActivity> reference : references) {
				if (reference.get() != null
						&& !(reference.get() instanceof LoginActivity)) {
					Logger.i(Logger.class,
							"[Activity管理类]:finsh activity class:", reference
									.get().getClass());
					reference.get().finish();
				}
			}
		} catch (Exception e) {
			Logger.d(Logger.class, "[Activity管理类]:finsh activity exception :",
					e);
		}

	}

	public static void closeSettingActivity() {
		try {
			Logger.i(Logger.class, "[Activity管理类]:references size:",
					references.size());
			for (WeakReference<BaseActivity> reference : references) {
				if (reference.get() != null
						&& reference.get() instanceof SettingActivity) {
					Logger.i(Logger.class,
							"[Activity管理类]:finsh activity class:", reference
									.get().getClass());
					reference.get().finish();
				}
			}
		} catch (Exception e) {
			Logger.d(Logger.class, "[Activity管理类]:finsh activity exception :",
					e);
		}

	}

	/**
	 * 输出所有的activity
	 */
	public static void outputAllActivity() {
		Logger.i(Logger.class, "[Activity管理类]:references size:",
				references.size());
		for (WeakReference<BaseActivity> reference : references) {
			if (reference.get() != null) {
				Logger.i(Logger.class, "[Activity管理类]:alive activity class:",
						reference.get().getClass());
			}
		}
	}

	/**
	 * 释放所有资源回到登录界面
	 */
	public static void releaseAllResourceAndGoLogin() {
		closeAllActivity();
		// new
		// LogoutBiz(YtApplication.getInstance().getApplicationContext()).clearCurrentAcountLoginInfo();
		// Intent intent = new
		// Intent(YtApplication.getInstance().getApplicationContext(),
		// LoginActivity.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// YtApplication.getInstance().startActivity(intent);

	}

	/**
	 * 是否在前台运行
	 * 
	 * @author zhouzc 2013-12-5 上午9:55:13
	 * 
	 * @return
	 */
	public static boolean isActive() {
		for (WeakReference<BaseActivity> reference : references) {
			if (reference.get() != null && !reference.get().isActive()) {
				return false;
			}
		}
		return true;
	}
}
