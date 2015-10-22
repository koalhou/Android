package com.yutong.axxc.parents.view.home.state;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.StudentStateConstant;
import com.yutong.axxc.parents.view.home.PushMessage;

public class SrvStateContext {
	private Map<String, BaseState> states;
	private BaseState currentState;
	private BaseState lastState;

	public void init(Activity activity) {
		states = new HashMap<String, BaseState>();
		BaseState state = null;
		// 未知状态
		state = new UnknownState();
		states.put(state.getSrvState(), state);

		// 上学前,和未知状态一致，new同样的状态实例
		state = new UnknownState();
		state.setSrvState(StudentStateConstant.SRV_SCHOOL_BEFORE);
		state.setSrvStateText("上学前");
		states.put(state.getSrvState(), state);

		// 上学在车上
		state = new OnRoadState();
		state.setSrvState(StudentStateConstant.SRV_SCHOOL_AT_BUS);
		state.setSrvStateText("上学在校车上");
		states.put(state.getSrvState(), state);

		// 上课中
		state = new InClassState();
		states.put(state.getSrvState(), state);

		// 放学在车上
		state = new OnRoadState();
		state.setSrvState(StudentStateConstant.SRV_AFTER_AT_BUS);
		state.setSrvStateText("放学在校车上");
		states.put(state.getSrvState(), state);

		// 已放学
		state = new InClassState();
		state.setSrvState(StudentStateConstant.SRV_AFTER_TAKEOFF);
		state.setSrvStateText("已放学");
		states.put(state.getSrvState(), state);

		// 上学上车等待
		state = new OnRoadState();
		state.setSrvState(StudentStateConstant.SRV_SCHOOL_ONBUS_WAIT);
		state.setSrvStateText("上学等待上车");
		states.put(state.getSrvState(), state);

		// 放学下车等待
		state = new OnRoadState();
		state.setSrvState(StudentStateConstant.SRV_AFTER_OFFBUS_WAIT);
		state.setSrvStateText("放学等待下车");
		states.put(state.getSrvState(), state);

		
		initStates(activity);
	}

	public void addListener(IOnMsgUpdateListener listener) {
		for (String key : states.keySet()) {

			BaseState value = states.get(key);
			value.addOnMsgUpdatedListener(listener);

		}
	}

	public void switchTo(String srvState, StudentInfoBean student) {
		if (lastState != null)
			lastState.stop();
		// test
		//srvState = StudentStateConstant.SRV_SCHOOL_AT_BUS;

		BaseState state = states.get(srvState);
		if (state != null) {
			state.start(student);
			lastState = currentState;
			currentState = state;
		}
	}

	public void start(StudentInfoBean student) {
		if (currentState == null)
			return ;
		// test
		//BaseState s = states.get(StudentStateConstant.SRV_SCHOOL_AT_BUS);
		//s.start(student);
		
		currentState.start(student);
	}
	public void stop() {
		if (currentState == null)
			return ;
		// test
		//BaseState s = states.get(StudentStateConstant.SRV_SCHOOL_AT_BUS);
		//s.stop();
		
		currentState.stop();
	}

	public PushMessage getMsg(StudentInfoBean student) {
		if (currentState == null)
			return null;
		// test
		//BaseState s = states.get(StudentStateConstant.SRV_SCHOOL_AT_BUS);
		//return s.getMsg(student);

		 return currentState.getMsg(student);
	}

	public void destroy() {
		destroyStates();
	}

	private void initStates(Activity activity) {
		for (String key : states.keySet()) {

			BaseState value = states.get(key);
			value.init(activity);

		}

	}

	private void destroyStates() {
		for (String key : states.keySet()) {

			BaseState value = states.get(key);
			value.destroy();

		}

	}
}
