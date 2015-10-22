package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.adapter;

import java.util.List;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StationHintAdapter extends BaseAdapter{

	private Context context;
	private List<StationInfo> stationInfoList;
	private ListView searchResultListView;
	private View.OnClickListener deleteBtnListener;
	
	public StationHintAdapter(Context context, List<StationInfo> stationInfoList , ListView searchResultListView) {
		this.context = context;
		this.stationInfoList = stationInfoList;
		this.searchResultListView = searchResultListView;
	}

	@Override
	public int getCount() {
		
		int count = 0;
		if(stationInfoList == null || stationInfoList.size()==0){
			return 0;
		}
		if( (Boolean) searchResultListView.getTag()){
			count = stationInfoList.size()+1;
		}else{
			count = stationInfoList.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {

		/*return stationInfoList.get(position);*/
		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		boolean showClearBtn = (Boolean) searchResultListView.getTag();
		if(showClearBtn && position ==0){
			
			View convertView_0 = View.inflate(context, R.layout.control_stationlist_headerview,null);
			Button deleteHint  =  (Button) convertView_0.findViewById(R.id.clearBtn);
			deleteHint.setOnClickListener(this.deleteBtnListener);
			
			return convertView_0;
		}
		
		View convertView_0 = View.inflate(context, R.layout.control_stationlist_item,null);
		RelativeLayout hintRL_0 = (RelativeLayout) convertView_0.findViewById(R.id.hintRL);
		ImageView flagImage_0 = (ImageView) convertView_0.findViewById(R.id.flagImage);
		TextView stationName_0 = (TextView) convertView_0.findViewById(R.id.tv_cli_name);
			
		try {
			StationInfo item = null;
			if(showClearBtn){
				item = stationInfoList.get(position-1);
			}else{
				item = stationInfoList.get(position);
			}
			
			flagImage_0.setBackgroundResource(item.isSearchHistory() ? R.drawable.ic_search_history:R.drawable.ic_search_current);
			stationName_0.setText(item.getName());
			
		} catch (Exception err) {
			err.printStackTrace();
		}
		
		return convertView_0;
	}
	
	class ViewHolder {
		
		public RelativeLayout hintRL;
		public ImageView flagImage;
		public TextView stationName;
	}

	public void setDeleteBtnListener(View.OnClickListener deleteBtnListener) {
		this.deleteBtnListener = deleteBtnListener;
	}
	
	
}
