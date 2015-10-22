/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-12-3 上午9:53:39
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消息分发泛型基类
 * 
 * @author zhouzc 2013-12-3 上午9:53:39
 * 
 */
public class MessageRouter<T> {

	// 侦听容器
	private List<OnReceiveMessageListener<T>> listeners;

	// 并发消息处理线程容器
	private ExecutorService msgService = null;

	// 并发侦听分发线程容器
	private ExecutorService listenerService = null;

	// 消息队列容器
	private Queue<T> msgQueue = null;

	// 标志位，是否正在执行消息分发
	private boolean isprocessing = false;

	/**
	 * 消息队列容量
	 */
	private int msgQueueCapacity,
	/**
	 * 消息处理线程池容量
	 */
	msgThreadCapacity,
	/**
	 * 消息分发线程池容量
	 */
	routeThreadCapacity;

	/**
	 * 构造函数
	 */
	public MessageRouter() {
		this(1000, 3, 5);
	}

	/**
	 * 构造函数
	 * 
	 * @param msgQueueCapacity
	 *            消息队列容量
	 * @param msgThreadCapacity
	 *            消息处理线程池容量
	 * @param routeThreadCapacity
	 *            消息分发线程池容量
	 */
	public MessageRouter(int msgQueueCapacity, int msgThreadCapacity,
			int routeThreadCapacity) {
		if (msgQueueCapacity <= 0) {
			msgQueueCapacity = 1000;
		}
		if (msgThreadCapacity <= 0) {
			msgThreadCapacity = 3;
		}
		if (routeThreadCapacity <= 0) {
			routeThreadCapacity = 5;
		}
		this.msgQueueCapacity = msgQueueCapacity;
		this.msgThreadCapacity = msgThreadCapacity;
		this.routeThreadCapacity = routeThreadCapacity;

		msgQueue = new ArrayBlockingQueue<T>(this.msgQueueCapacity);
		listeners = new ArrayList<MessageRouter.OnReceiveMessageListener<T>>();
		listenerService = Executors
				.newFixedThreadPool(this.routeThreadCapacity);
	}

	/**
	 * 
	 * @author zhouzc 2013-12-3 上午10:46:53
	 * 
	 * @return
	 */
	protected ExecutorService getMsgService() {
		if (msgService == null || msgService.isShutdown()
				|| msgService.isTerminated()) {
			int cpuNums = Runtime.getRuntime().availableProcessors();
			msgService = Executors.newFixedThreadPool(this.msgThreadCapacity
					* cpuNums);
		}
		return msgService;
	}

	/**
	 * 
	 * @author zhouzc 2013-12-3 上午10:46:59
	 * 
	 * @return
	 */
	protected ExecutorService getListenerService() {
		if (listenerService == null || listenerService.isShutdown()
				|| listenerService.isTerminated()) {
			int cpuNums = Runtime.getRuntime().availableProcessors();
			listenerService = Executors
					.newFixedThreadPool(this.routeThreadCapacity * cpuNums);
		}
		return listenerService;
	}

	/**
	 * 添加新消息
	 * 
	 * @author zhouzc 2013-12-3 上午10:25:39
	 * 
	 * @param message
	 */
	public void push(T message) {
		Logger.i(getClass(), "添加新的消息到队列");
		try {
			msgQueue.offer(message);
		} catch (Exception err) {
			err.printStackTrace();
		}
		if (isprocessing)
			return;
		else {
			isprocessing = true;
			startProcess();
		}
	}

	/**
	 * 停止工作
	 * 
	 * @author zhouzc 2013-12-3 上午11:23:38
	 * 
	 * @param stoprunningtasks
	 *            是否停止正在运行的任务
	 */
	public void stop(boolean stoprunningtasks) {
		if (msgService != null) {
			if (stoprunningtasks)
				msgService.shutdownNow();
			else
				msgService.shutdown();
		}
		if (listenerService != null) {
			if (stoprunningtasks)
				listenerService.shutdownNow();
			else
				listenerService.shutdown();
		}
	}

	private boolean startProcess() {
		Logger.i(getClass(), "开始处理消息队列");
		final T t = msgQueue.poll();
		if (t != null) {
			getMsgService().submit(new Runnable() {
				@Override
				public void run() {
					processPushMessage(t);
				}
			});
			return true;
		} else {
			return false;
		}
	}

	private synchronized void processPushMessage(final T msg) {
		Logger.i(getClass(), "准备消息分发");
		if (!onProcessMessage(msg)) {
			if (msg != null) {
				Logger.i(getClass(), "该条消息为NULL，不分发");
			} else {
				Logger.i(getClass(), "准备发送到" + listeners.size() + "个消息侦听器");
				for (final OnReceiveMessageListener<T> listener : listeners) {
					if (listener != null) {
						getListenerService().submit(new Runnable() {
							@Override
							public void run() {
								try {
									listener.OnReceive(msg);
								} catch (Exception err) {
									err.printStackTrace();
								}
							}
						});
					}
				}
			}
		}
		Logger.i(getClass(), "一个消息处理结束，准备开始处理下一个");
		boolean hasnext = startProcess();
		Logger.i(getClass(), "发现下个消息:" + hasnext);
		isprocessing = hasnext;
	}

	/**
	 * 正在处理当前消息
	 * 
	 * @author zhouzc 2013-12-3 上午10:27:52
	 * 
	 * @param t
	 *            消息
	 * @return 是否阻止父类执行
	 */
	protected boolean onProcessMessage(T t) {
		return false;
	}

	/**
	 * 添加侦听器
	 * 
	 * @author zhouzc 2013-12-3 上午10:25:11
	 * 
	 * @param listener
	 */
	public synchronized void addOnReceiveMessageListener(
			OnReceiveMessageListener<T> listener) {
		if (listener != null)
			listeners.add(listener);
		Logger.i(getClass(), "添加消息侦听，总侦听数" + listeners.size());
	}

	/**
	 * 移除侦听器
	 * 
	 * @author zhouzc 2013-12-3 上午10:25:14
	 * 
	 * @param listener
	 */
	public synchronized void removeOnReceiveMessageListener(
			OnReceiveMessageListener<T> listener) {
		if (listener != null)
			listeners.remove(listener);
		Logger.i(getClass(), "移除消息侦听，总侦听数" + listeners.size());
	}

	/**
	 * 消息侦听器
	 * 
	 * @author zhouzc 2013-12-3 上午10:24:57
	 * 
	 * @param <T>
	 */
	public interface OnReceiveMessageListener<T> {
		public void OnReceive(T t);
	}

}
