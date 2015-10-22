package com.yutong.clw.ygbclient.view.widget.querylist;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.utils.ClipUtil;

public class QueryResultListAdapter extends BaseAdapter {

	private List<QueryResultItem> mData;
	private Context mContext;

	public QueryResultListAdapter(Context context, List<QueryResultItem> data) {
		if (data == null)
			throw new IllegalArgumentException("参数不能为NULL");
		mData = data;
		mContext = context;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext,
					R.layout.control_queryresultlist_item, null);
			holder = new ViewHolder();
			holder.Background = convertView
					.findViewById(R.id.ll_cqi_background);
			holder.Title = (TextView) convertView
					.findViewById(R.id.tv_cqi_subtitle);
			holder.Content = (TextView) convertView
					.findViewById(R.id.tv_cqi_content);
			holder.Action1 = (Button) convertView
					.findViewById(R.id.btn_cqi_action1);
			holder.Action2 = (Button) convertView
					.findViewById(R.id.btn_cqi_action2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		QueryResultItem item = mData.get(position);

		if (item.getBackground() != null)
			holder.Background.setBackgroundDrawable(item.getBackground());
		holder.Title.setText(item.getTitle());
		holder.Content.setText(item.getContent());
		holder.Action1.setTag(position);
		holder.Action1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String data = mData
						.get(Integer.parseInt(v.getTag().toString()))
						.getContent();
				ClipUtil.saveToClip(mContext, data);
			}
		});
		holder.Action2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		return convertView;

	}

	class ViewHolder {
		View Background;
		TextView Title;
		TextView Content;
		Button Action1;
		Button Action2;
	}
}
