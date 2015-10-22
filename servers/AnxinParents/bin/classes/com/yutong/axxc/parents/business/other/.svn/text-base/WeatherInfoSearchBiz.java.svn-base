package com.yutong.axxc.parents.business.other;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.LocationUtils;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.WeatherInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.WeatherInfoReq;
import com.yutong.axxc.parents.connect.http.packet.WeatherInfoRes;
import com.yutong.axxc.parents.connect.http.packet.subres.WeatherDetailRes;
import com.yutong.axxc.parents.dao.other.WeatherInfoDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 天气信息查询业务逻辑类
 * 
 * @author zhangzhia 2013-9-2 下午2:37:51
 */
public class WeatherInfoSearchBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 学生id */
    private String cld_id;
    /**
     * 构造函数
     * 
     * @param context Context对象
     * @param handler Handler对象
     */
    public WeatherInfoSearchBiz(Context context, Handler handler, String cld_id)
    {
        this.context = context;
        this.handler = handler;
        this.cld_id = cld_id;
        logTypeName = "[天气信息查询业务逻辑类]:";

    }

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.common.YtAsyncTask#doInBackground()
     */
    @Override
    protected void doInBackground()
    {
        // 如果缓存中有内容且缓存更新时间小于当前时间则直接返回，否则去服务端获取
        if (YtApplication.getInstance().getWeatherInfoCache() == null)
        {
            getInfoFromLocal();
            getInfoFromServer();
        }
        else if (isNeedToUpgrade())
        {
            getInfoFromServer();
        }
        else
        {
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.HOME_GET_WEATHER_SUCCESS, YtApplication.getInstance()
                    .getWeatherInfoCache());
        }
    }

    /**
     * 从本地获取气象信息
     */
    private void getInfoFromLocal()
    {
        Logger.i(this.getClass(), logTypeName + "获取本地信息");

        String httpResContent = new WeatherInfoDao(context).getCachedWeatherInfo();
        if (StringUtils.isNotBlank(httpResContent))
        {
            parseAndProcessRes(httpResContent, null);
        }

    }

    /**
     * 从服务端获取数据
     */
    private void getInfoFromServer()
    {
        WeatherInfoReq req = buildWeatherInfoReq(context);
        Logger.i(getClass(), logTypeName + "发送信息获取请求");
        HttpRes httpRes = HttpMsgSendUtil.sendGetMsg(req);
        if (httpRes.isSuccess())
        {
            parseAndProcessRes(httpRes.getContent(), httpRes.getFailInfo());
            if (httpRes.needCached())
            {
                new WeatherInfoDao(context).cacheWeatherInfo(httpRes.getContent());
            }
            else
            {
                parseAndProcessRes(new WeatherInfoDao(context).getCachedWeatherInfo(), httpRes.getFailInfo());
            }
        }
        else if (httpRes.isTokenExpire())
        {
            Logger.i(getClass(), logTypeName + "Token失效");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.TOKEN_INVALID);
        }
        else
        {
            Logger.i(getClass(), logTypeName + "获取天气信息失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.HOME_GET_WEATHER_FAIL, httpRes.getFailInfo());
            parseAndProcessRes(new WeatherInfoDao(context).getCachedWeatherInfo(), null);
        }
    }

    /**
     * 解析并处理相应
     * 
     * @param httpResContent
     */
    private void parseAndProcessRes(String httpResContent, String toastContent)
    {
        WeatherInfoRes res = new WeatherInfoRes();
        res.parse(httpResContent);
        if (!res.isError())
        {
            Logger.i(getClass(), logTypeName + "获取天气请求发送成功");
            YtApplication.getInstance().setWeatherInfoCache(buildWeatherInfo(res));
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.HOME_GET_WEATHER_SUCCESS, YtApplication.getInstance()
                    .getWeatherInfoCache());
        }
        else
        {
            Logger.i(getClass(), logTypeName + "获取天气信息失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    /**
     * 是否需要更新
     * 
     * @return
     */
    private boolean isNeedToUpgrade()
    {
        try
        {
            if (StringUtils.isBlank(YtApplication.getInstance().getWeatherInfoCache().getNextUpgradTime()))
            {
                return false;
            }
            else
            {
                return Long.valueOf(Tools.getFullCurrentTime()) >= Long.valueOf(YtApplication.getInstance().getWeatherInfoCache()
                        .getNextUpgradTime());
            }
        }
        catch (Exception e)
        {
            return false;
        }

    }

    /**
     * 组装天气信息请求对象
     * 
     * @param context
     * @param bizLoginInfoBean
     * @return
     */
    private WeatherInfoReq buildWeatherInfoReq(Context context)
    {
        WeatherInfoReq weatherInfoReq = new WeatherInfoReq();
        weatherInfoReq.setAccessToken(YtApplication.getInstance().getAccessToken());
        //weatherInfoReq.setRegion(LocationUtils.getCityName());
        weatherInfoReq.setCld_id(cld_id);
        return weatherInfoReq;
    }

    /**
     * 组装天气信息对象
     * 
     * @param context
     * @param bizLoginInfoBean
     * @return
     */
    private WeatherInfoBean buildWeatherInfo(WeatherInfoRes weatherInfoRes)
    {
        WeatherInfoBean weatherInfoBean = new WeatherInfoBean();
        List<WeatherDetailRes> weatherDetailRes = weatherInfoRes.getWeatherDetailResList();
        if (weatherDetailRes != null && weatherDetailRes.size() == 1)
        {
            weatherInfoBean.setNextUpgradTime(weatherInfoRes.getTime());
            weatherInfoBean.setCity(weatherInfoRes.getCity());
            
            WeatherDetailRes today = weatherDetailRes.get(0);
            weatherInfoBean.setTodayDate(Tools.getTodayDate());
            weatherInfoBean.setTodayImg(today.getImgOne());
            weatherInfoBean.setTodayNextImg(today.getImgTwo());
            weatherInfoBean.setTodayDesc(today.getDesc());
            weatherInfoBean.setTodayTemper(today.getTemper() != null ? today.getTemper().replace("/", "~") : null);
            weatherInfoBean.setTodayWind(today.getWind());
            // weatherInfoBean.setTodayWeek(getWeekOfDate(0));
            weatherInfoBean.setTodayPM(today.getPm());
        }
        return weatherInfoBean;
    }

    /**
     * 获取星期
     * 
     * @param dt
     * @param offset
     * @return
     */
    private String getWeekOfDate(int offset)
    {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + offset);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }
}
