package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
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
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.utils.CoordinateConvert;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.baidu.platform.comapi.map.Projection;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.line.CoordPoint;
import com.yutong.clw.ygbclient.common.context.ContextUtil;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.StationType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.utils.ImageUtils;

import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
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

public class StationMapActivity extends RemindAccessActivity implements OnClickListener{

	private PopCheckList popcl;
	private int lastSelectedIndex = 0;
	private TextView tv_large,tv_small;
	private int currentLineIndex = -1;
	private RelativeLayout rl_center;
	private Button btn_left;
	
	private String belong_area_id; 
	
	private StatusRange currentStatus = StatusRange.MorningWork;
	private AreaType areaType;
	
	private List<StationInfo> stations;

	private GeoPoint searchCenter = null;
	
	// 地图是否完成加载
	boolean ismapready = false;
	
	/***** 地图变量 *******/
	// MapView 是地图主控件
	private MapView mMapView = null;
	// 用MapController完成地图控制
	private MapController mMapController = null;
	// MKMapViewListener 用于处理地图事件回调
	private MKMapViewListener mMapListener = null;
	// 站点层
	/*private OutFactoryStationOverlay stationlay;*/
	private StationOverlay stationlay;
	
	private GraphicsOverlay graphicsOverlay;  /*????*/
	// 图标临时层
	private ItemizedOverlay<OverlayItem> templay;
	// 定位中心点经度
	private double cLat = 34.693970;
	// 定位中心点纬度
	private double cLon = 113.697207;
	// 地图层级
	private float currentlevel = 16;
	
	private String areaId,stationName;
	/*private StatusRange statusRange;*/
	private BizOutOfFactory bizOutOfFactory;
	/********************/
	private List<StationInfo> stationInfoList = new ArrayList<StationInfo>();
	
	private StationItem stationItemTmp = null;
	

	private Button mZoomOutBtn,mZoomInBtn;
	private boolean canZoom;
	
	private List<StationInfo> getStationinfosList(){
		
		StationInfo si = new StationInfo();
		si.setId("3636");
		si.setArea_type(AreaType.FirstFactory);
		si.setType(StationType.NormalStaion);
		si.setName("紫荆山南路");
		si.setFavorites(true);
		si.setGps_lat(34.699871);
		si.setGps_lon(113.674638);
		
		/*StationInfo si2 = new StationInfo();
		si.setId("3637");
		si.setArea_type(AreaType.FirstFactory);
		si.setType(StationType.StartStaion);
		si.setName("黄科大东门");
		si.setFavorites(true);
		si.setGps_lat(34.690308);
		si.setGps_lon(113.676105);*/
		
		stationInfoList.add(si);
		/*stationInfoList.add(si2);*/
		return stationInfoList;
	}
	
