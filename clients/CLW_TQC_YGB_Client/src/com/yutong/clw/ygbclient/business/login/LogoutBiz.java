package com.yutong.clw.ygbclient.business.login;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.login.LogoutBizReq;
import com.yutong.clw.ygbclient.connect.http.login.LogoutBizRes;

/**
 * 退出登陆业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:26:01
 */
public class LogoutBiz extends AbstractDataControl
{
    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public LogoutBiz(Context context)
    {
        this.context = context;
        logTypeName = "[退出登陆业务逻辑类]:";
        setActionURI(ActionURI.LOGOUT);
    }

    public void logout() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        LogoutBizReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            LogoutBizRes res = new LogoutBizRes();
            res.parse(httpRes.getContent());
            if (!res.isError()){
                Logger.i(this.getClass(), logTypeName + "成功");

            }else{
                Logger.e(this.getClass(), logTypeName + "失败");
                throw new CommonException(CommonNetStatus.Error_Info, res.getErrorCode(), res.getErrorDes());
            }
        }else if (httpRes.isTokenExpire()){
        	
            Logger.e(this.getClass(), logTypeName + "Token失效");
            throw new CommonException(CommonNetStatus.Token_InValid);
        }else if (httpRes.isException()){
        	
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Exception);
        }else{
        	
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Not_Stable);
        }
    }

    /**
     * 组装请求对象
     * 
     * @param context
     * @return
     */
    private LogoutBizReq buildReq()
    {
        LogoutBizReq req = new LogoutBizReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        return req;
    }
}
