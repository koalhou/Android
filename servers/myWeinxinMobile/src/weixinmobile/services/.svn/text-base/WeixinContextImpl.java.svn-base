package weixinmobile.services;

import weixinFundation.core.common.LinkedMessageWeixinContext;
import weixinmobile.services.handlers.EventMessageHandler;
import weixinmobile.services.handlers.TextMessageHandler;

public class WeixinContextImpl extends LinkedMessageWeixinContext{
	public static final String Token = "helloweixin";
	
	public static final String appID = "wx875459130cdcb3f7";
	public static final String appsecret = "6defcd0de76908408ecf37a4527d97b6";
	
	@Override
	public void onCreate() {
		addHander(new TextMessageHandler());
		addHander(new EventMessageHandler());
	}

	@Override
	public String getToken() {
		return Token;
	}



}
