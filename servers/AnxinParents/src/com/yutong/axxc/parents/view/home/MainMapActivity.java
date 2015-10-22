package com.yutong.axxc.parents.view.home;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MKOfflineMap;
import com.baidu.mapapi.MKOfflineMapListener;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.beans.BizMapCtrlBean;
import com.yutong.axxc.parents.business.beans.BizOverlayItemBean;
import com.yutong.axxc.parents.business.beans.BizVehicleLocusItemBean;
import com.yutong.axxc.parents.business.common.CommonPushMsgUtil;
import com.yutong.axxc.parents.business.line.GetRealTimeInfoBiz;
import com.yutong.axxc.parents.business.line.GetStationInfoBiz;
import com.yutong.axxc.parents.business.line.GetStudentLineInfoBiz;
import com.yutong.axxc.parents.business.student.GetStudentMsgRecordBiz;
import com.yutong.axxc.parents.business.student.GetStudentRidingRecordBiz;
import com.yutong.axxc.parents.business.student.GetStudentStatusBiz;
import com.yutong.axxc.parents.business.vehicle.GetVehicleRealtimeInfoBiz;
import com.yutong.axxc.parents.business.view.ReadStudentInfoBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.LocationUtils;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.NeuGeoPoint;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.SharedPreferencesUtil;
import com.yutong.axxc.parents.common.StringTemplateHelper;
import com.yutong.axxc.parents.common.SysConfigUtil;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YTException;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.LineInfoBean;
import com.yutong.axxc.parents.common.beans.LinePointInfoBean;
import com.yutong.axxc.parents.common.beans.MsgRecordBean;
import com.yutong.axxc.parents.common.beans.RidingRecordBean;
import com.yutong.axxc.parents.common.beans.StationInfoBean;
import com.yutong.axxc.parents.common.beans.StationRealTimeInfoBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.beans.VehicleRealtimeInfoBean;
import com.yutong.axxc.parents.common.beans.WeatherInfoBean;
import com.yutong.axxc.parents.common.context.ContextUtil;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.connect.push.CommonPushMsg;
import com.yutong.axxc.parents.connect.push.PushMessageListenHost;
import com.yutong.axxc.parents.connect.push.PushMessageListenHost.OnMessageReceiveListener;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ChildCustomConfigs;
import com.yutong.axxc.parents.view.common.ChildCustomConfigs.ChildCustomTheme;
import com.yutong.axxc.parents.view.common.ActivityManager;
import com.yutong.axxc.parents.view.common.CircularImage;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.MessageBuilder;
import com.yutong.axxc.parents.view.common.MessageBuilder.curmsgconfig;
import com.yutong.axxc.parents.view.common.MessageBuilder.msgconfig;
import com.yutong.axxc.parents.view.common.ResourcesUtils;
import com.yutong.axxc.parents.view.common.SingleSelectChildrenBar;
import com.yutong.axxc.parents.view.common.SingleSelectChildrenBar.OnBarItemClickListener;
import com.yutong.axxc.parents.view.common.StudentStateConstant;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.UserGridAdapter;
import com.yutong.axxc.parents.view.common.UserGridAdapter.UserGridInfo;
import com.yutong.axxc.parents.view.common.UserGridExchangeListener;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.home.EnhancedMapView.OnPanChangeListener;
import com.yutong.axxc.parents.view.home.EnhancedMapView.OnZoomChangeListener;
import com.yutong.axxc.parents.view.util.DensityUtil;

/**
 * 地图视图
 * 
 * 加载顺序：小孩-》状态-》线路-》站-》历史消息-》根据状态启动定时器，定时查询乘车信息，车辆实时信息，估算值
 * 
 * @author zhangyongn
 * 
 */
