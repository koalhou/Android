/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-12-12 上午9:13:29
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;

/**
 * @author zhouzc 2013-12-12 上午9:13:29
 * 
 */
public class ExHorizontalListView extends LinearLayout {

	String emptyText = "暂无数据";

	int textColor = Color.WHITE;

	float textSize =20;

	TextView msgtv = null;

	HorizontalListView hlv;
	
	public ExHorizontalListView(Context context) {
		this(context, null);
	}

	public ExHorizontalListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (attrs != null) {
			int[] attrids = new int[] { android.R.attr.hint,// 0
					android.R.attr.textSize,// 1
					android.R.attr.textColor // 2
			};
			TypedArray array = context.obtainStyledAttributes(attrs, attrids);
			emptyText = array.getString(0);
			textSize = array.getDimension(1,16);
			// textSize= array.getDimensionPixelSize(1,
			// DensityUtil.sp2px(getContext(), 16));
			// textSize = array.getLayoutDimension(1,
			// DensityUtil.sp2px(getContext(), 16));
			textColor = array.getColor(2, Color.WHITE);
			array.recycle();
		}

		initView();

	}

	private void initView() {
		View content = View.inflate(getContext(), R.layout.control_exhlv, null);
		hlv = (HorizontalListView) content.findViewById(R.id.hlv_ceh_list);
		msgtv = (TextView) content.findViewById(R.id.tv_ceh_hint);
		msgtv.setTextSize(textSize);
		msgtv.setTextColor(textColor);
		msgtv.setText(emptyText);
		msgtv.setVisibility(View.GONE);
		this.addView(content);
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
		hlv.setOnItemClickListener(listener);
	}

	private DataSetObserver mDataObserver = new DataSetObserver() {

		@Override
		public void onChanged() {
			if (hlv.getAdapter() == null) {
				msgtv.setVisibility(View.VISIBLE);
				msgtv.bringToFront();
				return;
			}
			if (hlv.getAdapter().getCount() == 0) {
				msgtv.setVisibility(View.VISIBLE);
				msgtv.bringToFront();
			} else {
				msgtv.setVisibility(View.GONE);
			}
		}

		@Override
		public void onInvalidated() {
		}

	};

	public void setAdapter(ListAdapter adapter) {
		if (adapter == null)
			return;
		if (hlv.getAdapter() != null)
			hlv.getAdapter().unregisterDataSetObserver(mDataObserver);

		adapter.registerDataSetObserver(mDataObserver);
		hlv.setAdapter(adapter);
		
		if (hlv.getAdapter().getCount() == 0) {
			msgtv.setVisibility(View.VISIBLE);
			msgtv.bringToFront();
		} else {
			msgtv.setVisibility(View.GONE);
		}
	}
	
	public void scrollTo(int x){
		hlv.scrollTo(x);
	}
}
