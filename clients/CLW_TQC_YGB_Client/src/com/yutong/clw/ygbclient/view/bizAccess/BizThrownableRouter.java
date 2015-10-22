/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-14 上午11:22:04
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bizAccess;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.exception.CommonException;

/**
 * @author zhouzc 2013-11-14 上午11:22:04
 * 
 */
public class BizThrownableRouter {

	List<OnRecieveCommonExceptionListener> celisteners;

	ExecutorService service = null;

	Queue<Throwable> throwables = null;

	public BizThrownableRouter() {
		celisteners = new ArrayList<BizThrownableRouter.OnRecieveCommonExceptionListener>();
		throwables = new PriorityQueue<Throwable>();
		service = Executors.newFixedThreadPool(5);
	}

	public void push(Throwable throwable) {
		Logger.i(getClass(), "添加新的异常到队列");
		throwables.add(throwable);
		if (isprocessing)
			return;
		else {
			isprocessing = true;
			startProcess();
		}
	}

	private boolean isprocessing = false;

	private boolean startProcess() {
		Logger.i(getClass(), "开始处理队列异常");
		final Throwable t = throwables.poll();
		if (t != null) {
			service.submit(new Runnable() {

				@Override
				public void run() {
					processThrownable(t);
				}
			});
			return true;
		} else {
			return false;
		}
	}

	private void processThrownable(final Throwable throwable) {
		if (throwable == null)
			return;
		if (throwable instanceof Exception) {
			if (throwable instanceof CommonException) {
				List<OnRecieveCommonExceptionListener> temp = null;
				synchronized (locObj) {
					temp = new LinkedList<BizThrownableRouter.OnRecieveCommonExceptionListener>(
							celisteners);
				}
				Logger.i(getClass(), "准备发送到" + temp.size() + "个侦听器");
				for (final OnRecieveCommonExceptionListener listener : temp) {
					if (listener != null) {
						{
							Logger.i(getClass(), "准备发送到来自[" + listener.getTag()
									+ "]的侦听器");
							service.submit(new Runnable() {
								@Override
								public void run() {
									try {
										listener.OnRecieve((CommonException) throwable);
									} catch (Throwable t) {
										t.printStackTrace();
									}
								}
							});
						}
					} else {
						Logger.i(getClass(), "返现一个为NULL的侦听器");
					}
				}
			}
		}
		isprocessing = startProcess();
		Logger.i(getClass(), "一个异常处理结束，准备开始处理下一个，发现下个异常:" + isprocessing);
	}

	public void addOnRecieveCommonExceptionListener(
			OnRecieveCommonExceptionListener listener) {
		synchronized (locObj) {
			celisteners.add(listener);
			Logger.i(getClass(), "添加异常侦听，总侦听数" + celisteners.size());
		}
	}

	private static Object locObj = new Object();

	public void removeOnRecieveCommonExceptionListener(
			OnRecieveCommonExceptionListener listener) {
		synchronized (locObj) {
			celisteners.remove(listener);
			Logger.i(getClass(), "移除异常侦听，总侦听数" + celisteners.size());
		}
	}

	public static abstract class OnRecieveCommonExceptionListener {
		private String Tag = "NOTSET";

		public String getTag() {
			return Tag;
		}

		public void setTag(String tag) {
			Tag = tag;
		}

		public abstract void OnRecieve(CommonException exception);
	}
}
