/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-8 上午11:40:54
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yutong.clw.ygbclient.common.utils.ArrayUtil;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.common.validation.regulars.AbstractRegularRule;
import com.yutong.clw.ygbclient.common.validation.regulars.CustomRule;
import com.yutong.clw.ygbclient.common.validation.regulars.LengthRangeRule;
import com.yutong.clw.ygbclient.common.validation.regulars.NotContainSpecialCharacterRule;
import com.yutong.clw.ygbclient.common.validation.regulars.NotEmptyRule;
import com.yutong.clw.ygbclient.common.validation.regulars.OnlyNumberRule;
import com.yutong.clw.ygbclient.common.validation.regulars.PhoneRule;

/**
 * @author zhangzhia 2013-11-8 上午11:40:54
 */
public class ExpressionManager
{

    private static ExpressionManager singleton = null;

    public static synchronized ExpressionManager getInstance()
    {
        if (singleton == null)
        {
            singleton = new ExpressionManager();
        }
        return singleton;
    }

    private ExpressionManager()
    {
    }

    public AbstractRegularRule parseRuleString(String ruleString)
    {
        AbstractRegularRule rule = null;
        // RegularRuleExpression expression = new CustomExpression();
        //
        // if (!expression.parseType(ruleString))
        // {
        // expression = new DefaultExpression();
        // }

        ValidateType validateType = parseType(ruleString);

        if (validateType != null)
        {
            Pattern pattern = Pattern.compile("(?<=\\()(.*?)(?=\\))");
            Matcher matcher = pattern.matcher(ruleString);

            String paramString = null;
            if (matcher.find())
            {
                paramString = matcher.group();
            }

            String[] params = null;
            if (StringUtil.isNotBlank(paramString))
            {
                params = paramString.split(",");
            }

            String faildesc = "";
            int find_index = -1;
            for (int i = 0; i < params.length; i++)
            {
                // 查找自定义错误描述
                Pattern patternfaildesc = Pattern.compile("(?<=(<@))(.*?)(?=(@>))");
                Matcher matcherfaildesc = patternfaildesc.matcher(params[i]);

                if (matcherfaildesc.find())
                {
                    faildesc = matcherfaildesc.group();
                    find_index = i;
                    break;
                }
            }

            if (find_index != -1)
            {
                params = ArrayUtil.remove(params, find_index);
            }

            switch (validateType)
            {
            case CustomRule:
                rule = new CustomRule(params[0], params[1]);
                break;
            case LengthRangeRule:
                rule = new LengthRangeRule(Integer.valueOf(params[0]), Integer.valueOf(params[1]));
                break;
            case OnlyNumberRule:

                if (params == null || params.length == 0)
                {
                    rule = new OnlyNumberRule();
                }
                else
                {
                    rule = new OnlyNumberRule(Integer.valueOf(params[0]), Integer.valueOf(params[1]));
                }

                break;
            case PhoneRule:

                rule = new PhoneRule();
                break;
            case NotEmptyRule:

                rule = new NotEmptyRule();
                break;
            case NotContainSpecialCharacterRule:

                rule = new NotContainSpecialCharacterRule(paramString);
                break;
            default:
                break;
            }

            if (StringUtil.isNotBlank(faildesc))
            {
                rule.setFaildesc(faildesc);
            }
        }

        return rule;
    }

    public ValidateType parseType(String ruleString)
    {

        Pattern pattern = Pattern.compile("^" + ValidateType.CustomRule.getSimple());
        Matcher matcher = pattern.matcher(ruleString);
        if (matcher.find())
        {
            return ValidateType.CustomRule;
        }

        pattern = Pattern.compile("^" + ValidateType.LengthRangeRule.getSimple());
        matcher = pattern.matcher(ruleString);
        if (matcher.find())
        {
            return ValidateType.LengthRangeRule;
        }

        pattern = Pattern.compile("^" + ValidateType.OnlyNumberRule.getSimple());
        matcher = pattern.matcher(ruleString);
        if (matcher.find())
        {
            return ValidateType.OnlyNumberRule;
        }
        pattern = Pattern.compile("^" + ValidateType.PhoneRule.getSimple());
        matcher = pattern.matcher(ruleString);
        if (matcher.find())
        {
            return ValidateType.PhoneRule;
        }

        pattern = Pattern.compile("^" + ValidateType.EmailRule.getSimple());
        matcher = pattern.matcher(ruleString);
        if (matcher.find())
        {
            return ValidateType.EmailRule;
        }

        pattern = Pattern.compile("^" + ValidateType.NotEmptyRule.getSimple());
        matcher = pattern.matcher(ruleString);
        if (matcher.find())
        {
            return ValidateType.NotEmptyRule;
        }

        pattern = Pattern.compile("^" + ValidateType.NotContainSpecialCharacterRule.getSimple());
        matcher = pattern.matcher(ruleString);
        if (matcher.find())
        {
            return ValidateType.NotContainSpecialCharacterRule;
        }

        return null;
    }

}
