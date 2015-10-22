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
import com.yutong.clw.ygbclient.common.beans.StationAreaInfo;
import com.yutong.clw.ygbclient.dao.common.DatabaseHelper;
import com.yutong.clw.ygbclient.dao.common.tables.LineInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.StationAreaTable;

/**
 * 站点区域信息数据库操作类
 * 
 * @author zhangzhia 2013-10-25 下午3:51:41
 */
public class StationAreaDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    private String logName = "[收藏站点信息数据库操作类]:";

    /**
     * <默认构造函数>
     */
    public StationAreaDao(Context context)
    {
        this.context = context;
        databaseHelper = new DatabaseHelper(this.context);
    }

    /**
     * 组装数据库记录为站点实体
     * 
     * @author zhangzhia 2013-11-22 下午4:25:22
     * @param cursor
     * @return
     */
    private StationAreaInfo buildStationInfo(Cursor cursor)
    {
        StationAreaInfo temp = new StationAreaInfo();

        temp.setId(cursor.getString(cursor.getColumnIndex(StationAreaTable.Id)));
        temp.setName(cursor.getString(cursor.getColumnIndex(StationAreaTable.Name)));

        return temp;
    }

    /**
     * 批量更新站点区域信息
     * 
     * @author zhangzhia 2013-11-22 下午4:25:32
     * @param stations
     */
    public void batchUpdate(List<StationAreaInfo> stationAreas)
    {
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            Cursor cursor = null;
            ContentValues values = null;
            StationAreaInfo temp = null;
            int count = 0;
            db.beginTransaction();

            Logger.i(this.getClass(), logName + "先删除此用户所有站点区域信息");

            db.delete(StationAreaTable.Table_Name, null, null);

            if (stationAreas != null)
            {
                for (int i = 0; i < stationAreas.size(); i++)
                {
                    temp = stationAreas.get(i);
                    values = buildContentValue(temp);

                    db.insert(StationAreaTable.Table_Name, null, values);

                    if (cursor != null && !cursor.isClosed())
                    {
                        cursor.close();
                    }
                    count++;
                }

                Logger.i(this.getClass(), logName + "批量添加站点区域信息：" + count);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "批量添加站点区域信息异常：", e);
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

    private ContentValues buildContentValue(StationAreaInfo info)
    {
        ContentValues values = new ContentValues();
        values.put(StationAreaTable.Id, info.getId());
        values.put(StationAreaTable.Name, info.name);
        return values;
    }

    /**
     * 获取用户站点区域
     * 
     * @author zhangzhia 2013-11-22 下午4:18:47
     * @return
     */
    public List<StationAreaInfo> getStationAreas()
    {
        Logger.i(this.getClass(), logName + "获取站点区域信息");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            int count = 0;
            List<StationAreaInfo> infos = new ArrayList<StationAreaInfo>();
            StationAreaInfo bean = null;
            cursor = db.query(StationAreaTable.Table_Name, null, null, null, null, null, null);
            while (cursor != null && cursor.moveToNext())
            {
                bean = buildStationInfo(cursor);
                infos.add(bean);
                count++;
            }
            Logger.i(this.getClass(), logName + "获取站点区域信息数据信息条数：" + count);
            return infos;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "获取站点区域信息数据信息异常：", e);
            return null;
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
     * 清除当前站点区域信息
     * 
     * @author zhangzhia 2013-11-26 上午9:35:04
     */
    public void removeAll()
    {
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            Logger.i(this.getClass(), logName + "清除当前站点区域信息");

            db.delete(StationAreaTable.Table_Name, null, null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "清除当前站点区域信息：", e);
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
