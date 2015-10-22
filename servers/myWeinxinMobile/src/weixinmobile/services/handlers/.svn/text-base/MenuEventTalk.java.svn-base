package weixinmobile.services.handlers;

import weixinFundation.core.events.MenuEventMessage;
import weixinFundation.core.messages.TextAndImageMessageReply;
import weixinFundation.core.messages.TextMessageReply;
import weixinFundation.core.messages.TextAndImageMessageReply.Article;
import weixinFundation.core.utils.DateUtil;
import weixinFundation.core.utils.IReplyMessage;

public class MenuEventTalk {

	public static IReplyMessage talk(MenuEventMessage msg) {
		String eventKey = msg.EventKey;
		// 农业知识
		if ("v1_nongyezhishi".equals(eventKey)) {
			TextAndImageMessageReply replyMsg = new TextAndImageMessageReply();
			replyMsg.ToUserName = msg.FromUserName;
			replyMsg.FromUserName = msg.ToUserName;
			replyMsg.CreateTime = DateUtil.now() + "";

			Article art = new Article();
			art.Title = "Article标题1";
			art.Description = "Article描述";
			art.PicUrl = "http://mp.weixin.qq.com/wiki/skins/common/images/weixin_wiki_logo.png";
			art.Url = "http://mp.weixin.qq.com/wiki/index.php?title=%E9%A6%96%E9%A1%B5";
			replyMsg.Articles.add(art);
			replyMsg.ArticleCount = replyMsg.Articles.size();
			return replyMsg;
		} else if ("v1_gongneng".equals(eventKey)) {
			TextMessageReply replayMsg = null;
			replayMsg = TextMessageReply.createTextReplyMessage(msg.ToUserName,
					msg.FromUserName,TextMessageHandler.DEFALUT_MESSAGE);
			return replayMsg;
		} else if ("v1_gongsijianjie".equals(eventKey)) {// 公司简介
			return createReply(msg, "北京派得伟业科技发展有限公司  - 农业资源管理事业部");
		} else if ("V1_LIANXIFANGSHI".equals(eventKey)) {// 联系方式
			return createReply(msg, "我们的联系电话是：010-51503798");
		} else {
			return createReply(msg, "收到事件：" + eventKey);
		}
	}

	private static TextMessageReply createReply(MenuEventMessage msg,
			String content) {
		return TextMessageReply.createTextReplyMessage(msg.ToUserName,
				msg.FromUserName, content);
	}
}
