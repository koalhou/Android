package com.yutong.axxc.parents.view.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.yutong.axxc.parents.R;

public class FlowDialog extends AlertDialog implements
		android.view.View.OnClickListener {

	Context mContext = null;

	public FlowDialog(Context context) {
		super(context);
		mContext = context;
	}

	public FlowDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	public FlowDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
	}

	Button btn_updata, btn_cancel;
	TextView tv_title;
	View contentView = null;

	String okstr = "确定";
	String cancelstr = "取消";

	public String getOkstr() {
		return okstr;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			this.cancel();
			return true;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void setOkstr(String okstr) {
		this.okstr = okstr;
		if (btn_updata != null)
			btn_updata.setText(okstr);
	}

	public String getCancelstr() {
		return cancelstr;
	}

	public void setCancelstr(String cancelstr) {
		this.cancelstr = cancelstr;
		if (btn_cancel != null)
			btn_cancel.setText(cancelstr);
	}

	@Override
	public void show() {

		super.show();
		if (!((Activity) mContext).isFinishing()) {
			try {
				OnInitialViews(this);
				initviews();
			} catch (Exception err) {
				err.printStackTrace();
			}
		}

	}

	protected void OnInitialViews(Dialog dia) {

	}

	private void initviews() {
		this.setCancelable(false);

		if (contentView == null) {

			contentView = View.inflate(mContext, R.layout.flowdialog, null);
			ViewGroup.LayoutParams lp = new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			this.setContentView(contentView, lp);

			btn_updata = (Button) findViewById(R.id.btn_fd_update);
			btn_cancel = (Button) findViewById(R.id.btn_fd_cancel);
			tv_title = (TextView) findViewById(R.id.tv_fd_title);

			btn_updata.setOnClickListener(this);
			btn_cancel.setOnClickListener(this);

			this.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					FlowDialog.this.onCancel();

				}
			});
		}

		if (mContext instanceof Activity) {

			WindowManager m = ((Activity) mContext).getWindowManager();
			Display d = m.getDefaultDisplay();
			WindowManager.LayoutParams wparams = this.getWindow()
					.getAttributes();
			wparams.dimAmount = 0f;// dialog背景不变暗
			wparams.width = d.getWidth();
			wparams.height = WindowManager.LayoutParams.WRAP_CONTENT;
			this.getWindow().setAttributes(wparams);

			tv_title.setText(wparams.getTitle());

		}
		btn_cancel.setText(cancelstr);
		btn_updata.setText(okstr);
	}

	/**
	 * 点击确定后触发
	 */
	public void onOK() {

	}

	public void onCancel() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_fd_cancel:
			this.cancel();
			break;
		case R.id.btn_fd_update:
			onOK();
			this.dismiss();
			break;
		default:
			break;
		}

	}

}
