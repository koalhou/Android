package com.yutong.clw.ygbclient.view.common;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Message;
import android.util.Log;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.VersionInfo;
import com.yutong.clw.ygbclient.common.constant.ThreadCommStateCode;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.splash.BizSplash;
import com.yutong.clw.ygbclient.view.bizAccess.upgrade.VersionUpgradeBiz;

/**
 * 版本更新管理类 实例请统一从 {@link YtApplication#getVersionUpdateManager()} 获取
 * 
 * @author zhouzc
 */
public class VersionUpdateManager {

	private static final String TAG = "VersionUpdateManager";

	public VersionUpdateManager() {
		versionUpdateListeners = new ArrayList<VersionUpdateManager.VersionUpdateListener>();
	}

	BizSplash biz = null;

	VersionUpgradeBiz versionUpgradeBiz = null;

	private boolean versionChecking = false;

	private boolean versionUpdating = false;

	List<VersionUpdateListener> versionUpdateListeners;

	private VersionInfo versioninfo = null;

	/**
	 * 开始检测版本信息
	 * 
	 * @param aftercheckRunnable
	 */
	public void checkVersion() {
		
		if (biz == null)
			biz = new BizSplash(YtApplication.getInstance());
		if (!versionChecking) {
			versionChecking = true;
			biz.CheckUpdate(new BizResultProcess<VersionInfo>() {

				@Override
				public void onBizExecuteError(Exception exception, Error error) {
					
					versionChecking = false;
					versioninfo = null;
					
					if (versionUpdateListeners != null
							&& versionUpdateListeners.size() > 0){
						
						for (VersionUpdateListener versionUpdateListener : versionUpdateListeners){
							
							versionUpdateListener.getCheckStatusChanged(VersionCheckStatus.Failed, null);
						}
							
					}
						

				}

				@Override
				public void onBizExecuteEnd(BizDataTypeEnum datatype,
						VersionInfo t) {
					versionChecking = false;
					if (t == null) {
						versioninfo = null;
						if (versionUpdateListeners != null
								&& versionUpdateListeners.size() > 0)
							for (VersionUpdateListener versionUpdateListener : versionUpdateListeners)
								versionUpdateListener.getCheckStatusChanged(
										VersionCheckStatus.Failed, null);

						return;
					} else {
						if (t.need_update) {
							if (t.force) {
								// 强制更新
								versioninfo = t;
								if (versionUpdateListeners != null
										&& versionUpdateListeners.size() > 0)
									for (VersionUpdateListener versionUpdateListener : versionUpdateListeners)
										versionUpdateListener
												.getCheckStatusChanged(
														VersionCheckStatus.Success_ForceUpdate,
														versioninfo);
							} else {
								// 不强制更新
								versioninfo = t;
								if (versionUpdateListeners != null
										&& versionUpdateListeners.size() > 0)
									for (VersionUpdateListener versionUpdateListener : versionUpdateListeners)
										versionUpdateListener
												.getCheckStatusChanged(
														VersionCheckStatus.Success_NeedUpdate,
														versioninfo);

							}
						} else {
							if (versionUpdateListeners != null
									&& versionUpdateListeners.size() > 0)
								for (VersionUpdateListener versionUpdateListener : versionUpdateListeners)
									versionUpdateListener
											.getCheckStatusChanged(
													VersionCheckStatus.Success_NoNeedUpdate,
													t);

						}

					}

				}
			});
		} else {
			Log.i(TAG, "已经在版本检测中不需要重复检测");
			if (versionUpdateListeners != null
					&& versionUpdateListeners.size() > 0)
				for (VersionUpdateListener versionUpdateListener : versionUpdateListeners)
					versionUpdateListener.getCheckStatusChanged(
							VersionCheckStatus.Checking, null);

		}
	}

	/**
	 * 开始更新版本
	 * 
	 * @param VersionInfo
	 *            需要更新的版本信息
	 */
	public void UpdateNewVersion(VersionInfo VersionInfo) {

		if (versionUpgradeBiz == null){
			versionUpgradeBiz 
				= new VersionUpgradeBiz(YtApplication.getInstance(), new VersionChkHandler(),VersionInfo.getUri());
		}
			
		if (!versionUpdating) {
			Logger.i(getClass(), "正在进行软件版本更新......");
			versionUpgradeBiz.execute();
			versionUpdating = true;
		} else {
			Log.i(TAG, "已经再版本更新中不需要重复更新");
		}
	}

	/**
	 * 取消下载
	 */
	public void CancelUpdating() {
		if (versionUpgradeBiz != null) {
			versionUpgradeBiz.cancel();
			versionUpgradeBiz = null;
			versionUpdating = false;
		}
	}

