package com.yutong.clw.ygbclient.view.pages.setting.announcement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.PushMsgUtil;
import com.yutong.clw.ygbclient.common.beans.news.NewsInfo;
import com.yutong.clw.ygbclient.common.beans.news.NewsParam;
import com.yutong.clw.ygbclient.common.beans.news.NewsReturnInfo;
import com.yutong.clw.ygbclient.common.enums.news.NewsType;
import com.yutong.clw.ygbclient.common.enums.news.PageFlag;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;
import com.yutong.clw.ygbclient.dao.setting.NewsInfoDao;
import com.yutong.clw.ygbclient.view.bean.push.PushBean;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.setting.announcement.BizNews;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView.OnFooterRefreshListener;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView.OnHeaderRefreshListener;

/**
 * 公告页面
 * 
 * @author zhouzc
 */
public class AnnounceListActivity extends RemindAccessActivity implements OnClickListener{
	
	private static int LOADING_FLAG = 0x0;
	
    private List<NewsInfo> newsInfoBeans;

    private List<NewsInfo> allNewsInfos;

    /** 下拉刷新对象 */
    private PullToRefreshView pullToRefreshView;

    ArrayList<HashMap<String, Object>> listItem;

    private ListView listView;

    SimpleAdapter listItemAdapter;

    // 数据获取
    private BizNews biz;

    // 当前新闻摘要信息
    private NewsReturnInfo currentSummary;

    // 新闻数据库
    private NewsInfoDao newsInfosDao;

    private NewsParam param;

