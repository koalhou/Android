package com.yutong.clw.ygbclient.business.login;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.login.BindPhoneReq;
import com.yutong.clw.ygbclient.connect.http.login.BindPhoneRes;

/**
 * 手机绑定业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:24:03
 */
public class BindPhoneBiz extends AbstractDataControl
{
    /**
     * 绑定手机号码
     */
    private String phone;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public BindPhoneBiz(Context context, String phone)
    {
        this.context = context;
        this.phone = phone;

        logTypeName = "[手机绑定业务逻辑类]:";
        setActionURI(ActionURI.BIND_PHONE);
    }

    /**
     * 绑定手机
     * 
     * @author zhangzhia 2013-10-25 下午4:47:40
     * @throws CommonException
     */
    public boolean bindPhone() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        BindPhoneReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            BindPhoneRes res = new BindPhoneRes();
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
    private BindPhoneReq buildReq()
    {
        BindPhoneReq req = new BindPhoneReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setPhone(phone);
        return req;
    }
}
