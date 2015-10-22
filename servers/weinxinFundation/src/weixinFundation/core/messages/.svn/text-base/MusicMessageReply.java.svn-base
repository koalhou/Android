package weixinFundation.core.messages;

import weixinFundation.core.utils.IReplyMessage;

/**
 * 回复音乐消息
 * 
 * @author yunfei
 * 
 */
public class MusicMessageReply implements IReplyMessage {
	// ToUserName 是 接收方帐号（收到的OpenID）
	// FromUserName 是 开发者微信号
	// CreateTime 是 消息创建时间 （整型）
	// MsgType 是 music
	// Title 否 音乐标题
	// Description 否 音乐描述
	// MusicURL 否 音乐链接
	// HQMusicUrl 否 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	// ThumbMediaId 是 缩略图的媒体id，通过上传多媒体文件，得到的id
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType = "music";
	public MusicItem Music = new MusicItem();
	
	public static class MusicItem{
		public String Title;
		public String Description;
		public String MusicUrl;
		public String HQMusicUrl;
		public String ThumbMediaId;
	}
	
	public String toXml() {
		return MusicMessageWriter.toXml(this);
	}
}
