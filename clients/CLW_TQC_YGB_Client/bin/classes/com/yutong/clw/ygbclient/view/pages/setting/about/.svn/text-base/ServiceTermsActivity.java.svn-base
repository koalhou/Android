package com.yutong.clw.ygbclient.view.pages.setting.about;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;

public class ServiceTermsActivity extends RemindAccessActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_about_serviceterms);
	}

	public void initViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void loadTitleByPageSetting(Button btn_left, Button btn_right,
			RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
			TextView tv_small) {
		btn_left.setBackgroundResource(R.drawable.bg_prevbtn);
		btn_right.setVisibility(View.INVISIBLE);
		tv_large.setText("服务条款");
		tv_small.setVisibility(View.GONE);
		iv_tri.setVisibility(View.GONE);
		
		btn_left.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_it_left:
			finish();
			break;

		default:
			break;
		}
		
	}

}
