/*******************************************************************************
 * @(#)VConstants.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.yutong.axxc.parents.view.common;

/**
 * 车辆查找常量类
 * @author <a href="mailto:wu.xp@neusoft.com">wuxp </a>
 * @version $Revision 1.1 $ 2013-3-12 下午04:17:11
 */
public class VConstants {

    /** 节点类型。01：企业； */
    public static final String ORG_NODE_TYPE = "01";

    /** 节点类型。02：车辆 */
    public static final String VEHICLE_NODE_TYPE = "02";

    /** 车辆状态。00：全部状态； */
    public static final String V_ALL_STATUS = "4";

    /** 车辆状态。01：在线； */
    public static final String V_ONLINE = "3";

    /** 车辆状态。02：离线； */
    public static final String V_OFFLINE = "0";

    /** 车辆状态。03：行驶； */
    public static final String V_MOVING = "1";

    /** 车辆状态。04：停驶； */
    public static final String V_STOPPED = "2";

    /** 车辆状态。00：全部状态； */
    public static final String V_ALL_STATUS_STR = "全部";

    /** 车辆状态。01：在线； */
    public static final String V_ONLINE_STR = "在线";

    /** 车辆状态。02：离线； */
    public static final String V_OFFLINE_STR = "离线";

    /** 车辆状态。03：行驶； */
    public static final String V_MOVING_STR = "行驶";

    /** 车辆状态。04：停驶； */
    public static final String V_STOPPED_STR = "停驶";

    /** 车辆地图状态。05：正常在线； */
    public static final String V_NORMAL = "5";

    /** 车辆地图状态。06：告警； */
    public static final String V_WARNING = "6";

    /** 树形列表父节点复选框 */
    public static final String G_CB = "G_CB";

    /** 树形列表子节点复选框 */
    public static final String C_CB = "C_CB";

    /** [车辆监控批量选择]到[消息调度]页面传递用的parcelable的key */
    public static final String V_PAR_KEY = "vehicle.parcelable";

    /** [照片查看]到[写标记]页面传递用的parcelable的key */
    public static final String PHOTO_MARK_PAR_KEY = "photo_mark_par_key";

    /** [写标记]到[照片查看]页面传递用的parcelable的key */
    public static final String PHOTO_SHOW_PAR_KEY = "photo_show_par_key";

    public static final String STR_NOMAL = "在线";

    public static final String STR_WARNING = "告警";

    public static final String STR_OFFLINE = "离线";

    /** 消息发送结果提示 */
    public static final String MSG_ALL_SUCCESS_PROMPT = "全部车辆消息发送成功";

    /** 消息发送结果提示 */
    public static final String MSG_FAILED_PROMPT = "辆离线车辆发送失败";

    /** 拍照结果提示 -成功 */
    public static final String PHOTO_ALL_SUCCESS_PROMPT = "全部车辆拍照成功";

    /** 拍照结果提示-失败 */
    public static final String PHOTO_FAILED_PROMPT = "辆停驶离线车辆拍照失败";
    
    /** 没有在线车辆-失败*/
    public static final String PHOTO_ALL_FAILED_PROMPT="请至少选择一辆行驶车辆";

    /** 下发通道,紧急 */
    public static final String MSG_URGENT = "00";

    /** 下发通道,语音播报 */
    public static final String MSG_VOICE = "01";

    /** 下发通道,车载屏 */
    public static final String MSG_SCREENSHOT = "02";

    /** 下发通道,终端显示器 */
    public static final String MSG_TERMINALSCREEN = "03";

    /** 下发通道,整车 */
    public static final String PHOTO_WHOLEVEHILCLE = "1";

    /** 下发通道,路况 */
    public static final String PHOTO_ROADCONDITION = "2";

    /** 下发通道,车门 */
    public static final String PHOTO_GATE = "3";

    /** 下发通道,司机 */
    public static final String PHOTO_DRIVER = "4";

    public static final String MSG_SEPARATOR = "|";

    /** 省略号 */
    public static final String STR_ELLIPSES = "……";

    /** 左括号 */
    public static final String LEFT_BRACKET = "(";

    /** 右括号 */
    public static final String RIGHT_BRACKET = ")";

    /** 模板內容 的key */
    public static final String TEMPLATE = "template";

    /** 问题反馈內容 的key */
    public static final String ADVISE_CONTENT_KEY = "advise_content_key";

    /** 问题反馈时间 的key */
    public static final String ADVISE_TIME_KEY = "advise_time_key";

    /** 模板bundle 的key */
    public static final String MSG_BUNDLE_KEY = "msg_bundle_key";

    public static final String MAP_TYPE = "map_type";

    public static final int STATE_BUS = 0;

    public static final int STATE_STATION = 1;

    public static final String BUS_DETAIL_KEY = "bus_detail";

    /** 上行站点标识 */
    public static final String V_UP_STATION = "00";

    /** 下行站点标识 */
    public static final String V_DOWN_STATION = "01";

