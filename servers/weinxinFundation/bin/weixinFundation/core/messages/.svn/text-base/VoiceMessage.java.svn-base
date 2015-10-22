package weixinFundation.core.messages;

public class VoiceMessage {
	// ToUserName 开发者微信号
	// FromUserName 发送方帐号（一个OpenID）
	// CreateTime 消息创建时间 （整型）
	// MsgType 语音为voice
	// MediaId 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
	// Format 语音格式，如amr，speex等
	// MsgID 消息id，64位整型
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType;
	public String MediaId;
	public String Format;
	public String MsgID;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s=%s", "ToUserName", ToUserName));
		sb.append(String.format("%s=%s", "FromUserName", FromUserName));
		sb.append(String.format("%s=%s", "CreateTime", CreateTime));
		sb.append(String.format("%s=%s", "MsgType", MsgType));
		sb.append(String.format("%s=%s", "MediaId", MediaId));
		sb.append(String.format("%s=%s", "Format", Format));
		sb.append(String.format("%s=%s", "MsgID", MsgID));
		return sb.toString();
	}
	public static VoiceMessage fromXml(String xml){
		return VoiceMessageReader.readTextMessage(xml);
	}
}
