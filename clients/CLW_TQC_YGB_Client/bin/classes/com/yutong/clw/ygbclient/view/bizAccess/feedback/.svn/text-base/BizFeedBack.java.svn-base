/**
 * @公司名称：YUTONG
 * @作者：zhangyongn
 * @版本号：1.0
 * @生成日期：2013-10-30 下午3:59:00
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bizAccess.feedback;

import java.util.concurrent.Callable;
import android.content.Context;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.setting.AdviseFeedbackBiz;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * @author zhangyongn 2013-10-30 下午3:59:00
 */
public class BizFeedBack extends BizBase
{
    public BizFeedBack(Context context){
        super(context);
    }

    public BizFeedBack(Context context, int threadcount){
        super(context, threadcount);
    }

    public void SubmitFeedBack(final String msgID,String content, BizResultProcess<Boolean> process){
    	
        final AdviseFeedbackBiz biz = new AdviseFeedbackBiz(YtApplication.getInstance(), content);
        
        Callable<Boolean> submitcallable = new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception{
                return biz.reportAdviseFeedback(msgID);
            }
        };
        BizNetWork(submitcallable, process);
    }

}
