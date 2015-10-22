
package com.yutong.axxc.parents.dao.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  数据访问层学生打卡实体类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class DaoPunchCardDateBean {
    
    private String cld_id;

    private String punchcard_month;

    private String punchCard_day;

    public String getCld_id()
    {
        return cld_id;
    }

    public void setCld_id(String cld_id)
    {
        this.cld_id = cld_id;
    }

    public String getPunchcard_month()
    {
        return punchcard_month;
    }

    public void setPunchcard_month(String punchcard_month)
    {
        this.punchcard_month = punchcard_month;
    }

    public String getPunchCard_day()
    {
        return punchCard_day;
    }

    public void setPunchCard_day(String punchCard_day)
    {
        this.punchCard_day = punchCard_day;
    }


}
