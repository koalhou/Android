package com.yutong.axxc.parents.view.settings.child;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.common.ReceiverUtil;
import com.yutong.axxc.parents.business.settings.GetPushMsgSettingBiz;
import com.yutong.axxc.parents.business.settings.SetPushMsgSettingBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.beans.PushMsgRuleBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 */
public class PushRuleActivity extends YtAbstractActivity implements
		OnClickListener, OnCheckedChangeListener
{

	private Button btn_title_left;

	private TextView tv_top_title;

	private Button btn_title_right;

	// 加载弹出提示相关视图

	private CheckBox cb_stationremind, cb_cardremind;

	List<PushMsgRuleBean> currentrules = null;

	List<PushMsgRuleBean> modifyRules = new ArrayList<PushMsgRuleBean>();

	private StudentInfoBean studentInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_child_pushrule);
		initViews();
		loadUserSettings();
		initListener();

		// 用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
		UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0016);

	}

	private void initListener()
	{
		btn_title_left.setOnClickListener(this);
		cb_cardremind.setOnCheckedChangeListener(this);
		cb_stationremind.setOnCheckedChangeListener(this);
	}

	private void initViews()
	{

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setVisibility(View.INVISIBLE);
		// tv_top_title.setText(R.string.registerTitle);

		cb_stationremind = (CheckBox) findViewById(R.id.cb_scpr_stationremind);
		cb_cardremind = (CheckBox) findViewById(R.id.cb_scpr_checkcard);

		cb_stationremind.setEnabled(false);
		cb_cardremind.setEnabled(false);
	}

	private void loadStudentinfo()
	{
		Intent intent = getIntent();
		if (intent == null)
			return;
		Bundle bundle = intent.getExtras();
		if (bundle == null)
			return;
		studentInfo = (StudentInfoBean) bundle
				.getSerializable(ActivityCommConstant.STUDENT_INFO);

	}

	/**
	 * 获取用户消息配置
	 */
	private void loadUserSettings()
	{

		// TODO 加载小孩数据
		loadStudentinfo();

		if (studentInfo != null)
		{
			tv_top_title.setText(studentInfo.getCld_name() + "的消息推送");
		}

		// TODO 获取用户关于该小孩的设置，这边需要检查下数据获取
		GetPushMsgSettingBiz getsetbiz = new GetPushMsgSettingBiz(this,
				new YtHandler()
				{
					@Override
					public void handleMessage(Message msg)
					{
						switch (msg.what)
						{
						case ThreadCommStateCode.REMOTE_DATA_CHANGED:
						case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
						case ThreadCommStateCode.COMMON_SUCCESS:
							Log.i("GetPushMsgSettingBiz", "获取推送配置成功");
							@SuppressWarnings("unchecked")
							List<PushMsgRuleBean> rules = (List<PushMsgRuleBean>) msg.obj;
							currentrules = rules;
							loadSettings();
							break;
						case ThreadCommStateCode.COMMON_FAILED:
							ToastMessage((String) msg.obj);
							break;
						default:
							ToastMessage((String) msg.obj);
							break;
						}
						super.handleMessage(msg);
					}
				});
		getsetbiz.execute();
	}

	// 配置开关对应的规则有哪些
	String[] satationruleids = { "01_002", "01_105" };

	String[] cardruleids = { "01_004", "01_007", "01_104", "01_107" };

	/**
	 * 根据下载的配置信息设置界面的现实
	 */
	private void loadSettings()
	{
		cb_stationremind.setEnabled(true);
		cb_cardremind.setEnabled(true);

		if (currentrules == null)
		{
			Log.i("loadSettings", "获取的推送配置=NULL");
			currentrules = new ArrayList<PushMsgRuleBean>();
			for (String ruleid : cardruleids)
			{
				PushMsgRuleBean rule = new PushMsgRuleBean();
				rule.setCld_id(studentInfo.getCld_id());
				rule.setRule_id(ruleid);
				rule.setFlag("0");
				currentrules.add(rule);
			}
			for (String ruleid : satationruleids)
			{
				PushMsgRuleBean rule = new PushMsgRuleBean();
				rule.setCld_id(studentInfo.getCld_id());
				rule.setRule_id(ruleid);
				rule.setFlag("0");
				currentrules.add(rule);
			}
		}

		int ccount = 0;
		int scount = 0;
		Log.i("loadSettings", "开始加载获取的配置");

		for (PushMsgRuleBean rule : currentrules)
		{
		    if(!studentInfo.getCld_id().equals(rule.getCld_id()))
                continue;
			for (String ruleid : cardruleids)
			{
				if (rule.getRule_id().equals(ruleid)
						&& rule.getOn_off().equals("1"))
				{
					ccount++;
				}
			}
			for (String ruleid : satationruleids)
			{
				if (rule.getRule_id().equals(ruleid)
						&& rule.getOn_off().equals("1"))
				{
					scount++;
				}
			}
		}

		if (ccount == cardruleids.length)
		{
			Log.i("loadSettings", "刷卡提醒配置为打开");
			cb_cardremind.setChecked(true);
		}
		else
		{
			Log.i("loadSettings", "刷卡提醒配置为关闭");
			cb_cardremind.setChecked(false);
		}
		if (scount == satationruleids.length)
		{
			Log.i("loadSettings", "刷卡到站提醒为打开");
			cb_stationremind.setChecked(true);
		}
		else
		{
			Log.i("loadSettings", "刷卡到站提醒为关闭");
			cb_stationremind.setChecked(false);
		}
		cb_cardremind.invalidate();
		cb_stationremind.invalidate();
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_title_right:

			break;
		case R.id.btn_title_left:
			finish();
			break;
		default:
			break;
		}

	}

	SetPushMsgSettingBiz setbiz;
	// add by lizyi
	// 开关是否打开
	private boolean isChanged;

	/**
	 * 更新配置信息
	 * 
	 * @param isChecked
	 */
	private void updateSettings()
	{
		if (setbiz == null)
		{
			YtHandler setHandler = new YtHandler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					super.handleMessage(msg, PushRuleActivity.this);
					// dismissLoading();
					switch (msg.what)
					{
					case ThreadCommStateCode.REMOTE_DATA_CHANGED:
					case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
					case ThreadCommStateCode.COMMON_SUCCESS:
						// Toast.makeText(getApplicationContext(), "修改提醒配置成功!",
						// Toast.LENGTH_SHORT).show();
						// add by lizyi 未区分刷卡提醒和站点提醒
						if (isChanged)
						{
							YtApplication.getInstance().isInitPush = false;
							YtApplication.getInstance().registerPushReceiver();
						}
						else
						{
							YtApplication.getInstance().isInitPush = true;
							YtApplication.getInstance()
									.unRegisterPushReceiver();
						}
						break;
					case ThreadCommStateCode.PUSH_MSG_NEEDNOT_SAVE:
						break;
					case ThreadCommStateCode.COMMON_FAILED:
						ToastMessage((String) msg.obj);
						break;

					default:
						ToastMessage((String) msg.obj);
						break;
					}

				}
			};

			setbiz = new SetPushMsgSettingBiz(this, setHandler, modifyRules);
		}
		// showLoading("修改配置中...", 1);
		setbiz.execute();
	}

	@Override
	protected void onLoadingCanceled(int key)
	{
		switch (key)
		{
		case 1:
			if (setbiz != null && !setbiz.isCanceled())
			{
				setbiz.cancel();
			}
			break;

		default:
			break;
		}
		super.onLoadingCanceled(key);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{

		modifyRules.clear();
		switch (buttonView.getId())
		{
		case R.id.cb_scpr_checkcard:
			// 设置刷卡提醒
			if (currentrules != null)
			{
				for (PushMsgRuleBean rule : currentrules)
				{
					for (String id : cardruleids)
					{
						if (rule.getRule_id().equals(id))
						{
							rule.setOn_off(isChecked ? "1" : "0");
							modifyRules.add(rule);
							break;
						}
					}
				}
			}
			updateSettings();
			isChanged = isChecked;
			break;
		case R.id.cb_scpr_stationremind:
			// 设置到站提醒
			if (currentrules != null)
			{
				for (PushMsgRuleBean rule : currentrules)
				{
					for (String id : satationruleids)
					{
						if (rule.getRule_id().equals(id))
						{
							rule.setOn_off(isChecked ? "1" : "0");
							modifyRules.add(rule);
							break;
						}
					}
				}
			}
			updateSettings();
			isChanged = isChecked;
			break;
		default:
			break;
		}
	}

}
