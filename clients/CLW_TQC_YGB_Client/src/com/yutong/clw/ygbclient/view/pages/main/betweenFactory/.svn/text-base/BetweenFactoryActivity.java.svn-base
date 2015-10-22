package com.yutong.clw.ygbclient.view.pages.main.betweenFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.time.DateFormatUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.PopupWindow.OnDismissListener;
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
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.utils.ListQueryUtil;
import com.yutong.clw.ygbclient.utils.ListQueryUtil.IListComparer;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.betweenFactory.BizBetweenFactory;
import com.yutong.clw.ygbclient.view.bizAccess.common.BizVehicheUpdate;
import com.yutong.clw.ygbclient.view.bizAccess.inFactory.BizInFactory;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizBusDriver;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.common.map.CarOverlay;
import com.yutong.clw.ygbclient.view.common.map.CarOverlay.CarMapItem;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.StationItem;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.StationItemPositionType;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.driver.BusDriverDetailsActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.DateChooseActivity;
import com.yutong.clw.ygbclient.view.pages.setting.SettingActivity;
import com.yutong.clw.ygbclient.view.pages.setting.about.AboutActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.util.DensityUtil;
import com.yutong.clw.ygbclient.view.widget.ExHorizontalListView;
import com.yutong.clw.ygbclient.view.widget.HorizontalListView;
import com.yutong.clw.ygbclient.view.widget.PopCheckList;
import com.yutong.clw.ygbclient.view.widget.PopMenu;
import com.yutong.clw.ygbclient.view.widget.PopCheckList.OnCheckChangedListener;

/**
 * 厂间通勤主界面
 * 
 * @author zhouzc
 */
public class BetweenFactoryActivity extends RemindAccessActivity implements OnClickListener
{

    private static final int CLEAN_CACHE_SUCCESS = 0x1;
    private static final int LoadingFlag = 0x2;
    
    List<String> names = new ArrayList<String>();
    /***** 业务变量 *******/
    private BizBetweenFactory basebiz;

    private BizVehicheUpdate vehiclebiz;

    /***** 控件变量 *******/
    // 地图遮盖层
    private View mapcover;

    // 弹出线路选择框
    private PopCheckList popcl;

    // 顶部选择项
    private TextView txt_small;

    private ImageView iv_tri;

    // 时间显示
    private TextView txt_Time;

    // 横向车辆列表
    /*private ExHorizontalListView hl_list;*/
    private HorizontalListView	hl_list;
    
    // 刷新定时器
    private Timer refreshTimer = null;

    // 刷新间隔
    private long refreshInterval = 30000;

    private boolean autorefresh = false;

    // 横向列表适配器
    private CarListAdapter hcarsadapter;

    /***** 数据变量 *******/
    // 路线坐标点
    private GeoPoint[] linePoints;

    // 所有线路信息
    private List<LineInfo> allLines = null;

    // 车辆列表显示数据
    private List<CarListItem> cars;

    // 当前车辆数据
    private List<VehicheInfo> currentCars;

    // 当前选择时间
    private Date currentTime;

    /***** 标志变量 *******/
    // 当前线路序号
    private int currentLineIndex = -1;

    // 是否正在加载车辆信息
    private boolean isloadingbus = false;

    // 地图是否完成加载
    private boolean ismapready = false;

    // 当前路线是否已经加载
    private boolean hasLoadedCurrentLine = false;

    private final static int REQUESTCODE_SELECTTIME = 0X0001;

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

    // 清理缓存线程
    private Thread cleanCacheThread = null;

    // 手势检测
    private GestureDetector gdetector;

    private LinearLayout calendar_LL, ll_amb_host;

	private ProgressBar loadingProgressBar;
	private TextView tipsTV;
	
	private String tipStr_loading = "正在查询发车安排";
	
	private Button mZoomOutBtn,mZoomInBtn;
	
	private boolean canZoom;
	
	/*
	 * ****************************司机信息相关变量*******************************/
	private PopMenu popMenu;
	
	private Context mContext;
	
	private BizBusDriver bizBusDriver;
	
	private List<String> mVehicle_vins  = new ArrayList<String>();

    private List<String> mLine_ids = new ArrayList<String>();
  
    private List<VehicleDriver> mDriverInfoList = new ArrayList<VehicleDriver>();
    
    private int mWidth = 350;
    
    /*
	 * ****************************司机信息相关变量*******************************/
	
    private OnNetworkChangeListener mNetworkListener;
    
    private List<VehicleDriver> dirverDataList;
    
