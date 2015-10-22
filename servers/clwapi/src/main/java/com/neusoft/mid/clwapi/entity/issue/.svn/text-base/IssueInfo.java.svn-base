/**
 * @(#)IssueInfo.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.issue;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午9:14:23
 */
public class IssueInfo {
	public IssueInfo(Long id, String content) {
		this.id = id;
		this.content = content;
	}

	/**
	 * 问卷ID.
	 */
	@XmlElement(name = "id")
	private Long id;

	/**
	 * 问卷内容.
	 */
	@XmlElement(name = "cont")
	private String content;

	/**
	 * 问卷ID.
	 */
	@XmlElement(name = "choices")
	private List<IssueChoiceInfo> choiceList;

	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return Returns the choiceList.
	 */
	public List<IssueChoiceInfo> getChoiceList() {
		return choiceList;
	}

	/**
	 * @param choiceList The choiceList to set.
	 */
	public void setChoiceList(List<IssueChoiceInfo> choiceList) {
		this.choiceList = choiceList;
	}

}
