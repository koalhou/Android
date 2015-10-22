package com.yutong.axxc.parents.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.home.MainActivity;

/**
 * 
 * 
 * @author zhangyongn
 * 
 */
public class FindPWDActivity_Succ extends YtAbstractActivity implements
		OnClickListener {
	private UserInfoBean userInfoBean;
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;

	private TextView succtitle;
	private TextView succtip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpwd_succ);
		initUserInfo();
		initViews();
		initListener();
	}

	private void initListener() {

		btn_title_left.setOnClickListener(this);
		btn_title_right.setOnClickListener(this);

	}

	/**
	 * 初始化用户信息
	 */
	private void initUserInfo() {
		Intent intent = this.getIntent();
		userInfoBean = intent
				.getParcelableExtra(ActivityCommConstant.USER_INFO);

	}

	private void initViews() {

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		succtip = (TextView) findViewById(R.id.succtip);
		succtitle = (TextView) findViewById(R.id.succtitle);

		btn_title_right.setText(R.string.logintext);

		tv_top_title.setText(R.string.findpwdtitle);
		btn_title_left.setVisibility(View.INVISIBLE);

		succtitle.setText("密码已重新设定");
		String tip = "您已成功设定密码，请重新登录。";
		succtip.setText(tip);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_title_right:

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
