/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:29:55
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.push;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.MsgPushType;
import com.yutong.clw.ygbclient.common.push.AbstractPush;
import com.yutong.clw.ygbclient.common.push.FactoryInsidePush;
import com.yutong.clw.ygbclient.common.push.FactoryOuterPush;
import com.yutong.clw.ygbclient.common.push.NewsPush;

/**
 * 推送内容解析类
 * 
 * @author zhangzhia 2013-9-3 上午10:36:19
 */
public class PushMsgExpression
{
    public static MsgPushType getMsgPushType(String pushJsonString)
    {
        JSONObject jsonObject;
        try
        {
            jsonObject = new JSONObject(pushJsonString);
            String rule_id = jsonObject.optString("rule_id");

            return MsgPushType.myNameOf(rule_id);
        }
        catch (JSONException e)
        {
            Logger.e(PushMsgExpression.class, "[推送内容解析类]:解析接受消息出错，详细信息：", e);
            return MsgPushType.Unknow;
        }

    }

//    public static AbstractPush paser(String pushJsonString)
//    {
//        AbstractPush msgPush = null;
//        MsgPushType msgPushType = PushMsgExpression.getMsgPushType(pushJsonString);
//
//        switch(msgPushType)
//        {
//        case News:
//            msgPush = new NewsPush();
//            break;
//        case FactoryInside:
//            msgPush = new FactoryInsidePush();
//            break;
//        case FactoryOuter:
//            msgPush = new FactoryOuterPush();
//            break;
//        default:
//            //Unknow
//            break;
//        }
//        
//        if(msgPush != null && !msgPush.parse(pushJsonString));
//        {
//            return msgPush;
//        }
////        else
////        {
////            return null;
////        }
//        
//    }
    /**
     * 解析内容
     * 
     * @param jsonObject
     * @return
     */
    // public abstract boolean parseContent();

    // /**
    // * 获取内容值
    // *
    // * @author zhangzhia 2013-9-10 上午11:26:46
    // * @param key key
    // */
    // public String getContent(String key)
    // {
    // try
    // {
    // String value = null;
    // if (contentJsonObj != null)
    // {
    // value = contentJsonObj.optString(key);
    //
    // }
    //
    // return value;
    // }
    // catch (Exception e)
    // {
    // return null;
    // }
    // }
    //
    // /**
    // * 获取内容值Map集合
    // *
    // * @author zhangzhia 2013-9-10 上午11:26:46
    // */
    // public Map<String, String> getContents()
    // {
    // try
    // {
    // Map<String, String> map = new HashMap<String, String>();
    // if (contentJsonObj != null)
    // {
    // // value = contentJsonObj.optString(key);
    // Iterator<?> it = contentJsonObj.keys();
    // while (it.hasNext())
    // {
    // String key = (String) it.next();
    // map.put(key, contentJsonObj.getString(key));
    //
    // }
    // }
    //
    // return map;
    // }
    // catch (Exception e)
    // {
    // return null;
    // }
    // }

    // public String getId()
    // {
    // return id;
    // }
    //
    // public void setId(String id)
    // {
    // this.id = id;
    // }
    //
    // public String getRule_id()
    // {
    // return rule_id;
    // }
    //
    // public void setRule_id(String rule_id)
    // {
    // this.rule_id = rule_id;
    // }
    //
    // public String getUser_code()
    // {
    // return user_code;
    // }
    //
    // public void setUser_code(String user_code)
    // {
    // this.user_code = user_code;
    // }
    //
    // public String getEvent_time()
    // {
    // return event_time;
    // }
    //
    // public void setEvent_time(String event_time)
    // {
    // this.event_time = event_time;
    // }

}
