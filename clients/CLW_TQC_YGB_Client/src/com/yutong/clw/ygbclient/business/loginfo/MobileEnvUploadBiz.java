/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:02:58
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.loginfo;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.SysInfoGetUtil;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.loginfo.MobileEnvUploadReq;
import com.yutong.clw.ygbclient.connect.http.loginfo.MobileEnvUploadRes;

/**
 * 手机环境信息上传业务逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class MobileEnvUploadBiz extends AbstractDataControl
{
    private static final String OTHER = "other";

    /** 中国移动 */
    private static final String OPERATOR_CHINA_MOBILE = "中国移动";

    /** 中国移动 */
    private static final String OPERATOR_CHINA_MOBILE_EN = "CHINA  MOBILE";

    /** 中国电信 */
    private static final String OPERATOR_CHINA_TELECOM = "中国电信";

    /** 中国电信 */
    private static final String OPERATOR_CHINA_TELECOM_EN = "CHINA  TELECOM";

    /** 中国联通 */
    private static final String OPERATOR_CHINA_UNICOM = "中国联通";

    /** 中国联通 */
    private static final String OPERATOR_CHINA_UNICOM_EN = "CHINA  UNICOM";

    /** WIFI */
    public static final int OPERATOR_WIFI = 0;

    /** 中国移动2G */
    public static final int OPERATOR_CHINA_MOBILE_2G = 1;

    /** 中国移动3G */
    public static final int OPERATOR_CHINA_MOBILE_3G = 2;

    /** 中国电信2G */
    public static final int OPERATOR_CHINA_TELECOM_2G = 3;

    /** 中国电信3G */
    public static final int OPERATOR_CHINA_TELECOM_3G = 4;

    /** 中国联通2G */
    public static final int OPERATOR_CHINA_UNICOM_2G = 5;

    /** 中国联通2G */
    public static final int OPERATOR_CHINA_UNICOM_3G = 6;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public MobileEnvUploadBiz(Context context)
    {
        this.context = context;
        logTypeName = "[手机环境信息上传业务逻辑类]:";
        setActionURI(ActionURI.REPORT_MOBILE_ENVIRONMENT_INFO);
    }

    /**
     * 上报手机系统环境信息
     * 
     * @author zhangzhia 2013-10-28 上午11:13:39
     * @throws CommonException
     */
    public void reportMobileEnv() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        MobileEnvUploadReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            MobileEnvUploadRes res = new MobileEnvUploadRes();
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
    private MobileEnvUploadReq buildReq()
    {
        MobileEnvUploadReq req = new MobileEnvUploadReq();
        // TODO 给参数赋值
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        req.setMsisdn(telephonyManager.getLine1Number());
        req.setMode_numb(toOther(android.os.Build.MODEL));
        req.setBrand(toOther(android.os.Build.MANUFACTURER));
        req.setReso_rati(YtApplication.getInstance().getAppInitConfigs().getDisplayHeight() + "*"
                + YtApplication.getInstance().getAppInitConfigs().getDisplayWidth());

        req.setT_mobile("" + getNetworkOperator(telephonyManager.getNetworkOperatorName(), telephonyManager.getNetworkType()));
        req.setOs_vers("android " + toOther(android.os.Build.VERSION.RELEASE));
        req.setSoft_vers(toOther(SysInfoGetUtil.getPackageInfo(context)));
        req.setImei(toOther(telephonyManager.getDeviceId()));
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        return req;
    }

    private String toOther(String str)
    {
        if (StringUtils.isNotBlank(str))
        {
            return str;
        }
        else
        {
            return OTHER;
        }
    }

    private int getNetworkOperator(String networkOperatorName, int networkType)
    {
        int networkOperator = OPERATOR_WIFI;
        // TODO　用中国移动网络测试发现networkOperatorName可能是个数组类型的字符如：(中国移动,null)
        Logger.i(getClass(), "网络名称：", networkOperatorName, "网络type:", networkType);
        if (OPERATOR_CHINA_MOBILE.indexOf(networkOperatorName) >= 0 || OPERATOR_CHINA_MOBILE_EN.indexOf(networkOperatorName) >= 0)
        {
            if (networkType == TelephonyManager.NETWORK_TYPE_GPRS || networkType == TelephonyManager.NETWORK_TYPE_EDGE)
            {
                networkOperator = OPERATOR_CHINA_MOBILE_2G;
            }
            else
            {
                networkOperator = OPERATOR_CHINA_MOBILE_3G;
            }
        }
        else if (OPERATOR_CHINA_TELECOM.indexOf(networkOperatorName) >= 0 || OPERATOR_CHINA_TELECOM_EN.indexOf(networkOperatorName) >= 0)
        {
            if (networkType == TelephonyManager.NETWORK_TYPE_CDMA)
            {
                networkOperator = OPERATOR_CHINA_TELECOM_2G;
            }
            else
            {
                networkOperator = OPERATOR_CHINA_TELECOM_3G;
            }
        }
        else if (OPERATOR_CHINA_UNICOM.indexOf(networkOperatorName) >= 0 || OPERATOR_CHINA_UNICOM_EN.indexOf(networkOperatorName) >= 0)
        {
            if (networkType == TelephonyManager.NETWORK_TYPE_GPRS || networkType == TelephonyManager.NETWORK_TYPE_EDGE)
            {
                networkOperator = OPERATOR_CHINA_UNICOM_2G;
            }
            else
            {
                networkOperator = OPERATOR_CHINA_UNICOM_3G;
            }
        }
        else
        {
            networkOperator = OPERATOR_WIFI;
        }
        return networkOperator;
    }
}
