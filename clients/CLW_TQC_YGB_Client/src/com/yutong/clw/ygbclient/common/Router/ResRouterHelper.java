package com.yutong.clw.ygbclient.common.Router;

import android.content.Context;

import com.yutong.clw.ygbclient.dao.Router.ResRouterDao;

public class ResRouterHelper
{
    /** Context对象 */
    private Context context;

    private ResRouterDao dao;

    // /**
    // * 资源路由【需要存入sqlite数据库】 key：网络资源唯一标志 value：本地缓存资源唯一标识
    // */
    // private Map<String, String> resRouter = new HashMap<String, String>();

    private static ResRouterHelper singleton = null;

    public static synchronized ResRouterHelper getInstance(Context context)
    {
        if (singleton == null)
        {
            singleton = new ResRouterHelper(context);
        }
        return singleton;
    }

    private ResRouterHelper(Context context)
    {
        this.context = context;
        dao = new ResRouterDao(context);
    }

    /**
     * 保存路由资源
     * 
     * @author zhangzhia 2013-10-14 上午11:53:04
     * @param key
     * @param value
     */
    public boolean save(String key, ResRouterItem router)
    {
        return dao.save(key, router);
    }

    /**
     * 获取路由资源
     * 
     * @author zhangzhia 2013-10-14 上午11:53:16
     * @param key
     * @return
     */
    public ResRouterItem get(String key)
    {
        return dao.get(key);
    }

    /**
     * 此key是否存在路由资源
     * 
     * @author zhangzhia 2013-10-14 上午11:53:16
     * @param key
     * @return
     */
    public boolean containsKey(String key)
    {
        return dao.containsKey(key);
    }
}
