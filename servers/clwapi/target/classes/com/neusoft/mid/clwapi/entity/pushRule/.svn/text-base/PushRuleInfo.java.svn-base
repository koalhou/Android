/**
 * @(#)PushRule.java 2013-4-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.pushRule;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-8 上午9:47:57
 */
public class PushRuleInfo {

	/**
	 * 消息推送规则ID(对应用户个人)
	 */
	@XmlElement(name = "rule_id")
	private String ruleId = "";

	/**
	 * 消息推送规则描述
	 */
	@XmlElement(name = "rule_desc")
	private String ruleDesc = "";

	/**
	 * 是否推送：1– 开；0– 关
	 */
	@XmlElement(name = "on_off")
	private String onOff = "";

	/**
	 * 是否提示：1– 提示；0– 不提示
	 */
	private String flag = "";

	/**
	 * 用户ID
	 */
	@JsonIgnore
	private String usrId = "";

	/**
	 * 企业推送规则ID
	 */
	@XmlElement(name = "pm_rule_id")
	private String pmRuleId = "";

	/**
	 * 数据更新时间
	 */
	@JsonIgnore
	private String operateTime;

	/**
	 * @return Returns the operateTime.
	 */
	public String getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime
	 *            The operateTime to set.
	 */
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return Returns the ruleId.
	 */
	public String getRuleId() {
		return ruleId;
	}

	/**
	 * @param ruleId
	 *            The ruleId to set.
	 */
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * @return Returns the usrId.
	 */
	public String getUsrId() {
		return usrId;
	}

	/**
	 * @param usrId
	 *            The usrId to set.
	 */
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	/**
	 * @return Returns the pmRuleId.
	 */
	public String getPmRuleId() {
		return pmRuleId;
	}

	/**
	 * @param pmRuleId
	 *            The pmRuleId to set.
	 */
	public void setPmRuleId(String pmRuleId) {
		this.pmRuleId = pmRuleId;
	}

	/**
	 * @return Returns the ruleDesc.
	 */
	public String getRuleDesc() {
		return ruleDesc;
	}

	/**
	 * @param ruleDesc
	 *            The ruleDesc to set.
	 */
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	/**
	 * @return Returns the onOff.
	 */
	public String getOnOff() {
		return onOff;
	}

	/**
	 * @param onOff
	 *            The onOff to set.
	 */
	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}

	/**
	 * @return Returns the flag.
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            The flag to set.
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PushRuleInfo [ruleId=" + ruleId + ", ruleDesc=" + ruleDesc
				+ ", onOff=" + onOff + ", flag=" + flag + ", usrId=" + usrId
				+ ", pmRuleId=" + pmRuleId + ", operateTime=" + operateTime
				+ "]";
	}
}
