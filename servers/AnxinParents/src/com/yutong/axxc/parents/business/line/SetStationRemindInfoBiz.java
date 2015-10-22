package com.yutong.axxc.parents.business.line;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.cache.AppCacheData;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.StationRemindInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.SetSationRemindInfoReq;
import com.yutong.axxc.parents.connect.http.packet.SetSationRemindInfoRes;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 设置站点提醒信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class SetStationRemindInfoBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 学生Id */
    private String cld_id;

    /** 站点Id */
    private String station_id;

    /** 站点提醒对象 */
    private StationRemindInfoBean stationRemindInfoBean;

    public SetStationRemindInfoBiz(Context context, Handler handler, String cld_id, String station_id,
            StationRemindInfoBean stationRemindInfoBean)
    {
        this.context = context;
        this.handler = handler;
        this.cld_id = cld_id;
        this.station_id = station_id;
        this.stationRemindInfoBean = stationRemindInfoBean;
        logTypeName = "[设置站点提醒信息逻辑类]:";
    }

    @Override
    protected void doInBackground()
    {
        handleProcess();
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        SetSationRemindInfoReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendPutMsg(req);
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
        SetSationRemindInfoRes res = new SetSationRemindInfoRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS);

        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private SetSationRemindInfoReq buildReq()
    {
        SetSationRemindInfoReq req = new SetSationRemindInfoReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setCld_id(cld_id);
        req.setStation_id(station_id);
        req.setStationRemindInfoBean(stationRemindInfoBean);

        return req;
    }

}
