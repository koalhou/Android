package com.yutong.axxc.parents.connect.http.packet;


/**
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public abstract class AbstractReq {

    String reuqestUri = "";

    String accessToken;

    String ifNoneMatch;

    String clientInfo;
    
    public String getReuqestUri() {
        return ("/" + reuqestUri + "/").replace("//", "/");
    }


    public String getIfNoneMatch() {
        return ifNoneMatch;
    }


    public void setIfNoneMatch(String ifNoneMatch) {
        this.ifNoneMatch = ifNoneMatch;
    }


    public String getAccessToken() {
        return accessToken;
    }


    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    

    public String getClientInfo()
    {
        return clientInfo;
    }


    public void setClientInfo(String clientInfo)
    {
        this.clientInfo = clientInfo;
    }


    public abstract String packetMsgBody();


    public abstract void setReuqestUri();
}
