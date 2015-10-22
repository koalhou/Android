package com.yutong.clw.ygbclient.dao.common.tables;

import com.yutong.clw.ygbclient.common.enums.ResourceType;

/**
 * 资源路由表
 * 
 * @author zhangzhia 2013-10-14 下午1:45:06
 */
public class ResRouterTable
{

    /** 表名 */
    public static final String Table_Name = "ResRouter";

    /** 唯一key */
    public static final String Key = "Key";

    /** 路由类型 */
    public static final String  ResourceType = "ResourceType";

    /** 资源文件名 */
    public static final String Name = "Name";

    /** 资源大小 */
    public static final String Size = "Size";

    /** 资源路径 */
    public static final String Path = "Path";
    
    /** 后缀名，如png*/
    public static final String Suffix = "Suffix";
    
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
        builder.append(ResRouterTable.Table_Name).append("(");
        
        builder.append(ResRouterTable.Key).append(" TEXT,");
        builder.append(ResRouterTable.ResourceType).append(" INTEGER,");
        builder.append(ResRouterTable.Name).append(" TEXT,");
        builder.append(ResRouterTable.Size).append(" INTEGER,");
        builder.append(ResRouterTable.Suffix).append(" TEXT,");
        builder.append(ResRouterTable.Path).append(" TEXT");

        builder.append(");");
        return builder.toString();
    }
}
