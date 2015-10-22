package com.yutong.clw.ygbclient.business.login;

import android.content.Context;

import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.EncryptUtils;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.login.ForgotPasswordReq;
import com.yutong.clw.ygbclient.connect.http.login.ForgotPasswordRes;

/**
 * 找回密码业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:24:58
 */
public class ForgotPasswordBiz extends AbstractDataControl
{
    /**
     * 员工号
     */
    private String emp_code;

    /**
     * string MD5加密的新密码
     */
    private String passwordMD5;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public ForgotPasswordBiz(Context context, String emp_code, String passwordMD5)
    {
        this.context = context;
        this.emp_code = emp_code;
        this.passwordMD5 = passwordMD5;
        logTypeName = "[ 找回密码业务逻辑类]:";
        setActionURI(ActionURI.FORGOT_PASSWORD);
    }

    /**
     * 找回并修改密码
     * 
     * @author zhangzhia 2013-10-25 下午4:48:39
     * @throws CommonException
     */
    public boolean modifyPassword() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        ForgotPasswordReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            ForgotPasswordRes res = new ForgotPasswordRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                return true;

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
    private ForgotPasswordReq buildReq()
    {
        ForgotPasswordReq req = new ForgotPasswordReq();
        req.setEmp_code(emp_code);
        req.setPasswordMD5(passwordMD5);

        return req;
    }
}
