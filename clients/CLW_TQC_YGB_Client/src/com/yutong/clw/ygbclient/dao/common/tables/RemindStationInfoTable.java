/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 上午10:43:12
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.common.tables;

/**
 * 提醒站点信息表
 * @author zhangzhia 2013-10-25 上午10:43:12
 */
public class RemindStationInfoTable
{

    /** 表名称 */
    public static final String Table_Name = "RemindStationInfo";

    public static final String User_Code = "User_Code";
    
    public static final String Id = "Id";

    public static final String Area_Type = "Area_Type";

    public static final String Area_Name = "Area_Name";

    public static final String Line_Range = "Line_Range";
    
    public static final String Status_Range = "Status_Range";
    

    public static final String Station_Id = "Station_Id";

    public static final String Station_Name = "Station_Name";
    
    public static final String Line_Id = "Line_Id";

    public static final String Line_Name = "Line_Name";
    
    public static final String Vehiche_Vin = "Vehiche_Vin";
    
    public static final String Vehiche_Number = "Vehiche_Number";

    public static final String Remind_Range = "Remind_Range";

    public static final String Remind_Type = "Remind_Type";

    public static final String Remind_Value = "Remind_Value";

    public static final String Remind_Week = "Remind_Week";

    public static final String Remind_Status = "Remind_Status";

    public static final String Modify_Time =  "Modify_Time";
    
    public static final String No_Remind_Date = "No_Remind_Date";

    public static String getTableSQL()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(RemindStationInfoTable.Table_Name).append("(");
        
        
        builder.append(RemindStationInfoTable.User_Code).append(" TEXT,");
        builder.append(RemindStationInfoTable.Id).append(" TEXT,");
        builder.append(RemindStationInfoTable.Area_Type).append(" INTEGER,");
        builder.append(RemindStationInfoTable.Area_Name).append(" INTEGER,");
        builder.append(RemindStationInfoTable.Line_Range).append(" INTEGER,");
        builder.append(RemindStationInfoTable.Status_Range).append(" INTEGER,");
        builder.append(RemindStationInfoTable.Station_Id).append(" TEXT,");
        builder.append(RemindStationInfoTable.Station_Name).append(" TEXT,");
        builder.append(RemindStationInfoTable.Line_Id).append(" TEXT,");
        builder.append(RemindStationInfoTable.Line_Name).append(" TEXT,");

        builder.append(RemindStationInfoTable.Vehiche_Vin).append(" TEXT,");
        builder.append(RemindStationInfoTable.Vehiche_Number).append(" TEXT,");
        builder.append(RemindStationInfoTable.Remind_Range).append(" INTEGER,");
        builder.append(RemindStationInfoTable.Remind_Type).append(" INTEGER,");
        builder.append(RemindStationInfoTable.Remind_Value).append(" TEXT,");
        builder.append(RemindStationInfoTable.Remind_Week).append(" TEXT,");
        builder.append(RemindStationInfoTable.Remind_Status).append(" INTEGER,");
        
        builder.append(RemindStationInfoTable.Modify_Time).append(" LONG,");
        builder.append(RemindStationInfoTable.No_Remind_Date).append(" TEXT");
        builder.append(");");
        return builder.toString();
    }

}