    /********************/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_betweenfactory);
        mContext =this;
        AreaType myselect = PrefDataUtil.getFactory(BetweenFactoryActivity.this);
		
		if(lastSelectAreaType == null || lastSelectAreaType != myselect){
			lastSelectAreaType = myselect;
		}
		
        setAllowNetworkCheck(true);
        initViews();
        initBiz();
        setCurrentTime(Calendar.getInstance().getTime());
        showLoadingTips(tipStr_loading);
        loadLines();
        initMap();
        
        new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CJTQ_LOAD).reportBehavior(null);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        addNetWorkListener();
        /*设置日期*/
        if(!isFromActivityResult){
        	isFromActivityResult = false;
        	setCurrentTime(Calendar.getInstance().getTime());
        	
        	 /* onResume中重新加载班车信息使用 */
            if (currentCars != null && currentCars.size() > 0)
            {
                currentCars.clear();
                currentCars = null;
            }
            
        	reloadHorizList(false);
		}
		
		
        if (!isForceToDestory())
        {
            Logger.i(getClass(), "Between onResume");
            refreshInterval = PrefDataUtil.getMapRefreshInterval(this) * 1000;
            autorefresh = PrefDataUtil.getAutoMapRefresh(this);
            newresume = true;
            showMap();
            if (mLocationClient != null && myLocationListener != null)
                mLocationClient.registerLocationListener(myLocationListener);
            refreshData();
        }

    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.btn_it_left:
            Intent gotosetting = new Intent(BetweenFactoryActivity.this, SettingActivity.class);
            gotosetting.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(gotosetting);

            break;
        case R.id.btn_it_right:
            Intent gotoanxin = new Intent(BetweenFactoryActivity.this, AboutActivity.class);
            gotoanxin.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(gotoanxin);
            break;
        case R.id.rl_it_center:
            showOrDismissPopLines();
            
            break;
        case R.id.tv_amb_time:
            showTimeSelectDialog(currentTime);
            break;
        case R.id.btn_amb_resize:
        	
        	 //用户行为统计[厂间-回归中心点]  
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CJTQ_HUIGUI).reportBehavior(null);
            
            if (mMapController != null){
            	
                if (mCarOverlay != null){
                	 mCarOverlay.adapteToMapRotate();
                }
                LineInfo info = allLines.get(currentLineIndex);
                if (info != null){
                    matchToLine(info);
                }
                // 这个是动画效果要放在后面，否则会阻碍后面的动作
                mMapController.setRotation(0);
            }
            break;

        case R.id.btn_amb_refresh:

            if (DateUtils.dateToStr(new Date(), DateUtils.TIME_FORMAT).equals(DateUtils.dateToStr(currentTime, DateUtils.TIME_FORMAT)))
            {
                refreshBus();
            }
            else
            {
                mCarOverlay.removeAll();
            }
            //用户行为统计[厂间-页面刷新]  
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CJTQ_SHUAXIN).reportBehavior(null);

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
			
			 //用户行为统计[厂间-缩放]  
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CJTQ_SUOFANG).reportBehavior(null);

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
			 //用户行为统计[厂间-缩放]  
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CJTQ_SUOFANG).reportBehavior(null);

			break;
        default:
            break;
        }
    }

    private void refreshBus()
    {
        if (cleanCacheThread == null)
        {
            cleanCacheThread = new Thread(new Runnable()
            {

                @Override
                public void run()
                {
                    /*
                     * ProxyManager.getInstance(BetweenFactoryActivity.this)
                     * .clearAppCache();
                     */
                    cleanCacheHandler.sendEmptyMessage(CLEAN_CACHE_SUCCESS);
                }

            });
            cleanCacheThread.start();
        }
    }

    Handler cleanCacheHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {

            switch (msg.what)
            {
            case CLEAN_CACHE_SUCCESS:
                if (cleanCacheThread.isAlive())
                {
                    cleanCacheThread.interrupt();
                }
                cleanCacheThread = null;
                startLoadBusByRefreshBtn();
                break;
            }
            super.handleMessage(msg);
        }
    };

    Bitmap currentCover = null;

    boolean newresume = false;

    private void hideMap()
    {
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

        delayRun(new Runnable()
        {

            @Override
            public void run()
            {
                if (!newresume)
                {
                    mMapView.setVisibility(View.INVISIBLE);
                    mMapView.setVisibility(View.GONE);
                }
            }
        }, 100);

    }

    private void showMap()
    {
        if (ismapready)
        {
            mMapView.setVisibility(View.VISIBLE);
            mMapView.onResume();
            delayRun(new Runnable()
            {

                @Override
                public void run()
                {
                    mapcover.setVisibility(View.GONE);
                }
            }, 200);
        }
    }
    
    private AreaType lastSelectAreaType = null;
    
    @Override
    protected void onPause()
    {
        super.onPause();
        removeNetWorkListener();
        try
        {
            newresume = false;
            hideMap();
            if (mLocationClient != null && myLocationListener != null)
                mLocationClient.unRegisterLocationListener(myLocationListener);
            stopLoadBus();

            if (basebiz != null)
                basebiz.stop();
            if (vehiclebiz != null)
                vehiclebiz.stop();

            /* onResume中重新加载班车信息使用 */
            if (currentCars != null && currentCars.size() > 0)
            {
                currentCars.clear();
                currentCars = null;
            }
            
            hasLoadedCurrentLine = false;
            
           /* popcl.getMdata().clear();
			names.clear();*/
            
            lastSelectAreaType = PrefDataUtil.getFactory(BetweenFactoryActivity.this);
        }
        catch (Exception err)
        {
            err.printStackTrace();
        }

    }

    @Override
    protected void onStop()
    {
        if (basebiz != null)
            basebiz.stop();
        if (vehiclebiz != null)
            vehiclebiz.stop();

        /* onResume中重新加载班车信息使用 */
        if (currentCars != null && currentCars.size() > 0)
        {
            currentCars.clear();
            currentCars = null;
        }

        super.onStop();
    }

    

    @Override
    protected void onDestroy()
    {

        if (basebiz != null)
            basebiz.destory();
        if (vehiclebiz != null)
            vehiclebiz.destory();
        mMapView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        mMapView.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void destoryRunningWork()
    {
        super.destoryRunningWork();
        if (basebiz != null)
            basebiz.stop();
        if (vehiclebiz != null)
            vehiclebiz.stop();
        stopLoadBus();
    }

    @Override
    protected boolean hasTitle()
    {
        return true;
    }

    @Override
    protected void loadTitleByPageSetting(Button btn_left, Button btn_right, RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
            TextView tv_small)
    {
        btn_left.setBackgroundResource(R.drawable.bg_settingbtn);
        btn_right.setBackgroundResource(R.drawable.bg_anxinbtn);
        tv_large.setText("厂间通勤");
        tv_small.setText("");

        rl_center.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);

        tv_small.setVisibility(View.GONE);
        iv_tri.setVisibility(View.GONE);
        this.txt_small = tv_small;
        this.iv_tri = iv_tri;
    }
    
    private void showDriverPop(int dataIndex,final View view){
    	
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
				
				//用户行为统计[厂间-加载司机弹出框]
				new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CJTQ_SJTCK).reportBehavior(null);
			}
		}
    }
	
    
    
    private void initViews()
    {
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
					
					//用户行为统计[厂间-加载司机明细]
					new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CJTQ_SJMX).reportBehavior(null);
				}
			});
		}
    	
    	mZoomOutBtn = (Button) findViewById(R.id.btn_zoom_out);
		mZoomOutBtn.setOnClickListener(this);
		mZoomInBtn = (Button) findViewById(R.id.btn_zoom_in);
		mZoomInBtn.setOnClickListener(this);
		
    	loadingProgressBar  =(ProgressBar) findViewById(R.id.loadingBar);
    	tipsTV  =(TextView) findViewById(R.id.tipsTV);
    	
        ((Button) findViewById(R.id.btn_amb_refresh)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_amb_resize)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_amb_calendar)).setOnClickListener(this);
        mapcover = findViewById(R.id.ll_amb_mapcover);
        mapcover.setVisibility(View.GONE);
        txt_Time = (TextView) findViewById(R.id.tv_amb_time);
        txt_Time.setOnClickListener(this);
        hl_list = (HorizontalListView) findViewById(R.id.hl_amb_list);
        cars = new ArrayList<CarListItem>();
        hcarsadapter = new CarListAdapter(this, cars);
        hl_list.setAdapter(hcarsadapter);
        hl_list.setOnItemClickListener(new OnItemClickListener()
        {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showDriverPop(position,view);
                mCarOverlay.animateTo(cars.get(position).getId());
                mCarOverlay.splashItemTitle(cars.get(position).getId());
			}
        });

        calendar_LL = (LinearLayout) findViewById(R.id.calendar_LL);
        ll_amb_host = (LinearLayout) findViewById(R.id.calendar_busStartArrange_LL);

        /* 手势 */
        gdetector = new GestureDetector(new OnGestureListener()
        {
            @Override
            public boolean onDown(MotionEvent e)
            {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e)
            {
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
            {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e)
            {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return false;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
            {
                float deltax = e2.getX() - e1.getX();
                float deltay = e2.getY() - e1.getY();
                if (Math.abs(deltay) > Math.abs(deltax))
                {
                    if (deltay < -40)
                    {
                        /* hl_list.setVisibility(View.GONE); */
                        ll_amb_host.setVisibility(View.GONE);
                    }
                    else if (deltay > 40)
                    {
                        /* hl_list.setVisibility(View.VISIBLE); */
                        ll_amb_host.setVisibility(View.VISIBLE);
                    }
                }
                return true;
            }
        });

        findViewById(R.id.title_amb).setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                return gdetector.onTouchEvent(arg1);
            }
        });

        ll_amb_host.setOnTouchListener(new OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return gdetector.onTouchEvent(event);
            }
        });

        /*
         * calendar_LL.setOnTouchListener(new OnTouchListener() {
         * @Override public boolean onTouch(View v, MotionEvent event) { return
         * gdetector.onTouchEvent(event); } }); hl_list.setOnTouchListener(new
         * OnTouchListener() {
         * @Override public boolean onTouch(View v, MotionEvent event) { return
         * gdetector.onTouchEvent(event); } });
         */
        hl_list.setVisibility(View.VISIBLE);
    }
    
    private void initBiz()
    {
        if (basebiz == null)
            basebiz = new BizBetweenFactory(mContext);
    }

    private void loadLines()
    {

        if (popcl == null)
        {
            popcl = new PopCheckList(this);
            popcl.setOnCheckChangedListener(new OnCheckChangedListener()
            {
                @Override
                public void OnChecked(int index, String txt)
                {
                    if (currentLineIndex == index)
                    {
                        popcl.dismiss();
                        return;
                    }
                    
                    //用户行为统计[厂间-切换厂区]     
                    new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CJTQ_QHCQ).reportBehavior(null);

                    /*PrefDataUtil.setFactory(getApplicationContext(), data);*/
                    showLoadingTips(tipStr_loading);
                    
                    txt_small.setText(txt);
                    currentLineIndex = index;
                    
                    Log.i("TAB2", "OnChecked currentLineIndex:"+currentLineIndex);
                    stopLoadBus();

                    /* 切换重新加载发车安排 */
                    if (currentCars != null && currentCars.size() > 0)
                    {
                        currentCars.clear();
                        currentCars = null;
                    }
                    reloadHorizList(false);
                    
                    loadCurrentLine();
                    // ToastMessage("select:[" + index + "]" + txt);
                    popcl.dismiss();
                }
            });
            popcl.setOnDismisslistener(new OnDismissListener()
            {
                @Override
                public void onDismiss()
                {
                    // TODO title里面的小三角可能要变色
                }
            });
        }
        
        BizDataTypeEnum result = basebiz.getBetweenFactoryLines(new BizResultProcess<List<LineInfo>>()
        {

            @Override
            public void onBizExecuteError(Exception exception, Error error)
            {
                dismissLoading(2);
                ToastMessage("获取路线信息失败");
                HandleLogicErrorInfo(exception);
            }

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, final List<LineInfo> t)
            {
                dismissLoading(LoadingFlag);
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
						/*hasLoadedCurrentLine = true;*/
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								hasLoadedCurrentLine = true;
								refreshLineInfos(t);
								hasLoadedCurrentLine = false;
								startLoadBus();
							}
						});
						/*reloadLineStations(true);
						startLoadBus();*/
					}
					return;
				}
				
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        if (t == null)
                        {
                            Logger.i(this.getClass(), "获取路线信息为NULL");
                            return;
                        }
                        if (t.size() == 0)
                        {
                            Logger.i(this.getClass(), "获取路线信息条数为0");
                            return;
                        }
                        allLines = t;
                        
                        // 获取当前选择的线路
                		String lname = popcl.getSelectionValue();
                		
                        int initialindex = -1;
                        // 这边根据读取厂区选择配置来设置

                        AreaType myselect = PrefDataUtil.getFactory(BetweenFactoryActivity.this);
                        names.clear();
                        int count = 0;
                        for (LineInfo info : t)
                        {
                            names.add(info.line_name);
                            count++;
                            if (initialindex != -1)
                                continue;
                            
                            if (lname == null) {
                            	if ((myselect == AreaType.FirstFactory && info.area_type == AreaType.FirstFactory)
                                        || (myselect == AreaType.SecondFactory && info.area_type == AreaType.SecondFactory))
                                {
                                    initialindex = count - 1;

                                }
                            }else{
                            	if (info.line_name.equals(lname)) {
                					initialindex = count - 1;
                				}
                            }
                        }
                        initialindex = initialindex == -1 ? 0 : initialindex;
                        txt_small.setVisibility(View.VISIBLE);
                        txt_small.setText(names.get(initialindex));
                        if (count > 1)
                        {
                            iv_tri.setVisibility(View.VISIBLE);
                        }
                        popcl.setData(names, initialindex);
                        
                        currentLineIndex = initialindex;
                        
                        Log.i("TAB2", "currentLineIndex:"+currentLineIndex);
                        
                        if (ismapready && !hasLoadedCurrentLine)
                        {
                            Logger.i(this.getClass(), "发现还没开始加载当前线路信息，开始加载");
                            loadCurrentLine();
                        }
                    }
                });

            }
        });
        
