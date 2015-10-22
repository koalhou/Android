/**
 * @公司名称：YUTONG
 * @作者：houjunhu
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:31:56
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.loginfo;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 用户行为统计上报请求类
 * 
 * @author houjunhu 2013-10-23 上午9:31:56
 */
public class UsageUploadReq extends AbstractReq
{

    /** 客户端唯一标识 */
    private String module_son_id;

    /**
     * @param module_son_id The module_son_id to set.
     */
    public void setModule_son_id(String module_son_id)
    {
        this.module_son_id = module_son_id;
    }

    @Override
    public String packetMsgBody()
    {
        try
        {
            JSONObject req = new JSONObject();
            req.put("module_son_id", module_son_id);
            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(UsageUploadReq.class, "[用户行为统计类]：组包生成用户行为请求时失败，详细信息：", e);
            return null;
        }
    }

}
