/*******************************************************************************
 * @(#)DatabaseHelper.java 2013-3-20 Copyright 2013 Neusoft Group Ltd. All
 *                         rights reserved. Neusoft PROPRIETARY/CONFIDENTIAL.
 *                         Use is subject to license terms.
 *******************************************************************************/
package com.yutong.axxc.parents.dao.common;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.SharedPreferencesUtil;
import com.yutong.axxc.parents.common.SysConfigUtil;
import com.yutong.axxc.parents.dao.common.tables.LoginInfoTable;
import com.yutong.axxc.parents.dao.common.tables.NewsInfoTable;
import com.yutong.axxc.parents.dao.common.tables.PunchCardDateRecordTable;
import com.yutong.axxc.parents.dao.common.tables.PushMsgRuleTable;
import com.yutong.axxc.parents.dao.common.tables.StudentMsgRecordTable;
import com.yutong.axxc.parents.view.common.YtApplication;

public class DatabaseHelper extends SQLiteOpenHelper
{
    /** 数据库名 */
    private static final String DB_NAME = "axxcparents.db";

    /** 数据库版本 */
    private static final int DB_VERSION = 17;

    /** Context对象 */
    private Context context;

    /**
     * <默认构造函数>
     */
    public DatabaseHelper(Context context, String name, CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    /**
     * <默认构造函数>
     */
    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        rebuildDB(db);
        clearSharedPreferences();
        clearResCache();
        clearLogs();
    }

    public static void clearLogs()
    {
        String logPath = Logger.getProperty("save path", "/sdcard/yutong/xcp/log/applog.log");
        new File(logPath).delete();
    }

    public static void clearResCache()
    {
        delAllFile(SysConfigUtil.getResCachePath());
    }

    public static void clearSharedPreferences()
    {
        //清除数据缓存
        SharedPreferencesUtil.clear(SharedPreferencesUtil.PREFS_NAME_CACHE);
        //清除ETag缓存
        SharedPreferencesUtil.clear(SharedPreferencesUtil.PREFS_NAME_ETAG);
        //清除设置缓存
        SharedPreferencesUtil.clear(SharedPreferencesUtil.PREFS_NAME_SETTING);
      //清除标志缓存
        SharedPreferencesUtil.clear(SharedPreferencesUtil.PREFS_NAME_FLAG);
    }

    private void rebuildDB(SQLiteDatabase db)
    {
        context.deleteDatabase(DB_NAME);
        Logger.i(getClass(), "重建数据库");

        db.execSQL(getCreateUserInfoTableSQL());
        db.execSQL(getCreatePushMsgRuleTableSQL());
        db.execSQL(getCreateStudentMsgRecordTableSQL());
        db.execSQL(getCreatePunchCardDateRecordTableSQL());
        db.execSQL(getCreateNewsInfoTableSQL());

    }

    private static void delAllFile(String filePath)
    {
        /**
         * 指定删除目录路径构造一个文件对象
         */
        File file = new File(filePath);

        File[] fileList = file.listFiles();
        /**
         * 初始化子目录路径
         */
        String dirPath = null;

        if (fileList != null)
        {
            for (int i = 0; i < fileList.length; i++)
            {
                /**
                 * 如果是文件就将其删除
                 */
                if (fileList[i].isFile())
                {
                    fileList[i].delete();
                }
                /**
                 * 如果是目录,那么将些目录下所有文件删除后再将其目录删除,
                 */
                if (fileList[i].isDirectory())
                {

                    dirPath = fileList[i].getPath();
                    // 递归删除指定目录下所有文件
                    delAllFile(dirPath);
                }
            }
        }
        /**
         * 删除给定根目录
         */
        file.delete();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(getCreateUserInfoTableSQL());
        db.execSQL(getCreatePushMsgRuleTableSQL());
        db.execSQL(getCreateStudentMsgRecordTableSQL());
        db.execSQL(getCreatePunchCardDateRecordTableSQL());
        db.execSQL(getCreateNewsInfoTableSQL());

    }

    /**
     * 创建登录用户表
     * 
     * @author zhangzhia 2013-9-6 下午6:25:34
     * @return
     */
    private String getCreateUserInfoTableSQL()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(LoginInfoTable.TABLE_NAME).append("(");

