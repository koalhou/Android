
package com.yutong.clw.ygbclient.common.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.yutong.clw.ygbclient.YtApplication;

/**
 * 网络�?��工具�?
 * @author zhangzhia 2013-9-10 上午11:17:58
 *
 */
public final class NetworkCheckUitl {

    private NetworkCheckUitl() {
    }

    /*网络是否连接（包括Wifi和移动网络）*/
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
    
    public static boolean is3GOnline(Context context) {
        //Context context = YtApplication.getInstance();
        if (context != null) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
        } else {
            return false;
        }
    }
    
   /* wifi是否可用*/
    public static boolean isWifiEnable(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.isWifiEnabled();
	}
    
    /*Gps是否可用*/
    public static boolean isGpsEnable(Context context) {
		
		LocationManager locationManager = 
				((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
    
    public static void setGpsDisable(Context context) {
		
		LocationManager locationManager = 
				((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
		locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, false);
	}
    
    public static void setGpsEnable(Context context) {
		
		LocationManager locationManager = 
				((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
		locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
	}
}
