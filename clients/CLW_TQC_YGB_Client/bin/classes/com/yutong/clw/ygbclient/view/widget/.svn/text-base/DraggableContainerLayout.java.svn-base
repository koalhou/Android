/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-12-10 上午8:39:08
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.Logger;

/**
 * @author zhouzc 2013-12-10 上午8:39:08
 * 
 */
public class DraggableContainerLayout extends RelativeLayout implements
		OnTouchListener {

	private DockSide dock = DockSide.Right;
	private boolean dragInside = true;
	private boolean draggable = true;

	private int ignorescale = 50;

	public DraggableContainerLayout(Context context) {
		this(context, null);
	}

	public DraggableContainerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (attrs != null) {
			int[] attrids = new int[] { R.attr.draggable,// 0
					R.attr.draginside,// 1
					R.attr.dockside // 2
			};

			TypedArray array = context.obtainStyledAttributes(attrs, attrids);
			draggable = array.getBoolean(0, true);
			dragInside = array.getBoolean(1, true);
			dock = DockSide.fromIntValue(array.getInt(2, 0x0001));
			array.recycle();
		}
		refreshChildren();

	}

	@Override
	public void addView(View child, int index,
			android.view.ViewGroup.LayoutParams params) {
		super.addView(child, index, params);
		refreshChildren();
	}

	@Override
	protected boolean addViewInLayout(View child, int index,
			android.view.ViewGroup.LayoutParams params,
			boolean preventRequestLayout) {
		boolean res = super.addViewInLayout(child, index, params,
				preventRequestLayout);
		refreshChildren();
		return res;
	}

	private void refreshChildren() {
		if (!draggable)
			return;
		int cnum = getChildCount();
		for (int i = 0; i < cnum; i++) {
			View child = this.getChildAt(i);
			child.setOnTouchListener(this);
		}
	}

	boolean currentTouchMoved = false;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			v.setTag(R.id.tag_touchposition,
					new PointF(event.getX(), event.getY()));
			currentTouchMoved = false;
			break;
		case MotionEvent.ACTION_MOVE:
			Object po = v.getTag(R.id.tag_touchposition);
			if (po != null && po instanceof PointF) {
				PointF downp = (PointF) po;
				float offset_x = event.getX() - downp.x;
				float offset_y = event.getY() - downp.y;

				int left = v.getLeft() + (int) offset_x;
				int top = v.getTop() + (int) offset_y;
				int right = v.getRight() + (int) offset_x;
				int bottom = v.getBottom() + (int) offset_y;
				if (currentTouchMoved) {
					v.layout(left, top, right, bottom);
				} else if (Math.abs(offset_x) > ignorescale
						|| Math.abs(offset_y) > ignorescale) {
					currentTouchMoved = true;
					v.layout(left, top, right, bottom);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_OUTSIDE:
			v.setTag(R.id.tag_touchposition, null);
			changeViewLocation(v, v.getLeft(), v.getTop(), v.getRight(),
					v.getBottom());
			if (currentTouchMoved) {
				v.setPressed(false);
				return true;
			}
			break;
		default:

			break;
		}
		return false;
	}

	private void changeViewLocation(View v, int left, int top, int right,
			int bottom) {
		switch (dock) {
		case None:
			break;
		case Left:
			right = right - left;
			left = 0;
			break;
		case Right:
			left = left + this.getWidth() - right;
			right = this.getWidth();
			break;
		case Top:
			bottom = bottom - top;
			top = 0;
			break;
		case Bottom:
			top = top + this.getHeight() - bottom;
			bottom = this.getHeight();
			break;
		case LeftOrRight:
			if (left < this.getWidth() - right) {
				right = right - left;
				left = 0;
			} else {
				left = left + this.getWidth() - right;
				right = this.getWidth();
			}
			break;
		case TopOrBottom:
			if (top < this.getHeight() - bottom) {
				bottom = bottom - top;
				top = 0;
			} else {
				top = top + this.getHeight() - bottom;
				bottom = this.getHeight();
			}
			break;
		default:
			break;
		}
		if (dragInside) {
			// TODO 计算是否超出容器
			if (v.getWidth() < this.getWidth()) {
				if (left < 0) {
					right = right - left;
					left = 0;
				} else if (right > this.getWidth()) {
					left = left + this.getWidth() - right;
					right = this.getWidth();
				}
			}

			if (v.getHeight() < this.getHeight()) {
				if (top < 0) {
					bottom = bottom - top;
					top = 0;
				} else if (bottom > this.getHeight()) {
					top = top + this.getHeight() - bottom;
					bottom = this.getHeight();
				}
			}
		}
		Logger.i(getClass(), "changeViewLocation left:" + left + ", top:" + top
				+ ", right:" + right + ", bottom:" + bottom + "");
		v.layout(left, top, right, bottom);
	}

	public enum DockSide {

		Left(0x0001), Right(0x0002), Top(0x0004), Bottom(0x0008), TopOrBottom(
				0x0004 | 0x0008), LeftOrRight(0x0001 | 0x0002), None(0x0100);

		int value;

		DockSide(int dvalue) {
			value = dvalue;
		}

		public int getValue() {
			return value;
		}

		public boolean contain(DockSide ds) {
			return ((value | ds.value) == value);
		}

		public static DockSide fromIntValue(int value) {
			for (DockSide ds : DockSide.values()) {
				if (ds.value == value)
					return ds;
			}
			Logger.i(DockSide.class, "暂时不支持你选择的Dock类型，默认到不DockSide.None");
			return DockSide.None;
		}
	}
}
