package com.yutong.clw.ygbclient.view.bizAccess.inFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.linestation.GetAreaLinesBiz;
import com.yutong.clw.ygbclient.business.linestation.GetStationBiz;
import com.yutong.clw.ygbclient.business.linestation.GetVehichesBiz;
import com.yutong.clw.ygbclient.business.linestation.SetRemindStationsBiz;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.VehicheInfo;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.other.FilterEnum;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.utils.BusinessUtils;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

public class BizInFactory extends BizBase {

	public BizInFactory(Context context) {
		super(context);
	}

	public BizInFactory(Context context, int threadcount) {
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
	public BizDataTypeEnum getInFactoryLines(
			BizResultProcess<List<LineInfo>> process) {
		final GetAreaLinesBiz biz = new GetAreaLinesBiz(
				YtApplication.getInstance(),
				BusinessUtils.convertToList(FilterEnum.Value11002100));

		Callable<List<LineInfo>> cacheCallable = new Callable<List<LineInfo>>() {
			@Override
			public List<LineInfo> call() throws Exception {
				// List<LineInfo> lines = new ArrayList<LineInfo>();
				// LineInfo lineinfo = new LineInfo();
				// lineinfo.setLine_name("一厂厂内环线");
				// lineinfo.setArea_type(AreaType.FirstFactory);
				// lineinfo.setLine_id("1");
				// lineinfo.setLine_range(LineRange.FactoryInside);
				// lineinfo.setStatus_range(StatusRange.MorningWork);
				// lineinfo.setPoints(gettestpointsfromCache());
				// lineinfo.setStations(getteststations());
				// lines.add(lineinfo);
				// return lines;
				Logger.i(getClass(), "getInFactoryLines 从缓存获取线路数据");
				return biz.getLinesFromLocal();
			}
		};

		Callable<List<LineInfo>> networkCallable = new Callable<List<LineInfo>>() {
			@Override
			public List<LineInfo> call() throws Exception {
				// TODO 这里查询网络
				// Thread.sleep(2000);
				Logger.i(getClass(), "getInFactoryLines 从网络获取线路数据");
				List<LineInfo> rlines = biz.getLinesFromSever();
				return rlines;
				//
				// List<LineInfo> lines = new ArrayList<LineInfo>();
				// LineInfo lineinfo = new LineInfo();
				// lineinfo.setLine_name("一厂厂内环线");
				// lineinfo.setArea_type(AreaType.FirstFactory);
				// lineinfo.setLine_id("1");
				// lineinfo.setLine_range(LineRange.FactoryInside);
				// lineinfo.setStatus_range(StatusRange.MorningWork);
				// lineinfo.setPoints(gettestpointsFromRemote());
				// lineinfo.setStations(getteststations());
				// lines.add(lineinfo);
				//
				// return lines;
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
	public BizDataTypeEnum getVehiches(String lineid, Date time,
			BizResultProcess<List<VehicheInfo>> process) {
		final GetVehichesBiz biz = new GetVehichesBiz(
				YtApplication.getInstance(), time, Arrays.asList(lineid));

		Callable<List<VehicheInfo>> cacheCallable = new Callable<List<VehicheInfo>>() {
			@Override
			public List<VehicheInfo> call() throws Exception {
				Logger.i(getClass(), "getVehiches 从缓存获取车辆数据");
				return biz.getVehichesFromLocal();
			}
		};

		Callable<List<VehicheInfo>> networkCallable = new Callable<List<VehicheInfo>>() {
			@Override
			public List<VehicheInfo> call() throws Exception {
				Logger.i(getClass(), "getVehiches 从网络获取车辆数据");
				return biz.getVehichesFromSever();
			}
		};
		
		/*isVehichesFromCache(biz)*/
		return BizCommonWork(false, cacheCallable,
				networkCallable, process);
	}

	/**
	 * 设置站点提醒
	 * 
	 * @param stationid
	 * @param remind
	 * @param process
	 */
	public void SubmitRemind(String stationid, String lineid,
			AreaType areatype, boolean remind, BizResultProcess<Boolean> process) {
		RemindInfo info = new RemindInfo();
		info.setLine_id(lineid);
		info.setArea_type(areatype);
		info.setStation_id(stationid);
		info.setLine_range(LineRange.FactoryInside);
		info.setRemind_status(remind ? RemindStatus.Open : RemindStatus.Close);
		final SetRemindStationsBiz biz = new SetRemindStationsBiz(mContext,
				Arrays.asList(info));
		Callable<Boolean> submitcallable = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return biz.setRemindInfo();
			}
		};
		BizNetWork(submitcallable, process);

	}

	private boolean isStationInfosFromCache(GetStationBiz biz) {
		return !biz.IsCacheExpires();
	}
	public BizDataTypeEnum getSingleLine(final String lineid,
			BizResultProcess<LineInfo> process) {
		final GetAreaLinesBiz biz = new GetAreaLinesBiz(
				YtApplication.getInstance(),
				BusinessUtils.convertToList(FilterEnum.Value11002100));
		Callable<LineInfo> cacheCallable = new Callable<LineInfo>() {
			@Override
			public LineInfo call() throws Exception {
				Logger.i(getClass(), "getSingleLine 从缓存获取单条["+lineid+"]线路数据");

				List<LineInfo> lineinfos = biz.getLinesFromLocal();
				if (lineinfos == null || lineinfos.size() == 0)
					return null;

				for (LineInfo info : lineinfos) {
					if(info.line_id.equals(lineid)){
						return info;
					}
				}
				return null;
			}
		};

		Callable<LineInfo> networkCallable = new Callable<LineInfo>() {
			@Override
			public LineInfo call() throws Exception {
				Logger.i(getClass(), "getSingleLine 从网络获取单条["+lineid+"]线路数据");
		
				List<LineInfo> lineinfos = biz.getLinesFromSever();
				if (lineinfos == null || lineinfos.size() == 0)
					return null;

				for (LineInfo info : lineinfos) {
					if(info.line_id.equals(lineid)){
						return info;
					}
				}
				return null;
				
			}
		};

		return BizCommonWork(IsFactoryLinesFromCache(biz), cacheCallable,
				networkCallable, process);

	}
	
	
}
