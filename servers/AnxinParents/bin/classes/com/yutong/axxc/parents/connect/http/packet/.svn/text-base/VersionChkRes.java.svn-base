
package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;


/**
 * 版本检查响应类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class VersionChkRes extends AbstractRes {

    /** 目标软件版本号 */
    private String targetVersion;

    /** 软件升级描述 */
    private String changeDesc;

    /** 是否强制性更新  0：否，1：是 */
    private boolean force;
    
    /** 软件地址信息 */
    private String uri;

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString) {
        JSONObject res;
        try {
            res = new JSONObject(jsonString);
            targetVersion = res.optString("target_version");
            changeDesc = res.optString("change_desc");
            force = res.optBoolean("force");
            uri = res.optString("uri");
            return true;
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[版本检查响应类]:解析版本检查响应消息出错，详细信息：", e);
            return false;
        }
    }

    /**
     * @return Returns the targetVersion.
     */
    public String getTargetVersion() {
        return targetVersion;
    }

    /**
     * @return Returns the changeDesc.
     */
    public String getChangeDesc() {
        return changeDesc;
    }

    /**
     * @return Returns the uri.
     */
    public String getUri() {
        return uri;
    }

    public boolean isForce()
    {
        return force;
    }

    
}
