/**
 * @(#)ResourceServiceImpl.java 2013-4-11
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.resource;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.HttpConstant;
import com.neusoft.mid.clwapi.entity.resource.ResourceInfo;
import com.neusoft.mid.clwapi.entity.resource.ResourceResp;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.ResourceMapper;
import com.neusoft.mid.clwapi.tools.ImageEncoderUtils;
import com.neusoft.mid.clwapi.tools.JacksonUtils;
import com.neusoft.mid.clwapi.tools.URLCoderUtil;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-11 下午4:38:48
 */
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private ResourceMapper iResourceMapper;
	@Context
	private MessageContext context;

	private Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

	/**
	 * 终端获取资源API
	 * 
	 * @param token
	 *            用户token
	 * @param type
	 *            资源类型
	 * @param resId
	 *            资源ID
	 * @return
	 */
	@Override
	public Object getResource(String token, String type, String resId) {
		logger.info("处理终端获取资源请求");

		// 判断是否为空
		if (StringUtils.isEmpty(type) || StringUtils.isEmpty(resId)) {
			logger.error("终端参数不符合要求");
			throw new ApplicationException(ErrorConstant.ERROR10001,
					Response.Status.BAD_REQUEST);
		}

		String[] param = URLCoderUtil.decoder(type, resId);
		// 判断type值是否符合要求
		if (!(param[0].equals(HttpConstant.RESOURCEPORT_TYPE_MARKETING)
				|| param[0].equals(HttpConstant.RESOURCEPORT_TYPE_POLICY) || param[0]
					.equals(HttpConstant.RESOURCEPORT_TYPE_PHOTO))) {
			logger.error("终端参数不符合要求，type值只能为0、1、2");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.BAD_REQUEST);
		}

		logger.info("终端参数符合要求");
		logger.debug("终端要求查找的资源类型为：" + param[0] + "(0- 行业政策 1- 宇通营销 2- 拍摄照片)");
		logger.info("开始获取资源");

		// 取数据
		ResourceInfo iResourceInfo = iResourceMapper.getResource(
				param[0].equals(HttpConstant.RESOURCEPORT_TYPE_PHOTO) ? null
						: param[0], param[1]);

		if (iResourceInfo == null) {
			logger.error("未获取到对应的资源");
			return Response.noContent().build();
		}

		logger.info("成功获取资源");
		logger.debug("资源内容：");
		logger.debug(iResourceInfo.toString());

		// 拼装返回数据
		ResourceResp iResourceResp = new ResourceResp();
		iResourceResp.setType(param[0]);
		iResourceResp.setResId(param[1]);
		// 如果是照片类资源则读图片进行base64转换后传出
		// 如果是图片+文字类信息则将信息转换为json格式base64后输出
		if (param[0].equals(HttpConstant.RESOURCEPORT_TYPE_PHOTO)) {
			// 转换为base64
			try {
				// 将照片路径分隔符换为系统分隔符
				iResourceInfo.setPhoto(iResourceInfo.getPhoto().replace(
						iResourceInfo.getPhoto().subSequence(0, 1),
						File.separator));
				// 拼装照片路径
				logger.debug("图片路径：" + iResourceInfo.getPhoto());
				logger.info("成功获取到照片路径信息");
				String str = ImageEncoderUtils.getImageBinary(iResourceInfo
						.getPhoto());
				logger.info("成功转换照片流到BASE64");
				logger.debug("图片编码：");
				logger.debug(str);
				iResourceResp.setResource(str);
			} catch (IOException e) {
				logger.error("读图片时发生异常" + e.getMessage());
				return Response.noContent().build();
			}
		} else {
			if (!StringUtils.isEmpty(iResourceInfo.getImg())) {
				// 拼装新闻营销信息图片路径
				iResourceInfo.setImg(iResourceInfo.getImg().replace(
						iResourceInfo.getImg().subSequence(0, 1), "/"));
				iResourceInfo.setImg(HttpConstant.NEWS_IMG_PATH
						+ iResourceInfo.getImg());
			}
			logger.debug("新闻营销信息图片路径：" + iResourceInfo.getImg());
			logger.info("新闻营销信息图片路径");
			logger.debug("加密前下发的内容：" + iResourceInfo.toString());
			String resource = JacksonUtils
					.toJsonRuntimeException(iResourceInfo);

			byte[] b = new byte[resource.length()];

			try {
				b = resource.getBytes(HttpConstant.CHARSET);
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持的转码类型" + e.getMessage());
				throw new ApplicationException(ErrorConstant.ERROR90000,
						Response.Status.INTERNAL_SERVER_ERROR);
			}

			resource = Base64Utility.encode(b);
			logger.info("BASE64加密成功");
			logger.debug(resource);
			iResourceResp.setResource(resource);
		}

		logger.info("成功处理获取资源请求");

		return JacksonUtils.toJsonRuntimeException(iResourceResp);
	}
}
