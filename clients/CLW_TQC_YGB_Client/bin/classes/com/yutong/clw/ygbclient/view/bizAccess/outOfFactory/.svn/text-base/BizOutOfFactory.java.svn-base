package com.yutong.clw.ygbclient.view.bizAccess.outOfFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
import android.test.IsolatedContext;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.linestation.GetIdsLinesBiz;
import com.yutong.clw.ygbclient.business.linestation.GetRemindStationsBiz;
import com.yutong.clw.ygbclient.business.linestation.GetSingleRemindStationsBiz;
import com.yutong.clw.ygbclient.business.linestation.GetStationBiz;
import com.yutong.clw.ygbclient.business.linestation.SetRemindStationsBiz;
import com.yutong.clw.ygbclient.business.linestation.SetStationFavoritesBiz;
import com.yutong.clw.ygbclient.business.linestation.StationAreasBiz;
import com.yutong.clw.ygbclient.business.linestation.StationRealTimeDataBiz;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.CollectionStation;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.beans.StationAreaInfo;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.beans.line.StationVehicleRealTimeInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

public class BizOutOfFactory extends BizBase {

	public BizOutOfFactory(Context context) {
		super(context);
	}

	public BizOutOfFactory(Context context, int threadcount) {
		super(context, threadcount);
	}

	/* 获取站点信息 */
	public BizDataTypeEnum getStations(
			BizResultProcess<List<StationInfo>> process) {
		final GetStationBiz getStationBiz = new GetStationBiz(
				YtApplication.getInstance());

		Callable<List<StationInfo>> LocalCallable = new Callable<List<StationInfo>>() {

			@Override
			public List<StationInfo> call() throws Exception {
				return getStationBiz.getStationInfosFromLocal();
			}
		};

		Callable<List<StationInfo>> networkCallable = new Callable<List<StationInfo>>() {

			@Override
			public List<StationInfo> call() throws Exception {

				return getStationBiz.getStationInfosFromSever();
			}

		};
		return BizCommonWork(!getStationBiz.IsCacheExpires(), LocalCallable,
				networkCallable, process);
	}

	public BizDataTypeEnum getStation(final String stationid,
			BizResultProcess<StationInfo> process) {
		final GetStationBiz getStationBiz = new GetStationBiz(
				YtApplication.getInstance());

		Callable<StationInfo> LocalCallable = new Callable<StationInfo>() {

			@Override
			public StationInfo call() throws Exception {
				List<StationInfo> infos = getStationBiz
						.getStationInfosFromLocal();
				if (infos == null)
					return null;

				for (StationInfo info : infos) {
					if (info.getId().equals(stationid)) {
						return info;
					}
				}
				return null;
			}
		};

		Callable<StationInfo> networkCallable = new Callable<StationInfo>() {

			@Override
			public StationInfo call() throws Exception {
				List<StationInfo> infos = getStationBiz
						.getStationInfosFromSever();
				if (infos == null)
					return null;
				for (StationInfo info : infos) {
					if (info.getId().equals(stationid)) {
						return info;
					}
				}
				return null;
			}

		};
		return BizCommonWork(!getStationBiz.IsCacheExpires(), LocalCallable,
				networkCallable, process);
	}

	/** 站点班车界面使用的接口 **/

	public BizDataTypeEnum getLines(List<String> line_ids,
			BizResultProcess<List<LineInfo>> process) {
		final GetIdsLinesBiz biz = new GetIdsLinesBiz(mContext, line_ids);
		Callable<List<LineInfo>> cachecall = new Callable<List<LineInfo>>() {
			@Override
			public List<LineInfo> call() throws Exception {
				Logger.i(getClass(), "从缓存获取线路数据");
				return biz.getLinesFromLocal();
			}
		};
		Callable<List<LineInfo>> netcall = new Callable<List<LineInfo>>() {
			@Override
			public List<LineInfo> call() throws Exception {
				Logger.i(getClass(), "从网络获取线路数据");
				return biz.getLines();
			}
		};
		return BizCommonWork(!biz.IsCacheExpires(), cachecall, netcall, process);
	}

	public void getVehicle(String vehicleid, String stationid,
			BizResultProcess<List<StationVehicleRealTimeInfo>> process) {

	}

