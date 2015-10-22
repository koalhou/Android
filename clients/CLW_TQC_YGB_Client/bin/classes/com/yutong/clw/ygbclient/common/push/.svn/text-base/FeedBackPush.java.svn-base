/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-20 上午9:04:34
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.push;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.MsgPushType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindType;

/**
 * 厂外推送消息实体类
 * 
 * @author zhangzhia 2013-11-20 上午9:04:34
 */
public class FeedBackPush extends AbstractPush {

	public String msg_id;
	public String advise_time;
	public String advise;

	public String reply_time;
	public String reply;

	/**
	 * 解析内容
	 * 
	 * @param jsonObject
	 * @return
	 */
	boolean parseContent() {
		try {
			if (!msgPushType.equals(MsgPushType.Unknow)&& this.contentJsonObj != null) {
				msg_id = contentJsonObj.optString("msg_id");
				advise_time = contentJsonObj.optString("advise_time");
				advise = contentJsonObj.optString("advise");

				reply_time = contentJsonObj.optString("reply_time");
				reply = contentJsonObj.optString("reply");

				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Logger.e(this.getClass(), "[厂外推送消息实体类]:解析收到的【公告推送】消息出错，详细信息：", e);
			return false;
		}
	}

}
