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
import com.yutong.clw.ygbclient.business.login.BindPhoneBiz;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * @author zhangyongn 2013-10-30 下午3:59:00
 */
public class BizBindPhone extends BizBase
{
    public BizBindPhone(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public BizBindPhone(Context context, int threadcount)
    {
        super(context, threadcount);
        // TODO Auto-generated constructor stub
    }

    public void bind(String phone, BizResultProcess<Boolean> process)
    {
        final BindPhoneBiz biz=new BindPhoneBiz(YtApplication.getInstance(),phone);

        Callable<Boolean> submitcallable = new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception
            {
                return biz.bindPhone();

                // test
                //Thread.sleep(1000);
//                return true;
                

            }
        };
        BizNetWork(submitcallable, process);

    }

}
