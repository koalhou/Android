/**
 * @公司名称：YUTONG
 * @作者：zhangyongn
 * @版本号：1.0
 * @生成日期：2013-11-4 下午2:27:29
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.control;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.view.bean.IViewSwitcher;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView.OnFooterRefreshListener;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView.OnHeaderRefreshListener;

/**
 * @author zhangyongn 2013-11-4 下午2:27:29 推荐站点列表控件
 */
public class ConRecommandStation extends LinearLayout implements IViewSwitcher
{
    private List<StationInfo> sites = new ArrayList<StationInfo>();

    /** 下拉刷新对象 */
    private PullToRefreshView pullToRefreshView;

    private ListView list_view;

    private View footer;
    
    private RecommandSiteAdapter adapter;
    
    
    /**
     * 厂区
     */
    private AreaType facortyType;

    /**
     * 班车类型：早班，晚班
     */
    private StatusRange workPlan;

    public ConRecommandStation(Context context)
    {
        this(context, null);

    }

    public ConRecommandStation(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        if (isInEditMode())
            return;
        LayoutInflater.from(context).inflate(R.layout.control_main_outoffactory_recommandsite, this, true);
        initViews();
        initListView();
        registerListener();

    }

    public void setStations(List<StationInfo> stations)
    {
        this.sites = stations;
        this.adapter.SetDatas(sites);
        this.adapter.notifyDataSetChanged();
    }

    private void initListView()
    {
        footer = LayoutInflater.from(YtApplication.getInstance()).inflate(R.layout.listitem_recommandsite_footer, null);
        list_view.addFooterView(footer);
        adapter = new RecommandSiteAdapter(YtApplication.getInstance());

        list_view.setAdapter(adapter);
        list_view.setDivider(null);

    }

    private void registerListener()
    {
        

    }

    private void initViews()
    {
        list_view = (ListView) findViewById(R.id.list_view);
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        getPullToRefreshView().setPullUpEnable(false);
    }

    /**
     * 设置下拉刷新侦听
     * 
     * @author zhangyongn 2013-11-12 下午2:03:33
     * @param listener
     */
    public void setOnHeaderRefreshListener(OnHeaderRefreshListener listener)
    {
        if (getPullToRefreshView() != null)
            getPullToRefreshView().setOnHeaderRefreshListener(listener);
    }

    /**
     * 设置上拉侦听
     * 
     * @author zhangyongn 2013-11-12 下午2:03:45
     * @param listener
     */
    public void setOnFooterRefreshListener(OnFooterRefreshListener listener)
    {
        if (getPullToRefreshView() != null)
            getPullToRefreshView().setOnFooterRefreshListener(listener);
    }

    /**
     * @param favoriteClickListener 要设置的 favoriteClickListener
     */
    public void setFavoriteClickListener(View.OnClickListener favoriteClickListener)
    {
        
        adapter.setFavoriteClickListener(favoriteClickListener);

    }

    /**
     * @param siteClickListener 要设置的 siteClickListener
     */
    public void setSiteClickListener(View.OnClickListener siteClickListener)
    {
        
        adapter.setSiteClickListener(siteClickListener);
    }

    @Override
    public void switchOut()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void switchIn()
    {
        // TODO Auto-generated method stub

    }

    /**
     * @return pullToRefreshView
     */
    public PullToRefreshView getPullToRefreshView()
    {
        return pullToRefreshView;
    }
}
