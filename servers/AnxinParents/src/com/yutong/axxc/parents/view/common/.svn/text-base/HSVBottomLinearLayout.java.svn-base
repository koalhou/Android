package com.yutong.axxc.parents.view.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.view.util.DensityUtil;

public class HSVBottomLinearLayout extends LinearLayout {

	public HSVBottomLinearLayout(Context context) {
		super(context);
	}

	public HSVBottomLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	private void drawTriangle(int position, Canvas canvas) {
		Paint triPaint = new Paint();
		triPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		triPaint.setAntiAlias(true);
		triPaint.setColor(getResources().getColor(R.color.maincolor));
		Path tripath = new Path();
		int bh = this.getHeight();
		int th = DensityUtil.dip2px(getContext(), 20);
		int tw = DensityUtil.dip2px(getContext(), 30);
		int x0, y0;
		int x1, y1;
		int x2, y2;

		x0 = position - tw / 2;
		y0 = bh - th;

		x1 = position;
		y1 = bh;

		x2 = position + tw / 2;
		y2 = bh - th;

		tripath.moveTo(x0, y0);
		tripath.lineTo(x1, y1);
		tripath.lineTo(x2, y2);
		tripath.close();

		canvas.drawPath(tripath, triPaint);

	}

	boolean drawArraw = true;

	public boolean isDrawArraw() {
		return drawArraw;
	}

	public void setDrawArraw(boolean drawArraw) {
		this.drawArraw = drawArraw;
	}

	int arraw_postion = 35;

	public int getArrawPostion() {
		return arraw_postion;
	}

	public void setArrawPostion(int arraw_postion) {
		this.arraw_postion = arraw_postion;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (this.isInEditMode()) {
			super.onDraw(canvas);
		} else {
			if (drawArraw) {
				int position = DensityUtil.dip2px(getContext(), arraw_postion);
				drawTriangle(position, canvas);
			} else {
				super.onDraw(canvas);
			}
		}
	}
}
