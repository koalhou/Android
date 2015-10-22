package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.Tools;

/**
 * 获取学生个性信息请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class GetStudentCustomInfoReq extends AbstractReq {

    private String cld_id;
    
    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        return null;
    }

    
    public void setCld_id(String cld_id)
    {
        this.cld_id = cld_id;
    }


    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/child/custom/" + cld_id;
    }
}
