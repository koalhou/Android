package com.yutong.clw.ygbclient.view.common.map;

import android.widget.Toast;

import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;

public class BaiduMapGeneralListener implements MKGeneralListener {

	@Override
	public void onGetNetworkState(int iError) {
		if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
			Logger.e(getClass(), "您的网络出错啦！");
			// Toast.makeText(YtApplication.getInstance().getApplicationContext(),
			// "您的网络出错啦！",
			// Toast.LENGTH_SHORT).show();
		} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
			Logger.e(getClass(), "输入正确的检索条件！");
			// Toast.makeText(YtApplication.getInstance().getApplicationContext(),
			// "输入正确的检索条件！",
			// Toast.LENGTH_SHORT).show();
		}
		// ...
	}

	@Override
	public void onGetPermissionState(int iError) {
		if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
			Logger.e(getClass(), "授权Key有误，请使用正确的授权Key！");
			// 授权Key错误：
			// Toast.makeText(YtApplication.getInstance().getApplicationContext(),
			// "请使用正确的授权Key！", Toast.LENGTH_SHORT).show();
			YtApplication.getInstance().getBaiduMapManager().setKeyRight(false);
		}
	}
}