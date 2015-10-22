package com.yutong.clw.ygbclient.connect.http.common;

/**
 * http 响应包装类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class HttpRes
{

    public static final String EXPIRED_TOKEN_PROMPT = "登录已失效，请您重新登录";

    public static final String NOT_MODIFIED_PROMPT = "服务端内容无变化";

    public static final String NO_CONTENT_PROMPT = "无查询结果";

    public static final String TOKENEXPIRE_PROPMT = "ACCESE_TOKEN过期";

    public static final String NETWORK_NOT_STABLE_PROMPT = "网络不稳定，请稍后重试";

    public static final String NETWORK_EXCEPTION_PROPMT = "网络连接异常，请检查您的网络";

    public static final String SERVER_EXCEPTION_PROPMT = "网络异常";
    
    public static final String LOGIN_FAIL = "登录失败，请检查用户名或密码";

    /** ETag */
    private String ETag;

    /** 状态码 */
    private int statusCode;

    /** 返回内容 */
    private String content;

    /** 是否异常 */
    private boolean isException = false;

    public boolean isSuccess()
    {
        if (!isException &&
         (statusCode != 404 &&
         statusCode != 500 &&
          (statusCode == 200 || statusCode == 204 || statusCode == 304 || content != null && content.length() > 0)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean needCached()
    {
        if (!isException && (statusCode == 200 || statusCode == 204))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isTokenExpire()
    {
        return statusCode == 298;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setException(boolean isException)
    {
        this.isException = isException;
    }

    public boolean isException()
    {
        return isException;
    }

    /**
     * 获取错误描述
     * 
     * @author zhangzhia 2013-8-29 下午7:37:59
     * @return
     */
    public String getFailInfo()
    {
        if (isException)
        {
            return NETWORK_EXCEPTION_PROPMT;
        }
        else if (statusCode == 204)
        {
            return NO_CONTENT_PROMPT;
        }
        else if (statusCode == 298)
        {
            return TOKENEXPIRE_PROPMT;
        }
        else if (statusCode == 304)
        {
            return NOT_MODIFIED_PROMPT;
        }
        else if (statusCode == 401)
        {
            return EXPIRED_TOKEN_PROMPT;
            // } else if (statusCode == 400) {
            // return "客户端请求交易内容异常";
            // } else if (statusCode == 403) {
            // return "服务端拒绝处理请求内容";
        }
        else if( statusCode == 404){
        	
        	return LOGIN_FAIL;
        }
        else
        {
            return NETWORK_NOT_STABLE_PROMPT;
        }
    }

    public String getETag()
    {
        return ETag;
    }

    public void setETag(String eTag)
    {
        ETag = eTag;
    }

}
