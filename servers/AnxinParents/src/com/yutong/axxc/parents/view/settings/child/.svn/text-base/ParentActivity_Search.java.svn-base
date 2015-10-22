package com.yutong.axxc.parents.view.settings.child;

import java.lang.ref.WeakReference;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.student.SearchCertigierUserBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ExEditText;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtHandler;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 * 
 */
public class ParentActivity_Search extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	private StudentInfoBean studentInfo;
	SearchCertigierUserBiz biz;
	private ExEditText et_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_child_parent_search);
		initViews();
		initListener();
		loadInfo();
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

		btn_title_right.setText("提交");
		tv_top_title.setText("添加关联的家长");
		et_code = (ExEditText) findViewById(R.id.et_scps_search);

	}

	 private void loadInfo()
	    {
	        Intent intent = getIntent();
	        if (intent == null)
	            return;
	        Bundle bundle = intent.getExtras();
	        studentInfo = (StudentInfoBean) bundle.getSerializable(ActivityCommConstant.STUDENT_INFO);

	    }
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	public final static String BUNDLE_CODE_PARENT = "BUNDLE_CODE_PARENT";

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:
			// TODO 根据输入，开始搜索家长
			String account = et_code.getText().toString();
			//这里开始查询，下面的代码是查询完后作的操作
			
			startTask(account);

			break;
		case R.id.btn_title_left:
			finish();
			break;
		default:
			break;
		}

	}
	
    private void startTask(String account) {

        if (biz != null)
            biz.cancel();
        loadingoverlay.setVisibility(View.VISIBLE);
        loadingoverlay.setLoadingTip("正在加载信息,请稍候...");
	       biz = new SearchCertigierUserBiz(getApplicationContext(),
	                new ProcessHandler(ParentActivity_Search.this),
 account, studentInfo.getCld_id());

	        biz.execute();

	    }
	   
	    private static class ProcessHandler extends YtHandler
	    {
	        private final WeakReference<ParentActivity_Search> weakActivity;

	        public ProcessHandler(ParentActivity_Search activity)
	        {
	            this.weakActivity = new WeakReference<ParentActivity_Search>(activity);
	        }

	        /**
	         * (non-Javadoc)
	         * 
	         * @see android.os.Handler#handleMessage(android.os.Message)
	         */
	        @Override
	        public void handleMessage(Message msg)
	        {
	            Logger.i(ParentActivity_Search.class, "[查询授权家长-handler]:msg.what:", msg.what);
	            ParentActivity_Search activity = weakActivity.get();
	            if (activity != null)
	            {
	                super.handleMessage(msg, activity);
	                switch (msg.what)
	                {
	                case ThreadCommStateCode.COMMON_SUCCESS:

	                    activity.loadingoverlay.setVisibility(View.INVISIBLE);
	                    //Toast.makeText(activity.getApplicationContext(), "查询授权家长成功！", Toast.LENGTH_SHORT).show();
	                    UserInfoBean bean =  (UserInfoBean)msg.obj;
	                    
	                    // 搜索到结果，转到授权页面
	                    Intent intent = new Intent(activity, ParentActivity_Result.class);
	                    Bundle bundle = new Bundle();
	                    bundle.putSerializable(ActivityCommConstant.USER_INFO, bean);
	                    bundle.putSerializable(ActivityCommConstant.STUDENT_INFO, activity.studentInfo);
	                    intent.putExtras(bundle);
	                    activity.startActivity(intent);
	                    
	                    break;

	                case ThreadCommStateCode.TOKEN_INVALID:
	                    activity.loadingoverlay.setVisibility(View.INVISIBLE);
	                    Toast.makeText(activity.getApplicationContext(), "Token失效，请重新登录！", Toast.LENGTH_SHORT).show();
	                    break;

	                case ThreadCommStateCode.COMMON_FAILED:

	                    activity.loadingoverlay.setVisibility(View.INVISIBLE);
	                    Toast.makeText(activity.getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();
	                    break;

	                default:
	                    activity.loadingoverlay.setVisibility(View.INVISIBLE);
	                    Toast.makeText(activity.getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();
	                    break;
	                }
	            }
	        }
	    }
}
