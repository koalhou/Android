/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-12-5 上午11:53:34
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.common;

import java.io.File;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.widget.RemoteViews;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.VersionInfo;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.view.bean.push.AlarmPushBean;
import com.yutong.clw.ygbclient.view.bizAccess.upgrade.VersionUpgradeBiz;
import com.yutong.clw.ygbclient.view.common.VersionUpdateManager.VersionCheckStatus;
import com.yutong.clw.ygbclient.view.common.VersionUpdateManager.VersionUpdateListener;
import com.yutong.clw.ygbclient.view.common.VersionUpdateManager.VersionUpdateStatus;
import com.yutong.clw.ygbclient.view.pages.setting.about.AboutActivity;
import com.yutong.clw.ygbclient.view.pages.splash.SplashActivity;
import com.yutong.clw.ygbclient.view.util.NotificationUtil;

/**
 * @author zhouzc 2013-12-5 上午11:53:34
 * 
 */
public class NotificationListenService extends IntentService {

	VersionUpdateListener vlistener = null;

	NotificationServiceBinder binder = new NotificationServiceBinder();
	
	/**
	 * @param name
	 */
	public NotificationListenService() {
		super("NotificationListenService");

	}

	@Override
	public void onCreate() {
		
		super.onCreate();
		
		Logger.i(getClass(), "==已创建通知侦听服务");
		
		/*用于在通知栏显示下载进度*/
		vlistener = new VersionUpdateListener() {
			@Override
			public void onUpdateStatusChanged(VersionUpdateStatus status,
					int percent) {
				switch (status) {
				case Updating:
					binder.onGetUpgradeProgress(percent);
					break;
				case Success:
					binder.onGetUpgradeProgress(percent);
					break;
				case Canceled:
					binder.onGetUpgradeCanceled(percent);
					break;
				case Failed:
					binder.onGetUpgradeFailed(percent);
					break;
				default:
					break;
				}

			}

			@Override
			public void getCheckStatusChanged(VersionCheckStatus status,
					VersionInfo versioninfo) {
				// TODO Auto-generated method stub

			}
		};
		YtApplication.getInstance().getVersionUpdateManager()
				.addVersionUpdateListener(vlistener);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Logger.i(getClass(), "==已启动通知侦听服务：" + startId);
	}

