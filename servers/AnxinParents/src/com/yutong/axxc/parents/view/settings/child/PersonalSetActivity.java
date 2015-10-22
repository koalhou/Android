package com.yutong.axxc.parents.view.settings.child;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.cache.AppCacheData;
import com.yutong.axxc.parents.business.student.SetStudentCustomInfoBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.beans.StudentCustomInfoBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ChildCustomConfigs;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.ChildCustomConfigs.ChildCustomTheme;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtHandler;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 * 
 */
public class PersonalSetActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	// 加载弹出提示相关视图

	private ListView lv_colors;
	private ColorsAdapter coloradapter;
	private SetStudentCustomInfoBiz studentCustomBiz;
	private StudentCustomInfoBean customInfo;
	private StudentInfoBean studentInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_child_personaltheme);
		initViews();
		initListener();
		loadStudentInfo();
	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		btn_title_right.setEnabled(false);
	}

	private void loadStudentInfo() {

		Intent intent = getIntent();
		if (intent == null)
			return;
		Bundle bundle = intent.getExtras();
		if (bundle == null)
			return;
		studentInfo = (StudentInfoBean) bundle
				.getSerializable(ActivityCommConstant.STUDENT_INFO);
		if (studentInfo == null)
			return;

		loadColorSetting();
		btn_title_right.setEnabled(true);
	}

	private void loadColorSetting() {

		ChildCustomTheme theme = ChildCustomConfigs.getInstance()
				.getChildCustomThemeByKey(studentInfo.getCld_color());
		if (theme != null)
			coloradapter.setSelectedColor(theme.getName());

	}

	private void initViews() {

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setText(R.string.confirm);
		// tv_top_title.setText(R.string.registerTitle);

		lv_colors = (ListView) findViewById(R.id.lv_scp_colors);
		coloradapter = new ColorsAdapter(this);
		lv_colors.setAdapter(coloradapter);

	}

	public static final String COLORSET_CODE = "COLORSET_CODE";

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:
			// TODO 提交个性信息
			showLoading("保存个性色彩配置至云端...", 1);
			// getupdateBiz().execute();

			// 设置头像显示
			startTask(studentInfo.getCustonInfo());

			break;
		case R.id.btn_title_left:
			setResult(RESULT_CANCELED, getIntent());
			finish();
			break;
		default:
			break;
		}

	}

	class ColorEntity {
		public String Key;
		public String Name;
		public Drawable Draw;
	}

	class ColorsAdapter extends BaseAdapter {

		private int currentselectindex = -1;

		private List<ColorEntity> mdata = new ArrayList<PersonalSetActivity.ColorEntity>();

		private Context mcontext;

		public ColorsAdapter(Context context) {
			mcontext = context;
			prepareData();
		}

		@Override
		public int getCount() {
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

		/**
		 * 
		 * 获取当前选中色
		 * 
		 * @return
		 */
		public ColorEntity getCurrentColor() {
			if (currentselectindex < 0 || mdata == null
					|| currentselectindex >= mdata.size())
				return null;
			return mdata.get(currentselectindex);
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder holder = null;
			if (arg1 == null) {
				holder = new ViewHolder();
				arg1 = View.inflate(mcontext,
						R.layout.setting_child_personaltheme_citem, null);
				holder.Color = (ImageView) arg1
						.findViewById(R.id.iv_scpci_color);
				holder.Check = (ImageView) arg1
						.findViewById(R.id.iv_scpci_check);
				holder.Name = (TextView) arg1
						.findViewById(R.id.tv_scpci_colorname);
				holder.Background = (RelativeLayout) arg1
						.findViewById(R.id.rl_scpi_bg);
				if (arg0 == 0) {
					holder.Background
							.setBackgroundResource(R.drawable.settingitem_bg_topround);
				} else if (arg0 == mdata.size() - 1) {
					holder.Background
							.setBackgroundResource(R.drawable.settingitem_bg_bottomround);
				} else {
					holder.Background
							.setBackgroundResource(R.drawable.settingitem_bg_allround);
				}

				holder.Background.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						int index = Integer.parseInt(arg0.getTag(
								R.layout.setting_child_personaltheme_citem)
								.toString());
						ColorsAdapter.this.currentselectindex = index;
						ColorsAdapter.this.notifyDataSetChanged();
						selectionchanged();
					}

					private void selectionchanged() {
						// TODO 设置个性颜色
						studentInfo.setCld_color(getCurrentColor().Key);

					}
				});
				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}
			holder.Background.setTag(
					R.layout.setting_child_personaltheme_citem, arg0);

			ColorEntity entity = mdata.get(arg0);
			holder.Name.setText(entity.Name);
			holder.Color.setImageDrawable(entity.Draw);

			if (currentselectindex == arg0) {
				holder.Check.setImageDrawable(getResources().getDrawable(
						R.drawable.listselected));
			} else {
				holder.Check.setImageDrawable(null);
			}

			return arg1;
		}

		public void setSelectedIndex(int index) {
			if (index < 0) {
				currentselectindex = 0;
			} else if (index > mdata.size() - 1) {
				currentselectindex = mdata.size() - 1;
			} else {
				currentselectindex = index;
			}
			notifyDataSetChanged();
		}

		public int setSelectedColor(String name) {
			for (int i = 0; i < mdata.size(); i++) {
				if (mdata.get(i).Name.equals(name)) {
					currentselectindex = i;
					notifyDataSetChanged();
					return currentselectindex;
				}
			}
			return currentselectindex;
		}

		private void prepareData() {
			mdata.clear();
			ColorEntity enti = new ColorEntity();
			enti.Key = "0";
			enti.Name = "宁静绿";
			enti.Draw = getResources().getDrawable(R.drawable.color_green);
			mdata.add(enti);
			enti = new ColorEntity();
			enti.Key = "1";
			enti.Name = "可爱红";
			enti.Draw = getResources().getDrawable(R.drawable.color_pink);
			mdata.add(enti);
			enti = new ColorEntity();
			enti.Key = "2";
			enti.Name = "活泼红";
			enti.Draw = getResources().getDrawable(R.drawable.color_red);
			mdata.add(enti);
			enti = new ColorEntity();
			enti.Key = "3";
			enti.Name = "优雅紫";
			enti.Draw = getResources().getDrawable(R.drawable.color_purple);
			mdata.add(enti);
			enti = new ColorEntity();
			enti.Key = "4";
			enti.Name = "透彻蓝";
			enti.Draw = getResources().getDrawable(R.drawable.color_blue);
			mdata.add(enti);
			currentselectindex = 0;
		}

		class ViewHolder {
			public RelativeLayout Background;
			public ImageView Color;
			public ImageView Check;
			public TextView Name;
			public int flag;
		}
	}

	private void startTask(StudentCustomInfoBean studentCustomInfoBean) {
		if (studentCustomBiz != null)
			studentCustomBiz.cancel();
		studentCustomBiz = new SetStudentCustomInfoBiz(getApplicationContext(),
				new ProcessHandler(PersonalSetActivity.this),
				studentInfo.getCld_id(), studentCustomInfoBean);

		studentCustomBiz.execute();

	}

	private class ProcessHandler extends YtHandler {
		private final WeakReference<PersonalSetActivity> weakActivity;

		public ProcessHandler(PersonalSetActivity activity) {
			this.weakActivity = new WeakReference<PersonalSetActivity>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(PersonalSetActivity.class, "[修改-handler]:msg.what:",
					msg.what);
			PersonalSetActivity activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					dismissLoading();
					ToastMessage("修改成功！");
					AppCacheData.putStudentInfo(YtApplication.getInstance()
							.getUid(), activity.studentInfo);

					// Intent intent = getIntent();
					// Bundle bundle = intent.getExtras();
					// if (bundle == null) {
					// bundle = new Bundle();
					// }
					// bundle.putString(COLORSET_CODE,
					// coloradapter.getCurrentColor().Key);
					// intent.putExtras(bundle);
					// setResult(RESULT_OK, intent);
					finish();

					// activity.loadColorSetting();
					break;

				case ThreadCommStateCode.TOKEN_INVALID:
					dismissLoading();
					ToastMessage("Token失效，请重新登录！");
					break;

				case ThreadCommStateCode.COMMON_FAILED:

					dismissLoading();
					ToastMessage((String) msg.obj);

					break;

				default:
					dismissLoading();
					ToastMessage((String) msg.obj);
					break;
				}
			}
		}
	}

}
