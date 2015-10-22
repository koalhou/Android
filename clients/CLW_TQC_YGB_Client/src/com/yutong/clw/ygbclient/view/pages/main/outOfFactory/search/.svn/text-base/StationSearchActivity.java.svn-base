package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationAreaInfo;
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
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.adapter.StationAreasSearchAdapter;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.search.adapter.StationHintAdapter;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation.SingleStationScheduleActivity;
import com.yutong.clw.ygbclient.view.util.DensityUtil;
import com.yutong.clw.ygbclient.view.widget.PopCheckList;
import com.yutong.clw.ygbclient.view.widget.PopCheckList.OnCheckChangedListener;

/**
 * 站点搜索界面,搜索首页
 * 
 * @author zhouzc
 */
public class StationSearchActivity extends RemindAccessActivity implements OnClickListener
{

	private final String ValidStringLoignEmpCode = "[$NoEmptyRule$][$Len(1,50,<@数字位数必须在1到50之间@>)$]";
	
    private static final int SEARCH_SUCCESS = 1, SEARCH_FAILED = 0;

    private static final int LOAD_SUCCESS = 1, LOAD_FAILED = 0,LOAD_NO_STATION_DATA = 2;

    private static final int HIDE = 2, SHOW = 3;

    public static int AreaType_tag = 1;
    
    private RelativeLayout rl_center;

    private Button btn_left;

    private TextView tv_large, tv_small;

    private Context context;

    private PopCheckList popcl;

    private int lastSelectedIndex = 0;
    
    private int popclWidth = 120;
    
    private GridView stationAreasGridView;

    private EditText stationSearchEdit;

    private BizOutOfFactory bizOutOfFactory;

    private StationAreasSearchAdapter mStationAreasSearchAdapter;

    private List<StationAreaInfo> stationAreaInfoList = new ArrayList<StationAreaInfo>();

    private RelativeLayout mapChoosePointRL;

    private boolean focusFlag = true;

    private ListView searchResultListView;

    private StationHintAdapter stationHintAdapter; // 搜索提示adapter

    private List<StationInfo> stationInfoList_FromServer_bak = new ArrayList<StationInfo>();
    
    private List<StationInfo> stationInfoList_FromServer = new ArrayList<StationInfo>();

    private List<StationInfo> stationInfoList_FromSharePref = new ArrayList<StationInfo>();

    private List<StationInfo> stationInfoList = new ArrayList<StationInfo>();

    private InputMethodManager manager;

    private ImageView deleteIma;

    private SharedPreferences settings;

    private Editor editor;

    private boolean isFirstShow = true;

    private String userCode = ProxyManager.getInstance(getContext()).getUserCode();

    private AreaType areaType = AreaType.FirstFactory;/* 厂区 */

    private StatusRange statusRange = StatusRange.MorningWork;/* 早晚班 */

    private boolean isInMain = true;

    private int maxHistoryItem = 5;

    private boolean showClearBtn = true;
    
    private String search = "";

    public void readSharedPreferences()
    {

        Logger.i(getClass(), "[从sharePref 配置文件中读取历史搜索记录]" + "userCode:" + userCode);

        if (userCode != null && !userCode.equals(""))
        {
            settings = getSharedPreferences(userCode+"_"+AreaType.myValueOf(areaType.value())+"_"+StatusRange.myValueOf(statusRange.value()), 0);
            editor = settings.edit();
        }
    }

    private Context getContext()
    {
        this.context = this;
        return this;
    }

    private void initBiz()
    {
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        bizOutOfFactory = new BizOutOfFactory(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_outoffactory_search_stationsearch);
        readSharedPreferences();
        initBiz();
        initViews();
        loadData();
    }

    private void initFromIntent() {
    	
    	Intent intent = getIntent();
    	if (intent != null && intent.getExtras() != null) {
			Bundle bundle = intent.getExtras();
			areaType = (AreaType) bundle.getSerializable(ActivityCommConstant.AREATYPE);
			statusRange = (StatusRange) bundle.getSerializable(ActivityCommConstant.STATUSRANGE);
    	}	
	}

