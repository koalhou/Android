package com.yutong.axxc.parents.business.view;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.cache.AppCacheData;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.GetStudentInfoReq;
import com.yutong.axxc.parents.connect.http.packet.GetStudentInfoRes;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 读取学生信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class ReadStudentInfoBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;


    /**
     * 构造函数
     * 
     * @param context Context对象
     * @param handler Handler对象
     */
    public ReadStudentInfoBiz(Context context, Handler handler)
    {
        this.context = context;
        this.handler = handler;
        logTypeName = "读取学生信息逻辑类";
    }


    @Override
    protected void doInBackground()
    {
        Logger.i(this.getClass(), logTypeName + "获取本地信息");
        List<StudentInfoBean> studentInfoBeans = AppCacheData.getStudentInfos(YtApplication.getInstance().getUid());

        ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, studentInfoBeans);
    }

}
