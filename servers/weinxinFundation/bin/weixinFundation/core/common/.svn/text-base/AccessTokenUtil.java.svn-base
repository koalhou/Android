package weixinFundation.core.common;

import net.sf.json.JSONObject;
import weixinFundation.core.utils.HttpsUtil;
import weixinFundation.core.utils.LogHelper;

/**
 * 
 access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。正常情况下access_token有效期为7200秒，
 * 重复获取将导致上次获取的access_token失效。
 * 
 * 公众号可以使用AppID和AppSecret调用本接口来获取access_token。AppID和AppSecret可在开发模式中获得（需要已经成为开发者
 * ，且帐号没有异常状态）。注意调用所有微信接口时均需使用https协议。
 * 
 * 接口调用请求说明
 * 
 * http请求方式: GET
 * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential
 * &appid=APPID&secret=APPSECRET 参数说明
 * 
 * 参数 是否必须 说明 grant_type 是 获取access_token填写client_credential appid 是 第三方用户唯一凭证
 * secret 是 第三方用户唯一凭证密钥，既appsecret 返回说明
 * 
 * 正常情况下，微信会返回下述JSON数据包给公众号：
 * 
 * {"access_token":"ACCESS_TOKEN","expires_in":7200} 参数 说明 access_token 获取到的凭证
 * expires_in 凭证有效时间，单位：秒 错误时微信会返回错误码等信息，JSON数据包示例如下（该示例为AppID无效错误）:
 * 
 * {"errcode":40013,"errmsg":"invalid appid"}
 * 
 * 120分钟后才过期
 * 
 * @author yunfei
 * 
 */
public class AccessTokenUtil {
	/**
	 * 获得 AccessToken
	 * 
	 * @return
	 */
	public static String getAccessToken(String appid, String secret) {
		String url1 = String
				.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
						appid, secret);

		String accessToken = HttpsUtil.get(url1);
		if (accessToken == null)
			return null;
		JSONObject json = JSONObject.fromObject(accessToken);
		String token = json.getString("access_token");
		String expires_in = json.getString("expires_in");
		LogHelper.i(String.format("收到Access Token:%s, %s", token, expires_in));
		if (token == null) {
			String errcode = json.getString("errcode");
			String errmsg = json.getString("errmsg");
			LogHelper.i(String
					.format("请求Access Token异常：%s,%s", errcode, errmsg));
		}
		return token;
	}

}
