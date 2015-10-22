package com.yutong.axxc.parents.connect.http.packet;

import com.yutong.axxc.parents.common.beans.StudentCustomInfoBean;

/**
 * 设置学生个性信息响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class SetStudentCustomInfoRes extends AbstractRes
{

    /** 学生信息 */
    private StudentCustomInfoBean studentCustomInfoBean;

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString)
    {
        return true;

    }

}
