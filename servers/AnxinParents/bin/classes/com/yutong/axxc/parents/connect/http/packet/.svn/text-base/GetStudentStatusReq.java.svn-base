package com.yutong.axxc.parents.connect.http.packet;


/**
 * 得到学生最新状态请求
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class GetStudentStatusReq extends AbstractReq {

    private String cld_id;

    public void setCld_id(String cld_id)
    {
        this.cld_id = cld_id;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        return null;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/child/status/" + this.cld_id;
    }
}
