
package com.yutong.axxc.parents.common;

/**
 * 线程通信状态码类，与UI线程交互状态码在此类中定义
 * @author zhangzhia 2013-9-10 上午11:18:21
 *
 */
public final class ThreadCommStateCode {

    private ThreadCommStateCode() {
    }

    /** token失效消息码 */
    public static final int TOKEN_INVALID = 0x0000;

    /** 网络错误 */
    public static final int NETWORK_ERROR = 0x7009;
    
    /** 请求成功 */
    public static final int COMMON_SUCCESS = 0x1111;
    
    /** 请求失败 */
    public static final int COMMON_FAILED = 0x1112;
    
    
    /** 缓存无数据 */
    public static final int CACHE_NO_DATA = 0x1113;

    /** 网络数据已变化 */
    public static final int REMOTE_DATA_CHANGED = 0x1114;
    
    /** 网络数据已变化 */
    public static final int REMOTE_DATA_NO_CHANGED = 0x1115;

    
    
    
    
    /** GetRealTimeInfoBiz */
    public static final int REALTIMEINFO_NO_DATA = 0x1102;
    
    
    
    /** 用户注册-注册成功 */
    public static final int REG_REGISTER_SUCCESS = 0x1103;
    /** 用户注册-注册失败 */
    public static final int REG_REGISTER_FAILED = 0x1104;
    
    /** 用户登录-无用户信息 */
    public static final int LOGIN_NO_USER_INFO = 0x1001;

    /** 用户登录-存在用户信息 */
    public static final int LOGIN_EXIST_USER_INFO = 0x1002;

    /** 用户登录-登录成功 */
    public static final int LOGIN_SUCCESS = 0x1003;

    /** 用户登录-登录失败 */
    public static final int LOGIN_FAILED = 0x1004;


    
    
    
    
    /** 用户登录-自动登录成功 */
    public static final int AUTO_LOGIN_SUCCESS = 0x100C;

    /** 用户登录-自动登录失败 */
    public static final int AUTO_LOGIN_FAILED = 0x100D;

    /** 用户登录-需升级 */
    public static final int NEED_UPGRADE = 0x100E;
    
    /** 用户登录-强制升级 */
    public static final int FORCE_UPGRADE = 0x100F;

    /** 用户登录-不需升级 */
    public static final int NO_NEED_UPGRADE = 0x1012;

    /** 检查版本失败 */
    public static final int CHK_VERSION_FAILED = 0x1011;

    /** 检查版本 网络异常 */
    public static final int CHK_VERSION_NETWORK_EXCPT = 0x1013;

    /** 主页-获取图标 */
    public static final int IMAGE_GET = 0x1010;

   
    /** 问题反馈成功 */
    public static final int ADVISE_SUCCESS = 0x4001;

    /** 问题反馈失败 */
    public static final int ADVISE_FAILED = 0x4002;



    /** 旧密码验证成功 */
    public static final int OLD_PWD_CHECK_SUCCESS = 0x4004;

    /** 旧密码验证失败 */
    public static final int OLD_PWD_CHECK_FAILED = 0x4005;

    /** 密码修改成功 */
    public static final int PWD_UPDATE_SUCCESS = 0x4006;

    /** 密码修改失败 */
    public static final int PWD_UPDATE_FAILED = 0x4007;

    
    /** 查询推送规则成功 */
    public static final int PUSH_MSG_GET_SUCCESS = 0x4008;

    /** 查询推送规则失败 */
    public static final int PUSH_MSG_GET_FAILED = 0x4009;

    /** 保存推送规则成功 */
    public static final int PUSH_MSG_SAVE_SUCCESS = 0x400A;

    /** 保存推送规则失败 */
    public static final int PUSH_MSG_SAVE_FAILED = 0x400B;

    /** 不需要保存推送规则 */
    public static final int PUSH_MSG_NEEDNOT_SAVE = 0x4010;

    /** 从本地查询推送规则成功 */
    public static final int PUSH_MSG_DB_GET_SUCCESS = 0x400C;

    /** 从本地查询推送规则失败 */
    public static final int PUSH_MSG_DB_GET_FAILED = 0x400D;



    /** 照片查找成功 */
    public static final int PHOTO_ORDER_SEARCH_SUCCESS = 0x7001;

    /** 照片查找失败 */
    public static final int PHOTO_ORDER_SEARCH_FAILED = 0x7002;

    /** 照片下载成功 */
    public static final int PHOTO_DOWNLOAD_SUCCESS = 0x7003;

    /** 照片下载失败 */
    public static final int PHOTO_DOWNLOAD_FAILED = 0x7004;


    /** 照片获取流量超载 */
    public static final int PHOTO_IMAGE_GET_OVERFLOW = 0x700A;


    /** 版本更新失败 */
    public static final int VERSON_UPDATE_FAILED = 0xC001;

    /** 版本更新完成 */
    public static final int VERSION_UPDATE_COMPLETE = 0xC002;

    /** 版本更新中 */
    public static final int VERSION_UPDATING = 0xC003;
    /** 版本更新失败 */
    public static final int VERSON_UPDATE_CANCEL = 0xC004;

    
    
    /** 查询返回条数 */
    public static final int COMMON_SEARCH_RES_COUNT = 0xD001;

    /** 查询无返回 */
    public static final int COMMON_SEARCH_NO_RES = 0xD002;


    
    
    
    /** 天气模块 */
    
    /** 其他-查询天气信息成功 */
    public static final int HOME_GET_WEATHER_SUCCESS = 0x1008;

    /** 其他-查询天气信息失败 */
    public static final int HOME_GET_WEATHER_FAIL = 0x1009;
    

}
