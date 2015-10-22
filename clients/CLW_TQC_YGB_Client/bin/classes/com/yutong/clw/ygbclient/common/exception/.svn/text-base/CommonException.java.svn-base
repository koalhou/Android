/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-24 上午11:12:09
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.exception;

import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.enums.ErrorInfoType;

/**
 * 自定义通用异常
 * 
 * @author zhangzhia 2013-10-24 上午11:12:09
 */
public class CommonException extends Exception
{
    private static final long serialVersionUID = 1L;

    public CommonNetStatus status;

    public String error_code;

    public String error_des;

    public ErrorInfoType errorInfoType;

    public CommonException()
    {
        super();
    }

    public CommonException(CommonNetStatus status)
    {
        super();
        this.status = status;
    }

    public CommonException(CommonNetStatus status, String error_code, String error_des)
    {
        super();
        this.status = status;
        this.error_code = error_code;
        this.error_des = error_des;
        this.errorInfoType = ErrorInfoType.myValueOf(Integer.valueOf(error_code));
    }

    public CommonException(String msg)
    {
        super(msg);
    }
}
