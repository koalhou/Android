/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:38:12
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.JsonObject;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.common.beans.line.StationVehicleRealTimeInfo;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StationType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取站点与车辆相对实时数据信息响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:38:17
 */
public class StationRealTimeDataRes extends AbstractRes
{
    /**
     * 站点与车辆相对实时数据
     */
    private List<StationVehicleRealTimeInfo> realTimeInfos;

    @Override
    public boolean parseCorrectMsg(String jsonObject)
    {

        try
        {
            JSONArray res = new JSONArray(jsonObject);
            realTimeInfos = new ArrayList<StationVehicleRealTimeInfo>();
            StationVehicleRealTimeInfo bean = null;
            StationInfo stationInfo = null;
            JSONArray tempRealJson = null;
            List<VehicleRealtime> tempInfos = null;
            for (int i = 0; i < res.length(); i++)
            {
                bean = new StationVehicleRealTimeInfo();
                JSONObject item = res.optJSONObject(i);
                stationInfo = new StationInfo();
                stationInfo.line_range = LineRange.myValueOf(item.optInt("line_range"));
                stationInfo.status_range = StatusRange.myValueOf(item.optInt("status_range"));
                stationInfo.id = item.optString("station_id");

                stationInfo.belong_area_id = item.optString("belong_area_id");
                stationInfo.name = item.optString("name");
                stationInfo.alias = item.optString("alias");

                stationInfo.gps_lon = item.optDouble("gps_lon");
                stationInfo.gps_lat = item.optDouble("gps_lat");
                stationInfo.status = DataTypeConvertUtils.int2Boolean(item.optInt("status"));
                if (item.optString("plan_arrive_time") != null)
                {
                    stationInfo.plan_arrive_time = item.optString("plan_arrive_time");
                }
                stationInfo.favorites = DataTypeConvertUtils.int2Boolean(item.optInt("favorites"));
                bean.setStationInfos(stationInfo);
                // 车辆信息
                tempRealJson = item.optJSONArray("vehicles");
                tempInfos = new ArrayList<VehicleRealtime>();
                VehicleRealtime vehicleRealtime = null;
                for (int j = 0; j < tempRealJson.length(); j++)
                {
                    vehicleRealtime = new VehicleRealtime();
                    vehicleRealtime.parse(tempRealJson.optJSONObject(j));
                    tempInfos.add(vehicleRealtime);
                }
                bean.VehicleRealtimeInfos = tempInfos;
                realTimeInfos.add(bean);
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取站点与车辆相对实时数据信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }
    }

    /**
     * 站点与车辆相对实时数据
     */
    public List<StationVehicleRealTimeInfo> getRealTimeInfos()
    {
        return realTimeInfos;
    }

}
