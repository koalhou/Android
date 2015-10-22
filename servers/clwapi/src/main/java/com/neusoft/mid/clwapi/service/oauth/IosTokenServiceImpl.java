package com.neusoft.mid.clwapi.service.oauth;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.common.UserInfoKey;
import com.neusoft.mid.clwapi.entity.oauth.UsrInfo;
import com.neusoft.mid.clwapi.mapper.OauthMapper;
import com.neusoft.mid.clwapi.tools.CheckRequestParam;

public class IosTokenServiceImpl implements IosTokenService {
	private static Logger logger = LoggerFactory.getLogger(ModCommonConstant.LOGGER_NAME);
	@Autowired
	private OauthMapper oauthMapper;
	@Context
	private MessageContext context;
	@Override
	public Response ios_token_up(String token, String ios_token) {
		String usrId = context.getHttpHeaders().getHeaderString(UserInfoKey.USR_ID);
		if (CheckRequestParam.isEmpty(usrId)) {
			logger.error("未获取用户ID信息");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ErrorConstant.ERROR90000.toJson()).header(HttpHeaders.CACHE_CONTROL, "no-store").header("Pragma", "no-cache").build();
		}
		UsrInfo xcpUsrInfo = oauthMapper.getXcpUsrInfoByUsrId(usrId);
		if (null == xcpUsrInfo) {
			logger.info("用户[" + usrId + "]不存在有效的登录信息");
			return Response.status(Response.Status.UNAUTHORIZED).entity(ErrorConstant.ERROR_LOGIN_10102.toJson()).build();
		}
		/*为避免多个用户使用同一个手机（主要是针对ios），造成推送消息重复推送的bug*/
		oauthMapper.clearIosTokenByToken(ios_token);//先将所有等于该token的用户，使其token为'0'
		oauthMapper.refreshIosToken(usrId,ios_token);
		return Response.ok().build();
	}

}
