package weixinFundation.core.messages;

/**
 * 链接消息
 * @author yunfei
 *
 */
public class LinkMessage {
	// ToUserName 接收方微信号
	// FromUserName 发送方微信号，若为普通用户，则是一个OpenID
	// CreateTime 消息创建时间
	// MsgType 消息类型，link
	// Title 消息标题
	// Description 消息描述
	// Url 消息链接
	// MsgId 消息id，64位整型
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType;
	public String Title;
	public String Description;
	public String Url;
	public String MsgId;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s=%s", "ToUserName",ToUserName));
		sb.append(String.format("%s=%s", "FromUserName",FromUserName));
		sb.append(String.format("%s=%s", "CreateTime",CreateTime));
		sb.append(String.format("%s=%s", "MsgType",MsgType));
		sb.append(String.format("%s=%s", "Title",Title));
		sb.append(String.format("%s=%s", "Description",Description));
		sb.append(String.format("%s=%s", "Url",Url));
		sb.append(String.format("%s=%s", "MsgId",MsgId));
		return sb.toString();
	}
	public static LinkMessage fromXml(String xml){
		return LinkMessageReader.readTextMessage(xml);
	}
}
