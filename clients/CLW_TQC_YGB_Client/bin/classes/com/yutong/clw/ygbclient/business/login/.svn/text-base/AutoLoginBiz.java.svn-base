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
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.common.utils.SysInfoGetUtil;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.login.AutoLoginReq;
import com.yutong.clw.ygbclient.connect.http.login.AutoLoginRes;
import com.yutong.clw.ygbclient.dao.login.LoginInfoDao;

/**
 * 自动登录业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:25:37
 */
public class AutoLoginBiz extends AbstractDataControl
{
    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public AutoLoginBiz(Context context)
    {
        this.context = context;
        logTypeName = "[自动登录业务逻辑类]:";
        setActionURI(ActionURI.AUTO_LOGIN);

        cacheKey += ProxyManager.getInstance(context).getAccessToken();
    }

    /**
     * 判断是否能自动登录
     *@author zhangzhia 2013-11-8 下午2:08:02
     *
     * @return
     */
    public boolean isAutoLogin()
    {
        // 从本地获取
        UserInfo emp_info = new LoginInfoDao(context).getLoginInfo();
        if (emp_info != null && StringUtil.isNotBlank(emp_info.Code))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public UserInfo autoLogin() throws CommonException
    {
        // TODO 需实现逻辑代码
        Logger.i(this.getClass(), logTypeName + "发送请求");
        AutoLoginReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            AutoLoginRes res = new AutoLoginRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");

                UserInfo emp_info;
                // 判断Etag标记是否过期
                if (httpRes.needCached())
                {
                    // 存入本地
                    emp_info = res.getEmp_info();
                    new LoginInfoDao(context).addLoginInfo(emp_info);
                }
                else
                {
                    // 从本地获取
                    emp_info = new LoginInfoDao(context).getLoginInfo();
                }

                if (emp_info != null)
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
            throw new CommonException(CommonNetStatus.NetWork_Not_Stable);
        }
    }

    /**
     * 组装请求对象
     * 
     * @param context
     * @return
     */
    private AutoLoginReq buildReq()
    {
        AutoLoginReq req = new AutoLoginReq();

        req.setClientInfo(SysInfoGetUtil.getClientInfoBean(context).getJsonString());
        req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI, cacheKey));
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        return req;
    }
}
