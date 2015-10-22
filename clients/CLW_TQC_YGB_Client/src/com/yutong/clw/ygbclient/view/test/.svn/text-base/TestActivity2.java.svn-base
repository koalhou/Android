package com.yutong.clw.ygbclient.view.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.SystemUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.GetNetResourceBiz;
import com.yutong.clw.ygbclient.business.login.AutoLoginBiz;
import com.yutong.clw.ygbclient.business.login.BindPhoneBiz;
import com.yutong.clw.ygbclient.business.login.ForgotPasswordBiz;
import com.yutong.clw.ygbclient.business.login.LoginBiz;
import com.yutong.clw.ygbclient.business.login.LogoutBiz;
import com.yutong.clw.ygbclient.business.login.VerifyAccountBiz;
import com.yutong.clw.ygbclient.business.linestation.GetAreaLinesBiz;
import com.yutong.clw.ygbclient.business.linestation.GetIdsLinesBiz;
import com.yutong.clw.ygbclient.business.linestation.GetRemindStationsBiz;
import com.yutong.clw.ygbclient.business.linestation.GetStationBiz;
import com.yutong.clw.ygbclient.business.linestation.GetStationFavoritesBiz;
import com.yutong.clw.ygbclient.business.linestation.GetVehichesBiz;
import com.yutong.clw.ygbclient.business.linestation.RecommendStationsBiz;
import com.yutong.clw.ygbclient.business.linestation.SetRemindStationsBiz;
import com.yutong.clw.ygbclient.business.linestation.SetStationFavoritesBiz;
import com.yutong.clw.ygbclient.business.linestation.StationAreasBiz;
import com.yutong.clw.ygbclient.business.linestation.StationRealTimeDataBiz;
import com.yutong.clw.ygbclient.business.linestation.VehicheRealTimeDataBiz;
import com.yutong.clw.ygbclient.business.login.LoginBiz;
import com.yutong.clw.ygbclient.business.other.SendPhoneSMSBiz;
import com.yutong.clw.ygbclient.common.Router.ResRouterItem;
import com.yutong.clw.ygbclient.common.beans.CollectionStation;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.beans.StationAreaInfo;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.beans.VehicheInfo;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.common.beans.VerifyAccountInfo;
import com.yutong.clw.ygbclient.common.beans.line.CoordPoint;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.beans.line.StationVehicleRealTimeInfo;
import com.yutong.clw.ygbclient.common.beans.news.NewsInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.ResourceType;
import com.yutong.clw.ygbclient.common.enums.SMSVerifyType;
import com.yutong.clw.ygbclient.common.enums.StationType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.news.HasNextStatus;
import com.yutong.clw.ygbclient.common.enums.news.NewsType;
import com.yutong.clw.ygbclient.common.enums.other.FilterEnum;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindType;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.BusinessUtils;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.common.utils.EncryptUtils;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.dao.Router.ResRouterDao;
import com.yutong.clw.ygbclient.dao.common.tables.RemindStationInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.ResRouterTable;
import com.yutong.clw.ygbclient.dao.linestation.LineInfoDao;
import com.yutong.clw.ygbclient.dao.linestation.RemindStationInfoDao;
import com.yutong.clw.ygbclient.dao.login.LoginInfoDao;
import com.yutong.clw.ygbclient.dao.setting.NewsInfoDao;

public class TestActivity2 extends Activity
{

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        /*iv = (ImageView) findViewById(R.id.iv);*/

        // iv = (ImageView) findViewById(R.id.iv);

