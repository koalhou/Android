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
 * 不能包含特殊字符
 * 
 * @author zhangzhia 2013-11-8 上午10:26:42
 */
public class NotContainSpecialCharacterRule extends AbstractRegularRule
{

    private String specialChars;

    public NotContainSpecialCharacterRule(String specialChars)
    {
        this.specialChars = specialChars; // ^*$#

        this.faildesc = String.format("不能包含%s特殊字符", this.specialChars);

    }

    @Override
    public VerifyResult verifyRegular(String str)
    {
        VerifyResult verifyResult = new VerifyResult();

        char[] array = this.specialChars.toCharArray();

        for (char c : array)
        {
            String regular = String.format("[%c]+", c);
            Pattern pattern = Pattern.compile(regular);
            Matcher matcher = pattern.matcher(str);
            boolean isPass = matcher.find();

            if (isPass)
            {
                verifyResult.isPass = false;
                verifyResult.faildesc = faildesc;
                break;
            }
        }

        return verifyResult;

    }
}
