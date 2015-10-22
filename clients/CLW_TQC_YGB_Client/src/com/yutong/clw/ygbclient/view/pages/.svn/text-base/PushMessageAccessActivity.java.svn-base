/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-19 下午4:22:47
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.pages;

import android.os.Bundle;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.view.bean.push.AlarmPushBean;
import com.yutong.clw.ygbclient.view.bean.push.AnnouncePushBean;
import com.yutong.clw.ygbclient.view.bean.push.FeedBackPushBean;
import com.yutong.clw.ygbclient.view.bean.push.PushBean;
import com.yutong.clw.ygbclient.view.common.PushMessageRouter.OnRecieveFeedBackPushMessageListener;
import com.yutong.clw.ygbclient.view.common.PushMessageRouter.OnRecievePushAlarmMessageListener;
import com.yutong.clw.ygbclient.view.common.PushMessageRouter.OnRecievePushAnnounceMessageListener;

/**
 * @author zhouzc 2013-11-19 下午4:22:47
 */
public class PushMessageAccessActivity extends BaseActivity {

	OnRecievePushAlarmMessageListener alarmlistener;

	OnRecievePushAnnounceMessageListener annlistener;

	OnRecieveFeedBackPushMessageListener announcementListener;

	/**
	 * 是否侦听提醒推送信息，在oncreate super方法之后设置有效
	 */
	protected boolean listenAlarm = false;

	/**
	 * 是否侦听公告推送信息，在oncreate super方法之后设置有效
	 */
	protected boolean listenAnnounce = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onResume() {

		if (listenAlarm) {
			if (alarmlistener == null) {
				alarmlistener = new OnRecievePushAlarmMessageListener() {
					@Override
					public void OnReceive(AlarmPushBean bean) {
						Logger.i(getClass(), "页面[" + getClass()+ "]接收到提醒信息推送消息");
						onReceivedPushMessage(bean);
					}
				};
			}
			YtApplication.getInstance().getPushMessageRouter().addOnRecieveAlarmMessageListener(alarmlistener);
		}
		
		if (listenAnnounce) {
			if (annlistener == null) {
				annlistener = new OnRecievePushAnnounceMessageListener() {
					@Override
					public void OnReceive(AnnouncePushBean bean) {
						Logger.i(getClass(), "页面[" + getClass() + "]接收到消息");
						onReceivedPushMessage(bean);
					}
				};
			}
			YtApplication.getInstance().getPushMessageRouter().addOnRecieveAnnounceMessageListener(annlistener);
		}

		if (announcementListener == null) {
			announcementListener = new OnRecieveFeedBackPushMessageListener() {

				@Override
				public void OnReceive(FeedBackPushBean bean) {
					Logger.i(getClass(), "页面[" + getClass() + "]接收到公告信息推送消息");
					onReceivedPushMessage(bean);
				}
			};
		}
		
		YtApplication.getInstance().getPushMessageRouter().addOnRecieveFeedBackPushMessageListener(announcementListener);
		
		super.onResume();
	}

	@Override
	protected void onPause() {
		
		if (alarmlistener != null) {
			YtApplication.getInstance().getPushMessageRouter().removeOnPageRecieveAlarmMessageListener(alarmlistener);
		}
		
		if (annlistener != null) {
			YtApplication.getInstance().getPushMessageRouter().removeOnPageRecieveAnnounceMessageListener(annlistener);
		}
		
		if (announcementListener != null) {
			YtApplication.getInstance().getPushMessageRouter().removeOnRecieveFeedBackPushMessageListener(announcementListener);
		}

		super.onPause();
	}

	/**
	 * @param msg
	 */
	protected void onReceivedPushMessage(PushBean msg) {

	}

}
