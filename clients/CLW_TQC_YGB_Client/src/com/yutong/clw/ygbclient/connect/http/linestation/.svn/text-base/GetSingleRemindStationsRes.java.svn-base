/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:36:18
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取单个站点提醒设置响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:36:18
 */
public class GetSingleRemindStationsRes extends AbstractRes
{
    /**
     * 单个站点提醒信息
     */
    private RemindInfo remindInfo;

    @Override
    public boolean parseCorrectMsg(String jsonObject)
    {
        try
        {
            JSONObject res = new JSONObject(jsonObject);
            if (res != null)
            {
                remindInfo = new RemindInfo();
                remindInfo.parse(res);
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取单个站点提醒设置响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }
    }

    /**
     * 站点提醒信息列表
     */
    public RemindInfo getRemindInfo()
    {
        return remindInfo;
    }

}
