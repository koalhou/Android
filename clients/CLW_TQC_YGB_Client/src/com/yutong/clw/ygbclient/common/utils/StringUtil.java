package com.yutong.clw.ygbclient.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;

public final class StringUtil extends StringUtils
{

    /**
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
     */
    private static final char DBC_CHAR_START = 33; // 半角!

    /**
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
     */
    private static final char DBC_CHAR_END = 126; // 半角~

    /**
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
     */
    private static final char SBC_CHAR_START = 65281; // 全角！

    /**
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
     */
    private static final char SBC_CHAR_END = 65374; // 全角～

    /**
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
     */
    private static final int CONVERT_STEP = 65248; // 全角半角转换间隔

    /**
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
     */
    private static final char SBC_SPACE = 12288; // 全角空格 12288

    /**
     * 半角空格的值，在ASCII中为32(Decimal)
     */
    private static final char DBC_SPACE = ' '; // 半角空格

    /**
     * 验证输入字符串是否包包含空格，tab，是否为null
     * 
     * @param str
     * @return
     */
    public static boolean containSpace(String str)
    {
        boolean result = false;
        if (str == null || str.equals(EMPTY))
        {
            result = true;
        }
        else
        {
            char[] strChars = str.toCharArray();
            for (char strChar : strChars)
            {
                if (Character.isWhitespace(strChar))
                {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 转换Object为String类型
     * 
     * @param sequence
     * @return String
     */
    public static String parseStr(Object object)
    {
        if (object != null)
            return String.valueOf(object).trim();
        else
            return null;
    }

    /**
     * 转换CharSequence为String类型
     * 
     * @param sequence
     * @return String
     */
    public static String parseStr(CharSequence sequence)
    {
        if (sequence != null)
        {
            return String.valueOf(sequence);
        }
        else
        {
            return null;
        }
    }

    /**
     * <PRE>
     * 全角字符->半角字符转换   
     * 只处理全角的空格，全角！到全角～之间的字符，忽略其他
     * </PRE>
     */
    public static String qj2bj(String src)
    {
        if (src == null)
        {
            return src;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++)
        {
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END)
            { // 如果位于全角！到全角～区间内
                buf.append((char) (ca[i] - CONVERT_STEP));
            }
            else if (ca[i] == SBC_SPACE)
            { // 如果是全角空格
                buf.append(DBC_SPACE);
            }
            else
            { // 不处理全角空格，全角！到全角～区间外的字符
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }

    /**
     * <PRE>
     * 半角字符->全角字符转换   
     * 只处理空格，!到&tilde;之间的字符，忽略其他
     * </PRE>
     */
    public static String bj2qj(String src)
    {
        if (src == null)
        {
            return src;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < ca.length; i++)
        {
            if (ca[i] == DBC_SPACE)
            { // 如果是半角空格，直接用全角空格替代
                buf.append(SBC_SPACE);
            }
            else if ((ca[i] >= DBC_CHAR_START) && (ca[i] <= DBC_CHAR_END))
            { // 字符是!到~之间的可见字符
                buf.append((char) (ca[i] + CONVERT_STEP));
            }
            else
            { // 不对空格以及ascii表中其他可见字符之外的字符做任何处理
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 提取信息中的网络链接:(h|H)(r|R)(e|E)(f|F) *= *('|")?(\w|\\|\/|\.)+('|"| *|>)?
     * 提取信息中的邮件地址:\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*
     * 提取信息中的图片链接:(s|S)(r|R)(c|C) *= *('|")?(\w|\\|\/|\.)+('|"| |>)?
     * 提取信息中的IP地址:(\d+)\.(\d+)\.(\d+)\.(\d+) 提取信息中的中国手机号码:(86)*0*13\d{9}
     * 提取信息中的中国固定电话号码:(\(\d{3,4}\)|\d{3,4}-|\s)?\d{8}
     * 提取信息中的中国电话号码（包括移动和固定电话）:(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}
     * 提取信息中的中国邮政编码:[1-9]{1}(\d+){5} 提取信息中的中国身份证号码:\d{18}|\d{15} 提取信息中的整数：\d+
     * 提取信息中的浮点数（即小数）：(-?\d*)\.?\d+ 提取信息中的任何数字 ：(-?\d*)(\.\d+)?
     * 提取信息中的中文字符串：[\u4e00-\u9fa5]* 提取信息中的双字节字符串 (汉字)：[^\x00-\xff]*
     */

    public static final String PATTERN_TEL = "(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)";

    /**
     * 从输入字符串中提取对应pattern的内容
     * 
     * @param res
     * @param pattern_str
     * @return
     */
    public static String getSpecContent(String res, String pattern_str)
    {
        Pattern pattern = Pattern.compile(pattern_str);
        Matcher matcher = pattern.matcher(res);
        StringBuilder bf = new StringBuilder(64);
        while (matcher.find())
        {
            bf.append(matcher.group()).append("|");
        }
        int len = bf.length();
        if (len > 0)
        {
            bf.deleteCharAt(len - 1);
        }
        return bf.toString();
    }
    /**
     * 屏蔽手机号中间四位,比如：13555558888，会得到：135****8888
     *@author zhangyongn 2013-11-1 下午4:30:02
     *
     * @param phone
     * @return
     */
    public static String maskPhone(String phone)
    {
        try
        {
            return phone.substring(0, 3) + "****" + phone.substring(7, 11);
        }
        catch (Exception err)
        {
            return phone;
        }
    }

    /**
     * 清除字符串的回车换行，前后空格
     * 
     * @param str
     * @return
     */
    public static String replaceBlank(String str)
    {
        try
        {
            String dest = str;
            if (str != null)
            {
                dest = str.replaceAll("\\s*|\\t|\\r|\\n", "");

            }
            return dest;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return str;
        }

    }

    /**
     * 获取url的扩展名
     * 
     * @author zhangzhia 2013-10-29 上午9:20:45
     * @param url
     * @return
     */
    @SuppressWarnings("unused")
    public static String getUrlSuffix(String url)
    {

        String suffix = null;
        String[] strs = url.split("/");
        if (strs != null && strs.length > 0)
        {
            String temp = strs[strs.length - 1];
            String[] temps = StringUtils.split(temp, ".");
            if (temps != null && temps.length > 0)
            {
                suffix = temps[temps.length - 1];
            }
        }

        return suffix;
    }

    // 替换、过滤特殊字符
    /**
     * 处理Toast中的字符问题
     * 
     * @author 
     * @param String
     * @return String
     */
    public static String StringFilter(String str) throws PatternSyntaxException{
        str=str.replaceAll("【","[").replaceAll("】","]").replaceAll("！","!");//替换中文标号
        String regEx="[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
     return m.replaceAll("").trim();
    }
    
    public static String ToDBC(String input) {
    	   char[] c = input.toCharArray();
    	   for (int i = 0; i< c.length; i++) {
    	       if (c[i] == 12288) {
    	         c[i] = (char) 32;
    	         continue;
    	       }if (c[i]> 65280&& c[i]< 65375)
    	          c[i] = (char) (c[i] - 65248);
    	       }
    	   return new String(c);
    	}



}
