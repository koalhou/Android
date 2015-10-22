/**
 * @(#)envServiceImpl.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.env;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.HttpConstant;
import com.neusoft.mid.clwapi.common.UserInfoKey;
import com.neusoft.mid.clwapi.entity.env.EnvInfo;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.EnvReportMapper;
import com.neusoft.mid.clwapi.service.common.UsrOauthService;
import com.neusoft.mid.clwapi.tools.BeanUtil;
import com.neusoft.mid.clwapi.tools.JacksonUtils;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-3-26 上午11:03:00
 */
public class EnvServiceImpl implements EnvService {

	@Context
	private MessageContext context;

	@Autowired
	private UsrOauthService usrOauthService;

	@Autowired
	private EnvReportMapper envReportMapper;

	private Logger logger = LoggerFactory.getLogger(EnvServiceImpl.class);

	@Override
	public String truntime(String token, String info) {
		String usrId = context.getHttpHeaders().getHeaderString(
				UserInfoKey.USR_ID);

		logger.info("得到的用户ID：" + usrId);
		logger.info("开始解析终端上传的环境信息");

		EnvInfo iEnvInfo = new EnvInfo();
		try {
			iEnvInfo = JacksonUtils.fromJson(info, EnvInfo.class);
			iEnvInfo.setUserId(usrId);
			BeanUtil.checkObjectLegal(iEnvInfo);
		} catch (Exception e) {
			logger.error("本次上传信息不符合要求" + e.getMessage());
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		logger.info("终端上报时间:" + iEnvInfo.getTime());
		logger.info("客户端唯一标识:" + iEnvInfo.getImei());
		logger.info("终端手机号码:" + iEnvInfo.getMsisdn());
		logger.info("终端机型:" + iEnvInfo.getModeNumb());
		logger.info("终端品牌:" + iEnvInfo.getBrand());
		logger.info("终端屏幕分辨率:" + iEnvInfo.getResoRati());
		logger.info("运营商类别:"
				+ iEnvInfo.getTypeMobile()
				+ "(0-wifi\t1-中国移动2G\t2-中国移动3G\t3-中国电信2G\t4-中国电信3G\t5-中国联通2G\t6-中国联通3G)");
		logger.info("操作系统版本:" + iEnvInfo.getOsVers());
		logger.info("安芯移动应用软件版本:" + iEnvInfo.getSoftVers());
		logger.info("用户所在地市:" + iEnvInfo.getCity());
		logger.info("用户所在省会:" + iEnvInfo.getProvince());

		// 终端信息入库
		envReportMapper.insertEnvInfo(iEnvInfo);

		logger.info("终端信息成功入库 ");

		return HttpConstant.RESP_200;
	}
}
