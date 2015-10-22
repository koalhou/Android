package com.yutong.axxc.parents.view.settings.child;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.beans.BizMapCtrlBean;
import com.yutong.axxc.parents.business.beans.BizOverlayItemBean;
import com.yutong.axxc.parents.business.line.GetStationInfoBiz;
import com.yutong.axxc.parents.business.line.GetStationRemindInfoBiz;
import com.yutong.axxc.parents.business.line.GetStudentLineInfoBiz;
import com.yutong.axxc.parents.business.line.SetStationRemindInfoBiz;
import com.yutong.axxc.parents.business.student.GetStudentCustomInfoBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.beans.LineInfoBean;
import com.yutong.axxc.parents.common.beans.LinePointInfoBean;
import com.yutong.axxc.parents.common.beans.StationInfoBean;
import com.yutong.axxc.parents.common.beans.StationRemindInfoBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ChildCustomConfigs;
import com.yutong.axxc.parents.view.common.ChildCustomConfigs.ChildCustomTheme;
import com.yutong.axxc.parents.view.common.ActivityManager;
import com.yutong.axxc.parents.view.common.CommonCheckBox;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.home.EnhancedMapView;
import com.yutong.axxc.parents.view.home.EnhancedMapView.OnZoomChangeListener;
import com.yutong.axxc.parents.view.home.MyOverItem;
import com.yutong.axxc.parents.view.home.MyOverItemT;
import com.yutong.axxc.parents.view.util.DensityUtil;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 * 
 */
public class RemindSetActivity extends MapActivity implements OnClickListener
{
	/**
	 * 线路类型 "0" 上行 "1" 下行
	 */
	private String LINE_TYPE = "0";
	// 小孩儿id
	private String id = "";
	/**
	 * 站点类型
	 */
	private String STATION_TYPE = STATION_TYPE_NOMAL;
	// 普通
	private static final String STATION_TYPE_NOMAL = "0";
	// 上车站点
	private static final String STATION_TYPE_UP = "1";
	// 下车站点
	private static final String STATION_TYPE_DOWN = "2";
	/*****************************************/
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;
	// 封装学生信息
	private StudentInfoBean studentInfo;

