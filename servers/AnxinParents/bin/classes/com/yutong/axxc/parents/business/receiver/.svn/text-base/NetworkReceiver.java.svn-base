package com.yutong.axxc.parents.business.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.NetworkCheckUitl;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 网络状态变化监听类
 * 
 * @author zhangzhia 2013-9-3 上午9:29:57
 * 
 */
public class NetworkReceiver extends BroadcastReceiver {

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 *      android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// updated by zhouchao at 20130510 for 使用网络时才提示网络连接异常
		// if (!NetworkCheckUitl.isOnline()) {
		// Toast.makeText(context, ErrorInfo.NETWORK_EXCEPTION,
		// Toast.LENGTH_SHORT).show();
		// }

		Logger.i(getClass(),
				"======================网络状态变化:" + NetworkCheckUitl.isOnline());
		boolean b = NetworkCheckUitl.isOnline();
		YtApplication.getInstance().trigerOnNetworkChangeListener(b);
	}

}
