/*******************************************************************************
 * @(#)BizVehicleBean.java 2013-3-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.yutong.axxc.parents.business.beans;

import java.io.Serializable;

import com.yutong.axxc.parents.view.common.VConstants;

/**
 * 车辆查找结果类
 * 
 * @author <a href="mailto:wu.xp@neusoft.com">wuxp </a>
 * @version $Revision 1.1 $ 2013-3-12 下午04:17:11
 */
public class BizVehicleBean implements Serializable
{
	/**
	 * 序列号，数据传输需要
	 */
	private static final long serialVersionUID = 1L;

	/** 节点类型。01：车队；02：车辆 */
	private String type;

	/** 节点ID（车辆ID），即：车辆VIN */
	private String id;

	/** 节点名称。如type=01，表示车队名称；type=02，车牌号 */
	private String name;

	/** 父节点ID（车队ID） */
	private String pid;

	/** 车辆状态。0：离线；1：行驶；2：停驶；3：在线；4：全部状态 */
	private String vStatus;

	/** 车辆地图状态。0：离线；5：正常在线；6：告警（正常在线数+告警数=总在线数） */
	private String vMapStatus;

	/** 终端最后一次上报时间 */
	private String time;

	/** 纬度 */
	private double latitude;

	/** 经度 */
	private double longitude;

	/** 车速。单位km/h */
	private String speed;

	/** 当前车上学生数 */
	private String stuNum;

	/** 可承载人数 */
	private String capacity;

	/** 当前线路 */
	private String routeName;

	/** 驾驶员姓名 */
	private String driverName;

	/** 驾驶员手机号 */
	private String driverTel;

	/** 司乘姓名 */
	private String attendantName;

	/** 司乘手机号 */
	private String attendantTel;

	/** sherly.wen新添字段，告警类型 */
	private String alarm_notice_flag;

	public String getAlarm_notice_flag()
	{
		return alarm_notice_flag;
	}

	public void setAlarm_notice_flag(String alarm_notice_flag)
	{
		this.alarm_notice_flag = alarm_notice_flag;
	}

	/**
	 * 获取 type
	 * 
	 * @return 返回 type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * 设置 type
	 * 
	 * @param 对type进行赋值
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * 获取 id
	 * 
	 * @return 返回 id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * 设置 id
	 * 
	 * @param 对id进行赋值
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * 获取 name
	 * 
	 * @return 返回 name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 设置 name
	 * 
	 * @param 对name进行赋值
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 获取 pid
	 * 
	 * @return 返回 pid
	 */
	public String getPid()
	{
		return pid;
	}

	/**
	 * 设置 pid
	 * 
	 * @param 对pid进行赋值
	 */
	public void setPid(String pid)
	{
		this.pid = pid;
	}

	/**
	 * 获取 vStatus
	 * 
	 * @return 返回 vStatus
	 */
	public String getvStatus()
	{
		return vStatus;
	}

	/** 
     * 
     */
	public String getvStatusStr()
	{
		String vStatusStr = VConstants.V_OFFLINE_STR;
		if (VConstants.V_MOVING.equals(vStatus))
		{
			vStatusStr = VConstants.V_MOVING_STR;
		}
		else if (VConstants.V_STOPPED.equals(vStatus))
		{
			vStatusStr = VConstants.V_STOPPED_STR;
		}
		else if (VConstants.V_OFFLINE.equals(vStatus))
		{
			vStatusStr = VConstants.V_OFFLINE_STR;
		}
		return vStatusStr;
	}

	/**
	 * 设置 vStatus
	 * 
	 * @param 对vStatus进行赋值
	 */
	public void setvStatus(String vStatus)
	{
		this.vStatus = vStatus;
	}

	/**
	 * @return Returns the vMapStatus.
	 */
	public String getvMapStatus()
	{
		return vMapStatus;
	}

	/**
	 * @param vMapStatus
	 *            The vMapStatus to set.
	 */
	public void setvMapStatus(String vMapStatus)
	{
		this.vMapStatus = vMapStatus;
	}

	/**
	 * @return
	 * @see java.lang.String#toString()
	 */
	public String toString()
	{
		return name.toString();
	}

	/**
	 * @return Returns the time.
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * @param time
	 *            The time to set.
	 */
	public void setTime(String time)
	{
		this.time = time;
	}

	/**
	 * @return Returns the latitude.
	 */
	public double getLatitude()
	{
		return latitude;
	}

	/**
	 * @param latitude
	 *            The latitude to set.
	 */
	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	/**
	 * @return Returns the longitude.
	 */
	public double getLongitude()
	{
		return longitude;
	}

	/**
	 * @param longitude
	 *            The longitude to set.
	 */
	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	/**
	 * @return Returns the speed.
	 */
	public String getSpeed()
	{
		return speed;
	}

	/**
	 * @param speed
	 *            The speed to set.
	 */
	public void setSpeed(String speed)
	{
		this.speed = speed;
	}

	/**
	 * @return Returns the stuNum.
	 */
	public String getStuNum()
	{
		return stuNum;
	}

	/**
	 * @param stuNum
	 *            The stuNum to set.
	 */
	public void setStuNum(String stuNum)
	{
		this.stuNum = stuNum;
	}

	/**
	 * @return Returns the capacity.
	 */
	public String getCapacity()
	{
		return capacity;
	}

	/**
	 * @param capacity
	 *            The capacity to set.
	 */
	public void setCapacity(String capacity)
	{
		this.capacity = capacity;
	}

	/**
	 * @return Returns the routeName.
	 */
	public String getRouteName()
	{
		return routeName;
	}

	/**
	 * @param routeName
	 *            The routeName to set.
	 */
	public void setRouteName(String routeName)
	{
		this.routeName = routeName;
	}

	/**
	 * @return Returns the driverName.
	 */
	public String getDriverName()
	{
		return driverName;
	}

	/**
	 * @param driverName
	 *            The driverName to set.
	 */
	public void setDriverName(String driverName)
	{
		this.driverName = driverName;
	}

	/**
	 * @return Returns the driverTel.
	 */
	public String getDriverTel()
	{
		return driverTel;
	}

	/**
	 * @param driverTel
	 *            The driverTel to set.
	 */
	public void setDriverTel(String driverTel)
	{
		this.driverTel = driverTel;
	}

	/**
	 * @return Returns the attendantName.
	 */
	public String getAttendantName()
	{
		return attendantName;
	}

	/**
	 * @param attendantName
	 *            The attendantName to set.
	 */
	public void setAttendantName(String attendantName)
	{
		this.attendantName = attendantName;
	}

	/**
	 * @return Returns the attendantTel.
	 */
	public String getAttendantTel()
	{
		return attendantTel;
	}

	/**
	 * @param attendantTel
	 *            The attendantTel to set.
	 */
	public void setAttendantTel(String attendantTel)
	{
		this.attendantTel = attendantTel;
	}

}
