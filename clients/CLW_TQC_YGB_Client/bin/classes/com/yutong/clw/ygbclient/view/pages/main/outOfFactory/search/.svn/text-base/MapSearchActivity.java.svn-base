package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.ItemizedOverlay;
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
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.utils.CoordinateConvert;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.context.ContextUtil;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.StationType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizOutOfFactory;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.OnItemTapListener;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.StationItem;
import com.yutong.clw.ygbclient.view.common.map.StationOverlay.StationItemPositionType;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation.SingleStationScheduleActivity;
import com.yutong.clw.ygbclient.view.widget.CustomDialog;
import com.yutong.clw.ygbclient.view.widget.PopCheckList;
import com.yutong.clw.ygbclient.view.widget.PopCheckList.OnCheckChangedListener;

/**
 * 地图选点搜索界面
 * 
 * @author zhouzc
 */
public class MapSearchActivity extends RemindAccessActivity implements OnClickListener
{
	private final static int REQUEST_CODE = 1;
	
    /***** 业务变量 *******/
    //
    private BizOutOfFactory biz;

    /***** 控件变量 *******/
    // 弹出线路选择框
    private PopCheckList popcl;

    private int popclWidth = 70;
    // 顶部选择项
    private TextView txt_small;

    // 顶部Title文字
    private TextView txt_large;

    // 顶部小三角
    private ImageView iv_tri;

    //
    private ImageView aniView = null;

    /***** 数据变量 *******/
    private StatusRange currentStatus = StatusRange.MorningWork;

    private AreaType areaType = AreaType.FirstFactory;

    private List<StationInfo> allStationList = new ArrayList<StationInfo>();

    private List<StationInfo> stationInfoListByRadius = new ArrayList<StationInfo>();

    private GeoPoint searchCenter = null;

    // 搜索半径，单位米
    private int searchRadius = 2000;

    /***** 标志变量 *******/
    // 地图是否完成加载
    boolean ismapready = false;

    private boolean firstLocation = true;

    /***** 地图变量 *******/
    // MapView 是地图主控件
    private MapView mMapView = null;

    // 用MapController完成地图控制
    private MapController mMapController = null;

    // MKMapViewListener 用于处理地图事件回调
    private MKMapViewListener mMapListener = null;

    // 站点层
    private StationOverlay stationlay;

    private GraphicsOverlay graphicsOverlay;

    // 图标临时层
    private ItemizedOverlay<OverlayItem> templay;

    // 定位中心点经度
    private double cLat = 34.693970;

    // 定位中心点纬度
    private double cLon = 113.697207;

    // 地图层级
    private float currentlevel = 16;

    // 定位
    public LocationClient mLocationClient = null;

    // 自己定位层
    private MyLocationOverlay selflocationLay;

    // 百度定位服务侦听器
    private BDLocationListener myLocationListener = null;

    private StationItem stationItem_tmp = null;
    
    //用于接收onActivityResult回传参数
    StationItem sitem = null;
    
    private int popcIndexPre = 0; 
    
    private Button mZoomOutBtn,mZoomInBtn;
    private boolean canZoom;
    