	@Override
    protected void onResume()
    {

        super.onResume();

        if (isInMain)
        {
            searchResultListView.setVisibility(View.GONE);
            stationAreasGridView.setVisibility(View.VISIBLE);
            mapChoosePointRL.setVisibility(View.VISIBLE);
        }

    }

    private void loadHistorySearchData()
    {
    	stationInfoList_FromSharePref.clear();
        /* 加载历史站点 */
    	Map m = settings.getAll();
        for(int i=0;i<m.size();i++){
        	
        	  Object value = m.get(i+"");
        	  Gson gson = new Gson();
              StationInfo stationInfo = gson.fromJson(value.toString(), StationInfo.class);
              stationInfo.setSearchHistory(true);

              stationInfoList_FromSharePref.add(stationInfo);
        }
    }

    public void initViews()
    {

        showLoading("加载数据,请稍候...", 0);
        // 默认软键盘不弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        stationSearchEdit = (EditText) findViewById(R.id.stationSearchEdit);
        loadSearchEditHintText();
        stationSearchEdit.setOnClickListener(this);
        stationSearchEdit.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {

                if (hasFocus)
                {

                    if (isFirstShow)
                    {
                        stationInfoList.clear();

                        if (stationInfoList_FromSharePref != null && stationInfoList_FromSharePref.size() > 0)
                        {

                            stationInfoList.addAll(stationInfoList_FromSharePref);

                            showClearBtn = true;
                            searchResultListView.setTag(showClearBtn);

                            stationHintAdapter.notifyDataSetChanged();

                            searchResultListView.setVisibility(View.VISIBLE);
                            stationAreasGridView.setVisibility(View.GONE);
                            mapChoosePointRL.setVisibility(View.GONE);

                            isFirstShow = false;
                            isInMain = false;
                        }
                    }
                }
            }
        });

        rl_center = (RelativeLayout) findViewById(R.id.rl_it_center);
        btn_left = (Button) findViewById(R.id.btn_it_left);
        tv_large = (TextView) findViewById(R.id.tv_it_centerup);
        tv_small = (TextView) findViewById(R.id.tv_it_centerdown);

        deleteIma = (ImageView) findViewById(R.id.deleteIma);
        deleteIma.setOnClickListener(this);

        searchResultListView = (ListView) findViewById(R.id.searchResultList);

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headView = inflater.inflate(R.layout.control_stationlist_headerview, null);

        stationAreasGridView = (GridView) findViewById(R.id.stationAreas);

        mapChoosePointRL = (RelativeLayout) findViewById(R.id.mapChoosePointRL);
        mapChoosePointRL.setOnClickListener(this);
    }

    LayoutInflater inflater;

    View headView;

    private Handler searchHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == SEARCH_SUCCESS)
            {

                runOnUiThread(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        showClearBtn = false;
                        searchResultListView.setTag(showClearBtn);
                        stationHintAdapter.notifyDataSetChanged();
                        searchResultListView.setVisibility(View.VISIBLE);
                        /*stationAreasGridView.setVisibility(View.GONE);*/
                        if (isInMain)
                        {
                        	stationAreasGridView.setVisibility(View.VISIBLE);
                            mapChoosePointRL.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                        	stationAreasGridView.setVisibility(View.GONE);
                            mapChoosePointRL.setVisibility(View.GONE);
                        }
                    }
                });
            }
            else
            {
                Toast.makeText(getContext(), "搜索失败", 0).show();
            }
        }
    };

    /*
     * 点击一个item后从服务器端查出的list不进行数据的删减，但是本地中读取的list进行变化
     */
    private class saveItemRunnable implements Runnable
    {

        int index;

        public saveItemRunnable(int index)
        {
            this.index = index;
        }

        @Override
        public void run()
        {

            int flag = 0;

            if ((Boolean) searchResultListView.getTag())
            {
                index--;
            }

            /* 加Header后修改Index值 */
            StationInfo mapItem_0 = stationInfoList.get(index);
            StationInfo mapItem = null;

            /* 克隆对象 */
            try
            {
                mapItem = (StationInfo) mapItem_0.clone();
            }
            catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
                Logger.e(getClass(), "StationInfo 对象浅拷贝出错");
            }
            /* 修改历史标志 */
            mapItem.setSearchHistory(true);

            /* 添加到内存的历史站点列表 */
            if (stationInfoList_FromSharePref == null)
            {
                stationInfoList_FromSharePref = new ArrayList<StationInfo>();
            }

            List<StationInfo> historyStationtmp = new ArrayList<StationInfo>();

            /* 剔除重复的历史记录 */
            if (stationInfoList_FromSharePref.size() > 0)
            {

                for (int i = 0; i < stationInfoList_FromSharePref.size(); i++, flag++)
                {

                    if (stationInfoList_FromSharePref.get(i).getId().equals(mapItem.getId()))
                    {

                        stationInfoList_FromSharePref.remove(i);

                        /* 记录提前 */
                        stationInfoList_FromSharePref.add(0, mapItem);
                        break;
                    }
                }
                
                /*没有重复的item*/
                if(flag == stationInfoList_FromSharePref.size() && flag == maxHistoryItem){
                	
                	stationInfoList_FromSharePref.remove(flag-1);
                	stationInfoList_FromSharePref.add(0, mapItem);
                }else if (flag == stationInfoList_FromSharePref.size()){
                	 historyStationtmp.add(mapItem);
                }
            }
            else
            {
                historyStationtmp.add(mapItem);
            }

            stationInfoList_FromSharePref.addAll(0, historyStationtmp);

            historyStationtmp.clear();

            // 存sharef配置文件
            /*Gson gson = new Gson();
            Type type = new TypeToken<StationInfo>()
            {
            }.getType();
            editor.putString((String) mapItem.getId(), gson.toJson(mapItem, type));
            editor.commit();*/
            
            editor.clear();
        	editor.commit();
        	
            saveHistoryItem();
        }
    }

    private Handler loadDataHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == LOAD_SUCCESS)
            {

                /* 加载站点区域 */
                bizOutOfFactory.getStationAreas(new BizResultProcess<List<StationAreaInfo>>()
                {

                    @Override
                    public void onBizExecuteError(Exception exception, Error error)
                    {
                    	HandleLogicErrorInfo(exception);
                    }

                    @Override
                    public void onBizExecuteEnd(BizDataTypeEnum datatype, List<StationAreaInfo> t)
                    {
                    	if(t!=null && t.size()>0){
                    		
                    		stationAreaInfoList.clear();
                            stationAreaInfoList.addAll(t);//
                            runOnUiThread(new Runnable()
                            {

                                @Override
                                public void run()
                                {
                                    mStationAreasSearchAdapter.notifyDataSetChanged();
                                    if (isInMain){
                                    	
                                    	searchResultListView.setVisibility(View.GONE);
                                        stationAreasGridView.setVisibility(View.VISIBLE);
                                    	mapChoosePointRL.setVisibility(View.VISIBLE);
                                    	
                                    }else{
                                    	
                                    	searchResultListView.setVisibility(View.VISIBLE);
                                        stationAreasGridView.setVisibility(View.GONE);
                                    	mapChoosePointRL.setVisibility(View.GONE);
                                    }
                                        
                                }
                            });
                    	}else{
                    		Toast.makeText(getContext(), "暂无区域数据", 0).show();
                    	}
                    	dismissLoading(0);
                    }
                });
            }else if(msg.what == LOAD_NO_STATION_DATA){
            	Toast.makeText(getContext(), "暂无站点数据", 0).show();
            	
            	
            	 /* 加载站点区域 */
                /*bizOutOfFactory.getStationAreas(new BizResultProcess<List<StationAreaInfo>>()
                {

                    @Override
                    public void onBizExecuteError(Exception exception, Error error)
                    {
                    	HandleLogicErrorInfo(exception);
                    }

                    @Override
                    public void onBizExecuteEnd(BizDataTypeEnum datatype, List<StationAreaInfo> t)
                    {

                    	if(t!=null && t.size()>0){
                    		
                    		stationAreaInfoList.clear();
                            stationAreaInfoList.addAll(t);//
                            runOnUiThread(new Runnable()
                            {

                                @Override
                                public void run()
                                {
                                    mStationAreasSearchAdapter.notifyDataSetChanged();
                                    if (isInMain)
                                        mapChoosePointRL.setVisibility(View.VISIBLE);
                                    else
                                        mapChoosePointRL.setVisibility(View.GONE);

                                    
                                }
                            });
                    	}else{
                    		Toast.makeText(getContext(), "暂无区域数据", 0).show();
                    	}
                    	dismissLoading(0);
                    }
                });*/
            	dismissLoading(0);
            }
            else
            {
                Toast.makeText(getContext(), "数据加载失败", 0).show();
            }
        }
    };

    /**
     * 页面进入时加载数据
     * 
     * @return
     */
    private void loadData()
    {

        showLoading("加载数据,请稍候...", 0);
        /* 片区列表 */
        stationAreasGridView.setTag(statusRange);
        mStationAreasSearchAdapter = new StationAreasSearchAdapter(getContext(), stationAreaInfoList, stationAreasGridView);

        stationAreasGridView.setAdapter(mStationAreasSearchAdapter);
        stationAreasGridView.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
            	
            	/*用户行为统计-*/
                new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_DJQY).reportBehavior(null);
                
                StationAreaInfo stationAreaInfo = stationAreaInfoList.get(position);

                Bundle bundle = new Bundle();
                bundle.putString("id", stationAreaInfo.getId());
                bundle.putString("name", stationAreaInfo.getName());
                bundle.putInt("areaType", areaType.value());
                bundle.putInt("statusRange", statusRange.value());
                
                ContextUtil.alterActivity((Activity) context, StationListActivity.class, bundle);
            }

        });

        /* 站点搜索框列表 */
        stationHintAdapter = new StationHintAdapter(getContext(), stationInfoList, searchResultListView);
        stationHintAdapter.setDeleteBtnListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                stationInfoList_FromSharePref.clear();
                stationInfoList.clear();
                showClearBtn = false;
                searchResultListView.setTag(showClearBtn);
                stationHintAdapter.notifyDataSetChanged();
                editor.clear();
                if (editor.commit())
                {
                    ToastMessage("搜索历史已清空~");
                }
            }
        });

        searchResultListView.setAdapter(stationHintAdapter);

        // 下拉列表行点击处理
        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
            	
            	/*用户行为统计-厂外通勤搜索站点后查看站点详情*/
                new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_CKZDXQ).reportBehavior(null);
               
                /* stationSearchEdit.setText("");*/
                /* stationSearchEdit.clearFocus(); */

                // 1.本地存储搜索历史
                Thread saveThread = new Thread(new saveItemRunnable(position));
                saveThread.start();

                // 2. 重新组装list数据
                /*
                 * aNewAssemble(search);
                 * stationHintAdapter.notifyDataSetChanged();
                 */

                // 3. 修改标记值
                isInMain = true;

                // 4.跳转发车安排界面
                int positionTmp = position;
                if ((Boolean) searchResultListView.getTag())
                {
                    positionTmp--;
                }
                Bundle bundle = new Bundle();
                StationInfo info = stationInfoList.get(positionTmp);
               
                /* 从实时数据中查找*/
                StationInfo info_FromServer = null;
                for(int i=0;i < stationInfoList_FromServer.size();i++){
                	
                	info_FromServer = stationInfoList_FromServer.get(i);
                	if(info_FromServer.getId().equals(info.getId())){
                		break;
                	}
                }
                
                bundle.putSerializable(ActivityCommConstant.STATION_INFO, info_FromServer);
                bundle.putSerializable(ActivityCommConstant.AREATYPE, areaType);
                bundle.putSerializable(ActivityCommConstant.STATUSRANGE, statusRange);
                bundle.putSerializable(ActivityCommConstant.DATE, new Date());
                bundle.putSerializable(ActivityCommConstant.ISFAVOR, info.favorites);
                bundle.putSerializable(ActivityCommConstant.ISCLOCK, info.isStatus());
                ContextUtil.alterActivity((Activity) getContext(), SingleStationScheduleActivity.class, bundle);
            }
        });

        if (popcl == null)
        {
            popcl = new PopCheckList(this);
            popcl.setOnCheckChangedListener(new OnCheckChangedListener()
            {
                @Override
                public void OnChecked(int index, String txt)
                {
                    tv_small.setText(txt);
                    if(lastSelectedIndex == index){
                    	popcl.dismiss();
                    	return;
                    }
                    
                    lastSelectedIndex = index;
                    if (index == 0)
                    {
                        statusRange = StatusRange.MorningWork;
                        stationSearchEdit.setHint("输入乘车站点");
                    }
                    else
                    {
                        statusRange = StatusRange.NightWork;
                        stationSearchEdit.setHint("输入下车站点");
                    }
                    /*重新加载所属早晚班历史数据*/
                    readSharedPreferences();
                    loadHistorySearchData();
                    
                    /* 重新加载数据 */
                    stationAreasGridView.setTag(statusRange);
                    showLoading("加载数据,请稍候...", 0);
                    getStationAndAreaData(areaType, statusRange);
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
            
            lastSelectedIndex = statusRange== StatusRange.MorningWork ? 0:1;
            popcl.setData(names, lastSelectedIndex);
        }

        /* 加载历史站点数据后进行处理 */
        loadHistorySearchData();

        /* 加载网络站点和片区数据 */
        getStationAndAreaData(areaType, statusRange);

        /* 搜索框关键字搜索 */
        stationSearchEdit.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void afterTextChanged(Editable s)
            {
            	
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            	
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                if (!stationSearchEdit.getText().toString().trim().equals("")&& 
                		stationSearchEdit.getText().toString().trim().length() > 0	
                	)
                {
                    search = stationSearchEdit.getText().toString().trim().replace("%", "\\%").replace("_", "\\_");
                }
                else
                {
                	search = "";
                	stationInfoList.clear();
                    stationHintAdapter.notifyDataSetChanged();
                    
                    if (stationInfoList_FromSharePref != null && stationInfoList_FromSharePref.size() > 0)
                    {
                        if(stationInfoList_FromSharePref!=null && stationInfoList_FromSharePref.size()>0){
                        	stationInfoList.clear();
                        	stationInfoList.addAll(stationInfoList_FromSharePref);

                            showClearBtn = true;
                            searchResultListView.setTag(showClearBtn);

                            stationHintAdapter.notifyDataSetChanged();

                            searchResultListView.setVisibility(View.VISIBLE);
                            stationAreasGridView.setVisibility(View.GONE);
                            mapChoosePointRL.setVisibility(View.GONE);

                            isFirstShow = false;
                            isInMain = false;
                        }
                    }
                    
                	return;
                }
                final String searchKey = search;

                if (searchKey.length() > 0)
                {
                    deleteIma.setVisibility(View.VISIBLE);

                    Thread searchThread = new Thread(new Runnable()
                    {

                        @Override
                        public void run()
                        {

                            aNewAssemble(searchKey);
                            isInMain = false;
                            searchHandler.sendEmptyMessage(StationSearchActivity.SEARCH_SUCCESS);
                        }
                    });

                    searchThread.start();
                }
                else
                {
                    deleteIma.setVisibility(View.GONE);
                }
            }

        });

        // 忽略回车键事件
        stationSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                return true;
            }
        });

    }

    /* 重新组装数据 */
    private void aNewAssemble(String searchKey)
    {
        if(searchKey!=null && searchKey.equals("")){
        	return;
        }
    	
    	// 搜索下拉list
        stationInfoList.clear();
        stationInfoList_FromServer_bak.clear();
        stationInfoList_FromServer_bak.addAll(stationInfoList_FromServer);
        
        for (int i = 0; i < stationInfoList_FromSharePref.size(); i++)
        {
            String id = (String) stationInfoList_FromSharePref.get(i).getId();

            for (int j = 0; j < stationInfoList_FromServer_bak.size(); j++)
            {
                String id_0 = stationInfoList_FromServer_bak.get(j).getId();
                if (id.equals(id_0))
                {
                	stationInfoList_FromServer_bak.remove(j);
                }
            }
        }
        
        /* 1. 搜索历史 */
        for (int i = 0; i < stationInfoList_FromSharePref.size(); i++)
        {
            StationInfo stationInfo = stationInfoList_FromSharePref.get(i);
            String name = (String) stationInfo.getName();

            if (name.contains(searchKey))
            {
                stationInfoList.add(stationInfo);
            }
        }
        
        /* 2. 搜索非历史站点 */
        for (int i = 0; i < stationInfoList_FromServer_bak.size(); i++)
        {
            StationInfo stationInfo_0 = stationInfoList_FromServer_bak.get(i);
            String name = (String) stationInfo_0.getName();
            
            if (name.contains(searchKey))
            {
                stationInfoList.add(stationInfo_0);
            }
        }
        
    }

    public void getStationAndAreaData(AreaType areaType, StatusRange statusRange)
    {

        /* 获取站点数据; */
        bizOutOfFactory.getSearchStations(areaType, statusRange, new BizResultProcess<List<StationInfo>>()
        {

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, List<StationInfo> t)
            {
            	
            	if(t!=null&& t.size()>0){
            		 /* 网络站点数据 */
                	stationInfoList_FromServer_bak.clear();
                    stationInfoList_FromServer.clear();
                    
                    if(t!=null){
                    	stationInfoList_FromServer_bak.addAll(t);
                    	stationInfoList_FromServer.addAll(t);
                    }
                    runOnUiThread(new Runnable() {
						
						@Override
						public void run() {

							aNewAssemble(search);
						}
					});
                    
                    loadDataHandler.sendEmptyMessage(LOAD_SUCCESS);
            	}else{
            		loadDataHandler.sendEmptyMessage(LOAD_NO_STATION_DATA);
            	}
               
            }

            @Override
            public void onBizExecuteError(Exception exception, Error error)
            {
                ToastMessage("站点信息加载失败");
                HandleLogicErrorInfo(exception);
            }
            
        });
        
        
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

    @Override
    protected boolean hasTitle()
    {
        return true;
    }

    @Override
    protected void loadTitleByPageSetting(Button btn_left, Button btn_right, RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
            TextView tv_small)
    {
    	initFromIntent();
    	
        btn_left.setBackgroundResource(R.drawable.bg_prevbtn);
        btn_right.setVisibility(View.GONE);

        tv_large.setText(areaType.getName());
        tv_small.setText(statusRange.getName());

        btn_left.setOnClickListener(this);
        rl_center.setOnClickListener(this);
    }
    
    private void loadSearchEditHintText(){
    	if(statusRange == StatusRange.MorningWork){
    		stationSearchEdit.setHint("输入乘车站点");
    	}else{
    		stationSearchEdit.setHint("输入下车站点");
    	}
    }
    
    /*对历史数据进行保存*/
    private void updateHistoryItem(){
    	
    	if(editor==null){
    		readSharedPreferences();
    	}
    	
    	/*stationInfoList_FromSharePref.clear();*/
    	editor.clear();
    	editor.commit();
    	
    	saveHistoryItem();
    }
    private void saveHistoryItem(){
    	
    	SharedPreferences settings_Morning,settings_Night;

    	Editor editor_Morning,editor_Night;
    	
    	if (userCode != null && !userCode.equals(""))
        {
    		settings_Morning = getSharedPreferences(userCode+"_"+StatusRange.myValueOf(StatusRange.MorningWork.value()), 0);
    		editor_Morning = settings_Morning.edit();
    		
    		settings_Night = getSharedPreferences(userCode+"_"+StatusRange.myValueOf(StatusRange.NightWork.value()), 0);
    		editor_Night = settings_Night.edit();
        }
        
    	int len = stationInfoList_FromSharePref.size();
    	
    	if(stationInfoList_FromSharePref !=null && len >0){
        	
    		for(int i=0; i < len; i++){
        		
        		StationInfo mapItem = stationInfoList_FromSharePref.get(i);
        		if(mapItem.getStatus_range().value() == statusRange.value() ||mapItem.getStatus_range().value() ==  StatusRange.AllWork.value()){
        			
        			Gson gson = new Gson();
                    Type type = new TypeToken<StationInfo>()
                    {
                    }.getType();
                    editor.putString( i+"", gson.toJson(mapItem, type));
                    editor.commit();
        		}
        	}
    	}
    }
    
    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {

        case R.id.rl_it_center:
            showOrDismissPopLines();
            break;

        case R.id.btn_it_left:
        	
        	/*对历史数据进行保存*/
        	updateHistoryItem();
        	
        	finish();
            break;

        case R.id.stationSearchEdit:
            isInMain = false;
            if (stationSearchEdit.isFocusable())
            {
            	if(search.length()>0){
            		deleteIma.setVisibility(View.VISIBLE);
            		stationInfoList.clear();
            		Thread searchThread = new Thread(new Runnable()
            		{
            			@Override
            			public void run()
            			{
            				aNewAssemble(search);
            				isInMain = false;
            				searchHandler.sendEmptyMessage(StationSearchActivity.SEARCH_SUCCESS);
            				
            			}
            		});
            		searchThread.start();
            	}else{
            		stationInfoList.clear();
                    stationHintAdapter.notifyDataSetChanged();
            	}
            }else{
        		stationInfoList.clear();
                stationHintAdapter.notifyDataSetChanged();
                if (stationInfoList_FromSharePref != null && stationInfoList_FromSharePref.size() > 0)
                {

                    stationInfoList.addAll(stationInfoList_FromSharePref);

                    showClearBtn = true;
                    searchResultListView.setTag(showClearBtn);

                    stationHintAdapter.notifyDataSetChanged();

                    searchResultListView.setVisibility(View.VISIBLE);
                    stationAreasGridView.setVisibility(View.GONE);
                    mapChoosePointRL.setVisibility(View.GONE);

                    isFirstShow = false;
                    isInMain = false;
                }
        	}

            stationSearchEdit.setFocusable(true);
           /* stationInfoList.clear();*/
            break;

        case R.id.mapChoosePointRL:
        	
        	/*用户行为统计-地图选点*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_CWTQ_DTXD).reportBehavior(null);

            /* 地图选点参数填充 */
            Bundle bundle = new Bundle();
            bundle.putSerializable("areaType", areaType);
            bundle.putSerializable("statusRange", statusRange);

            ContextUtil.alterActivity((Activity) getContext(), MapSearchActivity.class, bundle);
            break;

        case R.id.deleteIma:
            stationSearchEdit.setText("");
            search = "";
            deleteIma.setVisibility(View.INVISIBLE);

            stationInfoList.clear();
            stationHintAdapter.notifyDataSetChanged();

            stationAreasGridView.setVisibility(View.VISIBLE);
            mapChoosePointRL.setVisibility(View.VISIBLE);
            break;

        default:
            break;
        }
    }

    private void onpause()
    {
        stationSearchEdit.setFocusable(false);
        isFirstShow = true;
    }

    @Override
    public void onBackPressed()
    {
    	
        super.onBackPressed();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            if (searchResultListView.getVisibility() == View.VISIBLE)
            {
                searchResultListView.setVisibility(View.GONE);
                deleteIma.setVisibility(View.GONE);
                stationAreasGridView.setVisibility(View.VISIBLE);
                mapChoosePointRL.setVisibility(View.VISIBLE);
                isInMain = true;
            }
            else
            {
                onBackPressed();
            }
        }
        return false;
    }

    @Override
    protected void onLoadingCanceled(int key)
    {

        super.onLoadingCanceled(key);

        ToastMessage("取消数据加载~");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {

            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null)
            {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

        /* 如果搜索栏为空则返回主界面 */
        /*if (search.trim().equals(""))
        {
            deleteIma.setVisibility(View.INVISIBLE);
            searchResultListView.setVisibility(View.GONE);
            stationAreasGridView.setVisibility(View.VISIBLE);
            mapChoosePointRL.setVisibility(View.VISIBLE);
            isInMain = true;
        }*/
        String searchText = stationSearchEdit.getText().toString();
        if (!searchText.trim().equals("")){
        	deleteIma.setVisibility(View.VISIBLE);
        }else{
        	deleteIma.setVisibility(View.INVISIBLE);
        }
        searchResultListView.setVisibility(View.GONE);
        stationAreasGridView.setVisibility(View.VISIBLE);
        mapChoosePointRL.setVisibility(View.VISIBLE);
        
        isInMain = true;    

        return super.onTouchEvent(event);
    }

    /* 隐藏软键盘 */
    @Override
    protected void HideSoftKeyboard()
    {

        super.HideSoftKeyboard();
    }
}
