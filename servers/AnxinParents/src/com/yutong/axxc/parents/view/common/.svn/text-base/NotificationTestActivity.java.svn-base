package com.yutong.axxc.parents.view.common;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yutong.axxc.parents.R;

public class NotificationTestActivity extends YtAbstractActivity implements
		OnClickListener {

	private TextView tv_new_content;
	private ImageView tv_new_image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_detail_test);

		initViews();
		initListener();
	}

	private void initListener() {

	}

	private void initViews() {
		tv_new_content = (TextView) findViewById(R.id.noticedetail_text);
		tv_new_image = (ImageView) findViewById(R.id.noticedetail_image);
		Intent intent = getIntent();
		tv_new_content.setText(intent.getStringExtra("TXT"));
		
		Context context = YtApplication.getInstance();
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);
		mNotificationManager.cancel(1);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:

			break;
		case R.id.btn_title_left:
			finish();
			break;
		default:
			break;
		}

	}
}
