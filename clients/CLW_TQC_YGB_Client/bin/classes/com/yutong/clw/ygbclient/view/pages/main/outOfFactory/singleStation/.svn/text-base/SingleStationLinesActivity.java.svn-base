package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.DialogInterface;
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
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.common.beans.line.CoordPoint;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.beans.line.StationVehicleRealTimeInfo;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StationType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.VehicheStatus;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.ToastUtils;
import com.yutong.clw.ygbclient.dao.linestation.StationInfoDao;
import com.yutong.clw.ygbclient.view.bean.push.AlarmPushBean;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.common.BizVehicheUpdate;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizOutOfFactory;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.common.map.CarOverlay;
import com.yutong.clw.ygbclient.view.common.map.CarOverlay.CarMapItem;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.StationItem;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.StationItemPositionType;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.util.DensityUtil;
import com.yutong.clw.ygbclient.view.widget.CustomDialog;
import com.yutong.clw.ygbclient.view.widget.CustomDialog.Builder;
import com.yutong.clw.ygbclient.view.widget.PopCheckList;
import com.yutong.clw.ygbclient.view.widget.PopCheckList.OnCheckChangedListener;

/**
 * 厂外-单个站点路线地图显示界面
 * 
 * @author zhouzc
 */
public class SingleStationLinesActivity extends RemindAccessActivity implements
		OnClickListener {

	/***** 业务变量 *******/
	//
	private BizOutOfFactory biz;

	//
	private BizVehicheUpdate vehiclebiz;

	private StationInfo currentStation;

	/***** 控件变量 *******/
	// 弹出线路选择框
	private PopCheckList popcl;
	
	private int lastSelectedIndex = 0; 
	
	// 顶部选择项
	private TextView txt_small;

	//
	private TextView tv_large;

	// 收藏站点
	private TextView collecting_stations;

	//
	private ImageView iv_tri;

	//
	private ImageView iv_remind;

	//
	private ImageView iv_collect;

	// 刷新定时器
	private Timer refreshTimer = null;

	// 刷新间隔
	private long refreshInterval = 30000;

	// 当前日期
	private Date currentDate = Calendar.getInstance().getTime();

	/***** 数据变量 *******/
	// 路线坐标点
	// private GeoPoint[] linePoints;

	// 所有线路信息
	private List<LineInfo> allLines = null;

	// 当前车辆数据
	private HashMap<String, VehicleRealtime> currentCars;

	/***** 标志变量 *******/
	// 是否正在加载车辆信息
	boolean isloadingbus = false;

	// 地图是否完成加载
	boolean ismapready = false;

	// 当前路线是否已经加载
	boolean hasLoadedCurrentLine = false;

	// 弹出Loading框的KeyCode
	private final int REQUEST_CODE_REMINDSUBMIT = 1;

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
	
	private Button mZoomOutBtn,mZoomInBtn,mResizeLineBtn;
	private boolean canZoom;
	
	private Context mContext;

	/***********************************/
	/***********************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_outoffactory_singlestation_lines);
		setAllowNetworkCheck(true);
		mContext = this;
		initBiz();
		initData();
		initViews();
		initMap();
		newIntent=getIntent();
	}

	private void initData() {
		currentCars = new HashMap<String, VehicleRealtime>();
		allLines = new LinkedList<LineInfo>();
	}

	private void initViews() {
		
		mResizeLineBtn = (Button) findViewById(R.id.btn_ami_resize);
		mResizeLineBtn.setOnClickListener(this);
		
		mZoomOutBtn = (Button) findViewById(R.id.btn_zoom_out);
		mZoomOutBtn.setOnClickListener(this);
		mZoomInBtn = (Button) findViewById(R.id.btn_zoom_in);
		mZoomInBtn.setOnClickListener(this);
		
		findViewById(R.id.btn_amosl_refresh).setOnClickListener(this);
		findViewById(R.id.rl_amosl_bottomleft).setOnClickListener(this);

		/* 到站提醒 */
		findViewById(R.id.rl_amosl_bottomright).setOnClickListener(this);

		iv_collect = (ImageView) findViewById(R.id.iv_amosl_left);

		/* 到站提醒、闹钟图标 */
		iv_remind = (ImageView) findViewById(R.id.iv_amosl_right);
		collecting_stations = (TextView) findViewById(R.id.collecting_stations);

		popcl = new PopCheckList(this);
		popcl.setData(Arrays.asList("早班", "晚班"), 0);
		popcl.setOnCheckChangedListener(new OnCheckChangedListener() {

			@Override
			public void OnChecked(int index, String value) {
				txt_small.setText(value);
				popcl.dismiss();
				
				if(lastSelectedIndex == index){
					return;
				}
				
				if (currentStation != null)
					
					
					lastSelectedIndex = index;
					if (index == 0) {
						currentStatusRange = StatusRange.MorningWork;
						currentStation.setStatus_range(StatusRange.MorningWork);
						loadVehicles();
					} else if (index == 1) {
						currentStatusRange = StatusRange.NightWork;
						currentStation.setStatus_range(StatusRange.NightWork);
						loadVehicles();
					}
				}
		});
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void loadTitleByPageSetting(Button btn_left, Button btn_right,
			RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
			TextView tv_small) {
		btn_left.setBackgroundResource(R.drawable.bg_prevbtn);
		btn_right.setVisibility(View.INVISIBLE);
		tv_large.setText("站点名称");
		tv_small.setText("");

		rl_center.setOnClickListener(this);
		btn_left.setOnClickListener(this);
		btn_right.setOnClickListener(this);

		tv_small.setVisibility(View.GONE);
		iv_tri.setVisibility(View.GONE);
		this.tv_large = tv_large;
		this.txt_small = tv_small;
		this.iv_tri = iv_tri;
	}

	private void initBiz() {
		biz = new BizOutOfFactory(this);
	}

	private void initMap() {

		mMapView = (MapView) findViewById(R.id.bmv_amosl_map);
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
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (stationlay != null)
							stationlay.clearAllFromEdit();
					}
				});

			}
		});
		initMapOvarlays();
	}

	private void initMapOvarlays() {
		selflocationLay = new MyLocationOverlay(mMapView);
		roadsOverlay = new GraphicsOverlay(mMapView);
		mMapView.getOverlays().add(roadsOverlay);
		stationlay = new StationOverlay(this, mMapView, getResources()
				.getDrawable(R.drawable.ic_station_active_current),
				getResources().getDrawable(R.drawable.ic_station_normal),
				getResources().getDrawable(R.drawable.ic_station_active),
				getResources()
						.getDrawable(R.drawable.ic_station_normal_current),
				null, null, null, null);
		mMapView.getOverlays().add(selflocationLay);
	}

	private void doworkWhenMapLoaded() {
		ismapready = true;
		loadMySelf();
	}

	/***********************************/
	/***********************************/

	private void reloadByStationInfo(StationInfo info, StatusRange statusr) {
		Logger.i(getClass(), "开始刷新当前站点信息");
		currentStation = info;
		if (info == null) {
			tv_large.setText("");
			txt_small.setText("");
			txt_small.setVisibility(View.GONE);
			iv_tri.setVisibility(View.INVISIBLE);
			return;
		} else {
			popcl.setSelection(statusr == StatusRange.MorningWork ? 0 : 1,
					false);
			txt_small.setText(statusr == StatusRange.MorningWork ? "早班" : "晚班");
			txt_small.setVisibility(View.VISIBLE);
			tv_large.setText(info.getName());
			iv_tri.setVisibility(View.VISIBLE);
			iv_collect
					.setImageResource(info.isFavorites() ? R.drawable.ic_favored_white
							: R.drawable.ic_favor_white);

			SingleStationLinesActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {

					StationInfo stationInfo = new StationInfoDao(YtApplication
							.getInstance()).getStation(currentStation.id,
							ProxyManager.getInstance(getApplicationContext())
									.getUserCode());
					if (stationInfo != null && stationInfo.isStatus()) {

						iv_remind.setImageResource(R.drawable.ic_clocked_white);
					} else {
						iv_remind.setImageResource(R.drawable.ic_clock_white);
					}

				}
			});

			// iv_remind
			// .setImageResource(!info.isStatus() ? R.drawable.ic_clock_white
			// : R.drawable.ic_clocked_white);
		}
		loadVehicles();
	}

	private boolean isloadingVehicles = false;

	private boolean autorefresh;

	private void loadVehicles() {
		if (isloadingVehicles)
			return;
		isloadingVehicles = true;
		Logger.i(getClass(), "开始加载车辆信息");

		if (currentStation == null) {
			Logger.e(getClass(), "当前无站点信息，无法加载车辆信息");
			return;
		}
		clearAllInfos();
		biz.getVehiches(currentStation.getId(), currentStation.getArea_type(),
				currentStatusRange,
				new BizResultProcess<List<StationVehicleRealTimeInfo>>() {

					@Override
					public void onBizExecuteError(Exception exception,
							Error error) {
						Logger.e(getClass(), "获取车辆数据异常");
						isloadingVehicles = false;
						HandleLogicErrorInfo(exception);
					}

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							List<StationVehicleRealTimeInfo> t) {
						Logger.i(getClass(), "获取车辆数据成功");
						isloadingVehicles = false;

						if (t == null || t.size() == 0) {
							Logger.i(getClass(), "获取到车辆信息为空");
							return;
						}
						List<String> lineids = new ArrayList<String>();
						Logger.i(getClass(), "查询到" + t.size() + "条站点车辆实时信息");
						for (StationVehicleRealTimeInfo rinfo : t) {
							for (VehicleRealtime real : rinfo.VehicleRealtimeInfos) {
								if (real == null) {
									Logger.i(getClass(), "查询当条车辆实时信息为NULL");
									continue;
								}
								Logger.i(getClass(), "需要查询的车辆增加："
										+ real.vehicle_vin);
								currentCars.put(real.vehicle_vin, real);

								if (!lineids.contains(real.line_id)) {
									Logger.i(getClass(), "需要查询的线路增加："
											+ real.line_id);
									lineids.add(real.line_id);
								}
							}
						}
						Logger.i(getClass(), "查询到" + lineids.size() + "条线路信息");
						loadLines(lineids);

					}
				});

	}

	private void clearAllInfos() {
		Logger.i(getClass(), "clearAllInfos-begin");
		if (mCarOverlay != null)
			mCarOverlay.removeAll();
		stationlay.removeAll();
		roadsOverlay.removeAll();
		currentCars.clear();
		allLines.clear();

		createCurrentStation();// 先画当前站点

		mMapView.refresh();
		Logger.i(getClass(), "clearAllInfos-end");

	}

	private void createCurrentStation() {
		Logger.i(getClass(), "绘制当前站点");
		StationItemPositionType ptype = StationItemPositionType.CENTER;
		GeoPoint baidupoint = CoordinateConvert.fromWgs84ToBaidu(new GeoPoint(
				(int) (currentStation.getGps_lat() * 1E6),
				(int) (currentStation.getGps_lon() * 1E6)));
		StationItem ci = new StationItem(currentStation.id, baidupoint,
				currentStation.getName(), currentStation.getName(), 1, true,
				ptype);
		ci.setNeedEdit(false);
		stationlay.addItem(ci);
		mMapController.setCenter(baidupoint);
		// stationlay.appendData(Arrays.asList(ci));
		mMapView.refresh();
	}

	private void loadLines(List<String> ids) {
		Logger.i(getClass(), "开始从Biz获取线路数据");
		biz.getLines(ids, new BizResultProcess<List<LineInfo>>() {
			@Override
			public void onBizExecuteError(Exception exception, Error error) {
				Logger.e(getClass(), "获取路线数据异常");
				HandleLogicErrorInfo(exception);
			}

			@Override
			public void onBizExecuteEnd(BizDataTypeEnum datatype,
					List<LineInfo> t) {
				Logger.e(getClass(),
						"获取路线数据成功:" + (null == t ? "NULL" : t.size()));
				if (t == null) {
					return;
				}
				allLines.addAll(t);
				if (allLines.size() > 0)
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								List<LineInfo> temp = new LinkedList<LineInfo>(
										allLines);
								if (temp != null && temp.size() > 0) {
									List<CoordPoint> ps = new LinkedList<CoordPoint>();
									for (LineInfo info : temp) {
										if (info == null)
											continue;
										if (info.getPoints() != null
												&& info.getPoints().size() > 0) {
											ps.addAll(info.getPoints());
											createLine(info);
										}
										createStations(info);

									}
									createCurrentStation();// 先画当前站点
									matchtopoints(ps);
									startLoadBus();
								}
							} catch (Exception err) {
								err.printStackTrace();
							}
						}
					});
			}
		});
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
				return;
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
			int extend = 1000;
			mMapController.zoomToSpan(
					(int) (lat_max * 1E6 - lat_min * 1E6 + extend),
					(int) (lon_max * 1E6 - lon_min * 1E6 + extend));

			mMapController.setCenter(centerp);
			// mMapController.animateTo(centerp);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	private void createStations(LineInfo info) {
		// 绘制站点
		if (info == null)
			return;
		List<StationItem> items = new ArrayList<StationOverlay.StationItem>();
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
		}
		stationlay.appendData(items);
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
	}

	private void createLine(GeoPoint[] gpoints, Symbol lineSymbol) {
		Logger.i(this.getClass(), "开始绘制道路地图线  " + gpoints.length);
		// 直接添加线层，效果不佳有锯齿
		// 构建线
		Geometry lineGeometry = new Geometry();

		lineGeometry.setPolyLine(gpoints);
		// 生成Graphic对象
		Graphic lineGraphic = new Graphic(lineGeometry, lineSymbol);
		roadsOverlay.setData(lineGraphic);
		mMapView.refresh();
	}

	/***********************************/
	/***********************************/
	private void loadMySelf() {
		Logger.i(this.getClass(), "开始加载自己定位数据");
		if (selflocationLay != null) {
			selflocationLay.setMarker(getResources().getDrawable(
					R.drawable.ic_myself));
			selflocationLay.enableCompass();
			selflocationLay.setLocationMode(LocationMode.NORMAL);

			// 使用百度定位服务
			initBaidulocClient();
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

	/***********************************/
	/***********************************/
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
		if (clearbefore) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					mCarOverlay.removeAll();
				}
			});
		}
		if (currentCars == null) {
			Logger.i(getClass(), "当前班车信息为空，停止刷新");
			return;
		}
		Logger.i(getClass(), "准备获取车辆[" + currentCars.size() + "]实时信息");
		// 如果路线换了，就要先清空下数据重新加载
		for (VehicleRealtime real : currentCars.values()) {
			final VehicleRealtime car = real;
			// 获取当前车辆定位数据并显示
			if (vehiclebiz == null)
				vehiclebiz = new BizVehicheUpdate(this);
			vehiclebiz.getRealTimeVehicheData(Arrays.asList(car.vehicle_vin),
					Arrays.asList(car.line_id),
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
							HandleLogicErrorInfo(exception);
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
		if (autorefresh) {// 自动刷新
			refreshTimer = new Timer(true);
			refreshTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					reloadBus(false);
				}
			}, refreshInterval, refreshInterval);
		} else {
			reloadBus(false);
		}
	}

	private void startLoadBus() {
		Logger.i(this.getClass(), "准备刷新班车数据");
		// 获取汽车
		if (!isActive()) {
			Logger.i(this.getClass(), "当前页面不在最前，停止刷新");
			return;
		}
		if (currentCars == null) {
			Logger.i(this.getClass(), "没有找到班车数据");
		} else {
			if (DateUtils.dateToStr(new Date(), DateUtils.TIME_FORMAT).equals(
					DateUtils.dateToStr(currentDate, DateUtils.TIME_FORMAT))) {
				reloadBus(true);
				restartRefreshTimer();
			}
		}
	}


	private void loadStationInfoFromIntent(Intent intent) {

		if (intent == null || intent.getExtras() == null) {
			return;
		}
		try {
			if (intent.getExtras().containsKey(ActivityCommConstant.PUSHBEAN)) {
				final AlarmPushBean bean = (AlarmPushBean) intent.getExtras()
						.getSerializable(ActivityCommConstant.PUSHBEAN);
				if (bean != null) {
				
					currentDate = (Date) bean.getTime();

					stopRemindMedia();
					stopViberate();
					showRemindDialog(bean);
					biz.getStation(bean.getStationId(),
							new BizResultProcess<StationInfo>() {
								@Override
								public void onBizExecuteError(
										Exception exception, Error error) {
									HandleLogicErrorInfo(exception);
								}

								@Override
								public void onBizExecuteEnd(
										BizDataTypeEnum datatype,
										final StationInfo t) {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											currentStatusRange = bean
													.getStatusRange();
											reloadByStationInfo(t,
													currentStatusRange);
											currentStatusRange = (currentStatusRange == StatusRange.AllWork ? StatusRange.MorningWork
													: currentStatusRange);

										}
									});
								}
							});
				}
			} else {
				loadActivitySendData(intent);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void loadActivitySendData(Intent intent) {
		currentDate = (Date) intent.getExtras().getSerializable(
				ActivityCommConstant.DATE);

		if (intent.getExtras().containsKey(ActivityCommConstant.STATION_INFO)) {
			StationInfo info = (StationInfo) intent.getExtras()
					.getSerializable(ActivityCommConstant.STATION_INFO);
			currentStation = info;
		}
		if (intent.getExtras().containsKey(ActivityCommConstant.STATUSRANGE)) {
			currentStatusRange = (StatusRange) intent.getExtras().getSerializable(ActivityCommConstant.STATUSRANGE);
			
			lastSelectedIndex = (currentStatusRange == StatusRange.MorningWork ? StatusRange.MorningWork.value()
					: StatusRange.NightWork.value());
			
			currentStatusRange = (currentStatusRange == StatusRange.AllWork ? StatusRange.MorningWork
					: currentStatusRange);
			
			
		}
		if (currentStation != null)
			reloadByStationInfo(currentStation, currentStatusRange);
	}

	StatusRange currentStatusRange = StatusRange.MorningWork;

	private void stopLoadBus() {
		Logger.i(this.getClass(), "停止刷新班车数据");
		if (refreshTimer != null) {
			refreshTimer.cancel();
		}
		if (vehiclebiz != null) {
			vehiclebiz.stop();
		}
	}

	/***********************************/
	/***********************************/
	private void showOrDismissPopLines() {
		if (popcl != null) {
			View center = findViewById(R.id.rl_it_center);
			int xoffset = (center.getWidth() - popcl.getWidth()) / 2;
			popcl.showAsDropDown(findViewById(R.id.rl_it_center), xoffset, 2);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_it_left:
			finish();
			break;
		case R.id.rl_it_center:
			// 弹出框
			showOrDismissPopLines();
			break;
		case R.id.rl_amosl_bottomleft:
			// 收藏
			showchangeFavorityDialog();
			break;
		case R.id.rl_amosl_bottomright:
			// 提醒设置
			changeRemind();
			break;
		case R.id.btn_amosl_refresh:

			if (DateUtils.dateToStr(new Date(), DateUtils.TIME_FORMAT).equals(
					DateUtils.dateToStr(currentDate, DateUtils.TIME_FORMAT))) {

				if (autorefresh) {
					startLoadBus();
				} else {
					reloadBus(false);
				}
			}
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
				if (allLines == null || allLines.size() == 0) {
					return;
				}
				
				/*多条线路以第一条线路进行居中*/
				LineInfo info = allLines.get(0);
				if (info != null) {
					if(info.getPoints().size() == 0){
						ToastUtils.show(mContext, "暂无线路信息,不能重置线路");
					}else{
						matchToLine(info);
					}
				}else{
					if(allLines.size()<=0){
						ToastUtils.show(mContext, "暂无线路信息,不能重置线路");
					}
				}
				// 这个是动画效果要放在后面，否则会阻碍后面的动作
				mMapController.setRotation(0);
			}
			break;
			
		default:
			break;
		}

	}

	private void showchangeFavorityDialog() {
		CustomDialog.Builder builder = new Builder(this);
		builder.setTitle("添加收藏")
				.setMessage(
						"是否" + (currentStation.isFavorites() ? "取消收藏" : "添加收藏"))
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						changeFavority();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	private void changeFavority() {
		if (biz != null) {
			final boolean tosetFavor = !currentStation.isFavorites();
			biz.setStationFavority(currentStation.getId(), tosetFavor,
					new BizResultProcess<Boolean>() {

						@Override
						public void onBizExecuteError(Exception exception,
								Error error) {
							ToastMessage("收藏失败");
							HandleLogicErrorInfo(exception);
						}

						@Override
						public void onBizExecuteEnd(BizDataTypeEnum datatype,
								Boolean t) {
							currentStation.setFavorites(tosetFavor);
							runOnUiThread(new Runnable() {
								public void run() {
									collecting_stations
											.setText(tosetFavor ? "取消收藏"
													: "收藏站点");
									iv_collect
											.setImageResource(tosetFavor ? R.drawable.ic_favored_white
													: R.drawable.ic_favor_white);

									ToastMessage(tosetFavor ? "已收藏" : "已取消收藏");
								}
							});
						}
					});
		}
	}

	private void changeRemind() {
		// if (!currentStation.isStatus()) {
		// Logger.i(getClass(), "准备添加站点[" + currentStation.getId() + "]到提醒状态");
		// createNewRemind();
		// } else {
		Logger.i(getClass(), "准备修改站点[" + currentStation.getId() + "]的提醒");
		biz.getStationRemind(currentStation.getId(),
				currentStation.getArea_type(), currentStatusRange,
				new BizResultProcess<RemindInfo>() {
					@Override
					public void onBizExecuteError(Exception exception,
							Error error) {
						ToastMessage("获取站点提醒信息失败");
						HandleLogicErrorInfo(exception);
					}

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							RemindInfo t) {
						if (t != null) {
							t.setStatus_range(currentStatusRange);
							Intent gotosetting = new Intent(
									SingleStationLinesActivity.this,
									StationRemindSettingActivity.class);
							Bundle data = new Bundle();
							data.putSerializable(
									ActivityCommConstant.REMINDINFO, t);
							gotosetting.putExtras(data);
							startActivityForResult(gotosetting,
									REQUEST_CODE_REMINDSUBMIT);
						} else {
							// ToastMessage("获取站点提醒信息失败");
							createNewRemind();
						}
					}
				});
		// }

	}

	private void createNewRemind() {
		Logger.i(getClass(), "准备添加站点[" + currentStation.getId() + "]到提醒状态");
		Intent gotosetting = new Intent(SingleStationLinesActivity.this,
				StationRemindSettingActivity.class);
		RemindInfo info = new RemindInfo();
		info.setId(null);
		info.setStation_id(currentStation.getId());
		info.setStation_name(currentStation.getName());
		info.setArea_type(currentStation.getArea_type());
		info.setLine_range(LineRange.FactoryOuter);
		info.status_range = currentStatusRange;
		info.remind_range = RemindRange.OnlyStation;
		Bundle data = new Bundle();
		data.putSerializable(ActivityCommConstant.REMINDINFO, info);
		gotosetting.putExtras(data);
		startActivityForResult(gotosetting, REQUEST_CODE_REMINDSUBMIT);
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
		if (biz != null)
			biz.stop();
		if (vehiclebiz != null)
			vehiclebiz.stop();
		super.onStop();
	}

	@Override
	protected void destoryRunningWork() {
		super.destoryRunningWork();
		if (biz != null)
			biz.stop();
		if (vehiclebiz != null)
			vehiclebiz.stop();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Logger.i(getClass(), "------------------------------onNewIntent");
		newIntent=intent;
		super.onNewIntent(intent);
	}

	private Intent newIntent = null;

	@Override
	protected void onResume() {
		Logger.i(getClass(), "------------------------------onResume");
		super.onResume();
		if (!isForceToDestory()) {
			refreshInterval = PrefDataUtil.getMapRefreshInterval(this) * 1000;
			autorefresh = PrefDataUtil.getAutoMapRefresh(this);
			mMapView.onResume();
			if (mLocationClient != null && myLocationListener != null)
				mLocationClient.registerLocationListener(myLocationListener);

			if (newIntent != null) {
				loadStationInfoFromIntent(newIntent);
				
				newIntent=null;//清空newIntent防止按home键后再返回当前页多次出现提醒对话框
			}else{
				startLoadBus();
			}
			// if (currentStation == null) {

			// } else {
			// startLoadBus();
			// }
		}

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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_REMINDSUBMIT:
			if (resultCode == RESULT_OK) {

				RemindInfo result = (RemindInfo) data.getExtras()
						.getSerializable(ActivityCommConstant.REMINDINFO);
				currentStation
						.setStatus(result.getRemind_status() != RemindStatus.Delete);

				SingleStationLinesActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {

						StationInfo stationInfo = new StationInfoDao(
								YtApplication.getInstance()).getStation(
								currentStation.id,
								ProxyManager.getInstance(
										getApplicationContext()).getUserCode());
						if (stationInfo != null && stationInfo.isStatus()) {

							iv_remind
									.setImageResource(R.drawable.ic_clocked_white);
						} else {
							iv_remind
									.setImageResource(R.drawable.ic_clock_white);
						}

					}
				});

				// iv_remind
				// .setImageResource(result.getRemind_status() ==
				// RemindStatus.Delete ? R.drawable.ic_clock_white
				// : R.drawable.ic_clocked_white);
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
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
}
