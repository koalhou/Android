package com.yutong.clw.ygbclient.business.setting;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationAreaInfo;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.news.NewsInfo;
import com.yutong.clw.ygbclient.common.beans.news.NewsParam;
import com.yutong.clw.ygbclient.common.beans.news.NewsReturnInfo;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.enums.news.PageFlag;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.setting.NewSummaryReq;
import com.yutong.clw.ygbclient.connect.http.setting.NewSummaryRes;

/**
 * 获取新闻概要业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:32:43
 */
public class NewSummaryBiz extends AbstractDataControl {
	/** Context对象 */
	private Context context;

	/**
	 * 新闻参数
	 */
	private NewsParam newsParam;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            Context对象
	 */
	public NewSummaryBiz(Context context, NewsParam newsParam) {
		this.context = context;
		this.newsParam = newsParam;
		logTypeName = "[获取新闻概要业务逻辑类]:";
		setActionURI(ActionURI.GET_NEWS_SUMMARY);

		// 如果是下翻页，则读取最新数据
		if (newsParam.getPageFlag() == PageFlag.Next) {
			CahceDataManager.getInstance(context).removeCache(ActionURI.GET_NEWS_SUMMARY);
		}
	}

	public NewsReturnInfo getNewsSummaryFromLocal() {

		Logger.i(this.getClass(), logTypeName + "从本地获取数据");
		cacheItem = CahceDataManager.getInstance(context).getCacheData(actionURI, cacheKey);
		if (cacheItem != null && StringUtil.isNotBlank(cacheItem.CacheData)) {

			Gson gson = new Gson();
			Type type = new TypeToken<NewsReturnInfo>() {
			}.getType();
			return gson.fromJson(cacheItem.CacheData, type);
		} else {
			return null;
		}
	}

	public NewsReturnInfo getNewsSummaryFromServer() throws CommonException {

		Logger.i(this.getClass(), logTypeName + "发送请求");
		NewSummaryReq req = buildReq();

		HttpRes httpRes = getHttpRes(req);
		NewsReturnInfo returnInfo = null;
		if (httpRes.isSuccess()) {

			NewSummaryRes res = new NewSummaryRes();
			res.parse(httpRes.getContent());
			if (!res.isError()) {
				Logger.i(this.getClass(), logTypeName + "成功");
				if (httpRes.needCached()) {
					returnInfo = res.getNewsReturnInfo();

					// 只缓存首页数据
					if (newsParam.pageFlag == PageFlag.First) {
						GsonBuilder builder = new GsonBuilder();
						builder.serializeSpecialFloatingPointValues();
						Gson gson = builder.create();
						Type type = new TypeToken<NewsReturnInfo>() {
						}.getType();

						CahceDataManager.getInstance(context).putCacheConfig(
								actionURI, cacheKey, httpRes.getETag(),
								gson.toJson(returnInfo, type));
					}

					Logger.i(this.getClass(), logTypeName + "有新数据");
					return returnInfo;
				} else {
					return getNewsSummaryFromLocal();
				}
			} else {
				Logger.e(this.getClass(), logTypeName + "失败");
				throw new CommonException(CommonNetStatus.Error_Info,
						res.getErrorCode(), res.getErrorDes());

			}
		} else if (httpRes.isTokenExpire()) {
			Logger.e(this.getClass(), logTypeName + "Token失效");
			throw new CommonException(CommonNetStatus.Token_InValid);
		} else if (httpRes.isException()) {
			Logger.e(this.getClass(), logTypeName + "失败：",
					httpRes.getFailInfo());
			throw new CommonException(CommonNetStatus.NetWork_Exception);
		} else {
			Logger.e(this.getClass(), logTypeName + "失败：",
					httpRes.getFailInfo());
			throw new CommonException(CommonNetStatus.NetWork_Not_Stable);
		}
	}

	/**
	 * 组装请求对象
	 * 
	 * @param context
	 * @return
	 */
	private NewSummaryReq buildReq() {
		NewSummaryReq req = new NewSummaryReq();
		req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
		req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(
				actionURI, cacheKey));
		req.setNewsParam(newsParam);
		return req;
	}
}
