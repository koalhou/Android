package com.yutong.axxc.parents.view.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKEvent;
import com.baidu.mapapi.MKGeneralListener;
import com.yutong.axxc.parents.business.common.ReceiverUtil;
import com.yutong.axxc.parents.business.receiver.NetworkReceiver;
import com.yutong.axxc.parents.common.GlobleConstants;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.OnNetworkChangeListener;
import com.yutong.axxc.parents.common.SharedPreferencesUtil;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.common.beans.WeatherInfoBean;
import com.yutong.axxc.parents.common.context.ContextUtil;
import com.yutong.axxc.parents.connect.push.CommonPushMsg;
import com.yutong.axxc.parents.connect.push.PushMessageListenHost;
import com.yutong.axxc.xmpp.client.MessageManager;

/**
 * 应用程序管理类
 */
public final class YtApplication extends Application {
	private static final String SHARED_TOKEN = "shared_token";

	private static final String SHARED_UID = "shared_uid";

	private static final String SHARED_DISP_HEIGHT = "shared_disp_height";

	private static final String SHARED_DISP_WIDTH = "shared_disp_width";
	private List<CommonPushMsg> commonPushMsgs = new ArrayList<CommonPushMsg>();
	/** 是否能写日志 */
	private boolean isCanWriteLog = true;

	private static YtApplication mInstance = null;
	/**
	 * 地图管理工具
	 */
	public BMapManager mBMapManager = null;
	/**
	 * 存储线性布局
	 */
	private List<LinearLayout> linearLayouts;
	/**
	 * 百度地图APIkey
	 */
	public static final String strKey = "35813AD62C3C4E1DF12EAF4510B7494DEF7AB7FB";
	private final int OFFSET = 2;// 控制坐标点图标上下左右右的显示位置

	/* 页头主题菜单当前状态 */
	private String vStatus = VConstants.V_ALL_STATUS; // 保存车辆监控界面当前TAB选中项
	/* 搜索条件-类型 */
	private String searchConditionType;
	/* 搜索条件-名称 */
	private String searchConditionName = GlobleConstants.EMPTY_STR;
	/** Preferences名称 */
	public static final String PREFS_NAME = "SettingPrefs";
	private static final String SHARED_C_LATITUDE = "shared_c_latitude";
	private static final String SHARED_C_LONGITUDE = "shared_c_longitude";

	/**
	 * 控制坐标点图标上下左右右的显示位置
	 * 
	 * @return
	 */
	public int getOFFSET() {
		return OFFSET;
	}

	/**
	 * @return Returns the cLatitude.
	 */
	public double getcLatitude() {
		return Double.parseDouble(ContextUtil.getPreference(this,
				SharedPreferencesUtil.PREFS_NAME_SETTING,
				Context.MODE_WORLD_READABLE, SHARED_C_LATITUDE, "120.675955"));
	}

	/**
	 * @param cLatitude
	 *            The cLatitude to set.
	 */
	public void setcLatitude(double cLatitude) {
		ContextUtil
				.setPreferences(this, SharedPreferencesUtil.PREFS_NAME_SETTING,
						Context.MODE_WORLD_WRITEABLE, SHARED_C_LATITUDE, ""
								+ cLatitude);
	}

	/**
	 * @return Returns the cLongitude.
	 */
	public double getcLongitude() {
		return Double.parseDouble(ContextUtil.getPreference(this,
				SharedPreferencesUtil.PREFS_NAME_SETTING,
				Context.MODE_WORLD_READABLE, SHARED_C_LONGITUDE, "31.09081"));
	}

	public String getSearchConditionType() {
		return searchConditionType;
	}

	public void setSearchConditionType(String searchConditionType) {
		this.searchConditionType = searchConditionType;
	}

	public String getSearchConditionName() {
		return searchConditionName;
	}

	public void setSearchConditionName(String searchConditionName) {
		this.searchConditionName = searchConditionName;
	}

	/**
	 * @param cLongitude
	 *            The cLongitude to set.
	 */
	public void setcLongitude(double cLongitude) {
		ContextUtil.setPreferences(this,
				SharedPreferencesUtil.PREFS_NAME_SETTING,
				Context.MODE_WORLD_WRITEABLE, SHARED_C_LONGITUDE, ""
						+ cLongitude);
	}

	@Override
	public void onTerminate() {
		if (mBMapManager != null) {
			mBMapManager.destroy();
			mBMapManager = null;
		}
		super.onTerminate();
	}

