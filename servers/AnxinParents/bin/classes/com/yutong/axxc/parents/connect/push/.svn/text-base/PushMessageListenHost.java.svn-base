package com.yutong.axxc.parents.connect.push;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.yutong.axxc.parents.business.messagepush.PushConstant;
import com.yutong.axxc.parents.business.messagepush.PushNotificationUtil;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.home.MainActivity;
import com.yutong.axxc.parents.view.settings.news.NewsHomeActivity;

/**
 * 推送广播监听类
 * 
 * @author zhouzechen 2013-9-11 下午5:20:07
 * 
 */
public class PushMessageListenHost {

	private final String LOGTAG = PushMessageListenHost.class.toString();

	List<OnMessageReceiveListener> listeners = new ArrayList<PushMessageListenHost.OnMessageReceiveListener>();

	public void clearOnMessageReceiveAllListeners() {
		listeners.clear();
	}

	public void addOnMessageReceiveListener(OnMessageReceiveListener listener) {
		if (listener == null)
			return;
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void removeOnMessageReceiveListener(OnMessageReceiveListener listener) {
		if (listener == null)
			return;
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	/**
	 * 主动触发消息接受事件
	 * 
	 * @param message
	 */
	public void receiveMessage(CommonPushMsg message) {
		if (message == null) {
			Log.i(LOGTAG, "收到一条NULL消息");
			return;
		}

		int mtype = getTypeFromString(message.getRule_id());
		// TODO 判断推送信息
		switch (mtype) {
		case PushConstant.SYSTEM_NEWS_TYPE:
			String msg = "[安芯]新闻提醒信息";
			PushNotificationUtil.newsNotification(YtApplication.getInstance(),
					NewsHomeActivity.class, msg);
			break;
		case PushConstant.RIDDING_CARD_GETOFF:
		case PushConstant.RIDDING_CARD_GETON:
		case PushConstant.RIDDING_STATION_ATTEND:
		case PushConstant.RIDDING_STATION_LETOUT:
			String msg1 = "[安芯]乘车提醒信息";
			PushNotificationUtil.ridingNotification(
					YtApplication.getInstance(), MainActivity.class, msg1,
					mtype);
			break;
		case PushConstant.OTHER:

			break;
		}

		if (listeners == null)
			return;

		if (listeners.size() == 0)
			return;
		synchronized (listeners) {
			for (OnMessageReceiveListener listener : listeners) {
				listener.OnReceived(message);
				switch (mtype) {
				case PushConstant.SYSTEM_NEWS_TYPE:
					listener.OnReceivedNews(message);
					break;
				case PushConstant.RIDDING_CARD_GETOFF:
				case PushConstant.RIDDING_CARD_GETON:
				case PushConstant.RIDDING_STATION_ATTEND:
				case PushConstant.RIDDING_STATION_LETOUT:
					listener.OnReceivedReminds(message);
					break;
				case PushConstant.OTHER:

					break;
				}

			}
		}

	}

	public enum InnerMsgType {
		/**
		 * 提醒信息
		 */
		Reminds,

		/**
		 * 新闻信息
		 */
		News,
		/**
		 * 其他信息
		 */
		Others
	}

	/**
	 * 接收推送信息侦听器
	 * 
	 * @author zhouzc
	 * 
	 */
	public interface OnMessageReceiveListener {
		/**
		 * 收到任意信息
		 * 
		 * @param message
		 */
		public void OnReceived(CommonPushMsg message);

		/**
		 * 收到新闻信息
		 * 
		 * @param message
		 */
		public void OnReceivedNews(CommonPushMsg message);

		/**
		 * 收到提醒信息
		 * 
		 * @param message
		 */
		public void OnReceivedReminds(CommonPushMsg message);
	}

	private int getTypeFromString(String tstr) {
		int mtype = PushConstant.OTHER;
		if (null != tstr) {
			if ("02".equals(tstr)) {
				mtype = PushConstant.SYSTEM_NEWS_TYPE;
			} else if (tstr.startsWith("01")) {
				if (tstr.equals("01_002")) {
					mtype = PushConstant.RIDDING_STATION_ATTEND;
				} else if (tstr.equals("01_105")) {
					mtype = PushConstant.RIDDING_STATION_LETOUT;
				} else if (tstr.equals("01_004")) {
					mtype = PushConstant.RIDDING_CARD_GETON;
				} else if (tstr.equals("01_007")) {
					mtype = PushConstant.RIDDING_CARD_GETOFF;
				} else if (tstr.equals("01_104")) {
					mtype = PushConstant.RIDDING_CARD_GETON;
				} else if (tstr.equals("01_107")) {
					mtype = PushConstant.RIDDING_CARD_GETOFF;
				}
			} else {
				mtype = PushConstant.OTHER;
			}
		}
		return mtype;
	}

}
