package com.yutong.clw.ygbclient.common;

import java.util.HashMap;
import java.util.Map;

import com.yutong.clw.ygbclient.common.beans.UriAction;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.HttpAction;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;

/**
 * uri工厂管理类
 * 
 * @author zhangzhia 2013-10-21 上午9:17:40
 */
public class UriFactory
{

    /** API host地址 */
    public static final String API_BASE_URL = SysConfigUtil.getHttpServerUrl(); 

    /** API 路径地址 */
    public static final String API_PATH_URL = API_BASE_URL; // +
                                                            // SysConfigUtil.getHttpApiPath();

    /** uri字典 */
    @SuppressWarnings("serial")
    private static final Map<String, UriAction> uriMap = new HashMap<String, UriAction>()
    {
        {
            // -----------登陆绑定鉴权----------------------
            // 手机绑定
            put(ActionURI.BIND_PHONE.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/account/phonebind"));
            // 登陆
            put(ActionURI.LOGIN.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/account/login"));
            // 验证帐号
            put(ActionURI.VERIFY_ACCOUNT.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/account/accountcheck"));
            // 找回密码
            put(ActionURI.FORGOT_PASSWORD.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/account/pwd"));
            // AccessToken更新
            put(ActionURI.REFRESH_ACCESSTOKEN.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/account/refresh"));
            // 自动登陆
            put(ActionURI.AUTO_LOGIN.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/account/autologin"));
            // 退出登陆
            put(ActionURI.LOGOUT.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/account/logout"));

            // -----------系统设置----------------------
            // 意见反馈
            put(ActionURI.ADVISE_FEEDBACK.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/system/advise"));
            // 意见反馈回复获取
            put(ActionURI.ADVISE_REPLY.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/system/advisereply"));
            // /获取新闻概要
            put(ActionURI.GET_NEWS_SUMMARY.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/system/newssummary"));
            // 获取新闻详细
            put(ActionURI.GET_NEWS_DETAIL.toString(), new UriAction(API_PATH_URL, HttpAction.Get, "/system/newsdetail/"));
            // 获取推送规则
            put(ActionURI.GET_PUSH_MSG_RULES.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/system/getpushrule"));
            // 设置推送规则
            put(ActionURI.SET_PUSH_MSG_RULES.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/system/setpushrule"));
            // 版本检查
            put(ActionURI.CHECK_VERSION.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/version/version"));
            // 旧密码验证
            put(ActionURI.VERIFY_OLD_PASSWORD.toString(), new UriAction(API_PATH_URL, HttpAction.Get, "/system/check/"));
            // 用户密码修改
            put(ActionURI.SET_NEW_PASSWORD.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/system/pwd"));

            // -----------线路与站点相关----------------------
            // 获取厂区范围线路信息
            put(ActionURI.GET_AREA_LINES.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/lineunionin"));
            // 获取编号集合的线路信息
            put(ActionURI.GET_IDS_LINES.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/lineunionout"));
            // 获取推荐站点列表信息
            put(ActionURI.GET_RECOMMEND_STATIONS.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/default"));
            // 获取站点列表信息
            put(ActionURI.GET_STATIONS.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/stations"));
            // 获取车辆信息
            put(ActionURI.GET_VEHICHES.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/vehicles"));
            // 获取站点区域信息
            put(ActionURI.GET_STATION_AREAS.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/areas"));
            // 获取单个站点提醒设置接口
            put(ActionURI.GET_SINGLE_REMIND_STATIONS.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/remindsingle"));
            // 获取站点提醒
            put(ActionURI.GET_REMIND_STATIONS.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/getremind"));
            // 设置提醒站点信息
            put(ActionURI.SET_REMIND_STATION.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/setremind"));
            // 获取已收藏站点
            put(ActionURI.GET_FAVORITES.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/getfaversites"));
            // 设置收藏站点
            put(ActionURI.SET_FAVORITES.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/setfaversites"));
            // 获取站点与车辆相对实时数据
            put(ActionURI.GET_STATION_REALTIME_DATA.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/position"));
            // 获取车辆实时信息
            put(ActionURI.GET_VEHICHE_REALTIME_DATA.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/realinfo"));
            // 获取车辆司机信息
            put(ActionURI.GET_VEHICHE_DRIVER_DATA.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/station/driverinfo"));

            // -----------信息日志收集----------------------
            // 异常信息收集上报
            put(ActionURI.REPORT_EXCEPTION_INFO.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/log/errinfo"));
            // 终端软硬件环境信息上报
            put(ActionURI.REPORT_MOBILE_ENVIRONMENT_INFO.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/log/envinfo"));
            // 用户行为统计上报
            put(ActionURI.REPORT_MOBILE_USAGE_INFO.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/log/usage"));

            // -----------其他接口----------------------
            // 手机短信验证
            put(ActionURI.SEND_PHONE_SMS.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/account/phonecodeget"));
            // 获取二维码
            put(ActionURI.GET_QRCODE.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/getqrcode"));
            // 资源上传
            put(ActionURI.UPLOAD_RESOURCES.toString(), new UriAction(API_PATH_URL, HttpAction.Post, "/resources/putresources"));
            // 资源下载
            put(ActionURI.DOWNLOAD_RESOURCES.toString(), new UriAction(API_PATH_URL, HttpAction.Get, "/resources/"));
            
            
        }
    };

    public static UriAction getUriAction(ActionURI uri)
    {
        UriAction uriAction = uriMap.get(uri.toString());

        return uriAction;
    }

//    public static String getURI(ActionURI uri)
//    {
//        UriAction uriAction = uriMap.get(uri.toString());
//
//        return uriAction.getUri();
//    }

}