    private TextView news_tips_TV;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        listenAnnounce = true;
        setContentView(R.layout.activity_setting_announce);
        initViews();
        initBiz();
        initListener();
        initData();
    }

    private void initBiz() {
    	biz = new BizNews(this);
	}

	private void initData()
    {
        allNewsInfos = new ArrayList<NewsInfo>();
        newsInfosDao = new NewsInfoDao(this);
        
        showLoading("正在加载新闻数据...", LOADING_FLAG);
        pullToRefreshView.setFooterVisible(false);
        
        param = new NewsParam();
        param.setLast_id("0");
        param.setNewsType(NewsType.Notice);
        param.setPageFlag(PageFlag.First);
        param.setPageTime("0");
        param.setReq_num(SysConfigUtil.getPageSize());
        // 加载新闻
        loadNewsInfo(param);
    }

    /**
     * 加载新闻
     * 
     * @author lizyi 2013-11-10 下午3:16:06
     * @param param
     */
    private void loadNewsInfo(NewsParam param)
    {
        // 从服务器接口
        biz.getNewsSummary(param, new BizResultProcess<NewsReturnInfo>()
        {

            @Override
            public void onBizExecuteError(Exception exception, Error error){

            	dismissLoading(LOADING_FLAG);
                HandleLogicErrorInfo(exception);
            }

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, final NewsReturnInfo info)
            {
                runOnUiThread(new Runnable(){

                    @Override
                    public void run(){
                    	
                        dismissLoading(LOADING_FLAG);
                        if (info == null){
                            return;
                        }
                        currentSummary = info;
                        if (currentSummary.infos != null && currentSummary.infos.size() > 0){
                        	
                            /*pullToRefreshView.setFooterVisible(true);*/
                            newsInfoBeans = currentSummary.infos;
                            allNewsInfos.addAll(newsInfoBeans);
                            listView.setVisibility(View.VISIBLE);
                            loadNews();
                            news_tips_TV.setVisibility(View.GONE);
                        }else{
                        	listView.setVisibility(View.GONE);
                        	news_tips_TV.setVisibility(View.VISIBLE);	
                        }

                    }
                });

            }
        });
    }

    private void loadNews()
    {

        // listItem.clear();
        if (newsInfoBeans != null)
        {
            for (NewsInfo news : newsInfoBeans)
            {
                HashMap<String, Object> map = new HashMap<String, Object>();

                // if (!news.is_read)
                // {
                // map.put("ItemImage", R.drawable.ic_new_news);//
                // 图像资源图片，显示在小项右端
                // }
                // 检查是否已读
                
                if (!newsInfosDao.isExist(news.id, ProxyManager.getInstance(AnnounceListActivity.this).getUserCode())){
                    map.put("ItemImage", R.drawable.ic_new_news);// 图像资源图片，显示在小项右端
                }else{
                	map.remove("ItemImage");
                }
                
                map.put("ItemTitle", news.title);

                String dateStr = "";
                if (DateUtils.isToday(news.publish_time)){
                	
                    dateStr = "今天" + DateUtils.dateToStr(news.publish_time, "HH:mm");
                }else if (DateUtils.isYesterday(news.publish_time)){
                    dateStr = "昨天" + DateUtils.dateToStr(news.publish_time, "HH:mm");
                }else{
                    dateStr = DateUtils.dateToStr(news.publish_time, "yyyy/MM/dd HH:mm");
                }

                map.put("ItemDesc", dateStr);
                map.put("ItemContent", news.summary);

                map.put("newsId", news.id);

                listItem.add(map);// 添加到listItem中
            }
        }
        
        announceAdapter.notifyDataSetChanged();
    }

    private void refreshNews(){
    	
        if (listItem != null && listItem.size()>0){
            for (HashMap<String, Object> map : listItem){
                String newsId = map.get("newsId").toString();
                
                Log.i("TAG", "newsId = "+ newsId);
                
                // 检查是否已读
                if (newsInfosDao.isExist(newsId, ProxyManager.getInstance(AnnounceListActivity.this).getUserCode())){
                    map.remove("ItemImage");
                }
            }
            announceAdapter.notifyDataSetChanged();
        }
    }
    
    private AnnounceAdapter announceAdapter;
    
    public void initViews(){
    	
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        listView = (ListView) findViewById(R.id.ListView_News);

        // 生成动态数组，加入数据
        listItem = new ArrayList<HashMap<String, Object>>();

        /*listItemAdapter = new SimpleAdapter(this,
                listItem,
                R.layout.listitem_news,
                new String[] { "ItemImage", "ItemTitle", "ItemDesc", "ItemContent" },
                new int[] { R.id.NewItemImage, R.id.NewItemTitle, R.id.NewItemDesc, R.id.NewItemContent });
        
        list.setAdapter(listItemAdapter);*/
        
        announceAdapter = new AnnounceAdapter(listItem, AnnounceListActivity.this);
        listView.setAdapter(announceAdapter);
        
        news_tips_TV =  (TextView) findViewById(R.id.news_tips);
    }

    private void initListener(){
    	
        // 添加点击
    	listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            // 重写单击响应
            public void onItemClick(AdapterView<?> arg0, View arg1, int poisition, long arg3){
            	
            	/*用户行为统计-*/
                new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_GONGGAOMINGXI).reportBehavior(null);
                
                Intent intent = new Intent(getApplication(), AnnounceDetailActivity.class);
                Bundle bundle = new Bundle();
                try{
                    bundle.putSerializable(ActivityCommConstant.NEWS_INFO, allNewsInfos.get(poisition));
                }catch (Exception e){
                    return;
                }
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
            }
        });

        pullToRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener(){
            @Override
            public void onHeaderRefresh(PullToRefreshView view){
                // 从服务器接口
                // TO DO 设置参数
                updateNews();
            }
        });
        
        pullToRefreshView.setOnFooterRefreshListener(new OnFooterRefreshListener()
        {
            @SuppressWarnings("unchecked")
            @Override
            public void onFooterRefresh(PullToRefreshView view)
            {
                // 从服务器接口
                // TO DO 设置参数
            	
            	pullToRefreshView.setFooterVisible(true);
            	
                param.setNewsType(NewsType.Notice);
                param.setPageFlag(PageFlag.Next);
                param.setReq_num(SysConfigUtil.getPageSize());

                if (newsInfoBeans != null && newsInfoBeans.size() > 0){
                	
                    List<NewsInfo> searchNews = new ArrayList<NewsInfo>();
                    searchNews.addAll(newsInfoBeans);
                    Collections.sort(searchNews, new GetLastNewsIdComparator());

                    NewsInfo info = searchNews.get(0);
                    param.setLast_id(info.id);

                    Collections.sort(searchNews, new GetLastTimeIdComparator());

                    info = searchNews.get(0);
                    param.setPageTime(DateUtils.dateToStr(info.publish_time, "yyyyMMddHHmmss"));
                }else{
                	
                    pullToRefreshView.onFooterRefreshComplete();
                    return;
                }

                biz.getNewsSummary(param, new BizResultProcess<NewsReturnInfo>()
                {

                    @Override
                    public void onBizExecuteError(Exception exception, Error error)
                    {
                       
                    	/*pullToRefreshView.setFooterVisible(false);*/
                        pullToRefreshView.onFooterRefreshComplete();
                        HandleLogicErrorInfo(exception);
                    }

                    @Override
                    public void onBizExecuteEnd(BizDataTypeEnum datatype, final NewsReturnInfo info)
                    {
                        runOnUiThread(new Runnable()
                        {

                            @Override
                            public void run()
                            {
                            	/*pullToRefreshView.setFooterVisible(false);*/
                                dismissLoading(LOADING_FLAG);
                               
                                if (info == null)
                                {
                                    return;
                                }
                                currentSummary = info;

                                newsInfoBeans.clear();

                                if (currentSummary.infos != null && currentSummary.infos.size() > 0)
                                {
                                    newsInfoBeans = currentSummary.infos;
                                    allNewsInfos.addAll(newsInfoBeans);
                                    loadNews();
                                }
                                pullToRefreshView.onFooterRefreshComplete();
                            }
                        });

                    }
                });
            }
        });
    }

    protected void updateNews()
    {
        pullToRefreshView.setHeaderRefreshing();
        param.setNewsType(NewsType.Notice);
        param.setPageFlag(PageFlag.First);
        param.setReq_num(SysConfigUtil.getPageSize());

        param.setLast_id("0");
        param.setPageTime("0");

        biz.getNewsSummary(param, new BizResultProcess<NewsReturnInfo>()
        {

            @Override
            public void onBizExecuteError(Exception exception, Error error)
            {
                
                dismissLoading(0);
                pullToRefreshView.onHeaderRefreshComplete();
                HandleLogicErrorInfo(exception);
            }

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, final NewsReturnInfo info)
            {
                runOnUiThread(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        // dismissLoading(0);
                        pullToRefreshView.onHeaderRefreshComplete();
                        if (info == null)
                        {
                            return;
                        }
                        currentSummary = info;

                        // 清空当前界面列表数据
                        allNewsInfos.clear();
                        listItem.clear();
                        newsInfoBeans.clear();

                        if (currentSummary.infos != null && currentSummary.infos.size() > 0)
                        {
                            newsInfoBeans = currentSummary.infos;
                            allNewsInfos.addAll(newsInfoBeans);
                            loadNews();
                        }
                    }
                });

            }
        });

    }

    private class GetLastNewsIdComparator implements Comparator
    {

        public int compare(Object arg0, Object arg1)
        {
            NewsInfo news1 = (NewsInfo) arg0;
            NewsInfo news2 = (NewsInfo) arg1;
            
            
            int flag = Integer.valueOf(news1.id).compareTo(Integer.valueOf(news2.id));
            
            
            return flag;
        }

    }

    private class GetLastTimeIdComparator implements Comparator
    {

        public int compare(Object arg0, Object arg1)
        {
            NewsInfo news1 = (NewsInfo) arg0;
            NewsInfo news2 = (NewsInfo) arg1;
            
        
            Long l1 = news1.publish_time.getTime();

            Long l2 = news2.publish_time.getTime();
            
            int flag = l1.compareTo(l2);

            return flag;
        }

    }

    @Override
    protected void onResume()
    {

        refreshNews();
        super.onResume();
    }

    @Override
    protected void onPause(){
        // TODO Auto-generated method stub
        super.onPause();
        PushMsgUtil.deleteNewsNotificationInfo();

    }

    @Override
    protected void onReceivedPushMessage(PushBean msg)
    {
        super.onReceivedPushMessage(msg);
        switch (msg.getType())
        {
        case News:
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    PushMsgUtil.deleteNewsNotificationInfo();
                    updateNews();
                }
            });
            break;
        default:
            break;
        }

    }

    @Override
    protected boolean hasTitle()
    {
        return true;
    }

    @Override
    protected void loadTitleByPageSetting(Button btn_left, Button btn_right, RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
            TextView tv_small)
    {
        btn_left.setBackgroundResource(R.drawable.bg_prevbtn);
        btn_right.setVisibility(View.INVISIBLE);
        tv_large.setText("公告信息");
        tv_small.setVisibility(View.GONE);
        iv_tri.setVisibility(View.GONE);

        btn_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.btn_it_left:
            finish();
            break;

        default:
            break;
        }

    }
}
