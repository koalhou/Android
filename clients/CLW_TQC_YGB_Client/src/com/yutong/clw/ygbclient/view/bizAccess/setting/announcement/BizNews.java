/**
 * @公司名称：YUTONG
 * @作者：lizyi
 * @版本号：1.0
 * @生成日期：2013-11-10 上午10:58:12
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bizAccess.setting.announcement;

import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.business.setting.AdviseReceiveBiz;
import com.yutong.clw.ygbclient.business.setting.GetPushMsgRulesBiz;
import com.yutong.clw.ygbclient.business.setting.NewSummaryBiz;
import com.yutong.clw.ygbclient.business.setting.NewsDetailBiz;
import com.yutong.clw.ygbclient.business.setting.SetPushMsgRulesBiz;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.AdviseReply;
import com.yutong.clw.ygbclient.common.beans.PushMsgRule;
import com.yutong.clw.ygbclient.common.beans.news.NewsInfo;
import com.yutong.clw.ygbclient.common.beans.news.NewsParam;
import com.yutong.clw.ygbclient.common.beans.news.NewsReturnInfo;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.view.bean.push.FeedBackPushBean;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * 新闻数据获取逻辑控制类
 * 
 * @author lizyi 2013-11-10 上午10:58:12
 */
public class BizNews extends BizBase
{

    public BizNews(Context context, int threadcount)
    {
        super(context, threadcount);
    }

    public BizNews(Context context)
    {
        super(context);
    }

    /**
     * 获取新闻概要
     * 
     * @author lizyi 2013-11-10 上午11:06:46
     * @param newsParam
     * @param process
     * @return
     */
    public BizDataTypeEnum getNewsSummary(final NewsParam newsParam, BizResultProcess<NewsReturnInfo> process)
    {
        if (newsParam == null)
        {
            return null;
        }
        final NewSummaryBiz biz = new NewSummaryBiz(mContext, newsParam);

        Callable<NewsReturnInfo> LocalCallable = new Callable<NewsReturnInfo>()
        {
            @Override
            public NewsReturnInfo call() throws Exception
            {
//                NewsReturnInfo newsInfo = new NewsReturnInfo();
//                newsInfo.timestamp = DateUtils.getCurDate();
//                newsInfo.hasnext = HasNextStatus.HasData;
//                newsInfo.totalnum = 5;
//                List<NewsInfo> newsInfoBeans = new ArrayList<NewsInfo>();
//                NewsInfo info = null;
//                for (int i = 1; i <= 5; i++)
//                {
//                    info = new NewsInfo();
//                    info.title = "测试新闻编号" + "00" + (Integer.parseInt(newsParam.last_id) * 5 + i);
//                    info.publish_time = DateUtils.getCurDate();
//                    info.is_read = false;
//                    info.id = "00" + i;
//                    info.summary = "宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。";
//                    info.content = info.summary;
//                    newsInfoBeans.add(info);
//                }
//                newsInfo.infos = newsInfoBeans;
//                return newsInfo;
                return biz.getNewsSummaryFromLocal();
            }
        };

        Callable<NewsReturnInfo> networkCallable = new Callable<NewsReturnInfo>()
        {
            @Override
            public NewsReturnInfo call() throws Exception
            {
//                NewsReturnInfo newsInfo = new NewsReturnInfo();
//                newsInfo.timestamp = DateUtils.getCurDate();
//                newsInfo.hasnext = HasNextStatus.HasData;
//                newsInfo.totalnum = 5;
//                List<NewsInfo> newsInfoBeans = new ArrayList<NewsInfo>();
//                NewsInfo info = null;
//                for (int i = 1; i <= 5; i++)
//                {
//                    info = new NewsInfo();
//                    info.title = "测试新闻编号" +"00" + (Integer.parseInt(newsParam.last_id) * 5 + i);
//                    info.publish_time = DateUtils.getCurDate();
//                    info.is_read = false;
//                    info.id = "00" + i;
//                    info.summary = "宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。";
//                    info.content = info.summary;
//                    newsInfoBeans.add(info);
//                }
//                newsInfo.infos = newsInfoBeans;
//                return newsInfo;
                return biz.getNewsSummaryFromServer();
            }
        };

        return BizCommonWork(!biz.IsCacheExpires(), LocalCallable, networkCallable, process);

    }

    /**
     * 获取新闻详情
     * 
     * @author lizyi 2013-11-10 上午11:06:57
     * @param newsParam
     * @param process
     * @return
     */
    public BizDataTypeEnum getNewsDetail(final String news_id, BizResultProcess<NewsInfo> process)
    {
        if (StringUtil.isBlank(news_id))
        {
            return null;
        }
        final NewsDetailBiz biz = new NewsDetailBiz(mContext, news_id);

        Callable<NewsInfo> LocalCallable = new Callable<NewsInfo>()
        {
            @Override
            public NewsInfo call() throws Exception
            {
//                NewsInfo info = new NewsInfo();
//                info.title = "宇通新闻编号" + news_id;
//                info.publish_time = DateUtils.getCurDate();
//                info.is_read = false;
//                info.id = news_id;
//                info.summary = "宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。";
//                info.content = "宇通新闻测试数据宇通新闻测试数据宇通新闻测试数据宇通新闻测试数据宇通新闻测试数据";
//                return info;
                return biz.getNewsDetailFromLocal();
            }
        };

        Callable<NewsInfo> networkCallable = new Callable<NewsInfo>()
        {
            @Override
            public NewsInfo call() throws Exception
            {
//                NewsInfo info = new NewsInfo();
//                info.title = "宇通新闻编号" + news_id;
//                info.publish_time = DateUtils.getCurDate();
//                info.is_read = false;
//                info.id = news_id;
//                info.summary = "宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。宇通测试摘要。";
//                info.content = "宇通新闻测试数据宇通新闻测试数据宇通新闻测试数据宇通新闻测试数据宇通新闻测试数据";
//                return info;

                return biz.getNewsDetailFromServer();
            }
        };

        return BizCommonWork(!biz.IsCacheExpires(), LocalCallable, networkCallable, process);

    }
    
