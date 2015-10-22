/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:18:51
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business;

import java.io.File;

import android.content.Context;

import com.yutong.clw.ygbclient.business.linestation.GetStationBiz;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.constant.GlobleConstants;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.prefs.SettingPrefsUtils;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;
import com.yutong.clw.ygbclient.dao.common.CommonDao;
import com.yutong.clw.ygbclient.dao.login.LoginInfoDao;

/**
 * 代理业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午10:18:51
 */
public class ProxyManager
{
    /** Context对象 */
    protected Context context;

    private String logTypeName = "[代理业务逻辑类]:";

    private static ProxyManager singleton = null;

    public static synchronized ProxyManager getInstance(Context context)
    {
        if (singleton == null)
        {
            singleton = new ProxyManager(context);
        }
        return singleton;
    }

    private ProxyManager(Context context)
    {
        this.context = context;
        init();
    }

    private void init()
    {
        // initCahceConfig();
    }

    /**
     * 初始化超时机制配置，分3G和WIFI网络。
     * 
     * @author zhangzhia 2013-10-25 下午6:04:50
     */
    // private void initCahceConfig()
    // {
    // // TODO
    // }

    /**
     * 读取AccessToken
     * 
     * @author zhangzhia 2013-11-8 下午2:25:12
     * @return
     */
    public String getAccessToken()
    {
        return SettingPrefsUtils.getString(context, GlobleConstants.SHARED_ACCESS_TOKEN);
    }

    /**
     * 保存AccessToken
     * 
     * @author zhangzhia 2013-11-8 下午2:25:01
     * @param accessToken
     */
    public void setAccessToken(String accessToken)
    {
        SettingPrefsUtils.putString(context, GlobleConstants.SHARED_ACCESS_TOKEN, accessToken);
    }

    /**
     * 读取员工号
     * 
     * @author zhangzhia 2013-11-8 下午2:24:51
     * @return
     */
    public String getUserCode()
    {
        return SettingPrefsUtils.getString(context, GlobleConstants.SHARED_USER_CODE);
    }

    /**
     * 保存员工号
     * 
     * @author zhangzhia 2013-11-8 下午2:24:36
     * @param userCode
     */
    public void setUserCode(String userCode)
    {
        SettingPrefsUtils.putString(context, GlobleConstants.SHARED_USER_CODE, userCode);
    }

    /**
     * 清除日志
     * 
     * @author zhangzhia 2013-11-8 下午2:24:27
     */
    public static void clearLogs()
    {
        String logPath = Logger.getProperty("save path", "/sdcard/yutong/xcp/log/applog.log");
        new File(logPath).delete();
    }

    /**
     * 清除资源数据缓存
     * 
     * @author zhangzhia 2013-11-8 下午2:23:55
     */
    public static void clearResCache()
    {
        String logPath = SysConfigUtil.getResCachePath();
        new File(logPath).delete();
    }

    /**
     * 清除网络数据缓存
     * 
     * @author zhangzhia 2013-11-8 下午2:23:40
     */
    public static void clearNetCache()
    {
        String logPath = SysConfigUtil.getNetCachePath();
        new File(logPath).delete();
    }

    /**
     * 清除登录信息
     * 
     * @author zhangzhia 2013-11-8 下午2:23:08
     */
    public void clearLoginInfo()
    {
        new LoginInfoDao(context).delLoginInfo();
    }

    /**
     * 清除本地缓存
     * 
     * @author zhangzhia 2013-11-8 下午2:23:08
     */
    public void clearAppCache()
    {
        // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_NEWS_SUMMARY);
        //
        // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_NEWS_DETAIL);
        // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_PUSH_MSG_RULES);
        // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_AREA_LINES);
        // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_IDS_LINES);
        // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_RECOMMEND_STATIONS);
        // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_STATIONS);
        // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_STATION_AREAS);
        // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_REMIND_STATIONS);
        // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_FAVORITES);

        new CommonDao(context).cleanCahce();

        clearLogs();
        clearResCache();
        clearNetCache();

        addStationInfo();
    }
    

    public void addStationInfo()
    {
        // #region 加载站点列表信息
        try
        {
            Logger.w(this.getClass(), logTypeName + "开始加载站点列表信息......");
            GetStationBiz biz = new GetStationBiz(context);
            if (biz.IsCacheExpires())
            {
                biz.getStationInfosFromSever();
            }
        }
        catch (CommonException ce)
        {
            Logger.e(this.getClass(), logTypeName + "加载站点列表信息失败", ce);
        }

        // #endregion

    }
}
