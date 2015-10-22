/**
 * @(#)PushRuleServiceImpl.java 2013-4-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.pushRule;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.HttpConstant;
import com.neusoft.mid.clwapi.entity.pushRule.PushRuleInfo;
import com.neusoft.mid.clwapi.entity.pushRule.PushRuleResp;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.OauthMapper;
import com.neusoft.mid.clwapi.mapper.PushRuleMapper;
import com.neusoft.mid.clwapi.tools.BeanUtil;
import com.neusoft.mid.clwapi.tools.JacksonUtils;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-8 上午9:43:48
 */
public class PushRuleServiceImpl implements PushRuleService {

	@Context
	private MessageContext context;

	@Autowired
	private PushRuleMapper iPushRuleMapper;

	@Autowired
	private OauthMapper oauthMapper;

	private Logger logger = LoggerFactory.getLogger(PushRuleServiceImpl.class);

	/**
	 * 获取个人推送规则服务
	 * 
	 * @param token
	 *            用户令牌
	 * @param eTag
	 *            个人推送规则标记
	 * @return 最新个人推送规则标记
	 */
	@Override
	public Object getRule(String token, String eTag) {
		// 验证终端参数标志
		boolean checkEtag = true;
		// 获取用户ID
		String usrId = context.getHttpHeaders().getHeaderString("usr_id");
		String enId = context.getHttpHeaders().getHeaderString("enterprise_id");
		String plaETag = context.getHttpHeaders().getHeaderString("rule_ETag");
		logger.info("处理终端要求获取用户[" + usrId + "]的推送规则请求");
		logger.info("终端持有的ETag:" + eTag);
		logger.info("平台持有的ETag:" + plaETag);

		if (eTag == null) {
			logger.error("If-None-Match值为空");
			throw new ApplicationException(ErrorConstant.ERROR10001, Response.Status.BAD_REQUEST);
		}

		// 终端持有的时间
		Date dateTer = null;
		// 平台持有的时间
		Date datePla = null;

		// 强制更新
		if (!eTag.equals("0")) {
			try {
				dateTer = BeanUtil.checkTimeForm(eTag, HttpConstant.TIME_FORMAT);
			} catch (ParseException e) {
				logger.error("终端上传的参数非法" + e.getMessage());
				throw new ApplicationException(ErrorConstant.ERROR10002, Response.Status.BAD_REQUEST);
			}
			// 转换平台标志时间
			try {
				datePla = BeanUtil.checkTimeForm(plaETag, HttpConstant.TIME_FORMAT);
			} catch (ParseException e) {
				logger.error("转换平台标记时间时发生异常" + e.getMessage());
				throw new ApplicationException(ErrorConstant.ERROR90000,Response.Status.INTERNAL_SERVER_ERROR);
			}
		} else {
			// 不需要验证终端上传的标志
			checkEtag = false;
		}

		logger.info("获取用户推送规则列表");
		// 获取用户的所有推送规则
		List<PushRuleInfo> list = iPushRuleMapper.getPushRules(usrId, enId);
		logger.info("成功获取用户推送规则列表");
		logger.debug("list = " + (list == null ? "NULL" : list.size()));

		// 如果用户个人表内无推送规则，则此用户为新建用户
		if (list == null || list.size() == 0) {

			logger.info("用户推送规则列表为空，开始提取用户所在组织下推送规则列表");
			// 用户所在企业的默认推送规则
			List<PushRuleInfo> enList = iPushRuleMapper.getEnterpriseRule(context.getHttpHeaders().getHeaderString("enterprise_id"));
			// 如果企业未设定过初始推送规则
			if (enList == null || enList.size() == 0) {
				logger.error("用户所属企业下推送规则为空");
				throw new ApplicationException(ErrorConstant.ERROR10104,Response.Status.NOT_FOUND);
			}
			logger.info("成功获得用户所在组织下推送规则列表");

			// 获取UUID
			String[] uuids = BeanUtil.getUUIDs(enList.size());

			// 将企业的初始推送规则插入用户个人设置表内
			Iterator<PushRuleInfo> it = enList.iterator();
			logger.info("执行批量导入用户个人推送列表");
			int i = 0;
			// 临时存储容器
			List<PushRuleInfo> temp = new ArrayList<PushRuleInfo>();
			while (it.hasNext()) {
				PushRuleInfo iPushRuleInfo = it.next();
				// 存入用户信息
				iPushRuleInfo.setUsrId(usrId);
				iPushRuleInfo.setRuleId(uuids[i++]);
				temp.add(iPushRuleInfo);
			}

			logger.debug("插入数据信息:");
			if (logger.isDebugEnabled()) {
				it = temp.iterator();
				while (it.hasNext()) {
					PushRuleInfo iPushRuleInfo = it.next();
					logger.debug(iPushRuleInfo.toString());
				}
			}

			// 执行批量插入语句
			iPushRuleMapper.insertPersonalRule(temp);

			// 重新获取用户推送规则
			list = iPushRuleMapper.getPushRules(usrId, enId);
			if (list == null || list.size() == 0) {
				logger.error("导入失败，获取个人推送规则列表为空");
				throw new ApplicationException(ErrorConstant.ERROR10010,Response.Status.NOT_FOUND);
			}
			logger.info("导入成功");
			plaETag = list.get(0).getOperateTime();
			logger.debug("数据最新标记信息为：" + plaETag);
			// 更新平台个人推送标记
			upUserETag(token, usrId, plaETag);
			logger.info("成功更新平台标志信息");
		} else {
			logger.debug("数据：");
			if (logger.isDebugEnabled()) {
				Iterator<PushRuleInfo> it = list.iterator();
				int i = 0;
				while (it.hasNext()) {
					PushRuleInfo iPushRuleInfo = it.next();
					logger.debug(++i + iPushRuleInfo.toString());
				}
			}
			// 数据中最新的标记信息
			String e = list.get(0).getOperateTime();
			logger.info("成功从数据中获取最新标志信息");
			logger.debug("个人推送规则数据最新标志信息为：" + e);
			logger.debug("平台最新标志信息为：" + plaETag);
			// 平台标志异常，执行更正策略
			if (!StringUtils.equals(e, plaETag)) {
				logger.info("平台用户表中的标记异常，执行更正策略");
				// 不相同更新用户表中的ETag值
				upUserETag(token, usrId, e);
				plaETag = e;
			}
		}

		logger.info("成功获取平台最新标记");

		// 判断是否是无内容
		if ((list == null || list.size() == 0) && !plaETag.equals("0")) {
			logger.info("用户模板为空，不需更新");
			return Response.noContent().build();
		} else if (checkEtag && dateTer.compareTo(datePla) == 0) {
			// 如果终端时间与平台时间相等则无数据返回
			logger.info("终端数据版本已经是最新的，不需更新");
			return Response.notModified().build();
		} else if (checkEtag && dateTer.compareTo(datePla) > 0) {
			logger.error("终端数据版本大于平台数据版本");
			throw new ApplicationException(ErrorConstant.ERROR10103,
					Response.Status.BAD_REQUEST);
		}

		// 拼装返回参数
		PushRuleResp iPushRuleResp = new PushRuleResp();
		iPushRuleResp.seteTag(plaETag);
		iPushRuleResp.setRuleContent(list);
		return JacksonUtils.toJsonRuntimeException(iPushRuleResp);
	}

