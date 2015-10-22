package com.neusoft.mid.clwapi.service.video;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.common.UserInfoKey;
import com.neusoft.mid.clwapi.entity.video.BusVideoStatus;
import com.neusoft.mid.clwapi.entity.video.VideoCarTree;
import com.neusoft.mid.clwapi.entity.video.WatchSet;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.VideoMapper;
import com.neusoft.mid.clwapi.tools.JacksonUtils;

public class VideoServiceImpl implements VideoService {
	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	@Autowired
	private VideoMapper videoMapper;
	@Autowired
	private WatchVideoService watchVideoService;
	@Context 
	private MessageContext context;
	
	public void setContext(MessageContext context) {
		this.context = context;
	}

	@Override
	public Response getWatchRule() {
		String userId = context.getHttpHeaders().getHeaderString("usr_id");
		WatchSet set=videoMapper.getWatchSet(userId);
		if(set==null){
			logger.info("用户{}没有设置，配置默认设置",userId);
			set=new WatchSet();
			set.setUID(userId);
			set.setInWifi("0");//默认不选中，支持2G/3G，和wifi
			set.setPlayTime("30");//默认是30s
			watchVideoService.add(set);
		}
		
		return Response.ok().entity(JacksonUtils.toJsonRuntimeException(set)).build();
	}

	@Override
	public Response setWatchRule(String req) {
		String userId = context.getHttpHeaders().getHeaderString("usr_id");
		WatchSet set=JacksonUtils.fromJsonRuntimeException(req, WatchSet.class);
		if(set==null||!StringUtils.hasText(set.getInWifi())){
			logger.error("是否仅支持wifi参数为空");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		set.setUID(userId);
		logger.info("保存查看视频设置:{}",set);
		watchVideoService.save(set);
		
		return Response.ok().build();
	}

	@Override
	public Response list(String req) {
		Map reqMap=JacksonUtils.jsonToMapRuntimeException(req);
		List<String>  brands=(List<String>)reqMap.get("brand");
		if(brands==null||brands.size()<1){
			logger.error("参数为空");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		String epid = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ENTERPRISE_ID);
		String orgID = context.getHttpHeaders().getHeaderString(
				UserInfoKey.ORGANIZATION_ID);

		logger.info("车辆监控-企业ID为:" + epid + ",组织ID为:" + orgID);

		if (!StringUtils.hasText(epid) || !StringUtils.hasText(orgID)) {
			logger.error("车辆监控-缺少企业ID或者组织ID");
			throw new ApplicationException(ErrorConstant.ERROR90000,
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		List<VideoCarTree> list=videoMapper.getVideoBusTree(epid,orgID,brands);

		return Response.ok().entity(JacksonUtils.toJsonRuntimeException(list)).build();
	}

	@Override
	public Response status(String vin) {
		if(!StringUtils.hasText(vin)){
			logger.error("参数为空");
			throw new ApplicationException(ErrorConstant.ERROR10002,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		BusVideoStatus status=videoMapper.getBusVideoStatus(vin);
		
		logger.info("{}",status);
		return Response.ok().entity(JacksonUtils.toJsonRuntimeException(status)).build();
	}

}
