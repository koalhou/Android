package com.yutong.clw.ygbclient.view.pages.setting.refreshSetting;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.constant.GlobleConstants;
import com.yutong.clw.ygbclient.common.prefs.SettingPrefsUtils;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.widget.CommonCheckBox;

/**
 * 自动刷新设置界面
 * 
 * @author zhouzc
 */
@SuppressLint("ResourceAsColor")
public class RefreshSettingActivity extends RemindAccessActivity implements OnClickListener
{
    /**
     * 刷新开关
     */
    private CommonCheckBox cb_map_refresh;

    // 5秒
    private RelativeLayout fiveScondsRL;

    // 10秒
    private RelativeLayout tenScondsRL;

    // 15秒
    private RelativeLayout fifteenScondsRL;

    private ImageView fiveScondsIV;

    private ImageView tenScondsIV;

    private ImageView fifteenScondsIV;

    private List<ImageView> icons;

    private LinearLayout timeLL;

    private long mapRefreshInterval_5 = 5l;

    private long mapRefreshInterval_10 = 10l;

    private long mapRefreshInterval_15 = 15l;

    private TextView tv_five_second;

    private TextView tv_ten_second;

    private TextView tv_fifteen_second;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_refreshset);
        initViews();
        initData();
        initListener();
    }

    /**
     * 设置监听
     * 
     * @author lizyi 2013-11-9 下午2:58:09
     */
    @SuppressLint("ResourceAsColor")
    private void initListener()
    {
        fiveScondsRL.setOnClickListener(this);
        tenScondsRL.setOnClickListener(this);
        fifteenScondsRL.setOnClickListener(this);
        // 刷新开关
        cb_map_refresh.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    tv_five_second.setTextColor(Color.BLACK);
                    tv_ten_second.setTextColor(Color.BLACK);
                    tv_fifteen_second.setTextColor(Color.BLACK);
                    // 开启刷新
                    timeLL.setBackgroundResource(R.drawable.bg_block);
                    ToastMessage("自动刷新开启");
                }
                else
                {
                    tv_five_second.setTextColor(R.color.flush_time_bg);
                    tv_ten_second.setTextColor(R.color.flush_time_bg);
                    tv_fifteen_second.setTextColor(R.color.flush_time_bg);
                    // 关闭刷新
                    timeLL.setBackgroundResource(R.drawable.bg_close_flush);
                    ToastMessage("自动刷新关闭");

                }
                // 保存设置

                PrefDataUtil.setAutoMapRefresh(getApplicationContext(), isChecked);

                fiveScondsRL.setEnabled(isChecked);
                tenScondsRL.setEnabled(isChecked);
                if (!isChecked)
                {
                    tenScondsRL.setBackgroundResource(R.drawable.bg_graybtn);
                }
                else
                {
                    tenScondsRL.setBackgroundResource(R.drawable.bg_settingitem);
                }

                fifteenScondsRL.setEnabled(isChecked);
            }
        });
        initHistorySetting();
    }

    // 读取历史配置信息
    private void initHistorySetting()
    {

        boolean isAutoFlush = PrefDataUtil.getAutoMapRefresh(getApplicationContext());
        cb_map_refresh.setChecked(isAutoFlush);
        cb_map_refresh.invalidate();
        if (isAutoFlush)
        {
            tv_five_second.setTextColor(Color.BLACK);
            tv_ten_second.setTextColor(Color.BLACK);
            tv_fifteen_second.setTextColor(Color.BLACK);
            // 开启刷新
            timeLL.setBackgroundResource(R.drawable.bg_block);
        }
        else
        {
            tv_five_second.setTextColor(R.color.flush_time_bg);
            tv_ten_second.setTextColor(R.color.flush_time_bg);
            tv_fifteen_second.setTextColor(R.color.flush_time_bg);
            // 关闭刷新
            timeLL.setBackgroundResource(R.drawable.bg_close_flush);

        }
        fiveScondsRL.setEnabled(isAutoFlush);
        tenScondsRL.setEnabled(isAutoFlush);
        if (!isAutoFlush)
        {
            tenScondsRL.setBackgroundResource(R.drawable.bg_graybtn);
        }
        else
        {
            tenScondsRL.setBackgroundResource(R.drawable.bg_settingitem);
        }

        fifteenScondsRL.setEnabled(isAutoFlush);
    }

    /**
     * 加载数据
     * 
     * @author lizyi 2013-11-9 下午2:58:23
     */
    private void initData()
    {
        initIcons();
        // 读取历史配置信息
        // String flushTime =
        // SettingPrefsUtils.getString(getApplicationContext(),
        // GlobleConstants.SHARED_MAP_FLUSH_TIME, 5 + "");

        long flushTime = PrefDataUtil.getMapRefreshInterval(getApplicationContext());

        ImageView lastSelectedIcon = null;

        if (mapRefreshInterval_5 == flushTime)
        {
            lastSelectedIcon = fiveScondsIV;
        }
        else if (mapRefreshInterval_10 == flushTime)
        {
            lastSelectedIcon = tenScondsIV;
        }
        else
        {
            lastSelectedIcon = fifteenScondsIV;
        }
        manageIcons(lastSelectedIcon);
    }

    /**
 *
 */
    private void initIcons()
    {
        icons = new ArrayList<ImageView>();
        icons.add(fiveScondsIV);
        icons.add(tenScondsIV);
        icons.add(fifteenScondsIV);

    }

    public void initViews()
    {
        cb_map_refresh = (CommonCheckBox) findViewById(R.id.cb_map_refresh);
        timeLL = (LinearLayout) findViewById(R.id.timeLL);
        fiveScondsRL = (RelativeLayout) findViewById(R.id.fiveScondsRL);
        tv_five_second = (TextView) findViewById(R.id.tv_five_second);
        tv_ten_second = (TextView) findViewById(R.id.tv_ten_second);
        tv_fifteen_second = (TextView) findViewById(R.id.tv_fifteen_second);
        tenScondsRL = (RelativeLayout) findViewById(R.id.tenScondsRL);
        fifteenScondsRL = (RelativeLayout) findViewById(R.id.fifteenScondsRL);

        fiveScondsIV = (ImageView) findViewById(R.id.fiveScondsIV);
        tenScondsIV = (ImageView) findViewById(R.id.tenScondsIV);
        fifteenScondsIV = (ImageView) findViewById(R.id.fifteenScondsIV);
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
        tv_large.setText("地图自动刷新设置");
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
        case R.id.fiveScondsRL:
            if (!cb_map_refresh.isChecked())
            {
                return;
            }
            manageIcons(fiveScondsIV);
            saveRreshTime(mapRefreshInterval_5);
            finish();
            break;
        case R.id.tenScondsRL:
            if (!cb_map_refresh.isChecked())
            {
                return;
            }
            manageIcons(tenScondsIV);
            saveRreshTime(mapRefreshInterval_10);
            finish();
            break;
        case R.id.fifteenScondsRL:
            if (!cb_map_refresh.isChecked())
            {
                return;
            }
            manageIcons(fifteenScondsIV);
            saveRreshTime(mapRefreshInterval_15);
            finish();
            break;
        default:
            break;
        }

    }

    /**
     * 存储刷新时间
     * 
     * @author lizyi 2013-11-9 下午4:33:23
     * @param i
     */
    private void saveRreshTime(long refreshTime)
    {
        PrefDataUtil.setMapRefreshInterval(getApplicationContext(), refreshTime);

        ToastMessage("保存成功");
    }

    /**
     * 管理图标的显示与隐藏
     * 
     * @author lizyi 2013-11-9 下午4:16:39
     * @param v
     */
    private void manageIcons(ImageView icon)
    {
        if (icon == null)
        {
            return;
        }
        for (int i = 0; i < icons.size(); i++)
        {
            if (icon.equals(icons.get(i)))
            {
                icon.setVisibility(View.VISIBLE);
            }
            else
            {
                icons.get(i).setVisibility(View.INVISIBLE);
            }

        }

    }

}
