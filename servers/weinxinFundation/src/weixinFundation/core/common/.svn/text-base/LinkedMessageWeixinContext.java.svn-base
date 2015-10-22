package weixinFundation.core.common;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import weixinFundation.core.common.WeixinMessageHandler.WeixinMessageHandlerResult;

/**
 * 微信服务的 上下文环境
 * @author yunfei
 *
 */
public abstract class LinkedMessageWeixinContext extends WeixinContext{
	LinkedList<WeixinMessageHandler> mHandlers;
	
	public LinkedMessageWeixinContext() {
		super();
		mHandlers = new LinkedList<WeixinMessageHandler>();
	}

	public void addHander(WeixinMessageHandler handler){
		mHandlers.add(handler);
	}
	
	public void removeHander(WeixinMessageHandler handler){
		mHandlers.remove(handler);
	}
	
	public Collection<WeixinMessageHandler> getHandlers() {
		return Collections.unmodifiableCollection(mHandlers);
	}
	
	public abstract String getToken();

	/**
	 * 处理 消息。遍历所有的 handler。如果某个handler处理成，则停止。
	 * @param messageType
	 * @param messageBody
	 * @return
	 */
	public String handleMsg(String messageType, String messageBody) {
		WeixinMessageHandlerResult result  = new WeixinMessageHandlerResult();
		for (WeixinMessageHandler item : mHandlers) {
			if(item == null)
				continue;
			if(item.handleMsg(messageType, messageBody, result)){
				break;
			}
		}
		return result.result;
	}

}
