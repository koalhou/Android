package com.yutong.axxc.parents.business.line;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YTException;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.StationRealTimeInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.GetRealTimeInfoReq;
import com.yutong.axxc.parents.connect.http.packet.GetRealTimeInfoRes;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 获取相对实时数据信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class GetRealTimeInfoBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 学生Id */
    private String cld_id;

    private String vehicle_vin;

    /** 站点Id */
    private String station_id;

    private String remind_type;

    public GetRealTimeInfoBiz(Context context, Handler handler, String cld_id, String vehicle_vin, String station_id, String remind_type)
    {
        this.context = context;
        this.handler = handler;
        this.cld_id = cld_id;
        this.vehicle_vin = vehicle_vin;
        this.station_id = station_id;
        this.remind_type = remind_type;

        logTypeName = "[获取相对实时数据信息逻辑类]:";
    }

    @Override
    protected void doInBackground()
    {
        handleProcess();
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetRealTimeInfoReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendPostMsg(req);
        // 用来UI取消操作
        if (isCanceled())
        {
            return;
        }

        if (httpRes.isSuccess())
        {
            if (httpRes.getStatusCode() == 204)
            {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REALTIMEINFO_NO_DATA);
            }
            else
            {
                parseAndProcessRes(httpRes);
            }

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
        GetRealTimeInfoRes res = new GetRealTimeInfoRes();

        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, res.getStationRealTimeInfoBean());

        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private GetRealTimeInfoReq buildReq()
    {
        GetRealTimeInfoReq req = new GetRealTimeInfoReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setCld_id(cld_id);
        req.setVehicle_vin(vehicle_vin);
        req.setStation_id(station_id);
        req.setRemind_type(remind_type);

        return req;
    }

    public static int dis = 3000;

    /**
     * 同步方式获取数据
     * 
     * @return
     * @throws Exception
     */
    public StationRealTimeInfoBean getStationRealTimeInfo() throws Exception
    {
        // test
//    	if(dis>4000)
//    	{
//    		dis=3000;
//    		throw new YTException(ThreadCommStateCode.REALTIMEINFO_NO_DATA);
//    	}
//         StationRealTimeInfoBean bean = new StationRealTimeInfoBean();
//         bean.setRemind_alias("宇通公司");
//         bean.setRemind_type("1");
//         dis+=200;
//         bean.setRemind_value("" + dis);
//         bean.setStation_name("远大理想城");
//         bean.setVehiche_plate("豫A22222");
//        
//         return bean;

        StationRealTimeInfoBean bean = null;
        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetRealTimeInfoReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendPostMsg(req);

        if (httpRes.isSuccess())
        {
            if (httpRes.getStatusCode() == 204)
            {
            	return bean;
                //throw new YTException(ThreadCommStateCode.REALTIMEINFO_NO_DATA);
            }
            else
            {
                GetRealTimeInfoRes res = new GetRealTimeInfoRes();
                res.parse(httpRes.getContent());
                if (!res.isError())
                {
                    Logger.i(this.getClass(), logTypeName + "成功");
                    return res.getStationRealTimeInfoBean();
                }
                else
                {
                    Logger.i(this.getClass(), logTypeName + "失败");
                    throw new YTException(ThreadCommStateCode.COMMON_FAILED);
                }
            }
        }
        else if (httpRes.isTokenExpire())
        {
            Logger.i(this.getClass(), logTypeName + "Token失效");

            throw new YTException(ThreadCommStateCode.TOKEN_INVALID);
        }
        else if (httpRes.isException())
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new YTException(ThreadCommStateCode.NETWORK_ERROR);
        }
        else
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new YTException(ThreadCommStateCode.COMMON_FAILED);
        }
    }
}
