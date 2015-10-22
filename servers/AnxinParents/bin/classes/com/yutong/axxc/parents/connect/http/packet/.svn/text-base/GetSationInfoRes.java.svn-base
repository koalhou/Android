package com.yutong.axxc.parents.connect.http.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.LocationUtils;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.NeuGeoPoint;
import com.yutong.axxc.parents.common.beans.StationInfoBean;

/**
 * 获取学生站点信息响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetSationInfoRes extends AbstractRes
{
    /** trip_id */
    private String trip_id;
    
    /** line_id */
    private String line_id;
      
    /** 站点信息 */
    private List<StationInfoBean> stationInfoBeans;

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonString);

            trip_id = res.optString("trip_id");
            line_id = res.optString("line_id");
            
            JSONArray jsonArray = res.getJSONArray("station_infos");// 获取JSONArray

            if (jsonArray != null)
            {
                stationInfoBeans = new ArrayList<StationInfoBean>();
                for (int i = 0, length = jsonArray.length(); i < length; i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject != null)
                    {
                        StationInfoBean stationInfoBean = new StationInfoBean();

                        stationInfoBean.setStation_id(jsonObject.optString("station_id"));
                        stationInfoBean.setStation_name(jsonObject.optString("station_name"));
                        stationInfoBean.setPlan_arrive_time(jsonObject.optString("plan_arrive_time"));

                        //直接转换为百度地图坐标
                        NeuGeoPoint bdGeoPoint = LocationUtils.fromWgs84ToBaidu(jsonObject.optString("gps_lon"), jsonObject.optString("gps_lat"));
                        stationInfoBean.setGps_lon(String.valueOf(bdGeoPoint.getX()));
                        stationInfoBean.setGps_lat(String.valueOf(bdGeoPoint.getY()));

//                        stationInfoBean.setGps_lon(jsonObject.optString("gps_lon"));
//                        stationInfoBean.setGps_lat(jsonObject.optString("gps_lat"));
                        stationInfoBean.setStation_type(jsonObject.optString("station_type"));

                        stationInfoBeans.add(stationInfoBean);
                        

                    }
                }

            }

            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取学生站点信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public String getTrip_id()
    {
        return trip_id;
    }


    public String getLine_id()
    {
        return line_id;
    }




    public List<StationInfoBean> getStationInfoBeans()
    {
        return stationInfoBeans;
    }

  

}
