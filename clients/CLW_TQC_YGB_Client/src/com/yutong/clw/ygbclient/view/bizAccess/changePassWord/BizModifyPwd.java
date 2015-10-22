/**
 * @公司名称：YUTONG
 * @作者：lizyi
 * @版本号：1.0
 * @生成日期：2013-11-8 下午3:23:26
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bizAccess.changePassWord;

import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.login.ForgotPasswordBiz;
import com.yutong.clw.ygbclient.business.setting.SetNewPasswordBiz;
import com.yutong.clw.ygbclient.business.setting.VerifyOldPasswordBiz;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * 密码修改逻辑控制类
 * 
 * @author lizyi 2013-11-8 下午3:23:26
 */
public class BizModifyPwd extends BizBase
{

    public BizModifyPwd(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public BizModifyPwd(Context context, int threadcount)
    {
        super(context, threadcount);
        // TODO Auto-generated constructor stub
    }

    /**
     * 验证旧密码
     * 
     * @author lizyi 2013-11-8 下午3:26:21
     * @param oldPwdMD5 旧密码
     * @param process
     */
    public void verifyOldPwd(String oldPwdMD5, BizResultProcess<Boolean> process)
    {
        final VerifyOldPasswordBiz biz = new VerifyOldPasswordBiz(YtApplication.getInstance(), oldPwdMD5);

        Callable<Boolean> submitcallable = new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception
            {
                return biz.verifyOldPassword();
            }
        };
        BizNetWork(submitcallable, process);
    }

    /**
     * 提交新密码
     * 
     * @author lizyi 2013-11-8 下午3:26:21
     * @param oldPwdMD5 旧密码
     * @param newPwdMD5 新密码
     * @param process
     */
    public void modifyPwd(String oldPwdMd5, String newPwdMd5, BizResultProcess<Boolean> process)
    {
        final SetNewPasswordBiz biz = new SetNewPasswordBiz(YtApplication.getInstance(), oldPwdMd5, newPwdMd5);

        Callable<Boolean> submitcallable = new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception
            {
                return biz.updatePassword();
            }
        };
        BizNetWork(submitcallable, process);
    }
}