public class MainMapActivity extends MapActivity implements OnClickListener,
		MKOfflineMapListener {
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	private OnMessageReceiveListener msgListener;

	/* 小孩相关 */
	private List<StudentInfoBean> students;
	private SingleSelectChildrenBar mhv_users;
	private UserGridAdapter uadapter;
	private List<UserGridInfo> userdata;
	private StudentInfoBean currentChild;

	private YtAsyncTask childbiz;// 小孩信息
	private YtAsyncTask msgBiz; // 历史消息
	private YtAsyncTask curStateBiz; // 小孩状态
	private YtAsyncTask stationBiz; // 站点信息
	private YtAsyncTask lineBiz;// 线路轨迹信息

	private String line_type;// 线路类型,根据小孩的状态，设置该值。

	// 历史消息列表
	private List<MsgRecordBean> historymsgs = new ArrayList<MsgRecordBean>();
	// 当前弹出的消息
	private MsgRecordBean currentMsg;
	// 当前状态
	private String curSrvState = StudentStateConstant.SRV_SCHOOL_BEFORE;
	/* 当前日期 */
	private Calendar currentCal = Calendar.getInstance();
	/* 站点信息 */
	private List<StationInfoBean> stationInfos = new ArrayList<StationInfoBean>();
	/* 线路轨迹信息 */
	private LineInfoBean lineInfo = new LineInfoBean();
	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;
	/**
	 * 全局Application对象
	 */
	private YtApplication app;

	/*----------------------地图相关-----------------------*/
	/* 历史消息覆盖物 */
	private MsgOverItemT msgoveritem;
	/* 站点信息覆盖物 */
	private StationOverItemT stationoveritem;

	private GeoPoint centerPoint;
	/**
	 * 自定义显示地图MapView
	 */
	private EnhancedMapView mMapView;
	/* 离线地图相关 */
	private MKOfflineMap mOffline = null;
	/* 汽车运行时显示的图标 */
	private View mCarIconView;
	/**
	 * 当前状态提示
	 */
	private View curStateTipPopView;
	/**
	 * 预估textView
	 */
	private TextView tvPredict;
	/**
	 * 消息提示
	 */
	private View msgPopView;
	/**
	 * 消息overitem点击侦听
	 */
	private MsgItemClickListener msgItemListener;
	/**
	 * 地图点位信息
	 */
	private ArrayList<BizOverlayItemBean> locusGeopointList = new ArrayList<BizOverlayItemBean>();

	// 定时器执行更新动作
	/*
	 * 用于判断resume的时候，是否需要启动定时器， 每次重新加载数据前置false。加载完成置true，判断是否启动定时器需要同时判断该标志
	 */
	private boolean isLoaded = false;
	private Timer timer = new Timer();
	private TimerTask task;
	private int DELAY = 2000;
	private int PERIOD = 1000 * 30;
	private Handler handler = null;
	private boolean isStart = false;

	private final static int SUCCESS = 0;
	private final static int NODATA = 1;
	private final static int STATUSCHANGE = 2;
	/* 预先保存车辆实时信息估算提示文本 */
	private String predictText = "正在获取估算信息...";

	// 评估值相关
	private GetStudentRidingRecordBiz busDetailBiz; // 跟车信息
	private GetRealTimeInfoBiz predictBiz; // 预计路程
	private GetVehicleRealtimeInfoBiz carRealTimeBiz;// 车辆实时信息
	private List<VehicleRealtimeInfoBean> carRealTimeInfos;
	private StationRealTimeInfoBean predictInfo;
	private RidingRecordBean detailInfo;

	/**
	 * 历史消息弹出泡泡定位y偏移量,px
	 */
	private int offset_msgpop_y = 0;

	/**
	 * 当前消息弹出泡泡定位偏移量,px
	 */
	private int offset_carpop_y = 0;
	/**
	 * 汽车小孩图标定位偏移量,px
	 */
	private int offset_car_y = 50;

	/**
	 * 是否已经弹出提示
	 */
	private boolean poptip = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_map);
		WeakReference<Activity> reference = new WeakReference<Activity>(this);
		ActivityManager.addActivity(reference);

		initParam();
		initData();

		initHandler();

		initViews();
		initListener();
		initMapEnginer();
		loadOfflineMap();

		initCarIconView();
		initCurStatePop();
		initMsgPop();

		initChildInfo();

		// 用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
		UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0008);

	}

	private void initParam() {
		try {
			int delay = Integer.parseInt(SysConfigUtil.getProperty(
					"MAINMAP_TIMER_PERIOD", "30"));
			PERIOD = 1000 * delay;
		} catch (Exception e) {
			PERIOD = 1000 * 30;
		}
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

		updateChildMsg();
		PushMessageListenHost host = YtApplication.getInstance().getPushHost();
		if (host != null) {

			host.addOnMessageReceiveListener(msgListener);
		}
		if (IfNeedStartTimer())
			startTimer();
	}

	/**
	 * 更新学生消息
	 */
	protected void updateChildMsg() {
		if (this.students == null)
			return;
		for (int i = 0; i < students.size(); i++) {
			onChildNewMsg(students.get(i).getCld_id());
		}
	}

	/**
	 * 检查是否需要启动定时器
	 * 
	 * @return
	 */
	private boolean IfNeedStartTimer() {
		// test
		// return true;
		if (!isLoaded)
			return false;
		return StringUtil.equals(this.curSrvState,
				StudentStateConstant.SRV_AFTER_AT_BUS)
				|| StringUtil.equals(this.curSrvState,
						StudentStateConstant.SRV_SCHOOL_AT_BUS)
				|| StringUtil.equals(this.curSrvState,
						StudentStateConstant.SRV_SCHOOL_ONBUS_WAIT)
				|| StringUtil.equals(this.curSrvState,
						StudentStateConstant.SRV_AFTER_OFFBUS_WAIT);

	}

	@Override
	protected void onPause() {
		super.onPause();
		// saveMapStatus();
		if (app.mBMapManager != null)
			app.mBMapManager.stop();
		PushMessageListenHost host = YtApplication.getInstance().getPushHost();
		if (host != null) {
			host.removeOnMessageReceiveListener(msgListener);
		}
		stopTimer();
	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		loadingoverlay
				.addOnCancelListener(new LoadingOverlay.OnCancelListener() {

					@Override
					public void onCancel() {
						if (childbiz != null)
							childbiz.cancel();
						loadingoverlay.setVisibility(View.INVISIBLE);

					}
				});

		msgListener = new OnMessageReceiveListener() {

			@Override
			public void OnReceivedReminds(CommonPushMsg message) {
				onChildNewMsg(message.getCld_id());
			}

			@Override
			public void OnReceivedNews(CommonPushMsg message) {

			}

			@Override
			public void OnReceived(CommonPushMsg message) {

			}
		};
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
		tv_top_title.setText("地图");

		// tv_top_title.setText(R.string.registerTitle);
		/* 头像列表相关 */
		mhv_users = (SingleSelectChildrenBar) findViewById(R.id.mHScrollView1);
		loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);
	}

	private void initData() {
		Intent intent = getIntent();
		if (intent == null)
			return;
		Bundle bundle = intent.getExtras();
		if (bundle == null)
			return;
		setCurrentChild((StudentInfoBean) bundle
				.getSerializable(ActivityCommConstant.STUDENT_INFO));
	}

	private void initChildInfo() {
		// loadingoverlay.setVisibility(View.VISIBLE);//隐藏加载条
		loadingoverlay.setLoadingTip("正在加载学生信息,请稍候...");
		startLoadStudentTask();
	}

	private void startLoadStudentTask() {
		if (childbiz != null)
			childbiz.cancel();
		// childbiz = new GetStudentInfoBiz(getApplicationContext(),
		// new StudentHandler(MainMapActivity.this),
		// ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE);
		childbiz = new ReadStudentInfoBiz(getApplicationContext(),
				new StudentHandler(MainMapActivity.this));

		childbiz.execute();

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
		mMapView.setClickable(true);

		// 进入时候的中心点应该是小孩儿家的位置
		mMapView.getController().setZoom(15);
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
		// 该事件很难触发，地图sdk问题
		// mMapView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Toast.makeText(MainMapActivity.this, "map click",
		// Toast.LENGTH_SHORT).show();
		// if (mCarPopView == null)
		// return;
		// if (mCarPopView.getVisibility() == View.VISIBLE) {
		// mCarPopView.setVisibility(View.INVISIBLE);
		// }
		//
		// }
		// });
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

	@Override
	protected void onDestroy() {
		if (app.mBMapManager != null) {
			app.mBMapManager.destroy();
			app.mBMapManager = null;
		}
		stopTimer();
		super.onDestroy();

	}

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
			Logger.i(MainMapActivity.class, "离线地图下载更新事件类型:" + state);
			mOffline.getUpdateInfo(state);
		}
			break;
		case MKOfflineMap.TYPE_NEW_OFFLINE:
			// 新安装离线地图事件类型
			Logger.i(MainMapActivity.class, "新安装离线地图事件类型:" + state);

			break;
		case MKOfflineMap.TYPE_VER_UPDATE:
			// 离线地图数据版本更新事件类型
			Logger.i(MainMapActivity.class, " 离线地图数据版本更新事件类型" + state);
			break;
		}

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	protected void onChildNewMsg(String childId) {
		try {
			if (childId == null)
				return;
			UserGridInfo info = getuserInChildBar(childId);
			int count = CommonPushMsgUtil
					.getChildRidingNotificationCount(childId);
			if (info != null) {
				info.Message = (count <= 0) ? "" : count + "";
				uadapter.notifyDataSetChanged();
			}
			// StudentInfoBean currentcld = this.getCurrentChild();
			// if (currentcld != null
			// && StringUtil.equals(currentcld.getCld_id(), childId)) {
			// onChildChanged(currentcld);
			// }

		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}

	}

	/**
	 * Activity上下文对象
	 * 
	 * @return
	 */
	private MainMapActivity getContext() {
		return this;
	}

	/**
	 * @return the currentChild
	 */
	public StudentInfoBean getCurrentChild() {
		return currentChild;
	}

	/**
	 * @param currentChild
	 *            the currentChild to set
	 */
	public void setCurrentChild(StudentInfoBean currentChild) {
		this.currentChild = currentChild;
	}

	protected void onChildChanged(StudentInfoBean child) {
		if (child == null) {
			Toast.makeText(this, "无学生信息。", Toast.LENGTH_SHORT).show();
			return;
		}
		isLoaded = false;

		stopTimer();
		clearMaps();
		updateCarBg_ChildHeader();
		tv_top_title.setText("地图-" + child.getFullName());
		// loadingoverlay.setVisibility(View.VISIBLE);//不显示加载条
		loadingoverlay.setLoadingTip("获取当前学生状态...");
		startLoadCurStateTask();
	}

	private void loadChildBar(List<StudentInfoBean> studentinfos) {
		prepareuserData(studentinfos);
		uadapter = new UserGridAdapter(this, userdata, mhv_users);
		mhv_users.setOnBarItemClickListener(new OnBarItemClickListener() {

			@Override
			public void onClick(int index) {
				try {
					if (!uadapter.getSelectedIndexs().contains(index))
						return;
					predictText = "正在获取估算信息...";
					Logger.i(getClass(), "点击小孩事件：" + index);
					UserGridInfo firstinfo = userdata.get(index);

					uadapter.notifyDataSetChanged();
					currentChild = getStudentById(firstinfo.ChildId);
					if (currentChild != null) {
						CommonPushMsgUtil
								.deleteChildNotificationCount(currentChild
										.getCld_id());
						onChildChanged(currentChild);
					}
				} catch (Exception e) {
					Logger.e(getClass(), e.getMessage());
				}
			}

		});
		uadapter.setExChangelistener(new UserGridExchangeListener() {

			@Override
			public void GotoTop(int oldindex) {
				try {
					int newIndex = 0;// 第0个为最新的选中项。
					predictText = "正在获取估算信息...";
					Logger.i(getClass(), "点击小孩事件：" + newIndex);
					UserGridInfo firstinfo = userdata.get(newIndex);

					uadapter.notifyDataSetChanged();
					currentChild = getStudentById(firstinfo.ChildId);
					if (currentChild != null) {
						CommonPushMsgUtil
								.deleteChildNotificationCount(currentChild
										.getCld_id());
						onChildChanged(currentChild);
					}
				} catch (Exception e) {
					Logger.e(getClass(), e.getMessage());
				}
			}

			@Override
			public void ExchangeStart(int index1, int index2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void ExchangeEnd(int oldIndex, int newIndex) {

			}
		});
		mhv_users.setGridViewAdapter(uadapter);

		// 设置选中小孩
		boolean isset = false;
		List<Integer> selectedIndexs = uadapter.getSelectedIndexs();
		if (this.currentChild != null) {
			for (int i = 0; i < userdata.size(); i++) {
				if (StringUtil.equals(this.currentChild.getCld_id(),
						userdata.get(i).ChildId)) {
					uadapter.setSelectedIndex(i);
					isset = true;
				}
			}
			// 如果未设置，则设置一个默认的
			if (!isset) {
				if (selectedIndexs != null && selectedIndexs.size() > 0)
					this.currentChild = students.get(selectedIndexs.get(0));
			}
		} else {

			if (selectedIndexs != null && selectedIndexs.size() > 0)
				this.currentChild = students.get(selectedIndexs.get(0));
		}
	}

	/**
	 * 设置头像信息
	 */
	private void prepareuserData(List<StudentInfoBean> studentinfos) {
		userdata = new ArrayList<UserGridInfo>();

		if (studentinfos == null || studentinfos.size() <= 0) {
			return;
		}
		for (int i = 0; i < studentinfos.size(); i++) {
			StudentInfoBean b = studentinfos.get(i);

			int headresid = getHeadRes(b);
			userdata.add(new UserGridInfo(b.getCld_id(), b.getCld_name(),
					getResources().getDrawable(headresid), "", b.getCld_photo()));

		}

	}

	private int getHeadRes(StudentInfoBean b) {
		if (StringUtil.equals(b.getCld_sex(), StudentInfoBean.SEX_MAIL))
			return R.drawable.default_boy;
		else
			return R.drawable.default_gril;

	}

	private UserGridInfo getuserInChildBar(String childId) {
		if (childId == null)
			return null;
		if (userdata == null)
			return null;
		UserGridInfo info = null;
		for (int i = 0; i < userdata.size(); i++) {
			info = userdata.get(i);
			if (StringUtil.equals(childId, info.ChildId))
				return info;
		}
		return null;
	}

	private StudentInfoBean getStudentById(String childId) {
		if (this.students == null)
			return null;
		for (int i = 0; i < students.size(); i++) {
			if (StringUtil.equals(childId, students.get(i).getCld_id())) {
				return students.get(i);
			}
		}
		return null;
	}

	private void startLoadMsgsTask() {
		if (msgBiz != null) {
			msgBiz.cancel();
			msgBiz = null;
		}
		msgBiz = new GetStudentMsgRecordBiz(this, new LoadMsgHandler(this),
				this.currentChild.getCld_id(),
				Tools.getFormatTime(this.currentCal));
		msgBiz.execute();
	}

	public void startLoadCurStateTask() {
		if (curStateBiz != null) {
			curStateBiz.cancel();
			curStateBiz = null;
		}
		curStateBiz = new GetStudentStatusBiz(this, new LoadCurStateHandler(
				this), this.currentChild.getCld_id());
		curStateBiz.execute();

	}

	/**
	 * 加载校车站点数据
	 */
	private void startLoadStationsTask() {
		if (stationBiz != null) {
			stationBiz.cancel();
			stationBiz = null;
		}

		stationBiz = new GetStationInfoBiz(this, new StationHandler(this),
				this.currentChild.getCld_id(), line_type,
				ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE);

		stationBiz.execute();
	}

	/**
	 * 加载车辆轨迹数据
	 */
	private void startLoadLineTrace() {
		if (lineBiz != null) {
			lineBiz.cancel();
			lineBiz = null;
		}
		lineBiz = new GetStudentLineInfoBiz(getContext(),
				new LineHandler(this), this.currentChild.getCld_id(),
				line_type, ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE);
		lineBiz.execute();
	}

	/**
	 * 加载学生Handler
	 * 
	 * @author zhangyongn
	 * 
	 */
	private static class StudentHandler extends YtHandler {
		private final WeakReference<MainMapActivity> activity;

		public StudentHandler(MainMapActivity activity) {
			this.activity = new WeakReference<MainMapActivity>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {

			MainMapActivity ac = activity.get();
			if (ac != null) {

				super.handleMessage(msg, ac);
				switch (msg.what) {
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:

					ac.students = (List<StudentInfoBean>) msg.obj;
					if (ac.students == null || ac.students.size() <= 0) {
						ac.ToastMessage("无小孩信息");
						break;
					}
					ac.loadChildBar(ac.students);
					ac.onChildChanged(ac.getCurrentChild());

					break;
				case ThreadCommStateCode.COMMON_FAILED:
					ac.ToastMessage((String) msg.obj);
					ac.loadingoverlay.setVisibility(View.INVISIBLE);
					break;

				default:
					break;
				}
			}
		}
	}

	/**
	 * 加载学生当前状态Handler
	 * 
	 * @author zhangyongn
	 */
	private static class LoadCurStateHandler extends YtHandler {
		private final WeakReference<MainMapActivity> viewW;

		public LoadCurStateHandler(MainMapActivity view) {
			super();
			this.viewW = new WeakReference<MainMapActivity>(view);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			try {
				super.handleMessage(msg);
				MainMapActivity v = viewW.get();
				super.handleMessage(msg, v);

				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:

					v.curSrvState = (String) msg.obj;
					// v.loadingoverlay.setLoadingTip("获取学生当前状态...");
					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Logger.e(this.getClass(), msg.obj);

					v.curSrvState = StudentStateConstant.SRV_SCHOOL_BEFORE;

					break;
				}
				v.line_type = v.getLineTypeByStatus(v.curSrvState);
				v.loadingoverlay.setLoadingTip("获取线路数据...");
				v.startLoadLineTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 线路轨迹handler
	 * 
	 * @author zhangyongn
	 * 
	 */
	private static class LineHandler extends YtHandler {
		private final WeakReference<MainMapActivity> viewW;

		public LineHandler(MainMapActivity view) {
			super();
			this.viewW = new WeakReference<MainMapActivity>(view);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			MainMapActivity v = viewW.get();
			try {
				super.handleMessage(msg, v);

				switch (msg.what) {
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:
					v.lineInfo = (LineInfoBean) msg.obj;
					v.loadingoverlay.setLoadingTip("获取站点数据...");
					v.startLoadStationsTask();

					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Logger.e(this.getClass(), msg.obj);
					v.ToastMessage((String) msg.obj);
					v.loadingoverlay.setVisibility(View.INVISIBLE);
					break;
				default:
					Logger.e(this.getClass(), msg.obj);
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
				v.loadingoverlay.setVisibility(View.INVISIBLE);
				Toast.makeText(v, "发生错误！", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 站点数据handler
	 * 
	 * @author zhangyongn
	 * 
	 */
	private static class StationHandler extends YtHandler {
		private final WeakReference<MainMapActivity> viewW;

		public StationHandler(MainMapActivity view) {
			super();
			this.viewW = new WeakReference<MainMapActivity>(view);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			MainMapActivity v = viewW.get();
			try {

				super.handleMessage(msg, v);

				switch (msg.what) {
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:
					v.stationInfos = (List<StationInfoBean>) msg.obj;
					v.clearNormalStation();
					v.loadingoverlay.setLoadingTip("获取历史消息...");
					v.startLoadMsgsTask();
					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Logger.e(this.getClass(), msg.obj);
					v.ToastMessage((String) msg.obj);
					v.loadingoverlay.setVisibility(View.INVISIBLE);
					break;
				default:
					Logger.e(this.getClass(), msg.obj);
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
				v.loadingoverlay.setVisibility(View.INVISIBLE);
				Toast.makeText(v, "发生错误！", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 历史消息handler
	 * 
	 * @author zhangyongn
	 * 
	 */
	private static class LoadMsgHandler extends YtHandler {
		private final WeakReference<MainMapActivity> viewW;

		public LoadMsgHandler(MainMapActivity view) {
			super();
			this.viewW = new WeakReference<MainMapActivity>(view);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			MainMapActivity v = viewW.get();
			try {

				super.handleMessage(msg, v);

				switch (msg.what) {
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:
					CommonPushMsgUtil
							.deleteChildNotificationCount(v.currentChild
									.getCld_id());
					v.historymsgs = (List<MsgRecordBean>) msg.obj;
					v.loadingoverlay.setVisibility(View.INVISIBLE);
					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Logger.e(this.getClass(), msg.obj);
					v.loadingoverlay.setVisibility(View.INVISIBLE);
					break;
				default:
					Logger.e(this.getClass(), msg.obj);
					break;
				}

				if (v.historymsgs != null)
					Collections.sort(v.historymsgs, v.new SortByTime());

				// to do：运行到这里，表示所有数据已加载。接下里是地图相关数据，元素，图层的显示，交互。

				v.onDataLoadComplete();

			} catch (Exception e) {
				e.printStackTrace();
				v.loadingoverlay.setVisibility(View.INVISIBLE);
				Toast.makeText(v, "发生错误！", Toast.LENGTH_SHORT).show();
			}
		}
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
				Toast.makeText(MainMapActivity.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	public void clearNormalStation() {
		if (stationInfos == null)
			return;
		List<StationInfoBean> infos = new ArrayList<StationInfoBean>();
		for (int i = 0; i < stationInfos.size(); i++) {
			if (StringUtil.equals(stationInfos.get(i).getStation_type(),
					StationInfoBean.STATION_TYPE_NORML))
				continue;
			infos.add(stationInfos.get(i));
		}
		stationInfos = infos;
	}

	public void onDataLoadComplete() {
		isLoaded = true;
		loadMapData();
		updateChildMsg();
		if (IfNeedStartTimer()) {
			startTimer();
		} else {

			hideCar_Pop();
		}
	}

	// private double lat = 39.90923, lon = 116.397428;

	public void loadMapData() {
		try {
			// 先清除

			genLineInMap();
			genStationInMap();
			genMsgInMap();

			// 设置中心点

			if (centerPoint == null) {
				centerPoint = MyOverItem.getGeoPoint(YtApplication
						.getInstance().getcLatitude(), YtApplication
						.getInstance().getcLongitude());
				mMapView.getController().setCenter(centerPoint);
			}

		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
			ToastMessage("生成地图元素出错！");
		}
	}

	/**
	 * 生成线路
	 */
	private void genLineInMap() {
		try {
			if (lineInfo == null) {
				Logger.i(getClass(), "无线路信息。");
				// Toast.makeText(this, "无线路信息。", Toast.LENGTH_SHORT).show();
				return;
			}
			// 生成线路
			List<LinePointInfoBean> list = lineInfo.getPointInfos();
			int marker = R.drawable.map_black_point;
			GeoPoint gp = null;
			LinePointInfoBean bean = null;
			BizOverlayItemBean overItem = null;
			for (int position = 0; position < list.size(); position++) {

				bean = list.get(position);
				gp = MyOverItem.getGeoPoint(
						Double.parseDouble(bean.getGps_lat()),
						Double.parseDouble(bean.getGps_lon()));
				overItem = MyOverItem.getOverlayItems(
						Double.parseDouble(bean.getGps_lat()),
						Double.parseDouble(bean.getGps_lon()), marker, null,
						null);

				// 设置覆盖物的点位信息
				overItem.setLocusItem(bean);
				// 存储覆盖物
				locusGeopointList.add(overItem);
			}

			if (locusGeopointList.size() != 0) {
				// 生成车具体位置的悬浮框
				MyOverItem overlay = initMyOverlayItem();
				mMapView.getOverlays().add(overlay);
			}
			// 设置中心点
			centerPoint = gp;
			// add by lizyi
			// 设置当前所有轨迹中心点为地图中心
			BizMapCtrlBean mapCtr = getBizMapCtrl(list);
			mMapView.getController().setCenter(
					MyOverItem.getGeoPoint(mapCtr.getcLatitude(),
							mapCtr.getcLongitude()));

		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
			// ToastMessage("生成线路出错！");
		}
	}

	// add by lizyi
	/**
	 * 根据位置坐标计算地图控制信息
	 * 
	 * @return Returns the bizMapCtrl.
	 */
	private BizMapCtrlBean getBizMapCtrl(List<LinePointInfoBean> list) {
		if (list == null || list.size() == 0)
			return new BizMapCtrlBean();

		double[] latitudes = new double[list.size()];
		double[] longitudes = new double[list.size()];
		int index = 0;
		for (LinePointInfoBean bean : list) {
			latitudes[index] = Double.parseDouble(bean.getGps_lat());
			longitudes[index] = Double.parseDouble(bean.getGps_lon());
			index++;
		}
		Arrays.sort(latitudes);
		Arrays.sort(longitudes);
		BizMapCtrlBean tBizMapCtrl = new BizMapCtrlBean();
		tBizMapCtrl
				.setcLatitude((latitudes[0] + latitudes[latitudes.length - 1]) / 2);
		tBizMapCtrl
				.setcLongitude((longitudes[0] + longitudes[longitudes.length - 1]) / 2);
		return tBizMapCtrl;
	}

	/**
	 * 生成站点
	 */
	private void genStationInMap() {
		try {
			if (stationInfos == null) {
				// ToastMessage("没有对应的站点信息！");
				Logger.e(getClass(), "没有对应的站点信息！");
				return;
			}

			stationoveritem = new StationOverItemT(this.stationInfos,
					this.line_type, currentChild);
			// add by lizyi
			// 设置家的站点为中心点
			if (centerPoint == null) {
				centerPoint = getHomePoint();
				mMapView.getController().setCenter(centerPoint);
			}
			mMapView.getOverlays().add(stationoveritem);
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
			// ToastMessage("生成站点图标出错！");
		}
	}

	private GeoPoint getHomePoint() {
		GeoPoint p = centerPoint;
		if (stationInfos == null)
			return p;

		if (StringUtil.equals(line_type, LineInfoBean.LINE_TYPE_UP)) {
			for (int i = 0; i < stationInfos.size(); i++) {
				StationInfoBean s = stationInfos.get(i);
				if (StringUtil.equals(s.getStation_type(),
						StationInfoBean.STATION_TYPE_UP)) {
					double lat = Double.parseDouble(s.getGps_lat());
					double lon = Double.parseDouble(s.getGps_lon());
					p = MyOverItem.getGeoPoint(lat, lon);
					return p;
				}
			}

		} else {
			for (int i = 0; i < stationInfos.size(); i++) {
				StationInfoBean s = stationInfos.get(i);
				if (StringUtil.equals(s.getStation_type(),
						StationInfoBean.STATION_TYPE_DOWN)) {
					double lat = Double.parseDouble(s.getGps_lat());
					double lon = Double.parseDouble(s.getGps_lon());
					p = MyOverItem.getGeoPoint(lat, lon);
					return p;
				}
			}
		}
		return p;
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

		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
			// ToastMessage("生成消息图标出错！");
		}
	}

	/* 定时器相关 */
	private void initHandler() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				PushMessage newmsg = (PushMessage) msg.obj;
				try {
					if (!isStart) {
						Logger.i(getClass(), "首页地图：定时器已经关闭.");
						return;
					}
					int what = msg.what;
					switch (what) {
					case SUCCESS:
					case NODATA:
						updateCarInMap();
						onPredictTextUpdated(predictText);
						break;
					case STATUSCHANGE:
						hideCar_Pop();
						// Toast.makeText(MainMapActivity.this, "无定位数据。",
						// Toast.LENGTH_SHORT).show();
						Logger.i(getClass(), "定时获取状态，状态已改变。");
						break;
					}

				} catch (Exception e) {
					Logger.e(this.getClass(), e.getMessage());
				}
				super.handleMessage(msg);
			}

		};

	}

	private void startTimer() {

		if (isStart) {
			Logger.i(this.getClass(), "定时器已经启动,不能重复启动。");
			return;
		}
		Logger.i(getClass(), "indexmap:启动定时器。");
		if (timer == null) {
			timer = new Timer();
		}
		if (task == null) {
			task = new TimerTask() {
				@Override
				public void run() {
					Logger.d(getClass(), "indexmap timer run...");
					startGetPredictInfo();

				}
			};
		}

		timer.schedule(task, DELAY, PERIOD);
		isStart = true;
	}

	private void stopTimer() {
		Logger.i(getClass(), "indexmap:关闭定时器。");
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (task != null) {
			task.cancel();
			task = null;
		}
		isStart = false;
	}

	protected void startGetPredictInfo() {

		try {
			predictBiz = new GetRealTimeInfoBiz(this, null,
					this.currentChild.getCld_id(), null, null, null);

			busDetailBiz = new GetStudentRidingRecordBiz(this, null,
					this.currentChild.getCld_id());
			GetStudentStatusBiz statusBiz = new GetStudentStatusBiz(this, null,
					this.currentChild.getCld_id());

			// 先查状态
			String status = statusBiz.getStatus();
			if (!ifNeedPredict(status)) {
				Logger.i(getClass(), "状态发生变化，停止定时器。");
				triger(STATUSCHANGE);
				return;
			}

			this.predictInfo = predictBiz.getStationRealTimeInfo();
			if (this.detailInfo == null) {
				// 这里指取一次，节省性能
				this.detailInfo = busDetailBiz.getRidingRecordInfo();
			}
			List<String> vins = new ArrayList<String>();

			vins.add(detailInfo.getVehicle_vin());
			this.carRealTimeBiz = new GetVehicleRealtimeInfoBiz(this, null,
					vins);
			carRealTimeInfos = carRealTimeBiz.getVehicleRealTimeInfo();

			buildPredictText();

			triger(SUCCESS);

		} catch (YTException yte) {
			if (yte.getStatus() == ThreadCommStateCode.TOKEN_INVALID) {
				Logger.i(getClass(), "tocken invalid。");
				triger(STATUSCHANGE);
				return;
			}
			Logger.e(getClass(), yte.getStatus() + ";" + yte.getMessage());
			buildPredictText();
			triger(NODATA);
		} catch (Exception e) {

			e.printStackTrace();
			Logger.e(this.getClass(), e.getMessage());
		}

	}

	/**
	 * 只有如下状态，才有预估值：上学前、在车上（上学，放学）
	 * 
	 * @param status
	 * @return
	 */
	private boolean ifNeedPredict(String status) {
		boolean b = StringUtil.equals(StudentStateConstant.SRV_AFTER_AT_BUS,
				status)
				|| StringUtil.equals(StudentStateConstant.SRV_SCHOOL_AT_BUS,
						status)
				|| StringUtil.equals(
						StudentStateConstant.SRV_AFTER_OFFBUS_WAIT, status)
				|| StringUtil.equals(
						StudentStateConstant.SRV_SCHOOL_ONBUS_WAIT, status);
		return b;
	}

	/**
	 * 根据获取的估算值，构造估算提示文本
	 */
	private void buildPredictText() {
		try {
			// predictText = "正在获取数据...";
			Map<String, String> data = MessageBuilder
					.buildDataMap(this.currentChild);
			if (data == null)
				data = new HashMap<String, String>();
			if (predictInfo != null) {
				// data.put("", value)
				String predictvalue = buildpredictValue(predictInfo);
				data.put("predict_value", predictvalue);
				data.put("station_name", predictInfo.getStationFullName());
				data.put("vehicle_plate", predictInfo.getVehicle_plate());
				curmsgconfig config = MessageBuilder
						.getCurMsgConfig(curSrvState);

				if (config != null) {
					predictText = StringTemplateHelper.parse(config.getBody(),
							data);
				} else {

					predictText = "发生错误！";
				}

				// msg.setTime(Tools.getFormatTime(Calendar.getInstance(),
				// "HH:mm"));
			} else {
				// predictText = "未获取到估算数据。";
				predictText = buildDefaultBody();
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
			predictText = "发生错误！";
		}
	}

	private String buildDefaultBody() {

		String body = "在校车上";
		try {
			String name = this.currentChild.getFullName();
			String plate = detailInfo.getVehicle_plate();
			body = name + "在" + plate + "校车上。";
			return body;
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
			return body;
		}
	}

	private void triger(int returntype) {
		Message message = new Message();
		message.what = returntype;
		handler.sendMessage(message);
	}

	/* 地图相关 */
	/**
	 * 清除当前地图内容，同时清除相关全局变量缓存
	 */
	protected void clearMaps() {
		Logger.i(getClass(), "overlays size:", mMapView.getOverlays().size());
		if (mMapView != null && mMapView.getOverlays().size() != 0) {
			mMapView.getOverlays().clear();

		}
		if (locusGeopointList != null && locusGeopointList.size() != 0)
			locusGeopointList.clear();
		if (stationInfos != null && stationInfos.size() != 0)
			stationInfos.clear();
		if (lineInfo != null)
			lineInfo = null;
		if (historymsgs != null && historymsgs.size() != 0)
			historymsgs.clear();

		mMapView.updateViewLayout(mCarIconView, new MapView.LayoutParams(0, 0,
				null, MapView.LayoutParams.BOTTOM_CENTER));
		mCarIconView.setVisibility(View.GONE);
		mMapView.updateViewLayout(curStateTipPopView, new MapView.LayoutParams(
				0, 0, null, MapView.LayoutParams.BOTTOM_CENTER));
		curStateTipPopView.setVisibility(View.GONE);

	}

	/**
	 * 估算值更新触发方法
	 * 
	 * @param predictText2
	 */
	protected void onPredictTextUpdated(String text) {
		// 判断当前汽车上的冒泡是否显示，如果显示，则刷新泡泡上的提示文本
		if (StringUtil.isNull(predictText))
			return;
		if (curStateTipPopView == null)
			return;
		if (curStateTipPopView.getVisibility() == View.VISIBLE) {
			tvPredict.setText(Html.fromHtml(predictText));
			//tvPredict.setText(predictText);
		}
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
			int pxoffset = DensityUtil.dip2px(MainMapActivity.this, 200.0f);
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
	 * 切换小孩后，需要更新汽车图标背景，小孩头像
	 */
	private void updateCarBg_ChildHeader() {
		if (currentChild == null)
			return;
		try {
			if (curStateTipPopView != null) {
				ImageView iv_cariconbg = (ImageView) mCarIconView
						.findViewById(R.id.iv_cariconbg);

				ChildCustomTheme theme = ChildCustomConfigs.getInstance()
						.getChildCustomThemeByKey(currentChild.getCld_color());
				iv_cariconbg.setImageResource(theme.getMapCarBgResId());

				CircularImage header = (CircularImage) mCarIconView
						.findViewById(R.id.ci_userheader);
				if (currentChild.getCld_sex()
						.equals(StudentInfoBean.SEX_FEMAIL)) {
					header.setImageResource(R.drawable.default_gril);
				} else {
					header.setImageResource(R.drawable.default_boy);
				}
				if (StringUtils.isNotBlank(currentChild.getCld_photo()))
					ResourcesUtils.startGetImg(getApplicationContext(), header,
							currentChild.getCld_photo());

			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}

	/**
	 * 更新汽车实时位置，汽车冒泡提示信息。
	 */
	protected void updateCarInMap() {
		if (mCarIconView == null)
			return;

		if (this.carRealTimeInfos == null || this.carRealTimeInfos.size() <= 0)
			return;
		VehicleRealtimeInfoBean vin = this.carRealTimeInfos.get(0);
		double lat = Double.parseDouble(vin.getGps_lat());
		double lon = Double.parseDouble(vin.getGps_lon());
		GeoPoint geoPoint = MyOverItem.getGeoPoint(lat, lon);
		mMapView.updateViewLayout(mCarIconView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, geoPoint,
				0, offset_car_y, MapView.LayoutParams.BOTTOM_CENTER));
		mCarIconView.setVisibility(View.VISIBLE);

		ImageView caricon = (ImageView) mCarIconView.findViewById(R.id.caricon);
		int cariconresid = getCarIconRes(vin.getStatus());
		// 设置角度
		caricon.setImageBitmap(scaleBitmap(vin.getDirection(), cariconresid));
		// 设置popview
		if (curStateTipPopView.getVisibility() == View.VISIBLE) {

			mMapView.updateViewLayout(
					curStateTipPopView,
					new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, geoPoint, 0,
							offset_carpop_y, MapView.LayoutParams.BOTTOM_CENTER));

			LayoutParams l = tvPredict.getLayoutParams();
			int pxoffset = DensityUtil.dip2px(MainMapActivity.this, 50.0f);
			l.width = mMapView.getWidth() - pxoffset;
			tvPredict.setLayoutParams(l);
		}
		// if (!checkPointInMap(geoPoint))
		// mMapView.getController().setCenter(geoPoint);

	}

	private int getCarIconRes(String status) {
		
		int resid = R.drawable.car_online;
		//TODO:此处先直接返回在线。
		return resid;
		
//		if (StringUtil.equals(VehicleRealtimeInfoBean.STATUS_DRIVING, status)
//				|| StringUtil.equals(VehicleRealtimeInfoBean.STATUS_FIRE,
//						status)
//				|| StringUtil.equals(VehicleRealtimeInfoBean.STATUS_ONLINE,
//						status))
//			resid = R.drawable.car_online;
//		else
//			resid = R.drawable.car_offline;
//		return resid;
	}

	private boolean checkPointInMap(GeoPoint geoPoint) {
		Point p = MyOverItem.getPointPixels(mMapView, geoPoint);

		// int top = mMapView.getTop();
		// int bottom = mMapView.getBottom();
		// int left = mMapView.getLeft();
		// int right = mMapView.getRight();
		int width = mMapView.getWidth();
		int height = mMapView.getHeight();
		if (p.x < 0 || p.y < 0 || p.x > width || p.y > height)
			return false;
		return true;
	}

	/**
	 * 定义车辆运行图标视图
	 */
	private void initCarIconView() {
		mCarIconView = super.getLayoutInflater().inflate(
				R.layout.map_marker_car, null);
		mMapView.addView(mCarIconView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.TOP_LEFT));
		CircularImage header = (CircularImage) mCarIconView
				.findViewById(R.id.ci_userheader);
		header.setImageResource(R.drawable.default_boy);
		LayoutParams l = header.getLayoutParams();
		l.width = DensityUtil.dip2px(MainMapActivity.this, 30.0f);
		l.height = DensityUtil.dip2px(MainMapActivity.this, 30.0f);
		header.setLayoutParams(l);

		mCarIconView.setVisibility(View.GONE);

		RelativeLayout carRL = (RelativeLayout) mCarIconView
				.findViewById(R.id.carRL);
		carRL.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				TogglePredictPopView();
			}
		});

	}

	protected void TogglePredictPopView() {
		boolean visible = curStateTipPopView.getVisibility() == View.VISIBLE;
		if (visible) {
			curStateTipPopView.setVisibility(View.INVISIBLE);
		} else {

			VehicleRealtimeInfoBean vin = this.carRealTimeInfos.get(0);
			double lat = Double.parseDouble(vin.getGps_lat());
			double lon = Double.parseDouble(vin.getGps_lon());
			GeoPoint geoPoint = MyOverItem.getGeoPoint(lat, lon);

			mMapView.updateViewLayout(
					curStateTipPopView,
					new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, geoPoint, 0,
							offset_carpop_y, MapView.LayoutParams.BOTTOM_CENTER));
			curStateTipPopView.setVisibility(View.VISIBLE);
			if (!poptip) {
				Toast.makeText(this, "再次点击图标，可隐藏弹出提示。", Toast.LENGTH_SHORT)
						.show();
				poptip = true;

			}
		}
	}

	/**
	 * 初始化当前状态泡泡
	 */
	private void initCurStatePop() {
		offset_carpop_y = DensityUtil.dip2px(this, -50);
		curStateTipPopView = super.getLayoutInflater().inflate(
				R.layout.map_pop_curstate, null);
		mMapView.addView(curStateTipPopView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.TOP_LEFT));
		curStateTipPopView.setVisibility(View.GONE);
		tvPredict = (TextView) curStateTipPopView.findViewById(R.id.tv_remind);
		
		tvPredict.setText(Html.fromHtml(predictText));
		//tvPredict.setText(predictText);
	}

	/**
	 * 隐藏汽车图标，停止定时器
	 */
	private void hideCar_Pop() {
		stopTimer();
		if (curStateTipPopView != null)
			curStateTipPopView.setVisibility(View.INVISIBLE);
		if (mCarIconView != null)
			mCarIconView.setVisibility(View.INVISIBLE);
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

	/**
	 * 初始化定义OverlayItem 悬浮内容
	 * 
	 * @return
	 */
	protected MyOverItem initMyOverlayItem() {
		final int roadwidth = DensityUtil.dip2px(getContext(), 2);
		return new MyOverItem(getContext(), locusGeopointList) {
			@Override
			public void draw(Canvas canvas, MapView mapview, boolean shadow) {
				if (locusGeopointList.size() != 0) {
					Paint paint = new Paint();

					int color = ChildCustomConfigs
							.getInstance()
							.getChildCustomThemeByKey(
									currentChild.getCld_color())
							.getColorvalue();

					paint.setColor(color);
					paint.setDither(true);
					paint.setStyle(Paint.Style.STROKE);
					paint.setStrokeJoin(Paint.Join.ROUND);
					paint.setStrokeCap(Paint.Cap.ROUND);
					paint.setStrokeWidth(roadwidth);

					paint.setPathEffect(new PathEffect());
					defaultDraw(canvas, mapview, shadow, paint);
				}
			}
		};
	}

	/**
	 * 设置车辆图标方向
	 * 
	 * @param degree
	 * @return
	 */
	private Bitmap scaleBitmap(String degree, int resid) {
		Bitmap obmp = BitmapFactory.decodeResource(getResources(), resid);
		int width = obmp.getWidth();
		int height = obmp.getHeight();
		Matrix matrix = new Matrix();
		matrix.postRotate(Float.parseFloat(degree));
		Bitmap bm = Bitmap
				.createBitmap(obmp, 0, 0, width, height, matrix, true);
		return bm;
	}

	/**
	 * 根据学生服务状态判断线路类型
	 * 
	 * @param state
	 * @return
	 */
	private String getLineTypeByStatus(String state) {
		if (state == null)
			return LineInfoBean.LINE_TYPE_UP;
		if (state.equals(StudentStateConstant.SRV_UNKNOWN)
				|| state.equals(StudentStateConstant.SRV_SCHOOL_BEFORE)
				|| state.equals(StudentStateConstant.SRV_SCHOOL_AT_BUS))
			return LineInfoBean.LINE_TYPE_UP;
		else
			return LineInfoBean.LINE_TYPE_DOWN;

	}

	// 这个类比较结果不一定正确。
	public class SortByTime implements Comparator {
		public int compare(Object o1, Object o2) {
			MsgRecordBean s1 = (MsgRecordBean) o1;
			MsgRecordBean s2 = (MsgRecordBean) o2;
			if (Tools.strToDate(s1.getMsg_time(), "yyyyMMddHHmmss").getTime() > Tools
					.strToDate(s2.getMsg_time(), "yyyyMMddHHmmss").getTime())
				return 1;
			else if (Tools.strToDate(s1.getMsg_time(), "yyyyMMddHHmmss")
					.getTime() < Tools.strToDate(s2.getMsg_time(),
					"yyyyMMddHHmmss").getTime())
				return -1;
			return 0;
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

	private String buildpredictValue(StationRealTimeInfoBean predict) {
		try {
			if (predict == null)
				return "";
			String v = "";
			if (StationRealTimeInfoBean.REMIND_TYPE_BY_TIME.equals(predict
					.getRemind_type())) {
				v = predict.getRemind_value() + "分钟";

			} else if (StationRealTimeInfoBean.REMIND_TYPE_BY_DISTANT
					.equals(predict.getRemind_type())) {
				float d = Float.parseFloat(predict.getRemind_value());
				d = d / 1000;
				v = String.format("%.1f 公里", d);
			} else {
				v = predict.getRemind_value() + "个站";
			}
			return v;
		} catch (Exception e) {
			Logger.e(this.getClass(), e.getMessage());
			return "";
		}
	}

	public interface MsgItemClickListener {
		public void onMsgItemClick(MsgRecordBean msg);

		public void onTap(GeoPoint p, MapView mapview);
	}
}

/**
 * 历史消息自定义覆盖物
 * 
 * @author zhangyongn
 * 
 */
class MsgOverItemT extends ItemizedOverlay<OverlayItem> {
	public List<MsgRecordBean> msgs = new ArrayList<MsgRecordBean>();

	private MainMapActivity.MsgItemClickListener listener;

	public MsgOverItemT(MainMapActivity.MsgItemClickListener listener,
			List<MsgRecordBean> msgs) {
		super(boundCenterBottom(YtApplication.getInstance().getResources()
				.getDrawable(R.drawable.map_black_point)));

		this.listener = listener;

		this.msgs = msgs;
		populate(); // createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return msgs.size();
	}

	public void updateOverlay() {
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		try {
			MsgRecordBean msg = msgs.get(i);
			// 直接转换为百度地图坐标
			NeuGeoPoint bdGeoPoint = LocationUtils.fromWgs84ToBaidu(
					msg.getContent(MsgRecordBean.KEY_GPS_LON),
					msg.getContent(MsgRecordBean.KEY_GPS_LAT));

			double lat = bdGeoPoint.getY();
			double lon = bdGeoPoint.getX();
			GeoPoint p = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
			String time = Tools.getFormatTime(msg.getMsg_time(),
					"yyyyMMddHHmmss", "HH:mm");
			OverlayItem item = new OverlayItem(p, time, time);
			Drawable marker = YtApplication.getInstance().getResources()
					.getDrawable(R.drawable.map_message_bg);
			
			boundCenterBottom(marker);

			item.setMarker(marker);
			return item;
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
		return null;
	}

	@Override
	// 处理当点击事件
	protected boolean onTap(int i) {
		if (listener != null)
			listener.onMsgItemClick(msgs.get(i));
		return true;
	}

	@Override
	public boolean onTap(GeoPoint p, MapView mapview) {
		if (listener != null)
			listener.onTap(p, mapview);
		return super.onTap(p, mapview);
	}
}

/**
 * 站点信息自定义覆盖物
 * 
 * @author zhangyongn
 * 
 */
class StationOverItemT extends ItemizedOverlay<OverlayItem> {
	public List<StationInfoBean> stations = new ArrayList<StationInfoBean>();
	public String line_type = LineInfoBean.LINE_TYPE_UP;
	public StudentInfoBean child = null;

	public StationOverItemT(List<StationInfoBean> stations, String line_type,
			StudentInfoBean child) {
		super(boundCenterBottom(YtApplication.getInstance().getResources()
				.getDrawable(R.drawable.map_black_point)));
		this.line_type = line_type;
		this.stations = stations;
		this.child = child;
		populate(); // createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}

	@Override
	public int size() {

		return stations.size();
	}

	public void updateOverlay() {
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		try {
			StationInfoBean s = stations.get(i);
			double lat = Double.parseDouble(s.getGps_lat());
			double lon = Double.parseDouble(s.getGps_lon());
			GeoPoint p = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
			String title = s.getStation_name();
			OverlayItem item = new OverlayItem(p, title, title);

			Drawable marker = getMarker(s);
			boundCenterBottom(marker);
			item.setMarker(marker);
			return item;
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
		return null;
	}

	private Drawable getMarker(StationInfoBean s) {
		try {
			if (s == null)
				return null;
			ChildCustomTheme theme = ChildCustomConfigs.getInstance()
					.getChildCustomThemeByKey(child.getCld_color());
			int resId = -1;

			if (StringUtil.equals(line_type, LineInfoBean.LINE_TYPE_UP)) {
				if (StringUtil.equals(s.getStation_type(),
						StationInfoBean.STATION_TYPE_UP)) {

					resId = theme.getHomeResId();
				} else if (StringUtil.equals(s.getStation_type(),
						StationInfoBean.STATION_TYPE_DOWN)) {
					resId = theme.getSchoolResId();
				}

			} else {
				if (StringUtil.equals(s.getStation_type(),
						StationInfoBean.STATION_TYPE_DOWN)) {

					resId = theme.getHomeResId();
				} else if (StringUtil.equals(s.getStation_type(),
						StationInfoBean.STATION_TYPE_UP)) {
					resId = theme.getSchoolResId();
				}
			}
			return YtApplication.getInstance().getResources()
					.getDrawable(resId);
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
			return null;
		}
	}
}
