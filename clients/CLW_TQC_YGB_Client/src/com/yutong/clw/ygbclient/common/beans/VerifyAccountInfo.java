/**
 * @公司名称：YUTONG
 * @作者：lizyi
 * @版本号：1.0
 * @生成日期：2013-11-1 下午1:24:10
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans;

/**
 * 验证账号信息
 * 
 * @author lizyi 2013-11-1 下午1:24:10
 */
public class VerifyAccountInfo
{
    /**
     * 员工号，数据库唯一
     */
    public String emp_code;

    /**
     * 是否绑定手机号
     */
    public boolean phonebind;

    /**
     * 已绑定的手机号
     */
    public String phone;

}
