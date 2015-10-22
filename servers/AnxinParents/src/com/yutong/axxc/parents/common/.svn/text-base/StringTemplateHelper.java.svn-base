package com.yutong.axxc.parents.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串模板简单解析帮助类。
 * 字符串模板的格式：${name}与${time}在${station}下车。
 * 
 * @author zhangyongn
 *
 */
public class StringTemplateHelper {
	/**
	 * 解析字符串模板
	 * @param tpl，模板，字符串模板的格式：${name}与${time}在${station}下车。
	 * @param data，替换的数据字典，其中的key即模板里的${name}中的name。
	 * @return 返回解析后的字符串,如果data中无模板内指定的变量，则直接填写变量名。
	 */
	public static String parse(String tpl, Map<String, String> data) {
		
		// sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。
				StringBuffer sb = new StringBuffer();
				try{
					Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
					Matcher matcher = pattern.matcher(tpl);
					while(matcher.find()){
						String name = matcher.group(1);// 键名
						String value = (String)data.get(name);// 键值
						if(value == null){
							value = name;
						}else{
							value = value.replaceAll("\\$", "\\\\\\$");
						}
						matcher.appendReplacement(sb, value);
					}
					
					matcher.appendTail(sb);
				}catch(Exception e){
					e.printStackTrace();
				}
				return sb.toString();	
	}
	public static void main(String[] args){
		String ss="${name}与${time}在${station}下车。";
		Map<String,String> data=new HashMap<String,String>();
		data.put("name", "果果");
		data.put("time", "12:30");
		String ssr=parse(ss,data);
		
	}
}
