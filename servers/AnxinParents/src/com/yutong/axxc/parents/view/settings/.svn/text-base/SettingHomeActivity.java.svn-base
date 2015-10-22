package com.yutong.axxc.parents.view.settings;

import java.lang.ref.WeakReference;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.cache.AppCacheData;
import com.yutong.axxc.parents.business.common.CommonPushMsgUtil;
import com.yutong.axxc.parents.business.view.ReadStudentInfoBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.connect.push.CommonPushMsg;
import com.yutong.axxc.parents.connect.push.PushMessageListenHost.OnMessageReceiveListener;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ChildCustomConfigs;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.ResourcesUtils;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.login.GuideActivity;
import com.yutong.axxc.parents.view.settings.account.SettingAccountHomeActivity;
import com.yutong.axxc.parents.view.settings.child.SettingChildHomeActivity;
import com.yutong.axxc.parents.view.settings.news.NewsHomeActivity;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 */
public class SettingHomeActivity extends YtAbstractActivity implements OnClickListener
{
    private YtAsyncTask biz;

    private Button btn_title_left;

    private TextView tv_top_title;

    private Button btn_title_right;

    private LinearLayout studentsLL;

    private List<StudentInfoBean> students;

    // 加载弹出提示相关视图
    private LoadingOverlay loadingoverlay;

    private RelativeLayout accounthomeRL;

    private RelativeLayout newsRL;

    private RelativeLayout guidRL;

    private RelativeLayout feedbackRL;

    private RelativeLayout aboutRL;

    private TextView tv_newcount;

    private LinearLayout ll_newscount;

    private ImageView accounthomeiconIV;

    public StudentInfoBean getStudent(String id)
    {
        if (students == null)
            return null;
        for (int i = 0; i < students.size(); i++)
        {
            if (id.equals(students.get(i).getCld_id()))
                return students.get(i);
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_home);
        initViews();
        initListener();
        
        //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0009);
        

    }
    
    

    private void loadData()
    {
        try
        {
            String photo = YtApplication.getInstance().getUserInfoCache().getUsr_photo();
            if (StringUtils.isNotBlank(photo))
            {
                ResourcesUtils.startGetImg(getApplicationContext(), accounthomeiconIV, photo);
            }
        }
        catch (Exception e)
        {

        }

    }

    private OnMessageReceiveListener msglistener = new OnMessageReceiveListener()
    {

        @Override
        public void OnReceivedReminds(CommonPushMsg message)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void OnReceivedNews(CommonPushMsg message)
        {
            int newscount = CommonPushMsgUtil.getNewsNotificationCount();
            tv_newcount.setText(newscount + "");
            ll_newscount.setVisibility(newscount == 0 ? View.INVISIBLE : View.VISIBLE);
        }

        @Override
        public void OnReceived(CommonPushMsg message)
        {
            // TODO Auto-generated method stub

        }
    };

    @Override
    protected void onResume()
    {
        // TODO 加载新闻数据
        YtApplication.getInstance().getPushHost().addOnMessageReceiveListener(msglistener);
        int newscount = CommonPushMsgUtil.getNewsNotificationCount();
        tv_newcount.setText(newscount + "");
        ll_newscount.setVisibility(newscount == 0 ? View.INVISIBLE : View.VISIBLE);
        initStudents();
        loadData();
        super.onResume();

    }

    @Override
    protected void onPause()
    {
        // TODO 移除新闻数据侦听
        YtApplication.getInstance().getPushHost().removeOnMessageReceiveListener(msglistener);
        super.onPause();
    }

    private void initStudents()
    {
        //showLoading("正在加载学生信息,请稍候...", 0);
        
//        students = AppCacheData.getStudentInfos(YtApplication.getInstance().getUid());
//        buildStudentsUI();
        
        startLoadStudentTask();
    }

    private void startLoadStudentTask()
    {
        if (biz != null)
            biz.cancel();
        biz = new ReadStudentInfoBiz(getApplicationContext(), new StudentHandler(SettingHomeActivity.this));

        biz.execute();

    }

    private void initListener()
    {
        btn_title_right.setOnClickListener(this);
        btn_title_left.setOnClickListener(this);

        accounthomeRL.setOnClickListener(this);

        newsRL.setOnClickListener(this);
        guidRL.setOnClickListener(this);
        feedbackRL.setOnClickListener(this);
        aboutRL.setOnClickListener(this);

        loadingoverlay.addOnCancelListener(new LoadingOverlay.OnCancelListener()
        {

            @Override
            public void onCancel()
            {

                loadingoverlay.setVisibility(View.INVISIBLE);

            }
        });

    }

