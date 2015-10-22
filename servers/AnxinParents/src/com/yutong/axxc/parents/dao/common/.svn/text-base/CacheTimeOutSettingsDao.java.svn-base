package com.yutong.axxc.parents.dao.common;

import com.yutong.axxc.parents.common.SharedPreferencesUtil;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 缓存超时标记管理类
 * 
 * @author zhangzhia 2013-9-3 下午7:01:45
 */
public class CacheTimeOutSettingsDao
{

    /** Preferences名称 */
    public static final String PREFS_NAME = SharedPreferencesUtil.PREFS_NAME_CACHE_TIMEOUT;

    /** 1. 查询推送规则信息接口，用户首次登录或者终端没有缓存信息时值为0。 */
    public static final String PUSH_MSG_RULE_ETAG = "etag_push_msg_rule";

    /** 2. 获取系统消息记录接口，用户首次登录或者终端没有缓存信息时值为0。 */
    public static final String SYSTEM_MSG_ETAG = "etag_system_msg";

    /** 3. 查询所有授权的家长信息接口，用户首次登录或者终端没有缓存信息时值为0。 */
    public static final String CERTIGIER_INFO_ETAG = "etag_certigier_info";

    /** 4. 获取学生列表信息接口，用户首次登录或者终端没有缓存信息时值为0。 */
    public static final String STUDENT_INFO_LIST_ETAG = "etag_student_info_list";

    /** 5. 获取学生个性信息接口，用户首次登录或者终端没有缓存信息时值为0。 */
    public static final String STUDENT_CUSTOM_INFO_ETAG = "etag_student_custom_info";

    /** 6. 获取学生乘车信息接口，用户首次登录或者终端没有缓存信息时值为0。 */
    public static final String STUDENT_RIDING_INFO_ETAG = "etag_student_riding_info";

    /** 7. 获取学生打卡日期接口，用户首次登录或者终端没有缓存信息时值为0。 */
    public static final String PUNCH_CARD_ETAG = "etag_punch_card";

    /** 8. 获取学生消息记录接口，用户首次登录或者终端没有缓存信息时值为0。 */
    public static final String MSG_HISTORY_ETAG = "etag_msg_history";

    /** 9. 获取学生路线信息接口，用户首次登录或者终端没有缓存信息时值为0。 */
    public static final String STUDENT_LINE_INFO_ETAG = "etag_student_line_info";

    /** 10. 获取学生站点信息接口，用户首次登录或者终端没有缓存信息时值为0。 */
    public static final String STUDENT_STATION_INFO_ETAG = "etag_student_station_info";
    
    /** 11. 获取站点提醒信息接口，用户首次登录或者终端没有缓存信息时值为0。 */
    public static final String STATION_REMIND_INFO_ETAG = "etag_station_remind_info";
    
    /** 12. 获取车辆信息接口，用户首次登录或者终端没有缓存信息时值为0。 */
    public static final String VEHICLE_INFO_ETAG = "etag_vehicle_info";
    
    
    

    /**
     * 保存
     * 
     * @param etag
     */
    public static void save(String belongEtag, String exkey, String sign)
    {
        SharedPreferencesUtil.set(PREFS_NAME, belongEtag + exkey, sign);
    }

    /**
     * 获取
     * 
     * @return
     */
    public static String get(String belongEtag, String exkey)
    {
        return SharedPreferencesUtil.get(PREFS_NAME, belongEtag + exkey, null);
    }

}
