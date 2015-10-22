package com.yutong.axxc.parents.view.home;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.student.GetStudentInfoBiz;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.adapter.BindStudentAdapter;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtHandler;

/**
 * 
 * 
 * @author zhangyongn
 * 
 */
public class BindChildActivity_Succ extends YtAbstractActivity implements
		OnClickListener {
	private YtAsyncTask biz;
	private List<StudentInfoBean> students;
	// 加载弹出提示相关视图
		private LoadingOverlay loadingoverlay;
	
	
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;

	private TextView succtitle;
	private TextView succtip;
	
	private ListView list_view;
	private Button btn_confirm;
	private BindStudentAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bindchild_succ);
		
		initViews();
		initListener();
		initStudents();
	}
	private void initStudents() {
		loadingoverlay.setVisibility(View.VISIBLE);
		loadingoverlay.setLoadingTip("正在加载学生信息,请稍候...");
		startLoadStudentTask();
	}
	private void startLoadStudentTask() {
		if (biz != null)
			biz.cancel();
		biz = new GetStudentInfoBiz(getApplicationContext(),
				new StudentHandler(BindChildActivity_Succ.this),ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE);

		biz.execute();

	}
	private void initListener() {

		btn_title_left.setOnClickListener(this);
		btn_title_right.setOnClickListener(this);
		loadingoverlay
		.addOnCancelListener(new LoadingOverlay.OnCancelListener() {

			@Override
			public void onCancel() {
				if(biz!=null)
				{
					biz.cancel();
					biz=null;
				}
				loadingoverlay.setVisibility(View.INVISIBLE);

			}
		});
	}

	

	private void initViews() {
		loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);
		list_view=(ListView) findViewById(R.id.list_view);
		//btn_confirm = (Button) findViewById(R.id.btn_confirm);
		
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		succtip = (TextView) findViewById(R.id.succtip);
		succtitle = (TextView) findViewById(R.id.succtitle);

		btn_title_right.setText("进入首页");

		tv_top_title.setText(R.string.bindchildtitle);
		btn_title_left.setVisibility(View.INVISIBLE);

		adapter = new BindStudentAdapter(this);
		list_view.setAdapter(adapter);
		list_view.setDivider(null);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_title_right:

			Intent intent1 = new Intent(getApplication(),
					MainActivity.class);
			startActivity(intent1);
			finish();
			break;

		case R.id.btn_title_left:
			
			break;
		default:
			break;
		}
	}
	private static class StudentHandler extends YtHandler {
		private final WeakReference<BindChildActivity_Succ> activity;

		public StudentHandler(BindChildActivity_Succ activity) {
			this.activity = new WeakReference<BindChildActivity_Succ>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {

			BindChildActivity_Succ ac = activity.get();
			if (ac != null) {
			    ac.loadingoverlay.setVisibility(View.INVISIBLE);
				super.handleMessage(msg, ac);
				switch (msg.what) {
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:
					ac.loadingoverlay.setVisibility(View.INVISIBLE);

					ac.students = (List<StudentInfoBean>) msg.obj;
					ac.buildStudentsUI();
					break;
				case ThreadCommStateCode.COMMON_FAILED:
					ac.ToastMessage((String) msg.obj);

					break;

				default:
					break;
				}
			}
		}
	}
	public void buildStudentsUI() {
		View v = null;
		if (students == null)
			return;
		adapter.SetDatas(students);
		adapter.notifyDataSetChanged();
		
	}
}
