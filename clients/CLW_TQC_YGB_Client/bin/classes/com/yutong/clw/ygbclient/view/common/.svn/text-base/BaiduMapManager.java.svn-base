package com.yutong.clw.ygbclient.view.common;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;
import com.yutong.clw.ygbclient.view.common.map.BaiduMapGeneralListener;

/**
 * 地图控件管理类
 * 
 * @author zhouzc
 * 
 */
public class BaiduMapManager {

	/*是否打开GPS标志*/
	private boolean isOpenGps = false;
	
	public BaiduMapManager() {
		
	}

	private boolean isKeyRight = true;

	private String strKey = null;

	private BMapManager bMapManager;

	public void initial() {

		if (bMapManager != null) {
			return;
		}
		/**
		 * 使用地图sdk前需先初始化BMapManager. BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
		 * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
		 */
		if (YtApplication.getInstance().getBaiduMapManager().getBMapManager() == null) {
			YtApplication
					.getInstance()
					.getBaiduMapManager()
					.setBMapManager(
							new BMapManager(YtApplication.getInstance()));
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			YtApplication
					.getInstance()
					.getBaiduMapManager()
					.getBMapManager()
					.init(YtApplication.getInstance().getBaiduMapManager()
							.getStrKey(), new BaiduMapGeneralListener());
		}
	}

	/**
	 * 获取百度序列Key
	 * 
	 * @return
	 */
	public String getStrKey() {
		if (strKey == null)
			strKey = SysConfigUtil.getBaiduMapKey();
		return strKey;
	}

	/**
	 * 当前key是否正确
	 * 
	 * @return
	 */
	public boolean isKeyRight() {
		return isKeyRight;
	}

	public void setKeyRight(boolean isKeyRight) {
		this.isKeyRight = isKeyRight;
	}

	/**
	 * 获取百度地图管理器--多个MapView可共用一个
	 * 
	 * @return
	 */
	public BMapManager getBMapManager() {
		return bMapManager;
	}

	public void setBMapManager(BMapManager inFactoryMapManager) {
		this.bMapManager = inFactoryMapManager;
	}

	private LocationClient mLocationClient;

	/**
	 * 获取定位服务客户端
	 * 
	 * @return
	 */
	public LocationClient getLocationClient() {
		if (mLocationClient == null) {
			mLocationClient = new LocationClient(YtApplication.getInstance());
			mLocationClient.setAK(getStrKey());
			LocationClientOption option = new LocationClientOption();
			option.setPriority(LocationClientOption.GpsFirst);
			option.setOpenGps(true);// 设置是否打开gps，使用gps前提是用户硬件打开gps。默认是不打开gps的。
			option.setAddrType("null");// 值为 all时，表示返回地址信息,其他值都表示不返回地址信息。
			option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
			// (国测局经纬度坐标系 coor=gcj02,
			// 百度墨卡托坐标系 coor=bd09,
			// 百度经纬度坐标系 coor=bd09ll)
			option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
			option.disableCache(false);// true表示禁用缓存定位，false表示启用缓存定位。
			option.setProdName(YtApplication.getInstance().getPackageName());
			option.setPoiNumber(1); // 最多返回POI个数
			option.setPoiDistance(1000); // poi查询距离
			option.setPoiExtraInfo(false);// 设置是否返回POI的电话和地址等详细信息。默认值为false，即不返回POI的电话和地址信息。
			option.setServiceName("com.baidu.location.f");

			// option.setTimeOut(20000);
			mLocationClient.setLocOption(option);
		}

		return mLocationClient;
	}

}
