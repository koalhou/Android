package com.yutong.clw.ygbclient.business.setting;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.setting.AdviseFeedbackReq;
import com.yutong.clw.ygbclient.connect.http.setting.AdviseFeedbackRes;

/**
 * 意见反馈业务逻辑类
 * @author zhangzhia 2013-10-22 上午9:31:13
 *
 */
public class AdviseFeedbackBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    /**
     * 意见反馈信息
     */
    private String content;
    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public AdviseFeedbackBiz(Context context, String content)
    {
        this.context = context;
        this.content = content;
        logTypeName = "[意见反馈业务逻辑类]:";
        setActionURI(ActionURI.ADVISE_FEEDBACK);
    }

    public boolean reportAdviseFeedback(String msg_id) throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        AdviseFeedbackReq req = buildReq(msg_id);

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            AdviseFeedbackRes res = new AdviseFeedbackRes();
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
    private AdviseFeedbackReq buildReq(String msg_id)
    {
        AdviseFeedbackReq req = new AdviseFeedbackReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setContent(content);
        req.setMsg_id(msg_id);
        return req;
    }

}