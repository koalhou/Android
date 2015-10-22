/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-13 下午3:53:28
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bean.push;

import java.io.Serializable;
import java.util.Date;

import com.yutong.clw.ygbclient.common.enums.MsgPushType;
import com.yutong.clw.ygbclient.common.push.AbstractPush;
import com.yutong.clw.ygbclient.common.push.FeedBackPush;
import com.yutong.clw.ygbclient.common.push.FactoryInsidePush;
import com.yutong.clw.ygbclient.common.push.FactoryOuterPush;
import com.yutong.clw.ygbclient.common.push.NewsPush;

/**
 * @author zhouzc 2013-11-13 下午3:53:28
 * 
 */
public class PushBean  implements Serializable{

	/**
	 * @author zhouzc 2013-12-2 下午1:53:10
	 *
	 */
	private static final long serialVersionUID = -3850410799935083245L;

	private MsgPushType type;

	private Date time;
	
	public MsgPushType getType() {
		return type;
	}

	public void setType(MsgPushType type) {
		this.type = type;
	}
	

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public static PushBean parseFromCommonMessage(AbstractPush msg) {
		if (msg == null)
			return null;
		if (msg instanceof NewsPush) {
			AnnouncePushBean bean = new AnnouncePushBean();
			NewsPush p = (NewsPush) msg;
			bean.setType(MsgPushType.News);
			bean.setId(p.news_id);
			bean.setTime(p.news_time);
			bean.setTitle(p.news_title);
			bean.setDescription(p.news_summary);
			bean.setTime(p.event_time);
			return bean;
		} else if (msg instanceof FactoryInsidePush) {
			AlarmPushBean bean = new AlarmPushBean();
			FactoryInsidePush p = (FactoryInsidePush) msg;
			bean.setType(MsgPushType.FactoryInside);
			bean.setRemindId(p.remind_id);
			bean.setAreaType(p.area_type);
			bean.setStationId(p.station_id);
			bean.setVehicleLat(p.vehicle_lat);
			bean.setVehicleLon(p.vehicle_lon);
			bean.setVehicleNumber(p.vehicle_number);
			bean.setLineid(p.line_id);
			bean.setVehicleVin(p.vehicle_vin);
			bean.setStationName(p.station_name);
			bean.setStationAlias(p.station_alias);
			bean.setTime(p.event_time);
			//add By
			bean.setRuleId(p.getRule_id());
			
			return bean;
		} else if (msg instanceof FactoryOuterPush) {
			
			AlarmPushBean bean = new AlarmPushBean();
			FactoryOuterPush p = (FactoryOuterPush) msg;
			bean.setType(MsgPushType.FactoryOuter);
			bean.setRemindId(p.remind_id);
			bean.setAreaType(p.area_type);
			bean.setRemindValue(p.remind_value);
			bean.setRemindRange(p.remind_range);
			bean.setRemindType(p.remind_type);
			bean.setStationId(p.station_id);
			bean.setVehicleLat(p.vehicle_lat);
			bean.setVehicleLon(p.vehicle_lon);
			bean.setVehicleNumber(p.vehicle_number);
			bean.setVehicleVin(p.vehicle_vin);
			bean.setStationName(p.station_name);
			bean.setStationAlias(null);
			bean.setTime(p.event_time);
			bean.setStatusRange(p.status_range);
			bean.setLineid(p.line_id);
			bean.setLinename(p.line_name);
			//add By
			bean.setRuleId(p.getRule_id());
			
			return bean;
		}else if (msg instanceof FeedBackPush) {
			
			FeedBackPush p = (FeedBackPush) msg;
			
			FeedBackPushBean bean = new FeedBackPushBean();
			bean.msgID = p.msg_id;
			bean.adviseTime = p.advise_time;
			bean.advise = p.advise;
			bean.replyTime = p.reply_time;
			bean.reply = p.reply;
			
			return bean;
		}
		return null;
	}



}
