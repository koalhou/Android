package com.yutong.axxc.parents.business.student;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YTException;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.RidingRecordBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.GetStudentRedingRecordReq;
import com.yutong.axxc.parents.connect.http.packet.GetStudentRedingRecordRes;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 获取学生乘车信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class GetStudentRidingRecordBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 学生Id */
    private String cld_id;

    /** 数据读取方式 */
    private ReadMethodEnum optype = ReadMethodEnum.OPTYPE_REMOTE;

    private String belongEtag = EtagSettingsDao.CERTIGIER_INFO_ETAG;

    private String belongCache = CahceSettingsDao.CERTIGIER_INFO_ETAG;

    private String exkey;
    
    public GetStudentRidingRecordBiz(Context context, Handler handler, String cld_id)
    {
        this.context = context;
        this.handler = handler;
        this.cld_id = cld_id;
        logTypeName = "[获取学生乘车信息逻辑类]:";
        
        //缓存只跟学生关联。
        exkey =  cld_id;
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

        RidingRecordBean ridingRecordBean = getLocalData();
        if (ridingRecordBean != null)
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS,ridingRecordBean);
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

    private RidingRecordBean getLocalData()
    {
        String jsonString = CahceSettingsDao.getCacheInfo(belongCache, exkey);
        
        if (StringUtils.isNotBlank(jsonString))
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");
            GetStudentRedingRecordRes res = new GetStudentRedingRecordRes();
            res.parse(jsonString);
            return res.getRidingRecordBean();
        }
        else
        {
            return null;
        }
    }
    
    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetStudentRedingRecordReq req = buildReq();

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
        GetStudentRedingRecordRes res = new GetStudentRedingRecordRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            if (httpRes.needCached())
            {
                Logger.i(this.getClass(), logTypeName + "设置缓存");

                CahceSettingsDao.setCacheInfo(belongCache,exkey, httpRes.getContent());
                EtagSettingsDao.saveETag(belongEtag, exkey, httpRes.getETag());

                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_CHANGED, res.getRidingRecordBean());
            }
            else
            {
                Logger.i(this.getClass(), logTypeName + "网络数据无变化");
                RidingRecordBean ridingRecordBean = getLocalData();
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_NO_CHANGED, ridingRecordBean);
            }
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private GetStudentRedingRecordReq buildReq()
    {
        GetStudentRedingRecordReq req = new GetStudentRedingRecordReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setIfNoneMatch(EtagSettingsDao.getETag(belongEtag, exkey));
        req.setCld_id(cld_id);
        return req;
    }
    
    private static int speedtest=30;
    /**
     * 同步方式获取数据
     * @return
     * @throws Exception
     */
    public RidingRecordBean getRidingRecordInfo() throws Exception
    {
    	//test
//    	RidingRecordBean b=new RidingRecordBean();
//    	b.setCld_id("1");
//    	b.setDriver("张宏");
//    	b.setTeacher("李江");
//    	b.setTeacher_phone("13555555555");
//    	b.setVehicle_speed(""+(speedtest++));
//    	b.setVehicle_vin("123");
//    	return b;
    	
        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetStudentRedingRecordReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendGetMsg(req);

        if (httpRes.isSuccess())
        {
            GetStudentRedingRecordRes res = new GetStudentRedingRecordRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");

                if (httpRes.needCached())
                {
                    Logger.i(this.getClass(), logTypeName + "设置缓存");

                    CahceSettingsDao.setCacheInfo(belongCache,exkey, httpRes.getContent());
                    EtagSettingsDao.saveETag(belongEtag, exkey, httpRes.getETag());

                }
                else
                {
                    Logger.i(this.getClass(), logTypeName + "网络数据无变化");
                }
                RidingRecordBean ridingRecordBean = getLocalData();
                return ridingRecordBean;
            }
            else
            {
                Logger.i(this.getClass(), logTypeName + "失败");
                throw new YTException(ThreadCommStateCode.COMMON_FAILED);
            }
        }
        else if (httpRes.isTokenExpire())
        {
            Logger.i(this.getClass(), logTypeName + "Token失效");
            throw new YTException(ThreadCommStateCode.TOKEN_INVALID);
        }
        else if (httpRes.isException())
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new YTException(ThreadCommStateCode.NETWORK_ERROR);
        }
        else
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            
            throw new YTException(ThreadCommStateCode.COMMON_FAILED);
        }
    }

}
