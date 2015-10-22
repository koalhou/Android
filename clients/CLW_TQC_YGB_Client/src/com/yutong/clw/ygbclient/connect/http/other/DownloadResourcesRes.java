/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:32:32
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.other;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.ResourceInfo;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 资源获取响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:32:32
 */
public class DownloadResourcesRes extends AbstractRes
{
    // 资源信息实体类
    private ResourceInfo resourceInfo;

    @Override
    public boolean parseCorrectMsg(String jsonObject)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonObject);
            resourceInfo = new ResourceInfo();
            resourceInfo.setResource(res.optString("resource"));
            resourceInfo.suffix = res.optString("suffix");

            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[资源获取响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }
    }

    // 资源信息实体类
    public ResourceInfo getResourceInfo()
    {
        return resourceInfo;
    }

}
