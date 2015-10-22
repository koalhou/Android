package com.yutong.axxc.parents.view.settings.news;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.cache.CacheUtils;
import com.yutong.axxc.parents.business.common.CommonPushMsgUtil;
import com.yutong.axxc.parents.business.news.NewsSummaryInfoBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.beans.NewsInfoBean;
import com.yutong.axxc.parents.common.constant.NewsTypeConstant;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtHandler;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 */
public class NewsHomeActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;

	private TextView tv_top_title;

	private Button btn_title_right;

	// 加载弹出提示相关视图
	// private LoadingOverlay loadingoverlay;

	private NewsSummaryInfoBiz biz;

	List<NewsInfoBean> newsInfoBeans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newshome);
		initViews();
		initListener();
        startTask();
        
        //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0021);
        
	}

	
	@Override
    protected void onResume()
    {
        super.onResume();
        CommonPushMsgUtil.deleteNewsNotificationInfo();
        newsInfoBeans = CacheUtils.getNews(this);
        loadNews();
    }


    private void initListener() {
		// btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		// loadingoverlay
		// .addOnCancelListener(new LoadingOverlay.OnCancelListener() {
		//
		// @Override
		// public void onCancel() {
		//
		// loadingoverlay.setVisibility(View.INVISIBLE);
		//
		// }
		// });

	}

	ArrayList<HashMap<String, Object>> listItem;

	SimpleAdapter listItemAdapter;
	
	private static final int LOADING_CODE_1 = 0X1001;

	@Override
	protected void onLoadingCanceled(int key) {
		switch (key) {
		case LOADING_CODE_1:
			if (biz != null&&!biz.isCanceled())
				biz.cancel();
			break;

		default:
			break;
		}
		super.onLoadingCanceled(key);
	}

	
	private void startTask() {

		if (biz != null&&!biz.isCanceled())
			biz.cancel();
		// loadingoverlay.setVisibility(View.VISIBLE);
		// loadingoverlay.setLoadingTip("正在执行,请稍候...");
		showLoading("加载中...", LOADING_CODE_1);
		biz = new NewsSummaryInfoBiz(getApplicationContext(),
				new ProcessHandler(NewsHomeActivity.this),
				NewsTypeConstant.ALL_TYPE);
		biz.execute();

	}

	private class ProcessHandler extends YtHandler {
		private final WeakReference<NewsHomeActivity> weakActivity;

		@SuppressLint("HandlerLeak")
		public ProcessHandler(NewsHomeActivity activity) {
			this.weakActivity = new WeakReference<NewsHomeActivity>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			Logger.i(NewsHomeActivity.class, "[获取新闻概要-handler]:msg.what:",
					msg.what);
			NewsHomeActivity activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					dismissLoading();
					// Toast.makeText(activity.getApplicationContext(),
					// "获取新闻概要成功！", Toast.LENGTH_SHORT).show();
					activity.newsInfoBeans = (List<NewsInfoBean>) msg.obj;

					activity.loadNews();
					break;
					
	            case ThreadCommStateCode.COMMON_SEARCH_RES_COUNT:
                    activity.dismissLoading();

                    break;    
	            case ThreadCommStateCode.COMMON_SEARCH_NO_RES:
                    activity.dismissLoading();

                    break;  
				case ThreadCommStateCode.TOKEN_INVALID:
				    activity.dismissLoading();
					ToastMessage("Token失效，请重新登录！");
					break;

				case ThreadCommStateCode.COMMON_FAILED:
				    activity.dismissLoading();
					ToastMessage("获取新闻概要失败！请重试！");
					break;

				default:
				    activity.dismissLoading();
				    Toast.makeText(activity.getApplicationContext(),
                            (String)msg.obj, Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}
	}

	private void loadNews() {

	    listItem.clear();
		// TODO 获取新闻数据
		if (newsInfoBeans != null) {
			for (NewsInfoBean news : newsInfoBeans) {
				HashMap<String, Object> map = new HashMap<String, Object>();

				if (!news.getIs_read()) {
					map.put("ItemImage", R.drawable.news_new);// 图像资源图片，显示在小项右端
				}
				
				map.put("ItemTitle", news.getNews_title());
				map.put("ItemText", Tools.getFormatTime(news.getNews_time(),
						"yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss"));
				map.put("ItemSign", news.getNews_summary());
				listItem.add(map);// 添加到listItem中
			}
		}

//		 for (int i = 0; i < 100; i++) {
//		 // HashMap为键值对类型。第一个参数为键，第二个参数为值
//		 HashMap<String, Object> map = new HashMap<String, Object>();
//		 map.put("ItemImage", R.drawable.news_new);// 图像资源图片，显示在小项右端
//		 map.put("ItemTitle", "河南信阳一辆6座校车载了28...");
//		 map.put("ItemText", "2013-09-02");
//		 map.put("ItemSign", "此处可显示正文或者文件路径,此处可显示正文或者文件路径,此处可显示正文或者文件路径  ");
//		 listItem.add(map);// 添加到listItem中
//		 }
		listItemAdapter.notifyDataSetChanged();
	}

	private void initViews() {
		// loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		btn_title_right.setVisibility(View.INVISIBLE);

		// btn_title_right.setText(R.string.next);
		tv_top_title.setText("新闻");

		ListView list = (ListView) findViewById(R.id.ListView_News);

		// 生成动态数组，加入数据
		listItem = new ArrayList<HashMap<String, Object>>();

		listItemAdapter = new SimpleAdapter(
				this,// this是当前Activity的对象
				listItem,// 数据源 为填充数据后的ArrayList类型的对象
				R.layout.newlistview_item,// 子项的布局.xml文件名
				new String[] { "ItemImage", "ItemTitle", "ItemText", "ItemSign" },
				// 这个String数组中的元素就是list对象中的列，list中有几这个数组中就要写几列。
				new int[] { R.id.NewItemImage, R.id.NewItemTitle,
						R.id.NewItemDesc, R.id.NewItemContent });// 值是对应XML布局文件中的一个ImageView,三个TextView的id
		// 添加并显示
		list.setAdapter(listItemAdapter);

		// 添加点击
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			// 重写单击响应
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// setTitle("点击第" + arg2 + "个项目");//直接在标题显示
				Intent intent = new Intent(getApplication(),
						NewsDetailActivity.class);

				Bundle bundle = new Bundle();

				bundle.putSerializable(ActivityCommConstant.NEWS_INFO,
						newsInfoBeans.get(arg2));

				intent.putExtras(bundle);

				startActivity(intent);
				overridePendingTransition(R.anim.enter_righttoleft,
						R.anim.exit_righttoleft);
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:

			break;
		case R.id.btn_title_left:
			finish();
			break;
		default:
			break;
		}

	}
}
