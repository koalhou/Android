/**
 * @公司名称：YUTONG
 * @作者：zhangyongn
 * @版本号：1.0
 * @生成日期：2013-11-6 下午2:14:52
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.common.beans.line.StationVehicleRealTimeInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.ScheduleItemType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.view.bean.IViewSwitcher;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.control.BusScheduleExpListAdapter.BusScheduleItemClickListener;
import com.yutong.clw.ygbclient.view.util.DensityUtil;
import com.yutong.clw.ygbclient.view.widget.PopCheckList;
import com.yutong.clw.ygbclient.view.widget.PopCheckList.OnCheckChangedListener;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView.OnFooterRefreshListener;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView.OnHeaderRefreshListener;

/**
 * @author zhangyongn 2013-11-6 下午2:14:52
 */
public class ConFavoriteSites extends LinearLayout implements OnClickListener,
		IViewSwitcher {
	/**
	 * 当前筛选项
	 */
	private int p_currentFilterIndex = 0;

	/**
	 * 当前日期
	 */
	private Date p_currentDate = DateUtils.getCurDate();

	/**
	 * 当前展开站点
	 */
	private StationInfo p_currentStation;

	/**
	 * 当前展开的组索引
	 */
	private int p_currentExpandedGroudIndex = 0;

	/**
	 * 上一个展开的组
	 */
	private int lastExpandedGroupIndex = -1;

	/**
	 * 筛选
	 */
	private TextView tv_filter;

	private Button btn_amb_calendar;

	private TextView tv_amb_time;

	private PopCheckList popcl;

	private int popCheckListWidth = DensityUtil.dip2px(getContext(), 100);

	private List<String> filteroptions = Arrays.asList("全部", "未进站", "已进站",
			"直达技术北楼");

	private ExpandableListView expandableListView;

	private BusScheduleExpListAdapter adapter;

	/** 下拉刷新对象 */
	private PullToRefreshView pullToRefreshView;

	/**
	 * 站点切换侦听器
	 */
	private StationExpandListener stationExpandListener;

	/**
	 * 筛选项点击侦听
	 */
	private BusFilterClickListener busFilterClickListener;
	
	private int lastSelectedindex = 0;

	public ConFavoriteSites(Context context) {
		this(context, null);

	}

	public ConFavoriteSites(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode())
			return;
		LayoutInflater.from(context).inflate(
				R.layout.control_main_outoffactory_favoritesite, this, true);
		initViews();

		registerListener();
		initFilters();
		initListView();

	}

	/**
	 * 设置日期选择侦听
	 * 
	 * @author zhangyongn 2013-11-13 上午10:30:41
	 * @param listener
	 */
	public void setDateTimePickerListener(View.OnClickListener listener) {
		if (btn_amb_calendar == null)
			btn_amb_calendar = (Button) this
					.findViewById(R.id.btn_amb_calendar);
		btn_amb_calendar.setOnClickListener(listener);
		if (tv_amb_time == null)
			tv_amb_time = (TextView) this.findViewById(R.id.tv_amb_time);
		tv_amb_time.setOnClickListener(listener);
	}

	/**
	 * 设置站点收藏侦听
	 * 
	 * @author zhangyongn 2013-11-13 上午10:30:41
	 * @param listener
	 */
	public void setSiteFavorClickListener(View.OnClickListener listener) {
		adapter.setSiteFavorClickListener(listener);
	}

	/**
	 * 设置站点【联系司机】设置侦听
	 * 
	 * @param listener
	 */
	public void setSiteContactDriverClickListener(View.OnClickListener listener) {
		adapter.setSiteBusContactDriverClickListener(listener);
	}
	
	/**
	 * 设置站点闹钟设置侦听
	 * 
	 * @author zhangyongn 2013-11-13 上午10:30:41
	 * @param listener
	 */
	public void setSiteClockClickListener(View.OnClickListener listener) {
		adapter.setSiteClockClickListener(listener);
	}

	/**
	 * 设置站点地图侦听
	 * 
	 * @author zhangyongn 2013-11-13 上午10:30:41
	 * @param listener
	 */
	public void setSiteMapClickListener(View.OnClickListener listener) {
		adapter.setSiteMapClickListener(listener);

	}

	/**
	 * 设置站点的某一班车的闹钟设置侦听
	 * 
	 * @author zhangyongn 2013-11-13 上午10:30:41
	 * @param listener
	 */
	public void setSiteBusClockClickListener(View.OnClickListener listener) {
		adapter.setSiteBusClockClickListener(listener);
	}

	/**
	 * 设置站点的某一班车地图侦听
	 * 
	 * @author zhangyongn 2013-11-13 上午10:30:41
	 * @param listener
	 */
	public void setSiteBusMapClickListener(View.OnClickListener listener) {
		adapter.setSiteBusMapClickListener(listener);
	}

	/**
	 * 设置下拉刷新侦听
	 * 
	 * @author zhangyongn 2013-11-12 下午2:03:33
	 * @param listener
	 */
	public void setOnHeaderRefreshListener(OnHeaderRefreshListener listener) {
		if (getPullToRefreshView() != null)
			getPullToRefreshView().setOnHeaderRefreshListener(listener);
	}

	/**
	 * 设置上拉侦听
	 * 
	 * @author zhangyongn 2013-11-12 下午2:03:45
	 * @param listener
	 */
	public void setOnFooterRefreshListener(OnFooterRefreshListener listener) {
		if (getPullToRefreshView() != null)
			getPullToRefreshView().setOnFooterRefreshListener(listener);
	}

	/**
	 * 站点切换侦听
	 * 
	 * @author zhangyongn 2013-11-13 上午11:25:39
	 * @param listener
	 */
	public void setStationExpandListener(StationExpandListener listener) {
		stationExpandListener = listener;
	}

	/**
	 * 筛选项选择侦听
	 * 
	 * @author zhangyongn 2013-11-13 上午11:25:54
	 * @param listener
	 */
	public void setBusFilterClickListener(BusFilterClickListener listener) {
		busFilterClickListener = listener;
	}
	
	private void initFilters() {
		
		popcl = new PopCheckList(YtApplication.getInstance());

		popcl.setOnCheckChangedListener(new OnCheckChangedListener() {

			@Override
			public void OnChecked(int index, String value) {
				
				popcl.dismiss();
				if(lastSelectedindex == index){
					return;
				}
				
				/*用户行为统计-厂外通勤筛选*/
	            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_SX).reportBehavior(null);
				
	            lastSelectedindex = index;
				setP_currentFilterIndex(index);
				if (busFilterClickListener != null)
					busFilterClickListener.onFilterClick(index, value);
			}
		});
		
		popcl.setOnDismisslistener(new OnDismissListener() {

			@Override
			public void onDismiss() {

			}
		});

		lastSelectedindex =  getP_currentFilterIndex();
		popcl.setData(filteroptions,lastSelectedindex);

	}

	StatusRange currentStatusRange = StatusRange.MorningWork;

	public void updateFilters(AreaType type, StatusRange range) {
		
		Log.i("TAB", "popcl.getSelectionValue():"+popcl.getSelectionValue());
		
		currentStatusRange = range;
		List<String> f;
		if (range == StatusRange.NightWork) {
			f = new ArrayList<String>() {
				{
					add("全部");
					add("未发车");
					add("已发车");
				}
			};
			if (f.contains(popcl.getSelectionValue())) {
				setP_currentFilterIndex(f.indexOf(popcl.getSelectionValue()));
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
					&& range == StatusRange.MorningWork) {
				f.add("直达技术北楼");
				if (f.contains(popcl.getSelectionValue())) {
					setP_currentFilterIndex(f
							.indexOf(popcl.getSelectionValue()));
				} else {
					setP_currentFilterIndex(0);
				}
			}
		}
		popcl.setData(f, getP_currentFilterIndex());
	}

	private void initListView() {
		expandableListView.setGroupIndicator(null);
		adapter = new BusScheduleExpListAdapter();
		expandableListView.setAdapter(adapter);

		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				int childcnt = adapter.getChildrenCount(groupPosition);
				if (childcnt <= 0)
					return true;
				return false;
			}
		});
		
		expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {

//			Callback method to be invoked when a group in this expandable list has been expanded.
			@Override
			public void onGroupExpand(int groupPosition) {
				p_currentExpandedGroudIndex = groupPosition;
				for (int i = 0; i < adapter.getGroupCount(); i++) {
					if (groupPosition != i) {
						expandableListView.collapseGroup(i);
					}
				}
				try {
					StationInfo st = ((StationVehicleRealTimeInfo) adapter.getGroup(groupPosition)).stationInfo;
					setP_currentStation(st);
					if (stationExpandListener != null){
						stationExpandListener.onStationExpand(st);
					}
					
				} catch (Exception e) {
					Logger.e(getClass(), e.getMessage());
				}
			}
		});

		adapter.setBusScheduleItemClickListener(new BusScheduleItemClickListener() {

			@Override
			public void onItemClick(int groupPosition, int childPosition) {
				VehicleRealtime childdata = (VehicleRealtime) adapter.getChild(
						groupPosition, childPosition);
				StationVehicleRealTimeInfo groupdata = (StationVehicleRealTimeInfo) adapter
						.getGroup(groupPosition);
				if (childdata == null)
					return;
				if (childdata.itemType == ScheduleItemType.Tip)
					return;
				if (!childdata.showOP) {
					childdata.showOP = true;
					for (int i = 0; i < groupdata.VehicleRealtimeInfos.size(); i++) {
						VehicleRealtime item = groupdata.VehicleRealtimeInfos
								.get(i);
						if (item != childdata) {
							item.showOP = false;
						}
					}
				} else {
					childdata.showOP = false;
				}
				adapter.notifyDataSetChanged();

			}
		});

	}

	private void registerListener() {
		tv_filter.setOnClickListener(this);

	}

	private void initViews() {
		tv_filter = (TextView) findViewById(R.id.tv_filter);
		expandableListView = (ExpandableListView) findViewById(R.id.explistview);
		pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
		getPullToRefreshView().setPullUpEnable(false);

		btn_amb_calendar = (Button) this.findViewById(R.id.btn_amb_calendar);
		tv_amb_time = (TextView) this.findViewById(R.id.tv_amb_time);
	}

	public void setDatas(List<StationVehicleRealTimeInfo> datas,
			boolean needExpandGroup) {

		this.adapter.setDatas(datas);
		this.adapter.notifyDataSetChanged();
		
		expandableListView.setSelectionAfterHeaderView();
		expandableListView.setSelectedGroup(p_currentExpandedGroudIndex);
		expandableListView.setSelected(true);
		
		
		if (needExpandGroup) {
			if (datas != null && datas.size() > 0) {
				setP_currentStation(datas.get(0).stationInfo);
				/*if (p_currentExpandedGroudIndex > 0
						&& p_currentExpandedGroudIndex < datas.size()) {
					listView.expandGroup(p_currentExpandedGroudIndex);
				} else {
					listView.expandGroup(0);
				}*/
				expandableListView.expandGroup(0);
			}
		}
	}

	private void showOrDismissPopFilter() {
		if (popcl != null) {

			int offsetx = -popcl.getWidth() - tv_filter.getWidth();
			int offsety = -popcl.getHeight();
			if ((tv_filter.getTop() + tv_filter.getHeight() / 2) < ((View) tv_filter
					.getParent()).getHeight() / 2) {
				offsety += (popcl.getHeight() - tv_filter.getHeight());
				offsety += DensityUtil.dip2px(getContext(), 3);
			} else {
				offsety -= DensityUtil.dip2px(getContext(), 3);
			}

			Logger.i(getClass(), "showOrDismissPopFilter offsetx:" + offsetx
					+ " ,offsety:" + offsety);
			popcl.showAsDropDown(tv_filter, offsetx, offsety);

			// popcl.showAtLocation(this, Gravity.NO_GRAVITY, 100,100);

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.tv_filter:
			showOrDismissPopFilter();
			break;
			
		default:
			break;
		}
	}

	@Override
	public void switchOut() {
		// TODO Auto-generated method stub

	}

	@Override
	public void switchIn() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return pullToRefreshView
	 */
	public PullToRefreshView getPullToRefreshView() {
		return pullToRefreshView;
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
				.getFormatTime(p_currentDate, "yyyy/MM/dd"));
		this.adapter.setCurDate(p_currentDate);
		this.adapter.notifyDataSetChanged();
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
	}

	/**
	 * 站点切换侦听
	 * 
	 * @author zhangyongn 2013-11-13 上午11:12:59
	 */
	public interface StationExpandListener {
		/**
		 * 站点切换
		 * 
		 * @author zhangyongn 2013-11-13 上午11:19:38
		 * @param station
		 *            切换后的station
		 */
		void onStationExpand(StationInfo station);
	}

	/**
	 * 筛选项点击侦听
	 * 
	 * @author zhangyongn 2013-11-13 上午11:18:39
	 */
	public interface BusFilterClickListener {
		/**
		 * 筛选项点击处理
		 * 
		 * @author zhangyongn 2013-11-13 上午11:19:03
		 * @param index
		 *            筛选项索引
		 * @param value
		 *            筛选项值
		 */
		void onFilterClick(int index, String value);
	}

	public String getCalendarTime() {
		return tv_amb_time.getText().toString();
	}

	public int getLastSelectedindex() {
		return lastSelectedindex;
	}

	public void setLastSelectedindex(int lastSelectedindex) {
		this.lastSelectedindex = lastSelectedindex;
	}
}
