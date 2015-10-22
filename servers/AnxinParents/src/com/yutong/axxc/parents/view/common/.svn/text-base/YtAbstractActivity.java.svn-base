/*******************************************************************************
 * @(#)YtAbstractActivity.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.yutong.axxc.parents.view.common;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yutong.axxc.parents.view.common.ActivityCoverManager.CoverInitialListener;
import com.yutong.axxc.parents.view.common.LoadingOverlay.OnCancelListener;

/**
 * 抽象Activity
 * 
 * @author <a href="mailto:fengzht@neusoft.com">Jason Feng</a>
 * @version $Revision 1.1 $ 2013-3-18 下午6:28:15
 */
public class YtAbstractActivity extends Activity {
	private InputMethodManager manager = null;

	private LoadingOverlay mloadingoverlay;

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onCancelListeners = new HashMap<Integer, LoadingOverlay.OnCancelListener>();
		manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		WeakReference<Activity> reference = new WeakReference<Activity>(this);
		ActivityManager.addActivity(reference);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		// 加载引导页
		if (YtApplication.getInstance().getActivityCoverManager()
				.hasCover(this)) {
			if (YtApplication.getInstance().getActivityCoverManager()
					.hasShowCover(this))
				return;
			YtApplication.getInstance().getActivityCoverManager()
					.showCover(this, new CoverInitialListener() {
						@Override
						public void OnInitial(View cover) {
							OnShowCover(cover);
						}
					});
		}
	}

	/**
	 * 引导页显示的时候会被调用
	 * 
	 * @param coverView
	 *            引导页的View
	 */
	protected void OnShowCover(View coverView) {

	}

	/**
	 * 显示加载中
	 * 
	 * @param msg
	 *            提示信息
	 */
	/**
	 * 显示加载中
	 * 
	 * @param msg
	 *            提示信息
	 * @param keycode
	 *            唯一CODE，会在{@link #onLoadingCanceled }中用到
	 */
	protected void showLoading(String msg, int keycode) {
		Log.d("", "showLoading " + msg);
		isloading = true;
		final int key = keycode;
		if (!onCancelListeners.containsKey(key)) {
			onCancelListeners.put(key, new OnCancelListener() {
				@Override
				public void onCancel() {
					onLoadingCanceled(key);
				}
			});
		}
		if (mloadingoverlay != null) {
			mloadingoverlay.setVisibility(View.VISIBLE);
			mloadingoverlay.setLoadingTip(msg);
			mloadingoverlay.bringToFront();
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		initLoadingView();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}
	
	private boolean isloading = false;

	private HashMap<Integer, OnCancelListener> onCancelListeners;

	private void initLoadingView() {
		if (mloadingoverlay == null) {
			mloadingoverlay = new LoadingOverlay(this);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			this.addContentView(mloadingoverlay, lp);
			if (isloading) {
				mloadingoverlay.setVisibility(View.VISIBLE);
				mloadingoverlay.bringToFront();
			} else {
				mloadingoverlay.setVisibility(View.GONE);
			}

			mloadingoverlay.addOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel() {
					isloading = false;
					dismissLoading();
					if (onCancelListeners != null
							&& !onCancelListeners.isEmpty()) {
						for (Integer key : onCancelListeners.keySet()) {
							onCancelListeners.get(key).onCancel();
						}
					}
				}
			});
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (isloading) {
				cancelLoading();
				return true;
			}
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void cancelLoading() {
		if (mloadingoverlay != null) {
			mloadingoverlay.setVisibility(View.GONE);
			mloadingoverlay.cancel();
		} else
			isloading = false;
	}

	/**
	 * 隐藏加载中
	 */
	protected void dismissLoading() {
		isloading = false;
		if (mloadingoverlay != null)
			mloadingoverlay.setVisibility(View.GONE);
	}

	/**
	 * 在加载中进度条取消的时候会调用的函数
	 * 
	 * @param key
	 *            唯一CODE，在{@link #showLoading(String, int)}中的第二个参数设置
	 */
	protected void onLoadingCanceled(int key) {

	}

	/**
	 * Toast提示，保证在UI线程执行提示
	 * 
	 * @param msg
	 */
	protected void ToastMessage(final String msg) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(YtAbstractActivity.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}
}
