package com.yutong.axxc.parents.view.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class MHorizontalScrollView extends HorizontalScrollView {

	int unitwidth = 60;
	int leftpadding = 0;

	ScrollDirection lastScrollDirection = ScrollDirection.None;

	/**
	 * 上次滚动方向
	 * 
	 * @return
	 */
	public ScrollDirection getLastScrollDirection() {
		return lastScrollDirection;
	}

	public MHorizontalScrollView(Context context) {
		super(context);
		this.setSmoothScrollingEnabled(true);
	}

	public MHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setSmoothScrollingEnabled(true);
	}

	public MHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.setSmoothScrollingEnabled(true);
	}

	MHScrollChangeListener mhlistener = null;

	public void setOnMHScrollChangeListener(MHScrollChangeListener mhlistener) {
		this.mhlistener = mhlistener;
	}

	private int lastX = 0;
	private int touchEventId = -9983761;

	boolean handleworking = false;
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			View scroller = (View) msg.obj;
			if (msg.what == touchEventId) {
				// Log.d("", "收到新消息:");
				if (lastX == scroller.getScrollX()) {
					// Log.d("", "发现和前面的坐标一样:认为停止滚动了");
					handleStop(scroller);
					handleworking = false;
				} else {

					// Log.d("", "发现和前面的坐标不一样: 等50毫秒再发送消息");
					handler.sendMessageDelayed(
							handler.obtainMessage(touchEventId, scroller), 5);
					lastX = scroller.getScrollX();

				}
			}
		}
	};

	boolean scrolling = false;

	public boolean isScrolling() {
		return scrolling;
	}

	boolean istouching = false;

	public boolean isTouching() {
		return istouching;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			// Log.d("", "ACTION_MOVE:");
			istouching = true;
			scrolling = true;
			break;
		case MotionEvent.ACTION_DOWN:
			// Log.d("", "ACTION_DOWN:");
			istouching = true;
			break;
		case MotionEvent.ACTION_UP:
			// Log.d("", "ACTION_UP:");
			istouching = false;

			break;
		default:
			break;
		}

		return super.onTouchEvent(ev);
	}

	private void handleStop(Object view) {
		// Log.d("", "停止滚动:" + this.getScrollX());
		scrolling = false;

		if (mhlistener != null) {
			mhlistener.ScrollEnd(this.getScrollX(), this.getScrollY());
		}

	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {

		int hdelta = l - oldl;
		int vdelta = t - oldt;

		ScrollDirection leftrightflag = ((hdelta) == 0) ? ScrollDirection.None
				: ((hdelta > 0) ? ScrollDirection.Left : ScrollDirection.Right);
		ScrollDirection updownflag = ((vdelta) == 0) ? ScrollDirection.None
				: ((vdelta > 0) ? ScrollDirection.Up : ScrollDirection.Down);

		if (leftrightflag == ScrollDirection.Left) {
			if (updownflag == ScrollDirection.Up) {
				lastScrollDirection = ScrollDirection.LeftUp;
			} else if (updownflag == ScrollDirection.Down) {
				lastScrollDirection = ScrollDirection.LeftDown;
			} else if (updownflag == ScrollDirection.None) {
				lastScrollDirection = ScrollDirection.Left;
			}
		} else if (leftrightflag == ScrollDirection.Right) {
			if (updownflag == ScrollDirection.Up) {
				lastScrollDirection = ScrollDirection.RightUp;
			} else if (updownflag == ScrollDirection.Down) {
				lastScrollDirection = ScrollDirection.RightDown;
			} else if (updownflag == ScrollDirection.None) {
				lastScrollDirection = ScrollDirection.Right;
			}
		} else if (leftrightflag == ScrollDirection.None) {
			lastScrollDirection = updownflag;
		}

		if (this.mhlistener != null)
			this.mhlistener.ScrollChanged(l, t, oldl, oldt);

		if (!handleworking) {
			// Log.d("", "滚动触发");
			handleworking = true;
			handler.sendMessageDelayed(
					handler.obtainMessage(touchEventId, this), 5);
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}

}