    public BizDataTypeEnum getPushMsgRules(BizResultProcess<List<PushMsgRule>> process){
    	
    	Logger.i(getClass(), "[开始加载公告信息规则:]");
    	final  GetPushMsgRulesBiz getPushMsgRulesBiz  = new GetPushMsgRulesBiz(mContext);
    	
    	Callable<List<PushMsgRule>> getRulesFromLocal = new Callable<List<PushMsgRule>>(){

			@Override
			public List<PushMsgRule> call() throws Exception {
				
				return getPushMsgRulesBiz.getRulesFromLocal();
			}
    		
    	};
    	
    	Callable<List<PushMsgRule>> getRulesFromServer = new Callable<List<PushMsgRule>>(){

			@Override
			public List<PushMsgRule> call() throws Exception {
				
				return getPushMsgRulesBiz.getRulesFromServer();
			}
    		
    	};
    	
    	return BizCommonWork(!getPushMsgRulesBiz.IsCacheExpires(), getRulesFromLocal, getRulesFromServer, process);
    }
    
    public BizDataTypeEnum setPushMsgRules(List<PushMsgRule> rules,BizResultProcess<Boolean> process){
    	
    	final SetPushMsgRulesBiz setPushMsgRulesBiz = new SetPushMsgRulesBiz(mContext,rules);
    	
    	Callable<Boolean> updateRules = new Callable<Boolean>(){

			@Override
			public Boolean call() throws Exception {
				
				return setPushMsgRulesBiz.updateRules();
			}
    	};
    	return BizCommonWork(false, null, updateRules, process);
    }
    
    
    /*意见反馈消息查询方法*/
    public BizDataTypeEnum getAdviceInfoNumByReadFlag(final String readFlag,final String replyFlag,BizResultProcess<Integer> process){
    	
    	final AdviseReceiveBiz adviseReceiveBiz = new AdviseReceiveBiz(mContext);
    	
    	Callable<Integer> updateRules = new Callable<Integer>(){

			@Override
			public Integer call() throws Exception {
				return adviseReceiveBiz.getAdviceInfoNumByReadFlag(readFlag,replyFlag);
			}
    	};
    	return BizCommonWork(true, updateRules, null, process);
    }
    
    /*意见反馈消息查询列表*/
    public BizDataTypeEnum getUnReadAdviceList(final String userCode,BizResultProcess<List<FeedBackPushBean>> process){
    	
    	final AdviseReceiveBiz adviseReceiveBiz = new AdviseReceiveBiz(mContext);
    	
    	Callable<List<FeedBackPushBean>> updateRules = new Callable<List<FeedBackPushBean>>(){

			@Override
			public List<FeedBackPushBean> call() throws Exception {
				
				return adviseReceiveBiz.getUnReadAdviceList(userCode);
			}
    	};
    	
    	return BizCommonWork(true, updateRules, null, process);
    }
    
    public BizDataTypeEnum setAdviceListStatus(final String readFlag,final String replyFlag,BizResultProcess<Boolean> process){
    	
    	final AdviseReceiveBiz adviseReceiveBiz = new AdviseReceiveBiz(mContext);
    	
    	Callable<Boolean> updateRules = new Callable<Boolean>(){

			@Override
			public Boolean call() throws Exception {
				return adviseReceiveBiz.setAdviceListStatus(readFlag,replyFlag);
			}
    	};
    	
    	return BizCommonWork(true, updateRules, null, process);
    }
   
    //get
    public BizDataTypeEnum getUnReadAdviceListMerger(final String userCode,BizResultProcess<List<FeedBackPushBean>> process){
    	
    	final AdviseReceiveBiz adviseReceiveBiz = new AdviseReceiveBiz(mContext);
    	
    	Callable<List<FeedBackPushBean>> updateRules = new Callable<List<FeedBackPushBean>>(){

			@Override
			public List<FeedBackPushBean> call() throws Exception {
				
				return adviseReceiveBiz.getUnReadAdviceList(userCode);
			}
    	};
    	
    	return BizCommonWork(true, updateRules, null, process);
    }
    
    public BizDataTypeEnum test(final String userCode,BizResultProcess<List<FeedBackPushBean>> process){
    	
    	final AdviseReceiveBiz adviseReceiveBiz = new AdviseReceiveBiz(mContext);
    	
    	Callable<List<FeedBackPushBean>> updateRules = new Callable<List<FeedBackPushBean>>(){

			@Override
			public List<FeedBackPushBean> call() throws Exception {
				
				/*1. 服务端获取*/
				List<FeedBackPushBean> adviseReplyList = adviseReceiveBiz.getAdviseReplyList();
				 
				/*2. 本地获取比对更新*/
				adviseReceiveBiz.setAdviceListStatus(adviseReplyList);
				 
				/*3. 本地获取更新后信息*/
				return adviseReceiveBiz.getUnReadAdviceList(userCode);
			}
    	};
    	
    	return BizCommonWork(false, null, updateRules, process);
    }
}
