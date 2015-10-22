package com.yutong.axxc.parents.business.student;

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
 * 获取学生信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class GetStudentInfoBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 数据读取方式 */
    private ReadMethodEnum optype = ReadMethodEnum.OPTYPE_REMOTE;

    private String belongEtag = EtagSettingsDao.STUDENT_INFO_LIST_ETAG;

    private String belongCache = CahceSettingsDao.STUDENT_INFO_LIST_ETAG;

    private String exkey;

    /**
     * 构造函数
     * 
     * @param context Context对象
     * @param handler Handler对象
     */
    public GetStudentInfoBiz(Context context, Handler handler)
    {
        this(context, handler, ReadMethodEnum.OPTYPE_REMOTE);
    }

    public GetStudentInfoBiz(Context context, Handler handler, ReadMethodEnum optype)
    {
        this.context = context;
        this.handler = handler;
        logTypeName = "[获取学生信息逻辑类]:";

        exkey = YtApplication.getInstance().getUid();
        this.optype = optype;
    }

    public void setReadMethod(ReadMethodEnum optype)
    {
        this.optype = optype;
    }

    @Override
    protected void doInBackground()
    {
        // SharedPreferencesUtil.clear(SharedPreferencesUtil.CACHE_PREFS_NAME);
        // SharedPreferencesUtil.clear(SharedPreferencesUtil.ETAG_PREFS_NAME);

        // List<StudentInfoBean> list = new ArrayList<StudentInfoBean>();
        // for (int i = 0; i < 2; i++) {
        // StudentInfoBean b = new StudentInfoBean();
        // b.setCld_id(i + "");
        // b.setCld_name("果果" + i);
        // list.add(b);
        // }
        // ThreadCommUtil.sendMsgToUI(handler,
        // ThreadCommStateCode.COMMON_SUCCESS,
        // list);

        switch (optype)
        {
        case OPTYPE_LOCAL:
            getInfoFromLocal();
            break;
        case OPTYPE_REMOTE:
            handleProcess();
            break;
        case OPTYPE_LOCAL_AND_REMOTE:
            getInfoFromLocal();
            break;
        default:
            break;
        }
    }

    /**
     * 从本地获取信息
     */
    private void getInfoFromLocal()
    {

        Logger.i(this.getClass(), logTypeName + "获取本地信息");
        List<StudentInfoBean> studentInfoBeans = getLocalData();

        if (studentInfoBeans != null)
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, studentInfoBeans);

            // 临时加
            if (studentInfoBeans != null && studentInfoBeans.size() > 0)
            {
                for (StudentInfoBean stuInfo : studentInfoBeans)
                {
                    GetStudentCustomInfoBiz bizz = new GetStudentCustomInfoBiz(context, null, stuInfo.getCld_id());
                    bizz.setReadMethod(ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE);
                    bizz.execute();
                }
            }

        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "没有缓存数据");
            if (optype == ReadMethodEnum.OPTYPE_LOCAL)
            {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.CACHE_NO_DATA);
            }
            else if (optype == ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE)
            {
                handleProcess();
            }
        }
    }

    private List<StudentInfoBean> getLocalData()
    {
        String jsonString = CahceSettingsDao.getCacheInfo(belongCache, exkey);

        if (StringUtils.isNotBlank(jsonString))
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");
            GetStudentInfoRes res = new GetStudentInfoRes();
            res.parse(jsonString);
            AppCacheData.putStudentInfos(YtApplication.getInstance().getUid(), res.getStudentInfoBeans());
            return res.getStudentInfoBeans();
        }
        else
        {
            return null;
        }
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetStudentInfoReq req = buildReq();

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
        GetStudentInfoRes res = new GetStudentInfoRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            if (httpRes.needCached())
            {
                Logger.i(this.getClass(), logTypeName + "设置缓存");

                CahceSettingsDao.setCacheInfo(belongCache, exkey, httpRes.getContent());
                EtagSettingsDao.saveETag(belongEtag, exkey, httpRes.getETag());

                AppCacheData.putStudentInfos(YtApplication.getInstance().getUid(), res.getStudentInfoBeans());

                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_CHANGED, res.getStudentInfoBeans());
            }
            else
            {
                Logger.i(this.getClass(), logTypeName + "网络数据无变化");
                List<StudentInfoBean> studentInfoBeans = getLocalData();
               
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_NO_CHANGED, studentInfoBeans);
            }
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private GetStudentInfoReq buildReq()
    {
        GetStudentInfoReq req = new GetStudentInfoReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setIfNoneMatch(EtagSettingsDao.getETag(belongEtag, exkey));
        return req;
    }

}