	/**
	 * 更新用户个人推送规则标记信息.
	 * 
	 * @param token
	 *            用户当前使用的令牌信息. edit by majch,使用mybatis注解传参.
	 * @param userID
	 *            用户ID
	 * @param ETag
	 *            个人推送规则标记信息.
	 */
	private void upUserETag(String token, String userID, String ETag) {
		oauthMapper.updateUserRuleEtag(token, ETag, userID);
	}

	/**
	 * 设置个人推送规则服务
	 * 
	 * @param token
	 *            用户令牌
	 * @param content
	 *            个人推送规则消息体
	 * @return 最新个人推送规则标记
	 */
	@Override
	public String setRule(String token, String content) {
		logger.info("处理设置个人推送规则请求");
		@SuppressWarnings("unchecked")
		Map<String, List<Map<String, String>>> tm = (Map<String, List<Map<String, String>>>) JacksonUtils
				.jsonToMapRuntimeException(content);
		List<Map<String, String>> list = tm.get("rule_content");
		// 获取用户ID
		String usrId = context.getHttpHeaders().getHeaderString("usr_id");
		String enId = context.getHttpHeaders().getHeaderString("enterprise_id");
		String plaETag = context.getHttpHeaders().getHeaderString("rule_ETag");

		// 验证参数
		if (list == null || list.size() == 0) {
			logger.error("终端上传参数不符合要求");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}
		Iterator<Map<String, String>> it = list.iterator();
		List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
		while (it.hasNext()) {
			Map<String, String> map = it.next();
			if (StringUtils.isEmpty(map.get("rule_id"))
					|| StringUtils.isEmpty(map.get("on_off"))
					|| StringUtils.isEmpty(map.get("flag"))) {
				logger.error("终端上传参数不符合要求");
				throw new ApplicationException(ErrorConstant.ERROR10001,
						Response.Status.BAD_REQUEST);
			} else if (!StringUtils.equals(map.get("on_off"), "0")
					&& !StringUtils.equals(map.get("on_off"), "1")) {
				logger.error("终端上传参数不符合要求，on_off值只能是数字0与1");
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			} else if (!StringUtils.equals(map.get("flag"), "0")
					&& !StringUtils.equals(map.get("flag"), "1")) {
				logger.error("终端上传参数不符合要求，flag值只能是数字0与1");
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}
			Map<String, String> m = new HashMap<String, String>(map);
			m.put("usrId", usrId);
			temp.add(m);
		}
		list = temp;
		logger.info("参数验证通过，开始更新用户推送规则记录");
		// 更新用户推送规则
		iPushRuleMapper.updatePersonalRule(list);
		logger.info("成功更新用户推送规则记录");

		// 查找用户推送规则记录
		List<PushRuleInfo> t = iPushRuleMapper.getPushRules(usrId, enId);
		logger.info("成功获得用户推送规则记录");

		// 提取最新标记
		String newEtag = t.get(0).getOperateTime();
		logger.debug("最新标记[" + newEtag + "]平台标记[" + plaETag + "]");

		logger.info("开始更新平台标记");
		// 更新标记
		upUserETag(token, usrId, newEtag);
		logger.info("平台标记成功更新为：" + newEtag);

		// 拼装返回参数
		Map<String, String> respMap = new HashMap<String, String>();
		respMap.put("ETag", newEtag);
		return JacksonUtils.toJsonRuntimeException(respMap);
	}
}
