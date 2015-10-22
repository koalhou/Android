/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:36:38
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 获取车辆信息请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:36:38
 */
public class GetVehichesReq extends AbstractReq
{
    /**
     * 查询时间，格式：yyyymmddhh24miss
     */
    private String search_date;

    private List<String> line_ids;

    /**
     * 查询时间，格式：yyyymmddhh24miss
     */
    public void setSearch_date(String search_date)
    {
        this.search_date = search_date;
    }

    public void setLine_ids(List<String> line_ids)
    {
        this.line_ids = line_ids;
    }

    @Override
    public String packetMsgBody()
    {
        try
        {

            JSONObject req = new JSONObject();
            req.put("search_date", search_date);

            if (line_ids != null && line_ids.size() > 0)
            {
                JSONArray jsonArray = new JSONArray(line_ids);
                req.put("line_ids", jsonArray);
            }

            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取车辆信息请求信息类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }

}
