package weixinFundation.core.events;

/**
 * 正常搜索到本用户时，并关注，将引发本事件，无Ticket字段的值；
 * 通过二维码扫描并关注时，也会引发本事件，同时具有Ticket字段的值指示二维码；
 * 
 * <xml> <ToUserName><![CDATA[toUser]]></ToUserName>
 * <FromUserName><![CDATA[FromUser]]></FromUserName>
 * <CreateTime>123456789</CreateTime> <MsgType><![CDATA[event]]></MsgType>
 * <Event><![CDATA[subscribe]]></Event> </xml>
 * 
 * ToUserName	开发者微信号
FromUserName	 发送方帐号（一个OpenID）
CreateTime	 消息创建时间 （整型）
MsgType	 消息类型，event
Event	 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
 * ***************************************************
 * 用户扫描带场景值二维码时，可能推送以下两种事件：
如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者。
1. 用户未关注时，进行关注后的事件推送

推送XML数据包示例：

<xml><ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[FromUser]]></FromUserName>
<CreateTime>123456789</CreateTime>
<MsgType><![CDATA[event]]></MsgType>
<Event><![CDATA[subscribe]]></Event>
<EventKey><![CDATA[qrscene_123123]]></EventKey>
<Ticket><![CDATA[TICKET]]></Ticket>
</xml>
 * 
 * 关注或 取消关注 消息
 * @author yunfei
 * 
 */
public class GuanzhuEventMessage {
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType;
	public String Event;
	
	/**
	 * 来自二维码关注时，本字段才有值
	 * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
	 */
	public String EventKey;
	
	/**
	 * 来自二维码关注时，本字段才有值
	 */
	public String Ticket;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s=%s", "ToUserName", ToUserName));
		sb.append(String.format("%s=%s", "FromUserName", FromUserName));
		sb.append(String.format("%s=%s", "CreateTime", CreateTime));
		sb.append(String.format("%s=%s", "MsgType", MsgType));
		sb.append(String.format("%s=%s", "Event", Event));
		sb.append(String.format("%s=%s", "Ticket", Ticket));
		return sb.toString();
	}
	
	public static GuanzhuEventMessage fromXml(String xml){
		return GuanzhuEventMessageReader.fromXml(xml);
	}
}
