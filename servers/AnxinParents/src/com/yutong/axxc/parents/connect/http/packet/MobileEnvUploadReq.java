
package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.Tools;


/**
 * 手机环境上传请求
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class MobileEnvUploadReq extends AbstractReq{
    /** 终端手机号码 */
    private String msisdn;
    
    /** 终端机型 */
    private String mode_numb;
    
    /** 终端品牌 */
    private String brand;
    
    /** 终端屏幕分辨率，分辨率格式为“长*宽”，例：1024*768 */
    private String reso_rati;
    
    /** 运营商类型，0-wifi；1-中国移动2G；2-中国移动3G；3-中国电信2G；4-中国电信3G；5-中国联通2G；6-中国联通3G */
    private String t_mobile;
    
    /** 操作系统版本，格式为操作系统名称+版本号，例：android 4.1.1 */
    private String os_vers;
    
    /** 安芯移动应用软件版本 */
    private String soft_vers;
    
    /** 客户端唯一标识 */
    private String imei;
    
    /** 用户所在地市，需上传含有市或县的名称，例如：沈阳市或新民县 */
    private String city;
    
    /** 用户所在省会，需上传含有省的名称，例如：辽宁省 */
    private String province;

    /**
     * @param msisdn The msisdn to set.
     */
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * @param mode_numb The mode_numb to set.
     */
    public void setMode_numb(String mode_numb) {
        this.mode_numb = mode_numb;
    }

    /**
     * @param brand The brand to set.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @param reso_rati The reso_rati to set.
     */
    public void setReso_rati(String reso_rati) {
        this.reso_rati = reso_rati;
    }

    /**
     * @param t_mobile The t_mobile to set.
     */
    public void setT_mobile(String t_mobile) {
        this.t_mobile = t_mobile;
    }

    /**
     * @param os_vers The os_vers to set.
     */
    public void setOs_vers(String os_vers) {
        this.os_vers = os_vers;
    }

    /**
     * @param soft_vers The soft_vers to set.
     */
    public void setSoft_vers(String soft_vers) {
        this.soft_vers = soft_vers;
    }

    /**
     * @param imei The imei to set.
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * @param city The city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @param province The province to set.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try {
            JSONObject req = new JSONObject();
            req.put("time", Tools.getFullCurrentTime());
            req.put("msisdn", msisdn);
            req.put("mode_numb", mode_numb);
            req.put("brand", brand);
            req.put("reso_rati", reso_rati);
            req.put("t_mobile", t_mobile);
            req.put("os_vers", os_vers);
            req.put("soft_vers", soft_vers);
            req.put("imei", imei);
            req.put("city", city);
            req.put("province", province);
            return req.toString();
 
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[手机软硬件环境上传请求类]：组包生成请求时失败，详细信息：", e);
            return null;
        }
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/log/envinfo";
    }

}
