/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-8 上午8:21:27
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.validation.regulars;

import com.yutong.clw.ygbclient.common.validation.VerifyResult;


/**
 * @author zhangzhia 2013-11-8 上午8:21:27
 */
public abstract class AbstractRegularRule
{
    /**
     * 验证不通过描述
     */
    protected String faildesc;

    public String getFaildesc()
    {
        return faildesc;
    }

    public void setFaildesc(String faildesc)
    {
        this.faildesc = faildesc;
    }

    public AbstractRegularRule()
    {

    }

    /**
     * 验证字符串是否满足验证条件
     * 
     * @author zhangzhia 2013-11-7 上午11:40:26
     * @param str 检验的字符串
     * @return
     */
    public abstract VerifyResult verifyRegular(String str);

}
