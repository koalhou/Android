package com.yutong.axxc.parents.dao.news;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.dao.beans.DaoNewsInfoBean;
import com.yutong.axxc.parents.dao.common.DatabaseHelper;
import com.yutong.axxc.parents.dao.common.tables.NewsInfoTable;
import com.yutong.axxc.parents.dao.login.LoginInfoDao;

/**
 * 新闻信息数据库操作类
 * 
 * @author zhangzhia 2013-9-9 下午3:59:21
 */
public class NewsInfoDao {
	private DatabaseHelper databaseHelper;

	/** Context对象用于查询 */
	private Context context;

	private String logTypeName = "[新闻信息数据库操作类1]:";

	/**
	 * <默认构造函数>
	 */
	public NewsInfoDao(Context context) {
		this.context = context;
		databaseHelper = new DatabaseHelper(this.context);
	}

	/**
	 * 批量添加新闻信息
	 * 
	 * @param newsInfoBeans
	 *            新闻信息
	 * @param userID
	 *            用户ID
	 * @return
	 */
	public boolean addNewsInfos(List<DaoNewsInfoBean> daoNewsInfoBeans,
			String userID) {
		Logger.i(this.getClass(), logTypeName + "批量添加新闻信息");
		SQLiteDatabase db = null;
		try {
			db = databaseHelper.getWritableDatabase();

			if (daoNewsInfoBeans != null && daoNewsInfoBeans.size() > 0) { // 手动设置开始事务
				db.beginTransaction();
				for (DaoNewsInfoBean newsInfoBean : daoNewsInfoBeans) {
					if (!isExistNewsInfo(newsInfoBean, userID, db)) {
						Logger.d(this.getClass(), logTypeName
								+ "新增新闻信息，newsid：", newsInfoBean.getNews_id());
						ContentValues values = new ContentValues();

						values.put(NewsInfoTable.NEWS_ID,
                                newsInfoBean.getNews_id());
						values.put(NewsInfoTable.NEWS_CONTENT,
								newsInfoBean.getNews_content());
						values.put(NewsInfoTable.NEWS_SUMMARY,
								newsInfoBean.getNews_summary());
						values.put(NewsInfoTable.NEWS_TIME,
								newsInfoBean.getNews_time());
						values.put(NewsInfoTable.NEWS_TITLE,
								newsInfoBean.getNews_title());
						values.put(NewsInfoTable.NEWS_TYPE,
								newsInfoBean.getNews_type());
						values.put(NewsInfoTable.ISREAD,
								newsInfoBean.getIs_read());

						values.put(NewsInfoTable.NEWS_AUTHOR,
								newsInfoBean.getNews_author());
						values.put(NewsInfoTable.NEWS_IMAGE,
								newsInfoBean.getNews_image());
						values.put(NewsInfoTable.NEWS_IMAGE_URL,
								newsInfoBean.getNews_image_url());

						values.put(NewsInfoTable.USER_ID, userID);

						/* 插入数据库操作 */
						db.insert(NewsInfoTable.TABLE_NAME, null, values);
					}
				}
				// 设置事务处理成功，不设置会自动回滚不提交
				db.setTransactionSuccessful();
			}

			return true;
		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "新增新闻信息异常：", e);
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
	 * 修改新闻信息
	 * 
	 * @param alertInfoBean
	 * @param userID
	 *            用户ID
	 * @return
	 */
	public boolean modifyNewsInfo(DaoNewsInfoBean newsInfoBean, String userID) {
		Logger.i(LoginInfoDao.class, logTypeName + "修改新闻已读信息");
		SQLiteDatabase db = null;
		try {
			db = databaseHelper.getWritableDatabase();
			if (newsInfoBean != null) {
				ContentValues values = new ContentValues();
				String whereClause = new StringBuilder()
						.append(NewsInfoTable.NEWS_ID).append("=? and ")
						.append(NewsInfoTable.USER_ID).append("=?").toString();
				String[] whereArgs = { newsInfoBean.getNews_id(), userID };

				values.put(NewsInfoTable.NEWS_CONTENT,
						newsInfoBean.getNews_content());
				// values.put(NewsInfoTable.NEWS_SUMMARY,
				// newsInfoBean.getNews_summary());
				// values.put(NewsInfoTable.NEWS_TIME,
				// newsInfoBean.getNews_time());
				// values.put(NewsInfoTable.NEWS_TITLE,
				// newsInfoBean.getNews_title());
				// values.put(NewsInfoTable.NEWS_TYPE,
				// newsInfoBean.getNews_type());
				// values.put(NewsInfoTable.ISREAD, newsInfoBean.getIs_read());

				values.put(NewsInfoTable.NEWS_AUTHOR,
						newsInfoBean.getNews_author());
				values.put(NewsInfoTable.NEWS_IMAGE,
						newsInfoBean.getNews_image());
				values.put(NewsInfoTable.NEWS_IMAGE_URL,
						newsInfoBean.getNews_image_url());
				values.put(NewsInfoTable.ISREAD,
                        newsInfoBean.getIs_read());
				// values.put(NewsInfoTable.USER_ID, userID);

				db.update(NewsInfoTable.TABLE_NAME, values, whereClause,
						whereArgs);
			}
			return true;
		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "添加新闻备注已读信息异常：", e);
			return false;
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 获取本地不存在的新闻数量
	 * 
	 * @param newsInfoBeans
	 *            新闻信息
	 * @param userID
	 *            用户ID
	 * @return
	 */
	public int getNonExistNewsCount(List<DaoNewsInfoBean> newsInfoBeans,
			String userID) {
		Logger.i(this.getClass(), logTypeName + "获取本地不存在的新闻数量");
		SQLiteDatabase db = null;
		int count = 0;
		try {
			db = databaseHelper.getWritableDatabase();

			if (newsInfoBeans != null && newsInfoBeans.size() > 0) {
				for (DaoNewsInfoBean newsInfoBean : newsInfoBeans) {
					if (!isExistNewsInfo(newsInfoBean, userID, db)) {
						count++;
					}
				}
			}

		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "获取本地不存在的新闻数量：", e);
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return count;
	}

	/**
	 * 是否存在新闻信息 /**
	 * 
	 * @param newsInfoBean
	 * @param userID
	 *            用户ID
	 * @param db
	 * @return
	 */
	private boolean isExistNewsInfo(DaoNewsInfoBean newsInfoBean,
			String userID, SQLiteDatabase db) {
		Logger.i(this.getClass(), logTypeName + "检查新闻信息是否存在，news_id：",
				newsInfoBean.getNews_id());
		Cursor cursor = null;
		String selection = new StringBuilder().append(NewsInfoTable.NEWS_ID)
				.append("=? and ").append(NewsInfoTable.USER_ID).append("=?")
				.toString();
		String[] selectionArgs = { newsInfoBean.getNews_id(), userID };

		try {
			cursor = db.query(NewsInfoTable.TABLE_NAME, null, selection,
					selectionArgs, null, null, null);
			if (cursor != null && cursor.moveToNext()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "查询新闻信息异常：", e);
			return false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * 是否存在新闻信息 /**
	 * 
	 * @param newsInfoBean
	 * @param userID
	 *            用户ID
	 * @param db
	 * @return
	 */
	public boolean isExistNewsInfo(DaoNewsInfoBean newsInfoBean, String userID) {
		SQLiteDatabase db = null;
		try {
			db = databaseHelper.getReadableDatabase();
			return isExistNewsInfo(newsInfoBean, userID, db);
		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "查询新闻信息异常：", e);
			return false;
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 是否存在新闻详细信息 /**
	 * 
	 * @param newsInfoBean
	 * @param userID
	 *            用户ID
	 * @param db
	 * @return
	 */
	private boolean isExistNewsDetailInfo(String news_id, String userID) {
		SQLiteDatabase db = null;
		Logger.i(this.getClass(), logTypeName + "检查新闻详细信息是否存在，news_id：",
				news_id);
		Cursor cursor = null;

		String selection = new StringBuilder().append(NewsInfoTable.NEWS_ID)
				.append("=? and ").append(NewsInfoTable.USER_ID)
				.append("=? and ").append(NewsInfoTable.NEWS_AUTHOR)
				.append("<> '' and ").append(NewsInfoTable.NEWS_AUTHOR)
				.append("is not null)").toString();
		String[] selectionArgs = { news_id, userID };
		try {

			db = databaseHelper.getReadableDatabase();
			cursor = db.query(NewsInfoTable.TABLE_NAME, null, selection,
					selectionArgs, null, null, null);
			if (cursor != null && cursor.moveToNext()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "查询新闻详细信息异常：", e);
			return false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * 查询新闻信息
	 * 
	 * @param userID
	 *            用户ID
	 * @return
	 */
	public DaoNewsInfoBean getNewsDetailInfo(String news_id, String userID) {
		Logger.i(this.getClass(), logTypeName + "获取新闻信息,id:" + news_id);
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		DaoNewsInfoBean newsInfoBean = null;
		Cursor cursor = null;
		String[] columns = { NewsInfoTable.ISREAD, NewsInfoTable.NEWS_AUTHOR,
				NewsInfoTable.NEWS_IMAGE, NewsInfoTable.NEWS_IMAGE_URL,
				NewsInfoTable.NEWS_CONTENT, NewsInfoTable.NEWS_ID,
				NewsInfoTable.NEWS_SUMMARY, NewsInfoTable.NEWS_TIME,
				NewsInfoTable.NEWS_TITLE, NewsInfoTable.NEWS_TYPE };

		//
		String selection = new StringBuilder(NewsInfoTable.USER_ID)
				.append("=? and ").append(NewsInfoTable.NEWS_ID).append("=?")
				.toString();
		String[] selectionArgs = { userID, news_id };

		try {
			cursor = db.query(NewsInfoTable.TABLE_NAME, null, selection,
					selectionArgs, null, null, null);
			if (cursor != null && cursor.moveToNext()) {
				newsInfoBean = new DaoNewsInfoBean();
				newsInfoBean.setNews_content(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_CONTENT)));
				newsInfoBean.setNews_id(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_ID)));
				newsInfoBean.setNews_summary(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_SUMMARY)));
				newsInfoBean.setNews_time(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_TIME)));
				newsInfoBean.setNews_title(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_TITLE)));
				newsInfoBean.setNews_type(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_TYPE)));

				newsInfoBean.setNews_author(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_AUTHOR)));
				newsInfoBean.setNews_image(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_IMAGE)));
				newsInfoBean.setNews_image_url(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_IMAGE_URL)));

				newsInfoBean.setIs_read(cursor.getInt(cursor
						.getColumnIndex(NewsInfoTable.ISREAD)) == 0 ? false
						: true);

			}

			return newsInfoBean;
		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "查询新闻信息异常：", e);
			return newsInfoBean;
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
	 * 查询新闻信息
	 * 
	 * @param userID
	 *            用户ID
	 * @return
	 */
	public List<DaoNewsInfoBean> getNewsInfos(String userID, String new_type) {
		Logger.i(this.getClass(), logTypeName + "获取所有新闻信息");
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor cursor = null;
		String[] columns = { NewsInfoTable.ISREAD, NewsInfoTable.NEWS_AUTHOR,
				NewsInfoTable.NEWS_IMAGE, NewsInfoTable.NEWS_IMAGE_URL,
				NewsInfoTable.NEWS_CONTENT, NewsInfoTable.NEWS_ID,
				NewsInfoTable.NEWS_SUMMARY, NewsInfoTable.NEWS_TIME,
				NewsInfoTable.NEWS_TITLE, NewsInfoTable.NEWS_TYPE };

		// new StringBuilder("s").append("=?")
		String selection = NewsInfoTable.USER_ID + "=?";

		List<String> list = new ArrayList<String>();
		list.add(userID);
		if ("0".equals(new_type)) {
			selection += " and " + NewsInfoTable.NEWS_TYPE + "=?";
			list.add(new_type);
		}
		
		int size = list.size();
		String[] selectionArgs = (String[])list.toArray(new String[size]);

		//selectionArgs = (String[]) list.toArray();

		String sortOrder = new StringBuilder(NewsInfoTable.NEWS_TIME).append(
				" DESC").toString();

		List<DaoNewsInfoBean> newsInfoBeans = new ArrayList<DaoNewsInfoBean>();
		try {
			cursor = db.query(NewsInfoTable.TABLE_NAME, columns, selection,
					selectionArgs, null, null, sortOrder);

			while (cursor != null && cursor.moveToNext()) {
				DaoNewsInfoBean newsInfoBean = new DaoNewsInfoBean();
				newsInfoBean.setNews_content(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_CONTENT)));
				newsInfoBean.setNews_id(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_ID)));
				newsInfoBean.setNews_summary(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_SUMMARY)));
				newsInfoBean.setNews_time(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_TIME)));
				newsInfoBean.setNews_title(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_TITLE)));
				newsInfoBean.setNews_type(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_TYPE)));

				newsInfoBean.setNews_author(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_AUTHOR)));
				newsInfoBean.setNews_image(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_IMAGE)));
				newsInfoBean.setNews_image_url(cursor.getString(cursor
						.getColumnIndex(NewsInfoTable.NEWS_IMAGE_URL)));

				newsInfoBean.setIs_read(cursor.getInt(cursor
						.getColumnIndex(NewsInfoTable.ISREAD)) == 0 ? false
						: true);
				newsInfoBeans.add(newsInfoBean);
			}
			return newsInfoBeans;
		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "查询新闻信息异常：", e);
			return newsInfoBeans;
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
	 * 添加已读标志
	 * 
	 * @param alertInfoBean
	 * @param userID
	 *            用户ID
	 * @return
	 */
	public boolean addReadFlag(DaoNewsInfoBean newsInfoBean, String userID) {
		Logger.i(LoginInfoDao.class, logTypeName + "添加新闻已读信息");
		SQLiteDatabase db = null;
		try {
			db = databaseHelper.getWritableDatabase();
			if (newsInfoBean != null) {
				ContentValues values = new ContentValues();
				String whereClause = new StringBuilder()
						.append(NewsInfoTable.NEWS_ID).append("=? and ")
						.append(NewsInfoTable.USER_ID).append("=?").toString();
				String[] whereArgs = { newsInfoBean.getNews_id(), userID };
				values.put(NewsInfoTable.ISREAD, true);
				db.update(NewsInfoTable.TABLE_NAME, values, whereClause,
						whereArgs);
			}
			return true;
		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "添加新闻备注已读信息异常：", e);
			return false;
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 获取未读新闻条数
	 * 
	 * @param userID
	 *            用户ID
	 * @return
	 */
	public int getUnreadNewsCount(String userID) {
		Logger.i(this.getClass(), logTypeName + "获取未读新闻条数");
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor cursor = null;
		String selection = new StringBuilder().append(NewsInfoTable.ISREAD)
				.append("<>? AND ").append(NewsInfoTable.USER_ID).append("=?")
				.toString();
		String[] selectionArgs = { "1", userID };
		try {
			cursor = db.query(NewsInfoTable.TABLE_NAME, null, selection,
					selectionArgs, null, null, null);
			if (cursor != null && cursor.moveToNext()) {
				return cursor.getCount();
			}
			return 0;
		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "获取未读新闻条数异常：", e);
			return 0;
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
	 * 删除所有新闻信息
	 * 
	 * @param userID
	 *            用户ID
	 * @return
	 */
	public boolean delNewsInfos(String userID) {
		Logger.i(LoginInfoDao.class, logTypeName + "删除所有新闻信息");
		SQLiteDatabase db = null;
		try {
			db = databaseHelper.getWritableDatabase();
			String whereClause = NewsInfoTable.USER_ID + "=?";
			String[] whereArgs = { userID };
			db.delete(NewsInfoTable.TABLE_NAME, whereClause, whereArgs);
			return true;
		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "删除所有新闻信息异常：", e);
			return false;
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 删除三月前新闻信息
	 * 
	 * @param userID
	 *            用户ID
	 * @return
	 */
	public boolean del3MonthsAgoNewsInfos(String userID) {
		Logger.i(LoginInfoDao.class, logTypeName + "删除三个月前新闻信息");
		SQLiteDatabase db = null;
		try {
			db = databaseHelper.getWritableDatabase();
			String whereClause = NewsInfoTable.USER_ID + "=? and "
					+ NewsInfoTable.NEWS_TIME + ">?";
			String[] whereArgs = { userID, Tools.get3MonthsAgoTime() };
			db.delete(NewsInfoTable.TABLE_NAME, whereClause, whereArgs);
			return true;
		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "删除三个月前新闻信息异常：", e);
			return false;
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 获取最大新闻时间
	 * 
	 * @param userID
	 *            用户ID
	 * @return
	 */
	public String getMaxNewsTime(String userID) {
		Logger.i(this.getClass(), logTypeName + " 获取最大新闻时间");
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor cursor = null;
		String sql = new StringBuilder().append("Select max(")
				.append(NewsInfoTable.NEWS_TIME).append(") maxTime from ")
				.append(NewsInfoTable.TABLE_NAME).append(" where ")
				.append(NewsInfoTable.USER_ID).append("='").append(userID)
				.append("'").toString();
		try {
			cursor = db.rawQuery(sql, null);

			if (cursor != null && cursor.moveToNext()) {
				return cursor.getString(cursor.getColumnIndex("maxTime"));
			}
			return null;
		} catch (Exception e) {
			Logger.e(this.getClass(), logTypeName + "查询最大新闻时间异常：", e);
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		}
	}
}
