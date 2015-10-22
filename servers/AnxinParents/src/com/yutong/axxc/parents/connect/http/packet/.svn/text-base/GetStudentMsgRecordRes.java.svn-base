package com.yutong.axxc.parents.connect.http.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.MsgRecordBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;

/**
 * 获取学生消息记录响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetStudentMsgRecordRes extends AbstractRes
{

    /** ETag */
    private String ETag;

    /** 学生消息记录 */
    private List<MsgRecordBean> msgRecordBeans;

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

            ETag = res.optString("ETag");

            JSONArray jsonArray = res.getJSONArray("msg_infos");// 获取JSONArray

            if (jsonArray != null)
            {
                msgRecordBeans = new ArrayList<MsgRecordBean>();
                for (int i = 0, length = jsonArray.length(); i < length; i++)
                {// 遍历JSONArray

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject != null)
                    {
                        MsgRecordBean msgRecordBean = new MsgRecordBean();

                        msgRecordBean.setMsg_id(jsonObject.optString("msg_id"));
                        msgRecordBean.setCld_id(jsonObject.optString("cld_id"));
                        msgRecordBean.setRule_id(jsonObject.optString("rule_id"));
                        msgRecordBean.setMsg_content(jsonObject.optString("msg_content"));
                        msgRecordBean.setMsg_time(jsonObject.optString("msg_time"));

                        msgRecordBeans.add(msgRecordBean);
                    }
                }
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取学生消息记录请求类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public String getETag()
    {
        return ETag;
    }

    public List<MsgRecordBean> getMsgRecordBeans()
    {
        return msgRecordBeans;
    }

}