//        由于线路失效时间为一周，暂不进行加载线路提示
        /*if (result == BizDataTypeEnum.FromNetwork)
        {
            showLoading("通勤路线获取中...", LoadingFlag, false);
        }*/
    }

    private void initMap()
    {
        mMapView = (MapView) findViewById(R.id.bmv_amb_map);
        mMapController = mMapView.getController();
        mMapController.enableClick(true);
        // 设置地图缩放级别
        mMapController.setZoom(16);
        // 显示内置缩放控件
        mMapView.setBuiltInZoomControls(false);
        // 设置中心点为宇通
        GeoPoint centerpoint = new GeoPoint((int) (cLat * 1E6), (int) (cLon * 1E6));
        mMapController.setCenter(centerpoint);
        mMapController.setRotationGesturesEnabled(true);// 旋转设置
        mMapController.setOverlookingGesturesEnabled(false);// 俯视角设置
        mMapController.setCompassMargin(0, 0);
        mMapListener = new MKMapViewListener()
        {
            @Override
            public void onMapMoveFinish()
            {
                /**
                 * 在此处理地图移动完成回调 缩放，平移等操作完成后，此回调被触发
                 */
                Logger.i(getClass(), "地图onMapMoveFinish");
                // 刷新地图上面得车辆旋转方向
                if (mCarOverlay != null)
                    mCarOverlay.adapteToMapRotate();
            }

            @Override
            public void onClickMapPoi(MapPoi mapPoiInfo)
            {
                /**
                 * 在此处理底图poi点击事件 显示底图poi名称并移动至该点 设置过：
                 * mMapController.enableClick(true); 时，此回调才能被触发
                 */
            }

            @Override
            public void onGetCurrentMap(Bitmap b)
            {
                /**
                 * 当调用过 mMapView.getCurrentMap()后，此回调会被触发 可在此保存截图至存储设备
                 */
            }

            @Override
            public void onMapAnimationFinish()
            {
            }

            /**
             * 在此处理地图载完成事件
             */
            @Override
            public void onMapLoadFinish()
            {
                Logger.i(getClass(), "地图加载完成");
                doworkWhenMapLoaded();
            }
        };
        mMapView.regMapViewListener(YtApplication.getInstance().getBaiduMapManager().getBMapManager(), mMapListener);

        // 实现对地图状态改变的处理
        MKMapStatusChangeListener slistener = new MKMapStatusChangeListener()
        {
            public void onMapStatusChange(MKMapStatus mapStatus)
            {
                // float zoom = mapStatus.zoom; // 地图缩放等级
                // int overlooking = mapStatus.overlooking; // 地图俯视角度
                // int rotate = mapStatus.rotate; // 地图旋转角度

            }
        };
        // 为 mapview 注册地图状态监听者。本方法只能注册一次，多次注册会覆盖
        mMapView.regMapStatusChangeListener(slistener);

        mMapView.regMapTouchListner(new MKMapTouchListener()
        {

            @Override
            public void onMapLongClick(GeoPoint arg0)
            {
            }

            @Override
            public void onMapDoubleClick(GeoPoint arg0)
            {
            }

            @Override
            public void onMapClick(GeoPoint arg0)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (stationlay != null)
                            stationlay.clearAllFromEdit();
                    }
                });

            }
        });
    }

    private void doworkWhenMapLoaded()
    {
        ismapready = true;
        loadMySelf();
        if (allLines != null && allLines.size() != 0)
        {
            Logger.i(this.getClass(), "此时路线信息不为空");
            if (!hasLoadedCurrentLine)
            {
                Logger.i(this.getClass(), "发现还没开始加载当前线路信息，开始加载");
                loadCurrentLine();
            }
        }
        showMap();
    }

    private void loadMySelf()
    {
        Logger.i(this.getClass(), "开始加载自己定位数据");
        if (selflocationLay == null)
        {
            selflocationLay = new MyLocationOverlay(mMapView);
            selflocationLay.setMarker(getResources().getDrawable(R.drawable.ic_myself));
            selflocationLay.enableCompass();
            selflocationLay.setLocationMode(LocationMode.NORMAL);
            mMapView.getOverlays().add(selflocationLay);
        }
        // 使用百度定位服务
        initBaidulocClient();
    }

    private void loadCurrentLine()
    {
        Logger.i(this.getClass(), "开始加载当前道路信息");
        hasLoadedCurrentLine = true;
        loadRoads();
        loadStations();
        
        startLoadBus();
        /*if (DateUtils.dateToStr(new Date(), DateUtils.TIME_FORMAT).equals(DateUtils.dateToStr(currentTime, DateUtils.TIME_FORMAT)))
        {
            startLoadBus();
        }*/
    }

    private void loadRoads()
    {
        if (roadsOverlay == null)
        {
            roadsOverlay = new GraphicsOverlay(mMapView);
            mMapView.getOverlays().add(roadsOverlay);
        }
        else
        {
            roadsOverlay.removeAll();
        }

        Logger.i(this.getClass(), "开始加载道路数据");
        if (currentLineIndex < 0)
        {
            Logger.i(this.getClass(), "没有当前线路信息，无法加载道路数据");
            return;
        }

        // // 贴图形式
        // GroundOverlay roadol = new GroundOverlay(mMapView);
        //
        // GeoPoint lb = new GeoPoint((int) (34.699594 * 1E6),
        // (int) (113.701160 * 1E6));
        // GeoPoint rt = new GeoPoint((int) (34.688471 * 1E6),
        // (int) (113.690506 * 1E6));
        //
        // roadol.addGround(new Ground(((BitmapDrawable) getResources()
        // .getDrawable(R.drawable.bg_map_road_infactory)).getBitmap(),
        // rt, lb));
        // mMapView.getOverlays().add(roadol);

        // 直接画线
        List<GeoPoint> poq = new ArrayList<GeoPoint>();
        
        Log.i("TAB2", "loadRoads() currentLineIndex:"+currentLineIndex);
        // 加载当前路线信息
        LineInfo info = allLines.get(currentLineIndex);

        Logger.i(this.getClass(), "当前线路由" + info.getPoints().size() + "个点组成");
        for (CoordPoint point : info.getPoints())
        {
            // 进行坐标转换
            poq.add(CoordinateConvert.fromWgs84ToBaidu(new GeoPoint((int) (point.gps_lat * 1E6), (int) (point.gps_lon * 1E6))));
        }

        /****/
        linePoints = new GeoPoint[poq.size()];
        poq.toArray(linePoints);

        createLine(linePoints);
        matchToLine(info);
    }

    private void refreshData()
    {
        if (ismapready)
        {
            /*
             * if (allLines == null || allLines.size() == 0) {
             * Logger.i(getClass(), "发现厂内路线为空，重新加载路线数据"); loadLines(); } else {
             * startLoadBus(); }
             */

            Logger.i(getClass(), "重新加载路线数据");
            
            hasLoadedCurrentLine = false;
            if(currentCars!=null && currentCars.size()>0){
            	currentCars.clear();
                currentCars = null;
            }
            
            /* 通勤路线获取 */
            loadLines();

            /* startLoadBus(); */
            
        }
    }

    private void matchToLine(LineInfo info)
    {
        if (info == null || info.getPoints() == null || info.getPoints().size() == 0)
            return;
        matchtopoints(info.getPoints());
    }

    private void matchtopoints(List<CoordPoint> points)
    {
        try
        {
            if (points == null || points.size() == 0)
                return;
            CoordPoint fp = points.get(0);
            double lat_max = fp.gps_lat;
            double lat_min = fp.gps_lat;
            double lon_max = fp.gps_lon;
            double lon_min = fp.gps_lon;
            for (CoordPoint point : points)
            {
                if (point.gps_lat < lat_min)
                {
                    lat_min = point.gps_lat;
                }
                else if (point.gps_lat > lat_max)
                {
                    lat_max = point.gps_lat;
                }

                if (point.gps_lon < lon_min)
                {
                    lon_min = point.gps_lon;
                }
                else if (point.gps_lon > lon_max)
                {
                    lon_max = point.gps_lon;
                }
            }
            GeoPoint centerp = CoordinateConvert.fromWgs84ToBaidu(new GeoPoint((int) ((lat_max + lat_min) / 2 * 1E6),
                    (int) ((lon_max + lon_min) / 2 * 1E6)));
            int extend = 3000;
            mMapController.zoomToSpan((int) (lat_max * 1E6 - lat_min * 1E6 + extend), (int) (lon_max * 1E6 - lon_min * 1E6 + extend));
            mMapController.setCenter(centerp);
            // mMapController.animateTo(centerp);
        }
        catch (Exception err)
        {
            err.printStackTrace();
        }
    }

    private void createLine(GeoPoint[] gpoints)
    {
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
        int width = DensityUtil.dip2px(BetweenFactoryActivity.this, 2);
        lineSymbol.setLineSymbol(lineColor, width);
        // 生成Graphic对象
        Graphic lineGraphic = new Graphic(lineGeometry, lineSymbol);
        roadsOverlay.setData(lineGraphic);

        mMapView.refresh();
    }

    private void loadStations()
    {
        Logger.i(this.getClass(), "开始加载站点数据");
        if (currentLineIndex < 0)
        {
            Logger.i(this.getClass(), "没有当前线路信息，无法加载站点数据");
            return;
        }
        Log.i("TAB2", "loadStations() currentLineIndex:"+currentLineIndex);
        LineInfo currentLine = allLines.get(currentLineIndex);
        // 加载站点数据
        if (stationlay == null)
        {
            stationlay = new StationOverlay(BetweenFactoryActivity.this, mMapView,
                    getResources().getDrawable(R.drawable.ic_station_normal), getResources().getDrawable(R.drawable.ic_station_normal),
                    null, getResources().getDrawable(R.drawable.ic_station_active), null, null, null, null);
            stationlay.setNeedEdit(true);
        }
        List<StationItem> items = new ArrayList<StationItem>();
        if (currentLine.getStations() == null)
            return;
        for (StationInfo station : currentLine.getStations())
        {
            // 更新地图站点
            StationItemPositionType ptype = station.getType() == StationType.StartStaion ? StationItemPositionType.START : (station
                    .getType() == StationType.EndStaion ? StationItemPositionType.END : StationItemPositionType.CENTER);
            GeoPoint baidupoint = CoordinateConvert.fromWgs84ToBaidu(new GeoPoint((int) (station.getGps_lat() * 1E6), (int) (station
                    .getGps_lon() * 1E6)));
            StationItem sitem = new StationItem(station.id, baidupoint, station.getName(), station.getAlias(), 1, station.isFavorites(),
                    ptype);
            sitem.setNeedEdit(false);
            items.add(sitem);

        }
        stationlay.loadData(items);

        // stationlay.notifyDataSetChanged();

        mMapView.refresh();
    }

    private void initBaidulocClient()
    {
        mLocationClient = YtApplication.getInstance().getBaiduMapManager().getLocationClient();
        myLocationListener = new BDLocationListener()
        {

            @Override
            public void onReceivePoi(BDLocation arg0)
            {

            }

            @Override
            public void onReceiveLocation(BDLocation arg0)
            {
                updateToNewBDLocation(arg0);
            }
        };

        mLocationClient.registerLocationListener(myLocationListener);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
    }

    private void updateToNewBDLocation(BDLocation location)
    {
        if (location == null)
            return;
        LocationData ld = new LocationData();
        ld.latitude = location.getLatitude();
        ld.longitude = location.getLongitude();
        // ld.direction = location.getDerect();
        selflocationLay.setData(ld);
        // mMapController.animateTo(new GeoPoint((int)(ld.latitude*1E6),
        // (int)(ld.longitude*1E6)));
        mMapView.refresh();
    }

    private void reloadBus(boolean clearbefore)
    {
        Logger.i(getClass(), "reloadBus() 开始刷新班车数据");
        if (isloadingbus)
        {
            Logger.i(getClass(), "发现已经再刷新，取消当前刷新操作");
            return;
        }
        isloadingbus = true;
        if (mCarOverlay == null)
        {
            mCarOverlay = new CarOverlay(this, mMapView);
        }

        // //如果路线换了，就要先清空下数据重新加载
        if (clearbefore)
            mCarOverlay.removeAll();

        if (currentCars == null)
        {
            isloadingbus = false;
            Logger.i(getClass(), "reloadBus()发现没有车辆信息，停止刷新车辆");
            stopLoadBus();
            return;
        }
        
        synchronized (currentCars)
        {
            for (int i = 0; i < currentCars.size(); i++)
            {
                final VehicheInfo car = currentCars.get(i);
                // 获取当前车辆定位数据并显示
                if (vehiclebiz == null)
                    vehiclebiz = new BizVehicheUpdate(this);
                vehiclebiz.getRealTimeVehicheData(Arrays.asList(car.getVehicle_vin()), Arrays.asList(car.getLine_id()),
                        new BizResultProcess<List<VehicleRealtime>>()
                        {
                            @Override
                            public void onBizExecuteError(Exception exception, Error error)
                            {
                                Logger.e(getClass(), "查询车辆【" + car.getVehiche_number() + "】实时信息异常");
                                runOnUiThread(new Runnable()
                                {

                                    @Override
                                    public void run()
                                    {
                                        mCarOverlay.setOnLineStatus(car.getVehicle_vin(), false);
                                    }
                                });
                                HandleLogicErrorInfo(exception);
                            }

                            @Override
                            public void onBizExecuteEnd(BizDataTypeEnum datatype, List<VehicleRealtime> t)
                            {
                                if (t == null || t.size() == 0)
                                {
                                    Logger.e(getClass(), "查询车辆【" + car.getVehiche_number() + "】实时信息为NULL");
                                    mCarOverlay.removeItem(car.getVehiche_number());
                                    CarListItem citem = ListQueryUtil.query(cars, new IListComparer<CarListItem>()
                                    {
                                        @Override
                                        public boolean check(CarListItem t)
                                        {
                                            if (t.getId().equals(car.vehicle_vin))
                                                return true;
                                            return false;
                                        }
                                    });
                                    if (citem != null)
                                    {
                                        if (citem.isRightline())
                                        {
                                            citem.setRightline(false);
                                            // runOnUiThread(new Runnable() {
                                            // public void run() {
                                            // hcarsadapter.notifyDataSetChanged();
                                            // }
                                            // });
                                        }
                                    }
                                    return;
                                }
                                final VehicleRealtime realinfo = t.get(0);
                                runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                        mCarOverlay.addOrUpdate(new CarMapItem(car.getVehicle_vin(), CoordinateConvert
                                                .fromWgs84ToBaidu(new GeoPoint((int) (realinfo.gps_lat * 1E6),
                                                        (int) (realinfo.gps_lon * 1E6))), car.getVehiche_number(), realinfo.direction,
                                                VehicheStatus.IsVehicheStatus(realinfo.status, VehicheStatus.Online)));
//                                        CarListItem citem = ListQueryUtil.query(cars, new IListComparer<CarListItem>()
//                                        {
//                                            @Override
//                                            public boolean check(CarListItem t)
//                                            {
//                                                if (t.getId().equals(car.vehicle_vin))
//                                                    return true;
//                                                return false;
//                                            }
//                                        });
//                                        if (citem != null)
//                                        {
//                                            if (!citem.isRightline())
//                                            {
//                                                citem.setRightline(true);
//                                                // hcarsadapter.notifyDataSetChanged();
//                                            }
//                                        }
                                    }
                                });

                            }

                        });
            }

        }
        mMapView.refresh();
        isloadingbus = false;
    }

    private void setCurrentTime(Date date)
    {
        currentTime = date;
        txt_Time.setText(DateUtils.dateToStr(currentTime, "yyyy/MM/dd"));
    }

    private void startLoadBus()
    {
        Logger.i(this.getClass(), "startLoadBus()准备刷新班车数据");

        if (!isActive())
        {
            Logger.i(this.getClass(), "当前页面不在最前，停止刷新");
            return;
        }
        // 获取汽车
        if (currentLineIndex < 0)
        {
            return;
        }
        if (currentCars == null)
        {
            LineInfo currentLine = allLines.get(currentLineIndex);
            basebiz.getVehiches(currentLine.getLine_id(), currentTime, new BizResultProcess<List<VehicheInfo>>()
            {
                @Override
                public void onBizExecuteEnd(BizDataTypeEnum datatype, List<VehicheInfo> t)
                {
                    currentCars = t;
                    // 加载班车数据
                    Logger.d(getClass(), "startLoadBus() 开始刷新班车数据");
                    if (!isActive())
                    {
                        Logger.i(this.getClass(), "当前页面不在最前，停止刷新");
                        return;
                    }
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                        	hideLoadingTips();
                            reloadHorizList(true);
                            reloadBus(true);
                            restartRefreshTimer();
                        }
                    });
                }

                @Override
                public void onBizExecuteError(Exception exception, Error error)
                {
                    Logger.e(getClass(), "获取车辆数据异常");
                    HandleLogicErrorInfo(exception);
                }
            });
        }
        else
        {
            restartRefreshTimer();
        }
    }

    private void startLoadBusByRefreshBtn()
    {
        Logger.i(this.getClass(), "准备刷新班车数据");

        if (!isActive())
        {
            Logger.i(this.getClass(), "当前页面不在最前，停止刷新");
            return;
        }
        // 获取汽车
        if (currentLineIndex < 0)
        {
            return;
        }
        LineInfo currentLine = allLines.get(currentLineIndex);
        basebiz.getVehiches(currentLine.getLine_id(), currentTime, new BizResultProcess<List<VehicheInfo>>()
        {
            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, List<VehicheInfo> t)
            {
                currentCars = t;
                // 加载班车数据
                Logger.d(getClass(), "开始刷新班车数据");
                if (!isActive())
                {
                    Logger.i(this.getClass(), "当前页面不在最前，停止刷新");
                    return;
                }
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        reloadHorizList(true);
                        reloadBus(true);
                        restartRefreshTimer();
                    }
                });
            }

            @Override
            public void onBizExecuteError(Exception exception, Error error)
            {
                Logger.e(getClass(), "获取车辆数据异常");
                HandleLogicErrorInfo(exception);
            }
        });
    }

    private void reloadHorizList(boolean isSearchResult)
    {
    	if(isSearchResult){
    		
    		if (currentCars == null){
    			cars.clear();
	            hcarsadapter.notifyDataSetChanged();
	            loadingProgressBar.setVisibility(View.GONE);
	            tipsTV.setText("暂无发车安排");
	     		tipsTV.setVisibility(View.VISIBLE);
	            return;
    		}
    	}else{
    		if (currentCars == null){
    			cars.clear();
	            hcarsadapter.notifyDataSetChanged();
	            hl_list.scrollTo(0);
	            
	            return;
    		}
    	}
    	
        /*if (currentCars == null)
        {
            cars.clear();
            hcarsadapter.notifyDataSetChanged();
            return;
        }*/

        cars.clear();
        for (VehicheInfo info : currentCars)
        {
            String timeStr = "循环发车";
            if (info.getPlan_start_time() != null)
            {
                timeStr = DateFormatUtils.format(info.getPlan_start_time(), "HH:mm");
            }

            cars.add(new CarListItem(info.getVehicle_vin(), info.getVehiche_number(), timeStr));
        }
    	
//    	 cars.clear();
//    	 for(int i=0;i<20;i++){
//    	 cars.add(new CarListItem(i+"", i+"", "19:20"));
//    	 }
        hcarsadapter.notifyDataSetChanged();
    }

    private void restartRefreshTimer()
    {
        if (refreshTimer != null)
            refreshTimer.cancel();
        if (autorefresh)
        {// 自动刷新
            refreshTimer = new Timer(true);
            refreshTimer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    /* reloadBus(false); */

                    /* 用于刷新时重新加载线路 */
                    hasLoadedCurrentLine = false;
                    if (currentCars != null && currentCars.size() > 0)
                    {
                        currentCars.clear();
                        currentCars = null;
                    }

                    refreshData();
                }
            }, refreshInterval, refreshInterval);
        }
        else
        {
            reloadBus(false);
        }
    }

    private void stopLoadBus()
    {
        Logger.i(this.getClass(), "停止刷新班车数据");
        if (refreshTimer != null)
        {
            refreshTimer.cancel();
        }
        if (vehiclebiz != null)
        {
            vehiclebiz.stop();
        }
    }

    private void showOrDismissPopLines()
    {
        if (popcl != null)
        {
            View center = findViewById(R.id.rl_it_center);
            int xoffset = (center.getWidth() - popcl.getWidth()) / 2;
            popcl.showAsDropDown(findViewById(R.id.rl_it_center), xoffset, 2);
        }
    }

    private void showTimeSelectDialog(Date inputtime)
    {
    	
    	/*用户行为统计-切换日期*/
        new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CJTQ_QHRQ).reportBehavior(null);
        
        // 进入日期选择页面
        Intent selectTime = new Intent(BetweenFactoryActivity.this, DateChooseActivity.class);
        selectTime.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        Bundle bundle = new Bundle();
        Calendar c = Calendar.getInstance();
        c.setTime(inputtime);
        bundle.putInt("year", c.get(Calendar.YEAR));
        bundle.putInt("month", c.get(Calendar.MONTH) + 1);
        bundle.putInt("day", c.get(Calendar.DAY_OF_MONTH));
        selectTime.putExtras(bundle);
        startActivityForResult(selectTime, REQUESTCODE_SELECTTIME);
    }

    private boolean isFromActivityResult = false;
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        isFromActivityResult = false;
        switch (requestCode)
        {
        case REQUESTCODE_SELECTTIME:
            if (resultCode == RESULT_OK)
            {
            	isFromActivityResult = true;
            	
                int year = data.getExtras().getInt("year");
                int month = data.getExtras().getInt("month");
                int day = data.getExtras().getInt("day");
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month - 1);
                c.set(Calendar.DAY_OF_MONTH, day);
                final Date newdate = c.getTime();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        whenTimeSelected(newdate);
                    }
                });
            }
            break;
        default:
            break;
        }
    }

    // 在日期选择完之后调用这个方法
    private void whenTimeSelected(Date outputtime)
    {
    	setCurrentTime(outputtime);
        stopLoadBus();

        mCarOverlay.removeAll();
        
        /*if (DateUtils.dateToStr(new Date(), DateUtils.TIME_FORMAT).equals(DateUtils.dateToStr(outputtime, DateUtils.TIME_FORMAT)))
        {
            currentCars = null;
            refreshBus();
            startLoadBus();
        }*/
        
        currentCars = null;
        refreshBus();
    }
    private void refreshLineInfos(List<LineInfo> t) {
    	
		if (t == null || t.size() == 0) {
			
			Logger.i(this.getClass(), "获取路线信息为空");
			txt_small.setVisibility(View.INVISIBLE);
			txt_small.setText("");
			
			return;
		}
		allLines = t;
		/*List<String> names = new ArrayList<String>();*/
		// 获取当前选择的线路
		String lname = popcl.getSelectionValue();
		int initialindex = -1;
		int count = 0;
		// 这边根据读取厂区选择配置来设置
		AreaType myselect = PrefDataUtil.getFactory(BetweenFactoryActivity.this);
		boolean isSame = false;
		if(lastSelectAreaType == null || lastSelectAreaType != myselect){
			
			Log.i("TAB2", "refreshLineInfos()->( lastSelectAreaType == null ) -> currentLineIndex:"+currentLineIndex);
			
			lastSelectAreaType = myselect;
			
			Log.i("TAB2", "refreshLineInfos()->( lastSelectAreaType == null ) -> lastSelectAreaType:"+lastSelectAreaType.getName());
			
			hasLoadedCurrentLine = false;
			isSame = false;
			
		}else{
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
				if(isSame){
					if (info.line_name.equals(lname)) {
						initialindex = count - 1;
					}
				}else{
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
		}
		popcl.setData(names, initialindex);
		currentLineIndex = initialindex;
		
		Log.i("TAB2", "refreshLineInfos() currentLineIndex:"+currentLineIndex);
		
		if (ismapready && !hasLoadedCurrentLine) {
			Logger.i(this.getClass(), "发现还没开始加载当前线路信息，开始加载");
			loadCurrentLine();
		}

	}
    
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
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder holder = null;
			if (arg1 == null) {
				holder = new ViewHolder();
				arg1 = View.inflate(mContext, R.layout.control_carlist_item,
						null);
				holder.Name = (TextView) arg1.findViewById(R.id.tv_clc_num);
				holder.Time = (TextView) arg1.findViewById(R.id.tv_clc_time);

				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}
			try {
				CarListItem item = mData.get(arg0);
				holder.Name.setText(item.getName());
//				if (item.rightline) {
					holder.Time.setText(item.getTime());
//				} else {
//					holder.Time.setText("循环发车");
//				}
			} catch (Exception err) {
				err.printStackTrace();
			}
			return arg1;
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
    
     
     private void showLoadingTips(String tipStr){
 		loadingProgressBar.setVisibility(View.VISIBLE);
 		tipsTV.setText(tipStr);
 		tipsTV.setVisibility(View.VISIBLE);
 	}
 	
 	private void hideLoadingTips(){
 		loadingProgressBar.setVisibility(View.GONE);
 		tipsTV.setVisibility(View.GONE);
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
}
