package com.yutong.axxc.parents.common.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

/**
 * 消息记录实体类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 * 
 */
public class MsgRecordBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4109608713665884766L;

	public final static String KEY_GPS_LON = "vehicle_lon";

	public final static String KEY_GPS_LAT = "vehicle_lat";

	public final static String KEY_STATION_NAME = "station_name";

	public final static String KEY_TEACHER_PHONE = "teacher_phone";

	public final static String KEY_TEACHER = "teacher";
	
	public final static String KEY_PLATE = "vehicle_plate";
	

	private String msg_id;
	/** 用户id */
	private String usr_id;
	/** 学生id */
	private String cld_id;
	/** 规则id */
	private String rule_id;
	/** 内容json串 */
	private String msg_content;
	/** 事件时间 */
	private String msg_time;

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	public String getUsr_id() {
		return usr_id;
	}

	public void setUsr_id(String usr_id) {
		this.usr_id = usr_id;
	}

	public String getCld_id() {
		return cld_id;
	}

	public void setCld_id(String cld_id) {
		this.cld_id = cld_id;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public String getMsg_content() {
		return msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}

	public String getMsg_time() {
		return msg_time;
	}

	public void setMsg_time(String msg_time) {
		this.msg_time = msg_time;
	}

	/**
	 * 获取内容值
	 * 
	 * @author zhangzhia 2013-9-10 上午11:26:46
	 * @param key
	 *            key
	 */
	public String getContent(String key) {
		try {
			String value = null;
			if (msg_content != null) {
				JSONObject jsonObj = new JSONObject(msg_content);

				value = jsonObj.optString(key);

			}

			return value;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取内容值Map集合
	 * 
	 * @author zhangzhia 2013-9-10 上午11:26:46
	 */
	public Map<String, String> getContents() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			if (msg_content != null) {
				JSONObject contentJsonObj = new JSONObject(msg_content);
				if (contentJsonObj != null) {
					// value = contentJsonObj.optString(key);
					Iterator<?> it = contentJsonObj.keys();
					while (it.hasNext()) {
						String key = (String) it.next();
						map.put(key, contentJsonObj.getString(key));

					}
				}
			}

			return map;
		} catch (Exception e) {
			return null;
		}
	}
}
