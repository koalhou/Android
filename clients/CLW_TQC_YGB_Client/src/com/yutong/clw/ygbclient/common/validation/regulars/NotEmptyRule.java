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
 * 不能为空
 * @author zhangzhia 2013-11-8 上午10:26:42
 *
 */
public class NotEmptyRule extends AbstractRegularRule
{
    private String regular;
    
    public NotEmptyRule()
    {
         this.regular = String.format("\\S+");
        
         this.faildesc = String.format("输入内容不能为空");
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

