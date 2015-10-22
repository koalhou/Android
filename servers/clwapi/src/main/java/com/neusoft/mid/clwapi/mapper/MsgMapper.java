/**
 * @(#)MsgMapper.java 2013-4-11
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.entity.msg.MsgMoldInfo;
import com.neusoft.mid.clwapi.entity.msg.TerminalViBean;
import com.neusoft.mid.clwapi.process.delivermsg.ClwapiAccessCoreCommon;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-11 上午9:01:09
 */
public interface MsgMapper {
	/**
	 * 根据车辆VIN信息查询终端最新状态信息.
	 * 
	 * @param vin
	 * @return 终端最新状态信息.
	 * @throws DataAccessException
	 *             数据库访问异常.
	 */
	TerminalViBean getRealVehcileByVin(String vin) throws DataAccessException;

	/**
	 * 根据应用ID信息获取核心服务分配给CLWAPI的应用配置信息.
	 * 
	 * @param appId
	 *            应用ID信息
	 * @return 应用配置信息.
	 * @throws DataAccessException
	 *             数据库访问异常.
	 */
	ClwapiAccessCoreCommon getAppConfInfo(String appId)
			throws DataAccessException;

	/**
	 * 新增消息调度模板.
	 * 
	 * @param msgMold
	 *            消息调度模板
	 * @return
	 * @throws DataAccessException
	 *             数据库访问异常.
	 */
	void insertMsgMoldInfo(MsgMoldInfo msgMold) throws DataAccessException;

	/**
	 * 根据模板ID信息获取调度信息模板信息.
	 * 
	 * @param msgMold
	 *            消息调度模板
	 * @return 调度信息模板.
	 * @throws DataAccessException
	 *             数据库访问异常.
	 */
	MsgMoldInfo getMsgMoldInfo(MsgMoldInfo msgMold) throws DataAccessException;

	/**
	 * 更新消息调度模板.
	 * 
	 * @param msgMold
	 *            消息调度模板
	 * @return
	 * @throws DataAccessException
	 *             数据库访问异常.
	 */
	void updateUserMsgMold(MsgMoldInfo msgMold) throws DataAccessException;

	/**
	 * 消息调度模板条数.
	 * 
	 * @param tempId
	 *            调度消息模板ID
	 * @return 数目
	 * @throws DataAccessException
	 *             数据库访问异常.
	 */
	int getMsgMoldCount(@Param(value="tempId") String tempId, @Param(value="usrId") String usrId) throws DataAccessException;

	/**
	 * 根据用户ID获取消息模板
	 * 
	 * @param usrId
	 *            用户ID
	 * @return
	 * @throws DataAccessException
	 */
	List<MsgMoldInfo> getMsgMoldInfoWithUsr(String usrId)
			throws DataAccessException;

	/**
	 * 更新推送信息[告警信息]已成功被客户端接收标识.
	 * @author majch.
	 * @param msgId 推送消息ID.
	 * @param userId 用户ID.
	 * @param clientId 终端标识ID.
	 * @throws DataAccessException
	 */
	void updateAlarmPushMsgReceived(@Param(value="msgId") String msgId,
			@Param(value="userId") String userId,
			@Param(value="clientId") String clientId) throws DataAccessException;

	/**
	 * 更新推送信息[照片信息]已成功被客户端接收标识.
	 * @author majch.
	 * @param msgId 推送消息ID.
	 * @param userId 用户ID.
	 * @param clientId 终端标识ID.
	 * @throws DataAccessException
	 */
	void updatePicPushMsgReceived(@Param(value="msgId") String msgId,
			@Param(value="userId") String userId,
			@Param(value="clientId") String clientId) throws DataAccessException;

	/**
	 * 更新推送信息[新闻信息]已成功被客户端接收标识.
	 * @author majch.
	 * @param msgId 推送消息ID.
	 * @param userId 用户ID.
	 * @param clientId 终端标识ID.
	 * @throws DataAccessException
	 */
	void updateNewPushMsgReceived(@Param(value="msgId") String msgId,
			@Param(value="userId") String userId,
			@Param(value="clientId") String clientId) throws DataAccessException;
}
