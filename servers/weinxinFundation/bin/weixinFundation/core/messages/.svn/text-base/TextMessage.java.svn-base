package weixinFundation.core.messages;

/**
 * 文本消息
 * 
 * ToUserName 开发者微信号 FromUserName 发送方帐号（一个OpenID） CreateTime 消息创建时间 （整型） MsgType
 * text Content 文本消息内容 MsgId 消息id，64位整型
 * 
 * @author yunfei
 * 
 */
public class TextMessage {
	/*
	 * <ToUserName><![CDATA[toUser]]></ToUserName>
	 * <FromUserName><![CDATA[fromUser]]></FromUserName>
	 * <CreateTime>1348831860</CreateTime> <MsgType><![CDATA[text]]></MsgType>
	 * <Content><![CDATA[this is a test]]></Content>
	 * <MsgId>1234567890123456</MsgId>
	 */
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType = "text";
	public String Content;
	public String MsgId;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s=%s", "ToUserName", ToUserName));
		sb.append(String.format("%s=%s", "FromUserName", FromUserName));
		sb.append(String.format("%s=%s", "CreateTime", CreateTime));
		sb.append(String.format("%s=%s", "MsgType", MsgType));
		sb.append(String.format("%s=%s", "Content", Content));
		sb.append(String.format("%s=%s", "MsgId", MsgId));
		return sb.toString();
	}

	public static TextMessage fromXml(String xml){
		return TextMessageReader.readTextMessage(xml);
	}
}
