package com.neusoft.mid.clwapi.service.pushNotice;

import java.util.Set;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.HttpConstant;
import com.neusoft.mid.clwapi.common.UserInfoKey;
import com.neusoft.mid.clwapi.entity.pushnotice.PushNoticeRequ;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.memcached.MemcacheCache;
import com.neusoft.mid.clwapi.memcached.MemcacheCacheManager;
import com.neusoft.mid.clwapi.tools.JacksonUtils;

public class PushNoticeServiceImpl implements PushNoticeService{
	@Context
	private MessageContext context;
	@Autowired
	private MemcacheCacheManager memcacheCacheManager;
	
	private Logger logger = LoggerFactory.getLogger(PushNoticeServiceImpl.class);
	/**
	 * 更新照片的标记信息
	 * 
	 * @param token
	 *            用户token
	 * @param body
	 *            消息体
	 * @return
	 */
	@Override
	public String pushNotice(String token, String body) {
		MemcacheCache cache = (MemcacheCache)memcacheCacheManager.getCache("CLW_API_CACHE");
		String userId = context.getHttpHeaders().getHeaderString(UserInfoKey.USR_ID);
		logger.info("num start==="+cache.get(userId+"_PUSHNOTICE_NUM").get());
		logger.info("处理推送通知请求");
		// 获取参数
		PushNoticeRequ iPushNoticeRequ = JacksonUtils.fromJsonRuntimeException(body,PushNoticeRequ.class);
		// 去除前后空格
		iPushNoticeRequ.setPushNum(StringUtils.strip(iPushNoticeRequ.getPushNum()));
		// 验证参数
		if (StringUtils.isEmpty(iPushNoticeRequ.getPushNum())) {
			logger.error("终端请求参数非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,Response.Status.BAD_REQUEST);
		}
		cache.put(userId+"_PUSHNOTICE_NUM", iPushNoticeRequ.getPushNum());
		logger.info("num end==="+cache.get(userId+"_PUSHNOTICE_NUM").get());
		return HttpConstant.RESP_200;
	}
	
}
