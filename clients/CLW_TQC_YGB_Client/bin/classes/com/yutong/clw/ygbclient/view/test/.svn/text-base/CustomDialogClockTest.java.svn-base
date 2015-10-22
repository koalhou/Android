package com.yutong.clw.ygbclient.view.test;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.view.widget.CustomClockDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CustomDialogClockTest extends Activity {

	private Button showDialogBtn;
	private TextView testView;
	
	private SpannableString addFontSpan() {  
		SpannableString spanString = new SpannableString("36号字体");  
		AbsoluteSizeSpan span = new AbsoluteSizeSpan(40);  
		spanString.setSpan(span, 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		spanString.setSpan(span, 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		return spanString;
	}  
	
	private SpannableStringBuilder addFontSpan2() {  
		String param = "abc"  ;
		String text = param  + "宇通客车fjdkfjdfjdkf" ;

		SpannableStringBuilder ss = new SpannableStringBuilder(text);
		ss.setSpan(new StyleSpan(Typeface.BOLD),0, param.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(30,true), 0, param.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
		ss.setSpan(new AbsoluteSizeSpan(40,true), param.length(), param.length()+5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(16,true), param.length()+5, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		return ss;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_dialog_test);
		
		testView  = (TextView) findViewById(R.id.test);
		testView.setTextSize(10);
		testView.append(addFontSpan2());
		showDialogBtn = (Button) findViewById(R.id.show_Dialog);
		showDialogBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				CustomClockDialog.Builder builder = new CustomClockDialog.Builder(
						CustomDialogClockTest.this);
				builder.setClock_remind_text("厂外通勤到站提醒");
				builder.setClock_remind_textGravity(Gravity.CENTER_HORIZONTAL);
				
				builder.setStation_hint_textSizeToBig(20);
				
				builder.setContentStr("发往[15]一厂[18]的[15]58号[18]班车即将到达[15]\"淋雨房站\"[18]距离[15]1000米[18]");
				
				builder.setStation_hint_textGravity(Gravity.LEFT);
				
				builder.setPositiveBtnTextSize(20);
				builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(), "确定", Toast.LENGTH_SHORT).show();
					}
				});
				builder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_SHORT).show();
					}
				});
				
				builder.create().show();
			}
		});

	}
}
