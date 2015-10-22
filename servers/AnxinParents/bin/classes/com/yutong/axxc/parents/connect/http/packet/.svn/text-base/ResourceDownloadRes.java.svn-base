package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.ResourceInfoBean;
import com.yutong.axxc.parents.common.beans.VehicleInfoBean;

/**
 * 资源下载响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class ResourceDownloadRes extends AbstractRes
{
    /** 资源信息 */
    private ResourceInfoBean resourceInfoBean;

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonString);

            resourceInfoBean = new ResourceInfoBean();

            resourceInfoBean.setSuffix(res.optString("suffix"));
            resourceInfoBean.setResource(res.optString("resource"));

            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[资源下载响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public ResourceInfoBean getResourceInfoBean()
    {
        return resourceInfoBean;
    }

}
