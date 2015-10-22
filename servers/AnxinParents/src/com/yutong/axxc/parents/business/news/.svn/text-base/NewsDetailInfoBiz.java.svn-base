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
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.NewsDetailInfoReq;
import com.yutong.axxc.parents.connect.http.packet.NewsDetailInfoRes;
import com.yutong.axxc.parents.dao.beans.DaoNewsInfoBean;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.dao.news.NewsInfoDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 新闻信息详细业务类
 * 
 * @author zhangzhia 2013-9-3 下午3:36:02
 */
public class NewsDetailInfoBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 新闻id */
    private String news_id;

    /** 数据读取方式 */
    private ReadMethodEnum optype = ReadMethodEnum.OPTYPE_REMOTE;

    private String belongEtag = EtagSettingsDao.PUSH_MSG_RULE_ETAG;

    private String belongCache = CahceSettingsDao.PUSH_MSG_RULE_ETAG;

    private String exkey;

    public NewsDetailInfoBiz(Context context, Handler handler, String news_id)
    {
       this(context, handler, news_id, ReadMethodEnum.OPTYPE_REMOTE);
    }
    public NewsDetailInfoBiz(Context context, Handler handler, String news_id,ReadMethodEnum optype)
    {
    	this.optype = optype;
        this.context = context;
        this.handler = handler;
        this.news_id = news_id;

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

        NewsInfoBean newsInfoBean = getNews();

        if (newsInfoBean != null)
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, newsInfoBean);
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
    private NewsInfoBean getNews()
    {
        NewsInfoDao newsInfoDao = new NewsInfoDao(context);

        DaoNewsInfoBean daoNewsInfoBean = newsInfoDao.getNewsDetailInfo(news_id, YtApplication.getInstance().getUid());

        if (daoNewsInfoBean == null
                || (StringUtils.isBlank(daoNewsInfoBean.getNews_author()) && StringUtils.isBlank(daoNewsInfoBean.getNews_image()) && StringUtils
                        .isBlank(daoNewsInfoBean.getNews_content())))
        {
            return null;
        }
        else
        {
            //添加已读标志
            daoNewsInfoBean.setIs_read(true);
            newsInfoDao.addReadFlag(daoNewsInfoBean, YtApplication.getInstance().getUid());
            return buildInfo(daoNewsInfoBean);
        }
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        NewsDetailInfoReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendGetMsg(req);
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
        NewsDetailInfoRes res = new NewsDetailInfoRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            if (res.getNewsInfoBean() != null)
            {
                DaoNewsInfoBean daoNewsInfoBean = buildDaoBean(res.getNewsInfoBean());

                //添加已读标志
                daoNewsInfoBean.setIs_read(true);
                
                List<DaoNewsInfoBean> daoNewsInfoBeans = new ArrayList<DaoNewsInfoBean>();

                NewsInfoDao newsInfoDao = new NewsInfoDao(context);

                if (newsInfoDao.isExistNewsInfo(daoNewsInfoBean, YtApplication.getInstance().getUid()))
                {
                    
                    newsInfoDao.modifyNewsInfo(daoNewsInfoBean, YtApplication.getInstance().getUid());
                }
                else
                {
                    daoNewsInfoBeans.add(daoNewsInfoBean);
                    newsInfoDao.addNewsInfos(daoNewsInfoBeans, YtApplication.getInstance().getUid());
                }

                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, res.getNewsInfoBean());
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
    private NewsInfoBean buildInfo(DaoNewsInfoBean daoNewsInfoBean)
    {
        NewsInfoBean newsInfoBean = null;
        if (daoNewsInfoBean != null)
        {
            newsInfoBean = new NewsInfoBean();
            newsInfoBean.setNews_id(daoNewsInfoBean.getNews_id());

            newsInfoBean.setNews_title(daoNewsInfoBean.getNews_title());
            newsInfoBean.setNews_summary(daoNewsInfoBean.getNews_summary());
            newsInfoBean.setNews_author(daoNewsInfoBean.getNews_author());
            newsInfoBean.setNews_image(daoNewsInfoBean.getNews_image());
            newsInfoBean.setNews_image_url(daoNewsInfoBean.getNews_image_url());
            newsInfoBean.setNews_content(daoNewsInfoBean.getNews_content());
            newsInfoBean.setNews_time(daoNewsInfoBean.getNews_time());
            newsInfoBean.setNews_type(daoNewsInfoBean.getNews_type());
            newsInfoBean.setIs_read(daoNewsInfoBean.getIs_read());
        }
        return newsInfoBean;
    }

    /**
     * 创建新新闻信息数据库操作实体
     * 
     * @param newsInfoRes
     * @return
     */
    private DaoNewsInfoBean buildDaoBean(NewsInfoBean newsInfoBean)
    {
        DaoNewsInfoBean daoNewsInfoBean = null;
        if (newsInfoBean != null)
        {
            daoNewsInfoBean = new DaoNewsInfoBean();

            daoNewsInfoBean.setNews_id(newsInfoBean.getNews_id());

            daoNewsInfoBean.setNews_title(newsInfoBean.getNews_title());
            daoNewsInfoBean.setNews_summary(newsInfoBean.getNews_summary());
            daoNewsInfoBean.setNews_author(newsInfoBean.getNews_author());
            daoNewsInfoBean.setNews_image(newsInfoBean.getNews_image());
            daoNewsInfoBean.setNews_image_url(newsInfoBean.getNews_image_url());
            daoNewsInfoBean.setNews_content(newsInfoBean.getNews_content());
            daoNewsInfoBean.setNews_time(newsInfoBean.getNews_time());
            daoNewsInfoBean.setNews_type(newsInfoBean.getNews_type());
            daoNewsInfoBean.setIs_read(newsInfoBean.getIs_read());
        }
        return daoNewsInfoBean;
    }

    private NewsDetailInfoReq buildReq()
    {
        NewsDetailInfoReq req = new NewsDetailInfoReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());

        req.setNews_id(news_id);

        return req;

    }
}
