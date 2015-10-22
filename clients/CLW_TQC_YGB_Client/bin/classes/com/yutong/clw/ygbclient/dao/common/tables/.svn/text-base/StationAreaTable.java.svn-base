/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 上午10:08:04
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.common.tables;

/**
 * 站点区域信息表
 * 
 * @author zhangzhia 2013-10-25 上午10:08:04
 */
public class StationAreaTable
{
    /** 表名 */
    public static final String Table_Name = "StationAreaTable";

    public static final String Id = "Id";

    public static final String Name = "Name";

    public static String getTableSQL()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(StationAreaTable.Table_Name).append("(");

        builder.append(StationAreaTable.Id).append(" TEXT,");
        builder.append(StationAreaTable.Name).append(" TEXT");
        builder.append(");");
        return builder.toString();
    }
}
