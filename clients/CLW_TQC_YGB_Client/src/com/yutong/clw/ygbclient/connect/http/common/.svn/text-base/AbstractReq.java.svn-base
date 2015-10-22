package com.yutong.clw.ygbclient.connect.http.common;

import com.yutong.clw.ygbclient.common.UriFactory;
import com.yutong.clw.ygbclient.common.beans.UriAction;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.HttpAction;

/**
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public abstract class AbstractReq
{

    protected String reuqestUrl = null;
    
    protected String uriParam;

    String accessToken;

    String ifNoneMatch;
    
    boolean isGzip = true;

    String clientInfo;

    UriAction uriAction;

    public String getReuqestUrl()
    {
        return reuqestUrl;
    }

    public UriAction getUriAction()
    {
        return uriAction;
    }
    
    public HttpAction getHttpAction()
    {
        return uriAction.getAction();
    }

    /**
     * 组包后必须调用
     *@author zhangzhia 2013-10-21 下午3:43:10
     *
     * @param uri
     */
    public void setUriAction(ActionURI actionURI)
    {
        this.uriAction = UriFactory.getUriAction(actionURI);
        
        this.reuqestUrl = uriAction.getUrl();
        if(uriParam != null && uriParam.length() > 0)
        {
            this.reuqestUrl += uriParam;
        }

    }

    public HttpAction getAction()
    {
        return uriAction.getAction();
    }

    public String getIfNoneMatch()
    {
        return ifNoneMatch;
    }

    public void setIfNoneMatch(String ifNoneMatch)
    {
        this.ifNoneMatch = ifNoneMatch;
    }
    
    public boolean isGzip()
    {
        return isGzip;
    }

    public void setGzip(boolean isGzip)
    {
        this.isGzip = isGzip;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
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

    //public abstract void setReuqestUri();

}
