package com.yutong.axxc.parents.business.messagepush;

import android.app.IntentService;
import android.content.Intent;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * PUSH数目初始化Service
 * 
 * @author zhangzhia 2013-9-3 上午8:48:40
 * 
 */
public class PushNumInitService extends IntentService {

	/**
	 * @param name
	 */
	public PushNumInitService() {
		super("PushInitService");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		int pushType = intent.getIntExtra(PushConstant.PUSH_TYPE, -1);
		Logger.i(getClass(), "[PUSH数目初始化Service]:PUSH_TYPE:", pushType);
		switch (pushType) {
		case PushConstant.RIDDING_CARD_GETOFF:
		case PushConstant.RIDDING_CARD_GETON:
		case PushConstant.RIDDING_STATION_ATTEND:
		case PushConstant.RIDDING_STATION_LETOUT:
			// 清除通知
			PushNotificationUtil.initRidingNotificationCount();
			  //add by lizyi
            YtApplication.getInstance().getCommonPushMsgs().clear();
			break;
		case PushConstant.SYSTEM_NEWS_TYPE:
			// 清除通知
			PushNotificationUtil.initSystemNotificationCount();
			  //add by lizyi
            YtApplication.getInstance().getCommonPushMsgs().clear();
			break;

		default:
			break;
		}

	}

}
