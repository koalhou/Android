package com.yutong.axxc.parents.view.home.state;

import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.MessageBuilder;
import com.yutong.axxc.parents.view.common.MessageBuilder.curmsgconfig;
import com.yutong.axxc.parents.view.common.StudentStateConstant;
import com.yutong.axxc.parents.view.home.PushMessage;

public class InClassState extends BaseState {
	

	public InClassState() {
		this.setSrvState(StudentStateConstant.SRV_IN_CLASS);
		this.setSrvStateText("上课中");
	}

	@Override
	public PushMessage getMsg(StudentInfoBean student) {
		PushMessage msg = this.getPushMessage();

		if (student != null) {
			curmsgconfig config = this.getconfig();
			msg.setShowCall(config.isCall());
			msg.setShowDetail(config.isBusdetail());
			msg.setShowGps(config.isGps());

			Map<String, String> data = MessageBuilder.buildDataMap(student);

			String body = getBody(config, data);
			msg.setBody(body == null ? this.getSrvStateText() : body);

			Calendar cal = Calendar.getInstance();
			msg.setTime(Tools.getFormatTime(cal, "HH:mm"));
			msg.setStudent(student);
		}
		return msg;
	}

}
