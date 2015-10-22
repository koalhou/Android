package com.yutong.clw.ygbclient.connect.http.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.VersionInfo;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 版本检查响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class VersionChkRes extends AbstractRes
{

    /** 软件版本信息 */

    private VersionInfo versionInfo;

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    public boolean parseCorrectMsg(String jsonString)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonString);
            String changeDesc = res.optString("change_desc");
            int force = res.optInt("force");
            String uri = res.optString("uri");
            String version_name = res.optString("version_name");
            int version_code = res.optInt("version_code");
            // 组装版本信息
            versionInfo = new VersionInfo();
            versionInfo.setChange_desc(changeDesc);
            versionInfo.setForce(DataTypeConvertUtils.int2Boolean(force));
            versionInfo.setUri(uri);
            versionInfo.setVersion_name(version_name);
            versionInfo.setVersion_code(version_code);
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[版本检查响应类]:解析版本检查响应消息出错，详细信息：", e);
            return false;
        }
    }

    /** 软件版本信息 */
    public VersionInfo getVersionInfo()
    {
        return versionInfo;
    }

}
