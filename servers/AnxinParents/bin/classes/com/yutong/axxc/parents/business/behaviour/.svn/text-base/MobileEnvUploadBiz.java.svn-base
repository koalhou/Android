
package com.yutong.axxc.parents.business.behaviour;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Handler;
import android.telephony.TelephonyManager;

import com.yutong.axxc.parents.business.common.SysInfoGetUtil;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.MobileEnvUploadReq;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 手机环境信息上传业务逻辑类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class MobileEnvUploadBiz extends YtAsyncTask {
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

    /** Context对象 */
    private Context context;

    /** Handler对象，用于与UI通信 */
    private Handler handler;
    
    /**
     * <默认构造函数>
     */
    public MobileEnvUploadBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        logTypeName = "[手机软硬件环境上传业务类]:";
    }


    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.common.YtAsyncTask#doInBackground()
     */
    @Override
    protected void doInBackground() {
        Logger.d(this.getClass(),logTypeName + "开始上传手机软硬件环境");
        // 组装报文请求
        MobileEnvUploadReq envUploadReq = buildMobileEnvUploadReq();
        HttpRes httpRes = HttpMsgSendUtil.sendPutMsg(envUploadReq);
        if (httpRes.isSuccess() && httpRes.getStatusCode() == 200) {
            Logger.i(this.getClass(),logTypeName + "上传手机软硬件环境成功");
        } else {
            Logger.w(this.getClass(),logTypeName + "上传手机软硬件环境失败：", httpRes.getFailInfo());
        }
    }

    private MobileEnvUploadReq buildMobileEnvUploadReq() {
        MobileEnvUploadReq envUploadReq = new MobileEnvUploadReq();
        envUploadReq.setAccessToken(YtApplication.getInstance().getAccessToken());
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        envUploadReq.setMsisdn(telephonyManager.getLine1Number());
        envUploadReq.setMode_numb(toOther(android.os.Build.MODEL));
        envUploadReq.setBrand(toOther(android.os.Build.MANUFACTURER));
        envUploadReq.setReso_rati(YtApplication.getInstance().getDisplayHeight() + "*"
               + YtApplication.getInstance().getDisplayWidth());
        envUploadReq.setT_mobile(""
                + getNetworkOperator(telephonyManager.getNetworkOperatorName(), telephonyManager.getNetworkType()));
        envUploadReq.setOs_vers("android " + toOther(android.os.Build.VERSION.RELEASE));
        envUploadReq.setSoft_vers(toOther(SysInfoGetUtil.getPackageInfo(context)));
        envUploadReq.setImei(toOther(telephonyManager.getDeviceId()));
        // 延后解析位置经纬度，否则会导致Loading时间太久
        // LocationUtils.saveAddr(YtApplication.getInstance().getLocation());
       // envUploadReq.setCity(YtApplication.getInstance().getCity());
       // envUploadReq.setProvince(YtApplication.getInstance().getProv());
        return envUploadReq;
    }

    private String toOther(String str) {
        if (StringUtils.isNotBlank(str)) {
            return str;
        } else {
            return OTHER;
        }
    }

    private int getNetworkOperator(String networkOperatorName, int networkType) {
        int networkOperator = OPERATOR_WIFI;
        // TODO　用中国移动网络测试发现networkOperatorName可能是个数组类型的字符如：(中国移动,null)
        Logger.i(getClass(), "网络名称：", networkOperatorName, "网络type:", networkType);
        if (OPERATOR_CHINA_MOBILE.indexOf(networkOperatorName) >= 0
                || OPERATOR_CHINA_MOBILE_EN.indexOf(networkOperatorName) >= 0) {
            if (networkType == TelephonyManager.NETWORK_TYPE_GPRS || networkType == TelephonyManager.NETWORK_TYPE_EDGE) {
                networkOperator = OPERATOR_CHINA_MOBILE_2G;
            } else {
                networkOperator = OPERATOR_CHINA_MOBILE_3G;
            }
        } else if (OPERATOR_CHINA_TELECOM.indexOf(networkOperatorName) >= 0
                || OPERATOR_CHINA_TELECOM_EN.indexOf(networkOperatorName) >= 0) {
            if (networkType == TelephonyManager.NETWORK_TYPE_CDMA) {
                networkOperator = OPERATOR_CHINA_TELECOM_2G;
            } else {
                networkOperator = OPERATOR_CHINA_TELECOM_3G;
            }
        } else if (OPERATOR_CHINA_UNICOM.indexOf(networkOperatorName) >= 0
                || OPERATOR_CHINA_UNICOM_EN.indexOf(networkOperatorName) >= 0) {
            if (networkType == TelephonyManager.NETWORK_TYPE_GPRS || networkType == TelephonyManager.NETWORK_TYPE_EDGE) {
                networkOperator = OPERATOR_CHINA_UNICOM_2G;
            } else {
                networkOperator = OPERATOR_CHINA_UNICOM_3G;
            }
        } else {
            networkOperator = OPERATOR_WIFI;
        }
        return networkOperator;
    }
}