        builder.append(LoginInfoTable.ACCESS_TOKEN).append(" TEXT,");
        builder.append(LoginInfoTable.EXPIRES_IN).append(" TEXT,");
        builder.append(LoginInfoTable.REFRESH_TOKEN).append(" TEXT,");
        builder.append(LoginInfoTable.USR_ID).append(" TEXT,");
        builder.append(LoginInfoTable.USR_NO).append(" TEXT,");
        builder.append(LoginInfoTable.USR_LOGIN_NAME).append(" TEXT,");
        builder.append(LoginInfoTable.USR_NAME).append(" TEXT,");
        builder.append(LoginInfoTable.USR_SEX).append(" TEXT,");
        builder.append(LoginInfoTable.USR_MAIN).append(" TEXT,");
        builder.append(LoginInfoTable.USR_PHONE).append(" TEXT,");
        builder.append(LoginInfoTable.USR_PHOTO).append(" TEXT,");
        builder.append(LoginInfoTable.USR_ADDR).append(" TEXT,");
        builder.append(LoginInfoTable.USR_EMAIL).append(" TEXT,");
        builder.append(LoginInfoTable.P_USR_NO).append(" TEXT,");
        builder.append(LoginInfoTable.P_USR_LOGIN_NAME).append(" TEXT,");

        builder.append(LoginInfoTable.ETAG).append(" TEXT");

        builder.append(");");
        return builder.toString();
    }

    /**
     * 创建规则配置表
     * 
     * @author zhangzhia 2013-9-6 下午6:25:50
     * @return
     */
    private String getCreatePushMsgRuleTableSQL()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(PushMsgRuleTable.TABLE_NAME).append("(");

        builder.append(PushMsgRuleTable.ID).append(" TEXT,");
        builder.append(PushMsgRuleTable.USR_ID).append(" TEXT,");
        builder.append(PushMsgRuleTable.CLD_ID).append(" TEXT,");
        builder.append(PushMsgRuleTable.RULE_ID).append(" TEXT,");
        builder.append(PushMsgRuleTable.RULE_TITLE).append(" TEXT,");
        builder.append(PushMsgRuleTable.RULE_DESC).append(" TEXT,");
        builder.append(PushMsgRuleTable.ON_OFF).append(" TEXT,");
        builder.append(PushMsgRuleTable.FLAG).append(" TEXT");

        builder.append(");");
        return builder.toString();
    }

    /**
     * 创建学生历史消息表
     * 
     * @author zhangzhia 2013-9-6 下午6:26:03
     * @return
     */
    private String getCreateStudentMsgRecordTableSQL()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(StudentMsgRecordTable.TABLE_NAME).append("(");

        builder.append(StudentMsgRecordTable.MSG_ID).append(" TEXT,");
        builder.append(StudentMsgRecordTable.CLD_ID).append(" TEXT,");
        builder.append(StudentMsgRecordTable.RULE_ID).append(" TEXT,");
        builder.append(StudentMsgRecordTable.MSG_CONTENT).append(" TEXT,");
        builder.append(StudentMsgRecordTable.MSG_TIME).append(" TEXT,");
        builder.append(StudentMsgRecordTable.MSG_DATE).append(" TEXT");

        builder.append(");");
        return builder.toString();
    }

    private String getCreatePunchCardDateRecordTableSQL()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(PunchCardDateRecordTable.TABLE_NAME).append("(");

        builder.append(PunchCardDateRecordTable.CLD_ID).append(" TEXT,");
        builder.append(PunchCardDateRecordTable.PUNCHCARD_MONTH).append(" TEXT,");
        builder.append(PunchCardDateRecordTable.PUNCHCARD_DAY).append(" TEXT");

        builder.append(");");
        return builder.toString();
    }

    private String getCreateNewsInfoTableSQL()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(NewsInfoTable.TABLE_NAME).append("(");
        builder.append(NewsInfoTable.USER_ID).append(" TEXT,");
        builder.append(NewsInfoTable.NEWS_ID).append(" TEXT,");
        builder.append(NewsInfoTable.NEWS_TITLE).append(" TEXT,");
        builder.append(NewsInfoTable.NEWS_SUMMARY).append(" TEXT,");
        builder.append(NewsInfoTable.NEWS_AUTHOR).append(" TEXT,");
        builder.append(NewsInfoTable.NEWS_IMAGE).append(" TEXT,");
        builder.append(NewsInfoTable.NEWS_IMAGE_URL).append(" TEXT,");
        builder.append(NewsInfoTable.NEWS_CONTENT).append(" TEXT,");
        builder.append(NewsInfoTable.NEWS_TIME).append(" TEXT,");
        builder.append(NewsInfoTable.NEWS_TYPE).append(" TEXT,");
        builder.append(NewsInfoTable.ISREAD).append(" TEXT");

        builder.append(");");
        return builder.toString();
    }
}
