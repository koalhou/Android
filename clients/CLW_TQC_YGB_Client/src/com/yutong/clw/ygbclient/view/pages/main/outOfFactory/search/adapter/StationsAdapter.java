package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.adapter;

import java.util.List;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.context.ContextUtil;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizOutOfFactory;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation.SingleStationScheduleActivity;
import com.yutong.clw.ygbclient.view.widget.CustomDialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StationsAdapter extends BaseAdapter {
	
	private List<StationInfo> mData;
	private Context mContext;
	private RelativeLayout leftRL,rightRL;
	private ListView stationListView;
	/*---------------侦听器-----------------*/
    /**
     * 站点收藏点击侦听
     */
    private View.OnClickListener siteFavorClickListener;
    private View.OnClickListener rigthtClickListener;
    
	public StationsAdapter(List<StationInfo> mData, Context mContext, ListView stationListView) {
		this.mData = mData;
		this.mContext = mContext;
		this.stationListView = stationListView;
	}

	@Override
	public int getCount() {
		
		return mData.size();
	}

	@Override
	public Object getItem(int arg0) {

		return mData.get(arg0);
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}
	
	
    
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.listitem_recommandsite,null);
			
			/*leftRL = (RelativeLayout) convertView.findViewById(R.id.leftRL);*/
			
			/*rightRL = (RelativeLayout) convertView.findViewById(R.id.rightRL);
			rightRL.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});*/
			
			holder.Image = (ImageView) convertView.findViewById(R.id.clockiconIV);
			holder.Favourite = (TextView) convertView.findViewById(R.id.setfavorTV);
			holder.Name = (TextView) convertView.findViewById(R.id.siteTV);
			holder.leftRL = (RelativeLayout) convertView.findViewById(R.id.leftRL);
			holder.leftRL.setOnClickListener(this.siteFavorClickListener);
			
			holder.rightRL = (RelativeLayout) convertView.findViewById(R.id.rightRL);
			holder.rightRL.setOnClickListener(this.rigthtClickListener);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			StationInfo item = mData.get(position);
//			holder.leftRL.setBackgroundResource(R.drawable.bg_recommandsite_item_favorite);
			holder.Image.setImageResource(item.isFavorites()? R.drawable.ic_favored_blue:R.drawable.ic_favor_blue);
			holder.Name.setText(item.getName());
			holder.Favourite.setText(item.isFavorites()?"取消收藏":"收藏");
			holder.leftRL.setTag(R.id.tag_station, mData.get(position));
			holder.rightRL.setTag(R.id.tag_station, mData.get(position));
			
		} catch (Exception err) {
			err.printStackTrace();
		}
		return convertView;
	}

	@Override
	public boolean isEmpty() {
		return mData == null || mData.size() == 0;
	}
	
	class ViewHolder {
		public RelativeLayout leftRL,rightRL;
		public ImageView Image;
		public TextView Favourite;
		public TextView Name;
	}
	
	public void setSiteFavorClickListener(View.OnClickListener siteFavorClickListener)
	{
	    this.siteFavorClickListener = siteFavorClickListener;
	}
	
	
	public void setRigthtClickListener(View.OnClickListener rigthtClickListener)
	{
	    this.rigthtClickListener = rigthtClickListener;
	}
}
