/**
 * @公司名称：YUTONG
 * @文件名：GetRemindStationsBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午9:57:47
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.linestation;

import java.util.List;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.SetRemindStationsReq;
import com.yutong.clw.ygbclient.connect.http.linestation.SetRemindStationsRes;
import com.yutong.clw.ygbclient.dao.linestation.StationInfoDao;

/**
 * 设置站点提醒业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:57:47
 */
public class SetRemindStationsBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    private List<RemindInfo> remindInfos;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public SetRemindStationsBiz(Context context, List<RemindInfo> remindInfos)
    {
        this.context = context;
        this.remindInfos = remindInfos;

        logTypeName = "[设置站点提醒业务逻辑类]:";
        setActionURI(ActionURI.SET_REMIND_STATION);
    }

    public boolean setRemindInfo() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        SetRemindStationsReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            SetRemindStationsRes res = new SetRemindStationsRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");

                if (remindInfos != null)
                {
                    // #region by zz 增加本地存取缓存，减少网络交互，提供响应速度。
                    StationInfoDao stationdao = new StationInfoDao(context);
                    for (RemindInfo remindInfo : remindInfos)
                    {
                        Logger.i(this.getClass(), logTypeName + "站点[" + remindInfo.station_id + "]提醒状态：" + remindInfo.remind_status);

                        if (remindInfo.line_range == LineRange.FactoryInside
                                || (remindInfo.line_range == LineRange.FactoryOuter && remindInfo.remind_range == RemindRange.OnlyStation))
                        {
                            switch (remindInfo.remind_status)
                            {
                            case Open:
                                stationdao.setStationStatus(remindInfo.station_id, true, ProxyManager.getInstance(context).getUserCode());
                                break;
                            case Close:
                                stationdao.setStationStatus(remindInfo.station_id, false, ProxyManager.getInstance(context).getUserCode());
                                break;
                            case Delete:
                                stationdao.setStationStatus(remindInfo.station_id, false, ProxyManager.getInstance(context).getUserCode());
                                break;
                            case NoRemind:
                                // if(remindInfo.line_range !=
                                // LineRange.FactoryInside)
                                // {
                                // stationdao.setStationStatus(remindInfo.station_id,
                                // false,
                                // ProxyManager.getInstance(context).getUserCode());
                                // }
                                stationdao.setStationStatus(remindInfo.station_id, false, ProxyManager.getInstance(context).getUserCode());
                                break;
                            }
                        }
                    }//for

                    // #endregion
                }
                // 受影响缓存需要设置超时
                // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_AREA_LINES);
                // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_IDS_LINES);
                // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_STATIONS);
                // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_FAVORITES);
                CahceDataManager.getInstance(context).removeCache(ActionURI.GET_REMIND_STATIONS);

                return true;
            }
            else
            {
                Logger.e(this.getClass(), logTypeName + "失败");
                throw new CommonException(CommonNetStatus.Error_Info, res.getErrorCode(), res.getErrorDes());

            }
        }
        else if (httpRes.isTokenExpire())
        {
            Logger.e(this.getClass(), logTypeName + "Token失效");
            throw new CommonException(CommonNetStatus.Token_InValid);
        }
        else if (httpRes.isException())
        {
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Exception);
        }
        else
        {
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Not_Stable);
        }

    }

    /**
     * 组装请求对象
     * 
     * @param context
     * @return
     */
    private SetRemindStationsReq buildReq()
    {
        SetRemindStationsReq req = new SetRemindStationsReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());

        req.setRemindInfos(remindInfos);
        return req;
    }

}
