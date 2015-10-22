package com.yutong.axxc.parents.view.settings.child;

import org.apache.commons.lang3.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.student.GetStudentCustomInfoBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.settings.SettingHomeActivity;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 * 
 */
public class OthersActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;

	private TextView tv_name, tv_sex, tv_schoolname, tv_class, tv_cardnum, tv_schoolnum,
			tv_teacher;
	private StudentInfoBean studentInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_child_others);
		initViews();
		initListener();
		loadStudentinfo();
		
		 //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0020);
        
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
		freshViewsByInfo(studentInfo);
	}

	private void freshViewsByInfo(StudentInfoBean info) {
	    
	    tv_top_title.setText("学生信息");
		if (info != null) {
		    
		    String sex = "未知";
		    if(StringUtils.isNotBlank(info.getCld_sex()))
		    {
		        sex = "0".equals(info.getCld_sex())?"男":"女";
		    }
		    tv_name.setText(info.getCld_name());
			tv_sex.setText(sex);
			tv_schoolname.setText(info.getCld_school());
			tv_class.setText(info.getCld_class());
			tv_cardnum.setText(info.getCld_code());
			tv_schoolnum.setText(info.getCld_no());
			tv_teacher.setText(info.getCld_class_adviser());
			
			tv_top_title.setText(info.getCld_name() + "信息");
		}
	}

	private void initListener() {
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

	private void initViews() {
		// loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
	
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		btn_title_right.setVisibility(View.INVISIBLE);
		
		tv_name = (TextView) findViewById(R.id.tv_sco_name);
		tv_sex = (TextView) findViewById(R.id.tv_sco_sex);
		tv_schoolname = (TextView) findViewById(R.id.tv_sco_schoolname);
		tv_class = (TextView) findViewById(R.id.tv_sco_class);
		tv_cardnum = (TextView) findViewById(R.id.tv_sco_cardnum);
		tv_schoolnum = (TextView) findViewById(R.id.tv_sco_schoolnum);
		tv_teacher = (TextView) findViewById(R.id.tv_sco_teacher);

		btn_title_right.setText(R.string.next);

		// tv_top_title.setText(R.string.registerTitle);

		// TODO 获取孩子信息并显示

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
