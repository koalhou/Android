package com.yutong.clw.ygbclient.dao.common.tables;

/**
 * 新闻信息表
 * 
 * @author zhangzhia 2013-9-9 下午3:45:44
 */
public class NewsInfoTable
{
    /** 新闻信息表名 */
    public static final String Table_Name = "NewsInfo";

    /** 用户ID */
    public static final String User_Code = "User_Code";

    /** 消息ID */
    public static final String NewsId = "NewsId";

    /** 消息标题 */
    public static final String NewsTitle = "NewsTitle";

    /** 消息概述信息 */
    public static final String NewsSummary = "NewsSummary";

    /** 消息发布者 */
    public static final String NewsAuthor = "NewsAuthor";

    /** 消息图片res_id */
    public static final String NewsImageResId = "NewsImageResId";

    /** 消息图片url */
    public static final String NewsImageUrl = "NewsImageUrl";

    /** 消息内容信息 */
    public static final String NewsContent = "NewsContent";

    /** 消息发布时间。格式：yyyymmddhh24miss */
    public static final String NewsTime = "NewsTime";

    /** 消息类型0-行业政策1-宇通营销 */
    public static final String NewsType = "NewsType";

    /** 是否已读 */
    public static final String isRead = "isRead";

    public static String getTableSQL()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(NewsInfoTable.Table_Name).append("(");
        builder.append(NewsInfoTable.User_Code).append(" TEXT,");
        builder.append(NewsInfoTable.NewsId).append(" TEXT,");
        builder.append(NewsInfoTable.NewsTitle).append(" TEXT,");
        builder.append(NewsInfoTable.NewsSummary).append(" TEXT,");
        builder.append(NewsInfoTable.NewsAuthor).append(" TEXT,");
        builder.append(NewsInfoTable.NewsImageResId).append(" TEXT,");
        builder.append(NewsInfoTable.NewsImageUrl).append(" TEXT,");
        builder.append(NewsInfoTable.NewsContent).append(" TEXT,");
        builder.append(NewsInfoTable.NewsTime).append(" TEXT,");
        builder.append(NewsInfoTable.NewsType).append(" INTEGER,");
        builder.append(NewsInfoTable.isRead).append(" INTEGER");

        builder.append(");");
        return builder.toString();
    }
}
