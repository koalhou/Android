/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:34:02
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.setting;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.AdviseReply;
import com.yutong.clw.ygbclient.common.beans.news.NewsInfo;
import com.yutong.clw.ygbclient.common.beans.news.NewsReturnInfo;
import com.yutong.clw.ygbclient.common.enums.news.NewsType;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;
import com.yutong.clw.ygbclient.view.bean.push.FeedBackPushBean;

/**
 * 获取新闻详细响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:34:02
 */
public class AdviseReplyRes extends AbstractRes{
	
    public List<FeedBackPushBean> feedBackPushBeanList = new ArrayList<FeedBackPushBean>();
    
    @Override
    public boolean parseCorrectMsg(String jsonObject)
    {
        try
        {
        	FeedBackPushBean feedBackPushBean = null;
            JSONObject tempAdviseReplyJson = null;
            
            JSONArray contentJsonArray = new JSONArray(jsonObject);
            int len = contentJsonArray.length();
            if (contentJsonArray != null && len > 0){
            	
                for (int i = 0; i < len; i++){
                	
                	
                	tempAdviseReplyJson = contentJsonArray.optJSONObject(i);
                	feedBackPushBean = new FeedBackPushBean();
                	
                	feedBackPushBean.msgID = tempAdviseReplyJson.optString("msg_id");
                	feedBackPushBean.replyTime = tempAdviseReplyJson.optString("reply_time");
                	feedBackPushBean.reply = tempAdviseReplyJson.optString("reply");
                	feedBackPushBean.adviseTime = tempAdviseReplyJson.optString("advise_time");
                	feedBackPushBean.advise = tempAdviseReplyJson.optString("advise");
                   
                	if(!StringUtil.isEmpty(feedBackPushBean.msgID)){
                		feedBackPushBeanList.add(feedBackPushBean);
                	}
                }
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[ 获取新闻详细响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }
    }

	public List<FeedBackPushBean> getFeedBackPushBeanList() {
		return feedBackPushBeanList;
	}
}
