package com.yutong.clw.ygbclient.view.pages.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.AppDataKeyConstant;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.PushMsgUtil;
import com.yutong.clw.ygbclient.business.setting.SetPushMsgRulesBiz;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.PushMsgRule;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.utils.PreferencesUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.view.bean.push.PushBean;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.setting.announcement.BizNews;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.setting.about.AboutActivity;
import com.yutong.clw.ygbclient.view.pages.setting.announcement.AnnounceListActivity;
import com.yutong.clw.ygbclient.view.pages.setting.changeFactory.ChangeFactoryActivity;
import com.yutong.clw.ygbclient.view.pages.setting.changePassword.OldPasswordSubmitActivity;
import com.yutong.clw.ygbclient.view.pages.setting.changePhone.ChangeBindPhoneActivity;
import com.yutong.clw.ygbclient.view.pages.setting.feedback.FeedBackListActivity;
import com.yutong.clw.ygbclient.view.pages.setting.feedback.FeedbackActivity;
import com.yutong.clw.ygbclient.view.pages.setting.refreshSetting.RefreshSettingActivity;
import com.yutong.clw.ygbclient.view.pages.setting.remindCollection.RemindCollectionActivity;
import com.yutong.clw.ygbclient.view.pages.setting.ring.RingActivity;
import com.yutong.clw.ygbclient.view.pages.setting.userguide.UserGuideActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.widget.CommonCheckBox;
import com.yutong.clw.ygbclient.view.widget.CustomAlertDialog;
import com.yutong.clw.ygbclient.view.widget.CustomDialog;

/**
 * 设置界面
 * 
 * @author zhouzc
 */
