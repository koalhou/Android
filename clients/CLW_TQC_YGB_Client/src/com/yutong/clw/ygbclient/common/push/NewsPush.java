/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-20 上午9:03:49
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.push;

import java.util.Date;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.MsgPushType;
import com.yutong.clw.ygbclient.common.enums.news.NewsType;
import com.yutong.clw.ygbclient.common.utils.DateUtils;

/**
 * 新闻推送消息实体类
 * @author zhangzhia 2013-11-20 上午9:03:49
 */
public class NewsPush extends AbstractPush
{
    public NewsType news_type;

    public String news_id;

    public String news_title;

    public String news_summary;

    public Date news_time;

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
                news_type = NewsType.myValueOf(contentJsonObj.optInt("news_type"));

                news_id = contentJsonObj.optString("news_id");
                news_title = contentJsonObj.optString("news_title");
                news_summary = contentJsonObj.optString("news_summary");
                news_time = DateUtils.strToDate(contentJsonObj.optString("news_time"), "yyyyMMddHHmmss");

                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            Logger.e(this.getClass(), "[新闻推送消息实体类]:解析接受消息出错，详细信息：", e);
            return false;
        }
    }

}
