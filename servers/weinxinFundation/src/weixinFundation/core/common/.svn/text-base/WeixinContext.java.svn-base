package weixinFundation.core.common;

//import java.util.Collection;
//import java.util.Collections;
//import java.util.LinkedList;
//
//import weixinFundation.core.common.WeixinMessageHandler.WeixinMessageHandlerResult;

/**
 * 微信服务的 上下文环境
 * @author yunfei
 *
 */
public abstract class WeixinContext {

	public abstract void onCreate();
	
	public abstract String getToken();

	/**
	 * 处理 消息。遍历所有的 handler。如果某个handler处理成，则停止。
	 * @param messageType
	 * @param messageBody
	 * @return
	 */
	public abstract String handleMsg(String messageType, String messageBody); 

}
