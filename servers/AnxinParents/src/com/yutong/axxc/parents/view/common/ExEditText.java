package com.yutong.axxc.parents.view.common;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.common.context.StringUtil;

/**
 * 自定义输入框组合控件，实现错误提示，清除按钮。可扩展的验证逻辑
 * 
 * @author zhangyongn
 * 
 */
public class ExEditText extends LinearLayout {

	private EditText inputET;

	private Set<OnValidateListener> mListeners = new HashSet<OnValidateListener>(); // 监听事件接口

	private ImageView labelIV;

	private ImageView alertIV;

	private ImageView clearIV;
	private String errMsg;
	private boolean showRightIcon=true;

	public ExEditText(Context context) {
		super(context, null);
	}

	public void addOnValidateListener(OnValidateListener listener) {
		mListeners.add(listener);

	}

	public void removeOnValidateListener(OnValidateListener listener) {
		mListeners.remove(listener);
	}

	/** 自定义接口 */
	public static interface OnValidateListener {
		String onValidate(String text);

	}

	public ExEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode())
			return;
		LayoutInflater.from(context).inflate(R.layout.control_exedittext, this,
				true);
		initViews();
		registerListener();

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.attr_exedittext);
		// 获得xml里定义的属性,格式为 名称_属性名 后面是默认值
		String hintText = a.getString(R.styleable.attr_exedittext_hintText);
		inputET.setHint(hintText);

		Drawable d = a.getDrawable(R.styleable.attr_exedittext_labelIcon);
		if (d != null) {
			labelIV.setBackgroundDrawable(d);
			labelIV.setVisibility(View.VISIBLE);
		} else {
			labelIV.setVisibility(View.INVISIBLE);
		}
		float paddingLeft = a.getDimension(
				R.styleable.attr_exedittext_paddingLeft, 30);
		inputET.setPadding((int) paddingLeft, 0, 0, 0);

		// 为了保持以后使用该属性一致性,返回一个绑定资源结束的信号给资源
		a.recycle();
	}

	private void registerListener() {

		clearIV.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				inputET.setText("");
			}
		});

		inputET.addTextChangedListener(new InputTextWatcher());

	}

	private void initViews() {

		inputET = (EditText) findViewById(R.id.inputET);
		alertIV = (ImageView) findViewById(R.id.alertIV);

		clearIV = (ImageView) findViewById(R.id.clearIV);
		labelIV = (ImageView) findViewById(R.id.labelIV);

	}

	public String getText() {
		return inputET.getText().toString();
	}

	public void setText(CharSequence text) {
		inputET.setText(text);
	}

	public EditText getEditText() {
		return inputET;
	}

	public boolean isValid() {
		return StringUtil.isNull(errMsg);
	}
	/**
	 * 验证数据
	 * @return 通过则返回true
	 */
	public boolean validate() {
		if (!showRightIcon)
			return true;
		errMsg = "";
		for (OnValidateListener mListener : mListeners) { // 自定义接口
			String t = inputET.getText().toString();
			String msg = mListener.onValidate(t);
			if (msg != "") {
				clearIV.setVisibility(View.INVISIBLE);
				alertIV.setVisibility(View.VISIBLE);
				errMsg += msg;
				return false;
			}
		}
		alertIV.setVisibility(View.INVISIBLE);
		return true;
	}

	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @return the showRightIcon
	 */
	public boolean isShowRightIcon() {
		return showRightIcon;
	}

	/**
	 * @param showRightIcon
	 *            the showRightIcon to set
	 */
	public void setShowRightIcon(boolean showRightIcon) {
		this.showRightIcon = showRightIcon;
		if (!showRightIcon) {
			alertIV.setVisibility(View.INVISIBLE);
			clearIV.setVisibility(View.INVISIBLE);
		}
	}

	private class InputTextWatcher implements TextWatcher {

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
		 */
		@Override
		public void afterTextChanged(Editable s) {
			if (!showRightIcon)
				return;
			alertIV.setVisibility(ImageView.INVISIBLE);
			String userName = StringUtil.parseStr(inputET.getText());
			if (userName == null || "".equals(userName)) {
				clearIV.setVisibility(ImageView.INVISIBLE);
			} else {
				clearIV.setVisibility(ImageView.VISIBLE);
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence,
		 *      int, int, int)
		 */
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence,
		 *      int, int, int)
		 */
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}
	}
}
