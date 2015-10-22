package com.yutong.axxc.parents.view.settings.account;

import java.lang.ref.WeakReference;
import java.util.UUID;

import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang3.StringUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.logout.LogoutBiz;
import com.yutong.axxc.parents.business.messagepush.PushNotificationUtil;
import com.yutong.axxc.parents.business.other.ResUploadBiz;
import com.yutong.axxc.parents.business.settings.UserInfoUpdateBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.BitmapUtils;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.ResourceInfoBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.common.rescache.ResFileCache;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.packet.LogoutReq;
import com.yutong.axxc.parents.connect.http.packet.ResourceDownloadRes;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ActivityManager;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.ResourcesUtils;
import com.yutong.axxc.parents.view.common.TransactionDataManager;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.TransactionDataManager.TransactionData;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.login.LoginActivity;
import com.yutong.axxc.parents.view.settings.child.HeadImageChangeActivity;

//import com.yutong.axxc.parents.view.common.ResourcesUtils;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 */
public class SettingAccountHomeActivity extends YtAbstractActivity implements
		OnClickListener
{

	/** popupWindow */
	private PopupWindow popupWindow;

	/** popupWindow 布局对象 */
	private View view;

	private Button loginoutButton;

	private Button btn_title_left;

	private TextView tv_top_title;

	private Button btn_title_right;

	private Button btn_changepwd;

	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	private ImageView txUserPhoto;

	private TextView tvLoginNameBig;

	private TextView tvUserNo;

	private TextView tvRoleInfo;

	private TextView tvLoginName;

	private TextView tvUserSex;

	private TextView tvUserPhone;

	private TextView tvUserName;

	private TextView tvUserAddress;

	private Intent intent;
	private Bundle bundle;

	/** 用户信息 */
	private UserInfoBean userInfo;
	
	private RelativeLayout userPhoneSettingsRL;

	private RelativeLayout sexSettingsRL;

	private RelativeLayout userNameSettingsRL;

	private RelativeLayout userAddressSettingsRL;

	private static int mSelectedItem;

	private YtAsyncTask biz;
	private ResUploadBiz resuploadbiz;

	private UserInfoBean userInfoBean;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_account_home);
		initViews();
		initListener();
		
		 //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0010);
        

	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		intData();
	}

	private void initListener()
	{
		loginoutButton.setOnClickListener(new PopupOnClickListener());

		// btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);

		loadingoverlay
				.addOnCancelListener(new LoadingOverlay.OnCancelListener()
				{

					@Override
					public void onCancel()
					{

						loadingoverlay.setVisibility(View.INVISIBLE);

					}
				});

		txUserPhoto.setOnClickListener(this);
		// tvLoginName.setOnClickListener(this);
		// tvUserSex.setOnClickListener(this);
		// tvUserName.setOnClickListener(this);
		// tvUserAddress.setOnClickListener(this);

		
		userPhoneSettingsRL.setOnClickListener(this);
		userNameSettingsRL.setOnClickListener(this);
		userAddressSettingsRL.setOnClickListener(this);
		sexSettingsRL.setOnClickListener(this);
		btn_changepwd.setOnClickListener(this);
	}

	private void initViews()
	{
		loginoutButton = (Button) findViewById(R.id.loginoutButton);
		loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		btn_title_right.setVisibility(View.INVISIBLE);

		tv_top_title.setText(R.string.myaccount);

		txUserPhoto = (ImageView) findViewById(R.id.txUserPhoto);
		tvLoginNameBig = (TextView) findViewById(R.id.tvLoginNameBig);
		tvUserNo = (TextView) findViewById(R.id.tvUserNo);
		tvRoleInfo = (TextView) findViewById(R.id.tvRoleInfo);

		// tvLoginName = (TextView) findViewById(R.id.tvLoginName);
		tvUserSex = (TextView) findViewById(R.id.tvUserSex);
		tvUserPhone = (TextView) findViewById(R.id.tvUserPhone);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvUserAddress = (TextView) findViewById(R.id.tvUserAddress);

		userPhoneSettingsRL = (RelativeLayout) findViewById(R.id.userPhoneSettingsRL);
		sexSettingsRL = (RelativeLayout) findViewById(R.id.sexSettingsRL);
		userNameSettingsRL = (RelativeLayout) findViewById(R.id.userNameSettingsRL);
		userAddressSettingsRL = (RelativeLayout) findViewById(R.id.userAddressSettingsRL);

		btn_changepwd = (Button) findViewById(R.id.btn_sah_changepwd);
		btn_changepwd.setText(R.string.update_password);
	}

	private void intData()
	{
		userInfo = YtApplication.getInstance().getUserInfoCache();

		if (userInfo != null)
		{

			if (StringUtils.isNotBlank(userInfo.getUsr_photo()))
			{
				ResourcesUtils.startGetImg(getApplicationContext(),
						txUserPhoto, userInfo.getUsr_photo());
			}

			tvLoginNameBig.setText(userInfo.getUsr_login_name());
			tvUserNo.setText(userInfo.getUsr_no());

			// if ("0".equals(ismainAccount)) {
			// tvRoleInfo.setText("您是主账号");
			// } else {
			// String showInfo = "您是由" +
			// userInfo.getMainUserInfoBean().getUsr_login_name() + "(安芯号:"
			// + userInfo.getMainUserInfoBean().getUsr_login_name() + ")授权";
			// tvUserSex.setText(showInfo);
			// }

			// tvLoginName.setText(userInfo.getUsr_login_name());

			String sex = userInfo.getUsr_sex();
			if ("0".equals(sex))
			{
				tvUserSex.setText("男");
			}
			else if ("1".equals(sex))
			{
				tvUserSex.setText("女");
			}
			else
			{
				tvUserSex.setText("未设置");
			}

			tvUserPhone.setText(userInfo.getUsr_phone());
			tvUserName.setText(userInfo.getUsr_name());
			tvUserAddress.setText(userInfo.getUsr_addr());
		}

	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();

	}

	private final static int REQUESTCODE_HEAD = 0x2001;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && null != data)
		{
			if (REQUESTCODE_HEAD == requestCode)
			{
				UUID tbmpkey = (UUID) data.getExtras().getSerializable(
						HeadImageChangeActivity.BUNDLE_CODE_IMAGEPATH);
				Bitmap tbmp = (Bitmap) TransactionDataManager.getInstance()
						.getData(tbmpkey).getObj();

				TransactionDataManager.getInstance().deleteData(tbmpkey);

				loadingoverlay.setVisibility(View.VISIBLE);
				loadingoverlay.setLoadingTip("正在执行，请稍等...");
				// TODO 这边提交头像修改信息
				// ResFileCache fileCache = new ResFileCache();
				// String jsonString = fileCache.getRes("22");
				// ResourceDownloadRes res = new ResourceDownloadRes();
				// res.parse(jsonString);
				// ResourceInfoBean resourceInfoBean =
				// ResourcesUtils.pagketResourceInfo("jpg",
				// res.getResourceInfoBean().getResourceBytes());

				ResourceInfoBean resourceInfoBean = ResourcesUtils
						.pagketResourceInfo("jpg",
								BitmapUtils.Bitmap2Bytes(tbmp));

				 //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
		        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0013);
		        
				resuploadbiz = new ResUploadBiz(getApplicationContext(),
						new UploadResProcessHandler(this), resourceInfoBean);
				resuploadbiz.execute();
			}
		}

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_title_left:
			finish();
			break;
		case R.id.btn_sah_changepwd:
			intent = new Intent(getApplication(),
					EditPWDInputOldPWDActivity.class);

			startActivity(intent);

			break;

		case R.id.txUserPhoto:
			intent = new Intent(getApplication(), HeadImageChangeActivity.class);
			bundle = new Bundle();
			UUID newid = UUID.randomUUID();
			txUserPhoto.setDrawingCacheEnabled(false);
			txUserPhoto.setDrawingCacheEnabled(true);
			TransactionDataManager.getInstance().putData(
					new TransactionData(newid, Bitmap.class, txUserPhoto
							.getDrawingCache()));
			bundle.putSerializable(
					HeadImageChangeActivity.BUNDLE_CODE_IMAGEPATH, newid);
			intent.putExtras(bundle);
			startActivityForResult(intent, REQUESTCODE_HEAD);
			overridePendingTransition(R.anim.enter_righttoleft,
					R.anim.exit_righttoleft);
			break;
		// case R.id.tvLoginName:
		// // 登录名不可被修改
		// // intent = new Intent(getApplication(),
		// // SettingAccountHomeActivity.class);
		// //
		// // startActivity(intent);
		// break;
		case R.id.sexSettingsRL:
			// sexSettingsRL

			// 数据列表
			String[] strList = new String[] { "男", "女" };

			AlertDialog mDialog = new AlertDialog.Builder(this)
					.setTitle("请选择性别")
					.setSingleChoiceItems(strList,

					"1".equals(userInfo.getUsr_sex()) ? 1 : 0,// 数据列表、第几个为选中
							new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									mSelectedItem = which;
								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									loadingoverlay.setVisibility(View.VISIBLE);
									loadingoverlay.setLoadingTip("正在执行，请稍等...");

									if (userInfo == null)
									{
										userInfoBean = new UserInfoBean();
									}
									else
									{
										userInfoBean = userInfo;
									}

									userInfoBean.setUsr_sex(String
											.valueOf(mSelectedItem));

									startTask(userInfoBean);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									dialog.dismiss();
								}
							}).create();

			mDialog.show();

			// new AlertDialog.Builder(PopupSelectedDialogDemo.this)
			// .setTitle("选择")
			// .setItems(ss,new DialogInterface.OnClickListener()
			// {
			// public void onClick(DialogInterface dialog, int whichcountry)
			// {
			// mTextView1.setText(ss[whichcountry]);
			// }
			// })
			// .setNegativeButton("取消", new DialogInterface.OnClickListener()
			// {
			// public void onClick(DialogInterface d, int which)
			// {
			// d.dismiss();
			// }
			// })
			// .show();
			//
			break;
		case R.id.userNameSettingsRL:

			intent = new Intent(getApplication(),
					EditUserRealNameActivity.class);
			bundle = new Bundle();
			bundle.putSerializable(ActivityCommConstant.USER_INFO, userInfo);
			intent.putExtras(bundle);
			startActivity(intent);

			break;
		case R.id.userAddressSettingsRL:

			intent = new Intent(getApplication(), EditUserAddressActivity.class);
			bundle = new Bundle();
			bundle.putSerializable(ActivityCommConstant.USER_INFO, userInfo);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	private void startTask(UserInfoBean userInfoBean)
	{
		if (biz != null)
			biz.cancel();
		biz = new UserInfoUpdateBiz(getApplicationContext(),
				new ProcessHandler(SettingAccountHomeActivity.this),
				userInfoBean);

		biz.execute();

	}

	private static class ProcessHandler extends YtHandler
	{
		private final WeakReference<SettingAccountHomeActivity> weakActivity;

		public ProcessHandler(SettingAccountHomeActivity activity)
		{
			this.weakActivity = new WeakReference<SettingAccountHomeActivity>(
					activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg)
		{
			Logger.i(SettingAccountHomeActivity.class,
					"[修改-handler]:msg.what:", msg.what);
			SettingAccountHomeActivity activity = weakActivity.get();
			if (activity != null)
			{
				super.handleMessage(msg, activity);
				switch (msg.what)
				{
				case ThreadCommStateCode.COMMON_SUCCESS:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					//Toast.makeText(activity.getApplicationContext(), "修改成功！",
					//		Toast.LENGTH_SHORT).show();
					if (activity.userInfoBean != null)
					{
						YtApplication.getInstance().setUserInfoCache(
								activity.userInfoBean);
					}
					activity.intData();
					break;

				case ThreadCommStateCode.TOKEN_INVALID:
					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"Token失效，请重新登录！", Toast.LENGTH_SHORT).show();
					break;

				case ThreadCommStateCode.COMMON_FAILED:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"修改失败！请重试！", Toast.LENGTH_SHORT).show();
					break;

				default:
					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}
	}

	private static class UploadResProcessHandler extends YtHandler
	{
		private final WeakReference<SettingAccountHomeActivity> weakActivity;

		public UploadResProcessHandler(SettingAccountHomeActivity activity)
		{
			this.weakActivity = new WeakReference<SettingAccountHomeActivity>(
					activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg)
		{
			Logger.i(SettingAccountHomeActivity.class,
					"[修改头像-handler]:msg.what:", msg.what);
			SettingAccountHomeActivity activity = weakActivity.get();
			Bitmap img;
			if (activity != null)
			{
				super.handleMessage(msg, activity);
				switch (msg.what)
				{
				case ThreadCommStateCode.COMMON_SUCCESS:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(), "修改头像成功！",
							Toast.LENGTH_SHORT).show();

					ResourceInfoBean res = (ResourceInfoBean) msg.obj;
					img = BitmapUtils
							.Bytes2Bimap(res.getResourceBytes());

					YtApplication.getInstance().getUserInfoCache()
							.setUsr_photo(res.getRes_id());
					// TODO这边修改头像
					activity.txUserPhoto.setImageBitmap(img);
					activity.userInfoBean = activity.userInfo;
					activity.userInfoBean.setUsr_photo(res.getRes_id());
					activity.startTask(activity.userInfoBean);
					// TODO
					img.isRecycled();
					break;

				case ThreadCommStateCode.TOKEN_INVALID:
					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"Token失效，请重新登录！", Toast.LENGTH_SHORT).show();
					break;

				case ThreadCommStateCode.COMMON_FAILED:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"修改头像失败！请重试！", Toast.LENGTH_SHORT).show();
					break;

				default:
					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}
	}

	/**
	 * Popup 单击监听类
	 * 
	 * @author <a href="mailto:fengzht@neusoft.com">Jason Feng</a>
	 * @version $Revision 1.1 $ 2013-3-26 上午8:54:04
	 */
	private class PopupOnClickListener implements OnClickListener
	{
		/** 退出 */
		private Button settingsLogoutButton;

		/** 取消退出 */
		private Button settingsCancelButton;

		/** 退出背景布局 */
		private RelativeLayout settingsBackGroundRL;

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v)
		{
			showPopupWindow(v);
		}

		/**
		 * 展现popup页面
		 * 
		 * @param parent
		 */
		private void showPopupWindow(View parent)
		{
			YtApplication.getInstance().unRegisterNetworkReceiver();

			if (popupWindow == null)
			{
				Logger.d(SettingAccountHomeActivity.class, "show pop us window");
				LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = layoutInflater.inflate(R.layout.settings_logout, null);
				initPageElement();
				registerListener();
				// 创建一个PopuWidow对象
				popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);
			}
			// 使其聚集
			popupWindow.setFocusable(true);
			// 设置允许在外点击消失
			popupWindow.setOutsideTouchable(true);

			// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
			popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		}

		/**
		 * 注册监听
		 */
		private void registerListener()
		{
			settingsLogoutButton.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					settingsLogoutButton.setEnabled(false);
					settingsCancelButton.setEnabled(false);
					popupWindow.setFocusable(false);
					// 20130805增加退出行为上报
					// UserBehaviorUploadBiz.startUserBehaviorUploadTask(null,
					// null, UserBehaviorConstants.MODULE_ID_BASE,
					// UserBehaviorConstants.MODULE_SON_ID_LOGOUT);
					new YtAsyncTask()
					{
						@Override
						protected void doInBackground()
						{
							// 注销
							new LogoutBiz(getApplicationContext()).logout();
							PushNotificationUtil.clearAllNotification();
							// 注销推送服务
							YtApplication.getInstance()
									.unRegisterPushReceiver();

							ActivityManager.clearReciverAndNotification();
							ActivityManager
									.closeOtherActivityExpectSettingAccountHome();
							// add by lizyi 
							// 注销调用了两次
							Intent intent = new Intent(YtApplication
									.getInstance().getApplicationContext(),
									LoginActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							YtApplication.getInstance().setWeatherInfoCache(
									null);
							startActivity(intent);
							ActivityManager.closeSettingAccountHomeActivity();
						}
					}.execute();
					// add by lizyi
					popupWindow.dismiss();
				}
			});
			settingsCancelButton.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					popupWindow.dismiss();
				}
			});
			settingsBackGroundRL.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (popupWindow != null && popupWindow.isShowing())
					{
						popupWindow.dismiss();
					}
				}
			});
		}

		/**
		 * 初始化页面元素
		 */
		private void initPageElement()
		{
			settingsLogoutButton = (Button) view
					.findViewById(R.id.settingsLogoutButton);
			settingsCancelButton = (Button) view
					.findViewById(R.id.settingsCancelButton);
			settingsBackGroundRL = (RelativeLayout) view
					.findViewById(R.id.settingsBackGroundRL);
		}
	}

}