        // ((Button) findViewById(R.id.btn_at_test)).setOnClickListener(new
        // OnClickListener()
        // {
        //
        // @Override
        // public void onClick(View arg0)
        // {
        // // TODO Auto-generated method stub
        //
        // try
        // {
        // new Thread()
        // {
        // @Override
        // public void run()
        // {
        // // TODO Auto-generated method stub
        // super.run();
        //
        // // LoginBiz loginBiz = new
        // // LoginBiz(YtApplication.getInstance(), "zz",
        // // "123");
        // // new
        // // GetNetResourceBiz(YtApplication.getInstance())
        // //
        // .getImage("http://www.yutong.com/images/news/yutongnews/2013/10/28/BDC8D5453EDB992BC0B375DF73D8A001.jpg");
        // // if(loginBiz.IsCacheTimeOut())
        // // {
        // //
        // // }
        // // loginBiz.login();
        // // 登录DAO测试
        // LoginInfoDao dao = new LoginInfoDao(YtApplication.getInstance());
        // UserInfo loginInfoBean = new UserInfo();
        // /** 用户ID */
        // loginInfoBean.Id = "22234234";
        // /** 员工号 */
        // /** 员工所在厂区 1:宇通工业园，2:：新能源工厂 */
        // loginInfoBean.BelongArea = AreaType.SecondFactory;
        //
        // String str = loginInfoBean.BelongArea.toString();
        // /** 是否未修改默认密码，0：否，1：是 */
        // loginInfoBean.DefaultPassword = false;
        //
        // dao.addLoginInfo(loginInfoBean);
        //
        // UserInfo user = dao.getLoginInfo();
        // // 线路Dao测试
        // LineInfoDao lineInfoDao = new LineInfoDao(TestActivity2.this);
        // LineInfo lineInfo = new LineInfo();
        // lineInfo.setArea_type(AreaType.FirstFactory);
        // lineInfo.setLine_id("0002");
        // lineInfo.setLine_name("二号线");
        // lineInfo.setLine_range(LineRange.FactoryInside);
        // lineInfo.setStatus_range(StatusRange.MorningWork);
        // List<CoordPoint> points = new ArrayList<CoordPoint>();
        // for (int i = 0; i < 9; i++)
        // {
        // CoordPoint coordPoint = new CoordPoint(809854, 3453454);
        // points.add(coordPoint);
        //
        // }
        // lineInfo.setPoints(points);
        // List<StationInfo> stations = new ArrayList<StationInfo>();
        // for (int i = 0; i < 9; i++)
        // {
        // StationInfo bean = new StationInfo();
        // bean.setId(UUID.randomUUID().toString());
        // bean.setBelong_area_id("1");
        // bean.setName("技术南楼");
        // bean.setAlias("起点");
        // bean.setGps_lat(888888.01);
        // bean.setGps_lon(9999.001);
        // bean.setType(StationType.StartStaion);
        // bean.setStatus(true);
        // bean.setPlan_arrive_time(DateUtils.getCurDateString(DateUtils.LONG_TIME_FORMAT));
        // bean.setFavorites(true);
        // bean.setLine_range(LineRange.FactoryInside);
        // bean.setStatus_range(StatusRange.MorningWork);
        // bean.setArea_type(AreaType.FirstFactory);
        // bean.setLine_id(UUID.randomUUID().toString());
        // stations.add(bean);
        //
        // }
        // lineInfo.setStations(stations);
        // List<LineInfo> infos = new ArrayList<LineInfo>();
        // infos.add(lineInfo);
        // lineInfoDao.insertLineInfo(infos, null);
        // List<String> temp = new ArrayList<String>();
        // temp.add("0002");
        // List<LineInfo> infoFromIds = lineInfoDao.getInfoFromIds(temp);
        // List<String> temp2 = new ArrayList<String>();
        // temp2.add("11002100");
        // List<LineInfo> infoFromFilter = lineInfoDao.getInfoFromFilter(temp2);
        // lineInfoDao.delete();
        // // 站点提醒DAO测试
        // RemindStationInfoDao remindStationInfoDao = new
        // RemindStationInfoDao(getApplicationContext());
        // RemindInfo daoLoginInfoBean = new RemindInfo();
        //
        // daoLoginInfoBean.setId("003");
        // daoLoginInfoBean.setArea_type(AreaType.FirstFactory);
        // daoLoginInfoBean.setArea_name("北区");
        //
        // daoLoginInfoBean.setLine_range(LineRange.FactoryInside);
        // daoLoginInfoBean.setStation_id("001");
        // daoLoginInfoBean.setStation_name("淋雨房");
        // daoLoginInfoBean.setStatus_range(StatusRange.MorningWork);
        // daoLoginInfoBean.setRemind_range(RemindRange.OnlyStation);
        // daoLoginInfoBean.setRemind_type(RemindType.Date);
        // daoLoginInfoBean.setRemind_value(10);
        // daoLoginInfoBean.setRemind_week("1111100");
        // daoLoginInfoBean.setRemind_status(true);
        // daoLoginInfoBean.setNo_remind_date(DateUtils.getCurDate());
        // remindStationInfoDao.updateRemindInfo(daoLoginInfoBean,"newUser");
        // List<RemindInfo> remindInfos =
        // remindStationInfoDao.getRemindInfos("newUser");
        // // dao.addLoginInfo(loginInfoBean);
        // // 新闻dao测试
        // // NewsInfoDao newsInfoDao = new
        // // NewsInfoDao(getApplicationContext());
        // // NewsInfo info = new NewsInfo();
        // // info.author = "我";
        // // info.content = "测试新闻测试新闻测试新闻测试新闻测试新闻测试新闻";
        // // info.hasnext = HasNextStatus.HasData;
        // // info.id = "001";
        // // List<String> resinfos = new ArrayList<String>();
        // // for (int i = 0; i < 2; i++)
        // // {
        // // resinfos.add(UUID.randomUUID().toString());
        // // }
        // // List<String> urlinfos = new ArrayList<String>();
        // // for (int i = 0; i < 2; i++)
        // // {
        // // urlinfos.add(UUID.randomUUID().toString());
        // // }
        // // info.image_res = resinfos;
        // // info.image_url = urlinfos;
        // // info.is_read = false;
        // // info.publish_time = DateUtils.getCurDate();
        // // info.summary = "新闻摘要";
        // // info.timestamp = DateUtils.getCurDate();
        // // info.title = "宇通新闻";
        // // info.totalnum = 10;
        // // info.type = NewsType.All;
        // // newsInfoDao.addNewsInfos(info, "anxin");
        // // NewsInfo newsInfo =
        // // newsInfoDao.getNewsInfo("001", "anxin");
        // // String id = newsInfo.id;
        // // boolean exist = newsInfoDao.isExist("001",
        // // "anxin");
        // // 资源路由DAO
        // // ResRouterDao resRouterDao = new
        // // ResRouterDao(getApplicationContext());
        // // ResRouterItem daoLoginInfoBean = new
        // // ResRouterItem("", null, "");
        // //
        // // daoLoginInfoBean.setResKey("url");
        // // daoLoginInfoBean.setResType(ResourceType.NetURL);
        // // daoLoginInfoBean.setName("资源");
        // //
        // // daoLoginInfoBean.setSize("1024");
        // // daoLoginInfoBean.setPath("/sdcard/");
        // // daoLoginInfoBean.setSuffix(".jpg");
        // // resRouterDao.save("url", daoLoginInfoBean);
        // // ResRouterItem resRouterItem =
        // // resRouterDao.get("url");
        // // boolean containsKey =
        // // resRouterDao.containsKey("url");
        // // resRouterDao.delete();
        // }
        // }.start();
        //
        // }
        // catch (Exception e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // }
        // });
        // }

