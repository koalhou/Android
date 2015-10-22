package com.yutong.clw.ygbclient.view.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;

/**
 * 自定义模态对话框
 * 
 * @author zhouzc
 * 
 */
public abstract class CustomAlertDialog extends AlertDialog implements
		android.view.View.OnClickListener {

	private Context mContext;

	protected CustomAlertDialog(Context context) {
		super(context);
		commonWorks(context);
	}

	protected CustomAlertDialog(Context context, int theme) {
		super(context, theme);
		commonWorks(context);
	}

	private void commonWorks(Context context) {
		mContext = context;
	}

	View mView = null;
	Button btn_ok, btn_cancel;
	RelativeLayout rl_parent;
	LinearLayout ll_container;
	TextView tv_title;

	String okStr = "确定";
	String cancelStr = "取消";

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
	}

	public String getOkStr() {
		return okStr;
	}

	public void setOkStr(String okStr) {
		this.okStr = okStr;
		if (btn_ok != null)
			btn_ok.setText(okStr);
	}

	public String getCancelStr() {
		return cancelStr;
	}

	public void setCancelStr(String cancelStr) {
		this.cancelStr = cancelStr;
		if (btn_cancel != null)
			btn_cancel.setText(cancelStr);
	}

	@Override
	public void show() {
		super.show();
		initialViews(mContext);// 必须放在这里，不然会报错
	}

	private void initialViews(Context context) {
		if (mView == null) {
			View v = View.inflate(context, R.layout.control_floatdialog, null);
			ViewGroup.LayoutParams lp = new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			this.setContentView(v, lp);
			mView = v;

			rl_parent = (RelativeLayout) mView
					.findViewById(R.id.settingsBackGroundRL);

			ll_container = (LinearLayout) mView
					.findViewById(R.id.ll_sl_container);
			btn_ok = (Button) mView.findViewById(R.id.settingsLogoutButton);
			btn_cancel = (Button) mView.findViewById(R.id.settingsCancelButton);
			tv_title = (TextView) mView.findViewById(R.id.settingsAlertTV);

			rl_parent.setOnClickListener(this);
			ll_container.setOnClickListener(this);
			btn_ok.setOnClickListener(this);
			btn_cancel.setOnClickListener(this);

			this.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					ll_container.setVisibility(View.GONE);
					onUserCancel();
				}
			});
		}

		tv_title.setText(this.getWindow().getAttributes().getTitle());
		btn_ok.setText(okStr);
		btn_cancel.setText(cancelStr);

		if (mContext instanceof Activity) {
			WindowManager m = ((Activity) mContext).getWindowManager();
			Display d = m.getDefaultDisplay();
			WindowManager.LayoutParams wparams = this.getWindow()
					.getAttributes();
			wparams.dimAmount = 0f;// dialog背景不变暗
			wparams.width = d.getWidth();
			wparams.height = WindowManager.LayoutParams.WRAP_CONTENT;
			this.getWindow().setAttributes(wparams);
		}

		ll_container.setVisibility(View.VISIBLE);

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

	public abstract void onUserCancel();

	public abstract void onUserOK();

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.settingsBackGroundRL:
			Log.d(getClass().toString(), "onclick BackGroundRL");
			this.cancel();
			break;
		case R.id.settingsCancelButton:
			Log.d(getClass().toString(), "onclick CancelButton");
			this.cancel();
			break;
		case R.id.settingsLogoutButton:
			Log.d(getClass().toString(), "onclick LogoutButton");
			onUserOK();
			this.dismiss();
			break;
		default:
			break;
		}
	}
}
