package com.yutong.clw.ygbclient.dao.other;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.AdviseReply;
import com.yutong.clw.ygbclient.common.utils.PreferencesUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.dao.common.DatabaseHelper;
import com.yutong.clw.ygbclient.dao.common.tables.PushAnnouncementMsgTable;
import com.yutong.clw.ygbclient.view.bean.push.FeedBackPushBean;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;

/**
 * 意见反馈推送操作类, 目前只存一条，后续根据需要更新功能
 */
public class AdvicePushInfoDao {
	
	private final static String TAG = "[意见反馈推送操作类]";
	
	private DatabaseHelper databaseHelper;

	/** Context对象用于查询 */
	private Context context;

	/**
	 * <默认构造函数>
	 */
	public AdvicePushInfoDao(Context context) {
		this.context = context;
		databaseHelper = new DatabaseHelper(this.context);
	}

	/**
	 * 添加意见反馈推送信息
	 * 
	 * @param loginInfo
	 * @return
	 */
	public boolean addAdviceInfo(FeedBackPushBean advicePushBean) {
		Logger.i(AdvicePushInfoDao.class, TAG+"：添加用户登录信息");
		SQLiteDatabase db = null;
		try {
			db = databaseHelper.getWritableDatabase();
			if (advicePushBean != null) {
				// 开启事务
				db.beginTransaction();

				/*
				 * public String msgID;
				 * 
				 * public String adviseTime; public String advise; public String
				 * userCode;
				 * 
				 * public String replyFlag;
				 * 
				 * public String replyTime; public String reply;
				 * 
				 * public String readFlag;
				 */

				ContentValues values = new ContentValues();
				values.put(PushAnnouncementMsgTable.AdviseMsgID,
						advicePushBean.msgID);

				values.put(PushAnnouncementMsgTable.Advise_Time,
						advicePushBean.adviseTime);
				values.put(PushAnnouncementMsgTable.Advise,
						advicePushBean.advise);

				values.put(PushAnnouncementMsgTable.User_Code,
						advicePushBean.userCode);

				values.put(PushAnnouncementMsgTable.ReplyFlag,
						advicePushBean.replyFlag);

				values.put(PushAnnouncementMsgTable.Reply_Time,
						advicePushBean.replyTime);

				values.put(PushAnnouncementMsgTable.Reply, advicePushBean.reply);

				values.put(PushAnnouncementMsgTable.ReadFlag,
						advicePushBean.readFlag);

				/* 插入数据库操作 */
				db.insert(PushAnnouncementMsgTable.Table_Name, null, values);
				// 关闭事务
				db.setTransactionSuccessful();
			}
			return true;
		} catch (Exception e) {
			Logger.e(AdvicePushInfoDao.class, TAG+"：【意见反馈】添加信息异常：", e);
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

	public boolean updateAdviceReplyInfo(FeedBackPushBean advicePushBean) {

		Logger.i(AdvicePushInfoDao.class, TAG+"更新【反馈意见】信息");
		SQLiteDatabase db = null;
		try {
			db = databaseHelper.getWritableDatabase();
			if (advicePushBean != null) {
				db.beginTransaction();

				String execSQL = "update "
						+ PushAnnouncementMsgTable.Table_Name + " set "
						+ PushAnnouncementMsgTable.Reply_Time + "= ?,"
						+ PushAnnouncementMsgTable.Reply + "= ?,"
						+ PushAnnouncementMsgTable.ReplyFlag + "= ?"
						+ " where " + PushAnnouncementMsgTable.AdviseMsgID
						+ "= ? and " + PushAnnouncementMsgTable.User_Code
						+ "=?";
				String[] args = { 
						advicePushBean.replyTime,
						advicePushBean.reply, 
						advicePushBean.replyFlag,
						advicePushBean.msgID, 
						advicePushBean.userCode };

				db.execSQL(execSQL, args);

				db.setTransactionSuccessful();
			}
			return true;
		} catch (Exception e) {
			Logger.e(AdvicePushInfoDao.class, TAG+":【意见反馈】添加信息异常：", e);
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
	 * 删除登录信息
	 * 
	 * @return true:成功，false:失败
	 * @see
	 */
	public boolean delAdviceInfo() {
		Logger.i(AdvicePushInfoDao.class, TAG+"：删除意见反馈");
		SQLiteDatabase db = null;
		try {
			db = databaseHelper.getWritableDatabase();
			db.delete(PushAnnouncementMsgTable.Table_Name, null, null);
			return true;
		} catch (Exception e) {
			Logger.e(AdvicePushInfoDao.class, TAG+"：删除意见反馈异常：", e);
			return false;
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 获取意见反馈消息列表
	 * 
	 * @return
	 */
	public List<FeedBackPushBean> getAdviceInfoList(String userCode) {
		Logger.i(AdvicePushInfoDao.class, TAG+"：获取意见反馈消息列表");
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {

			db = databaseHelper.getReadableDatabase();

			String execSQL = "select * from "
					+ PushAnnouncementMsgTable.Table_Name 
					+" where "+PushAnnouncementMsgTable.User_Code+"=? "
					+ " order by "
					+ PushAnnouncementMsgTable.ReplyFlag + " desc,"
					+ PushAnnouncementMsgTable.Reply_Time + " desc,"
					+ PushAnnouncementMsgTable.Advise_Time + " desc";
			
			String[] bindArgs = {userCode};
			cursor = db.rawQuery(execSQL, bindArgs);
			List<FeedBackPushBean> infos = new ArrayList<FeedBackPushBean>();
			while (cursor != null && cursor.moveToNext()) {
				infos.add(buildStationInfo(cursor));
			}
			return infos;
		} catch (Exception e) {
			Logger.e(AdvicePushInfoDao.class, TAG+"：获取意见反馈消息列表异常：", e);
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

	/**
	 * 获取意见反馈消息总数
	 * 
	 * @return
	 */
	public int getAdviceInfoNumByReadFlag(String readFlag, String replyFlag) {

		SQLiteDatabase db = null;
		Cursor cursor = null;
		int nums = 0;
		try {

			db = databaseHelper.getReadableDatabase();

			String execSQL = "select count(*) from "
					+ PushAnnouncementMsgTable.Table_Name + " where "
					+ PushAnnouncementMsgTable.ReadFlag + "= ? and "
					+ PushAnnouncementMsgTable.ReplyFlag + "= ?";
			String[] bindArgs = { readFlag, replyFlag };
			cursor = db.rawQuery(execSQL, bindArgs);

			if (cursor.moveToNext()) {
				nums = cursor.getInt(cursor.getColumnIndex("count(*)"));
			}
			return nums;
		} catch (Exception e) {
			Logger.e(AdvicePushInfoDao.class, TAG+"：获取意见反馈消息总数异常：", e);
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

	private FeedBackPushBean buildStationInfo(Cursor cursor) {

		FeedBackPushBean adviceReply = new FeedBackPushBean();
		adviceReply.msgID = cursor.getString(cursor
				.getColumnIndex(PushAnnouncementMsgTable.AdviseMsgID));
		adviceReply.adviseTime = cursor.getString(cursor
				.getColumnIndex(PushAnnouncementMsgTable.Advise_Time));
		adviceReply.advise = cursor.getString(cursor
				.getColumnIndex(PushAnnouncementMsgTable.Advise));
		adviceReply.userCode = cursor.getString(cursor
				.getColumnIndex(PushAnnouncementMsgTable.User_Code));
		adviceReply.replyFlag = cursor.getString(cursor
				.getColumnIndex(PushAnnouncementMsgTable.ReplyFlag));
		adviceReply.replyTime = cursor.getString(cursor
				.getColumnIndex(PushAnnouncementMsgTable.Reply_Time));
		adviceReply.reply = cursor.getString(cursor
				.getColumnIndex(PushAnnouncementMsgTable.Reply));
		adviceReply.readFlag = cursor.getString(cursor
				.getColumnIndex(PushAnnouncementMsgTable.ReadFlag));
		return adviceReply;
	}

	public boolean setAdviceListStatus(String readFlag, String replyFlag) {

		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {

			db = databaseHelper.getWritableDatabase();
			db.beginTransaction();

			String beforeFlag = readFlag
					.equals(ActivityCommConstant.ADVICE_READ) ? ActivityCommConstant.ADVICE_UN_READ
					: readFlag;
			String execSQL = "update " + PushAnnouncementMsgTable.Table_Name
					+ " set " + PushAnnouncementMsgTable.ReadFlag + "= ?"
					+ " where " + PushAnnouncementMsgTable.ReadFlag
					+ "= ? and " + PushAnnouncementMsgTable.ReplyFlag + "= ?";
			String[] args = { readFlag, beforeFlag, replyFlag };
			db.execSQL(execSQL, args);

			return true;
		} catch (Exception e) {
			Logger.e(AdvicePushInfoDao.class, TAG+"：设置意见反馈状态异常：", e);
			return false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}

			if (db != null) {
				if (db.inTransaction()) {
					db.setTransactionSuccessful();
					db.endTransaction();
				}
				db.close();
			}
		}
	}

	public void setAdviceListStatus(List<FeedBackPushBean> adviseReplyList) {
		
		if(adviseReplyList!=null && adviseReplyList.size()>0){
			
			for(FeedBackPushBean feedBackPushBean:adviseReplyList){
				
				feedBackPushBean.userCode = PreferencesUtils.getString(context, ActivityCommConstant.prefName,ActivityCommConstant.EMP_NUMBER);
				
				feedBackPushBean.readFlag = ActivityCommConstant.ADVICE_UN_READ;
				
				if(StringUtil.isEmpty(feedBackPushBean.replyTime)){
					feedBackPushBean.replyFlag = ActivityCommConstant.ADVICE_UN_REPLY;
				}else{
					feedBackPushBean.replyFlag = ActivityCommConstant.ADVICE_REPLY;
				}
				
				/*if(isExist(feedBackPushBean,ActivityCommConstant.ADVICE_UN_REPLY)){
					updateAdviceReplyInfo(feedBackPushBean);
				}else if(isExist(feedBackPushBean,ActivityCommConstant.ADVICE_REPLY)){
					continue;
				}else{
					addAdviceInfo(feedBackPushBean);
				}*/
				
				if(isExist(feedBackPushBean,ActivityCommConstant.ADVICE_UN_REPLY)){
					updateAdviceReplyInfo(feedBackPushBean);
				}else{
					addAdviceInfo(feedBackPushBean);
				}
				
			}
		}
	}
	
	/*检查某一条回复信息是否存在*/
	public boolean isExist(FeedBackPushBean feedBackPushBean,String isReply) {
		
		SQLiteDatabase db = null;
		Cursor cursor = null;
		int nums = 0;
		try {

			db = databaseHelper.getReadableDatabase();

			/*String execSQL = "select count(*) from "
					+ PushAnnouncementMsgTable.Table_Name + " where "
					+ PushAnnouncementMsgTable.AdviseMsgID + "= ? and "+
					PushAnnouncementMsgTable.ReplyFlag+" = ? and reply_time is not null and reply_time is not ''";
			String[] bindArgs = {feedBackPushBean.msgID,isReply};*/
			
			String execSQL = "select count(*) from "
				+ PushAnnouncementMsgTable.Table_Name + " where "
				+ PushAnnouncementMsgTable.AdviseMsgID + "= ?";
		String[] bindArgs = {feedBackPushBean.msgID};
			
			cursor = db.rawQuery(execSQL, bindArgs);

			if (cursor.moveToNext()) {
				nums = cursor.getInt(cursor.getColumnIndex("count(*)"));
				if(nums>0)
					return true;
				else
					return false;
			}
			return false;
			
		} catch (Exception e) {
			return false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		}
	}
	
	private void updateAdviceTable(AdviseReply adviseReply){
		
	}
}
