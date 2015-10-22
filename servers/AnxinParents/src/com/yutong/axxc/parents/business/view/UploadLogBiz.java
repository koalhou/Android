package com.yutong.axxc.parents.business.view;

import java.util.ArrayList;
import java.util.List;

import com.yutong.axxc.parents.business.behaviour.UserBehaviorUploadBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.SysConfigUtil;
import com.yutong.axxc.parents.common.beans.UserBehaviorBean;

public class UploadLogBiz
{
   private static List<UserBehaviorBean>  userBehaviorBeans = new ArrayList<UserBehaviorBean>();

    public static void addUserBehavior(String module)
    {
        Logger.i(UploadLogBiz.class, "[上传日志信息罗逻辑类]" + "保存用户行为日志");
        userBehaviorBeans.add(new UserBehaviorBean(module));
        if(userBehaviorBeans.size() >= SysConfigUtil.getUserBehaviorMaxSize())
        {
            uploadUserBehavior();
        }

    }

    public static void uploadUserBehavior()
    {
        Logger.i(UploadLogBiz.class, "[上传日志信息罗逻辑类]" + "上传用户行为日志");
        (new UserBehaviorUploadBiz(null, null, userBehaviorBeans)).execute();
    }

}