    /** 站点搜索方向word */
    public static final String V_STATION_DIRATION_CODE = "diration_code";

    /** 下发命令的照片 */
    public static final int TYPE_ORDER = 1;

    /** 全部照片 */
    public static final int TYPE_ALL = 0;

    /** 照片搜索异步任务类型-命令远程 */
    public static final int PHOTO_SEARCH_ORDER_REMOTE = 0;

    /** 照片搜索异步任务类型-全部远程 */
    public static final int PHOTO_SEARCH_ALL_REMOTE = 1;

    /** 照片搜索异步任务类型-本地 */
    public static final int PHOTO_SEARCH_LOCAL = 2;

    /** 车辆信息的key */
    public static final String BUS_INFO_KEY = "bus_info_key";

    /** 照片位置的key */
    public static final String PHOTO_POSITION_KEY = "photo_position_key";

    /** 照片类型 */
    public static final String PHOTO_TYPE = "photo_type";

    /** 照片日期 */
    public static final String PHOTO_SEARCH_DATE = "photo_search_data";

    /** 照片搜索车辆ID */
    public static final String PHOTO_SEARCH_BUS_ID = "photo_search_bus_id";

    /** 照片搜索组织ID */
    public static final String PHOTO_SEARCH_BUS_TYPE = "photo_search_bus_type";

    /** 照片超载标记 */
    public static final String PHOTO_OVERLOAD_MARK = "超载";

    public static final String BUS_NAME_KEY = "bus_name_key";

    public static final String BUS_ID_KEY = "bus_id_key";

    public static final String BUS_LOCUS_FROM_MAP = "bus_locus_from_map";

    public static final String BUS_DRIVER_NAME = "bus_driver_name";

    public static final int DATE_FLAG = 1;

    public static final int TIME_FLAG = 2;

    public static final String MAIL = "mail";

    public static final String END_TIME = "endTime";

    public static final String START_TIME = "startTime";

    public static final String MORE_ALL = "更多全部照片";

    public static final String MORE_ORDER = "更多手机拍摄照片";

    /* 进入单车列表界面请求消息码,返回需要相同的消息码 */
    public static final int GET_BUS_ID_CODE = 3;

    /* 标题通用适配器行数据key值 */
    public static final String COMMON_ADPTER_KEY = "adper_key";

    /* 传递搜索条件框是否可见 */
    public static final String SEARCH_RL_KEY = "search_rl_key";

    /** 照片标记类型。1 – 标记为超载 2 – 普通标记 */
    public static final String MARK_OVERLOAD_TYPE = "1";

    /** 照片标记类型。1 – 标记为超载 2 – 普通标记 */
    public static final String MARK_NORMAL_TYPE = "2";

    /** 从地图进入批量消息码 */
    public static final int POINT_MAP = 1;

    /** 从组织进入批量消息码 */
    public static final int POINT_LIST = 2;

    /** 信息推送设置id： 接收最新行业法规 */
    public static final String PUSH_MSG_REC_LAW = "接收最新行业法规";

    /** 信息推送设置id： 接收宇通营销信息 */
    public static final String PUSH_MSG_REC_MAR = "接收宇通营销消息";

    /** 信息推送设置id： 超速告警提示 */
    public static final String PUSH_MSG_OVERSPEED = "超速告警提示";

    /** 信息推送设置id： 超载告警提示 */
    public static final String PUSH_MSG_OVERLOAD = "超载告警提示";

    /** 信息推送设置id： 照片到达推送 */
    public static final String PUSH_MSG_PHOTO = "照片到达推送";

    /** 信息推送设置id： 接收最新行业法规 */
    public static final String PUSH_MSG_REC_LAW_ID = "01";

    /** 信息推送设置id： 接收宇通营销信息 */
    public static final String PUSH_MSG_REC_MAR_ID = "02";

    /** 信息推送设置id： 超速告警提示 */
    public static final String PUSH_MSG_OVERSPEED_ID = "03";

    /** 信息推送设置id： 超载告警提示 */
    public static final String PUSH_MSG_OVERLOAD_ID = "04";

    /** 信息推送设置id： 照片到达推送 */
    public static final String PUSH_MSG_PHOTO_ID = "05";

    /** 信息推送设置： 是否推送：1– 开；0– 关 */
    public static final String PUSH_MSG_OFF = "0";

    /** 信息推送设置： 是否推送：1– 开；0– 关 */
    public static final String PUSH_MSG_ON = "1";

    /** 信息推送设置： 是否提示：1– 提示； 0– 不提示 */
    public static final String PUSH_MSG_NOHINT = "0";

    /** 信息推送设置： 是否提示：1– 提示； 0– 不提示 */
    public static final String PUSH_MSG_HINT = "1";

    /** 照片查询-无结果 */
    public static final String PHOTO_NO_RESULT = "无查询结果";

    /** 照片查询-按日期查询无结果 */
    public static final String PHOTO_SEARCH_NO_PHOTO = "未查询到照片";
}
