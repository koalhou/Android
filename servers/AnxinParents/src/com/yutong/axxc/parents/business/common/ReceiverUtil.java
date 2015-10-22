package com.yutong.axxc.parents.business.common;

import android.content.IntentFilter;

import com.yutong.axxc.parents.business.messagepush.XmppSDKMsgReceiver;
import com.yutong.axxc.parents.business.receiver.NetworkReceiver;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.xmpp.client.MessageManager;

/**
 * Receiver工具类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 * 
 */
public final class ReceiverUtil
{

	private ReceiverUtil()
	{
	}

	/**
	 * 注册网络广播接收类
	 */
	public static void registerNetworkReceiver()
	{
		NetworkReceiver networkReceiver = new NetworkReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		YtApplication.getInstance().registerReceiver(networkReceiver, filter);
		YtApplication.getInstance().setNetworkReceiver(networkReceiver);
	}

	/**
	 * 取消注册所有广播接收
	 */
	public static void unRegisterNetworkReceiver()
	{
		if (YtApplication.getInstance().getNetworkReceiver() != null)
		{
			YtApplication.getInstance().unregisterReceiver(
					YtApplication.getInstance().getNetworkReceiver());
		}
	}
//add by lizyi 不要重复注册广播，否则消息数量错乱
	public static XmppSDKMsgReceiver xmppSDKMsgReceiver;

	/**
	 * 注册push广播接收类
	 */
	public static void registerPushReceiver()
	{
		if (xmppSDKMsgReceiver == null)
		{
			xmppSDKMsgReceiver = new XmppSDKMsgReceiver();
		}else{
			Logger.d(ReceiverUtil.class, "重复注册消息推送广播无效。。。");
		}
		YtApplication.getInstance().setServiceManager(
				MessageManager.getInstance());
		// Start the service
		
		YtApplication.getInstance().getServiceManager()
				.addInfoReceivedListener(xmppSDKMsgReceiver);

		YtApplication
				.getInstance()
				.getServiceManager()
				.initial(YtApplication.getInstance(),
						YtApplication.getInstance().getAppId(),
						YtApplication.getInstance().getUid());
	}

	/**
	 * 取消push广播接收
	 */
	public static void unRegisterPushReceiver()
	{

		try
		{
			YtApplication.getInstance().getServiceManager().stopService();
		}
		catch (Exception e)
		{
		}
	}

}
