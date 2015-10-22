package com.yutong.clw.ygbclient.view.test;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.DateChooseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DateTestActivity extends Activity {

	TextView view;
	Button btn;
	private final static int REQUEST_CODE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_test);

		view = (TextView) findViewById(R.id.text);
		btn = (Button) findViewById(R.id.testBtn);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				view.setText("");
				
				Intent intent = new Intent(DateTestActivity.this,
						DateChooseActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("year", 2013);
				bundle.putInt("month", 11);
				bundle.putInt("day", 12);
				intent.putExtras(bundle);
				
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				Bundle bundle = data.getExtras();
				view.append(bundle.getInt("year")+"/"+(bundle.getInt("month"))+"/"+bundle.getInt("day"));
			}
		}
	}

}
