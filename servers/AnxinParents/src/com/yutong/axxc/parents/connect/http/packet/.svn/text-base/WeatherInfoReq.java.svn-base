
package com.yutong.axxc.parents.connect.http.packet;

import android.net.Uri;


/**
 * 天气信息获取请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class WeatherInfoReq extends AbstractReq {

    /** 学生id */
    private String cld_id;

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
        reuqestUri = reuqestUri + "/msg/weather/" + cld_id;
    }

    public void setCld_id(String cld_id)
    {
        this.cld_id = cld_id;
    }


}
