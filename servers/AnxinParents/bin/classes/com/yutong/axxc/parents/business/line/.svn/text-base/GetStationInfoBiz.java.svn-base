package com.yutong.axxc.parents.business.line;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.cache.AppCacheData;
import com.yutong.axxc.parents.business.interfacer.ICacheControl;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.StationInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.GetSationInfoReq;
import com.yutong.axxc.parents.connect.http.packet.GetSationInfoRes;
import com.yutong.axxc.parents.dao.common.CacheTimeOutSettingsDao;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 获取学生站点信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class GetStationInfoBiz extends YtAsyncTask implements ICacheControl
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 学生Id */
    private String cld_id;
    /** 线路类型 */
    private String line_type;

    /** 数据读取方式 */
    private ReadMethodEnum optype = ReadMethodEnum.OPTYPE_REMOTE;

    private String belongEtag = EtagSettingsDao.STUDENT_STATION_INFO_ETAG;

    private String belongCache = CahceSettingsDao.STUDENT_STATION_INFO_ETAG;

    private String exkey;
    
    public GetStationInfoBiz(Context context, Handler handler, String cld_id, String line_type)
    {
       this(context,handler,cld_id,line_type,ReadMethodEnum.OPTYPE_REMOTE);
    }
    public GetStationInfoBiz(Context context, Handler handler, String cld_id, String line_type,ReadMethodEnum optype)
    {
        this.context = context;
        this.handler = handler;
        this.cld_id = cld_id;
        this.line_type = line_type;
        logTypeName = "[获取学生站点信息逻辑类]:";
        
        exkey = cld_id + line_type;
        this.optype = optype;
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
        List<StationInfoBean> stationInfoBeans = getLocalData();
        
        if (stationInfoBeans != null){
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");
            
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, stationInfoBeans);
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

    private List<StationInfoBean> getLocalData()
    {
        String jsonString = CahceSettingsDao.getCacheInfo(belongCache, exkey);
        
        if (StringUtils.isNotBlank(jsonString))
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");
            GetSationInfoRes res = new GetSationInfoRes();
            res.parse(jsonString);
            AppCacheData.putStationInfos(cld_id, line_type, res.getStationInfoBeans());
            
            return res.getStationInfoBeans();
        }
        else
        {
            return null;
        }
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetSationInfoReq req = buildReq();

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
        GetSationInfoRes res = new GetSationInfoRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            if (httpRes.needCached())
            {
                Logger.i(this.getClass(), logTypeName + "设置缓存");

                CahceSettingsDao.setCacheInfo(belongCache,exkey, httpRes.getContent());
                EtagSettingsDao.saveETag(belongEtag, exkey, httpRes.getETag());

                AppCacheData.putStationInfos(cld_id, line_type, res.getStationInfoBeans());
                
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_CHANGED, res.getStationInfoBeans());

            }
            else
            {
                Logger.i(this.getClass(), logTypeName + "网络数据无变化");
                List<StationInfoBean> stationInfoBeans = getLocalData();
                
                AppCacheData.putStationInfos(cld_id, line_type, res.getStationInfoBeans());
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_NO_CHANGED, stationInfoBeans);
            }
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private GetSationInfoReq buildReq()
    {
        GetSationInfoReq req = new GetSationInfoReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setIfNoneMatch(EtagSettingsDao.getETag(belongEtag, exkey));
        req.setCld_id(cld_id);
        req.setLine_type(line_type);
        
        return req;
    }
    
    @Override
    public boolean isCacheTimeOut()
    {
        try
        {
            String startTime = Tools.getFullCurrentTime();

            String endTime = CacheTimeOutSettingsDao.get(belongEtag, exkey);

            if (StringUtils.isBlank(endTime))
            {
                return true;
            }
            
            long diffseconds = Tools.getDifferenceTime(startTime, endTime);

            if (diffseconds > 60 * 60 * 24)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (ParseException e)
        {
            Logger.i(this.getClass(), logTypeName + "isCacheTimeOut计算出错");
            return true;
        }
    }

}