	public void getVehiches(String stationid, AreaType area_type,
			StatusRange dayornight,
			BizResultProcess<List<StationVehicleRealTimeInfo>> process) {
		Date dt = DateUtils.getCurDate();
		final StationRealTimeDataBiz biz = new StationRealTimeDataBiz(
				getmContext(), dt, Arrays.asList(stationid), area_type,
				dayornight);

		Callable<List<StationVehicleRealTimeInfo>> networkCallable = new Callable<List<StationVehicleRealTimeInfo>>() {
			@Override
			public List<StationVehicleRealTimeInfo> call() throws Exception {
				return biz.getRealTimeData();
			}
		};
		BizNetWork(networkCallable, process);
	}

	public void setStationFavority(String stationid, boolean isfavority,
			BizResultProcess<Boolean> process) {
		List<CollectionStation> stations = new ArrayList<CollectionStation>();
		CollectionStation station = new CollectionStation();
		station.setStation_id(stationid);
		station.setFavorites(isfavority);
		stations.add(station);
		final SetStationFavoritesBiz biz = new SetStationFavoritesBiz(mContext,
				stations);
		Callable<Boolean> networkcall = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return biz.setStatinFavorites();
			}
		};
		BizNetWork(networkcall, process);

	}

	/************************************ 站点搜索 ***************************************/
	/**
	 * 根据厂区类型和早晚班搜索站点（厂外通勤）
	 * 
	 * @author lizyi 2013-11-18 上午10:06:45
	 * @param areaType
	 *            厂区
	 * @param statusRange
	 *            早晚班
	 * @param bizResultProcess
	 * @return
	 */
	public BizDataTypeEnum getSearchStations(final AreaType areaType,
			final StatusRange statusRange,
			BizResultProcess<List<StationInfo>> bizResultProcess) {
		final GetStationBiz getStationBiz = new GetStationBiz(
				YtApplication.getInstance());

		Callable<List<StationInfo>> LocalCallable = new Callable<List<StationInfo>>() {

			@Override
			public List<StationInfo> call() throws Exception {
				List<StationInfo> stationInfosFromLocal = getStationBiz
						.getStationInfosFromLocal();
				return Filter(stationInfosFromLocal, areaType, statusRange);
			}
		};

		Callable<List<StationInfo>> networkCallable = new Callable<List<StationInfo>>() {

			@Override
			public List<StationInfo> call() throws Exception {
				List<StationInfo> stationInfosFromSever = getStationBiz
						.getStationInfosFromSever();
				return Filter(stationInfosFromSever, areaType, statusRange);
			}

		};

		return BizCommonWork(!getStationBiz.IsCacheExpires(), LocalCallable,
				networkCallable, bizResultProcess);
	}

	/**
	 * 获取片区对应的站点信息（厂外通勤）
	 * 
	 * @author lizyi 2013-11-18 上午10:39:02
	 * @param areaType
	 *            厂区
	 * @param statusRange
	 *            早晚班
	 * @param belong_area_id
	 *            区域id
	 * @param bizResultProcess
	 * @return
	 */
	public BizDataTypeEnum getAreaStations(final AreaType areaType,
			final StatusRange statusRange, final String belong_area_id,
			BizResultProcess<List<StationInfo>> bizResultProcess) {

		final GetStationBiz getStationBiz = new GetStationBiz(
				YtApplication.getInstance());

		Callable<List<StationInfo>> LocalCallable = new Callable<List<StationInfo>>() {

			@Override
			public List<StationInfo> call() throws Exception {
				List<StationInfo> stationInfosFromLocal = getStationBiz
						.getStationInfosFromLocal();
				return Filter(stationInfosFromLocal, areaType, statusRange,
						belong_area_id);
			}
		};

		Callable<List<StationInfo>> networkCallable = new Callable<List<StationInfo>>() {

			@Override
			public List<StationInfo> call() throws Exception {
				List<StationInfo> stationInfosFromSever = getStationBiz
						.getStationInfosFromSever();
				return Filter(stationInfosFromSever, areaType, statusRange,
						belong_area_id);
			}

		};

		return BizCommonWork(!getStationBiz.IsCacheExpires(), LocalCallable,
				networkCallable, bizResultProcess);
	}

	/**
	 * 获取站点区域（厂外通勤）
	 * 
	 * @author lizyi 2013-11-18 上午10:30:09
	 * @param process
	 * @return
	 */
	public BizDataTypeEnum getStationAreas(
			BizResultProcess<List<StationAreaInfo>> process) {

		final StationAreasBiz stationAreasBiz = new StationAreasBiz(
				YtApplication.getInstance());

		Callable<List<StationAreaInfo>> LocalCallable = new Callable<List<StationAreaInfo>>() {
			@Override
			public List<StationAreaInfo> call() throws Exception {
				return stationAreasBiz.getStationAreasFromLocal();
			}
		};

		Callable<List<StationAreaInfo>> networkCallable = new Callable<List<StationAreaInfo>>() {
			@Override
			public List<StationAreaInfo> call() throws Exception {
				return stationAreasBiz.getStationAreasFromSever();
			}
		};

		return BizCommonWork(!stationAreasBiz.IsCacheExpires(), LocalCallable,
				networkCallable, process);
	}

	/***************************************************************************/
	public void setStatinFavorites(List<CollectionStation> stations,
			BizResultProcess<Boolean> process) {

		final SetStationFavoritesBiz setStationFavoritesBiz = new SetStationFavoritesBiz(
				mContext, stations);

		/*
		 * try { setStationFavoritesBiz.setStatinFavorites(); } catch
		 * (CommonException e) { e.printStackTrace(); Logger.i(getClass(),
		 * "收藏站点异常："+e.toString()); }
		 */

		Callable<Boolean> LocalCallable = new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				return setStationFavoritesBiz.setStatinFavorites();
			}
		};
		BizNetWork(LocalCallable, process);
	}

	public void getStationRemind(final String stationid, AreaType areatype,
			StatusRange status_range, BizResultProcess<RemindInfo> process) {

		final GetSingleRemindStationsBiz biz = new GetSingleRemindStationsBiz(
				getmContext(), areatype, stationid, status_range, null);

		Callable<RemindInfo> networkcall = new Callable<RemindInfo>() {
			@Override
			public RemindInfo call() throws Exception {

				return biz.getRemind();
			}
		};
		BizNetWork(networkcall, process);
	}

	/*********************************************************************************/
	/**
	 * 检查筛选条件
	 * 
	 * @author lizyi 2013-11-26 上午9:11:01
	 * @param areaType
	 * @param range
	 * @param info
	 * @return
	 */
	private boolean checkCondition(AreaType areaType, StatusRange range,
			StationInfo info) {
		if (areaType == null || range == null || info == null) {
			return false;
		}
		return (areaType.equals(info.area_type) || AreaType.AllFactory
				.equals(info.area_type))
				&& (info.status_range == range || StatusRange.AllWork
						.equals(info.status_range));
	}

	/**
	 * 判断筛选条件
	 * 
	 * @author lizyi 2013-11-26 上午9:15:24
	 * @param areaType
	 * @param range
	 * @param belong_area_id
	 * @param info
	 * @return
	 */
	private boolean checkCondation(AreaType areaType, StatusRange range,
			String belong_area_id, StationInfo info) {
		if (areaType == null || range == null
				|| StringUtil.isBlank(belong_area_id) || info == null) {
			return false;
		}
		return (areaType.equals(info.area_type) || AreaType.AllFactory
				.equals(info.area_type))
				&& (info.status_range == range || StatusRange.AllWork
						.equals(info.status_range))
				&& belong_area_id.equals(info.belong_area_id);
	}

	/**
	 * 根据条件筛选站点（厂外通勤）
	 * 
	 * @author lizyi 2013-11-18 上午10:24:16
	 * @param infos
	 * @param range
	 * @return
	 */
	protected List<StationInfo> Filter(List<StationInfo> infos,
			AreaType areaType, StatusRange range) {
		if (infos == null || infos.size() == 0 || range == null
				|| areaType == null)
			return null;
		List<StationInfo> list = new ArrayList<StationInfo>();

		for (int i = 0; i < infos.size(); i++) {
			StationInfo info = infos.get(i);
			if (checkCondition(areaType, range, info))
				list.add(info);
		}
		return list;
	}

	/**
	 * 根据条件筛选站点（厂外通勤）
	 * 
	 * @author lizyi 2013-11-18 上午10:24:16
	 * @param infos
	 * @param range
	 * @param belong_area_id
	 * @return
	 */
	protected List<StationInfo> Filter(List<StationInfo> infos,
			AreaType areaType, StatusRange range, String belong_area_id) {
		if (infos == null || infos.size() == 0 || range == null
				|| areaType == null || StringUtil.isBlank(belong_area_id))
			return null;
		List<StationInfo> list = new ArrayList<StationInfo>();

		for (int i = 0; i < infos.size(); i++) {
			StationInfo info = infos.get(i);
			if (checkCondation(areaType, range, belong_area_id, info))
				list.add(info);
		}
		return list;
	}
}
