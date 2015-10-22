
package com.yutong.axxc.parents.common;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.baidu.mapapi.CoordinateConvert;
import com.baidu.mapapi.GeoPoint;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 经纬度解析位置地址工具类
 * @author zhangzhia 2013-9-11 下午5:16:19
 *
 */
public class LocationUtils {
    // private LocationManager lm;
    //
    // private LocationListener locationListener;

    /**
     * 城市名 如成都、北京
     * @return Returns the cityName.
     */
    public static String getCityName() {
//        String cityName = YtApplication.getInstance().getEnCity();
//        if (StringUtils.isBlank(cityName)) {
//            cityName = YtApplication.getInstance().getCity();
//        }
//        if (StringUtils.isNotBlank(cityName)) {
//            return cityName.replace("市", "");
//        }
        return "郑州";
    }

    /**
     * 依据位置经纬度解析位置地址
     * @return
     */
    public static Address getAddrByLocation(Location location) {
        double lat = 0;
        double lng = 0;
        List<Address> addrList = null;
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            try {
                addrList = new Geocoder(YtApplication.getInstance()).getFromLocation(lat, lng, 1); // 解析经纬度
            } catch (IOException e) {
                Logger.e(LocationUtils.class, "[位置获取工具类]：解析经纬度出现异常，", e);
            }
        } else {
            Logger.w(LocationUtils.class, "[位置获取工具类]：位置信息为空");
        }
        if (addrList != null && addrList.size() > 0) {
            for (int i = 0; i < addrList.size(); i++) {
                if (addrList.get(i) != null) {
                    return addrList.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 解析位置经纬度并将解析后的位置地址保存至shared preferences
     * @param location
     */
    // public static void saveAddr(Location location) {
    // Address addr = getAddrByLocation(location);
    // if (addr != null && StringUtils.isNotBlank(addr.getAdminArea()) && StringUtils.isNotBlank(addr.getLocality())) {
    // YtApplication.getInstance().setProv(addr.getAdminArea());
    // YtApplication.getInstance().setCity(addr.getLocality());
    // }
    // }

    /**
     * 获取location对象
     * @param mContext
     */
    // public void initLocation(Context mContext) {
    // // 获得系统及服务的LocationManager对象
    // lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    //
    // Criteria criteria = new Criteria();
    // criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
    // criteria.setAltitudeRequired(false); // 不要求海拔
    // criteria.setBearingRequired(false); // 不要求方位
    // criteria.setCostAllowed(false); // 不允许有话费
    // criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
    //
    // if (startLocation(LocationManager.NETWORK_PROVIDER)) {
    // // 首先检测通过network能否获得location对象
    // // 如果获得了location对象，则在这里注销location的监听
    // lm.removeUpdates(locationListener);
    // } else if (startLocation(LocationManager.GPS_PROVIDER)) {
    // // 通过gps能否获得location对象
    // // 如果获得了location对象，则在这里注销location的监听
    // // gps会在一定时间内自动关闭
    // lm.removeUpdates(locationListener);
    // }
    // }

    /**
     * 通过参数获取Location对象； 如果Location对象不为空，则返回 true，并且赋值给全局变量location； 如果为空，返回false，不赋值给全局变量location
     * @param provider
     * @return
     */
    // private boolean startLocation(String provider) {
    // Location location = lm.getLastKnownLocation(provider);
    //
    // // 位置监听器
    // locationListener = new LocationListener() {
    // /**
    // * 当位置改变时触发 (non-Javadoc)
    // * @see android.location.LocationListener#onLocationChanged(android.location.Location)
    // */
    // @Override
    // public void onLocationChanged(Location location) {
    // if (location != null) {
    // LocationUtils.saveAddr(location);
    // // 如果已经获取到location信息，则在这里注销location的监听
    // // gps会在一定时间内自动关闭
    // lm.removeUpdates(locationListener);
    // }
    // }
    //
    // @Override
    // public void onProviderDisabled(String arg0) {
    // }
    //
    // @Override
    // public void onProviderEnabled(String arg0) {
    // }
    //
    // @Override
    // public void onStatusChanged(String provider, int status, Bundle extras) {
    // }
    // };
    //
    // // 500毫秒更新一次，忽略位置变化
    // lm.requestLocationUpdates(provider, 500, 0, locationListener);
    //
    // // 如果Location对象不为空，则返回 true，并且赋值给全局变量 location
    // // 如果为空，返回false，不赋值给全局变量location
    // if (location != null) {
    // YtApplication.getInstance().setLocation(location);
    // return true;
    // }
    // return false;
    // }

    // public void destroyLocation() {
    // // 注销location的监听
    // // gps会在一定时间内自动关闭
    // lm.removeUpdates(locationListener);
    // }

    public static NeuGeoPoint fromWgs84ToBaidu(NeuGeoPoint wgsPoint) {
        NeuGeoPoint gcjPoint = new Converter().getEncryPoint(wgsPoint.getX(), wgsPoint.getY());
        GeoPoint bdGeoPoint = CoordinateConvert.bundleDecode(CoordinateConvert.fromWgs84ToBaidu(new GeoPoint(
                (int) (gcjPoint.getY() * 1000000), (int) (gcjPoint.getX() * 1000000))));
        return new NeuGeoPoint((double) bdGeoPoint.getLongitudeE6() / 1000000,
                (double) bdGeoPoint.getLatitudeE6() / 1000000);
    }

    public static NeuGeoPoint fromWgs84ToBaidu(String longitudeStr, String latitudeStr) {
        if (StringUtils.isBlank(longitudeStr) || StringUtils.isBlank(latitudeStr)) {
            return new NeuGeoPoint(0.0, 0.0);
        }
        // 由于CoordinateConvert.fromWgs84ToBaidu的实现与fromGcjToBaidu一样，所以需先将wgs坐标转换为gcj坐标
        NeuGeoPoint gcjPoint = new Converter().getEncryPoint(Double.parseDouble(longitudeStr),
                Double.parseDouble(latitudeStr));
        GeoPoint bdGeoPoint = CoordinateConvert.bundleDecode(CoordinateConvert.fromGcjToBaidu(new GeoPoint(
                (int) (gcjPoint.getY() * 1000000), (int) (gcjPoint.getX() * 1000000))));
        return new NeuGeoPoint((double) bdGeoPoint.getLongitudeE6() / 1000000,
                (double) bdGeoPoint.getLatitudeE6() / 1000000);
    }
}
