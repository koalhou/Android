/**
 * @公司名称：YUTONG
 * @作者：zhangyongn
 * @版本号：1.0
 * @生成日期：2013-11-15 上午10:00:11
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.singleStation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.common.enums.ArriveStatus;
import com.yutong.clw.ygbclient.common.enums.ScheduleItemType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.control.BusScheduleExpListAdapter.BusScheduleItemClickListener;

/**
 * @author zhangyongn 2013-11-15 上午10:00:11
 */
public class StationScheduleAdapter extends BaseAdapter
{
    private Date curDate = DateUtils.getCurDate();
    private List<VehicleRealtime> VehicleRealtimes = new ArrayList<VehicleRealtime>();

    private StationInfo station;

    private LayoutInflater inflater;

    /**
     * 站点下的某一bus的闹钟点击侦听
     */
    private View.OnClickListener onContactDriverListener;
    
    /**
     * 站点下的某一bus的闹钟点击侦听
     */
    private View.OnClickListener siteBusClockClickListener;

    /**
     * 站点下的某一bus的地图点击侦听
     */
    private View.OnClickListener siteBusMapClickListener;

    /**
     * 站点下的某一bus信息栏点击侦听
     */
    private BusScheduleItemClickListener busScheduleItemClickListener;

    public StationScheduleAdapter()
    {
        super();
        inflater = LayoutInflater.from(YtApplication.getInstance());
    }
    public void setCurDate(Date dt)
    {
        this.curDate = dt;
        if (this.curDate == null)
            this.curDate = DateUtils.getCurDate();
    }
    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return VehicleRealtimes.size();
    }

    @Override
    public Object getItem(int arg0)
    {
        // TODO Auto-generated method stub
        return VehicleRealtimes.get(arg0);
    }

    @Override
    public long getItemId(int arg0)
    {
        // TODO Auto-generated method stub
        return arg0;
    }
    /**
     * @param siteBusClockClickListener 要设置的 siteBusClockClickListener
     */
    public void setSiteBusClockClickListener(View.OnClickListener siteBusClockClickListener)
    {
        this.siteBusClockClickListener = siteBusClockClickListener;
    }

    /**
     * @param siteBusMapClickListener 要设置的 siteBusMapClickListener
     */
    public void setSiteBusMapClickListener(View.OnClickListener siteBusMapClickListener)
    {
        this.siteBusMapClickListener = siteBusMapClickListener;
    }

    /**
     * @param busScheduleItemClickListener 要设置的 busScheduleItemClickListener
     */
    public void setBusScheduleItemClickListener(BusScheduleItemClickListener busScheduleItemClickListener)
    {
        this.busScheduleItemClickListener = busScheduleItemClickListener;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listitem_bus_schedule, null);
            holder = buildHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        
        StationInfo site = station;
        VehicleRealtime childDataItem = VehicleRealtimes.get(position);
        try{
            if (childDataItem.itemType == ScheduleItemType.Tip){
                holder.opLL.setVisibility(View.GONE);
                holder.itemRL.setVisibility(View.GONE);
                holder.tipRL.setVisibility(View.VISIBLE);
                holder.tiptextTV.setText(childDataItem.tip);
            } else{
                setChildListeners(holder, convertView, site, childDataItem);

                holder.opLL.setVisibility(childDataItem.showOP ? View.VISIBLE : View.GONE);
                holder.itemRL.setVisibility(View.VISIBLE);
                holder.tipRL.setVisibility(View.GONE);
                
                /*联系司机*/
                holder.contact_driver_RL.setTag(R.id.tag_station, site);
                holder.contact_driver_RL.setTag(R.id.tag_realtimeinfo, childDataItem);
                
                holder.opclockbtnRL.setTag(R.id.tag_station, site);
                holder.opclockbtnRL.setTag(R.id.tag_realtimeinfo, childDataItem);
                
                holder.opmapbtnRL.setTag(R.id.tag_station, site);
                holder.opmapbtnRL.setTag(R.id.tag_realtimeinfo, childDataItem);
               
                holder.itemRL.setTag(R.id.tag_childpos, position);

                holder.busNumberTV.setText(childDataItem.vehicle_number);
                holder.statusTV.setText(childDataItem.arrive_status.getName());
                holder.planTV.setText(childDataItem.status_desc);
                holder.tipTV.setVisibility(childDataItem.flag ? View.VISIBLE : View.GONE);
                holder.clocktipTV.setVisibility((childDataItem.remind_status == RemindStatus.Open) ? View.VISIBLE : View.GONE);
                holder.clocktipTV.setText(buildClockTipText(childDataItem));
                                
               //
                if(childDataItem.arrive_status == ArriveStatus.Leave || childDataItem.arrive_status == ArriveStatus.NoArrive
                	||	childDataItem.arrive_status == ArriveStatus.Arrive
                	){
                	
                	int buscolor = (childDataItem.arrive_status == ArriveStatus.Leave) ? YtApplication.getInstance().getResources()
                            .getColor(R.color.grayfont) : YtApplication.getInstance().getResources().getColor(R.color.divider_color_blue);
                    
                    holder.busNumberTV.setTextColor(buscolor);
                    
                    int statuscolor = (childDataItem.arrive_status == ArriveStatus.Leave) ? YtApplication.getInstance().getResources()
                            .getColor(R.color.grayfont) : YtApplication.getInstance().getResources().getColor(R.color.grayfont_dark);
                    holder.statusTV.setTextColor(statuscolor);
                }else{
                	
                	int buscolor = (childDataItem.arrive_status == ArriveStatus.StartOff) ? YtApplication.getInstance().getResources()
                            .getColor(R.color.grayfont) : YtApplication.getInstance().getResources().getColor(R.color.divider_color_blue);
                    
                    holder.busNumberTV.setTextColor(buscolor);
                    
                    int statuscolor = (childDataItem.arrive_status == ArriveStatus.StartOff) ? YtApplication.getInstance().getResources()
                            .getColor(R.color.grayfont) : YtApplication.getInstance().getResources().getColor(R.color.grayfont_dark);
                    holder.statusTV.setTextColor(statuscolor);
                }
                //
                
                // 设置地图显示
//                boolean istoday = DateUtils.isToday(curDate);
//                holder.opmapbtnRL.setVisibility(istoday ? View.VISIBLE : View.INVISIBLE);
                holder.opmapbtnRL.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception err)
        {
            Logger.e(getClass(), err.getMessage());
        }
        return convertView;
    }
    private CharSequence buildClockTipText(VehicleRealtime childDataItem)
    {
        try
        {
            String tip = "";
            switch (childDataItem.remind_type)
            {
            case Date:// 单位：分钟
                tip = String.format("提前%d分钟提醒", childDataItem.remind_value);
                break;
            case Distance:// 单位：米
                tip = String.format("提前%d米提醒", childDataItem.remind_value);
                break;
            case StationNum:// 单位：个
                tip = String.format("提前%d站提醒", childDataItem.remind_value);
                break;

            }
            return tip;
        }
        catch (Exception e)
        {
            return "";
        }
    }

    private void setChildListeners(Holder holder, View convertView, StationInfo site, VehicleRealtime childDataItem)
    {
        
    	holder.contact_driver_RL.setOnClickListener(null);
        holder.contact_driver_RL.setOnClickListener(this.onContactDriverListener);
         
    	holder.opclockbtnRL.setOnClickListener(null);
        holder.opclockbtnRL.setOnClickListener(this.siteBusClockClickListener);

        holder.opmapbtnRL.setOnClickListener(null);
        holder.opmapbtnRL.setOnClickListener(this.siteBusMapClickListener);

        holder.itemRL.setOnClickListener(null);
        holder.itemRL.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                
                int childpos = (Integer) v.getTag(R.id.tag_childpos);
                if (busScheduleItemClickListener != null)
                    busScheduleItemClickListener.onItemClick(-1, childpos);
            }
        });

    }
    private Holder buildHolder(View v)
    {
        Holder holder = new Holder();
        holder.tiptextTV = (TextView) v.findViewById(R.id.tiptextTV);

        holder.busNumberTV = (TextView) v.findViewById(R.id.busNumberTV);
        holder.clocktipTV = (TextView) v.findViewById(R.id.clocktipTV);
        holder.opclockbtnRL = (RelativeLayout) v.findViewById(R.id.opclockbtnRL);
        holder.opLL = (LinearLayout) v.findViewById(R.id.opLL);
        holder.opmapbtnRL = (RelativeLayout) v.findViewById(R.id.opmapbtnRL);
        holder.planTV = (TextView) v.findViewById(R.id.planTV);
        holder.statusTV = (TextView) v.findViewById(R.id.statusTV);
        holder.tipTV = (TextView) v.findViewById(R.id.tipTV);

        holder.itemRL = (RelativeLayout) v.findViewById(R.id.itemRL);
        holder.tipRL = (RelativeLayout) v.findViewById(R.id.tipRL);
        
        holder.contact_driver_RL = (RelativeLayout) v.findViewById(R.id.contact_driver_RL);
        return holder;
    }

    /**
     * @param datas 要设置的 datas
     */
    public void setDatas(List<VehicleRealtime> datas, StationInfo site)
    {
        this.VehicleRealtimes = datas;
        this.station = site;
    }

    /**
     * @return vehicleRealtimes
     */
    public List<VehicleRealtime> getVehicleRealtimes()
    {
        return VehicleRealtimes;
    }

    /**
     * @param vehicleRealtimes 要设置的 vehicleRealtimes
     */
    public void setVehicleRealtimes(List<VehicleRealtime> vehicleRealtimes)
    {
        VehicleRealtimes = vehicleRealtimes;
    }

    /**
     * @return station
     */
    public StationInfo getStation()
    {
        return station;
    }

    /**
     * @param station 要设置的 station
     */
    public void setStation(StationInfo station)
    {
        this.station = station;
    }

    static class Holder
    {
        /**
         * 提示文本，只有在提示type下用到。
         */
        TextView tiptextTV;

        /**
         * 提示信息容器
         */
        RelativeLayout tipRL;

        /**
         * 班车信息容器
         */
        RelativeLayout itemRL;

        /**
         * 操作容器
         */
        LinearLayout opLL;

        /**
         * 闹钟设置
         */
        RelativeLayout opclockbtnRL;

        /**
         * 联系司机
         */
        RelativeLayout contact_driver_RL;
        
        /**
         * 地图
         */
        RelativeLayout opmapbtnRL;

        /**
         * 班车号
         */
        TextView busNumberTV;

        /**
         * 班车状态：已进站、未进站、已到站
         */
        TextView statusTV;

        /**
         * 直达技术北楼
         */
        TextView tipTV;

        /**
         * 发车安排：坐满发车，etc
         */
        TextView planTV;

        /**
         * 班车的到站提醒文本
         */
        TextView clocktipTV;
    }

	public void setOnContactDriverListener(View.OnClickListener onContactDriverListener) {
		this.onContactDriverListener = onContactDriverListener;
	}
}
