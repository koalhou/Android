package com.yutong.axxc.parents.business.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.LineInfoBean;
import com.yutong.axxc.parents.common.beans.NewsInfoBean;
import com.yutong.axxc.parents.common.beans.StationInfoBean;
import com.yutong.axxc.parents.common.beans.StationRemindInfoBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.constant.NewsTypeConstant;
import com.yutong.axxc.parents.connect.http.packet.GetSationInfoRes;
import com.yutong.axxc.parents.connect.http.packet.GetSationRemindInfoRes;
import com.yutong.axxc.parents.connect.http.packet.GetStudentInfoRes;
import com.yutong.axxc.parents.connect.http.packet.GetStudentLineInfoRes;
import com.yutong.axxc.parents.dao.beans.DaoNewsInfoBean;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.news.NewsInfoDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 缓存类
 * 
 * @author zhangzhia 2013-9-12 下午3:24:00
 */
public class CacheUtils
{

    /**
     * 学生信息获取
     * 
     * @author zhangzhia 2013-9-12 下午3:23:10
     * @return
     */
    public static List<StudentInfoBean> getStudentInfo()
    {
        String logTypeName = "[获取学生信息缓存类]:";

        String belongCache = CahceSettingsDao.STUDENT_INFO_LIST_ETAG;
        String exkey = YtApplication.getInstance().getUid();

        String jsonString = CahceSettingsDao.getCacheInfo(belongCache, exkey);

        if (StringUtils.isNotBlank(jsonString))
        {
            Logger.i(CacheUtils.class, logTypeName + "读取本读缓存数据");
            GetStudentInfoRes res = new GetStudentInfoRes();
            res.parse(jsonString);
            return res.getStudentInfoBeans();
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 学生路线信息获取
     * 
     * @author zhangzhia 2013-9-12 下午3:23:10
     * @return
     */
    public static LineInfoBean getLineInfo(String cld_id, String line_type)
    {
        String logTypeName = "[获取学生路线信息缓存类]:";

        String belongCache = CahceSettingsDao.STUDENT_LINE_INFO_ETAG;
        String exkey = cld_id + line_type;
     
        

        String jsonString = CahceSettingsDao.getCacheInfo(belongCache, exkey);

        if (StringUtils.isNotBlank(jsonString))
        {
            Logger.i(CacheUtils.class, logTypeName + "读取本读缓存数据");
            GetStudentLineInfoRes res = new GetStudentLineInfoRes();
            res.parse(jsonString);
            return res.getLineInfoBean();
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 学生站点信息获取
     * 
     * @author zhangzhia 2013-9-12 下午3:23:10
     * @return
     */
    public static List<StationInfoBean> getStationInfo(String cld_id, String line_type)
    {
        String logTypeName = "[获取学生站点信息缓存类]:";

        String belongCache = CahceSettingsDao.STUDENT_STATION_INFO_ETAG;
        String exkey = cld_id + line_type;
        
        String jsonString = CahceSettingsDao.getCacheInfo(belongCache, exkey);

        if (StringUtils.isNotBlank(jsonString))
        {
            Logger.i(CacheUtils.class, logTypeName + "读取本读缓存数据");
            GetSationInfoRes res = new GetSationInfoRes();
            res.parse(jsonString);
            return res.getStationInfoBeans();
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 学生站点提醒信息获取
     * 
     * @author zhangzhia 2013-9-12 下午3:23:10
     * @return
     */
    public static List<StationRemindInfoBean> getStationReminInfo(String cld_id, String line_type)
    {
        String logTypeName = "[获取学生站点提醒信息缓存类]:";

        String belongCache = CahceSettingsDao.STATION_REMIND_INFO_ETAG;
        String exkey = cld_id + line_type;
     
        

        String jsonString = CahceSettingsDao.getCacheInfo(belongCache, exkey);

        if (StringUtils.isNotBlank(jsonString))
        {
            Logger.i(CacheUtils.class, logTypeName + "读取本读缓存数据");
            GetSationRemindInfoRes res = new GetSationRemindInfoRes();
            res.parse(jsonString);
            return res.getStationRemindInfoBeans();
        }
        else
        {
            return null;
        }
    }
    

        /**
         * 获取新闻
         */
        public static List<NewsInfoBean> getNews(Context context)
        {
            List<DaoNewsInfoBean> daoNewsInfoBeans = null;
            NewsInfoDao newsInfoDao = new NewsInfoDao(context);

            daoNewsInfoBeans = newsInfoDao.getNewsInfos(YtApplication.getInstance().getUid(), String.valueOf(NewsTypeConstant.ALL_TYPE));

            return buildInfo(daoNewsInfoBeans);
        }
        
        /**
         * 组装新闻信息对象
         * 
         * @param daoNewsInfoBeans
         * @return
         */
        private static List<NewsInfoBean> buildInfo(List<DaoNewsInfoBean> daoNewsInfoBeans)
        {
            List<NewsInfoBean> newsInfoBeans = new ArrayList<NewsInfoBean>();
            if (daoNewsInfoBeans != null)
            {
                for (DaoNewsInfoBean daoNewsInfoBean : daoNewsInfoBeans)
                {
                    NewsInfoBean newsInfoBean = new NewsInfoBean();
                    newsInfoBean.setNews_id(daoNewsInfoBean.getNews_id());

                    newsInfoBean.setNews_title(daoNewsInfoBean.getNews_title());
                    newsInfoBean.setNews_summary(daoNewsInfoBean.getNews_summary());
                    newsInfoBean.setNews_author(daoNewsInfoBean.getNews_author());
                    newsInfoBean.setNews_image(daoNewsInfoBean.getNews_image());
                    newsInfoBean.setNews_content(daoNewsInfoBean.getNews_content());
                    newsInfoBean.setNews_time(daoNewsInfoBean.getNews_time());
                    newsInfoBean.setNews_type(daoNewsInfoBean.getNews_type());
                    newsInfoBean.setIs_read(daoNewsInfoBean.getIs_read());

                    newsInfoBeans.add(newsInfoBean);
                }
            }
            return newsInfoBeans;
        }

}
