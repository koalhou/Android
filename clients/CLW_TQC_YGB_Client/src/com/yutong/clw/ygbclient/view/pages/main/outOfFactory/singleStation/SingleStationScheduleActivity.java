package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.VehicheInfo;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.common.beans.line.StationVehicleRealTimeInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.ArriveStatus;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.ScheduleItemType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizOutOfFactory;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizOutOfFactoryIndex;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.OutOfFactoryActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.control.BusScheduleExpListAdapter.BusScheduleItemClickListener;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.driver.BusDriverDetailsActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.DateChooseActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleVehicle.SingleVehicleLinesActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.util.DensityUtil;
import com.yutong.clw.ygbclient.view.widget.CustomDialog;
import com.yutong.clw.ygbclient.view.widget.PopCheckList;
import com.yutong.clw.ygbclient.view.widget.PopCheckList.OnCheckChangedListener;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView.OnHeaderRefreshListener;

/**
 * 厂外-单个站点的发车安排详细界面。
 * 
 * @author zhouzc
 */
public class SingleStationScheduleActivity extends RemindAccessActivity
		implements OnClickListener {
	private List<VehicleRealtime> datas;

	/**
	 * 日期选择key
	 */
	private final static int REQUEST_CODE_TIME = 0X001;

	/**
	 * 日期选择key
	 */
	private final static int REQUEST_CODE_REMINDSET = 0X002;

	private BizOutOfFactoryIndex biz;

	/**
	 * 当前筛选项
	 */
	private int p_currentFilterIndex = 0;

	private AreaType p_currentAreaType = AreaType.FirstFactory;

	/**
	 * 当前班车类型：早班、晚班
	 */
	private StatusRange p_currentRange = StatusRange.MorningWork;

	private Date p_currentDate = DateUtils.getCurDate();

	private StationInfo p_currentStation;

	private boolean p_isfavorite;

	private boolean p_isClock;


	private List<String> poprangedatas = Arrays.asList("早班", "晚班");

	// 弹出选择框
	private PopCheckList popcl_range;

	private RelativeLayout rl_amosl_bottomleft;

	private ImageView iv_amosl_left;

	private RelativeLayout rl_amosl_bottomright;

	private ImageView iv_amosl_right;

	/**
	 * 筛选
	 */
	private TextView tv_filter;

	private TextView txt_small;

	private ListView list_view;

	private StationScheduleAdapter adapter;

	/** 下拉刷新对象 */
	private PullToRefreshView pullToRefreshView;

	private TextView txt_large;

	/* 日历按钮不再使用 */
	/* private Button btn_amb_calendar; */

	private TextView tv_amb_time, collecting_stations;

	private PopCheckList popcl_filter;

	private List<String> filteroptions = Arrays.asList("全部", "未进站", "已进站",
			"直达技术北楼");

	private FrameLayout bodyFL;

	// 当前厂区类型
	private AreaType cruuentType;

	private StatusRange currentRange;

	private Date currentDate;

	private int lastSelected = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_outoffactory_singlestationschedule);
		setAllowNetworkCheck(true);
		initParams();
		biz = new BizOutOfFactoryIndex(this);
		initViews();

		initPopUpRange();

		initFilters();
		updateFilters();

		initListeners();
		initListView();
		loadVehicleRealtimeInfo(p_currentStation);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadVehicleRealtimeInfo(p_currentStation);
	}

	private void initListView() {
		datas = new ArrayList<VehicleRealtime>();
		adapter = new StationScheduleAdapter();

		adapter.setDatas(datas, p_currentStation);
		adapter.setCurDate(p_currentDate);
		list_view.setAdapter(adapter);
		list_view.setDivider(null);
		adapter.setBusScheduleItemClickListener(new BusScheduleItemClickListener() {

			@Override
			public void onItemClick(int groupPosition, int childPosition) {
				VehicleRealtime data = (VehicleRealtime) adapter
						.getItem(childPosition);

				if (data == null)
					return;
				if (data.itemType == ScheduleItemType.Tip)
					return;
				if (!data.showOP) {
					data.showOP = true;
					for (int i = 0; i < datas.size(); i++) {
						VehicleRealtime item = datas.get(i);
						if (item != data) {
							item.showOP = false;
						}
					}

				} else {
					data.showOP = false;
				}
				adapter.notifyDataSetChanged();

			}
		});
		adapter.setSiteBusClockClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 到站提醒
				VehicleRealtime rt = (VehicleRealtime) v
						.getTag(R.id.tag_realtimeinfo);
				StationInfo stationInfo = (StationInfo) v
						.getTag(R.id.tag_station);
				if (rt == null || stationInfo == null) {
					Logger.e(this.getClass(), "站点或车辆为null。");
					return;
				}
				toSetRemind(stationInfo, rt);
			}
		});
		adapter.setSiteBusMapClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 单车地图
				VehicleRealtime rt = (VehicleRealtime) v
						.getTag(R.id.tag_realtimeinfo);
				StationInfo stationInfo = (StationInfo) v
						.getTag(R.id.tag_station);

				if (rt == null) {
					Logger.e(this.getClass(), "VehicleRealtime为null。");
					return;
				}
				VehicheInfo vinfo = new VehicheInfo();

				vinfo.line_id = rt.line_id;
				vinfo.vehiche_number = rt.vehicle_number;
				vinfo.vehicle_vin = rt.vehicle_vin;
				Bundle bundle = new Bundle();
				bundle.putSerializable(ActivityCommConstant.VEHICHLE, vinfo);
				bundle.putSerializable(ActivityCommConstant.VEHICHLE_REALTIME,
						rt);
				bundle.putSerializable(ActivityCommConstant.STATUSRANGE, p_currentRange);
				bundle.putSerializable(ActivityCommConstant.STATION_INFO,
						stationInfo);
				bundle.putSerializable(ActivityCommConstant.DATE, p_currentDate);
				ActivityUtils.changeActivity(
						SingleStationScheduleActivity.this,
						SingleVehicleLinesActivity.class, bundle);
			}
		});
		
		/*联系司机*/
		adapter.setOnContactDriverListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*用户行为统计-*/
	            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_SJMX).reportBehavior(null);
				
	            final VehicleRealtime rt = (VehicleRealtime) v.getTag(R.id.tag_realtimeinfo);
				Bundle bundle = new Bundle();
				bundle.putString(ActivityCommConstant.VEHICHLE, rt.vehicle_vin);
				bundle.putString(ActivityCommConstant.LINE_ID, rt.line_id);
				bundle.putString(ActivityCommConstant.VIHICLE_NUM, rt.vehicle_number);
				
				ActivityUtils.changeActivity(SingleStationScheduleActivity.this, BusDriverDetailsActivity.class, bundle);
			}
		});
	}

	public void initViews() {
		bodyFL = (FrameLayout) findViewById(R.id.bodyFL);
		/* btn_amb_calendar = (Button) this.findViewById(R.id.btn_amb_calendar); */
		tv_amb_time = (TextView) this.findViewById(R.id.tv_amb_time);
		tv_filter = (TextView) findViewById(R.id.tv_filter);

		list_view = (ListView) findViewById(R.id.list_view);
		pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
		pullToRefreshView.setPullUpEnable(false);

		rl_amosl_bottomleft = (RelativeLayout) findViewById(R.id.rl_amosl_bottomleft);
		iv_amosl_left = (ImageView) findViewById(R.id.iv_amosl_left);
		rl_amosl_bottomright = (RelativeLayout) findViewById(R.id.rl_amosl_bottomright);
		iv_amosl_right = (ImageView) findViewById(R.id.iv_amosl_right);

		collecting_stations = (TextView) findViewById(R.id.collecting_stations);

		setP_isfavorite(p_isfavorite);
		setP_isClock(p_isClock);
		setP_currentStation(p_currentStation);
		setP_currentDate(p_currentDate);
	}

	private void initListeners() {
		tv_filter.setOnClickListener(this);

		/* btn_amb_calendar.setOnClickListener(this); */
		tv_amb_time.setOnClickListener(this);

		rl_amosl_bottomleft.setOnClickListener(this);

		rl_amosl_bottomright.setOnClickListener(this);

		pullToRefreshView
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						loadVehicleRealtimeInfo(p_currentStation);

					}
				});
	}

	private void initFilters() {
		popcl_filter = new PopCheckList(YtApplication.getInstance());
		popcl_filter.setOnCheckChangedListener(new OnCheckChangedListener() {

			@Override
			public void OnChecked(int index, String value) {

				popcl_filter.dismiss();
				if(lastSelected == index){
					return;
				}
				lastSelected = index;
				setP_currentFilterIndex(index);
				loadVehicleRealtimeInfo(p_currentStation);
				
			}
		});
		popcl_filter.setOnDismisslistener(new OnDismissListener() {

			@Override
			public void onDismiss() {

			}
		});
		
		lastSelected = getP_currentFilterIndex();
		popcl_filter.setData(filteroptions,lastSelected);
	}

	/**
	 * 初始化传入的参数
	 * 
	 * @author zhangyongn 2013-11-12 下午4:43:41
	 */
	private void initParams() {
		initStation();
		initAreaType();
		initStatusRange();
		initDate();
		initFavor();
		initClockState();

	}

	/* 是否收藏 */
	private void initFavor() {

		try {
			if (p_currentStation != null) {
				p_isfavorite = p_currentStation.favorites;
			} else {
				p_isfavorite = (boolean) getIntent().getExtras().getBoolean(
						ActivityCommConstant.ISFAVOR);
			}
		} catch (Exception e) {
			p_isfavorite = false;
		}
	}

	/* 是否设置到站提醒 */
	private void initClockState() {
		try {
			if (p_currentStation != null) {
				p_isClock = p_currentStation.status;
			} else {
				p_isClock = (boolean) getIntent().getExtras().getBoolean(
						ActivityCommConstant.ISCLOCK);
			}
		} catch (Exception e) {
			p_isClock = false;
		}

	}

	/* 站点信息 */
	private void initStation() {
		try {
			p_currentStation = (StationInfo) getIntent().getExtras()
					.getSerializable(ActivityCommConstant.STATION_INFO);

		} catch (Exception e) {
			p_currentStation = null;
		}

	}

	private void initDate() {
		try {
			Date date = (Date) getIntent().getExtras().getSerializable(
					ActivityCommConstant.DATE);
			if (date != null)
				p_currentDate = date;
			else
				p_currentDate = DateUtils.getCurDate();
		} catch (Exception e) {
			p_currentDate = DateUtils.getCurDate();
		}
	}

	private void initStatusRange() {
		try {
			StatusRange range = (StatusRange) getIntent().getExtras()
					.getSerializable(ActivityCommConstant.STATUSRANGE);
			if (range != null)
				setP_currentRange(range);
			else {
				setP_currentRange(getRangeByTime());
			}
		} catch (Exception e) {
			setP_currentRange(getRangeByTime());
		}
	}

	private StatusRange getRangeByTime() {
		StatusRange r;
		Date now = DateUtils.getCurDate();
		Date p = DateUtils.getCurDate();
		p.setHours(9);
		p.setMinutes(0);
		p.setSeconds(0);
		// String nows = DateUtils.getFormatTime(now, "yyyyMMdd HH:mm:ss");
		// String ps = DateUtils.getFormatTime(p, "yyyyMMdd HH:mm:ss");
		if (now.before(p)) {
			r = StatusRange.MorningWork;
		} else {
			r = StatusRange.NightWork;
		}
		return r;
	}

	private void initAreaType() {
		try {
			AreaType type = (AreaType) getIntent().getExtras().getSerializable(
					ActivityCommConstant.AREATYPE);
			if (type != null)
				setP_currentAreaType(type);
			else {
				setP_currentAreaType(PrefDataUtil.getFactory(this));
			}
		} catch (Exception e) {
			setP_currentAreaType(PrefDataUtil.getFactory(this));
		}
	}

	private void initPopUpRange() {

		popcl_range = new PopCheckList(this);
		popcl_range.setOnCheckChangedListener(new OnCheckChangedListener() {

			@Override
			public void OnChecked(int index, String value) {
				setP_currentRange((index == 0) ? StatusRange.MorningWork
						: StatusRange.NightWork);
				
				if(lastSelected != index){
					
					lastSelected = index;
					onRangeChanged(getP_currentRange());
					updateFilters();
				}
				
				// ToastMessage("切换厂区");
				popcl_range.dismiss();
			}
		});
		popcl_range.setOnDismisslistener(new OnDismissListener() {

			@Override
			public void onDismiss() {

				// TODO Auto-generated method stub
				// ToastMessage("取消");
			}
		});

		int index = (getP_currentRange() == StatusRange.MorningWork) ? 0 : 1;
		lastSelected = index;
		popcl_range.setData(poprangedatas, index);
		setTitleForStatusRange(getP_currentRange());
	}

	/**
	 * 早晚班选择改变的对应处理
	 * 
	 * @author zhangyongn 2013-11-12 下午4:54:24
	 * @param type
	 */
	private void onRangeChanged(StatusRange range) {
		setTitleForStatusRange(range);
		loadVehicleRealtimeInfo(p_currentStation);
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
		btn_right.setBackgroundResource(R.drawable.bg_mapbtn);
		btn_right.setVisibility(View.VISIBLE);

		tv_large.setText("");
		tv_small.setText("");
		this.txt_small = tv_small;
		this.txt_large = tv_large;

		rl_center.setOnClickListener(this);

		btn_left.setOnClickListener(this);
		btn_right.setOnClickListener(this);
	}

	private void setTitleForStatusRange(StatusRange range) {

		String ftext = (range == StatusRange.MorningWork) ? "早班" : "晚班";
		txt_small.setText(ftext);
	}

	private void showOrDismissPopRange() {
		if (popcl_range != null) {
			View center = findViewById(R.id.rl_it_center);
			int xoffset = (center.getWidth() - popcl_range.getWidth()) / 2;
			popcl_range.showAsDropDown(findViewById(R.id.rl_it_center),
					xoffset, 2);
		}
	}

	private void showOrDismissPopFilter() {
		if (popcl_filter != null) {
			int offsetx = -popcl_filter.getWidth() - tv_filter.getWidth();
			int offsety = -popcl_filter.getHeight();
			if ((tv_filter.getTop() + tv_filter.getHeight() / 2) < ((View) tv_filter
					.getParent()).getHeight() / 2) {
				offsety += (popcl_filter.getHeight() - tv_filter.getHeight());
				offsety += DensityUtil.dip2px(this, 3);
			} else {
				offsety -= DensityUtil.dip2px(this, 3);
			}

			Logger.i(getClass(), "showOrDismissPopFilter offsetx:" + offsetx
					+ " ,offsety:" + offsety);
			popcl_filter.showAsDropDown(tv_filter, offsetx, offsety);
			// popcl_filter.showAtLocation(center, Gravity.CENTER, 0, 0);
		}
	}

	/**
	 * 加载实时排班计划
	 * 
	 * @author zhangyongn 2013-11-13 上午9:58:44
	 * @param station
	 */
	private void loadVehicleRealtimeInfo(StationInfo station) {
		if (station == null)
			return;
		List<String> station_ids = Arrays.asList(station.id);

		pullToRefreshView.setHeaderRefreshing();
		biz.getStationVehicleRealTimeInfo(station_ids, p_currentDate,
				p_currentAreaType, p_currentRange,
				new BizResultProcess<List<StationVehicleRealTimeInfo>>() {

					@Override
					public void onBizExecuteError(Exception exception,
							Error error) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// dismissLoading(0);
								pullToRefreshView.onHeaderRefreshComplete();
								SingleStationScheduleActivity.this
										.ToastMessage("加载数据失败！");
							}
						});

					}

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							final List<StationVehicleRealTimeInfo> t) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// dismissLoading(0);
								pullToRefreshView.onHeaderRefreshComplete();

								updateVehicleRealtimeInfo(t);

							}

						});

					}
				});
	}

	/**
	 * 更新实时信息
	 * 
	 * @author zhangyongn 2013-11-13 上午10:13:22
	 * @param t
	 */
	private void updateVehicleRealtimeInfo(List<StationVehicleRealTimeInfo> t) {
		if (t == null || t.size() <= 0) {
			t = new ArrayList<StationVehicleRealTimeInfo>();
			StationVehicleRealTimeInfo rt = new StationVehicleRealTimeInfo();
			rt.stationInfo = getP_currentStation();
			rt.VehicleRealtimeInfos = getEmptyVehicleRealtime();
			t.add(rt);

		}
		if (p_currentStation == null) {
			Logger.e(getClass(), "p_currentStation==null");
			return;
		}
		for (int i = 0; i < t.size(); i++) {
			StationVehicleRealTimeInfo newitem = t.get(i);
			if (StringUtil.equals(newitem.stationInfo.id, p_currentStation.id)) {
				datas = FilterRealTimeInfo(newitem.VehicleRealtimeInfos);
				break;
			}

		}

		adapter.setDatas(datas, p_currentStation);
		adapter.setCurDate(p_currentDate);
		adapter.notifyDataSetChanged();
	}

	/**
	 * 根据筛选条件筛选班车
	 * 
	 * @author zhangyongn 2013-11-14 上午11:17:41
	 * @param vehicleRealtimeInfos
	 * @return
	 */
	private List<VehicleRealtime> FilterRealTimeInfo(
			List<VehicleRealtime> vehicleRealtimeInfos) {
		List<VehicleRealtime> list = new ArrayList<VehicleRealtime>();
		if (vehicleRealtimeInfos == null || vehicleRealtimeInfos.size() <= 0) {
			return getEmptyVehicleRealtime();
		}

		if (p_currentRange == StatusRange.MorningWork) {
			switch (getP_currentFilterIndex()) {
			case 0:// 全部
				list = vehicleRealtimeInfos;
				break;
			case 1:
			case 2:
			// 筛选未进站：1、已进站：2。
			{
				for (int i = 0; i < vehicleRealtimeInfos.size(); i++) {
					VehicleRealtime rt = vehicleRealtimeInfos.get(i);
					ArriveStatus status = (getP_currentFilterIndex() == 1) ? ArriveStatus.NoArrive
							: ArriveStatus.Arrive;
					if (rt.arrive_status == status)
						list.add(rt);
				}
			}

				break;
			case 3:// 筛选直达技术北楼的
			{
				for (int i = 0; i < vehicleRealtimeInfos.size(); i++) {
					VehicleRealtime rt = vehicleRealtimeInfos.get(i);
					if (rt.flag)
						list.add(rt);
				}
			}
				break;

			default: {
				Logger.i(getClass(), "发现未知筛选项m");
			}
				break;
			}
			if (list.size() <= 0) {
				ToastMessage("重新设置筛选条件，可查看更多班车信息！");
				return getEmptyVehicleRealtime();
			}
			return list;
		} else {
			switch (getP_currentFilterIndex()) {
			case 0:// 全部
				list = vehicleRealtimeInfos;
				break;
			case 1:
			// 筛选未发车
			{
				for (int i = 0; i < vehicleRealtimeInfos.size(); i++) {
					VehicleRealtime rt = vehicleRealtimeInfos.get(i);
					if (rt.arrive_status == ArriveStatus.NoStartOff)
						list.add(rt);
				}
			}
				break;
			case 2:
			// 筛选已发车
			{
				for (int i = 0; i < vehicleRealtimeInfos.size(); i++) {
					VehicleRealtime rt = vehicleRealtimeInfos.get(i);
					if (rt.arrive_status == ArriveStatus.StartOff)
						list.add(rt);
				}
			}
				break;
			default: {
				Logger.i(getClass(), "发现未知筛选项n");
			}
				break;
			}
		}
		if (list.size() <= 0) {
			ToastMessage("重新设置筛选条件，可查看更多班车信息！");
			return getEmptyVehicleRealtime();
		}
		return list;
	}

	private List<VehicleRealtime> getEmptyVehicleRealtime() {
		VehicleRealtime item = new VehicleRealtime(ScheduleItemType.Tip);
		item.tip = "暂无发车安排";
		List<VehicleRealtime> list = new ArrayList<VehicleRealtime>();
		list.add(item);
		if (p_currentRange == StatusRange.MorningWork) {
			switch (getP_currentFilterIndex()) {
			case 0:// 全部
				break;
			case 1:
				item.tip = "暂无未进站车辆";
				break;
			case 2:
				item.tip = "暂无已进站发车车辆";
				break;
			case 3:// 筛选直达技术北楼的
				item.tip = "暂无直达技术北楼车辆";
				break;
			}

			return list;
		} else {
			switch (getP_currentFilterIndex()) {
			case 0:// 全部
				break;
			case 1:
				item.tip = "暂无未发车车辆";
				break;
			case 2:
				item.tip = "暂无已发车车辆";
				break;
			}
			return list;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_TIME) {
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				String dt = bundle.getInt("year") + "-"
						+ (bundle.getInt("month")) + "-" + bundle.getInt("day");
				p_currentDate = DateUtils.strToDate(dt, "yyyy-MM-dd");
				setP_currentDate(p_currentDate);
				adapter.setCurDate(p_currentDate);
				adapter.notifyDataSetChanged();
				loadVehicleRealtimeInfo(getP_currentStation());
			}
		} else if (requestCode == REQUEST_CODE_REMINDSET) {
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				RemindInfo info = (RemindInfo) bundle
						.getSerializable(ActivityCommConstant.REMINDINFO);
				if (info != null && info.remind_status != null) {
					setP_isClock(info.remind_status == RemindStatus.Open);
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_it_left:

			/* 返回父Activity数据 */
			Intent intent = new Intent();

			Bundle bundle_0 = new Bundle();
			bundle_0.putSerializable("stationInfo", p_currentStation);
			intent.putExtras(bundle_0);
			setResult(RESULT_OK, intent);

			finish();
			break;

		case R.id.btn_it_right:

			Bundle bundle = new Bundle();
			bundle.putSerializable(ActivityCommConstant.STATION_INFO,
					getP_currentStation());
			bundle.putSerializable(ActivityCommConstant.STATUSRANGE,
					p_currentRange);
			bundle.putSerializable(ActivityCommConstant.DATE, p_currentDate);
			ActivityUtils.changeActivity(SingleStationScheduleActivity.this,
					SingleStationLinesActivity.class, bundle);
			break;

		case R.id.rl_it_center:
			showOrDismissPopRange();
			break;
		case R.id.tv_filter:
			showOrDismissPopFilter();
			break;

		/* case R.id.btn_amb_calendar: */

		case R.id.tv_amb_time:
			popDatePicker();
			break;
		case R.id.rl_amosl_bottomleft:

			showFavoriteConfirmDialog(p_currentStation, p_isfavorite);

			break;
		case R.id.rl_amosl_bottomright:
			toSetRemind();
			break;
		default:
			break;
		}
	}

	// 跳转到闹钟提醒界面，有车辆
	private void toSetRemind(final StationInfo stationInfo,
			final VehicleRealtime rt) {

		showLoading("正在获取提醒信息...", 0);
		final StatusRange p_range = p_currentRange;
		biz.GetRemindInfo(p_currentAreaType, stationInfo.id, p_currentRange,
				rt.vehicle_vin, new BizResultProcess<RemindInfo>() {
					// final RemindInfo premindinfo = new RemindInfo(
					// p_currentAreaType, stationInfo.id, p_currentRange,
					// rt.vehicle_vin);

					@Override
					public void onBizExecuteError(Exception exception,
							Error error) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								dismissLoading(0);
								ToastMessage("获取提醒信息异常");

							}
						});

					}

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							final RemindInfo t) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								dismissLoading(0);
								RemindInfo p = t;
								if (t == null) {
									p = new RemindInfo();
									p.setId(null);
									p.setStation_id(stationInfo.getId());
									p.setStation_name(stationInfo.getName());
									p.setArea_type(stationInfo.getArea_type());
									p.setLine_range(LineRange.FactoryOuter);
									p.remind_range = RemindRange.StationAndVehiche;
									p.vehiche_vin = rt.vehicle_vin;
									p.vehiche_number = rt.vehicle_number;
								}
								p.setStatus_range(p_range);
								Bundle b = new Bundle();
								b.putSerializable(
										ActivityCommConstant.REMINDINFO, p);
								Intent intent = new Intent(
										SingleStationScheduleActivity.this,
										StationRemindSettingActivity.class);
								intent.putExtras(b);
								startActivityForResult(intent,
										REQUEST_CODE_REMINDSET);

							}
						});

					}
				});
	}

	// 跳转到闹钟提醒界面，无车辆
	private void toSetRemind() {

		showLoading("正在获取提醒信息...", 0);

		final StatusRange p_range = p_currentRange;
		biz.GetRemindInfo(p_currentAreaType, p_currentStation.id,
				p_currentRange, null, new BizResultProcess<RemindInfo>() {
					// final RemindInfo premindinfo = new
					// RemindInfo(p_currentAreaType, p_currentStation.id,
					// p_currentRange, null);

					@Override
					public void onBizExecuteError(Exception exception,
							Error error) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								dismissLoading(0);
								ToastMessage("获取提醒信息异常");

							}
						});

					}

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							final RemindInfo t) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								dismissLoading(0);
								RemindInfo p = t;
								if (p == null) {
									p = new RemindInfo();
									p.setId(null);
									p.setStation_id(p_currentStation.getId());
									p.setStation_name(p_currentStation
											.getName());
									p.setArea_type(p_currentStation
											.getArea_type());
									p.setLine_range(LineRange.FactoryOuter);
									p.remind_range = RemindRange.OnlyStation;
								}
								p.setStatus_range(p_range);
								Bundle b = new Bundle();
								b.putSerializable(
										ActivityCommConstant.REMINDINFO, p);
								Intent intent = new Intent(
										SingleStationScheduleActivity.this,
										StationRemindSettingActivity.class);
								intent.putExtras(b);
								startActivityForResult(intent,
										REQUEST_CODE_REMINDSET);
							}
						});

					}
				});
	}

	protected void showFavoriteConfirmDialog(final StationInfo site,
			final boolean isFavor) {
		if (site == null)
			return;

		String title = isFavor ? "取消收藏" : "添加收藏";
		String body = isFavor ? "是否取消收藏？" : "是否添加收藏？";
		final String hintStr = isFavor ? "正在取消收藏..." : "正在添加收藏...";
		CustomDialog dia = new CustomDialog.Builder(
				SingleStationScheduleActivity.this).setTitle(title)
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						SubmitFavorite(site, !isFavor);
						showLoading(hintStr, 1);
					}
				}).setMessage(body).create();
		dia.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();

			}
		});
		dia.show();

	}

	/**
	 * 提交收藏状态
	 * 
	 * @author zhangyongn 2013-11-14 下午2:33:04
	 * @param site
	 *            站点
	 * @param favor
	 *            要设置的收藏状态，true：表示收藏，false：表示不收藏
	 */
	protected void SubmitFavorite(StationInfo site, final boolean favor) {
		p_isfavorite = favor;
		BizOutOfFactory biz = new BizOutOfFactory(this);
		biz.setStationFavority(site.id, favor, new BizResultProcess<Boolean>() {

			@Override
			public void onBizExecuteError(Exception exception, Error error) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						ToastMessage("操作失败！");
						p_isfavorite = !p_isfavorite;

					}
				});

			}

			@Override
			public void onBizExecuteEnd(BizDataTypeEnum datatype,
					final Boolean t) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (favor) {
							ToastMessage("收藏成功");
						} else {
							ToastMessage("取消收藏");
						}

						p_currentStation.setFavorites(p_isfavorite);
						setP_isfavorite(p_isfavorite);
						collecting_stations.setText(favor ? "取消收藏" : "收藏站点");
						dismissLoading(1);
					}

				});

			}
		});
	}

	private void popDatePicker() {
		Intent intent = new Intent(SingleStationScheduleActivity.this,
				DateChooseActivity.class);
		Bundle bundle = new Bundle();

		bundle.putInt("year", DateUtils.getYear(p_currentDate));
		bundle.putInt("month", DateUtils.getMonth(p_currentDate));
		bundle.putInt("day", DateUtils.getDate(p_currentDate));
		intent.putExtras(bundle);

		startActivityForResult(intent, REQUEST_CODE_TIME);

	}

	private void updateFilters() {
		AreaType type = p_currentStation.getArea_type();
		List<String> f;
		if (getP_currentRange() == StatusRange.NightWork) {
			f = new ArrayList<String>() {
				{
					add("全部");
					add("未发车");
					add("已发车");
				}
			};
			if (f.contains(popcl_filter.getSelectionValue())) {
				setP_currentFilterIndex(f.indexOf(popcl_filter
						.getSelectionValue()));
			} else {
				setP_currentFilterIndex(0);
			}
		} else {
			f = new ArrayList<String>() {
				{
					add("全部");
					add("未进站");
					add("已进站");
				}
			};
			if (type == AreaType.FirstFactory
					&& getP_currentRange() == StatusRange.MorningWork) {
				f.add("直达技术北楼");
				if (f.contains(popcl_filter.getSelectionValue())) {
					setP_currentFilterIndex(f.indexOf(popcl_filter
							.getSelectionValue()));
				} else {
					setP_currentFilterIndex(0);
				}
			}
		}
		popcl_filter.setData(f, getP_currentFilterIndex());
	}

	/**
	 * @return p_currentDate
	 */
	public Date getP_currentDate() {
		return p_currentDate;
	}

	/**
	 * @param p_currentDate
	 *            要设置的 p_currentDate
	 */
	public void setP_currentDate(Date p_currentDate) {
		this.p_currentDate = p_currentDate;
		if (this.p_currentDate == null)
			tv_amb_time.setText("");
		tv_amb_time.setText(DateUtils
				.getFormatTime(p_currentDate, "yyyy-MM-dd"));
	}

	/**
	 * @return p_currentRange
	 */
	public StatusRange getP_currentRange() {
		return p_currentRange;
	}

	/**
	 * @param p_currentRange
	 *            要设置的 p_currentRange
	 */
	public void setP_currentRange(StatusRange p_currentRange) {
		this.p_currentRange = p_currentRange;
	}

	/**
	 * @return p_currentStation
	 */
	public StationInfo getP_currentStation() {
		return p_currentStation;
	}

	/**
	 * @param p_currentStation
	 *            要设置的 p_currentStation
	 */
	public void setP_currentStation(StationInfo p_currentStation) {
		this.p_currentStation = p_currentStation;
		txt_large.setText((p_currentStation != null) ? p_currentStation.name
				: "");
	}

	/**
	 * @return p_currentAreaType
	 */
	public AreaType getP_currentAreaType() {
		return p_currentAreaType;
	}

	/**
	 * @param p_currentAreaType
	 *            要设置的 p_currentAreaType
	 */
	public void setP_currentAreaType(AreaType p_currentAreaType) {
		this.p_currentAreaType = p_currentAreaType;
	}

	/**
	 * @return p_currentFilterIndex
	 */
	public int getP_currentFilterIndex() {
		return p_currentFilterIndex;
	}

	/**
	 * @param p_currentFilterIndex
	 *            要设置的 p_currentFilterIndex
	 */
	public void setP_currentFilterIndex(int p_currentFilterIndex) {
		this.p_currentFilterIndex = p_currentFilterIndex;
	}

	/**
	 * @return p_isClock
	 */
	public boolean isP_isClock() {
		return p_isClock;
	}

	/**
	 * @param p_isClock
	 *            要设置的 p_isClock
	 */
	public void setP_isClock(boolean p_isClock) {
		this.p_isClock = p_isClock;
		iv_amosl_right.setImageResource(p_isClock ? R.drawable.ic_clocked_white
				: R.drawable.ic_clock_white);
	}

	/**
	 * @return p_isfavorite
	 */
	public boolean isP_isfavorite() {
		return p_isfavorite;
	}

	/**
	 * @param p_isfavorite
	 *            要设置的 p_isfavorite
	 */
	public void setP_isfavorite(boolean p_isfavorite) {
		this.p_isfavorite = p_isfavorite;
		iv_amosl_left
				.setImageResource(p_isfavorite ? R.drawable.ic_favored_white
						: R.drawable.ic_favor_white);
	}

}
