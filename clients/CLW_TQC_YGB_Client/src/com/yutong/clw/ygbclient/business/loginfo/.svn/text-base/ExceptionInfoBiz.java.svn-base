/**
 * @公司名称：YUTONG
 * @文件名：ExceptionInfoBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:02:58
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.loginfo;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.EnvironmentInfo;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.SysInfoGetUtil;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.loginfo.ExceptionInfoReq;
import com.yutong.clw.ygbclient.connect.http.loginfo.ExceptionInfoRes;

/**
 * 异常信息收集上报业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午10:02:58
 */
public class ExceptionInfoBiz extends AbstractDataControl
{
    private String error_desc;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public ExceptionInfoBiz(Context context, String error_desc)
    {
        this.context = context;
        this.error_desc = error_desc;

        logTypeName = "[异常信息收集上报业务逻辑类]:";
        setActionURI(ActionURI.REPORT_EXCEPTION_INFO);
    }

    public void reportExceptionInfo() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        ExceptionInfoReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            ExceptionInfoRes res = new ExceptionInfoRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
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
    private ExceptionInfoReq buildReq()
    {
        ExceptionInfoReq req = new ExceptionInfoReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setEnv_info(getEnvInfo());
        req.setError_code("1");
        req.setError_desc(error_desc);
        req.setError_time(DateUtils.dateToStr(new Date(), "yyyyMMddHHmmss"));
        return req;
    }

    private EnvironmentInfo getEnvInfo()
    {
        EnvironmentInfo envInfo = new EnvironmentInfo();

        envInfo.app_version = SysInfoGetUtil.getPackageInfo(context);

        envInfo.os_name = "android";

        envInfo.os_version = StringUtils.isNotBlank(android.os.Build.VERSION.RELEASE) ? android.os.Build.VERSION.RELEASE : "other";

        return envInfo;
    }
}
