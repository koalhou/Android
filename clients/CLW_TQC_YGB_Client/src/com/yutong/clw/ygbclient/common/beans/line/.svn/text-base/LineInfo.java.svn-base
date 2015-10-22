/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:35:19
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans.line;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StatusRange;

/**
 * @author zhangzhia 2013-10-22 下午3:35:19
 */
public class LineInfo
{
    public String line_id;

    public String line_name;

    public AreaType area_type;

    public LineRange line_range;

    public StatusRange status_range;

    public List<CoordPoint> points;

    public List<StationInfo> stations;
    
    public Date creatTime;

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

    public AreaType getArea_type()
    {
        return area_type;
    }

    public void setArea_type(AreaType area_type)
    {
        this.area_type = area_type;
    }

    public LineRange getLine_range()
    {
        return line_range;
    }

    public void setLine_range(LineRange line_range)
    {
        this.line_range = line_range;
    }

    public StatusRange getStatus_range()
    {
        return status_range;
    }

    public void setStatus_range(StatusRange status_range)
    {
        this.status_range = status_range;
    }

    public List<CoordPoint> getPoints()
    {
        return points;
    }

    public void setPoints(List<CoordPoint> points)
    {
        this.points = points;
    }

    public List<StationInfo> getStations()
    {
        return stations;
    }

    public void setStations(List<StationInfo> stations)
    {
        this.stations = stations;
    }

    public void parse(JSONObject res)
    {
        line_id = res.optString("line_id");
        line_name = res.optString("line_name");
        if (StringUtils.isNotBlank(res.optString("area_type")))
        {
            area_type = AreaType.myValueOf(res.optInt("area_type"));
        }
        if (StringUtils.isNotBlank(res.optString("line_range")))
        {

            line_range = LineRange.myValueOf(res.optInt("line_range"));
        }
        if (StringUtils.isNotBlank(res.optString("status_range")))
        {
            if (res.optInt("status_range") < 2)
            {
                status_range = StatusRange.myValueOf(res.optInt("status_range"));
            }
        }
        // 线路轨迹信息
        JSONArray pointsJSONArray = res.optJSONArray("points");
        ArrayList<CoordPoint> coordPoints = new ArrayList<CoordPoint>();
        int pointsLen = pointsJSONArray.length();
        CoordPoint coordPoint = null;
        for (int i = 0; i < pointsLen; i++)
        {
            coordPoint = new CoordPoint();
            JSONObject optJSONObject = pointsJSONArray.optJSONObject(i);
            if (optJSONObject == null)
            {
                continue;
            }
            coordPoint.parse(pointsJSONArray.optJSONObject(i));
            coordPoints.add(coordPoint);
        }
        points = coordPoints;
        // 站点信息
        JSONArray stationsJSONArray = res.optJSONArray("stations");
        ArrayList<StationInfo> stationInfos = new ArrayList<StationInfo>();
        int stationsLen = pointsJSONArray.length();
        StationInfo stationInfo = null;
        for (int i = 0; i < stationsLen; i++)
        {
            stationInfo = new StationInfo();
            JSONObject optJSONObject = stationsJSONArray.optJSONObject(i);
            if (optJSONObject == null)
            {
                continue;
            }
            stationInfo.parse(stationsJSONArray.optJSONObject(i));
            stationInfo.setLine_id(line_id);
            stationInfo.setArea_type(area_type);
            stationInfo.setStatus_range(status_range);
            stationInfos.add(stationInfo);
        }
        stations = stationInfos;
    }
}
