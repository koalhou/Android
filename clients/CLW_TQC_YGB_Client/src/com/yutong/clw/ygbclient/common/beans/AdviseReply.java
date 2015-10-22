/**
 * @公司名称：YUTONG
 * @作者：houjunhu
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:08:36
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.news.NewsInfo;
import com.yutong.clw.ygbclient.common.enums.news.NewsType;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;

/**
 * 新闻信息实体类
 * 
 * @author houjunhu 2013-9-9 下午3:37:09
 */
public final class AdviseReply implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 3670826550506029100L;


    /** 消息ID */
    public String msg_id;

    public String content;

    /** 消息标题 */
    public String advise_time;

    /** 消息概述信息 */
    public String reply;

    /** 消息发布者 */
    public String reply_time;

    
    
}
