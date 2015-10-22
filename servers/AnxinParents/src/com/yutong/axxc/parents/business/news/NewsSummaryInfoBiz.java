package com.yutong.axxc.parents.business.news;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.NewsInfoBean;
import com.yutong.axxc.parents.common.constant.NewsTypeConstant;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.NewsSummaryInfoReq;
import com.yutong.axxc.parents.connect.http.packet.NewsSummaryInfoRes;
import com.yutong.axxc.parents.dao.beans.DaoNewsInfoBean;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.dao.news.NewsInfoDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 新闻信息业务类
 * 
 * @author zhangzhia 2013-9-3 下午3:36:02
 */
public class NewsSummaryInfoBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 新闻类型 */
    private int newsType = NewsTypeConstant.ALL_TYPE;

    /** 数据读取方式 */
    private ReadMethodEnum optype = ReadMethodEnum.OPTYPE_REMOTE;

    private String belongEtag = EtagSettingsDao.PUSH_MSG_RULE_ETAG;

    private String belongCache = CahceSettingsDao.PUSH_MSG_RULE_ETAG;

    private String exkey;

    public NewsSummaryInfoBiz(Context context, Handler handler, int newsType)
    {
        this.context = context;
        this.handler = handler;
        this.newsType = newsType;

        logTypeName = "[获取信息推送设置业务类]:";

        exkey = YtApplication.getInstance().getUid();
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

        List<NewsInfoBean> newsInfoBeans = getNews();

        if (newsInfoBeans != null && newsInfoBeans.size() > 0)
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, newsInfoBeans);
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "没有缓存数据");
            if(optype == ReadMethodEnum.OPTYPE_LOCAL)
            {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.CACHE_NO_DATA);
            }
            else if(optype == ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE)
            {
                handleProcess();
            }
        }
    }

    
    /**
     * 获取新闻
     */
    private List<NewsInfoBean> getNews()
    {
        List<DaoNewsInfoBean> daoNewsInfoBeans = null;
        NewsInfoDao newsInfoDao = new NewsInfoDao(context);

        switch (newsType)
        {
        case NewsTypeConstant.ALL_TYPE:
            daoNewsInfoBeans = newsInfoDao.getNewsInfos(YtApplication.getInstance().getUid(), String.valueOf(NewsTypeConstant.ALL_TYPE));
            break;
        case NewsTypeConstant.NEWS_TYPE:
            daoNewsInfoBeans = newsInfoDao.getNewsInfos(YtApplication.getInstance().getUid(), String.valueOf(NewsTypeConstant.NEWS_TYPE));
            break;
        default:
            break;
        }

        return buildInfo(daoNewsInfoBeans);
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        NewsSummaryInfoReq req = buildReq();

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
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.TOKEN_INVALID);
        }
        else if (httpRes.isException())
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.NETWORK_ERROR, httpRes.getFailInfo());
        }
        else
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, httpRes.getFailInfo());
        }
    }

    private void parseAndProcessRes(HttpRes httpRes)
    {
        NewsSummaryInfoRes res = new NewsSummaryInfoRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            NewsInfoDao newsInfoDao = new NewsInfoDao(context);
            List<DaoNewsInfoBean> daoNewsInfoBeans = buildDaoBean(res.getNewsInfoBeans());

            newsInfoDao.addNewsInfos(daoNewsInfoBeans, YtApplication.getInstance().getUid());

            List<NewsInfoBean> newsInfoBeans = getNews();

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, newsInfoBeans);

            int nonExistCount = newsInfoDao.getNonExistNewsCount(daoNewsInfoBeans, YtApplication.getInstance().getUid());
            if (res.getNewsInfoBeans() != null && res.getNewsInfoBeans().size() > 0 && nonExistCount > 0)
            {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SEARCH_RES_COUNT, nonExistCount);
            }
            else
            {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SEARCH_NO_RES);
            }

        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    /**
     * 组装新闻信息对象
     * 
     * @param daoNewsInfoBeans
     * @return
     */
    private List<NewsInfoBean> buildInfo(List<DaoNewsInfoBean> daoNewsInfoBeans)
    {
        List<NewsInfoBean> newsInfoBeans = new ArrayList<NewsInfoBean>();
        if (daoNewsInfoBeans != null)
        {
            for (DaoNewsInfoBean daoNewsInfoBean : daoNewsInfoBeans)
            {
                NewsInfoBean newsInfoBean = new NewsInfoBean();
                newsInfoBean.setNews_id(daoNewsInfoBean.getNews_id());

                newsInfoBean.setNews_title(daoNewsInfoBean.getNews_title());
                newsInfoBean.setNews_summary(daoNewsInfoBean.getNews_summary());
                newsInfoBean.setNews_author(daoNewsInfoBean.getNews_author());
                newsInfoBean.setNews_image(daoNewsInfoBean.getNews_image());
                newsInfoBean.setNews_content(daoNewsInfoBean.getNews_content());
                newsInfoBean.setNews_time(daoNewsInfoBean.getNews_time());
                newsInfoBean.setNews_type(daoNewsInfoBean.getNews_type());
                newsInfoBean.setIs_read(daoNewsInfoBean.getIs_read());

                newsInfoBeans.add(newsInfoBean);
            }
        }
        return newsInfoBeans;
    }

    /**
     * 创建新新闻信息数据库操作实体
     * 
     * @param newsInfoRes
     * @return
     */
    private List<DaoNewsInfoBean> buildDaoBean(List<NewsInfoBean> newsInfoBeans)
    {
        List<DaoNewsInfoBean> daoNewsInfoBeans = new ArrayList<DaoNewsInfoBean>();
        if (newsInfoBeans != null && newsInfoBeans.size() > 0)
        {
            for (NewsInfoBean newsInfoBean : newsInfoBeans)
            {
                DaoNewsInfoBean daoNewsInfoBean = new DaoNewsInfoBean();

                daoNewsInfoBean.setNews_id(newsInfoBean.getNews_id());

                daoNewsInfoBean.setNews_title(newsInfoBean.getNews_title());
                daoNewsInfoBean.setNews_summary(newsInfoBean.getNews_summary());
                daoNewsInfoBean.setNews_author(newsInfoBean.getNews_author());
                daoNewsInfoBean.setNews_image(newsInfoBean.getNews_image());
                daoNewsInfoBean.setNews_content(newsInfoBean.getNews_content());
                daoNewsInfoBean.setNews_time(newsInfoBean.getNews_time());
                daoNewsInfoBean.setNews_type(newsInfoBean.getNews_type());
                daoNewsInfoBean.setIs_read(newsInfoBean.getIs_read());

                daoNewsInfoBeans.add(daoNewsInfoBean);
            }
        }
        return daoNewsInfoBeans;
    }

    private NewsSummaryInfoReq buildReq()
    {
        NewsSummaryInfoReq req = new NewsSummaryInfoReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());

        // TODO 服务端升级后需删除该设置
        req.setNum("100");
        NewsInfoDao newsInfoDao = new NewsInfoDao(context);
        String startTime = newsInfoDao.getMaxNewsTime(YtApplication.getInstance().getUid());
        req.setStartTime(startTime);
        //req.setEndTime("");
        req.setNews_type(String.valueOf(newsType));

        return req;

    }

}
