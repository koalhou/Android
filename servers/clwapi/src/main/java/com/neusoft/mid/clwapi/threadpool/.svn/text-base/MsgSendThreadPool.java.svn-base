/**
 * @(#)MsgSendThreadPool.java 2013-5-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.entity.msg.CoreMsgInfo;
import com.neusoft.mid.clwapi.entity.oauth.MobileUsrAllInfo;
import com.neusoft.mid.clwapi.mapper.MsgMapper;
import com.neusoft.mid.clwapi.process.delivermsg.SendDeliverMsgService;
import com.neusoft.mid.clwapi.threadwork.MtMsgSendWorkThread;
import com.neusoft.mid.clwapi.threadwork.PhotoSendWorkThread;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-5-9 上午9:57:37
 */
@Service("msgSendThreadPool")
public class MsgSendThreadPool {
	private ThreadPoolExecutor threadPool;
	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	public MsgSendThreadPool() {
		threadPool = initThreadPool();
	}

	private ThreadPoolExecutor initThreadPool() {

		int corePoolSize = Runtime.getRuntime().availableProcessors() + 1;
		int maximumPoolSize = corePoolSize + 4;
		int keepAliveTime = 1;
		int workQueueSize = corePoolSize * 2;
		threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
				keepAliveTime, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(workQueueSize),
				new ThreadPoolExecutor.CallerRunsPolicy());
		return threadPool;
	}

	/**
	 * 下发调度信息.
	 * 
	 * @param msgInfo
	 * @param msgMapper
	 * @param sendDeliverMsgService
	 */
	public void executeSendMtMsgThread(CoreMsgInfo msgInfo,
			MsgMapper msgMapper, SendDeliverMsgService sendDeliverMsgService) {

		logger.info("核心线程数:" + threadPool.getCorePoolSize() + ",最大线程数:"
				+ threadPool.getMaximumPoolSize() + ",线程空闲时间为:"
				+ threadPool.getKeepAliveTime(TimeUnit.SECONDS) + "秒");
		threadPool.execute(new MtMsgSendWorkThread(msgInfo, msgMapper,
				sendDeliverMsgService));
	}

	/**
	 * 下发拍照命令.
	 * 
	 * @param msgInfo
	 * @param msgMapper
	 * @param sendDeliverMsgService
	 */
	public void executeSendPhotoThread(CoreMsgInfo msgInfo,
			MobileUsrAllInfo usrInfo, MsgMapper msgMapper,
			SendDeliverMsgService sendDeliverMsgService) {

		logger.info("核心线程数:" + threadPool.getCorePoolSize() + ",最大线程数:"
				+ threadPool.getMaximumPoolSize() + ",线程空闲时间为:"
				+ threadPool.getKeepAliveTime(TimeUnit.SECONDS) + "秒");
		threadPool.execute(new PhotoSendWorkThread(msgInfo, usrInfo, msgMapper,
				sendDeliverMsgService));
	}

}
