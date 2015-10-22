package com.yutong.axxc.parents.dao.common.tables;


/**
 * 新闻信息表
 * @author zhangzhia 2013-9-9 下午3:45:44
 *
 */
public class NewsInfoTable
{
    /** 新闻信息表名 */
    public static final String TABLE_NAME = "News_Info";

    /** 用户ID */
    public static final String USER_ID = "user_id";

    /** 消息ID */
    public static final String NEWS_ID = "news_id";

    /** 消息标题 */
    public static final String NEWS_TITLE = "news_title";

    /** 消息概述信息 */
    public static final String NEWS_SUMMARY = "news_summary";

    /** 消息发布者 */
    public static final String NEWS_AUTHOR = "news_author";

    /** 消息图片res_id */
    public static final String NEWS_IMAGE = "news_image";
    
    /** 消息图片url */
    public static final String NEWS_IMAGE_URL = "news_image_url";

    /** 消息内容信息 */
    public static final String NEWS_CONTENT = "news_content";

    /** 消息发布时间。格式：yyyymmddhh24miss */
    public static final String NEWS_TIME = "news_time";

    /** 消息类型0-行业政策1-宇通营销 */
    public static final String NEWS_TYPE = "news_type";

    /** 是否已读 */
    public static final String ISREAD = "is_read";

}
