/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 下午3:53:27
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.common.tables;

/**
 * 推荐站点
 * 
 * @author zhangzhia 2013-10-25 下午3:53:27
 */
public class RecommendStationTable
{
    /** 表名称 */
    public static final String Table_Name = " RecommendStation";

    public static final String Area_Type = "Area_Type";
    
    public static final String Status_Range = "Status_Range";

    public static final String Id = "Id";

    public static final String Belong_Area_Id = "Belong_Area_Id";

    public static final String Name = "Name";

    public static final String Alias = "Alias";

    public static final String gps_lon = "gps_lon";

    public static final String gps_lat = "gps_lat";

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
        builder.append(RecommendStationTable.Table_Name).append("(");
        builder.append(RecommendStationTable.Area_Type).append(" INTEGER,");
        builder.append(RecommendStationTable.Status_Range).append(" INTEGER,");
     
        builder.append(RecommendStationTable.Id).append(" TEXT,");
        builder.append(RecommendStationTable.Belong_Area_Id).append(" TEXT,");
        builder.append(RecommendStationTable.Name).append(" TEXT,");
        builder.append(RecommendStationTable.Alias).append(" TEXT,");
        builder.append(RecommendStationTable.gps_lon).append(" TEXT,");
        builder.append(RecommendStationTable.gps_lat).append(" TEXT");
        builder.append(");");
        return builder.toString();
    }
}
