/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-19 下午2:54:49
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.common.tables;


/**
 * @author zhangzhia 2013-11-19 下午2:54:49
 */
public class CahceItemConfigTable
{
    /** 表名称 */
    public static final String Table_Name = "CahceItemConfig";

    /** ActionURI */
    public static final String ActionURI = "ActionURI";

    /** 缓存KEY */
    public static final String Key = "Key";

    /** 下载时间 ,单位秒 */
    public static final String DownloadTime = "DownloadTime";

    /** 超时方式 */
    public static final String CacheTimeoutMethod = "CacheTimeoutMethod";

    /** 缓存ETag */
    public static final String ETag = "ETag";

    /** 缓存数据 【预留】 */
    public static final String CacheData = "CacheData";

    /** 是否超时 */
    public static final String IsCacheExpires = "IsCacheExpires";

    /** 是否需要缓存超时机制 */
    public static final String NeedCacheMechanism = "NeedCacheMechanism";

    /** 移动网络下有效时间,单位秒 */
    public static final String ValidTime_3G = "ValidTime_3G";

    /** WIFI网络下有效时间,单位秒 */
    public static final String ValidTime_WIFI = "ValidTime_WIFI";


    /**
     * 创建表
     * 
     * @author zhangzhia 2013-9-6 下午6:25:34
     * @return
     */
    public static String getTableSQL()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(CahceItemConfigTable.Table_Name).append("(");
        builder.append(CahceItemConfigTable.ActionURI).append(" TEXT,");
        builder.append(CahceItemConfigTable.Key).append(" TEXT,");
        builder.append(CahceItemConfigTable.DownloadTime).append(" TEXT,");
        builder.append(CahceItemConfigTable.CacheTimeoutMethod).append(" TEXT,");
        builder.append(CahceItemConfigTable.ETag).append(" TEXT,");
        builder.append(CahceItemConfigTable.CacheData).append(" TEXT,");
        builder.append(CahceItemConfigTable.IsCacheExpires).append(" TEXT,");
        builder.append(CahceItemConfigTable.NeedCacheMechanism).append(" TEXT,");
        builder.append(CahceItemConfigTable.ValidTime_3G).append(" TEXT,");
        builder.append(CahceItemConfigTable.ValidTime_WIFI).append(" TEXT");
        builder.append(");");
        return builder.toString();
    }
}
