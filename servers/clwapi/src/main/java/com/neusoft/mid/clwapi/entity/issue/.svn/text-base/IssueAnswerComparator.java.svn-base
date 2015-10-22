/**
 * @(#)IssueAnswerComparator.java 2013-4-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.issue;

import java.util.Comparator;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-17 下午7:20:14
 */
public class IssueAnswerComparator implements Comparator<IssueAnswer> {

	/**
	 * 比较两个IssueAnswer类中issueId值
	 */
	@Override
	public int compare(IssueAnswer o1, IssueAnswer o2) {
		Long value1 = o1.getIssueId();
		Long value2 = o2.getIssueId();
		int compareResult = value1.compareTo(value2);
		if (compareResult < 0) {
			return -1;
		} else if (compareResult == 0) {
			return 0;
		} else {
			return 1;
		}
	}

}
