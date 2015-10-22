package com.yutong.axxc.parents.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.view.util.DensityUtil;

/**
 * 学生选择bar控件
 * 
 * @author zhangyongn
 * 
 */
public class SingleSelectChildrenBar extends LinearLayout implements
		ISelectChildrenBar {

	private LinearLayout parentView;
	private LinearLayout backgroundView;
	private GridView gridView;
	private MHorizontalScrollView sv;
	private OnBarItemClickListener blistener;
	private OnBarItemLongClickListener llistener;

	public SingleSelectChildrenBar(Context context) {
		this(context, null);
		if (isInEditMode())
			return;
		LayoutInflater.from(context).inflate(R.layout.userselect_scroll, this,
				true);
		initViews();
	}

	public SingleSelectChildrenBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode())
			return;
		LayoutInflater.from(context).inflate(R.layout.userselect_scroll, this,
				true);
		initViews();
	}

	private void initViews() {
		parentView = (LinearLayout) findViewById(R.id.hsll_parent);
		backgroundView = (LinearLayout) findViewById(R.id.hsll_sub);

		gridView = (GridView) findViewById(R.id.gridView_users);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1); // , 1是可选写的
		lp.setMargins(0, 0, 0, DensityUtil.dip2px(getContext(), 0));// 设置为0，不画箭头
		gridView.setLayoutParams(lp);
		sv = (MHorizontalScrollView) findViewById(R.id.horizontalScrollView_body);
		sv.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					// if (!sv.isScrolling()) {
					// manageAutoFitOnScroll(sv.getScrollX(), sv.getScrollY());
					// }
				}
				return false;
			}
		});
		sv.setOnMHScrollChangeListener(new MHScrollChangeListener() {

			@Override
			public void ScrollChanged(int l, int t, int oldl, int oldt) {
				manageScrollMove(l);
			}

			@Override
			public void ScrollEnd(int scrollx, int scrolly) {
				// if (!sv.isTouching())
				// manageAutoFitOnScroll(scrollx, scrolly);//因为效果不好，暂时不做定格滚动
			}

		});
	}

	private int lastscrollx = 0;

	private void manageScrollMove(int scrollx) {

	}

	private void manageAutoFitOnScroll(int scrollx, int scrolly) {

		if (lastscrollx == scrollx)
			return;
		lastscrollx = scrollx;

		/* 计算选择项 */
		// Log.d("", "开始计算 选择项 ");
		// Log.d("", "当前位移:" + scrollx);
		int itemw = gvupadapter.getItemWidth();
		final int nwidth = DensityUtil.dip2px(getContext(), itemw);
		// Log.d("", "列宽 " + nwidth + " ");
		int sindex = scrollx / nwidth;
		// Log.d("", "初步计算 选择项 为:" + sindex);
		int delta = scrollx % nwidth;
		if (delta >= (nwidth / 2)) {
			sindex++;
			// Log.d("", "移动超过一半 选择项重新计算为:" + sindex);
		}

		final int nowindex = sindex;

		// Log.d("", "调整选中头像 到第 " + nowindex + " 个");

		sv.scrollTo(nowindex * nwidth, sv.getScrollY());
		gvupadapter.setSelectedIndex(nowindex);
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
		gvupadapter.setSingleSelcect(true);
		if (gridView != null) {
			gridView.setAdapter(adapter);
			gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if (llistener != null)
						llistener.onClick(arg2);
					return true;
				}
			});
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					if (blistener != null)
						blistener.onClick(arg2);
					if (gvupadapter.getSelectedIndexs() == null
							|| gvupadapter.getSelectedIndexs().size() == 0)
						gvupadapter.moveToFirst(arg2, true);
					else
						gvupadapter.exchange(gvupadapter.getSelectedIndexs()
								.get(0), arg2, true);
				}
			});
		}
	}

	@Override
	public LinearLayout getParentView() {
		return parentView;
	}

	public void setOnBarItemClickListener(OnBarItemClickListener listener) {
		blistener = listener;
	}

	public void setOnBarItemLongClickListener(
			OnBarItemLongClickListener llistener) {
		this.llistener = llistener;
	}

	/**
	 * 选项点击事件
	 * 
	 * @author zhouzc
	 * 
	 */
	public interface OnBarItemClickListener {
		/**
		 * 
		 * @param index
		 *            序号
		 */
		public void onClick(int index);
	}

	/**
	 * 选项长按事件
	 * 
	 * @author zhouzc
	 * 
	 */
	public interface OnBarItemLongClickListener {
		/**
		 * 
		 * @param index
		 *            序号
		 */
		public void onClick(int index);
	}

	@Override
	public HorizontalScrollView getScrollView() {
		return sv;
	}

}
