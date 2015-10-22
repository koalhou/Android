package com.yutong.axxc.parents.view.home.state;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.StringTemplateHelper;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.beans.WeatherInfoBean;
import com.yutong.axxc.parents.view.common.MessageBuilder;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.MessageBuilder.curmsgconfig;
import com.yutong.axxc.parents.view.common.StudentStateConstant;
import com.yutong.axxc.parents.view.home.PushMessage;

public class UnknownState extends BaseState {

	public UnknownState() {
		this.setSrvState(StudentStateConstant.SRV_UNKNOWN);
		this.setSrvStateText("未知状态");
	}

	@Override
	public PushMessage getMsg(StudentInfoBean student) {
		PushMessage msg = this.getPushMessage();
		try {
			if (student != null) {
				curmsgconfig config = this.getconfig();
				msg.setShowCall(config.isCall());
				msg.setShowDetail(config.isBusdetail());
				msg.setShowGps(config.isGps());
				Map<String, String> data = MessageBuilder.buildDataMap(student);

				WeatherInfoBean weather = YtApplication.getInstance()
						.getWeatherInfoCache();
				if (weather != null) {
					data.put("temp", weather.getTodayTemper());
					data.put("pm", weather.getTodayPM());
					data.put("wind", weather.getTodayWind());
				}

				String body = getBody(config, data);
				msg.setBody(body == null ? this.getSrvStateText() : body);

				Calendar cal = Calendar.getInstance();
				msg.setTime(Tools.getFormatTime(cal, "HH:mm"));
				msg.setStudent(student);
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
		return msg;
	}
}
