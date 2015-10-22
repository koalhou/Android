package com.yutong.clw.ygbclient.view.pages.setting.announcement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yutong.clw.ygbclient.R;

import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AnnounceAdapter extends BaseAdapter {

	private List<HashMap<String, Object>> dataSource = new ArrayList<HashMap<String, Object>>();
	private Context mContext;

	public AnnounceAdapter(List<HashMap<String, Object>> dataSource,
			Context mContext) {
		super();
		this.dataSource = dataSource;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return dataSource.size();
	}

	@Override
	public Object getItem(int position) {
		return dataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = View.inflate(mContext, R.layout.listitem_news, null);
		TextView NewItemTitle = (TextView) convertView
				.findViewById(R.id.NewItemTitle);
		TextView NewItemDesc = (TextView) convertView
				.findViewById(R.id.NewItemDesc);
		TextView NewItemContent = (TextView) convertView
				.findViewById(R.id.NewItemContent);
		ImageView NewItemImage = (ImageView) convertView
				.findViewById(R.id.NewItemImage);
		
		Map<String, Object> item = dataSource.get(position);

		NewItemTitle.setText(dealDetailString(item.get("ItemTitle").toString(), 12));
		NewItemDesc.setText(item.get("ItemDesc").toString());
		NewItemContent.setText(item.get("ItemContent").toString());
		
		if(item.get("ItemImage")!=null){
			NewItemImage.setImageResource((Integer) item.get("ItemImage"));
		}
		/*ViewHolder holder = null;

		if (convertView == null) {

			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.listitem_news, null);
			holder.NewItemTitle = (TextView) convertView
					.findViewById(R.id.NewItemTitle);
			holder.NewItemDesc = (TextView) convertView
					.findViewById(R.id.NewItemDesc);
			holder.NewItemContent = (TextView) convertView
					.findViewById(R.id.NewItemContent);
			holder.NewItemImage = (ImageView) convertView
					.findViewById(R.id.NewItemImage);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			Map<String, Object> item = dataSource.get(position);

			holder.NewItemTitle.setText(dealDetailString(item.get("ItemTitle").toString(), 12));
			holder.NewItemDesc.setText(item.get("ItemDesc").toString());
			holder.NewItemContent.setText(item.get("ItemContent").toString());
			
			if(item.get("ItemImage")!=null){
				holder.NewItemImage.setImageResource((Integer) item.get("ItemImage"));
			}else{
				Log.i("TAG", "ItemImage==null");
			}
		} catch (Exception err) {
			err.printStackTrace();
		}*/

		return convertView;
	}

	class ViewHolder {

		public TextView NewItemTitle;

		public TextView NewItemDesc;

		public TextView NewItemContent;

		public ImageView NewItemImage;
	}

	public String dealDetailString(String content,int len) {
		String title = "";
		if(content.length()>12){
			title = content.substring(0, len)+"...";
		}else{
			title = content;
		}
		return title;
	}
}
