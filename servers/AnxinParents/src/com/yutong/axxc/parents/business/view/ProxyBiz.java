package com.yutong.axxc.parents.business.view;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.behaviour.MobileEnvUploadBiz;
import com.yutong.axxc.parents.dao.common.DatabaseHelper;

public class ProxyBiz
{
    
    public static void startCleanData()
    {
        DatabaseHelper.clearLogs();
        DatabaseHelper.clearResCache();
        DatabaseHelper.clearSharedPreferences();
    }

}
