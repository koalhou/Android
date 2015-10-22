package weixinFundation.core.messages;

public class ImageMessage {
	// ToUserName 开发者微信号
	// FromUserName 发送方帐号（一个OpenID）
	// CreateTime 消息创建时间 （整型）
	// MsgType image
	// PicUrl 图片链接
	// MediaId 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	// MsgId 消息id，64位整型
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType;
	public String PicUrl;
	public String MediaId;
	public String MsgId;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s=%s", "ToUserName", ToUserName));
		sb.append(String.format("%s=%s", "FromUserName", FromUserName));
		sb.append(String.format("%s=%s", "CreateTime", CreateTime));
		sb.append(String.format("%s=%s", "MsgType", MsgType));
		sb.append(String.format("%s=%s", "PicUrl", PicUrl));
		sb.append(String.format("%s=%s", "MediaId", MediaId));
		sb.append(String.format("%s=%s", "MsgId", MsgId));
		return sb.toString();
	}
	public static ImageMessage fromXml(String xml){
		return ImageMessageReader.readTextMessage(xml);
	}
}
