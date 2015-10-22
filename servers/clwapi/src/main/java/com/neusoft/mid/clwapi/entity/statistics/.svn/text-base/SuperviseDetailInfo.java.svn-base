/**
 * @(#)SuperviseDetailInfo.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.statistics;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午9:45:53
 */
public class SuperviseDetailInfo {

	/**
	 * 监控时段信息
	 * 0:0时至9时
	 * 1:9时至15时
	 * 2:15时至0时
	 */
	@XmlElement(name = "time")
	private int time;

	/**
	 * 操作次数
	 */
	@XmlElement(name = "operate")
	private int operate;

	/**
	 * 评价信息
	 * 1：正常；0：异常
	 */
	@XmlElement(name = "evaluate")
	private int evaluate;

	/**
	 * @return Returns the time.
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time The time to set.
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return Returns the operate.
	 */
	public int getOperate() {
		return operate;
	}

	/**
	 * @param operate The operate to set.
	 */
	public void setOperate(int operate) {
		this.operate = operate;
	}

	/**
	 * @return Returns the evaluate.
	 */
	public int getEvaluate() {
		return evaluate;
	}

	/**
	 * @param evaluate The evaluate to set.
	 */
	public void setEvaluate(int evaluate) {
		this.evaluate = evaluate;
	}

}
