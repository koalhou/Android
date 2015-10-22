package com.yutong.clw.ygbclient.view.pages.setting.remindCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.enums.remind.RemindType;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.setting.BizSetting;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation.StationRemindSettingActivity;
import com.yutong.clw.ygbclient.view.util.DensityUtil;
import com.yutong.clw.ygbclient.view.util.GraphicUtil;
import com.yutong.clw.ygbclient.view.widget.PopCheckList;
import com.yutong.clw.ygbclient.view.widget.PopCheckList.OnCheckChangedListener;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.yutong.clw.ygbclient.view.widget.ToggleRadio;
import com.yutong.clw.ygbclient.view.widget.ToggleRadio.IToggleListener;
import com.yutong.clw.ygbclient.view.widget.ToggleRadio.ToggleOption;

/**
 * 站点提醒收藏界面
 * 
 * @author zhouzc
 */
public class RemindCollectionActivity extends RemindAccessActivity implements
		OnClickListener {

	/*********************/
	private BizSetting biz;

	private PopCheckList popcl;

	private PullToRefreshView pullToRefreshView_in;

	private PullToRefreshView pullToRefreshView_out;

	private ListView lv_in;

	private ListView lv_out;

	private ToggleRadio cr_dayornight;

	private TextView txt_small;

	private Button btn_cancelEdit;

	private Button btn_delete;

	private Button btn_allselect;

	private View v_selection;

	private InListAdapter inadApter;

	private OutListAdapter outAdapter;

	private List<InListItem> inData;

	private List<OutListItem> outData;

	private AreaType currentFactory = null;

	private ToggleOption currentIndex = ToggleOption.Left;

	private int station_hint_textSizeToBig = 16;


	/*********************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_remindcollection);
		initAreaType();
		initCurrentIndex();
		initBiz();
		initViews();
	}

	private void initCurrentIndex() {
		try {
			currentIndex = (ToggleOption) getIntent().getExtras()
					.getSerializable(ActivityCommConstant.TOGGLEOPTION);

		} catch (Exception e) {
			currentIndex = ToggleOption.Left;
		}

	}

	private void initBiz() {
		biz = new BizSetting(this);
	}

	private void initViews() {

		pullToRefreshView_in = (PullToRefreshView) findViewById(R.id.pullToRefreshView_in);
		pullToRefreshView_out = (PullToRefreshView) findViewById(R.id.pullToRefreshView_out);
		pullToRefreshView_in.setPullUpEnable(false);
		pullToRefreshView_out.setPullUpEnable(false);
		pullToRefreshView_in
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						refreshData();

					}
				});
		pullToRefreshView_out
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						refreshData();

					}
				});

		popcl = new PopCheckList(this);
		int index = (currentFactory == AreaType.FirstFactory) ? 0 : 1;

		popcl.setData(Arrays.asList("一厂", "二厂"), index);
		txt_small.setText((index == 0) ? "一厂" : "二厂");
		popcl.setOnCheckChangedListener(new OnCheckChangedListener() {
			@Override
			public void OnChecked(int index, String value) {
				popcl.dismiss();
				txt_small.setText(value);

				currentFactory = (index == 0) ? AreaType.FirstFactory
						: AreaType.SecondFactory;
				cancelSelectionMode();
				refreshData();
			}
		});
		v_selection = findViewById(R.id.ll_asr_edit);
		v_selection.setVisibility(View.GONE);

		btn_delete = (Button) findViewById(R.id.btn_asr_delete);
		btn_allselect = (Button) findViewById(R.id.btn_asr_allselect);
		btn_delete.setOnClickListener(this);
		btn_allselect.setOnClickListener(this);

		cr_dayornight = (ToggleRadio) findViewById(R.id.cr_asr);
		cr_dayornight.setOptionTexts(new String[] { "厂内", "厂外" });
		cr_dayornight.setSelectedOption(currentIndex);
		cr_dayornight.SetToggleListener(new IToggleListener() {
			@Override
			public void onToggle(ToggleOption option) {
				cancelSelectionMode();
				currentIndex = option;
				switch (option) {
				case Left:

					pullToRefreshView_in.setVisibility(View.VISIBLE);
					pullToRefreshView_in.bringToFront();
					pullToRefreshView_out.setVisibility(View.GONE);
					outAdapter.cancelEditMode();
					break;
				case Right:

					pullToRefreshView_out.setVisibility(View.VISIBLE);
					pullToRefreshView_out.bringToFront();
					pullToRefreshView_in.setVisibility(View.GONE);
					inadApter.cancelEditMode();
					break;
				}
				refreshData();
			}
		});

		lv_in = (ListView) findViewById(R.id.lv_asr_in);
		lv_out = (ListView) findViewById(R.id.lv_asr_out);

		inData = new ArrayList<RemindCollectionActivity.InListItem>();
		outData = new ArrayList<RemindCollectionActivity.OutListItem>();
		inadApter = new InListAdapter(this, inData);
		outAdapter = new OutListAdapter(this, outData);
		
		lv_in.setAdapter(inadApter);
		lv_out.setAdapter(outAdapter);
		lv_in.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 厂内暂时不加跳转
			}
		});
		lv_out.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent toSettting = new Intent(RemindCollectionActivity.this,
						StationRemindSettingActivity.class);
				toSettting.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
				Bundle data = new Bundle();

				data.putSerializable(ActivityCommConstant.REMINDINFO,
						outAdapter.getItem(arg2).RemindInfo);

				toSettting.putExtras(data);
				startActivity(toSettting);
			}
		});

		inadApter
				.setOnEditSelectionChangedListener(new OnEditSelectionChangedListener() {
					@Override
					public void OnSelectionChanged() {

					}
				});

		lv_in.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (!inadApter.inSelectionMode)
					gotoSelectionMode();
				return true;
			}
		});
		lv_out.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (!outAdapter.inSelectionMode)
					gotoSelectionMode();
				return true;
			}
		});
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
		btn_right.setBackgroundResource(R.drawable.bg_bluebtn);
		btn_right.setWidth(DensityUtil.dip2px(getApplicationContext(), 72));
		btn_right.setHeight(DensityUtil.dip2px(getApplicationContext(), 36));
		btn_right.setText("取消");
		btn_right.setVisibility(View.INVISIBLE);
		tv_large.setText("到站提醒");
		tv_small.setText("一厂");

		btn_right.setOnClickListener(this);
		btn_left.setOnClickListener(this);
		rl_center.setOnClickListener(this);
		this.btn_cancelEdit = btn_right;
		this.txt_small = tv_small;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_it_left:
			finish();
			break;
		case R.id.btn_it_right:
			cancelSelectionMode();
			break;
		case R.id.rl_it_center:
			showOrDismissPopLines();
			break;
		case R.id.btn_asr_allselect:
			if (currentIndex == ToggleOption.Left) {
				inadApter.setAllSelection(true);
			} else {
				outAdapter.setAllSelection(true);
			}
			break;
		case R.id.btn_asr_delete:
			// TODO 删除
			if ((currentIndex == ToggleOption.Left && inadApter
					.getCurrentSelections().size() == 0)
					|| (currentIndex == ToggleOption.Right && outAdapter
							.getCurrentSelections().size() == 0)) {
				ToastMessage("请选择需要删除的提醒");
				return;
			}
			deleteReminds();
			/*用户行为统计-*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_QXTX).reportBehavior(null);
			
			break;
		default:
			break;
		}
	}

	private void deleteReminds() {
		List<RemindInfo> infos = new LinkedList<RemindInfo>();
		List<Integer> mselections = null;
		final ToggleOption cindex = currentIndex;
		switch (cindex) {
		case Left: {
			mselections = inadApter.getCurrentSelections();
			for (Integer num : mselections) {
				infos.add(inadApter.getItem(num.intValue()).RemindInfo);
			}
		}
			break;
		case Right: {
			mselections = outAdapter.getCurrentSelections();
			for (Integer num : mselections) {
				infos.add(outAdapter.getItem(num.intValue()).RemindInfo);
			}
		}
			break;
		}
		Collections.sort(mselections);
		final List<Integer> fselections = mselections;

		showLoading("删除提醒中...", 2);
		biz.deleteReminds(infos, new BizResultProcess<Boolean>() {
			@Override
			public void onBizExecuteError(Exception exception, Error error) {
				dismissLoading(2);
				ToastMessage("删除提醒失败");
				HandleLogicErrorInfo(exception);
			}

			@Override
			public void onBizExecuteEnd(BizDataTypeEnum datatype, Boolean t) {
				dismissLoading(2);
				ToastMessage("提醒删除成功");
				runOnUiThread(new Runnable() {
					public void run() {
						cancelSelectionMode();
						switch (cindex) {
						case Left:
							inadApter.removeItems(fselections);
							break;
						case Right:
							outAdapter.removeItems(fselections);
							break;
						}
					}
				});
			}
		});
	}

	private void gotoSelectionMode() {
		btn_cancelEdit.setVisibility(View.VISIBLE);
		v_selection.setVisibility(View.VISIBLE);
		if (currentIndex == ToggleOption.Left) {
			inadApter.turnToEditMode();
		} else {
			outAdapter.turnToEditMode();
		}
	}

	private void cancelSelectionMode() {
		Logger.i(getClass(), "取消列表编辑模式");
		btn_cancelEdit.setVisibility(View.INVISIBLE);
		v_selection.setVisibility(View.GONE);
		if (currentIndex == ToggleOption.Left) {
			Logger.i(getClass(), "取消厂内列表编辑模式");
			inadApter.cancelEditMode();
		} else {
			Logger.i(getClass(), "取消厂外列表编辑模式");
			outAdapter.cancelEditMode();
		}
	}

	private void showOrDismissPopLines() {
		if (popcl != null) {
			View center = findViewById(R.id.rl_it_center);
			int xoffset = (center.getWidth() - popcl.getWidth()) / 2;
			popcl.showAsDropDown(findViewById(R.id.rl_it_center), xoffset, 2);
		}
	}

	private void loadInFactoryReminds() {
		Logger.i(getClass(), "开始刷新厂内提醒");
		BizDataTypeEnum ret = biz.getReminds(LineRange.FactoryInside,
				currentFactory, new BizResultProcess<List<RemindInfo>>() {
					@Override
					public void onBizExecuteError(final Exception exception,
							Error error) {
						RemindCollectionActivity.this
								.runOnUiThread(new Runnable() {
									@Override
									public void run() {
										Logger.i(getClass(), "刷新厂内提醒失败");
										pullToRefreshView_in
												.onHeaderRefreshComplete();
										ToastMessage("厂内提醒获取失败");
										HandleLogicErrorInfo(exception);
									}
								});
					}

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							final List<RemindInfo> t) {
						RemindCollectionActivity.this
								.runOnUiThread(new Runnable() {
									@Override
									public void run() {
										Logger.i(getClass(), "刷新厂内提醒成功");
										pullToRefreshView_in
												.onHeaderRefreshComplete();
										inData.clear();
										List<RemindInfo> remindInfoList = t;
										if (remindInfoList != null
												&& remindInfoList.size() > 0) {
											for (RemindInfo info : remindInfoList) {
												if (info.getRemind_status() != RemindStatus.Open)
													continue;
												inData.add(new InListItem(
														info.line_name,
														info.station_name, info));

											}
										}

										lv_in.post(new Runnable() {

											@Override
											public void run() {
												inadApter
														.notifyDataSetChanged();
											}
										});
									}
								});

					}
				});
		if (ret == BizDataTypeEnum.FromNetwork) {
			// showLoading("正在获取数据...", 0);
			pullToRefreshView_in.setHeaderRefreshing();
		}
	}

	private void loadOutOfFactoryReminds() {

		BizDataTypeEnum ret = biz.getReminds(LineRange.FactoryOuter,
				currentFactory, new BizResultProcess<List<RemindInfo>>() {

					@Override
					public void onBizExecuteError(final Exception exception,
							Error error) {
						RemindCollectionActivity.this
								.runOnUiThread(new Runnable() {
									@Override
									public void run() {
										pullToRefreshView_out
												.onHeaderRefreshComplete();
										ToastMessage("厂外提醒获取失败");
										HandleLogicErrorInfo(exception);
									}
								});
					}

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							final List<RemindInfo> t) {
						RemindCollectionActivity.this
								.runOnUiThread(new Runnable() {
									@Override
									public void run() {
										pullToRefreshView_out
												.onHeaderRefreshComplete();
										// 数据处理
										outData.clear();
										if (t != null && t.size() > 0) {
											for (RemindInfo info : t) {
												try {
													String remindstr = "提前"
															+ info.getRemind_value()
															+ (info.getRemind_type() == RemindType.Date ? "分钟"
																	: (info.getRemind_type() == RemindType.Distance ? "米"
																			: "站"));

													String status_range = "";
													switch (info.status_range) {
													case MorningWork:
														status_range = "早班";
														break;
													case NightWork:
														status_range = "晚班";
														break;
													case AllWork:
														status_range = "早班/晚班";
														break;
													}

													outData.add(new OutListItem(
															info.station_name,
															status_range,
															remindstr,
															info.getRemind_range() == RemindRange.OnlyStation ? "经过该站点的全部车辆"
																	: info.getVehiche_number(),
															info.remind_status != RemindStatus.Open,
															info));

												} catch (Exception err) {
													err.printStackTrace();
												}
											}
										}

										lv_out.post(new Runnable() {
											@Override
											public void run() {
												outAdapter
														.notifyDataSetChanged();
											}
										});

									}
								});

					}
				});
		if (ret == BizDataTypeEnum.FromNetwork) {
			// showLoading("正在获取数据...", 0);
			pullToRefreshView_out.setHeaderRefreshing();
		}
	}

	private void refreshData() {
		if (currentIndex == ToggleOption.Left) {
			loadInFactoryReminds();
		} else {
			loadOutOfFactoryReminds();
		}
	}

	private void initAreaType() {
		try {
			AreaType type = (AreaType) getIntent().getExtras().getSerializable(
					ActivityCommConstant.AREATYPE);
			if (type != null)
				currentFactory = type;
			else {
				currentFactory = PrefDataUtil.getFactory(this);
			}
		} catch (Exception e) {
			currentFactory = PrefDataUtil.getFactory(this);
		}
	}

	@Override
	protected void onResume() {

		super.onResume();
		refreshData();
	}

	/**************************/

	class InListItem {
		public InListItem(String line, String station, RemindInfo info) {
			this.LineName = line;
			this.StationName = station;
			this.RemindInfo = info;
		}

		public RemindInfo RemindInfo;

		public String LineName = "";

		public String StationName = "";

		private boolean checked = false;

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}
	}

	class InListAdapter extends BaseAdapter {

		List<InListItem> mData;

		Context mContext;

		boolean inSelectionMode = false;

		OnEditSelectionChangedListener listener;

		public List<Integer> getCurrentSelections() {
			List<Integer> selections = new ArrayList<Integer>();
			for (int i = 0; i < mData.size(); i++) {
				if (mData.get(i).isChecked())
					selections.add(i);
			}
			return selections;
		}

		public InListAdapter(Context context, List<InListItem> data) {
			this.mContext = context;
			this.mData = data;
		}

		@Override
		public int getCount() {
			return mData == null ? 0 : mData.size();
		}

		@Override
		public InListItem getItem(int arg0) {
			return mData.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder holder = null;
			if (arg1 == null) {
				arg1 = View.inflate(mContext,
						R.layout.control_remindlist_item_infactory, null);
				holder = new ViewHolder();
				holder.LineName = (TextView) arg1.findViewById(R.id.tv_cri_tl1);
				holder.StationName = (TextView) arg1
						.findViewById(R.id.tv_cri_bl);
				holder.Selected = (CheckBox) arg1
						.findViewById(R.id.cb_cri_checked);
				holder.Selected.setFocusable(false);
				holder.Selected.setClickable(false);
				holder.LineName.setVisibility(View.VISIBLE);
				holder.StationName.setVisibility(View.VISIBLE);
				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}
			View body = arg1.findViewById(R.id.rl_cri_body);
			body.setTag(arg0);
			InListItem item = mData.get(arg0);

			if (inSelectionMode) {
				body.setEnabled(true);
				holder.Selected.setVisibility(View.VISIBLE);
				holder.Selected.setChecked(item.checked);
				body.setFocusable(true);
				body.setClickable(true);
				body.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						int index = (Integer) arg0.getTag();
						CheckBox cb = (CheckBox) arg0
								.findViewById(R.id.cb_cri_checked);
						boolean flag = cb.isChecked();
						cb.setChecked(!flag);
						changeSelection(index, !flag);
					}
				});
			} else {
				body.setEnabled(false);
				holder.Selected.setVisibility(View.GONE);
				body.setOnClickListener(null);
				body.setFocusable(false);
				body.setClickable(false);
			}
			// 厂内不显示线路
			// holder.LineName.setText(item.LineName);

			// 格式化
			String stationHint = "到达 \"" + item.StationName + "\"提醒";
			SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder(
					stationHint);

			mSpannableStringBuilder.setSpan(
					new ForegroundColorSpan(RemindCollectionActivity.this
							.getResources().getColor(R.color.black_light)), 2,
					stationHint.length() - 2,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			mSpannableStringBuilder.setSpan(
					new AbsoluteSizeSpan(DensityUtil.sp2px(
							RemindCollectionActivity.this,
							station_hint_textSizeToBig), false), 2, stationHint
							.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			holder.StationName.setText(mSpannableStringBuilder);
			return arg1;
		}

		private void changeSelection(int index, boolean checked) {

			mData.get(index).setChecked(checked);
			if (listener != null) {
				listener.OnSelectionChanged();
			}
		}

		public void setOnEditSelectionChangedListener(
				OnEditSelectionChangedListener listener) {
			this.listener = listener;
		}

		public void setAllSelection(boolean checked) {
			for (int i = 0; i < mData.size(); i++) {
				mData.get(i).setChecked(checked);
			}
			notifyDataSetChanged();
		}

		public synchronized void turnToEditMode() {
			Logger.i(getClass(), "厂内列表进入编辑模式");
			if (inSelectionMode) {
				this.notifyDataSetChanged();
				Logger.i(getClass(), "厂内列表当前已经是编辑模式");
				return;
			}
			inSelectionMode = true;
			for (InListItem item : mData) {
				item.setChecked(false);
			}
			this.notifyDataSetChanged();
		}

		public synchronized void cancelEditMode() {
			Logger.i(getClass(), "厂内列表取消编辑模式");
			if (!inSelectionMode) {
				this.notifyDataSetChanged();
				Logger.i(getClass(), "厂内列表当前已经是非编辑模式");
				return;
			}
			inSelectionMode = false;
			for (InListItem item : mData) {
				item.setChecked(false);
			}
			this.notifyDataSetChanged();
			Logger.i(getClass(), "厂内列表取消编辑模式完成");
		}

		public void removeItem(int index) {
			if (index >= 0 && index < mData.size())
				mData.remove(index);
			this.notifyDataSetChanged();
		}

		public void removeItems(List<Integer> indexs) {
			if (indexs == null || indexs.size() == 0)
				return;
			Collections.sort(indexs);
			for (int i = indexs.size() - 1; i >= 0; i--) {
				int index = indexs.get(i).intValue();
				if (index >= 0 && index < mData.size())
					mData.remove(index);
			}
			this.notifyDataSetChanged();
		}

		class ViewHolder {
			TextView StationName;

			TextView LineName;

			CheckBox Selected;
		}
	}

	public static interface OnEditSelectionChangedListener {
		public void OnSelectionChanged();
	}

	/**************************/
	class OutListItem {
		public OutListItem(String station, String range, String rtype,
				String rcars, boolean closed, RemindInfo info) {
			this.IsClosed = closed;
			this.StationName = station;
			this.RangeName = range;
			this.RemindType = rtype;
			this.RemindCars = rcars;
			this.RemindInfo = info;
		}

		public RemindInfo RemindInfo;

		public String StationName = "";

		public String RangeName = "";

		public String RemindType = "";

		public String RemindCars = "";

		public boolean IsClosed = false;

		private boolean checked = false;

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}
	}

	class OutListAdapter extends BaseAdapter {
		List<OutListItem> mData;

		Context mContext;

		boolean inSelectionMode = false;

		OnEditSelectionChangedListener listener;

		// HashMap<Integer, String> currentSelections = new HashMap<Integer,
		// String>();

		public List<Integer> getCurrentSelections() {
			List<Integer> selections = new ArrayList<Integer>();
			for (int i = 0; i < mData.size(); i++) {
				if (mData.get(i).isChecked())
					selections.add(i);
			}
			return selections;
		}

		public OutListAdapter(Context context, List<OutListItem> data) {
			this.mContext = context;
			this.mData = data;
		}

		@Override
		public int getCount() {
			return mData == null ? 0 : mData.size();
		}

		@Override
		public OutListItem getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup parent) {
			ViewHolder holder = null;
			if (arg1 == null) {
				arg1 = View.inflate(mContext, R.layout.control_remindlist_item,
						null);
				holder = new ViewHolder();
				holder.StationName = (TextView) arg1
						.findViewById(R.id.tv_cri_tl1);
				holder.RangeName = (TextView) arg1
						.findViewById(R.id.tv_cri_tl2);
				holder.RemindCars = (TextView) arg1
						.findViewById(R.id.tv_cri_bl);
				holder.RemindType = (TextView) arg1
						.findViewById(R.id.tv_cri_tr);
				holder.IsClosed = (TextView) arg1.findViewById(R.id.tv_cri_br);
				holder.Selected = (CheckBox) arg1
						.findViewById(R.id.cb_cri_checked);
				holder.Selected.setFocusable(false);
				holder.Selected.setClickable(false);
				holder.RangeName.setVisibility(View.VISIBLE);
				holder.StationName.setVisibility(View.VISIBLE);
				holder.RemindCars.setVisibility(View.VISIBLE);
				holder.RemindType.setVisibility(View.VISIBLE);
				holder.IsClosed.setVisibility(View.VISIBLE);
				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}

			View body = arg1.findViewById(R.id.rl_cri_body);
			body.setTag(position);
			OutListItem item = mData.get(position);
			if (inSelectionMode) {
				Logger.i(getClass(), "刷新为编辑模式" + position);
				holder.Selected.setVisibility(View.VISIBLE);
				holder.Selected.setChecked(item.checked);
				body.setFocusable(true);
				body.setClickable(true);
				body.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						int index = (Integer) arg0.getTag();
						CheckBox cb = (CheckBox) arg0
								.findViewById(R.id.cb_cri_checked);
						boolean flag = cb.isChecked();
						cb.setChecked(!flag);
						changeSelection(index, !flag);
					}
				});
			} else {
				Logger.i(getClass(), "刷新为非编辑模式" + position);
				holder.Selected.setVisibility(View.GONE);
				body.setOnClickListener(null);
				body.setFocusable(false);
				body.setClickable(false);// 这个一定要卸载setOnClickListener的后面
			}
			holder.RangeName.setText(item.RangeName);
			holder.StationName.setText(item.StationName);
			holder.RemindCars.setText("提醒车辆:" + item.RemindCars);
			holder.RemindType.setText(item.RemindType);
			holder.IsClosed.setText(item.IsClosed ? "已关闭" : "");
			return arg1;
		}

		private void changeSelection(int index, boolean checked) {
			if (index < mData.size()) {
				mData.get(index).setChecked(checked);
				if (listener != null) {
					listener.OnSelectionChanged();
				}
			}
		}

		public void setOnEditSelectionChangedListener(
				OnEditSelectionChangedListener listener) {
			this.listener = listener;
		}

		public void setAllSelection(boolean checked) {
			for (int i = 0; i < mData.size(); i++) {
				mData.get(i).setChecked(checked);
			}
			notifyDataSetChanged();
		}

		public synchronized void turnToEditMode() {

			Logger.i(getClass(), "厂外列表进入编辑模式");
			if (inSelectionMode) {
				this.notifyDataSetChanged();
				Logger.i(getClass(), "厂外列表当前已经是编辑模式");
				return;
			}
			inSelectionMode = true;
			for (OutListItem item : mData) {
				item.setChecked(false);
			}
			this.notifyDataSetChanged();
			Logger.i(getClass(), "厂外列表进入编辑模式完成");
		}

		public synchronized void cancelEditMode() {
			Logger.i(getClass(), "厂外列表取消编辑模式");
			if (!inSelectionMode) {
				this.notifyDataSetChanged();
				Logger.i(getClass(), "厂外列表当前已经是非编辑模式");
				return;
			}
			inSelectionMode = false;
			// for (OutListItem item : mData) {
			// item.setChecked(false);
			// }
			this.notifyDataSetChanged();
			Logger.i(getClass(), "厂外列表取消编辑模式完成");
		}

		public void removeItems(List<Integer> indexs) {
			if (indexs == null || indexs.size() == 0)
				return;
			Collections.sort(indexs);
			for (int i = indexs.size() - 1; i >= 0; i--) {
				int index = indexs.get(i).intValue();
				if (index >= 0 && index < mData.size())
					mData.remove(index);
			}
			this.notifyDataSetChanged();
		}

		class ViewHolder {
			CheckBox Selected;

			TextView StationName;

			TextView RangeName;

			TextView RemindType;

			TextView RemindCars;

			TextView IsClosed;
		}
	}

}