package com.yutong.clw.ygbclient.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 可旋转按钮
 * @author zhouzc
 *
 */
public class RotateButton extends Button {

	public RotateButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public RotateButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RotateButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	private float rotatedegree = 45;

	public float getRotatedegree() {
		return rotatedegree;
	}

	public void setRotatedegree(float rotatedegree) {
		this.rotatedegree = rotatedegree;
		this.invalidate();
	}

	@Override
	public void draw(Canvas canvas) {
		int centerx = this.getWidth() / 2;
		int centery = this.getHeight() / 2;
		canvas.save();
		canvas.rotate(rotatedegree, centerx, centery);
		super.draw(canvas);
		canvas.restore();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	

}
