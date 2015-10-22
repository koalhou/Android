package com.yutong.clw.ygbclient.common.cache;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.google.gson.Gson;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.dao.cache.CahceItemConfigDao;

/**
 * 缓存管理类
 * 
 * @author zhangzhia 2013-10-21 上午9:17:40
 */
public class CahceDataManager {
	/** Context对象 */
	private Context context;

	private Gson gson = new Gson();

	private final Map<String, CacheConfig> configMap = new HashMap<String, CacheConfig>();

	private static CahceDataManager singleton = null;

	public static synchronized CahceDataManager getInstance(Context context) {
		if (singleton == null) {
			singleton = new CahceDataManager(context);
		}
		return singleton;
	}

	public CahceDataManager(Context context) {
		this.context = context;
		initConfig();
	}

	/**
	 * 初始化缓存配置数据
	 * 
	 * @author zhangzhia 2013-10-24 下午2:55:36
	 */
	public void initConfig() {
		// TODO 根据xml配置文件，初始化数据，并插入数据库
		initCacheConfigs();
	}

	/**
	 * 初始化缓存配置
	 * 
	 * @author zhangzhia 2013-10-24 下午2:49:46
	 * @param configs
	 */
	private void initCacheConfigs() {
		// 示例
		configMap.clear();
		CacheConfig config;
		// TODO
		/**
		 * 手机绑定
		 */
		config = new CacheConfig(ActionURI.BIND_PHONE, false, 30L, 30L);
		configMap.put(ActionURI.BIND_PHONE.toString(), config);
		/**
		 * 验证帐号
		 */
		config = new CacheConfig(ActionURI.VERIFY_ACCOUNT, false, 30L, 30L);
		configMap.put(ActionURI.VERIFY_ACCOUNT.toString(), config);
		/**
		 * 登陆
		 */
		config = new CacheConfig(ActionURI.LOGIN, false, 30L, 30L);
		configMap.put(ActionURI.LOGIN.toString(), config);
		/**
		 * 找回密码
		 */
		config = new CacheConfig(ActionURI.FORGOT_PASSWORD, false, 30L, 30L);
		configMap.put(ActionURI.FORGOT_PASSWORD.toString(), config);
		/**
		 * AccessToken更新
		 */
		config = new CacheConfig(ActionURI.REFRESH_ACCESSTOKEN, false, 30L, 30L);
		configMap.put(ActionURI.REFRESH_ACCESSTOKEN.toString(), config);
		/**
		 * 自动登陆
		 */
		config = new CacheConfig(ActionURI.AUTO_LOGIN, false, 30L, 30L);
		configMap.put(ActionURI.AUTO_LOGIN.toString(), config);
		/**
		 * 退出登陆
		 */
		config = new CacheConfig(ActionURI.LOGOUT, false, 30L, 30L);
		configMap.put(ActionURI.LOGOUT.toString(), config);

		// -----------系统设置----------------------
		/**
		 * 意见反馈
		 */
		config = new CacheConfig(ActionURI.ADVISE_FEEDBACK, false, 30L, 30L);
		configMap.put(ActionURI.ADVISE_FEEDBACK.toString(), config);
		/**
		 * 获取新闻概要
		 */
		config = new CacheConfig(ActionURI.GET_NEWS_SUMMARY, true, 5 * 60L, 30L);
		configMap.put(ActionURI.GET_NEWS_SUMMARY.toString(), config);
		/**
		 * 获取新闻详细
		 */
		config = new CacheConfig(ActionURI.GET_NEWS_DETAIL, false, 30L, 30L);
		configMap.put(ActionURI.GET_NEWS_DETAIL.toString(), config);
		/**
		 * 获取推送规则
		 */
		config = new CacheConfig(ActionURI.GET_PUSH_MSG_RULES, true,
				12 * 60 * 60L, 60 * 60L);
		configMap.put(ActionURI.GET_PUSH_MSG_RULES.toString(), config);
		/**
		 * 设置推送规则
		 */
		config = new CacheConfig(ActionURI.SET_PUSH_MSG_RULES, false, 30L, 30L);
		configMap.put(ActionURI.SET_PUSH_MSG_RULES.toString(), config);
		/**
		 * 版本检查
		 */
		config = new CacheConfig(ActionURI.CHECK_VERSION, true, 5 * 60L,
				2 * 60L);
		configMap.put(ActionURI.CHECK_VERSION.toString(), config);
		/**
		 * 旧密码验证
		 */
		config = new CacheConfig(ActionURI.VERIFY_OLD_PASSWORD, false, 30L, 30L);
		configMap.put(ActionURI.VERIFY_OLD_PASSWORD.toString(), config);

		/**
		 * 用户密码修改
		 */
		config = new CacheConfig(ActionURI.SET_NEW_PASSWORD, false, 30L, 30L);
		configMap.put(ActionURI.SET_NEW_PASSWORD.toString(), config);

		// -----------线路与站点相关----------------------
		/**
		 * 获取厂区范围线路信息
		 */
		config = new CacheConfig(ActionURI.GET_AREA_LINES, true,
				7 * 24 * 60 * 60L, 7 * 24 * 60 * 60L);
		configMap.put(ActionURI.GET_AREA_LINES.toString(), config);
		/**
		 * 获取编号集合的线路信息
		 */
		config = new CacheConfig(ActionURI.GET_IDS_LINES, true,
				7 * 24 * 60 * 60L, 7 * 24 * 60 * 60L);
		configMap.put(ActionURI.GET_IDS_LINES.toString(), config);
		/**
		 * 获取推荐站点列表信息
		 */
		config = new CacheConfig(ActionURI.GET_RECOMMEND_STATIONS, true,
				2 * 60 * 60L, 24 * 60 * 60L);
		configMap.put(ActionURI.GET_RECOMMEND_STATIONS.toString(), config);
		/**
		 * 获取站点列表信息
		 */
		config = new CacheConfig(ActionURI.GET_STATIONS, true,
				7 * 24 * 60 * 60L, 7 * 24 * 60 * 60L);
		configMap.put(ActionURI.GET_STATIONS.toString(), config);
		/**
		 * 获取车辆信息
		 */
		config = new CacheConfig(ActionURI.GET_VEHICHES, true,
				(long) (0.5 * 60L), (long) 0);
		configMap.put(ActionURI.GET_VEHICHES.toString(), config);
		/**
		 * 获取站点区域信息
		 */
		config = new CacheConfig(ActionURI.GET_STATION_AREAS, true,
				7 * 24 * 60 * 60L, 7 * 24 * 60 * 60L);
		configMap.put(ActionURI.GET_STATION_AREAS.toString(), config);
		/**
		 * 获取单个站点提醒
		 */
		config = new CacheConfig(ActionURI.GET_SINGLE_REMIND_STATIONS, false,
				30L, 30L);
		configMap.put(ActionURI.GET_SINGLE_REMIND_STATIONS.toString(), config);
		/**
		 * 获取站点提醒
		 */
		config = new CacheConfig(ActionURI.GET_REMIND_STATIONS, true, 30 * 60L,
				60L);
		configMap.put(ActionURI.GET_REMIND_STATIONS.toString(), config);
		/**
		 * 设置提醒站点信息
		 */
		config = new CacheConfig(ActionURI.SET_REMIND_STATION, false, 30L, 30L);
		configMap.put(ActionURI.SET_REMIND_STATION.toString(), config);
		/**
		 * 获取已收藏站点
		 */
		config = new CacheConfig(ActionURI.GET_FAVORITES, true, 2 * 60 * 60L,
				60 * 60L);
		configMap.put(ActionURI.GET_FAVORITES.toString(), config);
		/**
		 * 设置收藏站点
		 */
		config = new CacheConfig(ActionURI.SET_FAVORITES, false, 30L, 30L);
		configMap.put(ActionURI.SET_FAVORITES.toString(), config);
		/**
		 * 获取站点与车辆相对实时数据
		 */
		config = new CacheConfig(ActionURI.GET_STATION_REALTIME_DATA, false,
				30L, 30L);
		configMap.put(ActionURI.GET_STATION_REALTIME_DATA.toString(), config);
		/**
		 * 获取车辆实时信息
		 */
		config = new CacheConfig(ActionURI.GET_VEHICHE_REALTIME_DATA, false,
				30L, 30L);
		configMap.put(ActionURI.GET_VEHICHE_REALTIME_DATA.toString(), config);
		// -----------信息日志收集----------------------
		/**
		 * 异常信息收集上报
		 */
		config = new CacheConfig(ActionURI.REPORT_EXCEPTION_INFO, false, 30L,
				30L);
		configMap.put(ActionURI.REPORT_EXCEPTION_INFO.toString(), config);
		/**
		 * 终端软硬件环境信息上报
		 */
		config = new CacheConfig(ActionURI.REPORT_MOBILE_ENVIRONMENT_INFO,
				false, 30L, 30L);
		configMap.put(ActionURI.REPORT_MOBILE_ENVIRONMENT_INFO.toString(),
				config);
		// -----------其他接口----------------------
		/**
		 * 手机短信验证
		 */
		config = new CacheConfig(ActionURI.SEND_PHONE_SMS, false, 30L, 30L);
		configMap.put(ActionURI.SEND_PHONE_SMS.toString(), config);
		/**
		 * 获取二维码
		 */
		config = new CacheConfig(ActionURI.GET_QRCODE, false, 30L, 30L);
		configMap.put(ActionURI.GET_QRCODE.toString(), config);
		/**
		 * 资源上传
		 */
		config = new CacheConfig(ActionURI.UPLOAD_RESOURCES, false, 30L, 30L);
		configMap.put(ActionURI.UPLOAD_RESOURCES.toString(), config);
		/**
		 * 资源下载
		 */
		config = new CacheConfig(ActionURI.DOWNLOAD_RESOURCES, false, 30L, 30L);
		configMap.put(ActionURI.DOWNLOAD_RESOURCES.toString(), config);

		/**
		 * 获取车辆司机信息
		 */
		config = new CacheConfig(ActionURI.GET_VEHICHE_DRIVER_DATA, false,
				6 * 60 * 60L, 1 * 60 * 60L);
		configMap.put(ActionURI.GET_VEHICHE_DRIVER_DATA.toString(), config);
	}

