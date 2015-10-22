/**
 * @(#)SuperviseInfo.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.statistics;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午9:44:02
 */
public class SuperviseInfo {
	public SuperviseInfo(String userId) {
		this.userId = userId;
	}

	/**
	 * 监控员用户ID.
	 */
	@JsonIgnore
	private String userId;
	/**
	 * 监控员姓名.
	 */
	@XmlElement(name = "name")
	private String name;

	/**
	 * 联系电话.
	 */
	@XmlElement(name = "phone")
	private String phone;

	/**
	 * 累计在线时长
	 * 格式：小时|分钟
	 */
	@XmlElement(name = "duration")
	private String duration;

	/**
	 * 监控员详细统计信息.
	 */
	@XmlElement(name = "detail")
	private List<SuperviseDetailInfo> detail;

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the duration.
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration The duration to set.
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * @return Returns the detail.
	 */
	public List<SuperviseDetailInfo> getDetail() {
		return detail;
	}

	/**
	 * @param detail The detail to set.
	 */
	public void setDetail(List<SuperviseDetailInfo> detail) {
		this.detail = detail;
	}

	/**
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
