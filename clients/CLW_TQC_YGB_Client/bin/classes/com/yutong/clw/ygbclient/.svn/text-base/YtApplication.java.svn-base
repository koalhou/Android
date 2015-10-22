package com.yutong.clw.ygbclient;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.ReceiverUtil;
import com.yutong.clw.ygbclient.business.receiver.NetworkReceiver;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.OnNetworkChangeListener;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.NetworkCheckUitl;
import com.yutong.clw.ygbclient.common.utils.PreferencesUtils;
import com.yutong.clw.ygbclient.view.bizAccess.BizThrownableRouter;
import com.yutong.clw.ygbclient.view.bizAccess.BizThrownableRouter.OnRecieveCommonExceptionListener;
import com.yutong.clw.ygbclient.view.bizAccess.login.BizLogout;
import com.yutong.clw.ygbclient.view.common.ActivityCoverManager;
import com.yutong.clw.ygbclient.view.common.ActivityManager;
import com.yutong.clw.ygbclient.view.common.AppCrashHandler;
import com.yutong.clw.ygbclient.view.common.BaiduMapManager;
import com.yutong.clw.ygbclient.view.common.MediaManager;
import com.yutong.clw.ygbclient.view.common.NotificationListenService;
import com.yutong.clw.ygbclient.view.common.NotificationListenService.NotificationServiceBinder;
import com.yutong.clw.ygbclient.view.common.PushMessageRouter;
import com.yutong.clw.ygbclient.view.common.VersionUpdateManager;
import com.yutong.clw.ygbclient.view.pages.login.LoginActivity;
import com.yutong.clw.ygbclient.view.pages.main.inFactory.InFactoryActivity;
import com.yutong.clw.ygbclient.view.util.NotificationUtil;

/**
 * 应用程序管理类
 * 
 * @author zhouzc
 */
public final class YtApplication extends Application {

	/* 请不要再此类中添加私有变量 ，有特殊需求的请讨论过后再添加 */

	private static volatile YtApplication mInstance = null;

	public YtApplication() {
		mInstance = this;
	}

	/**
	 * 全局变量，用于存储一些应用程序级别的变量。
	 */
	private HashMap<String, Object> appDatas;

	/**
	 * 根据键取全局变量，通过AppDataKeyConstant类定义的key操作
	 * 
	 * @author zhangyongn 2013-10-31 下午2:46:46
	 * @param key
	 * @return
	 */
	public Object getDatas(String key) {
		if (appDatas.containsKey(key)) {
			return appDatas.get(key);
		}
		return null;
	}

	/**
	 * 添加全局变量，通过AppDataKeyConstant类定义的key操作
	 * 
	 * @author zhangyongn 2013-10-31 下午2:48:25
	 * @param key
	 * @param obj
	 */
	public void addDatas(String key, Object obj) {
		appDatas.put(key, obj);
	}

	/**
	 * 移除全局变量，通过AppDataKeyConstant类定义的key操作
	 * 
	 * @author zhangyongn 2013-10-31 下午2:48:35
	 * @param key
	 */
	public void removeDatas(String key) {
		if (appDatas.containsKey(key)) {
			appDatas.remove(key);
		}
	}

	/**
	 * 清除全局变量
	 * 
	 * @author zhangyongn 2013-10-31 下午2:49:08
	 */
	public void clearDatas() {
		appDatas.clear();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		appDatas = new HashMap<String, Object>();

		AppCrashHandler handler = AppCrashHandler.getInstance();
		handler.init(getApplicationContext());
		getBaiduMapManager().initial();
		registerNetworkReceiver();
		registerExceptionListener();
		getNotificationListenServiceBinder();
		Logger.d(getClass(), "当前系统版本："
				+ getAppInitConfigs().getAndroidSDKVersion());
		
		initImageLoader(getApplicationContext());
	}

	public static YtApplication getInstance() {
		if (mInstance == null) {
			synchronized (YtApplication.class) {
				if (mInstance == null) {
					mInstance = new YtApplication();
				}
			}
		}
		return mInstance;
	}

	private ActivityCoverManager activityCoverManager;

	private VersionUpdateManager versionUpdateManager;

	private BaiduMapManager baiduMapManager;

	private AppInitConfigs appInitConfigs;

	private MediaManager mediaManager;

	private BizThrownableRouter bizThrownableRouter;

	private PushMessageRouter pushMessageRouter;

	/**
	 * 获取覆盖引导层管理实例
	 * 
	 * @return
	 */

	public ActivityCoverManager getActivityCoverManager() {
		if (activityCoverManager == null)
			activityCoverManager = new ActivityCoverManager();
		return activityCoverManager;
	}

	/**
	 * 获取版本升级管理实例
	 * 
	 * @return
	 */
	public VersionUpdateManager getVersionUpdateManager() {
		if (versionUpdateManager == null)
			versionUpdateManager = new VersionUpdateManager();
		return versionUpdateManager;
	}

