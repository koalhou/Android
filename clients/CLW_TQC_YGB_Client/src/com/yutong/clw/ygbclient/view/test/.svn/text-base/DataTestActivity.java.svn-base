/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-22 上午9:05:00
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.view.widget.RotateButton;

/**
 * @author zhouzc 2013-11-22 上午9:05:00
 * 
 */
public class DataTestActivity extends Activity {

	TextView tv_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datatest);
		tv_content = (TextView) findViewById(R.id.tv_content);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		Intent t = getIntent();
		if (t.getExtras() != null && t.getExtras().containsKey("data")) {
			tv_content.setText(t.getExtras().getString("data"));
		}
	}
}
