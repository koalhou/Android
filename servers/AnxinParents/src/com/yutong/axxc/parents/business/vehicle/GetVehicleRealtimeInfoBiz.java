package com.yutong.axxc.parents.business.vehicle;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YTException;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.VehicleRealtimeInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.VehicleRealInfoReq;
import com.yutong.axxc.parents.connect.http.packet.VehicleRealInfoRes;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 获取车辆实时信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 * 
 */
public class GetVehicleRealtimeInfoBiz extends YtAsyncTask {

	/** Context对象 */
	private Context context;

	/** Handler对象 */
	private Handler handler;

	/** 车辆vins */
	private List<String> vehicle_vins;

	public GetVehicleRealtimeInfoBiz(Context context, Handler handler,
			List<String> vehicle_vins) {
		this.context = context;
		this.handler = handler;
		this.vehicle_vins = vehicle_vins;
		logTypeName = "[获取车辆实时信息逻辑类]:";

	}

	private static double mLat2 = 31.127423;
	private static double mLon2 = 120.67176;
	private static boolean teststatus = false;
	private static int degree = 0;

	/**
	 * 同步方式获取车辆实时信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<VehicleRealtimeInfoBean> getVehicleRealTimeInfo()
			throws Exception {

		// test
//		if (degree > 200)
//		{
//			degree=0;
//			throw new YTException(ThreadCommStateCode.REALTIMEINFO_NO_DATA);
//		}
//		VehicleRealtimeInfoBean b = new VehicleRealtimeInfoBean();
//		b.setDirection(degree + "");
//		b.setGps_lat(mLat2 + "");
//		b.setGps_lon(mLon2 + "");
//		b.setSpeed("100");
//		b.setStatus(teststatus ? VehicleRealtimeInfoBean.STATUS_ONLINE
//				: VehicleRealtimeInfoBean.STATUS_OFFLINE);
//		b.setVehicle_vin("123");
//		List<VehicleRealtimeInfoBean> vs = new ArrayList<VehicleRealtimeInfoBean>();
//
//		mLat2 += 0.0002;
//		degree += 10;
//		if (degree < 0)
//			degree = 0;
//		if (degree >= 360)
//			degree = 0;
//		teststatus = !teststatus;
//		vs.add(b);
//		return vs;
		
		
		 Logger.i(this.getClass(), logTypeName + "发送请求");
		 VehicleRealInfoReq req = buildReq();
		
		 HttpRes httpRes = HttpMsgSendUtil.sendPostMsg(req);
		 if (httpRes.isSuccess()) {
		 if (httpRes.getStatusCode() == 204) {
		 throw new YTException(ThreadCommStateCode.REALTIMEINFO_NO_DATA);
		
		 } else {
		 VehicleRealInfoRes res = new VehicleRealInfoRes();
		 res.parse(httpRes.getContent());
		 if (!res.isError()) {
		 Logger.i(this.getClass(), logTypeName + "成功");
		
		 return res.getVehicleRealInfoBeans();
		
		 } else {
		 Logger.i(this.getClass(), logTypeName + "失败");
		 throw new YTException(ThreadCommStateCode.COMMON_FAILED);
		 }
		 }
		 } else if (httpRes.isTokenExpire()) {
		 Logger.i(this.getClass(), logTypeName + "Token失效");
		
		 throw new YTException(ThreadCommStateCode.TOKEN_INVALID);
		 } else if (httpRes.isException()) {
		 Logger.w(this.getClass(), logTypeName + "失败：",
		 httpRes.getFailInfo());
		 throw new YTException(ThreadCommStateCode.NETWORK_ERROR);
		 } else {
		 Logger.w(this.getClass(), logTypeName + "失败：",
		 httpRes.getFailInfo());
		 throw new YTException(ThreadCommStateCode.COMMON_FAILED);
		 }
	}

	@Override
	protected void doInBackground() {
		handleProcess();
	}

	private void handleProcess() {

		Logger.i(this.getClass(), logTypeName + "发送请求");
		VehicleRealInfoReq req = buildReq();

		HttpRes httpRes = HttpMsgSendUtil.sendPostMsg(req);
		// 用来UI取消操作
		if (isCanceled()) {
			return;
		}

		if (httpRes.isSuccess()) {
			parseAndProcessRes(httpRes);
		} else if (httpRes.isTokenExpire()) {
			Logger.i(this.getClass(), logTypeName + "Token失效");
			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.TOKEN_INVALID);
		} else if (httpRes.isException()) {
			Logger.w(this.getClass(), logTypeName + "失败：",
					httpRes.getFailInfo());
			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.NETWORK_ERROR, httpRes.getFailInfo());
		} else {
			Logger.w(this.getClass(), logTypeName + "失败：",
					httpRes.getFailInfo());
			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.COMMON_FAILED, httpRes.getFailInfo());
		}
	}

	private void parseAndProcessRes(HttpRes httpRes) {
		VehicleRealInfoRes res = new VehicleRealInfoRes();
		res.parse(httpRes.getContent());
		if (!res.isError()) {
			Logger.i(this.getClass(), logTypeName + "成功");

			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.COMMON_SUCCESS,
					res.getVehicleRealInfoBeans());

		} else {
			Logger.i(this.getClass(), logTypeName + "失败");
			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil
							.getInstance().get(res.getErrorCode()));

		}
	}

	private VehicleRealInfoReq buildReq() {
		VehicleRealInfoReq req = new VehicleRealInfoReq();
		req.setAccessToken(YtApplication.getInstance().getAccessToken());
		req.setVehicle_vins(vehicle_vins);
		return req;
	}

}
