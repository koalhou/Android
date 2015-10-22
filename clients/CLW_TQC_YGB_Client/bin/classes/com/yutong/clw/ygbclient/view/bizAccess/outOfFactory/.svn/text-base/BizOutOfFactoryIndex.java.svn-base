package com.yutong.clw.ygbclient.view.bizAccess.outOfFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.linestation.GetSingleRemindStationsBiz;
import com.yutong.clw.ygbclient.business.linestation.GetStationFavoritesBiz;
import com.yutong.clw.ygbclient.business.linestation.RecommendStationsBiz;
import com.yutong.clw.ygbclient.business.linestation.StationRealTimeDataBiz;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.line.StationVehicleRealTimeInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * 厂外通勤首页逻辑类
 * 
 * @author zhangyongn 2013-11-8 下午4:09:17
 */
public class BizOutOfFactoryIndex extends BizBase
{

    public BizOutOfFactoryIndex(Context context)
    {
        super(context);
    }

    public BizOutOfFactoryIndex(Context context, int threadcount)
    {
        super(context, threadcount);
    }

    /**
     * 获取收藏站点数据
     * 
     * @author zhangyongn 2013-11-11 下午1:54:22
     * @param type 厂区
     * @param range 早晚班
     * @param process
     * @return
     */
    public BizDataTypeEnum getFavoriteStations(AreaType type, final StatusRange range, BizResultProcess<List<StationInfo>> process)
    {
        final GetStationFavoritesBiz biz = new GetStationFavoritesBiz(YtApplication.getInstance(), type);

        Callable<List<StationInfo>> cacheCallable = new Callable<List<StationInfo>>()
        {
            @Override
            public List<StationInfo> call() throws Exception
            {
                // return biz.getLinesFromLocal();
                List<StationInfo> infos = biz.getStationFavoritesFromLocal();
                return Filter(infos, range);
            }
        };

        Callable<List<StationInfo>> networkCallable = new Callable<List<StationInfo>>()
        {
            @Override
            public List<StationInfo> call() throws Exception
            {

                List<StationInfo> infos = biz.getStationFavoritesFromSever();
                // test
                // StationInfo info = new StationInfo();
                // for (int i = 0; i < 3; i++)
                // {
                // info = new StationInfo();
                // info.setId(i + "");
                // info.setName("佛冈" + i);
                // info.status_range = StatusRange.MorningWork;
                // infos.add(info);
                // }

                return Filter(infos, range);

            }
        };

        return BizCommonWork(isFavoriteSitesExpires(biz), cacheCallable, networkCallable, process);
    }

    protected List<StationInfo> Filter(List<StationInfo> infos, StatusRange range)
    {
        if (infos == null)
            return null;
        List<StationInfo> list = new ArrayList<StationInfo>();

        for (int i = 0; i < infos.size(); i++)
        {
            StationInfo info = infos.get(i);
            if (info.status_range == range || info.status_range == StatusRange.AllWork)
                list.add(info);
        }
        return list;
    }

    /**
     * 获取推荐站点
     * 
     * @author zhangyongn 2013-11-11 下午2:04:46
     * @param type
     * @param range
     * @param process
     * @return
     */
    public BizDataTypeEnum getRecommandStations(AreaType type, final StatusRange range, BizResultProcess<List<StationInfo>> process)
    {
        final RecommendStationsBiz biz = new RecommendStationsBiz(YtApplication.getInstance(), type);

        Callable<List<StationInfo>> cacheCallable = new Callable<List<StationInfo>>()
        {
            @Override
            public List<StationInfo> call() throws Exception
            {
                List<StationInfo> info = biz.getStationInfosFromLocal();
                return Filter(info, range);
            }
        };

        Callable<List<StationInfo>> networkCallable = new Callable<List<StationInfo>>()
        {
            @Override
            public List<StationInfo> call() throws Exception
            {

                List<StationInfo> info = biz.getStationInfosFromSever();
                return Filter(info, range);
            }
        };

        return BizCommonWork(isRecommendSitesExpires(biz), cacheCallable, networkCallable, process);
    }

    /**
     * 推荐站点是否过期
     * 
     * @author zhangyongn 2013-11-11 下午2:07:57
     * @param biz
     * @return
     */
    private boolean isRecommendSitesExpires(RecommendStationsBiz biz)
    {
        // return false;
        return !biz.IsCacheExpires();
    }

    /**
     * 收藏站点是否过期
     * 
     * @author zhangyongn 2013-11-11 下午1:54:08
     * @param biz
     * @return
     */
    private boolean isFavoriteSitesExpires(GetStationFavoritesBiz biz)
    {
        // return false;
        return !biz.IsCacheExpires();
    }

    /**
     * 获取站点的排班计划
     * 
     * @author zhangyongn 2013-11-11 下午2:13:55
     * @param station_ids 要查询的站点ID集合
     * @param dt 要查询的日期
     * @param range 早班晚班状态
     * @param process 回调
     */
    public void getStationVehicleRealTimeInfo(final List<String> station_ids, Date dt, AreaType area_type, StatusRange range,
            BizResultProcess<List<StationVehicleRealTimeInfo>> process)
    {
        final StationRealTimeDataBiz biz = new StationRealTimeDataBiz(mContext, dt, station_ids, area_type, range);

        Callable<List<StationVehicleRealTimeInfo>> networkcall = new Callable<List<StationVehicleRealTimeInfo>>()
        {
            @Override
            public List<StationVehicleRealTimeInfo> call() throws Exception
            {
                // test
                // List<StationVehicleRealTimeInfo> infos = new
                // ArrayList<StationVehicleRealTimeInfo>();
                // StationVehicleRealTimeInfo info = new
                // StationVehicleRealTimeInfo();
                // info = new StationVehicleRealTimeInfo();
                // info.stationInfo = new StationInfo();
                // info.stationInfo.id = station_ids.get(0);
                // for (int i = 0; i < 4; i++)
                // {
                //
                // VehicleRealtime r = new VehicleRealtime();
                // r.flag = false;
                // r.remind_status = RemindStatus.Open;
                // r.remind_type=RemindType.Date;
                // r.remind_value=70;
                // r.itemType = ScheduleItemType.Data;
                // r.status_desc = "19:30发证";
                // r.vehicle_number = "" + i;
                // r.arrive_status = ArriveStatus.Leave;
                // info.VehicleRealtimeInfos.add(r);
                // }
                // infos.add(info);
                // Thread.sleep(2000);
                // return infos;
                // modify by lizyi
                return biz.getRealTimeData();
            }
        };
        BizNetWork(networkcall, process);

    }

    /**
     * 获取到站提醒信息
     * 
     * @author zhangyongn 2013-11-20 上午8:41:22
     * @param area_type
     * @param station_id
     * @param status_range
     * @param vehiche_vin 可选参数。如果传入null，则表示获取站点到站提醒，如果传入非空，则表示获取班车的到站提醒
     * @param process
     */
    public void GetRemindInfo(AreaType area_type, String station_id, StatusRange status_range, String vehiche_vin,
            BizResultProcess<RemindInfo> process)
    {
        final GetSingleRemindStationsBiz biz = new GetSingleRemindStationsBiz(mContext, area_type, station_id, status_range, vehiche_vin);
        Callable<RemindInfo> networkcall = new Callable<RemindInfo>()
        {
            @Override
            public RemindInfo call() throws Exception
            {

                return biz.getRemind();
            }
        };
        BizNetWork(networkcall, process);
    }
}
