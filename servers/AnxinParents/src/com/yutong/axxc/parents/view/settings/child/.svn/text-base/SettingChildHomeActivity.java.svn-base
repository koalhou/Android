package com.yutong.axxc.parents.view.settings.child;

import java.lang.ref.WeakReference;
import java.util.UUID;

import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang3.StringUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.cache.AppCacheData;
import com.yutong.axxc.parents.business.other.ResUploadBiz;
import com.yutong.axxc.parents.business.student.GetStudentCustomInfoBiz;
import com.yutong.axxc.parents.business.student.SetStudentCustomInfoBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.BitmapUtils;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.beans.ResourceInfoBean;
import com.yutong.axxc.parents.common.beans.StudentCustomInfoBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ChildCustomConfigs;
import com.yutong.axxc.parents.view.common.ChildCustomConfigs.ChildCustomTheme;
import com.yutong.axxc.parents.view.common.TransactionDataManager.TransactionData;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.ResourcesUtils;
import com.yutong.axxc.parents.view.common.TransactionDataManager;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 * 
 */
public class SettingChildHomeActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	private TextView tv_childname;

	private RelativeLayout customsetColorRL;
	private RelativeLayout customsetSoundRL;
	private RelativeLayout remindsetRL;
	private RelativeLayout pushsetRL;
	private RelativeLayout historyRL;
	private RelativeLayout parentRL;
	private RelativeLayout othersRL;

	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	private ResUploadBiz resuploadbiz;
	private SetStudentCustomInfoBiz studentCustomBiz;

	private Button btn_cancelrelation;
	private ImageView imv_head;
	private ImageView imv_color;

	private TextView tv_color;
	private TextView tv_tip;

	private StudentCustomInfoBean customInfo;
	private StudentInfoBean studentInfo;

	private GetStudentCustomInfoBiz scustomTask;

	private final int REQUESTCODE_HEAD = 0x1001;
	private final int REQUESTCODE_COLOR = REQUESTCODE_HEAD + 1;
	private final int REQUESTCODE_NAME = REQUESTCODE_HEAD + 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_child_home);
		initViews();
		initListener();

		loadStudentinfo();
		
		 //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0014);
        

	}

	private void loadStudentinfo() {
		Intent intent = getIntent();
		if (intent == null)
			return;
		Bundle bundle = intent.getExtras();
		if (bundle == null)
			return;
		studentInfo = (StudentInfoBean) bundle
				.getSerializable(ActivityCommConstant.STUDENT_INFO);

		studentInfo = AppCacheData.getStudentInfo(YtApplication.getInstance()
				.getUid(), studentInfo.getCld_id());

		if (studentInfo == null)
			return;

		checkIsMainParent();

		// scustomTask = new GetStudentCustomInfoBiz(this, settinghandler,
		// studentInfo.getCld_id());
		// scustomTask.execute();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadStudentinfo();
		loadViewByInfo();
	}

	private void checkIsMainParent() {
		// 获取是否是主账户的信息
		if (studentInfo == null) {
			// ToastMessage("学生信息：NULL");
			return;
		}
		if (studentInfo.getUsr_main() == null) {
			// ToastMessage("是否是主账号信息：NULL");
			return;
		}
		// ToastMessage("是否是主账号信息：" + studentInfo.getUsr_main());
		if (studentInfo.getUsr_main().equals("1")) {
			// ToastMessage("本账号是主账号 显示家长栏");
			parentRL.setVisibility(View.VISIBLE);
		} else {// 授权家长暂时没有权限查看家长信息
			// ToastMessage("本账号是授权账号 不显示家长栏");
			parentRL.setVisibility(View.GONE);
		}

	}


	@Override
	protected void onStart() {
		super.onStart();

	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);

		customsetColorRL.setOnClickListener(this);
		customsetSoundRL.setOnClickListener(this);
		remindsetRL.setOnClickListener(this);
		pushsetRL.setOnClickListener(this);
		historyRL.setOnClickListener(this);
		parentRL.setOnClickListener(this);
		othersRL.setOnClickListener(this);

		btn_cancelrelation.setOnClickListener(this);
		imv_head.setOnClickListener(this);
		tv_childname.setOnClickListener(this);

		loadingoverlay
				.addOnCancelListener(new LoadingOverlay.OnCancelListener() {

					@Override
					public void onCancel() {

						loadingoverlay.setVisibility(View.INVISIBLE);

					}
				});
	}

	private void initViews() {

		loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		customsetColorRL = (RelativeLayout) findViewById(R.id.customsetColorRL);
		customsetSoundRL = (RelativeLayout) findViewById(R.id.customsetSoundRL);
		remindsetRL = (RelativeLayout) findViewById(R.id.remindsetRL);
		pushsetRL = (RelativeLayout) findViewById(R.id.pushsetRL);
		historyRL = (RelativeLayout) findViewById(R.id.historyRL);
		parentRL = (RelativeLayout) findViewById(R.id.parentRL);
		othersRL = (RelativeLayout) findViewById(R.id.othersRL);

		btn_cancelrelation = (Button) findViewById(R.id.btn_sch_cancelrelation);
		imv_head = (ImageView) findViewById(R.id.cimv_sch_childhead);
		imv_color=(ImageView)findViewById(R.id.iv_sch_colorset);
		tv_childname = (TextView) findViewById(R.id.tv_sch_childname);

		tv_color = (TextView) findViewById(R.id.tv_sch_colorname);
		tv_tip = (TextView) findViewById(R.id.tv_sch_tipsound);

		btn_title_right.setVisibility(View.INVISIBLE);
		customsetSoundRL.setVisibility(View.GONE);
	}

	private void loadViewByInfo() {
		// 设置姓名显示
		if (studentInfo != null) {
			tv_top_title.setText("小孩设置-" + studentInfo.getCld_name());

			if (StringUtils.isNotBlank(studentInfo.getCld_alias())) {
				tv_childname.setText(studentInfo.getCld_alias());
			}

		} else {
			tv_top_title.setText("小孩设置");
			tv_childname.setText("设定昵称");
			return;
		}

		// TODO 设置个性声音的名字
		// tv_tip.setText(studentInfo.getCld_audio());

		// 设置个性颜色文本
		if (StringUtils.isNotBlank(studentInfo.getCld_color())) {
			ChildCustomTheme theme = ChildCustomConfigs.getInstance()
					.getChildCustomThemeByKey(studentInfo.getCld_color());
			tv_color.setText(theme.getName());
			imv_color.setImageResource(theme.getIconResId());
		}

		if ("1".equals(studentInfo.getCld_sex())) {
			imv_head.setImageResource(R.drawable.default_gril);
		} else {
			imv_head.setImageResource(R.drawable.default_boy);
		}

		// 设置头像
		if (StringUtils.isNotBlank(studentInfo.getCld_photo())) {
			ResourcesUtils.startGetImg(getApplicationContext(), imv_head,
					studentInfo.getCld_photo());
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && null != data) {
			switch (requestCode) {
			case REQUESTCODE_HEAD:
				UUID tbmpkey = (UUID) data.getExtras().getSerializable(
						HeadImageChangeActivity.BUNDLE_CODE_IMAGEPATH);
				Bitmap tbmp = (Bitmap) TransactionDataManager.getInstance()
						.getData(tbmpkey).getObj();
				if (tbmp != null)
					// imv_head.setImageBitmap(tbmp);
					TransactionDataManager.getInstance().deleteData(tbmpkey);
				// TODO 这边提交头像修改信息

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

				break;
			case REQUESTCODE_COLOR:
				ChildCustomTheme theme = ChildCustomConfigs.getInstance()
						.getChildCustomThemeByKey(
								data.getExtras().getString(
										PersonalSetActivity.COLORSET_CODE));
				tv_color.setText(theme.getName());
				imv_color.setImageResource(theme.getIconResId());
				break;
			case REQUESTCODE_NAME:
				break;
			default:
				break;
			}

		}

	}

	@Override
	public void onClick(View v) {
		Intent intent1 = null;
		switch (v.getId()) {
		case R.id.btn_title_right:

			break;
		case R.id.btn_title_left:
			finish();
			break;
		case R.id.tv_sch_childname:// 孩子昵称修改
			intent1 = new Intent(getApplication(),
					ChildAliasChangeActivity.class);
			Bundle nbundle = new Bundle();
			nbundle.putSerializable(ActivityCommConstant.STUDENT_INFO,
					studentInfo);
			intent1.putExtras(nbundle);
			startActivityForResult(intent1, REQUESTCODE_NAME);
			return;
		case R.id.customsetColorRL:// 个性色彩设置
			intent1 = new Intent(getApplication(), PersonalSetActivity.class);
			Bundle cbundle = new Bundle();
			cbundle.putSerializable(ActivityCommConstant.STUDENT_INFO,
					studentInfo);
			intent1.putExtras(cbundle);
			startActivityForResult(intent1, REQUESTCODE_COLOR);
			return;
		case R.id.remindsetRL:// 提醒设置
			intent1 = new Intent(getApplication(), RemindSetActivity.class);
			break;
		case R.id.pushsetRL:// 推送信息设置
			intent1 = new Intent(getApplication(), PushRuleActivity.class);
			break;
		case R.id.historyRL:// 乘车历史查看
			intent1 = new Intent(getApplication(), TravelRecordActivity.class);
			break;
		case R.id.parentRL:// 家长显示
			intent1 = new Intent(getApplication(), ParentActivity.class);
			break;
		case R.id.othersRL:// 孩子的其他基本信息
			intent1 = new Intent(getApplication(), OthersActivity.class);
			break;
		case R.id.btn_sch_cancelrelation:// 取消孩子关联
			// TODO 取消关联
			break;
		case R.id.cimv_sch_childhead:// 孩子头像设置
			intent1 = new Intent(getApplication(),
					HeadImageChangeActivity.class);
			Bundle bundle = new Bundle();
			UUID newid = UUID.randomUUID();
			imv_head.setDrawingCacheEnabled(false);
			imv_head.setDrawingCacheEnabled(true);
			TransactionDataManager.getInstance().putData(
					new TransactionData(newid, Bitmap.class, imv_head
							.getDrawingCache()));
			bundle.putSerializable(
					HeadImageChangeActivity.BUNDLE_CODE_IMAGEPATH, newid);
			intent1.putExtras(bundle);
			startActivityForResult(intent1, REQUESTCODE_HEAD);
			overridePendingTransition(R.anim.enter_righttoleft,
					R.anim.exit_righttoleft);
			return;
		default:
			break;
		}
		if (intent1 != null) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(ActivityCommConstant.STUDENT_INFO,
					studentInfo);
			intent1.putExtras(bundle);
			startActivity(intent1);
			overridePendingTransition(R.anim.enter_righttoleft,
					R.anim.exit_righttoleft);
		}
	}

	private void startTask(StudentCustomInfoBean studentCustomInfoBean) {
		if (studentCustomBiz != null)
			studentCustomBiz.cancel();
		studentCustomBiz = new SetStudentCustomInfoBiz(getApplicationContext(),
				new ProcessHandler(SettingChildHomeActivity.this),
				studentInfo.getCld_id(), studentCustomInfoBean);

		studentCustomBiz.execute();

	}

	private static class ProcessHandler extends YtHandler {
		private final WeakReference<SettingChildHomeActivity> weakActivity;

		public ProcessHandler(SettingChildHomeActivity activity) {
			this.weakActivity = new WeakReference<SettingChildHomeActivity>(
					activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(SettingChildHomeActivity.class, "[修改-handler]:msg.what:",
					msg.what);
			SettingChildHomeActivity activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					//Toast.makeText(activity.getApplicationContext(), "修改成功！",
					//		Toast.LENGTH_SHORT).show();

					activity.loadStudentinfo();
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

	private static class UploadResProcessHandler extends YtHandler {
		private final WeakReference<SettingChildHomeActivity> weakActivity;

		public UploadResProcessHandler(SettingChildHomeActivity activity) {
			this.weakActivity = new WeakReference<SettingChildHomeActivity>(
					activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(SettingChildHomeActivity.class,
					"[修改头像-handler]:msg.what:", msg.what);
			SettingChildHomeActivity activity = weakActivity.get();
			Bitmap img;
			if (activity != null) {
				super.handleMessage(msg, activity);
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(), "修改头像成功！",
							Toast.LENGTH_SHORT).show();

					ResourceInfoBean res = (ResourceInfoBean) msg.obj;
					img = BitmapUtils
							.Bytes2Bimap(res.getResourceBytes());
					// TODO这边修改头像
					activity.imv_head.setImageBitmap(img);

					// 设置头像显示

					activity.studentInfo.setCld_photo(res.getRes_id());

					AppCacheData.putStudentInfo(YtApplication.getInstance()
							.getUid(), activity.studentInfo);

					activity.startTask(activity.studentInfo.getCustonInfo());
					
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

}
