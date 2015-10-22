package com.yutong.axxc.parents.connect.http.packet;


/**
 * 获取学生乘车信息请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class GetStudentRedingRecordReq extends AbstractReq {

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
        reuqestUri = reuqestUri + "/msg/riding/" + cld_id;
    }
}
