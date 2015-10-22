package weixinmobile.services.handlers;

import java.util.Date;
import java.util.Random;

import weixinFundation.core.utils.DateUtil;
import weixinFundation.core.messages.TextMessage;

/**
 * 对话
 * 
 * @author yunfei
 * 
 */
public class TalkManager {

	/**
	 * 处理回复
	 * 
	 * @param content
	 * @return
	 */
	public static boolean talk(String content, TaskResponse response) {
		if ("2".equals(content) || content.contains("公司")) {
			response.content = "北京派得伟业科技发展有限公司  - 农业资源管理事业部";
			return true;
		} else if ("3".equals(content) || content.contains("电话")) {
			response.content = "我们的联系电话是：010-51503798";
			return true;
		}else if ("4".equals(content)) {
			response.content = "更多信息请查看：<a href='http://www.baidu.com'>详情</a>";
			return true;
		}
		
		return false;
		// return String.format("“%s”??? 没听清，你再说一遍。", content);
	}

	public static class TaskResponse {
		public String content;
	}
}
