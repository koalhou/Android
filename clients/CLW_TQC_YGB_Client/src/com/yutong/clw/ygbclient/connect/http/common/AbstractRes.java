package com.yutong.clw.ygbclient.connect.http.common;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.yutong.clw.ygbclient.common.Logger;

/**
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public abstract class AbstractRes
{

    String errorCode;

    String errorDes;

    boolean isError = false;

    public boolean parse(String msg)
    {
        if (StringUtils.isBlank(msg))
        {
            return false;
        }
        try
        {
            //JSONTokener jsonTokener = new JSONTokener(msg);

            try
            {
                JSONArray jsonArray =new JSONArray(msg);
                return parseCorrectMsg(msg);

            }
            catch (Exception e)
            {
                //不处理
            }
            JSONObject jsonObject = new JSONObject(msg);
//            JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
            if (parseErrorMsg(jsonObject))
            {
                if (errorCode == null || "".equals(errorCode) || "null".equals(errorCode))
                {
                    return parseCorrectMsg(msg);
                }
                else
                {
                    isError = true;
                    return true;
                }
            }
            else
            {
                isError = false;
                return false;
            }
        }
        catch (Exception e)
        {
            Logger.e(getClass(), "[响应消息抽象类]：响应消息解析异常：", e);
            isError = false;
            return false;
        }
    }

    public abstract boolean parseCorrectMsg(String jsonObject);

    // protected
    private boolean parseErrorMsg(JSONObject jsonObject)
    {
        try
        {
            errorCode = jsonObject.optString("error_code");
            errorDes = jsonObject.optString("error_des");
            return true;
        }
        catch (Exception e)
        {
            Logger.e(getClass(), "[响应消息抽象类]：ERROR响应消息解析异常：", e);
            return false;
        }
    }

    public boolean isNoTokenInfo()
    {
        return StringUtils.equals("20000", errorCode);
    }

    public boolean isOauthFailed()
    {
        return StringUtils.equals("20001", errorCode);
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public String getErrorDes()
    {
        return errorDes;
    }

    public boolean isError()
    {
        return isError;
    }

}
