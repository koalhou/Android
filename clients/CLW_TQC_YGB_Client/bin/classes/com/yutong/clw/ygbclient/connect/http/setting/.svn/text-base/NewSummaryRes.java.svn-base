/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:34:17
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.setting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.news.NewsInfo;
import com.yutong.clw.ygbclient.common.beans.news.NewsReturnInfo;
import com.yutong.clw.ygbclient.common.enums.news.HasNextStatus;
import com.yutong.clw.ygbclient.common.enums.news.NewsType;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取新闻概要响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:34:17
 */
public class NewSummaryRes extends AbstractRes
{
    // 返回的新闻实体
    private NewsReturnInfo newsReturnInfo = new NewsReturnInfo();

    @Override
    public boolean parseCorrectMsg(String jsonObject)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonObject);
            newsReturnInfo.timestamp = DateUtils.strToDate(res.optString("timestamp"), "yyyyMMddHHmmss");
            newsReturnInfo.hasnext = HasNextStatus.myValueOf(res.optInt("hasnext"));
            newsReturnInfo.totalnum = res.optInt("totalnum");
            // 新闻信息集合
            JSONArray tempNewsInfoJson = res.optJSONArray("infos");
            JSONObject tempNewsJson = null;
            List<NewsInfo> newsInfos = new ArrayList<NewsInfo>();
            NewsInfo newsInfo = null;
            if (tempNewsInfoJson != null && tempNewsInfoJson.length() > 0)
            {
                for (int i = 0; i < tempNewsInfoJson.length(); i++)
                {
                    tempNewsJson = tempNewsInfoJson.optJSONObject(i);
                    newsInfo = new NewsInfo();
                    newsInfo.id = tempNewsJson.optString("id");
                    newsInfo.title = tempNewsJson.optString("title");
                    newsInfo.summary = tempNewsJson.optString("summary");
                    newsInfo.publish_time = DateUtils.strToDate(tempNewsJson.optString("publish_time"), DateUtils.LONG_TIME_FORMAT);
                    newsInfo.type = NewsType.myValueOf(tempNewsJson.optInt("type"));
                    newsInfos.add(newsInfo);
                }
                newsReturnInfo.infos = newsInfos;
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取新闻概要响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }
    }

    // 返回的新闻实体
    public NewsReturnInfo getNewsReturnInfo()
    {
        return newsReturnInfo;
    }

}
