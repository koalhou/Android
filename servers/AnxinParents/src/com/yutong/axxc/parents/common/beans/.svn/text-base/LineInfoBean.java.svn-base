package com.yutong.axxc.parents.common.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 线路信息实体类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class LineInfoBean
{
	/**
	 * 上行线路类型
	 */
	public static final String LINE_TYPE_UP="0";
	/**
	 * 下行线路类型
	 */
	public static final String LINE_TYPE_DOWN="1";
    /** 行程id */
    private String trip_id;
    /** 线路id  */
    private String line_id;
    /** 线路名称  */
    private String line_name;
    /** 线路类型 0：上行，1：下行  */
    private String line_type;

    private List<LinePointInfoBean> pointInfos = new ArrayList<LinePointInfoBean>();

    public String getLine_id()
    {
        return line_id;
    }

    public void setLine_id(String line_id)
    {
        this.line_id = line_id;
    }

    public String getLine_name()
    {
        return line_name;
    }

    public void setLine_name(String line_name)
    {
        this.line_name = line_name;
    }

    public List<LinePointInfoBean> getPointInfos()
    {
        return pointInfos;
    }

    public void setPointInfos(List<LinePointInfoBean> pointInfos)
    {
        this.pointInfos = pointInfos;
    }

    
    public String getTrip_id()
    {
        return trip_id;
    }

    public void setTrip_id(String trip_id)
    {
        this.trip_id = trip_id;
    }

    public String getLine_type()
    {
        return line_type;
    }

    public void setLine_type(String line_type)
    {
        this.line_type = line_type;
    }

    public void parse(String jsonString)
    {
        if (StringUtils.isBlank(jsonString)) {
            return;
        }
        try
        {
            JSONObject res = new JSONObject(jsonString);

            JSONObject jsonObject = res.getJSONObject("line_info");

            if (jsonObject != null)
            {
                line_id = jsonObject.optString("line_id");
                line_name = jsonObject.optString("line_name");

                JSONArray jsonArray = res.getJSONArray("point_infos");

                if (jsonArray != null)
                {

                    for (int i = 0, length = jsonArray.length(); i < length; i++)
                    {// 遍历JSONArray

                        JSONObject jsonPointObject = jsonArray.getJSONObject(i);

                        if (jsonPointObject != null)
                        {
                            LinePointInfoBean linePointInfoBean = new LinePointInfoBean();

                            linePointInfoBean.setGps_lon(jsonPointObject.optString("gps_lon"));
                            linePointInfoBean.setGps_lat(jsonPointObject.optString("gps_lat"));

                            pointInfos.add(linePointInfoBean);

                        }
                    }
                }
            }
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[线路信息实体类]:解析响应消息出错，详细信息：", e);
        }
    }

}
