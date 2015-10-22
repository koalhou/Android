package com.yutong.axxc.parents.dao.common.tables;

/**
 * 登陆用户信息表
 * 
 * @author zhangzhia 2013-9-4 下午7:13:01
 */
public final class LoginInfoTable
{
    /** 表名称 */
    public static final String TABLE_NAME = "usr_info_table";

    /** 要获取的Access Token */
    public static final String ACCESS_TOKEN = "accessToken";

    /** Access Token的有效期，单位：秒 */
    public static final String EXPIRES_IN = "expiresIn";

    /** 用于刷新Access Token的Refresh Token */
    public static final String REFRESH_TOKEN = "refreshToken";

    /** 用户ID */
    public static final String USR_ID = "usr_id";

    /** 安芯号 */
    public static final String USR_NO = "usr_no";

    /** 用户真实姓名 */
    public static final String USR_LOGIN_NAME = "usr_login_name";

    /** 用户真实姓名 */
    public static final String USR_NAME = "usr_name";

    /** 性别.0:男 1:女 -1:未设置 */
    public static final String USR_SEX = "usr_sex";

    /** 是否为主账号.0：否，1：是 */
    public static final String USR_MAIN = "usr_main";

    /** 手机号码 */
    public static final String USR_PHONE = "usr_phone";

    /** 头像图片名称【res_id】 */
    public static final String USR_PHOTO = "usr_photo";

    /** 家庭住址 */
    public static final String USR_ADDR = "usr_addr";

    /** 邮箱 */
    public static final String USR_EMAIL = "usr_email";

    /** 授权人安芯号 */
    public static final String P_USR_NO = "p_usr_no";

    /** 授权人登陆用户名 */
    public static final String P_USR_LOGIN_NAME = "p_usr_login_name";

    /** 用户信息ETag值 */
    public static final String ETAG = "eTag";

}
