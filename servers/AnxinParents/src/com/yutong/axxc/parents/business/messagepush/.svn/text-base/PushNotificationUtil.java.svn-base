package com.yutong.axxc.parents.business.messagepush;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.common.CommonPushMsgUtil;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 推送通知栏工具类
 * 
 * @author zhangzhia 2013-9-3 上午8:54:52
 * 
 */
public final class PushNotificationUtil {

	private static String logTypeName = "[推送通知栏工具类]：";

	private PushNotificationUtil() {
	}

	/**
	 * 乘车通知ID
	 */
	private static final int ridingNotificationId = 966424231;

	/**
	 * 系统通知ID
	 */
	private static final int systemNotificationId = ridingNotificationId + 1;

	/**
	 * 初始化通知数，每次启动时初始化
	 */
	public static void initNotificationCount() {
		clearAllNotification();
	}

	/**
	 * 初始化通知数
	 */
	public static void initRidingNotificationCount() {
		NotificationManager mNotificationManager = (NotificationManager) YtApplication
				.getInstance().getSystemService(
						Application.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(ridingNotificationId);
	}

	/**
	 * 初始化通知数
	 */
	public static void initSystemNotificationCount() {
		NotificationManager mNotificationManager = (NotificationManager) YtApplication
				.getInstance().getSystemService(
						Application.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(systemNotificationId);
	}

	/**
	 * 乘车通知
	 * 
	 * @param targetActivity
	 *            要启动的Activity
	 */
	public static void ridingNotification(Context context,
			Class<?> targetActivity, String currentMsg,int msgtype) {
		int newsNotificationCount = CommonPushMsgUtil
				.getRidingNotificationCount();

		String content = "您有" + newsNotificationCount + "条未查看的乘车提醒消息";
		Logger.i(PushNotificationUtil.class, logTypeName + "乘车推送提示");
		notification(ridingNotificationId, context, targetActivity, currentMsg,
				msgtype, content);
	}
	
	

	/**
	 * 系统通知
	 * 
	 * @param targetActivity
	 *            要启动的Activity
	 */
	public static void newsNotification(Context context,
			Class<?> targetActivity, String currentMsg) {
		int alertNotificationCount = CommonPushMsgUtil
				.getNewsNotificationCount();
		String content = "您有" + alertNotificationCount + "条未查看的新闻消息";
		Logger.i(PushNotificationUtil.class, logTypeName + "新闻消息推送提示");
		notification(systemNotificationId, context, targetActivity, currentMsg,
				PushConstant.SYSTEM_NEWS_TYPE, content);

	}

	private static Uri getSoundUriByNotificationType(int type) {
		// 这边是默认声音配置
		Uri suri = null;

		switch (type) {
		case PushConstant.SYSTEM_NEWS_TYPE:
			// TODO 配置路径 新闻提醒声
			suri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
					+ YtApplication.getInstance().getPackageName()
					+ "/raw/notifynews");

		case PushConstant.RIDDING_CARD_GETON:
			// TODO 配置路径 上学刷卡
			suri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
					+ YtApplication.getInstance().getPackageName()
					+ "/raw/notifyridding");
		case PushConstant.RIDDING_CARD_GETOFF:
			// TODO 配置路径 放学刷卡
			suri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
					+ YtApplication.getInstance().getPackageName()
					+ "/raw/notifyridding");
		case PushConstant.RIDDING_STATION_ATTEND:
			// TODO 配置路径 上学到站
			suri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
					+ YtApplication.getInstance().getPackageName()
					+ "/raw/notifyridding");
		case PushConstant.RIDDING_STATION_LETOUT:
			// TODO 配置路径 放学到站
			suri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
					+ YtApplication.getInstance().getPackageName()
					+ "/raw/notifyridding");
		default:
			break;
		}
		
		return suri;
	}

	/**
	 * 通知
	 * 
	 * @param notificationId
	 *            通知ID
	 * @param targetActivity
	 *            要启动的Activity
	 * @param tickerText
	 *            通知内容
	 */
	private static void notification(int notificationId, Context context,
			Class<?> targetActivity, String tickerText, int notificationType,
			String imMsg) {
		Logger.i(PushNotificationUtil.class, logTypeName + "notificationId:",
				notificationId);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.launcher)
				.setTicker(tickerText).setContentTitle("安芯校车提示")
				.setContentText(imMsg);

		Uri suri = getSoundUriByNotificationType(notificationType);
		if (suri != null) {
			mBuilder.setSound(suri);
		} else {
			mBuilder.setDefaults(Notification.DEFAULT_SOUND);
		}

		Intent intent = new Intent(context, targetActivity);
		intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		mBuilder.setContentIntent(pendingIntent);

		Intent deleteIntent = new Intent(context, PushNumInitService.class);
		deleteIntent.putExtra(PushConstant.PUSH_TYPE, notificationType);
		PendingIntent delPendingIntent = PendingIntent.getService(context, 0,
				deleteIntent, 0);
		mBuilder.setDeleteIntent(delPendingIntent);

		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		Notification notification = mBuilder.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// notification.sound = RingtoneManager
		// .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		mNotificationManager.notify(notificationId, notification);
	}

	/**
	 * 清除所有通知
	 */
	public static void clearAllNotification() {
		// 启动后删除之前我们定义的通知
		try {
			NotificationManager notificationManager = (NotificationManager) YtApplication
					.getInstance().getSystemService(
							Application.NOTIFICATION_SERVICE);
			notificationManager.cancel(ridingNotificationId);
			notificationManager.cancel(systemNotificationId);

		} catch (Exception e) {
			Logger.e(PushNotificationUtil.class,
					logTypeName + "取消所有通知出错，详细信息:", e);
		}
	}
}
