package com.yutong.axxc.parents.dao.student;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.dao.beans.DaoLoginInfoBean;
import com.yutong.axxc.parents.dao.beans.DaoStudentMsgRecordBean;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.DatabaseHelper;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.dao.common.tables.LoginInfoTable;
import com.yutong.axxc.parents.dao.common.tables.StudentMsgRecordTable;

/**
 * 学生历史消息类数据操作类
 * 
 * @author zhangzhia 2013-9-2 下午3:04:15
 */
public class StudentMsgRecordDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    private String logTypeName = "[学生历史消息类数据操作类]:";

    /**
     * <默认构造函数>
     */
    public StudentMsgRecordDao(Context context)
    {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(this.context);
    }

    public boolean addStudentMsgRecord(DaoStudentMsgRecordBean infoBean)
    {
        Logger.i(this.getClass(), logTypeName + "添加信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            if (infoBean != null)
            {
                ContentValues values = new ContentValues();

                values.put(StudentMsgRecordTable.MSG_ID, infoBean.getMsg_id());
                values.put(StudentMsgRecordTable.CLD_ID, infoBean.getCld_id());
                values.put(StudentMsgRecordTable.RULE_ID, infoBean.getRule_id());
                values.put(StudentMsgRecordTable.MSG_CONTENT, infoBean.getMsg_content());
                values.put(StudentMsgRecordTable.MSG_TIME, infoBean.getMsg_time());

                values.put(StudentMsgRecordTable.MSG_DATE, infoBean.getMsg_time().substring(0, 8));

                db.insert(StudentMsgRecordTable.TABLE_NAME, null, values);
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

    /**
     * 根据学生ID，消息日期删除信息
     * 
     * @author zhangzhia 2013-9-7 上午10:36:55
     * @param cld_id 学生ID
     * @param msg_date 消息日期 格式：yyyyMMdd
     * @return
     */
    public boolean delStudentMsgRecord(String cld_id, String msg_date)
    {
        Logger.i(this.getClass(), logTypeName + "根据学生ID，消息日期删除信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            String whereClause = new StringBuilder(StudentMsgRecordTable.CLD_ID).append("=?")
                    .append(" and ")
                    .append(StudentMsgRecordTable.MSG_DATE)
                    .append("=?").toString();
            String[] whereArgs = { cld_id, msg_date };
            // String sortOrder = new
            // StringBuilder(StudentMsgRecordTable.CLD_ID).append(" DESC").toString();

            db.delete(StudentMsgRecordTable.TABLE_NAME, whereClause, whereArgs);

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

    /**
     * 根据学生id，消息日期查询消息记录
     * 
     * @author zhangzhia 2013-9-8 下午3:16:49
     * @param cld_id
     * @param msg_date
     * @return
     */
    public List<DaoStudentMsgRecordBean> getStudentMsgRecord(String cld_id, String msg_date)
    {
        Logger.i(this.getClass(), logTypeName + "查询" + cld_id + " 信息, 查询日期 + " + msg_date);
        List<DaoStudentMsgRecordBean> daoStudentMsgRecordBeans = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();

            String whereClause = new StringBuilder(StudentMsgRecordTable.CLD_ID).append("=?")
                    .append(" and ")
                    .append(StudentMsgRecordTable.MSG_DATE)
                    .append("=?").toString();
            String[] whereArgs = { cld_id, msg_date };
            String sortOrder = new StringBuilder(StudentMsgRecordTable.MSG_TIME).append(" ASC").toString();

            String[] columns = { StudentMsgRecordTable.CLD_ID, StudentMsgRecordTable.RULE_ID, StudentMsgRecordTable.MSG_CONTENT,
                    StudentMsgRecordTable.MSG_TIME, StudentMsgRecordTable.MSG_DATE };

            cursor = db.query(StudentMsgRecordTable.TABLE_NAME, columns, whereClause, whereArgs, null, null, sortOrder);

            if (cursor != null && cursor.moveToFirst())
            {

                daoStudentMsgRecordBeans = new ArrayList<DaoStudentMsgRecordBean>();

                while (cursor.moveToNext())
                {

                    DaoStudentMsgRecordBean dal = new DaoStudentMsgRecordBean();
                    dal.setMsg_id(cursor.getString(cursor.getColumnIndex(StudentMsgRecordTable.MSG_ID)));
                    dal.setCld_id(cursor.getString(cursor.getColumnIndex(StudentMsgRecordTable.CLD_ID)));
                    dal.setRule_id(cursor.getString(cursor.getColumnIndex(StudentMsgRecordTable.RULE_ID)));
                    dal.setMsg_content(cursor.getString(cursor.getColumnIndex(StudentMsgRecordTable.MSG_CONTENT)));
                    dal.setMsg_time(cursor.getString(cursor.getColumnIndex(StudentMsgRecordTable.MSG_TIME)));

                    daoStudentMsgRecordBeans.add(dal);
                }
            }
            return daoStudentMsgRecordBeans;
        }
        catch (Exception e)
        {
            Logger.e(this.getClass(), logTypeName + "根据cld_id 和 日期查询学生消息信息异常：", e);
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
     * 删除学生消息记录
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

            String whereClause = new StringBuilder(StudentMsgRecordTable.CLD_ID).append("=?").toString();
            String[] whereArgs = { cld_id };

            db.delete(StudentMsgRecordTable.TABLE_NAME, whereClause, whereArgs);

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

            db.delete(StudentMsgRecordTable.TABLE_NAME, null, null);

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
