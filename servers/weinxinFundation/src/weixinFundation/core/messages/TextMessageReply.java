package weixinFundation.core.messages;

import weixinFundation.core.utils.DateUtil;
import weixinFundation.core.utils.IReplyMessage;

/**
 * 回复文本消息
 * @author yunfei
 *
 */
public class TextMessageReply  implements IReplyMessage{
/*
 * <xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[fromUser]]></FromUserName>
<CreateTime>12345678</CreateTime>
<MsgType><![CDATA[text]]></MsgType>
<Content><![CDATA[你好]]></Content>
</xml>
 * */
	
	
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType = "text";
	public String Content;

	public String toXml() {
		return TextMessageReplyWriter.toXml(this);
	}
	
	/**
	 * 根据一个消息，创建，回复消息。发件人和收件人地址要 互换
	 * 
	 * @param msg
	 * @return
	 */
	public static TextMessageReply createTextReplyMessage(String fromUserName,
			String toUserName, String replyString) {
		TextMessageReply reply = new TextMessageReply();
		reply.Content = replyString;//
		reply.ToUserName = toUserName;
		reply.FromUserName = fromUserName;
		reply.CreateTime = DateUtil.now() + "";
		return reply;
	}
}
