package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.enums.remind.RemindType;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.setting.BizSetting;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.setting.ring.RingActivity;
import com.yutong.clw.ygbclient.view.util.DensityUtil;
import com.yutong.clw.ygbclient.view.widget.CommonCheckBox;
import com.yutong.clw.ygbclient.view.widget.ExDropDownList;
import com.yutong.clw.ygbclient.view.widget.WeekSelectView;
import com.yutong.clw.ygbclient.view.widget.WeekSelectView.WeekSelection;
import com.yutong.clw.ygbclient.view.widget.WeekSelectView.onSelectionChangedListener;

/**
 * 厂外-站点提醒设置界面
 * 
 * @author zhouzc
 */
public class StationRemindSettingActivity extends RemindAccessActivity
		implements OnClickListener {

	/************************************/
	private BizSetting biz;

	private CommonCheckBox cb_open;

	private WeekSelectView weekView;

	private TextView tv_stationName;

	private TextView tv_dayornight;

	private TextView tv_cars;

	private TextView tv_remindType;

	private TextView tv_remindDetail;

	private ExDropDownList pop_type;

	private ExDropDownList pop_detail;

	private TreeMap<RemindType, TreeMap<Integer, String>> remindTypeDatas;

	private List<String> currentdetails;

	private RemindInfo currentRemind;

	/************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_outoffactory_singlestation_remindset);
		initData();
		initViews();
		loadIntentData();
	}

	private void initData() {
		biz = new BizSetting(this);
		remindTypeDatas = new TreeMap<RemindType, TreeMap<Integer, String>>();
		
		
		TreeMap<Integer, String> tdata = new TreeMap<Integer, String>();
		tdata.put(1000, "提前1000米");
		tdata.put(2000, "提前2000米");
		tdata.put(3000, "提前3000米");
		remindTypeDatas.put(RemindType.Distance, tdata);
		

		TreeMap<Integer, String> ddata = new TreeMap<Integer, String>();
		ddata.put(5, "提前5分钟");
		ddata.put(10, "提前10分钟");
		remindTypeDatas.put(RemindType.Date, ddata);
	}

	private void initViews() {

		findViewById(R.id.rl_amosr_ringset).setOnClickListener(this);

		cb_open = (CommonCheckBox) findViewById(R.id.cb_amosr_open);
		cb_open.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// 开关提醒
				currentRemind.setRemind_status(isChecked ? RemindStatus.Open
						: RemindStatus.Close);
			}
		});

		weekView = (WeekSelectView) findViewById(R.id.wsv_amosr_weeks);
		weekView.setOnSelectionChangedListener(new onSelectionChangedListener() {
			@Override
			public void OnSelectChanged(WeekSelection selection,
					boolean isselected) {
				if (currentRemind != null) {
					String remind_week = getStringFromWeekSelections(weekView
							.getCurrentSelections());
					currentRemind.setRemind_week(remind_week);
				}
			}
		});

		tv_stationName = (TextView) findViewById(R.id.tv_amosr_stationname);
		tv_dayornight = (TextView) findViewById(R.id.tv_amosr_range);
		tv_cars = (TextView) findViewById(R.id.tv_amosr_cars);
		tv_remindType = (TextView) findViewById(R.id.tv_amosr_remindtype);
		tv_remindDetail = (TextView) findViewById(R.id.tv_amosr_reminddetail);

		findViewById(R.id.ll_amosr_remindtype).setOnClickListener(this);
		findViewById(R.id.ll_amosr_reminddetail).setOnClickListener(this);
		findViewById(R.id.btn_amosr_confirm).setOnClickListener(this);
		findViewById(R.id.btn_amosr_cancel).setOnClickListener(this);

		final List<String> types = Arrays.asList("按距离", "按时间");
		pop_type = new ExDropDownList(this,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, types);
		pop_type.setItemDrawable(
				getResources().getDrawable(
						R.drawable.bg_exdropdown_blueitem_first),
				getResources().getDrawable(
						R.drawable.bg_exdropdown_blueitem_middle),
				getResources().getDrawable(
						R.drawable.bg_exdropdown_blueitem_last), getResources()
						.getDrawable(R.drawable.bg_exdropdown_blueitem_only));
		pop_type.setDivider(new ColorDrawable(Color.WHITE));

		pop_type.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				pop_type.dismiss();
				if (tv_remindType.getText().equals(types.get(arg2)))
					return;
				// 设置提醒类型
				switch (arg2) {
				case 1:
					currentRemind.setRemind_type(RemindType.Date);
					break;
				case 0:
					currentRemind.setRemind_type(RemindType.Distance);
					break;
				}
				loadDetail(currentRemind.getRemind_type());
			}
		});
		tv_remindType.setText(types.get(0));
		currentdetails = new LinkedList<String>(remindTypeDatas.get(
				RemindType.Date).values());
		pop_detail = new ExDropDownList(this,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				currentdetails);
		pop_detail.setItemDrawable(
				getResources().getDrawable(
						R.drawable.bg_exdropdown_grayitem_first),
				getResources().getDrawable(
						R.drawable.bg_exdropdown_grayitem_middle),
				getResources().getDrawable(
						R.drawable.bg_exdropdown_grayitem_last), getResources()
						.getDrawable(R.drawable.bg_exdropdown_grayitem_only));
		pop_detail.setDivider(new ColorDrawable(Color.GRAY));
		pop_detail.setTextColor(Color.GRAY);
		pop_detail.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				pop_detail.dismiss();
				// 设置提醒详情
				currentRemind.setRemind_value(Integer.parseInt(remindTypeDatas
						.get(currentRemind.getRemind_type()).keySet().toArray()[arg2]
						.toString()));
				tv_remindDetail.setText(currentdetails.get(arg2));
			}
		});
		tv_remindDetail.setText(currentdetails.get(0));
	}

	private void loadIntentData() {
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		try {
			if (data != null
					&& data.containsKey(ActivityCommConstant.REMINDINFO)) {
				Serializable reminddata = data
						.getSerializable(ActivityCommConstant.REMINDINFO);
				if (reminddata != null && reminddata instanceof RemindInfo) {
					currentRemind = checkIntputRemindInfo((RemindInfo) data
							.getSerializable(ActivityCommConstant.REMINDINFO));

				} else {
					throw new IllegalArgumentException(
							"厂外提醒设置界面传入参数类型错误，应该是RemindInfo");
				}
			} else {
				throw new IllegalArgumentException("厂外提醒设置界面传入参数不能为空");
			}
		} catch (Exception err) {
			err.printStackTrace();
			// TODO 应付客户不崩溃
			//ToastMessage("暂时无法设置提醒");
			setResult(RESULT_CANCELED);
			finish();
		}
		reloadByInfo(currentRemind);
	}

	private String getStringFromRemindType(RemindType type) {
		if (type == null)
			return "按时间";
		switch (type) {
		case Date:
			return "按时间";
		case Distance:
			return "按距离";
		default:
			break;
		}
		return "按时间";
	}

	private void loadDetail(RemindType type) {
		tv_remindType.setText(getStringFromRemindType(type));
		currentdetails = new ArrayList<String>(remindTypeDatas.get(type)
				.values());
		pop_detail.reloadData(currentdetails);
		tv_remindDetail.setText(currentdetails.size() == 0 ? ""
				: currentdetails.get(0));
		currentRemind.setRemind_value(Integer.parseInt(remindTypeDatas
				.get(type).keySet().toArray()[0].toString()));
	}

	private void loadDetail(RemindType type, int value) {

		tv_remindType.setText(getStringFromRemindType(type));
		currentdetails = new ArrayList<String>(remindTypeDatas.get(type)
				.values());
		// 设置提醒详情
		if (!remindTypeDatas.get(type).containsKey(value)) {
			remindTypeDatas.get(type).put(value,
					"提前" + value + getUnitStringFromType(type));
		}

		String valuedescription = remindTypeDatas.get(type).get(value);
		tv_remindDetail.setText(valuedescription);
		currentRemind.remind_value = value;
		currentdetails.clear();
		currentdetails.addAll(remindTypeDatas.get(type).values());
		pop_detail.reloadData(currentdetails);
	}

	private String getUnitStringFromType(RemindType type) {
		switch (type) {
		case Date:
			return "分钟";
		case Distance:
			return "米";
		case StationNum:
			return "站";
		}
		return "分钟";
	}

	private void reloadByInfo(RemindInfo info) {
		if (info == null)
			return;
		tv_stationName.setText(info.getStation_name());
		tv_dayornight
				.setText(info.status_range==StatusRange.AllWork?"早班/晚班":( info.status_range == StatusRange.MorningWork ? "早班"
						: "晚班"));
		String carstr = "";
		if (info.getRemind_range() == RemindRange.OnlyStation) {
			carstr = "经过站点的所有车辆";
		} else {
			carstr = info.vehiche_number;
		}
		tv_cars.setText(carstr);
		if (!remindTypeDatas.get(currentRemind.getRemind_type()).keySet()
				.contains(info.getRemind_value())) {
			int remind_value = Integer.parseInt(remindTypeDatas
					.get(currentRemind.getRemind_type()).keySet().toArray()[0]
					.toString());
			currentRemind.setRemind_value(remind_value);
		}
		loadDetail(info.getRemind_type(), info.getRemind_value());

		weekView.setSelections(getWeekSelectionsFromString(info
				.getRemind_week()));
		if (info.getRemind_status() == null) {
			cb_open.setChecked(true);
		} else {
			cb_open.setChecked(info.getRemind_status() == RemindStatus.Open);
		}
	}

	private List<WeekSelection> getWeekSelectionsFromString(String str) {
		List<WeekSelection> selections = new ArrayList<WeekSelectView.WeekSelection>();
		if (str != null) {
			char[] data = str.toCharArray();
			for (int i = 0; i < 7 && i < str.length(); i++) {
				if (data[i] == '1') {
					switch (i) {
					case 0:
						selections.add(WeekSelection.Monday);
						break;
					case 1:
						selections.add(WeekSelection.Tuesday);
						break;
					case 2:
						selections.add(WeekSelection.Wensday);
						break;
					case 3:
						selections.add(WeekSelection.Thursday);
						break;
					case 4:
						selections.add(WeekSelection.Friday);
						break;
					case 5:
						selections.add(WeekSelection.Saturday);
						break;
					case 6:
						selections.add(WeekSelection.Sunday);
						break;
					default:
						break;
					}
				}
			}
		}
		return selections;
	}

	private String getStringFromWeekSelections(List<WeekSelection> selelctions) {
		char[] result = "0000000".toCharArray();
		for (WeekSelection se : selelctions) {
			switch (se) {
			case Monday:
				result[0] = '1';
				break;
			case Tuesday:
				result[1] = '1';
				break;
			case Wensday:
				result[2] = '1';
				break;
			case Thursday:
				result[3] = '1';
				break;
			case Friday:
				result[4] = '1';
				break;
			case Saturday:
				result[5] = '1';
				break;
			case Sunday:
				result[6] = '1';
				break;
			default:
				break;
			}
		}
		return new String(result);
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

		tv_large.setText("到站提醒");
		tv_small.setText("");

		btn_left.setOnClickListener(this);

		tv_small.setVisibility(View.GONE);
		iv_tri.setVisibility(View.GONE);
		btn_right.setVisibility(View.INVISIBLE);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_it_left:
			setResult(RESULT_CANCELED);
			finish();
			break;
		case R.id.ll_amosr_remindtype:
			if (pop_detail.isShowing()) {
				pop_detail.dismiss();
			}
			if (pop_type.isShowing()) {
				pop_type.dismiss();
			} else {
				pop_type.setWidth(v.getWidth());
				pop_type.showAsDropDown(v, 0, DensityUtil.dip2px(this, 1));
			}
			break;
		case R.id.ll_amosr_reminddetail:
			if (pop_type.isShowing()) {
				pop_type.dismiss();
			}
			if (pop_detail.isShowing()) {
				pop_detail.dismiss();
			} else {
				pop_detail.setWidth(v.getWidth());
				pop_detail.showAsDropDown(v, 0, DensityUtil.dip2px(this, 1));
			}
			break;
		case R.id.rl_amosr_ringset:
			// 搜索音频
			Intent intent = new Intent(this, RingActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_amosr_confirm:
			// 确定
			showLoading("设置中...", 1);
			biz.setRemind(currentRemind, new BizResultProcess<Boolean>() {
				@Override
				public void onBizExecuteError(Exception exception, Error error) {
					dismissLoading(1);
					ToastMessage("设置失败，请检查网络连接后重试");
					HandleLogicErrorInfo(exception);
				}

				@Override
				public void onBizExecuteEnd(BizDataTypeEnum datatype, Boolean t) {
					dismissLoading(1);
					if (t) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ToastMessage("设置成功");
								Intent result = new Intent();
								Bundle data = new Bundle();
								data.putSerializable(
										ActivityCommConstant.REMINDINFO,
										currentRemind);
								result.putExtras(data);
								setResult(RESULT_OK, result);
								finish();
							}
						});
					} else {
						ToastMessage("设置失败，请检查网络连接后重试");
					}
					
					/*用户行为统计-收藏-设置或取消提醒*/
		            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_SZTX).reportBehavior(null);
				}
			});
			break;
		case R.id.btn_amosr_cancel:
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onResume() {

		super.onResume();

	}

	private RemindInfo checkIntputRemindInfo(RemindInfo info) {
		if (info == null) {
			throw new IllegalArgumentException("厂外提醒设置界面传入参数不能为空");
		}
		{// 公共判断
			if (info.getLine_range() == null) {
				throw new IllegalArgumentException(
						"厂外提醒设置界面传入参数[Line_range]不能为空");
			} else if (info.getArea_type() == null) {
				throw new IllegalArgumentException(
						"厂外提醒设置界面传入参数[Area_type]不能为空");
			} else if (info.getStation_id() == null) {
				throw new IllegalArgumentException(
						"厂外提醒设置界面传入参数[Station_id]不能为空");
			} else if (info.getStation_name() == null) {
				throw new IllegalArgumentException(
						"厂外提醒设置界面传入参数[Station_name]不能为空");
			} else if (info.status_range == null) {
				throw new IllegalArgumentException(
						"厂外提醒设置界面传入参数[status_range]不能为空");
			} else if (info.remind_range == null) {
				throw new IllegalArgumentException(
						"厂外提醒设置界面传入参数[remind_range]不能为空");
			}
		}
		if (null == info.getId()) {// 新提醒
			Logger.i(getClass(), "RemindInfo 的 Id为空，判断为新提醒设置");
			if (info.remind_range == RemindRange.StationAndVehiche
					&& (info.getVehiche_vin() == null || info
							.getVehiche_number() == null)) {
				throw new IllegalArgumentException(
						"厂外提醒设置界面传入参数，当班 车提醒设置的时候，[Vehiche_vin]和[Vehiche_number]不能为空");
			}

			// 下面开始设置默认参数
			info.setRemind_type(RemindType.Distance);
			info.setRemind_value(1000);
			info.setRemind_week("0000000");
			info.setRemind_status(RemindStatus.Open);

		} else {// 旧提醒
			Logger.i(getClass(), "RemindInfo 的 Id不为空，判断为旧提醒修改");
			if (info.getRemind_type() == null) {
				throw new IllegalArgumentException(
						"厂外提醒设置界面传入参数[Remind_type]不能为空");
			} else if (info.getRemind_week() == null) {
				throw new IllegalArgumentException(
						"厂外提醒设置界面传入参数[Remind_week]不能为空");
			} else if (info.getRemind_status() == null) {
				throw new IllegalArgumentException(
						"厂外提醒设置界面传入参数[Remind_status]不能为空");
			}
		}
		return info;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			setResult(RESULT_CANCELED);
			finish();
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

}
