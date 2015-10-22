/**
 * @公司名称：YUTONG
 * @作者：zhangyongn
 * @版本号：1.0
 * @生成日期：2013-11-6 下午1:38:06
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.pages.main.outOfFactory.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.common.beans.line.StationVehicleRealTimeInfo;
import com.yutong.clw.ygbclient.common.enums.ArriveStatus;
import com.yutong.clw.ygbclient.common.enums.ScheduleItemType;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.utils.DateUtils;

/**
 * @author zhangyongn 2013-11-6 下午1:38:06 收藏站点班车计划可折叠列表适配器
 */
public class BusScheduleExpListAdapter extends BaseExpandableListAdapter
{
    private Date curDate = DateUtils.getCurDate();

    private List<StationVehicleRealTimeInfo> datas = new ArrayList<StationVehicleRealTimeInfo>();

    private LayoutInflater inflater;

    /*---------------侦听器-----------------*/
    /**
     * 站点收藏点击侦听
     */
    private View.OnClickListener siteFavorClickListener;

    /**
     * 站点闹钟点击侦听
     */
    private View.OnClickListener siteClockClickListener;

    /**
     * 站点地图点击侦听
     */
    private View.OnClickListener siteMapClickListener;

    /**
     * 站点下的【联系司机】点击侦听
     */
    private View.OnClickListener siteBusContactDriverClickListener;
    
    /**
     * 站点下的【到站提醒】点击侦听
     */
    private View.OnClickListener siteBusClockClickListener;

    /**
     * 站点下的【位置详情】点击侦听
     */
    private View.OnClickListener siteBusMapClickListener;

    /**
     * 站点下的某一bus信息栏点击侦听
     */
    private BusScheduleItemClickListener busScheduleItemClickListener;

    public BusScheduleExpListAdapter()
    {
        super();
        inflater = LayoutInflater.from(YtApplication.getInstance());
    }

    /**
     * @param datas 要设置的 datas
     */
    public void setDatas(List<StationVehicleRealTimeInfo> datas)
    {
        this.datas = datas;
        if (this.datas == null)
            datas = new ArrayList<StationVehicleRealTimeInfo>();
    }

    public void setCurDate(Date dt)
    {
        this.curDate = dt;
        if (this.curDate == null)
            this.curDate = DateUtils.getCurDate();
    }

    public List<StationVehicleRealTimeInfo> getDatas()
    {
        return this.datas;
    }

    @Override
    public Object getChild(int gp, int cp)
    {
        return datas.get(gp).VehicleRealtimeInfos.get(cp);
    }

    @Override
    public long getChildId(int gp, int cp)
    {

        return cp;
    }

