package com.yutong.axxc.parents.view.home;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.view.util.DensityUtil;

public class CustomLayout extends LinearLayout {
	private float paddingLeft = 10;
	private float paddingTop = 0;
	private float paddingBottom = 0;
	private float width = 4;
	private int timelineColor;

	public CustomLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.attr_timelineitemlayout);

		timelineColor = getResources().getColor(R.color.maincolor);
		float l = 0.0f;
		 l = a.getDimension(R.styleable.attr_timelineitemlayout_paddingleft1,
		 35);
		

		 if(isInEditMode() ){
			 paddingLeft = DensityUtil.dip2px(this.getContext(), l);
		 }else{
			 paddingLeft=l;
		 }
		
		l = a.getDimension(R.styleable.attr_timelineitemlayout_paddingtop1, 0);
		 if(isInEditMode() ){
			 paddingTop = DensityUtil.dip2px(this.getContext(), l);
		 }else{
			 paddingTop=l;
		 }

		l = a.getDimension(R.styleable.attr_timelineitemlayout_paddingbottom1,
				0);
		 if(isInEditMode() ){
			 paddingBottom = DensityUtil.dip2px(this.getContext(), l);
		 }else{
			 paddingBottom=l;
		 }

		l = a.getDimension(R.styleable.attr_timelineitemlayout_width1, 2);
		
		width = DensityUtil.dip2px(this.getContext(), l);

		timelineColor = a.getColor(R.styleable.attr_timelineitemlayout_color1,
				getResources().getColor(R.color.maincolor));

		a.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setColor(timelineColor);

		canvas.drawRect(new Rect((int) paddingLeft, (int) paddingTop,
				(int) (paddingLeft + width),
				(int) (getHeight() - paddingBottom)), paint);

	}
}
