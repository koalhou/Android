/**
 * @(#)ReportDesc.java 2013-4-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.statistics;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-18 下午2:16:48
 */
public class ReportDesc {

	/**
	 * 评语ID
	 */
	@XmlElement(name = "desc_id")
	private String descId;

	/**
	 * 评语内容
	 */
	@XmlElement(name = "desc_content")
	private String descContent;

	/**
	 * 更新时间
	 */
	@JsonIgnore
	private String updateTime;

	/**
	 * 是否有效
	 */
	@JsonIgnore
	private String del;

	/**
	 * @return Returns the del.
	 */
	public String getDel() {
		return del;
	}

	/**
	 * @param del
	 *            The del to set.
	 */
	public void setDel(String del) {
		this.del = del;
	}

	/**
	 * @return Returns the descId.
	 */
	public String getDescId() {
		return descId;
	}

	/**
	 * @param descId
	 *            The descId to set.
	 */
	public void setDescId(String descId) {
		this.descId = descId;
	}

	/**
	 * @return Returns the descContent.
	 */
	public String getDescContent() {
		return descContent;
	}

	/**
	 * @param descContent
	 *            The descContent to set.
	 */
	public void setDescContent(String descContent) {
		this.descContent = descContent;
	}

	/**
	 * @return Returns the updateTime.
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            The updateTime to set.
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReportDesc [descId=" + descId + ", descContent=" + descContent
				+ ", updateTime=" + updateTime + "]";
	}
}
