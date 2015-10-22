/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:08:53
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.receiver;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yutong.axxc.xmpp.client.NotificationService;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.utils.NetworkCheckUitl;

/**
 * 网络状态变化监听类
 * 
 * @author zhangzhia 2013-9-3 上午9:29:57
 */
public class NetworkReceiver extends BroadcastReceiver
{

    /**
     * (non-Javadoc)
     * 
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     *      android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Logger.i(getClass(), "======================网络状态变化:" + NetworkCheckUitl.isOnline());
        boolean b = NetworkCheckUitl.isOnline();
        YtApplication.getInstance().trigerOnNetworkChangeListener(b);
        if(!b){
        	//YtApplication.getInstance().onLoginSuccess();
        	return;
        }
        
        if(isServiceRun(context)){
        	Logger.i(getClass(), "===========NotificationService:存活");
        }
        else{
        	Logger.i(getClass(), "===========NotificationService:已消亡");
        	//此处不再启动服务，防止还没有登录时，启动连接推送服务器
        	//intent.setClass(context, NotificationService.class);
        	//context.startService(intent);
			//YtApplication.getInstance().reLoginSuccess();
			//YtApplication.getInstance().getNotificationListenServiceBinder();
        	//YtApplication.getInstance().reLoginSuccess();

        }
        
    }
    
    
    
    public boolean isServiceRun(Context context){
        ActivityManager am = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> list = am.getRunningServices(100);
        for(RunningServiceInfo info : list){
            if(info.service.getClassName().toString().equals("com.yutong.axxc.xmpp.client.NotificationService")){	
                     return true;
            }
       }
       return false;
   }

}
