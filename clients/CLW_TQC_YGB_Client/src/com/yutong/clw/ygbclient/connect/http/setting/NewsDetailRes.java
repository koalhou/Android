/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:34:02
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.setting;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.PushMsgRule;
import com.yutong.clw.ygbclient.common.beans.news.NewsInfo;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取新闻详细响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:34:02
 */
public class NewsDetailRes extends AbstractRes
{
    // 新闻实体集合
    private NewsInfo newsInfo;

    @Override
    public boolean parseCorrectMsg(String jsonObject)
    {
        try
        {
            JSONObject res = new JSONObject(jsonObject);
            newsInfo = new NewsInfo();
            newsInfo.parse(res);
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[ 获取新闻详细响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }
    }

    // 新闻实体集合
    public NewsInfo getNewsInfo()
    {
        return newsInfo;
    }

}
