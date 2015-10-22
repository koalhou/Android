package com.yutong.clw.ygbclient.common.cache;

import java.io.Serializable;
import java.util.Date;

import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.TimeoutMethod;

public class CacheItem implements Serializable
{
    /**
     * @author zhangzhia 2013-10-23 上午11:38:41
     */
    private static final long serialVersionUID = 1L;

    /** ActionURI */
    public ActionURI ActionURI;

    /** 缓存KEY */
    public String Key;

    /** 下载时间 ,单位秒 */
    public Date DownloadTime = new Date();

    /** 超时方式 */
    public TimeoutMethod Method = TimeoutMethod.Normal;

    /** 缓存ETag */
    public String ETag = "0";

    /** 缓存数据 */
    public String CacheData;
    
    /** 是否超时 */
    public boolean IsCacheExpires = false;

    /** 是否需要缓存超时机制 */
    public boolean NeedCacheMechanism;

    /** 是否将数据存储到数据库，true：数据库，false：字符串 */
    //public boolean SaveToDB = false;

    /** 移动网络下有效时间,单位秒 */
    public Long ValidTime_3G;

    /** WIFI网络下有效时间,单位秒 */
    public Long ValidTime_WIFI;

}
