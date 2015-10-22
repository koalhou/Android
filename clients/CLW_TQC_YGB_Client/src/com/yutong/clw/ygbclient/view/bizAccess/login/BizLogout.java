/**
 * @公司名称：YUTONG
 * @作者：zhangyongn
 * @版本号：1.0
 * @生成日期：2013-10-30 下午3:59:00
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bizAccess.login;

import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.login.LoginBiz;
import com.yutong.clw.ygbclient.business.login.LogoutBiz;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * @author zhangyongn 2013-10-30 下午3:59:00
 */
public class BizLogout extends BizBase
{
    public BizLogout(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public BizLogout(Context context, int threadcount)
    {
        super(context, threadcount);
        // TODO Auto-generated constructor stub
    }

    public void logout(BizResultProcess<String> process)
    {
        final LogoutBiz biz = new LogoutBiz(YtApplication.getInstance());

        Callable<String> submitcallable = new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                biz.logout();
                return "";

            }
        };
        BizNetWork(submitcallable, process);
    }

}
