/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午8:20:31
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

import com.yutong.clw.ygbclient.common.Logger;

/**
 * 资源信息实体类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class ResourceInfo
{

    public String res_id;

    /**
     * 归属平台(AnxiParents：安芯家长版)
     */
    public String platform;

    /** 后缀名，如png */
    public String suffix;

    /** 资源字节大小，单位字节 */
    public String size;

    /** 资源base64位编码串 */
    private String resource;
    
    /**
     * 资源byte[]
     */
    private byte[] bytes;

//    public String resBytesToString(byte[] resourceByte)
//    {
//        return Base64.encodeToString(resourceByte, Base64.DEFAULT);
//    }
//
//    public byte[] getResourceBytes()
//    {
//        return Base64.decode(resource, Base64.DEFAULT);
//    }
    
    public void setResource(String resource)
    {
        this.resource = resource;
        bytes = Base64.decode(resource, Base64.DEFAULT);
    }

    public void setBytes(byte[] bytes)
    {
        this.bytes = bytes;
        resource = Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    
    public String getResource()
    {
        return resource;
    }

    public byte[] getBytes()
    {
        return bytes;
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
