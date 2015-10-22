/**
 * @(#)IssueAnswer.java 2013-4-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.issue;


import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-17 下午6:18:32
 */
public class IssueAnswer {

	/**
	 * 问卷ID.
	 */
	@XmlElement(name = "issue_id")
	private Long issueId;

	/**
	 * 用户所选择的问卷答案编号.
	 */
	@XmlElement(name = "choice_id")
	private Long choiceId;

	/**
	 * @return Returns the issueId.
	 */
	public Long getIssueId() {
		return issueId;
	}

	/**
	 * @param issueId The issueId to set.
	 */
	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	/**
	 * @return Returns the choiceId.
	 */
	public Long getChoiceId() {
		return choiceId;
	}

	/**
	 * @param choiceId The choiceId to set.
	 */
	public void setChoiceId(Long choiceId) {
		this.choiceId = choiceId;
	}

}
