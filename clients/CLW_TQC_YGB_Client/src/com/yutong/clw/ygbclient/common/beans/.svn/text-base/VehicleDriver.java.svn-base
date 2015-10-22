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
public class VehicleDriver implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



    // 司机名称
    public String emp_code;
    public String driver_name;
    public String vehicle_vin;
    public String url;

    // 司机员工号

    // 司机电话
    public String driver_tel;

    // 司机照片URL
    public String pic_url;
    public String line_id;
    //车牌号
    public String vehicleNum;
    
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
    
    public VehicleDriver()
    {
        this(ScheduleItemType.Data);
    }

    public VehicleDriver(ScheduleItemType type)
    {
        itemType = type;
    }

    /**
     * 解析json消息
     * 
     * @param vehiclesJSONArray
     */
    public void parse(JSONObject jsonObject)
    {
        vehicle_vin = jsonObject.optString("vehicle_vin");
        line_id = jsonObject.optString("line_id");
        driver_name = jsonObject.optString("driver_name");
        driver_tel = jsonObject.optString("driver_tel");
        pic_url =  jsonObject.optString("url");
        emp_code =  jsonObject.optString("emp_code");
    }

}
