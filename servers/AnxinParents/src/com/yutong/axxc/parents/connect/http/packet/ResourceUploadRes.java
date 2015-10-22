package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.ResourceInfoBean;
import com.yutong.axxc.parents.common.beans.VehicleInfoBean;

/**
 * 资源下载响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class ResourceUploadRes extends AbstractRes
{
    
    private String res_id;
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

            res_id = res.optString("res_id");

            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[资源上传响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }
    public String getRes_id()
    {
        return res_id;
    }


}
