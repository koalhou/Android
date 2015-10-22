
package com.yutong.axxc.parents.common.beans;

import com.yutong.axxc.parents.common.Tools;

/**
 * 行为日志上报实体类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class UserBehaviorBean {
    /** 模块ID */
    private String  module_id;

    /** 记录时间 */
    private String log_date;

    public String getModule_id()
    {
        return module_id;
    }

    public void setModule_id(String module_id)
    {
        this.module_id = module_id;
    }

    public String getLog_date()
    {
        return log_date;
    }

    public void setLog_date(String log_date)
    {
        this.log_date = log_date;
    }

    public UserBehaviorBean(String module_id)
    {
        this.module_id = module_id;
        this.log_date = Tools.getFullCurrentTime();
    }
    
}
