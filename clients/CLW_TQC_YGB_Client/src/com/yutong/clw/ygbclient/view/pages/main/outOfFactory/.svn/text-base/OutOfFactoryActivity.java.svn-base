package com.yutong.clw.ygbclient.view.pages.main.outOfFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.common.utils.ToastUtils;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizOutOfFactory;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizOutOfFactoryIndex;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.AnimCommon;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.control.ConFavoriteSites;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.control.ConFavoriteSites.BusFilterClickListener;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.control.ConFavoriteSites.StationExpandListener;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.control.ConRecommandStation;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.driver.BusDriverDetailsActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.DateChooseActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.StationSearchActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation.SingleStationLinesActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation.SingleStationScheduleActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation.StationRemindSettingActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleVehicle.SingleVehicleLinesActivity;
import com.yutong.clw.ygbclient.view.pages.setting.SettingActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.widget.CustomDialog;
import com.yutong.clw.ygbclient.view.widget.PopCheckList;
import com.yutong.clw.ygbclient.view.widget.PopCheckList.OnCheckChangedListener;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.yutong.clw.ygbclient.view.widget.ToggleRadio;
import com.yutong.clw.ygbclient.view.widget.ToggleRadio.IToggleListener;
import com.yutong.clw.ygbclient.view.widget.ToggleRadio.ToggleOption;

/**
 * 厂外通勤主界面
 * 
 * @author zhouzc
 */