	// 获取学生个性信息任务
	private GetStudentCustomInfoBiz scustomTask;
	private YtApplication app;
	private EnhancedMapView mMapView;
	private boolean viewEnanbled;
	/**
	 * 地图点位信息
	 */
	private ArrayList<BizOverlayItemBean> locusGeopointList;
	// 起点
	private View mStartPopView;
	// 终点
	private View mEndPopView;
	private View mStartIconView;
	private View mEndIconView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_child_remindset);
		WeakReference<Activity> reference = new WeakReference<Activity>(this);
		ActivityManager.addActivity(reference);
		initViews();
		initMapEnginer();
		initListener();
		// 加载小孩儿信息
		loadChildInfo();
		/*************************************/
		// 经纬度信息集合
		locusGeopointList = new ArrayList<BizOverlayItemBean>();
		stationList = new ArrayList<GeoPoint>();
		// 初始化泡泡显示控件
		initPop();
		// 加载车辆地图轨迹
		loadBusTraces();

		// 用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
		UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0017);

	}

	/**
	 * 加载站点提醒信息
	 */
	private void loadStationRemindInfo()
	{
		showReflushPop();
		new GetStationRemindInfoBiz(getContext(), remindHandler, id, LINE_TYPE)
				.execute();
	}

	private void initPop()
	{
		// 起点泡泡
		initStartPop();
		// 终点泡泡
		initEndPop();

	}

	private void initStartPop()
	{
		// 修改起始点和终点样式
		mStartPopView = RemindSetActivity.this.getLayoutInflater().inflate(
				R.layout.pop_start, null);
		mStartIconView = RemindSetActivity.this.getLayoutInflater().inflate(
				R.layout.pop_start_icon, null);
		mMapView.addView(mStartPopView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.BOTTOM_CENTER));
		mMapView.addView(mStartIconView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.BOTTOM_CENTER));
		mStartPopView.setVisibility(View.GONE);
	}

	private void initEndPop()
	{
		mEndPopView = RemindSetActivity.this.getLayoutInflater().inflate(
				R.layout.pop_end, null);
		mMapView.addView(mEndPopView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.BOTTOM_CENTER));
		mEndPopView.setVisibility(View.GONE);

		mEndIconView = RemindSetActivity.this.getLayoutInflater().inflate(
				R.layout.pop_end_icon, null);
		ImageView end_icon = (ImageView) mEndIconView
				.findViewById(R.id.end_icon);
		end_icon.setImageResource(theme.getSchoolResId());
		mMapView.addView(mEndIconView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.BOTTOM_CENTER));
		mEndIconView.setVisibility(View.GONE);
	}

	/**
	 * 加载车辆轨迹数据
	 */
	private void loadBusTraces()
	{
		showReflushPop();
		GetStudentLineInfoBiz infoBiz = new GetStudentLineInfoBiz(getContext(),
				traceInfoHandler, id, LINE_TYPE,
				ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE);
		infoBiz.execute();
	}

	/**
	 * 关闭刷新遮罩
	 */
	protected void dismissReflushPop()
	{
		if (converRl.getVisibility() == View.VISIBLE)
		{
			converRl.setVisibility(View.GONE);
		}
	}

	/**
	 * 显示刷新遮罩
	 */
	protected void showReflushPop()
	{
		if (converRl.getVisibility() != View.VISIBLE)
		{
			converRl.setVisibility(View.VISIBLE);
		}
	}

	// 新的站点信息
	private StationRemindInfoBean newRemind;
	/**
	 * 记录设置过的站点
	 */
	private StationRemindInfoBean currentRemind;
	private Handler remindHandler = new YtHandler()
	{

		public void handleMessage(Message msg)
		{
			super.handleMessage(msg, RemindSetActivity.this);
			if (!getContext().isFinishing())
			{
				clearCache();
				dismissReflushPop();
				switch (msg.what)
				{
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:
					Logger.d(RemindSetActivity.class, "访问学生提醒站点设置信息信息返回数据："
							+ msg.obj);
					remindInfos = (List<StationRemindInfoBean>) msg.obj;
					if (remindInfos != null && remindInfos.size() > 0)
					{
						for (StationRemindInfoBean bean : remindInfos)
						{
							for (StationInfoBean item : stations)
							{

								if (bean.getStation_id() == null)
									continue;
								if (bean.getStation_id().equals(
										item.getStation_id()))
								{
									initEditStationPop(item, bean);
									break;
								}
							}
						}
					}
					else
					{
						// 显示所有的灰色站点
						StationRemindInfoBean bean = null;
						for (StationInfoBean item : stations)
						{
							if (LINE_TYPE == "0")
							{// 上行
								if (STATION_TYPE_UP.equals(item
										.getStation_type()))
								{
									bean = new StationRemindInfoBean();
									bean.setStation_id(item.getStation_id());
									bean.setRemind_alias(item.getStation_name());
									bean.setRemind_type("1");
									bean.setRemind_value("5");
									bean.setValid("0");
									initEditStationPop(item, bean);
								}
							}
							else
							{
								// 下行
								if (STATION_TYPE_DOWN.equals(item
										.getStation_type()))
								{
									bean = new StationRemindInfoBean();
									bean.setStation_id(item.getStation_id());
									bean.setRemind_alias(item.getStation_name());
									bean.setRemind_type("1");
									bean.setRemind_value("5");
									bean.setValid("0");
									initEditStationPop(item, bean);
								}
							}
						}
					}
					// if (isFromToggleButton)
					// {
					// remindPop.dismiss();
					// currentStationPop.performClick();
					// isFromToggleButton = false;
					// }
					break;
				case ThreadCommStateCode.COMMON_FAILED:
					doException();
					break;
				default:
					break;
				}
			}
		}
	};

	// add by lizyi
	/**
	 * 根据位置坐标计算地图控制信息
	 * 
	 * @return Returns the bizMapCtrl.
	 */
	private BizMapCtrlBean getBizMapCtrl(List<LinePointInfoBean> list)
	{
		if (list == null || list.size() == 0)
			return new BizMapCtrlBean();

		double[] latitudes = new double[list.size()];
		double[] longitudes = new double[list.size()];
		int index = 0;
		for (LinePointInfoBean bean : list)
		{
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

	private void clearCache()
	{
		for (ViewGroup layout : popList)
		{
			layout.findViewById(R.id.iv_home).setBackgroundDrawable(null);
			layout.setBackgroundDrawable(null);
			layout.destroyDrawingCache();
			layout.removeAllViews();
		}
		popList.clear();
	}

	/**
	 * 轨迹信息
	 */
	private LineInfoBean infoBean;
	// 路线处理
	private Handler traceInfoHandler = new YtHandler()
	{

		public void handleMessage(Message msg)
		{
			super.handleMessage(msg, RemindSetActivity.this);
			if (!getContext().isFinishing())
			{
				clearMaps();
				// dismissReflushPop();
				switch (msg.what)
				{
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:
					Logger.d(RemindSetActivity.class, "访问学生车辆轨迹信息返回数据："
							+ msg.what);
					infoBean = (LineInfoBean) msg.obj;
					if (infoBean == null)
					{
						Toast.makeText(getContext(), "该学生还没有对应的车辆信息", 0).show();
						return;
					}

					List<LinePointInfoBean> list = infoBean.getPointInfos();
					BizOverlayItemBean overItem;
					LinePointInfoBean bean;
					int marker = 0;
					for (int position = 0; position < list.size(); position++)
					{
						marker = R.drawable.map_black_point;
						bean = list.get(position);

						overItem = MyOverItem.getOverlayItems(
								Double.parseDouble(bean.getGps_lat()),
								Double.parseDouble(bean.getGps_lon()), marker,
								StringUtil.parseStr(position),
								StringUtil.parseStr(position));
						// 设置覆盖物的点位信息
						overItem.setLocusItem(bean);
						// 存储覆盖物
						locusGeopointList.add(overItem);
					}

					if (locusGeopointList.size() != 0)
					{
						// 生成车具体位置的悬浮框
						MyOverItem traceOverlay = initMyOverlayItem();
						// 这个方法有什么用？
						mMapView.getOverlays().add(traceOverlay);
					}
					// add by lizyi 设置中心点
					BizMapCtrlBean mapCtr = getBizMapCtrl(list);
					mMapView.getController().setCenter(
							MyOverItem.getGeoPoint(mapCtr.getcLatitude(),
									mapCtr.getcLongitude()));
					loadBusStations();
					break;
				case ThreadCommStateCode.COMMON_FAILED:
					dismissReflushPop();
					Logger.d(RemindSetActivity.class, "访问学生车辆轨迹信息出错：" + msg.obj);
					Toast.makeText(getContext(), "加载车辆轨迹信息失败" + msg.obj, 0)
							.show();
					break;
				default:
					break;
				}
			}
		};
	};
	// 编辑站点
	private PopupWindow editPop;
	// 距离提醒
	private TextView tv_distance;
	// 完成
	private TextView tv_set_ok;
	// 取消提醒
	private TextView tv_set_cancle;

	/**
	 * 初始化可编辑站点（支持多个么？如果支持多个的话，可编辑站点就要用覆盖物画）
	 * 
	 * @param item
	 * @param bean
	 */
	private void initEditStationPop(StationInfoBean item,
			StationRemindInfoBean bean)
	{
		ViewGroup mRemindPop = (ViewGroup) super.getLayoutInflater().inflate(
				R.layout.pop_home, null);
		popList.add(mRemindPop);
		ImageView iv_home = (ImageView) mRemindPop.findViewById(R.id.iv_home);
		// 切换主题
		if (!isReminded(bean))
		{
			iv_home.setImageResource(R.drawable.home_gray);
		}
		else
		{
			iv_home.setImageResource(theme.getHomeResId());
		}
		mRemindPop.setVisibility(View.VISIBLE);
		GeoPoint gepoint = makeGepoint(item);
		mMapView.addView(mRemindPop, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, gepoint,
				MapView.LayoutParams.BOTTOM
						| MapView.LayoutParams.CENTER_HORIZONTAL));
		mRemindPop.setOnClickListener(new HomeClickListener(gepoint, iv_home,
				bean, item));
		initEditPop(mRemindPop);
	}

	/**
	 * 点位转换
	 * 
	 * @param item
	 */
	private GeoPoint makeGepoint(StationInfoBean item)
	{
		return MyOverItem.getGeoPoint(Double.parseDouble(item.getGps_lat()),
				Double.parseDouble(item.getGps_lon()));
	}

	protected String checkStationType()
	{
		if (LINE_TYPE == "0")
		{
			STATION_TYPE = STATION_TYPE_UP;
		}
		else
		{
			STATION_TYPE = STATION_TYPE_DOWN;
		}
		return STATION_TYPE;
	}

	/**
	 * 当前被点击的view对象
	 */
	private View currentStationPop;
	// 站点点击时间限制
	private static long CLICK_INTERVAL = 800;
	// 记录上次点击时间
	private long lastTimemGoToMainMenuListener;

	/**
	 * 点击事件处理
	 */
	private class HomeClickListener implements OnClickListener
	{
		private GeoPoint gepoint;
		private ImageView iv_home;
		private StationRemindInfoBean bean;
		private StationInfoBean item;

		public HomeClickListener(GeoPoint gepoint, ImageView iv_home,
				StationRemindInfoBean bean, StationInfoBean item)
		{
			this.gepoint = gepoint;
			this.iv_home = iv_home;
			this.bean = bean;
			this.item = item;
		}

		@Override
		public void onClick(View v)
		{
			// 防止频繁点击,状态不是最新状态出错
			long time = System.currentTimeMillis();
			if (time - lastTimemGoToMainMenuListener < CLICK_INTERVAL)
			{
				return;
			}
			// 有必要记录到站点信息之前的状态，防止访问服务器出错后的逻辑错误
			lastTimemGoToMainMenuListener = time;
			currentStationPop = v;
			newRemind = this.bean;
			// TODO Auto-generated method stub
			showEditPop(v, this.iv_home, this.bean);
			if (this.gepoint != null)
			{
				// 定位到可乘坐站点

				mMapView.getController().setCenter(makeGepoint(this.item));
			}
		}
	}

	/**
	 * 距离点击事件处理
	 */
	private class SelectDistanceListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			showDialog();
		}

	}

	/**
	 * 设置完成事件处理
	 */
	private class SaveSetupListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			boolean isSetupChanged = false;
			newStationName = et_station_name.getText().toString().trim();
			newDistance = tv_distance.getText().toString().trim();
			// 如果不为空而且发现名字被修改了
			if (!StringUtil.isNull(newStationName)
					&& !StringUtil.isNull(newDistance))
			{
				// 修改距离
				if (newRemind != null)
				{
					// 检查站点别名以及提醒距离是否改变
					isSetupChanged = !newRemind.getRemind_alias().equals(
							newStationName)
							|| !(newRemind.getRemind_value().charAt(0) + "")
									.equals(newDistance.charAt(2) + "");
				}
			}
			else
			{
				Toast.makeText(getContext(), "站点提醒信息不能为空", 0).show();
				return;
			}
			// add by lizyi 用户体验不好，不知道到底修改了没
			isSetupChanged = true;
			if (isSetupChanged)
			{
				updownLoadSetting();
				editPop.dismiss();
			}
			else
			{
				Toast.makeText(getContext(), "站点提醒信息未修改", 0).show();
			}

		}

	}

	/**
	 * 取消提醒事件处理
	 */
	private class CancleSetupListener implements OnClickListener
	{

		public CancleSetupListener()
		{
		}

		@Override
		public void onClick(View v)
		{
			editPop.dismiss();
			newRemind.setValid("0");
			new SetStationRemindInfoBiz(getContext(), setUploadHandler, id,
					newRemind.getStation_id(), newRemind).execute();
		}

	}

	private boolean isChanged = false;
	// 是否从开关打开
	private boolean isFromToggleButton = false;

	/**
	 * 显示消息提醒开关
	 * 
	 * @param v
	 * @param bean
	 * @param iv_home2
	 */
	public void showRemindToggleButton(View v,
			final StationRemindInfoBean bean, final ImageView iv_home)
	{
		View layout = super.getLayoutInflater().inflate(
				R.layout.pop_remind_toggle, null);
		layout.setBackgroundColor(getContext().getResources().getColor(
				R.color.body_bg));
		/**
		 * 控件初始化
		 */
		final CommonCheckBox cb_remind_toggle = (CommonCheckBox) layout
				.findViewById(R.id.remind_toggle);
		cb_remind_toggle.setChecked(false);
		remindPop = new PopupWindow(layout,
				WindowManager.LayoutParams.FILL_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT);
		remindPop.setBackgroundDrawable(new ColorDrawable(00000000));
		remindPop.setFocusable(true);// menu菜单获得焦点 如果没有获得焦点menu菜单中的控件事件无法响应
		remindPop.setOutsideTouchable(true);
		remindPop.setOnDismissListener(new OnDismissListener()
		{

			@Override
			public void onDismiss()
			{
				boolean checked = cb_remind_toggle.isChecked();
				if (!checked)
				{
					iv_home.setImageResource(R.drawable.home_gray);
				}
				else
				{
					iv_home.setImageResource(theme.getHomeResId());
				}
			}
		});
		cb_remind_toggle
				.setOnCheckedChangeListener(new OnCheckedChangeListener()
				{

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked)
					{

						if (isChecked)
						{

							bean.setValid("1");
							isFromToggleButton = true;
						}
						else
						{
							bean.setValid("0");
						}
						new SetStationRemindInfoBiz(getContext(),
								setUploadHandler, id, bean.getStation_id(),
								bean).execute();
					}
				});
		remindPop.showAtLocation(v, Gravity.BOTTOM, 0, 0);
	}

	/**
	 * 判断站点提醒是否打开
	 * 
	 * @param bean
	 * 
	 * @return
	 */
	private boolean isReminded(StationRemindInfoBean bean)
	{
		if (bean != null && "1".equals(bean.getValid()))
		{
			return true;
		}
		return false;
	}

	/**
	 * 将设置更新同步到网络
	 * 
	 * @param bean
	 */
	public void updownLoadSetting()
	{
		if (newRemind == null)
		{
			Toast.makeText(getContext(), "上传失败", 0).show();
			return;
			// 提醒消息的id如何处理？
		}
		newRemind.setRemind_alias(newStationName);
		newRemind.setRemind_value(Integer.parseInt(newDistance.charAt(2) + "")
				* 1000 + "");
		newRemind.setRemind_type("1");
		newRemind.setValid("1");
		new SetStationRemindInfoBiz(getContext(), setUploadHandler, id,
				newRemind.getStation_id(), newRemind).execute();

	}

	/**
	 * 设置信息上传
	 */
	private Handler setUploadHandler = new YtHandler()
	{

		public void handleMessage(Message msg)
		{
			super.handleMessage(msg, RemindSetActivity.this);
			if (!getContext().isFinishing())
			{
				dismissReflushPop();
				switch (msg.what)
				{
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:
					Logger.d(RemindSetActivity.class, "访问学生设置站点信息返回数据："
							+ msg.what);
					Toast.makeText(getContext(), "乘车站点修改成功", 0).show();
					// 在这儿加入开启和关闭消息提醒的代码
					loadStationRemindInfo();
					// 提醒 站点可以设置几个？
					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Toast.makeText(getContext(), msg.obj + "", 0).show();
					break;
				default:
					break;
				}
			}
		}

	};

	/**
	 * 显示编辑页面
	 * 
	 * @param bean
	 */
	private void initEditPop(View v)
	{
		editLayout = super.getLayoutInflater().inflate(R.layout.pop_edit, null);
		/**
		 * 控件初始化
		 */
		et_station_name = (EditText) editLayout
				.findViewById(R.id.et_station_name);
		RelativeLayout iv_select_distance = (RelativeLayout) editLayout
				.findViewById(R.id.rl_select_distance);
		tv_distance = (TextView) editLayout.findViewById(R.id.tv_distance);
		tv_set_ok = (TextView) editLayout.findViewById(R.id.tv_set_ok);
		editLayout.setBackgroundColor(getResources().getColor(R.color.body_bg));
		tv_set_cancle = (TextView) editLayout.findViewById(R.id.tv_set_cancle);
		/**
		 * 设置控件的监听
		 */
		tv_set_ok.setOnClickListener(new SaveSetupListener());
		tv_set_cancle.setOnClickListener(new CancleSetupListener());
		iv_select_distance.setOnClickListener(new SelectDistanceListener());
		editPop = new PopupWindow(editLayout,
				WindowManager.LayoutParams.FILL_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT);
		// 设置软键盘弹出方式
		editPop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		editPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		// 背景颜色替换
		editPop.setBackgroundDrawable(new ColorDrawable(00000000));
		editPop.setFocusable(true);// menu菜单获得焦点 如果没有获得焦点menu菜单中的控件事件无法响应
		editPop.setOutsideTouchable(true);
		// 设置位置
		initDialog();
	}

	/**
	 * 显示编辑页面
	 * 
	 * @param bean
	 * @param bean2
	 * @param iv_home2
	 */
	private void showEditPop(View v, final ImageView iv_home,
			final StationRemindInfoBean bean)
	{
		iv_home.setImageResource(theme.getEditResId());
		editPop.setOnDismissListener(new OnDismissListener()
		{

			@Override
			public void onDismiss()
			{
				if (!isReminded(bean))
				{
					iv_home.setImageResource(R.drawable.home_gray);
					return;
				}
				iv_home.setImageResource(theme.getHomeResId());
			}
		});
		// 不为空而且有效
		if (isReminded(bean))
		{
			if (bean != null)
			{
				// 修改过的站点名称
				et_station_name.setText(bean.getRemind_alias());
				tv_distance.setText("提前" + bean.getRemind_value().charAt(0)
						+ "公里提醒");
			}
			else
			{
				// 默认站点名称
				et_station_name.setText("请输入站点名称");
				tv_distance.setText("提前5公里提醒");
			}
		}
		else
		{
			showRemindToggleButton(v, bean, iv_home);
			return;
		}
		editPop.showAtLocation(v, Gravity.BOTTOM, 0, 0);
	}

	/**
	 * 地图站点信息
	 */
	private ArrayList<GeoPoint> stationList;

	/**
	 * 初始化定义OverlayItem 悬浮内容
	 * 
	 * @return
	 */
	protected MyOverItem initMyOverlayItem()
	{
		final int roadwidth = DensityUtil.dip2px(getContext(), 2);
		return new MyOverItem(getContext(), locusGeopointList)
		{
			@Override
			public void draw(Canvas canvas, MapView mapview, boolean shadow)
			{
				if (locusGeopointList.size() != 0)
				{
					Paint paint = new Paint();
					if (theme == null)
					{
						// 颜色没有定义
						paint.setColor(Color.GREEN);
					}
					else
					{
						paint.setColor(theme.getColorvalue());
					}
					// 颜色没有定义
					paint.setDither(true);
					paint.setStyle(Paint.Style.STROKE);
					paint.setStrokeJoin(Paint.Join.ROUND);
					paint.setStrokeCap(Paint.Cap.SQUARE);
					paint.setStrokeWidth(roadwidth);

					defaultDraw(canvas, mapview, shadow, paint);
				}
			}
		};
	}

	/**
	 * 清除当前地图内容，同时清除相关全局变量缓存
	 */
	protected void clearMaps()
	{
		Logger.i(getClass(), "overlays size:", mMapView.getOverlays().size());
		if (mMapView != null && mMapView.getOverlays().size() != 0)
		{
			for (int position = 0; position < mMapView.getOverlays().size();)
			{
				mMapView.getOverlays().remove(position);
			}
		}
		if (locusGeopointList != null && locusGeopointList.size() != 0)
			locusGeopointList.clear();
		if (stations != null && stations.size() != 0)
			stations.clear();
		if (stationList != null && stationList.size() != 0)
			stationList.clear();
		if (MyOverItemT.mGeoList != null && MyOverItemT.mGeoList.size() != 0)
			MyOverItemT.mGeoList.clear();

	}

	private List<StationInfoBean> stations;
	// 站点处理
	private Handler stationInfoHandler = new YtHandler()
	{
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg, RemindSetActivity.this);
			if (!getContext().isFinishing())
			{
				// dismissReflushPop();
				MyOverItemT.mGeoList.clear();
				switch (msg.what)
				{
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:
					Logger.d(RemindSetActivity.class, "访问学生站点信息返回数据："
							+ msg.what);

					// 读取站点信息
					stations = (List<StationInfoBean>) msg.obj;
					if (stations == null)
					{
						Toast.makeText(getContext(), "该学生还没有对应的站点信息", 0).show();
						return;
					}
					StationInfoBean bean = null;
					GeoPoint geoPoint = null;
					OverlayItem ot = null;

					for (int position = 0; position < stations.size(); position++)
					{
						bean = stations.get(position);
						// 设置覆盖物的点位信息
						geoPoint = MyOverItem.getGeoPoint(
								Double.parseDouble(bean.getGps_lat()),
								Double.parseDouble(bean.getGps_lon()));
						// 区分站点上下行以及特殊站点
						if (LINE_TYPE == "0")
						{// 上行
							// 起点
							if (position == 0)
							{
								// updateStartPop(geoPoint);
							}
							// 终点
							else if (position == (stations.size() - 1))
							{
								updateEndPop(geoPoint);
							}
						}
						else
						{
							// 起点学校
							if (position == 0)
							{
								updateEndPop(geoPoint);
							}
							// 终点
							else if (position == (stations.size() - 1))
							{
								// updateStartPop(geoPoint);
							}
						}
						// 制作OverlayItem
						ot = new OverlayItem(geoPoint, bean.getStation_name(),
								null);
						MyOverItemT.mGeoList.add(ot);
						// 存储覆盖物
						stationList.add(geoPoint);
					}
					// 缺少一个透明图标
					// Drawable drawable = getResources().getDrawable(
					// R.drawable.map_black_point);
					// MyOverItemT myOverItemT = new MyOverItemT(drawable,
					// getContext(), stationList);
					// mMapView.getOverlays().add(myOverItemT);
					// 加载设置过的乘车点信息
					loadStationRemindInfo();
					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Toast.makeText(getContext(), "获取站点信息失败", 0).show();
					dismissReflushPop();
					return;
				default:
					break;
				}
			}

		}

		/**
		 * 异常处理
		 */
		private void doException()
		{
			dismissReflushPop();
			Toast.makeText(getContext(), "获取学生的站点提醒信息失败", 0).show();
		}

		private void updateEndPop(GeoPoint geoPoint)
		{
			// mEndPopView.setVisibility(View.VISIBLE);
			// mEndPopView.setKeepScreenOn(true);
			// mMapView.updateViewLayout(mEndPopView, new MapView.LayoutParams(
			// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
			// geoPoint, MapView.LayoutParams.BOTTOM
			// | MapView.LayoutParams.CENTER));

			mEndIconView.setVisibility(View.VISIBLE);
			mEndIconView.setKeepScreenOn(true);
			mMapView.updateViewLayout(mEndIconView, new MapView.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					geoPoint, MapView.LayoutParams.BOTTOM
							| MapView.LayoutParams.CENTER));
		}

		private void updateStartPop(GeoPoint geoPoint)
		{
			mStartPopView.setVisibility(View.VISIBLE);
			mStartPopView.setKeepScreenOn(true);
			mMapView.updateViewLayout(mStartPopView, new MapView.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					geoPoint, MapView.LayoutParams.BOTTOM
							| MapView.LayoutParams.CENTER));

			mStartIconView.setVisibility(View.VISIBLE);
			mStartIconView.setKeepScreenOn(true);
			mMapView.updateViewLayout(mStartIconView, new MapView.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					geoPoint, MapView.LayoutParams.BOTTOM
							| MapView.LayoutParams.CENTER));
		}

	};

	/**
	 * 异常处理
	 */
	private void doException()
	{
		dismissReflushPop();
		Toast.makeText(getContext(), "获取学生的站点提醒信息失败", 0).show();
		// newRemind = null;
	}

	// 加载提示
	private com.yutong.axxc.parents.view.common.LoadingOverlay converRl;
	// 站点编辑控件
	private EditText et_station_name;
	// 距离选择对话框
	private AlertDialog.Builder builder;
	// 存储设置项数据
	private String[] items;
	// 记录提醒站点信息
	private List<StationRemindInfoBean> remindInfos;
	// 之前的设置信息
	private StationRemindInfoBean currentRemindInfo;
	/**
	 * 新设置的站点别名以及提醒距离
	 */
	private String newStationName;
	private String newDistance;
	/**
	 * 线路选择
	 */
	private RelativeLayout rl_line_select;
	/**
	 * 线路选择浮动框
	 */
	private PopupWindow linePop;
	protected int currentPostion;

	/**
	 * 更新可编辑站点的位置
	 * 
	 * @param geoPoint
	 */
	private void updateHomePop(StationInfoBean bean)
	{
		// if (mRemindPop == null)
		// {
		// mRemindPop = super.getLayoutInflater().inflate(R.layout.pop_home,
		// null);
		// mMapView.addView(mRemindPop, new MapView.LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
		// MapView.LayoutParams.CENTER));
		// }
		// // 显示提醒站点图标
		// if (mRemindPop.getVisibility() != View.VISIBLE)
		// {
		// mRemindPop.setVisibility(View.VISIBLE);
		// }
		// homePoint = MyOverItem.getGeoPoint(
		// Double.parseDouble(bean.getGps_lat()),
		// Double.parseDouble(bean.getGps_lon()));
		// mMapView.updateViewLayout(mRemindPop, new MapView.LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
		// homePoint, MapView.LayoutParams.BOTTOM_CENTER));
	};

	/**
	 * 加载校车站点数据
	 */
	private void loadBusStations()
	{
		new GetStationInfoBiz(this, stationInfoHandler, id, LINE_TYPE,
				ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE).execute();
	}

	/**
	 * 地图引擎初始化
	 */
	private void initMapEnginer()
	{
		app = (YtApplication) getApplication();
		if (app.mBMapManager == null)
		{
			app.mBMapManager = new BMapManager(app);
			app.mBMapManager.init(YtApplication.strKey,
					new YtApplication.MyGeneralListener());
		}
		// 如果使用地图SDK，请初始化地图Activity
		super.initMapActivity(app.mBMapManager);
		mMapView = (EnhancedMapView) findViewById(R.id.bmapView);

		mMapView.setBuiltInZoomControls(true);
		// 设置在缩放动画过程中也显示overlay,默认为不绘制
		mMapView.setDrawOverlayWhenZooming(true);
		mMapView.setLongClickable(true);

		if (MyOverItemT.lastGepoint != null)
		{
			mMapView.getController().setCenter(MyOverItemT.lastGepoint);
		}
		else
		{
			// 默认的中心点
			mMapView.getController().setCenter(
					MyOverItem.getGeoPoint(YtApplication.getInstance()
							.getcLatitude(), YtApplication.getInstance()
							.getcLongitude()));
		}
		if (MyOverItemT.lastZoom != 0)
		{
			mMapView.getController().setZoom(MyOverItemT.lastZoom);
		}
		else
		{
			mMapView.getController().setZoom(14);
		}
		mMapView.setOnZoomChangeListener(new OnZoomChangeListener()
		{

			@Override
			public void onZoomChange(MapView view, int newZoom, int oldZoom)
			{
				// Logger.i(StudentInfoMapActivity.class, "之前缩放级别:" + oldZoom);
				Logger.i(RemindSetActivity.class, "新的缩放级别:" + newZoom);
				// 大于一定的缩放级别才显示覆盖物的信息
				if (newZoom >= 14)
				{
					// mMapView.setDrawOverlayWhenZooming(true);
					mEndIconView.setVisibility(View.VISIBLE);
				}
				else
				{
					// mMapView.setDrawOverlayWhenZooming(false);
					mEndIconView.setVisibility(View.GONE);
				}
			}
		});
		// mMapView.setOnPanChangeListener(new OnPanChangeListener()
		// {
		//
		// @Override
		// public void onPanChange(MapView view, GeoPoint newCenter,
		// GeoPoint oldCenter)
		// {
		// // Logger.i(StudentInfoMapActivity.class, "之前中心点:" + oldCenter);
		// // Logger.i(StudentInfoMapActivity.class, "新的中心点:" + newCenter);
		//
		// }
		// });
	}

	// 加载小孩儿信息
	private void loadChildInfo()
	{
		Intent intent = getIntent();
		if (intent == null)
			return;
		Bundle bundle = intent.getExtras();
		if (bundle == null)
			return;
		studentInfo = (StudentInfoBean) bundle
				.getSerializable(ActivityCommConstant.STUDENT_INFO);
		if (studentInfo != null)
		{
			tv_top_title.setText(studentInfo.getCld_name() + "的" + lines[0]);

			id = studentInfo.getCld_id();
			// 定位到中国地图或省级地图
		}
		theme = ChildCustomConfigs.getInstance().getChildCustomThemeByKey(
				studentInfo.getCld_color());
		items = new String[] { "提前1公里提醒", "提前2公里提醒", "提前3公里提醒", "提前4公里提醒",
				"提前5公里提醒" };
		popList = new ArrayList<ViewGroup>();
	}

	private void initListener()
	{
		rl_line_select.setOnClickListener(this);
	}

	private void initViews()
	{

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_left.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finish();

			}
		});
		rl_line_select = (RelativeLayout) findViewById(R.id.rl_line_select);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		converRl = (LoadingOverlay) findViewById(R.id.loadingoverlay);
	}

	// @Override
	// public void onBackPressed()
	// {
	// if (app.mBMapManager != null)
	// app.mBMapManager.stop();
	// super.onBackPressed();
	//
	// }

	@Override
	protected void onResume()
	{
		viewEnanbled = true;
		// 后期可能添加当网络不可用时，判断是否有离线地图，如果有，则显示离线地图，否则启动app.mBMapManager
		// int current_offline_num = ContextUtil.getPreference(getContext(),
		// ActivityCommConstant.PREFS_NAME, MODE_PRIVATE,
		// ActivityCommConstant.OFFLINE_NUMBER_KEY, 0);
		// Logger.d(StudentInfoMapActivity.class, "扫描到的离线地图个数resume："
		// + current_offline_num);
		app.mBMapManager.start();
		super.onResume();
	}

	/**
	 * Activity上下文对象
	 * 
	 * @return
	 */
	private RemindSetActivity getContext()
	{
		return this;
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		saveMapStatus();
		if (app.mBMapManager != null)
			app.mBMapManager.stop();
	}

	/**
	 * 记录地图状态
	 */
	private void saveMapStatus()
	{
		// TODO Auto-generated method stub
		MyOverItemT.lastGepoint = mMapView.getMapCenter();
		MyOverItemT.lastZoom = mMapView.getZoomLevel();
		// YtApplication.getInstance().setcLatitude(
		// (double) (lastGpCenter.getLatitudeE6()));
		// YtApplication.getInstance().

	}

	@Override
	protected void onDestroy()
	{
		mMapView.destroyDrawingCache();
		if (app.mBMapManager != null)
		{
			app.mBMapManager.destroy();
			app.mBMapManager = null;
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.rl_line_select:
			showLineInfos(tv_top_title);
			break;
		case R.id.btn_title_left:
			finish();
			break;
		default:
			break;
		}

	}

	/**
	 * 创建列表对话框
	 */
	private Dialog initDialog()
	{
		Dialog dialog = null;
		builder = new AlertDialog.Builder(this);

		// 设置对话框的标题
		builder.setTitle("消息提醒距离选择");
		// 读取配置文件，显示上次选择的距离
		builder.setSingleChoiceItems(items, 4,
				new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						tv_distance.setText(items[which]);
						// 数据需要持久化，下次进来直接读取用户设置
						dialog.dismiss();
					}
				});
		return dialog;
	}

	/**
	 * 创建列表对话框
	 */
	private void showDialog()
	{
		builder.show();
	}

	/**
	 * 加载线路信息
	 * 
	 * @param v
	 */
	private void showLineInfos(View v)
	{
		// 应该做成QQ登陆的下拉菜单的样式
		// ListView lv = new ListView(getContext());
		// View view = View.inflate(getContext(), R.layout.common_title_pop,
		// null);
		ViewGroup view = (ViewGroup) View.inflate(getContext(),
				R.layout.common_title_pop, null);
		ListView lv = (ListView) view.findViewById(R.id.timeList);
		lv.setAdapter(new LineAdapter());
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				if (currentPostion == position)
				{
					Logger.d(RemindSetActivity.class, "重复选择：");
					linePop.dismiss();
					return;
				}
				tv_top_title.setText(studentInfo.getCld_name() + "的"
						+ lines[position]);
				LINE_TYPE = (position == 0) ? "0" : "1";
				linePop.dismiss();
				currentRemind = null;
				newRemind = null;
				isFromToggleButton = false;
				// 线路切换
				loadBusTraces();
				currentPostion = position;
			}
		});
		linePop = null;
		// 会有版本兼容wenti
		linePop = new PopupWindow(v, DensityUtil.dip2px(getContext(), 260),
				DensityUtil.dip2px(getContext(), 150));
		linePop.setFocusable(true);
		linePop.setOutsideTouchable(true);
		linePop.setBackgroundDrawable(new ColorDrawable(00000000));
		linePop.setContentView(view);
		linePop.showAsDropDown(v, DensityUtil.dip2px(getContext(), -30),
				DensityUtil.dip2px(getContext(), 15));

	}

	private String[] lines = new String[] { "上学站点设定", "放学站点设定" };
	private View editLayout;
	/**
	 * 可乘坐站点位置信息
	 */
	private GeoPoint homePoint;
	// 开关提醒
	private PopupWindow remindPop;
	// 主题设置
	public static ChildCustomTheme theme;
	/**
	 * 图标管理集合类
	 */
	private ArrayList<ViewGroup> popList;

	/**
	 * 线路选择数据适配器
	 */
	private class LineAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return lines.length;
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return lines[position];
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent)
		{
			View inflate = View.inflate(getContext(), R.layout.line_item, null);

			TextView tv_line = (TextView) inflate.findViewById(R.id.tv_line);
			CheckBox cb_line = (CheckBox) inflate.findViewById(R.id.cb_line);
			if (position == currentPostion)
			{
				cb_line.setVisibility(View.VISIBLE);
			}
			tv_line.setText(studentInfo.getCld_name() + "的" + lines[position]);
			// android:textColor="#ffffffff"
			// android:textSize="19sp"
			tv_line.setTextColor(Color.WHITE);
			// 字体大小需要进行像素转换
			// tv_line.setTextSize(DensityUtil.sp2px(getContext(), 19));
			tv_line.setGravity(Gravity.CENTER_HORIZONTAL);
			return inflate;
		}
	}

	@Override
	protected boolean isRouteDisplayed()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
