/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-20 上午9:04:34
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.push;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.MsgPushType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindType;

/**
 * 厂外推送消息实体类
 * 
 * @author zhangzhia 2013-11-20 上午9:04:34
 */
public class FactoryOuterPush extends AbstractPush
{
    public AreaType area_type;

    public String remind_id;

    public RemindRange remind_range;

    public RemindType remind_type;

    public int remind_value;

    public String station_id;

    public String station_name;

    // public String station_alias;

    public StatusRange status_range;
    
    public String line_id;
    
    public String line_name;

    public String vehicle_vin;

    public String vehicle_number;

    public Double vehicle_lon;

    public Double vehicle_lat;

    /**
     * 解析内容
     * 
     * @param jsonObject
     * @return
     */
    boolean parseContent()
    {
        try
        {
            if (!msgPushType.equals(MsgPushType.Unknow) && this.contentJsonObj != null)
            {
                area_type = AreaType.myValueOf(contentJsonObj.optInt("area_type"));

                remind_id = contentJsonObj.optString("remind_id");
                remind_range = RemindRange.myValueOf(contentJsonObj.optInt("remind_range"));
                remind_type = RemindType.myValueOf(contentJsonObj.optInt("remind_type"));
                remind_value = contentJsonObj.optInt("remind_value");

                station_id = contentJsonObj.optString("station_id");
                station_name = contentJsonObj.optString("station_name");
                // station_alias = contentJsonObj.optString("station_alias");

                status_range = StatusRange.myValueOf(contentJsonObj.optInt("status_range"));
                
                line_id =contentJsonObj.optString("line_id");
                line_name =contentJsonObj.optString("line_name");
                
                vehicle_vin = contentJsonObj.optString("vehicle_vin");
                vehicle_number = contentJsonObj.optString("vehicle_number");
                vehicle_lon = contentJsonObj.optDouble("vehicle_lon");
                vehicle_lat = contentJsonObj.optDouble("vehicle_lat");

                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            Logger.e(this.getClass(), "[厂外推送消息实体类]:解析接受消息出错，详细信息：", e);
            return false;
        }
    }

}
