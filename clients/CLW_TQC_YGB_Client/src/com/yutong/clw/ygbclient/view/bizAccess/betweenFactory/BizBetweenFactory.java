package com.yutong.clw.ygbclient.view.bizAccess.betweenFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.linestation.GetAreaLinesBiz;
import com.yutong.clw.ygbclient.business.linestation.GetVehichesBiz;
import com.yutong.clw.ygbclient.common.beans.VehicheInfo;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.enums.other.FilterEnum;
import com.yutong.clw.ygbclient.common.utils.BusinessUtils;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

public class BizBetweenFactory extends BizBase {

	public BizBetweenFactory(Context context) {
		super(context);
	}

	public BizBetweenFactory(Context context, int threadcount) {
		super(context, threadcount);
	}

	/**
	 * 厂区线路数据是否从缓存获取
	 * 
	 * @param biz
	 * @return
	 */
	private boolean IsFactoryLinesFromCache(GetAreaLinesBiz biz) {
		return !biz.IsCacheExpires();

	}

	/**
	 * 获取线路数据
	 * 
	 * @param process
	 * @return
	 */
	public BizDataTypeEnum getBetweenFactoryLines(
			BizResultProcess<List<LineInfo>> process) {
		
		final GetAreaLinesBiz biz = new GetAreaLinesBiz(
				YtApplication.getInstance(),
				BusinessUtils.convertToList(FilterEnum.Value10012001));

		Callable<List<LineInfo>> cacheCallable = new Callable<List<LineInfo>>() {
			@Override
			public List<LineInfo> call() throws Exception {
				return biz.getLinesFromLocal();
			}
		};

		Callable<List<LineInfo>> networkCallable = new Callable<List<LineInfo>>() {
			@Override
			public List<LineInfo> call() throws Exception {
				return biz.getLinesFromSever();
			}
		};

		return BizCommonWork(IsFactoryLinesFromCache(biz), cacheCallable,
				networkCallable, process);
	}

	private boolean isVehichesFromCache(GetVehichesBiz biz) {
		// return false;
		return !biz.IsCacheExpires();
	}

	/**
	 * 获取车辆信息
	 * 
	 * @param time
	 * @param area_type
	 * @param line_range
	 * @param process
	 * @return
	 */
	public BizDataTypeEnum getVehiches(String lineid,Date time,  BizResultProcess<List<VehicheInfo>> process) {
		final GetVehichesBiz biz = new GetVehichesBiz(
				YtApplication.getInstance(), time,Arrays.asList(lineid));

		Callable<List<VehicheInfo>> cacheCallable = new Callable<List<VehicheInfo>>() {
			@Override
			public List<VehicheInfo> call() throws Exception {
				return biz.getVehichesFromLocal();
			}
		};

		Callable<List<VehicheInfo>> networkCallable = new Callable<List<VehicheInfo>>() {
			@Override
			public List<VehicheInfo> call() throws Exception {
				return biz.getVehichesFromSever();
			}
		};
		/*isVehichesFromCache(biz)*/
		return BizCommonWork(false, cacheCallable,
				networkCallable, process);
	}

}
