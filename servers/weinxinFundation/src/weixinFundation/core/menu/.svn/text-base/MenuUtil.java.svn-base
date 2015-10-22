package weixinFundation.core.menu;

import net.sf.json.JSONObject;
import weixinFundation.core.utils.HttpsUtil;
import weixinFundation.core.common.AccessTokenUtil;

public class MenuUtil {

	/**
	 * 创建菜单
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static boolean createMenu(String appid,String secret,String menuDescriptionStr) {
		if(appid == null)
			throw new NullPointerException();
		if(secret == null)
			throw new NullPointerException();
		String accessToken = AccessTokenUtil.getAccessToken(appid,secret);
		
		String urlStr = String
				.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s",
						accessToken);
		String resStr = HttpsUtil.post(urlStr, menuDescriptionStr);
		if (resStr == null)
			return false;
		JSONObject json = JSONObject.fromObject(resStr);
		String errcode = json.getString("errcode");
		String errmsg = json.getString("errmsg");
		if (errcode.equals("0"))
			return true;
		else
			return false;
		
	}


}
