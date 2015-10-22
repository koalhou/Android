/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:08:36
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;

/**
 * 客户端信息实体类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class ClientInfo
{

    // client_info:{
    // "app_version":"v1.0.1-130424","os_name":"android"," os_version":"4.3.3"}

    /** 客户端版本 */
    public String app_version;

    /** 手机系统名称 */
    public String os_name = "android";

    /** 手机系统版本 */
    public String os_version;

    public String getJsonString()
    {
        try
        {
            JSONObject jsonobj = new JSONObject();

            jsonobj.put("app_version", app_version);

            jsonobj.put("os_name", os_name);
            jsonobj.put("os_version", os_version);

            return jsonobj.toString();
        }
        catch (JSONException e)
        {
            Logger.i(this.getClass(), "[客户端信息实体类]" + "生成json失败");
            return "";
        }
    }

    public String getApp_version()
    {
        return app_version;
    }

    public void setApp_version(String app_version)
    {
        this.app_version = app_version;
    }

    public String getOs_name()
    {
        return os_name;
    }

    public void setOs_name(String os_name)
    {
        this.os_name = os_name;
    }

    public String getOs_version()
    {
        return os_version;
    }

    public void setOs_version(String os_version)
    {
        this.os_version = os_version;
    }

}
