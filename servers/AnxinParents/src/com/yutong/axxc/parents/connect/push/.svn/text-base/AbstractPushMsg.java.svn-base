package com.yutong.axxc.parents.connect.push;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 推送内容抽象类
 * 
 * @author zhangzhia 2013-9-3 上午10:36:19
 */
public abstract class AbstractPushMsg
{

    protected String id;

    protected String rule_id;

    /** 用户id */
    protected String usr_id;

    /** 学生id */
    protected String cld_id;

    protected String event_time;

    protected JSONObject contentJsonObj;

    public boolean parse(String msg)
    {
        JSONObject jsonObject;
        try
        {
            jsonObject = new JSONObject(msg);
            id = jsonObject.optString("id");
            rule_id = jsonObject.optString("rule_id");
            usr_id = jsonObject.optString("usr_id");
            cld_id = jsonObject.optString("cld_id");
            event_time = jsonObject.optString("event_time");

            contentJsonObj = jsonObject.optJSONObject("content");

            // parseContent(jsonObject);
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[推送内容抽象类]:解析接受消息出错，详细信息：", e);
            return false;
        }

    }

    /**
     * 解析内容
     * 
     * @param jsonObject
     * @return
     */
    abstract void parseContent(JSONObject jsonObject);

    /**
     * 获取内容值
     * 
     * @author zhangzhia 2013-9-10 上午11:26:46
     * @param key key
     */
    public String getContent(String key)
    {
        try
        {
            String value = null;
            if (contentJsonObj != null)
            {
                value = contentJsonObj.optString(key);

            }

            return value;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 获取内容值Map集合
     * 
     * @author zhangzhia 2013-9-10 上午11:26:46
     */
    public Map<String, String> getContents()
    {
        try
        {
            Map<String, String> map = new HashMap<String, String>();
            if (contentJsonObj != null)
            {
                // value = contentJsonObj.optString(key);
                Iterator<?> it = contentJsonObj.keys();
                while (it.hasNext())
                {
                    String key = (String) it.next();
                    map.put(key, contentJsonObj.getString(key));

                }
            }

            return map;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getRule_id()
    {
        return rule_id;
    }

    public void setRule_id(String rule_id)
    {
        this.rule_id = rule_id;
    }

    public String getUsr_id()
    {
        return usr_id;
    }

    public void setUsr_id(String usr_id)
    {
        this.usr_id = usr_id;
    }

    public String getCld_id()
    {
        return cld_id;
    }

    public void setCld_id(String cld_id)
    {
        this.cld_id = cld_id;
    }

    public String getEvent_time()
    {
        return event_time;
    }

    public void setEvent_time(String event_time)
    {
        this.event_time = event_time;
    }

}
