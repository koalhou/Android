package weixinFundation.core.messages;

import weixinFundation.core.utils.IReplyMessage;

/**
 * 视频消息 回复
 * @author yunfei
 *
 */
public class VideoMessageReply  implements IReplyMessage{
	// ToUserName 是 接收方帐号（收到的OpenID）
	// FromUserName 是 开发者微信号
	// CreateTime 是 消息创建时间 （整型）
	// MsgType 是 video
	// MediaId 是 通过上传多媒体文件，得到的id
	// Title 否 视频消息的标题
	// Description 否 视频消息的描述
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType;
	public String MediaId;
	public String Title;
	public String Description;
	public String toXml() {
		return VideoMessageReplyWriter.toXml(this);
	}
}