	/**
	 * 获取缓存配置模板
	 * 
	 * @author zhangzhia 2013-10-24 下午5:35:38
	 * @param uri
	 * @return
	 */
	private CacheConfig getConfig(ActionURI uri) {
		return configMap.get(uri.toString());
	}

	/**
	 * 加入模块缓存模块,存入数据库
	 * 
	 * @author zhangzhia 2013-10-24 下午5:23:12
	 * @param uri
	 * @param key
	 */
	public void putCacheConfig(ActionURI uri, String key, String etag) {
		putCacheConfig(uri, key, true, etag, null);
	}

	/**
	 * 加入模块缓存模块,不存入数据库
	 * 
	 * @author zhangzhia 2013-10-24 下午5:23:12
	 * @param uri
	 * @param key
	 */
	public void putCacheConfig(ActionURI uri, String key, String etag,
			String value) {
		putCacheConfig(uri, key, false, etag, value);
	}

	/**
	 * 加入模块缓存模块
	 * 
	 * @author zhangzhia 2013-10-24 下午5:23:12
	 * @param uri
	 * @param key
	 */
	private void putCacheConfig(ActionURI uri, String key, boolean saveToDB,
			String etag, String value) {
		try {
			CacheItem cacheItem = new CacheItem();

			cacheItem.ActionURI = uri;
			cacheItem.Key = key;
			cacheItem.ETag = etag;

			// cache.DownloadTime = new Date();

			// 读取模块缓存配置
			CacheConfig config = getConfig(uri);

			// 如果没有缓存配置
			if (config == null) {
				Logger.w(this.getClass(), "【缓存管理类】未发现缓存配置，不存储");
			}

			cacheItem.NeedCacheMechanism = config.NeedCacheMechanism;
			cacheItem.ValidTime_3G = config.ValidTime_3G;
			cacheItem.ValidTime_WIFI = config.ValidTime_WIFI;

			// cache.ETag = "0";
			// cache.SaveToDB = true;
			// cache.IsCacheTimeout = true;
			// cache.ValidTime_3G = 300L;
			// cache.ValidTime_WIFI = 100L;

			if (!saveToDB) {
				cacheItem.CacheData = value;
			}

			new CahceItemConfigDao(context).putCacheItem(cacheItem);

			// CahcePrefsUtils.putString(context, uri.toString(), key,
			// gson.toJson(cache));
		} catch (Exception e) {
			Logger.e(this.getClass(), "【缓存管理类】保存缓存出错");
		}
	}

