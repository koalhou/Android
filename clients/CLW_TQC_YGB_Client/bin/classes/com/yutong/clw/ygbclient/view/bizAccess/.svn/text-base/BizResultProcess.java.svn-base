package com.yutong.clw.ygbclient.view.bizAccess;

/**
 * Biz返回结果处理类
 * 
 * @author zhouzc
 * 
 * @param <T>
 *            返回结果泛型
 */
public abstract class BizResultProcess<T> {

	private BizResultProcessTypeEnum mprocesstype;
	
	public BizResultProcess() {
		mprocesstype = BizResultProcessTypeEnum.Auto;
	}

	public BizResultProcess(BizResultProcessTypeEnum processtype) {
		mprocesstype = processtype;
	}

	/**
	 * 获取当前处理的方法类型
	 * 
	 * @return
	 */
	public BizResultProcessTypeEnum getProcesstype() {
		return mprocesstype;
	}

	/**
	 * 设置当前处理的方法类型
	 * 
	 */
	public void setProcesstype(BizResultProcessTypeEnum mprocesstype) {
		this.mprocesstype = mprocesstype;
	}

	/**
	 * Biz执行完成
	 * 
	 * @param datatype
	 *            数据来源
	 * @param t
	 *            返回数据
	 */
	public abstract void onBizExecuteEnd(BizDataTypeEnum datatype, T t);

	/**
	 * Biz执行错误
	 */
	public abstract void onBizExecuteError(Exception exception, Error error);
}
