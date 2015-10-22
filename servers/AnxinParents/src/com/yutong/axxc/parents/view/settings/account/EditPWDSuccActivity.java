package com.yutong.axxc.parents.view.settings.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.settings.SettingHomeActivity;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 * 
 */
public class EditPWDSuccActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_account_editpwd_succ);
		initViews();
		initListener();
	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		// loadingoverlay
		// .addOnCancelListener(new LoadingOverlay.OnCancelListener() {
		//
		// @Override
		// public void onCancel() {
		//
		// loadingoverlay.setVisibility(View.INVISIBLE);
		//
		// }
		// });

	}

	private void initViews() {
		// loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_left.setText(R.string.return_back);
		btn_title_right.setVisibility(View.INVISIBLE);
		tv_top_title.setText(R.string.update_password);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:
			finish();
		    break;
		case R.id.btn_title_left:
		    finish();

			break;
		default:
			break;
		}

	}
}