	/**
	 * 通过uri模块和key获取唯一缓存数据
	 * 
	 * @author zhangzhia 2013-10-24 下午2:55:54
	 * @param uri
	 * @param key
	 * @return
	 */
	public CacheItem getCacheData(ActionURI uri, String key) {
		if (configMap.size() == 0) {
			initConfig();
		}

		CacheItem cache = new CacheItem();

		/** zz测试代码 */
		// cache.ActionURI = ActionURI.LOGIN;
		// cache.IsCacheTimeout = true;
		// cache.Key = "11";
		// cache.DownloadTime = new Date();
		// cache.ETag = "0";
		// cache.IsCacheTimeout = true;
		// cache.ValidTime_3G = 300L;
		// cache.ValidTime_WIFI = 100L;

		cache = new CahceItemConfigDao(context).getCacheItem(uri, key);
		// cache = gson.fromJson(CahcePrefsUtils.getString(context,
		// uri.toString(), key), CacheItem.class);

		if (cache != null && cache.ETag == null) {
			cache.ETag = "0";
		}

		return cache;
	}

	/**
	 * 获取缓存ETag值
	 * 
	 * @author zhangzhia 2013-11-15 上午10:23:16
	 * @param uri
	 * @param key
	 * @return
	 */
	public String getCacheETag(ActionURI uri, String key) {
		if (configMap.size() == 0) {
			initConfig();
		}

		// CacheItem cache = gson.fromJson(CahcePrefsUtils.getString(context,
		// uri.toString(), key), CacheItem.class);

		CacheItem cache = new CahceItemConfigDao(context)
				.getCacheItem(uri, key);

		if (cache != null) {
			return cache.ETag;
		} else {
			return "0";
		}
	}