	/**
	 * 是否正在版本检测
	 * 
	 * @return
	 */
	public boolean isVersionChecking() {
		return versionChecking;
	}

	/**
	 * 是否正在版本更新
	 * 
	 * @return
	 */
	public boolean isVersionUpdating() {
		return versionUpdating;
	}

	/**
	 * @return
	 */
	public VersionInfo getLatestVersioninfo() {
		return versioninfo;
	}

	public void addVersionUpdateListener(VersionUpdateListener listener) {
		if (versionUpdateListeners != null)
			versionUpdateListeners.add(listener);
	}

	public void removeVersionUpdateListener(VersionUpdateListener listener) {
		if (versionUpdateListeners != null)
			versionUpdateListeners.remove(listener);

	}

	@SuppressLint("HandlerLeak")
	private class VersionChkHandler extends YtHandler {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case ThreadCommStateCode.VERSION_UPDATING:
				if (versionUpdateListeners != null
						&& versionUpdateListeners.size() > 0)
					for (VersionUpdateListener versionUpdateListener : versionUpdateListeners) {
						
						versionUpdateListener.onUpdateStatusChanged(VersionUpdateStatus.Updating,(Integer) msg.obj);
					}
				return;
			case ThreadCommStateCode.VERSION_UPDATE_COMPLETE:
				if (versionUpdateListeners != null
						&& versionUpdateListeners.size() > 0)
					for (VersionUpdateListener versionUpdateListener : versionUpdateListeners){
						versionUpdateListener.onUpdateStatusChanged(VersionUpdateStatus.Success, 100);
					}
						
				break;
			case ThreadCommStateCode.VERSON_UPDATE_FAILED:
				if (versionUpdateListeners != null
						&& versionUpdateListeners.size() > 0)
					
					for (VersionUpdateListener versionUpdateListener : versionUpdateListeners){
						
						versionUpdateListener.onUpdateStatusChanged(VersionUpdateStatus.Failed, 0);
					}
						
				break;
			case ThreadCommStateCode.VERSON_UPDATE_CANCEL:
				if (versionUpdateListeners != null
						&& versionUpdateListeners.size() > 0)
					for (VersionUpdateListener versionUpdateListener : versionUpdateListeners){
						versionUpdateListener.onUpdateStatusChanged(
								VersionUpdateStatus.Canceled, 0);
					}
					
				break;
			// 网络异常
			case ThreadCommStateCode.CHK_VERSION_NETWORK_EXCPT:
				versioninfo = null;
				if (versionUpdateListeners != null
						&& versionUpdateListeners.size() > 0)
					for (VersionUpdateListener versionUpdateListener : versionUpdateListeners){
						versionUpdateListener.getCheckStatusChanged(VersionCheckStatus.Failed, null);
					}
						
				break;
			default:
				break;
			}
			if (versionChecking) {
				versionChecking = false;
			}
			if (versionUpdating) {
				versionUpdating = false;
			}
		}
	}

	/**
	 * 版本检测状态
	 * 
	 * @author zhouzc
	 */
	public enum VersionCheckStatus {
		/**
		 * 成功 需要正常更新
		 */
		Success_NeedUpdate,
		/**
		 * 成功 需要强制更新
		 */
		Success_ForceUpdate,
		/**
		 * 成功 不需要更新
		 */
		Success_NoNeedUpdate,
		/**
		 * 失败
		 */
		Failed,
		/**
		 * 检测中
		 */
		Checking,
	}

	/**
	 * 版本更新状态
	 * 
	 * @author zhouzc
	 */
	public enum VersionUpdateStatus {
		/**
		 * 成功
		 */
		Success,
		/**
		 * 失败
		 */
		Failed,
		/**
		 * 正在更新中
		 */
		Updating,
		/**
		 * 取消
		 */
		Canceled

	}

	/**
	 * 更新检测及下载侦听器
	 * 
	 * @author zhouzc
	 */
	public interface VersionUpdateListener {

		/**
		 * 版本检测状态改变
		 * 
		 * @param status
		 *            当前状态
		 * @param versioninfo
		 *            版本信息 注意：检测失败的时候为NULL
		 */
		public void getCheckStatusChanged(VersionCheckStatus status,
				VersionInfo versioninfo);

		/**
		 * 版本更新状态改变
		 * 
		 * @param status
		 *            当前状态
		 * @param percent
		 *            下载进度百分比
		 */
		public void onUpdateStatusChanged(VersionUpdateStatus status,
				int percent);
	}
}
