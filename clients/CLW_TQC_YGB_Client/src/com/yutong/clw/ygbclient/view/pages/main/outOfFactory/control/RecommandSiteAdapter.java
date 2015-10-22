package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.control;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;

/**
 * 推荐站点列表适配器
 * 
 * @author zhangyongn 2013-11-5 上午9:57:42
 */
public class RecommandSiteAdapter extends BaseAdapter {

	private List<StationInfo> sites = new ArrayList<StationInfo>();

	private Context context;

	private LayoutInflater inflater;

	private View.OnClickListener favoriteClickListener;

	private View.OnClickListener siteClickListener;

	
    
	public RecommandSiteAdapter(Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public RecommandSiteAdapter(Context context, List<StationInfo> stations) {
		super();
		this.context = context;
		this.sites = stations;

	}

	public void SetDatas(List<StationInfo> stations) {
		this.sites = stations;
	}

	@Override
	public int getCount() {
		if (sites == null)
			return 0;
		return sites.size();
	}

	@Override
	public Object getItem(int position) {
		return sites.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		StationInfo site = sites.get(position);
		// System.out.println("position===========" + position);
		try {
			ViewHolder holder;
			if (convertView == null
					|| (holder = (ViewHolder) convertView.getTag()).flag != position) {

				holder = new ViewHolder();
				holder.flag = position;

				convertView = LayoutInflater.from(context).inflate(R.layout.listitem_recommandsite, null);
				buildHolder(holder, convertView);
				updateValue(holder, site);
				updateUI(holder, position);

				setListener(holder, site);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
				updateValue(holder, site);
				updateUI(holder, position);
				setListener(holder, site);
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
		return convertView;
	}

	private void updateUI(ViewHolder holder, int position) {
		// 最后一条记录
		if (position >= (sites.size() - 1)) {
			holder.itemLL.setBackgroundResource(R.drawable.bg_block);
			holder.baselineIV.setVisibility(View.GONE);

		} else {
			holder.itemLL.setBackgroundDrawable(null);

			holder.baselineIV.setVisibility(View.VISIBLE);

		}

		if (sites.size() > 1) {
			if (position >= (sites.size() - 1)) {
				holder.leftRL
						.setBackgroundResource(R.drawable.bg_recommandsite_item_favorite_bottom);
				holder.rightRL
						.setBackgroundResource(R.drawable.bg_recommandsite_item_site_bottom);
			} else if (position == 0) {
				holder.leftRL
						.setBackgroundResource(R.drawable.bg_recommandsite_item_favorite_top);
				holder.rightRL
						.setBackgroundResource(R.drawable.bg_recommandsite_item_site_top);
			} else {
				holder.leftRL
						.setBackgroundResource(R.drawable.bg_recommandsite_item_favorite);
				holder.rightRL
						.setBackgroundResource(R.drawable.bg_recommandsite_item_site);
			}
		} else {
			holder.leftRL
					.setBackgroundResource(R.drawable.bg_recommandsite_item_favorite_all);
			holder.rightRL
					.setBackgroundResource(R.drawable.bg_recommandsite_item_site_all);
		}

	}

	private void buildHolder(ViewHolder holder, View convertView) {
		try {
			holder.siteTV = (TextView) convertView.findViewById(R.id.siteTV);
			holder.leftRL = (RelativeLayout) convertView
					.findViewById(R.id.leftRL);
			holder.rightRL = (RelativeLayout) convertView
					.findViewById(R.id.rightRL);
			holder.itemLL = (LinearLayout) convertView
					.findViewById(R.id.itemLL);
			holder.baselineIV = (ImageView) convertView
					.findViewById(R.id.baselineIV);
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}

	private void updateValue(ViewHolder holder, StationInfo site) {
		try {
			holder.siteTV.setText(site.getName());
			holder.leftRL.setTag(site);
			holder.rightRL.setTag(site);
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}

	private void setListener(ViewHolder holder, StationInfo m) {

		holder.leftRL.setOnClickListener(null);
		holder.leftRL.setOnClickListener(this.favoriteClickListener);

		holder.rightRL.setOnClickListener(null);
		holder.rightRL.setOnClickListener(this.siteClickListener);

	}

	/**
	 * @param favoriteClickListener
	 *            要设置的 favoriteClickListener
	 */
	public void setFavoriteClickListener(
			View.OnClickListener favoriteClickListener) {
		this.favoriteClickListener = favoriteClickListener;
	}

	/**
	 * @param siteClickListener
	 *            要设置的 siteClickListener
	 */
	public void setSiteClickListener(View.OnClickListener siteClickListener) {
		this.siteClickListener = siteClickListener;
	}

	static class ViewHolder {

		int flag = -1;

		TextView siteTV;

		LinearLayout itemLL;

		RelativeLayout leftRL;

		RelativeLayout rightRL;

		ImageView baselineIV;

	}
}
