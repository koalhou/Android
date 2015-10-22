/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 下午3:53:27
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.common.tables;

/**
 * 收藏站点
 * 
 * @author zhangzhia 2013-10-25 下午3:53:27
 */
public class FavoritesTable
{
    /** 表名称 */
    public static final String Table_Name = "Favorites";

    public static final String Id = "Id";

    public static final String Belong_Area_Id = "Belong_Area_Id";

    public static final String Name = "Name";

    public static final String Alias = "Alias";

    public static final String gps_lon = "gps_lon";

    public static final String gps_lat = "gps_lat";

    // public static final String Type = "Type";

    public static final String Status = "Status";

    // public static final String Plan_Arrive_Time = "Plan_Arrive_Time";

    public static final String Favorites = "Favorites";

    public static final String Area_Type = "Area_Type";

    // 暂不使用
    // public static final String Line_Range = "Line_Range";

    public static final String Status_Range = "Status_Range";

    // add by lizyi
    public static final String User_Code = "User_Code";

    /**
     * 创建表
     * 
     * @author zhangzhia 2013-9-6 下午6:25:34
     * @return
     */
    public static String getTableSQL()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(FavoritesTable.Table_Name).append("(");
        builder.append(FavoritesTable.Id).append(" TEXT,");
        builder.append(FavoritesTable.Belong_Area_Id).append(" TEXT,");
        builder.append(FavoritesTable.Name).append(" TEXT,");
        builder.append(FavoritesTable.gps_lon).append(" TEXT,");
        builder.append(FavoritesTable.gps_lat).append(" TEXT,");
        builder.append(FavoritesTable.Favorites).append(" INTEGER,");
        builder.append(FavoritesTable.Status).append(" INTEGER,");
        builder.append(FavoritesTable.Area_Type).append(" INTEGER,");
        builder.append(FavoritesTable.Status_Range).append(" INTEGER,");
        builder.append(FavoritesTable.Alias).append(" TEXT,");
        builder.append(FavoritesTable.User_Code).append(" TEXT");
        builder.append(");");
        return builder.toString();
    }
}
