/**
 * @公司名称：YUTONG
 * @作者：zhangyongn
 * @版本号：1.0
 * @生成日期：2013-10-31 下午2:57:05
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bizAccess.validate;

import java.util.Date;
import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.linestation.GetVehichesBiz;
import com.yutong.clw.ygbclient.business.other.SendPhoneSMSBiz;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.SMSVerifyType;
import com.yutong.clw.ygbclient.common.validation.DataValidation;
import com.yutong.clw.ygbclient.common.validation.VerifyResult;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * @author zhangyongn 2013-10-31 下午2:57:05 发送验证码
 */
public class BizSendValidCode extends BizBase
{
    public BizSendValidCode(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public BizSendValidCode(Context context, int threadcount)
    {
        super(context, threadcount);
        // TODO Auto-generated constructor stub
    }

    /**
     * 发送验证码
     *@author zhangyongn 2013-10-31 下午3:11:07
     *
     * @param code 需要服务端通过短信发送的验证码
     * @param process
     */
    public void SendCode(String phone,String code, SMSVerifyType type,BizResultProcess<String> process)
    {
        final SendPhoneSMSBiz biz = new SendPhoneSMSBiz(YtApplication.getInstance(), phone,code,type);
        
        //final GetVehichesBiz biz1 = new GetVehichesBiz(YtApplication.getInstance(), new Date(), AreaType.FirstFactory, LineRange.FactoryInside);
        
        Callable<String> submitcallable = new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                return biz.sendSMS();
                //test
                //Thread.sleep(1000);
                //biz1.getVehichesFromLocal();
                //biz1.getVehichesFromSever();
                
//                DataValidation validObj = new DataValidation();
//                VerifyResult bl = validObj.VerifyData("JavaSDFSDFSDFS");
               
               
//                return "111111";
            }
        };
        BizNetWork(submitcallable, process);

    }

}
