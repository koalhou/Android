/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:08:36
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans;

import com.yutong.clw.ygbclient.common.enums.HttpAction;

public class UriAction
{
    private HttpAction action;

    private String url;

    private String uri;

    public UriAction(HttpAction action, String uri)
    {
        this.action = action;
        this.uri = uri;

    }
    
    public UriAction(String apiPath, HttpAction action, String uri)
    {
        this.action = action;
        this.uri = uri;
        this.url = apiPath + uri;

    }

    public HttpAction getAction()
    {
        return action;
    }

    public void setAction(HttpAction action)
    {
        this.action = action;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

}
