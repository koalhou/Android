package weixinFundation.core.events;

/**
ToUserName	开发者微信号
FromUserName	 发送方帐号（一个OpenID）
CreateTime	 消息创建时间 （整型）
MsgType	 消息类型，event
Event	 事件类型，CLICK
EventKey	 事件KEY值，与自定义菜单接口中KEY值对应
 * 
 * 
 * 关注或 取消关注 消息
 * @author yunfei
 * 
 */
public class MenuEventMessage {
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType;
	public String Event;
	public String EventKey;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s=%s", "ToUserName", ToUserName));
		sb.append(String.format("%s=%s", "FromUserName", FromUserName));
		sb.append(String.format("%s=%s", "CreateTime", CreateTime));
		sb.append(String.format("%s=%s", "MsgType", MsgType));
		sb.append(String.format("%s=%s", "Event", Event));
		sb.append(String.format("%s=%s", "EventKey", EventKey));
		return sb.toString();
	}
	
	public static MenuEventMessage fromXml(String xml){
		return MenuEventMessageReader.fromXml(xml);
	}
}
