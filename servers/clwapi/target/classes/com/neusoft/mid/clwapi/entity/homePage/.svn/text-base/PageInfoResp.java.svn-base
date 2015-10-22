/**
 * @(#)PageInfo.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.homePage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;


/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-3-26 下午12:16:03
 */
public class PageInfoResp {
	
	/**
	 * 用户企业下的所有已注册车辆数量
	 */
	private String total = "";
	/**
	 * 当前行驶中的车辆数量
	 */
	private String running = "";
	/**
	 * 当前在线的车辆数量
	 */
	private String stop = "";
	/**
	 * 最新告警数量，该数量有可能为零
	 */
	@XmlElement(name = "warn_num")
	private String warnNum = "";
	/**
	 * 最新信息数量，该数量有可能为零
	 */
	@XmlElement(name = "msg_num")
	private String msgNum = "";
	/**
	 * 最近一天（当前时间前推24小时）内所有由手机终端拍照指令发起拍照成功的照片列表
	 * 参照com.neusoft.mid.clwapi.entity.common.PhotoInfo
	 */
	@XmlElement(name = "photo_content")
	private List<String> photoContent = new ArrayList<String>();
	/**
	 * 最新企业报告有效月
	 */
	@XmlElement(name = "ep_month")
	private String epMonth = "";
	/**
	 * 服务端年月日
	 */
	@XmlElement(name = "server_date")
	private String serverDate = "";
	/**
	 * 是否已生成企业报告,1-有   0-无;该值不能为空
	 */
	@XmlElement(name = "has_report")
	private String hasReport = "0";	

	/**
	 * @return Returns the stop.
	 */
	public String getStop() {
		return stop;
	}

	/**
	 * @param stop The stop to set.
	 */
	public void setStop(String stop) {
		this.stop = stop;
	}

	/**
	 * @return Returns the total.
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            The total to set.
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return Returns the running.
	 */
	public String getRunning() {
		return running;
	}

	/**
	 * @param running
	 *            The running to set.
	 */
	public void setRunning(String running) {
		this.running = running;
	}

	/**
	 * @return Returns the warnNum.
	 */
	public String getWarnNum() {
		return warnNum;
	}

	/**
	 * @param warnNum
	 *            The warnNum to set.
	 */
	public void setWarnNum(String warnNum) {
		this.warnNum = warnNum;
	}

	/**
	 * @return Returns the msgNum.
	 */
	public String getMsgNum() {
		return msgNum;
	}

	/**
	 * @param msgNum
	 *            The msgNum to set.
	 */
	public void setMsgNum(String msgNum) {
		this.msgNum = msgNum;
	}

	/**
	 * @return Returns the photoContent.
	 */
	public List<String> getPhotoContent() {
		return photoContent;
	}

	/**
	 * @param photoContent
	 *            The photoContent to set.
	 */
	public void setPhotoContent(List<String> photoContent) {
		this.photoContent = photoContent;
	}

	/**
	 * @return Returns the epMonth.
	 */
	public String getEpMonth() {
		return epMonth;
	}

	/**
	 * @param epMonth The epMonth to set.
	 */
	public void setEpMonth(String epMonth) {
		this.epMonth = epMonth;
	}

	/**
	 * @return Returns the serverDate.
	 */
	public String getServerDate() {
		return serverDate;
	}

	/**
	 * @param serverDate The serverDate to set.
	 */
	public void setServerDate(String serverDate) {
		this.serverDate = serverDate;
	}

	/**
	 * @return Returns the hasReport.
	 */
	public String getHasReport() {
		return hasReport;
	}

	/**
	 * @param hasReport The hasReport to set.
	 */
	public void setHasReport(String hasReport) {
		this.hasReport = hasReport;
	}
}
