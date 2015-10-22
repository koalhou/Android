package com.yutong.axxc.parents.view.home;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MKOfflineMap;
import com.baidu.mapapi.MKOfflineMapListener;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.baidu.mapapi.OverlayItem;
import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.common.LocationUtils;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.NeuGeoPoint;
import com.yutong.axxc.parents.common.SharedPreferencesUtil;
import com.yutong.axxc.parents.common.StringTemplateHelper;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.beans.MsgRecordBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.beans.WeatherInfoBean;
import com.yutong.axxc.parents.common.context.ContextUtil;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.MessageBuilder;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.MessageBuilder.msgconfig;
import com.yutong.axxc.parents.view.home.EnhancedMapView.OnPanChangeListener;
import com.yutong.axxc.parents.view.home.EnhancedMapView.OnZoomChangeListener;
import com.yutong.axxc.parents.view.home.MainMapActivity.MsgItemClickListener;
import com.yutong.axxc.parents.view.util.DensityUtil;

/**
 * 站点位置定位，传入GPS坐标，显示其位置信息
 * 
 * @author zhangyongn
 * 
 */
public class LocationMapActivity extends MapActivity implements
		OnClickListener, MKOfflineMapListener {
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;

	// private LocationPoint location;
	private String stationName;
	// private OverItemT overitem = null;
	private GeoPoint centerpoint = null;
	/* 历史消息覆盖物 */
	private MsgOverItemT msgoveritem;
	// 历史消息列表
	private List<MsgRecordBean> historymsgs = new ArrayList<MsgRecordBean>();

	private StudentInfoBean currentChild;
	// 当前弹出的消息
	private MsgRecordBean currentMsg;
	/**
	 * 历史消息弹出泡泡定位y偏移量,px
	 */
	private int offset_msgpop_y = 0;

	/**
	 * 消息提示
	 */
	private View msgPopView;
	/**
	 * 消息overitem点击侦听
	 */
	private MsgItemClickListener msgItemListener;
	/**
	 * 全局Application对象
	 */
	private YtApplication app;
	/**
	 * 自定义显示地图MapView
	 */
	private EnhancedMapView mMapView;
	/* 离线地图相关 */
	private MKOfflineMap mOffline = null;

	private void initData() {
		Intent intent = getIntent();
		if (intent == null)
			return;
		Bundle bundle = intent.getExtras();
		if (bundle == null)
			return;
		currentChild = (StudentInfoBean) bundle
				.getSerializable(ActivityCommConstant.STUDENT_INFO);
		MsgRecordBean msg = (MsgRecordBean) bundle
				.getSerializable(ActivityCommConstant.LOCATIN_MSG);
		stationName = (String) bundle
				.getSerializable(ActivityCommConstant.STATION_NAME);
		if (msg != null)
			historymsgs.add(msg);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_location);
		initData();

		initViews();
		initListener();
		initMapEnginer();
		loadOfflineMap();

		initMsgPop();
		genMsgInMap();
		
		// 设置中心点
		if (centerpoint == null) {
			centerpoint = MyOverItem.getGeoPoint(YtApplication.getInstance()
					.getcLatitude(), YtApplication.getInstance()
					.getcLongitude());

		}
		mMapView.getController().setCenter(centerpoint);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 后期可能添加当网络不可用时，判断是否有离线地图，如果有，则显示离线地图，否则启动app.mBMapManager
		int current_offline_num = ContextUtil.getPreference(getContext(),
				SharedPreferencesUtil.PREFS_NAME_SETTING, MODE_PRIVATE,
				ActivityCommConstant.OFFLINE_NUMBER_KEY, 0);
		Logger.d(MainMapActivity.class, "扫描到的离线地图个数resume："
				+ current_offline_num);
		app.mBMapManager.start();

		if (historymsgs != null && historymsgs.size() > 0)
			showMsgPopView(historymsgs.get(0));
	}

	@Override
	protected void onPause() {
		super.onPause();
		// saveMapStatus();
		if (app.mBMapManager != null)
			app.mBMapManager.stop();

	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		// 历史消息地图overitem点击事件
		msgItemListener = new MsgItemClickListener() {

			@Override
			public void onTap(GeoPoint p, MapView mapview) {
				if (msgPopView == null)
					return;
				msgPopView.setVisibility(View.INVISIBLE);

			}

			@Override
			public void onMsgItemClick(MsgRecordBean msg) {
				showMsgPopView(msg);

			}
		};
	}

	private void initViews() {

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setVisibility(View.INVISIBLE);

		if (StringUtil.isNull(stationName))
			tv_top_title.setText("无站点名称");
		else
			tv_top_title.setText(stationName);
	}

	/**
	 * 地图引擎初始化
	 */
	private void initMapEnginer() {
		app = (YtApplication) getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(app);
			app.mBMapManager.init(YtApplication.strKey,
					new YtApplication.MyGeneralListener());
		}
		// 如果使用地图SDK，请初始化地图Activity
		super.initMapActivity(app.mBMapManager);
		mMapView = (EnhancedMapView) findViewById(R.id.bmapView);

		mMapView.setBuiltInZoomControls(false);
		// 设置在缩放动画过程中也显示overlay,默认为不绘制
		mMapView.setDrawOverlayWhenZooming(true);
		mMapView.setLongClickable(true);
		// // 开启指南针
		// // 添加定位图层
		// MyLocationOverlay mylocTest = new MyLocationOverlay(this, mMapView);
		// mylocTest.enableCompass(); // 启用指南针
		// mMapView.getOverlays().add(mylocTest);
		// 进入时候的中心点应该是小孩儿家的位置
		mMapView.getController().setZoom(17);
		mMapView.getController().setCenter(
				MyOverItem.getGeoPoint(YtApplication.getInstance()
						.getcLatitude(), YtApplication.getInstance()
						.getcLongitude()));
		mMapView.setOnZoomChangeListener(new OnZoomChangeListener() {

			@Override
			public void onZoomChange(MapView view, int newZoom, int oldZoom) {
				Logger.i(MainMapActivity.class, "之前缩放级别:" + oldZoom);
				Logger.i(MainMapActivity.class, "新的缩放级别:" + newZoom);

			}
		});
		mMapView.setOnPanChangeListener(new OnPanChangeListener() {

			@Override
			public void onPanChange(MapView view, GeoPoint newCenter,
					GeoPoint oldCenter) {
				Logger.i(MainMapActivity.class, "之前中心点:" + oldCenter);
				Logger.i(MainMapActivity.class, "新的中心点:" + newCenter);

			}
		});
	}

	private void loadOfflineMap() {
		/*****************************************************/
		// 后期可能添加当网络不可用时，判断是否有离线地图，如果有，则显示离线地图，否则启动app.mBMapManager
		// 离线地图相关
		mOffline = new MKOfflineMap();
		mOffline.init(app.mBMapManager, this);
		// 扫描当前离线地图
		int mOfflineNum = mOffline.scan();
		// 获取系统中已经存在的离线地图
		int current_offline_num = ContextUtil.getPreference(getContext(),
				SharedPreferencesUtil.PREFS_NAME_SETTING, MODE_PRIVATE,
				ActivityCommConstant.OFFLINE_NUMBER_KEY, 0);
		// 将当前扫描到的离线地图与已有离线地图数量相加后保存
		ContextUtil.setPreferences(getContext(),
				SharedPreferencesUtil.PREFS_NAME_SETTING, MODE_PRIVATE,
				ActivityCommConstant.OFFLINE_NUMBER_KEY, current_offline_num
						+ mOfflineNum);
		Logger.d(MainMapActivity.class, "扫描到的离线地图个数："
				+ (current_offline_num + mOfflineNum));
		/******************* 离线地图配置结束 **********************************/
	}

	/**
	 * 生成历史消息
	 */
	private void genMsgInMap() {
		try {
			if (historymsgs == null) {
				Logger.i(getClass(), "无历史消息!");
				return;
			}

			msgoveritem = new MsgOverItemT(msgItemListener, this.historymsgs);
			mMapView.getOverlays().add(msgoveritem);

			if (historymsgs != null && historymsgs.size() > 0) {
				MsgRecordBean msg = historymsgs.get(0);

				NeuGeoPoint bdGeoPoint = LocationUtils.fromWgs84ToBaidu(
						msg.getContent(MsgRecordBean.KEY_GPS_LON),
						msg.getContent(MsgRecordBean.KEY_GPS_LAT));

				double lat = bdGeoPoint.getY();
				double lon = bdGeoPoint.getX();

				centerpoint = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
			// ToastMessage("生成消息图标出错！");
		}
	}

	/**
	 * 初始化历史消息泡泡
	 */
	private void initMsgPop() {
		offset_msgpop_y = DensityUtil.dip2px(this, -20);
		msgPopView = super.getLayoutInflater().inflate(R.layout.map_pop_msg,
				null);
		mMapView.addView(msgPopView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.BOTTOM_CENTER));
		msgPopView.setVisibility(View.GONE);

		Button callBtn = (Button) msgPopView.findViewById(R.id.callBtn);
		if (callBtn != null) {
			callBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (currentMsg == null) {
						Logger.i(getClass(), "点击消息泡泡呼叫按钮：无选择消息。");
					}
					String phone = currentMsg
							.getContent(MsgRecordBean.KEY_TEACHER_PHONE);
					if (StringUtil.isNull(phone)) {
						Toast.makeText(
								YtApplication.getInstance()
										.getApplicationContext(), "无电话信息。",
								Toast.LENGTH_SHORT).show();
						return;
					}
					Uri uri = Uri.parse("tel:" + phone);
					Intent it = new Intent(Intent.ACTION_DIAL, uri);
					it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					YtApplication.getInstance().getApplicationContext()
							.startActivity(it);

				}
			});
		}

	}

	@Override
	protected void onDestroy() {
		if (app.mBMapManager != null) {
			app.mBMapManager.destroy();
			app.mBMapManager = null;
		}
		super.onDestroy();
		super.onDestroy();
	}

	/**
	 * 历史消息OverItem点击触发方法
	 * 
	 * @param msg
	 */
	private void showMsgPopView(MsgRecordBean msg) {
		try {
			if (msgPopView == null)
				return;
			// 直接转换为百度地图坐标
			NeuGeoPoint bdGeoPoint = LocationUtils.fromWgs84ToBaidu(
					msg.getContent(MsgRecordBean.KEY_GPS_LON),
					msg.getContent(MsgRecordBean.KEY_GPS_LAT));

			double lat = bdGeoPoint.getY();
			double lon = bdGeoPoint.getX();

			GeoPoint p = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
			mMapView.updateViewLayout(msgPopView, new MapView.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, p, 0,
					offset_msgpop_y, MapView.LayoutParams.BOTTOM_CENTER));

			TextView tv_msg = (TextView) msgPopView.findViewById(R.id.tv_msg);
			TextView tv_time = (TextView) msgPopView.findViewById(R.id.tv_time);
			// 设置宽度，否则textview不会换行
			LayoutParams l = tv_msg.getLayoutParams();
			int pxoffset = DensityUtil.dip2px(LocationMapActivity.this, 200.0f);
			l.width = mMapView.getWidth() - pxoffset;
			tv_msg.setLayoutParams(l);

			// 填充数据
			String time = Tools.getFormatTime(msg.getMsg_time(),
					"yyyyMMddHHmmss", "HH:mm");
			tv_time.setText(time);
			msgconfig c = MessageBuilder.getMsgConfig(msg);
			String body = buildMsgBody(c, msg);
			tv_msg.setText(body);
			msgPopView.setVisibility(View.VISIBLE);

			currentMsg = msg;
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
			ToastMessage("发生错误！");
		}
	}

	/**
	 * 建造历史消息正文
	 * 
	 * @param msg
	 * @return
	 */
	private String buildMsgBody(msgconfig c, MsgRecordBean msg) {
		if (msg == null)
			return null;
		if (c == null)
			return msg.getRule_id();

		Map<String, String> data = MessageBuilder.buildDataMap(msg,
				currentChild);

		WeatherInfoBean weather = YtApplication.getInstance()
				.getWeatherInfoCache();
		if (weather != null) {
			data.put("temp", weather.getTodayTemper());
			data.put("pm", weather.getTodayPM());
			data.put("wind", weather.getTodayWind());
		}
		String body = StringTemplateHelper.parse(c.getBody(), data);
		return body;
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:

			break;
		case R.id.btn_title_left:
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	public void onGetOfflineMapState(int type, int state) {
		switch (type) {
		case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
			// 离线地图下载更新事件类型
			Logger.i(LocationMapActivity.class, "离线地图下载更新事件类型:" + state);
			mOffline.getUpdateInfo(state);
		}
			break;
		case MKOfflineMap.TYPE_NEW_OFFLINE:
			// 新安装离线地图事件类型
			Logger.i(LocationMapActivity.class, "新安装离线地图事件类型:" + state);

			break;
		case MKOfflineMap.TYPE_VER_UPDATE:
			// 离线地图数据版本更新事件类型
			Logger.i(LocationMapActivity.class, " 离线地图数据版本更新事件类型" + state);
			break;
		}

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Toast提示，保证在UI线程执行提示
	 * 
	 * @param msg
	 */
	protected void ToastMessage(final String msg) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(LocationMapActivity.this, msg,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * Activity上下文对象
	 * 
	 * @return
	 */
	private LocationMapActivity getContext() {
		return this;
	}

	
}


