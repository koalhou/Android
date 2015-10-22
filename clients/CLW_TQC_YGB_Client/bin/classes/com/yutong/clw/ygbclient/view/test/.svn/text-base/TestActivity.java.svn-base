package com.yutong.clw.ygbclient.view.test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.validation.DataValidation;
import com.yutong.clw.ygbclient.common.validation.VerifyResult;

public class TestActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ((Button) findViewById(R.id.btn_at_test)).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                // TODO Auto-generated method stub

                try
                {
                    new Thread()
                    {
                        @Override
                        public void run()
                        {
                            // TODO Auto-generated method stub
                            super.run();

                            /**
                             * @param ruleString [$ruleString$][$ruleString$]
                             * 
                             * ruleString 为以下情况
                             * 
                             *        Custom("正则表达式", "验证不通过描述")
                             *            示例：Custom("\d+","必须为数字，且至少包含1个数字") 
                             *        Len("最小长度", "最大长度")
                             *            示例：Len(1,4)
                             *        Num() 或 Num("最小长度", "最大长度")
                             *            示例：Num或者 Num(1,4)
                             *        NoChar(字符1字符2...)
                             *            示例：NoChar("%@") 
                             *        NoEmpty
                             *            示例：NoEmpty
                             *        Phone 
                             *            示例：Phone
                             *        Email 
                             *            示例：Email
                             */

                            //规则串写在[$和$]之间，多个规则:[$规则一$][$规则二$], 例如：
                            //只能为数字:[$Num$]
                            //只能为数字且长度在4-12之间:[$Num(1,12)$]
                            //只能是电话号码格式:[$Phone$]
                            //只能是邮箱格式:[$Phone$]
                            //获取方式一
                            //发现一项未通过后返回
                            VerifyResult result = DataValidation.validate("[$Len(1,3)$][$Num$]", "1111");
                            if(result.isPass)
                            {
                                Logger.i(this.getClass(), "通过");
                            }
                            else
                            {
                                Logger.i(this.getClass(), "不通过，原因：" + result.faildesc);
                            }
                            
                            //获取方式二
                            //发现多项未通过后返回
                            VerifyResult results = DataValidation.validate("[$Len(1,3)$][$Num$]","zhangzhi", false);
                            
                            if(results.isPass)
                            {
                                Logger.i(this.getClass(), "通过");
                            }
                            else
                            {
                                for(VerifyResult item:results.verifyResults)
                                {
                                    Logger.i(this.getClass(), "不通过，原因：" + item.faildesc);
                                }
                                
                            }
                           
                            
                            
                            
                              //LoginBiz loginBiz = new LoginBiz(YtApplication.getInstance(), "90077645", "A11111111111111111111111111111111");
                            
//                            VerifyOldPasswordBiz loginBiz = new VerifyOldPasswordBiz(YtApplication.getInstance(), "sd");
//                            
//                            if (loginBiz.IsCacheExpires())
//                            {
//
//                            }
//                      
//                                //loginBiz.login();
//                                try
//                                {
//                                    loginBiz.verifyOldPassword();
//                                }
//                                catch (CommonException e)
//                                {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                }
//                                
                      
                            // new GetNetResourceBiz(YtApplication.getInstance()).getImage("http://www.yutong.com/images/news/yutongnews/2013/10/28/BDC8D5453EDB992BC0B375DF73D8A001.jpg");
//                            LoginInfoDao dao = new LoginInfoDao(YtApplication.getInstance());
//                            UserInfo loginInfoBean = new UserInfo();
//                            /** 用户ID */
//                            loginInfoBean.Id = "11";
//                            /** 员工号 */
//                            /** 员工所在厂区 1:宇通工业园，2:：新能源工厂 */
//                            loginInfoBean.BelongArea = AreaType.FirstFactory;
//
//                            String str = loginInfoBean.BelongArea.toString();
//                            /** 是否未修改默认密码，0：否，1：是 */
//                            loginInfoBean.DefaultPassword = false;
//
//                            dao.addLoginInfo(loginInfoBean);
//
//                            UserInfo user = dao.getLoginInfo();
                        }
                    }.start();

                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

}
