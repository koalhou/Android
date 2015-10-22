
package com.yutong.axxc.parents.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 网络检查工具类
 * @author zhangzhia 2013-9-10 上午11:17:58
 *
 */
public final class NetworkCheckUitl {

    private NetworkCheckUitl() {
    }


    public static boolean isOnline() {
        Context context = YtApplication.getInstance();
        if (context != null) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        } else {
            return false;
        }
    }

    public static boolean is3GOnline() {
        Context context = YtApplication.getInstance();
        if (context != null) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
        } else {
            return false;
        }
    }
}
