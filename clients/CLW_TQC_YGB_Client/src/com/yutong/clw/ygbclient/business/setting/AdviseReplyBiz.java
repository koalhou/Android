package com.yutong.clw.ygbclient.business.setting;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.setting.AdviseReplyReq;
import com.yutong.clw.ygbclient.connect.http.setting.AdviseReplyRes;

/**
 * 意见反馈业务逻辑类
 * @author zhangzhia 2013-10-22 上午9:31:13
 *
 */
public class AdviseReplyBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;
    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public AdviseReplyBiz(Context context, String content)
    {
        this.context = context;
        logTypeName = "[意见反馈回复获取业务逻辑类]:";
        setActionURI(ActionURI.ADVISE_REPLY);
    }

    public boolean reportAdviseReply() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        AdviseReplyReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            AdviseReplyRes res = new AdviseReplyRes();
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
    private AdviseReplyReq buildReq()
    {
        AdviseReplyReq req = new AdviseReplyReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        return req;
    }

}