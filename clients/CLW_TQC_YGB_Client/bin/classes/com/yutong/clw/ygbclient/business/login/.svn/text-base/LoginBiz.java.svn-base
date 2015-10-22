package com.yutong.clw.ygbclient.business.login;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.SysInfoGetUtil;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.login.LoginReq;
import com.yutong.clw.ygbclient.connect.http.login.LoginRes;
import com.yutong.clw.ygbclient.dao.login.LoginInfoDao;

/**
 * 登陆业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:24:11
 */
public class LoginBiz extends AbstractDataControl
{
    /**
     * 登陆账号
     */
    private String account;

    /**
     * 登陆密码,MD5加密
     */
    private String passwordMD5;

    /**
     * 构造函数
     * 
     * @param context Context对象
     * @param account
     * @param passwordMD5
     */
    public LoginBiz(Context context, String account, String passwordMD5)
    {
        this.context = context;
        this.account = account;
        // this.passwordMD5 = EncryptUtils.getMD5Str(passwordMD5);
        this.passwordMD5 = passwordMD5;
        logTypeName = "[登录业务逻辑类]:";
        setActionURI(ActionURI.LOGIN);

        cacheKey += account;
    }

    /**
     * 登陆
     * 
     * @author zhangzhia 2013-10-28 上午11:11:55
     * @return
     * @throws CommonException
     */
    public UserInfo login() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        LoginReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            LoginRes res = new LoginRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                
                UserInfo emp_info;
                //判断Etag标记是否过期
                if (httpRes.needCached())
                {
                    // 存入本地
                    emp_info = res.getEmp_info();
                    new LoginInfoDao(context).addLoginInfo(emp_info);
                }
                else
                {
                    //从本地获取 
                    emp_info = new LoginInfoDao(context).getLoginInfo();
                }

                if(emp_info != null)
                {
                    ProxyManager.getInstance(context).setUserCode(emp_info.Code);
                    ProxyManager.getInstance(context).setAccessToken(res.getAccessToken());
                }
                
                return emp_info;
            }
            else
            {
                Logger.e(this.getClass(), logTypeName + "失败");
                throw new CommonException(CommonNetStatus.Error_Info, res.getErrorCode(), res.getErrorDes());
            }
        }
        else if (httpRes.isTokenExpire())
        {
            Logger.e(this.getClass(), logTypeName + "Token失效");
            throw new CommonException(CommonNetStatus.Token_InValid);
        }
        else if (httpRes.isException())
        {
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Exception);
        }
        else
        {
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            if(httpRes.getStatusCode() == 404){
            	throw new CommonException(CommonNetStatus.LOGIN_FAIL);
            }else{
            	throw new CommonException(CommonNetStatus.NetWork_Not_Stable);
            }
            
            
        }
    }

    /**
     * 组装请求对象
     * 
     * @param context
     * @return
     */
    private LoginReq buildReq()
    {
        LoginReq req = new LoginReq();
        req.setAccount(account);
        req.setPasswordMD5(passwordMD5);

        req.setClientInfo(SysInfoGetUtil.getClientInfoBean(context).getJsonString());
        req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI, cacheKey));

        return req;
    }

}
