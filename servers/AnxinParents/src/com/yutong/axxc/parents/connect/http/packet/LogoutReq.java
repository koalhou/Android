
package com.yutong.axxc.parents.connect.http.packet;

/**
 * 退出登录请求
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class LogoutReq extends AbstractReq {

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
        reuqestUri = reuqestUri + "/user/logout";

    }
}
