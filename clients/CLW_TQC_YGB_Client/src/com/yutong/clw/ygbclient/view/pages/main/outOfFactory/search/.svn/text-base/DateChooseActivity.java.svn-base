package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.Tools;
import com.yutong.clw.ygbclient.view.bean.GridInfo;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.adapter.MCalendarGridAdapter;
import com.yutong.clw.ygbclient.view.widget.CustomerDatePickerDialog;

public class DateChooseActivity  extends RemindAccessActivity implements OnClickListener{

	/*日期相关*/
	Calendar cal = Calendar.getInstance();

	private GridView gv;
	private TextView tv_time;
	
	/*存储传进来的日期*/
	private int current_year = cal.get(Calendar.YEAR);
	private int current_month = cal.get(Calendar.MONTH);
	private int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
	
	/*今天时间*/
	private int current_year_0 = cal.get(Calendar.YEAR);
	private int current_month_0 = cal.get(Calendar.MONTH);
	private int current_day_0 = cal.get(Calendar.DAY_OF_MONTH);
	
	private List<GridInfo> gdata;
	private MCalendarGridAdapter adapter;
	
	/*日历选择*/
	private ImageView btn_change,triangle;
	
	private Button btn_left,taday_btn;
	
	/*public final static int REQUEST_OK = 1;*/

