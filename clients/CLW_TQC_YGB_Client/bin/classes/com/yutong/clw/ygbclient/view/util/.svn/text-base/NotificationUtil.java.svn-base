package com.yutong.clw.ygbclient.view.util;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.view.common.NotificationListenService;

/**
 * 通知工具类
 * 
 * @author zhouzc
 * 
 */
public class NotificationUtil {
	public static final int NOTIFICATIONID_ALARM = 0x0001;
	public static final int NOTIFICATIONID_ANNOUNCE = 0x0002;
	public static final int NOTIFICATIONID_UPGRADE = 0x0001;

	/**
	 * 显示新通知
	 * 
	 * @param notificationId
	 *            通知ID
	 * @param targetActivity
	 *            点击通知后跳转的页面
	 * @param title
	 *            通知标题
	 * @param tickerText
	 *            通知滚动显示的信息
	 * @param contentText
	 *            通知停止的时候显示的静态信息
	 * @param icon
	 *            通知图标
	 * @param notisound
	 *            通知声音
	 * @param autoclear
	 *            点击后是否自动消失
	 */
	public static Notification pushNewNotification(int notificationId,
			Class<?> targetActivity, String title, String tickerText,
			String contentText, int icon, Uri notisound, boolean autoclear) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				YtApplication.getInstance());
		mBuilder.setSmallIcon(icon);
		mBuilder.setTicker(tickerText).setContentTitle(title)
				.setContentText(contentText);

		if (notisound != null)
			mBuilder.setSound(notisound);

		Intent intent = new Intent(YtApplication.getInstance(), targetActivity);
		intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(
				YtApplication.getInstance(), 0, intent, 0);
		mBuilder.setContentIntent(pendingIntent);

		NotificationManager mNotificationManager = (NotificationManager) YtApplication
				.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = mBuilder.build();
		if (autoclear)
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
		mNotificationManager.notify(notificationId, notification);
		return notification;
	}

	/**
	 * 显示新通知
	 * 
	 * @param notificationId
	 *            通知ID
	 * @param targetActivity
	 *            点击通知后跳转的页面
	 * @param data
	 *            传入数据
	 * @param title
	 *            通知标题
	 * @param tickerText
	 *            通知滚动显示的信息
	 * @param contentText
	 *            通知停止的时候显示的静态信息
	 * @param icon
	 *            通知图标
	 * @param notisound
	 *            通知声音
	 * @param autoclear
	 *            点击后是否自动消失
	 */
	public static void pushNewNotification(int notificationId,
			Class<?> targetActivity, Bundle data, String title,
			String tickerText, String contentText, int icon, Uri notisound,
			boolean autoclear, boolean bringtofront) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				YtApplication.getInstance());
		mBuilder.setSmallIcon(icon);
		mBuilder.setTicker(tickerText).setContentTitle(title)
				.setContentText(contentText);

		if (notisound != null)
			mBuilder.setSound(notisound);

		Intent intent = new Intent(YtApplication.getInstance(), targetActivity);
		if (bringtofront)
			intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		if (data != null)
			intent.putExtras(data);
		PendingIntent pendingIntent = PendingIntent.getActivity(
				YtApplication.getInstance(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		mBuilder.setContentIntent(pendingIntent);

		Intent deleteIntent = new Intent(YtApplication.getInstance(),
				NotificationListenService.class);
		deleteIntent.putExtras(data);
		PendingIntent delPendingIntent = PendingIntent.getService(
				YtApplication.getInstance(), 0, deleteIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setDeleteIntent(delPendingIntent);

		NotificationManager mNotificationManager = (NotificationManager) YtApplication
				.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = mBuilder.build();
		if (autoclear)
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
		if (notisound == null)
			notification.flags |= Notification.DEFAULT_SOUND;
		mNotificationManager.notify(notificationId, notification);
	}

	/**
	 * 清除通知
	 * 
	 * @param notiid
	 *            通知ID
	 */
	public static void removeNotification(int notiid) {
		NotificationManager notificationManager = (NotificationManager) YtApplication
				.getInstance().getSystemService(
						Application.NOTIFICATION_SERVICE);
		notificationManager.cancel(notiid);
	}

	public static void removeAllNotifications() {
		removeNotification(NOTIFICATIONID_ALARM);
		removeNotification(NOTIFICATIONID_ANNOUNCE);
		removeNotification(NOTIFICATIONID_UPGRADE);
	}

}
