package com.yutong.clw.ygbclient.view.pages.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.RadioButton;
import android.widget.TabHost;

import com.baidu.location.LocationClient;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.utils.NetworkCheckUitl;
import com.yutong.clw.ygbclient.dao.login.LoginInfoDao;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.AnimCommon;
import com.yutong.clw.ygbclient.view.pages.debugtool.DebugToolActivity;
import com.yutong.clw.ygbclient.view.pages.main.betweenFactory.BetweenFactoryActivity;
import com.yutong.clw.ygbclient.view.pages.main.inFactory.InFactoryActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.OutOfFactoryActivity;
import com.yutong.clw.ygbclient.view.widget.CustomAlertDialog;

/**
 * 主界面
 * 
 * @author zhouzc
 */
public class MainActivity extends TabActivity implements OnClickListener,
		OnTouchListener {
	
	private CustomAlertDialog 	menuDialog = null;

	TabHost 					tabHost;

	GestureDetector 			gdetector;

	RadioButton 				inFactory_RadioButton, betweenFactory_RadioButton, outFactory_RadioButton;

	private int 				initSelectTabIndex = 0;

	private LocationClient 		mLocationClient;
	
	
	private List<Integer> 		secretKeys = Arrays.asList(R.id.rbtn_main_infactory,
												R.id.rbtn_main_infactory, 
												R.id.rbtn_main_infactory, 
												R.id.rbtn_main_infactory,
												R.id.rbtn_main_outfactory);
	
	private int 				secretindex = 0;

	private static int 			indexInFactory = 0,indexOutFactory = 1,indexBetweenFactory = 2;
	
	private List<String> 		callContextNames = new ArrayList<String>();

	private Bundle 				newIntentBundle = null;
	
	private static 	MainActivity instance = null;
	
	public static MainActivity getInstance() {
		
		return instance;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		instance = this;
		afterGetNewIntent(getIntent());
		
//		test
		UserInfo emp_info = new LoginInfoDao(MainActivity.this).getLoginInfo();
		Logger.i(getClass(), "emp_info.Code = :"+emp_info.Code);
		Logger.i(getClass(), "emp_info.DefaultPassword = :"+emp_info.DefaultPassword);
		Logger.i(getClass(), "emp_info.Phone = :"+emp_info.Phone);
	}

	private void initViews() {
		
		inFactory_RadioButton = (RadioButton) findViewById(R.id.rbtn_main_infactory);
		inFactory_RadioButton.setOnClickListener(this);
		
		outFactory_RadioButton = (RadioButton) findViewById(R.id.rbtn_main_outfactory);
		outFactory_RadioButton.setOnClickListener(this);
		
		betweenFactory_RadioButton = (RadioButton) findViewById(R.id.rbtn_main_betweenfactory);
		betweenFactory_RadioButton.setOnClickListener(this);

		initTab(initSelectTabIndex);
	}

	public Bundle getNewIntentBundle(String myname) {
		Logger.i(getClass(), "[" + myname + "]来获取ResumeBundle");
		if (callContextNames.contains(myname)) {
			Logger.i(getClass(), "[" + myname + "]已经获取过了，返回null");
			return null;
		} else {
			callContextNames.add(myname);
			Logger.i(getClass(), "获取到ResumeBundle:" + newIntentBundle);
			return newIntentBundle;
		}
	}

	/**
	 * 初始化Tab页
	 * 
	 * @param sindex
	 *            默认选择页序号
	 */
	private void initTab(int sindex) {
		
		tabHost = getTabHost();
		
		tabHost.addTab(tabHost.newTabSpec("in").setIndicator("in")
				.setContent(new Intent(this, InFactoryActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("out").setIndicator("out")
				.setContent(new Intent(this, OutOfFactoryActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("between").setIndicator("between")
				.setContent(new Intent(this, BetweenFactoryActivity.class)));
		
		switch2tab(sindex);
	}

	/**
	 * 跳转到制定Tab页
	 * 
	 * @param index
	 */
	private void switch2tab(int index) {
		
		if (tabHost.getCurrentTab() == index)
			return;
		
		switch (index) {
		
		case 0:
			inFactory_RadioButton.setChecked(true);
			outFactory_RadioButton.setChecked(false);
			betweenFactory_RadioButton.setChecked(false);
			
			/*用户行为统计-跳转到厂内通勤*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CNTQ_QHCQ).reportBehavior(null);

            break;
		
		case 1:			
			/*屏蔽厂外通勤切换时状态*/
			inFactory_RadioButton.setChecked(false);
			outFactory_RadioButton.setChecked(true);
			betweenFactory_RadioButton.setChecked(false);
			
			/*用户行为统计-跳转到厂外通勤*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_QHCQ).reportBehavior(null);
			break;
		
		case 2:
			inFactory_RadioButton.setChecked(false);
			outFactory_RadioButton.setChecked(false);
			betweenFactory_RadioButton.setChecked(true);
			
			/*用户行为统计-跳转到厂间通勤*/
			new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CJTQ_QHCQ).reportBehavior(null);
			
			break;

		}
		/*if (index >= 0 && index <= 2 && index != 1){
			
			tabHost.setCurrentTab(index);
			
		}else if(index >= 0 && index <= 2 && index == 1){
			
			Toast.makeText(MainActivity.this, "敬请期待", Toast.LENGTH_SHORT).show();
		}*/
		
		if (index >= 0 && index <= 2){
			
			tabHost.setCurrentTab(index);
		}
	}

	private void afterGetNewIntent(Intent intent) {
		callContextNames.clear();
		if (intent == null)
			return;
		newIntentBundle = intent.getExtras();
		if (newIntentBundle != null
				&& newIntentBundle.containsKey(ActivityCommConstant.PUSHBEAN)) {
			switch2tab(0);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		
		afterGetNewIntent(intent);
		super.onNewIntent(intent);

	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		
		case R.id.rbtn_main_infactory:
			
			if (((RadioButton) v).isChecked()){
				switch2tab(indexInFactory);
			}
			break;
			
		case R.id.rbtn_main_outfactory:
			
			if (((RadioButton) v).isChecked()){
				switch2tab(indexOutFactory);
			}
			break;

		case R.id.rbtn_main_betweenfactory:
			
			if (((RadioButton) v).isChecked()){
				switch2tab(indexBetweenFactory);
			}
			break;

		default:
			break;
		}
	}
	
	
	private void checkSecretKey(int id) {
		if (id == secretKeys.get(secretindex))
			secretindex++;
		else
			secretindex = 0;
		if (secretindex >= secretKeys.size()) {
			gotoDebugTool();
			secretindex = 0;
		}
	}

	@Override
	protected void onPause() {

		if (AnimCommon.in != 0 && AnimCommon.out != 0) {
			super.overridePendingTransition(AnimCommon.in, AnimCommon.out);
			AnimCommon.clear();
		}
		super.onPause();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				backHome();
			}
			return true;
		}
		
		if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				popUpExitConfirm();
			}
			return true;
		}
		
		return super.dispatchKeyEvent(event);
	}

	private void backHome() {

		/* 返回桌面，关闭gps设备，节电模式 */
		if (isGpsEnable()) {
			stopBaiduLocationClient();
		}
		
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	private boolean isGpsEnable(){
		
		return NetworkCheckUitl.isGpsEnable(getApplicationContext());
	}
	
	private void stopBaiduLocationClient(){
		mLocationClient = YtApplication.getInstance().getBaiduMapManager().getLocationClient();
		if (mLocationClient.isStarted())
			mLocationClient.stop();
	}
	
	private void popUpExitConfirm() {
		
		if (menuDialog == null) {
			menuDialog = new CustomAlertDialog(this) {

				@Override
				public void onUserOK() {
					YtApplication.getInstance().exit();
					this.dismiss();
				}

				@Override
				public void onUserCancel() {
				}
			};
			menuDialog.setTitle("如果退出，您将无法再收到消息和提醒");
			menuDialog.setCancelStr("取消");
			menuDialog.setOkStr("退出");
			menuDialog.show();
		} else {
			if (menuDialog.isShowing()) {
				menuDialog.dismiss();
			} else {
				menuDialog.show();
			}
		}
	}

	

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		return gdetector.onTouchEvent(arg1);

	}

	private void gotoDebugTool() {
		Intent i = new Intent(MainActivity.this, DebugToolActivity.class);
		// i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		startActivity(i);
	}

	

}
