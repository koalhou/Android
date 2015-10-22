/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:34:12
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.setting;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.news.NewsParam;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 获取新闻概要请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:34:12
 */
public class NewSummaryReq extends AbstractReq
{
    // 新闻摘要实体
    private NewsParam newsParam;

    @Override
    public String packetMsgBody()
    {
        try
        {
            JSONObject req = new JSONObject();
            if (newsParam.getNewsType() != null)
            {
                req.put("news_type", newsParam.getNewsType().value());
            }
            req.put("page_flag", newsParam.getPageFlag().value());
            req.put("page_time", newsParam.getPageTime());
            req.put("req_num", newsParam.getReq_num());
            req.put("last_id", newsParam.getLast_id());
            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[ 获取新闻概要请求类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }

    // 新闻摘要实体
    public void setNewsParam(NewsParam newsParam)
    {
        this.newsParam = newsParam;
    }

}
