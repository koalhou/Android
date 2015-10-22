package com.yutong.axxc.parents.business.student;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.StationInfoBean;
import com.yutong.axxc.parents.common.beans.StudentCustomInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.GetStudentCustomInfoReq;
import com.yutong.axxc.parents.connect.http.packet.GetStudentCustomInfoRes;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 获取学生个性信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class GetStudentCustomInfoBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 学生Id */
    private String cld_id;

    /** 数据读取方式 */
    private ReadMethodEnum optype = ReadMethodEnum.OPTYPE_REMOTE;

    private String belongEtag = EtagSettingsDao.STUDENT_CUSTOM_INFO_ETAG;

    private String belongCache = CahceSettingsDao.STUDENT_CUSTOM_INFO_ETAG;

    private String exkey;

    public GetStudentCustomInfoBiz(Context context, Handler handler, String cld_id)
    {
        this.context = context;
        this.handler = handler;
        this.cld_id = cld_id;
        logTypeName = "[获取学生个性信息逻辑类]:";

        exkey = cld_id;
    }

    public void setReadMethod(ReadMethodEnum optype)
    {
        this.optype = optype;
    }

    @Override
    protected void doInBackground()
    {
        switch (optype)
        {
        case OPTYPE_REMOTE:
            handleProcess();
            break;
        case OPTYPE_LOCAL:
            getInfoFromLocal();
            break;
        case OPTYPE_LOCAL_AND_REMOTE:
            getInfoFromLocal();
            handleProcess();
            break;
        default:
            break;
        }
        
        //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0015);
        
    }

    /**
     * 从本地获取信息
     */
    private void getInfoFromLocal()
    {
        Logger.i(this.getClass(), logTypeName + "获取本地信息");

        StudentCustomInfoBean studentCustomInfoBean = getLocalData();
        if (studentCustomInfoBean != null)
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, studentCustomInfoBean);
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "没有缓存数据");
            if(optype == ReadMethodEnum.OPTYPE_LOCAL)
            {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.CACHE_NO_DATA);
            }
            else if(optype == ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE)
            {
                handleProcess();
            }
        }
    }

    private StudentCustomInfoBean getLocalData()
    {
        String jsonString = CahceSettingsDao.getCacheInfo(belongCache, exkey);

        if (StringUtils.isNotBlank(jsonString))
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");
            GetStudentCustomInfoRes res = new GetStudentCustomInfoRes();
            res.parse(jsonString);
            return res.getStudentCustomInfoBean();
        }
        else
        {
            return null;
        }
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetStudentCustomInfoReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendGetMsg(req);
        // 用来UI取消操作
        if (isCanceled())
        {
            return;
        }

        if (httpRes.isSuccess())
        {
            parseAndProcessRes(httpRes);
        }
        else if (httpRes.isTokenExpire())
        {
            Logger.i(this.getClass(), logTypeName + "Token失效");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.TOKEN_INVALID);
        }
        else if (httpRes.isException())
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.NETWORK_ERROR, httpRes.getFailInfo());
        }
        else
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, httpRes.getFailInfo());
        }
    }

    private void parseAndProcessRes(HttpRes httpRes)
    {
        GetStudentCustomInfoRes res = new GetStudentCustomInfoRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            if (httpRes.needCached())
            {
                Logger.i(this.getClass(), logTypeName + "设置缓存");

                CahceSettingsDao.setCacheInfo(belongCache, exkey, httpRes.getContent());
                EtagSettingsDao.saveETag(belongEtag, exkey, httpRes.getETag());

                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_CHANGED, res.getStudentCustomInfoBean());

            }
            else
            {
                Logger.i(this.getClass(), logTypeName + "网络数据无变化");
                StudentCustomInfoBean studentCustomInfoBean = getLocalData();
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_NO_CHANGED, studentCustomInfoBean);
            }
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private GetStudentCustomInfoReq buildReq()
    {
        GetStudentCustomInfoReq req = new GetStudentCustomInfoReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setIfNoneMatch(EtagSettingsDao.getETag(belongEtag, exkey));
        req.setCld_id(cld_id);
        return req;
    }

}
