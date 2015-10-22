/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:31:42
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.loginfo;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.EnvironmentInfo;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 异常信息收集上报请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:31:42
 */
public class ExceptionInfoReq extends AbstractReq
{
    // 系统环境信息
    private EnvironmentInfo env_info;

    // 错误类型
    private String error_code;

    // 错误描述信息
    private String error_desc;

    // 错误日期
    private String error_time;

    // 系统环境信息
    public void setEnv_info(EnvironmentInfo env_info)
    {
        this.env_info = env_info;
    }

    // 错误描述信息
    public void setError_desc(String error_desc)
    {
        this.error_desc = error_desc;
    }

    // 错误日期
    public void setError_time(String error_time)
    {
        this.error_time = error_time;
    }
    
    public void setError_code(String error_code)
    {
        this.error_code = error_code;
    }

    @Override
    public String packetMsgBody()
    {
        try
        {
            JSONObject req = new JSONObject();
            JSONObject env_info_req = new JSONObject();
            env_info_req.put("app_version", env_info.app_version);
            env_info_req.put("os_name", env_info.os_name);
            env_info_req.put("os_version", env_info.os_version);

            req.put("env_info", env_info_req);
            req.put("error_code", error_code);
            req.put("error_desc", error_desc);
            req.put("error_time", error_time);

            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[异常信息收集上报请求类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }
}
