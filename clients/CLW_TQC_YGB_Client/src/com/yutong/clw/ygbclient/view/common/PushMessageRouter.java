/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-19 下午4:14:41
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.common.PushMsgUtil;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.push.FeedBackPush;
import com.yutong.clw.ygbclient.view.bean.push.AlarmPushBean;
import com.yutong.clw.ygbclient.view.bean.push.AnnouncePushBean;
import com.yutong.clw.ygbclient.view.bean.push.FeedBackPushBean;
import com.yutong.clw.ygbclient.view.bean.push.PushBean;
import com.yutong.clw.ygbclient.view.pages.main.MainActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation.SingleStationLinesActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleVehicle.SingleVehicleLinesActivity;
import com.yutong.clw.ygbclient.view.pages.setting.announcement.AnnounceListActivity;
import com.yutong.clw.ygbclient.view.util.NotificationUtil;

/**
 * 推送消息转发器
 * 
 * @author zhouzc 2013-11-19 下午4:14:41
 * 
 */
public class PushMessageRouter {

	List<OnRecievePushAlarmMessageListener> alarmlisteners;
	List<OnRecievePushAnnounceMessageListener> annlisteners;
	List<OnRecieveFeedBackPushMessageListener> announcementlisteners;
	ExecutorService service = null;

	Queue<PushBean> msgs = null;

	MessageRouteDirection alarmRouteDriection = MessageRouteDirection.Page;

	private boolean isprocessing = false;
	
	public PushMessageRouter() {
		alarmlisteners = new ArrayList<PushMessageRouter.OnRecievePushAlarmMessageListener>();
		annlisteners = new ArrayList<PushMessageRouter.OnRecievePushAnnounceMessageListener>();
		announcementlisteners  = new ArrayList<PushMessageRouter.OnRecieveFeedBackPushMessageListener>();
		msgs = new ArrayBlockingQueue<PushBean>(1000);
		service = Executors.newFixedThreadPool(3);
	}

	public void push(PushBean throwable) {
		Logger.i(getClass(), "添加新的消息到队列");
		try {
			msgs.offer(throwable);
		} catch (Exception err) {
			err.printStackTrace();
		}
		if (isprocessing){
			return;
		} else {
			isprocessing = true;
			startProcess();
		}
	}

	public void stop() {
		if (service != null) {

		}
	}

	public MessageRouteDirection getAlarmRouteDriection() {
		return alarmRouteDriection;
	}

	public void setAlarmRouteDriection(MessageRouteDirection alarmRouteDriection) {
		this.alarmRouteDriection = alarmRouteDriection;
	}

	

	private boolean startProcess() {
		Logger.i(getClass(), "开始处理消息队列");
		final PushBean t = msgs.poll();
		if (t != null) {
			service.submit(new Runnable() {
				@Override
				public void run() {
					processPushMessage(t);
				}
			});
			return true;
		} else {
			return false;
		}
	}

	private void processPushMessage(final PushBean bean) {
		if (bean == null)
			return;
		Logger.i(getClass(), "准备消息分发");
		if (bean instanceof AnnouncePushBean) {
			Logger.i(getClass(), "准备提示公告消息到通知栏");
			routeAnnouncePushBeanToNotification((AnnouncePushBean) bean);
			Logger.i(getClass(), "准备发送到" + alarmlisteners.size() + "个公告消息侦听器");
			for (OnRecievePushAnnounceMessageListener listener : annlisteners) {
				if (listener != null) {
					listener.OnReceive((AnnouncePushBean) bean);
				}
			}
		}else if (bean instanceof AlarmPushBean) {

			if (alarmRouteDriection == MessageRouteDirection.Notification
					|| alarmRouteDriection == MessageRouteDirection.All) {
				Logger.i(getClass(), "准备提示提醒消息到通知栏");
				routeAlarmPushBeanToNotification((AlarmPushBean) bean);
			}

			if (alarmRouteDriection == MessageRouteDirection.Page
					|| alarmRouteDriection == MessageRouteDirection.All) {
				Logger.i(getClass(), "准备发送到" + alarmlisteners.size()
						+ "个提醒消息侦听器");
				for (OnRecievePushAlarmMessageListener listener : alarmlisteners) {
					if (listener != null) {
						listener.OnReceive((AlarmPushBean) bean);
					}
				}

			}
		}else if (bean instanceof FeedBackPushBean) {
			
			Logger.i(getClass(), "准备处理公告消息推送");
			Logger.i(getClass(), "准备发送到" + announcementlisteners.size() + "个【意见反馈】侦听器");
			
			for (OnRecieveFeedBackPushMessageListener listener : announcementlisteners) {
				if (listener != null) {
					listener.OnReceive((FeedBackPushBean) bean);
				}
			}
		}

		Logger.i(getClass(), "一个消息处理结束，准备开始处理下一个");
		boolean hasnext = startProcess();
		Logger.i(getClass(), "发现下个消息:" + hasnext);
		isprocessing = hasnext;
	}

