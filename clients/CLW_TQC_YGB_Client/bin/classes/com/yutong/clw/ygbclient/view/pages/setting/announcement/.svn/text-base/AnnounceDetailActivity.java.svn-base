package com.yutong.clw.ygbclient.view.pages.setting.announcement;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomButtonsController;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.ResourceInfo;
import com.yutong.clw.ygbclient.common.beans.news.NewsInfo;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.ImageUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.dao.setting.NewsInfoDao;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.common.BizResource;
import com.yutong.clw.ygbclient.view.bizAccess.setting.announcement.BizNews;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;

public class AnnounceDetailActivity extends RemindAccessActivity implements
		OnClickListener {
	private TextView tv_new_title;

	private TextView tv_new_desc;

	/* private TextView tv_new_content; */
	/* private HtmlTextView tv_new_content; */
	private WebView tv_new_content;

	private ImageView tv_new_image;

	private NewsInfo newsInfo;

	/*
	 * private String bgColor_start = "<body bgcolor=\"#e8e8e8\">",bgColor_end =
	 * "</body>";
	 */
	private String bgColor_start = "<body >", bgColor_end = "</body>";
	// 数据获取
	private BizNews biz = new BizNews(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_announce_detail);
		initViews();
		loadInfo();
		newsInfo.content = bgColor_start + bgColor_end;
	}

	@Override
	protected void onResume() {

		tv_new_content.loadDataWithBaseURL(null, newsInfo.content, "text/html","utf-8", null);
		super.onResume();
	}

	public void initViews() {

		tv_new_title = (TextView) findViewById(R.id.NewDetail_Title);
		tv_new_desc = (TextView) findViewById(R.id.NewDetail_Desc);

		tv_new_content = (WebView) findViewById(R.id.NewDetail_Content);
		tv_new_content.getSettings().setDefaultTextEncodingName("utf-8");
		tv_new_content.getSettings().setJavaScriptEnabled(true);

		tv_new_content.getSettings().setSupportZoom(true);
		tv_new_content.getSettings().setBuiltInZoomControls(true);
		tv_new_content.requestFocus();
		
		setZoomControlGone(tv_new_content);  
		/*tv_new_content.getSettings().setUseWideViewPort(true);
		tv_new_content.getSettings().setLoadWithOverviewMode(true);*/
		
		tv_new_image = (ImageView) findViewById(R.id.NewDetail_Image);
	}

	// 实现放大缩小控件隐藏
	public void setZoomControlGone(View view) {
		Class classType;
		Field field;
		try {
			classType = WebView.class;
			field = classType.getDeclaredField("mZoomButtonsController");
			field.setAccessible(true);
			ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(
					view);
			mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
			try {
				field.set(view, mZoomButtonsController);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	private void loadInfo() {
		Intent intent = getIntent();
		
		if (intent == null)
			return;
		
		Bundle bundle = intent.getExtras();
		newsInfo = (NewsInfo) bundle.getSerializable(ActivityCommConstant.NEWS_INFO);

		biz.getNewsDetail(newsInfo.id, new BizResultProcess<NewsInfo>() {

			@Override
			public void onBizExecuteEnd(BizDataTypeEnum datatype, NewsInfo t) {
				final NewsInfo temp = t;
				DateUtils.getFormatTime(temp.publish_time, "yyyy/MM/dd HH:mm");
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						dismissLoading(0);
						newsInfo = temp;
						loadNews();
					}
				});

			}

			@Override
			public void onBizExecuteError(Exception exception, Error error) {

				dismissLoading(0);
				HandleLogicErrorInfo(exception);
			}

		});
	}

	private void loadNews() {
		tv_new_title.setText(newsInfo.title);
		StringBuilder strbuilder = new StringBuilder();
		// StringBuilder strbuilder = new StringBuilder("文/");
		// if (StringUtils.isNotBlank(news.getNews_author())) {
		// strbuilder.append(news.getNews_author());
		// } else {
		// strbuilder.append("未知来源");
		// }
		strbuilder.append("发表时间：").append(
				DateUtils.getFormatTime(newsInfo.publish_time, "yyyy/MM/dd HH:mm"));

		tv_new_desc.setText(strbuilder.toString());

		// ImageUtils.startGetImg(getApplicationContext(), tv_new_image,
		// "http://s9.51cto.com/wyfs01/M01/15/03/wKioJlIIpfnRgQwkAACgnhRVRzA230.jpg");

		if (newsInfo.image_res != null && newsInfo.image_res.size() > 0) {
			tv_new_image.setVisibility(View.VISIBLE);
			// new GetNetResourceBiz(this).startGetImage(news.image_res.get(0),
			// tv_new_image);
			BizResource bizResource = new BizResource(this);
			bizResource.getServerResource(newsInfo.image_res.get(0),
					new BizResultProcess<ResourceInfo>() {

						@Override
						public void onBizExecuteEnd(BizDataTypeEnum datatype,
								ResourceInfo t) {
							final ResourceInfo temp = t;
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									dismissLoading(0);

									if (temp != null
											&& StringUtil.isNotBlank(temp
													.getResource())) {
										Bitmap bitmap = ImageUtils
												.byteToBitmap(temp.getBytes());

										if (bitmap != null
												&& tv_new_image != null) {
											WeakReference<Bitmap> wk = new WeakReference<Bitmap>(
													bitmap);

											if (!wk.get().isRecycled()) {
												tv_new_image.setImageBitmap(wk
														.get());
											} else {
												Logger.i(this.getClass(),
														"图片已经被回收掉了");
											}
										} else {
											// 加载默认图片
											Logger.w(this.getClass(),
													"获取图片失败,加载默认图片");
										}
									}
								}
							});

						}

						@Override
						public void onBizExecuteError(Exception exception,
								Error error) {

							dismissLoading(0);

						}

					});

			// ImageUtils.startGetImg(getApplicationContext(), tv_new_image,
			// news.image_url);

		} else {
			tv_new_image.setVisibility(View.GONE);
		}

		/* tv_new_content.setText(news.content); */

		/* tv_new_content.setHtmlFromString(news.content,true); */

		newsInfo.content = bgColor_start + newsInfo.content + bgColor_end;
		tv_new_content.loadDataWithBaseURL(null, newsInfo.content, "text/html",
				"utf-8", null);

		// TODO 异步获取图片数据后再调下面的将tv_new_image显示出来
		// 这个异步获取图片不用loading进度条
		// tv_new_image.setImageResource(R.drawable.tnew);

		// tv_new_content
		// .setText("苹果小米火腿肠，这年头手机品牌都流行吃的，为啥就HTC玩不转？说到底还是自身实力的问题，卖咸鱼出身的三星所有硬件都能自产自销，核心技术在手完全不怕竞争，且销售渠道得天独厚，看看满大街的三星专卖店，但是HTC却很难找到一两家。论每一代旗舰机性能质量其实火腿肠和三星都旗鼓相当，造型也各有千秋，而这一代的one造型甚至甩开4代卫生巾几条街却还卖不赢，连后起之秀小米都杀到它老巢去了，说HTC最大的问题就是伪高端毫不为过。");

		// add by zhangzhi 在新闻详细成功获取到数据后，才会设置标志位
		// 更新已读状态
		newsInfo.is_read = true;
		new NewsInfoDao(this).addNewsInfos(newsInfo,
				ProxyManager.getInstance(AnnounceDetailActivity.this)
						.getUserCode());

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
		btn_right.setVisibility(View.INVISIBLE);
		tv_large.setText("公告详情");
		tv_small.setVisibility(View.GONE);
		iv_tri.setVisibility(View.GONE);

		btn_left.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_it_left:
			finish();
			break;

		default:
			break;
		}

	}
}
