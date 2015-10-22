
package com.yutong.axxc.parents.common.beans;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

import com.yutong.axxc.parents.common.Logger;

/**
 * 资源信息实体类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class ResourceInfoBean {

    private String res_id;

    /**
     * 归属平台(AnxiParents：安芯家长版)
     */
    private String platform;
    /**  后缀名，如png */
    private String suffix;
    /**  资源字节大小，单位字节 */
    private String size;

    /** 资源base64位编码串  */
    private String resource;
 
    public String getRes_id()
    {
        return res_id;
    }

    public void setRes_id(String res_id)
    {
        this.res_id = res_id;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public String getResource()
    {
        return resource;
    }

    public void setResource(String resource)
    {
        this.resource = resource;
    }

    public String resBytesToString(byte[] resourceByte)
    {
        return Base64.encodeToString(resourceByte,Base64.DEFAULT);
    }
    
    public byte[] getResourceBytes()
    {
        return Base64.decode(resource, Base64.DEFAULT);
    }
    
    public void parseJsonString(String jsonString)
    {
        JSONObject jsonObj;
        try
        {
            jsonObj = new JSONObject(jsonString);

            suffix = jsonObj.optString("suffix");
            resource = jsonObj.optString("resource");

        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[资源信息类]:解析 资源文件Json串出错，详细信息：", e);
        }

    }
    
    public String toJsonString()
    {
        JSONObject jsonObj;
        try
        {
            jsonObj = new JSONObject();
            
            jsonObj.put("suffix", suffix);
            jsonObj.put("resource", resource);

            return jsonObj.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[资源信息类]:组成 资源文件Json串出错，详细信息：", e);
            return null;
        }

    }
 
    
    
}
