package com.yutong.axxc.parents.dao.settings;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.dao.beans.DaoPushMsgRuleBean;
import com.yutong.axxc.parents.dao.common.DatabaseHelper;
import com.yutong.axxc.parents.dao.common.tables.PushMsgRuleTable;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 信息推送设置数据库操作类
 * @author zhangzhia 2013-9-3 下午6:19:24
 *
 */
public class PushMsgRuleDao {
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    /**
     * <默认构造函数>
     */
    public PushMsgRuleDao(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(this.context);
    }

    /**
     * 批量更改信息推送设置 (会首先清空掉以前的数据) 能有减少SQLLite频繁的开关事物，一定范围的提升效率
     * @return 是否更改成功
     */
    public boolean batchUpdateAllPushMsgRule(List<DaoPushMsgRuleBean> daoPmrs) {
        Logger.i(getClass(), "[信息推送设置表操作类]：批量修改信息推送设置，数量：", daoPmrs.size());
        SQLiteDatabase db = null;
        try {
            db = databaseHelper.getWritableDatabase();
            // 开启事务
            db.beginTransaction();
            db.delete(PushMsgRuleTable.TABLE_NAME,
                    new StringBuilder(PushMsgRuleTable.ID).append("='").append(YtApplication.getInstance().getUid())
                            .append("'").toString(), null);
            for (DaoPushMsgRuleBean daoPmr : daoPmrs) {
                ContentValues values = buildContentValues(daoPmr);
                db.insert(PushMsgRuleTable.TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Logger.e(getClass(), "[信息推送设置表操作类]：批量修改信息推送设置异常：", e);
            return false;
        } finally {
            if (db != null) {
                if (db.inTransaction()) {
                    db.endTransaction();
                }
                db.close();
            }
        }
    }

    /**
     * @param daoPush
     * @return
     */
    private ContentValues buildContentValues(DaoPushMsgRuleBean daoPush) {
        ContentValues values = new ContentValues();
        values.put(PushMsgRuleTable.ID, daoPush.getId());
        values.put(PushMsgRuleTable.USR_ID, daoPush.getUsr_id());
        values.put(PushMsgRuleTable.CLD_ID, daoPush.getCld_id());
        values.put(PushMsgRuleTable.RULE_ID, daoPush.getRule_id());
        values.put(PushMsgRuleTable.RULE_TITLE, daoPush.getRule_title());
        values.put(PushMsgRuleTable.RULE_DESC, daoPush.getRule_desc() != null ? daoPush.getRule_desc() : "");
        values.put(PushMsgRuleTable.ON_OFF, daoPush.getOn_off());
        values.put(PushMsgRuleTable.FLAG, daoPush.getFlag());
        
        return values;
    }

    public List<DaoPushMsgRuleBean> getPushMsgRules(String uid) {
        Logger.i(PushMsgRuleDao.class, "[信息推送设置表操作类]：获取信息推送设置");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = databaseHelper.getReadableDatabase();

            String selection = new StringBuilder(PushMsgRuleTable.USR_ID).append("='").append(uid).append("'").toString();
            cursor = db.query(PushMsgRuleTable.TABLE_NAME, null, selection, null, null, null, null);

            List<DaoPushMsgRuleBean> daoPushMsgRuleBeans = new ArrayList<DaoPushMsgRuleBean>();
            while (cursor != null && cursor.moveToNext()) {
                daoPushMsgRuleBeans.add(buildDaoPush(cursor));
            }
            return daoPushMsgRuleBeans;
        } catch (Exception e) {
            Logger.e(PushMsgRuleDao.class, "[信息推送设置表操作类]：获取信息推送设置异常：", e);
            return new ArrayList<DaoPushMsgRuleBean>();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }
    
    public List<DaoPushMsgRuleBean> getChildPushMsgRules(String cid) {
        Logger.i(PushMsgRuleDao.class, "[信息推送设置表操作类]：获取信息推送设置");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = databaseHelper.getReadableDatabase();

            String selection = new StringBuilder(PushMsgRuleTable.CLD_ID).append("='").append(cid).append("'").toString();
            cursor = db.query(PushMsgRuleTable.TABLE_NAME, null, selection, null, null, null, null);

            List<DaoPushMsgRuleBean> daoPushMsgRuleBeans = new ArrayList<DaoPushMsgRuleBean>();
            while (cursor != null && cursor.moveToNext()) {
                daoPushMsgRuleBeans.add(buildDaoPush(cursor));
            }
            return daoPushMsgRuleBeans;
        } catch (Exception e) {
            Logger.e(PushMsgRuleDao.class, "[信息推送设置表操作类]：获取信息推送设置异常：", e);
            return new ArrayList<DaoPushMsgRuleBean>();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * @param cursor
     * @return
     */
    private DaoPushMsgRuleBean buildDaoPush(Cursor cursor) {
        DaoPushMsgRuleBean daoPush = new DaoPushMsgRuleBean();

        daoPush.setId(cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.ID)));
        daoPush.setUsr_id(cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.USR_ID)));
        daoPush.setCld_id(cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.CLD_ID)));
        daoPush.setRule_id(cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.RULE_ID)));
        daoPush.setRule_title(cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.RULE_TITLE)));      
        daoPush.setRule_desc(cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.RULE_DESC)));       
        daoPush.setFlag(cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.FLAG)));
        daoPush.setOn_off(cursor.getString(cursor.getColumnIndex(PushMsgRuleTable.ON_OFF)));
        
        return daoPush;
    }
}
