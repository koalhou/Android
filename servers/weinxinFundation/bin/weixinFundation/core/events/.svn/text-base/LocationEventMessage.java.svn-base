package weixinFundation.core.events;

/**
 * 用户同意上报地理位置后，每次进入公众号会话时，都会在进入时上报地理位置，或在进入会话后每5秒上报一次地理位置，公众号可以在公众平台网站中修改以上设置。上报地理位置时，微信会将上报地理位置事件推送到开发者填写的URL。

推送XML数据包示例：

<xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[fromUser]]></FromUserName>
<CreateTime>123456789</CreateTime>
<MsgType><![CDATA[event]]></MsgType>
<Event><![CDATA[LOCATION]]></Event>
<Latitude>23.137466</Latitude>
<Longitude>113.352425</Longitude>
<Precision>119.385040</Precision>
</xml>
参数说明：

参数	描述
ToUserName	开发者微信号
FromUserName	 发送方帐号（一个OpenID）
CreateTime	 消息创建时间 （整型）
MsgType	 消息类型，event
Event	 事件类型，LOCATION
Latitude	 地理位置纬度
Longitude	 地理位置经度
Precision	 地理位置精度
 * @author yunfei
 *
 */
public class LocationEventMessage {
	public String ToUserName;
	public String FromUserName;
	public String CreateTime;
	public String MsgType;
	public String Event;
	public String Latitude;
	public String Longitude;
	public String Precision;
	
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
		sb.append(String.format("%s=%s", "Latitude", Latitude));
		sb.append(String.format("%s=%s", "Longitude", Longitude));
		sb.append(String.format("%s=%s", "Precision", Precision));
		return sb.toString();
	}
	
	public static LocationEventMessage fromXml(String xml){
		return LocationEventMessageReader.fromXml(xml);
	}
}
