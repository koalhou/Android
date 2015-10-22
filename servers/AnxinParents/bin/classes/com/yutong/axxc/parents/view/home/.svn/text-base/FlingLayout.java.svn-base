package com.yutong.axxc.parents.view.home;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class FlingLayout extends LinearLayout {

	private int minHeight;
	private int maxHeight;
	VelocityTracker mVelocityTracker;
	private final int SNAP_VELOCITY = 1000;
	private final int SNAP_DISTANCE = 100;
	private int deltMargin = 40;
	private float a = 1.6f;
	private boolean isup = true;

	public FlingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		minHeight = 0;
		maxHeight =300;

		mHandler = new Handler() {
			
			@Override
			public void handleMessage(Message msg) {
				FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) getLayoutParams();
				int top = linearParams.topMargin;
				if (isup)
					top -= deltMargin;
				else
					top += deltMargin;
				if (top <= minHeight)
					top = minHeight;
				if (top >= maxHeight)
					top = maxHeight;

				linearParams.setMargins(0, top, 0, 0);
				setLayoutParams(linearParams);
				deltMargin *= a;
				if (top <= minHeight || top >= maxHeight)
					mTimer.cancel();
			}
		};
	}

	private Timer mTimer;
	private Handler mHandler;

	private void startTime() {
		// if(mTimer!=null && mTimer.)
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				// ++mCount;
				mHandler.sendEmptyMessage(0);
			}
		}, 10, 10);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) getLayoutParams(); // ȡ�ؼ�aaa��ǰ�Ĳ��ֲ���
		return linearParams.topMargin > this.minHeight
				&& linearParams.topMargin < this.maxHeight;

	}

	int rawy = 0;

	int y = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
		LinearLayout.LayoutParams l;
		boolean t = false;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			rawy = (int) event.getY();
			t = true;
			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:
			int y = (int) event.getY();
			if (Math.abs(y - rawy) >= SNAP_DISTANCE) {
				final VelocityTracker velocityTracker = mVelocityTracker;
				velocityTracker.computeCurrentVelocity(1000);
				int velocityY = (int) velocityTracker.getYVelocity();
				deltMargin = 20;
				if (velocityY > SNAP_VELOCITY) {
					isup = false;

				} else {
					isup = true;

				}
				startTime();
			}
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}

			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		}

		// return false;
		return t;
		// return super.onTouchEvent(event);
	}

	private void obtainVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	private void releaseVelocityTracker() {
		if (mVelocityTracker != null) {
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	}

}