    /********************/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_outoffactory_search_mapsearch);
        initFromIntent(getIntent());
        initBiz();
        initViews();
        initMap();
        loadData();
    }

    private void loadData()
    {
        showTip("正在加载周边站点信息......");
        getStationAndAreaData(areaType, currentStatus);
    }

    private void initFromIntent(Intent intent)
    {

        if (intent != null && intent.getExtras() != null)
        {
            Bundle bundle = intent.getExtras();

            areaType = (AreaType) bundle.getSerializable("areaType");
            currentStatus = (StatusRange) bundle.getSerializable("statusRange");

            /*txt_large.setText(areaType.getName());*/
            txt_large.setText(areaType ==  AreaType.FirstFactory ? "一厂站点搜索":"二厂站点搜索");
            txt_small.setText(currentStatus.getName());
        }
    }

    private void initBiz()
    {
        biz = new BizOutOfFactory(this);
    }

    // 弹出对话框
    private void showOrDismissPopLines()
    {
        if (popcl != null)
        {
            View center = findViewById(R.id.rl_it_center);
            int xoffset = (center.getWidth() - popcl.getWidth()) / 2;
            popcl.showAsDropDown(findViewById(R.id.rl_it_center), xoffset, 2);
        }
    }

    private void initViews()
    {
    	mZoomOutBtn = (Button) findViewById(R.id.btn_zoom_out);
		mZoomOutBtn.setOnClickListener(this);
		mZoomInBtn = (Button) findViewById(R.id.btn_zoom_in);
		mZoomInBtn.setOnClickListener(this);
		
        popcl = new PopCheckList(this);
        popcIndexPre = currentStatus == StatusRange.MorningWork ? 0 : 1;
        popcl.setData(Arrays.asList("早班", "晚班"), currentStatus == StatusRange.MorningWork ? 0 : 1);
        popcl.setOnCheckChangedListener(new OnCheckChangedListener()
        {
            @Override
            public void OnChecked(int index, String value)
            {
                txt_small.setText(value);
                
                if(popcIndexPre == index){
                	 popcl.dismiss();
                	return;
                }
                
                if (index == 0)
                {
                    currentStatus = StatusRange.MorningWork;
                }
                else
                {
                    currentStatus = StatusRange.NightWork;
                }

                /* 移除站点 */
                stationlay.removeAll();
                /* 移除圆圈 */
                /* graphicsOverlay.removeAll(); */
                /* 当前位置擦除 */
                /* templay.removeAll(); */

                getStationAndAreaData(areaType, currentStatus);

                if (searchCenter != null)
                    searchStationsAround(searchCenter);
                
                popcIndexPre = index;
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

        /* txt_small.setText(currentStatus.getName()); */
        txt_small.setVisibility(View.VISIBLE);

    }

    private void initMap()
    {

        /*
         * 定位服务 mLocationClient = new LocationClient(getApplicationContext());
         * //声明LocationClient类 mLocationClient.registerLocationListener(
         * myListener ); //注册监听函数 LocationClientOption option = new
         * LocationClientOption(); option.setOpenGps(false);
         * option.setAddrType("all");//返回的定位结果包含地址信息
         * option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
         * option.setScanSpan
         * (500);//设置发起定位请求的间隔时间为5000ms,如果不设置或者所设置的整数值小于1000（ms）时，
         * //采用第一次定位模式。每调用一次requestLocatin()，定位SDK会发起一次定位。
         * option.disableCache(true);//禁止启用缓存定位 option.setPoiNumber(5);
         * //最多返回POI个数 option.setPoiDistance(1000); //poi查询距离
         * option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
         * mLocationClient.setLocOption(option);
         */
        mMapView = (MapView) findViewById(R.id.bmv_amosm_map);
        mMapController = mMapView.getController();
        mMapController.enableClick(true);
        // 设置地图缩放级别
        mMapController.setZoom(currentlevel);
        // 显示内置缩放控件
        mMapView.setBuiltInZoomControls(false);
        // 设置中心点为宇通
        GeoPoint centerpoint = new GeoPoint((int) (cLat * 1E6), (int) (cLon * 1E6));
        mMapController.setCenter(centerpoint);
        mMapController.setRotationGesturesEnabled(true);
        mMapController.setOverlookingGesturesEnabled(false);
        mMapController.setCompassMargin(mMapView.getWidth() - 20, 20);
        mMapListener = new MKMapViewListener()
        {
            @Override
            public void onMapMoveFinish()
            {
            }

            @Override
            public void onClickMapPoi(MapPoi mapPoiInfo)
            {
            }

            @Override
            public void onGetCurrentMap(Bitmap b)
            {
            }

            @Override
            public void onMapAnimationFinish()
            {
            }

            @Override
            public void onMapLoadFinish()
            {
                Logger.i(getClass(), "地图加载完成");
                doWorkWhenMapLoaded();
            }
        };
        mMapView.regMapViewListener(YtApplication.getInstance().getBaiduMapManager().getBMapManager(), mMapListener);

        // 实现对地图状态改变的处理
        MKMapStatusChangeListener slistener = new MKMapStatusChangeListener()
        {
            public void onMapStatusChange(MKMapStatus mapStatus)
            {
                float zoom = mapStatus.zoom; // 地图缩放等级
                if (zoom != currentlevel)
                {
                    currentlevel = zoom;
                }
                // int overlooking = mapStatus.overlooking; // 地图俯视角度
                // int rotate = mapStatus.rotate; // 地图旋转角度
            }
        };
        // 为 mapview 注册地图状态监听者。本方法只能注册一次，多次注册会覆盖
        mMapView.regMapStatusChangeListener(slistener);
        mMapView.regMapTouchListner(new MKMapTouchListener()
        {
            @Override
            public void onMapLongClick(GeoPoint geoPoint)
            {
            	
            	
                showPointAnimation(geoPoint);
                searchCenter = geoPoint;
                /* searchStationsAround(geoPoint); */
                
                /*用户行为统计-地图选点圆形区域*/
                new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_ZDSS).reportBehavior(null);
            }

            @Override
            public void onMapDoubleClick(GeoPoint geoPoint)
            {

            }

            @Override
            public void onMapClick(GeoPoint geoPoint)
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
        initMapOvarlays();

    }

    private void initMapOvarlays()
    {

        /**
         * 站点层构造函数
         * 
         * @param context 上下文
         * @param view 地图控件
         * @param remindicon 提醒站点图标
         * @param normalicon 正常站点图标
         * @param editicon 正常站点编辑图标
         * @param editicon_remid 提醒站点编辑图标
         * @param starticon 开始站点图标
         * @param endicon 结束站点图标
         * @param shownormal 弹出框正常图标
         * @param showremind 弹出框提醒图标 public StationOverlay(Context context,
         *            MapView view, Drawable remindicon, Drawable normalicon,
         *            Drawable editicon, Drawable editicon_remid, Drawable
         *            starticon, Drawable endicon, Drawable shownormal, Drawable
         *            showremind);
         */
        stationlay = new StationOverlay(
        		this, 
        		mMapView, 
        		getResources().getDrawable(R.drawable.bg_station_favourite_normal), 
        		getResources().getDrawable(R.drawable.ic_station_normal), 
        		getResources().getDrawable(R.drawable.ic_station_active), 
        		getResources().getDrawable(R.drawable.bg_station_favourite_pressed), 
        		null, 
        		null, 
        		getResources().getDrawable(R.drawable.ic_favor_white),
                getResources().getDrawable(R.drawable.ic_favored_white));

        stationlay.setNeedStationName(false);
        
        /*收藏按钮点击事件*/
        stationlay.setOnItemRemindTapListener(new OnItemTapListener()
        {

            @Override
            public void OnItemTap(final StationItem stationItem)
            {

                stationItem_tmp = stationItem;
                Logger.i(getClass(), "stationItem_tmp==stationItem  :"+(stationItem_tmp==stationItem));
                int len = stationInfoListByRadius.size();
                StationInfo stationInfo = null;
                for (int i = 0; i < len; i++)
                {

                    stationInfo = stationInfoListByRadius.get(i);
                    if (stationInfo.getId().equals(stationItem.getId()))
                    {
                        stationInfo.setFavorites(stationItem_tmp.isRemind());
                        break;
                    }
                }

                showFavoriteConfirmDialog(stationInfo, stationInfo.isFavorites());
            }
        });
        
        /*站点名称点击跳转*/
        stationlay.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                StationItem StationItem = (StationItem) v.getTag();

                int len = stationInfoListByRadius.size();
                StationInfo stationInfo = null;
                for (int i = 0; i < len; i++)
                {
                    stationInfo = stationInfoListByRadius.get(i);
                    if (stationInfo.getId().equals(StationItem.getId()))
                    {
                    	stationInfo.setFavorites(StationItem.isRemind());
                        break;
                    }
                }
                
                Bundle bundle = new Bundle();
                bundle.putSerializable(ActivityCommConstant.STATION_INFO, stationInfo);
                bundle.putSerializable(ActivityCommConstant.AREATYPE, areaType);
                bundle.putSerializable(ActivityCommConstant.STATUSRANGE, currentStatus);
                bundle.putSerializable(ActivityCommConstant.DATE, new Date());
                bundle.putSerializable(ActivityCommConstant.ISFAVOR, stationInfo.isFavorites());
                bundle.putSerializable(ActivityCommConstant.ISCLOCK, stationInfo.isStatus());

                ContextUtil.alterActivityForResult((Activity) getContext(), SingleStationScheduleActivity.class, bundle, REQUEST_CODE);
            }

        });

        // 圆圈层
        graphicsOverlay = new GraphicsOverlay(mMapView);
        mMapView.getOverlays().add(graphicsOverlay);

        // 定位图标层
        templay = new ItemizedOverlay<OverlayItem>(getResources().getDrawable(R.drawable.ic_map_select), mMapView);
        mMapView.getOverlays().add(templay);

        if (aniView == null)
        {
            aniView = new ImageView(MapSearchActivity.this);
            aniView.setImageResource(R.drawable.ic_map_select);
            mMapView.addView(aniView);
            aniView.setVisibility(View.GONE);
        }
    }

    private Context getContext()
    {

        return this;
    }

    private void doWorkWhenMapLoaded()
    {
        ismapready = true;

        /* 定位请求 */
        /*
         * if (mLocationClient != null && mLocationClient.isStarted())
         * mLocationClient.requestLocation(); else Logger.i(getClass(),
         * "locClient is null or not started");
         */
        
        loadMySelf();
    }

    private void drawNewMarker(final GeoPoint point)
    {
        runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {
                templay.removeAll();
                templay.addItem(new OverlayItem(point, "", ""));
                mMapView.refresh();
            }
        });

    }

    private void drawPointAround(final GeoPoint point)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {

                graphicsOverlay.removeAll();
                int radius = searchRadius;
                // 构建圆
                Geometry circleGeometry = new Geometry();

                // 设置圆中心点坐标和半径
                circleGeometry.setCircle(point, radius);
                // 设置样式
                Symbol circleSymbol = new Symbol();
                Symbol.Color circleColor = circleSymbol.new Color();
               /* circleColor.red = 100;
                circleColor.green = 0;
                circleColor.blue = 255;
                circleColor.alpha = 180;*/
                
                circleColor.red = 0x33;
                circleColor.green = 0x66;
                circleColor.blue = 0xff;
                circleColor.alpha = 180;
                
                circleSymbol.setSurface(circleColor, 0, 5);
                /*circleSymbol.setLineSymbol(circleColor,3);*/
                
                // 生成Graphic对象
                Graphic circleGraphic = new Graphic(circleGeometry, circleSymbol);

                Symbol fillSymbol = new Symbol();
                Symbol.Color fillColor = circleSymbol.new Color();
               /* fillColor.red = 255;
                fillColor.green = 100;
                fillColor.blue = 0;
                fillColor.alpha = 100;*/
                
                fillColor.red = 0x9a;
                fillColor.green = 0xc0;
                fillColor.blue = 0xf3;
                fillColor.alpha = 100;
                
                fillSymbol.setSurface(fillColor, 1, 3);
                Graphic fillGraphic = new Graphic(circleGeometry, fillSymbol);
                Logger.i(getClass(), "开始绘制范围圈");
                // 添加圆
                graphicsOverlay.setData(fillGraphic);
                graphicsOverlay.setData(circleGraphic);
                mMapView.refresh();
            }
        });
    }

    private void searchStationsAround(GeoPoint center)
    {
        searchCenter = center;
        Logger.i(getClass(), "开始搜索中心点[" + center.getLatitudeE6() + "," + center.getLongitudeE6() + "]附近站点");
        // TODO
        stationInfoListByRadius = stationSearchByRadius(center, allStationList, searchRadius);
        if (stationInfoListByRadius.size() == 0)
        {

            ToastMessage("所选位置周边暂无站点");
        }

        loadStations(stationInfoListByRadius);
    }

    private List<StationInfo> stationSearchByRadius(GeoPoint center, List<StationInfo> stationList, double radius)
    {

        List<StationInfo> stationInfoList = new ArrayList<StationInfo>();

        if (stationList != null && stationList.size() > 0 && radius > 0)
        {

            for (int i = 0; i < stationList.size(); i++)
            {

                StationInfo stationInfo = stationList.get(i);
                GeoPoint geoPoint = CoordinateConvert.fromWgs84ToBaidu(new GeoPoint((int) (stationInfo.getGps_lat() * 1E6),
                        (int) (stationInfo.getGps_lon() * 1E6)));

                double distance = DistanceUtil.getDistance(geoPoint, center);
                if (distance <= searchRadius)
                {
                    Logger.i(getClass(), "lat:" + stationInfo.getGps_lat() + " lon:" + stationInfo.getGps_lon() + " curPoint name:"
                            + stationInfo.getName() + " distance:" + distance + " M");
                    stationInfoList.add(stationInfo);
                }

            }
        }

        return stationInfoList;

    }

    private void loadStations(List<StationInfo> infos)
    {
        Logger.i(this.getClass(), "开始加载站点数据");

        stationlay.removeAll();
        if (infos == null || infos.size() == 0)
        {
            Logger.i(this.getClass(), "没有当前线路信息，无法加载站点数据");
            return;
        }

        List<StationItem> items = new ArrayList<StationItem>();
        for (StationInfo station : infos)
        {
            // 更新地图站点
            StationItemPositionType ptype = station.getType() == StationType.StartStaion ? StationItemPositionType.START : (station
                    .getType() == StationType.EndStaion ? StationItemPositionType.END : StationItemPositionType.CENTER);
            GeoPoint baiDuPoint = CoordinateConvert.fromWgs84ToBaidu(new GeoPoint((int) (station.getGps_lat() * 1E6), (int) (station
                    .getGps_lon() * 1E6)));
            StationItem sitem = new StationItem(station.id, baiDuPoint, station.getName(), station.getAlias(), 1, station.isFavorites(),
                    ptype);
            sitem.setNeedEdit(true);
            items.add(sitem);
        }
        stationlay.loadData(items);
    }

    private void showPointAnimation(final GeoPoint point)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    aniView.setVisibility(View.VISIBLE);
                    aniView.bringToFront();
                    Point now = null;
                    now = mMapView.getProjection().toPixels(point, now);
                    int pw = aniView.getWidth();
                    int ph = aniView.getHeight();
                    // 使用动画效果加点，不会让点出现的太突兀
                    final Animation trAnimation = new TranslateAnimation(now.x - pw / 2, now.x - pw / 2, 0, now.y - ph);
                    trAnimation.setDuration(500);
                    trAnimation.setFillAfter(true);
                    trAnimation.setRepeatMode(0);
                    trAnimation.setAnimationListener(new AnimationListener()
                    {
                        @Override
                        public void onAnimationStart(Animation arg0)
                        {
                        }

                        @Override
                        public void onAnimationRepeat(Animation arg0)
                        {
                        }

                        @Override
                        public void onAnimationEnd(Animation arg0)
                        {
                            drawNewMarker(point);
                            drawPointAround(point);
                            // 延迟200毫秒，不然图标会有闪烁
                            delayRun(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    try
                                    {
                                        aniView.setVisibility(View.INVISIBLE);
                                        trAnimation.setFillAfter(false);
                                        searchStationsAround(searchCenter);
                                    }
                                    catch (Exception ex)
                                    {
                                        ex.printStackTrace();
                                    }
                                }
                            }, 200);

                        }
                    });
                    aniView.startAnimation(trAnimation);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
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

        btn_left.setBackgroundResource(R.drawable.bg_prevbtn);
        btn_right.setVisibility(View.INVISIBLE);
        tv_large.setText(areaType.getName());
        tv_small.setText(currentStatus.getName());

        rl_center.setOnClickListener(this);
        btn_left.setOnClickListener(this);

        tv_small.setVisibility(View.GONE);
        this.txt_large = tv_large;
        this.txt_small = tv_small;
        this.iv_tri = iv_tri;

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.rl_it_center:
            showOrDismissPopLines();
            break;
        case R.id.btn_it_left:
            finish();
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
        default:
            break;
        }
    }

    @Override
    protected void onPause()
    {
        /**
         * MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
         */
        mMapView.onPause();
        if (biz != null)
            biz.stop();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        /**
         * MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
         */  	
       
        mMapView.onResume();
       /* if (!isForceToDestory()) {
			
			if (mLocationClient != null && myLocationListener != null)
				mLocationClient.registerLocationListener(myLocationListener);
		}*/
        
        super.onResume();
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	
    	mLocationClient.stop();
		firstLocation = true;
    	super.onStop();
    }
    @Override
    protected void onDestroy()
    {
        /**
         * MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
         */
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

    public void getStationAndAreaData(AreaType areaType, StatusRange statusRange)
    {

        /* 获取站点数据; */
        biz.getSearchStations(areaType, statusRange, new BizResultProcess<List<StationInfo>>()
        {

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, List<StationInfo> t)
            {

                // 清空缓存
                allStationList.clear();
                if (t != null && t.size() > 0)
                {
                    allStationList.addAll(t);
                    
                    Logger.i(getClass(), "站点信息加载成功");
                    hideTip();
                }
                else
                {
                    Logger.i(getClass(), "暂无站点");
                    ToastMessage("所选位置周边暂无站点");
                }
            }

            @Override
            public void onBizExecuteError(Exception exception, Error error)
            {
                Logger.e(getClass(), "站点信息加载失败");
                ToastMessage("站点信息加载失败");
                HandleLogicErrorInfo(exception);
            }

        });
    }
    
    
    private void loadMySelf()
    {
        Logger.i(this.getClass(), "开始加载自己定位数据");
        if (selflocationLay == null)
        {
            selflocationLay = new MyLocationOverlay(mMapView);
            selflocationLay.setMarker(getResources().getDrawable(R.drawable.ic_myself));
            
            //开启定位图层接受方向数据功能，当定位数据中有方向时，定位图标会旋转至该方向
            selflocationLay.enableCompass();
            selflocationLay.setLocationMode(LocationMode.NORMAL);
            mMapView.getOverlays().add(selflocationLay);
        }
        // 使用百度定位服务
        initBaidulocClient();
    }

    private void initBaidulocClient()
    {
    	mLocationClient = YtApplication.getInstance().getBaiduMapManager()
		.getLocationClient();
        myLocationListener = new BDLocationListener()
        {
            @Override
            public void onReceivePoi(BDLocation location)
            {

            }

            @Override
            public void onReceiveLocation(BDLocation location)
            {
            	updateToNewBDLocation(location);
            }
        };

        mLocationClient.registerLocationListener(myLocationListener);
        
        if (!mLocationClient.isStarted())
        {
            mLocationClient.start();

        }
        processLocationResponse(mLocationClient.requestLocation());
    }
    
    private void processLocationResponse(int responsecode) {
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
    
    private void updateToNewBDLocation(BDLocation location)
    {
        if (location == null)
            return;
        // 更新用户当前坐标
        LocationData ld = new LocationData();
        ld.latitude = location.getLatitude();
        ld.longitude = location.getLongitude();
        // ld.direction = location.getDerect();
        selflocationLay.setData(ld);
        if (firstLocation)
        {
            mMapController.animateTo(new GeoPoint((int) (ld.latitude * 1E6), (int) (ld.longitude * 1E6)));
            firstLocation = false;
        }

        mMapView.refresh();
    }
    
    protected void showFavoriteConfirmDialog(final StationInfo site, final boolean isFavor)
    {
        if (site == null)
            return;

        String title = isFavor ? "取消收藏" : "添加收藏";
        String body = isFavor ? "是否取消收藏？" : "是否添加收藏？";
        final String hintStr = isFavor ? "正在取消收藏..." : "正在添加收藏...";

        CustomDialog dia = new CustomDialog.Builder(this).setTitle(title).setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                SubmitFavorite(site, !isFavor);
                showLoading(hintStr, 1);
            }
        }).setMessage(body).create();

        dia.show();

    }

    /**
     * 提交收藏状态
     * 
     * @author zhangyongn 2013-11-14 下午2:33:04
     * @param site 站点
     * @param favor 要设置的收藏状态，true：表示收藏，false：表示不收藏
     */
    protected void SubmitFavorite(final StationInfo site, final boolean favor)
    {
        BizOutOfFactory biz = new BizOutOfFactory(this);
        biz.setStationFavority(site.id, favor, new BizResultProcess<Boolean>()
        {
            boolean isFvourite = favor;

            @Override
            public void onBizExecuteError(Exception exception, Error error)
            {
                ToastMessage("收藏失败！");
                HandleLogicErrorInfo(exception);
            }

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, Boolean t)
            {
                final Boolean result = t;
                runOnUiThread(new Runnable()
                {

                    @Override
                    public void run()
                    {

                        ToastMessage(isFvourite == true ? "收藏成功" : "收藏取消");
                        stationItem_tmp.setRemind(!stationItem_tmp.isRemind());
                        //region by zz 解决收藏状态传递问题
                        if (result)
                        {
                            for (StationInfo stationInfo : stationInfoListByRadius)
                            {
                                if (stationInfo.id.equals(site.id))
                                {
                                    stationInfo.setFavorites(stationItem_tmp.isRemind());
                                    break;
                                }
                            }
                        }
                        //endregion
                        stationlay.updateItem(stationItem_tmp);
                        dismissLoading(1);
                    }
                });

            }
        });
    }
    
   /* 用于接收回传站点信息，来更新地图上的标点*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				Bundle bundle = data.getExtras();
				StationInfo station = (StationInfo) bundle.getSerializable("stationInfo");
				
				StationItemPositionType ptype = station.getType() == StationType.StartStaion ? StationItemPositionType.START : (station
	                    .getType() == StationType.EndStaion ? StationItemPositionType.END : StationItemPositionType.CENTER);
	            GeoPoint baidupoint = CoordinateConvert.fromWgs84ToBaidu(new GeoPoint((int) (station.getGps_lat() * 1E6), (int) (station
	                    .getGps_lon() * 1E6)));
	            sitem = new StationItem(station.id, baidupoint, station.getName(), station.getAlias(), 1, station.isFavorites(),
	                    ptype);
	            sitem.setNeedEdit(true);
	            

	            for (StationInfo stationInfo : stationInfoListByRadius)
                {
                    if (stationInfo.id.equals(station.getId()))
                    {
                        stationInfo.setFavorites(station.isFavorites());
                        loadStations(stationInfoListByRadius);
                        stationlay.updateItem(sitem);
                        break;
                    }
                }
			}
		}
    }
}
