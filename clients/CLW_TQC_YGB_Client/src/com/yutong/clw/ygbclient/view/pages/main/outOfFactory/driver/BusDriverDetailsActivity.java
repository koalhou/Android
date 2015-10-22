package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.driver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.beans.VehicleDriver;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;
import com.yutong.clw.ygbclient.common.utils.ToastUtils;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizBusDriver;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.ImageUtils;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.widget.CircleImageView;

public class BusDriverDetailsActivity extends RemindAccessActivity implements
		OnClickListener {

	private Button backBtn, mDialPhone_Btn;

	private List<String> mVehicle_vins = new ArrayList<String>();

	private List<String> mLine_ids = new ArrayList<String>();

	private BizBusDriver bizBusDriver;

	private int mLoadingKey = 0x0;

	private TextView busNum_TV, empNum_TV, phoneNum_TV, driverName_TV;

	private String mCurrentVihicleNum;

	private DisplayImageOptions options;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	private CircleImageView mDriverImage;
	
	private String uriStr;
	
	private String mSaveLocation = SysConfigUtil.getDriverPhotoLocation();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_outoffactory_driver_contact);
		initFromIntent();
		initViews();
		initBiz();
		initData();
	}

	private void getDriverImage() {
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.activity_main_outfactory_default_driver)
				.showImageForEmptyUri(R.drawable.activity_main_outfactory_default_driver)
				.showImageOnFail(R.drawable.activity_main_outfactory_default_driver).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
		

		imageLoader.loadImage(uriStr, new SimpleImageLoadingListener() {

			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				mDriverImage.setImageBitmap(loadedImage);

				/*ImageUtil.saveBitmap(loadedImage,mSavePic);*/
				
				try {
					ImageUtils.saveImageToSD(BusDriverDetailsActivity.this, mSaveLocation, loadedImage, 50);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_it_left:
			onBackPressed();
			break;

		case R.id.dialPhone_Btn:
			String phoneNum = phoneNum_TV.getText().toString();
			if (!StringUtil.isEmpty(phoneNum)) {
				ActivityUtils.call((Activity) getContext(), phoneNum);
			} else {
				ToastUtils.show(getContext(), "没有维护的手机号");
			}
			break;
			
		case R.id.driverImage:
			 Bundle bundle = new Bundle();
             bundle.putSerializable(ActivityCommConstant.SAVE_PIC, mSaveLocation);
             ActivityUtils.changeActivity(BusDriverDetailsActivity.this, DriverImgeActivity.class, bundle);
			break;
			
		default:
			break;

		}
	}
	
	private void initViews() {

		mDriverImage = (CircleImageView) findViewById(R.id.driverImage);
		mDriverImage.setOnClickListener(this);
		
		driverName_TV = (TextView) findViewById(R.id.driver_name);
		driverName_TV.setOnClickListener(this);

		busNum_TV = (TextView) findViewById(R.id.busNum_TV);
		empNum_TV = (TextView) findViewById(R.id.empNum_TV);
		phoneNum_TV = (TextView) findViewById(R.id.phoneNum_TV);
		mDialPhone_Btn = (Button) findViewById(R.id.dialPhone_Btn);
		mDialPhone_Btn.setOnClickListener(this);
	}

	private void initFromIntent() {
		Intent intent = getIntent();
		if (intent != null) {

			Bundle bundle = intent.getExtras();
			if (bundle != null
					&& bundle.containsKey(ActivityCommConstant.VEHICHLE)) {
				String vin = (String) bundle.get(ActivityCommConstant.VEHICHLE);
				mVehicle_vins.clear();
				mVehicle_vins.add(vin);
			}
			if (bundle != null
					&& bundle.containsKey(ActivityCommConstant.LINE_ID)) {
				String line_id = (String) bundle
						.get(ActivityCommConstant.LINE_ID);
				mLine_ids.clear();
				mLine_ids.add(line_id);
			}

			if (bundle != null
					&& bundle.containsKey(ActivityCommConstant.VIHICLE_NUM)) {
				mCurrentVihicleNum = (String) bundle
						.get(ActivityCommConstant.VIHICLE_NUM);
			}
		}
	}

	private void initBiz() {

		showLoading("正在登录,请稍候...", mLoadingKey);
		bizBusDriver = new BizBusDriver(getContext(), mVehicle_vins, mLine_ids);
	}
	
	private void initData(){
		
		
		bizBusDriver
		.getVehicheDriverData(new BizResultProcess<List<VehicleDriver>>() {

			@Override
			public void onBizExecuteEnd(BizDataTypeEnum datatype,
					List<VehicleDriver> t) {

				dismissLoading(mLoadingKey);
				final List<VehicleDriver> dirverDataList = t;
				uriStr = dirverDataList.get(0).pic_url;
				
				if(!StringUtil.isEmpty(uriStr)){
					mSaveLocation += uriStr.substring(uriStr.lastIndexOf(File.separator)+1);
				}else{
					mSaveLocation = uriStr;
				}
				
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (dirverDataList != null
								&& dirverDataList.size() > 0) {
							
							VehicleDriver vehicleDriver = dirverDataList
									.get(0);
							setPageInfo(vehicleDriver);
							if(!StringUtil.isEmpty(uriStr)){
								getDriverImage();
							}
						}
					}
				});
			}

			@Override
			public void onBizExecuteError(Exception exception,
					Error error) {
				dismissLoading(mLoadingKey);

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
		backBtn = btn_left;
		backBtn.setOnClickListener(this);

		btn_right.setVisibility(View.GONE);
		tv_large.setText("联系司机");
		tv_small.setVisibility(View.GONE);
		iv_tri.setVisibility(View.GONE);
	}

	

	private void setPageInfo(VehicleDriver vehicleDriver) {
		if (vehicleDriver != null) {
			driverName_TV.setText(vehicleDriver.driver_name);
			busNum_TV.setText(mCurrentVihicleNum);
			empNum_TV.setText(vehicleDriver.emp_code);
			phoneNum_TV.setText(vehicleDriver.driver_tel);
		} else {
			driverName_TV.setText("--");
			empNum_TV.setText("--");
			phoneNum_TV.setText("--");
		}
	}

	private Context getContext() {
		return this;
	}
}