    private void initViews()
    {
        studentsLL = (LinearLayout) findViewById(R.id.studentsLL);
        loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

        btn_title_left = (Button) findViewById(R.id.btn_title_left);
        tv_top_title = (TextView) findViewById(R.id.tv_top_title);
        btn_title_right = (Button) findViewById(R.id.btn_title_right);

        btn_title_right.setBackgroundResource(R.drawable.anxin_button);
        tv_top_title.setText("系统设置");

        accounthomeiconIV = (ImageView) findViewById(R.id.accounthomeiconIV);

        accounthomeRL = (RelativeLayout) findViewById(R.id.accounthomeRL);

        newsRL = (RelativeLayout) findViewById(R.id.newsRL);
        guidRL = (RelativeLayout) findViewById(R.id.guidRL);
        feedbackRL = (RelativeLayout) findViewById(R.id.feedbackRL);
        aboutRL = (RelativeLayout) findViewById(R.id.aboutRL);

        tv_newcount = (TextView) findViewById(R.id.tv_sh_newsnum);
        ll_newscount = (LinearLayout) findViewById(R.id.ll_sh_newcount);
        ll_newscount.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

    }

    @Override
    public void onClick(View v)
    {
        Intent intent;
        switch (v.getId())
        {
        case R.id.btn_title_right:
             intent = new Intent(getApplication(),
                    SettingAboutActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter_righttoleft,
                    R.anim.exit_righttoleft);
           
            break;
        case R.id.btn_title_left:
            finish();
            break;
        case R.id.accounthomeRL:
            intent = new Intent(getApplication(), SettingAccountHomeActivity.class);

            startActivity(intent);
            overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
            break;

        case R.id.newsRL:
        	
            intent = new Intent(getApplication(), NewsHomeActivity.class);

            startActivity(intent);
            overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
            break;

        case R.id.guidRL:
            intent = new Intent(getApplication(), GuideActivity.class);
            intent.putExtra(ActivityCommConstant.GUID_RETURN_TYPE, ActivityCommConstant.GUID_RETURN);
            startActivity(intent);
            overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
            break;

        case R.id.feedbackRL:
            intent = new Intent(getApplication(), SettingFeedBackActivity.class);

            startActivity(intent);
            overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
            break;
        case R.id.aboutRL:
            intent = new Intent(getApplication(), SettingAboutActivity.class);

            startActivity(intent);
            overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
            break;
        default:
            break;
        }

    }

    private static class StudentHandler extends YtHandler
    {
        private final WeakReference<SettingHomeActivity> activity;

        public StudentHandler(SettingHomeActivity activity)
        {
            this.activity = new WeakReference<SettingHomeActivity>(activity);
        }

        /**
         * (non-Javadoc)
         * 
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg)
        {

            SettingHomeActivity ac = activity.get();
          
           
            if (ac != null)
            {
            	 ac.dismissLoading();
                super.handleMessage(msg, ac);
                switch (msg.what)
                {
                case ThreadCommStateCode.COMMON_SUCCESS:

                    ac.loadingoverlay.setVisibility(View.INVISIBLE);

                    ac.students = (List<StudentInfoBean>) msg.obj;
                    ac.buildStudentsUI();
                    break;
                default:
                    ac.ToastMessage((String) msg.obj);
                    break;
                }
            }
        }
    }

    public void buildStudentsUI()
    {
        View v = null;
        if (students == null)
            return;
        studentsLL.removeAllViews();
        for (int i = 0; i < students.size(); i++)
        {
            StudentInfoBean item = students.get(i);
            v = this.getLayoutInflater().inflate(R.layout.setting_listitem_student, null);

            TextView studentindexTV = (TextView) v.findViewById(R.id.studentindexTV);
            studentindexTV.setText(item.getCld_id());

            TextView studentnameTV = (TextView) v.findViewById(R.id.studentnameTV);
            studentnameTV.setText(item.getCld_name());

            RelativeLayout studentRL = (RelativeLayout) v.findViewById(R.id.studentRL);
            ImageView studentheaderIV = (ImageView) v.findViewById(R.id.studentheaderIV);

            if (item.getCld_sex().equals("1"))
            {
                studentheaderIV.setImageResource(R.drawable.default_gril); 
            }
            else
            {
                studentheaderIV.setImageResource(R.drawable.default_boy);
            }

            // 设置个性背景颜色
            studentRL.setBackgroundResource(ChildCustomConfigs.getInstance().getChildCustomThemeByKey(item.getCld_color())
                    .getInfoBackgroundResId());
            // 设置个性头像
            Logger.w(getClass(), "1111111111111111111111111111111111111");
            if(StringUtils.isNotBlank(item.getCld_photo()))
            {
                ResourcesUtils.startGetImg(getApplicationContext(), studentheaderIV, item.getCld_photo());
            }
            Logger.w(getClass(), "2222222222222222222222222222222222222");
            studentRL.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    TextView studentindexTV = (TextView) v.findViewById(R.id.studentindexTV);
                    String id = studentindexTV.getText().toString();
                    Intent intent = new Intent(getApplication(), SettingChildHomeActivity.class);
                    Bundle bundle = new Bundle();
                    //StudentInfoBean b = getStudent(id);
                    
                    StudentInfoBean b = AppCacheData.getStudentInfo(YtApplication.getInstance().getUid(),id);
                    
                    bundle.putSerializable(ActivityCommConstant.STUDENT_INFO, b);

                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
                }
            });
            studentsLL.addView(v);

        }
        studentsLL.refreshDrawableState();
    }
}
