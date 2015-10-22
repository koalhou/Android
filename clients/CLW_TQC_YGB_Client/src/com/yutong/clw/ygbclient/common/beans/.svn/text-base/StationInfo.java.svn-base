/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:56:09
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StationType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;

/**
 * 场外站点信息实体类
 * 
 * @author zhangzhia 2013-10-22 下午3:56:09
 */
public class StationInfo implements Serializable,Cloneable
{
    // 没有收藏的站点
    public String id;

    public String belong_area_id;

    public String name;

    public String alias;

    public Double gps_lon;

    public Double gps_lat;

    public StationType type;

    public boolean status;//是否设置到站提醒

    public String plan_arrive_time;

    public boolean favorites;

    // 线路类型
    public LineRange line_range;

    public AreaType area_type;

    public StatusRange status_range;

    // 这个字段是什么意思
    public String line_id;

    //这个字段用于在站点搜索时提示使用
    public boolean searchHistory;
    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getBelong_area_id()
    {
        return belong_area_id;
    }

    public void setBelong_area_id(String belong_area_id)
    {
        this.belong_area_id = belong_area_id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public Double getGps_lon()
    {
        return gps_lon;
    }

    public void setGps_lon(Double gps_lon)
    {
        this.gps_lon = gps_lon;
    }

    public Double getGps_lat()
    {
        return gps_lat;
    }

    public void setGps_lat(Double gps_lat)
    {
        this.gps_lat = gps_lat;
    }

    public StationType getType()
    {
        return type;
    }

    public void setType(StationType type)
    {
        this.type = type;
    }

    public boolean isStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public String getPlan_arrive_time()
    {
        return plan_arrive_time;
    }

    public void setPlan_arrive_time(String plan_arrive_time)
    {
        this.plan_arrive_time = plan_arrive_time;
    }

    public boolean isFavorites()
    {
        return favorites;
    }

    public void setFavorites(boolean favorites)
    {
        this.favorites = favorites;
    }

    public AreaType getArea_type()
    {
        return area_type;
    }

    public void setArea_type(AreaType area_type)
    {
        this.area_type = area_type;
    }

    public StatusRange getStatus_range()
    {
        return status_range;
    }

    public void setStatus_range(StatusRange status_range)
    {
        this.status_range = status_range;
    }

    public String getLine_id()
    {
        return line_id;
    }

    public void setLine_id(String line_id)
    {
        this.line_id = line_id;
    }

    public LineRange getLine_range()
    {
        return line_range;
    }

    public void setLine_range(LineRange line_range)
    {
        this.line_range = line_range;
    }

    
    public boolean isSearchHistory() {
		return searchHistory;
	}

	public void setSearchHistory(boolean searchHistory) {
		this.searchHistory = searchHistory;
	}

	public void parse(JSONObject optJSONObject)
    {
        if (StringUtils.isNotBlank(optJSONObject.optString("area_type")))
        {

            area_type = AreaType.myValueOf(optJSONObject.optInt("area_type"));
        }
        if (StringUtils.isNotBlank(optJSONObject.optString("line_id")))
        {

            line_id = optJSONObject.optString("line_id");
        }

        if (StringUtils.isNotBlank(optJSONObject.optString("status_range")))
        {
            status_range = StatusRange.myValueOf(optJSONObject.optInt("status_range"));
        }
        id = optJSONObject.optString("id");
        belong_area_id = optJSONObject.optString("belong_area_id");
        name = optJSONObject.optString("name");
        alias = optJSONObject.optString("alias");
        if (StringUtils.isNotBlank(optJSONObject.optString("gps_lon")))
        {

            gps_lon = optJSONObject.optDouble("gps_lon");
        }
        if (StringUtils.isNotBlank(optJSONObject.optString("gps_lat")))
        {
            gps_lat = optJSONObject.optDouble("gps_lat");
        }
        if (StringUtils.isNotBlank(optJSONObject.optString("type")))
        {
            type = StationType.myValueOf(optJSONObject.optInt("type"));
        }
        if (StringUtils.isNotBlank(optJSONObject.optString("status")))
        {
            status = DataTypeConvertUtils.int2Boolean(optJSONObject.optInt("status"));
        }
        if (StringUtils.isNotBlank(optJSONObject.optString("plan_arrive_time")))
        {
            plan_arrive_time = optJSONObject.optString("plan_arrive_time");
        }
        if (StringUtils.isNotBlank(optJSONObject.optString("favorites")))
        {
            favorites = DataTypeConvertUtils.int2Boolean(optJSONObject.optInt("favorites"));
        }

    }

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		StationInfo stationInfo  =null;
		 try{
			 stationInfo = (StationInfo)super.clone();
	        }catch(CloneNotSupportedException e){
	            e.printStackTrace();
	        }

		return stationInfo;
	}

}
