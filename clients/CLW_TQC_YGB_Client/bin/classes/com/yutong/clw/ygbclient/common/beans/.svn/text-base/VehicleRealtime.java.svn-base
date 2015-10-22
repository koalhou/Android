/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:08:36
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.enums.ArriveStatus;
import com.yutong.clw.ygbclient.common.enums.ScheduleItemType;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.enums.remind.RemindType;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.common.utils.DateUtils;

/**
 * 车辆实时信息实体类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class VehicleRealtime implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 车辆vin */
    public String vehicle_vin;

    /** 车辆班号 */
    public String vehicle_number;

    /** 经度 */
    public Double gps_lon;

    /** 纬度 */
    public Double gps_lat;

    /** 速度 单位：公里/小时 */
    public int speed;

    /** 方向角度， 0-360 （度） */
    public int direction;

    /**
     * 车辆状态，由5位字符串组成，前2位保留，后3位依次代表；0:离线，1:在线，0:停驶，1:行驶，0:熄火，1:点火
     */
    public String status;

    /**
     * 更新时间
     */
    public Date update_time;

    // 线路id
    public String line_id;

    // 车辆到达状态
    public ArriveStatus arrive_status;

    /**
     * 实时状态描述（由服务端负责组织逻辑数据）， 如：7：15发车，坐满发车
     */
    public String status_desc;

    // 是否直达技术北楼， 0：否，1：是
    public boolean flag;

    // 提醒类型
    public RemindType remind_type;

    // 提醒值
    public int remind_value;

    /**
     * 提醒开启状态
     */
    public RemindStatus remind_status;

    /*-----------------UI用到的数据字段-----------------------*/
    /**
     * 是否显示操作区域
     */
    public boolean showOP = false;

    /**
     * 提示文本
     */
    public String tip;

    /**
     * 数据类型。该类型决定界面如何显示，是显示具体的data，还是显示tip信息。
     */
    public ScheduleItemType itemType = ScheduleItemType.Data;

    public boolean isResult = false;
    
    public VehicleRealtime(){
    	
        this(ScheduleItemType.Data);
    }

    public VehicleRealtime(ScheduleItemType type){
    	
        itemType = type;
    }

    /**
     * 解析json消息
     * 
     * @param vehiclesJSONArray
     */
    public void parse(JSONObject jsonObject){
        vehicle_vin = jsonObject.optString("vehicle_vin");
        vehicle_number = jsonObject.optString("vehicle_number");
        line_id = jsonObject.optString("line_id");
        arrive_status = ArriveStatus.myValueOf(jsonObject.optInt("arrive_status"));
        status_desc = jsonObject.optString("status_desc");
        flag = DataTypeConvertUtils.int2Boolean(jsonObject.optInt("flag"));
        remind_type = RemindType.myValueOf(jsonObject.optInt("remind_type"));
        remind_value = jsonObject.optInt("remind_value");
        remind_status = RemindStatus.myValueOf(jsonObject.optInt("remind_status"));
        update_time = DateUtils.strToDate(jsonObject.optString("update_time"), DateUtils.LONG_TIME_FORMAT);

    }

}
