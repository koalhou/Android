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
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.GetSingleRemindStationsReq;
import com.yutong.clw.ygbclient.connect.http.linestation.GetSingleRemindStationsRes;
import com.yutong.clw.ygbclient.dao.linestation.RemindStationInfoDao;

/**
 * 获取单个站点提醒业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:57:47
 */
public class GetSingleRemindStationsBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    private AreaType area_type;

    private StatusRange status_range;

    private String vehiche_vin;

    private String station_id;

    /**
     * @param context.必须
     * @param area_type.必须
     * @param station_id.必须
     * @param status_range.必须
     * @param vehiche_vin.可选，如果传入，则表示查询班车的到站提醒
     */
    public GetSingleRemindStationsBiz(Context context, AreaType area_type, String station_id, StatusRange status_range, String vehiche_vin)
    {
        this.context = context;
        this.area_type = area_type;
        this.station_id = station_id;
        this.status_range = status_range;
        this.vehiche_vin = vehiche_vin;

        logTypeName = "[获取单个站点提醒业务逻辑类]:";
        setActionURI(ActionURI.GET_SINGLE_REMIND_STATIONS);

    }

    public RemindInfo getRemind() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetSingleRemindStationsReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            GetSingleRemindStationsRes res = new GetSingleRemindStationsRes();
            res.parse(httpRes.getContent());

            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");

                return res.getRemindInfo();
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
    private GetSingleRemindStationsReq buildReq()
    {
        GetSingleRemindStationsReq req = new GetSingleRemindStationsReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI, cacheKey));

        req.setArea_type(area_type);
        req.setStation_id(station_id);
        req.setStatus_range(status_range);
        req.setVehiche_vin(vehiche_vin);
        return req;
    }

}
