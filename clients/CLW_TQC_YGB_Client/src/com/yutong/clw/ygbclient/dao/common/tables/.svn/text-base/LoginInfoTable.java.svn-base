package com.yutong.clw.ygbclient.dao.common.tables;


/**
 * 登陆用户信息表
 * 
 * @author zhangzhia 2013-9-4 下午7:13:01
 */
public final class LoginInfoTable
{
    /** 表名称 */
    public static final String Table_Name = "LoginInfo";

    /** 要获取的Access Token */
    public static final String AccessToken = "AccessToken";

    /** Access Token的有效期，单位：秒 */
    public static final String ExpiresIn = "ExpiresIn";

    /** 用于刷新Access Token的Refresh Token */
    public static final String RefreshToken = "RefreshToken";

    /** 员工Id */
    public static final String Id = "Id";

    /** 员工工号 */
    public static final String Code = "Code";
    
    /** 真实姓名 */
    public static final String Name = "Name";

    /** 员工姓名拼音缩写 */
    public static final String NameShort = "NameShort";
    
    /** 员工所在厂区  1:宇通工业园，2:：新能源工厂 */
    public static final String BelongArea = "BelongArea";

    /** 性别.0:男 1:女 -1:未设置 */
    public static final String Sex = "Sex";

    /** 手机号码 */
    public static final String Phone = "Phone";

    /** 头像图片名称【res_id】 */
    public static final String Photo = "Photo";

    /** 所在部门 */
    public static final String Department = "Department";
    
    /** eip绑定手机号 */
    public static final String EipPhone = "EipPhone";
    
    /** 手机是否绑定，0：否，1：是 */
    public static final String BindPhone = "BindPhone";
    
    /** 是否未修改默认密码，0：否，1：是 */
    public static final String DefaultPassword = "DefaultPassword";


    
    
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
        builder.append(LoginInfoTable.Table_Name).append("(");

        builder.append(LoginInfoTable.AccessToken).append(" TEXT,");
        builder.append(LoginInfoTable.ExpiresIn).append(" INTEGER,");
        builder.append(LoginInfoTable.RefreshToken).append(" TEXT,");
        
        builder.append(LoginInfoTable.Id).append(" TEXT,");
        builder.append(LoginInfoTable.Code).append(" TEXT,");
        builder.append(LoginInfoTable.Name).append(" TEXT,");
        
        builder.append(LoginInfoTable.NameShort).append(" TEXT,");
        builder.append(LoginInfoTable.BelongArea).append(" INTEGER,");
        builder.append(LoginInfoTable.Sex).append(" INTEGER,");
        builder.append(LoginInfoTable.Phone).append(" TEXT,");
        
        builder.append(LoginInfoTable.Photo).append(" TEXT,");
        builder.append(LoginInfoTable.Department).append(" TEXT,");
        builder.append(LoginInfoTable.EipPhone).append(" TEXT,");
        builder.append(LoginInfoTable.BindPhone).append(" TEXT,");
        builder.append(LoginInfoTable.DefaultPassword).append(" TEXT");

        builder.append(");");
        return builder.toString();
    }
}
