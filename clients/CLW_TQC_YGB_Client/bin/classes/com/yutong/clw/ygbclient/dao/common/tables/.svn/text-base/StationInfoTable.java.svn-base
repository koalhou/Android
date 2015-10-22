/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 上午10:08:04
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.common.tables;

/**
 * 站点信息表
 * 
 * @author zhangzhia 2013-10-25 上午10:08:04
 */
public class StationInfoTable
{
    /** 表名 */
    public static final String Table_Name = "StationInfo";

    public static final String Area_Type = "Area_Type";

    public static final String Status_Range = "Status_Range";

    // 暂不使用
    //public static final String Line_Id = "Line_Id";

    public static final String Id = "Id";

    public static final String Belong_Area_Id = "Belong_Area_Id";

    public static final String Name = "Name";

    public static final String Alias = "Alias";

    public static final String gps_lon = "gps_lon";

    public static final String gps_lat = "gps_lat";
   
    // 暂不使用
    //public static final String Type = "Type";

    public static final String Status = "Status";

    // 暂不使用
    //public static final String Plan_Arrive_Time = "Plan_Arrive_Time";

    // add by lizyi
    public static final String Favorites = "Favorites";

    public static final String User_Code = "User_Code";


    public static String getTableSQL()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(StationInfoTable.Table_Name).append("(");
        builder.append(StationInfoTable.Area_Type).append(" INTEGER,");
        builder.append(StationInfoTable.Status_Range).append(" INTEGER,");
        //builder.append(StationInfoTable.Line_Id).append(" TEXT,");
        builder.append(StationInfoTable.Id).append(" TEXT,");
        builder.append(StationInfoTable.Belong_Area_Id).append(" TEXT,");
        builder.append(StationInfoTable.Name).append(" TEXT,");
        builder.append(StationInfoTable.Alias).append(" TEXT,");
        builder.append(StationInfoTable.gps_lon).append(" TEXT,");
        builder.append(StationInfoTable.gps_lat).append(" TEXT,");
        //builder.append(StationInfoTable.Type).append(" INTEGER,");
        builder.append(StationInfoTable.Status).append(" INTEGER,");
        //builder.append(StationInfoTable.Plan_Arrive_Time).append(" TEXT,");
        
        builder.append(StationInfoTable.Favorites).append(" TEXT,");
        builder.append(StationInfoTable.User_Code).append(" TEXT");
        //builder.append(StationInfoTable.Search_Time).append(" TEXT");
        builder.append(");");
        return builder.toString();
    }
}
