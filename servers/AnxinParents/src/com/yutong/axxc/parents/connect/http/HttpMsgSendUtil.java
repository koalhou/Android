package com.yutong.axxc.parents.connect.http;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.NetworkCheckUitl;
import com.yutong.axxc.parents.common.SysConfigUtil;
import com.yutong.axxc.parents.connect.http.packet.AbstractReq;

/**
 * HTTP REST消息发送工具类<br>
 * usage:<br>
 * 1.发送post消息HttpMsgSendUtil.sendPostMsg(msg);<br>
 * 2.发送get消息HttpMsgSendUtil.sendGetMsg(msg);<br>
 * 3.发送put消息HttpMsgSendUtil.sendPutMsg(msg);<br>
 * 4.发送delete消息HttpMsgSendUtil.sendDeleteMsg(msg);<br>
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */

public final class HttpMsgSendUtil
{

    private HttpMsgSendUtil()
    {
    }

    public static HttpRes sendPostMsg(AbstractReq msg)
    {
        return sendPostMsg(msg, 1, 1);
    }

    public static HttpRes sendPostMsg(AbstractReq msg, int maxSendCount)
    {
        return sendPostMsg(msg, 1, maxSendCount);
    }

    private static HttpRes sendPostMsg(AbstractReq msg, int sendCount, int maxSendCount)
    {
        HttpRes httpRes = new HttpRes();
        if (NetworkCheckUitl.isOnline())
        {
            HttpClient httpClient = new DefaultHttpClient();

            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, SysConfigUtil.getHttpConnTimeout());

            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SysConfigUtil.getHttpRecvTimeout());

