/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-9 下午2:37:54
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

import com.yutong.clw.ygbclient.R;

/**
 * @author zhouzc 2013-11-9 下午2:37:54
 * 
 */
public class WeekSelectView extends LinearLayout implements
		OnCheckedChangeListener {

	private CheckBox cb_1, cb_2, cb_3, cb_4, cb_5, cb_6, cb_7;
	private onSelectionChangedListener listener;
	private List<WeekSelection> currentSelections = new ArrayList<WeekSelectView.WeekSelection>();

	public WeekSelectView(Context context) {
		super(context);
		commonInit();
	}

	public WeekSelectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		commonInit();
	}

	private void commonInit() {
		if (isInEditMode()) {
			return;
		}
		View content = View.inflate(getContext(),
				R.layout.control_weekselectview, null);

		this.addView(content, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		cb_1 = (CheckBox) content.findViewById(R.id.cb_cw_1);
		cb_2 = (CheckBox) content.findViewById(R.id.cb_cw_2);
		cb_3 = (CheckBox) content.findViewById(R.id.cb_cw_3);
		cb_4 = (CheckBox) content.findViewById(R.id.cb_cw_4);
		cb_5 = (CheckBox) content.findViewById(R.id.cb_cw_5);
		cb_6 = (CheckBox) content.findViewById(R.id.cb_cw_6);
		cb_7 = (CheckBox) content.findViewById(R.id.cb_cw_7);

		cb_1.setChecked(false);
		cb_2.setChecked(false);
		cb_3.setChecked(false);
		cb_4.setChecked(false);
		cb_5.setChecked(false);
		cb_6.setChecked(false);
		cb_7.setChecked(false);

		cb_1.setOnCheckedChangeListener(this);
		cb_2.setOnCheckedChangeListener(this);
		cb_3.setOnCheckedChangeListener(this);
		cb_4.setOnCheckedChangeListener(this);
		cb_5.setOnCheckedChangeListener(this);
		cb_6.setOnCheckedChangeListener(this);
		cb_7.setOnCheckedChangeListener(this);
	}

	public void setSelections(List<WeekSelection> selections) {
		if (selections == null) {
			currentSelections.clear();
			cb_1.setChecked(false);
			cb_2.setChecked(false);
			cb_3.setChecked(false);
			cb_4.setChecked(false);
			cb_5.setChecked(false);
			cb_6.setChecked(false);
			cb_7.setChecked(false);
			return;
		}
		currentSelections.clear();
		if (selections.contains(WeekSelection.Monday)) {
			cb_1.setChecked(true);
			currentSelections.add(WeekSelection.Monday);
		} else {
			cb_1.setChecked(false);
		}

		if (selections.contains(WeekSelection.Tuesday)) {
			cb_2.setChecked(true);
			currentSelections.add(WeekSelection.Tuesday);
		} else {
			cb_2.setChecked(false);
		}
		if (selections.contains(WeekSelection.Wensday)) {
			cb_3.setChecked(true);
			currentSelections.add(WeekSelection.Wensday);
		} else {
			cb_3.setChecked(false);
		}
		if (selections.contains(WeekSelection.Thursday)) {
			cb_4.setChecked(true);
			currentSelections.add(WeekSelection.Thursday);
		} else {
			cb_4.setChecked(false);
		}
		if (selections.contains(WeekSelection.Friday)) {
			cb_5.setChecked(true);
			currentSelections.add(WeekSelection.Friday);
		} else {
			cb_5.setChecked(false);
		}
		if (selections.contains(WeekSelection.Saturday)) {
			cb_6.setChecked(true);
			currentSelections.add(WeekSelection.Saturday);
		} else {
			cb_6.setChecked(false);
		}
		if (selections.contains(WeekSelection.Sunday)) {
			cb_7.setChecked(true);
			currentSelections.add(WeekSelection.Sunday);
		} else {
			cb_7.setChecked(false);
		}
	}

	public void changeSelections(WeekSelection selection, boolean isselected) {

		switch (selection) {
		case Monday:
			cb_1.setChecked(isselected);
			break;
		case Tuesday:
			cb_2.setChecked(isselected);
			break;
		case Wensday:
			cb_3.setChecked(isselected);
			break;
		case Thursday:
			cb_4.setChecked(isselected);
			break;
		case Friday:
			cb_5.setChecked(isselected);
			break;
		case Saturday:
			cb_6.setChecked(isselected);
			break;
		case Sunday:
			cb_7.setChecked(isselected);
			break;

		default:
			break;
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		WeekSelection selection = WeekSelection.Monday;
		switch (buttonView.getId()) {
		case R.id.cb_cw_1:
			selection = WeekSelection.Monday;
			break;
		case R.id.cb_cw_2:
			selection = WeekSelection.Tuesday;
			break;
		case R.id.cb_cw_3:
			selection = WeekSelection.Wensday;
			break;
		case R.id.cb_cw_4:
			selection = WeekSelection.Thursday;
			break;
		case R.id.cb_cw_5:
			selection = WeekSelection.Friday;
			break;
		case R.id.cb_cw_6:
			selection = WeekSelection.Saturday;
			break;
		case R.id.cb_cw_7:
			selection = WeekSelection.Sunday;
			break;

		default:
			break;
		}
		if (isChecked) {
			if (!currentSelections.contains(selection))
				currentSelections.add(selection);
		} else {
			while (currentSelections.contains(selection)) {
				currentSelections.remove(selection);
			}
		}
		if (listener != null) {
			listener.OnSelectChanged(selection, isChecked);
		}
	}

	public List<WeekSelection> getCurrentSelections() {
		return currentSelections;
	}

	public void setOnSelectionChangedListener(
			onSelectionChangedListener listener) {
		this.listener = listener;
	}

	public static enum WeekSelection {
		Monday, Tuesday, Wensday, Thursday, Friday, Saturday, Sunday
	}

	public static interface onSelectionChangedListener {

		public void OnSelectChanged(WeekSelection selection, boolean isselected);

	}
}
