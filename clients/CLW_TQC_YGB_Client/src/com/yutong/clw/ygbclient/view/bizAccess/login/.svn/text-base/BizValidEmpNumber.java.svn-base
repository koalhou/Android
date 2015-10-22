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
import com.yutong.clw.ygbclient.business.login.VerifyAccountBiz;
import com.yutong.clw.ygbclient.common.beans.VerifyAccountInfo;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * @author zhangyongn 2013-10-30 下午3:59:00 找回密码：验证员工号
 */
public class BizValidEmpNumber extends BizBase
{
    public BizValidEmpNumber(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public BizValidEmpNumber(Context context, int threadcount)
    {
        super(context, threadcount);
        // TODO Auto-generated constructor stub
    }

    public void valid(String empnumber, BizResultProcess<VerifyAccountInfo> process)
    {

        final VerifyAccountBiz biz = new VerifyAccountBiz(YtApplication.getInstance(), empnumber);
        Callable<VerifyAccountInfo> submitcallable = new Callable<VerifyAccountInfo>()
        {
            @Override
            public VerifyAccountInfo call() throws Exception
            {
                 return biz.verifyAccount();

                // test
//                Thread.sleep(1000);
//                VerifyAccountInfo info1 = new VerifyAccountInfo();
//                info1.emp_code = "90011111";
//                info1.phonebind = false;
//
//                return info1;

            }
        };
        BizNetWork(submitcallable, process);

    }

}