	/**
	 * 获百度地图管理实例
	 * 
	 * @return
	 */
	public BaiduMapManager getBaiduMapManager() {
		if (baiduMapManager == null)
			baiduMapManager = new BaiduMapManager();
		return baiduMapManager;
	}

	/**
	 * 获取基础设置实例
	 * 
	 * @author zhouzc 2013-11-1 上午9:49:30
	 * @return
	 */
	public AppInitConfigs getAppInitConfigs() {
		if (appInitConfigs == null)
			appInitConfigs = new AppInitConfigs(this);
		return appInitConfigs;
	}

	/**
	 * 获取音频播放实例
	 * 
	 * @author zhouzc 2013-11-13 下午2:36:48
	 * @return
	 */
	public MediaManager getMediaManager() {
		if (mediaManager == null)
			mediaManager = new MediaManager(this);
		return mediaManager;
	}

	/**
	 * 获取公共异常路由
	 * 
	 * @author zhouzc 2013-11-14 上午11:50:42
	 * @return
	 */
	public BizThrownableRouter getBizThrownableRouter() {
		if (bizThrownableRouter == null) {
			bizThrownableRouter = new BizThrownableRouter();
		}
		return bizThrownableRouter;
	}

	/**
	 * 获取消息转发路由
	 * 
	 * @author zhouzc 2013-11-19 下午4:26:22
	 * 
	 * @return
	 */
	public PushMessageRouter getPushMessageRouter() {
		if (pushMessageRouter == null)
			pushMessageRouter = new PushMessageRouter();
		return pushMessageRouter;
	}

	/**
	 * 登录成功后，统一做的一些事情。比如：注册消息推送，注册网络侦听广播。
	 * 
	 * @author zhangyongn 2013-11-18 下午2:24:30
	 */
	public void onLoginSuccess() {
		// 注册推送服务
		registerPushReceiver();
	}

