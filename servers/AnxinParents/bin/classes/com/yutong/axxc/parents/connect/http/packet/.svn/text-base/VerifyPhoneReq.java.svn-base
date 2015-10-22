package com.yutong.axxc.parents.connect.http.packet;


/**
 * 手机验证请求
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class VerifyPhoneReq extends AbstractReq {

    private String usr_phone;


    public void setUsr_phone(String usr_phone)
    {
        this.usr_phone = usr_phone;
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
        reuqestUri = reuqestUri + "/account/phone/" + this.usr_phone;
    }
}
