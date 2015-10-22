
package com.yutong.axxc.parents.dao.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.dao.beans.DaoLoginInfoBean;
import com.yutong.axxc.parents.dao.common.DatabaseHelper;
import com.yutong.axxc.parents.dao.common.tables.LoginInfoTable;

/**
 * 登录信息数据操作类, 目前只存一条，后续根据需要更新功能
 * @author zhangzhia 2013-9-2 下午3:04:15
 *
 */
public class LoginInfoDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    /**
     * <默认构造函数>
     */
    public LoginInfoDao(Context context)
    {
        this.context = context;
        databaseHelper = new DatabaseHelper(this.context);
    }

    /**
     * 添加登录信息
     * 
     * @param loginInfoBean
     * @return
     */
    public boolean addLoginInfo(DaoLoginInfoBean loginInfoBean)
    {
        Logger.i(LoginInfoDao.class, "[登录信息数据操作类]：添加用户登录信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            if (loginInfoBean != null)
            {
                // 为保证数据只有一条，添加之前先进行删除
                db.delete(LoginInfoTable.TABLE_NAME, null, null);
                ContentValues values = new ContentValues();

                values.put(LoginInfoTable.ACCESS_TOKEN, loginInfoBean.getAccessToken());
                values.put(LoginInfoTable.EXPIRES_IN, loginInfoBean.getExpiresIn());
                values.put(LoginInfoTable.REFRESH_TOKEN, loginInfoBean.getRefreshToken());
                values.put(LoginInfoTable.USR_ID, loginInfoBean.getUsr_id());
                values.put(LoginInfoTable.USR_NO, loginInfoBean.getUsr_no());
                values.put(LoginInfoTable.USR_LOGIN_NAME, loginInfoBean.getUsr_login_name());
                values.put(LoginInfoTable.USR_NAME, loginInfoBean.getUsr_name());
                values.put(LoginInfoTable.USR_SEX, loginInfoBean.getUsr_sex());
                values.put(LoginInfoTable.USR_PHONE, loginInfoBean.getUsr_phone());
                values.put(LoginInfoTable.USR_PHOTO, loginInfoBean.getUsr_photo());
                values.put(LoginInfoTable.USR_ADDR, loginInfoBean.getUsr_sex());
                values.put(LoginInfoTable.USR_EMAIL, loginInfoBean.getUsr_email());
                values.put(LoginInfoTable.P_USR_NO, loginInfoBean.getP_usr_no());
                values.put(LoginInfoTable.P_USR_LOGIN_NAME, loginInfoBean.getP_usr_login_name());
         
                values.put(LoginInfoTable.ETAG, loginInfoBean.geteTag());

                /* 插入数据库操作 */
                db.insert(LoginInfoTable.TABLE_NAME, null, values);

            }
            return true;
        }
        catch (Exception e)
        {
            Logger.e(LoginInfoDao.class, "[登录信息数据操作类]：添加用户信息异常：", e);
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
     * 升级登录信息
     * 
     * @param loginInfoBean
     * @return
     */
    public boolean updateLoginInfo(DaoLoginInfoBean loginInfoBean)
    {
        Logger.i(LoginInfoDao.class, "[登录信息数据操作类]：添加用户登录信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            if (loginInfoBean != null)
            {
                ContentValues values = new ContentValues();

                values.put(LoginInfoTable.EXPIRES_IN, loginInfoBean.getExpiresIn());
                values.put(LoginInfoTable.USR_ID, loginInfoBean.getUsr_id());
                values.put(LoginInfoTable.USR_NO, loginInfoBean.getUsr_no());
                values.put(LoginInfoTable.USR_LOGIN_NAME, loginInfoBean.getUsr_login_name());
                values.put(LoginInfoTable.USR_NAME, loginInfoBean.getUsr_name());
                values.put(LoginInfoTable.USR_SEX, loginInfoBean.getUsr_sex());
                values.put(LoginInfoTable.USR_PHONE, loginInfoBean.getUsr_phone());
                values.put(LoginInfoTable.USR_PHOTO, loginInfoBean.getUsr_photo());
                values.put(LoginInfoTable.USR_ADDR, loginInfoBean.getUsr_sex());
                values.put(LoginInfoTable.USR_EMAIL, loginInfoBean.getUsr_email());
                values.put(LoginInfoTable.P_USR_NO, loginInfoBean.getP_usr_no());
                values.put(LoginInfoTable.P_USR_LOGIN_NAME, loginInfoBean.getP_usr_login_name());
                values.put(LoginInfoTable.ETAG, loginInfoBean.geteTag());

                db.update(LoginInfoTable.TABLE_NAME, values, null, null);

            }
            return true;
        }
        catch (Exception e)
        {
            Logger.e(LoginInfoDao.class, "[登录信息数据操作类]：更新用户信息异常：", e);
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
     * 通过用户ID删除登录信息
     * 
     * @param uid 用户ID
     * @return true:成功，false:失败
     * @see
     */
    public boolean delLoginInfo()
    {
        Logger.i(LoginInfoDao.class, "[登录信息数据操作类]：根据用户ID删除登录信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            db.delete(LoginInfoTable.TABLE_NAME, null, null);
            return true;
        }
        catch (Exception e)
        {
            Logger.e(LoginInfoDao.class, "[登录信息数据操作类]：根据用户ID删除登录信息异常：", e);
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
     * 获取登录信息
     * 
     * @return
     */
    public DaoLoginInfoBean getLoginInfo()
    {
        Logger.i(LoginInfoDao.class, "[登录信息数据操作类]：根据USERID查询用户信息");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            String[] columns = { LoginInfoTable.ACCESS_TOKEN, LoginInfoTable.EXPIRES_IN, LoginInfoTable.REFRESH_TOKEN,
                    LoginInfoTable.USR_ID, LoginInfoTable.USR_NO, LoginInfoTable.USR_LOGIN_NAME, LoginInfoTable.USR_NAME, 
                    LoginInfoTable.USR_SEX, LoginInfoTable.USR_MAIN, LoginInfoTable.USR_PHONE, LoginInfoTable.USR_PHOTO,
                    LoginInfoTable.USR_ADDR, LoginInfoTable.USR_EMAIL, LoginInfoTable.P_USR_NO, LoginInfoTable.P_USR_LOGIN_NAME, LoginInfoTable.ETAG };

            cursor = db.query(LoginInfoTable.TABLE_NAME, columns, null, null, null, null, null);

            if (cursor != null && cursor.moveToNext())
            {
                DaoLoginInfoBean daoLoginInfoBean = new DaoLoginInfoBean();

                daoLoginInfoBean.setAccessToken(cursor.getString(cursor.getColumnIndex(LoginInfoTable.ACCESS_TOKEN)));
                daoLoginInfoBean.setExpiresIn(cursor.getLong(cursor.getColumnIndex(LoginInfoTable.EXPIRES_IN)));
                daoLoginInfoBean.setRefreshToken(cursor.getString(cursor.getColumnIndex(LoginInfoTable.REFRESH_TOKEN)));

                daoLoginInfoBean.setUsr_id(cursor.getString(cursor.getColumnIndex(LoginInfoTable.USR_ID)));
                daoLoginInfoBean.setUsr_no(cursor.getString(cursor.getColumnIndex(LoginInfoTable.USR_NO)));
                daoLoginInfoBean.setUsr_login_name(cursor.getString(cursor.getColumnIndex(LoginInfoTable.USR_LOGIN_NAME)));
                daoLoginInfoBean.setUsr_name(cursor.getString(cursor.getColumnIndex(LoginInfoTable.USR_NAME)));
                daoLoginInfoBean.setUsr_sex(cursor.getString(cursor.getColumnIndex(LoginInfoTable.USR_SEX)));
                 daoLoginInfoBean.setUsr_phone(cursor.getString(cursor.getColumnIndex(LoginInfoTable.USR_PHONE)));
                daoLoginInfoBean.setUsr_photo(cursor.getString(cursor.getColumnIndex(LoginInfoTable.USR_PHOTO)));
                daoLoginInfoBean.setUsr_addr(cursor.getString(cursor.getColumnIndex(LoginInfoTable.USR_ADDR)));
                daoLoginInfoBean.setUsr_email(cursor.getString(cursor.getColumnIndex(LoginInfoTable.USR_EMAIL)));
                daoLoginInfoBean.setP_usr_no(cursor.getString(cursor.getColumnIndex(LoginInfoTable.P_USR_NO)));
                daoLoginInfoBean.setP_usr_login_name(cursor.getString(cursor.getColumnIndex(LoginInfoTable.P_USR_LOGIN_NAME)));
                daoLoginInfoBean.seteTag(cursor.getString(cursor.getColumnIndex(LoginInfoTable.ETAG)));
                return daoLoginInfoBean;
            }
            return null;
        }
        catch (Exception e)
        {
            Logger.e(LoginInfoDao.class, "[登录信息数据操作类]：根据USERID查询用户信息异常：", e);
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
     * 获取accessToken
     * 
     * @return
     */
    public String getAccessToken()
    {
        Logger.i(LoginInfoDao.class, "[登录信息数据操作类]： 获取accessToken");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            String[] columns = { LoginInfoTable.USR_NAME };
            cursor = db.query(LoginInfoTable.TABLE_NAME, columns, null, null, null, null, null);

            if (cursor != null && cursor.moveToNext())
            {
                DaoLoginInfoBean loginInfoBean = new DaoLoginInfoBean();
                String accessToken = cursor.getString(cursor.getColumnIndex(LoginInfoTable.ACCESS_TOKEN));
                return accessToken;
            }
            return null;
        }
        catch (Exception e)
        {
            Logger.e(LoginInfoDao.class, "[登录信息数据操作类]： 获取accessToken异常：", e);
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

}
