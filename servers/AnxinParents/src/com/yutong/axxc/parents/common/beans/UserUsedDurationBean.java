
package com.yutong.axxc.parents.common.beans;

/**
 * 用户使用时长日志上报实体类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class UserUsedDurationBean {

    /** 使用开始时间。格式:yyyymmddhh24miss */
    private String start_time;
    
    /** 使用结束时间。格式:yyyymmddhh24miss */
    private String end_time;

    public String getStart_time()
    {
        return start_time;
    }

    public void setStart_time(String start_time)
    {
        this.start_time = start_time;
    }

    public String getEnd_time()
    {
        return end_time;
    }

    public void setEnd_time(String end_time)
    {
        this.end_time = end_time;
    }
  
    
}
