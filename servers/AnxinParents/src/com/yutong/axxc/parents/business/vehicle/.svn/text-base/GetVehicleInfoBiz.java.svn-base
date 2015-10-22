package com.yutong.axxc.parents.business.vehicle;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.VehicleInfoReq;
import com.yutong.axxc.parents.connect.http.packet.VehicleInfoRes;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 获取车辆信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class GetVehicleInfoBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 车辆vin */
    private String vehicle_vin;

    public GetVehicleInfoBiz(Context context, Handler handler, String vehicle_vin)
    {
        this.context = context;
        this.handler = handler;
        this.vehicle_vin = vehicle_vin;
        logTypeName = "[获取车辆信息逻辑类]:";
    }

    @Override
    protected void doInBackground()
    {
        handleProcess();
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        VehicleInfoReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendGetMsg(req);
        // 用来UI取消操作
        if (isCanceled())
        {
            return;
        }

        if (httpRes.isSuccess())
        {
            parseAndProcessRes(httpRes);
        }
        else if (httpRes.isTokenExpire())
        {
            Logger.i(this.getClass(), logTypeName + "Token失效");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.TOKEN_INVALID);
        }
        else if (httpRes.isException())
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.NETWORK_ERROR, httpRes.getFailInfo());
        }
        else
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, httpRes.getFailInfo());
        }
    }

    private void parseAndProcessRes(HttpRes httpRes)
    {
        VehicleInfoRes res = new VehicleInfoRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, res.getVehicleInfoBean());

        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));

        }
    }

    private VehicleInfoReq buildReq()
    {
        VehicleInfoReq req = new VehicleInfoReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setVehicle_vin(vehicle_vin);
        return req;
    }

}