    /* Group 布局 */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {

        GroupHolder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.groupitem_site, null);
            holder = buildGroupHolder(convertView);
            convertView.setTag(holder);

        }
        else
        {

            holder = (GroupHolder) convertView.getTag();
        }
        StationInfo site = ((StationVehicleRealTimeInfo) this.getGroup(groupPosition)).stationInfo;
        // 设置
        holder.clockIV.setTag(R.id.tag_station, site);
        holder.mapIV.setTag(R.id.tag_station, site);
        holder.favorIV.setTag(R.id.tag_station, site);
        setGroupListeners(holder, convertView, groupPosition);
        if (isExpanded)
        {
            holder.groupitemRL.setBackgroundColor(YtApplication.getInstance().getResources().getColor(R.color.maincolor));
            holder.triangleIV.setImageResource(R.drawable.ic_triangle_expand_white);

            holder.favorIV.setImageResource(site.isFavorites() ? R.drawable.ic_favored_white : R.drawable.ic_favor_white);
            holder.clockIV.setImageResource(site.isStatus() ? R.drawable.ic_clocked_white : R.drawable.ic_clock_white);
            holder.mapIV.setImageResource(R.drawable.ic_map_white);
            holder.siteTV.setTextColor(YtApplication.getInstance().getResources().getColor(R.color.white));
            holder.siteTV.setText(site.name);
        }
        else
        {
            holder.groupitemRL.setBackgroundColor(YtApplication.getInstance().getResources().getColor(R.color.bg_sitecolor_gray));
            holder.triangleIV.setImageResource(R.drawable.ic_triangle_fold_gray);

            holder.favorIV.setImageResource(site.isFavorites() ? R.drawable.ic_favored_gray : R.drawable.ic_favor_gray);
            holder.clockIV.setImageResource(site.isStatus() ? R.drawable.ic_clocked_gray : R.drawable.ic_clock_gray);
            holder.mapIV.setImageResource(R.drawable.ic_map_gray);
            holder.siteTV.setTextColor(YtApplication.getInstance().getResources().getColor(R.color.black));
            holder.siteTV.setText(site.name);
        }
        // 设置地图显示
        boolean istoday = DateUtils.isToday(curDate);
        holder.mapIV.setVisibility(istoday ? View.VISIBLE : View.GONE);
        return convertView;
    }

    /* Group 监听器 */
    private void setGroupListeners(GroupHolder holder, View convertView, int groupPosition)
    {
        holder.clockIV.setOnClickListener(null);
        holder.clockIV.setOnClickListener(this.siteClockClickListener);

        holder.favorIV.setOnClickListener(null);
        holder.favorIV.setOnClickListener(this.siteFavorClickListener);

        holder.mapIV.setOnClickListener(null);
        holder.mapIV.setOnClickListener(this.siteMapClickListener);
    }

    private GroupHolder buildGroupHolder(View view)
    {
        GroupHolder holder = new GroupHolder();
        holder.groupitemRL = (RelativeLayout) view.findViewById(R.id.groupitemRL);
        holder.clockIV = (ImageView) view.findViewById(R.id.clockIV);
        holder.favorIV = (ImageView) view.findViewById(R.id.favorIV);
        holder.mapIV = (ImageView) view.findViewById(R.id.mapIV);
        holder.siteTV = (TextView) view.findViewById(R.id.siteTV);
        holder.triangleIV = (ImageView) view.findViewById(R.id.triangleIV);

        return holder;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {

        ChildHolder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listitem_bus_schedule, null);
            holder = buildChildHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ChildHolder) convertView.getTag();
        }

        StationVehicleRealTimeInfo groupDataItem = ((StationVehicleRealTimeInfo) this.getGroup(groupPosition));
        StationInfo site = groupDataItem.stationInfo;
        VehicleRealtime childDataItem = groupDataItem.VehicleRealtimeInfos.get(childPosition);
        try
        {
            if (childDataItem.itemType == ScheduleItemType.Tip)
            {
                holder.opLL.setVisibility(View.GONE);
                holder.itemRL.setVisibility(View.GONE);
                holder.tipRL.setVisibility(View.VISIBLE);
                holder.tiptextTV.setText(childDataItem.tip);
                
                /*如果是查询的结果，则隐藏进度条*/
                if(childDataItem.isResult){
                	holder.searching_ProgressBar.setVisibility(View.GONE);
                }else{
                	holder.searching_ProgressBar.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                setChildListeners(holder, convertView, site, childDataItem);

                holder.opLL.setVisibility(childDataItem.showOP ? View.VISIBLE : View.GONE);
                holder.itemRL.setVisibility(View.VISIBLE);
                holder.tipRL.setVisibility(View.GONE);
                
                /*联系司机*/
                holder.contact_driver_RL.setTag(R.id.tag_station, site);
                holder.contact_driver_RL.setTag(R.id.tag_realtimeinfo, childDataItem);
                
                /*到站提醒*/
                holder.opclockbtnRL.setTag(R.id.tag_station, site);
                holder.opclockbtnRL.setTag(R.id.tag_realtimeinfo, childDataItem);
                
                /*位置详情*/
                holder.opmapbtnRL.setTag(R.id.tag_station, site);
                holder.opmapbtnRL.setTag(R.id.tag_realtimeinfo, childDataItem);
                
                /*整体容器*/
                holder.itemRL.setTag(R.id.tag_grouppos, groupPosition);
                holder.itemRL.setTag(R.id.tag_childpos, childPosition);

                holder.busNumberTV.setText(childDataItem.vehicle_number);
                holder.statusTV.setText(childDataItem.arrive_status.getName());
                String status_desc_str = childDataItem.status_desc;
                holder.planTV.setText( status_desc_str!=null && status_desc_str.length()>0 ? status_desc_str+"发车":"坐满发车" );
                holder.tipTV.setVisibility(childDataItem.flag ? View.VISIBLE : View.GONE);
                holder.clocktipTV.setVisibility((childDataItem.remind_status == RemindStatus.Open) ? View.VISIBLE : View.GONE);
                holder.clocktipTV.setText(buildClockTipText(childDataItem));

                int buscolor = (childDataItem.arrive_status == ArriveStatus.Leave||childDataItem.arrive_status==ArriveStatus.StartOff) ? YtApplication.getInstance().getResources()
                        .getColor(R.color.grayfont) : YtApplication.getInstance().getResources().getColor(R.color.divider_color_blue);

                holder.busNumberTV.setTextColor(buscolor);
                int statuscolor = (childDataItem.arrive_status == ArriveStatus.Leave||childDataItem.arrive_status==ArriveStatus.StartOff) ? YtApplication.getInstance().getResources()
                        .getColor(R.color.grayfont) : YtApplication.getInstance().getResources().getColor(R.color.grayfont_dark);
                holder.statusTV.setTextColor(statuscolor);

                // 设置地图显示
                boolean istoday = DateUtils.isToday(curDate);
//                holder.opmapbtnRL.setVisibility(istoday ? View.VISIBLE : View.INVISIBLE);

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

    private void setChildListeners(ChildHolder holder, View convertView, StationInfo site, VehicleRealtime childDataItem)
    {
        
    	holder.contact_driver_RL.setOnClickListener(null);
        holder.contact_driver_RL.setOnClickListener(this.siteBusContactDriverClickListener);
        
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
                int grouppos = (Integer) v.getTag(R.id.tag_grouppos);
                int childpos = (Integer) v.getTag(R.id.tag_childpos);
                if (busScheduleItemClickListener != null)
                    busScheduleItemClickListener.onItemClick(grouppos, childpos);
            }
        });

    }

    private ChildHolder buildChildHolder(View v)
    {
        ChildHolder holder = new ChildHolder();
        holder.tiptextTV = (TextView) v.findViewById(R.id.tiptextTV);

        holder.busNumberTV = (TextView) v.findViewById(R.id.busNumberTV);
        holder.clocktipTV = (TextView) v.findViewById(R.id.clocktipTV);
        
        holder.opLL = (LinearLayout) v.findViewById(R.id.opLL);
        
        /*联系司机*/
        holder.contact_driver_RL = (RelativeLayout) v.findViewById(R.id.contact_driver_RL);
        
        /*到站提醒*/
        holder.opclockbtnRL = (RelativeLayout) v.findViewById(R.id.opclockbtnRL);
        
        /*位置详情*/
        holder.opmapbtnRL = (RelativeLayout) v.findViewById(R.id.opmapbtnRL);

        holder.planTV = (TextView) v.findViewById(R.id.planTV);
        holder.statusTV = (TextView) v.findViewById(R.id.statusTV);
        holder.tipTV = (TextView) v.findViewById(R.id.tipTV);

        holder.itemRL = (RelativeLayout) v.findViewById(R.id.itemRL);
        holder.tipRL = (RelativeLayout) v.findViewById(R.id.tipRL);
        holder.searching_ProgressBar = (ProgressBar) v.findViewById(R.id.searching_ProgressBar);
        
        return holder;
    }

    @Override
    public int getChildrenCount(int gp)
    {
        return this.datas.get(gp).VehicleRealtimeInfos.size();
    }

    @Override
    public Object getGroup(int gp)
    {
        return this.datas.get(gp);
    }

    @Override
    public int getGroupCount()
    {
        return this.datas.size();
    }

    @Override
    public long getGroupId(int gp)
    {
        return gp;
    }

    /**
     * 有没有ID
     * 
     * @author zhangyongn 2013-11-6 下午2:05:59
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds()
    {

        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {

        return true;
    }

    /**
     * @param siteFavorClickListener 要设置的 siteFavorClickListener
     */
    public void setSiteFavorClickListener(View.OnClickListener siteFavorClickListener)
    {
        this.siteFavorClickListener = siteFavorClickListener;
    }

	public void setSiteBusContactDriverClickListener(
			View.OnClickListener siteBusContactDriverClickListener) {
		this.siteBusContactDriverClickListener = siteBusContactDriverClickListener;
	}

	/**
     * @param siteClockClickListener 要设置的 siteClockClickListener
     */
    public void setSiteClockClickListener(View.OnClickListener siteClockClickListener)
    {
        this.siteClockClickListener = siteClockClickListener;
    }

    /**
     * @param siteMapClickListener 要设置的 siteMapClickListener
     */
    public void setSiteMapClickListener(View.OnClickListener siteMapClickListener)
    {
        this.siteMapClickListener = siteMapClickListener;
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

    static class GroupHolder
    {
        /**
         * 根容器
         */
        RelativeLayout groupitemRL;

        /**
         * 左边小三角
         */
        ImageView triangleIV;

        /**
         * 站点名
         */
        TextView siteTV;

        /**
         * 收藏图标
         */
        ImageView favorIV;

        /**
         * 闹钟设置图标
         */
        ImageView clockIV;

        /**
         * 地图图标
         */
        ImageView mapIV;
    }

    static class ChildHolder
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
         * 联系司机
         */
        RelativeLayout contact_driver_RL;
        /**
         * 闹钟设置
         */
        RelativeLayout opclockbtnRL;

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
        
        /**
         * 正在查询提示progressBar
         */
        ProgressBar searching_ProgressBar;
        
        
        
    }

    public interface BusScheduleItemClickListener
    {
        /**
         * 班车计划item点击
         * 
         * @author zhangyongn 2013-11-13 下午5:09:17
         * @param groupPosition
         * @param childPosition
         */
        void onItemClick(int groupPosition, int childPosition);
    }
}
