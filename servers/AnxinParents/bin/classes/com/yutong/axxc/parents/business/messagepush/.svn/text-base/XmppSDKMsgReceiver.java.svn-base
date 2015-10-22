package com.yutong.axxc.parents.business.messagepush;

import org.apache.commons.lang3.StringUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.connect.push.CommonPushMsg;
import com.yutong.axxc.parents.view.common.NotificationTestActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.home.MainActivity;
import com.yutong.axxc.xmpp.client.model.XmppInfo;
import com.yutong.axxc.xmpp.client.provider.InfoReceivedListener;

/**
 * 推送消息广播接收类
 * 
 * @author zhangzhia 2013-9-3 上午9:14:25
 */
public class XmppSDKMsgReceiver implements InfoReceivedListener
{
    /** 通知栏不提示 */
    private final String NON_PROMPT = "0";

    private String logTypeName = "[推送消息广播接收类]:";

    @Override
    public void Receive(XmppInfo msginfo)
    {
        // TODO Auto-generated method stub
        Logger.i(this.getClass(), logTypeName + "收到推送消息");
        handleMsgData(msginfo.getInfo());
    }

    /**
     * 处理消息数据
     * 
     * @param bundle
     * @param context
     */
    private void handleMsgData(String pushJsonString)
    {
    	//ShowMessageInNotificationBar(pushJsonString);
        if (StringUtils.isNotBlank(pushJsonString))
        {
            Logger.i(this.getClass(), logTypeName + "消息内容：" + pushJsonString);
            CommonPushMsg commonPush = new CommonPushMsg();
            if(commonPush.parse(pushJsonString))
            {
                //TODO 解析数据
                YtApplication.getInstance().getCommonPushMsgs().add(commonPush);
                YtApplication.getInstance().getPushHost().receiveMessage(commonPush);
            }

//            if (CommonPushMsg.RIDING_PUSH_MSG_TYPE.equalsIgnoreCase(commonPush.getMsgType()))
//            {
//                //乘车记录推送信息
//               
//              // 取内容字段 示例（取车牌号码）：
//              String str = commonPush.getContent("vehicle_plate");
//                
//            }
//            else if (CommonPushMsg.NEW_PUSH_MSG_TYPE.equalsIgnoreCase(commonPush.getMsgType()))
//            {
//              //系统新闻推送信息
//            }
//            else
//            {
//                //未知推送消息，不处理
//            }
        }
    }

    public void ShowMessageInNotificationBar(String pushJsonString) {
		Context context = YtApplication.getInstance();
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);
		// 定义通知栏展现的内容信息
		int icon = R.drawable.anxin_button;
		CharSequence tickerText = "新消息";
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		// 定义下拉通知栏时要展现的内容信息

		CharSequence contentTitle = "接收到新消息";
		CharSequence contentText = pushJsonString;
		Intent notificationIntent = new Intent(context, NotificationTestActivity.class);
		notificationIntent.putExtra("TXT", contentText);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		// 用mNotificationManager的notify方法通知用户生成标题栏消息通知
		mNotificationManager.notify(1, notification);
	}
    
/*    private void shownoti() {
		Notification notif = new Notification(R.drawable.ic_launcher, "",
				System.currentTimeMillis());
		Intent notificationIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		notificationIntent.putExtra("NotificationMessage", "hello");
		// notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
		// | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
				getApplicationContext(),1, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		//notif.flags |= Notification.FLAG_AUTO_CANCEL;
		notif.setLatestEventInfo(getApplicationContext(), "new msg",
				"mesgbody", pendingNotificationIntent);

		NotificationManager nm = (NotificationManager) this
				.getSystemService(Application.NOTIFICATION_SERVICE);
		nm.notify(1, notif);
	}*/

}
