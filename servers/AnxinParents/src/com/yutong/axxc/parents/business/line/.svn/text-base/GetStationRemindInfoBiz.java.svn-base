package com.yutong.axxc.parents.business.line;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.cache.AppCacheData;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.SharedPreferencesUtil;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.LineInfoBean;
import com.yutong.axxc.parents.common.beans.StationRemindInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.GetCertigierInfoRes;
import com.yutong.axxc.parents.connect.http.packet.GetSationRemindInfoReq;
import com.yutong.axxc.parents.connect.http.packet.GetSationRemindInfoRes;
import com.yutong.axxc.parents.connect.http.packet.GetStudentLineInfoRes;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 获取站点提醒信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class GetStationRemindInfoBiz extends YtAsyncTask
{

	/** Context对象 */
	private Context context;

	/** Handler对象 */
	private Handler handler;

	/** 学生Id */
	private String cld_id;

	/** 线路类型 */
	private String line_type;

	/** 数据读取方式 */
	private ReadMethodEnum optype = ReadMethodEnum.OPTYPE_REMOTE;

	private String belongEtag = EtagSettingsDao.STATION_REMIND_INFO_ETAG;

	private String belongCache = CahceSettingsDao.STATION_REMIND_INFO_ETAG;

	private String exkey;

	public GetStationRemindInfoBiz(Context context, Handler handler,
			String cld_id, String line_type)
	{
		this.context = context;
		this.handler = handler;
		this.cld_id = cld_id;
		this.line_type = line_type;
		logTypeName = "[获取站点提醒信息逻辑类]:";

		exkey = cld_id + line_type;
	}

	public void setReadMethod(ReadMethodEnum optype)
	{
		this.optype = optype;
	}

	@Override
	protected void doInBackground()
	{
		switch (optype)
		{
		case OPTYPE_REMOTE:
			handleProcess();
			break;
		case OPTYPE_LOCAL:
			getInfoFromLocal();
			break;
		case OPTYPE_LOCAL_AND_REMOTE:
			getInfoFromLocal();
			break;
		default:
			break;
		}
	}

	/**
	 * 从本地获取信息
	 */
	private void getInfoFromLocal()
	{
		Logger.i(this.getClass(), logTypeName + "获取本地信息");

		List<StationRemindInfoBean> stationRemindInfoBeans = getLocalData();

		if (stationRemindInfoBeans != null)
		{
			Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");

			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.COMMON_SUCCESS, stationRemindInfoBeans);
		}
		else
		{
			Logger.i(this.getClass(), logTypeName + "没有缓存数据");
			if (optype == ReadMethodEnum.OPTYPE_LOCAL)
			{
				ThreadCommUtil.sendMsgToUI(handler,
						ThreadCommStateCode.CACHE_NO_DATA);
			}
			else if (optype == ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE)
			{
				handleProcess();
			}
		}
	}

	private List<StationRemindInfoBean> getLocalData()
	{
		String jsonString = CahceSettingsDao.getCacheInfo(belongCache, exkey);

		if (StringUtils.isNotBlank(jsonString))
		{
			Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");
			GetSationRemindInfoRes res = new GetSationRemindInfoRes();
			res.parse(jsonString);
			AppCacheData.putStationRemindInfos(cld_id, line_type, res.getStationRemindInfoBeans());
            
			return res.getStationRemindInfoBeans();
		}
		else
		{
			return null;
		}
	}

	private void handleProcess()
	{

		Logger.i(this.getClass(), logTypeName + "发送请求");
		GetSationRemindInfoReq req = buildReq();

		HttpRes httpRes = HttpMsgSendUtil.sendPostMsg(req);
		// 用来UI取消操作
		if (isCanceled())
		{
			return;
		}

		if (httpRes.isSuccess())
		{
			parseAndProcessRes(httpRes);
		}
		else if (httpRes.isTokenExpire())
		{
			Logger.i(this.getClass(), logTypeName + "Token失效");
			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.TOKEN_INVALID);
		}
		else if (httpRes.isException())
		{
			Logger.w(this.getClass(), logTypeName + "失败：",
					httpRes.getFailInfo());
			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.NETWORK_ERROR, httpRes.getFailInfo());
		}
		else
		{
			Logger.w(this.getClass(), logTypeName + "失败：",
					httpRes.getFailInfo());
			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.COMMON_FAILED, httpRes.getFailInfo());
		}
	}

	private void parseAndProcessRes(HttpRes httpRes)
	{
		GetSationRemindInfoRes res = new GetSationRemindInfoRes();

		res.parse(httpRes.getContent());
		if (!res.isError())
		{
			Logger.i(this.getClass(), logTypeName + "成功");

			if (httpRes.needCached())
			{
				Logger.i(this.getClass(), logTypeName + "设置缓存");

				CahceSettingsDao.setCacheInfo(belongCache, exkey,
						httpRes.getContent());
				EtagSettingsDao.saveETag(belongEtag, exkey, httpRes.getETag());

				AppCacheData.putStationRemindInfos(cld_id, line_type, res.getStationRemindInfoBeans());
				
				ThreadCommUtil.sendMsgToUI(handler,
						ThreadCommStateCode.REMOTE_DATA_CHANGED,
						res.getStationRemindInfoBeans());

			}
			else
			{
				Logger.i(this.getClass(), logTypeName + "网络数据无变化");
				List<StationRemindInfoBean> stationRemindInfoBeans = getLocalData();
				ThreadCommUtil.sendMsgToUI(handler,
						ThreadCommStateCode.REMOTE_DATA_NO_CHANGED,
						stationRemindInfoBeans);
			}
		}
		else
		{
			Logger.i(this.getClass(), logTypeName + "失败");
			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil
							.getInstance().get(res.getErrorCode()));
		}
	}

	private GetSationRemindInfoReq buildReq()
	{
		GetSationRemindInfoReq req = new GetSationRemindInfoReq();
		req.setAccessToken(YtApplication.getInstance().getAccessToken());
		req.setIfNoneMatch(EtagSettingsDao.getETag(belongEtag, exkey));
		req.setCld_id(cld_id);
		req.setLine_type(line_type);
		return req;
	}

}
