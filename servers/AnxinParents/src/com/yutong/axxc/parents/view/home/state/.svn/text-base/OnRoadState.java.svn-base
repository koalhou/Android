package com.yutong.axxc.parents.view.home.state;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.yutong.axxc.parents.business.line.GetRealTimeInfoBiz;
import com.yutong.axxc.parents.business.student.GetStudentRidingRecordBiz;
import com.yutong.axxc.parents.business.student.GetStudentStatusBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.StringTemplateHelper;
import com.yutong.axxc.parents.common.SysConfigUtil;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YTException;
import com.yutong.axxc.parents.common.beans.RidingRecordBean;
import com.yutong.axxc.parents.common.beans.StationRealTimeInfoBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.view.common.MessageBuilder;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.MessageBuilder.curmsgconfig;
import com.yutong.axxc.parents.view.common.StudentStateConstant;
import com.yutong.axxc.parents.view.home.MainMapActivity;
import com.yutong.axxc.parents.view.home.PushMessage;

public class OnRoadState extends BaseState {

	private GetStudentRidingRecordBiz busDetailBiz; // 跟车信息
	private GetRealTimeInfoBiz predictBiz; // 预计路程
	private StationRealTimeInfoBean predictInfo;
	private RidingRecordBean detailInfo;

	private Timer timer = new Timer();
	private TimerTask task;
	private int DELAY = 2000;

	private int PERIOD = 1000 * 30;

	private Handler handler = null;
	private boolean isStart = false;
	private final static int SUCCESS = 0;
	private final static int NODATA = 1;
	private final static int STATUSCHANGE = 2;

	public OnRoadState() {
		this.setSrvState(StudentStateConstant.SRV_SCHOOL_AT_BUS);
		this.setSrvStateText("上学在校车上");
		initHandler();
		initParam();

	}

	private void initParam() {
		try {
			int delay = Integer.parseInt(SysConfigUtil.getProperty(
					"INDEXPAGE_TIMER_PERIOD", "30"));
			PERIOD = 1000 * delay;
		} catch (Exception e) {
			PERIOD = 1000 * 30;
		}
	}

