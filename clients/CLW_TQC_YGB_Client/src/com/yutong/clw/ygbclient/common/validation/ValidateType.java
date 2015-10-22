package com.yutong.clw.ygbclient.common.validation;

/**
 * 验证类型
 * 
 * @author zhangzhia 2013-10-21 下午1:16:47
 */
public enum ValidateType
{
    // 自定义类型
    CustomRule(0, "CustomRule", "Custom"),
    // 长度范围限制
    LengthRangeRule(1, "LengthRangeRule", "Len"),
    // 仅包含数字
    OnlyNumberRule(2, "OnlyNumberRule", "Num"),
    // 手机号码
    PhoneRule(3, "PhoneRule", "Phone"),
    // 邮箱格式
    EmailRule(4, "EmailRule", "Email"),
    // 输入字符串不能为空或者全部都是空格
    NotEmptyRule(5, "NotEmptyRule", "NotEmpty"),
    // 不能包含特殊字符
    NotContainSpecialCharacterRule(6, "NotContainSpecialCharacterRule", "NoChar");

    private int _value;

    private String _name;

    private String _simple;

    private ValidateType(int value, String name, String simple)
    {
        _value = value;
        _name = name;
        _simple = simple;
    }

    public int value()
    {
        return _value;
    }

    public String getName()
    {
        return _name;
    }

    public String getSimple()
    {
        return _simple;
    }

    // public static ValidateType myValueOf(int value)
    // {
    //
    // switch (value) {
    // case 0:
    // return CustomExpression;
    // case 1:
    // return LengthRangeRule;
    // case 2:
    // return OnlyNumberExpression;
    // case 3:
    // return PhoneExpression;
    // default:
    // throw new IndexOutOfBoundsException("枚举数组角标越界");
    // }
    // }
}
