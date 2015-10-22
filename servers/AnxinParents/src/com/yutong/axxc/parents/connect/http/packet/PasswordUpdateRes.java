
package com.yutong.axxc.parents.connect.http.packet;

/**
 * 密码更新响应
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class PasswordUpdateRes extends AbstractRes {

    /**
     * (non-Javadoc)
     * @see com.neusoft.yt.connect.http.packet.PasswordUpdateRes#parse(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString) {
        return true;
    }

}
