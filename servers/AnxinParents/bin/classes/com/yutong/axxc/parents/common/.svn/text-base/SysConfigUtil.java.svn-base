package com.yutong.axxc.parents.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 系统配置工具类
 * @author zhangzhia 2013-8-23 下午2:17:46
 *
 */
public final class SysConfigUtil {

    private static Properties properties = initProperties();

    private SysConfigUtil() {
    }

    private static Properties initProperties() {
        Properties props = new Properties();
        try {
            InputStream in = SysConfigUtil.class.getResourceAsStream("/assets/sysconfig.properties");
            props.load(in);
        } catch (IOException e) {
        }
        return props;
    }

    private static String getProperty(String propertyName) {
        if (properties == null) {
            properties = initProperties();
        }
        return properties.getProperty(propertyName);
    }


    public static String getProperty(String propertyName, String defaultValue) {
        if (properties == null) {
            properties = initProperties();
        }
        return properties.getProperty(propertyName, defaultValue);
    }
    
    /**
     * HTTP服务IP地址
     *@author zhangzhia 2013-9-10 上午10:52:21
     *
     * @return
     */
    public static String getHttpServerUrl() {
        return getProperty("HTTP_SERVER_URL").trim();
    }


    /**
     * HTTP请求最大超时时间
     *@author zhangzhia 2013-9-10 上午10:52:21
     *
     * @return
     */
    public static int getHttpConnTimeout() {
        return Integer.valueOf(getProperty("HTTP_CONN_TIMEOUT", "30000").trim());
    }


    /**
     * HTTP响应最大超时时间
     *@author zhangzhia 2013-9-10 上午10:52:21
     *
     * @return
     */
    public static int getHttpMaxResendCount() {
        return Integer.valueOf(getProperty("HTTP_RESEND_COUNT", "3").trim());
    }

    /**
     * HTTP接收最大超时时间
     *@author zhangzhia 2013-9-10 上午10:52:21
     *
     * @return
     */
    public static int getHttpRecvTimeout() {
        return Integer.valueOf(getProperty("HTTP_RECV_TIMEOUT", "300000").trim());
    }

    /**
     * HTTP数据解析编码
     *@author zhangzhia 2013-9-10 上午10:52:21
     *
     * @return
     */
    public static String getCharset() {
        return String.valueOf(getProperty("UTF-8", "UTF-8").trim());
    }

    /**
     * 资源缓存目录
     *@author zhangzhia 2013-9-10 上午10:51:49
     *
     * @return
     */
    public static String getResCachePath() {
        return getProperty("RES_CACHE_PATH").trim();
    }

    /**
     * 临时图片缓存目录
     *@author zhangzhia 2013-9-10 上午10:51:49
     *
     * @return
     */
    public static String getPhotoCachePath() {
        return getProperty("PHOTO_CACHE_PATH").trim();
    }
    
    /**
     * 文件读取大小，字节
     *@author zhangzhia 2013-9-10 上午10:51:01
     *
     * @return
     */
    public static int getFileReadSize() {
        return Integer.valueOf(getProperty("FILE_READ_SIZE", "1024").trim());
    }


    public static int getPageSize() {
        return Integer.valueOf(getProperty("PAGE_SIZE", "16").trim());
    }

    /**
     * 车辆实时数据最小刷新时间，秒
     *@author zhangzhia 2013-9-10 上午10:50:00
     *
     * @return
     */
    public static int getMinVehRefreshTime() {
        return Integer.valueOf(getProperty("MIN_VEH_REFRESH_TIME", "120").trim());
    }


    /**
     * 图片下载提示字节大小，字节
     *@author zhangzhia 2013-9-10 上午10:50:17
     *
     * @return
     */
    public static int getPhotoDownloadLimit() {
        return Integer.valueOf(getProperty("PHOTO_DOWNLOAD_LIMIT", "1024").trim());
    }
    
    /**
     * 资源平台，字节
     *@author zhangzhia 2013-9-10 上午10:50:17
     *
     * @return
     */
    public static String getPlatfrom() {
        return getProperty("PLATFORM", "AnxiParents").trim();
    }
    
    public static int getUserBehaviorMaxSize() {
        return Integer.valueOf(getProperty("USER_BEHAVIOR_MAX_SIZE", "20").trim());
    }


}
