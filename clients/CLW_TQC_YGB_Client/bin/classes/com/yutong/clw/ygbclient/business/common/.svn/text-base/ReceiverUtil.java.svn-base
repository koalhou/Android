/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:18:51
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.common;

import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import com.yutong.axxc.xmpp.client.MessageManager;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.messagepush.XmppSDKMsgReceiver;
import com.yutong.clw.ygbclient.business.receiver.NetworkReceiver;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;

/**
 * Receiver工具类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public final class ReceiverUtil {

	protected static Context context;

	private static MessageManager serviceManager = MessageManager.getInstance();

	private static XmppSDKMsgReceiver xmppSDKMsgReceiver = new XmppSDKMsgReceiver();
	
	private ReceiverUtil(Context context) {
		this.context = context;
	}

	/** 是否已经初始化push */
	private static boolean isInitPush = false;

	// public boolean isInitPush()
	// {
	// return isInitPush;
	// }
	//
	// public void setInitPush(boolean isInitPush)
	// {
	// this.isInitPush = isInitPush;
	// }

	/**
	 * 注册网络广播接收类
	 */
	public static void registerNetworkReceiver() {
		
		NetworkReceiver networkReceiver = new NetworkReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		YtApplication.getInstance().registerReceiver(networkReceiver, filter);
		YtApplication.getInstance().setNetworkReceiver(networkReceiver);
	}

	/**
	 * 取消注册所有广播接收
	 */
	public static void unRegisterNetworkReceiver() {
		
		if (YtApplication.getInstance().getNetworkReceiver() != null) {
			YtApplication.getInstance().unregisterReceiver(
					YtApplication.getInstance().getNetworkReceiver());
		}
	}

	

	/**
	 * 注册push广播接收类
	 */
	public static void registerPushReceiver() {
		if (!isInitPush) {
			serviceManager.addInfoReceivedListener(xmppSDKMsgReceiver);
			String appid = SysConfigUtil.getPlatfrom();
			String userid = ProxyManager.getInstance(context).getUserCode();
			Log.i("ReceiverUtil", "准备注册到推送服务appid：" + appid + "， userid："+ userid);
			serviceManager.initial(YtApplication.getInstance(), appid, userid);
			isInitPush = true;
		}
	}

	/**
	 * 取消push广播接收
	 */
	public static void unRegisterPushReceiver() {
		try {
			if (serviceManager != null) {
				serviceManager.stopService();
				isInitPush = false;
			}
		} catch (Exception e) {
			isInitPush = false;
		}
	}

}