	/**
	 * 通过uri模块和key标记缓存超时
	 * 
	 * @author zhangzhia 2013-10-24 下午2:55:54
	 * @param uri
	 * @return
	 */
	public void setCacheExpires(ActionURI uri) {
		new CahceItemConfigDao(context).setCacheExpires(uri);
	}

	/**
	 * 通过uri模块删除缓存数据
	 * 
	 * @author zhangzhia 2013-10-24 下午2:55:54
	 * @param uri
	 * @return
	 */
	public void removeCache(ActionURI uri) {
		new CahceItemConfigDao(context).removeCache(uri);
	}

	/**
	 * 通过uri模块和key删除缓存数据
	 * 
	 * @author zhangzhia 2013-10-24 下午2:55:54
	 * @param uri
	 * @param key
	 * @return
	 */
	public void removeCache(ActionURI uri, String key) {
		new CahceItemConfigDao(context).removeCache(uri, key);
		// CahcePrefsUtils.remove(context, uri.toString(), key);
	}

	/**
	 * 通过uri模块删除缓存数据
	 * 
	 * @author zhangzhia 2013-10-24 下午2:55:54
	 * @return
	 */
	public void removeAllCache() {
		new CahceItemConfigDao(context).removeAllCache();
		// CahcePrefsUtils.remove(context);
	}
}