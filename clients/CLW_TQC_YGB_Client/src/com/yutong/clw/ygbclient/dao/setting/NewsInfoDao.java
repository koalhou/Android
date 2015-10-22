/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 下午1:15:15
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.setting;

import java.lang.reflect.Type;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.news.NewsInfo;
import com.yutong.clw.ygbclient.common.enums.news.NewsType;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.dao.common.DatabaseHelper;
import com.yutong.clw.ygbclient.dao.common.tables.NewsInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.RemindStationInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.ResRouterTable;

/**
 * 新闻信息数据操作类
 * 
 * @author zhangzhia 2013-10-25 下午1:15:15
 */
public class NewsInfoDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    /**
     * <默认构造函数>
     */
    public NewsInfoDao(Context context)
    {
        this.context = context;
        databaseHelper = new DatabaseHelper(this.context);
    }

    /**
     * 批量添加新闻信息 添加后则标志位默认为已阅读
     * 
     * @param infos 新闻信息
     * @param user_code 用户code
     * @return
     */
    public boolean addNewsInfos(NewsInfo info, String user_code)
    {
        Logger.i(NewsInfoDao.class, "[新闻信息数据操作类]：批量添加新闻信息");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            ContentValues values = null;
            String selection = NewsInfoTable.NewsId + "=? AND " + NewsInfoTable.User_Code + "=?";
            String[] selectionArgs = { info.id, user_code };
            cursor = db.query(NewsInfoTable.Table_Name, null, selection, selectionArgs, null, null, null);
            values = buildLineInfoValue(info, user_code);
            if (cursor != null && cursor.moveToNext())
            {
                // 更新记录
                db.update(NewsInfoTable.Table_Name, values, selection, selectionArgs);

            }
            else
            {
                /* 插入数据库操作 */
                db.insert(NewsInfoTable.Table_Name, null, values);
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(NewsInfoDao.class, "[新闻信息数据操作类]：批量添加新闻信息异常：", e);
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
     * 组装新闻信息实体为数据库记录
     * 
     * @author lizyi 2013-10-30 上午9:56:26
     * @param info
     * @return
     */
    private ContentValues buildLineInfoValue(NewsInfo info, String user_code)
    {
        ContentValues values = new ContentValues();

        values.put(NewsInfoTable.User_Code, user_code);
        values.put(NewsInfoTable.NewsId, info.id);
        values.put(NewsInfoTable.NewsTitle, info.title);
        values.put(NewsInfoTable.NewsSummary, info.summary);

        values.put(NewsInfoTable.NewsAuthor, info.author);
        values.put(NewsInfoTable.NewsImageResId, info.image_res == null ? "" : buildJson(info.image_res));
//        values.put(NewsInfoTable.NewsImageUrl, info.image_url == null ? "" : buildJson(info.image_url));
        values.put(NewsInfoTable.NewsContent, info.content);
        values.put(NewsInfoTable.NewsTime, info.publish_time == null ? "" : DateUtils.dateToStr(info.publish_time, DateUtils.LONG_TIME_FORMAT));
        values.put(NewsInfoTable.NewsType, info.type == null ? 0 : info.type.value());
        values.put(NewsInfoTable.isRead, 1);
        return values;
    }

    /**
     * 是否存在新闻详细信息
     * 
     * @author zhangzhia 2013-10-25 下午2:58:42
     * @param news_id 新闻id
     * @param user_code 用户code
     * @return+-
     */
    public boolean isExist(String news_id, String user_code)
    {
        //Logger.i(NewsInfoDao.class, "[新闻信息数据操作类]：根据新闻id和user_code查询是否存在新闻详细信息");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            // 查询是否存在已有记录
            String selection = NewsInfoTable.NewsId + "=? AND " + NewsInfoTable.User_Code + "=?";
            String[] selectionArgs = {news_id, user_code };
            cursor = db.query(NewsInfoTable.Table_Name, null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToNext()) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(NewsInfoDao.class, "[新闻信息数据操作类]：查询新闻详细信息异常：", e);
            return false;
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
     * 获取新闻详细信息
     * 
     * @author zhangzhia 2013-10-25 下午3:01:13
     * @param news_id 新闻id
     * @param user_code 用户code
     * @return
     */
    public NewsInfo getNewsInfo(String news_id, String user_code)
    {
        Logger.i(NewsInfoDao.class, "[新闻信息数据操作类]：根据新闻id和user_code查询是否存在新闻详细信息");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        NewsInfo info = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            // 查询是否存在已有记录
            String selection = NewsInfoTable.NewsId + "=? AND " + NewsInfoTable.User_Code + "=?";
            String[] selectionArgs = { news_id, user_code };
            cursor = db.query(NewsInfoTable.Table_Name, null, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToNext())
            {
                info = buildNewsInfo(cursor);
            }
            return info;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(NewsInfoDao.class, "[新闻信息数据操作类]：查询新闻详细信息异常：", e);
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
     * 组装数据库记录为新闻实体
     * 
     * @author lizyi 2013-10-30 上午9:48:15
     * @param cursor
     * @return
     */
    public NewsInfo buildNewsInfo(Cursor cursor)
    {
        NewsInfo bean = new NewsInfo();

        bean.id = cursor.getString(cursor.getColumnIndex(NewsInfoTable.NewsId));
        bean.title = cursor.getString(cursor.getColumnIndex(NewsInfoTable.NewsTitle));
        bean.summary = cursor.getString(cursor.getColumnIndex(NewsInfoTable.NewsSummary));

        bean.author = cursor.getString(cursor.getColumnIndex(NewsInfoTable.NewsAuthor));
        bean.image_res = parseJson(cursor.getString(cursor.getColumnIndex(NewsInfoTable.NewsImageResId)));
//        bean.image_url = parseJson(cursor.getString(cursor.getColumnIndex(NewsInfoTable.NewsImageUrl)));
        bean.content = cursor.getString(cursor.getColumnIndex(NewsInfoTable.NewsContent));
        bean.publish_time = (DateUtils.strToDate(cursor.getString(cursor.getColumnIndex(NewsInfoTable.NewsTime)), DateUtils.LONG_TIME_FORMAT));
        bean.type = NewsType.myValueOf(cursor.getInt(cursor.getColumnIndex(NewsInfoTable.NewsType)));
        bean.is_read = DataTypeConvertUtils.int2Boolean(cursor.getInt(cursor.getColumnIndex(NewsInfoTable.isRead)));

        return bean;
    }

    /**
     * 对象转json字符串
     * 
     * @param bean
     * @return
     */
    public String buildJson(List<String> images)
    {
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>()
        {
        }.getType();
        return gson.toJson(images, type);
    }

    /**
     * json字符串转对象
     * 
     * @param json
     * @param c
     * @return
     */
    public List<String> parseJson(String json)
    {
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>()
        {
        }.getType();
        return gson.fromJson(json, type);
    }

    /**
     * 清除当前用户所有新闻
     * 
     * @author zhangzhia 2013-11-26 上午9:35:04
     */
    public void removeAll(String user_code)
    {
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            Logger.i(this.getClass(), "[新闻信息数据操作类]：清除当前用户所有新闻");
            String delselection = NewsInfoTable.User_Code + "=?";
            String[] delselectionArgs = { user_code };
            db.delete(NewsInfoTable.Table_Name, delselection, delselectionArgs);


        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), "[新闻信息数据操作类]：清除当前用户所有新闻：", e);
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
