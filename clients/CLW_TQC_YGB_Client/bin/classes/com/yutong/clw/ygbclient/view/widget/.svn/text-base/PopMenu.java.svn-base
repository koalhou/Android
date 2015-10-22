package com.yutong.clw.ygbclient.view.widget;

import java.util.ArrayList;
import java.util.List;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.beans.VehicleDriver;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.common.utils.ToastUtils;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.OutOfFactoryActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.driver.BusDriverDetailsActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class PopMenu {
	
	private List<VehicleDriver> vehicleDriverList = new ArrayList<VehicleDriver>(); 
	private Context mContext;
	private PopupWindow mPop;
	private ListView listView;
	private int popwidth = -1;
	private PopAdapter adapter;
	private View.OnClickListener mClickListener;
	
	public PopMenu(Context context) {
		this.mContext = context;
		initView();
	}
	
	public PopMenu(Context mContext, int popwidth) {
		super();
		this.mContext = mContext;
		this.popwidth = popwidth;
		initView();
	}
	
	private void initView() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.control_popmenu, null);

		adapter = new PopAdapter();
		listView = (ListView) view.findViewById(R.id.driver_info_listview);
		listView.setAdapter(adapter);
		listView.setFocusableInTouchMode(true);
		listView.setFocusable(true);
		
		if(popwidth==-1){
			mPop = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		}else{
			mPop = new PopupWindow(view, popwidth, LayoutParams.WRAP_CONTENT);
		}
		
		mPop.setBackgroundDrawable(new BitmapDrawable());
		mPop.setOutsideTouchable(true);
	}

	public void setData(List<VehicleDriver> vehicleDriverList) {

		this.vehicleDriverList.clear();
		this.vehicleDriverList.addAll(vehicleDriverList);
	}
	
	/**
	 * 刷新数据显示
	 */
	public void notifyDataSetChanged() {
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}

	public void showAsDropDown(View anchor, int xoff, int yoff) {
		mPop.showAsDropDown(anchor, xoff, yoff);
	}

	public void showAtLocation(View parent, int gravity, int xoff, int yoff) {
		mPop.showAtLocation(parent, gravity, xoff, yoff);
	}

	
	public void dismiss() {
		mPop.dismiss();
	}

	// 适配器
	private final class PopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return vehicleDriverList.size();
		}

		@Override
		public Object getItem(int position) {
			return vehicleDriverList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				
				holder = new ViewHolder();
				
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.control_popmenu_item, null);
				holder.driver_phone_num_TV = (TextView) convertView.findViewById(R.id.driver_phone_num_TV);
				holder.driver_name_TV = (TextView) convertView.findViewById(R.id.driver_name_TV);
				holder.driver_phone_TV = (TextView) convertView.findViewById(R.id.driver_phone_TV);
				holder.arrowIV = (ImageView) convertView.findViewById(R.id.arrowIV);
				
				holder.driver_phone_TV.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String phoneNum = vehicleDriverList.get(0).driver_tel;
						if(!StringUtil.isEmpty(phoneNum)){
							ActivityUtils.call((Activity) mContext, phoneNum);
						}else{
							ToastUtils.show(mContext, "没有维护的手机号");
						}
					}
				});
				holder.arrowIV.setOnClickListener(null);
				holder.arrowIV.setOnClickListener(mClickListener);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.driver_phone_num_TV.setText(vehicleDriverList.get(0).vehicleNum);
			holder.driver_name_TV.setText(vehicleDriverList.get(0).driver_name);
			holder.driver_phone_TV.setText(Html.fromHtml("<u>"+vehicleDriverList.get(0).driver_tel+"</u>"));
			
			
			return convertView;
		}

		private final class ViewHolder {
			TextView driver_phone_num_TV;
			TextView driver_name_TV;
			TextView driver_phone_TV;
			ImageView arrowIV;
		}
	}

	public void setmClickListener(View.OnClickListener mClickListener) {
		this.mClickListener = mClickListener;
	}
	
	
}
