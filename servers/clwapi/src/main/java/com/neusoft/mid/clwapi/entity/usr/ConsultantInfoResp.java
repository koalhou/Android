/**
 * @(#)ConsultantInfoResp.java 2013-5-30
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.usr;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-5-30 上午10:08:28
 */
public class ConsultantInfoResp {

	/**
	 * 问卷消息.
	 */
	@XmlElement(name = "result")
	private List<ConsultantInfo> consultantList;

	/**
	 * @return Returns the consultantList.
	 */
	public List<ConsultantInfo> getConsultantList() {
		return consultantList;
	}

	/**
	 * @param consultantList The consultantList to set.
	 */
	public void setConsultantList(List<ConsultantInfo> consultantList) {
		this.consultantList = consultantList;
	}

}
