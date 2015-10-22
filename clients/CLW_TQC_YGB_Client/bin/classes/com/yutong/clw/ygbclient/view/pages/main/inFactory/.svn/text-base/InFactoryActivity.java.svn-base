package com.yutong.clw.ygbclient.view.pages.main.inFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.time.DateFormatUtils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.baidu.platform.comapi.map.Projection;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.OnNetworkChangeListener;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.VehicheInfo;
import com.yutong.clw.ygbclient.common.beans.VehicleDriver;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.common.beans.line.CoordPoint;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.StationType;
import com.yutong.clw.ygbclient.common.enums.VehicheStatus;
import com.yutong.clw.ygbclient.common.utils.ImageUtils;
import com.yutong.clw.ygbclient.common.utils.NetworkCheckUitl;
import com.yutong.clw.ygbclient.common.utils.PreferencesUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.common.utils.ToastUtils;
import com.yutong.clw.ygbclient.utils.ListQueryUtil;
import com.yutong.clw.ygbclient.utils.ListQueryUtil.IListComparer;
import com.yutong.clw.ygbclient.view.bean.push.AlarmPushBean;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcessTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.common.BizVehicheUpdate;
import com.yutong.clw.ygbclient.view.bizAccess.inFactory.BizInFactory;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizBusDriver;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.common.map.CarOverlay;
import com.yutong.clw.ygbclient.view.common.map.CarOverlay.CarMapItem;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.OnItemTapListener;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.StationItem;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.StationItemPositionType;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.main.MainActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.driver.BusDriverDetailsActivity;
import com.yutong.clw.ygbclient.view.pages.setting.SettingActivity;
import com.yutong.clw.ygbclient.view.pages.setting.about.AboutActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.util.DensityUtil;
import com.yutong.clw.ygbclient.view.widget.CustomDialog;
import com.yutong.clw.ygbclient.view.widget.ExHorizontalListView;
import com.yutong.clw.ygbclient.view.widget.HorizontalListView;
import com.yutong.clw.ygbclient.view.widget.PopCheckList;
import com.yutong.clw.ygbclient.view.widget.PopCheckList.OnCheckChangedListener;
import com.yutong.clw.ygbclient.view.widget.PopMenu;

/**
 * 厂内通勤主界面
 * 
 * @author zhouzc
 */
