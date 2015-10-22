/**
 * @(#)CancelAuthReq.java 2013-6-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.auth;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-6-25 下午2:12:08
 */
public class CancelAuthReq {

	/**
	 * 访问接口的用户名.
	 */
	@XmlElement(name = "username")
	private String userName;

	/**
	 * 访问接口的用户密码.
	 */
	@XmlElement(name = "password")
	private String password;

	/**
	 * 设置取消权限的平台管理员用户ID.
	 */
	@XmlElement(name = "operate_user_id")
	private String operateUserId;

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the operateUserId.
	 */
	public String getOperateUserId() {
		return operateUserId;
	}

	/**
	 * @param operateUserId The operateUserId to set.
	 */
	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}
}
