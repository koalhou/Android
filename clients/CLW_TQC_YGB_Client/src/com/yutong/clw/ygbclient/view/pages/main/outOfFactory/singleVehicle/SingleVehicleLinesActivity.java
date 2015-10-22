/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-6 上午9:39:55
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleVehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapStatus;
import com.baidu.mapapi.map.MKMapStatusChangeListener;
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.utils.CoordinateConvert;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.VehicheInfo;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.common.beans.line.CoordPoint;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StationType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.VehicheStatus;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.ToastUtils;
import com.yutong.clw.ygbclient.view.bean.push.AlarmPushBean;
import com.yutong.clw.ygbclient.view.bean.push.PushBean;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.common.BizRemind;
import com.yutong.clw.ygbclient.view.bizAccess.common.BizVehicheUpdate;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizOutOfFactory;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizOutOfFactoryIndex;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.common.map.CarOverlay;
import com.yutong.clw.ygbclient.view.common.map.CarOverlay.CarMapItem;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.OnItemTapListener;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.StationItem;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.StationItemPositionType;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation.StationRemindSettingActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.util.DensityUtil;

/**
 * 厂外-单个班车线路地图页面
 * 
 * @author zhouzc 2013-11-6 上午9:39:55
 */
public class SingleVehicleLinesActivity extends RemindAccessActivity implements
		OnClickListener {

	/***** 业务变量 *******/
	//
	private BizOutOfFactory biz;

	private BizRemind rbiz;

	//
	private BizOutOfFactoryIndex indexBiz;

	private BizVehicheUpdate vehiclebiz;

	// 当前车辆信息
	private VehicheInfo currentVehicle;

	private RemindInfo currentRemindInfo;

	/***** 控件变量 *******/
	// 顶部选择项
	private TextView txt_small;

	//
	private TextView tv_large;

	//
	private ImageView iv_remind;

	// 刷新定时器
	private Timer refreshTimer = null;

	// 刷新间隔
	private long refreshInterval = 1000;

	/***** 数据变量 *******/
	// 当前线路信息
	private LineInfo currentLine = null;

    
	// 当前日期
	private Date currentDate = Calendar.getInstance().getTime();

	private StatusRange currentStatusRange = StatusRange.MorningWork;

	/***** 标志变量 *******/
	// 是否正在加载车辆信息
	boolean isloadingbus = false;

	// 地图是否完成加载
	boolean isMapReady = false;

	// 当前路线是否已经加载
	boolean hasLoadedCurrentLine = false;

	// 弹出Loading框的KeyCode
	private final int LOADING_KEY_REMINDSUBMIT = 1;

	/***** 地图变量 *******/
	// MapView 是地图主控件
	private MapView mMapView = null;

	// 用MapController完成地图控制
	private MapController mMapController = null;

	// MKMapViewListener 用于处理地图事件回调
	private MKMapViewListener mMapListener = null;

	// 自己定位层
	private MyLocationOverlay selflocationLay;

	// 百度定位服务客户端对象
	private LocationClient mLocationClient = null;

	// 百度定位服务侦听器
	private BDLocationListener myLocationListener = null;

	// 车辆层
	private CarOverlay mCarOverlay;

	// 道路层
	private GraphicsOverlay roadsOverlay;

	// 站点层
	private StationOverlay stationlay;

	// 定位中心点经度
	private double cLat = 34.693970;

	// 定位中心点纬度
	private double cLon = 113.697207;

	// 地图层级
	private float currentlevel = 16;

	// 是否刷新
	private boolean autorefresh;

	// 当前站点
	private StationInfo currentSite;

	private Button refreshBtn,mResizeLineBtn;

	private boolean isRefreshClick = false;
	
	private Button mZoomOutBtn,mZoomInBtn;
	private boolean canZoom;
	
	private Context mContext;
	
	/********************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_outoffactory_singlevehicle_lines);
		mContext = this;
		setAllowNetworkCheck(true);
		initBiz();
		initViews();
		initMap();
		newIntent = getIntent();
	}

	private Intent newIntent = null;

	@Override
	protected void onNewIntent(Intent intent) {
		newIntent = intent;
		super.onNewIntent(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!isForceToDestory()) {
			mMapView.onResume();

			refreshInterval = PrefDataUtil.getMapRefreshInterval(this) * 1000;
			autorefresh = PrefDataUtil.getAutoMapRefresh(this);
			if (mLocationClient != null && myLocationListener != null)
				mLocationClient.registerLocationListener(myLocationListener);
			
			
			if(!this.isMapReady)
			{
			    return;
			}
			
			if (newIntent != null) {
				reloadDataFromIntent(newIntent);
				newIntent = null;
			} else {
				/**
				 * 刷新班车车辆
				 */

				// currentDate = Calendar.getInstance().getTime();

				if (DateUtils.dateToStr(new Date(), DateUtils.TIME_FORMAT)
						.equals(DateUtils.dateToStr(currentDate,
								DateUtils.TIME_FORMAT))) {

					if (autorefresh) {
						startLoadBus();
					} else {
						reloadBus(false);
					}
					reloadStation();
				}
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
		
		if (mLocationClient != null && myLocationListener != null)
			mLocationClient.unRegisterLocationListener(myLocationListener);
		stopLoadBus();
		if (vehiclebiz != null) {
			vehiclebiz.stop();
		}

	}

	@Override
	protected void onStop() {
		super.onStop();
		if (biz != null)
			biz.stop();
		if (vehiclebiz != null) {
			vehiclebiz.stop();
		}
	}

	@Override
	protected void destoryRunningWork() {
		super.destoryRunningWork();
		stopLoadBus();
		if (biz != null)
			biz.stop();
		if (vehiclebiz != null) {
			vehiclebiz.stop();
		}

	}

	private void reloadDataFromIntent(Intent intent) {

		if (intent.getExtras() == null)
			return;

		if (intent.getExtras().containsKey(ActivityCommConstant.PUSHBEAN)) {
			final AlarmPushBean bean = (AlarmPushBean) intent.getExtras()
					.getSerializable(ActivityCommConstant.PUSHBEAN);
			if (bean == null) {
				return;
			}
			currentDate = (Date) bean.getTime();
			currentStatusRange = bean.getStatusRange();
			stopRemindMedia();
			stopViberate();
			showRemindDialog(bean);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					tv_large.setText(bean.getVehicleNumber() + "号班车");
				}
			});

			if (bean.getRemindId() != null) {
				if (rbiz == null)
					rbiz = new BizRemind(this);

				indexBiz.GetRemindInfo(bean.getAreaType(), bean.getStationId(),
						bean.getStatusRange(), bean.getVehicleVin(),
						new BizResultProcess<RemindInfo>() {

							@Override
							public void onBizExecuteError(Exception exception,
									Error error) {
								Logger.i(getClass(), "提醒信息查询失败，无法加载改提醒对应的车辆信息");

							}

							@Override
							public void onBizExecuteEnd(
									BizDataTypeEnum datatype, RemindInfo t) {
								if (t == null) {
									Logger.i(getClass(),
											"提醒信息查询失败，无法加载改提醒对应的车辆信息");
									return;
								}
								// 填充数据
								VehicheInfo info = new VehicheInfo();
								info.vehicle_vin = t.vehiche_vin;
								info.vehiche_number = t.vehiche_number;
								info.line_id = bean.getLineid();
								currentVehicle = info;
								loadNewVehicle(currentVehicle);
								if(PrefDataUtil.getAutoMapRefresh(SingleVehicleLinesActivity.this)){
									startLoadBus();
								}
								if (bean.getStationId() != null) {
									biz.getStation(
											bean.getStationId(),
											new BizResultProcess<StationInfo>() {

												@Override
												public void onBizExecuteError(
														Exception exception,
														Error error) {
													Logger.i(getClass(),
															"站点信息查询失败，无法加载改提醒对应的信息");
												}

												@Override
												public void onBizExecuteEnd(
														BizDataTypeEnum datatype,
														StationInfo t) {
													if (t == null) {
														Logger.i(getClass(),
																"站点信息查询失败，无法加载改提醒对应的信息");
													}
													currentSite = t;
													reloadStation();
												}
											});
								}
								currentRemindInfo = t;
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										if (currentRemindInfo != null
												&& currentRemindInfo
														.getRemind_status() == RemindStatus.Open) {
											iv_remind
													.setImageResource(R.drawable.ic_clocked_white);
										} else {
											iv_remind
													.setImageResource(R.drawable.ic_clock_white);
										}
									}
								});

							}
						});

			}
			return;
		}

		loadActivitySendData();

	}

	private void loadActivitySendData() {
		currentDate = (Date) getIntent().getExtras().getSerializable(
				ActivityCommConstant.DATE);

		/**
		 * 加载车辆信息
		 */
		VehicheInfo info = loadVehicheInfoFromIntent(getIntent());
		if (info != null && info.getVehicle_vin() != null) {
			currentVehicle = info;
			loadNewVehicle(currentVehicle);
			if(PrefDataUtil.getAutoMapRefresh(this)){
				startLoadBus();
			}else{
				reloadBus(true);
			}
		}
		currentStatusRange = (StatusRange) getIntent().getExtras()
				.getSerializable(ActivityCommConstant.STATUSRANGE);
		/**
		 * 加载当前站点状态
		 */
		if (currentSite == null) {
			currentSite = loadStationInfoFromIntent(getIntent());
		}
		reloadStation();
	}

	private void reloadStation() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (currentSite != null) {
					createCurrentStation();
					CoordPoint coordPoint = new CoordPoint(currentSite.gps_lat,
							currentSite.gps_lon);
					List<CoordPoint> stationPoints = new ArrayList<CoordPoint>();
					stationPoints.add(coordPoint);
					// if (currentSite.isStatus()) {
					// iv_remind.setImageResource(R.drawable.ic_clocked_white);
					// } else {
					// iv_remind.setImageResource(R.drawable.ic_clock_white);
					// }
						indexBiz.GetRemindInfo(currentSite.area_type,
								currentSite.id, currentStatusRange,
								currentVehicle.vehicle_vin,
								new BizResultProcess<RemindInfo>() {

									@Override
									public void onBizExecuteError(
											Exception exception, Error error) {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												dismissLoading(0);
												ToastMessage("获取提醒信息异常");

											}
										});

									}

									@Override
									public void onBizExecuteEnd(
											BizDataTypeEnum datatype,
											final RemindInfo t) {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												dismissLoading(0);
												currentRemindInfo = t;
												if (currentRemindInfo != null
														&& currentRemindInfo
																.getRemind_status() == RemindStatus.Open) {
													iv_remind
															.setImageResource(R.drawable.ic_clocked_white);
												} else {
													iv_remind
															.setImageResource(R.drawable.ic_clock_white);
												}

											}
										});
									}
								});

					// SingleVehicleLinesActivity.this.runOnUiThread(new
					// Runnable() {
					// @Override
					// public void run() {
					//
					// StationInfo stationInfo = new
					// StationInfoDao(YtApplication.getInstance()).getStation(currentSite.id,
					// ProxyManager.getInstance(getApplicationContext()).getUserCode());
					// if (stationInfo != null &&
					// stationInfo.isStatus())
					// {
					//
					// iv_remind.setImageResource(R.drawable.ic_clocked_white);
					// }
					// else
					// {
					// iv_remind.setImageResource(R.drawable.ic_clock_white);
					// }
					//
					// }
					// });

				}
			}
		});

	}

	private StationInfo loadStationInfoFromIntent(Intent intent) {
		StationInfo info = (StationInfo) intent.getExtras().getSerializable(
				ActivityCommConstant.STATION_INFO);
		return info;
	}

	@Override
	protected void onDestroy() {
		/**
		 * MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		 */
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	private void initBiz() {
		biz = new BizOutOfFactory(this);
		indexBiz = new BizOutOfFactoryIndex(this);
	}

	private void initViews() {
		findViewById(R.id.btn_amosvl_refresh).setOnClickListener(this);
		findViewById(R.id.ll_amosvl_remind).setOnClickListener(this);
		iv_remind = (ImageView) findViewById(R.id.iv_amosvl_remind);
		
		mZoomOutBtn = (Button) findViewById(R.id.btn_zoom_out);
		mZoomOutBtn.setOnClickListener(this);
		mZoomInBtn = (Button) findViewById(R.id.btn_zoom_in);
		mZoomInBtn.setOnClickListener(this);
		
		mResizeLineBtn = (Button) findViewById(R.id.btn_ami_resize);
		mResizeLineBtn.setOnClickListener(this);
	}

	private void initMap() {

		mMapView = (MapView) findViewById(R.id.bmv_amosvl_map);
		mMapController = mMapView.getController();
		mMapController.enableClick(true);
		// 设置地图缩放级别
		mMapController.setZoom(currentlevel);
		// 显示内置缩放控件
		mMapView.setBuiltInZoomControls(false);
		// 设置中心点为宇通
		GeoPoint centerpoint = new GeoPoint((int) (cLat * 1E6),
				(int) (cLon * 1E6));
		mMapController.setCenter(centerpoint);
		mMapController.setRotationGesturesEnabled(true);
		mMapController.setOverlookingGesturesEnabled(false);
		mMapController.setCompassMargin(mMapView.getWidth() - 20, 20);

		mMapListener = new MKMapViewListener() {
			@Override
			public void onMapMoveFinish() {
				/**
				 * 在此处理地图移动完成回调 缩放，平移等操作完成后，此回调被触发
				 */
				Logger.i(getClass(), "地图onMapMoveFinish");
				// 刷新地图上面得车辆旋转方向
				if (mCarOverlay != null)
					mCarOverlay.adapteToMapRotate();
			}

			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
			}

			@Override
			public void onGetCurrentMap(Bitmap b) {
			}

			@Override
			public void onMapAnimationFinish() {
			}

			@Override
			public void onMapLoadFinish() {
				Logger.i(getClass(), "地图加载完成");
				//TODO
				Logger.e(getClass(), "isMapLoad = ture--------------------------------------------------");
				doworkWhenMapLoaded();
			}
		};
		mMapView.regMapViewListener(YtApplication.getInstance()
				.getBaiduMapManager().getBMapManager(), mMapListener);

		// 实现对地图状态改变的处理
		MKMapStatusChangeListener slistener = new MKMapStatusChangeListener() {
			public void onMapStatusChange(MKMapStatus mapStatus) {
				float zoom = mapStatus.zoom; // 地图缩放等级
				if (zoom != currentlevel) {
					currentlevel = zoom;
				}
				// int overlooking = mapStatus.overlooking; // 地图俯视角度
				// int rotate = mapStatus.rotate; // 地图旋转角度
			}
		};
		// 为 mapview 注册地图状态监听者。本方法只能注册一次，多次注册会覆盖
		mMapView.regMapStatusChangeListener(slistener);

		mMapView.regMapTouchListner(new MKMapTouchListener() {

			@Override
			public void onMapLongClick(GeoPoint arg0) {
			}

			@Override
			public void onMapDoubleClick(GeoPoint arg0) {
			}

			@Override
			public void onMapClick(GeoPoint arg0) {
				// runOnUiThread(new Runnable()
				// {
				// @Override
				// public void run()
				// {
				// if (stationlay != null)
				// stationlay.clearAllFromEdit();
				// }
				// });

			}
		});
		initMapOvarlays();

	}

	private void initMapOvarlays() {
		roadsOverlay = new GraphicsOverlay(mMapView);
		mMapView.getOverlays().add(roadsOverlay);

		stationlay = new StationOverlay(this, mMapView, getResources()
				.getDrawable(R.drawable.ic_station_active_current),
				getResources().getDrawable(R.drawable.ic_station_normal),
				getResources().getDrawable(R.drawable.ic_station_active),
				getResources()
						.getDrawable(R.drawable.ic_station_normal_current),
				null, null, null, null);

		stationlay.setOnItemRemindTapListener(new OnItemTapListener() {

			@Override
			public void OnItemTap(final StationItem value) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ToastMessage(value.getName());
					}
				});

			}
		});
		initSelfLocationLay();
	}
	
	private void initSelfLocationLay(){
		
		selflocationLay = new MyLocationOverlay(mMapView);
		selflocationLay.setMarker(getResources().getDrawable(R.drawable.ic_myself));
        mMapView.refresh();
        selflocationLay.enableCompass();
        selflocationLay.setLocationMode(LocationMode.NORMAL);
        mMapView.getOverlays().add(selflocationLay);
	}
	
	private void doworkWhenMapLoaded() {
		isMapReady = true;
		loadMySelf();
		
		//OnResure
        if (newIntent != null) {
            reloadDataFromIntent(newIntent);
            newIntent = null;
        } else {
            /**
             * 刷新班车车辆
             */

            // currentDate = Calendar.getInstance().getTime();

            if (DateUtils.dateToStr(new Date(), DateUtils.TIME_FORMAT)
                    .equals(DateUtils.dateToStr(currentDate,
                            DateUtils.TIME_FORMAT))) {

                if (autorefresh) {
                    startLoadBus();
                } else {
//                  reloadBus(false);
                    if(PrefDataUtil.getAutoMapRefresh(this)){
                        startLoadBus();
                    }
                }
            }
        }
	}

	private void loadMySelf() {
		Logger.i(this.getClass(), "开始加载自己定位数据");
//		selflocationLay.setMarker(getResources().getDrawable(
//				R.drawable.ic_myself));
//		selflocationLay.enableCompass();
//		selflocationLay.setLocationMode(LocationMode.NORMAL);

		if (selflocationLay == null){
	         initSelfLocationLay();
		}
	     
		 // 使用百度定位服务
	    initBaidulocClient();
	}
	
	private void matchToLine(LineInfo info) {
		if (info == null || info.getPoints() == null
			|| info.getPoints().size() == 0)
		return;
		matchtopoints(info.getPoints());
	}
	
	private void matchtopoints(List<CoordPoint> points) {
		try {
			if (points == null || points.size() == 0)
			{
	        GeoPoint baidupoint = CoordinateConvert.fromWgs84ToBaidu(new GeoPoint(
	                (int) (currentSite.getGps_lat() * 1E6), (int) (currentSite
	                        .getGps_lon() * 1E6)));

	        mMapController.setCenter(baidupoint);
				return;
			}
			CoordPoint fp = points.get(0);
			double lat_max = fp.gps_lat;
			double lat_min = fp.gps_lat;
			double lon_max = fp.gps_lon;
			double lon_min = fp.gps_lon;
			for (CoordPoint point : points) {
				if (point.gps_lat < lat_min) {
					lat_min = point.gps_lat;
				} else if (point.gps_lat > lat_max) {
					lat_max = point.gps_lat;
				}

				if (point.gps_lon < lon_min) {
					lon_min = point.gps_lon;
				} else if (point.gps_lon > lon_max) {
					lon_max = point.gps_lon;
				}
			}
			GeoPoint centerp = CoordinateConvert.fromWgs84ToBaidu(new GeoPoint(
					(int) ((lat_max + lat_min) / 2 * 1E6),
					(int) ((lon_max + lon_min) / 2 * 1E6)));
			int extend = 5000;

			    mMapController.zoomToSpan(
					(int) (lat_max * 1E6 - lat_min * 1E6 + extend),
					(int) (lon_max * 1E6 - lon_min * 1E6 + extend));

		
			mMapController.setCenter(centerp);
			//mMapController.setZoom(currentlevel);
			// mMapController.animateTo(centerp);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	private void initBaidulocClient() {
		mLocationClient = YtApplication.getInstance().getBaiduMapManager()
				.getLocationClient();
		myLocationListener = new BDLocationListener() {

			@Override
			public void onReceivePoi(BDLocation arg0) {

			}

			@Override
			public void onReceiveLocation(BDLocation arg0) {
				updateToNewBDLocation(arg0);
			}
		};

		mLocationClient.registerLocationListener(myLocationListener);
		if (!mLocationClient.isStarted())
			mLocationClient.start();
	}

	private void updateToNewBDLocation(BDLocation location) {
		if (location == null)
			return;
		// 更新用户当前坐标
		LocationData ld = new LocationData();
		ld.latitude = location.getLatitude();
		ld.longitude = location.getLongitude();
		// ld.direction = location.getDerect();
		selflocationLay.setData(ld);
		// mMapController.animateTo(new GeoPoint((int)(ld.latitude*1E6),
		// (int)(ld.longitude*1E6)));
		mMapView.refresh();
	}

	private void loadNewVehicle(final VehicheInfo info) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (info.getVehiche_number() != null
						&& !info.getVehiche_number().equals(""))
					tv_large.setText(info.getVehiche_number() + "号班车");
				loadLineByVehicle(info);
			}
		});
		
	}

	private void loadLineByVehicle(VehicheInfo vehicle) {
		if (vehicle == null)
			return;
		biz.getLines(Arrays.asList(vehicle.getLine_id()),
				new BizResultProcess<List<LineInfo>>() {
					@Override
					public void onBizExecuteError(Exception exception,
							Error error) {
						Logger.e(getClass(), "获取路线信息异常");
					}

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							List<LineInfo> t) {
						if (t == null || t.size() == 0) {
							Logger.i(getClass(), "获取到的路线信息为空");
							return;
						}
						loadLineInfo(t.get(0));
					}
				});
	}

	@Override
	protected void loadTitleByPageSetting(Button btn_left, Button btn_right,
			RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
			TextView tv_small) {

		btn_left.setBackgroundResource(R.drawable.bg_prevbtn);
		btn_left.setOnClickListener(this);
		btn_right.setVisibility(View.INVISIBLE);
		tv_large.setText("班车地图");
		tv_small.setText("---");

		tv_small.setVisibility(View.VISIBLE);
		iv_tri.setVisibility(View.GONE);// 右下角符号

		this.tv_large = tv_large;
		this.txt_small = tv_small;
	}

	private void loadLineInfo(final LineInfo info) {
		currentLine = info;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (info == null) {
					Logger.i(getClass(), "开始加载线路：线路为null");
					return;
				}
				Logger.i(getClass(), "开始加载线路：" + info.getLine_id());
				txt_small.setText(info == null ? "" : info.getLine_name());
				roadsOverlay.removeAll();// 清除原线路
				if (info != null) {
					createLine(info);
					createStations(info);
				}
				createCurrentStation();
			}
		});

	}

	private void createLine(LineInfo info) {
		Logger.i(getClass(), "绘制线路[" + info.getLine_id() + "]");
		// 设置样式
		Symbol lineSymbol = new Symbol();
		Symbol.Color lineColor = lineSymbol.new Color();
		lineColor.red = 0;
		lineColor.green = 0;
		lineColor.blue = 255;
		lineColor.alpha = 180;
		int width = DensityUtil.dip2px(this, 2);
		lineSymbol.setLineSymbol(lineColor, width);
		List<GeoPoint> pointlist = new ArrayList<GeoPoint>();
		for (CoordPoint cp : info.getPoints()) {
			pointlist.add(CoordinateConvert.fromWgs84ToBaidu(new GeoPoint(
					(int) (cp.gps_lat * 1E6), (int) (cp.gps_lon * 1E6))));
		}
		GeoPoint[] points = new GeoPoint[pointlist.size()];
		pointlist.toArray(points);// TODO 待验证
		createLine(points, lineSymbol);
		matchtopoints(info.getPoints());
	}

	private void createStations(LineInfo info) {
		// 绘制站点
		if (info == null)
			return;
		List<StationItem> items = new ArrayList<StationOverlay.StationItem>();
		// StationItem stationItemTmp = null;
		for (StationInfo station : info.getStations()) {
			// 更新地图站点
			StationItemPositionType ptype = station.getType() == StationType.StartStaion ? StationItemPositionType.START
					: (station.getType() == StationType.EndStaion ? StationItemPositionType.END
							: StationItemPositionType.CENTER);
			GeoPoint baidupoint = CoordinateConvert
					.fromWgs84ToBaidu(new GeoPoint(
							(int) (station.getGps_lat() * 1E6), (int) (station
									.getGps_lon() * 1E6)));
			StationItem item = new StationItem(station.id, baidupoint,
					station.getName(), station.getAlias(), 1, false, ptype);
			item.setNeedEdit(false);
			items.add(item);

			/*
			 * currentSite = loadStationInfoFromIntent(getIntent()); if
			 * (currentSite != null &&
			 * currentSite.getId().equals(station.getId())) { stationItemTmp =
			 * new StationItem(station.id, baidupoint, station.getName(),
			 * station.getAlias(), 1, false, ptype); }
			 */
		}

		stationlay.appendData(items);

		/*
		 * if(stationItemTmp != null){ stationlay.changeToEdit(stationItemTmp);
		 * stationlay.drawStationName(stationItemTmp); }
		 */

		mMapView.refresh();

	}

	private void createCurrentStation() {
		Logger.i(this.getClass(), "开始绘制当前站点");
		if (currentSite == null) {
			Logger.i(this.getClass(), "当前站点为空");
			return;
		}
		StationItemPositionType ptype = StationItemPositionType.CENTER;
		GeoPoint baidupoint = CoordinateConvert.fromWgs84ToBaidu(new GeoPoint(
				(int) (currentSite.getGps_lat() * 1E6), (int) (currentSite
						.getGps_lon() * 1E6)));
		StationItem ci = new StationItem(currentSite.id, baidupoint,
				currentSite.getName(), currentSite.getName(), 1, true, ptype);
		ci.setNeedEdit(false);
		stationlay.addItem(ci);
		//mMapController.setCenter(baidupoint);
		// stationlay.appendData(Arrays.asList(ci));
		// stationlay.changeToEdit(ci);

		mMapView.refresh();
	}

	private void createLine(GeoPoint[] gpoints, Symbol lineSymbol) {
		Logger.i(this.getClass(), "开始绘制道路地图线");
		// 直接添加线层，效果不佳有锯齿
		// 构建线
		Geometry lineGeometry = new Geometry();
		lineGeometry.setPolyLine(gpoints);
		// 生成Graphic对象
		Graphic lineGraphic = new Graphic(lineGeometry, lineSymbol);
		roadsOverlay.setData(lineGraphic);
		mMapView.refresh();
	}

	private void reloadBus(boolean clearbefore) {
		Logger.i(getClass(), "开始刷新班车数据");
		if (isloadingbus) {
			Logger.i(getClass(), "发现已经再刷新，取消当前刷新操作");
			return;
		}
		isloadingbus = true;
		Logger.i(getClass(), "刷新班车数据");
		if (mCarOverlay == null) {
			mCarOverlay = new CarOverlay(this, mMapView);
		}
		if (clearbefore)
			mCarOverlay.removeAll();

		if (currentVehicle == null) {
			return;
		}
		synchronized (currentVehicle) {
			// 如果路线换了，就要先清空下数据重新加载
			final VehicheInfo car = currentVehicle;
			// 获取当前车辆定位数据并显示
			if (vehiclebiz == null)
				vehiclebiz = new BizVehicheUpdate(this);
			vehiclebiz.getRealTimeVehicheData(Arrays.asList(car.vehicle_vin),
					Arrays.asList(car.getLine_id()),
					new BizResultProcess<List<VehicleRealtime>>() {
						@Override
						public void onBizExecuteError(Exception exception,
								Error error) {
							Logger.e(getClass(), "查询车辆【" + car.vehicle_vin
									+ "】实时信息异常");
							runOnUiThread(new Runnable() {
								@Override
								public void run() {

									mCarOverlay.setOnLineStatus(
											car.vehicle_vin, false);

								}
							});
						}

						@Override
						public void onBizExecuteEnd(BizDataTypeEnum datatype,
								List<VehicleRealtime> t) {
							if (t == null || t.size() == 0) {
								Logger.e(getClass(), "查询车辆【" + car.vehicle_vin
										+ "】实时信息为NULL");
								mCarOverlay.removeItem(car.vehicle_vin);
								return;
							}
							final VehicleRealtime realinfo = t.get(0);
							runOnUiThread(new Runnable() {
								public void run() {
									mCarOverlay.addOrUpdate(new CarMapItem(
											car.vehicle_vin,
											CoordinateConvert
													.fromWgs84ToBaidu(new GeoPoint(
															(int) (realinfo.gps_lat * 1E6),
															(int) (realinfo.gps_lon * 1E6))),
											realinfo.vehicle_number,
											realinfo.direction,
											VehicheStatus.IsVehicheStatus(
													realinfo.status,
													VehicheStatus.Online)));
									/* 加载车辆位置 */
//									mMapController.setCenter(new GeoPoint(
//											(int) (realinfo.gps_lat * 1E6),
//											(int) (realinfo.gps_lon * 1E6)));
								}
							});

						}

					});

		}
		mMapView.refresh();
		isloadingbus = false;
	}

	private void restartRefreshTimer() {
		if (refreshTimer != null)
			refreshTimer.cancel();
		refreshTimer = new Timer(true);
		refreshTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				reloadBus(false);
			}
		}, 0, refreshInterval);
	}

	private void startLoadBus() {
		Logger.i(this.getClass(), "准备刷新班车数据");
		if (!isActive()) {
			Logger.i(this.getClass(), "当前页面不在最前，停止刷新");
			return;
		}
		restartRefreshTimer();
	}

	private void stopLoadBus() {
		Logger.i(this.getClass(), "停止刷新班车数据");
		if (refreshTimer != null) {
			refreshTimer.cancel();
		}
	}

	private VehicheInfo loadVehicheInfoFromIntent(Intent intent) {
		VehicheInfo info = (VehicheInfo) intent.getExtras().getSerializable(
				ActivityCommConstant.VEHICHLE);
		return info;
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_it_left:
			finish();
			break;
		case R.id.btn_amosvl_refresh:
			isRefreshClick = true;

			if (DateUtils.dateToStr(new Date(), DateUtils.TIME_FORMAT).equals(
					DateUtils.dateToStr(currentDate, DateUtils.TIME_FORMAT))) {
				reloadBus(false);
			}

			break;
		case R.id.ll_amosvl_remind:
			if (currentSite == null || currentVehicle == null) {
				Logger.e(this.getClass(), "站点或VehicleRealtime为null。");
				return;
			}

			if (currentRemindInfo == null) {
				// p = premindinfo;

				// zhouzc 修改跳转到提醒设置界面的参数问题
				currentRemindInfo = new RemindInfo();
				currentRemindInfo.setId(null);
				currentRemindInfo.setStation_id(currentSite.getId());
				currentRemindInfo.setStation_name(currentSite.getName());
				currentRemindInfo.setArea_type(currentSite.getArea_type());
				currentRemindInfo.setLine_range(LineRange.FactoryOuter);
				currentRemindInfo.remind_range = RemindRange.StationAndVehiche;
				currentRemindInfo.vehiche_vin = currentVehicle.getVehicle_vin();
				currentRemindInfo.vehiche_number = currentVehicle
						.getVehiche_number();
			}
			currentRemindInfo.setStatus_range(currentStatusRange);
			Bundle b = new Bundle();
			b.putSerializable(ActivityCommConstant.REMINDINFO,
					currentRemindInfo);
			ActivityUtils.changeActivity(SingleVehicleLinesActivity.this,
					StationRemindSettingActivity.class, b);

			break;
			
			/*缩小*/
		case R.id.btn_zoom_out:
			if(mMapController!=null){
				canZoom = mMapController.zoomIn();
				if(!canZoom){
					mZoomOutBtn.setEnabled(false);
					mZoomInBtn.setEnabled(true);
				}else{
					mZoomOutBtn.setEnabled(true);
					mZoomInBtn.setEnabled(true);
				}
			}
			break;
			
			/*放大*/
		case R.id.btn_zoom_in:
			if(mMapController!=null){
				canZoom = mMapController.zoomOut();
				if(!canZoom){
					mZoomOutBtn.setEnabled(true);
					mZoomInBtn.setEnabled(false);
				}else{
					mZoomOutBtn.setEnabled(true);
					mZoomInBtn.setEnabled(true);
				}
			}
			break;
			
		case R.id.btn_ami_resize:
			if (mMapController != null) {
				if (mCarOverlay != null)
					mCarOverlay.adapteToMapRotate();
				
				
				if (currentLine != null) {
					if(currentLine.getPoints().size() == 0){
						ToastUtils.show(mContext, "暂无线路信息,不能重置线路");
					}else{
						matchToLine(currentLine);
					}
				}else{
					ToastUtils.show(mContext, "暂无线路信息,不能重置线路");
				}
				// 这个是动画效果要放在后面，否则会阻碍后面的动作
				mMapController.setRotation(0);
			}
			break;
		default:
			break;
		}

	}

	/**
	 * 接收站点提醒
	 */
	@Override
	protected void onReceivedPushMessage(PushBean msg) {
		super.onReceivedPushMessage(msg);
	}
}
