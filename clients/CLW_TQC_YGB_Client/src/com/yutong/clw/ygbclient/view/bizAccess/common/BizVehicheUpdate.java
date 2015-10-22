package com.yutong.clw.ygbclient.view.bizAccess.common;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.business.linestation.VehicheRealTimeDataBiz;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

public class BizVehicheUpdate extends BizBase {

	public BizVehicheUpdate(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BizVehicheUpdate(Context context, int threadcount) {
		super(context, threadcount);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取实时车辆信息
	 * 
	 * @author zhouzc 2013-10-30 上午9:06:32
	 * 
	 * @param station_ids
	 * @param process
	 */
	public void getRealTimeVehicheData(final List<String> station_ids,final List<String> line_ids,
			BizResultProcess<List<VehicleRealtime>> process) {
		final VehicheRealTimeDataBiz biz = new VehicheRealTimeDataBiz(mContext,
				station_ids,line_ids);

		Callable<List<VehicleRealtime>> networkcall = new Callable<List<VehicleRealtime>>() {
			@Override
			public List<VehicleRealtime> call() throws Exception {
				// if (station_ids.get(0).equals("107")) {
				// VehicleRealtime v = new VehicleRealtime();
				// v.direction = 45;
				// v.gps_lat = 34.685515d;
				// v.gps_lon = 113.6868667d;
				// v.vehiche_number = "107";
				// return Arrays.asList(v);
				// } else if (station_ids.get(0).equals("108")) {
				// VehicleRealtime v = new VehicleRealtime();
				// v.direction = 90;
				// v.gps_lat = 34.69208d;
				// v.gps_lon = 113.6868767d;
				// v.vehiche_number = "108";
				// return Arrays.asList(v);
				// } else if (station_ids.get(0).equals("109")) {
				// VehicleRealtime v = new VehicleRealtime();
				// v.direction = 180;
				// v.gps_lat = 34.691365d;
				// v.gps_lon = 113.6867d;
				// v.vehiche_number = "109";
				// return Arrays.asList(v);
				// }
				return biz.getRealTimeData();
			}
		};
		BizNetWork(networkcall, process);

	}

}
