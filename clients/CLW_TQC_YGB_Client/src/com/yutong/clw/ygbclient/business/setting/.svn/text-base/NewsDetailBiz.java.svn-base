package com.yutong.clw.ygbclient.business.setting;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.news.NewsInfo;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.setting.NewsDetailReq;
import com.yutong.clw.ygbclient.connect.http.setting.NewsDetailRes;
import com.yutong.clw.ygbclient.dao.setting.NewsInfoDao;

/**
 * 获取新闻详细业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:33:54
 */
public class NewsDetailBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    /**
     * 新闻id
     */
    private String news_id;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public NewsDetailBiz(Context context, String news_id)
    {
        this.context = context;
        this.news_id = news_id;
        logTypeName = "[获取新闻详细业务逻辑类]:";
        setActionURI(ActionURI.GET_NEWS_DETAIL);
    }

    public NewsInfo getNewsDetailFromLocal()
    {
        return new NewsInfoDao(context).getNewsInfo(news_id, ProxyManager.getInstance(context).getUserCode());
    }

    public NewsInfo getNewsDetailFromServer() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        NewsDetailReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            NewsDetailRes res = new NewsDetailRes();
            res.parse(httpRes.getContent());
            if (!res.isError()){
            	
                Logger.i(this.getClass(), logTypeName + "成功");
//                if (httpRes.needCached())
//                {
                    NewsInfoDao newsInfoDao = new NewsInfoDao(context);
                    CahceDataManager.getInstance(context).putCacheConfig(actionURI, cacheKey, null);
                    newsInfoDao.addNewsInfos(res.getNewsInfo(), ProxyManager.getInstance(context).getUserCode());
                    return res.getNewsInfo();
//                }
//                else
//                {
//                    return getNewsDetailFromLocal();
//                }
            }
            else
            {
                Logger.e(this.getClass(), logTypeName + "失败");
                throw new CommonException(CommonNetStatus.Error_Info, res.getErrorCode(), res.getErrorDes());

            }
        }
        else if (httpRes.isTokenExpire())
        {
            Logger.e(this.getClass(), logTypeName + "Token失效");
            throw new CommonException(CommonNetStatus.Token_InValid);
        }
        else if (httpRes.isException())
        {
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Exception);
        }
        else
        {
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Not_Stable);
        }
    }

    /**
     * 组装请求对象
     * 
     * @param context
     * @return
     */
    private NewsDetailReq buildReq()
    {
        NewsDetailReq req = new NewsDetailReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setUriParam(news_id);

        return req;
    }

}