	@Override
	public void onDestroy() {
		Logger.i(getClass(), "==开始销毁通知侦听服务");
		YtApplication.getInstance().getVersionUpdateManager()
				.removeVersionUpdateListener(vlistener);
		super.onDestroy();
	}

	

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent.getExtras() != null) {
			if (intent.getExtras().containsKey(ActivityCommConstant.PUSHBEAN)) {
				Logger.i(getClass(), "==收到通知删除广播");
				if (intent.getExtras().getSerializable(
						ActivityCommConstant.PUSHBEAN) instanceof AlarmPushBean) {
					binder.onDeleteAlarmNotification((AlarmPushBean) intent
							.getExtras().getSerializable(
									ActivityCommConstant.PUSHBEAN));
				}
			}
		}
	}

	public static class NotificationServiceBinder extends Binder {
		
		private Handler mHandler;
		
		protected void stopViberate() {
			if (!PrefDataUtil.getRemindVibrate(YtApplication.getInstance()))
				return;
			Vibrator v = (Vibrator) YtApplication.getInstance()
					.getSystemService(Context.VIBRATOR_SERVICE);
			v.cancel();
		}
		
		@SuppressLint("HandlerLeak")
		public NotificationServiceBinder() {
			
			mHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					switch (msg.what) {
					case 0:
						if (!ActivityManager.isActive()) {
							// 当应用不在最前，提醒通知被删除的时候，停止音乐播放
							YtApplication.getInstance().getMediaManager()
									.StopPlay();
							stopViberate();
						}
						break;
					case MSGWHAT_NOTI_UPGRADE_PROGRESS: {
						int percent = msg.arg1;
						if (percent < 100) {
							NotificationManager mNotificationManager = (NotificationManager) YtApplication
									.getInstance().getSystemService(
											Context.NOTIFICATION_SERVICE);
							mNotificationManager.notify(
									NotificationUtil.NOTIFICATIONID_UPGRADE,
									createNewUpgradeNotification(percent));
						} else {
							NotificationManager mNotificationManager = (NotificationManager) YtApplication
									.getInstance().getSystemService(
											Context.NOTIFICATION_SERVICE);
							mNotificationManager.notify(
									NotificationUtil.NOTIFICATIONID_UPGRADE,
									createNewUpgradeNotification(percent));
							{
								NotificationUtil
										.removeNotification(NotificationUtil.NOTIFICATIONID_UPGRADE);
							}
						}
					}
						break;
					case MSGWHAT_NOTI_UPGRADE_FAILED: {
						int percent = msg.arg1;
						NotificationManager mNotificationManager = (NotificationManager) YtApplication
								.getInstance().getSystemService(
										Context.NOTIFICATION_SERVICE);
						mNotificationManager.notify(
								NotificationUtil.NOTIFICATIONID_UPGRADE,
								createUpgradeFailedNotification(percent));

						// NotificationUtil
						// .removeNotification(NotificationUtil.NOTIFICATIONID_UPGRADE);
					}
						break;
					case MSGWHAT_NOTI_UPGRADE_CANCELED: {
						NotificationUtil
								.removeNotification(NotificationUtil.NOTIFICATIONID_UPGRADE);
					}
						break;
					default:
						break;
					}
				}
			};
		}

		

		private static final int MSGWHAT_NOTI_UPGRADE_PROGRESS = 0X001;
		private static final int MSGWHAT_NOTI_UPGRADE_FAILED = 0X002;
		private static final int MSGWHAT_NOTI_UPGRADE_CANCELED = 0X003;

		public void onDeleteAlarmNotification(AlarmPushBean bean) {
			mHandler.obtainMessage(0, bean).sendToTarget();
		}

		public void onGetUpgradeProgress(int percent) {
			Logger.i(getClass(), "Binder 接收到更新进度 " + percent);
			mHandler.obtainMessage(MSGWHAT_NOTI_UPGRADE_PROGRESS, percent, 0)
					.sendToTarget();
		}

		public void onGetUpgradeFailed(int percent) {
			Logger.i(getClass(), "Binder 接收到更新失败 ");
			mHandler.obtainMessage(MSGWHAT_NOTI_UPGRADE_FAILED, percent, 0)
					.sendToTarget();
		}

		public void onGetUpgradeCanceled(int percent) {
			Logger.i(getClass(), "Binder 接收到更新取消 ");
			mHandler.obtainMessage(MSGWHAT_NOTI_UPGRADE_CANCELED, percent, 0)
					.sendToTarget();
		}

		private Notification createUpgradeFailedNotification(int percent) {
			Notification Noti = new Notification(R.drawable.ic_launcher,
					"安芯通勤车", System.currentTimeMillis());
			// 放置在"正在运行"栏目中
			Noti.flags = Notification.FLAG_ONGOING_EVENT;

			RemoteViews contentView = new RemoteViews(YtApplication
					.getInstance().getPackageName(),
					R.layout.control_notification_content);
			String txt = "下载更新失败,请检查网络后重新下载";

			contentView.setTextViewText(R.id.tv_cnc_content, txt);
			contentView.setTextViewText(R.id.tv_cnc_time,
					DateUtils.getFormatTime(Calendar.getInstance(), "HH:mm"));
			contentView.setProgressBar(R.id.pb_cnc_progress, 100, percent,
					false);
			// 指定个性化视图
			Noti.contentView = contentView;

			Intent intent = new Intent(YtApplication.getInstance(),
					AboutActivity.class);
			if (!ActivityManager.hasLoadMainActivity()) {
				intent = new Intent(YtApplication.getInstance(),
						SplashActivity.class);
			}
			PendingIntent contentIntent = PendingIntent.getActivity(
					YtApplication.getInstance(), 0, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			
			Noti.flags |= Notification.FLAG_AUTO_CANCEL;
			// 指定内容意图
			Noti.contentIntent = contentIntent;
			return Noti;
		}

		private Notification createNewUpgradeNotification(int percent) {
			Notification Noti = new Notification(R.drawable.ic_launcher,
					"安芯通勤车", System.currentTimeMillis());
			// 放置在"正在运行"栏目中
			Noti.flags = Notification.FLAG_ONGOING_EVENT;

			RemoteViews contentView = new RemoteViews(YtApplication
					.getInstance().getPackageName(),
					R.layout.control_notification_content);
			String txt = "正在更新中 ( " + percent + "% )";
			if (percent == 100) {
				txt = "下载完成！";
			}
			contentView.setTextViewText(R.id.tv_cnc_content, txt);
			contentView.setTextViewText(R.id.tv_cnc_time,
					DateUtils.getFormatTime(Calendar.getInstance(), "HH:mm"));
			contentView.setProgressBar(R.id.pb_cnc_progress, 100, percent,
					false);
			// 指定个性化视图
			Noti.contentView = contentView;

			Intent intent = new Intent(YtApplication.getInstance(),
					AboutActivity.class);
			if (!ActivityManager.hasLoadMainActivity()) {
				intent = new Intent(YtApplication.getInstance(),
						SplashActivity.class);
			}
			PendingIntent contentIntent = PendingIntent.getActivity(
					YtApplication.getInstance(), 0, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			if (percent == 100) {
				// 下载完成后
				intent = new Intent(Intent.ACTION_VIEW);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setDataAndType(Uri.fromFile(new File(Environment
						.getExternalStorageDirectory(),
						VersionUpgradeBiz.UPDATE_SAVENAME)),
						"application/vnd.android.package-archive");
				contentIntent = PendingIntent.getActivity(
						YtApplication.getInstance(), 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				Noti.flags |= Notification.FLAG_AUTO_CANCEL;
			}

			// 指定内容意图
			Noti.contentIntent = contentIntent;
			return Noti;
		}

	}

}
