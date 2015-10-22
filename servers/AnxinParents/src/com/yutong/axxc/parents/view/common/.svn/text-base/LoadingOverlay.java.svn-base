package com.yutong.axxc.parents.view.common;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yutong.axxc.parents.R;

/**
 * 加载提示
 * 
 * @author zhangyongn
 * 
 */
public class LoadingOverlay extends LinearLayout {

	private Set<OnCancelListener> mListeners = new HashSet<OnCancelListener>(); // 监听事件接口

	private Button btn_loading_cancel;
	private TextView loading_tipTV;

	public LoadingOverlay(Context context) {
		this(context, null);
	}

	public void addOnCancelListener(OnCancelListener listener) {
		mListeners.add(listener);
	}

	public void removeOnCancelListener(OnCancelListener listener) {
		mListeners.remove(listener);
	}

	/** 自定义接口 */
	public static interface OnCancelListener {
		void onCancel();

	}

	public LoadingOverlay(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode())
			return;
		LayoutInflater.from(context).inflate(R.layout.loading_overlay, this,
				true);
		initViews();
		registerListener();

	}

	private void registerListener() {
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 空方法，必须有。防止加载层点击事件传递到底层，触发其他点击事件。
			}
		});
		btn_loading_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cancel();

			}

		});
	}

	public void cancel() {
		for (OnCancelListener mListener : mListeners) { // 自定义接口
			mListener.onCancel();
		}

	}

	private void initViews() {

		btn_loading_cancel = (Button) findViewById(R.id.btn_loading_cancel);
		loading_tipTV = (TextView) findViewById(R.id.loading_tipTV);
	}

	public void setLoadingTip(CharSequence text) {
		loading_tipTV.setText(text);
	}

	public String getLoadingTip() {
		return loading_tipTV.getText().toString();
	}

}