        ((Button) findViewById(R.id.btn_at_test)).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                // TODO Auto-generated method stub

                try
                {
                    new Thread()
                    {

                        @Override
                        public void run()
                        {
                            // TODO Auto-generated method stub
                            super.run();
                            // 90077645 A11111111111111111111111111111111
                            LoginBiz loginBiz = new LoginBiz(YtApplication.getInstance(), "lizy", EncryptUtils.getMD5Str("123"));
                            // LoginBiz loginBiz = new
                            // LoginBiz(YtApplication.getInstance(), "90077645",
                            // "202CB962AC59075B964B07152D234B70");
                            // new
                            // GetNetResourceBiz(YtApplication.getInstance())
                            // .getImage("http://www.yutong.com/images/news/yutongnews/2013/10/28/BDC8D5453EDB992BC0B375DF73D8A001.jpg");
                            if (loginBiz.IsCacheExpires())
                                // new
                                // GetNetResourceBiz(YtApplication.getInstance())
                                // .getImage("http://www.yutong.com/images/news/yutongnews/2013/10/28/BDC8D5453EDB992BC0B375DF73D8A001.jpg");
                                if (loginBiz.IsCacheExpires())
                                {

                                }
                            try
                            {
                                // ProxyManager.getInstance(getApplicationContext()).setAccessToken("f222197cfb7339ed");
                                // 1.4.2 登录接口
                                // 返回的数据中default_password数据有问题，其他ok
                                UserInfo login = loginBiz.login();
                                // 1.4.6 自动登录接口
                                // 请求成功，有数据返回，但是用户信息字段usr_info应该为emp_info
                                AutoLoginBiz autoLoginBiz = new AutoLoginBiz(getApplicationContext());
                                autoLoginBiz.autoLogin();
                                // 退出登录接口
                                LogoutBiz logoutBiz = new LogoutBiz(getApplicationContext());
                                // logoutBiz.logout();
                                // 1.4.1 手机绑定接口
                                // 更换手机号服务器报内部错误
                                // 手机号正确的话提示已经绑定
                                // 未验证手机号没有绑定的情况
                                BindPhoneBiz bindPhoneBiz = new
                                // BindPhoneBiz(getApplicationContext(),
                                // "15188394566");
                                // BindPhoneBiz bindPhoneBiz = new
                                BindPhoneBiz(getApplicationContext(), "18686868686");
                                // boolean bindPhine = bindPhoneBiz.bindPhine();

                                // 1.4.3 验证账号接口
                                // 员工号(构造方法中传入账户类型是否务必要)
                                VerifyAccountBiz verifyAccountBiz = new VerifyAccountBiz(getApplicationContext(), "123");
                                // 手机号码

                                // VerifyAccountBiz verifyAccountBiz = new
                                // VerifyAccountBiz(getApplicationContext(),
                                // "18686868686");

                                // VerifyAccountInfo verifyAccount =
                                // verifyAccountBiz.verifyAccount();
                                // 1.4.4 找回密码接口
                                // 返回200，提示修改成功，但是重新登录的时候新密码无法登陆，更换为原密码可以登录
                                ForgotPasswordBiz forgotPasswordBiz = new ForgotPasswordBiz(getApplicationContext(), "90077777",
                                        EncryptUtils.getMD5Str("456"));

                                // boolean modifyPassword =
                                // forgotPasswordBiz.modifyPassword();

                            }
                            catch (Exception e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            List<String> filter = new ArrayList<String>();
                            filter.add("1100");
                            filter.add("2100");
                            // 获取厂区范围路线测试
                            try
                            {

                                List<String> convertToList = BusinessUtils.convertToList(FilterEnum.Value11002100);
                                List<LineInfo> linesFromSever = new GetAreaLinesBiz(getApplicationContext(), convertToList)
                                        .getLinesFromSever();
                            }
                            catch (Exception e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            // 获取编号集合的线路信息TEST
                            List<String> resinfos = new ArrayList<String>();
                            for (int i = 0; i < 2; i++)
                            {

                            }
                            resinfos.add("4371");
                            GetIdsLinesBiz idsLineBiz = new GetIdsLinesBiz(YtApplication.getInstance(), resinfos);
                            try
                            {
                                List<LineInfo> lines = idsLineBiz.getLines();
                            }
                            catch (Exception e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            // 1.6.3 获取推荐站点列表信息接口
                            RecommendStationsBiz recommendStationsBiz = new RecommendStationsBiz(getApplicationContext(),
                                    AreaType.FirstFactory);
                            try
                            {
                                // List<StationInfo> stationInfosFromSever =
                                // recommendStationsBiz.getStationInfosFromSever();
                            }
                            catch (Exception e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            // 1.6.4 获取站点列表信息接口
                            GetStationBiz getStationBiz = new GetStationBiz(getApplicationContext());
                            try
                            {
                                // List<StationInfo> stationInfosFromSever =
                                // getStationBiz.getStationInfosFromSever();
                            }
                            catch (Exception e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            // 1.6.5 获取车辆信息接口
                            GetVehichesBiz getVehichesBiz = new GetVehichesBiz(getApplicationContext(), DateUtils.getCurDate(),
                                    Arrays.asList(""));
                            try
                            {
                                List<VehicheInfo> vehichesFromSever = getVehichesBiz.getVehichesFromSever();
                            }
                            catch (Exception e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            // 1.6.6 获取站点区域信息接口
                            StationAreasBiz stationAreasBiz = new StationAreasBiz(getApplicationContext());
                            try
                            {
                                // List<StationAreaInfo> stationAreasFromSever =
                                // stationAreasBiz.getStationAreasFromSever();
                            }
                            catch (Exception e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            // 1.6.7 获取站点提醒设置接口
                            // GetRemindStationsBiz getRemindStationsBiz = new
                            // GetRemindStationsBiz(getApplicationContext());
                            try
                            {
                                // List<RemindInfo> remindsFromSever =
                                // getRemindStationsBiz.getRemindsFromSever();
                            }
                            catch (Exception e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            // 1.6.8 设置提醒站点信息接口
                            RemindInfo info = new RemindInfo();

                            //new SetRemindStationsBiz(getApplicationContext(), info);
                            // 1.6.9 获取已收藏站点接口
                            GetStationFavoritesBiz getStationFavoritesBiz = new GetStationFavoritesBiz(getApplicationContext(),
                                    AreaType.FirstFactory);
                            try
                            {
                                // List<StationInfo> stationAreasFromSever =
                                // getStationFavoritesBiz.getStationFavoritesFromSever();
                            }
                            catch (Exception e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            // 1.6.10 设置收藏站点接口

                            List<CollectionStation> stations = new ArrayList<CollectionStation>();
                            CollectionStation collectionStation = new CollectionStation();
                            collectionStation.setStation_id("3636");
                            collectionStation.setFavorites(true);
                            stations.add(collectionStation);
//                            SetStationFavoritesBiz setStationFavoritesBiz = new SetStationFavoritesBiz(getApplicationContext(), stations);
//                            try
//                            {
//                                // setStationFavoritesBiz.setStatinFavorites();
//                            }
//                            catch (Exception e1)
//                            {
//                                // TODO Auto-generated catch block
//                                e1.printStackTrace();
//                            }
                            // 1.6.11 获取站点与车辆相对实时数据接口
                            List<String> ids = new ArrayList<String>();
                            ids.add("3759");
                            /*StationRealTimeDataBiz stationRealTimeDataBiz = new StationRealTimeDataBiz(getApplicationContext(), DateUtils
                                    .getCurDate(), ids, StatusRange.MorningWork);*/
                            try
                            {
                                // List<StationVehicleRealTimeInfo> realTimeData
                                // = stationRealTimeDataBiz.getRealTimeData();
                            }
                            catch (Exception e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            // 1.6.12 获取车辆实时信息接口
                            List<String> vins = new ArrayList<String>();
                            vins.add("VIN11111111111111");
                            vins.add("12345678912345678");
//                            VehicheRealTimeDataBiz vehicheRealTimeDataBiz = new VehicheRealTimeDataBiz(getApplicationContext(), vins);
//                            try
//                            {
//                                List<VehicleRealtime> realTimeData = vehicheRealTimeDataBiz.getRealTimeData();
//                            }
//                            catch (Exception e2)
//                            {
//                                // TODO Auto-generated catch block
//                                e2.printStackTrace();
//                            }
                            // 1.8.1 手机短信验证接口
                            // SendPhoneSMSBiz sendPhoneSMSBiz = new
                            // SendPhoneSMSBiz(getApplicationContext(),
                            // "15188394566", "90078888",
                            // SMSVerifyType.CHANGEBINDPHONE);
                            try
                            {
                                // sendPhoneSMSBiz.sendSMS();
                            }
                            catch (Exception e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            try
                            {
                                // List<VehicleRealtime> realTimeData =
                                // vehicheRealTimeDataBiz.getRealTimeData();
                                // 缓存测试
//                                new GetNetResourceBiz(TestActivity2.this).setImage(
//                                        "http://www.yutong.com/images/news/yutongnews/2013/10/28/BDC8D5453EDB992BC0B375DF73D8A001.jpg", iv);
                            }
                            catch (Exception e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }.start();

                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
