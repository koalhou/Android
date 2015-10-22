/**
 * @公司名称：YUTONG
 * @文件名：StationRealTimeDataBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:01:30
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.linestation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.common.beans.line.StationVehicleRealTimeInfo;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.ArriveStatus;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.StationRealTimeDataReq;
import com.yutong.clw.ygbclient.connect.http.linestation.StationRealTimeDataRes;

/**
 * 获取站点和车辆相对实时数据业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午10:01:30
 */
public class StationRealTimeDataBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    private Date search_date;

    /**
     * 站点ID集合
     */
    private List<String> station_ids;

    private AreaType area_type;

    private StatusRange status_range;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public StationRealTimeDataBiz(Context context, Date search_date, List<String> station_ids, AreaType area_type, StatusRange status_range)
    {
        this.context = context;
        this.search_date = search_date;
        this.station_ids = station_ids;
        this.area_type = area_type;
        this.status_range = status_range;

        logTypeName = "[获取站点和车辆相对实时数据业务逻辑类]:";
        setActionURI(ActionURI.GET_STATION_REALTIME_DATA);
    }

    public List<StationVehicleRealTimeInfo> getRealTimeData() throws CommonException
    {
        StationRealTimeDataReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            StationRealTimeDataRes res = new StationRealTimeDataRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                List<StationVehicleRealTimeInfo> realTimeInfos = res.getRealTimeInfos();

                List<StationVehicleRealTimeInfo> sortRealTimeInfos = null;

                if (realTimeInfos != null && realTimeInfos.size() > 0)
                {
                    sortRealTimeInfos = new ArrayList<StationVehicleRealTimeInfo>();
                    for (StationVehicleRealTimeInfo info : realTimeInfos)
                    {
                        StationVehicleRealTimeInfo sortInfo = new StationVehicleRealTimeInfo();
                        sortInfo.stationInfo = info.getStationInfos();

                        sortInfo.setVehicleRealtimeInfos(getSortVehicleRealtimeInfos(info.getVehicleRealtimeInfos(), info.getStationInfos()
                                .getStatus_range()));

                        sortRealTimeInfos.add(sortInfo);

                    }
                }

                return res.getRealTimeInfos();
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

    private List<VehicleRealtime> getSortVehicleRealtimeInfos(List<VehicleRealtime> list, StatusRange statusRange)
    {
        if (list == null)
        {
            return list;
        }

        List<VehicleRealtime> reals0 = new ArrayList<VehicleRealtime>();
        List<VehicleRealtime> reals1 = new ArrayList<VehicleRealtime>();
        List<VehicleRealtime> reals2 = new ArrayList<VehicleRealtime>();
        List<VehicleRealtime> reals3 = new ArrayList<VehicleRealtime>();
        List<VehicleRealtime> reals5 = new ArrayList<VehicleRealtime>();

        for (VehicleRealtime real : list)
        {
            if (ArriveStatus.NoArrive == real.arrive_status)
            {
                reals0.add(real);
            }
            else if (ArriveStatus.Arrive == real.arrive_status)
            {
                reals1.add(real);
            }
            else if (ArriveStatus.Leave == real.arrive_status)
            {
                reals2.add(real);
            }
            else if (ArriveStatus.NoStartOff == real.arrive_status)
            {
                reals3.add(real);
            }
            else if (ArriveStatus.Leave == real.arrive_status)
            {
                reals5.add(real);
            }
        }

        if (StatusRange.MorningWork == statusRange)
        {

            reals1.addAll(reals0);
            reals1.addAll(reals2);
        }
        else
        {

            reals3.addAll(reals5);
        }

        return null;
    }

    /**
     * 组装请求对象
     * 
     * @param context
     * @return
     */
    private StationRealTimeDataReq buildReq()
    {
        StationRealTimeDataReq req = new StationRealTimeDataReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setSearch_date(search_date);
        req.setStation_ids(station_ids);
        req.setArea_type(area_type);
        req.setStatus_range(status_range);
        return req;
    }

}
