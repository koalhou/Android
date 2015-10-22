package weixinFundation.core.messages;

import java.util.ArrayList;
import java.util.List;

import weixinFundation.core.utils.IReplyMessage;

/**
 * 图文消息 回复。
 * @author yunfei
 *
 */
public class TextAndImageMessageReply implements IReplyMessage {
	// ToUserName 是 接收方帐号（收到的OpenID）
	// FromUserName 是 开发者微信号
	// CreateTime 是 消息创建时间 （整型）
	// MsgType 是 news
	// ArticleCount 是 图文消息个数，限制为10条以内
	// Articles 是 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	// Title 否 图文消息标题
	// Description 否 图文消息描述
	// PicUrl 否 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	// Url 否 点击图文消息跳转链接
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType = "news";
	public int ArticleCount;
	public List<Article> Articles = new ArrayList<Article>();

	public String toXml() {
		return TextAndImageWriter.toXml(this);
	}
	
	public static class Article{
		public String Title;
		public String Description;
		public String PicUrl;
		public String Url;
	}
}
