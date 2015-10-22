package com.yutong.axxc.parents.dao.student;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.dao.beans.DaoPunchCardDateBean;
import com.yutong.axxc.parents.dao.beans.DaoPunchCardDateBean;
import com.yutong.axxc.parents.dao.common.DatabaseHelper;
import com.yutong.axxc.parents.dao.common.tables.PunchCardDateRecordTable;
import com.yutong.axxc.parents.dao.common.tables.PunchCardDateRecordTable;

/**
 * 学生打卡日期数据操作类
 * 
 * @author zhangzhia 2013-9-2 下午3:04:15
 */
public class PunchCardDateRecordDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    private String logTypeName = "[学生打卡日期数据操作类]:";

    /**
     * <默认构造函数>
     */
    public PunchCardDateRecordDao(Context context)
    {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(this.context);
    }

    public boolean addRecord(DaoPunchCardDateBean infoBean)
    {
        Logger.i(this.getClass(), logTypeName + "添加信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            if (infoBean != null)
            {
                ContentValues values = new ContentValues();

                values.put(PunchCardDateRecordTable.CLD_ID, infoBean.getCld_id());
                values.put(PunchCardDateRecordTable.PUNCHCARD_MONTH, infoBean.getPunchcard_month());
                values.put(PunchCardDateRecordTable.PUNCHCARD_DAY, infoBean.getPunchCard_day());

                db.insert(PunchCardDateRecordTable.TABLE_NAME, null, values);
            }
            return true;
        }
        catch (Exception e)
        {
            Logger.e(this.getClass(), logTypeName + "添加信息异常：", e);
            return false;
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }
    }

    public boolean delRecord(String cld_id, String punchcard_month)
    {
        Logger.i(this.getClass(), logTypeName + "根据学生ID，日期删除信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            String whereClause = new StringBuilder(PunchCardDateRecordTable.CLD_ID).append("=?")
                     .append(" and ")
                    .append(PunchCardDateRecordTable.PUNCHCARD_MONTH).append("=?").toString();
            String[] whereArgs = { cld_id, punchcard_month };
            // String sortOrder = new
            // StringBuilder(PunchCardDateRecordTable.CLD_ID).append(" DESC").toString();

            db.delete(PunchCardDateRecordTable.TABLE_NAME, whereClause, whereArgs);

            return true;
        }
        catch (Exception e)
        {
            Logger.e(this.getClass(), logTypeName + "根据学生ID，消息日期删除信息异常：", e);
            return false;
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }
    }

    public DaoPunchCardDateBean getRecord(String cld_id, String punchcard_month)
    {
        Logger.i(this.getClass(), logTypeName + "查询信息");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();

            String whereClause = new StringBuilder(PunchCardDateRecordTable.CLD_ID).append("=?")
                    .append(" and ")
                    .append(PunchCardDateRecordTable.PUNCHCARD_MONTH).append("=?").toString();
            String[] whereArgs = { cld_id, punchcard_month };
            String sortOrder = null;

            String[] columns = { PunchCardDateRecordTable.CLD_ID, PunchCardDateRecordTable.PUNCHCARD_MONTH,
                    PunchCardDateRecordTable.PUNCHCARD_DAY };

            cursor = db.query(PunchCardDateRecordTable.TABLE_NAME, columns, whereClause, whereArgs, null, null, sortOrder);

            DaoPunchCardDateBean dal = null;
            if (cursor != null && cursor.moveToFirst())
            {
                dal = new DaoPunchCardDateBean();
                dal.setCld_id(cursor.getString(cursor.getColumnIndex(PunchCardDateRecordTable.CLD_ID)));
                dal.setPunchcard_month(cursor.getString(cursor.getColumnIndex(PunchCardDateRecordTable.PUNCHCARD_MONTH)));
                dal.setPunchCard_day(cursor.getString(cursor.getColumnIndex(PunchCardDateRecordTable.PUNCHCARD_DAY)));

            }
            return dal;
        }
        catch (Exception e)
        {
            Logger.e(this.getClass(), logTypeName + "根据cld_id 和 日期查询信息异常：", e);
            return null;
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
     * 删除学生打卡记录
     * 
     * @author zhangzhia 2013-9-8 下午3:20:30
     * @param cld_id
     * @return
     */
    public boolean clean(String cld_id)
    {
        Logger.i(this.getClass(), logTypeName + "根据学生ID删除信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            String whereClause = new StringBuilder(PunchCardDateRecordTable.CLD_ID).append("=?").toString();
            String[] whereArgs = { cld_id };

            db.delete(PunchCardDateRecordTable.TABLE_NAME, whereClause, whereArgs);

            return true;
        }
        catch (Exception e)
        {
            Logger.e(this.getClass(), logTypeName + "根据学生ID删除信息异常：", e);
            return false;
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }
    }

    /**
     * 删除所有记录
     * 
     * @author zhangzhia 2013-9-8 下午3:20:30
     * @param cld_id
     * @return
     */
    public boolean clean()
    {
        Logger.i(this.getClass(), logTypeName + "删除所有信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            db.delete(PunchCardDateRecordTable.TABLE_NAME, null, null);

            return true;
        }
        catch (Exception e)
        {
            Logger.e(this.getClass(), logTypeName + "删除所有信息异常：", e);
            return false;
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
