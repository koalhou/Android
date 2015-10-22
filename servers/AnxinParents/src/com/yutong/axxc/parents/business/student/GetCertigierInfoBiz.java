package com.yutong.axxc.parents.business.student;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.SharedPreferencesUtil;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.LineInfoBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.GetCertigierInfoReq;
import com.yutong.axxc.parents.connect.http.packet.GetCertigierInfoRes;
import com.yutong.axxc.parents.connect.http.packet.GetStudentLineInfoRes;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 获取所有授权人信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class GetCertigierInfoBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 学生id */
    private String cld_id;

    /** 数据读取方式 */
    private ReadMethodEnum optype = ReadMethodEnum.OPTYPE_REMOTE;

    private String belongEtag = EtagSettingsDao.CERTIGIER_INFO_ETAG;

    private String belongCache = CahceSettingsDao.CERTIGIER_INFO_ETAG;

    private String exkey;

    public GetCertigierInfoBiz(Context context, Handler handler, String cld_id)
    {
        this.context = context;
        this.handler = handler;
        this.cld_id = cld_id;
        logTypeName = "[获取学生所有授权人信息逻辑类]:";

        exkey = YtApplication.getInstance().getUid() + cld_id;
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
        List<UserInfoBean> userInfoBeans = getLocalData();
        
        if (userInfoBeans != null)
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, userInfoBeans);
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

    private List<UserInfoBean> getLocalData()
    {
        String jsonString = CahceSettingsDao.getCacheInfo(belongCache, exkey);
        
        if (StringUtils.isNotBlank(jsonString))
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");
            GetCertigierInfoRes res = new GetCertigierInfoRes();
            res.parse(jsonString);
            return res.getUserInfoBeans();
        }
        else
        {
            return null;
        }
    }
    
    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetCertigierInfoReq req = buildReq();

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
        GetCertigierInfoRes res = new GetCertigierInfoRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            if (httpRes.needCached())
            {
                Logger.i(this.getClass(), logTypeName + "设置缓存");

                CahceSettingsDao.setCacheInfo(belongCache,exkey, httpRes.getContent());
                EtagSettingsDao.saveETag(belongEtag, exkey, httpRes.getETag());

                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_CHANGED, res.getUserInfoBeans());

            }
            else
            {
                Logger.i(this.getClass(), logTypeName + "网络数据无变化");
                List<UserInfoBean> userInfoBeans = getLocalData();
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_NO_CHANGED, userInfoBeans);
            }
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private GetCertigierInfoReq buildReq()
    {
        GetCertigierInfoReq req = new GetCertigierInfoReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setIfNoneMatch(EtagSettingsDao.getETag(belongEtag, exkey));
        req.setCld_id(cld_id);
        return req;
    }

}
