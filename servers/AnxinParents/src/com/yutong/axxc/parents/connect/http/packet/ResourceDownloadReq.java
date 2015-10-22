package com.yutong.axxc.parents.connect.http.packet;


/**
 * 资源下载请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class ResourceDownloadReq extends AbstractReq {

    private String res_id;
    
    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        return null;
    }


    public void setRes_id(String res_id)
    {
        this.res_id = res_id;
    }


    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/resources/" + res_id;
    }
}
