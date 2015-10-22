package com.yutong.clw.ygbclient.view.pages.setting.feedback;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.utils.PreferencesUtils;
import com.yutong.clw.ygbclient.common.utils.ToastUtils;
import com.yutong.clw.ygbclient.view.bean.push.FeedBackPushBean;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.setting.announcement.BizNews;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.main.inFactory.InFactoryActivity;
import com.yutong.clw.ygbclient.view.pages.setting.feedback.adapter.FeedBackListAdapter;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView;
import com.yutong.clw.ygbclient.view.widget.PullToRefreshView.OnHeaderRefreshListener;

/**
 * 意见反馈列表界面
 */
public class FeedBackListActivity extends RemindAccessActivity implements
		OnClickListener {

	private LinearLayout publish_suggestion_LL,item_root_LL;
	private RelativeLayout item_root_RL;
	private TextView publish_suggestion_TV,feedBackTipsTV;
	private ListView mFeedBackList_LV;
	private Context mContext;
	private BizNews bizNews;
	private List<FeedBackPushBean> mAdiviceList = new ArrayList<FeedBackPushBean>();
	
	private FeedBackListAdapter mFeedBackListAdapter;
	private int mKeycode = 0x0;
	private List<FeedBackPushBean> adviceList;
	private String userCode;
	
	private PullToRefreshView mPullToRefreshView;
	
	private boolean mSetFlag = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_feedback_list);
		mContext = this;
		initViews();
		initBiz();
		initData();
		
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		loadData();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mSetFlag = false;
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mSetFlag = false;
	}
	
	private void setAdviceListStatus(String readFlag,String replyFlag){
		
		bizNews.setAdviceListStatus(readFlag,replyFlag, new BizResultProcess<Boolean>() {

			@Override
			public void onBizExecuteEnd(BizDataTypeEnum datatype, Boolean t) {
				if(t){
					Log.i("TAG", "意见反馈： 已置为已读状态");
				}
			}

			@Override
			public void onBizExecuteError(Exception exception, Error error) {
				Log.i("TAG", "意见反馈： 状态设置失败！");
			}
		});
	}
	
	private void loadData() {
		
		mPullToRefreshView.setHeaderRefreshing();
		/*bizNews.getUnReadAdviceList(userCode,new BizResultProcess<List<FeedBackPushBean>>(){*/
		bizNews.test(userCode,new BizResultProcess<List<FeedBackPushBean>>(){

			@Override
			public void onBizExecuteEnd(BizDataTypeEnum datatype,
					 List<FeedBackPushBean> t) {
				dismissLoading(mKeycode);
				
				adviceList = t;
				runOnUiThread(new Runnable() {
					public void run() {
						
						mPullToRefreshView.onHeaderRefreshComplete();
						
						mFeedBackListAdapter.setmAdiviceList(adviceList);
						mFeedBackListAdapter.notifyDataSetChanged();
						if(adviceList.size()>0){
							feedBackTipsTV.setVisibility(View.GONE);
						}else{
							feedBackTipsTV.setVisibility(View.VISIBLE);
						}
						
						/*设置意见反馈状态为已读过*/
						if(mSetFlag){
							mSetFlag = false;
							setAdviceListStatus(ActivityCommConstant.ADVICE_READ,ActivityCommConstant.ADVICE_REPLY);
						}
					}
				});
			}

			@Override
			public void onBizExecuteError(Exception exception, Error error) {
				dismissLoading(mKeycode);
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		});
	}
	
	View feedBackListHeader;
	
	public void initViews() {

		mPullToRefreshView  =(PullToRefreshView) findViewById(R.id.pullToRefreshView);
		mPullToRefreshView.setPullUpEnable(false);
		feedBackTipsTV = (TextView) findViewById(R.id.feedBackTipsTV);
		mPullToRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener(){

            @Override
            public void onHeaderRefresh(PullToRefreshView view){
            	mSetFlag = true;
                loadData();
            }
        });
		
		publish_suggestion_LL = (LinearLayout) findViewById(R.id.publish_suggestion_LL);
		publish_suggestion_LL.setOnClickListener(this);

		publish_suggestion_TV = (TextView) findViewById(R.id.publish_suggestion_TV);
		
		mFeedBackList_LV = (ListView) findViewById(R.id.feedBackList_LV);
		
		
		feedBackListHeader = View.inflate(mContext,
				R.layout.activity_feedback_adapter_listitem_header, null);
		mFeedBackList_LV.addHeaderView(feedBackListHeader);
		
		mFeedBackListAdapter = new FeedBackListAdapter(mContext);
		mFeedBackListAdapter.setmAdiviceList(mAdiviceList);
		mFeedBackList_LV.setAdapter(mFeedBackListAdapter);
		mFeedBackListAdapter.notifyDataSetChanged();
	}
	
	public void initBiz() {
		this.bizNews = new BizNews(mContext);
	}

	private void initData() {
		
		showLoading("正在加载数据", mKeycode);
		userCode = PreferencesUtils.getString(mContext, ActivityCommConstant.prefName,ActivityCommConstant.EMP_NUMBER);
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void loadTitleByPageSetting(Button btn_left, Button btn_right,
			RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
			TextView tv_small) {
		btn_left.setBackgroundResource(R.drawable.bg_prevbtn);

		tv_large.setText("意见反馈");
		tv_small.setVisibility(View.GONE);
		iv_tri.setVisibility(View.GONE);

		btn_right.setVisibility(View.GONE);

		btn_left.setOnClickListener(this);
		btn_right.setOnClickListener(this);
	}

	@Override
	protected void onLoadingCanceled(int key) {
		super.onLoadingCanceled(key);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.publish_suggestion_LL:
			ActivityUtils.changeActivity((Activity) mContext,
					FeedbackActivity.class);
			break;

		case R.id.btn_it_left:
			finish();
			break;

		default:
			break;
		}

	}
}
