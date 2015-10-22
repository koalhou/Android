package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.view.bean.GridInfo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MCalendarGridAdapter extends BaseAdapter {
	List<GridInfo> mData;
	Context mContext;
	List<Integer> selecteditems;
	private GridView gv;
	private View lastSelectedItem = null;
	
	public void setSelectedItem(int index) {
		selecteditems.clear();
		selecteditems.add(index);
		notifyDataSetChanged();
	}

	public void ClearSelection() {
		selecteditems.clear();
		notifyDataSetChanged();
	}

	public MCalendarGridAdapter(Context context, List<GridInfo> data,
			GridView gv) {
		selecteditems = new ArrayList<Integer>();
		// selecteditems.add(0);
		mContext = context;
		mData = data;
		this.gv = gv;
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
	public long getItemId(int arg0) {
		return 0;
	}

	private boolean isSelectedItem(int index) {
		if (selecteditems.contains(index))
			return true;
		return false;
	}

	public void changeSelection(View newv) {

		if (newv == null)
			return;

		if (lastSelectedItem != null && lastSelectedItem.equals(newv))
			return;

		if (lastSelectedItem != null) {
			ViewHolder oldvholder = (ViewHolder) lastSelectedItem.getTag();
			int oldselection = (Integer) lastSelectedItem
					.getTag(R.layout.component_calendar_item);

			// 间隔色
			if (oldselection % 2 == 0)
				oldvholder.BackGround
						.setBackgroundResource(R.drawable.griditem1);
			else
				oldvholder.BackGround
						.setBackgroundResource(R.drawable.griditem0);
		}

		ViewHolder newvholder = (ViewHolder) newv.getTag();
		newvholder.BackGround.setBackgroundColor(mContext.getResources()
				.getColor(R.color.maincolor));
		int newindex = (Integer) newv.getTag(R.layout.component_calendar_item);
		
		Log.d("", "changeSelection to:" + newindex);
		
		selecteditems.clear();
		selecteditems.add(newindex);

		lastSelectedItem = newv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			holder = new ViewHolder();
			LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			arg1 = View.inflate(mContext,R.layout.component_calendar_item, null);
			convertView = inflater.inflate(R.layout.component_calendar_item, null, false);
			holder.BackGround = (LinearLayout) convertView
					.findViewById(R.id.ll_bg);
			holder.Flag = (RelativeLayout) convertView.findViewById(R.id.ll_im);
			holder.Content = (TextView) convertView.findViewById(R.id.tv_content);
			holder.today_txt = (TextView) convertView.findViewById(R.id.today_txt);
			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.today_txt.setVisibility(View.GONE);
		
		convertView.setTag(R.layout.component_calendar_item, position);
		// 获取信息
		GridInfo info = mData.get(position);
		if (!info.isCanbeselect()) {
			
			// 这段一加, gridview的onitemclicklistener就没有效果了
			/*convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});*/
		}
		
		/*此处过滤不显示的日期*/
		String ctext = "";
		/*int tmp = (Integer) gv.getTag();
		if (info.getDate().get(Calendar.MONTH) == tmp) {
			ctext = info.getDate().get(Calendar.DAY_OF_MONTH) + "";
		}*/
		ctext = info.getDate().get(Calendar.DAY_OF_MONTH) + "";
		// 设置显示
		holder.Content.setText(ctext);
		
		final String tmp2 = ctext;
		
		/*是否能够点击选择*/
		if (info.isCanbeselect()) {
			
			info.getDate().get(Calendar.MONTH);
			holder.Content.setTextColor(mContext.getResources().getColor(R.color.fontcolor_weekday));
		} else {
			holder.Content.setTextColor(mContext.getResources().getColor(R.color.fontcolor_weekend));
		}
		
		// 设置背景色
		if (info.isMarked()){
			holder.Flag.setBackgroundResource(R.drawable.bg_calender);
			holder.Content.setTextColor(mContext.getResources().getColor(R.color.white));			
			/*holder.today_txt.setVisibility(View.VISIBLE);*/
			lastSelectedItem = convertView;
		}else
			holder.Flag.setBackgroundDrawable(null);
		
		if(info.isToday() && info.isMarked()){
			holder.today_txt.setVisibility(View.VISIBLE);
			holder.today_txt.setTextColor(mContext.getResources().getColor(R.color.white));
			holder.Content.setTextColor(mContext.getResources().getColor(R.color.white));
		}else if(info.isToday()){
			holder.today_txt.setVisibility(View.VISIBLE);
			holder.today_txt.setTextColor(mContext.getResources().getColor(R.color.black));
		}
		
		// 间隔色
		if (position % 2 == 0)
			holder.BackGround.setBackgroundResource(R.drawable.griditem1);
		else
			holder.BackGround.setBackgroundResource(R.drawable.griditem0);

		// 选中色
		if (isSelectedItem(position)) {
			holder.BackGround.setBackgroundColor(mContext.getResources().getColor(
					R.color.maincolor));
			lastSelectedItem = convertView;
		}

		return convertView;
	}

	class ViewHolder {
		public LinearLayout BackGround;

		public RelativeLayout Flag;

		public TextView Content, today_txt;
	}
}
