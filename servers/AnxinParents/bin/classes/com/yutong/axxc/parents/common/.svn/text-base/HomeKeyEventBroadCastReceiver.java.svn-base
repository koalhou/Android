package com.yutong.axxc.parents.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yutong.axxc.parents.view.common.YtApplication;

public class HomeKeyEventBroadCastReceiver extends BroadcastReceiver {

    static final String SYSTEM_REASON = "reason";

    static final String SYSTEM_HOME_KEY = "homekey";// home key

    static final String SYSTEM_RECENT_APPS = "recentapps";// long home key

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_REASON);
            if (reason != null) {
//                if (reason.equals(SYSTEM_HOME_KEY)) {
//                    YtApplication.getInstance().setLoadQeustionnaire(true);
//                }
                // else if (reason.equals(SYSTEM_RECENT_APPS)) {
                // // long home key处理点
                // }
            }
        }
    }

}
