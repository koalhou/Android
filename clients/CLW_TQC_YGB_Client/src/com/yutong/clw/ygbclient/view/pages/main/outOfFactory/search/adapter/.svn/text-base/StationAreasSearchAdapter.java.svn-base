package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.beans.StationAreaInfo;

public class StationAreasSearchAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;
	private List<StationAreaInfo> stationAreaInfoList;
	private GridView stationAreasGridView;

	public StationAreasSearchAdapter(Context context,
			List<StationAreaInfo> stationAreaInfoList,
			GridView stationAreasGridView) {
		super();
		this.context = context;
		this.stationAreaInfoList = stationAreaInfoList;
		this.mInflater = LayoutInflater.from(context);
		this.stationAreasGridView = stationAreasGridView;
	}

	@Override
	public int getCount() {

		return stationAreaInfoList.size();
	}

	@Override
	public Object getItem(int position) {

		return stationAreaInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {

			view = mInflater
					.inflate(
							R.layout.activity_main_outoffactory_search_stationsearch_adapter,
							null);

		} else {
			view = convertView;
		}
		Button stationBtn = (Button) view.findViewById(R.id.stationBtn);
		final StationAreaInfo stationAreaInfo = stationAreaInfoList
				.get(position);
		stationBtn.setText(stationAreaInfo.getName());
		return view;
	}

}
