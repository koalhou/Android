/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-6 下午2:48:01
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.validation.regulars.AbstractRegularRule;

/**
 * 数据有效性验证
 * 
 * @author zhangzhia 2013-11-6 下午2:48:01
 */
public class DataValidation
{
    /**
     * @param ruleString [$ruleString$][$ruleString$]
     * 
     * ruleString 为以下情况
     * 
     *        Custom("正则表达式", "验证不通过描述")
     *            示例：Custom("\d+","必须为数字，且至少包含1个数字") 
     *        Len("最小长度", "最大长度")
     *            示例：Len(1,4)
     *        Num() 或 Num("最小长度", "最大长度")
     *            示例：Num 或者 Num(1,4)
     *        NoChar(字符1字符2...)
     *            示例：NoChar("%@") 
     *        NoEmptyRule
     *            示例：NoEmptyRule 
     *        PhoneRule 
     *            示例：PhoneRule
     *        EmailRule 
     *            示例：EmailRule
     */
    private static List<AbstractRegularRule> parseRuleString(String ruleStrings)
    {
        List<AbstractRegularRule> rules = new ArrayList<AbstractRegularRule>();

        List<String> ruleStringlist = new ArrayList<String>();

        /*Pattern pattern = Pattern.compile("(?<=(\\[\\$))(.*?)(?=(\\$\\]))");*/
        Pattern pattern = Pattern.compile("(?<=(\\[\n.\\$))(.*?)(?=(\\$\\]))");
        Matcher matcher = pattern.matcher(ruleStrings);
        while (matcher.find())
        {
            ruleStringlist.add(matcher.group());
        }

        for (String ruleString : ruleStringlist)
        {
            AbstractRegularRule rule = ExpressionManager.getInstance().parseRuleString(ruleString);
            if (rule != null)
            {
                rules.add(rule);
            }
            else
            {
                Logger.e(DataValidation.class, "[数据有效性验证]未解析规则类型：" + ruleString);
            }
        }

        return rules;
    }

    /**
     * 默认发现一项未通过后返回
     *@author zhangzhia 2013-11-13 下午4:25:44
     *
     * @param ruleStrings   规则项字符串，格式：[$规则一$][$规则二$]
     * @param str   验证的字符串
     * @return
     */
    public static VerifyResult validate(String ruleStrings, String str)
    {
        return validate(ruleStrings, str, true);
    }

    /**
     * 验证字符串是否满足验证条件
     * 
     * @author zhangzhia 2013-11-7 上午11:40:26
     * @param str 检验的字符串
     * @param single true: 发现一项未通过后返回 false: 发现多项未通过后返回
     * @return
     */
    public static VerifyResult validate(String ruleStrings, String str, boolean single)
    {
        List<AbstractRegularRule> rules = parseRuleString(ruleStrings);
        VerifyResult verifyResult = new VerifyResult();

        //无验证规则
        if (rules.size() == 0)
        {
            return verifyResult;
        }

        for (AbstractRegularRule rule : rules)
        {
            VerifyResult item = rule.verifyRegular(str);

            if (single)
            {
                if (!item.isPass)
                {
                    verifyResult = item;
                    break;
                }
            }
            else
            {
                if (!item.isPass)
                {
                    verifyResult.verifyResults.add(item);
                }
            }

        }

        if (!single && verifyResult.verifyResults.size() > 0)
        {
            verifyResult.isPass = false;
        }

        return verifyResult;
    }
    
}
