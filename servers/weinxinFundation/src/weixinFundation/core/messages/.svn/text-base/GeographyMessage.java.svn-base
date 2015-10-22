package weixinFundation.core.messages;

public class GeographyMessage {
	// ToUserName 开发者微信号
	// FromUserName 发送方帐号（一个OpenID）
	// CreateTime 消息创建时间 （整型）
	// MsgType location
	// Location_X 地理位置维度
	// Location_Y 地理位置经度
	// Scale 地图缩放大小
	// Label 地理位置信息
	// MsgId 消息id，64位整型
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType;
	public String Location_X;
	public String Location_Y;
	public String Scale;
	public String Label;
	public String MsgId;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s=%s", "ToUserName", ToUserName));
		sb.append(String.format("%s=%s", "FromUserName", FromUserName));
		sb.append(String.format("%s=%s", "CreateTime", CreateTime));
		sb.append(String.format("%s=%s", "MsgType", MsgType));
		sb.append(String.format("%s=%s", "Location_X", Location_X));
		sb.append(String.format("%s=%s", "Location_Y", Location_Y));
		sb.append(String.format("%s=%s", "Scale", Scale));
		sb.append(String.format("%s=%s", "Label", Label));
		sb.append(String.format("%s=%s", "MsgId", MsgId));
		return sb.toString();
	}

	public static GeographyMessage fromXml(String xml) {
		return GeographyReader.readTextMessage(xml);
	}
}
