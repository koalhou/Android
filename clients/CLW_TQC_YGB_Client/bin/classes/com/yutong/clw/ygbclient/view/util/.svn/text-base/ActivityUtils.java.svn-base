/*******************************************************************************
 * @(#)ActivityUtils.java 2013-2-25 Copyright 2013 Neusoft Group Ltd. All rights
 *                        reserved. Neusoft PROPRIETARY/CONFIDENTIAL. Use is
 *                        subject to license terms.
 *******************************************************************************/

package com.yutong.clw.ygbclient.view.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.yutong.clw.ygbclient.R;

/**
 * @author <a href="mailto:wenxw@neusoft.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2013-2-25 下午03:20:19
 */
public final class ActivityUtils
{

    private ActivityUtils()
    {

    }

    /**
     * activity跳转
     * 
     * @param from 源activity
     * @param to 目标activity
     * @param extras 需要传递的数据
     */
    public static void changeActivity(Activity from, Class<?> to)
    {
        changeActivity(from, to, null);
    }

    /**
     * activity跳转
     * 
     * @param from 源activity
     * @param to 目标activity
     * @param extras 需要传递的数据
     */
    public static void changeActivity(Activity from, Class<?> to, Bundle extras)
    {
        // Intent intent = setIntent(from, to, extras);
        Intent intent = new Intent();
        if (extras != null)
            intent.putExtras(extras);
        intent.setClass(from.getBaseContext(), to);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        from.startActivity(intent);
        // from.overridePendingTransition(android.R.anim.fade_in,
        // android.R.anim.fade_out);
        from.overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
    }

    /**
     * @param vMonitorBatchActivity TODO
     * @param from 源activity
     * @param to 目标biactivity
     * @param vBeanList 需要传递的列表数据
     */
    public static void changeActivityWithData(Activity from, Class<?> to, Intent mIntent)
    {
        if (mIntent != null)
        {
            mIntent.setClass(from, to);
            from.startActivity(mIntent);
        }
    }

    /**
     * 电话呼叫指定号码
     * @param activity
     * @param telNumber
     */
    public static void call(Activity activity, String phoneNumber) {
        /*Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telNumber));*/
    	
    	/* 拨号模式	*/
    	Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + phoneNumber));
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
    	activity.startActivity(intent);
    }
}
