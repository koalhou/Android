/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-8 上午10:26:42
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.validation.regulars;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yutong.clw.ygbclient.common.validation.VerifyResult;


/**
 * 必须包含数字
 * 
 * @author zhangzhia 2013-11-8 上午10:26:42
 */
public class LengthRangeRule extends AbstractRegularRule
{
    private String regular;
    
//    public LengthRangeRule(int LenMin, boolean equal)
//    {
//        if (equal)
//        {
//            this.regular = String.format("^.{%d}$", LenMin);
//
//            this.faildesc = String.format("数据长度为%d位", LenMin);
//        }
//        else
//        {
//            this.regular = String.format("^\\S{%d,}$", LenMin);
//
//            this.faildesc = String.format("数据长度至少大于%d位", LenMin);
//        }
//    }

    public LengthRangeRule(int LenMin, int LenMax)
    {
        this.regular = String.format("^.{%d,%d}$", LenMin, LenMax);
        this.faildesc = String.format("输入内容长度在%d位与%d位之间", LenMin, LenMax);
    }


    @Override
    public VerifyResult verifyRegular(String str)
    {
        VerifyResult verifyResult = new VerifyResult();

        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(str);
        boolean isPass = matcher.find();

        if (!isPass)
        {
            verifyResult.isPass = false;
            verifyResult.faildesc = faildesc;
        }

        return verifyResult;

    }
}
