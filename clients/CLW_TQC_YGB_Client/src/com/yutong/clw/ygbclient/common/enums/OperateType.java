package com.yutong.clw.ygbclient.common.enums;

/**
 * 操作类型，增删改
 * @author zhangzhia 2013-10-21 下午2:07:48
 *
 */
public enum OperateType
{
    /**
     * 新增,0
     */
    Add(0, "新增"),
    /**
     * 修改,1
     */
    Modify(1, "修改"),
    /**
     * 删除,2
     */
    Delete(2, "删除");

    private int _value;

    private String _name;

    private OperateType(int value, String name)
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
