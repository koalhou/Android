package com.yutong.axxc.parents.view.home.state;

import java.util.Map;

import android.app.Activity;

import com.yutong.axxc.parents.common.StringTemplateHelper;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.MessageBuilder;
import com.yutong.axxc.parents.view.common.MessageBuilder.curmsgconfig;
import com.yutong.axxc.parents.view.home.PushMessage;

public abstract class BaseState implements ISrvViewState {

	private String srvState;
	private String srvStateText;
	private StudentInfoBean student;
	private PushMessage pushMessage;
	private Activity activity;

	private IOnMsgUpdateListener mlistener;

	public void addOnMsgUpdatedListener(IOnMsgUpdateListener listener) {
		this.mlistener = listener;
	}

	public void removeOnMsgUpdatedListener() {
		this.mlistener = null;
	}

	protected void onMsgUpdate(PushMessage msg) {
		if (this.mlistener != null)
			this.mlistener.onMessageUpdated(msg);
	}

	@Override
	public void init(Activity activity) {
		this.setActivity(activity);
	}

	@Override
	public void start(StudentInfoBean student) {
		this.setStudent(student);

	}

	@Override
	public void stop() {
		

	}

	

	@Override
	public void destroy() {

	}

	/**
	 * @return the srvState
	 */
	public String getSrvState() {
		return srvState;
	}

	/**
	 * @param srvState
	 *            the srvState to set
	 */
	public void setSrvState(String srvState) {
		this.srvState = srvState;
	}

	public curmsgconfig getconfig() {
		return MessageBuilder.getCurMsgConfig(srvState);
	}

	public String getBody(curmsgconfig config, Map<String, String> data) {
		if (config == null)
			return null;
		return StringTemplateHelper.parse(config.getBody(), data);
	}

	/**
	 * @return the student
	 */
	public StudentInfoBean getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            the student to set
	 */
	public void setStudent(StudentInfoBean student) {
		this.student = student;
	}

	/**
	 * @return the pushMessage
	 */
	protected PushMessage getPushMessage() {
		if (pushMessage == null) {
			pushMessage = new PushMessage();
			pushMessage.setType(PushMessage.CURRENTMSG);
		}
		return pushMessage;
	}

	/**
	 * @param pushMessage
	 *            the pushMessage to set
	 */
	public void setPushMessage(PushMessage pushMessage) {
		this.pushMessage = pushMessage;
	}

	/**
	 * @return the srvStateText
	 */
	public String getSrvStateText() {
		return srvStateText;
	}

	/**
	 * @param srvStateText
	 *            the srvStateText to set
	 */
	public void setSrvStateText(String srvStateText) {
		this.srvStateText = srvStateText;
	}

	/**
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