public class InFactoryActivity extends RemindAccessActivity implements
		OnClickListener {

	/***** 业务变量 *******/
	//
	private static final int show_tips = 2, noTipsIndex = 2;
	private BizInFactory biz;
	//
	private BizVehicheUpdate vehiclebiz;

	/***** 控件变量 *******/
	// 地图遮盖层
	private View mapCover;

	// 弹出线路选择框
	private PopCheckList popcl;

	// 顶部选择项
	private TextView txt_small;

	//
	private ImageView iv_tri;
	
	private RelativeLayout horizontalListViewRL;

	// 横向车辆列表
	/*private ExHorizontalListView hl_list;*/
	
	private HorizontalListView hl_list;
	
	// 刷新定时器
	private Timer refreshTimer = null;

	// 刷新间隔
	private long refreshInterval = 30000;

	private boolean isAutoRefresh = true;

	// 横向列表适配器
	private CarListAdapter carListAdapter;

	// 手势检测
	private GestureDetector gestureDetector;

	/***** 数据变量 *******/
	// 路线坐标点
	private GeoPoint[] linePoints;

	// 所有线路信息
	private List<LineInfo> allLines = null;

	// 车辆列表显示数据
	private ArrayList<CarListItem> cars;

	// 当前车辆数据
	private List<VehicheInfo> currentCars;

	private List<StationItem> items = null;
	// private Bitmap currentCover = null;

	/***** 标志变量 *******/
	private boolean isGpsEnable = false, isSettingBtnPressed = false,
			firstLocation = true;

	// 当前线路序号
	private int currentLineIndex = -1;

	// 是否正在加载车辆信息
	boolean isloadingbus = false;

	boolean showpopcl = false;

	// 地图是否完成加载
	boolean ismapready = false;

	// 当前路线是否已经加载
	boolean hasLoadedCurrentLine = false;

	// 弹出Loading框的KeyCode
	private final int LOADING_KEY_REMINDSUBMIT = 1;

	// 是否有新的Resume事件来取消地图控件的隐藏
	boolean newresume = false;

	private int currentSationQueryCode = 1;
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

	private Thread cleanCacheThread = null;

	// // 离线地图
	// private MKOfflineMap mOffline = null;

	public static final String YUN_YING_NAME = "yunYingShiJian",
			YUN_YING_KEY = "hasShow";

	private OnNetworkChangeListener innerNetworkListener;

	private boolean allowNetworkCheck = false;

	List<String> names = new ArrayList<String>();

	boolean isfrompush = false;

	boolean isDirect = false;
	
	String lastpushlineid = null;

	private String tipStr_loading = "正在查询发车安排";
	
	private Button mZoomOutBtn,mZoomInBtn;
	
	/*
	 * ****************************司机信息相关变量*******************************/
	private PopMenu popMenu;
	
	private Context mContext;
	
	private BizBusDriver bizBusDriver;
	
	private List<String> mVehicle_vins  = new ArrayList<String>();

    private List<String> mLine_ids = new ArrayList<String>();
  
    private List<VehicleDriver> mDriverInfoList = new ArrayList<VehicleDriver>();
    
    private int mWidth = 450;
    
    private OnNetworkChangeListener mNetworkListener;
    
    private List<VehicleDriver> dirverDataList;
    /*
	 * ****************************司机信息相关变量*******************************/
	
	/********************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		autoShowCover = true;
		mContext = this;
		setContentView(R.layout.activity_main_infactory);

		currentLineIndex = PrefDataUtil.getFactory(InFactoryActivity.this) == AreaType.SecondFactory ? 0: 1;
		
		/* gps是否开启 */
		isGpsEnable = NetworkCheckUitl.isGpsEnable(getApplicationContext());
		if (!isGpsEnable) {
			boolean isShow = PreferencesUtils.getBoolean(getBaseContext(),
					"gps_setting", "isShow");
			if (!isSettingBtnPressed && !isShow) {
				if (!YtApplication.getInstance().getActivityCoverManager()
						.isShowingCover()) {
					showFavoriteConfirmDialogTest();
				}
			}
		}

		/* 断网重连位置监听 */
		initNetworkListener();

		setAllowNetworkCheck(true);
		initBiz();
		initViews();
		showLoading("通勤路线获取中...", show_tips, true);
		showLoadingTips();
		loadLines();

		
		initMap();
		loadMySelf();
		
		/*用户行为统计-跳转到厂内通勤*/
        new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CNTQ_LOAD).reportBehavior(null);
	}

	private void showYunYingTips() {

		initTime(currentLineIndex == 0 ? AreaType.SecondFactory
				: AreaType.FirstFactory, isSummerTime());

		initTips();
	}

	private  void addNetWorkListener(){
		// 网络状态
		mNetworkListener = new OnNetworkChangeListener() {

			@Override
			public void NetworkChanged(boolean isConnected) {
				
				if(popMenu!=null){
					popMenu.dismiss();
				}
			}
		};
		YtApplication.getInstance().addOnNetworkChangeListener(mNetworkListener);
	}
	
	private  void removeNetWorkListener(){
		if(mNetworkListener!=null){
			YtApplication.getInstance().removeOnNetworkChangeListener(mNetworkListener);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		addNetWorkListener();
		
		if (!isForceToDestory()) {
			refreshInterval = PrefDataUtil.getMapRefreshInterval(this) * 1000;
			isAutoRefresh = PrefDataUtil.getAutoMapRefresh(this);
			newresume = true;
			showMap();
			Bundle maininput = MainActivity.getInstance().getNewIntentBundle(
					this.getClass().toString());
			if (maininput != null
					&& maininput.containsKey(ActivityCommConstant.PUSHBEAN)) {
				isfrompush = true;
				final AlarmPushBean bean = (AlarmPushBean) maininput
						.getSerializable(ActivityCommConstant.PUSHBEAN);
				if (bean != null) {
					lastpushlineid = bean.getLineid();
					stopRemindMedia();
					stopViberate();
					showRemindDialog(bean);
				}
			} else {
				isfrompush = false;
			}
			if (mLocationClient != null && myLocationListener != null) {
				mLocationClient.registerLocationListener(myLocationListener);
			}

			showLoading("通勤路线获取中...", show_tips, true);
			refreshData();
			reloadLineStations(false);

		}
		showpopcl = true;

		// initTime(PrefDataUtil.getFactory(InFactoryActivity.this),
		// isSummerTime());

		if (currentLineIndex != noTipsIndex) {

			showYunYingTips();
		}
	}

	private void initNetworkListener() {

		// 网络状态
		innerNetworkListener = new OnNetworkChangeListener() {

			@Override
			public void NetworkChanged(boolean isConnected) {
				if (isConnected) {
					firstLocation = true;
					loadMySelf();
				}
			}
		};
		YtApplication.getInstance().addOnNetworkChangeListener(
				innerNetworkListener);
	}

	private Calendar morningStart, morningEnd, afterNoonStart, afterNoonEnd;
	private String tipStr;
	private boolean hasShow;

	private void initTime(AreaType areaType, boolean isSummer) {

		morningStart = Calendar.getInstance();
		morningEnd = Calendar.getInstance();
		afterNoonStart = Calendar.getInstance();
		afterNoonEnd = Calendar.getInstance();

		if (areaType == AreaType.FirstFactory) {
			
			if (isSummer) {
				morningStart.set(Calendar.HOUR_OF_DAY, 8);
				morningStart.set(Calendar.MINUTE, 20);

				morningEnd.set(Calendar.HOUR_OF_DAY, 11);
				morningEnd.set(Calendar.MINUTE, 30);

				afterNoonStart.set(Calendar.HOUR_OF_DAY, 13);
				afterNoonStart.set(Calendar.MINUTE, 30);

				afterNoonEnd.set(Calendar.HOUR_OF_DAY, 18);
				afterNoonEnd.set(Calendar.MINUTE, 30);

				tipStr = getResources().getText(
						R.string.firstFactory_summer_body).toString();

			} else {

				morningStart.set(Calendar.HOUR_OF_DAY, 8);
				morningStart.set(Calendar.MINUTE, 20);

				morningEnd.set(Calendar.HOUR_OF_DAY, 11);
				morningEnd.set(Calendar.MINUTE, 30);

				afterNoonStart.set(Calendar.HOUR_OF_DAY, 13);
				afterNoonStart.set(Calendar.MINUTE, 00);

				afterNoonEnd.set(Calendar.HOUR_OF_DAY, 18);
				afterNoonEnd.set(Calendar.MINUTE, 00);

				tipStr = getResources().getText(
						R.string.firstFactory_winter_body).toString();
			}
		} else {

			if (isSummer) {

				morningStart.set(Calendar.HOUR_OF_DAY, 9);
				morningStart.set(Calendar.MINUTE, 00);

				morningEnd.set(Calendar.HOUR_OF_DAY, 12);
				morningEnd.set(Calendar.MINUTE, 0);

				afterNoonStart.set(Calendar.HOUR_OF_DAY, 13);
				afterNoonStart.set(Calendar.MINUTE, 30);

				afterNoonEnd.set(Calendar.HOUR_OF_DAY, 18);
				afterNoonEnd.set(Calendar.MINUTE, 30);

				tipStr = getResources().getText(
						R.string.secondFactory_summer_body).toString();
			} else {

				morningStart.set(Calendar.HOUR_OF_DAY, 9);
				morningStart.set(Calendar.MINUTE, 00);

				morningEnd.set(Calendar.HOUR_OF_DAY, 12);
				morningEnd.set(Calendar.MINUTE, 0);

				afterNoonStart.set(Calendar.HOUR_OF_DAY, 13);
				afterNoonStart.set(Calendar.MINUTE, 00);

				afterNoonEnd.set(Calendar.HOUR_OF_DAY, 18);
				afterNoonEnd.set(Calendar.MINUTE, 00);

				tipStr = getResources().getText(
						R.string.secondFactory_winter_body).toString();
			}
		}

	}
	
	private Toast toast;
	
	private void initTips() {

		Calendar currentCal = Calendar.getInstance();
		if (isExpire)
			return;
		if (currentCal.before(morningStart)
				|| (currentCal.after(morningEnd) && currentCal
						.before(afterNoonStart))
				|| currentCal.after(afterNoonEnd)) {
			
			toast = Toast.makeText(InFactoryActivity.this, StringUtil.StringFilter(tipStr), Toast.LENGTH_LONG);
			toast.show();
		}
		
	}

	private void initBiz() {
		biz = new BizInFactory(this);
	}

	private void locateBus(int dataIndex){
		if (mCarOverlay != null) {
			// 定位到车辆
			String itemid = cars.get(dataIndex).Id;
			mCarOverlay.animateTo(itemid);
			mCarOverlay.splashItemTitle(itemid);
		}
	}
	
	
	private void showDriverPop(int dataIndex,final View view){
		
		/*Thread getDeriverInfoThread = new Thread(new GetDeriverInfoRunnalbe(dataIndex));
		getDeriverInfoThread.start();*/
		
		if(currentCars!=null && currentCars.size()>0){
			final VehicheInfo vehicheInfo = currentCars.get(dataIndex);
			
			Log.i("TAG", "vin = "+vehicheInfo.vehicle_vin);
			Log.i("TAG", "line_id = "+vehicheInfo.line_id);
			
			mVehicle_vins.clear();
			mVehicle_vins.add(vehicheInfo.vehicle_vin);
			
			mLine_ids.clear();
			mLine_ids.add(vehicheInfo.line_id);
			
			if(bizBusDriver ==null){
				bizBusDriver = new BizBusDriver(mContext, mVehicle_vins, mLine_ids);
				bizBusDriver.getVehicheDriverData( new BizResultProcess<List<VehicleDriver>>(){

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,List<VehicleDriver> t) {
						
						bizBusDriver = null;
						dirverDataList = t;
						dirverDataList.get(0).vehicleNum = vehicheInfo.vehiche_number;
						dirverDataList.get(0).line_id = vehicheInfo.line_id;
						
						runOnUiThread(new MyRunnable(dirverDataList, view));
					}

					@Override
					public void onBizExecuteError(Exception exception, Error error) {
						bizBusDriver = null;
					}
					
				});
			}
		}
	}
	
	class MyRunnable implements Runnable{
		
		private List<VehicleDriver> dirverDataList;
		private View view;
		
		public MyRunnable(List<VehicleDriver> dirverDataList,View view) {
			this.dirverDataList = dirverDataList;
			this.view = view;
		}


		@Override
		public void run() {
			if(dirverDataList!=null && dirverDataList.size()>0){
				VehicleDriver vehicleDriver = dirverDataList.get(0);
				
				Log.i("TAG", "onBizExecuteEnd vehicleDriver.driver_name = "+vehicleDriver.driver_name);
				Log.i("TAG", "onBizExecuteEnd vehicleDriver.emp_code = "+vehicleDriver.emp_code);
				Log.i("TAG", "onBizExecuteEnd vehicleDriver.driver_tel = "+vehicleDriver.driver_tel);
				
				popMenu.setData(dirverDataList);
				popMenu.showAsDropDown(view, 0, -4);
				
				//用户行为统计[厂内-加载司机弹出框]
				new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CNTQ_SJTCK).reportBehavior(null);
			}
		}
	}
	
	class GetDeriverInfoRunnalbe implements Runnable{
		
		private int index;
		
		
		public GetDeriverInfoRunnalbe(int index) {
			this.index = index;
		}

		@Override
		public void run() {
			
		}  
	}
	
	class  DriverPopHandler extends Handler{
		
		WeakReference<Activity > mActivityReference;
		
		
		public DriverPopHandler(WeakReference<Activity> mActivityReference) {
			this.mActivityReference = mActivityReference;
		}

		@Override
		public void handleMessage(Message msg) {
			
			final Activity activity = mActivityReference.get();
			
			switch (msg.what) {
			case 0x01:
				
				break;

			default:
				break;
			}
			
		}
	}
	
	public void initViews() {
		
		if(popMenu == null){
			/*popMenu = new PopMenu(mContext,mWidth);*/
			popMenu = new PopMenu(mContext);
			popMenu.setmClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putString(ActivityCommConstant.VEHICHLE, dirverDataList.get(0).vehicle_vin);
					bundle.putString(ActivityCommConstant.LINE_ID, dirverDataList.get(0).line_id);
					bundle.putString(ActivityCommConstant.VIHICLE_NUM, dirverDataList.get(0).vehicleNum);
					
					ActivityUtils.changeActivity((Activity) mContext, BusDriverDetailsActivity.class, bundle);
					
					//用户行为统计[厂内-司机明细]
					new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CNTQ_SJMX).reportBehavior(null);
				}
			} );
		}
		
		((Button) findViewById(R.id.btn_ami_refresh)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_ami_resize)).setOnClickListener(this);
		horizontalListViewRL = (RelativeLayout) findViewById(R.id.ll_ami_host);
		mapCover = findViewById(R.id.ll_ami_mapcover);
		hl_list = (HorizontalListView) findViewById(R.id.hl_ami_list);
		cars = new ArrayList<InFactoryActivity.CarListItem>();
		carListAdapter = new CarListAdapter(this, cars);
		hl_list.setAdapter(carListAdapter);
		hl_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showDriverPop(position,view);
				locateBus(position);
				
			}
			
		});
		
		gestureDetector = new GestureDetector(new OnGestureListener() {
			@Override
			public boolean onDown(MotionEvent e) {
				return true;
			}

			@Override
			public void onLongPress(MotionEvent e) {
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
			}
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				
				float deltax = e2.getX() - e1.getX();
				float deltay = e2.getY() - e1.getY();
				if (Math.abs(deltay) > Math.abs(deltax)) {
					if (deltay < -40) {
						/*Animation animation = AnimationUtils.loadAnimation(InFactoryActivity.this, R.anim.popup_exit);
						animation.setDuration(200);
						horizontalListViewRL.setAnimation(animation);
						animation.startNow();*/
						horizontalListViewRL.setVisibility(View.GONE);
					} else if (deltay > 40) {
						/*Animation animation = AnimationUtils.loadAnimation(InFactoryActivity.this, R.anim.popup_enter);
						animation.setDuration(500);
						horizontalListViewRL.setAnimation(animation);
						animation.startNow();*/
						horizontalListViewRL.setVisibility(View.VISIBLE);
					}
				}
				return true;
			}
		});

		findViewById(R.id.title_ami).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				return gestureDetector.onTouchEvent(arg1);
			}
		});
		
		hl_list.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});
		
		hl_list.setVisibility(View.VISIBLE);
		
		horizontalListViewRL.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				return gestureDetector.onTouchEvent(event);
			}
		});
		
		loadingProgressBar  =(ProgressBar) findViewById(R.id.loadingBar);
		tipsTV  =(TextView) findViewById(R.id.tipsTV);
		
		mZoomOutBtn = (Button) findViewById(R.id.btn_zoom_out);
		mZoomOutBtn.setOnClickListener(this);
		mZoomInBtn = (Button) findViewById(R.id.btn_zoom_in);
		mZoomInBtn.setOnClickListener(this);
	}
	
	
	private ProgressBar loadingProgressBar;
	private TextView tipsTV;
	private boolean canZoom;
	
	private void showLoadingTips(){
		loadingProgressBar.setVisibility(View.VISIBLE);
		tipsTV.setVisibility(View.VISIBLE);
	}
	
	private void hideLoadingTips(){
		loadingProgressBar.setVisibility(View.GONE);
		tipsTV.setVisibility(View.GONE);
	}
	
	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void loadTitleByPageSetting(Button btn_left, Button btn_right,
			RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
			TextView tv_small) {
		btn_left.setBackgroundResource(R.drawable.bg_settingbtn);
		btn_right.setBackgroundResource(R.drawable.bg_anxinbtn);
		tv_large.setText("厂内通勤");
		tv_small.setText("");

		rl_center.setOnClickListener(this);
		btn_left.setOnClickListener(this);
		btn_right.setOnClickListener(this);

		tv_small.setVisibility(View.GONE);
		iv_tri.setVisibility(View.GONE);
		this.txt_small = tv_small;
		this.iv_tri = iv_tri;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_it_left:
			Intent gotosetting = new Intent(InFactoryActivity.this,
					SettingActivity.class);
			gotosetting.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			startActivity(gotosetting);
			break;
		case R.id.btn_it_right:
			Intent gotoanxin = new Intent(InFactoryActivity.this,
					AboutActivity.class);
			gotoanxin.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			startActivity(gotoanxin);
			
			/*用户行为统计- 跳转到关于安芯*/
			BizBehaviorStatistic bizBehaviorStatistic = new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_ABOUT);
			bizBehaviorStatistic.reportBehavior(null);
			
			break;
		case R.id.rl_it_center:
			showOrDismissPopLines();
			
			break;
		case R.id.btn_ami_refresh:

			if (cleanCacheThread == null) {
				cleanCacheThread = new Thread(new Runnable() {

					@Override
					public void run() {
						/*
						 * ProxyManager.getInstance(InFactoryActivity.this)
						 * .clearAppCache();
						 */
						cleanCacheHandler.sendEmptyMessage(1);
					}

				});
				cleanCacheThread.start();
				
				
			}
			
			//用户行为统计[厂内-刷新]
			new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CNTQ_SHUAXIN).reportBehavior(null);
			
			break;

		case R.id.btn_ami_resize:
			if (mMapController != null) {
				if (mCarOverlay != null)
					mCarOverlay.adapteToMapRotate();
				if (allLines == null || allLines.size() == 0) {
					return;
				}
				LineInfo info = allLines.get(currentLineIndex);
				if (info != null) {
					matchToLine(info);
				}
				// 这个是动画效果要放在后面，否则会阻碍后面的动作
				mMapController.setRotation(0);
			}
			
			//用户行为统计-重置按钮
			new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CNTQ_HUIGUI).reportBehavior(null);
			
			
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
			//用户行为统计[厂内-缩放]
			new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CNTQ_SUOFANG).reportBehavior(null);
			
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
			
			//用户行为统计[厂内-缩放]
			new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CNTQ_SUOFANG).reportBehavior(null);
			
			break;
			
		default:
			break;
		}
	}

	private AreaType lastSelectAreaType = null;

	@Override
	protected void onPause() {
		super.onPause();
		
		removeNetWorkListener();
		
		if(toast!=null)
			toast.cancel();
		
		try {
			newresume = false;
			hideMap();
			if (mLocationClient != null && myLocationListener != null)
				mLocationClient.unRegisterLocationListener(myLocationListener);
			stopLoadBus();

			if (biz != null)
				biz.stop();
			if (vehiclebiz != null)
				vehiclebiz.stop();

			/* onResume中重新加载班车信息使用 */
			if (currentCars != null && currentCars.size() > 0) {
				currentCars.clear();
				currentCars = null;
			}
			/*
			 * allLines.clear(); allLines = null;
			 */

			hasLoadedCurrentLine = false;
			/*
			 * popcl.getMdata().clear(); names.clear();
			 */

			lastSelectAreaType = PrefDataUtil
					.getFactory(InFactoryActivity.this);			
			
		} catch (Exception err) {
			err.printStackTrace();
		}

	}

	@Override
	protected void onStop() {
		/*
		 * if (biz != null) biz.stop(); if (vehiclebiz != null)
		 * vehiclebiz.stop();
		 */

		mLocationClient.stop();
		firstLocation = true;
		super.onStop();
	}

	private void hideMap() {
		// mMapView.setDrawingCacheEnabled(true);
		//
		// currentCover = mMapView.getDrawingCache();
		// if (currentCover != null && currentCover.isRecycled())
		// mapcover.setBackgroundDrawable(new BitmapDrawable(currentCover));
		// else
		// mapcover.setBackgroundDrawable(null);
		// mapcover.setVisibility(View.VISIBLE);
		// mapcover.bringToFront();
		// mapcover.invalidate();
		mMapView.onPause();
		delayRun(new Runnable() {
			@Override
			public void run() {
				if (!newresume) {
					mMapView.setVisibility(View.INVISIBLE);
					mMapView.setVisibility(View.GONE);
				}
			}
		}, 100);

	}

	@Override
	protected void onStart() {

		super.onStart();
	}

	private void showMap() {
		if (ismapready) {
			mMapView.setVisibility(View.VISIBLE);
			mMapView.onResume();
			delayRun(new Runnable() {
				@Override
				public void run() {
					mapCover.setVisibility(View.GONE);
				}
			}, 200);
		}
	}

	private boolean isSummerTime() {

		Calendar nowCal = Calendar.getInstance();
		int currentMonth = nowCal.get(Calendar.MONTH);
		if (currentMonth > 4 && currentMonth < 9)
			return true;
		return false;
	}

	@Override
	protected void onDestroy() {
		if (biz != null)
			biz.destory();
		if (vehiclebiz != null)
			vehiclebiz.destory();
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void destoryRunningWork() {
		super.destoryRunningWork();
		if (biz != null)
			biz.stop();
		if (vehiclebiz != null)
			vehiclebiz.stop();
		stopLoadBus();
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

	private void refreshData() {
		if (ismapready) {

			Logger.i(getClass(), "刷新厂内路线数据");

			if (currentCars != null && currentCars.size() > 0) {
				currentCars.clear();
				currentCars = null;
			}
			hasLoadedCurrentLine = false;
			loadLines();
		}
	}

	public void reloadLineStations(final boolean onlyremind) {
		Logger.i(getClass(), "开始刷新站点数据");
		if (biz != null) {
			if (allLines == null || allLines.size() == 0)
				return;
			if (currentLineIndex < 0)
				return;
			if (currentLineIndex >= allLines.size())
				return;
			LineInfo currentLine = allLines.get(currentLineIndex);
			if (currentLine == null)
				return;

			// 生成随机查询号
			final int randomkey = (int) (Math.random() * 10000);
			currentSationQueryCode = randomkey;

			BizResultProcess<LineInfo> process = new BizResultProcess<LineInfo>() {
				@Override
				public void onBizExecuteError(Exception exception, Error error) {
					Logger.e(getClass(), "更新站点数据失败");
					HandleLogicErrorInfo(exception);
				}

				@Override
				public void onBizExecuteEnd(BizDataTypeEnum datatype, LineInfo t) {
					if (currentSationQueryCode != randomkey) {
						Logger.e(getClass(), "发现此次站点更新已经过期，不再执行刷新");
						return;
					}
					if (t == null)
						return;
					allLines.set(currentLineIndex, t);
					if (onlyremind) {
						for (final StationInfo info : t.getStations()) {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									stationlay.setItemRemindStatus(
											info.getId(), info.isStatus());
									mMapView.refresh();
								}
							});

						}
					} else {
						runOnUiThread(new Runnable() {
							public void run() {
								loadStations();
							}
						});
					}
				}
			};
			process.setProcesstype(BizResultProcessTypeEnum.Async);
			biz.getSingleLine(currentLine.getLine_id(), process);
		}
	}

	private void initMap() {

		mMapView = (MapView) findViewById(R.id.bmv_ami_map);
		mMapController = mMapView.getController();
		mMapController.enableClick(true);
		// 设置地图缩放级别
		/* mMapController.setZoom(currentlevel); */
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
				// Logger.i(getClass(), "地图onMapMoveFinish");
				// 刷新地图上面得车辆旋转方向
				if (mCarOverlay != null)
					mCarOverlay.adapteToMapRotate();

			}

			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				/**
				 * 在此处理底图poi点击事件 显示底图poi名称并移动至该点 设置过：
				 * mMapController.enableClick(true); 时，此回调才能被触发
				 */
			}

			@Override
			public void onGetCurrentMap(Bitmap b) {
				/**
				 * 当调用过 mMapView.getCurrentMap()后，此回调会被触发 可在此保存截图至存储设备
				 */
				// saveMapScreenFile(b);
			}

			private void saveMapScreenFile(Bitmap b) {
				byte[] buffer = ImageUtils.bitmapToByte(b);
				try {

					Projection pro = mMapView.getProjection();

					GeoPoint lb = pro.fromPixels(0, mMapView.getHeight());
					GeoPoint rt = pro.fromPixels(mMapView.getWidth(), 0);

					String filename = lb.getLatitudeE6() + "_"
							+ lb.getLongitudeE6() + "__" + rt.getLatitudeE6()
							+ "_" + rt.getLongitudeE6() + ".jpg";

					File ofile = new File(
							Environment.getExternalStorageDirectory()
									+ "/annxin/YGB/temp/" + filename);
					if (!ofile.exists()) {
						if (!ofile.getParentFile().exists()) {
							ofile.getParentFile().mkdirs();
						}
						ofile.createNewFile();
					}

					FileOutputStream fos = new FileOutputStream(ofile);
					fos.write(buffer);
					fos.flush();
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMapAnimationFinish() {
			}

			/**
			 * 在此处理地图载完成事件
			 */
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
	}

	private void doworkWhenMapLoaded() {
		ismapready = true;
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				mMapView.setVisibility(View.VISIBLE);
				mMapView.onResume();
				delayRun(new Runnable() {

					@Override
					public void run() {
						mapCover.setVisibility(View.GONE);
					}
				}, 100);
			}
		});

		// 定位移动至onResume方法中
		// loadMySelf();
		if (allLines != null && allLines.size() != 0) {
			Logger.i(this.getClass(), "此时路线信息不为空");
			if (!hasLoadedCurrentLine) {
				Logger.i(this.getClass(), "发现还没开始加载当前线路信息，开始加载");
				loadCurrentLine();
			}
		}
	}

	private void loadCurrentLine() {
		Logger.i(this.getClass(), "开始加载当前道路信息");
		// 置空车辆信息
		currentCars = null;
		hasLoadedCurrentLine = true;
		loadRoads();
		reloadLineStations(false);
		startLoadBus();
	}

	private void loadLines() {

		if (popcl == null) {
			popcl = new PopCheckList(this);

			popcl.setOnCheckChangedListener(new OnCheckChangedListener() {
				@Override
				public void OnChecked(int index, String txt) {
					if (currentLineIndex == index) {
						popcl.dismiss();
						return;
					}
					
					showLoadingTips();
					
					if (currentCars != null && currentCars.size() > 0) {
						currentCars.clear();
						currentCars = null;
					}
					reloadhorizList(false );
					
					txt_small.setText(txt);
					currentLineIndex = index;

					/* 运营时间提示信息 */
					if (currentLineIndex != noTipsIndex) {
						showYunYingTips();
					}

					stopLoadBus();

					/* onResume中重新加载班车信息使用 */
					if (currentCars != null && currentCars.size() > 0) {
						currentCars.clear();
						currentCars = null;
					}

					hasLoadedCurrentLine = false;
					loadCurrentLine();
					// mMapController.setZoom(currentlevel);
					popcl.dismiss();
					
					//用户行为统计[厂内-厂区切换]
					new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CNTQ_QHCQ).reportBehavior(null);
				}
			});
			popcl.setOnDismisslistener(new OnDismissListener() {
				@Override
				public void onDismiss() {
					// TODO title里面的小三角可能要变色
				}
			});
		}
		Logger.i(getClass(), "====================获取线路数据");
		BizDataTypeEnum result = biz
				.getInFactoryLines(new BizResultProcess<List<LineInfo>>() {

					@Override
					public void onBizExecuteError(Exception exception,
							Error error) {
						dismissLoading(show_tips);
						ToastMessage("获取路线信息失败");
						HandleLogicErrorInfo(exception);
						if (allLines != null && allLines.size() > 0) {
							Logger.i(getClass(), "开始刷新原线路站点和车辆信息");
							reloadLineStations(true);
							startLoadBus();
						}
					}

					@Override
					public void onBizExecuteEnd(final BizDataTypeEnum datatype,
							final List<LineInfo> t) {
						Logger.i(getClass(), "====================获取线路数据，完成："
								+ datatype);
						showpopcl = false;
						dismissLoading(show_tips);
						Logger.i(this.getClass(), "获取路线信息成功");
						// 不是第一次加载数据成功
						if (allLines != null && allLines.size() > 0) {

							if (datatype == BizDataTypeEnum.FromNetwork) {
								Logger.i(getClass(), "从网络获取的最新数据，所以重新刷新线路");
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										refreshLineInfos(t);
									}
								});
							} else {
								Logger.i(getClass(),
										"从本地获取的缓存数据，所以不重新刷新线路,开始刷新站点和车辆信息");
								/* hasLoadedCurrentLine = true; */
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										hasLoadedCurrentLine = true;
										refreshLineInfos(t);
										hasLoadedCurrentLine = false;
									}
								});
								/*
								 * reloadLineStations(true); startLoadBus();
								 */
							}
							return;
						}
						runOnUiThread(new Runnable() {
							public void run() {

								if (t == null) {
									Logger.i(this.getClass(), "获取路线信息为NULL");
									return;
								}
								if (t.size() == 0) {
									Logger.i(this.getClass(), "获取路线信息条数为0");
									return;
								}
								allLines = t;
								/* List<String> names = new ArrayList<String>(); */

								int initialindex = -1;
								// 这边根据读取厂区选择配置来设置
								AreaType myselect = PrefDataUtil
										.getFactory(InFactoryActivity.this);

								int count = 0;
								names.clear();
								for (LineInfo info : t) {
									names.add(info.line_name);
								}
								for (LineInfo info : t) {
									count++;
									if (isfrompush
											&& info.line_id != null
											&& info.line_id
													.equals(lastpushlineid)) {// 跳转到推送的线路
										initialindex = count - 1;
										break;
									}
									if (initialindex != -1)
										continue;
									if ((myselect == AreaType.FirstFactory && info.area_type == AreaType.FirstFactory)
											|| (myselect == AreaType.SecondFactory && info.area_type == AreaType.SecondFactory)) {

										initialindex = count - 1;
									}
								}

								initialindex = initialindex == -1 ? 0
										: initialindex;
								txt_small.setVisibility(View.VISIBLE);
								txt_small.setText(names.get(initialindex));
								if (count > 1) {
									iv_tri.setVisibility(View.VISIBLE);
									showpopcl = true;
								}
								popcl.setData(names, initialindex);
								currentLineIndex = initialindex;
								if (ismapready && !hasLoadedCurrentLine) {
									Logger.i(this.getClass(),
											"发现还没开始加载当前线路信息，开始加载");
									loadCurrentLine();
								}
							}

						});

					}
				});
		/*
		 * if (result == BizDataTypeEnum.FromNetwork) {
		 * showLoading("通勤路线获取中...", show_tips, true); }
		 */

	}

	private void refreshLineInfos(List<LineInfo> t) {

		if (t == null || t.size() == 0) {

			Logger.i(this.getClass(), "获取路线信息为空");
			txt_small.setVisibility(View.INVISIBLE);
			txt_small.setText("");
			showpopcl = false;
			return;
		}
		if (allLines == null) {
			hasLoadedCurrentLine = false;
		}
		allLines = t;
		/* List<String> names = new ArrayList<String>(); */
		// 获取当前选择的线路
		String lname = popcl.getSelectionValue();

		Log.i("TAB", "popcl.getSelectionValue():" + lname);

		int initialindex = -1;
		int count = 0;
		// 这边根据读取厂区选择配置来设置
		AreaType myselect = PrefDataUtil.getFactory(InFactoryActivity.this);

		boolean isSame = false;
		if (lastSelectAreaType == null || lastSelectAreaType != myselect) {

			lastSelectAreaType = myselect;
			hasLoadedCurrentLine = false;
			isSame = false;

		} else {
			isSame = true;
		}

		names.clear();
		for (LineInfo info : t) {
			names.add(info.line_name);
			count++;
			if (initialindex != -1)// 已配置选项
				continue;
			// 配置选项
			if (lname == null) {
				if ((lastSelectAreaType == AreaType.FirstFactory && info.area_type == AreaType.FirstFactory)
						|| (lastSelectAreaType == AreaType.SecondFactory && info.area_type == AreaType.SecondFactory)) {
					initialindex = count - 1;
				}
			} else {

				if (isSame) {
					if (info.line_name.equals(lname)) {
						initialindex = count - 1;
					}
				} else {
					if ((lastSelectAreaType == AreaType.FirstFactory && info.area_type == AreaType.FirstFactory)
							|| (lastSelectAreaType == AreaType.SecondFactory && info.area_type == AreaType.SecondFactory)) {
						initialindex = count - 1;
					}
				}
			}
		}

		initialindex = initialindex == -1 ? 0 : initialindex;
		txt_small.setVisibility(View.VISIBLE);
		txt_small.setText(names.get(initialindex));

		if (count > 1) {
			iv_tri.setVisibility(View.VISIBLE);
			showpopcl = true;
		}

		popcl.setData(names, initialindex);
		showpopcl = true;
		currentLineIndex = initialindex;

		if (ismapready && !hasLoadedCurrentLine) {

			Logger.i(this.getClass(), "发现还没开始加载当前线路信息，开始加载");
			loadCurrentLine();
		} else if (ismapready && hasLoadedCurrentLine) {

			/* reloadLineStations(false); */
			startLoadBus();

		}
	}

	private void showOrDismissPopLines() {
		/* if (showpopcl) */
		if (popcl != null) {
			View center = findViewById(R.id.rl_it_center);
			int xoffset = (center.getWidth() - popcl.getWidth()) / 2;
			popcl.showAsDropDown(findViewById(R.id.rl_it_center), xoffset, 2);
		}
	}

	private void loadRoads() {
		if (roadsOverlay == null) {
			roadsOverlay = new GraphicsOverlay(mMapView);
			mMapView.getOverlays().add(roadsOverlay);
		} else {
			roadsOverlay.removeAll();
		}

		Logger.i(this.getClass(), "开始加载道路数据");
		if (currentLineIndex < 0) {
			Logger.i(this.getClass(), "没有当前线路信息，无法加载道路数据");
			return;
		}

		// 贴图形式
		// GroundOverlay roadol = new GroundOverlay(mMapView);
		//
		// GeoPoint lb = new GeoPoint((int) (34.699994 * 1E6),
		// (int) (113.701660 * 1E6));
		// GeoPoint rt = new GeoPoint((int) (34.688071 * 1E6),
		// (int) (113.690006 * 1E6));
		//
		// roadol.addGround(new Ground(((BitmapDrawable) getResources()
		// .getDrawable(R.drawable.bg_transparent)).getBitmap(), rt, lb));
		// mMapView.getOverlays().add(roadol);

		// 直接画线
		List<GeoPoint> poq = new ArrayList<GeoPoint>();
		// 加载当前路线信息
		LineInfo info = allLines.get(currentLineIndex);

		Logger.i(this.getClass(), "当前线路由" + info.getPoints().size() + "个点组成");
		for (CoordPoint point : info.getPoints()) {
			// 进行坐标转换
			poq.add(CoordinateConvert.fromWgs84ToBaidu(new GeoPoint(
					(int) (point.gps_lat * 1E6), (int) (point.gps_lon * 1E6))));
		}

		linePoints = new GeoPoint[poq.size()];
		poq.toArray(linePoints);

		createLine(linePoints);
		matchToLine(info);

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
			switch (getDisplayMetrics()) {
			case 0:
				// 480分辨率
				extend = 1012;
				break;
			case 1:
				// 高分辨率
				break;
			}

			mMapController.zoomToSpan(
					(int) (((lat_max - lat_min)) * 1E6 + extend),
					(int) (((lon_max - lon_min)) * 1E6 + extend));

			mMapController.setCenter(centerp);
			// mMapController.animateTo(centerp);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	private int getDisplayMetrics() {

		int flag = 1;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;

		/*
		 * Toast.makeText(InFactoryActivity.this, width+"*"+height,
		 * Toast.LENGTH_LONG).show();
		 */

		if (width == 480 && (height == 854 || height == 800)) {
			flag = 0;
		}
		return flag;
	}

	private void createLine(GeoPoint[] gpoints) {
		Logger.i(this.getClass(), "开始绘制道路地图线");
		// 直接添加线层，效果不佳有锯齿

		// 构建线
		Geometry lineGeometry = new Geometry();
		lineGeometry.setPolyLine(gpoints);
		// 设定样式
		Symbol lineSymbol = new Symbol();
		Symbol.Color lineColor = lineSymbol.new Color();
		lineColor.red = 0;
		lineColor.green = 0;
		lineColor.blue = 255;
		lineColor.alpha = 180;
		int width = DensityUtil.dip2px(InFactoryActivity.this, 2);
		lineSymbol.setLineSymbol(lineColor, width);
		// 生成Graphic对象
		Graphic lineGraphic = new Graphic(lineGeometry, lineSymbol);
		roadsOverlay.setData(lineGraphic);

		mMapView.refresh();
	}

	private void showRemindSettingDialog(final StationItem value) {
		String tiptitle = (value.isRemind() ? "取消" : "设置")
				+ ("车辆到达“" + value.getName() + "”站提醒？");
		String tipcontent = value.isRemind() ? ""
				: "为了避免您错过通勤车乘坐，建议设置您乘车位置的上一站为闹钟提醒站点";
		CustomDialog dia = new CustomDialog.Builder(InFactoryActivity.this)
				.setTitle(tiptitle)
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						showLoading("设置中...", LOADING_KEY_REMINDSUBMIT, false);
						LineInfo line = allLines.get(currentLineIndex);
						biz.SubmitRemind(value.getId(), line.line_id,
								line.area_type, !value.isRemind(),
								new BizResultProcess<Boolean>() {
									@Override
									public void onBizExecuteError(
											Exception exception, Error error) {
										dismissLoading(LOADING_KEY_REMINDSUBMIT);
										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												stationlay.cancelEdit(value
														.getId());
											}
										});
										ToastMessage("闹钟设置失败");
										HandleLogicErrorInfo(exception);
									}

									@Override
									public void onBizExecuteEnd(
											BizDataTypeEnum datatype, Boolean t) {
										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												String stationid = value
														.getId();
												boolean isremind = !value
														.isRemind();
												String hintStr = isremind ? "到站提醒设置成功"
														: "已取消到站提醒";

												// 修改地图提醒状态
												stationlay.setItemRemind(
														stationid, isremind);
												// 修改本地内存中提醒状态
												updateRemind(currentLineIndex,
														stationid, isremind);
												stationlay
														.cancelEdit(stationid);
												dismissLoading(LOADING_KEY_REMINDSUBMIT);

												// 更新items值,重新绘制地图,会出现短暂闪烁，优化办法处理中.....
												StationItem stationItem = null;
												for (int i = 0; i < items
														.size(); i++) {
													stationItem = items.get(i);
													if (stationItem.getId()
															.equals(value
																	.getId())) {

														stationItem
																.setRemind(isremind);
														break;
													}
												}
												/* stationlay.loadData(items); */
												stationlay
														.updateItem(stationItem);
												mMapView.refresh();
												ToastMessage(hintStr);
											}
										});

									}
								});
					
						//用户行为统计[厂内-通勤设置或取消提醒]
						new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CNTQ_SZQXTX).reportBehavior(null);
					}
				}).setMessage(tipcontent).create();
		dia.show();
	}

	private void updateRemind(int lineindex, String stationid, boolean remind) {
		for (StationInfo info : allLines.get(lineindex).getStations()) {
			if (info.getId().equals(stationid)) {
				info.status = remind;
				Logger.i(getClass(), "修改站点[" + stationid + "]提醒状态[" + remind
						+ "]成功(内存数据)");
				break;
			}
		}
	}

	private void loadStations() {
		Logger.i(this.getClass(), "开始加载站点数据");
		if (currentLineIndex < 0) {
			Logger.i(this.getClass(), "没有当前线路信息，无法加载站点数据");
			return;
		}
		LineInfo currentLine = allLines.get(currentLineIndex);
		// 加载站点数据
		if (stationlay == null) {
			stationlay = new StationOverlay(InFactoryActivity.this, mMapView);

			stationlay.setOnItemRemindTapListener(new OnItemTapListener() {

				@Override
				public void OnItemTap(final StationItem value) {
					// ToastMessage("准备修改站点：【" + value.getName() + "】");
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							showRemindSettingDialog(value);
						}
					});
				}
			});

		}
		items = new ArrayList<StationItem>();
		if (currentLine.getStations() == null)
			return;
		for (StationInfo station : currentLine.getStations()) {
			// 更新地图站点
			StationItemPositionType ptype = station.getType() == StationType.StartStaion ? StationItemPositionType.START
					: (station.getType() == StationType.EndStaion ? StationItemPositionType.END
							: StationItemPositionType.CENTER);
			GeoPoint baidupoint = CoordinateConvert
					.fromWgs84ToBaidu(new GeoPoint(
							(int) (station.getGps_lat() * 1E6), (int) (station
									.getGps_lon() * 1E6)));
			items.add(new StationItem(station.id, baidupoint,
					station.getName(), station.getAlias(), 1, station
							.isStatus(), ptype));
		}
		stationlay.loadData(items);
		mMapView.refresh();
	}

	private void enableLocationSettings() {
		Intent settingsIntent = new Intent(
				Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(settingsIntent);
	}

	private void showFavoriteConfirmDialogTest() {
		String title = "如需获取更精确的位置服务,\n请:";
		String body = "在位置设置中打开GPS";
		CustomDialog dia = new CustomDialog.Builder(InFactoryActivity.this)
				.setTitle(title)
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						isSettingBtnPressed = true;
					}
				})
				.setPositiveButton("设置", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						enableLocationSettings();
						isSettingBtnPressed = true;
						return;
					}
				}).setMessage(body).setTitleLayoutGravity(Gravity.LEFT)
				.setIsShowGpsSetting(View.VISIBLE).create();

		dia.show();

	}

	private void loadMySelf() {

		Logger.i(this.getClass(), "开始加载自己定位数据");
		if (selflocationLay == null) {
			selflocationLay = new MyLocationOverlay(mMapView);
			selflocationLay.setMarker(getResources().getDrawable(
					R.drawable.ic_myself));
			selflocationLay.enableCompass();
			selflocationLay.setLocationMode(LocationMode.NORMAL);
			mMapView.getOverlays().add(selflocationLay);
		}
		if (mLocationClient == null) {
			initBaidulocClient();
		} else {
			processLocationResponse(mLocationClient.requestLocation());
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
			public void onReceiveLocation(BDLocation location) {
				if (location != null) {

					/* 在大部分机器上，gps初次定位需要1分钟，有的甚至要两分钟才能定位。初次定位以后，再定位会快很多 */

					/*
					 * 61 ： GPS定位结果 62 ： 扫描整合定位依据失败。此时定位结果无效。 63 ：
					 * 网络异常，没有成功向服务器发起请求。此时定位结果无效。 65 ： 定位缓存的结果。 66 ：
					 * 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果 67 ：
					 * 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果 68 ：
					 * 网络连接失败时，查找本地离线定位时对应的返回结果 161： 表示网络定位结果 162~167： 服务端定位失败。
					 */

					if (location.getLocType() == 61) {
						/* ToastMessage("GPS定位结果"); */

					} else if (location.getLocType() == 161) {
						/* ToastMessage("网络定位结果"); */
					} else {
						/* ToastMessage("服务端定位失败"); */
						return;
					}

					updateToNewBDLocation(location);
				}
			}
		};

		mLocationClient.registerLocationListener(myLocationListener);
		if (!mLocationClient.isStarted()) {
			mLocationClient.start();
		}

		processLocationResponse(mLocationClient.requestLocation());
	}

	private void processLocationResponse(int responsecode) {

		/*
		 * 0：正常发起了定位。 1：服务没有启动。 2：没有监听函数。 6：请求间隔过短。 前后两次请求定位时间间隔不能小于1000ms。
		 */

		switch (responsecode) {
		case 0:
			Logger.i(getClass(), "正常发起了定位");
			break;
		case 1:
			Logger.i(getClass(), "定位服务没有启动,等待自动定位");
			break;
		case 2:
			Logger.i(getClass(), "定位服务没有监听函数");
			break;
		case 6:
			Logger.i(getClass(), "定位请求间隔过短。 前后两次请求定位时间间隔不能小于1000ms。");
			break;
		default:
			break;
		}
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
		if (firstLocation) {
			// mMapController.animateTo(new GeoPoint((int) (ld.latitude *
			// 1E6),(int) (ld.longitude * 1E6)));
			firstLocation = false;
		}

		mMapView.refresh();
	}

	private void reloadBus(boolean clearbefore) {
		Logger.i(InFactoryActivity.this.getClass(), "开始刷新班车数据");
		if (isloadingbus) {
			Logger.i(InFactoryActivity.this.getClass(), "发现已经再刷新，取消当前刷新操作");
			return;
		}
		isloadingbus = true;
		Logger.i(InFactoryActivity.this.getClass(), "刷新班车数据");
		if (mCarOverlay == null) {
			mCarOverlay = new CarOverlay(this, mMapView);
		}
		if (clearbefore)
			mCarOverlay.removeAll();

		if (currentCars == null) {
			isloadingbus = false;
			Logger.i(getClass(), "发现没有车辆信息，停止刷新车辆");
			stopLoadBus();
			return;
		}
		synchronized (currentCars) {
			
			// 如果路线换了，就要先清空下数据重新加载
			
			for (int i = 0; i < currentCars.size(); i++) {
			
				final VehicheInfo car = currentCars.get(i);
				// 获取当前车辆定位数据并显示
				if (vehiclebiz == null)
					vehiclebiz = new BizVehicheUpdate(this);
				vehiclebiz.getRealTimeVehicheData(
						Arrays.asList(car.getVehicle_vin()),
						Arrays.asList(car.getLine_id()),
						new BizResultProcess<List<VehicleRealtime>>() {
							@Override
							public void onBizExecuteError(Exception exception,
									Error error) {
								Logger.e(getClass(),
										"查询车辆【" + car.getVehiche_number()
												+ "】实时信息异常");
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										mCarOverlay.setOnLineStatus(
												car.getVehiche_number(), false);
									}
								});
							}

							@Override
							public void onBizExecuteEnd(
									BizDataTypeEnum datatype,
									List<VehicleRealtime> t) {
								if (t == null || t.size() == 0) {//没有获取到车辆数据
									Logger.e(getClass(),
											"查询车辆【" + car.getVehiche_number()
													+ "】实时信息为NULL");
									mCarOverlay.removeItem(car.vehicle_vin);
									CarListItem citem = ListQueryUtil.query(
											cars,
											new IListComparer<CarListItem>() {
												@Override
												public boolean check(
														CarListItem t) {
													if (t.Id.equals(car.vehicle_vin))
														return true;
													return false;
												}
											});
									if (citem != null) {
										if (citem.isRightline()) {//设置成不在线状态
											citem.setRightline(false);
										}
									}

									return;
								}
								//获取到车辆数据
								final VehicleRealtime realinfo = t.get(0);//现在是单辆车的数据查询，所以取第一条
								runOnUiThread(new Runnable() {
									public void run() {
										boolean isonline = VehicheStatus
												.IsVehicheStatus(
														realinfo.status,
														VehicheStatus.Online);
										
										Logger.i(getClass(), "检测到车辆状态status【"
												+ realinfo.status
												+ "】isonline【" + isonline + "】");
										
										mCarOverlay.addOrUpdate(new CarMapItem(
												car.getVehicle_vin(),
												CoordinateConvert
														.fromWgs84ToBaidu(new GeoPoint(
																(int) (realinfo.gps_lat * 1E6),
																(int) (realinfo.gps_lon * 1E6))),
												car.getVehiche_number(),
												realinfo.direction, 
												isonline));
										
										CarListItem citem = ListQueryUtil
												.query(cars,
														new IListComparer<CarListItem>() {
															@Override
															public boolean check(
																	CarListItem t) {
																if (t.Id.equals(car.vehicle_vin))
																	return true;
																return false;
															}
														});
										if (citem != null) {
											if (!citem.isRightline()) {//修改车辆状态为在行程中，会影响横向列表显示
												citem.setRightline(true);
												// hcarsadapter.notifyDataSetChanged();//刷新横向列表
											}
										}
									}
								});

							}

						});
			}

		}
		mMapView.refresh();
		isloadingbus = false;
	}

	Handler cleanCacheHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1:
				if (cleanCacheThread.isAlive()) {
					cleanCacheThread.interrupt();
				}
				cleanCacheThread = null;
				startLoadBusByRefreshBtn();
				reloadBus(false);
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void startLoadBusByRefreshBtn() {

		Logger.i(this.getClass(), "准备刷新班车数据");
		// 获取汽车
		if (!isActive()) {
			Logger.i(this.getClass(), "当前页面不在最前，停止刷新");
			return;
		}
		if (currentLineIndex < 0) {
			Logger.i(this.getClass(), "当前没有路线信息，所以没有车辆需要刷新");
			return;
		}

		LineInfo currentLine = allLines.get(currentLineIndex);
		Logger.i(getClass(), "开始查询线路[" + currentLine.getLine_id() + "]的车辆信息");

		biz.getVehiches(currentLine.getLine_id(),
				Calendar.getInstance(Locale.CHINA).getTime(),
				new BizResultProcess<List<VehicheInfo>>() {
					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							List<VehicheInfo> t) {
						currentCars = t;

						// 加载班车数据
						Logger.d(getClass(), "开始刷新班车数据");
						if (!isActive()) {
							Logger.i(this.getClass(), "当前页面不在最前，停止刷新");
							return;
						}
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								
								reloadhorizList(true );
								reloadBus(true);
								restartRefreshTimer();
							}
						});
					}

					@Override
					public void onBizExecuteError(Exception exception,
							Error error) {
						Logger.e(getClass(), "获取车辆数据异常");
						HandleLogicErrorInfo(exception);
					}
				});
	}

	private void startLoadBus() {
		Logger.i(this.getClass(), "准备刷新班车数据");
		// 获取汽车
		if (!isActive()) {
			Logger.i(this.getClass(), "当前页面不在最前，停止刷新");
			return;
		}
		if (currentLineIndex < 0) {
			Logger.i(this.getClass(), "当前没有路线信息，所以没有车辆需要刷新");
			return;
		}
		if (currentCars == null) {
			LineInfo currentLine = allLines.get(currentLineIndex);
			Logger.i(getClass(), "开始查询线路[" + currentLine.getLine_id()+ "]的车辆信息");
			biz.getVehiches(currentLine.getLine_id(),
					Calendar.getInstance(Locale.CHINA).getTime(),
					new BizResultProcess<List<VehicheInfo>>() {
						@Override
						public void onBizExecuteEnd(BizDataTypeEnum datatype,
								List<VehicheInfo> t) {
							currentCars = t;

							// 加载班车数据
							Logger.d(getClass(), "开始刷新班车数据");
							if (!isActive()) {
								Logger.i(this.getClass(), "当前页面不在最前，停止刷新");
								return;
							}
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									hideLoadingTips();
									reloadhorizList(true );
									reloadBus(true);
									restartRefreshTimer();
								}
							});
						}

						@Override
						public void onBizExecuteError(Exception exception,
								Error error) {
							Logger.e(getClass(), "获取车辆数据异常");
							HandleLogicErrorInfo(exception);
							/*
							 * if (currentCars != null ){
							 * 
							 * currentCars.clear(); currentCars = null; }
							 * reloadhorizList();
							 */
						}
					});
		} else {
			restartRefreshTimer();
		}
	}
	
	private ArrayList<CarListItem> tmp = new ArrayList<CarListItem>();
	
	private synchronized void reloadhorizList(	boolean isSearchResult ) {
		Logger.i(getClass(), "开始加载横向车辆列表数据");
		
		/*int firstVisiblePosition  = 0;*/
		
		tmp.clear();
		tmp.addAll(cars);
		
		cars.clear();
		
		if(isSearchResult){
			if (currentCars == null || currentCars.size() == 0) {
				Logger.i(getClass(), "当前车辆信息为空");
				Log.i("TAB2", "isSearchResult：当前车辆信息为空");
				carListAdapter.notifyDataSetChanged();
				
				loadingProgressBar.setVisibility(View.GONE);
	            tipsTV.setText("暂无发车安排");
	     		tipsTV.setVisibility(View.VISIBLE);
				return;
			}
			
		}else{
			if (currentCars == null || currentCars.size() == 0) {
				Logger.i(getClass(), "当前车辆信息为空");
				carListAdapter.notifyDataSetChanged();
				hl_list.scrollTo(0);
				return;
			}
		}
		
		hl_list.setVisibility(View.VISIBLE);
		for (VehicheInfo info : currentCars) {
			String timeStr = "循环发车";
			if (info.plan_start_time != null)
				timeStr = DateFormatUtils.format(info.plan_start_time, "HH:mm");
			cars.add(new CarListItem(info.vehicle_vin, info.vehiche_number, timeStr));
		}
		
		carListAdapter.notifyDataSetChanged();
		/*if(isNeedScroll){
			hl_list.scrollTo(0);
		}*/
		
	}

	private void restartRefreshTimer() {
		if (refreshTimer != null)
			refreshTimer.cancel();
		if (isAutoRefresh) {// 自动刷新
			refreshTimer = new Timer(true);
			refreshTimer.schedule(new TimerTask() {
				@Override
				public void run() {

					/* 用于刷新时重新加载线路 */
					hasLoadedCurrentLine = false;

					/* reloadBus(false); */
					refreshData();
				}
			}, refreshInterval, refreshInterval);
		} else {
			reloadBus(false);
		}
	}

	private void stopLoadBus() {
		Logger.i(this.getClass(), "停止刷新班车数据");
		if (refreshTimer != null) {
			refreshTimer.cancel();
		}
		if (vehiclebiz != null) {
			vehiclebiz.stop();
		}
	}

	/* 横向列表相关 */

	 static class CarListItem {

		public CarListItem(String id, String name, String timestr) {
			this.Id = id;
			this.name = name;
			this.time = timestr;
		}

		private String Id;

		private String name;

		private String time;
		// 在线
		private boolean online = true;
		// 在行程中
		private boolean rightline = true;

		public String getId() {
			return Id;
		}

		public void setId(String id) {
			Id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public boolean isOnline() {
			return online;
		}

		public void setOnline(boolean online) {
			this.online = online;
		}

		public boolean isRightline() {
			return rightline;
		}

		public void setRightline(boolean rightline) {
			this.rightline = rightline;
		}

	}

	 static class CarListAdapter extends BaseAdapter {

		private List<CarListItem> mData;

		private Context mContext;

		public CarListAdapter(Context context, List<CarListItem> data) {
			mContext = context;
			mData = data;
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder = null;
			
			if (convertView == null) {
				
				holder = new ViewHolder();
				convertView = View.inflate(mContext, R.layout.control_carlist_item,null);
				holder.Name = (TextView) convertView.findViewById(R.id.tv_clc_num);
				holder.Time = (TextView) convertView.findViewById(R.id.tv_clc_time);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			try {
				CarListItem item = mData.get(position);
				holder.Name.setText(item.getName());
				holder.Time.setText(item.getTime());
				
				/*在线显示时间，不在线显示循环发车*/
				/*if (item.rightline) {
					Log.i("TAB2", "rightline:  "+item.getName()+" " +item.getTime());
					holder.Time.setText(item.getTime());
				} else {
					Log.i("TAB2", "qita: "+item.getName() + "循环发车--");
					holder.Time.setText("循环发车--");
				}*/
			} catch (Exception err) {
				err.printStackTrace();
			}
			return convertView;
		}

		

		@Override
		public boolean isEmpty() {
			return mData == null || mData.size() == 0;
		}

		class ViewHolder {
			public TextView Name;

			public TextView Time;
		}
	}
}