	/**
	 * 在注销登录前，统一做的一些事情。
	 * 
	 * @author zhangyongn 2013-11-18 下午3:25:30
	 */
	public void onLogout() {
		// TO DO:注销一些服务
		ProxyManager.getInstance(this).clearLoginInfo();
		
		unRegisterNetworkReceiver();
		unRegisterPushReceiver();
		NotificationUtil.removeAllNotifications();
		/* 注销登录，关闭GPS设备 */
		if (NetworkCheckUitl.isGpsEnable(getApplicationContext())) {
			closeGps();
		}
		
		/*运营时间置空*/
		PreferencesUtils.putBoolean(getApplicationContext(), InFactoryActivity.YUN_YING_NAME, InFactoryActivity.YUN_YING_KEY, false);
		
		NotificationManager mNotificationManager = (NotificationManager) YtApplication
		.getInstance().getSystemService(
				Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancelAll();
	}
	
	public void onLogout2() {
		// TO DO:注销一些服务
		unRegisterNetworkReceiver();
		unRegisterPushReceiver();
		NotificationUtil.removeAllNotifications();
		/* 注销登录，关闭GPS设备 */
		if (NetworkCheckUitl.isGpsEnable(getApplicationContext())) {
			closeGps();
		}
		
		/*运营时间置空*/
		PreferencesUtils.putBoolean(getApplicationContext(), InFactoryActivity.YUN_YING_NAME, InFactoryActivity.YUN_YING_KEY, false);
		
		NotificationManager mNotificationManager = (NotificationManager) YtApplication
		.getInstance().getSystemService(
				Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancelAll();
	}

	/**
	 * 退出应用程序。整个app的退出应用程序，统一调用该方法。
	 * 
	 * @author zhangyongn 2013-11-8 下午2:38:50
	 */
	public void exit() {
		// TO DO:注销一些服务
		NotificationUtil.removeAllNotifications();
		unregisterExceptionListener();
		unRegisterPushReceiver();
		ActivityManager.exitApp();
		unRegisterNetworkReceiver();
		/* 退出登录，关闭GPS设备 */
		if (NetworkCheckUitl.isGpsEnable(getApplicationContext())) {
			closeGps();
		}
		
		NotificationManager mNotificationManager = (NotificationManager) YtApplication
		.getInstance().getSystemService(
				Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancelAll();
	}

	/**
	 * 注销。整个app的注销动作，统一调用该方法
	 */
	public void logout() {

		onLogout();
		BizLogout biz = new BizLogout(this);
		biz.logout(null);

		ActivityManager.closeOtherActivityExpectSetting();

		Intent intent = new Intent(YtApplication.getInstance().getApplicationContext(), LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		Bundle bundle = new Bundle();
		/*UserInfo user = (UserInfo) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_USER);*/
		/*bundle.putSerializable(AppDataKeyConstant.KEY_USER, "1");*/
		bundle.putBoolean(AppDataKeyConstant.EXIT_FLAG,true);
		
		intent.putExtras(bundle);

		startActivity(intent);
		ActivityManager.closeSettingActivity();
	}

	@Override
	public void onTerminate() {
		NotificationUtil.removeAllNotifications();
		super.onTerminate();
	}

	/**************************** 网络相关 ********************************/
	private NetworkReceiver networkReceiver;

	List<OnNetworkChangeListener> networklistener = new ArrayList<OnNetworkChangeListener>();

	public NetworkReceiver getNetworkReceiver() {
		return networkReceiver;
	}

	public void setNetworkReceiver(NetworkReceiver networkReceiver) {
		this.networkReceiver = networkReceiver;
	}

	public void unRegisterNetworkReceiver() {

		try {

			Logger.i(this.getClass(), "注销网络监测服务");
			ReceiverUtil.unRegisterNetworkReceiver();

		} catch (Exception e) {
		}
	}

	public void registerNetworkReceiver() {
		try {

			Logger.i(this.getClass(), "注册网络监测服务");
			ReceiverUtil.registerNetworkReceiver();

		} catch (Exception e) {
		}

	}

	public void clearOnNetworkChangeListeners() {
		networklistener.clear();
	}

	public void addOnNetworkChangeListener(OnNetworkChangeListener listener) {
		if (listener == null)
			return;
		synchronized (networklistener) {
			networklistener.add(listener);
		}
	}

	public void removeOnNetworkChangeListener(OnNetworkChangeListener listener) {
		if (listener == null)
			return;
		synchronized (networklistener) {
			networklistener.remove(listener);
		}
	}

	public void trigerOnNetworkChangeListener(boolean isconnected) {
		if (networklistener == null)
			return;

		if (networklistener.size() == 0)
			return;
		try {
			synchronized (networklistener) {
				for (OnNetworkChangeListener listener : networklistener) {

					listener.NetworkChanged(isconnected);
				}
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}

	/* 关闭GPS设备 */
	private void closeGps() {

		/*
		 * Settings.Secure.setLocationProviderEnabled( getContentResolver(),
		 * LocationManager.GPS_PROVIDER, false ); Logger.i(getClass(),
		 * "GPS 设备已经关闭");
		 */

		Intent gpsIntent = new Intent();
		gpsIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
		gpsIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(this, 0, gpsIntent, 0).send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}
	}

	/**************************** 网络相关 ********************************/

	/**************************** 异常消息start **********************************/
	OnRecieveCommonExceptionListener onRecieveCommonExceptionListener;

	private void registerExceptionListener() {
		if (onRecieveCommonExceptionListener == null) {
			onRecieveCommonExceptionListener = new OnRecieveCommonExceptionListener() {
				@Override
				public void OnRecieve(CommonException exception) {
					if (exception.status == CommonNetStatus.Token_InValid) {
						onLogout();
					}
				}
			};
			onRecieveCommonExceptionListener.setTag("From " + getClass());
		}
		getBizThrownableRouter().addOnRecieveCommonExceptionListener(
				onRecieveCommonExceptionListener);
	}

	private void unregisterExceptionListener() {
		if (onRecieveCommonExceptionListener != null) {
			getBizThrownableRouter().removeOnRecieveCommonExceptionListener(
					onRecieveCommonExceptionListener);
		}
	}

	/**************************** 异常消息end **********************************/

	/**************************** 推送消息start **********************************/

	/**
	 * 注册push广播接收类
	 */
	private void registerPushReceiver() {
		Logger.i(getClass(), "开始注册到推送服务");
		ReceiverUtil.registerPushReceiver();
	}

	/**
	 * 取消push广播接收
	 */
	private void unRegisterPushReceiver() {
		Logger.i(getClass(), "开始从推送服务注销");
		ReceiverUtil.unRegisterPushReceiver();
	}

	/**************************** 推送消息end **********************************/

	/**************************** 通知侦听服务 start **********************************/

	private NotificationServiceBinder notificationServiceBinder;

	public NotificationServiceBinder getNotificationListenServiceBinder() {
		if (notificationServiceBinder != null)
			return notificationServiceBinder;
		Logger.i(getClass(), "开始绑定通知消息侦听服务");
		Intent intent = new Intent(this, NotificationListenService.class);
		ServiceConnection conn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Logger.i(getClass(), "绑定通知消息侦听服务完成");
				if (service instanceof NotificationServiceBinder)
					notificationServiceBinder = (NotificationServiceBinder) service;
			}
		};
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
		return notificationServiceBinder;
	}

	/**************************** 通知侦听服务 end **********************************/
	public static void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache");
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024) // 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.discCache(new UnlimitedDiscCache(cacheDir))
				.build();
		
		ImageLoader.getInstance().init(config);
	}
	
}