public class OutOfFactoryActivity extends RemindAccessActivity implements
		OnClickListener {
	/**
	 * 日期选择key
	 */
	private final static int REQUEST_CODE = 0;

	private BizOutOfFactoryIndex biz;

	private AreaType p_currentAreaType = AreaType.FirstFactory;

	/**
	 * 当前班车类型：早班、晚班
	 */
	private StatusRange p_currentRange = StatusRange.MorningWork;

	private Date p_currentDate = DateUtils.getCurDate();

	/*-------------------------------------------------数据----------------------------*/
	/**
	 * 收藏站点集合
	 */
	private List<StationInfo> favoriteStations;

	/**
	 * ExpandableListView的数据源
	 */
	List<StationVehicleRealTimeInfo> stationVehicleRealTimeInfos;

	/**
	 * 推荐站点列表的数据源
	 */
	List<StationInfo> recommendStations;

	/*-------------------------------------------------数据----------------------------*/

	TextView txt_small;

	/**
	 * 主视图容器
	 */
	private LinearLayout ll_main_content;

	// 弹出选择框
	private PopCheckList popcl;

	private int popWidth = 120;

	private List<String> factorys = Arrays.asList("一厂", "二厂");

	/**
	 * 早班晚班选择
	 */
	private ToggleRadio toggleRadio;

	/**
	 * 推荐站点
	 */
	private ConRecommandStation view_recommandStation;

	/**
	 * 收藏站点
	 */
	private ConFavoriteSites view_favoritesits;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main_outoffactory);
		setAllowNetworkCheck(true);
		initParams();
		biz = new BizOutOfFactoryIndex(this);
		initViews();
		initFactorySetPopUp();
		initRecommendView();
		initFavoriteView();

		initListeners();

		/* loadFavoriteStations(p_currentAreaType, p_currentRange); */
		// loadRecommendStations();
		// switchView();
        new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_LOAD).reportBehavior(null);
	}
	
	private int lastAreaType = -1;
	
	@Override
	protected void onResume() {

		super.onResume();
		
		p_currentAreaType = PrefDataUtil.getFactory(OutOfFactoryActivity.this);
		
		int index = (p_currentAreaType == AreaType.FirstFactory) ? 0 : 1;
		
		
		if(lastSelected==-1 || lastAreaType!=index){
			
			lastSelected = index;
		}
		
		popcl.setData(factorys, lastSelected);
		p_currentAreaType = lastSelected == 0 ? AreaType.FirstFactory : AreaType.SecondFactory ;
		setTitleForFactory(txt_small, p_currentAreaType);
//		loadFavoriteStations(p_currentAreaType, p_currentRange);
		loadFavoriteStations(lastSelected == 0 ? AreaType.FirstFactory : AreaType.SecondFactory, p_currentRange);
	
		/*处理日期问题*/
		if(!isFromActivityResult){
			isFromActivityResult = false;
			p_currentDate = DateUtils.getCurDate();
			view_favoritesits.setP_currentDate(p_currentDate);
			loadVehicleRealtimeInfo(view_favoritesits.getP_currentStation(), true);
		}
		
		/*view_favoritesits.setP_currentDate(p_currentDate);*/
		
	}
	
	private void setTitleForFactory(TextView tv, AreaType areatype) {

//		String ftext = (areatype == AreaType.FirstFactory) ? "一厂" : "二厂";
		tv.setText(factorys.get(lastSelected));
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		p_currentAreaType = PrefDataUtil.getFactory(OutOfFactoryActivity.this);
		lastAreaType = (p_currentAreaType == AreaType.FirstFactory) ? 0 : 1;
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	/**
	 * 初始化传入的参数
	 * 
	 * @author zhangyongn 2013-11-12 下午4:43:41
	 */
	private void initParams() {
		initAreaType();
		initStatusRange();
		initDate();
	}

	private void initDate() {
		try {
			Date dt = (Date) getIntent().getExtras().getSerializable(
					ActivityCommConstant.DATE);
			if (dt != null)
				p_currentDate = dt;
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
				p_currentRange = range;
			else {
				p_currentRange = getRangeByTime();
			}
		} catch (Exception e) {
			p_currentRange = getRangeByTime();
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
				p_currentAreaType = type;
			else {
				p_currentAreaType = PrefDataUtil.getFactory(this);
			}
		} catch (Exception e) {
			p_currentAreaType = PrefDataUtil.getFactory(this);
		}
	}

	/**
	 * 是否有收藏站点
	 * 
	 * @author zhangyongn 2013-11-12 上午8:34:26
	 * @return
	 */
	private boolean hasFavoriteSites() {
		return favoriteStations != null && favoriteStations.size() > 0;
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
			rt.stationInfo = view_favoritesits.getP_currentStation();
			rt.VehicleRealtimeInfos = getResultVehicleRealtime();
			
			t.add(rt);
		}

		for (int i = 0; i < t.size(); i++) {
			
			StationVehicleRealTimeInfo newitem = t.get(i);
			
			for (int j = 0; j < stationVehicleRealTimeInfos.size(); j++) {
				
				StationVehicleRealTimeInfo olditem = stationVehicleRealTimeInfos.get(j);
				
				if (StringUtil.equals(newitem.stationInfo.id,olditem.stationInfo.id)) {
					olditem.VehicleRealtimeInfos = FilterRealTimeInfo(newitem.VehicleRealtimeInfos);
					break;
				}
			}

		}
		view_favoritesits.setDatas(stationVehicleRealTimeInfos, false);

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
			/*return getEmptyVehicleRealtime();*/
			return getResultVehicleRealtime();
		}
		
		if (p_currentRange == StatusRange.MorningWork) {
			switch (view_favoritesits.getP_currentFilterIndex()) {
			case 0:// 全部
				list = vehicleRealtimeInfos;
				break;
			case 1:
			case 2:
			// 筛选未进站：1、已进站：2。
			{
				for (int i = 0; i < vehicleRealtimeInfos.size(); i++) {
					VehicleRealtime rt = vehicleRealtimeInfos.get(i);
					ArriveStatus status = (view_favoritesits
							.getP_currentFilterIndex() == 1) ? ArriveStatus.NoArrive
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
				/*return getEmptyVehicleRealtime();*/
				return getResultVehicleRealtime();
			}
			return list;
		} else {
			switch (view_favoritesits.getP_currentFilterIndex()) {
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
			if (list.size() <= 0) {
				ToastMessage("重新设置筛选条件，可查看更多班车信息！");
				/*return getEmptyVehicleRealtime();*/
				return getResultVehicleRealtime();
			}
			return list;
		}
	}

	private List<VehicleRealtime> getEmptyVehicleRealtime() {
		VehicleRealtime item = new VehicleRealtime(ScheduleItemType.Tip);
		/*item.tip = "暂无发车安排";*/
		item.tip = "正在查询班";
		List<VehicleRealtime> list = new ArrayList<VehicleRealtime>();
		list.add(item);
		if (p_currentRange == StatusRange.MorningWork) {
			switch (view_favoritesits.getP_currentFilterIndex()) {
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
			switch (view_favoritesits.getP_currentFilterIndex()) {
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
	
	private List<VehicleRealtime> getResultVehicleRealtime() {
		VehicleRealtime item = new VehicleRealtime(ScheduleItemType.Tip);
		item.tip = "暂无发车安排";
		item.isResult = true;
		List<VehicleRealtime> list = new ArrayList<VehicleRealtime>();
		list.add(item);
		if (p_currentRange == StatusRange.MorningWork) {
			switch (view_favoritesits.getP_currentFilterIndex()) {
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
			switch (view_favoritesits.getP_currentFilterIndex()) {
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
	
	/**
	 * 加载收藏站点
	 * 
	 * @author zhangyongn 2013-11-12 上午8:30:38
	 * @param type
	 * @param range
	 */
	private void loadFavoriteStations(AreaType type, StatusRange range) {
		
		Logger.i(getClass(), "开始刷新所有站点信息");
		// showLoading("正在获取收藏站点", 0);
		BizDataTypeEnum returntype = biz.getFavoriteStations(type, range,
				new BizResultProcess<List<StationInfo>>() {

					@Override
					public void onBizExecuteError(Exception exception,
							Error error) {
						dismissLoading(0);
						// OutOfFactoryActivity.this.ToastMessage("加载收藏站点失败！");
						HandleLogicErrorInfo(exception);

					}

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							List<StationInfo> t) {
						// TODO Auto-generated method stub
						favoriteStations = t;
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								dismissLoading(0);
								switchView();
								if (hasFavoriteSites()) {
									// OutOfFactoryActivity.this.view_favoritesits.setDatas(datas)
									buildFavoriteSitesDataSource(favoriteStations);
									view_favoritesits.setDatas(
											stationVehicleRealTimeInfos, true);
								} else { // 推荐站点
									loadRecommendStations();
								}

								// 判断是否取消引导页
								if (!hasFavoriteSites()) {
									String key = OutOfFactoryActivity.class
											.toString() + "recommend";
									boolean shown = YtApplication.getInstance()
											.getActivityCoverManager()
											.hasShowCover(key);
									if (!shown) {
										YtApplication
												.getInstance()
												.getActivityCoverManager()
												.showCover(
														OutOfFactoryActivity.this,
														key, null);
									}

								}
							}

						});
					}
				});

		if (returntype == BizDataTypeEnum.FromNetwork) {
			showLoading("正在获取收藏站点", 0);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	/**
	 * 加载实时排班计划
	 * 
	 * @author zhangyongn 2013-11-13 上午9:58:44
	 * @param station
	 * @param needShowLoading
	 */
	private void loadVehicleRealtimeInfo(StationInfo station,
			boolean needShowLoading) {
		if (station == null)
			return;
		List<String> station_ids = Arrays.asList(station.id);

		if (needShowLoading) {
			 showLoading("正在加载发车安排信息...", 0);
			view_favoritesits.getPullToRefreshView().setHeaderRefreshing();
		}
		
		biz.getStationVehicleRealTimeInfo(station_ids, p_currentDate,
				p_currentAreaType, p_currentRange,
				new BizResultProcess<List<StationVehicleRealTimeInfo>>() {

					@Override
					public void onBizExecuteError(Exception exception,
							Error error) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								
								dismissLoading(0);
								view_favoritesits.getPullToRefreshView().onHeaderRefreshComplete();
								OutOfFactoryActivity.this.ToastMessage("加载站点与车辆实时数据失败！");
							}
						});

					}

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							final List<StationVehicleRealTimeInfo> t) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								
								dismissLoading(0);
								view_favoritesits.getPullToRefreshView().onHeaderRefreshComplete();
								updateVehicleRealtimeInfo(t);
							}

						});

					}
				});
	}

	private void loadRecommendStations() {
		// showLoading("正在获取推荐站点", 0);
		biz.getRecommandStations(p_currentAreaType, p_currentRange,
				new BizResultProcess<List<StationInfo>>() {

					@Override
					public void onBizExecuteError(Exception exception,
							Error error) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								OutOfFactoryActivity.this.view_recommandStation
										.getPullToRefreshView()
										.onHeaderRefreshComplete();

							}
						});
					}

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							final List<StationInfo> t) {

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								OutOfFactoryActivity.this.view_recommandStation
										.getPullToRefreshView()
										.onHeaderRefreshComplete();
								recommendStations = t;
								view_recommandStation
										.setStations(recommendStations);
							}
						});

					}
				});

	}

	private void buildFavoriteSitesDataSource(List<StationInfo> stations) {

		if (stationVehicleRealTimeInfos == null)
			stationVehicleRealTimeInfos = new ArrayList<StationVehicleRealTimeInfo>();
		if (stations == null)
			return;
		stationVehicleRealTimeInfos.clear();

		for (int i = 0; i < stations.size(); i++) {
			StationVehicleRealTimeInfo info = new StationVehicleRealTimeInfo();
			info.stationInfo = stations.get(i);
			info.VehicleRealtimeInfos = getEmptyVehicleRealtime();

			stationVehicleRealTimeInfos.add(info);

		}

	}

	/* 初始化收藏站点列表 */
	private void initFavoriteView() {
		view_favoritesits = new ConFavoriteSites(this);
		view_favoritesits.setP_currentDate(p_currentDate);
		stationVehicleRealTimeInfos = new ArrayList<StationVehicleRealTimeInfo>();

		view_favoritesits.setDatas(stationVehicleRealTimeInfos, true);

		/*---------设置【联系司机】侦听----------*/
		view_favoritesits.setSiteContactDriverClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*用户行为统计-*/
	            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_SJMX).reportBehavior(null);
				
	            final VehicleRealtime rt = (VehicleRealtime) v.getTag(R.id.tag_realtimeinfo);
				Bundle bundle = new Bundle();
				bundle.putString(ActivityCommConstant.VEHICHLE, rt.vehicle_vin);
				bundle.putString(ActivityCommConstant.LINE_ID, rt.line_id);
				bundle.putString(ActivityCommConstant.VIHICLE_NUM, rt.vehicle_number);
				
				ActivityUtils.changeActivity(OutOfFactoryActivity.this, BusDriverDetailsActivity.class, bundle);
			}
		});
		
		/*---------设置【到站提醒】侦听----------*/
		view_favoritesits.setSiteBusClockClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final StationInfo site = (StationInfo) v
						.getTag(R.id.tag_station);
				final VehicleRealtime rt = (VehicleRealtime) v
						.getTag(R.id.tag_realtimeinfo);
				if (site == null || rt == null) {
					Logger.e(this.getClass(), "站点或VehicleRealtime为null。");
					return;
				}
				
				showLoading("正在获取提醒信息...", 0);

				final StatusRange c_range = p_currentRange;
				biz.GetRemindInfo(p_currentAreaType, site.id, p_currentRange,
						rt.vehicle_vin, new BizResultProcess<RemindInfo>() {
							// zhouzc 移至内部处理
							// final RemindInfo premindinfo = new
							// RemindInfo(p_currentAreaType, site.id,
							// p_currentRange, rt.vehicle_vin);

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
							public void onBizExecuteEnd(
									BizDataTypeEnum datatype, final RemindInfo t) {
								runOnUiThread(new Runnable() {

									@Override
									public void run() {
										dismissLoading(0);
										RemindInfo p = t;
										if (p == null) {

											// zhouzc 修改跳转到提醒设置界面的参数问题
											p = new RemindInfo();
											p.setId(null);
											p.setStation_id(site.getId());
											p.setStation_name(site.getName());
											p.setArea_type(site.getArea_type());
											p.setLine_range(LineRange.FactoryOuter);
											p.remind_range = RemindRange.StationAndVehiche;
											p.vehiche_vin = rt.vehicle_vin;
											p.vehiche_number = rt.vehicle_number;
											// p = premindinfo;

										}
										p.status_range = c_range;
										Bundle b = new Bundle();
										b.putSerializable(
												ActivityCommConstant.REMINDINFO,
												p);
										ActivityUtils
												.changeActivity(
														OutOfFactoryActivity.this,
														StationRemindSettingActivity.class,
														b);

									}
								});

							}
						});
			}
		});
		
		/*---------展开具体车辆【位置详情】监听----------*/
		view_favoritesits.setSiteBusMapClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				VehicleRealtime rt = (VehicleRealtime) v
						.getTag(R.id.tag_realtimeinfo);// 车辆实时信息
				StationInfo site = (StationInfo) v.getTag(R.id.tag_station);// 站点信息
				if (rt == null) {
					Logger.e(this.getClass(), "VehicleRealtime is null。");
					return;
				}

				/*用户行为统计-*/
	            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_CKDT).reportBehavior(null);
	            
				VehicheInfo vinfo = new VehicheInfo();
				vinfo.line_id = rt.line_id;
				vinfo.vehiche_number = rt.vehicle_number;
				vinfo.vehicle_vin = rt.vehicle_vin;

				// ToastMessage("日期："+view_favoritesits.getCalendarTime()+" 站点ID:"+site.getId()
				// +" 站点名称:"+site.getName());

				Bundle bundle = new Bundle();
				bundle.putSerializable(ActivityCommConstant.VEHICHLE, vinfo);
				bundle.putSerializable(ActivityCommConstant.STATION_INFO, site);
				bundle.putSerializable(ActivityCommConstant.STATUSRANGE,
						p_currentRange);
				bundle.putSerializable(ActivityCommConstant.DATE, DateUtils
						.strToDate(view_favoritesits.getCalendarTime(),
								"yyyy/MM/dd"));
				bundle.putSerializable(ActivityCommConstant.VEHICHLE_REALTIME,
						rt);

				ActivityUtils.changeActivity(OutOfFactoryActivity.this,
						SingleVehicleLinesActivity.class, bundle);
				
			}
		});
		
		
		view_favoritesits.setSiteClockClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final StationInfo site = (StationInfo) v
						.getTag(R.id.tag_station);
				if (site == null) {
					Logger.e(this.getClass(), "站点点击：站点为null。");
					return;
				}
				
				showLoading("正在获取提醒信息...", 0);
				final StatusRange crange = p_currentRange;
				biz.GetRemindInfo(p_currentAreaType, site.id, p_currentRange,
						null, new BizResultProcess<RemindInfo>() {

							// zhouzc 移至内部处理
							// final RemindInfo premindinfo = new RemindInfo(
							// p_currentAreaType, site.id, p_currentRange,
							// null);

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
							public void onBizExecuteEnd(
									BizDataTypeEnum datatype, final RemindInfo t) {
								runOnUiThread(new Runnable() {

									@Override
									public void run() {
										dismissLoading(0);
										RemindInfo p = t;
										if (p == null) {
											// p = premindinfo;
											// zhouzc 修改跳转到提醒设置界面的参数问题
											p = new RemindInfo();
											p.setId(null);
											p.setStation_id(site.getId());
											p.setStation_name(site.getName());
											p.setArea_type(site.getArea_type());
											p.setLine_range(LineRange.FactoryOuter);
											p.status_range = site
													.getStatus_range();
											p.remind_range = RemindRange.OnlyStation;
										}
										p.status_range = crange;
										{
											if (p.getLine_range() == null) {
												p.setLine_range(LineRange.FactoryOuter);
											}
										}

										Bundle b = new Bundle();
										b.putSerializable(
												ActivityCommConstant.REMINDINFO,
												p);
										ActivityUtils
												.changeActivity(
														OutOfFactoryActivity.this,
														StationRemindSettingActivity.class,
														b);

									}
								});

							}
						});
			}
		});
		view_favoritesits.setSiteFavorClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StationInfo site = (StationInfo) v.getTag(R.id.tag_station);
				if (site == null) {
					Logger.e(this.getClass(), "站点点击：站点为null。");
					return;
				}
				showFavoriteConfirmDialog(site, site.isFavorites());
			}
		});
		
		/*站点地图 GroupMap*/
		view_favoritesits.setSiteMapClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StationInfo site = (StationInfo) v.getTag(R.id.tag_station);
				if (site == null) {
					Logger.e(this.getClass(), "站点点击：站点为null。");
					return;
				}
				Bundle bundle = new Bundle();
				bundle.putSerializable(ActivityCommConstant.STATION_INFO, site);
				bundle.putSerializable(ActivityCommConstant.STATUSRANGE,
						p_currentRange);

				bundle.putSerializable(ActivityCommConstant.DATE, DateUtils
						.strToDate(view_favoritesits.getCalendarTime(),
								"yyyy/MM/dd"));

				ActivityUtils.changeActivity(OutOfFactoryActivity.this,
						SingleStationLinesActivity.class, bundle);
				
				/*用户行为统计-站点地图*/
	            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_CKDT).reportBehavior(null);
			}
		});
		
		
		view_favoritesits.setDateTimePickerListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				/*用户行为统计-切换日期*/
	            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_QHRQ).reportBehavior(null);
				
	            // TODO Auto-generated method stub
				Intent intent = new Intent(OutOfFactoryActivity.this,
						DateChooseActivity.class);
				Bundle bundle = new Bundle();

				bundle.putInt("year", DateUtils.getYear(p_currentDate));
				bundle.putInt("month", DateUtils.getMonth(p_currentDate));
				bundle.putInt("day", DateUtils.getDate(p_currentDate));
				intent.putExtras(bundle);

				startActivityForResult(intent, REQUEST_CODE);
			}
		});
		
		//点击展开进行监听
		view_favoritesits.setStationExpandListener(new StationExpandListener() {
			
//			Callback method to be invoked when a group in this expandable list has been expanded.
			@Override
			public void onStationExpand(StationInfo station) {
				
				loadVehicleRealtimeInfo(station, false);
			}
		});
		
		//筛选 侧边框
		view_favoritesits
				.setBusFilterClickListener(new BusFilterClickListener() {

					@Override
					public void onFilterClick(int index, String value) {
						
						loadVehicleRealtimeInfo(view_favoritesits.getP_currentStation(), true);
					}
				});
		
		view_favoritesits
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						
						
						/*用户行为统计-下拉刷新状态*/
			            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_SXZD).reportBehavior(null);
			            
						loadVehicleRealtimeInfo(
								view_favoritesits.getP_currentStation(), false);
						/*
						 * loadFavoriteStations(p_currentAreaType,
						 * p_currentRange);
						 */
					}
				});

		view_favoritesits.updateFilters(p_currentAreaType, p_currentRange);
	}

	private void initRecommendView() {
		view_recommandStation = new ConRecommandStation(this);
		recommendStations = new ArrayList<StationInfo>();

		view_recommandStation.setStations(recommendStations);
		view_recommandStation.setFavoriteClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StationInfo site = (StationInfo) v.getTag();
				if (site == null) {
					Logger.e(this.getClass(), "推荐站点点击：站点为null。");
					return;
				}

				OutOfFactoryActivity.this
						.showFavoriteConfirmDialog(site, false);
			}
		});
		view_recommandStation.setSiteClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StationInfo site = (StationInfo) v.getTag();
				if (site == null) {
					Logger.e(this.getClass(), "站点点击：站点为null。");
					return;
				}

				Bundle bundle = new Bundle();
				bundle.putSerializable(ActivityCommConstant.STATION_INFO, site);
				bundle.putBoolean(ActivityCommConstant.ISFAVOR, site.favorites);
				bundle.putSerializable(ActivityCommConstant.STATUSRANGE, p_currentRange);
				
				// bundle.putBoolean(ActivityCommConstant.ISFAVOR, false);
				AnimCommon.set(R.anim.enter_righttoleft,
						R.anim.exit_righttoleft);

				ActivityUtils.changeActivity(OutOfFactoryActivity.this,
						SingleStationScheduleActivity.class, bundle);
			}
		});
		view_recommandStation
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						OutOfFactoryActivity.this.loadRecommendStations();

					}
				});
	}

	/**
	 * 切换视图
	 * 
	 * @author zhangyongn 2013-11-12 上午11:16:23
	 */
	private void switchView() {
		try {
			ll_main_content.removeAllViews();
			if (hasFavoriteSites()) {
				view_recommandStation.switchOut();
				ll_main_content.addView(view_favoritesits);
				view_favoritesits.switchIn();

			} else {
				view_favoritesits.switchOut();
				ll_main_content.addView(view_recommandStation);
				view_recommandStation.switchIn();
			}
		} catch (Exception err) {
			Logger.e(getClass(), err.getMessage());
		}
	}

	private void initListeners() {
		
		toggleRadio.SetToggleListener(new IToggleListener() {

			@Override
			public void onToggle(ToggleOption option) {
				
				/*用户行为统计-切换早晚班*/
	            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_QHZWB).reportBehavior(null);
				
	            view_favoritesits.setLastSelectedindex(0);
				p_currentRange = option==ToggleOption.Left ? StatusRange.MorningWork: StatusRange.NightWork;
				view_favoritesits.updateFilters(p_currentAreaType,p_currentRange);
				onStatusRangeChanged(p_currentRange);
			}
		});
	}

	/**
	 * 早班晚班切换选择变化的处理
	 * 
	 * @author zhangyongn 2013-11-12 下午5:06:48
	 * @param range
	 */
	protected void onStatusRangeChanged(StatusRange range) {
		loadFavoriteStations(p_currentAreaType, range);

	}

	protected void showFavoriteConfirmDialog(final StationInfo site,
			final boolean isFavor) {
		if (site == null)
			return;

		String title = isFavor ? "取消收藏" : "添加收藏";
		String body = isFavor ? "是否取消收藏？" : "是否添加收藏？";
		CustomDialog dia = new CustomDialog.Builder(OutOfFactoryActivity.this)
				.setTitle(title)
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						/*用户行为统计-收藏或取消站点*/
			            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_SCZD).reportBehavior(null);
						dialog.dismiss();
						SubmitFavorite(site, !isFavor);
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
	protected void SubmitFavorite(StationInfo site, boolean favor) {
		BizOutOfFactory biz = new BizOutOfFactory(this);
		biz.setStationFavority(site.id, favor, new BizResultProcess<Boolean>() {

			@Override
			public void onBizExecuteError(Exception exception, Error error) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						ToastMessage("操作失败！");

					}
				});

			}

			@Override
			public void onBizExecuteEnd(BizDataTypeEnum datatype, Boolean t) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						loadFavoriteStations(p_currentAreaType, p_currentRange);

					}
				});

			}
		});
	}

	private int lastSelected = -1;

	private void initFactorySetPopUp() {

		popcl = new PopCheckList(this);
		popcl.setOnCheckChangedListener(new OnCheckChangedListener() {

			@Override
			public void OnChecked(int index, String value) {

				if (lastSelected != index) {
					
					
					lastSelected = index;
					p_currentAreaType = (index == 0) ? AreaType.FirstFactory
							: AreaType.SecondFactory;
					view_favoritesits.updateFilters(p_currentAreaType,
							p_currentRange);
					onAreaTypeChanged(p_currentAreaType);
				}

				popcl.dismiss();

			}
		});
		popcl.setOnDismisslistener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				// ToastMessage("取消");
			}
		});

	}

	/**
	 * 厂区选择改变的对应处理
	 * 
	 * @author zhangyongn 2013-11-12 下午4:54:24
	 * @param type
	 */
	private void onAreaTypeChanged(AreaType type) {
		setTitleForFactory(txt_small, type);
		
//		//清空数据
//		stationVehicleRealTimeInfos.clear();
//		view_favoritesits.setDatas(stationVehicleRealTimeInfos, true);
		
		loadFavoriteStations(type, p_currentRange);
	}

	private void initViews() {
		ll_main_content = (LinearLayout) findViewById(R.id.ll_main_contnet);
		toggleRadio = (ToggleRadio) findViewById(R.id.workplanradio);
		toggleRadio.setOptionTexts(new String[] { "早班", "晚班" });
		toggleRadio
				.setSelectedOption((p_currentRange == StatusRange.MorningWork) ? ToggleOption.Left
						: ToggleOption.Right);

	}

	public void refreshRealVehicleInfo() {
		if (view_favoritesits != null)
			loadFavoriteStations(p_currentAreaType, p_currentRange);
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}
	
	private boolean isFromActivityResult  = false;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		isFromActivityResult  = false;
		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				isFromActivityResult  = true;
				Bundle bundle = data.getExtras();
				String dt = bundle.getInt("year") + "-"
						+ (bundle.getInt("month")) + "-" + bundle.getInt("day");
				p_currentDate = DateUtils.strToDate(dt, "yyyy-MM-dd");
				view_favoritesits.setP_currentDate(p_currentDate);

				loadVehicleRealtimeInfo(
						view_favoritesits.getP_currentStation(), true);
			}
		}
	}

	@Override
	protected void loadTitleByPageSetting(Button btn_left, Button btn_right,
			RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
			TextView tv_small) {
		btn_left.setBackgroundResource(R.drawable.bg_settingbtn);
		btn_right.setBackgroundResource(R.drawable.bg_searchbtn);

		tv_large.setText("厂外通勤");

		rl_center.setOnClickListener(this);
		btn_left.setOnClickListener(this);
		btn_right.setOnClickListener(this);

		txt_small = tv_small;
		// AreaType f = PrefDataUtil.getFactory(this);
		//
		// setTitleForFactory(txt_small, f);
	}

	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_it_left:
			Intent gotosetting = new Intent(OutOfFactoryActivity.this,
					SettingActivity.class);
			gotosetting.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			startActivity(gotosetting);
			break;
		case R.id.btn_it_right:
			
			/*用户行为统计-厂外通勤查找站点页面进入*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_CZZD).reportBehavior(null);
            
			Bundle b = new Bundle();
			b.putSerializable(ActivityCommConstant.STATUSRANGE, p_currentRange);
			b.putSerializable(ActivityCommConstant.AREATYPE, p_currentAreaType);
			ActivityUtils.changeActivity(OutOfFactoryActivity.this,
					StationSearchActivity.class, b);
			break;
		case R.id.rl_it_center:
			showOrDismissPopLines();
			break;
		default:
			break;
		}
	}

	private void showOrDismissPopLines() {
		if (popcl != null) {
			View center = findViewById(R.id.rl_it_center);
			int xoffset = (center.getWidth() - popcl.getWidth()) / 2;
			popcl.showAsDropDown(findViewById(R.id.rl_it_center), xoffset, 2);
		}
	}

	@Override
	public boolean isForceToDestory() {
		return super.isForceToDestory();

	}
}
