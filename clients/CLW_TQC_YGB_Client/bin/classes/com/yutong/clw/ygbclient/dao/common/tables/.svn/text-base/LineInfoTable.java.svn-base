/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 上午10:16:18
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.common.tables;

/**
 * 线路信息表
 * 
 * @author zhangzhia 2013-10-25 上午10:16:18
 */
public class LineInfoTable
{
    /** 表名称 */
    public static final String User_Code = "User_Code";

    public static final String Table_Name = "LineInfo";

    public static final String Create_Time = "Create_Time";

    public static final String Line_Id = "Line_Id";

    public static final String Line_Name = "Line_Name";

    public static final String Area_Type = "Area_Type";

    public static final String Line_Range = "Line_Range";

    public static final String Status_Range = "Status_Range";

    public static final String Cache_key = "Cache_key";

    public static final String Json_Data = "Json_Data";

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
        builder.append(LineInfoTable.Table_Name).append("(");
        builder.append(LineInfoTable.User_Code).append(" TEXT,");
        builder.append(LineInfoTable.Create_Time).append(" TEXT,");
        builder.append(LineInfoTable.Line_Id).append(" TEXT,");
        builder.append(LineInfoTable.Line_Name).append(" TEXT,");
        builder.append(LineInfoTable.Area_Type).append(" INTEGER,");

        builder.append(LineInfoTable.Line_Range).append(" INTEGER,");
        builder.append(LineInfoTable.Status_Range).append(" INTEGER,");

        builder.append(LineInfoTable.Cache_key).append(" TEXT,");
        builder.append(LineInfoTable.Json_Data).append(" TEXT");

        builder.append(");");
        return builder.toString();
    }
}
