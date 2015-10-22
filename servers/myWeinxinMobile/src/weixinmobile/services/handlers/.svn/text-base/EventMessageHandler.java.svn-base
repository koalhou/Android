package weixinmobile.services.handlers;

import weixinFundation.core.common.WeixinMessageHandler;
import weixinFundation.core.events.GuanzhuEventMessage;
import weixinFundation.core.events.LocationEventMessage;
import weixinFundation.core.events.MenuEventMessage;
import weixinFundation.core.messages.TextMessageReply;
import weixinFundation.core.utils.EventTypeUtil;
import weixinFundation.core.utils.IReplyMessage;
import weixinFundation.core.utils.LogHelper;

public class EventMessageHandler implements WeixinMessageHandler {
	public static final String WELCOME_MESSAGE_TXT = "欢迎你关注我，你可以发送关键字 公司 ，电话，等关键字看看。";

	@Override
	public boolean handleMsg(String messageType, String messageBody,
			WeixinMessageHandlerResult resultOutput) {
		// 判读消息类型，按类型读取消息
		if ("event".equals(messageType)) {
			String eventType = EventTypeUtil.getEventType(messageBody);

			// 处理处理事件：关注事件
			/*
			 * 用户扫描带场景值二维码时，可能推送以下两种事件
			 * 如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
			 * 如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者。
			 */
			if ("subscribe".equals(eventType)) {
				GuanzhuEventMessage msg = GuanzhuEventMessage
						.fromXml(messageBody);
				LogHelper.i("接收到事件： 关注");
				if (msg.EventKey != null) {
					LogHelper.i("用户 未关注 时的事件，而又扫描了二维码 关注了我们时" + msg.EventKey);
				}
				TextMessageReply replayMsg = null;
				replayMsg = TextMessageReply.createTextReplyMessage(
						msg.ToUserName, msg.FromUserName, WELCOME_MESSAGE_TXT);
				String replayMsgStr = replayMsg.toXml();
				resultOutput.result = replayMsgStr;
				return true;
			} else if ("unsubscribe".equals(eventType)) {
				GuanzhuEventMessage msg = GuanzhuEventMessage
						.fromXml(messageBody);
				LogHelper.i("接收到事件： 取消关注");
				// 处理 删除记录的用户信息处理等。
				return true;
			} else if ("SCAN".equals(eventType)) {// 用户已关注时的事件，而又扫描了二维码时
				GuanzhuEventMessage msg = GuanzhuEventMessage
						.fromXml(messageBody);
				LogHelper.i("如果用户已经关注公众号，微信带场景值扫描事件:" + msg.EventKey);
				// 处理 删除记录的用户信息处理等。
				return true;
			}

			// 处理事件：菜单相关
			else if ("CLICK".equals(eventType)) {// 点击菜单拉取消息时的事件推送
				MenuEventMessage msg = MenuEventMessage.fromXml(messageBody);
				LogHelper.i("触发事件 点击菜单事件推送：" + msg.EventKey);
				IReplyMessage replyMeg = MenuEventTalk.talk(msg);
				resultOutput.result = replyMeg.toXml();
				return true;
			} else if ("VIEW".equals(eventType)) {// 点击菜单跳转链接时的事件推送
				MenuEventMessage msg = MenuEventMessage.fromXml(messageBody);
				LogHelper.i("触发事件 链接事件推送：" + msg.EventKey);
				IReplyMessage replyMeg = MenuEventTalk.talk(msg);
				resultOutput.result = replyMeg.toXml();
				return true;
			}

			// 用户同意上报地理位置后，每次进入公众号会话时，都会在进入时上报地理位置，
			// 或在进入会话后每5秒上报一次地理位置，公众号可以在公众平台网站中修改以上设置。
			// 上报地理位置时，微信会将上报地理位置事件推送到开发者填写的URL。
			else if ("LOCATION".equals(eventType)) {// 点击菜单拉取消息时的事件推送
				LocationEventMessage msg = LocationEventMessage.fromXml(messageBody);
				LogHelper.i(String.format("触发事件 上报地理位置事件：%s,%s,%s" , msg.Latitude,msg.Longitude,msg.Precision));
				return true;
			}
		}
		return false;
	}
}
