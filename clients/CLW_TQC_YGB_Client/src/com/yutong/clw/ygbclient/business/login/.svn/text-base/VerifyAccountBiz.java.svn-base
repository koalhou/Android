package com.yutong.clw.ygbclient.business.login;

import android.content.Context;

import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.VerifyAccountInfo;
import com.yutong.clw.ygbclient.common.enums.AccountType;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.login.VerifyAccountReq;
import com.yutong.clw.ygbclient.connect.http.login.VerifyAccountRes;

/**
 * 验证帐号业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:24:48
 */
public class VerifyAccountBiz extends AbstractDataControl
{
    /**
     * 员工账号，可以为员工号，手机号码
     */
    private String account;

    /**
     * 账号类型
     */
    private AccountType type = AccountType.AllType;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public VerifyAccountBiz(Context context, String account)
    {
        this.context = context;
        this.account = account;
        this.type = AccountType.CodeType;
        logTypeName = "[验证帐号业务逻辑类]:";
        setActionURI(ActionURI.VERIFY_ACCOUNT);
    }

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
     public VerifyAccountBiz(Context context, String account, AccountType
     type)
     {
     this.context = context;
     this.account = account;
     this.type = type;
    
     logTypeName = "[验证帐号业务逻辑类]:";
     actionURI = ActionURI.VERIFY_ACCOUNT;
     }

    /**
     * 验证账号
     *@author zhangzhia 2013-11-4 上午9:38:41
     *
     * @return  验证账号响应实体
     * @throws CommonException
     */
    public VerifyAccountInfo verifyAccount() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        VerifyAccountReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            VerifyAccountRes res = new VerifyAccountRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");

                return res.getVerifyInfo();
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
    private VerifyAccountReq buildReq()
    {
        VerifyAccountReq req = new VerifyAccountReq();
        req.setType(type);
        req.setAccount(account);
        return req;
    }
}
