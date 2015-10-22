package com.yutong.axxc.parents.dao.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据访问层信息实体类
 * 
 * @author zhangzhia 2013-9-3 下午6:18:27
 */
public class DaoStudentMsgRecordBean
{
    private String msg_id;

    private String cld_id;

    private String rule_id;

    private String msg_content;

    private String msg_time;

    private String msg_date;

    private Date msg_datetime;

    public String getMsg_id()
    {
        return msg_id;
    }

    public void setMsg_id(String msg_id)
    {
        this.msg_id = msg_id;
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

    public String getMsg_content()
    {
        return msg_content;
    }

    public void setMsg_content(String msg_content)
    {
        this.msg_content = msg_content;
    }

    public String getMsg_time()
    {
        return msg_time;
    }

    public void setMsg_time(String msg_time)
    {
        this.msg_time = msg_time;
    }

    public String getMsg_date()
    {
        return msg_date;
    }

    public void setMsg_date(String msg_date)
    {
        this.msg_date = msg_date;
    }

    public Date getMsg_datetime()
    {
        SimpleDateFormat sdf = null;
        Date date = null;
        sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        try
        {
            date = sdf.parse(msg_time);
            return date;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
