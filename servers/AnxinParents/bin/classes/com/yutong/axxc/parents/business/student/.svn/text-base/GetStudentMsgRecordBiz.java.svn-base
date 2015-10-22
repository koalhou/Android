package com.yutong.axxc.parents.business.student;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.SharedPreferencesUtil;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.MsgRecordBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.GetStudentMsgRecordReq;
import com.yutong.axxc.parents.connect.http.packet.GetStudentMsgRecordRes;
import com.yutong.axxc.parents.dao.beans.DaoStudentMsgRecordBean;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.dao.student.StudentMsgRecordDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 获取学生消息记录逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class GetStudentMsgRecordBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 学生Id */
    private String cld_id;

    /** 消息时间 格式：yyyyMMdd */
    private String msg_date;

    /** 数据读取方式 */
    private ReadMethodEnum optype = ReadMethodEnum.OPTYPE_REMOTE;

    private String belongEtag = EtagSettingsDao.MSG_HISTORY_ETAG;

    private String belongCache = CahceSettingsDao.MSG_HISTORY_ETAG;

    private String exkey;

    public GetStudentMsgRecordBiz(Context context, Handler handler, String cld_id, String msg_date)
    {
        this.context = context;
        this.handler = handler;
        this.cld_id = cld_id;
        this.msg_date = msg_date;
        logTypeName = "[获取学生消息记录逻辑类]:";
        exkey = cld_id + msg_date;

    }

    @Override
    protected void doInBackground()
    {
    	//test
//        List<MsgRecordBean> msgRecordBeans = initDemoData();
//        ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, msgRecordBeans);

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

        List<MsgRecordBean> msgRecordBeans = getLocalData();

        if (msgRecordBeans != null && msgRecordBeans.size() > 0)
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, msgRecordBeans);
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

    private List<MsgRecordBean> getLocalData()
    {
        List<MsgRecordBean> msgRecordBeans = null;
        StudentMsgRecordDao dao = new StudentMsgRecordDao(context);
        List<DaoStudentMsgRecordBean> daoStudentMsgRecordBeans = dao.getStudentMsgRecord(cld_id, msg_date);

        if (daoStudentMsgRecordBeans != null && daoStudentMsgRecordBeans.size() > 0)
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");
            msgRecordBeans = new ArrayList<MsgRecordBean>();
            for (DaoStudentMsgRecordBean item : daoStudentMsgRecordBeans)
            {
                MsgRecordBean msgRecordBean = buildMsgRecordBean(item);
                msgRecordBeans.add(msgRecordBean);
            }
        }

        return msgRecordBeans;
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetStudentMsgRecordReq req = buildReq();

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
        GetStudentMsgRecordRes res = new GetStudentMsgRecordRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            if (httpRes.needCached())
            {
                Logger.i(this.getClass(), logTypeName + "设置缓存");

                StudentMsgRecordDao dao = new StudentMsgRecordDao(context);
                // 先删除数据库中此学生当前日期所有记录
                dao.delStudentMsgRecord(cld_id, msg_date);

                if (res.getMsgRecordBeans() != null && res.getMsgRecordBeans().size() > 0)
                {
                    for (MsgRecordBean item : res.getMsgRecordBeans())
                    {
                        DaoStudentMsgRecordBean daoStudentMsgRecordBean = buildDaoStudentMsgRecordBean(item);

                        dao.addStudentMsgRecord(daoStudentMsgRecordBean);
                    }
                }

                EtagSettingsDao.saveETag(belongEtag, exkey, httpRes.getETag());

                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_CHANGED, res.getMsgRecordBeans());

            }
            else
            {
                Logger.i(this.getClass(), logTypeName + "网络数据无变化");
                List<MsgRecordBean> msgRecordBeans = getLocalData();

                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_NO_CHANGED, msgRecordBeans);
            }

        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private DaoStudentMsgRecordBean buildDaoStudentMsgRecordBean(MsgRecordBean obj)
    {
        DaoStudentMsgRecordBean daoStudentMsgRecordBean = new DaoStudentMsgRecordBean();
        daoStudentMsgRecordBean.setMsg_id(obj.getMsg_id());
        daoStudentMsgRecordBean.setCld_id(obj.getCld_id());
        daoStudentMsgRecordBean.setRule_id(obj.getRule_id());
        daoStudentMsgRecordBean.setMsg_content(obj.getMsg_content());
        daoStudentMsgRecordBean.setMsg_time(obj.getMsg_time());

        return daoStudentMsgRecordBean;
    }

    private MsgRecordBean buildMsgRecordBean(DaoStudentMsgRecordBean obj)
    {
        MsgRecordBean msgRecordBean = new MsgRecordBean();
        msgRecordBean.setMsg_id(obj.getMsg_id());
        msgRecordBean.setCld_id(obj.getCld_id());
        msgRecordBean.setRule_id(obj.getRule_id());
        msgRecordBean.setMsg_content(obj.getMsg_content());
        msgRecordBean.setMsg_time(obj.getMsg_time());

        return msgRecordBean;
    }

    private GetStudentMsgRecordReq buildReq()
    {
        GetStudentMsgRecordReq req = new GetStudentMsgRecordReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setIfNoneMatch(EtagSettingsDao.getETag(belongEtag, exkey));
        req.setCld_id(cld_id);
        req.setRecord_date(msg_date);
        
        //req.setUsr_id(YtApplication.getInstance().getUid());
        return req;
    }
    
    private  List<MsgRecordBean> initDemoData()
    {
        List<MsgRecordBean> msgRecordBeans = new ArrayList<MsgRecordBean>();
        MsgRecordBean obj1 = new MsgRecordBean();
        obj1.setMsg_id(UUID.randomUUID().toString());
      
        obj1.setMsg_id("1");
        obj1.setCld_id(cld_id);
        obj1.setRule_id("01_001");
        obj1.setMsg_time("20130910082000"); 
        JSONObject jObj1 = new JSONObject();
        try
        {
      
            jObj1.put("cld_alias", "果果");
            jObj1.put("vehicle_vin", "1");
            jObj1.put("vehicle_plate", "辽L28660");
            jObj1.put("vehicle_lon", "120.67176");
            jObj1.put("vehicle_lat", "31.127423");
            jObj1.put("driver_name", "王正平");
            jObj1.put("teacher", "李小路");
            jObj1.put("teacher_phone", "13854125555");
            jObj1.put("station_name", "某某地点");
            
            
        }
        catch (JSONException e)
        {
        }
        obj1.setMsg_content(jObj1.toString());
        msgRecordBeans.add(obj1);
        
        /*--------------------------------------------------------*/
        MsgRecordBean obj2 = new MsgRecordBean();
        obj2.setMsg_id(UUID.randomUUID().toString());
      
        obj2.setMsg_id("2");
        obj2.setCld_id(cld_id);
        obj2.setRule_id("01_002");
        obj2.setMsg_time("20130910093000");
        JSONObject jObj2 = new JSONObject();
        try
        {
            jObj2.put("cld_alias", "果果");
            jObj2.put("vehicle_vin", "2");
            jObj2.put("vehicle_plate", "辽L28660");
            jObj2.put("vehicle_lon", "120.67176");
            jObj2.put("vehicle_lat", "31.167423");
            jObj2.put("driver_name", "王正平");
            jObj2.put("teacher", "李小路");
            jObj2.put("teacher_phone", "13854125471");
            jObj2.put("station_name", "某某地点");

        }
        catch (JSONException e)
        {
        }
        obj2.setMsg_content(jObj2.toString());
        msgRecordBeans.add(obj2);
        
        /*--------------------------------------------------------*/
        MsgRecordBean obj3 = new MsgRecordBean();
        obj3.setMsg_id(UUID.randomUUID().toString());
      
        obj3.setMsg_id("3");
        obj3.setCld_id(cld_id);
        obj3.setRule_id("01_003");
        obj3.setMsg_time("20130910122200");
        JSONObject jobj3 = new JSONObject();
        try
        {
            jobj3.put("cld_alias", "果果");
            jobj3.put("vehicle_vin", "3");
            jobj3.put("vehicle_plate", "辽L28660");
            jobj3.put("vehicle_lon", "120.67176");
            jobj3.put("vehicle_lat", "31.197423");
            jobj3.put("driver_name", "王正平");
            jobj3.put("teacher", "李小路");
            jobj3.put("teacher_phone", "13854126666");
            jobj3.put("station_name", "某某地点");

        }
        catch (JSONException e)
        {
        }
        obj3.setMsg_content(jobj3.toString());
        msgRecordBeans.add(obj3);
        
        
        return msgRecordBeans;
    }
    
    

}
