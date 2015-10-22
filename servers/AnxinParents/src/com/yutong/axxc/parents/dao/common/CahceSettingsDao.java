package com.yutong.axxc.parents.dao.common;

import com.yutong.axxc.parents.common.SharedPreferencesUtil;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 缓存Etag标记管理类
 * 
 * @author zhangzhia 2013-9-3 下午7:01:45
 */
public class CahceSettingsDao
{

    /** Preferences名称 */
    public static final String PREFS_NAME = SharedPreferencesUtil.PREFS_NAME_CACHE;

    /** 1. 查询推送规则信息 */
    public static final String PUSH_MSG_RULE_ETAG = "push_msg_rule";

    /** 2. 获取系统消息记录 */
    public static final String SYSTEM_MSG_ETAG = "system_msg";

    /** 3. 查询所有授权的家长信息 */
    public static final String CERTIGIER_INFO_ETAG = "certigier_info";

    /** 4. 获取学生列表信息 */
    public static final String STUDENT_INFO_LIST_ETAG = "student_info_list";

    /** 5. 获取学生个性信息 */
    public static final String STUDENT_CUSTOM_INFO_ETAG = "student_custom_info";

    /** 6. 获取学生乘车信息 */
    public static final String STUDENT_RIDING_INFO_ETAG = "student_riding_info";

    /** 7. 获取学生打卡日期 */
    public static final String PUNCH_CARD_ETAG = "punch_card";

    /** 8. 获取学生消息记录*/
    public static final String MSG_HISTORY_ETAG = "msg_history";

    /** 9. 获取学生路线信息 */
    public static final String STUDENT_LINE_INFO_ETAG = "student_line_info";

    /** 10. 获取学生站点信息 */
    public static final String STUDENT_STATION_INFO_ETAG = "student_station_info";
    
    /** 11. 获取站点提醒信息 */
    public static final String STATION_REMIND_INFO_ETAG = "station_remind_info";
    
    /** 12. 获取车辆信息 */
    public static final String VEHICLE_INFO_ETAG = "vehicle_info";
    
    /** 12. 用户行为信息 */
    public static final String USER_BEHAVIOR_INFO = "user_behavior_info";
    
    /** 12. 用户使用时长信息 */
    public static final String USER_USED_DURATION = "user_used_durationBean_info";
    
    
    /**
     * 保存数据到配置文件
     * 
     * @param etag
     */
    public static void setCacheInfo(String belongEtag, String exkey, String jsonInfo)
    {
        SharedPreferencesUtil.set(PREFS_NAME, belongEtag + exkey, jsonInfo);
    }

    /**
     * 从配置文件获取数据
     * 
     * @return
     */
    public static String getCacheInfo(String belongEtag, String exkey)
    {
        return SharedPreferencesUtil.get(PREFS_NAME, belongEtag + exkey, null);
    }

}
