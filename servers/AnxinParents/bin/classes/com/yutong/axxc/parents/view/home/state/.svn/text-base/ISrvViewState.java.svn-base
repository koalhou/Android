package com.yutong.axxc.parents.view.home.state;

import android.app.Activity;

import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.home.PushMessage;
/**
 * 当前 状态机接口
 * @author zhangyongn
 *
 */
public interface ISrvViewState {
	void init(Activity activity);
	/**
	 * 启动
	 */
	void start(StudentInfoBean student);
	/**
	 * 停止
	 */
	void stop();
//	/**
//	 * 状态切入。表示从上一个状态切换到当前状态
//	 */
//	void switchIn(StudentInfoBean student);
//	/**
//	 * 状态切出。表示从当前状态切出到其他状态。在切换到下一状态前调用。
//	 */
//	void switchOut();
	/**
	 * 销毁
	 */
	void destroy();
	/**
	 * 得到视图对应的消息数据
	 * @param student
	 * @return
	 */
	PushMessage getMsg(StudentInfoBean student);
}
