/**
 * @(#)PhotoServiceImpl.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.photo;

import java.text.ParseException;
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
import com.neusoft.mid.clwapi.common.UserInfoKey;
import com.neusoft.mid.clwapi.entity.photo.PhotoInfo;
import com.neusoft.mid.clwapi.entity.photo.PhotoRequ;
import com.neusoft.mid.clwapi.entity.photo.PhotoResp;
import com.neusoft.mid.clwapi.entity.photo.PhotoSignInfo;
import com.neusoft.mid.clwapi.entity.photo.PhotoSignRequ;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.PhotoMapper;
import com.neusoft.mid.clwapi.process.delivermsg.DeliverMsgResult;
import com.neusoft.mid.clwapi.process.delivermsg.SendDeliverMsgService;
import com.neusoft.mid.clwapi.tools.BeanUtil;
import com.neusoft.mid.clwapi.tools.JacksonUtils;
import com.neusoft.mid.clwapi.tools.TimeUtil;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-9 下午3:19:49
 */
public class PhotoServiceImpl implements PhotoService {
	@Autowired
	private PhotoMapper iPhotoMapper;

	@Autowired
	private SendDeliverMsgService iSendDeliverMsgService;

	@Context
	private MessageContext context;

	private Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);

	/**
	 * 批量获取照片API
	 * 
	 * @param token
	 *            用户token
	 * @param body
	 *            消息体
	 * @return
	 */
	@Override
	public Object getPhotoList(String token, String body) {
		logger.info("处理批量获取照片请求");

		String organizationId = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ORGANIZATION_ID);

		// 获取参数
		PhotoRequ iPhotoRequ = JacksonUtils.fromJsonRuntimeException(body,
				PhotoRequ.class);
		// 去除前后空格
		iPhotoRequ.setEndTime(StringUtils.strip(iPhotoRequ.getEndTime()));
		iPhotoRequ.setStartTime(StringUtils.strip(iPhotoRequ.getStartTime()));
		iPhotoRequ.setNum(StringUtils.strip(iPhotoRequ.getNum()));
		iPhotoRequ.setType(StringUtils.strip(iPhotoRequ.getType()));
		iPhotoRequ.setPage(StringUtils.strip(iPhotoRequ.getPage()));
		iPhotoRequ.setOrganizationId(organizationId);
		
		// 验证参数
		logger.info("验证终端请求参数");
		if (StringUtils.isEmpty(iPhotoRequ.getType())
				|| StringUtils.isEmpty(iPhotoRequ.getNum())
				|| StringUtils.isEmpty(iPhotoRequ.getPage())) {
			logger.error("终端请求参数非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		} else if (StringUtils.isEmpty(iPhotoRequ.getStartTime())
				|| StringUtils.isEmpty(iPhotoRequ.getEndTime())) {
			logger.error("终端请求参数非法，开始时间与结束时间不能为空");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		} else if (!iPhotoRequ.getType().equals(
				HttpConstant.PHOTOPORT_TYPE_ORDER)
				&& !iPhotoRequ.getType()
						.equals(HttpConstant.PHOTOPORT_TYPE_ALL)) {
			logger.error("终端请求参数非法，type值只能是1、2");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		} else if (!StringUtils.isNumeric(iPhotoRequ.getNum())) {
			logger.error("终端请求参数非法，num值只能是数字");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		} else if (!StringUtils.isNumeric(iPhotoRequ.getPage())) {
			logger.error("终端请求参数非法，page值只能是数字");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		// 验证开始时间与结束时间是否为有效时间
		if (!HttpConstant.TIME_ZERO.equals(iPhotoRequ.getStartTime())) {
			try {
				TimeUtil.parseStringToDate(iPhotoRequ.getStartTime(),
						HttpConstant.TIME_FORMAT);
			} catch (ParseException e) {
				logger.error("终端上传参数非法，开始时间不符合协议格式", e);
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}
		} else {
			iPhotoRequ.setStartTime(null);
		}
		if (!HttpConstant.TIME_ZERO.equals(iPhotoRequ.getEndTime())) {
			try {
				TimeUtil.parseStringToDate(iPhotoRequ.getEndTime(),
						HttpConstant.TIME_FORMAT);
			} catch (ParseException e) {
				logger.error("终端上传参数非法，结束时间不符合协议格式", e);
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}
		} else {
			iPhotoRequ.setEndTime(null);
		}

		// 提取页数与每页显示数量
		int page = Integer.valueOf(iPhotoRequ.getPage());
		int num = Integer.valueOf(iPhotoRequ.getNum());

		// 验证页数是否符合要求
		if (page <= 0) {
			logger.error("终端请求参数非法，page值只能大于0");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		logger.info("终端请求参数符合要求");

		// 设置过滤条件
		iPhotoRequ.setVin(StringUtils.isEmpty(iPhotoRequ.getVin()) ? null
				: iPhotoRequ.getVin());
		iPhotoRequ.setOrgaId(StringUtils.isEmpty(iPhotoRequ.getOrgaId()) ? null
				: iPhotoRequ.getOrgaId());

		// 拼装查询需要的参数
		// 拼装用户ID与EN_ID
		iPhotoRequ.setUsrId(context.getHttpHeaders().getHeaderString(
				UserInfoKey.USR_ID));
		iPhotoRequ.setEnId(context.getHttpHeaders().getHeaderString(
				UserInfoKey.ENTERPRISE_ID));

		// 页数转换为行数
		iPhotoRequ.setStartRow(String.valueOf(page * num - num));
		iPhotoRequ.setEndRow(String.valueOf(page * num));
		// 如果查找类型为2时将type置空，以达到动态生成sql的目的
		iPhotoRequ.setType(iPhotoRequ.getType().equals(
				HttpConstant.PHOTOPORT_TYPE_ALL) ? null : iPhotoRequ.getType());

		logger.info("开始获取照片列表");
		logger.debug("请求参数：");
		logger.debug(iPhotoRequ.toString());

		// 获取列表
		List<PhotoInfo> imgs = iPhotoMapper.getPhotoList(iPhotoRequ);
		logger.info("成功获取照片列表");
		logger.debug("imgs=" + (imgs == null ? "NULL" : imgs.size()));

		if (imgs == null || imgs.size() == 0) {
			logger.info("未找到满足条件的照片");
			return Response.noContent().build();
		}

		logger.debug("获取到的照片列表为：");
		if (logger.isDebugEnabled()) {
			Iterator<PhotoInfo> it = imgs.iterator();
			while (it.hasNext()) {
				PhotoInfo temp = it.next();
				logger.debug(temp.toString());
			}
		}

		// 拼装返回体
		PhotoResp iPhotoResp = new PhotoResp();
		iPhotoResp.setImgs(imgs);
		logger.info("成功处理获取照片列表请求");

		return JacksonUtils.toJsonRuntimeException(iPhotoResp);
	}

	/**
	 * 获取照片标注信息
	 * 
	 * @param token
	 *            用户token
	 * @param body
	 *            消息体
	 * @return
	 */
	@Override
	public Object getSignInfo(String token, String body) {
		logger.info("处理获取照片标注信息请求");

		PhotoSignRequ iPhotoSignRequ = JacksonUtils.fromJsonRuntimeException(
				body, PhotoSignRequ.class);
		logger.info("图片ID：" + iPhotoSignRequ.getImgId());
		logger.info("更新时间：" + iPhotoSignRequ.getUpTime());
		logger.info("验证请求参数");
		// 检查参数
		if (StringUtils.isEmpty(iPhotoSignRequ.getImgId())
				|| StringUtils.isEmpty(iPhotoSignRequ.getUpTime())) {
			logger.error("终端请求参数非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

		// 修改为0的参数为空
		if (iPhotoSignRequ.getUpTime().equals(HttpConstant.TIME_ZERO)) {
			iPhotoSignRequ.setUpTime(null);
		} else {
			// 检查时间格式
			try {
				BeanUtil.checkTimeForm(iPhotoSignRequ.getUpTime(),
						HttpConstant.TIME_FORMAT);
			} catch (ParseException e) {
				logger.error("终端上传时间格式异常" + e.getMessage());
				throw new ApplicationException(ErrorConstant.ERROR10002,
						Response.Status.BAD_REQUEST);
			}
		}

		logger.info("终端请求参数合法");
		// 获取标记信息
		PhotoSignInfo iPhotoSignInfo = iPhotoMapper
				.getPhotoSign(iPhotoSignRequ);

		// 如果未找到或标记信息为空
		if (iPhotoSignInfo == null || iPhotoSignInfo.getInfo() == null) {
			logger.info("未找到请求的照片标记信息");
			return Response.noContent().build();
		}

		logger.info("成功从数据库中获取照片标记信息");
		logger.debug(iPhotoSignInfo.toString());

		logger.info("成功处理获取照片标注信息请求");
		return JacksonUtils.toJsonRuntimeException(iPhotoSignInfo);
	}

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
	public String signPhoto(String token, String body) {
		logger.info("处理标记照片请求");
		boolean informFlag = false;

		logger.info("验证终端上传参数");
		PhotoSignRequ iPhotoSignRequ = JacksonUtils.fromJsonRuntimeException(
				body, PhotoSignRequ.class);
		logger.info("图片ID：" + iPhotoSignRequ.getImgId());
		logger.info("FLAG: " + iPhotoSignRequ.getFlag());
		logger.info("标记信息为：" + iPhotoSignRequ.getInfo());

		// 检查参数
		if (StringUtils.isEmpty(iPhotoSignRequ.getImgId())
				|| StringUtils.isEmpty(iPhotoSignRequ.getFlag())
				|| StringUtils.isEmpty(iPhotoSignRequ.getVin())) {
			logger.error("终端请求参数非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

		if (iPhotoSignRequ.getFlag().equals(
				HttpConstant.SINGPHOTOPORT_FLAG_OVERLOAD)) {
			logger.info("本次请求为将照片标记为超载");
			informFlag = true;
		} else if (iPhotoSignRequ.getFlag().equals(
				HttpConstant.SINGPHOTOPORT_FLAG_NORMAL)) {
			logger.info("本次请求为普通标记");
			iPhotoSignRequ.setFlag(null);
		} else {
			logger.error("终端请求参数非法，flag值只能为1、2");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}
		logger.info("终端上传参数合法");
		// 设置用户ID
		iPhotoSignRequ.setUsrId(context.getHttpHeaders().getHeaderString(
				UserInfoKey.USR_ID));
		PhotoSignInfo iPhotoSignInfo = null;
		if (informFlag) {
			// 获取照片标记信息
			iPhotoSignInfo = iPhotoMapper.getPhotoSign(iPhotoSignRequ);
		}
		// 不可能出现的情况，严谨考虑
		if (informFlag && iPhotoSignInfo == null) {
			logger.error("库中不存在照片ID：" + iPhotoSignRequ.getImgId() + "的相关信息");
			throw new ApplicationException(ErrorConstant.ERROR10010,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		// 判断照片是否被标记过超载
		if (informFlag && iPhotoSignInfo.getFlag().equals("1")) {
			logger.info("照片ID:" + iPhotoSignRequ.getImgId()
					+ "已经被标记为超载,本次标记失败只更新备注信息");
			informFlag = false;
		}
		// 通知核心
		if (informFlag) {

			logger.info("通知核心生成告警信息");
			DeliverMsgResult iDeliverMsgResult = iSendDeliverMsgService
					.sendMarkAlarmToCoreService(iPhotoSignRequ.getVin(),
							HttpConstant.TERMINAL_ALARM_TYPE, iPhotoSignRequ
									.getImgId(), context.getHttpHeaders()
									.getHeaderString(UserInfoKey.ENTERPRISE_ID));
			if (null != iDeliverMsgResult
					&& iDeliverMsgResult.getCode()
							.equals(HttpConstant.NO_ERROR)) {
				logger.info("照片标记为超载与核心服务交易成功");
			} else {
				logger.info("照片标记为超载与核心服务交易失败");
				logger.error("由于通知核心生成告警信息失败，本次将照片标记为超载不成功");
				throw new ApplicationException(ErrorConstant.ERROR10106,
						Response.Status.INTERNAL_SERVER_ERROR);
			}
		} else {
			logger.info("本次标注为普通标注，不通知核心生成告警");
		}

		logger.info("开始更新标记信息");
		logger.debug(iPhotoSignRequ.toString());
		// 更新标记信息
		iPhotoMapper.signPhoto(iPhotoSignRequ);
		logger.info("成功更新标记信息");
		logger.info("获取照片更新时间");
		String upTime = iPhotoMapper.getUpDate(iPhotoSignRequ);
		logger.info("成功获取照片更新时间");
		logger.debug(upTime);

		// 返回更新时间
		Map<String, String> response = new HashMap<String, String>();
		response.put(HttpConstant.UP_TIME, upTime);

		logger.info("成功处理标记照片请求");
		return JacksonUtils.toJsonRuntimeException(response);
	}
}
