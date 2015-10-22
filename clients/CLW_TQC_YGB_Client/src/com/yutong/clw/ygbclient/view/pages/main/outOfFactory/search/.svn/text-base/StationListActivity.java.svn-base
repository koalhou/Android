package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.context.ContextUtil;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.outOfFactory.BizOutOfFactory;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.adapter.PinyinComparator;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.adapter.StationsAdapter;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation.SingleStationScheduleActivity;
import com.yutong.clw.ygbclient.view.widget.CustomDialog;
import com.yutong.clw.ygbclient.view.widget.PopCheckList;
import com.yutong.clw.ygbclient.view.widget.PopCheckList.OnCheckChangedListener;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView.OnHeaderRefreshListener;

/**
 * 站点区域下站点列表界面
 * 
 * @author zhangyongn 2013-11-29 上午11:12:09
 */
public class StationListActivity extends RemindAccessActivity implements OnClickListener
{

    private ListView stationListView;

    private PullToRefreshView pullToRefreshView;

    /* 站点列表 */
    private List<StationInfo> stationInfoList = new ArrayList<StationInfo>();

    private StationsAdapter stationsAdapter;

    /* 早晚班选择 */
    private PopCheckList popcl;

    private TextView tv_large, tv_small, searchResultTV;

    /* 撤销按钮、地图按钮 */
    private Button btn_left, btn_it_right;

    /* 早晚班选择区域、收藏 */
    private RelativeLayout rl_center, favoriteLeftRL;

    private int currentLineIndex = -1;

    /* 接收intent传进来的参数 */
    private String areaId, areaName;

    private AreaType areaType;

    private StatusRange statusRange;

    private BizOutOfFactory bizOutOfFactory;

