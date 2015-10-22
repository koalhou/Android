package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.StudentCustomInfoBean;

/**
 * 设置学生个性信息请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class SetStudentCustomInfoReq extends AbstractReq {

    private StudentCustomInfoBean studentCustomInfoBean;
    
    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try
        {
            JSONObject req = new JSONObject();


            JSONObject child_custom_info = new JSONObject();
            if(studentCustomInfoBean != null)
            {
                child_custom_info.put("cld_id", studentCustomInfoBean.getCld_id());
                child_custom_info.put("cld_alias", studentCustomInfoBean.getCld_alias());
                child_custom_info.put("cld_color", studentCustomInfoBean.getCld_color());
                child_custom_info.put("cld_photo", studentCustomInfoBean.getCld_photo());
                child_custom_info.put("cld_bg", studentCustomInfoBean.getCld_bg());
                child_custom_info.put("audio", studentCustomInfoBean.getCld_audio());
            }
            
            req.put("child_custom_info", child_custom_info);

            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[设置学生个性信息请求类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }

    }

    public void setStudentCustomInfoBean(StudentCustomInfoBean studentCustomInfoBean)
    {
        this.studentCustomInfoBean = studentCustomInfoBean;
    }




    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/child/custom";
    }
}
