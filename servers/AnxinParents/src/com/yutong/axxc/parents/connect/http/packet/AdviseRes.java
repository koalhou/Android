package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.UserInfoBean;

/**
 * 问题反馈响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class AdviseRes extends AbstractRes
{

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString)
    {
       return true;

    }

}
