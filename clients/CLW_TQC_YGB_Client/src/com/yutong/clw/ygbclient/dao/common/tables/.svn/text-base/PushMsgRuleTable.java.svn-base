package com.yutong.clw.ygbclient.dao.common.tables;

public class PushMsgRuleTable
{
    /** 表名 */
    public static final String Table_Name = "PushMsgRule";

    /** 配置ID */
    //public static final String Id = "Id";

    /** 用户Code */
    public static final String User_Code = "User_Code";

    /** 消息推送规则ID */
    public static final String Rule_Id = "Rule_Id";

    /** 消息推送规则标题 */
    //public static final String Rule_Title = "Rule_Title";

    /** 消息推送规则描述 */
    //public static final String Rule_Desc = "Rule_Desc";

    /** 是否推送：1– 开；0– 关 */
    public static final String On_Off = "On_Off";

    /** 是否提示：1– 提示；0– 不提示 */
    public static final String Flag = "Flag";

    /**
     * 创建表
     * 
     * @author zhangzhia 2013-9-6 下午6:25:50
     * @return
     */
    public static String getTableSQL()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(PushMsgRuleTable.Table_Name).append("(");

        //builder.append(PushMsgRuleTable.Id).append(" TEXT,");
        builder.append(PushMsgRuleTable.User_Code).append(" TEXT,");
        builder.append(PushMsgRuleTable.Rule_Id).append(" TEXT,");
        //builder.append(PushMsgRuleTable.Rule_Title).append(" TEXT,");
        //builder.append(PushMsgRuleTable.Rule_Desc).append(" TEXT,");
        builder.append(PushMsgRuleTable.On_Off).append(" INTEGER,");
        builder.append(PushMsgRuleTable.Flag).append(" INTEGER");

        builder.append(");");
        return builder.toString();
    }

}
