/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:08:36
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindType;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.common.utils.DateUtils;

/**
 * 推送消息规则内容类
 * 
 * @author zhangzhia 2013-9-3 下午4:10:32
 */
public class PushMsgRule
{

	/** 唯一id */
	//public String id;
	/** 用户code */
	public String usr_code;
	/** 规则id */
	public String rule_id;
	/** 父规则id */
	//private String rule_parent_id;
	/** 规则标题 */
	//public String rule_title;
	/** 规则描述 */
	//public String rule_desc;
	/** 是否显示 falase：不显示，true：显示 */
	public boolean flag;
	/** 是否推送，falase：关闭，true：开启 */
	public boolean on_off;

	/**
	 * @param object
	 * @return
	 * @see java.lang.String#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PushMsgRule other = (PushMsgRule) obj;
		return new EqualsBuilder().append(rule_id, other.rule_id)
				.append(on_off, other.on_off).append(flag, other.flag)
				.isEquals();
	}

	public void parse(JSONObject optJSONObject)
	{
		//id = optJSONObject.optString("id");
		usr_code = optJSONObject.optString("emp_code");
		rule_id = optJSONObject.optString("rule_id");
		//rule_parent_id = optJSONObject.optString("rule_parent_id");
		//rule_title = optJSONObject.optString("rule_title");
		//rule_desc = optJSONObject.optString("rule_desc");
		flag = DataTypeConvertUtils.int2Boolean(optJSONObject.optInt("flag"));
		on_off = DataTypeConvertUtils.int2Boolean(optJSONObject
				.optInt("on_off"));
		
	}
	
	public String getUsr_code() {
		return usr_code;
	}

	public void setUsr_code(String usr_code) {
		this.usr_code = usr_code;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isOn_off() {
		return on_off;
	}

	public void setOn_off(boolean on_off) {
		this.on_off = on_off;
	}
}