            HttpPost httpPost = null;
            try
            {
                msg.setReuqestUri();
                String uri = SysConfigUtil.getHttpServerUrl() + msg.getReuqestUri();
                httpPost = new HttpPost(uri);
                setBizHeader(msg, httpPost);
                String msgBody = msg.packetMsgBody();
                if (msgBody != null)
                {
                    httpPost.setEntity(new StringEntity(msgBody, SysConfigUtil.getCharset()));
                }
                Logger.i(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：POST消息第", sendCount, "次发送，Request URI：", uri, "，Token：",
                        msg.getAccessToken(), "，MsgBody：", msgBody);
                HttpResponse response = httpClient.execute(httpPost);

                httpRes = new HttpRes();
                httpRes.setStatusCode(response.getStatusLine().getStatusCode());
                
                getBizHeader(httpRes, response);
                if (response.getEntity() != null)
                {
                    httpRes.setContent(EntityUtils.toString((response.getEntity()), SysConfigUtil.getCharset()));
                }
                Logger.i(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：POST消息响应，statusCode：", httpRes.getStatusCode(), "，content：",
                        httpRes.getContent(), "，requestUri：", msg.getReuqestUri());
                return httpRes;
            }
            catch (Exception e)
            {
                Logger.e(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：发送POST消息异常，详细信息：", e);
                int tempSendCount = sendCount;
                tempSendCount++;
                if (tempSendCount <= maxSendCount)
                {
                    return sendPostMsg(msg, tempSendCount, maxSendCount);
                }
                else
                {
                    httpRes.setException(true);
                    return httpRes;
                }

            }
            finally
            {
                if (httpPost != null)
                {
                    httpPost.abort();
                }
                if (httpClient != null)
                {
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }
        else
        {
            httpRes.setException(true);
            return httpRes;
        }

    }

    public static HttpRes sendGetMsg(AbstractReq msg)
    {
        return sendGetMsg(SysConfigUtil.getHttpServerUrl(), msg, 1, SysConfigUtil.getHttpMaxResendCount());
    }

    public static HttpRes sendGetMsg(String serverUrl, AbstractReq msg)
    {
        return sendGetMsg(serverUrl, msg, 1, SysConfigUtil.getHttpMaxResendCount());
    }

    private static HttpRes sendGetMsg(String serverUrl, AbstractReq msg, int sendCount, int maxSendCount)
    {
        HttpRes httpRes = new HttpRes();
        if (NetworkCheckUitl.isOnline())
        {
            HttpClient httpClient = new DefaultHttpClient();

            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, SysConfigUtil.getHttpConnTimeout());

            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SysConfigUtil.getHttpRecvTimeout());

            HttpGet httpGet = null;
            try
            {
                msg.setReuqestUri();
                String uri = serverUrl + msg.getReuqestUri();
                httpGet = new HttpGet(uri);
                setBizHeader(msg, httpGet);
                Logger.i(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：GET消息第", sendCount, "次发送，Request URI：", uri, "，Token：",
                        msg.getAccessToken(), "，ETag：", msg.getIfNoneMatch());
                HttpResponse response = httpClient.execute(httpGet);

                httpRes.setStatusCode(response.getStatusLine().getStatusCode());
                
                getBizHeader(httpRes, response);
                if (response.getEntity() != null)
                {
                    httpRes.setContent(EntityUtils.toString((response.getEntity()), SysConfigUtil.getCharset()));
                }
                Logger.i(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：GET消息响应，statusCode：", httpRes.getStatusCode(), "，content：",
                        httpRes.getContent(), "，requestUri：", msg.getReuqestUri());
                return httpRes;
            }
            catch (Exception e)
            {
                Logger.e(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：发送Get消息异常，详细信息：", e);
                int tempSendCount = sendCount;
                tempSendCount++;
                if (tempSendCount <= maxSendCount)
                {
                    return sendGetMsg(serverUrl, msg, tempSendCount, maxSendCount);
                }
                else
                {
                    httpRes.setException(true);
                    return httpRes;
                }

            }
            finally
            {
                if (httpGet != null)
                {
                    httpGet.abort();
                }
                if (httpClient != null)
                {
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }
        else
        {
            httpRes.setException(true);
            return httpRes;
        }
    }

    public static HttpRes sendPutMsg(AbstractReq msg)
    {
        return sendPutMsg(msg, 1, 1);
    }

    public static HttpRes sendPutMsg(AbstractReq msg, int maxSendCount)
    {
        return sendPutMsg(msg, 1, maxSendCount);
    }

    private static HttpRes sendPutMsg(AbstractReq msg, int sendCount, int maxSendCount)
    {
        HttpRes httpRes = new HttpRes();
        if (NetworkCheckUitl.isOnline())
        {
            HttpClient httpClient = new DefaultHttpClient();

            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, SysConfigUtil.getHttpConnTimeout());

            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SysConfigUtil.getHttpRecvTimeout());

            HttpPut httpPut = null;
            try
            {
                msg.setReuqestUri();
                String uri = SysConfigUtil.getHttpServerUrl() + msg.getReuqestUri();
                httpPut = new HttpPut(uri);
                setBizHeader(msg, httpPut);
                String msgBody = msg.packetMsgBody();
                httpPut.setEntity(new StringEntity(msgBody, SysConfigUtil.getCharset()));
                Logger.i(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：PUT消息第", sendCount, "次发送，Request URI：", uri, "，Token：",
                        msg.getAccessToken(), "，MsgBody：", msgBody);
                HttpResponse response = httpClient.execute(httpPut);

                getBizHeader(httpRes, response);

                httpRes.setStatusCode(response.getStatusLine().getStatusCode());
                if (response.getEntity() != null)
                {
                    httpRes.setContent(EntityUtils.toString((response.getEntity()), SysConfigUtil.getCharset()));
                }
                Logger.i(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：PUT消息响应，statusCode：", httpRes.getStatusCode(), "，content：",
                        httpRes.getContent(), "，requestUri：", msg.getReuqestUri());
                return httpRes;
            }
            catch (Exception e)
            {
                Logger.e(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：发送PUT消息异常，详细信息：", e);
                int tempSendCount = sendCount;
                tempSendCount++;
                if (tempSendCount <= maxSendCount)
                {
                    return sendPutMsg(msg, tempSendCount, maxSendCount);
                }
                else
                {
                    httpRes.setException(true);
                    return httpRes;
                }

            }
            finally
            {
                if (httpPut != null)
                {
                    httpPut.abort();
                }
                if (httpClient != null)
                {
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }
        else
        {
            httpRes.setException(true);
            return httpRes;
        }
    }

    public static HttpRes sendDeleteMsg(AbstractReq msg)
    {
        return sendDeleteMsg(msg, 1, SysConfigUtil.getHttpMaxResendCount());
    }

    private static HttpRes sendDeleteMsg(AbstractReq msg, int sendCount, int maxSendCount)
    {
        HttpRes httpRes = new HttpRes();
        if (NetworkCheckUitl.isOnline())
        {
            HttpClient httpClient = new DefaultHttpClient();

            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, SysConfigUtil.getHttpConnTimeout());

            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SysConfigUtil.getHttpRecvTimeout());

            HttpDelete httpDelete = null;
            try
            {
                msg.setReuqestUri();
                String uri = SysConfigUtil.getHttpServerUrl() + msg.getReuqestUri();
                httpDelete = new HttpDelete(uri);
                setBizHeader(msg, httpDelete);
                Logger.i(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：DELETE消息第", sendCount, "次发送，Request URI：", uri, "，Token：",
                        msg.getAccessToken());
                HttpResponse response = httpClient.execute(httpDelete);

                httpRes.setStatusCode(response.getStatusLine().getStatusCode());
                if (response.getEntity() != null)
                {
                    httpRes.setContent(EntityUtils.toString((response.getEntity()), SysConfigUtil.getCharset()));
                }
                Logger.i(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：DELETE消息响应，statusCode：", httpRes.getStatusCode(), "，content：",
                        httpRes.getContent(), "，requestUri：", msg.getReuqestUri());
                return httpRes;
            }
            catch (Exception e)
            {
                Logger.e(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：发送DELETE消息异常，详细信息：", e);
                int tempSendCount = sendCount;
                tempSendCount++;
                if (tempSendCount <= maxSendCount)
                {
                    return sendDeleteMsg(msg, tempSendCount, maxSendCount);
                }
                else
                {
                    httpRes.setException(true);
                    return httpRes;
                }

            }
            finally
            {
                if (httpDelete != null)
                {
                    httpDelete.abort();
                }
                if (httpClient != null)
                {
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }
        else
        {
            httpRes.setException(true);
            return httpRes;
        }
    }

    private static void setBizHeader(AbstractReq msg, HttpRequestBase httpClient)
    {
        httpClient.setHeader("Content-Type", "application/json;charset=UTF-8");
        if (msg.getAccessToken() != null)
        {
            httpClient.setHeader("access_token", msg.getAccessToken());
        }
        if (msg.getIfNoneMatch() != null)
        {
            httpClient.setHeader("If-None-Match", msg.getIfNoneMatch());
        }
        if (msg.getClientInfo() != null)
        {
            httpClient.setHeader("client_info", msg.getClientInfo());
        }

    }

    private static void getBizHeader(HttpRes msg, HttpResponse httpServer)
    {
        Header header = httpServer.getFirstHeader("ETag");
        if(header != null)
        {
            msg.setETag(header.getValue());
        }

    }

}
