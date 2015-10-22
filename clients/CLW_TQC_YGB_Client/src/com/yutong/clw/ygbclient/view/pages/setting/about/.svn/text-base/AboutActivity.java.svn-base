package com.yutong.clw.ygbclient.view.pages.setting.about;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.VersionInfo;
import com.yutong.clw.ygbclient.common.utils.NetworkCheckUitl;
import com.yutong.clw.ygbclient.common.utils.SysInfoGetUtil;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.common.VersionUpdateManager.VersionCheckStatus;
import com.yutong.clw.ygbclient.view.common.VersionUpdateManager.VersionUpdateListener;
import com.yutong.clw.ygbclient.view.common.VersionUpdateManager.VersionUpdateStatus;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.widget.CustomAlertDialog;

/**
 * 安芯关于界面
 * 
 * @author zhouzc
 */
public class AboutActivity extends RemindAccessActivity implements OnClickListener
{
    RelativeLayout rl_version, rl_serverlist, rl_qrcode;

    ImageView iv_new;

    TextView tv_currentversion, tv_newversion;

    private VersionUpdateListener vlistener;

    boolean hasNewVersion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_about);
        initViews();
        initListener();
        
		/*用户行为统计-关于安芯页面加载*/
        new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_ABOUT_LOAD).reportBehavior(null);
    }

    @Override
    protected void onResume()
    {
        VersionInfo v = YtApplication.getInstance().getVersionUpdateManager().getLatestVersioninfo();
        if ( v!= null && v.isNeed_update())
        {
            iv_new.setVisibility(View.VISIBLE);
            tv_newversion.setText(v.getVersion_name() + "");
            hasNewVersion = true;
        }
        else
        {
            iv_new.setVisibility(View.GONE);
            tv_newversion.setText("已经是最新版本");
        }
        if (vlistener != null)
            YtApplication.getInstance().getVersionUpdateManager().addVersionUpdateListener(vlistener);
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        if (vlistener != null)
            YtApplication.getInstance().getVersionUpdateManager().removeVersionUpdateListener(vlistener);
        super.onPause();
    }

    public void initViews()
    {
        rl_version = (RelativeLayout) findViewById(R.id.rl_sa_version);
        rl_serverlist = (RelativeLayout) findViewById(R.id.rl_sa_serverlist);
        rl_qrcode = (RelativeLayout) findViewById(R.id.rl_sa_qrcode);

        tv_currentversion = (TextView) findViewById(R.id.tv_sa_currentversion);
        tv_newversion = (TextView) findViewById(R.id.tv_sa_newversion);

        iv_new = (ImageView) findViewById(R.id.iv_sa_isnew);
        iv_new.setVisibility(View.INVISIBLE);

        tv_newversion.setText("已经是最新版本");
        tv_currentversion.setText(SysInfoGetUtil.getClientInfoBean(this).getApp_version());
    }

    private void initListener()
    {
        vlistener = new VersionUpdateListener()
        {
            @Override
            public void onUpdateStatusChanged(final VersionUpdateStatus status, final int percent)
            {
                runOnUiThread(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        switch (status)
                        {
                        case Success:
                            break;
                        case Canceled:
                            startVersionChkTask();
                            Toast.makeText(AboutActivity.this, "已停止下载", Toast.LENGTH_SHORT).show();
                            break;
                        case Failed:
                            tv_newversion.setText("下载失败...");
                            break;
                        case Updating:
                            tv_newversion.setText("正在下载中(" + percent + ")%");
                            break;
                        default:
                            break;
                        }

                    }
                });

            }

            @Override
            public void getCheckStatusChanged(final VersionCheckStatus status, final VersionInfo versioninfo)
            {
                runOnUiThread(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        switch (status)
                        {
                        case Failed:
                            tv_newversion.setText("获取最新版本信息失败...");
                            hasNewVersion = false;
                            break;
                        case Success_ForceUpdate:
                        case Success_NeedUpdate:
                            tv_newversion.setText(versioninfo.getVersion_name());
                            iv_new.setVisibility(View.VISIBLE);
                            hasNewVersion = true;
                            break;
                        case Success_NoNeedUpdate:
                            tv_newversion.setText("已经是最新版本");
                            iv_new.setVisibility(View.INVISIBLE);
                            break;
                        case Checking:
                            tv_newversion.setText("正在版本检测...");
                            iv_new.setVisibility(View.INVISIBLE);
                            break;
                        default:
                            break;
                        }

                    }
                });

            }
        };
        rl_serverlist.setOnClickListener(this);
        rl_version.setOnClickListener(this);
        rl_qrcode.setOnClickListener(this);
    }

    @Override
    protected boolean hasTitle()
    {
        return true;
    }

    @Override
    protected void loadTitleByPageSetting(Button btn_left, Button btn_right, RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
            TextView tv_small)
    {
        btn_left.setBackgroundResource(R.drawable.bg_prevbtn);
        btn_right.setVisibility(View.INVISIBLE);
        tv_large.setText("关于安芯");
        tv_small.setVisibility(View.GONE);
        iv_tri.setVisibility(View.GONE);

        btn_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.btn_it_left:
            finish();
            break;
        case R.id.rl_sa_version:
            if (hasNewVersion)
            {
                showConfirmDialog();
            }
            else
            {
                startVersionChkTask();
            }
            
            /*用户行为统计- 检查新版本*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_ABOUT_CHECKNEW).reportBehavior(null);
            
            break;
        case R.id.rl_sa_serverlist:
            ActivityUtils.changeActivity(AboutActivity.this, ServiceTermsActivity.class, null);
            
            /*用户行为统计- 服务条款*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_ABOUT_FWTK).reportBehavior(null);
            break;
        case R.id.rl_sa_qrcode:
            ActivityUtils.changeActivity(AboutActivity.this, QRCodeActivity.class, null);
            
            /*用户行为统计-查看二维码*/
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_ABOUT_CKEWM).reportBehavior(null);
            break;

        default:
            break;
        }

    }

    private void showConfirmDialog()
    {
        if (YtApplication.getInstance().getVersionUpdateManager().isVersionUpdating())
        {
            CustomAlertDialog deletedia = new CustomAlertDialog(AboutActivity.this)
            {
                @Override
                public void onUserOK()
                {
                    Logger.i(this.getClass(), "停止下载新版本...");
                    stopVersionUpdate();
                }

                @Override
                public void onUserCancel()
                {
                }
            };
            deletedia.setTitle("是否停止下载");
            deletedia.setOkStr("停止下载");
            deletedia.setCancelStr("继续下载");

            deletedia.show();
        }
        else
        {
            boolean _3gonline = NetworkCheckUitl.is3GOnline(this);
            String title = "请确认是否下载？";
            if (_3gonline)
            {
                title = "当前不是wifi环境，继续下载将会耗费流量，请确认是否下载？";
            }

            CustomAlertDialog deletedia = new CustomAlertDialog(AboutActivity.this)
            {
                @Override
                public void onUserOK()
                {
                    Logger.i(this.getClass(), "开始下载新版本...");
                    startVersionUpdateTask();
                }

                @Override
                public void onUserCancel()
                {
                }
            };
            deletedia.setTitle(title);
            deletedia.setOkStr("开始下载");
            deletedia.setCancelStr("取消");

            deletedia.show();
        }

    }

    protected void stopVersionUpdate()
    {
        YtApplication.getInstance().getVersionUpdateManager().CancelUpdating();
    }

    private void startVersionChkTask()
    {

        if (!YtApplication.getInstance().getVersionUpdateManager().isVersionChecking())
        {
            YtApplication.getInstance().getVersionUpdateManager().checkVersion();
        }
        tv_newversion.setText("正在检测新版本...");

    }

    private void startVersionUpdateTask()
    {
        if (YtApplication.getInstance().getVersionUpdateManager().isVersionUpdating())
            return;
        YtApplication.getInstance().getVersionUpdateManager()
                .UpdateNewVersion(YtApplication.getInstance().getVersionUpdateManager().getLatestVersioninfo());
        tv_newversion.setText("正在下载中(0%)");
    }
}
