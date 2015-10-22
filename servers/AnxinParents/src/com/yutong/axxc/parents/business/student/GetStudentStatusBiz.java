package com.yutong.axxc.parents.business.student;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YTException;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.GetStudentRedingRecordReq;
import com.yutong.axxc.parents.connect.http.packet.GetStudentStatusReq;
import com.yutong.axxc.parents.connect.http.packet.GetStudentStatusRes;
import com.yutong.axxc.parents.view.common.StudentStateConstant;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 获取学生状态逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class GetStudentStatusBiz extends YtAsyncTask {

	/** Context对象 */
	private Context context;

	/** Handler对象 */
	private Handler handler;

	/** 学生Id */
	private String cld_id;

	public GetStudentStatusBiz(Context context, Handler handler, String cld_id) {
		this.context = context;
		this.handler = handler;
		this.cld_id = cld_id;
		logTypeName = "[获取学生状态逻辑类]:";
	}

	@Override
	protected void doInBackground() {
		// test
//		 ThreadCommUtil.sendMsgToUI(handler,
//		 ThreadCommStateCode.COMMON_SUCCESS,
//		 StudentStateConstant.SRV_SCHOOL_AT_BUS);

		handleProcess();
	}
/**
 * 同步获取状态
 * @return
 * @throws Exception
 */
	public String getStatus() throws Exception {
		Logger.i(this.getClass(), logTypeName + "发送请求");
		GetStudentStatusReq req = buildReq();

		HttpRes httpRes = HttpMsgSendUtil.sendGetMsg(req);

		if (httpRes.isSuccess()) {
			GetStudentStatusRes res = new GetStudentStatusRes();
			res.parse(httpRes.getContent());
			if (!res.isError()) {
				Logger.i(this.getClass(), logTypeName + "成功");

				return res.getStatus();

			} else {
				Logger.i(this.getClass(), logTypeName + "失败");

				throw new YTException(ThreadCommStateCode.COMMON_FAILED);

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

	private void handleProcess() {

		Logger.i(this.getClass(), logTypeName + "发送请求");
		GetStudentStatusReq req = buildReq();

		HttpRes httpRes = HttpMsgSendUtil.sendGetMsg(req);
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
		GetStudentStatusRes res = new GetStudentStatusRes();
		res.parse(httpRes.getContent());
		if (!res.isError()) {
			Logger.i(this.getClass(), logTypeName + "成功");

			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.COMMON_SUCCESS, res.getStatus());

		} else {
			Logger.i(this.getClass(), logTypeName + "失败");
			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil
							.getInstance().get(res.getErrorCode()));

		}
	}

	private GetStudentStatusReq buildReq() {
		GetStudentStatusReq req = new GetStudentStatusReq();
		req.setAccessToken(YtApplication.getInstance().getAccessToken());
		req.setCld_id(cld_id);
		return req;
	}
}
