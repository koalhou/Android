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
import com.yutong.clw.ygbclient.business.login.ForgotPasswordBiz;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * @author zhangyongn 2013-10-30 下午3:59:00
 * 修改密码
 */
public class BizChangePwd extends BizBase
{
    public BizChangePwd(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public BizChangePwd(Context context, int threadcount)
    {
        super(context, threadcount);
        // TODO Auto-generated constructor stub
    }

    public void changePwd(String emp_code, String passwordMD5, BizResultProcess<Boolean> process)
    {
        final ForgotPasswordBiz biz = new ForgotPasswordBiz(YtApplication.getInstance(), emp_code, passwordMD5);

        Callable<Boolean> submitcallable = new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception
            {
                 return biz.modifyPassword();

                // test
                 //return true;

            }
        };
        BizNetWork(submitcallable, process);

    }

}
