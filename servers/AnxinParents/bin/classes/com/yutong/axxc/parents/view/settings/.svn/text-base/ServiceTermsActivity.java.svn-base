package com.yutong.axxc.parents.view.settings;

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
public class ServiceTermsActivity extends YtAbstractActivity implements
		OnClickListener {
	
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_terms);
		initViews();
		initListener();
	}

	private void initListener() {
		
		btn_title_left.setOnClickListener(this);
	}

	private void initViews() {
		
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		

		btn_title_right.setVisibility(View.INVISIBLE);
		tv_top_title.setText("服务条款");

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.btn_title_left:
			
			 
			this.finish();
			break;
		default:
			break;
		}
	}

}
