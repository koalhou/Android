package com.yutong.clw.ygbclient.dao.common.tables;

public class PushAnnouncementMsgTable {

	/** 表名称 */
	public static final String Table_Name = "advise_pushReply";

	/* 推送信息ID */
	public static final String AdviseMsgID = "advise_id";

	/* 客户端提出建议时间 */
	public static final String Advise_Time = "advise_time";
	
	/* 客户端提出建议内容 */
	public static final String Advise = "advise";
	
	/* 员工号 */
	public static final String User_Code = "user_code";
	
	/* 建议是否被回复  	0:未回复    1：已回复*/
	public static final String ReplyFlag = "if_reply_flag";

	/* 服务端回复时间 */
	public static final String Reply_Time = "reply_time";

	/* 服务端回复内容 */
	public static final String Reply = "reply";

	/* 是否查看过 0:未查看 1：查看 */
	public static final String ReadFlag = "if_read_flag";
	
	

	/**
	 * 创建表
	 * 
	 * @author zhangzhia 2013-9-6 下午6:25:34
	 * @return
	 */
	public static String getTableSQL() {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS ");
		builder.append(PushAnnouncementMsgTable.Table_Name).append("(");
		
		builder.append(PushAnnouncementMsgTable.AdviseMsgID).append(" TEXT,");
		builder.append(PushAnnouncementMsgTable.Advise_Time).append(" TEXT,");
		builder.append(PushAnnouncementMsgTable.Advise).append(" TEXT,");
		builder.append(PushAnnouncementMsgTable.User_Code).append(" TEXT,");
		
		
		builder.append(PushAnnouncementMsgTable.ReplyFlag).append(" TEXT,");
		builder.append(PushAnnouncementMsgTable.Reply_Time).append(" TEXT,");
		builder.append(PushAnnouncementMsgTable.Reply).append(" TEXT,");
		builder.append(PushAnnouncementMsgTable.ReadFlag).append(" TEXT");
		builder.append(");");
		return builder.toString();
	}
}
