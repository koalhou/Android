/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:07:36
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.other;

import android.content.Context;

import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.enums.SMSVerifyType;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.other.SendPhoneSMSReq;
import com.yutong.clw.ygbclient.connect.http.other.SendPhoneSMSRes;

/**
 * 手机短信验证业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午10:07:36
 */
public class SendPhoneSMSBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    // 手机号
    private String phone;

    // 工号
    private String code;

    // 短信验证类型
    private SMSVerifyType type;

    /**
     * 构造函数
     * 
     * @param context
     * @param phone 手机号
     * @param code 验证码
     * @param type 验证类型
     */
    public SendPhoneSMSBiz(Context context, String phone, String code, SMSVerifyType type)
    {
        this.context = context;
        this.phone = phone;
        this.code = code;
        this.type = type;
        logTypeName = "[手机短信验证业务逻辑类]:";
        setActionURI(ActionURI.SEND_PHONE_SMS);
    }

    public String sendSMS() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        SendPhoneSMSReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            SendPhoneSMSRes res = new SendPhoneSMSRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");

                return res.getCode();
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
    private SendPhoneSMSReq buildReq()
    {
        SendPhoneSMSReq req = new SendPhoneSMSReq();
        req.setPhone(phone);
        req.setCode(code);
        req.setType(type);
        return req;
    }

}