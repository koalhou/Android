package weixinFundation.core.messages;

import weixinFundation.core.utils.IReplyMessage;

public class ImageMessageReply implements IReplyMessage{
	// ToUserName 是 接收方帐号（收到的OpenID）
	// FromUserName 是 开发者微信号
	// CreateTime 是 消息创建时间 （整型）
	// MsgType 是 image
	// MediaId 是 通过上传多媒体文件，得到的id。
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType;
	public String MediaId;

	public String toXml() {
		return ImageMessageWriter.toXml(this);
	}
}
