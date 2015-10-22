/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:38:12
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 获取站点与车辆相对实时数据信息请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:38:12
 */
public class StationRealTimeDataReq extends AbstractReq
{
    /**
     * 查询日期
     */
    private Date search_date;

    /**
     * 站点id集合
     */
    private List<String> station_ids;

    private AreaType area_type;
    
    /**
     * 早班晚班状态
     */
    private StatusRange status_range;

    @Override
    public String packetMsgBody()
    {
        try
        {

            JSONObject req = new JSONObject();
            req.put("search_date", DateUtils.dateToStr(search_date, DateUtils.TIME_FORMAT));
            JSONArray jsonArray = new JSONArray(station_ids);
            req.put("station_ids", jsonArray);
            req.put("area_type", area_type.value());   
            req.put("status_range", status_range.value());
            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[ 获取站点与车辆相对实时数据信息请求类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }

    public void setSearch_date(Date search_date)
    {
        this.search_date = search_date;
    }

    /**
     * 站点id集合
     */
    public void setStation_ids(List<String> station_ids)
    {
        this.station_ids = station_ids;
    }

    
    public void setArea_type(AreaType area_type)
    {
        this.area_type = area_type;
    }

    /**
     * 早班晚班状态
     */
    public void setStatus_range(StatusRange status_range)
    {
        this.status_range = status_range;
    }
    
    

}
