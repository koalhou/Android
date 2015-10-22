/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:31:19
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.AccountType;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 验证账号请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:31:19
 */
public class VerifyAccountReq extends AbstractReq
{
    /** 员工账号，可以为员工号，手机号码 */
    private String account;

    /**
     * 账号类型： 0-所有类型 1-员工号 2-手机号码 为0时数据服务自行判断所有账号类型。
     */
    private AccountType type;

    @Override
    public String packetMsgBody()
    {
        try
        {

            JSONObject req = new JSONObject();
            req.put("account", account);
            req.put("type", type.value());
            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[验证账号请求信息类]:组包验证账号请求消息时失败，详细信息：", e);
            return null;
        }
    }

    /** 员工账号，可以为员工号，手机号码 */
    public void setAccount(String account)
    {
        this.account = account;
    }

    public void setType(AccountType type)
    {
        this.type = type;
    }
    
    
}
