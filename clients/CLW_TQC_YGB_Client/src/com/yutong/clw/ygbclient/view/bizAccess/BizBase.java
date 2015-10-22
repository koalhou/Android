package com.yutong.clw.ygbclient.view.bizAccess;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.Context;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;

/**
 * 页面业务数据获取Biz基类
 * 
 * @author zhouzc 2013-10-30 下午2:55:39
 * 
 */
public class BizBase {

	protected Context mContext = null;
	private int mthreadcount = 3;
	protected ExecutorService meservice = null;
	
	public BizBase(Context context) {
		this(context, 3);
	}

	public BizBase(Context context, int threadcount) {

		if (context == null)
			throw new IllegalArgumentException("上下文Context 不能为NULL");
		mContext = context;
		if (threadcount < 1) {
			threadcount = 1;
		} else if (threadcount > 20) {
			threadcount = 20;
		}
		mthreadcount = threadcount;
		meservice = Executors.newFixedThreadPool(mthreadcount);
	}

	public Context getmContext() {
		return mContext;
	}

	/**
	 * 公共异常处理
	 * 
	 * @param exception
	 *            当前异常
	 */
	private void manageCommonException(Exception exception) {
		YtApplication.getInstance().getBizThrownableRouter().push(exception);
		// if (exception.status == CommonNetStatus.Token_InValid) {
		// // TODO
		// }
	}

	private <T> void managerResult(Future<T> result,
			BizResultProcess<T> process, BizDataTypeEnum returnvalue) {

		T data = null;
		try {
			data = result.get();
		} catch (Exception ex) {
			processException(ex, process);
			return;
		} catch (Error e) {
			processError(e, process);
			return;
		}
		if (process != null)
			process.onBizExecuteEnd(returnvalue, data);
	}

	private <T> void processException(Exception ex, BizResultProcess<T> process) {
		Logger.e(getClass(), "Biz执行过程中发生异常");
		if (ex == null) {
			process.onBizExecuteError(new Exception("NULL"), null);
			return;
		}
		ex.printStackTrace();
		if (ex instanceof ExecutionException) {
			if (ex.getCause() != null && ex.getCause() instanceof Exception) {
				manageCommonException((Exception) ex.getCause());
				if (process != null)
					process.onBizExecuteError((Exception) ex.getCause(), null);
				return;
			} else {
				if (process != null)
					process.onBizExecuteError(ex, null);
				return;
			}
		} else if (ex instanceof CommonException) {
			manageCommonException(ex);
			if (process != null)
				process.onBizExecuteError(ex, null);
		}

	}

	private <T> void processError(Error er, BizResultProcess<T> process) {
		er.printStackTrace();
		if (process != null)
			process.onBizExecuteError(null, er);
	}

	/**
	 * 通用Biz处理调用方法
	 * 
	 * @param fromcache
	 *            是否从缓存获取
	 * @param cachecall
	 *            从缓存获取的方法
	 * @param networkcall
	 *            从网络获取的方法
	 * @param process
	 *            回调方法
	 * @return 当前数据来源类型
	 */
	protected <T> BizDataTypeEnum BizCommonWork(boolean fromcache,
			Callable<T> cachecall, Callable<T> networkcall,
			final BizResultProcess<T> process) {

		if (fromcache) {// 从缓存获取的数据

			if (process != null
					&& process.getProcesstype() == BizResultProcessTypeEnum.Async) {
				// 用户设置为异步执行
				final Future<T> result = getExecuteService().submit(cachecall);
				// 启动异步任务
				getExecuteService().submit(new Runnable() {
					@Override
					public void run() {
						managerResult(result, process,
								BizDataTypeEnum.FromCache);
					}
				});
			} else {
				// 默认或指定同步执行
				T t = null;
				try {
					t = cachecall.call();
				} catch (Exception ex) {
					processException(ex, process);
					return BizDataTypeEnum.FromCache;
				} catch (Error e) {
					processError(e, process);
					return BizDataTypeEnum.FromCache;
				}
				if (process != null)
					process.onBizExecuteEnd(BizDataTypeEnum.FromCache, t);
				return BizDataTypeEnum.FromCache;
			}
			return BizDataTypeEnum.FromCache;
		} else {
			// 从网络获取数据
			final Future<T> result = getExecuteService().submit(networkcall);
			// 用户设置为同步执行
			if (process != null
					&& process.getProcesstype() == BizResultProcessTypeEnum.Sync) {
				// 开始同步等待执行
				managerResult(result, process, BizDataTypeEnum.FromNetwork);
			} else {
				// 默认或用户指定异步执行
				getExecuteService().submit(new Runnable() {
					@Override
					public void run() {
						managerResult(result, process,
								BizDataTypeEnum.FromNetwork);
					}
				});
			}
			return BizDataTypeEnum.FromNetwork;
		}
	}

	/**
	 * 通用Biz处理调用方法 (只调用网络异步处理的时候调用）
	 * 
	 * @author zhouzc 2013-10-30 下午2:54:45
	 * 
	 * @param networkcall
	 * @param process
	 */
	protected <T> void BizNetWork(Callable<T> networkcall,
			final BizResultProcess<T> process) {
		
		if (process != null) {
			process.setProcesstype(BizResultProcessTypeEnum.Auto);
		}
		BizCommonWork(false, null, networkcall, process);
	}

	/**
	 * 通用Biz处理调用方法 (只调用本地同步方法的时候调用）
	 * 
	 * @author zhouzc 2013-10-30 下午2:54:45
	 * 
	 * @param localwork
	 * @param process
	 */
	protected <T> void LocalWork(Callable<T> localwork,
			BizResultProcess<T> process) {
		if (process != null) {
			process.setProcesstype(BizResultProcessTypeEnum.Auto);
		}
		BizCommonWork(true, localwork, null, process);
	}

	private ExecutorService getExecuteService() {
		if (meservice == null || meservice.isShutdown()
				|| meservice.isTerminated())
			start();
		return meservice;
	}

	public void start() {
		if (meservice == null || meservice.isShutdown()
				|| meservice.isTerminated())
			meservice = Executors.newFixedThreadPool(mthreadcount);
	}

	public void stop() {
		if (meservice != null && !meservice.isShutdown()) {
			meservice.shutdownNow();
			meservice = null;

		}
	}

	public void destory() {
		if (meservice != null && !meservice.isTerminated()
				&& !meservice.isShutdown())
			meservice.shutdownNow();
	}
}
