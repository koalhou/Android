/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午4:50:10
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.enums.remind.RemindType;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;

/**
 * 提醒信息实体类
 * 
 * @author zhangzhia 2013-10-22 下午4:50:10
 */
public class RemindInfo implements Serializable
{
    
    public String id;

    public AreaType area_type;

    public String area_name;

    public LineRange line_range;

    public StatusRange status_range;

    public String station_id;

    public String station_name;

    public String line_id;

    public String line_name;

    public String vehiche_vin;

    public String vehiche_number;

    public RemindRange remind_range;

    public RemindType remind_type;

    public int remind_value;

    public String remind_week;

    public RemindStatus remind_status;
    
    public Date modify_time;


    public Date no_remind_date;
    
    
    public RemindInfo()
    {
    	
    }

    public RemindInfo(AreaType area_type, String station_id, StatusRange status_range, String vehiche_vin)
    {
        this.area_type = area_type;
        this.station_id = station_id;
        this.status_range = status_range;
        this.vehiche_vin = vehiche_vin;
    }
    
    
    public void parse(JSONObject optJSONObject)
    {
        // "id": "141132",
        // "area_type": "1",
        // "station_name": "4号东门（ZDCS）",
        // "remind_status": "1",
        // "remind_week": "0001010",
        // "remind_type": "1",
        // "area_name": "第一工厂",
        // "station_id": "3758",
        // "status_range": "0",
        // "remind_range": "0",
        // "remind_value": "0"

        id = optJSONObject.optString("id");
        if (StringUtil.isNotBlank(optJSONObject.optString("area_type")))
        {
            area_type = AreaType.myValueOf(optJSONObject.optInt("area_type"));
        }
        area_name = optJSONObject.optString("area_name");
        if (StringUtil.isNotBlank(optJSONObject.optString("line_range")))
        {
            line_range = LineRange.myValueOf(optJSONObject.optInt("line_range"));
        }
        if (StringUtil.isNotBlank(optJSONObject.optString("status_range")))
        {
            status_range = StatusRange.myValueOf(optJSONObject.optInt("status_range"));
        }
        station_id = optJSONObject.optString("station_id");
        station_name = optJSONObject.optString("station_name");

        line_id = optJSONObject.optString("line_id");
        line_name = optJSONObject.optString("line_name");

        vehiche_vin = optJSONObject.optString("vehicle_vin");
        vehiche_number = optJSONObject.optString("vehicle_number");
        if (StringUtil.isNotBlank(optJSONObject.optString("remind_range")))
        {
            remind_range = RemindRange.myValueOf(optJSONObject.optInt("remind_range"));
        }
        if (StringUtil.isNotBlank(optJSONObject.optString("remind_type")))
        {
            remind_type = RemindType.myValueOf(optJSONObject.optInt("remind_type"));
        }
        if (StringUtil.isNotBlank(optJSONObject.optString("remind_value")))
        {
            remind_value = optJSONObject.optInt("remind_value");
        }
        remind_week = optJSONObject.optString("remind_week");

        if (StringUtil.isNotBlank(optJSONObject.optString("remind_status")))
        {
            remind_status = RemindStatus.myValueOf(optJSONObject.optInt("remind_status"));
        }
        if (StringUtil.isNotBlank(optJSONObject.optString("no_remind_date")))
        {
            no_remind_date = DateUtils.strToDate(optJSONObject.optString("no_remind_date"), DateUtils.TIME_FORMAT);
        }
        
        if (StringUtil.isNotBlank(optJSONObject.optString("modify_time")))
        {
            modify_time = DateUtils.strToDate(optJSONObject.optString("modify_time"), DateUtils.LONG_TIME_FORMAT);
        }
        
    }
    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public AreaType getArea_type()
    {
        return area_type;
    }

    public void setArea_type(AreaType area_type)
    {
        this.area_type = area_type;
    }

    public String getArea_name()
    {
        return area_name;
    }

    public void setArea_name(String area_name)
    {
        this.area_name = area_name;
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

    public String getStation_id()
    {
        return station_id;
    }

    public void setStation_id(String station_id)
    {
        this.station_id = station_id;
    }

    public String getStation_name()
    {
        return station_name;
    }

    public void setStation_name(String station_name)
    {
        this.station_name = station_name;
    }

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

    public String getVehiche_vin()
    {
        return vehiche_vin;
    }

    public void setVehiche_vin(String vehiche_vin)
    {
        this.vehiche_vin = vehiche_vin;
    }

    public String getVehiche_number()
    {
        return vehiche_number;
    }

    public void setVehiche_number(String vehiche_number)
    {
        this.vehiche_number = vehiche_number;
    }

    public RemindRange getRemind_range()
    {
        return remind_range;
    }

    public void setRemind_range(RemindRange remind_range)
    {
        this.remind_range = remind_range;
    }

    public RemindType getRemind_type()
    {
        return remind_type;
    }

    public void setRemind_type(RemindType remind_type)
    {
        this.remind_type = remind_type;
    }

    public int getRemind_value()
    {
        return remind_value;
    }

    public void setRemind_value(int remind_value)
    {
        this.remind_value = remind_value;
    }

    public String getRemind_week()
    {
        return remind_week;
    }

    public void setRemind_week(String remind_week)
    {
        this.remind_week = remind_week;
    }

    public RemindStatus getRemind_status()
    {
        return remind_status;
    }

    public void setRemind_status(RemindStatus remind_status)
    {
        this.remind_status = remind_status;
    }

    public Date getModify_time()
    {
        return modify_time;
    }

    public void setModify_time(Date modify_time)
    {
        this.modify_time = modify_time;
    }

    public Date getNo_remind_date()
    {
        return no_remind_date;
    }

    public void setNo_remind_date(Date no_remind_date)
    {
        this.no_remind_date = no_remind_date;
    }
}