    private PinyinComparator pinyinComparator = new PinyinComparator();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_outoffactory_search_stationlist);
        
        initViews();
        initBiz();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //showLoading("加载数据,请稍候...", 1);
        loadData();
    }

    private Context getContext()
    {
        return this;
    }

    public void initViews()
    {
        searchResultTV = (TextView) findViewById(R.id.searchResultTV);

        stationListView = (ListView) findViewById(R.id.stationList);
        /* favoriteLeftRL.setOnClickListener(this); */
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setPullUpEnable(false);
        pullToRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener()
        {

            @Override
            public void onHeaderRefresh(PullToRefreshView view)
            {
                loadData();

            }
        });
        rl_center = (RelativeLayout) findViewById(R.id.rl_it_center);
        btn_left = (Button) findViewById(R.id.btn_it_left);
        btn_it_right = (Button) findViewById(R.id.btn_it_right);
        tv_large = (TextView) findViewById(R.id.tv_it_centerup);
        tv_small = (TextView) findViewById(R.id.tv_it_centerdown);

        if (popcl == null)
        {
            popcl = new PopCheckList(this);
            popcl.setOnCheckChangedListener(new OnCheckChangedListener()
            {
                @Override
                public void OnChecked(int index, String txt)
                {

                    if( currentLineIndex == index){
                    	popcl.dismiss();
                    	return;
                    }
                    
                	tv_small.setText(txt);
                    if (index == 0)
                    {
                        statusRange = StatusRange.MorningWork;
                    }
                    else
                    {
                        statusRange = StatusRange.NightWork;
                    }
                    stationListView.setTag(statusRange);
                    loadData();

                    currentLineIndex = index;

                    popcl.dismiss();
                }
            });
            popcl.setOnDismisslistener(new OnDismissListener()
            {
                @Override
                public void onDismiss()
                {
                    // TODO title里面的小三角可能要变色
                }
            });

            // 测试数据
            List<String> names = new ArrayList<String>();
            names.add("早班");
            names.add("晚班");
            
            currentLineIndex = statusRange == StatusRange.MorningWork ? 0 : 1;
            popcl.setData(names, currentLineIndex);

        }
        stationListView.setTag(statusRange);
        stationsAdapter = new StationsAdapter(stationInfoList, getContext(), stationListView);
        stationListView.setAdapter(stationsAdapter);
        stationListView.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
            	
            }

        });
        initListener();
    }

    /* 业务类 */
    private void initBiz()
    {

        bizOutOfFactory = new BizOutOfFactory(getContext());
    }

    /* 初始加载数据 */
    public void loadData()
    {
        pullToRefreshView.setHeaderRefreshing();
        /* 服务器数据 */
        /*
         * getAreaStations(final AreaType areaType, final StatusRange
         * statusRange, final String belong_area_id,
         * BizResultProcess<List<StationInfo>> bizResultProcess)
         */
        bizOutOfFactory.getAreaStations(areaType, statusRange, areaId, new BizResultProcess<List<StationInfo>>()
        {

            @SuppressWarnings("unchecked")
            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, List<StationInfo> t)
            {

                stationInfoList.clear();
                if (t != null && t.size() > 0)
                {
                    Logger.i(getClass(), "加载数据成功");
                    
                    Collections.sort(t, pinyinComparator);
                    
                    for (StationInfo stationInfo : t)
                    {

                        String stationName = stationInfo.getName();
                        if (stationName.length() > 10)
                        {
                            stationInfo.setName(stationName.substring(0, 10));
                        }
                    }

                    stationInfoList.addAll(t);
                    runOnUiThread(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            pullToRefreshView.onHeaderRefreshComplete();
                            searchResultTV.setVisibility(View.GONE);
                            stationsAdapter.notifyDataSetChanged();
                        }
                    });
                }
                else
                {
                    runOnUiThread(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            pullToRefreshView.onHeaderRefreshComplete();
                            searchResultTV.setVisibility(View.VISIBLE);
                        }
                    });
                }
                dismissLoading(1);
            }

            @Override
            public void onBizExecuteError(final Exception exception, Error error)
            {

                runOnUiThread(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        pullToRefreshView.onHeaderRefreshComplete();

                        ToastMessage("获取站点信息失败");
                        HandleLogicErrorInfo(exception);
                    }
                });
            }
        });

    }

    private void initFromIntent(Intent intent)
    {
        if (intent != null && intent.getExtras() != null)
        {

            Bundle bundle = intent.getExtras();
            areaId = bundle.getString("id");
            areaName = bundle.getString("name");
            areaType = bundle.getInt("areaType") == 1 ? AreaType.FirstFactory : AreaType.SecondFactory;
            statusRange = bundle.getInt("statusRange") == 0 ? StatusRange.MorningWork : StatusRange.NightWork;
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

        initFromIntent(getIntent());

        btn_left.setBackgroundResource(R.drawable.bg_prevbtn);
        btn_right.setBackgroundResource(R.drawable.bg_mapbtn);
        btn_right.setVisibility(View.VISIBLE);

        tv_large.setText(areaName);
        tv_small.setText(statusRange == StatusRange.MorningWork ? "早班" : "晚班");

        btn_left.setOnClickListener(this);
        rl_center.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {

        case R.id.btn_it_left:
            onBackPressed();
            break;

        case R.id.rl_it_center:
            showOrDismissPopLines();
            break;
        case R.id.btn_it_right:
            String areaId_0 = areaId;
            StatusRange statusRange_0 = statusRange;

            Bundle bundle = new Bundle();
            bundle.putString("areaId", areaId_0);
            bundle.putString("name", areaName);
            bundle.putSerializable("areaType", areaType);
            bundle.putSerializable("statusRange", statusRange_0);

            ContextUtil.alterActivity((Activity) getContext(), StationMapActivity.class, bundle);

            break;

        default:
            ;
        }
    }

    // 弹出对话框
    private void showOrDismissPopLines()
    {
        if (popcl != null)
        {
            View center = findViewById(R.id.rl_it_center);
            int xoffset = (center.getWidth() - popcl.getWidth()) / 2;
            popcl.showAsDropDown(findViewById(R.id.rl_it_center), xoffset, 2);
        }
    }

    /* 为addapter初始化监听器 */
    private void initListener()
    {

        stationsAdapter.setSiteFavorClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                StationInfo site = (StationInfo) v.getTag(R.id.tag_station);
                if (site == null)
                {
                    Logger.e(this.getClass(), "站点点击：站点为null。");
                    return;
                }
                showFavoriteConfirmDialog(site, site.isFavorites());
            }
        });

        stationsAdapter.setRigthtClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
            	
            	/*用户行为统计-查看站点发车安排*/
                new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_DJZD).reportBehavior(null);

                StationInfo stationInfo = (StationInfo) v.getTag(R.id.tag_station);

                Bundle bundle = new Bundle();
                bundle.putSerializable(ActivityCommConstant.STATION_INFO, stationInfo);
                bundle.putSerializable(ActivityCommConstant.AREATYPE, areaType);
                bundle.putSerializable(ActivityCommConstant.STATUSRANGE, statusRange);
                bundle.putSerializable(ActivityCommConstant.DATE, new Date());
                bundle.putSerializable(ActivityCommConstant.ISFAVOR, stationInfo.isFavorites());
                bundle.putSerializable(ActivityCommConstant.ISCLOCK, stationInfo.isStatus());

                ContextUtil.alterActivity((Activity) getContext(), SingleStationScheduleActivity.class, bundle);
            }
        });

    }

    protected void showFavoriteConfirmDialog(final StationInfo site, final boolean isFavor)
    {
        if (site == null)
            return;

        String title = isFavor ? "取消收藏" : "添加收藏";
        String body = isFavor ? "是否取消收藏？" : "是否添加收藏？";
        final String hintStr = isFavor ? "正在取消收藏..." : "正在添加收藏...";

        CustomDialog dia = new CustomDialog.Builder(this).setTitle(title).setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
            	new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_SCZD).reportBehavior(null);
                
            	dialog.dismiss();
                SubmitFavorite(site, !isFavor);
                showLoading(hintStr, 1);
            }
        }).setMessage(body).create();

        dia.show();

    }

    /**
     * 提交收藏状态
     * 
     * @author zhangyongn 2013-11-14 下午2:33:04
     * @param site 站点
     * @param favor 要设置的收藏状态，true：表示收藏，false：表示不收藏
     */
    protected void SubmitFavorite(StationInfo site, boolean favor)
    {
        BizOutOfFactory biz = new BizOutOfFactory(this);
        biz.setStationFavority(site.id, favor, new BizResultProcess<Boolean>()
        {

            @Override
            public void onBizExecuteError(Exception exception, Error error)
            {
                ToastMessage("收藏失败！");
                HandleLogicErrorInfo(exception);
            }

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, Boolean t)
            {
                runOnUiThread(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        /*
                         * loadFavoriteStations(p_currentAreaType,
                         * p_currentRange);
                         */
                        /* ToastMessage("收藏成功~"); */

                        loadData();
                    }
                });

            }
        });
    }

    private static class StationComparator implements Comparator
    {

        private Comparator cpt = Collator.getInstance(java.util.Locale.CHINA);

        @Override
        public int compare(Object lhs, Object rhs)
        {

            StationInfo o1 = (StationInfo) lhs;
            StationInfo o2 = (StationInfo) rhs;
            return cpt.compare(o1.getName(), o2.getName());
        }
    }

}
