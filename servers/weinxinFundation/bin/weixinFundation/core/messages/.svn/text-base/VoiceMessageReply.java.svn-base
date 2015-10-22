package weixinFundation.core.messages;

import weixinFundation.core.utils.IReplyMessage;

public class VoiceMessageReply  implements IReplyMessage{
	// ToUserName 是 接收方帐号（收到的OpenID）
	// FromUserName 是 开发者微信号
	// CreateTime 是 消息创建时间戳 （整型）
	// MsgType 是 语音，voice
	// MediaId 是 通过上传多媒体文件，得到的id
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType = "voice";
	public String MediaId;

	public String toXml() {
		return VoiceMessageReplyWriter.toXml(this);
	}
}
