package com.yutong.clw.ygbclient.common.enums.remind;

/**
 * 提醒开启状态
 * 
 * @author zhangzhia 2013-10-21 下午2:05:56
 */
public enum RemindStatus
{
    /**
     * 关闭,0
     */
    Close(0, "关闭"),
    /**
     * 开启,1
     */
    Open(1, "开启"),
    /**
     * 删除,2
     */
    Delete(2, "删除"),
    /**
     * 不再提醒,3
     */
    NoRemind(3, "不再提醒");

    private int _value;

    private String _name;

    private RemindStatus(int value, String name)
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

    public static RemindStatus myValueOf(int value)
    {
        switch (value)
        {
        case 0:
            return Close;
        case 1:
            return Open;
        case 2:
            return Delete;
        case 3:
            return NoRemind;

        default:
            throw new IndexOutOfBoundsException("枚举数组角标越界");
        }
    }
}
