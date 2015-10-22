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
import com.yutong.clw.ygbclient.dao.common.tables.FavoritesTable;
import com.yutong.clw.ygbclient.dao.common.tables.RemindStationInfoTable;

/**
 * 收藏站点信息数据库操作类
 * 
 * @author zhangzhia 2013-10-25 下午3:51:41
 */
public class FavoritiesStationDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    private String logName = "[收藏站点信息数据库操作类]:";

    /**
     * <默认构造函数>
     */
    public FavoritiesStationDao(Context context)
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
        if (notBlank(cursor, FavoritesTable.Area_Type))
        {
            temp.setArea_type(AreaType.myValueOf(cursor.getInt(cursor.getColumnIndex(FavoritesTable.Area_Type))));
        }
        if (notBlank(cursor, FavoritesTable.Status_Range))
        {
            temp.setStatus_range(StatusRange.myValueOf(cursor.getInt(cursor.getColumnIndex(FavoritesTable.Status_Range))));
        }

        temp.setId(cursor.getString(cursor.getColumnIndex(FavoritesTable.Id)));
        temp.setBelong_area_id(cursor.getString(cursor.getColumnIndex(FavoritesTable.Belong_Area_Id)));
        temp.setName(cursor.getString(cursor.getColumnIndex(FavoritesTable.Name)));
        temp.setAlias(cursor.getString(cursor.getColumnIndex(FavoritesTable.Alias)));
        temp.setGps_lon(cursor.getDouble(cursor.getColumnIndex(FavoritesTable.gps_lon)));
        temp.setGps_lat(cursor.getDouble(cursor.getColumnIndex(FavoritesTable.gps_lat)));

        temp.setFavorites(DataTypeConvertUtils.int2Boolean(cursor.getInt(cursor.getColumnIndex(FavoritesTable.Favorites))));

        // temp.setType(StationType.myValueOf(cursor.getInt(cursor.getColumnIndex(FavoritesTable.Type))));
        // temp.setStatus(DataTypeConvertUtils.int2Boolean(cursor.getInt(cursor.getColumnIndex(FavoritesTable.Status))));
        // temp.setPlan_arrive_time(cursor.getString(cursor.getColumnIndex(FavoritesTable.Plan_Arrive_Time)));
        return temp;
    }

    private boolean notBlank(Cursor cursor, String field)
    {
        return StringUtil.isNotBlank(cursor.getString(cursor.getColumnIndex(field)));
    }

    /**
     * 批量更新站点信息
     * 
     * @author zhangzhia 2013-10-25 下午4:44:49
     * @param stations 收藏的站点信息
     * @param user_code 工号
     * @return
     */
    public void batchUpdate(AreaType area_type, List<StationInfo> stations, String user_code)
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

            Logger.i(this.getClass(), logName + "先删除此用户所有收藏站点信息");
            String delselection = FavoritesTable.User_Code + "=? AND " + RemindStationInfoTable.Area_Type + "=?";
            String[] delselectionArgs = { user_code, area_type.value() + "" };
            db.delete(FavoritesTable.Table_Name, delselection, delselectionArgs);

            if (stations != null)
            {
                for (int i = 0; i < stations.size(); i++)
                {
                    temp = stations.get(i);
                    values = buildContentValue(temp, user_code);

                    db.insert(FavoritesTable.Table_Name, null, values);
                    if (cursor != null && !cursor.isClosed())
                    {
                        cursor.close();
                    }
                    count++;
                }
                Logger.i(this.getClass(), logName + "批量添加站点信息：" + count);
            }
            db.setTransactionSuccessful();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "添加数据信息异常：", e);
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

    /**
     * 添加站点信息
     * 
     * @author zhangzhia 2013-10-25 下午4:44:49
     * @param station 收藏的站点信息
     * @param user_code 工号
     * @return
     */
    public void addFavoritiesStation(StationInfo station, String user_code)
    {
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            ContentValues values = null;

            db.beginTransaction();

            if (station != null)
            {
                values = buildContentValue(station, user_code);

                db.insert(FavoritesTable.Table_Name, null, values);

                Logger.i(this.getClass(), logName + "厂区[" + station.area_type != null ? station.area_type.getName() : "" + "]添加站点信息："
                        + station.id + "[" + station.name + "]");
            }
            db.setTransactionSuccessful();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "添加数据信息异常：", e);
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

    /**
     * 取消收藏站点
     * 
     * @author zhangzhia 2013-11-26 上午9:35:04
     * @param station_id
     * @param user_code
     */
    public void removeFavoritiesStation(String station_id, String user_code)
    {
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            Logger.i(this.getClass(), logName + "移除收藏站点信息");
            String delselection = FavoritesTable.Id + "=? AND " + FavoritesTable.User_Code + "=?";
            String[] delselectionArgs = { station_id, user_code };
            db.delete(FavoritesTable.Table_Name, delselection, delselectionArgs);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "移除收藏站点异常：", e);
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

    private ContentValues buildContentValue(StationInfo info, String user_code)
    {
        ContentValues values = new ContentValues();
        values.put(FavoritesTable.Id, info.getId());
        values.put(FavoritesTable.Belong_Area_Id, info.getBelong_area_id());
        values.put(FavoritesTable.Name, info.getName());
        values.put(FavoritesTable.Alias, info.getAlias());
        values.put(FavoritesTable.gps_lon, info.getGps_lon());
        values.put(FavoritesTable.gps_lat, info.getGps_lat());
        values.put(FavoritesTable.Favorites, DataTypeConvertUtils.boolean2Int(info.isFavorites()));
        values.put(FavoritesTable.Status, DataTypeConvertUtils.boolean2Int(info.status));
        if (info.area_type != null)
        {
            values.put(FavoritesTable.Area_Type, info.getArea_type().value());
        }
        if (info.status_range != null)
        {
            values.put(FavoritesTable.Status_Range, info.status_range.value());
        }
        values.put(FavoritesTable.User_Code, user_code);
        return values;
    }

    /**
     * 获取用户收藏站点
     * 
     * @author lizyi 2013-11-5 下午2:44:51
     * @param area_type 厂区范围
     * @param user_code 工号
     * @return
     */
    public List<StationInfo> getFavoritesStations(AreaType area_type, String user_code)
    {
        Logger.i(this.getClass(), logName + "获取收藏站点信息");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            int count = 0;
            List<StationInfo> infos = new ArrayList<StationInfo>();
            StationInfo bean = null;
            String selection = FavoritesTable.Area_Type + "=? AND " + FavoritesTable.User_Code + "=?";
            String[] selectionArgs = { area_type.value() + "", user_code };
            cursor = db.query(FavoritesTable.Table_Name, null, selection, selectionArgs, null, null, null);
            while (cursor != null && cursor.moveToNext())
            {
                bean = buildStationInfo(cursor);
                infos.add(bean);
                count++;
            }
            Logger.i(this.getClass(), logName + "获取收藏站点信息数据信息条数：" + count);
            return infos;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "获取收藏站点信息数据信息异常：", e);
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
     * 清除当前用户所有收藏站点信息
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

            Logger.i(this.getClass(), logName + "清除当前用户所有收藏站点信息");
            String delselection = FavoritesTable.User_Code + "=?";
            String[] delselectionArgs = { user_code };
            db.delete(FavoritesTable.Table_Name, delselection, delselectionArgs);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "清除当前用户所有收藏站点信息：", e);
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
