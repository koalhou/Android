/**
 * @(#)LoginServiceImpl.java 2013-4-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.oauth;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.entity.oauth.LoginReq;
import com.neusoft.mid.clwapi.entity.oauth.LoginResp;
import com.neusoft.mid.clwapi.entity.oauth.UsrInfo;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.OauthMapper;
import com.neusoft.mid.clwapi.service.common.UsrOauthService;
import com.neusoft.mid.clwapi.service.software.SoftwareServiceImpl;
import com.neusoft.mid.clwapi.tools.CheckRequestParam;
import com.neusoft.mid.clwapi.tools.JacksonUtils;
import com.neusoft.mid.clwapi.tools.MD5SequenceGenerator;
import com.neusoft.mid.clwapi.tools.PropertiesTools;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-8 下午1:07:13
 */
public class LoginServiceImpl implements LoginService {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory.getLogger(ModCommonConstant.LOGGER_NAME);

	@Autowired
	private OauthMapper oauthMapper;
	@Autowired
	private UsrOauthService usrOauthService;
	@Resource
	private SoftwareServiceImpl softwareService;

	// access_token超时时间设置,单位:秒.
	private static Long ACCESS_TOKEN_EXPIRE_TIME = 86400L;
	// 配置文件中存放access_token超时时间配置的key值.
	private static String ACCESS_TOKEN_EXPIRE_TIME_KEY = "token.expire.interval";

	/**
	 * 终端用户登录操作.
	 * 
	 * @param loginInfo
	 *            终端用户登录信息.
	 * @return 服务端登录结果信息或新版终端软件信息.
	 * 
	 * 演示账号的登录处理：如果是演示账号则使用旧的access token，第一次登陆好、以及退出不变
	 * 
	 */
	@Override
	public Response login(String loginInfo) {
		logger.info("接收到用户登录请求信息");

		// 解析客户端登录请求参数信息
		logger.info("解析客户端登录请求参数信息");
		LoginReq loginReq = JacksonUtils.fromJsonRuntimeException(loginInfo, LoginReq.class);
		// 校验请求参数信息
		checkLoginReq(loginReq);

		//
		logger.debug("机构代码转换前:" + loginReq.getEnterpriseId());
		String enCode = addZeroBeforeStr(loginReq.getEnterpriseId(), 10);
		logger.debug("机构代码转换后:" + enCode);
		String isEnterpriseAllow = oauthMapper.getEnAllowByEnCode(enCode);
		if (StringUtils.isEmpty(isEnterpriseAllow)) {
			logger.info("用户所使用的企业机构编码[" + enCode + "]错误");
			return Response.status(Response.Status.UNAUTHORIZED).entity(ErrorConstant.ERROR_LOGIN_10102.toJson()).build();
		}
		if ("0".equals(isEnterpriseAllow)) {
			logger.info("用户所属企业机构编码[" + enCode + "]无权使用终端客户端软件登录");
			return Response.status(297).entity(ErrorConstant.ERROR10107.toJson()).build();
		}

		// TOKEN信息
		String accessToken = new MD5SequenceGenerator().generate16ByUuid();
		String refreshToken = new MD5SequenceGenerator().generate16ByUuid();
		// 获取token失效时长.
		Long expireIn = getTokenExpireIntervall();
		List<String> userLimitList = null;

		// 从数据库中获取平台用户信息.
		List<UsrInfo> usrInfoList = oauthMapper.getXcpUsrInfo(loginReq.getUserName(),loginReq.getPassword(), enCode);
		UsrInfo usrInfo = null;
		if (null == usrInfoList || usrInfoList.size() == 0) {
			logger.info("不存在与LOGIN_NAME=" + loginReq.getUserName() + ";LOGIN_PWD=" + loginReq.getPassword() + ";ENTERPRISE_CODE=" + enCode + "登录信息相符的用户信息");
			return Response.status(Response.Status.UNAUTHORIZED).entity(ErrorConstant.ERROR_LOGIN_10102.toJson()).build();
		} else {
			logger.info("共检索到符合登录信息的用户信息" + usrInfoList.size() + "条");
			usrInfo = usrInfoList.get(0);
			logger.info("终端用户登录信息正确,USER_ID=" + usrInfo.getUsrId() + ",ENTERPRISE_ID=" + usrInfo.getEnId());
			//用户禁用
			if("2".equals(usrInfo.getValidFlag())){
				logger.info("LOGIN_NAME=" + loginReq.getUserName() + ";LOGIN_PWD=" + loginReq.getPassword() + ";ENTERPRISE_CODE=" + enCode + "的用户被禁用");
				return Response.status(Response.Status.UNAUTHORIZED).entity(ErrorConstant.ERROR_LOGIN_10103.toJson()).build();
			}else if("0".equals(usrInfo.getValidFlag())){
				String lastToken = usrInfo.getAccessToken();
				
				logger.info("检测IOS标识：【" + loginReq.getIos_flag()+"】");
				if(loginReq.getIos_flag() == null){
					logger.info("未检测到IOS标识，默认为安卓客户端登陆，更新IOS标识为空。");
					oauthMapper.updateIosFlag(usrInfo.getUsrId());
				}
				
				
				if (!StringUtils.isEmpty(lastToken)) {
					//注意：演示账号的accessToken不变
//					if("yanshi".equals(loginReq.getUserName())){
//						accessToken=lastToken;
//					}
					logger.info("本次登录操作用户曾经成功登录系统,过去使用的TOKEN信息:" + usrInfo.getAccessToken());
					// 更新终端用户信息
					oauthMapper.userLoginOnceMore(lastToken, accessToken, usrInfo.getEnId(), usrInfo.getOrgId(), refreshToken, expireIn, usrInfo.getUsrId());
				} else {
					logger.info("本次登录操作用户为首次使用客户端软件登录");
					// 向终端用户信息表添加授权信息.
					oauthMapper.insertMblUserInfo(usrInfo.getUsrId(), usrInfo.getEnId(), usrInfo.getOrgId(), accessToken, refreshToken, expireIn);
					//净新增用户时-更新接收问卷预定用户数+1
					oauthMapper.updateQuesUserNum();
					//净新增用户时-更新推送预定用户数+1
					oauthMapper.updatePushMsgNum();
				}
				logger.info("本次登录操作新产生的TOKEN信息:" + accessToken);
			}
			
		}
		
		// 获取用户权限列表信息.
		// userLimitList = oauthMapper.getUserLimit(usrInfo.getUsrId());

		// 返回应答信息.
		return Response.ok().entity(getLoginRespCont(accessToken, refreshToken, expireIn, usrInfo, userLimitList)).build();
	}