public class SettingActivity extends RemindAccessActivity implements
		OnClickListener {

	private int cleanningKey = 1;

	private boolean needsubmitpushrule = false;

	private CustomAlertDialog menudia = null;

	private Button loginoutButton;

	private RelativeLayout newsRL;

	private RelativeLayout newspushRL;

	private RelativeLayout factorysetRL;

	private RelativeLayout clockRL;

	private RelativeLayout clockringRL;

	private RelativeLayout maprefreshRL;

	private RelativeLayout aboutRL;

	private RelativeLayout guidRL;

	private RelativeLayout feedbackRL;

	private RelativeLayout recommandRL;

	private RelativeLayout pwdeditRL;

	private RelativeLayout phoneeditRL;
	/* 刷新测试 */
	private RelativeLayout cleanRL;

	private CommonCheckBox publicPushSetting;

	private View v_newsnum;

	private TextView tv_newnum;

	private TextView tv_sh_curfreq;

	private boolean isFirst = true;

	/**
	 * 当前厂区
	 */
	private TextView tv_sh_curfactory;

	/**
	 * 当前手机号码,隐藏4位.如:135****2120
	 */
	private TextView tv_sh_curphone;

	private BizNews bizNews;

	private List<PushMsgRule> pushMsgRuleList = new ArrayList<PushMsgRule>();

	private PushMsgRule pmRule;

	private SetPushMsgRulesBiz setPushMsgRulesBiz;

	private Thread cleanCacheThread = null;

	private TextView mFeedBackCountTV;
	
	private LinearLayout mFeedBackCount_ll;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		listenAnnounce = true;
		setContentView(R.layout.activity_setting);
		initBiz();
		initViews();
		initListener();
	}

	private void initBiz() {

		this.bizNews = new BizNews(getContext());
	}

	public void initViews() {
		loginoutButton = (Button) findViewById(R.id.loginoutButton);
		newsRL = (RelativeLayout) findViewById(R.id.newsRL);

		factorysetRL = (RelativeLayout) findViewById(R.id.factorysetRL);
		clockRL = (RelativeLayout) findViewById(R.id.clockRL);
		clockringRL = (RelativeLayout) findViewById(R.id.clockringRL);
		maprefreshRL = (RelativeLayout) findViewById(R.id.maprefreshRL);

		guidRL = (RelativeLayout) findViewById(R.id.guidRL);
		feedbackRL = (RelativeLayout) findViewById(R.id.feedbackRL);
		aboutRL = (RelativeLayout) findViewById(R.id.aboutRL);
		recommandRL = (RelativeLayout) findViewById(R.id.recommandRL);

		pwdeditRL = (RelativeLayout) findViewById(R.id.pwdeditRL);
		phoneeditRL = (RelativeLayout) findViewById(R.id.phoneeditRL);

		cleanRL = (RelativeLayout) findViewById(R.id.cleanCacheRL);

		/* 厂区切换右侧文字 */
		tv_sh_curfactory = (TextView) findViewById(R.id.tv_sh_curfactory);
		tv_sh_curphone = (TextView) findViewById(R.id.tv_sh_curphone);

		/* 公告推送设置 */
		publicPushSetting = (CommonCheckBox) findViewById(R.id.cb_scpr_stationremind);
		tv_sh_curfreq = (TextView) findViewById(R.id.tv_sh_curfreq);

		v_newsnum = findViewById(R.id.ll_sh_newcount);
		tv_newnum = (TextView) findViewById(R.id.tv_sh_newsnum);

		if (PushMsgUtil.getNewsNotificationCount() > 0) {
			v_newsnum.setVisibility(View.VISIBLE);
			tv_newnum.setText(String.valueOf(PushMsgUtil
					.getNewsNotificationCount()));
		} else {
			v_newsnum.setVisibility(View.INVISIBLE);
			tv_newnum.setText(String.valueOf(0));
		}
		mFeedBackCountTV =  (TextView) findViewById(R.id.feedbackcount_num_TV);
		mFeedBackCount_ll =(LinearLayout) findViewById(R.id.feedbackcount_ll);
	}

	private void initListener() {
		loginoutButton.setOnClickListener(this);
		newsRL.setOnClickListener(this);

		factorysetRL.setOnClickListener(this);
		clockRL.setOnClickListener(this);
		clockringRL.setOnClickListener(this);
		maprefreshRL.setOnClickListener(this);

		aboutRL.setOnClickListener(this);
		guidRL.setOnClickListener(this);
		feedbackRL.setOnClickListener(this);
		recommandRL.setOnClickListener(this);

		pwdeditRL.setOnClickListener(this);
		phoneeditRL.setOnClickListener(this);
		/* 刷新 */
		cleanRL.setOnClickListener(this);

		/* 公告推送设置 */
		publicPushSetting
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// 第一次程序设置界面状态的时候也会触发该事件，这个时候不需要响应。
						if (!needsubmitpushrule)
							return;
						
						/*用户行为统计-*/
			            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_GGTSSZ).reportBehavior(null);
						
			            SettingActivity.this.showLoading("正在提交...", 0);
						if (isChecked) {
							List<PushMsgRule> rules = new ArrayList<PushMsgRule>();
							pmRule.setOn_off(isChecked);
							rules.add(pmRule);

							bizNews.setPushMsgRules(rules,
									new BizResultProcess<Boolean>() {

										@Override
										public void onBizExecuteEnd(
												BizDataTypeEnum datatype,
												Boolean t) {
											dismissLoading(0);
											if (t) {
												if (isFirst)
													isFirst = false;
												else
													ToastMessage("开启公告推送设置成功");
											}

										}

										@Override
										public void onBizExecuteError(
												Exception exception, Error error) {
											dismissLoading(0);
											ToastMessage("开启公告推送设置失败");
										}

									});

						} else {
							List<PushMsgRule> rules = new ArrayList<PushMsgRule>();
							pmRule.setOn_off(isChecked);
							rules.add(pmRule);

							bizNews.setPushMsgRules(rules,
									new BizResultProcess<Boolean>() {

										@Override
										public void onBizExecuteEnd(
												BizDataTypeEnum datatype,
												Boolean t) {
											dismissLoading(0);
											if (t) {
												if (isFirst)
													isFirst = false;
												else
													ToastMessage("关闭公告推送设置成功");
											}
										}

										@Override
										public void onBizExecuteError(
												Exception exception, Error error) {
											dismissLoading(0);
											ToastMessage("关闭公告推送设置失败");
										}

									});
						}

					}
				});

	}

	@Override
	protected void onReceivedPushMessage(PushBean msg) {
		
		switch (msg.getType()) {
		case News:
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					updateNews();
				}
			});
			break;
		default:
			break;
		}
		super.onReceivedPushMessage(msg);
	}

	protected void updateNews() {
		int cnt = PushMsgUtil.getNewsNotificationCount();
		v_newsnum.setVisibility((cnt <= 0) ? View.GONE : View.VISIBLE);
		tv_newnum
				.setText(String.valueOf(PushMsgUtil.getNewsNotificationCount()));

	}

	@Override
	protected void onResume() {
		super.onResume();

		initData();
		updateNews();
		getAdviceNum();
	}
	
	private void getAdviceNum() {
		
		/*回复过的且未读的*/
		bizNews.getAdviceInfoNumByReadFlag(ActivityCommConstant.ADVICE_UN_READ,ActivityCommConstant.ADVICE_REPLY,new BizResultProcess<Integer>(){

			@Override
			public void onBizExecuteEnd(BizDataTypeEnum datatype, Integer t) {
				if(t.intValue()>0){
					mFeedBackCount_ll.setVisibility(View.VISIBLE);
					mFeedBackCountTV.setText(String.valueOf(t.intValue()));
				}else{
					mFeedBackCount_ll.setVisibility(View.GONE);
				}
			}

			@Override
			public void onBizExecuteError(Exception exception, Error error) {
				mFeedBackCount_ll.setVisibility(View.GONE);
			}
		});
	}
	
	private void initData() {
		tv_sh_curfactory.setText(PrefDataUtil.getFactory(
				getApplicationContext()).getName());

		tv_sh_curfreq
				.setText(!PrefDataUtil
						.getAutoMapRefresh(getApplicationContext()) ? "已关闭"
						: (PrefDataUtil
								.getMapRefreshInterval(getApplicationContext()) + "秒钟"));

		 String phoneNum = PreferencesUtils.getString(getBaseContext(), ActivityCommConstant.prefName, ActivityCommConstant.PHONE);
		 if (StringUtil.isEmpty(phoneNum)) {
			 tv_sh_curphone.setText("未获取到手机号");	
			 phoneeditRL.setEnabled(false);
		 }else{
			 tv_sh_curphone.setText(StringUtil.maskPhone(phoneNum));
			 phoneeditRL.setEnabled(true);
		 }

		/* 加载公告信息规则 */
		bizNews.getPushMsgRules(new BizResultProcess<List<PushMsgRule>>() {

			@Override
			public void onBizExecuteEnd(BizDataTypeEnum datatype,
					List<PushMsgRule> t) {

				Logger.i(getClass(), "加载公告信息规则成功");

				pushMsgRuleList = t;

				for (PushMsgRule pushMsgRule : pushMsgRuleList) {
					Logger.i(getClass(), "Rule_id:" + pushMsgRule.getRule_id()
							+ " Usr_code：" + pushMsgRule.getUsr_code());
				}

				for (PushMsgRule pushMsgRule : pushMsgRuleList) {

					if (pushMsgRule.getRule_id().equals(
							ActivityCommConstant.ANNOUNCE_PUSH_SETTING_ID)) {

						pmRule = pushMsgRule;
						break;
					}
				}

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						needsubmitpushrule = false;
						publicPushSetting.setChecked(pmRule.isOn_off());
						needsubmitpushrule = true;
						publicPushSetting.invalidate();
					}
				});
			}

			@Override
			public void onBizExecuteError(Exception exception, Error error) {

				// ToastMessage("加载信息规则失败");
				HandleLogicErrorInfo(exception);
			}

		});

		
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
		btn_right.setBackgroundResource(R.drawable.bg_anxinbtn);
		tv_large.setText("系统设置");
		tv_small.setVisibility(View.GONE);
		iv_tri.setVisibility(View.GONE);

		btn_left.setOnClickListener(this);
		btn_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginoutButton:
			popupLoginOutConfirm();
			break;
		case R.id.btn_it_left:
			finish();
			break;
		case R.id.btn_it_right:
			ActivityUtils.changeActivity(SettingActivity.this,
					AboutActivity.class);
			break;

		case R.id.rl_it_center:
			break;
		case R.id.newsRL:
			
			/*用户行为统计-*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_GONGGAOLOAD).reportBehavior(null);
            
			v_newsnum.setVisibility(View.INVISIBLE);
			ActivityUtils.changeActivity(SettingActivity.this,
					AnnounceListActivity.class);

			break;
		case R.id.factorysetRL:
			
			/*用户行为统计-厂区切换*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_QHCQ).reportBehavior(null);
			ActivityUtils.changeActivity(SettingActivity.this,
					ChangeFactoryActivity.class);

			break;
		case R.id.clockRL:
			
			/*用户行为统计-*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_DZTXLOAD).reportBehavior(null);
            
			ActivityUtils.changeActivity(SettingActivity.this,RemindCollectionActivity.class);

			break;
		case R.id.clockringRL:
			/*用户行为统计-*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_XLFS).reportBehavior(null);
            
			ActivityUtils.changeActivity(SettingActivity.this,
					RingActivity.class);

			break;

		case R.id.maprefreshRL:
			/*用户行为统计-*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_DTSXSZ).reportBehavior(null);
			ActivityUtils.changeActivity(SettingActivity.this,RefreshSettingActivity.class);

			break;
		case R.id.aboutRL:
			
			/*用户行为统计-*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_ABOUT).reportBehavior(null);
			ActivityUtils.changeActivity(SettingActivity.this,
					AboutActivity.class);

			break;
		case R.id.guidRL:

			/*用户行为统计-*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_GUIDE).reportBehavior(null);
            
			Bundle bundle = new Bundle();
			bundle.putString(ActivityCommConstant.GUID_RETURN_TYPE,
					ActivityCommConstant.GUID_RETURN);
			ActivityUtils.changeActivity(SettingActivity.this,
					UserGuideActivity.class, bundle);

			break;
		case R.id.feedbackRL:
			/*ActivityUtils.changeActivity(SettingActivity.this,
					FeedbackActivity.class);*/
			/*用户行为统计-*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_YJFK).reportBehavior(null);
			ActivityUtils.changeActivity(SettingActivity.this,FeedBackListActivity.class);
			break;
		case R.id.recommandRL:
			new CustomDialog.Builder(this)
					.setMessage("要发短信推荐给好友么？")
					.setTitle("推荐给好友")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									
									/*用户行为统计-*/
						            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_TJ).reportBehavior(null);
									dialog.dismiss();
									sendSMS();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();

								}
							}).create().show();

			break;
		case R.id.pwdeditRL:
			/*用户行为统计-*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_XGMM).reportBehavior(null);
			ActivityUtils.changeActivity(SettingActivity.this,
					OldPasswordSubmitActivity.class);

			break;
		case R.id.phoneeditRL:
			
			/*用户行为统计-*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_XGSJH).reportBehavior(null);
            
			ActivityUtils.changeActivity(SettingActivity.this,
					ChangeBindPhoneActivity.class);

			break;
		/* 刷新 */
		case R.id.cleanCacheRL:
			
			/*用户行为统计-*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_QLHC).reportBehavior(null);
			showLoading("正在清理...", cleanningKey, false);
			if (cleanCacheThread == null) {
				cleanCacheThread = new Thread(new Runnable() {

					@Override
					public void run() {
						ProxyManager.getInstance(SettingActivity.this)
								.clearAppCache();

						/*
						 * try { Thread.sleep(3000); } catch
						 * (InterruptedException e) { e.printStackTrace(); }
						 */
						cleanCacheHandler.sendEmptyMessage(1);
					}

				});
				cleanCacheThread.start();
			}
			break;
		default:
			break;
		}

	}

	Handler cleanCacheHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1:
				dismissLoading(cleanningKey);
				ToastMessage("清理成功");
				if (cleanCacheThread.isAlive()) {
					cleanCacheThread.interrupt();
				}
				cleanCacheThread = null;
				break;
			}
			super.handleMessage(msg);
		}
	};

	/*
	 * @Override protected void onLoadingCanceled(int key) {
	 * super.onLoadingCanceled(key); if(cleanCacheThread.isAlive()){
	 * cleanCacheThread.interrupt(); cleanCacheThread = null; }
	 * ToastMessage("取消清理"); }
	 */

	/**
	 * 发送短信
	 * 
	 * @param smsBody
	 */

	private void sendSMS() {

		String smsBody = "";
		int r = (int) (Math.random() * 10 / 3);
		switch (r) {
		case 0:
			smsBody = this.getResources().getString(R.string.recommend_body);
			break;
		case 1:
			smsBody = this.getResources().getString(R.string.recommend_body1);
			break;
		case 2:
			smsBody = this.getResources().getString(R.string.recommend_body2);
			break;
		default:
			smsBody = this.getResources().getString(R.string.recommend_body2);
			break;
		}

		Uri smsToUri = Uri.parse("smsto:");

		Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
		// Intent intent = new Intent(Intent.ACTION_SENDTO);

		intent.putExtra("sms_body", smsBody);

		startActivity(intent);

	}

	private void popupLoginOutConfirm() {
		if (menudia == null) {
			menudia = new CustomAlertDialog(this) {

				@Override
				public void onUserOK() {
					/*用户行为统计-*/
		            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_ZXZH).reportBehavior(null);
					YtApplication.getInstance().logout();
				}

				@Override
				public void onUserCancel() {
				}
			};
			menudia.setTitle("注销不会删除或更改您的任何数据");
			menudia.setCancelStr("取消");
			menudia.setOkStr("注销当前登录账户");
			menudia.show();
		} else {
			if (menudia.isShowing()) {
				menudia.dismiss();
			} else {
				menudia.show();
			}
		}

	}

	private Context getContext() {

		return this;
	}
}
