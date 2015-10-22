package com.yutong.clw.ygbclient.business.setting;

import android.content.Context;

import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.VehicheInfo;
import com.yutong.clw.ygbclient.common.beans.VersionInfo;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.SysInfoGetUtil;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.setting.VersionChkReq;
import com.yutong.clw.ygbclient.connect.http.setting.VersionChkRes;

/**
 * 版本检查业务逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class VersionChkBiz extends AbstractDataControl
{


    /**
     * 构造函数
     * 
     * @param context Context对象
     * @param handler Handler对象
     */
    public VersionChkBiz(Context context)
    {
        this.context = context;
        logTypeName = "[版本检查业务逻辑类]:";
        setActionURI(ActionURI.CHECK_VERSION);
    }

    /**
     * 登陆逻辑
     * 
     * @throws CommonException
     */
    public VersionInfo versionChk() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        VersionChkReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            VersionChkRes res = new VersionChkRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                
                /*204:无新版本软件*/
                if (httpRes.getStatusCode() == 204)
                {
                    VersionInfo versionInfo = new VersionInfo();
                    versionInfo.need_update = false;

                    return versionInfo;
                }

                return res.getVersionInfo();
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
     * 组装登录请求对象
     * 
     * @return
     */
    private VersionChkReq buildReq()
    {
        VersionChkReq versionChkReq = new VersionChkReq();

        versionChkReq.setVersion_code(SysInfoGetUtil.getVersionCode(context) + "");

        versionChkReq.setVersion_name(SysInfoGetUtil.getPackageInfo(context));
        versionChkReq.setClientInfo(SysInfoGetUtil.getClientInfoBean(context).getJsonString());
        return versionChkReq;
    }
}
