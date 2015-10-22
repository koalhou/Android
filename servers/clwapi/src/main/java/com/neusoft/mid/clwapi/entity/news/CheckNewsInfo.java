/**
 * @(#)CheckNewsInfo.java 2013-5-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.news;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-5-6 下午5:05:47
 */
public class CheckNewsInfo {
	/**
	 * 用户ID
	 */
	private String usrId;

	/**
	 * 消息ID
	 */
	private String msgId;

	/**
	 * 消息类型 0-行业政策 1-宇通营销
	 */
	private String msgType;

	/**
	 * 查看次数
	 */
	private String count;

	/**
	 * 创建时间，第一次查看时间
	 */
	private String createTime;

	/**
	 * 更新时间，最后一次查看时间
	 */
	private String updateTime;

	/**
	 * 发布时间
	 */
	private String publishTime;

	/**
	 * @return Returns the usrId.
	 */
	public String getUsrId() {
		return usrId;
	}

	/**
	 * @param usrId
	 *            The usrId to set.
	 */
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	/**
	 * @return Returns the msgId.
	 */
	public String getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId
	 *            The msgId to set.
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return Returns the msgType.
	 */
	public String getMsgType() {
		return msgType;
	}

	/**
	 * @param msgType
	 *            The msgType to set.
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	/**
	 * @return Returns the count.
	 */
	public String getCount() {
		return count;
	}

	/**
	 * @param count
	 *            The count to set.
	 */
	public void setCount(String count) {
		this.count = count;
	}

	/**
	 * @return Returns the createTime.
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            The createTime to set.
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return Returns the updateTime.
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            The updateTime to set.
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return Returns the publishTime.
	 */
	public String getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime
	 *            The publishTime to set.
	 */
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CheckNewsInfo [usrId=" + usrId + ", msgId=" + msgId
				+ ", msgType=" + msgType + ", count=" + count + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", publishTime="
				+ publishTime + "]";
	}
}
