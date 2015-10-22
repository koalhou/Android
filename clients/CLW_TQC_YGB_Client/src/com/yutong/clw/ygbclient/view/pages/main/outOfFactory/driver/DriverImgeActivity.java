package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.driver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.ImageUtils;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;

public class DriverImgeActivity extends RemindAccessActivity implements OnClickListener {
	
	private ImageView driverImage;
	private RelativeLayout rootRL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_image);
		getIntentData();
		driverImage  =(ImageView) findViewById(R.id.driverImage);
		
		if(!StringUtil.isEmpty(imageUri)){
			Bitmap bitmap = BitmapFactory.decodeFile(imageUri);
			driverImage.setBackgroundDrawable(new BitmapDrawable(ImageUtils.zoomBitMap(bitmap, 6.0f,  6.0f)));
		}else{
			driverImage.setBackgroundResource(R.drawable.driver_image_rectangle);
		}
		
		rootRL = (RelativeLayout) findViewById(R.id.root_RL);
		rootRL.setOnClickListener(this);
	}
	
	private String imageUri;
	private void getIntentData(){
		Intent intent = getIntent();
		
		if(intent!=null){
			Bundle bundle = intent.getExtras();
			imageUri = (String) bundle.get(ActivityCommConstant.SAVE_PIC);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.driverImage:
			
			break;
		
		case R.id.root_RL:
			onBackPressed();
			break;
			
		default:
			break;
		}
	}

}