	private int parentYear,parentMonth,parentDay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_outoffactory_search_calendar);
		initFromIntent(getIntent());
		initViews();
		initListener();
		
		/*刷新日期*/
		reloadData(current_year,current_month);
	}
	
	/*假设传过来一个字符串时间值*/
	private void initFromIntent(Intent intent) {
		
		if (intent != null && intent.getExtras() != null) {
			Bundle bundle = intent.getExtras();  
			current_year = bundle.getInt("year");
			current_month = bundle.getInt("month")-1;
			dayOfMonth = bundle.getInt("day");
			
			parentYear = current_year;
			parentMonth = current_month;
		}
	}
	
	private void initViews(){
		btn_left  =(Button) findViewById(R.id.btn_it_left);
		btn_change = (ImageView) findViewById(R.id.btn_change);
		triangle = (ImageView) findViewById(R.id.iv_it_triangle);
		triangle.setVisibility(View.GONE);
		
		//日期标题
		tv_time  = (TextView) findViewById(R.id.tv_timeshow);
		taday_btn = (Button) findViewById(R.id.taday_btn);
		taday_btn.setOnClickListener(this);
		/* 日历相关 */
		gv = (GridView) findViewById(R.id.gridView_calendar);

		tv_time = (TextView) findViewById(R.id.tv_timeshow);
		String month_tmp = (current_month + 1)> 9 ? (""+(current_month + 1)):("0"+(current_month + 1));
		tv_time.setText(current_year + "年"
				+ month_tmp +"月");
		gdata = new ArrayList<GridInfo>();
		
		gv.setTag(current_month);
		adapter = new MCalendarGridAdapter(this, gdata,gv);

		gv.setAdapter(adapter);
		gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				GridInfo info = gdata.get(position);
				Calendar calendar = info.getDate();
				if (info.isCanbeselect()) {
					
					adapter.changeSelection(view);
					
					/*返回父Activity数据*/
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putInt("year",calendar.get(Calendar.YEAR));
					bundle.putInt("month",calendar.get(Calendar.MONTH)+1);
					bundle.putInt("day",calendar.get(Calendar.DAY_OF_MONTH));
					intent.putExtras(bundle);
					
					setResult(RESULT_OK,intent);
					finish();
				}
			}
			
		});
		
		
	}
	
	private void initListener() {
		
		final CustomerDatePickerDialog datePickerDialog = new CustomerDatePickerDialog(
				getContext(), new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int month,
							int day) {
						if (current_year == year && current_month == month)
							return;
						
						current_year = year;
						current_month = month;
						tv_time.setText(current_year + "/"
								+ (current_month + 1));
						gv.setTag(current_month);
						/*刷新日期*/
						reloadData(current_year,current_month);
					}
				}, current_year, current_month, 1);
		
		btn_change.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				datePickerDialog.setDate(current_year, current_month);
				datePickerDialog.show();
			}
		});
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
		btn_right.setVisibility(View.GONE);

		tv_large.setText("日期选择");
		tv_small.setVisibility(View.GONE);
		
		btn_left.setOnClickListener(this);
	}
	
	private void reloadData(int year, int month) {
		// 默认从第一周开始
		reloadData(year, month, 1);
	}
	
	/* 下面是日历相关 */
	private void reloadData(int year, int month, int week) {
		gdata.clear();
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(year, month, 1);
		calendar.set(Calendar.WEEK_OF_MONTH, week);

		int before = calendar.get(Calendar.DAY_OF_WEEK)-1;
		if (before >= 7)// 一周都是上月的情况
			before = 0;
		
		//日期向后减去before天
		calendar.add(Calendar.DAY_OF_YEAR, -before);//
		
		int month_deal,year_deal;
		for (int i = 0; i < 35; i++) {
			Calendar newc = Calendar.getInstance(Locale.CHINA);
			newc.setTime(calendar.getTime());
			
			GridInfo info = new GridInfo();
			info.setDate(newc);

			Logger.i(getClass(), "newc:"+newc.get(Calendar.MONTH)+"/"+newc.get(Calendar.DAY_OF_MONTH));
			year_deal = newc.get(Calendar.YEAR);
			month_deal = newc.get(Calendar.MONTH);
			
			info.setContent("");
			String day = Tools.getFormatTime(newc);
			
			/*处理今天标志*/
			int tmp = info.getDate().get(Calendar.DAY_OF_MONTH);
			int month0 = info.getDate().get(Calendar.MONTH);
			int year0 = info.getDate().get(Calendar.YEAR);
			if ( tmp == current_day_0 && month0 == current_month_0  && year0 == current_year_0){
				/*info.setIsmarked(true);*/
				info.setToday(true);
				/*info.setIsmarked(true);*/
			}
			
			/*传进来的值标蓝标志*/
			int c_year = info.getDate().get(Calendar.YEAR);
			int c_month = info.getDate().get(Calendar.MONTH);
			if(tmp == dayOfMonth && year == parentYear && month == parentMonth && year == c_year && month == c_month){
				
				info.setIsmarked(true);
			}
			
			/*处理是否可点击状态*/
			/*if(year < current_year_0 ){
				info.setCanbeselect(false);
			}else if(year == current_year_0 && month_deal < current_month_0){
				info.setCanbeselect(false);
			}else if(year == current_year_0 && month_deal == current_month_0){
				if( tmp < current_day_0 ){
					info.setCanbeselect(false);
				}else if(tmp == current_day_0){
					info.setCanbeselect(true);
					info.setToday(true);
				}else{
					info.setCanbeselect(true);
				}
			}else if(year == current_year_0 && month_deal > current_month_0){
				info.setCanbeselect(true);
			}else if(year > current_year_0){
				info.setCanbeselect(true);
			}*/
			
			if(year_deal < current_year_0 ){
				info.setCanbeselect(false);
			}else if(year_deal == current_year_0 && month_deal < current_month_0){
				info.setCanbeselect(false);
			}else if(year_deal == current_year_0 && month_deal == current_month_0){
				if( tmp < current_day_0 ){
					info.setCanbeselect(false);
				}else if(tmp == current_day_0){
					info.setCanbeselect(true);
					info.setToday(true);
				}else{
					info.setCanbeselect(true);
				}
			}else if(year_deal == current_year_0 && month_deal > current_month_0){
				info.setCanbeselect(true);
			}else if(year_deal > current_year_0){
				info.setCanbeselect(true);
			}
			
			/*添加进list*/
			gdata.add(info);
			
			/*日期向前递增一天*/
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		adapter.ClearSelection();
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
			case R.id.btn_it_left:
				onBackPressed();
				break;
			case R.id.taday_btn:
				Calendar calendar = Calendar.getInstance();
				int current_year = calendar.get(Calendar.YEAR);
				int current_month = calendar.get(Calendar.MONTH);
				int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
				
				/*返回父Activity数据*/
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putInt("year",current_year);
				bundle.putInt("month",current_month+1);
				bundle.putInt("day",dayOfMonth);
				intent.putExtras(bundle);
				
				setResult(RESULT_OK,intent);
				finish();
			default:
				
				;
		}
	}
	
	private Context getContext(){
		return this;
	}
}
