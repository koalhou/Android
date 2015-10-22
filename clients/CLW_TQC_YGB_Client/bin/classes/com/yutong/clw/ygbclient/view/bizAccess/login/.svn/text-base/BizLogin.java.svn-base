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
public class BizLogin extends BizBase
{
    public BizLogin(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public BizLogin(Context context, int threadcount)
    {
        super(context, threadcount);
        // TODO Auto-generated constructor stub
    }

    public void login(String account, String passwordMD5, BizResultProcess<UserInfo> process)
    {
        final LoginBiz biz = new LoginBiz(YtApplication.getInstance(), account, passwordMD5);

        Callable<UserInfo> submitcallable = new Callable<UserInfo>()
        {
            @Override
            public UserInfo call() throws Exception
            {
                return biz.login();

                // test
                // Thread.sleep(1000);
                // UserInfo user = new UserInfo();
                // user.AccessToken = "test";
                // user.BindPhone = false;
                // user.Name = "testuser";
                // user.Phone="13555555555";
                // return user;

            }
        };
        BizNetWork(submitcallable, process);

    }

    

}
