/**
 * @公司名称：YUTONG
 * @文件名：StationDriverDataBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:01:30
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.linestation;

import java.util.List;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.VehicleDriver;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.VehicheDriverDataReq;
import com.yutong.clw.ygbclient.connect.http.linestation.VehicheDriverDataRes;
import com.yutong.clw.ygbclient.dao.linestation.LineInfoDao;
import com.yutong.clw.ygbclient.dao.other.DriverInfoDao;

/**
 * 获取车辆司机联系方式信息
 * 
 * @author houjunhu 2014-07-08 上午9:38:30
 */
public class VehicheDriverDataBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    /**
     * 车辆vin号集合
     */
    private List<String> vehicle_vins;

    private List<String> line_ids;
    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public VehicheDriverDataBiz(Context context, List<String> vehicle_vins, List<String> line_ids)
    {
        this.context = context;
        this.vehicle_vins = vehicle_vins;
        this.line_ids = line_ids;
        
        logTypeName = "[获取车辆实时数据业务逻辑类]:";
        setActionURI(ActionURI.GET_VEHICHE_DRIVER_DATA);
    }

    public List<VehicleDriver> getDriverData() throws CommonException{
    	
        VehicheDriverDataReq req = buildReq();
        HttpRes httpRes = getHttpRes(req);
        if (httpRes.isSuccess()){
        	
            VehicheDriverDataRes res = new VehicheDriverDataRes();
            res.parse(httpRes.getContent());
            if (!res.isError()){
                Logger.i(this.getClass(), logTypeName + "成功");
                
                /*缓存本地*/
                if (httpRes.needCached()){
                	Logger.i(this.getClass(), logTypeName + "从网络返回最新数据，并缓存到本地");
                	CahceDataManager.getInstance(context).putCacheConfig(actionURI, cacheKey, httpRes.getETag());
                	
                	/*new DriverInfoDao(context).insertLineInfo(res.getDriverInfos(), filter, ProxyManager.getInstance(context).getUserCode());*/
                	
                	new DriverInfoDao(context).addDriverInfo(res.getDriverInfos().get(0));
                }
                return res.getDriverInfos();
            }else{
                Logger.e(this.getClass(), logTypeName + "失败");
                throw new CommonException(CommonNetStatus.Error_Info, res.getErrorCode(), res.getErrorDes());
            }
        }else if (httpRes.isTokenExpire()){
            Logger.e(this.getClass(), logTypeName + "Token失效");
            throw new CommonException(CommonNetStatus.Token_InValid);
        }else if (httpRes.isException()){
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Exception);
        }else{
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Not_Stable);
        }

    }

    public List<VehicleDriver> getDriverDataFromLocal() throws CommonException{
    	return new DriverInfoDao(context).queryDriverInfo(vehicle_vins);
    }
    /**
     * 组装请求对象
     * 
     * @param context
     * @return
     */
    private VehicheDriverDataReq buildReq()
    {
        VehicheDriverDataReq req = new VehicheDriverDataReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());

        req.setVehicle_vins(vehicle_vins);
        req.setLine_ids(line_ids);
        return req;
    }

}
