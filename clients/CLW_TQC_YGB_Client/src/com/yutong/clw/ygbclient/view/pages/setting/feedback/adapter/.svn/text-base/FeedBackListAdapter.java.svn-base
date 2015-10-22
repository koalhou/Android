package com.yutong.clw.ygbclient.view.pages.setting.feedback.adapter;

import java.util.List;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.R.color;
import com.yutong.clw.ygbclient.view.bean.push.FeedBackPushBean;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FeedBackListAdapter extends BaseAdapter{
	
	private Context mContext;
	private List<FeedBackPushBean> mAdiviceList;
	
	
	public FeedBackListAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mAdiviceList.size();
	}

	@Override
	public Object getItem(int position) {
		return mAdiviceList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.activity_feedback_adapter_listitem,null);
			
			holder.suggestionTime_TV = (TextView) convertView.findViewById(R.id.suggestion_time);
			holder.suggestion_content_TV = (TextView) convertView.findViewById(R.id.suggestion_content);
			
			holder.suggestion_reply_time_TV = (TextView) convertView.findViewById(R.id.suggestion_reply_time);
			holder.suggestion_reply_content_TV = (TextView) convertView.findViewById(R.id.suggestion_reply_content);
			
			holder.division_Line = (View) convertView.findViewById(R.id.divisionLine);
			holder.reply_LL = (LinearLayout) convertView.findViewById(R.id.reply_LL);
			holder.suggestion_reply_content = (TextView) convertView.findViewById(R.id.suggestion_reply_content);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		/*设置数据*/
		FeedBackPushBean announcementBean = mAdiviceList.get(position);
		try {
			holder.suggestionTime_TV.setText(announcementBean.adviseTime);
			holder.suggestion_content_TV.setText(announcementBean.advise);
			
			if(announcementBean.replyFlag.equals(ActivityCommConstant.ADVICE_REPLY)){
				holder.division_Line.setVisibility(View.VISIBLE);
				holder.reply_LL.setVisibility(View.VISIBLE);
				holder.suggestion_reply_content.setVisibility(View.VISIBLE);
				
				holder.suggestion_reply_time_TV.setText(announcementBean.replyTime);
				holder.suggestion_reply_content_TV.setText(announcementBean.reply);
				
				if(announcementBean.readFlag.equals(ActivityCommConstant.ADVICE_UN_READ)){
					holder.suggestion_reply_content_TV.setTextColor(Color.BLUE);
				}else{
					holder.suggestion_reply_content_TV.setTextColor(Color.BLACK);
				}
				
			}else{
				holder.division_Line.setVisibility(View.GONE);
				holder.reply_LL.setVisibility(View.GONE);
				holder.suggestion_reply_content.setVisibility(View.GONE);
			}
			
		} catch (Exception err) {
			err.printStackTrace();
		}
		return convertView;
	}

	
	class ViewHolder {
		
		TextView suggestionTime_TV;
		TextView suggestion_content_TV;
		
		TextView suggestion_reply_time_TV;
		TextView suggestion_reply_content_TV;
		
		View division_Line;
		LinearLayout reply_LL;
		TextView suggestion_reply_content;
	}

	public void setmAdiviceList(List<FeedBackPushBean> mAdiviceList) {
		this.mAdiviceList = mAdiviceList;
	}
	
}
