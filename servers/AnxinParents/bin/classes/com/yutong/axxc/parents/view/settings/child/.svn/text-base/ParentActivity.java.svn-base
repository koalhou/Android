package com.yutong.axxc.parents.view.settings.child;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.student.DeleteCertigierUserBiz;
import com.yutong.axxc.parents.business.student.GetCertigierInfoBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.CustomAlertDialog;
import com.yutong.axxc.parents.view.common.ResourcesUtils;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 */
public class ParentActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;

	private TextView tv_top_title;

	private Button btn_title_right;

	private ListView lv_parents;

	private ParentListAdapter adapter;

	private List<ParentInfo> pdata;

	private GetCertigierInfoBiz biz;

	private StudentInfoBean studentInfo;

	// 加载弹出提示相关视图
	// private LoadingOverlay loadingoverlay;

	private List<UserInfoBean> userInfoBeans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_child_parent);
		initViews();
		initListener();

		loadStudentinfo();
		loadParentData();
		
		 //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0019);
        
	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);

		lv_parents.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				if (studentInfo.getUsr_main() == null)// 用户对孩子的授权信息未能获取到不能移除授权
				{
					Logger.i(this.getClass(), "用户对孩子的授权信息未能获取到不能移除授权");
					return;
				}

				UserInfoBean info = userInfoBeans.get(arg2);
				String title = "解除["
						+ (StringUtil.isNull(info.getUsr_name()) ? "未填写" : info
								.getUsr_name()) + "]的授权";
				final String uid = info.getUsr_id();

				if (uid.equals(YtApplication.getInstance().getUid())) {// 本人不能删除自己的授权
					Logger.i(this.getClass(), "本人不能删除自己的授权");
					return;
				} else {

					if (info.getUsr_main().equals("1"))// 主授权人不能移除授权
					{
						Logger.i(this.getClass(), "主授权人不能移除授权");
						return;
					}
				}

				CustomAlertDialog deletedia = new CustomAlertDialog(
						ParentActivity.this) {
					@Override
					public void onUserOK() {
						Logger.i(this.getClass(), "to delete" + uid);
						startDeleteTask(uid);
					}

					@Override
					public void onUserCancel() {
					}
				};
				deletedia.setTitle("提示");
				deletedia.setOkStr(title);
				deletedia.setCancelStr("取消");

				deletedia.show();

			}
		});

		/*
		 * loadingoverlay .addOnCancelListener(new
		 * LoadingOverlay.OnCancelListener() {
		 * 
		 * @Override public void onCancel() {
		 * 
		 * loadingoverlay.setVisibility(View.INVISIBLE);
		 * 
		 * } });
		 */

	}

	private void initViews() {
		// loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setText("添加");
		tv_top_title.setText("关联的家长");
		lv_parents = (ListView) findViewById(R.id.lv_scp_parents);
		pdata = new ArrayList<ParentInfo>();
		adapter = new ParentListAdapter(this, pdata);
		lv_parents.setAdapter(adapter);
	}

	private void loadStudentinfo() {
		Intent intent = getIntent();
		if (intent == null)
			return;
		Bundle bundle = intent.getExtras();
		studentInfo = (StudentInfoBean) bundle
				.getSerializable(ActivityCommConstant.STUDENT_INFO);
		if (studentInfo != null) {
			tv_top_title.setText("关联" + studentInfo.getCld_name() + "的家长");
		}

	}

	private static final int LOADING_CODE_1 = 0X1001;
	private static final int LOADING_CODE_2 = 0X1002;

	private void loadParentData() {
		// TODO 获取孩子授权家长的信息
		// pdata.clear();
		// ParentInfo info = new ParentInfo();
		// info.setCname("吴玲");
		// info.setEname("（Lin-W）");
		// info.setPhonenum("18605365875");
		// pdata.add(info);
		// info = new ParentInfo();
		// info.setCname("罗明");
		// info.setEname("（Ming-L）");
		// info.setPhonenum("18605332175");
		// pdata.add(info);

		// 家长id：161,学生：71 有授权家长,test
		if (studentInfo == null) {
			ToastMessage("没有获取到孩子信息，无法加载家长信息");
			return;
		}
		showLoading("正在加载信息,请稍候...", LOADING_CODE_1);
		// loadingoverlay.setVisibility(View.VISIBLE);
		// loadingoverlay.setLoadingTip("正在加载信息,请稍候...");

		biz = new GetCertigierInfoBiz(getApplication(),
				new ProcessHandler(this), studentInfo.getCld_id());
		// biz = new GetCertigierInfoBiz(getApplication(), new
		// ProcessHandler(this), studentInfo.getCld_id());
		biz.execute();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:
			Intent intent = new Intent(getApplication(),
					ParentActivity_Search.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable(ActivityCommConstant.STUDENT_INFO,
					studentInfo);
			intent.putExtras(bundle);
			startActivity(intent);
			overridePendingTransition(R.anim.enter_righttoleft,
					R.anim.exit_righttoleft);
			break;
		case R.id.btn_title_left:
			finish();
			break;
		default:
			break;
		}

	}

	public static class ParentInfo implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4650962361538944431L;

		private Drawable headimag;

		private String res_id;

		private String cname;

		private String ename;

		private String phonenum;

		private boolean is_self;

		public boolean isIs_self() {
			return is_self;
		}

		public void setIs_self(boolean is_self) {
			this.is_self = is_self;
		}

		public Drawable getHeadimag() {
			return headimag;
		}

		public void setHeadimag(Drawable headimag) {
			this.headimag = headimag;
		}

		public String getCname() {
			return cname;
		}

		public void setCname(String cname) {
			this.cname = cname;
		}

		public String getEname() {
			return ename;
		}

		public void setEname(String ename) {
			this.ename = ename;
		}

		public String getPhonenum() {
			return phonenum;
		}

		public void setPhonenum(String phonenum) {
			this.phonenum = phonenum;
		}

		public String getRes_id() {
			return res_id;
		}

		public void setRes_id(String res_id) {
			this.res_id = res_id;
		}

	}

	public static class ParentListAdapter extends BaseAdapter {

		private List<ParentInfo> mdata;

		private Context mcontext;

		public ParentListAdapter(Context context, List<ParentInfo> data) {
			mcontext = context;
			mdata = data;
		}

		@Override
		public int getCount() {
			if (mdata == null)
				return 0;
			return mdata.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder holder = null;
			if (arg1 == null) {
				arg1 = View.inflate(mcontext,
						R.layout.setting_child_parentitem, null);
				holder = new ViewHolder();
				holder.Head = (ImageView) arg1.findViewById(R.id.iv_scpi_head);
				holder.CName = (TextView) arg1.findViewById(R.id.tv_scpi_cname);
				holder.Ename = (TextView) arg1.findViewById(R.id.tv_scpi_ename);
				holder.Phone = (TextView) arg1.findViewById(R.id.tv_scpi_phone);
				holder.Mark = (ImageView) arg1.findViewById(R.id.iv_scpi_mark);
				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}

			ParentInfo info = mdata.get(arg0);

			// 设置头像
			if (StringUtils.isNotBlank(info.getRes_id()))
				ResourcesUtils.startGetImg(mcontext, holder.Head,
						info.getRes_id());
			else
				holder.Head.setImageDrawable(mcontext.getResources()
						.getDrawable(R.drawable.user_head_default));

			// TODO
			if (StringUtils.isBlank(info.getCname())) {
				holder.CName.setText("[未填写]");
			} else {
				holder.CName.setText(info.getCname());
			}

			holder.Ename.setText("(" + info.getEname() + ")");
			holder.Phone.setText(info.getPhonenum());

			if (info.is_self) {
				// TODO 设置是否本人标志
				holder.Mark.setImageDrawable(mcontext.getResources()
						.getDrawable(R.drawable.current_mark));
			} else {
				holder.Mark.setImageDrawable(null);
			}

			return arg1;
		}

		class ViewHolder {
			public ImageView Head;

			public TextView CName;

			public TextView Ename;

			public TextView Phone;

			public ImageView Mark;
		}
	}

	private class ProcessHandler extends YtHandler {
		private final WeakReference<ParentActivity> weakActivity;

		public ProcessHandler(ParentActivity activity) {
			this.weakActivity = new WeakReference<ParentActivity>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(ParentActivity.class, "[获取授权家长-handler]:msg.what:",
					msg.what);
			ParentActivity activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				switch (msg.what) {
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:
					dismissLoading();
					// activity.loadingoverlay.setVisibility(View.INVISIBLE);
					// Toast.makeText(activity.getApplicationContext(),
					// "获取授权家长成功！", Toast.LENGTH_SHORT).show();
					userInfoBeans = (List<UserInfoBean>) msg.obj;
					activity.pdata.clear();
					if (userInfoBeans != null && userInfoBeans.size() > 0) {
						for (UserInfoBean user : userInfoBeans) {
							ParentInfo painfo = new ParentInfo();
							painfo.setCname(user.getUsr_name());
							painfo.setEname(user.getUsr_login_name());
							painfo.setPhonenum(user.getUsr_phone());
							painfo.setRes_id(user.getUsr_photo());
							if (YtApplication.getInstance().getUid()
									.equals(user.getUsr_id())) {
								painfo.is_self = true;
								
							} else {
								painfo.is_self = false;
								
							}
							activity.pdata.add(painfo);
						}
					}

					activity.adapter.notifyDataSetChanged();
					break;

				case ThreadCommStateCode.TOKEN_INVALID:
					dismissLoading();
					Toast.makeText(activity.getApplicationContext(),
							"Token失效，请重新登录！", Toast.LENGTH_SHORT).show();
					break;

				case ThreadCommStateCode.COMMON_FAILED:

					dismissLoading();
					Toast.makeText(activity.getApplicationContext(),
							"获取授权家长失败！请重试！", Toast.LENGTH_SHORT).show();
					break;

				default:
					break;
				}
			}
		}
	}

	private void startDeleteTask(String usr_id) {

		if (biz != null)
			biz.cancel();
		showLoading("正在执行,请稍候...", LOADING_CODE_2);
		// loadingoverlay.setVisibility(View.VISIBLE);
		// loadingoverlay.setLoadingTip("正在执行,请稍候...");
		DeleteCertigierUserBiz delbiz = new DeleteCertigierUserBiz(
				getApplicationContext(), new ProcessDeleteHandler(
						ParentActivity.this), usr_id, studentInfo.getCld_id());

		delbiz.execute();

	}

	private class ProcessDeleteHandler extends YtHandler {
		private final WeakReference<ParentActivity> weakActivity;

		public ProcessDeleteHandler(ParentActivity activity) {
			this.weakActivity = new WeakReference<ParentActivity>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(ParentActivity.class, "[删除授权家长-handler]:msg.what:",
					msg.what);
			ParentActivity activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:

					dismissLoading();
					// 重新加载授权家长信息
					loadParentData();
					// Toast.makeText(activity.getApplicationContext(),
					// "删除授权家长成功！", Toast.LENGTH_SHORT).show();

					break;

				case ThreadCommStateCode.TOKEN_INVALID:
					dismissLoading();
					Toast.makeText(activity.getApplicationContext(),
							"Token失效，请重新登录！", Toast.LENGTH_SHORT).show();
					break;

				case ThreadCommStateCode.COMMON_FAILED:

					dismissLoading();
					Toast.makeText(activity.getApplicationContext(),
							"删除授权家长失败！请重试！", Toast.LENGTH_SHORT).show();
					break;

				default:
					break;
				}
			}
		}
	}

}
