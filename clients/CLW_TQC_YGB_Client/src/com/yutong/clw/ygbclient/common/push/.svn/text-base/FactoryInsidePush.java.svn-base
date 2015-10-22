/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-20 上午9:04:17
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.push;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.MsgPushType;
import com.yutong.clw.ygbclient.connect.push.PushMsgExpression;

/**
 * 厂内推送消息实体类
 * @author zhangzhia 2013-11-20 上午9:04:17
 */
public class FactoryInsidePush extends AbstractPush
{
	 public String remind_id;
	 
    public AreaType area_type;

    public String station_id;

    public String station_name;

    public String station_alias;
    
    public String line_id;

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
                station_id = contentJsonObj.optString("station_id");
                station_name = contentJsonObj.optString("station_name");
                station_alias = contentJsonObj.optString("station_alias");
                
                line_id = contentJsonObj.optString("line_id");
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
            Logger.e(this.getClass(), "[厂内推送消息实体类]:解析接受消息出错，详细信息：", e);
            return false;
        }
    }
}
