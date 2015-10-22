/**
 * @(#)MoldStatInfo.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.statistics;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;


/**
 * @author <a href="mailto:suyingtao@neusoft.com">suyingtao</a>
 * @version $Revision 1.0 $ 2013-3-26 上午9:49:08
 */
public class MoldStatInfo {
	/**
	 * 统计维度.
	 * 01-	百公里超速里程
	 * 02-	百公里不良驾驶时长
	 * 03-	百公里油耗
	 * 04-	百公里超速车辆占比/超速车辆占比
	 * 05-	百公里不良驾驶车辆占比/不良驾驶车辆占比
	 * 06-	超油车辆占比
	 * 07-	超速
	 * 08-	超转
	 * 09-	超长怠速
	 * 10-	怠速空调
	 * 11-	超经济区运行
	 */
	@XmlElement(name = "st_type")
	private String stMold;
	
	/**
	 * 行业平均水平.
	 */
	@XmlElement(name = "st_avg")
	private String stAvg;
	
	/**
	 * 多个月份统计信息.
	 */
	@XmlElement(name = "st_month_info")
	private List<MonthStatInfo> stMonthInfos;
	
	/**
	 * 评语ID.
	 */
	@XmlElement(name = "st_desc_id")
	private String stDesc;
	
	/**
	 * 比上月.
	 */
	@XmlElement(name = "st_cmp_prior")
	private String stCmpPrior;
	
	/**
	 * 比行业平均.
	 */
	@XmlElement(name = "st_cmp_avg")
	private String stCmpAvg;

	/**
	 * @return Returns the stMold.
	 */
	public String getStMold() {
		return stMold;
	}

	/**
	 * @param stMold The stMold to set.
	 */
	public void setStMold(String stMold) {
		this.stMold = stMold;
	}

	/**
	 * @return Returns the stAvg.
	 */
	public String getStAvg() {
		return stAvg;
	}

	/**
	 * @param stAvg The stAvg to set.
	 */
	public void setStAvg(String stAvg) {
		this.stAvg = stAvg;
	}

	/**
	 * @return Returns the stMonthInfos.
	 */
	public List<MonthStatInfo> getStMonthInfos() {
		return stMonthInfos;
	}

	/**
	 * @param stMonthInfos The stMonthInfos to set.
	 */
	public void setStMonthInfos(List<MonthStatInfo> stMonthInfos) {
		this.stMonthInfos = stMonthInfos;
	}

	/**
	 * @return Returns the stDesc.
	 */
	public String getStDesc() {
		return stDesc;
	}

	/**
	 * @param stDesc The stDesc to set.
	 */
	public void setStDesc(String stDesc) {
		this.stDesc = stDesc;
	}

	/**
	 * @return Returns the stCmpPrior.
	 */
	public String getStCmpPrior() {
		return stCmpPrior;
	}

	/**
	 * @param stCmpPrior The stCmpPrior to set.
	 */
	public void setStCmpPrior(String stCmpPrior) {
		this.stCmpPrior = stCmpPrior;
	}

	/**
	 * @return Returns the stCmpAvg.
	 */
	public String getStCmpAvg() {
		return stCmpAvg;
	}

	/**
	 * @param stCmpAvg The stCmpAvg to set.
	 */
	public void setStCmpAvg(String stCmpAvg) {
		this.stCmpAvg = stCmpAvg;
	}
}
