package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.ResourceInfoBean;


/**
 * 资源上传请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class ResourceUploadReq extends AbstractReq {

    /** 资源信息 */
    private ResourceInfoBean resourceInfoBean;
    
    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try {
            JSONObject req = new JSONObject();
            
            JSONObject res_descObject = new JSONObject();

            res_descObject.put("platform", resourceInfoBean.getPlatform());
            res_descObject.put("suffix", resourceInfoBean.getSuffix());
            res_descObject.put("size", resourceInfoBean.getSize());
            
            req.put("resource", resourceInfoBean.getResource());
            req.put("res_desc", res_descObject);
//            
//            req.put("resource", "dsd");
//            String ss = req.toString();
            

            return req.toString();
        } catch (JSONException e) {
            Logger.e(PasswordUpdateReq.class, "[资源上传请求类]:组包请求时失败，详细信息：", e);
            return null;
        }
    }


    public void setResourceInfoBean(ResourceInfoBean resourceInfoBean)
    {
        this.resourceInfoBean = resourceInfoBean;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/resources";
    }
}
