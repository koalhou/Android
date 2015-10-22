package weixinmobile.services.handlers;

import weixinFundation.core.common.WeixinMessageHandler;
import weixinFundation.core.messages.MusicMessageReply;
import weixinFundation.core.messages.TextAndImageMessageReply;
import weixinFundation.core.messages.TextAndImageMessageReply.Article;
import weixinFundation.core.messages.TextMessage;
import weixinFundation.core.messages.TextMessageReply;
import weixinFundation.core.utils.DateUtil;
import weixinFundation.core.utils.LogHelper;
import weixinmobile.services.handlers.TalkManager.TaskResponse;

public class TextMessageHandler implements WeixinMessageHandler {

	@Override
	public boolean handleMsg(String messageType, String messageBody,
			WeixinMessageHandlerResult result) {
		// 判读消息类型，按类型读取消息
		if ("text".equals(messageType)) {
			TextMessage msg = TextMessage.fromXml(messageBody);
			String content = msg.Content;
			if("1".equals(content)){//模拟图文消息
				TextAndImageMessageReply replyMsg = new TextAndImageMessageReply();
				replyMsg.ToUserName = msg.FromUserName;
				replyMsg.FromUserName = msg.ToUserName;
				replyMsg.CreateTime = DateUtil.now()+"";
				
				Article art = new Article();
				art.Title = "Article标题1";
				art.Description = "Article描述";
				art.PicUrl = "http://mp.weixin.qq.com/wiki/skins/common/images/weixin_wiki_logo.png";
				art.Url = "http://mp.weixin.qq.com/wiki/index.php?title=%E9%A6%96%E9%A1%B5";
				replyMsg.Articles.add(art);
				replyMsg.ArticleCount = replyMsg.Articles.size();
				
				result.result = replyMsg.toXml();
				return true;
			}else if ("5".equals(content)) {
				MusicMessageReply replyMsg = new MusicMessageReply();
				replyMsg.ToUserName = msg.FromUserName;
				replyMsg.FromUserName = msg.ToUserName;
				replyMsg.CreateTime = DateUtil.now()+"";
				
				replyMsg.Music.Title = "我的未来不是梦";
				replyMsg.Music.Description = "经典的一首歌";
				replyMsg.Music.MusicUrl = "http://117.78.17.107/myWeinxinMobile/wodeweilaibushimeng.mp3";
				replyMsg.Music.HQMusicUrl = "http://117.78.17.107/myWeinxinMobile/wodeweilaibushimeng.mp3";
				replyMsg.Music.ThumbMediaId = null;
				
				result.result = replyMsg.toXml();
				return true;
			}
			// 根据对白内容，返回对话
			TaskResponse response = new TaskResponse();
			if ( TalkManager.talk(content, response)) {
				String replyMsgStr = response.content;
				TextMessageReply replayMsg = null;
				replayMsg = TextMessageReply.createTextReplyMessage(msg.ToUserName,
						msg.FromUserName, replyMsgStr);
				String replayMsgStr = replayMsg.toXml();
				result.result = replayMsgStr;
				return true;
			}
			//当用户的消息，没有对应的处理内容。默认处理。
			TextMessageReply replayMsg = null;
			replayMsg = TextMessageReply.createTextReplyMessage(msg.ToUserName,
					msg.FromUserName, DEFALUT_MESSAGE);
			String replayMsgStr = replayMsg.toXml();
			result.result = replayMsgStr;
			return true;
			
		}
		return false;
	}

	public static final String DEFALUT_MESSAGE = "请根据提示选择你的操作:\r\n" +
			"1.图文消息示例。\r\n" +
			"2.公司名称。\r\n" +
			"3.联系方式。\r\n" +
			"4.回复文本消息带超链接的演示。\r\n" +
			"5.回复音乐消息示例。\r\n";

}
