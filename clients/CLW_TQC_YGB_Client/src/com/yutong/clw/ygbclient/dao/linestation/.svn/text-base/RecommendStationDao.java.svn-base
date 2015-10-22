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
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.dao.common.DatabaseHelper;
import com.yutong.clw.ygbclient.dao.common.tables.LineInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.RecommendStationTable;
import com.yutong.clw.ygbclient.dao.common.tables.RecommendStationTable;
import com.yutong.clw.ygbclient.dao.common.tables.RemindStationInfoTable;

/**
 * 推荐站点信息数据库操作类
 * 
 * @author zhangzhia 2013-10-25 下午3:51:41
 */
public class RecommendStationDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    private String logName = "[推荐站点信息数据库操作类]:";

    /**
     * <默认构造函数>
     */
    public RecommendStationDao(Context context)
    {
        this.context = context;
        databaseHelper = new DatabaseHelper(this.context);
    }

    /**
     * 组装数据库记录为站点实体
     * 
     * @author lizyi 2013-10-30 上午8:59:36
     * @param cursor
     * @return
     */
    private StationInfo buildStationInfo(Cursor cursor)
    {
        StationInfo temp = new StationInfo();
      
        temp.setId(cursor.getString(cursor.getColumnIndex(RecommendStationTable.Id)));
        temp.setBelong_area_id(cursor.getString(cursor.getColumnIndex(RecommendStationTable.Belong_Area_Id)));
        temp.setName(cursor.getString(cursor.getColumnIndex(RecommendStationTable.Name)));
        temp.setAlias(cursor.getString(cursor.getColumnIndex(RecommendStationTable.Alias)));
        temp.setGps_lon(cursor.getDouble(cursor.getColumnIndex(RecommendStationTable.gps_lon)));
        temp.setGps_lat(cursor.getDouble(cursor.getColumnIndex(RecommendStationTable.gps_lat)));

        if (notBlank(cursor, RecommendStationTable.Area_Type))
        {
            temp.setArea_type(AreaType.myValueOf(cursor.getInt(cursor.getColumnIndex(RecommendStationTable.Area_Type))));
        }
        if (notBlank(cursor, RecommendStationTable.Status_Range))
        {
            temp.setStatus_range(StatusRange.myValueOf(cursor.getInt(cursor.getColumnIndex(RecommendStationTable.Status_Range))));
        }

         return temp;
    }

    private boolean notBlank(Cursor cursor, String field)
    {
        return StringUtil.isNotBlank(cursor.getString(cursor.getColumnIndex(field)));
    }

    /**
     * 批量更新推荐站点信息
     * 
     * @author zhangzhia 2013-10-25 下午4:44:49
     * @param stations 推荐的站点信息
     * @param user_code 工号
     * @return
     */
    public void batchUpdate(AreaType area_type, List<StationInfo> stations)
    {
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            Cursor cursor = null;
            ContentValues values = null;
            StationInfo temp = null;
            int count = 0;
            db.beginTransaction();

            Logger.i(this.getClass(), logName + "先删除厂区["+area_type.getName()+"]推荐站点信息");
            
            String delselection = RemindStationInfoTable.Area_Type + "=?" ;
            String[] delselectionArgs = { area_type.value() + "" };
            db.delete(RecommendStationTable.Table_Name, delselection, delselectionArgs);

            if (stations != null)
            {
                for (int i = 0; i < stations.size(); i++)
                {
                    temp = stations.get(i);
                    values = buildContentValue(temp);

                    db.insert(RecommendStationTable.Table_Name, null, values);
                    if (cursor != null && !cursor.isClosed())
                    {
                        cursor.close();
                    }
                    count++;
                }
                Logger.i(this.getClass(), logName + "批量添加推荐站点信息：" + count);
            }
            db.setTransactionSuccessful();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "添加推荐站点信息异常：", e);
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

    private ContentValues buildContentValue(StationInfo info)
    {
        ContentValues values = new ContentValues();
        values.put(RecommendStationTable.Id, info.getId());
        values.put(RecommendStationTable.Belong_Area_Id, info.getBelong_area_id());
        values.put(RecommendStationTable.Name, info.getName());
        values.put(RecommendStationTable.Alias, info.getAlias());
        values.put(RecommendStationTable.gps_lon, info.getGps_lon());
        values.put(RecommendStationTable.gps_lat, info.getGps_lat());

        if (info.area_type != null)
        {
            values.put(RecommendStationTable.Area_Type, info.getArea_type().value());
        }
        if (info.status_range != null)
        {
            values.put(RecommendStationTable.Status_Range, info.status_range.value());
        }
      
        return values;
    }

    /**
     * 获取厂区推荐站点
     *@author zhangzhia 2013-11-27 下午2:39:15
     *
     * @return
     */
    public List<StationInfo> getRecommendStations(AreaType area_type)
    {
        Logger.i(this.getClass(), logName + "获取厂区["+area_type.getName()+"]推荐站点信息");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            int count = 0;
            List<StationInfo> infos = new ArrayList<StationInfo>();
            StationInfo bean = null;
            
            String delselection = RemindStationInfoTable.Area_Type + "=?" ;
            String[] delselectionArgs = { area_type.value() + "" };
            cursor = db.query(RecommendStationTable.Table_Name, null, delselection, delselectionArgs, null, null, null);
            while (cursor != null && cursor.moveToNext())
            {
                bean = buildStationInfo(cursor);
                infos.add(bean);
                count++;
            }
            Logger.i(this.getClass(), logName + "获取推荐站点信息数据信息条数：" + count);
            return infos;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "获取推荐站点信息数据信息异常：", e);
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
     * 清除当前推荐站点信息
     * 
     * @author zhangzhia 2013-11-26 上午9:35:04
     */
    public void removeAll()
    {
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            Logger.i(this.getClass(), logName + "清除当前推荐站点信息");

            db.delete(RecommendStationTable.Table_Name, null, null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "清除当前推荐站点信息：", e);
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
