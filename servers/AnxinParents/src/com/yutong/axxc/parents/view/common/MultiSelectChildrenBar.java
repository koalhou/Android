package com.yutong.axxc.parents.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.yutong.axxc.parents.R;

public class MultiSelectChildrenBar extends LinearLayout implements
		ISelectChildrenBar {
	private LinearLayout parentView;
	private LinearLayout backgroundView;
	private GridView gridView;
	private HorizontalScrollView sv;

	public MultiSelectChildrenBar(Context context) {
		this(context, null);
	}

	public MultiSelectChildrenBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.userselect_scroll, this,
				true);
		initViews();
	}

	private void initViews() {
		parentView = (LinearLayout) findViewById(R.id.hsll_parent);
		backgroundView = (LinearLayout) findViewById(R.id.hsll_sub);

		gridView = (GridView) findViewById(R.id.gridView_users);
		sv=(HorizontalScrollView)findViewById(R.id.horizontalScrollView_body);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1); // , 1是可选写的
		lp.setMargins(0, 0, 0, 0);
		gridView.setLayoutParams(lp);
		final MHorizontalScrollView sv = (MHorizontalScrollView) findViewById(R.id.horizontalScrollView_body);
		sv.setOnMHScrollChangeListener(new MHScrollChangeListener() {

			@Override
			public void ScrollChanged(int l, int t, int oldl, int oldt) {
			}

			@Override
			public void ScrollEnd(int l, int t) {

			}
		});
	}

	public LinearLayout getBackgroundView() {
		return backgroundView;
	}

	public GridView getGridView() {
		return gridView;
	}

	private UserGridAdapter gvupadapter = null;

	public void setGridViewAdapter(UserGridAdapter adapter) {
		gvupadapter = adapter;
		if (gridView != null) {
			gridView.setAdapter(adapter);
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					gvupadapter.changeSelectStatus(arg2);
				}
			});
		}
	}

	@Override
	public LinearLayout getParentView() {
		return parentView;
	}

	@Override
	public HorizontalScrollView getScrollView() {
		return sv;
	}

}
