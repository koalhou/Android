package com.yutong.axxc.parents.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;

/**
 * 
 * 
 * @author zhangyongn
 * 
 */
public class RegisterActivity_Protocol extends YtAbstractActivity implements
		OnClickListener {
	private TextView register_ln_ET;
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	private Button btn_register_agree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_license);
		initViews();
		initListener();
	}

	private void initListener() {
		btn_register_agree.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
	}

	private void initViews() {
		register_ln_ET = (TextView) findViewById(R.id.register_ln_ET);
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		btn_register_agree = (Button) findViewById(R.id.btn_register_agree);

		btn_title_right.setVisibility(View.INVISIBLE);
		tv_top_title.setText(R.string.registerTitle);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register_agree:
			// 同意注册协议
			 startActivity(new Intent(getApplication(),
			 RegisterActivity_InputPhone.class));
			this.finish();
			break;
		case R.id.btn_title_left:
			// 返回登录
			 
			this.finish();
			break;
		default:
			break;
		}
	}

}
