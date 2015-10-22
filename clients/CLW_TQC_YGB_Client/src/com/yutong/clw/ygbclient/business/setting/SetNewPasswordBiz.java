/**
 * @公司名称：YUTONG
 * @文件名：SetNewPasswordBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午9:43:31
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.setting;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.setting.SetNewPasswordReq;
import com.yutong.clw.ygbclient.connect.http.setting.SetNewPasswordRes;

/**
 * 用户密码修改业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:43:31
 */
public class SetNewPasswordBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    /**
     * 旧密码
     */
    private String old_pwdMD5;

    /**
     * 新密码
     */
    private String new_pwdMD5;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public SetNewPasswordBiz(Context context, String old_pwdMD5, String new_pwdMD5)
    {
        this.context = context;
        this.old_pwdMD5 = old_pwdMD5;
        this.new_pwdMD5 = new_pwdMD5;

        logTypeName = "[用户密码修改业务逻辑类]:";
        setActionURI(ActionURI.SET_NEW_PASSWORD);
    }

    public boolean updatePassword() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        SetNewPasswordReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            SetNewPasswordRes res = new SetNewPasswordRes();
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
    private SetNewPasswordReq buildReq()
    {
        SetNewPasswordReq req = new SetNewPasswordReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setOld_pwd(old_pwdMD5);
        req.setNew_pwd(new_pwdMD5);

        return req;
    }

}
