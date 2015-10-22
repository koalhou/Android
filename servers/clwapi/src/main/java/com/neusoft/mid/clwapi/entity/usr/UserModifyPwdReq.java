/**
 * @(#)UserModifyPwdReq.java 2013-4-13
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.usr;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-13 下午2:05:43
 */
public class UserModifyPwdReq {

	/**
	 * 功能模块ID.
	 */
	@XmlElement(name = "old_pwd")
	private String oldPwd;


	/**
	 * 功能模块ID.
	 */
	@XmlElement(name = "new_pwd")
	private String newPwd;


	/**
	 * @return Returns the oldPwd.
	 */
	public String getOldPwd() {
		return oldPwd;
	}


	/**
	 * @param oldPwd The oldPwd to set.
	 */
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}


	/**
	 * @return Returns the newPwd.
	 */
	public String getNewPwd() {
		return newPwd;
	}


	/**
	 * @param newPwd The newPwd to set.
	 */
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

}
