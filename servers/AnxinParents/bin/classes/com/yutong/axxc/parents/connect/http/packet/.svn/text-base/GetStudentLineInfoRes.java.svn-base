package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.LocationUtils;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.NeuGeoPoint;
import com.yutong.axxc.parents.common.beans.LineInfoBean;
import com.yutong.axxc.parents.common.beans.LinePointInfoBean;

/**
 * 获取学生路线信息响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetStudentLineInfoRes extends AbstractRes
{

    private LineInfoBean lineInfoBean;

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

    
                lineInfoBean = new LineInfoBean();
                lineInfoBean.setTrip_id(res.optString("trip_id"));
                lineInfoBean.setLine_id(res.optString("line_id"));
                lineInfoBean.setLine_name(res.optString("line_name"));
                lineInfoBean.setLine_type(res.optString("line_type"));
                
                JSONArray jsonArray = res.getJSONArray("point_infos");

                if (jsonArray != null)
                {

                    for (int i = 0, length = jsonArray.length(); i < length; i++)
                    {// 遍历JSONArray

                        JSONObject jsonPointObject = jsonArray.getJSONObject(i);

                        if (jsonPointObject != null)
                        {
                            LinePointInfoBean linePointInfoBean = new LinePointInfoBean();
                            
                            NeuGeoPoint bdGeoPoint = LocationUtils.fromWgs84ToBaidu(jsonPointObject.optString("gps_lon"), jsonPointObject.optString("gps_lat"));
                            linePointInfoBean.setGps_lon(String.valueOf(bdGeoPoint.getX()));
                            linePointInfoBean.setGps_lat(String.valueOf(bdGeoPoint.getY()));
                            
                            //linePointInfoBean.setGps_lon(jsonPointObject.optString("gps_lon"));
                            //linePointInfoBean.setGps_lat(jsonPointObject.optString("gps_lat"));

                            lineInfoBean.getPointInfos().add(linePointInfoBean);

                        }
                    }
                }

            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取学生路线信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public LineInfoBean getLineInfoBean()
    {
        return lineInfoBean;
    }

    

}