	private void initHandler() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				PushMessage newmsg = (PushMessage) msg.obj;
				try {
					if (!isStart) {
						Logger.i(getClass(), "首页时间轴：定时器已经关闭.");
						return;
					}

					int what = msg.what;
					switch (what) {
					case SUCCESS:
					case NODATA:
						onMsgUpdate(newmsg);
						break;
					case STATUSCHANGE:
						stopTimer();
						Logger.i(getClass(), "定时获取状态，状态改变。");
						// Toast.makeText(
						// YtApplication.getInstance()
						// .getApplicationContext(), "学生状态已变化。",
						// Toast.LENGTH_SHORT).show();

						break;
					}

				} catch (Exception e) {
					Logger.e(this.getClass(), e.getMessage());
				}
				super.handleMessage(msg);
			}

		};

	}

	private void triger(int returntype) {
		Message message = new Message();
		message.what = returntype;
		PushMessage p = getPushMessage();
		message.obj = p;
		handler.sendMessage(message);
	}

	@Override
	public void init(Activity activity) {
		super.init(activity);

	}

	protected void startGetMsgInfo() {

		try {
			predictBiz = new GetRealTimeInfoBiz(this.getActivity(), null, this
					.getStudent().getCld_id(), null, null, null);

			busDetailBiz = new GetStudentRidingRecordBiz(getActivity(), null,
					this.getStudent().getCld_id());
			GetStudentStatusBiz statusBiz = new GetStudentStatusBiz(
					getActivity(), null, this.getStudent().getCld_id());

			// 先查状态
			String status = statusBiz.getStatus();
			if (!ifNeedPredict(status)) {
				Logger.i(getClass(), "状态发生变化，停止定时器。");
				triger(STATUSCHANGE);
				return;
			}

			this.predictInfo = predictBiz.getStationRealTimeInfo();
			this.detailInfo = busDetailBiz.getRidingRecordInfo();
			buildMsg();
			triger(SUCCESS);

		} catch (YTException yte) {
			if (yte.getStatus() == ThreadCommStateCode.TOKEN_INVALID) {
				Logger.i(getClass(), "tocken invalid。");
				triger(STATUSCHANGE);
				return;
			}
			Logger.e(getClass(), yte.getStatus() + ";" + yte.getMessage());
			buildMsg();
			triger(NODATA);
		} catch (Exception e) {

			e.printStackTrace();
			Logger.e(this.getClass(), e.getMessage());
		}

	}

	/**
	 * 只有如下状态，才有预估值：上学前、在车上（上学，放学）
	 * 
	 * @param status
	 * @return
	 */
	private boolean ifNeedPredict(String status) {
		boolean b = StringUtil.equals(StudentStateConstant.SRV_AFTER_AT_BUS,
				status)
				|| StringUtil.equals(StudentStateConstant.SRV_SCHOOL_AT_BUS,
						status)
				|| StringUtil.equals(
						StudentStateConstant.SRV_AFTER_OFFBUS_WAIT, status)
				|| StringUtil.equals(
						StudentStateConstant.SRV_SCHOOL_ONBUS_WAIT, status);
		return b;
	}

	private String buildDefaultBody() {

		String body = "在校车上";
		try {
			String name = this.getStudent().getFullName();
			String plate = detailInfo.getVehicle_plate();
			body = name + "在" + plate + "校车上。";
			return body;
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
			return body;
		}
	}

	private void buildMsg() {
		try {
			PushMessage msg = this.getPushMessage();
			Map<String, String> data = MessageBuilder.buildDataMap(this
					.getStudent());
			if (data == null)
				data = new HashMap<String, String>();

			if (predictInfo != null) {
				// data.put("", value)
				String predictvalue = buildpredictValue(predictInfo);
				data.put("predict_value", predictvalue);
				data.put("station_name", predictInfo.getStationFullName());
				data.put("vehicle_plate", predictInfo.getVehicle_plate());
				curmsgconfig config = this.getconfig();
				String body = this.getSrvStateText();

				if (config != null) {
					body = StringTemplateHelper.parse(config.getBody(), data);
				}
				msg.setBody(body);
				msg.setTime(Tools.getFormatTime(Calendar.getInstance(), "HH:mm"));
			} else {

				// msg.setBody("未获取到估算数据。");
				msg.setBody(buildDefaultBody());

			}
			if (detailInfo != null) {
				msg.setBus_vin(detailInfo.getVehicle_vin());
				msg.setDriver(detailInfo.getDriver() == "" ? "未刷卡" : detailInfo
						.getDriver());
				msg.setSpeed(detailInfo.getVehicle_speed());
				msg.setTeachername(detailInfo.getTeacher() == "" ? "未刷卡"
						: detailInfo.getTeacher());
				msg.setTeacherphone(detailInfo.getTeacher_phone());
			}

			this.setPushMessage(msg);
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}

	@Override
	public void start(StudentInfoBean student) {
		super.start(student);
		startTimer();
	}

	@Override
	public void stop() {
		super.stop();
		stopTimer();

		// GetRealTimeInfoBiz.dis = 3000;// test
	}

	@Override
	public void destroy() {
		this.stop();

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

				// Map<String, String> data =
				// MessageBuilder.buildDataMap(student);
				//
				// String body = getBody(config, data);
				String body = "正在获取估算信息...";
				msg.setBody(body);// 这里先显示静态信息，待定时器获取到数据后，刷新。

				Calendar cal = Calendar.getInstance();
				msg.setTime(Tools.getFormatTime(cal, "HH:mm"));
				msg.setStudent(student);
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
		return msg;
	}

	private void startTimer() {
		if (isStart) {
			Logger.i(this.getClass(), "定时器已经启动,不能重复启动。");
			return;
		}
		if (timer == null) {
			timer = new Timer();
		}
		if (task == null) {
			task = new TimerTask() {
				@Override
				public void run() {
					Logger.d(getClass(),
							"--------------------------------------onroadstate run...");
					startGetMsgInfo();

				}
			};
		}

		timer.schedule(task, DELAY, PERIOD);
		isStart = true;
	}

	private void stopTimer() {

		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (task != null) {
			task.cancel();
			task = null;
		}
		isStart = false;
	}

	private String buildpredictValue(StationRealTimeInfoBean predict) {
		try {
			if (predict == null)
				return "";
			String v = "";
			if (StationRealTimeInfoBean.REMIND_TYPE_BY_TIME.equals(predict
					.getRemind_type())) {
				v = predict.getRemind_value() + "分钟";

			} else if (StationRealTimeInfoBean.REMIND_TYPE_BY_DISTANT
					.equals(predict.getRemind_type())) {
				float d = Float.parseFloat(predict.getRemind_value());
				d = d / 1000;
				v = String.format("%.1f 公里", d);
			} else {
				v = predict.getRemind_value() + "个站";
			}
			return v;
		} catch (Exception e) {
			Logger.e(this.getClass(), e.getMessage());
			return "";
		}
	}
}
