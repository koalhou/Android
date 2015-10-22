/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:08:36
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans.news;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.news.HasNextStatus;
import com.yutong.clw.ygbclient.common.enums.news.NewsType;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;

/**
 * 新闻信息实体类
 * 
 * @author zhangzhia 2013-9-9 下午3:37:09
 */
public final class NewsInfo implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 3670826550506029100L;

    // /**
    // * 服务器当前时间
    // */
    // public Date timestamp;
    //
    // /**
    // * 下翻是否还有数据
    // */
    // public HasNextStatus hasnext;
    //
    // /**
    // * 总条数
    // */
    // public int totalnum;

    /** 消息ID */
    public String id;

    public NewsType type;

    /** 消息标题 */
    public String title;

    /** 消息概述信息 */
    public String summary;

    /** 消息发布者 */
    public String author;

    /** 消息图片 res_id */
    public List<String> image_res;

    /** 消息内容信息 */
    public String content;

    /** 消息发布时间 */
    public Date publish_time;

    /** 是否已读 */
    public boolean is_read;

    public void parse(JSONObject optJSONObject)
    {

        try
        {
            if (StringUtil.isNotBlank(optJSONObject.optString("type")))
            {
                type = NewsType.myValueOf(optJSONObject.optInt("type"));
            }
            id = optJSONObject.optString("id");
            title = optJSONObject.optString("title");
            summary = optJSONObject.optString("summary");
            author = optJSONObject.optString("author");
            if (StringUtil.isNotBlank(optJSONObject.optString("publish_time")))
            {
                publish_time = DateUtils.strToDate(optJSONObject.optString("publish_time"), "yyyyMMddHHmmss");
            }
            if (StringUtil.isNotBlank(optJSONObject.optString("image_res")))
            {
                /** 消息图片 res_id */
                JSONArray optJSONArray = optJSONObject.optJSONArray("image_res");
                JSONObject optJSONObject2 = null;
                image_res = new ArrayList<String>();
                NewsInfo newsInfo = null;
                for (int i = 0; i < optJSONArray.length(); i++)
                {
                    image_res.add(optJSONArray.getString(i));

                }
                //image_res.add("1");
                //image_res.add("http://www.yutong.com/images/news/yutongnews/2013/10/28/BDC8D5453EDB992BC0B375DF73D8A001.jpg");
            }
            // if (StringUtil.isNotBlank(optJSONObject.optString("image_url")))
            // {
            // /** 消息图片 */
            // JSONArray optJSONArray1 =
            // optJSONObject.optJSONArray("image_url");
            // image_url = new ArrayList<String>();
            // for (int i = 0; i < optJSONArray1.length(); i++)
            // {
            // JSONObject temp = optJSONArray1.optJSONObject(i);
            // if (temp == null)
            // {
            // continue;
            // }
            // image_url.add(optJSONArray1.getString(i));
            //
            // }
            //
            //
            // }
            content = optJSONObject.optString("content");
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[ 获取新闻详细响应类]:解析 响应消息出错，详细信息：", e);
            e.printStackTrace();
        }

    }
}
