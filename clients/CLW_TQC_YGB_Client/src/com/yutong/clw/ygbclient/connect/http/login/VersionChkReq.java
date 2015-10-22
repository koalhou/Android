package com.yutong.clw.ygbclient.connect.http.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 版本检查请求类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class VersionChkReq extends AbstractReq
{
    /**
     * 客户端软件版本名
     */
    private String version_name;

    /**
     * 客户端软件版本号
     */
    private int version_code;

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody()
    {
        try
        {
            JSONObject req = new JSONObject();
            req.put("version_name", version_name);
            req.put("version_code", version_code);
            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[版本检查请求类]:组包生成版本检查请求消息时失败，详细信息：", e);
            return null;
        }
    }

    /**
     * 客户端软件版本名
     */
    public void setVersion_name(String version_name)
    {
        this.version_name = version_name;
    }

    /**
     * 客户端软件版本号
     */
    public void setVersion_code(int version_code)
    {
        this.version_code = version_code;
    }

}
