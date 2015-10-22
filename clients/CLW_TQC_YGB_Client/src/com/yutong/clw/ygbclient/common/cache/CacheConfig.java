package com.yutong.clw.ygbclient.common.cache;

import java.io.Serializable;

import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.TimeoutMethod;

/**
 * 缓存配置实体类
 * 
 * @author zhangzhia 2013-10-24 下午5:31:47
 */
public class CacheConfig implements Serializable
{
    /**
     * @author zhangzhia 2013-10-23 上午11:38:41
     */
    private static final long serialVersionUID = 1L;

    /** ActionURI */
    public ActionURI ActionURI;

    /** 超时方式 */
    public TimeoutMethod CacheTimeoutMethod = TimeoutMethod.Normal;

    /** 是否需要缓存超时机制 */
    public boolean NeedCacheMechanism;

    /** 是否将数据存储到数据库，true：数据库，false：Json序列化到CacheData字段中 */
    //public boolean SaveToDB;

    /** 移动网络下有效时间,单位秒 */
    public Long ValidTime_3G;

    /** WIFI网络下有效时间,单位秒 */
    public Long ValidTime_WIFI;

    public CacheConfig()
    {
    }

    // public CacheConfig(ActionURI actionURI, Long validTime_3G, Long
    // validTime_WIFI)
    // {
    // ActionURI = actionURI;
    // ValidTime_3G = validTime_3G;
    // ValidTime_WIFI = validTime_WIFI;
    // }

    public CacheConfig(ActionURI actionURI, boolean needCacheMechanism, Long validTime_3G, Long validTime_WIFI)
    {
        ActionURI = actionURI;
        NeedCacheMechanism = needCacheMechanism;
        ValidTime_3G = validTime_3G * 1000;
        ValidTime_WIFI = validTime_WIFI * 1000;
//        ValidTime_3G = 0L;
//        ValidTime_WIFI = 0L;
    }

}
