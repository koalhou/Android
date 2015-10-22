package com.yutong.axxc.parents.view.login;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
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
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.view.adapter.BindStudentAdapter;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.home.MainActivity;
import com.yutong.axxc.parents.view.home.MainNotBindActivity;

/**
 * @author zhangyongn
 */
public class RegisterActivity_Succ extends YtAbstractActivity implements OnClickListener
{
    private YtAsyncTask biz;

    private List<StudentInfoBean> students;

    private View header;

    private View footer;

    // 加载弹出提示相关视图
    private LoadingOverlay loadingoverlay;

    private UserInfoBean userInfoBean;

    private Button btn_title_left;

    private TextView tv_top_title;

    private Button btn_title_right;

    private TextView succtitle;

    private TextView succtip;

    private TextView loginTV;

    private Button btn_fillprofile;

    private ListView list_view;

    private BindStudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_succ);
        initUserInfo();
        initViews();
        initListener();

        if ("1".equals(userInfoBean.getPhone_bind()))
        {
            initStudents();
        }
    }

    private void initListener()
    {

        loginTV.setOnClickListener(this);
        btn_fillprofile.setOnClickListener(this);

        loadingoverlay.addOnCancelListener(new LoadingOverlay.OnCancelListener()
        {

            @Override
            public void onCancel()
            {
                if (biz != null)
                {
                    biz.cancel();
                    biz = null;
                }
                loadingoverlay.setVisibility(View.INVISIBLE);

            }
        });
    }

    /**
     * 初始化用户信息
     */
    private void initUserInfo()
    {
        Intent intent = this.getIntent();
        userInfoBean = (UserInfoBean) intent.getSerializableExtra(ActivityCommConstant.USER_INFO);

    }

    private void initViews()
    {
        footer = LayoutInflater.from(this).inflate(R.layout.register_succ_list_footer, null);
        header = LayoutInflater.from(this).inflate(R.layout.register_succ_list_header, null);
        loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);
        list_view = (ListView) findViewById(R.id.list_view);
        btn_title_left = (Button) findViewById(R.id.btn_title_left);
        tv_top_title = (TextView) findViewById(R.id.tv_top_title);
        btn_title_right = (Button) findViewById(R.id.btn_title_right);

        btn_fillprofile = (Button) footer.findViewById(R.id.btn_fillprofile);
        loginTV = (TextView) footer.findViewById(R.id.loginTV);
        succtip = (TextView) header.findViewById(R.id.succtip);
        succtitle = (TextView) header.findViewById(R.id.succtitle);

        btn_title_right.setText(R.string.next);
        btn_title_right.setVisibility(View.INVISIBLE);
        tv_top_title.setText(R.string.registerTitle);
        btn_title_left.setVisibility(View.INVISIBLE);

        adapter = new BindStudentAdapter(this);

        list_view.addHeaderView(header);
        list_view.addFooterView(footer);
        list_view.setAdapter(adapter);
        list_view.setDivider(null);

        if (userInfoBean != null)
        {
            succtitle.setText(userInfoBean.getFullName() + ",注册成功");
            String tip = "欢迎您成为安芯用户，您的安芯号是%s，您也可以使用该号码登录。";
            succtip.setText(String.format(tip, userInfoBean.getUsr_no()));
        }
    }

    private void initStudents()
    {
        loadingoverlay.setVisibility(View.VISIBLE);
        loadingoverlay.setLoadingTip("正在加载学生信息,请稍候...");
        startLoadStudentTask();
    }

    private void startLoadStudentTask()
    {
        if (biz != null)
            biz.cancel();
        biz = new GetStudentInfoBiz(getApplicationContext(), new StudentHandler(RegisterActivity_Succ.this),
                ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE);

        biz.execute();

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

        case R.id.loginTV:

            Intent intent1;
            if ("1".equals(YtApplication.getInstance().getUserInfoCache().getPhone_bind()))
            {
                intent1 = new Intent(getApplication(), MainActivity.class);

            }
            else
            {
                intent1 = new Intent(getApplication(), MainNotBindActivity.class);
            }

            startActivity(intent1);
            overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
            finish();
            this.finish();
            break;

        case R.id.btn_fillprofile:
            Intent intent2 = new Intent(getApplication(), RegisterActivity_FillProfile.class);
            startActivity(intent2);
            overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
            finish();
            this.finish();
            break;
        default:
            break;
        }
    }

    private static class StudentHandler extends YtHandler
    {
        private final WeakReference<RegisterActivity_Succ> activity;

        public StudentHandler(RegisterActivity_Succ activity)
        {
            this.activity = new WeakReference<RegisterActivity_Succ>(activity);
        }

        /**
         * (non-Javadoc)
         * 
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg)
        {

            RegisterActivity_Succ ac = activity.get();
            if (ac != null)
            {
                ac.loadingoverlay.setVisibility(View.INVISIBLE);
                super.handleMessage(msg, ac);
                switch (msg.what)
                {
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

    public void buildStudentsUI()
    {
        View v = null;
        if (students == null)
            return;
        adapter.SetDatas(students);
        adapter.notifyDataSetChanged();

    }
}
