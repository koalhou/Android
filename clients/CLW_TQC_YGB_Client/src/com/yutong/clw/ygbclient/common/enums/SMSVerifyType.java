package com.yutong.clw.ygbclient.common.enums;

/**
 * 短信验证类型
 * 绑定手机,1  更换绑定手机,2  找回密码,3
 * @author zhangzhia 2013-10-21 下午2:19:47
 *
 */
public enum SMSVerifyType
{
    /**
     * 绑定手机,1
     */
    BINGPHONE(1, "绑定手机"),
    /**
     * 更换绑定手机,2
     */
    CHANGEBINDPHONE(2, "更换绑定手机"),
    /**
     * 找回密码,3
     */
    FINDPWD(3, "找回密码");

    private int _value;

    private String _name;

    private SMSVerifyType(int value, String name)
    {
        _value = value;
        _name = name;
    }

    public int value()
    {
        return _value;
    }

    public String getName()
    {
        return _name;
    }

}
