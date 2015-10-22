package com.yutong.clw.ygbclient.view.bizAccess.outOfFactory;

import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.linestation.VehicheDriverDataBiz;
import com.yutong.clw.ygbclient.common.beans.VehicleDriver;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

public class BizBusDriver extends BizBase {
	
	private List<String> vehicle_vins;

	private List<String> line_ids;

	public BizBusDriver(Context context) {
		super(context);
	}

	public BizBusDriver(Context context, int threadcount) {
		super(context, threadcount);
	}

	public BizBusDriver(Context context,List<String> vehicle_vins,List<String> line_ids) {
		super(context);
		this.vehicle_vins = vehicle_vins;
		this.line_ids = line_ids;
	}
	
	/* 获取站点信息 */
	public BizDataTypeEnum getVehicheDriverData(BizResultProcess<List<VehicleDriver>> process) {
		
		final VehicheDriverDataBiz vehicheDriverDataBiz = new VehicheDriverDataBiz(YtApplication.getInstance(), vehicle_vins, line_ids);
		
		Callable <List<VehicleDriver>> networkCallable = new Callable<List<VehicleDriver>>() {
			@Override
			public List<VehicleDriver> call() throws Exception {
				return vehicheDriverDataBiz.getDriverData();
			}
		};
		
		Callable <List<VehicleDriver>> localCallable = new Callable<List<VehicleDriver>>() {
			@Override
			public List<VehicleDriver> call() throws Exception {
				return vehicheDriverDataBiz.getDriverDataFromLocal();
			}
		};
		
		/*return BizCommonWork(false, null,networkCallable, process);*/
		
		return BizCommonWork(!vehicheDriverDataBiz.IsCacheExpires(), localCallable,networkCallable, process);
	}
}