	private void playRemindMedia(PushBean pushmsg) {
		Logger.i(getClass(), "开始播放提醒音乐");
		Uri mediauri = PrefDataUtil.getRemindRingUri(YtApplication
				.getInstance());
		/*if (mediauri == null) {
			mediauri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
					+ "://" + YtApplication.getInstance().getPackageName()
					+ "/raw/alarm");
		}*/
		
		if (mediauri == null) {
			
			AlarmPushBean alarmPushBean = null;
			if(pushmsg instanceof AlarmPushBean){
				alarmPushBean = (AlarmPushBean) pushmsg;
				String ruleId = alarmPushBean.getRuleId();
				
				//厂内
				if(ruleId.equals("02")){
					
					mediauri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
							+ "://" + YtApplication.getInstance().getPackageName()
							+ "/raw/station_arrived");
					
				}else if( ruleId.equals("03") ){//厂外
					StatusRange statusRange = alarmPushBean.getStatusRange();
					
					if(statusRange.value() == 0){//早班
						mediauri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
								+ "://" + YtApplication.getInstance().getPackageName()
								+ "/raw/station_arrived");
					}else if(statusRange.value() == 1){//晚班
						mediauri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
								+ "://" + YtApplication.getInstance().getPackageName()
								+ "/raw/station_arrived");
					}
				}
			}
		}
		
		
		YtApplication
				.getInstance()
				.getMediaManager()
				.PlayMedia(
						mediauri,
						PrefDataUtil.getRemindRingVolume(YtApplication
								.getInstance()));

	}

	private void routeAlarmPushBeanToNotification(AlarmPushBean bean) {
		// if (!YtApplication.getInstance().getMediaManager().isPlaying()) {
		playRemindMedia(bean);
		// }
		switch (bean.getType()) {
		case FactoryInside: {
			Class targetActivity = MainActivity.class;
			String title = "安芯通勤车";
			String tickerText = String.format("[厂内]班车%s已到达%s",
					bean.getVehicleNumber(), bean.getStationName());

			String contentText = String.format("[厂内]班车%s已到达%s",
					bean.getVehicleNumber(), bean.getStationName());
			int icon = R.drawable.bg_anxinbtn_active;
			Uri notisound = null;
			boolean autoclear = true;
			Bundle data = new Bundle();
			data.putSerializable(ActivityCommConstant.PUSHBEAN, bean);
			NotificationUtil.pushNewNotification(
					NotificationUtil.NOTIFICATIONID_ALARM, targetActivity,
					data, title, tickerText, contentText, icon, notisound,
					autoclear, true);
		}
			break;
		case FactoryOuter: {

			Class targetActivity = bean.getRemindRange() == RemindRange.OnlyStation ? SingleStationLinesActivity.class
					: SingleVehicleLinesActivity.class;
			String remind_type = "";
			switch (bean.getRemindType()) {
			case Date:
				remind_type = "分钟";
				break;
			case Distance:
				remind_type = "米";
				break;
			case StationNum:
				remind_type = "个";
				break;
			}

			String title = "安芯通勤车";

			String tickerText = String.format("[厂外]班车%s将在%d%s后到达%s",
					bean.getVehicleNumber(), bean.getRemindValue(),
					remind_type, bean.getStationName());

			String contentText = String.format("[厂外]班车%s将在%d%s后到达%s",
					bean.getVehicleNumber(), bean.getRemindValue(),
					remind_type, bean.getStationName());

			int icon = R.drawable.bg_anxinbtn_active;
			Uri notisound = null;
			boolean autoclear = true;
			Bundle data = new Bundle();
			data.putSerializable(ActivityCommConstant.PUSHBEAN, bean);
			NotificationUtil.pushNewNotification(
					NotificationUtil.NOTIFICATIONID_ALARM, targetActivity,
					data, title, tickerText, contentText, icon, notisound,
					autoclear, false);
		}
			break;
		default:
			break;
		}
		startViberate();

	}

	protected void startViberate() {
		if (!PrefDataUtil.getRemindVibrate(YtApplication.getInstance()))
			return;

		Vibrator v = (Vibrator) YtApplication.getInstance().getSystemService(
				Context.VIBRATOR_SERVICE);
		v.vibrate(new long[] { 1000, 10, 100, 1000 }, -1);
	}

	protected void stopViberate() {
		if (!PrefDataUtil.getRemindVibrate(YtApplication.getInstance()))
			return;
		Vibrator v = (Vibrator) YtApplication.getInstance().getSystemService(
				Context.VIBRATOR_SERVICE);
		v.cancel();
	}

	private void routeAnnouncePushBeanToNotification(AnnouncePushBean bean) {
		// TODO 通知栏提示
		Class targetActivity = AnnounceListActivity.class;
		String title = "安芯通勤车";
		String tickerText = bean.getTitle();
		String contentText = bean.getDescription();
		int icon = R.drawable.bg_anxinbtn_normal;
		Uri notisound = null;
		boolean autoclear = true;
		Bundle data = new Bundle();
		data.putSerializable(ActivityCommConstant.PUSHBEAN, bean);
		int newCount = PushMsgUtil.getNewsNotificationCount();
		if (newCount > 1) {
			String show_text = String.format("[公告]有%d条新公告消息，请注意查收", newCount);
			NotificationUtil.pushNewNotification(
					NotificationUtil.NOTIFICATIONID_ANNOUNCE, targetActivity,
					data, title, tickerText, show_text, icon, notisound,
					autoclear, true);
		} else {
			NotificationUtil.pushNewNotification(
					NotificationUtil.NOTIFICATIONID_ANNOUNCE, targetActivity,
					data, title, tickerText, "[公告]" + contentText, icon,
					notisound, autoclear, true);
		}
	}

	public void addOnRecieveAlarmMessageListener(
			OnRecievePushAlarmMessageListener listener) {
		alarmlisteners.add(listener);
		Logger.i(getClass(), "添加提醒消息侦听，总侦听数" + alarmlisteners.size());
	}

	public void removeOnPageRecieveAlarmMessageListener(
			OnRecievePushAlarmMessageListener listener) {
		alarmlisteners.remove(listener);
		Logger.i(getClass(), "移除提醒消息侦听，总侦听数" + alarmlisteners.size());
	}

	public void addOnRecieveAnnounceMessageListener(
			OnRecievePushAnnounceMessageListener listener) {
		annlisteners.add(listener);
		Logger.i(getClass(), "添加公告消息侦听，总侦听数" + annlisteners.size());
	}

	public void removeOnPageRecieveAnnounceMessageListener(
			OnRecievePushAnnounceMessageListener listener) {
		annlisteners.remove(listener);
		Logger.i(getClass(), "移除公告消息侦听，总侦听数" + annlisteners.size());
	}
	
	//增加公告侦听
	public void addOnRecieveFeedBackPushMessageListener(OnRecieveFeedBackPushMessageListener listener) {
		announcementlisteners.add(listener);
		Logger.i(getClass(), "添加新增公告消息侦听，总侦听数" + annlisteners.size());
	}

	public void removeOnRecieveFeedBackPushMessageListener(OnRecieveFeedBackPushMessageListener listener) {
		announcementlisteners.remove(listener);
		Logger.i(getClass(), "移除新增公告消息侦听，总侦听数" + annlisteners.size());
	}

	public static interface OnRecievePushAlarmMessageListener {
		public void OnReceive(AlarmPushBean bean);
	}

	public static interface OnRecievePushAnnounceMessageListener {
		public void OnReceive(AnnouncePushBean bean);
	}
	
	//公告推送接收
	public static interface OnRecieveFeedBackPushMessageListener {
		public void OnReceive(FeedBackPushBean bean);
	}

	public static enum MessageRouteDirection {
		/**
		 * 转发到页面
		 */
		Page,
		/**
		 * 转发到通知栏
		 */
		Notification,
		/**
		 * 全部转发
		 */
		All
	}

}
