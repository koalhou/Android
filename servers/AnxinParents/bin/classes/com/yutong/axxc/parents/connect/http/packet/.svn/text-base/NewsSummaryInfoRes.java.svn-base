package com.yutong.axxc.parents.connect.http.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.NewsInfoBean;

/**
 * 新闻概要信息响应类
 * 
 * @author zhangzhia 2013-9-9 下午5:16:04
 */
public class NewsSummaryInfoRes extends AbstractRes
{

    private List<NewsInfoBean> newsInfoBeans = new ArrayList<NewsInfoBean>();

    /**
     * (non-Javadoc)
     * 
     * @see com.neusoft.yt.connect.http.packet.AbstractRes#parse(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonString);
            JSONArray news = res.optJSONArray("news");
            if (news != null && news.length() > 0)
            {
                for (int i = 0, length = news.length(); i < length; i++)
                {
                    NewsInfoBean newsInfoBean = new NewsInfoBean();
                    JSONObject obj = news.getJSONObject(i);

                    newsInfoBean.setNews_id(obj.optString("news_id"));
                    newsInfoBean.setNews_type(obj.optString("news_type"));
                    newsInfoBean.setNews_title(obj.optString("news_title"));
                    newsInfoBean.setNews_summary(obj.optString("news_summary"));
                    newsInfoBean.setNews_time(obj.optString("news_time"));

                    newsInfoBeans.add(newsInfoBean);
                }
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(NewsSummaryInfoRes.class, "[新闻概要信息响应类]:解析响应消息出错，详细信息：", e);
            return false;
        }

    }

    public List<NewsInfoBean> getNewsInfoBeans()
    {
        return newsInfoBeans;
    }

}
