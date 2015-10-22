package com.yutong.axxc.parents.connect.http.packet;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.RidingRecordBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;

/**
 * 获取学生乘车信息响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetStudentRedingRecordRes extends AbstractRes
{

    /** 学生乘车信息 */
    private RidingRecordBean ridingRecordBean;

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

           JSONObject jsonObject = res.getJSONObject("riding_info"); 
           
            if (jsonObject != null)
            {
                ridingRecordBean = new RidingRecordBean();
    
                
                ridingRecordBean.setCld_id(jsonObject.optString("cld_id"));;
                ridingRecordBean.setVehicle_speed(jsonObject.optString("vehicle_speed"));
                ridingRecordBean.setEnterprise_mode(jsonObject.optString("enterprise_mode"));
                ridingRecordBean.setDriver(jsonObject.optString("driver"));
                ridingRecordBean.setTeacher(jsonObject.optString("teacher"));
                ridingRecordBean.setTeacher_phone(jsonObject.optString("teacher_phone"));
                ridingRecordBean.setVehicle_vin(jsonObject.optString("vehicle_vin"));
                ridingRecordBean.setVehicle_plate(jsonObject.optString("vehicle_plate"));

            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取学生乘车信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }


    public RidingRecordBean getRidingRecordBean()
    {
        return ridingRecordBean;
    }

}
