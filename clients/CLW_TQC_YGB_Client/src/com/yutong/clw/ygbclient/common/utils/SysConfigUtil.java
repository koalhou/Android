package com.yutong.clw.ygbclient.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 系统配置工具�?
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
    
    public static void setProperty(String propertyName, String defaultValue) {
        if (properties == null) {
            properties = initProperties();
        }
        properties.setProperty(propertyName, defaultValue);
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
    
    public static String getHttpApiPath() {
        return getProperty("HTTP_API_PATH").trim();
    }


    /**
     * HTTP请求�?��超时时间
     *@author zhangzhia 2013-9-10 上午10:52:21
     *
     * @return
     */
    public static int getHttpConnTimeout() {
        return Integer.valueOf(getProperty("HTTP_CONN_TIMEOUT", "30000").trim());
    }


    /**
     * HTTP响应�?��超时时间
     *@author zhangzhia 2013-9-10 上午10:52:21
     *
     * @return
     */
    public static int getHttpMaxResendCount() {
        return Integer.valueOf(getProperty("HTTP_RESEND_COUNT", "3").trim());
    }

    /**
     * HTTP接收�?��超时时间
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
     * 临时网络缓存目录
     *@author zhangzhia 2013-9-10 上午10:51:49
     *
     * @return
     */
    public static String getNetCachePath() {
        return getProperty("NET_CACHE_PATH").trim();
    }
    
    /**
     * 文件读取大小，字�?
     *@author zhangzhia 2013-9-10 上午10:51:01
     *
     * @return
     */
    public static int getFileReadSize() {
        return Integer.valueOf(getProperty("FILE_READ_SIZE", "1024").trim());
    }

    /**
     * 新闻每次获取的最大条数
     *@author zhangzhia 2013-11-11 下午1:59:54
     *
     * @return
     */
    public static int getPageSize() {
        return Integer.valueOf(getProperty("PAGE_SIZE", "16").trim());
    }

    /**
     * 车辆实时数据�?��刷新时间，秒
     *@author zhangzhia 2013-9-10 上午10:50:00
     *
     * @return
     */
    public static int getMinVehRefreshTime() {
        return Integer.valueOf(getProperty("MIN_VEH_REFRESH_TIME", "120").trim());
    }


    /**
     * 图片下载提示字节大小，字�?
     *@author zhangzhia 2013-9-10 上午10:50:17
     *
     * @return
     */
    public static int getPhotoDownloadLimit() {
        return Integer.valueOf(getProperty("PHOTO_DOWNLOAD_LIMIT", "1024").trim());
    }
    
    /**
     * 资源平台，字�?
     *@author zhangzhia 2013-9-10 上午10:50:17
     *
     * @return
     */
    public static String getPlatfrom() {
        return getProperty("PLATFORM", "anxin_tqc").trim();
    }
    
    public static int getUserBehaviorMaxSize() {
        return Integer.valueOf(getProperty("USER_BEHAVIOR_MAX_SIZE", "20").trim());
    }

    public static boolean getCacheOpen() {
        return Boolean.valueOf(getProperty("CacheOpen", "true").trim());
    }
    
    public static void  setCacheOpen(boolean status) {
       setProperty("CacheOpen", String.valueOf(status));
    }
    
    public static String getBaiduMapKey(){
    	   return getProperty("BAIDU_MAP_KEY", "105cbf0678f4e5ffd7f484aa8e8be3f7").trim();
    }
    
    public static String getDriverPhotoLocation() {
        return  String.valueOf(getProperty("DRIVER_PHOTO_LOCATION", "UTF-8").trim());
    }
    
    public static int getCodeReSendSeconds(){
        return Integer.valueOf(getProperty("CODE_EXPIRE_SECONDS", "20").trim());
 }
    
}
