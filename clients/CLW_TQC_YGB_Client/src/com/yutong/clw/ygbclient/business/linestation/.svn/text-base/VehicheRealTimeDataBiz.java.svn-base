/**
 * @公司名称：YUTONG
 * @文件名：StationRealTimeDataBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:01:30
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.linestation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.VehicheRealTimeDataReq;
import com.yutong.clw.ygbclient.connect.http.linestation.VehicheRealTimeDataRes;

/**
 * 获取车辆实时数据业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午10:01:30
 */
public class VehicheRealTimeDataBiz extends AbstractDataControl
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
    public VehicheRealTimeDataBiz(Context context, List<String> vehicle_vins, List<String> line_ids)
    {
        this.context = context;
        this.vehicle_vins = vehicle_vins;
        this.line_ids = line_ids;
        
        logTypeName = "[获取车辆实时数据业务逻辑类]:";
        setActionURI(ActionURI.GET_VEHICHE_REALTIME_DATA);
    }

    public List<VehicleRealtime> getRealTimeData() throws CommonException
    {
        VehicheRealTimeDataReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            VehicheRealTimeDataRes res = new VehicheRealTimeDataRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
  
                //zz test 2014-01-13 10:18
//                   List<VehicleRealtime> list = new ArrayList<VehicleRealtime>();    
//                   VehicleRealtime data = new VehicleRealtime();
//                   
//                   /** 车辆vin */
//                   //data.vehicle_vin;
//                   /** 车辆班号 */
//                   data.vehicle_number = "3";
//                   /** 经度 */
//                   data.gps_lon = 113.638079;
//                   /** 纬度 */
//                   data.gps_lat = 34.717343;
//
//                   /** 方向角度， 0-360 （度） */
//                   data.direction = 50;
//                   
//                   data.status = "000111";
//                   list.add(data);
//                   
//                   return list;
                return res.getRealTimeInfos();
            }
            else
            {
                Logger.e(this.getClass(), logTypeName + "失败");
                throw new CommonException(CommonNetStatus.Error_Info, res.getErrorCode(), res.getErrorDes());

            }
        }
        else if (httpRes.isTokenExpire())
        {
            Logger.e(this.getClass(), logTypeName + "Token失效");
            throw new CommonException(CommonNetStatus.Token_InValid);
        }
        else if (httpRes.isException())
        {
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Exception);
        }
        else
        {
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Not_Stable);
        }

    }

    /**
     * 组装请求对象
     * 
     * @param context
     * @return
     */
    private VehicheRealTimeDataReq buildReq()
    {
        VehicheRealTimeDataReq req = new VehicheRealTimeDataReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());

        req.setVehicle_vins(vehicle_vins);
        req.setLine_ids(line_ids);
        return req;
    }

}
