/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:33:15
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.other;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.ResourceInfo;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 资源上传请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:33:15
 * 
 */
public class UploadResourcesReq extends AbstractReq
{
	// 资源信息实体类
	private ResourceInfo resourceInfo;

	@Override
	public String packetMsgBody()
	{
		try
		{
			JSONObject req = new JSONObject();
			
			JSONObject res_desc = new JSONObject();
			res_desc.put("platform", resourceInfo.platform);
			res_desc.put("suffix", resourceInfo.suffix);
			res_desc.put("size", resourceInfo.size);
			req.put("res_desc", res_desc);
			
			req.put("resource", resourceInfo.getResource());
			return req.toString();
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[资源获取请求信息类]:组包生成资源获取请求消息时失败，详细信息：", e);
			return null;
		}

	}

	// 资源信息实体类
	public void setResourceInfo(ResourceInfo resourceInfo)
	{
		this.resourceInfo = resourceInfo;
	}
}
