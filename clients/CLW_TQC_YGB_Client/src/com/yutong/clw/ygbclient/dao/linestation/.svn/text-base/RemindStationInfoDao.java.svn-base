/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 下午3:51:41
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.linestation;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.enums.remind.RemindType;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.dao.common.DatabaseHelper;
import com.yutong.clw.ygbclient.dao.common.tables.FavoritesTable;
import com.yutong.clw.ygbclient.dao.common.tables.RemindStationInfoTable;

/**
 * 获取提醒信息信息数据操作类
 * 
 * @author zhangzhia 2013-10-25 下午3:51:41
 */
public class RemindStationInfoDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    private String logName = "[获取提醒信息信息数据操作类]:";

    /**
     * <默认构造函数>
     */
    public RemindStationInfoDao(Context context)
    {
        this.context = context;
        databaseHelper = new DatabaseHelper(this.context);
    }

    /**
     * 获取提醒信息
     * 
     * @author zhangzhia 2013-10-25 下午4:44:36
     * @param user_code
     * @return
     */
    public List<RemindInfo> getRemindInfos(AreaType area_type, String user_code)
    {
        Logger.i(RemindStationInfoDao.class, logName + "获取提醒信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            Cursor cursor = null;
            int count = 0;
            List<RemindInfo> infos = new ArrayList<RemindInfo>();
            RemindInfo bean = null;
            // 查询是否存在已有记录
            String selection = RemindStationInfoTable.Area_Type + "=? AND " + RemindStationInfoTable.User_Code + "=?";
            String[] selectionArgs = { area_type.value() + "", user_code };
            cursor = db.query(RemindStationInfoTable.Table_Name, null, selection, selectionArgs, null, null,
                    RemindStationInfoTable.Modify_Time + " DESC");
            while (cursor != null && cursor.moveToNext())
            {
                bean = buildRemindStationInfo(cursor);
                infos.add(bean);
                count++;
            }
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            Logger.i(RemindStationInfoDao.class, logName + "获取提醒信息数据信息条数：" + count);
            return infos;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(RemindStationInfoDao.class, logName + "获取提醒信息数据信息异常：", e);
            return null;
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
     * 组装数据库记录为站点提醒实体
     * 
     * @author lizyi 2013-10-30 上午8:59:36
     * @param cursor
     * @return
     */
    private RemindInfo buildRemindStationInfo(Cursor cursor)
    {
        RemindInfo temp = new RemindInfo();

        temp.setId(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.Id)));
        temp.setArea_type(AreaType.myValueOf(cursor.getInt(cursor.getColumnIndex(RemindStationInfoTable.Area_Type))));
        temp.setArea_name(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.Area_Name)));

        temp.setLine_range(LineRange.myValueOf(cursor.getInt(cursor.getColumnIndex(RemindStationInfoTable.Line_Range))));
        temp.setStatus_range(StatusRange.myValueOf(cursor.getInt(cursor.getColumnIndex(RemindStationInfoTable.Status_Range))));

        temp.setStation_id(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.Station_Id)));
        temp.setStation_name(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.Station_Name)));
        temp.setLine_id(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.Line_Id)));
        temp.setLine_name(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.Line_Name)));

        temp.setVehiche_vin(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.Vehiche_Vin)));
        temp.setVehiche_number(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.Vehiche_Number)));
        temp.setRemind_range(RemindRange.myValueOf(cursor.getInt(cursor.getColumnIndex(RemindStationInfoTable.Remind_Range))));
        temp.setRemind_type(RemindType.myValueOf(cursor.getInt(cursor.getColumnIndex(RemindStationInfoTable.Remind_Type))));
        temp.setRemind_value(cursor.getInt(cursor.getColumnIndex(RemindStationInfoTable.Remind_Value)));
        temp.setRemind_week(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.Remind_Week)));
        temp.setRemind_status(RemindStatus.myValueOf(cursor.getInt(cursor.getColumnIndex(RemindStationInfoTable.Remind_Status))));

        if (StringUtil.isNotBlank(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.Modify_Time))))
        {
        temp.setModify_time(DateUtils.strToDate(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.Modify_Time)),
                    DateUtils.LONG_TIME_FORMAT));
        }
        
        if (StringUtil.isNotBlank(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.No_Remind_Date))))
        {
            temp.setNo_remind_date(DateUtils.strToDate(cursor.getString(cursor.getColumnIndex(RemindStationInfoTable.No_Remind_Date)),
                    DateUtils.TIME_FORMAT));
        }
        return temp;
    }

    /**
     * 更新提醒信息
     * 
     * @author zhangzhia 2013-10-25 下午4:44:49
     * @param info
     * @return
     */
    public void updateRemindInfo(AreaType area_type, List<RemindInfo> infos, String user_code)
    {
        Logger.i(RemindStationInfoDao.class, logName + "更新提醒信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            Cursor cursor = null;
            ContentValues values = null;
            RemindInfo temp = null;
            int count = 0;
            db.beginTransaction();

            Logger.i(this.getClass(), logName + "先删除此用户所有提醒站点信息");
            String delselection = RemindStationInfoTable.User_Code + "=? AND " + RemindStationInfoTable.Area_Type + "=?";
            String[] delselectionArgs = { user_code, area_type.value() + "" };
            db.delete(RemindStationInfoTable.Table_Name, delselection, delselectionArgs);

            if (infos != null)
            {
                for (int i = 0; i < infos.size(); i++)
                {
                    temp = infos.get(i);
                    values = buildContentValue(temp, user_code);

                    db.insert(RemindStationInfoTable.Table_Name, null, values);
                    if (cursor != null && !cursor.isClosed())
                    {
                        cursor.close();
                    }
                    count++;
                }
            }

            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(RemindStationInfoDao.class, logName + "更新提醒信息数据信息异常：", e);
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

    private ContentValues buildContentValue(RemindInfo info, String user_code)
    {
        ContentValues values = new ContentValues();

        values.put(RemindStationInfoTable.Id, info.getId());
        if (info.getArea_type() != null)
        {
            values.put(RemindStationInfoTable.Area_Type, info.getArea_type().value());
        }
        values.put(RemindStationInfoTable.Area_Name, info.getArea_name());

        if (info.getLine_range() != null)
        {
            values.put(RemindStationInfoTable.Line_Range, info.getLine_range().value());
        }

        if (info.getStatus_range() != null)
        {
            values.put(RemindStationInfoTable.Status_Range, info.getStatus_range().value());
        }

        values.put(RemindStationInfoTable.Station_Id, info.getStation_id());
        values.put(RemindStationInfoTable.Station_Name, info.getStation_name());

        values.put(RemindStationInfoTable.Line_Id, info.line_id);
        values.put(RemindStationInfoTable.Line_Name, info.line_name);

        values.put(RemindStationInfoTable.Vehiche_Vin, info.getVehiche_vin());
        values.put(RemindStationInfoTable.Vehiche_Number, info.getVehiche_number());
        if (info.getRemind_range() != null)
        {
            values.put(RemindStationInfoTable.Remind_Range, info.getRemind_range().value());
        }
        if (info.getRemind_type() != null)
        {
            values.put(RemindStationInfoTable.Remind_Type, info.getRemind_type().value());
        }
        values.put(RemindStationInfoTable.Remind_Value, info.getRemind_value());
        values.put(RemindStationInfoTable.Remind_Week, info.getRemind_week());
        if (info.getRemind_status() != null)
        {
            values.put(RemindStationInfoTable.Remind_Status, info.getRemind_status().value());
        }

        if (info.getModify_time() != null)
        {
            values.put(RemindStationInfoTable.Modify_Time, DateUtils.dateToStr(info.getModify_time(), DateUtils.LONG_TIME_FORMAT));
        }

        if (info.getNo_remind_date() != null)
        {
            values.put(RemindStationInfoTable.No_Remind_Date, DateUtils.dateToStr(info.getNo_remind_date(), DateUtils.TIME_FORMAT));
        }

        values.put(RemindStationInfoTable.User_Code, user_code);
        return values;
    }

    public boolean deleteRemind(String remind_id, String user_code)
    {
        Logger.i(this.getClass(), logName + "删除站点信息：" + remind_id);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();

            String selection = RemindStationInfoTable.Id + "=? AND " + RemindStationInfoTable.User_Code + "=?";
            String[] selectionArgs = { remind_id, user_code };

            db.delete(RemindStationInfoTable.Table_Name, selection, selectionArgs);

            Logger.i(this.getClass(), logName + "删除站点信息成功：" + remind_id);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "删除站点信息异常：" + remind_id, e);
            return false;
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
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
     * 清除当前用户所有站点提醒信息
     * 
     * @author zhangzhia 2013-11-26 上午9:35:04
     * @param user_code
     */
    public void removeAll(String user_code)
    {
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            Logger.i(this.getClass(), logName + "清除当前用户所有站点提醒信息");
            String delselection = RemindStationInfoTable.User_Code + "=?";
            String[] delselectionArgs = { user_code };
            db.delete(RemindStationInfoTable.Table_Name, delselection, delselectionArgs);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "清除当前用户所有站点提醒信息：", e);
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
