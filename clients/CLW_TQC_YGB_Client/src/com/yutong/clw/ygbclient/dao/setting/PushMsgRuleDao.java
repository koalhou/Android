/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 下午3:07:28
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.setting;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.PushMsgRule;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.dao.common.DatabaseHelper;
import com.yutong.clw.ygbclient.dao.common.tables.NewsInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.PushMsgRuleTable;

/**
 * @author zhangzhia 2013-10-25 下午3:07:28
 */
public class PushMsgRuleDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    /**
     * <默认构造函数>
     */
    public PushMsgRuleDao(Context context)
    {
        this.context = context;
        databaseHelper = new DatabaseHelper(this.context);
    }

    /**
     * 批量更改信息推送设置 (会首先清空掉以前的数据) 能有减少SQLLite频繁的开关事物，一定范围的提升效率
     *@author zhangzhia 2013-10-31 上午8:47:47
     *
     * @param rules 推送规则集合
     * @param user_code   员工号emp_code
     * @return
     */
    public boolean batchUpdateAllPushMsgRule(List<PushMsgRule> rules, String user_code)
    {
        Logger.i(getClass(), "[信息推送设置表操作类]：批量修改信息推送设置，数量：", rules.size());
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            // 开启事务
            db.beginTransaction();
            db.delete(PushMsgRuleTable.Table_Name, new StringBuilder(PushMsgRuleTable.User_Code).append("='").append(user_code).append("'")
                    .toString(), null);
            for (PushMsgRule rule : rules)
            {
                ContentValues values = buildContentValues(rule, user_code);
                db.insert(PushMsgRuleTable.Table_Name, null, values);
            }
            db.setTransactionSuccessful();
            return true;
        }
        catch (Exception e)
        {
            Logger.e(getClass(), "[信息推送设置表操作类]：批量修改信息推送设置异常：", e);
            return false;
        }
        finally
        {
            if (db != null)
            {
                if (db.inTransaction())
                {
                    db.endTransaction();
                }
                db.close();
            }
        }
    }

    private ContentValues buildContentValues(PushMsgRule rule, String user_code)
    {
        ContentValues values = new ContentValues();
        //values.put(PushMsgRuleTable.Id, rule.id);
        values.put(PushMsgRuleTable.User_Code, rule.usr_code);
        values.put(PushMsgRuleTable.Rule_Id, rule.rule_id);
        //values.put(PushMsgRuleTable.Rule_Title, rule.rule_title);
        //values.put(PushMsgRuleTable.Rule_Desc, rule.rule_desc != null ? rule.rule_desc : "");
        values.put(PushMsgRuleTable.On_Off, rule.on_off);
        values.put(PushMsgRuleTable.Flag, rule.flag);

        return values;
    }

    /**
     * 获取用户推送规则
     *@author zhangzhia 2013-10-31 上午8:47:08
     *
     * @param user_code   员工号emp_code
     * @return
     */
    public List<PushMsgRule> getPushMsgRules(String user_code)
    {
        Logger.i(PushMsgRuleDao.class, "[信息推送设置表操作类]：获取信息推送设置");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();

            String selection = new StringBuilder(PushMsgRuleTable.User_Code).append("='").append(user_code).append("'").toString();
            cursor = db.query(PushMsgRuleTable.Table_Name, null, selection, null, null, null, null);

            List<PushMsgRule> PushMsgRules = new ArrayList<PushMsgRule>();
            while (cursor != null && cursor.moveToNext())
            {
                PushMsgRules.add(buildrule(cursor));
            }
            return PushMsgRules;
        }
        catch (Exception e)
        {
            Logger.e(PushMsgRuleDao.class, "[信息推送设置表操作类]：获取信息推送设置异常：", e);
            return new ArrayList<PushMsgRule>();
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }

    /**
     * @param cursor
     * @return
     */
    private PushMsgRule buildrule(Cursor cursor)
    {
        PushMsgRule rule = new PushMsgRule();

        //rule.id = cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.Id));
        rule.usr_code = cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.User_Code));
        rule.rule_id = cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.Rule_Id));
        //rule.rule_title = cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.Rule_Title));
        //rule.rule_desc = cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.Rule_Desc));
        rule.flag = DataTypeConvertUtils.int2Boolean(cursor.getInt(cursor.getColumnIndex(PushMsgRuleTable.Flag)));
        rule.on_off = DataTypeConvertUtils.int2Boolean(cursor.getInt(cursor.getColumnIndex(PushMsgRuleTable.On_Off)));

        return rule;
    }
    
    /**
     * 清除当前用户所有推送信息
     * 
     * @author zhangzhia 2013-11-26 上午9:35:04
     */
    public void removeAll(String user_code)
    {
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            Logger.i(this.getClass(), "[信息推送设置表操作类]：清除当前用户所有推送信息");
            String delselection = PushMsgRuleTable.User_Code + "=?";
            String[] delselectionArgs = { user_code };
            db.delete(PushMsgRuleTable.Table_Name, delselection, delselectionArgs);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), "[信息推送设置表操作类]：清除当前用户所有推送信息：", e);
        }
        finally
        {

            if (db != null)
            {
                db.close();
            }
        }
    }
}