	/**
	 * 校验并显示终端用户登录信息.
	 * 
	 * @param loginReq
	 */
	private void checkLoginReq(LoginReq loginReq) {
		// 校验客户端登录参数
		if (!"password".equals(loginReq.getGrantType())) {
			logger.error("登录请求参数中grant_type值[" + loginReq.getGrantType()
					+ "]非法");
			throw new ApplicationException(ErrorConstant.ERROR_LOGIN_10101,
					Response.Status.BAD_REQUEST);
		}

		logger.info("授权类型:[" + loginReq.getGrantType() + "]");
		logger.info("登录用户名:[" + loginReq.getUserName() + "]");
		logger.info("登录用户密码:[" + loginReq.getPassword() + "]");
		logger.info("机构编码:[" + loginReq.getEnterpriseId() + "]");
		logger.info("客户端软件版本信息:[" + loginReq.getVersion() + "]");

		// 关键参数是否为空检查
		if (CheckRequestParam.isEmpty((loginReq.getUserName()))
				|| CheckRequestParam.isEmpty(loginReq.getPassword())
				|| CheckRequestParam.isEmpty(loginReq.getEnterpriseId())
				|| CheckRequestParam.isEmpty(loginReq.getGrantType())) {
			logger.error("登录请求参数非法");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

	}

	/**
	 * 字符串在小于指定长度时前补0.
	 * 
	 * @param str
	 *            待检查字符串.
	 * @param strLength
	 *            字符串指定长度.
	 * @return 前补零的定长字符串.
	 */
	private String addZeroBeforeStr(String str, int strLength) {
		int strLen = str.length();
		StringBuffer sb = null;
		while (strLen < strLength) {
			sb = new StringBuffer();
			sb.append("0").append(str);// 左(前)补0
			str = sb.toString();
			strLen = str.length();
		}
		return str;
	}

	/**
	 * 从配置文件中获取token更新时间间隔.
	 * 
	 * @return token更新时间间隔.
	 */
	public Long getTokenExpireIntervall() {
		Long expireTime = ACCESS_TOKEN_EXPIRE_TIME;
		try {
			// 从系统配置文件中获取token超时时间配置.
			expireTime = Long.parseLong(PropertiesTools.readValue(
					ModCommonConstant.SYS_CONF_FILE_PATH,
					ACCESS_TOKEN_EXPIRE_TIME_KEY));
			logger.debug("配置文件中时间间隔为:" + expireTime + "秒");
		} catch (IOException e) {
			logger.error("从配置文件中获取token更新时间间隔时出错", e);
			// 使用默认超时时间配置
			logger.info("使用默认时间间隔" + expireTime + "秒");
		}
		return expireTime;
	}

	/**
	 * 拼装登录操作应答信息内容.
	 * 
	 * @param accessToken
	 *            access_token信息.
	 * @param refreshToken
	 *            refresh_token信息.
	 * @param expireTime
	 *            token有效时长.
	 * @param usrInfo
	 *            用户基本信息.
	 * @param scope
	 *            用户权限列表信息.
	 * @return 登录操作应答信息.
	 */
	private String getLoginRespCont(String accessToken, String refreshToken,
			Long expireTime, UsrInfo usrInfo, List<String> scope) {
		LoginResp loginResp = new LoginResp();
		loginResp.setAccessToken(accessToken);
		loginResp.setRefreshToken(refreshToken);
		loginResp.setExpiresIn(expireTime);
		loginResp.setUsrInfo(usrInfo);
		loginResp.setScope(scope);
		return JacksonUtils.toJsonRuntimeException(loginResp);
	}
}