	/*地图中心点算法*/
	private void matchToPoints(List<StationInfo> points) {
		try {
			if (points == null || points.size() == 0)
				return;
			StationInfo fp = points.get(0);
			double lat_max = fp.gps_lat;
			double lat_min = fp.gps_lat;
			double lon_max = fp.gps_lon;
			double lon_min = fp.gps_lon;
			
			for (StationInfo point : points) {
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
			mMapController.animateTo(centerp);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main_outoffactory_search_stationmap);
		
		initViews();
		initBiz();
		initMap();
		/*loadData();*/
		/*loadStations(getStationinfosList());*/
	}
	
	private void initFromIntent(Intent intent) {
		
		if (intent != null && intent.getExtras() != null) {
			Bundle bundle = intent.getExtras();
			areaId = bundle.getString("areaId");
			stationName = bundle.getString("name");
			areaType = (AreaType) bundle.getSerializable("areaType");
			currentStatus = (StatusRange) bundle.getSerializable("statusRange");
		}
	}

	@Override
	protected void onResume() {

		super.onResume();
		mMapView.onResume();
	}
	
	@Override
	protected void onPause() {
		
		mMapView.onPause();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		mMapView.destroy();
	}
	
	private void initBiz() {
		bizOutOfFactory = new BizOutOfFactory(getContext());
	}
	
	private void initViews() {
		
		mZoomOutBtn = (Button) findViewById(R.id.btn_zoom_out);
		mZoomOutBtn.setOnClickListener(this);
		mZoomInBtn = (Button) findViewById(R.id.btn_zoom_in);
		mZoomInBtn.setOnClickListener(this);
		
		rl_center = (RelativeLayout) findViewById(R.id.rl_it_center);
		btn_left  =(Button) findViewById(R.id.btn_it_left);
		tv_large = (TextView) findViewById(R.id.tv_it_centerup);
		tv_small = (TextView) findViewById(R.id.tv_it_centerdown);
		
		
		if (popcl == null) {
			popcl = new PopCheckList(this);
			popcl.setOnCheckChangedListener(new OnCheckChangedListener() {
				@Override
				public void OnChecked(int index, String txt) {
					
					if(lastSelectedIndex==index){
						popcl.dismiss();
						return;
					}
					
					lastSelectedIndex = index;
					tv_small.setText(txt);
					if(index == 0){
						currentStatus = StatusRange.MorningWork;
					}else{
						currentStatus = StatusRange.NightWork;
					}
					loadData();
					popcl.dismiss();
				}
			});
			popcl.setOnDismisslistener(new OnDismissListener() {
				@Override
				public void onDismiss() {
					// TODO title里面的小三角可能要变色
				}
			});
			
			//测试数据
			List<String> names = new ArrayList<String>();
			names.add("早班");
			names.add("晚班");
			lastSelectedIndex = currentStatus == StatusRange.MorningWork ? 0:1;
			popcl.setData(names, lastSelectedIndex);
		}
	}
	
	private void initMap() {

		mMapView = (MapView) findViewById(R.id.bmv_ami_map);
		mMapController = mMapView.getController();
		mMapController.enableClick(true);
		// 设置地图缩放级别
		mMapController.setZoom(currentlevel);
		// 显示内置缩放控件
		mMapView.setBuiltInZoomControls(false);
		// 设置中心点为宇通
		GeoPoint centerpoint = new GeoPoint((int) (cLat * 1E6),(int) (cLon * 1E6));
		mMapController.setCenter(centerpoint);
		
		mMapController.setRotationGesturesEnabled(true);
		mMapController.setOverlookingGesturesEnabled(false);
		mMapController.setCompassMargin(mMapView.getWidth() - 20, 20);
		mMapListener = new MKMapViewListener() {
			@Override
			public void onMapMoveFinish() {
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

				/*showPointAnimation(arg0);
				searchStationsAround(arg0);*/
				
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
		
		/*stationlay = new OutFactoryStationOverlay(
					this, 
					mMapView, 
					getResources().getDrawable(R.drawable.ic_favor_gray), 
					getResources().getDrawable(R.drawable.ic_favor_white));*/
		/**
		 * 站点层构造函数
		 * 
		 * @param context
		 *            上下文
		 * @param view
		 *            地图控件
		 * @param remindicon
		 *            提醒站点图标
		 * @param normalicon
		 *            正常站点图标
		 * @param editicon
		 *            正常站点编辑图标
		 * @param editicon_remid
		 *            提醒站点编辑图标
		 * @param starticon
		 *            开始站点图标
		 * @param endicon
		 *            结束站点图标
		 * @param shownormal
		 *            弹出框正常图标
		 * @param showremind
		 *            弹出框提醒图标
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
				getResources().getDrawable(R.drawable.bg_star_null),
				getResources().getDrawable(R.drawable.bg_star_white));
		
		stationlay.setOnItemRemindTapListener(new OnItemTapListener() {

			@Override
			public void OnItemTap(StationItem value) {
				
			}
			
		});
		
		stationlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				StationItem StationItem = (StationItem) v.getTag();
				
				int len = stationInfoList.size();
				StationInfo stationInfo = null;
				for(int i=0;i<len;i++){
					
					stationInfo = stationInfoList.get(i);
					if(stationInfo.getId().equals(StationItem.getId())){
						break;
					}
				}
				Bundle bundle  = new Bundle();
				bundle.putSerializable(ActivityCommConstant.STATION_INFO, stationInfo);
				bundle.putSerializable(ActivityCommConstant.AREATYPE, areaType);
				bundle.putSerializable(ActivityCommConstant.STATUSRANGE, currentStatus);
				bundle.putSerializable(ActivityCommConstant.DATE,new Date());
				bundle.putSerializable(ActivityCommConstant.ISFAVOR,stationInfo.isFavorites());
				bundle.putSerializable(ActivityCommConstant.ISCLOCK,stationInfo.isStatus());
				
				ContextUtil.alterActivity((Activity) getContext(), SingleStationScheduleActivity.class, bundle);
			}
			
		});
		
		/*收藏点击事件*/
		stationlay.setOnItemRemindTapListener(new OnItemTapListener(){

			@Override
			public void OnItemTap(StationItem stationItem) {
				
				stationItemTmp = stationItem;
				
				/*收藏弹出框*/
				StationInfo stationInfo = null;
				int len = stationInfoList.size();
				for(int i=0;i<len;i++){
					
					stationInfo = stationInfoList.get(i);
					if(stationInfo.getId().equals(stationItem.getId())){
						stationInfo.setFavorites(stationItemTmp.isRemind());
						break;
					}
				}
				
				showFavoriteConfirmDialog(stationInfo, stationInfo.isFavorites());
			}
			
		});
		
		graphicsOverlay = new GraphicsOverlay(mMapView);
		mMapView.getOverlays().add(graphicsOverlay);

		/*templay = new ItemizedOverlay<OverlayItem>(getResources().getDrawable(
				R.drawable.ic_map_select), mMapView);
		mMapView.getOverlays().add(templay);*/
		
	}
	protected void showFavoriteConfirmDialog(final StationInfo site, final boolean isFavor)
    {
        if (site == null)
            return;

        String title = isFavor ? "取消收藏" : "添加收藏";
        String body = isFavor ? "是否取消收藏？" : "是否添加收藏？";
        final String hintStr = isFavor ? "正在取消收藏..." : "正在添加收藏...";
        
        
        CustomDialog dia = new CustomDialog.Builder(this).setTitle(title)
                .setNegativeButton("取消", new DialogInterface.OnClickListener()
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
            @Override
            public void onBizExecuteError(Exception exception, Error error)
            {
               ToastMessage("收藏失败！");
               HandleLogicErrorInfo(exception);
            }

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, Boolean t)
            {
            	final boolean resultFlag  = t;
                runOnUiThread(new Runnable()
                {

                    @Override
                    public void run(){
                    	
                    	ToastMessage( resultFlag == true ? "收藏成功":"收藏取消");
                    	stationItemTmp.setRemind(!stationItemTmp.isRemind());
                    	stationlay.updateItem(stationItemTmp);
                    	dismissLoading(1);
                    }
                });

            }
        });
    }
	private void doworkWhenMapLoaded() {
		ismapready = true;
		/*loadStations(getStationinfosList());*/
		loadData();
	}

	
	private void loadData() {
		
		//mMapView.getOverlays().clear();
		
		showLoading("加载数据,请稍候...", 1);
		/*服务器数据*/
		bizOutOfFactory.getAreaStations(areaType,currentStatus,areaId,new BizResultProcess<List<StationInfo>>() {

			@Override
			public void onBizExecuteEnd(BizDataTypeEnum datatype,
					List<StationInfo> t) {
				
				stationInfoList.clear();
				if(t!=null && t.size()>0){
					Logger.i(getClass(), "加载数据成功");
					
					for(StationInfo stationInfo:t){
						String stationName = stationInfo.getName();
						if(stationName.length()>10){
							stationInfo.setName(stationName.substring(0, 10));
						}
					}
					
					stationInfoList.addAll(t);
					/*地图标点*/
					
				}else{
					ToastMessage("暂无站点");
				}
				loadStations(stationInfoList);
				dismissLoading(1);
			}

			@Override
			public void onBizExecuteError(Exception exception, Error error) {
				ToastMessage("获取站点信息失败");
				HandleLogicErrorInfo(exception);
			}
		});
	}
	
	private List<StationItem> items  = null;
	private void loadStations(List<StationInfo> infos) {
		Logger.i(this.getClass(), "开始加载站点数据");

		
		stationlay.removeAll();
		if (infos == null || infos.size() == 0) {
			Logger.i(this.getClass(), "没有当前线路信息，无法加载站点数据");
			return;
		}
		
		items = new ArrayList<StationItem>();
		for (StationInfo station : infos) {
			// 更新地图站点
			StationItemPositionType ptype = station.getType() == StationType.StartStaion ? StationItemPositionType.START
					: (station.getType() == StationType.EndStaion ? StationItemPositionType.END
							: StationItemPositionType.CENTER);
			
			GeoPoint baidupoint = CoordinateConvert
					.fromWgs84ToBaidu(new GeoPoint(
							(int) (station.getGps_lat() * 1E6), (int) (station
									.getGps_lon() * 1E6)));
			/*TEST*/
			mMapController.animateTo(baidupoint);
			StationItem sitem = new StationItem(
					station.id, 
					baidupoint,
					station.getName(), 
					station.getAlias(), 
					1,
					station.isFavorites(), 
					ptype);
			sitem.setNeedEdit(true);
			items.add(sitem);
		}
		stationlay.loadData(items);
		
		mMapView.refresh();
		matchToPoints(infos);
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}
	
	@Override
	protected void loadTitleByPageSetting(Button btn_left, Button btn_right,
			RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
			TextView tv_small) {
		
		initFromIntent(getIntent());
		
		btn_left.setBackgroundResource(R.drawable.bg_prevbtn);
		btn_right.setVisibility(View.GONE);
		
		
		
		tv_large.setText(stationName);
		tv_small.setText(currentStatus == StatusRange.MorningWork?"早班":"晚班");
		
		btn_left.setOnClickListener(this);
		rl_center.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.btn_it_left:
			onBackPressed();
			break;
		
		case R.id.rl_it_center:
			showOrDismissPopLines();
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
			;
		}
	}

	private Context getContext(){
		return this;
	}
	//弹出对话框
	private void showOrDismissPopLines() {
		if (popcl != null) {
			View center = findViewById(R.id.rl_it_center);
			int xoffset = (center.getWidth() - popcl.getWidth()) / 2;
			popcl.showAsDropDown(findViewById(R.id.rl_it_center), xoffset, 2);
		}
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
