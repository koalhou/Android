/**
 * @公司名称：YUTONG
 * @作者：zhangyongn
 * @版本号：1.0
 * @生成日期：2013-10-30 下午2:18:40
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bizAccess.splash;

import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.linestation.GetAreaLinesBiz;
import com.yutong.clw.ygbclient.business.linestation.GetStationBiz;
import com.yutong.clw.ygbclient.business.linestation.RecommendStationsBiz;
import com.yutong.clw.ygbclient.business.linestation.StationAreasBiz;
import com.yutong.clw.ygbclient.business.login.AutoLoginBiz;
import com.yutong.clw.ygbclient.business.setting.GetPushMsgRulesBiz;
import com.yutong.clw.ygbclient.business.setting.VersionChkBiz;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.beans.VersionInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.enums.other.FilterEnum;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.BusinessUtils;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * @author zhangyongn 2013-10-30 下午2:18:40
 */
public class BizSplash extends BizBase
{
    private AutoLoginBiz autoLoginBiz;

    private String logTypeName = "[初始化业务数据]：";

    public BizSplash(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public BizSplash(Context context, int threadcount)
    {
        super(context, threadcount);
        // TODO Auto-generated constructor stub
    }

    /**
     * 闪屏初始化，闪屏业务逻辑由此开始
     * 
     * @author zhangyongn 2013-10-30 下午2:25:34
     * @param process
     */
    public void splashInit(BizResultProcess<String> process)
    {

        Callable<String> submitcallable = new Callable<String>()
        {
            @Override
            public String call() throws CommonException
            {
                // #region 加载站点区域信息
                try
                {
                    Logger.w(this.getClass(), logTypeName + "开始加载站点区域信息......");
                    StationAreasBiz biz = new StationAreasBiz(getmContext());

                    biz.getStationAreasFromSever();
                }
                catch (CommonException ce)
                {
                    if (ce.status == CommonNetStatus.NetWork_Exception)
                    {
                        return "";
                    }
                    else if (ce.status == CommonNetStatus.NetWork_Not_Stable)
                    {
                        return "";
                    }

                    Logger.e(this.getClass(), logTypeName + "加载站点区域信息失败", ce);
                }

                // #endregion

                // #region 加载推送设置信息
                try
                {
                    Logger.w(this.getClass(), logTypeName + "开始加载推送设置信息......");
                    GetPushMsgRulesBiz biz = new GetPushMsgRulesBiz(getmContext());

                    biz.getRulesFromServer();
                }
                catch (CommonException ce)
                {
                    if (ce.status == CommonNetStatus.NetWork_Exception)
                    {
                        return "";
                    }
                    else if (ce.status == CommonNetStatus.NetWork_Not_Stable)
                    {
                        return "";
                    }

                    Logger.e(this.getClass(), logTypeName + "加载推送设置信息失败", ce);
                }

                // #endregion

                // #region 加载推荐站点信息
                try
                {
                    Logger.w(this.getClass(), logTypeName + "开始加载[" + AreaType.FirstFactory.getName() + "]推荐站点信息......");
                    RecommendStationsBiz biz = new RecommendStationsBiz(getmContext(), AreaType.FirstFactory);

                    biz.getStationInfosFromSever();

                    Logger.w(this.getClass(), logTypeName + "开始加载[" + AreaType.SecondFactory.getName() + "]推荐站点信息......");
                    biz = new RecommendStationsBiz(getmContext(), AreaType.SecondFactory);

                    biz.getStationInfosFromSever();

                }
                catch (CommonException ce)
                {
                    if (ce.status == CommonNetStatus.NetWork_Exception)
                    {
                        return "";
                    }
                    else if (ce.status == CommonNetStatus.NetWork_Not_Stable)
                    {
                        return "";
                    }

                    Logger.e(this.getClass(), logTypeName + "加载推荐站点信息失败", ce);
                }

                // #endregion

                // #region 加载站点列表信息
                try
                {
                    Logger.w(this.getClass(), logTypeName + "开始加载站点列表信息......");
                    GetStationBiz biz = new GetStationBiz(getmContext());

                    biz.getStationInfosFromSever();

                }
                catch (CommonException ce)
                {
                    if (ce.status == CommonNetStatus.NetWork_Exception)
                    {
                        return "";
                    }
                    else if (ce.status == CommonNetStatus.NetWork_Not_Stable)
                    {
                        return "";
                    }

                    Logger.e(this.getClass(), logTypeName + "加载站点列表信息失败", ce);
                }

                // #endregion

                // #region 加载区域线路信息
                try
                {
                    FilterEnum filter = FilterEnum.Value11002100;
                    Logger.w(this.getClass(), logTypeName + "开始加载区域[" + filter.getName() + "]线路信息......");
                    GetAreaLinesBiz biz = new GetAreaLinesBiz(getmContext(), BusinessUtils.convertToList(filter));

                    biz.getLinesFromSever();

                    filter = FilterEnum.Value10012001;
                    Logger.w(this.getClass(), logTypeName + "开始加载区域[" + filter.getName() + "]线路信息......");
                    biz = new GetAreaLinesBiz(getmContext(), BusinessUtils.convertToList(filter));

                    biz.getLinesFromSever();

                }
                catch (CommonException ce)
                {
                    if (ce.status == CommonNetStatus.NetWork_Exception)
                    {
                        return "";
                    }
                    else if (ce.status == CommonNetStatus.NetWork_Not_Stable)
                    {
                        return "";
                    }

                    Logger.e(this.getClass(), logTypeName + "加载线路信息失败", ce);
                }

                // #endregion

                return null;
            }
        };

        BizNetWork(submitcallable, process);

    }

    // /**
    // * 加载站点基础数据
    // *
    // * @author zhangyongn 2013-11-26 下午2:57:45
    // * @param process
    // * @return
    // */
    // public BizDataTypeEnum getStations(BizResultProcess<List<StationInfo>>
    // process)
    // {
    // final GetStationBiz getStationBiz = new
    // GetStationBiz(YtApplication.getInstance());
    //
    // Callable<List<StationInfo>> LocalCallable = new
    // Callable<List<StationInfo>>()
    // {
    //
    // @Override
    // public List<StationInfo> call() throws Exception
    // {
    // List<StationInfo> stationInfosFromLocal =
    // getStationBiz.getStationInfosFromLocal();
    // return stationInfosFromLocal;
    // }
    // };
    //
    // Callable<List<StationInfo>> networkCallable = new
    // Callable<List<StationInfo>>()
    // {
    //
    // @Override
    // public List<StationInfo> call() throws Exception
    // {
    // List<StationInfo> stationInfosFromSever =
    // getStationBiz.getStationInfosFromSever();
    // return stationInfosFromSever;
    // }
    //
    // };
    //
    // return BizCommonWork(!getStationBiz.IsCacheExpires(), LocalCallable,
    // networkCallable, process);
    // }

    /**
     * 检查是否有更新
     * 
     * @author zhangyongn 2013-10-30 下午2:20:37
     * @param process
     */
    public void CheckUpdate(BizResultProcess<VersionInfo> process)
    {
        final VersionChkBiz biz = new VersionChkBiz(getmContext());
        Callable<VersionInfo> submitCallable = new Callable<VersionInfo>()
        {
            @Override
            public VersionInfo call() throws Exception
            {
                return biz.versionChk();
            }
        };
        BizNetWork(submitCallable, process);

    }

    /**
     * 自动登录
     * 
     * @author zhangyongn 2013-10-30 下午2:20:37
     * @param process
     */
    public void AutoLogin(BizResultProcess<UserInfo> process)
    {
        Callable<UserInfo> submitcallable = new Callable<UserInfo>()
        {
            @Override
            public UserInfo call() throws Exception
            {
                return autoLoginBiz.autoLogin();
            }
        };
        BizNetWork(submitcallable, process);

    }

    /**
     * 是否有自动登录
     * 
     * @author zhangyongn 2013-11-8 下午2:09:36
     * @return
     */
    public boolean isAutoLogin()
    {
        if (autoLoginBiz == null)
        {
            autoLoginBiz = new AutoLoginBiz(YtApplication.getInstance());
        }
        return autoLoginBiz.isAutoLogin();
    }
}
