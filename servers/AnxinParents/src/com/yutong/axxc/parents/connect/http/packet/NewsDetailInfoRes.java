package com.yutong.axxc.parents.connect.http.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.NewsInfoBean;

/**
 * 新闻详细信息响应类
 * 
 * @author zhangzhia 2013-9-9 下午5:16:04
 */
public class NewsDetailInfoRes extends AbstractRes
{

    private NewsInfoBean newsInfoBean = null;

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
            JSONObject obj = res.getJSONObject("new");
            if (obj != null)
            {
                newsInfoBean = new NewsInfoBean();
                newsInfoBean.setNews_id(obj.optString("news_id"));
                newsInfoBean.setNews_type(obj.optString("news_type"));
                newsInfoBean.setNews_title(obj.optString("news_title"));
                newsInfoBean.setNews_summary(obj.optString("news_summary"));
                newsInfoBean.setNews_time(obj.optString("news_time"));

                newsInfoBean.setNews_author(obj.optString("news_author"));
                newsInfoBean.setNews_image(obj.optString("news_image_id"));
                newsInfoBean.setNews_image_url(obj.optString("news_image_url"));
                newsInfoBean.setNews_content(obj.optString("news_content"));
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(NewsDetailInfoRes.class, "[新闻详细信息响应类]:解析响应消息出错，详细信息：", e);
            return false;
        }

    }

    public NewsInfoBean getNewsInfoBean()
    {
        return newsInfoBean;
    }

  

}
