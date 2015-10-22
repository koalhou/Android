package com.yutong.axxc.parents.view.settings.child;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.student.AddCertigierUserBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.settings.child.ParentActivity.ParentInfo;
import com.yutong.axxc.parents.view.settings.child.ParentActivity.ParentListAdapter;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 */
public class ParentActivity_Result extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;

	private TextView tv_top_title;

	private Button btn_title_right;

	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	private ListView lv_parents;

	private ParentListAdapter adapter;

	private List<ParentInfo> pdata;

	private UserInfoBean user;

	private AddCertigierUserBiz biz;

	private StudentInfoBean studentInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_child_parent_result);
		initViews();
		initListener();
		loadData();
	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
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

		btn_title_right.setText("确认授权");
		btn_title_right.setEnabled(false);
		tv_top_title.setText("账号详情");

		lv_parents = (ListView) findViewById(R.id.lv_scpr_parents);
		pdata = new ArrayList<ParentActivity.ParentInfo>();
		adapter = new ParentListAdapter(this, pdata);
		lv_parents.setAdapter(adapter);

	}

	private void loadData() {
		// TODO 加载家长数据结果

		Intent intent = getIntent();
		if (intent == null)
			return;
		Bundle bundle = intent.getExtras();
		user = (UserInfoBean) bundle
				.getSerializable(ActivityCommConstant.USER_INFO);

		studentInfo = (StudentInfoBean) bundle
				.getSerializable(ActivityCommConstant.STUDENT_INFO);

		if (user != null) {
			ParentInfo painfo = new ParentInfo();
			painfo.setCname(user.getUsr_name());
			painfo.setEname(user.getUsr_login_name());
			painfo.setPhonenum(user.getUsr_phone());
			painfo.setRes_id(user.getUsr_photo());
			if (YtApplication.getInstance().getUid().equals(user.getUsr_id())) {
				painfo.setIs_self(true);
			} else {
				painfo.setIs_self(false);
			}

			pdata.add(painfo);

			adapter.notifyDataSetChanged();
			btn_title_right.setEnabled(true);
		}else{
			
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:
			// TODO 授权
			startTask();
			break;
		case R.id.btn_title_left:
			finish();
			break;
		default:
			break;
		}

	}

	private void startTask() {

		if (biz != null)
			biz.cancel();
		loadingoverlay.setVisibility(View.VISIBLE);
		loadingoverlay.setLoadingTip("正在执行,请稍候...");
		biz = new AddCertigierUserBiz(getApplicationContext(),
				new ProcessHandler(ParentActivity_Result.this),
				user.getUsr_id(), studentInfo.getCld_id());

		biz.execute();

	}

	private class ProcessHandler extends YtHandler {
		private final WeakReference<ParentActivity_Result> weakActivity;

		public ProcessHandler(ParentActivity_Result activity) {
			this.weakActivity = new WeakReference<ParentActivity_Result>(
					activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(ParentActivity_Result.class, "[添加授权家长-handler]:msg.what:",
					msg.what);
			ParentActivity_Result activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					ToastMessage("添加授权家长成功");
					Intent intent = new Intent(ParentActivity_Result.this,
							ParentActivity.class);
	                   Bundle bundle = new Bundle();
	                    bundle.putSerializable(ActivityCommConstant.STUDENT_INFO,
	                            studentInfo);
	                    intent.putExtras(bundle);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					
					startActivity(intent);

					// Toast.makeText(activity.getApplicationContext(),
					// "查询授权家长成功！", Toast.LENGTH_SHORT).show();

					break;

				case ThreadCommStateCode.TOKEN_INVALID:
					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"Token失效，请重新登录！", Toast.LENGTH_SHORT).show();
					break;

				case ThreadCommStateCode.COMMON_FAILED:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
					        (String) msg.obj, Toast.LENGTH_SHORT).show();
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
