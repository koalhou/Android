package com.yutong.axxc.parents.common.beans;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 推送消息规则内容类
 * 
 * @author zhangzhia 2013-9-3 下午4:10:32
 */
public class PushMsgRuleBean
{

    /** 唯一id  */
    private String id;
    /** 用户id */
    private String usr_id;
    /** 学生id  */
    private String cld_id;
    /** 规则id  */
    private String rule_id;
    /** 规则标题  */
    private String rule_title;
    /** 规则描述  */
    private String rule_desc;
    /** 是否显示 0：不显示，1：显示  */
    private String flag;
    /** 是否推送，0：关闭，1：开启  */
    private String on_off;

    /**
     * 组包生成推送消息规则内容
     * 
     * @return
     */
    public JSONObject packaged()
    {
        try
        {
            JSONObject content = new JSONObject();
            content.put("id", id);
            content.put("cld_id", cld_id);
            content.put("rule_id", rule_id);
            content.put("on_off", on_off);
  
            return content;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[推送消息规则内容类]:组包生成推送消息规则内容时失败，详细信息：", e);
            return null;
        }
    }

    public void parse(JSONObject jsonObj)
    {
        //Logger.d(this.getClass(), "[推送消息规则内容类]：内容：", jsonObj);

        id = jsonObj.optString("id");
        usr_id = jsonObj.optString("usr_id");
        cld_id = jsonObj.optString("cld_id");
        rule_id = jsonObj.optString("rule_id");
        rule_title = jsonObj.optString("rule_title");
        rule_desc = jsonObj.optString("rule_desc");
        flag = jsonObj.optString("flag");
        on_off = jsonObj.optString("on_off");

    }

    /**
     * @param object
     * @return
     * @see java.lang.String#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PushMsgRuleBean other = (PushMsgRuleBean) obj;
        return new EqualsBuilder().append(rule_id, other.rule_id).append(on_off, other.on_off).append(flag, other.flag).isEquals();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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

    public String getRule_id()
    {
        return rule_id;
    }

    public void setRule_id(String rule_id)
    {
        this.rule_id = rule_id;
    }

    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    public String getOn_off()
    {
        return on_off;
    }

    public void setOn_off(String on_off)
    {
        this.on_off = on_off;
    }

    public String getRule_title()
    {
        return rule_title;
    }

    public void setRule_title(String rule_title)
    {
        this.rule_title = rule_title;
    }

    public String getRule_desc()
    {
        return rule_desc;
    }

    public void setRule_desc(String rule_desc)
    {
        this.rule_desc = rule_desc;
    }

    
}
