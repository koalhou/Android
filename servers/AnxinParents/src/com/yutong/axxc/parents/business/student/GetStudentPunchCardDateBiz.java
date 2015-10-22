package com.yutong.axxc.parents.business.student;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.PunchCardDateBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.GetStudentPunchCardDateReq;
import com.yutong.axxc.parents.connect.http.packet.GetStudentPunchCardDateRes;
import com.yutong.axxc.parents.dao.beans.DaoPunchCardDateBean;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.dao.student.PunchCardDateRecordDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 获取学生打卡日期逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class GetStudentPunchCardDateBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 查询月份 */
    private String month;

    /** 学生Id */
    private String cld_id;

    private PunchCardDateRecordDao dao;

    private Gson gson = new Gson();
    
    private Type type = new TypeToken<List<String>>() {
    }.getType();

    /** 数据读取方式 */
    private ReadMethodEnum optype = ReadMethodEnum.OPTYPE_REMOTE;

    private String belongEtag = EtagSettingsDao.MSG_HISTORY_ETAG;

    private String belongCache = CahceSettingsDao.MSG_HISTORY_ETAG;

    private String exkey;

    public GetStudentPunchCardDateBiz(Context context, Handler handler, String cld_id, String month)
    {
        this.context = context;
        this.handler = handler;
        this.cld_id = cld_id;
        this.month =month;// "201205";
        logTypeName = "[获取学生打卡日期逻辑类]:";
        exkey = cld_id + month;
        dao = new PunchCardDateRecordDao(context);
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

        PunchCardDateBean punchCardDateBean = getLocalData();
        if (punchCardDateBean != null)
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");

            // 检查是否包含今天的数据
            String punchCardDay = Tools.getFormatTime(Calendar.getInstance((Locale.CHINA)));
            if (punchCardDateBean.getPunchCardDates().contains(punchCardDay))
            {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, punchCardDateBean.getPunchCardDates());
            }
            else
            {
                if (optype == ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE)
                {
                    handleProcess();
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

    @SuppressWarnings("unchecked")
    private PunchCardDateBean getLocalData()
    {
        PunchCardDateBean punchCardDateBean = null;
        DaoPunchCardDateBean daoPunchCardDateBean = dao.getRecord(cld_id, month);

        if (daoPunchCardDateBean != null)
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");
            punchCardDateBean = new PunchCardDateBean();
            punchCardDateBean.setCld_id(cld_id);
            punchCardDateBean.setMonth(month);

                    
            punchCardDateBean.setPunchCardDates((List<String>) gson.fromJson(daoPunchCardDateBean.getPunchCard_day(),type));

            return punchCardDateBean;
        }
        else
        {
            return null;
        }
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetStudentPunchCardDateReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendPostMsg(req);
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
        GetStudentPunchCardDateRes res = new GetStudentPunchCardDateRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            if (httpRes.needCached())
            {
                if (res.getPunchCardDateBean() != null)
                {

                    Logger.i(this.getClass(), logTypeName + "设置缓存");

                    // 先删除数据库中此学生当前日期所有记录
                    dao.delRecord(cld_id, month);

                    DaoPunchCardDateBean daoBean = buildDaoPunchCardDateBean(res.getPunchCardDateBean());
                    dao.addRecord(daoBean);

                    EtagSettingsDao.saveETag(belongEtag, exkey, httpRes.getETag());

                    ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_CHANGED, res.getPunchCardDateBean().getPunchCardDates());

                }
            }
            else
            {
                Logger.i(this.getClass(), logTypeName + "网络数据无变化");
                PunchCardDateBean punchCardDateBean = getLocalData();
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_NO_CHANGED, punchCardDateBean);
            }

        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private DaoPunchCardDateBean buildDaoPunchCardDateBean(PunchCardDateBean punchCardDateBean)
    {
        DaoPunchCardDateBean daoBean = new DaoPunchCardDateBean();
        daoBean.setCld_id(cld_id);
        daoBean.setPunchcard_month(month);
        daoBean.setPunchCard_day(gson.toJson(punchCardDateBean.getPunchCardDates(), type));

        return daoBean;
    }

    private GetStudentPunchCardDateReq buildReq()
    {
        GetStudentPunchCardDateReq req = new GetStudentPunchCardDateReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setIfNoneMatch(EtagSettingsDao.getETag(belongEtag, exkey));
        req.setCld_id(cld_id);
        req.setMonth(month);
        return req;
    }

}
