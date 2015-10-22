/**
 * @(#)LoginReq.java 2013-3-31
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.oauth;

import javax.xml.bind.annotation.XmlElement;


/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-31 下午2:52:23
 */
public class LoginReq {

	/**
	 * 授权类型.
	 */
	@XmlElement(name = "grant_type")
	private String grantType;

	/**
	 * 登录用户名.
	 */
	@XmlElement(name = "username")
	private String userName;

	/**
	 * MD5加密的登录用户密码.
	 */
	@XmlElement(name = "password")
	private String password;

	/**
	 * 机构编码.
	 */
	@XmlElement(name = "en_id")
	private String enterpriseId;

	/**
	 * 客户端软件版本信息.
	 */
	@XmlElement(name = "version")
	private String version;

	/**
	 * Modified by houjh 2014-02-25
	 * 兼容IOS系统
	 */
	@XmlElement(name = "ios_flag")
	private String ios_flag;
	
	
	public String getIos_flag() {
		return ios_flag;
	}

	public void setIos_flag(String ios_flag) {
		this.ios_flag = ios_flag;
	}

	/**
	 * @return Returns the grantType.
	 */
	public String getGrantType() {
		return grantType;
	}

	/**
	 * @param grantType The grantType to set.
	 */
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

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
	 * @return Returns the enterpriseId.
	 */
	public String getEnterpriseId() {
		return enterpriseId;
	}

	/**
	 * @param enterpriseId The enterpriseId to set.
	 */
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	/**
	 * @return Returns the version.
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version The version to set.
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}
