package com.yutong.axxc.parents.dao.beans;

/**
 * 数据访问层推送规则信息实体类
 * @author zhangzhia 2013-9-3 下午6:18:41
 *
 */
public class DaoPushMsgRuleBean
{
    private String id;

    private String usr_id;

    private String cld_id;

    private String rule_id;

    private String rule_title;
    
    private String rule_desc;
    
    private String flag;

    private String on_off;

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
    
    
}