	private void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(strKey, new MyGeneralListener())) {
			Toast.makeText(YtApplication.getInstance().getApplicationContext(),
					"BMapManager  初始化百度地图参数错误", Toast.LENGTH_LONG).show();
		}
	}

	/****** 管理线性布局 *******/
	public List<LinearLayout> getLinearLayouts() {
		return linearLayouts;
	}

	public String getvStatus() {
		return vStatus;
	}

	public void setvStatus(String vStatus) {
		this.vStatus = vStatus;
	}

	public void setLinearLayouts(List<LinearLayout> linearLayouts) {
		this.linearLayouts = linearLayouts;
	}

	public static YtApplication getInstance() {
		if (mInstance == null) {
			mInstance = new YtApplication();
		}
		return mInstance;
	}

	private NetworkReceiver networkReceiver;

	private MessageManager serviceManager;

	private VersionUpdateManager versionUpdateManager;

	private ActivityCoverManager activityCoverManager;

	/** 是否已经初始化push */
	public boolean isInitPush = false;

	/** 用户信息缓存，在其他界面实例化改activity时使用 */
	private UserInfoBean userInfoCache;

	/** 天气信息缓存 */
	private WeatherInfoBean weatherInfoCache;

	/**
	 * 注册手机号
	 */
	private String registerPhone;

	/**
	 * 注册验证码
	 */
	private String registerCode;

	/**
	 * 接收注册验证码的时间,用于判断是否过期,有效期通过参数配置。
	 */
	private Date receiveRegisterCodeTime;

	/**
	 * 找回密码手机号
	 */
	private String findpwdPhone;

	/**
	 * 找回密码验证码
	 */
	private String findpwdCode;

	/**
	 * 接收找回密码验证码的时间,用于判断是否过期,有效期通过参数配置。
	 */
	private Date receiveFindpwdCodeTime;

	/* 验证码 */

	/**
	 * 关联孩子验证码
	 */
	private String bindStudentCode;

	/**
	 * 关联孩子验证码的时间,用于判断是否过期,有效期通过参数配置。
	 */
	private Date bindStudentCodeTime;

	/**
	 * 百度地图key是否正确
	 */
	public boolean m_bKeyRight;

	private PushMessageListenHost pushHost;

	@Override
	public void onCreate() {
		super.onCreate();
		AppCrashHandler handler = AppCrashHandler.getInstance();
		handler.init(getApplicationContext());
		// 初始化地图相关数据资源
		mInstance = this;
		pushHost = new PushMessageListenHost();
		versionUpdateManager = new VersionUpdateManager();
	}

	/**
	 * @return Returns the isCanWriteLog.
	 */
	public boolean isCanWriteLog() {
		return isCanWriteLog;
	}

	/**
	 * @param isCanWriteLog
	 *            The isCanWriteLog to set.
	 */
	public void setCanWriteLog(boolean isCanWriteLog) {
		this.isCanWriteLog = isCanWriteLog;
	}

	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String verifyCode) {
		this.registerCode = verifyCode;
	}

	public Date getReceiveRegisterCodeTime() {
		return receiveRegisterCodeTime;
	}

	public void setReceiveRegisterCodeTime(Date receiveCodeTime) {
		this.receiveRegisterCodeTime = receiveCodeTime;
	}

	public String getRegisterPhone() {
		return registerPhone;
	}

	public void setRegisterPhone(String registerPhone) {
		this.registerPhone = registerPhone;
	}

	public String getAppId() {
		return "axxc_parents";

		// return InstallationUtils.id(getInstance());
	}

	/**
	 * @return Returns the accessToken.
	 */
	public String getAccessToken() {
		return ContextUtil.getPreference(this,
				SharedPreferencesUtil.PREFS_NAME_SETTING,
				Context.MODE_WORLD_READABLE, SHARED_TOKEN, "");
	}

	/**
	 * @param accessToken
	 *            The accessToken to set.
	 */
	public void setAccessToken(String accessToken) {
		ContextUtil.setPreferences(this,
				SharedPreferencesUtil.PREFS_NAME_SETTING,
				Context.MODE_WORLD_WRITEABLE, SHARED_TOKEN, accessToken);
	}

	public UserInfoBean getUserInfoCache() {
		return userInfoCache;
		// if (userInfoCache == null) {
		// DaoLoginInfoBean daoLoginInfoBean = new
		// LoginInfoDao(this).getLogInfo();
		// userInfoCache.setUsrName(daoLoginInfoBean.getUsrName());
		// userInfoCache.setUsrSex(daoLoginInfoBean.getUsrSex());
		// userInfoCache.setEnId(daoLoginInfoBean.getEnId());
		// userInfoCache.setUsrId(daoLoginInfoBean.getUsrId());
		// userInfoCache.setOrg_id(daoLoginInfoBean.getOrgId());
		// }
		// return userInfoCache;
	}

	public void setUserInfoCache(UserInfoBean userInfoCache) {
		this.userInfoCache = userInfoCache;
	}

	public WeatherInfoBean getWeatherInfoCache() {
		return weatherInfoCache;
	}

	public void setWeatherInfoCache(WeatherInfoBean weatherInfoCache) {
		this.weatherInfoCache = weatherInfoCache;
	}

	/**
	 * @return the findpwdCode
	 */
	public String getFindpwdCode() {
		return findpwdCode;
	}

	/**
	 * @param findpwdCode
	 *            the findpwdCode to set
	 */
	public void setFindpwdCode(String findpwdCode) {
		this.findpwdCode = findpwdCode;
	}

	/**
	 * @return the receiveFindpwdCodeTime
	 */
	public Date getReceiveFindpwdCodeTime() {
		return receiveFindpwdCodeTime;
	}

	/**
	 * @param receiveFindpwdCodeTime
	 *            the receiveFindpwdCodeTime to set
	 */
	public void setReceiveFindpwdCodeTime(Date receiveFindpwdCodeTime) {
		this.receiveFindpwdCodeTime = receiveFindpwdCodeTime;
	}

	/**
	 * @return the findpwdPhone
	 */
	public String getFindpwdPhone() {
		return findpwdPhone;
	}

	/**
	 * @param findpwdPhone
	 *            the findpwdPhone to set
	 */
	public void setFindpwdPhone(String findpwdPhone) {
		this.findpwdPhone = findpwdPhone;
	}

	/**
	 * @return Returns the uid.
	 */
	public String getUid() {
		return ContextUtil.getPreference(this,
				SharedPreferencesUtil.PREFS_NAME_SETTING,
				Context.MODE_WORLD_READABLE, SHARED_UID, "defaultuser");
	}

	/**
	 * @param uid
	 *            The uid to set.
	 */
	public void setUid(String uid) {
		ContextUtil.setPreferences(this,
				SharedPreferencesUtil.PREFS_NAME_SETTING,
				Context.MODE_WORLD_WRITEABLE, SHARED_UID, uid);
	}

	public NetworkReceiver getNetworkReceiver() {
		return networkReceiver;
	}

	public void setNetworkReceiver(NetworkReceiver networkReceiver) {
		this.networkReceiver = networkReceiver;
	}

	public MessageManager getServiceManager() {
		return serviceManager;
	}

	public void setServiceManager(MessageManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	public boolean isInitPush() {
		return isInitPush;
	}

	public void setInitPush(boolean isInitPush) {
		this.isInitPush = isInitPush;
	}

	/**
	 * @return Returns the displayHeight.
	 */
	public int getDisplayHeight() {
		return Integer.parseInt(ContextUtil.getPreference(this,
				SharedPreferencesUtil.PREFS_NAME_SETTING,
				Context.MODE_WORLD_READABLE, SHARED_DISP_HEIGHT, "1280"));
	}

	/**
	 * @param displayHeight
	 *            The displayHeight to set.
	 */
	public void setDisplayHeight(int displayHeight) {
		ContextUtil.setPreferences(this,
				SharedPreferencesUtil.PREFS_NAME_SETTING,
				Context.MODE_WORLD_WRITEABLE, SHARED_DISP_HEIGHT, ""
						+ displayHeight);
	}

	/**
	 * @return Returns the displayWidth.
	 */
	public int getDisplayWidth() {
		return Integer.parseInt(ContextUtil.getPreference(this,
				SharedPreferencesUtil.PREFS_NAME_SETTING,
				Context.MODE_WORLD_READABLE, SHARED_DISP_WIDTH, "720"));
	}

	/**
	 * @param displayWidth
	 *            The displayWidth to set.
	 */
	public void setDisplayWidth(int displayWidth) {
		ContextUtil.setPreferences(this,
				SharedPreferencesUtil.PREFS_NAME_SETTING,
				Context.MODE_WORLD_WRITEABLE, SHARED_DISP_WIDTH, ""
						+ displayWidth);
	}

	/**
	 * @return the bindStudentCode
	 */
	public String getBindStudentCode() {
		return bindStudentCode;
	}

	/**
	 * @param bindStudentCode
	 *            the bindStudentCode to set
	 */
	public void setBindStudentCode(String bindStudentCode) {
		this.bindStudentCode = bindStudentCode;
	}

	/**
	 * @return the bindStudentCodeTime
	 */
	public Date getBindStudentCodeTime() {
		return bindStudentCodeTime;
	}

	/**
	 * @param bindStudentCodeTime
	 *            the bindStudentCodeTime to set
	 */
	public void setBindStudentCodeTime(Date bindStudentCodeTime) {
		this.bindStudentCodeTime = bindStudentCodeTime;
	}

	/**
	 * 
	 * @author zhangzhia 2013-9-11 上午9:44:02
	 * 
	 */
	public void registerPushReceiver() {
		try {
			if (!isInitPush) {
				Logger.i(this.getClass(), "注册推送服务");
				ReceiverUtil.registerPushReceiver();
				isInitPush = true;
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 注销推送服务
	 * 
	 * @author zhangzhia 2013-9-11 上午9:44:02
	 * 
	 */
	public void unRegisterPushReceiver() {
		try {
			if (isInitPush) {
				Logger.i(this.getClass(), "注销推送服务");
				ReceiverUtil.unRegisterPushReceiver();
				isInitPush = false;
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @author zhangzhia 2013-9-11 上午9:53:32
	 * 
	 * @return
	 */
	public List<CommonPushMsg> getCommonPushMsgs() {
		return commonPushMsgs;
	}

	public void setCommonPushMsgs(List<CommonPushMsg> commonPushMsgs) {
		this.commonPushMsgs = commonPushMsgs;
	}

	/**
	 * 获取消息侦听管理器
	 * 
	 * @return
	 */
	public PushMessageListenHost getPushHost() {
		return pushHost;
	}

	/**
	 * 获取版本更新管理类
	 * 
	 * @return
	 */
	public VersionUpdateManager getVersionUpdateManager() {
		if (versionUpdateManager == null)
			versionUpdateManager = new VersionUpdateManager();
		return versionUpdateManager;
	}

	/**
	 * 获取覆盖引导管理类
	 * @return
	 */
	public ActivityCoverManager getActivityCoverManager() {
		if (activityCoverManager == null)
			activityCoverManager = new ActivityCoverManager();
		return activityCoverManager;
	}

	/**
	 * 常用事件监听，用来处理通常的网络错误，授权验证错误等
	 * 
	 * @author <a href="mailto:wenxw@neusoft.com">sherly.wen </a>
	 * @version $Revision 1.1 $ 2013-2-26 上午09:54:37
	 */
	public static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				// Toast.makeText(getInstance().getApplicationContext(),
				// "初始化百度地图错误，请检查你的网络是否正常", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				// Toast.makeText(
				// YtApplication.getInstance().getApplicationContext(),
				// "初始化百度地图错误，网络数据错误", Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(
						YtApplication.getInstance().getApplicationContext(),
						"请在 YtApplication.java文件输入正确的授权Key", Toast.LENGTH_LONG)
						.show();
				YtApplication.getInstance().m_bKeyRight = false;
			}
		}
	}

	public void unRegisterNetworkReceiver() {

		try {

			Logger.i(this.getClass(), "注销网络监测服务");
			ReceiverUtil.unRegisterNetworkReceiver();

		} catch (Exception e) {
		}
	}

	public void registerNetworkReceiver() {
		try {

			Logger.i(this.getClass(), "注册网络监测服务");
			ReceiverUtil.registerNetworkReceiver();

		} catch (Exception e) {
		}

	}

	/**************************** 网络相关 ********************************/
	List<OnNetworkChangeListener> networklistener = new ArrayList<OnNetworkChangeListener>();

	public void clearOnNetworkChangeListeners() {
		networklistener.clear();
	}

	public void addOnNetworkChangeListener(OnNetworkChangeListener listener) {
		if (listener == null)
			return;
		synchronized (networklistener) {
			networklistener.add(listener);
		}
	}

	public void removeOnNetworkChangeListener(OnNetworkChangeListener listener) {
		if (listener == null)
			return;
		synchronized (networklistener) {
			networklistener.remove(listener);
		}
	}

	public void trigerOnNetworkChangeListener(boolean isconnected) {
		if (networklistener == null)
			return;

		if (networklistener.size() == 0)
			return;
		try {
			synchronized (networklistener) {
				for (OnNetworkChangeListener listener : networklistener) {

					listener.NetworkChanged(isconnected);
				}
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}
	/**************************** 网络相关 ********************************/

}
