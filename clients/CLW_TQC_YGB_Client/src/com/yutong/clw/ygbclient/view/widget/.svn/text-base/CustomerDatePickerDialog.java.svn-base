package com.yutong.clw.ygbclient.view.widget;

import com.yutong.clw.ygbclient.common.Logger;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;


/**
 * 自定义日期选择空间
 * 
 * @author maoky
 */
public class CustomerDatePickerDialog extends DatePickerDialog {
	/**
	 * 默认构造函数
	 * 
	 * @param context
	 * @param callBack
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 */
	public CustomerDatePickerDialog(Context context,
			OnDateSetListener callBack, int year, int month, int day) {
		super(context, callBack, year, month, day);
		this.setTitle(year + "年" + (month + 1) + "月");
	}

	/**
	 * 从当前Dialog中查找DatePicker子控件
	 * 
	 * @param group
	 * @return
	 */
	public static DatePicker findDatePicker(ViewGroup group) {
		if (group != null) {
			for (int i = 0, j = group.getChildCount(); i < j; i++) {
				View child = group.getChildAt(i);
				if (child instanceof DatePicker) {
					return (DatePicker) child;
				} else if (child instanceof ViewGroup) {
					DatePicker result = findDatePicker((ViewGroup) child);
					if (result != null)
						return result;
				}
			}
		}
		return null;

	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		super.onDateChanged(view, year, month, day);
		this.setTitle(year + "年" + (month + 1) + "月");
	}

	DatePicker mdp = null;

	public void setDate(int year, int month) {
		super.onDateChanged(mdp, year, month, 1);
		this.setTitle(year + "年" + (month + 1) + "月");
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatePicker dp = findDatePicker((ViewGroup) this.getWindow()
				.getDecorView());
		mdp = dp;
		if (dp != null) {
			//TODO 设置中文
			ViewGroup yearGroup = (ViewGroup) dp.getChildAt(0);
			if (yearGroup != null && yearGroup.getChildCount() > 2) {
				yearGroup.getChildAt(yearGroup.getChildCount() - 1)
						.setVisibility(View.GONE);
			} else {
				try {
					((ViewGroup) (yearGroup.getChildAt(0))).getChildAt(2)
							.setVisibility(View.GONE);
				} catch (Exception e) {
					Logger.e(getClass(), e);
				}
			}
		}

	}
}
