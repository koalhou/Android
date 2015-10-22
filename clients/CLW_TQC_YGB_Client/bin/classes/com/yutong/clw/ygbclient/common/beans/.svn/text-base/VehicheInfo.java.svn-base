/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午4:43:07
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StationType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.utils.DateUtils;

/**
 * 车辆信息实体类
 * 
 * @author zhangzhia 2013-10-22 下午4:43:07
 */
public class VehicheInfo implements Serializable
{
    /**
     * @author zhangyongn 2013-11-14 上午10:00:51
     */
    private static final long serialVersionUID = -8397336065662707116L;

    // 车辆VIN号
    public String vehicle_vin;

    // 提醒车辆班号
    public String vehiche_number;

    // 当前归属的line_id
    public String line_id;

    // 计划发车时间
    public Date plan_start_time;
    
    public void parse(JSONObject optJSONObject)
    {

        vehicle_vin = optJSONObject.optString("vehicle_vin");
        vehiche_number = optJSONObject.optString("vehicle_number");
        line_id = optJSONObject.optString("line_id");
        plan_start_time = DateUtils.strToDate(optJSONObject.optString("plan_start_time"), "yyyyMMddHHmmss");

    }

    public String getVehicle_vin()
    {
        return vehicle_vin;
    }

    public void setVehicle_vin(String vehicle_vin)
    {
        this.vehicle_vin = vehicle_vin;
    }

    public String getVehiche_number()
    {
        return vehiche_number;
    }

    public void setVehiche_number(String vehiche_number)
    {
        this.vehiche_number = vehiche_number;
    }

    public String getLine_id()
    {
        return line_id;
    }

    public void setLine_id(String line_id)
    {
        this.line_id = line_id;
    }

    public Date getPlan_start_time()
    {
        return plan_start_time;
    }

    public void setPlan_start_time(Date plan_start_time)
    {
        this.plan_start_time = plan_start_time;
    }

}
