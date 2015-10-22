/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:37:13
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 设置提醒站点或车辆提醒信息请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:37:13
 */
public class SetRemindStationsReq extends AbstractReq
{
    /**
     * 站点提醒信息，具体的字段填充根据不同类型决定
     */
    private List<RemindInfo> remindInfos;

    /**
     * 站点提醒信息，具体的字段填充根据不同类型决定
     */
    public void setRemindInfos(List<RemindInfo> remindInfos)
    {
        this.remindInfos = remindInfos;
    }

    @Override
    public String packetMsgBody()
    {
        try
        {
            if (remindInfos == null)
            {
                return null;
            }
            JSONArray jsonArray = new JSONArray();
            JSONObject req = null;
            for (RemindInfo remind : remindInfos)
            {
                req = new JSONObject();

                req.put("id", remind.getId());
                if (remind.area_type != null)
                {
                    req.put("area_type", remind.area_type.value());
                }

                if (remind.line_range != null)
                {
                    req.put("line_range", remind.line_range.value());
                }

                if (LineRange.FactoryOuter.equals(remind.getLine_range()))
                {
                	 if(null!=remind.status_range)
                    req.put("status_range", remind.status_range.value());

                    if(null!=remind.getRemind_range())
                    req.put("remind_range", remind.getRemind_range().value());

                    if(null!=remind.getRemind_type())
                    req.put("remind_type", remind.getRemind_type().value());
                    if(0!=remind.getRemind_value())
						req.put("remind_value", remind.getRemind_value());
                    if(null!=remind.getRemind_week())
                    req.put("remind_week", remind.getRemind_week());
                    if (remind.getNo_remind_date() != null)
                    {
                        req.put("no_remind_date", DateUtils.dateToStr(remind.getNo_remind_date(), DateUtils.TIME_FORMAT));
                    }
                }

                req.put("station_id", remind.getStation_id());

                if (StringUtil.isNotBlank(remind.getLine_id()))
                {
                    req.put("line_id", remind.getLine_id());
                }
                if (StringUtil.isNotBlank(remind.getLine_name()))
                {
                    req.put("line_name", remind.getLine_name());
                }

                if (StringUtil.isNotBlank(remind.getVehiche_vin()))
                {
                    req.put("vehicle_vin", remind.getVehiche_vin());
                }
                if (StringUtil.isNotBlank(remind.getVehiche_number()))
                {
                    req.put("vehicle_number", remind.getVehiche_number());
                }

                req.put("remind_status", remind.remind_status.value());

                jsonArray.put(req);
            }
            return jsonArray.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[ 设置提醒站点或车辆提醒信息请求信息类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }
}
