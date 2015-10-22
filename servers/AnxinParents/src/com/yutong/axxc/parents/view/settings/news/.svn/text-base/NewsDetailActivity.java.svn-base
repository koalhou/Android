package com.yutong.axxc.parents.view.settings.news;

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.news.NewsDetailInfoBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.beans.NewsInfoBean;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ImageUtils;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtHandler;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 */
public class NewsDetailActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;

	private TextView tv_top_title;

	private Button btn_title_right;

	private TextView tv_new_title;

	private TextView tv_new_desc;

	private TextView tv_new_content;

	private ImageView tv_new_image;

	private NewsDetailInfoBiz biz;

	private NewsInfoBean news;

	// 加载弹出提示相关视图
	// private LoadingOverlay loadingoverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsdetail);
		initViews();
		initListener();
		loadInfo();
	}

	private void initListener() {
		btn_title_left.setOnClickListener(this);

	}

	private void initViews() {

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		tv_new_title = (TextView) findViewById(R.id.NewDetail_Title);
		tv_new_desc = (TextView) findViewById(R.id.NewDetail_Desc);
		tv_new_content = (TextView) findViewById(R.id.NewDetail_Content);
		tv_new_image = (ImageView) findViewById(R.id.NewDetail_Image);

		btn_title_right.setVisibility(View.INVISIBLE);

		// TODO 加载新闻数据
		tv_top_title.setText("新闻详情");

	}

	private void loadInfo() {
		Intent intent = getIntent();
		if (intent == null)
			return;
		Bundle bundle = intent.getExtras();
		news = (NewsInfoBean) bundle
				.getSerializable(ActivityCommConstant.NEWS_INFO);

		startTask(news.getNews_id());
	}

	private void loadNews() {
		tv_new_title.setText(news.getNews_title());
		StringBuilder strbuilder = new StringBuilder();
//		StringBuilder strbuilder = new StringBuilder("文/");
//		if (StringUtils.isNotBlank(news.getNews_author())) {
//			strbuilder.append(news.getNews_author());
//		} else {
//			strbuilder.append("未知来源");
//		}
		strbuilder.append("发表时间：").append(
				Tools.getFormatTime(news.getNews_time(), "yyyyMMddHHmmss",
						"yyyy/MM/dd HH:mm:ss"));

		tv_new_desc.setText(strbuilder.toString());

//		ImageUtils.startGetImg(getApplicationContext(), tv_new_image,
//                "http://s9.51cto.com/wyfs01/M01/15/03/wKioJlIIpfnRgQwkAACgnhRVRzA230.jpg");

		if (StringUtils.isNotBlank(news.getNews_image_url())) {
			tv_new_image.setVisibility(View.VISIBLE);
			ImageUtils.startGetImg(getApplicationContext(), tv_new_image,news.getNews_image_url());
     
		} else {
			tv_new_image.setVisibility(View.GONE);
		}

		tv_new_content.setText(news.getNews_content());
		// TODO 异步获取图片数据后再调下面的将tv_new_image显示出来
		// 这个异步获取图片不用loading进度条
		// tv_new_image.setImageResource(R.drawable.tnew);

		// tv_new_content
		// .setText("苹果小米火腿肠，这年头手机品牌都流行吃的，为啥就HTC玩不转？说到底还是自身实力的问题，卖咸鱼出身的三星所有硬件都能自产自销，核心技术在手完全不怕竞争，且销售渠道得天独厚，看看满大街的三星专卖店，但是HTC却很难找到一两家。论每一代旗舰机性能质量其实火腿肠和三星都旗鼓相当，造型也各有千秋，而这一代的one造型甚至甩开4代卫生巾几条街却还卖不赢，连后起之秀小米都杀到它老巢去了，说HTC最大的问题就是伪高端毫不为过。");
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

	private static final int LOADING_CODE_1 = 0X1001;

	@Override
	protected void onLoadingCanceled(int key) {
		switch (key) {
		case LOADING_CODE_1:
			//TODO 用户取消加载数据后要做的事
			break;

		default:
			break;
		}
		super.onLoadingCanceled(key);
	}
	
	private void startTask(String news_id) {

		if (biz != null)
			biz.cancel();
		showLoading("加载中...", LOADING_CODE_1);
		biz = new NewsDetailInfoBiz(getApplicationContext(),
				new ProcessHandler(NewsDetailActivity.this), news_id);
		biz.setReadMethod(ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE);
		biz.execute();

	}

	private class ProcessHandler extends YtHandler {
		private final WeakReference<NewsDetailActivity> weakActivity;

		public ProcessHandler(NewsDetailActivity activity) {
			this.weakActivity = new WeakReference<NewsDetailActivity>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(NewsDetailActivity.class, "[获取新闻详细-handler]:msg.what:",
					msg.what);
			NewsDetailActivity activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				dismissLoading();
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					// Toast.makeText(activity.getApplicationContext(),
					// "获取新闻概要成功！", Toast.LENGTH_SHORT).show();
					activity.news = (NewsInfoBean) msg.obj;

					activity.loadNews();
					break;

				case ThreadCommStateCode.TOKEN_INVALID:
					Toast.makeText(activity.getApplicationContext(),
							"Token失效，请重新登录！", Toast.LENGTH_SHORT).show();
					break;

				case ThreadCommStateCode.COMMON_FAILED:
					Toast.makeText(activity.getApplicationContext(),
							"获取新闻详细失败！请重试！", Toast.LENGTH_SHORT).show();
					break;

				default:
					Toast.makeText(activity.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}
	}
}
