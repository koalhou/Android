/*******************************************************************************
 * @(#)BizVehicleAlarmInfo.java 2013-3-28
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.yutong.axxc.parents.business.beans;

import com.yutong.axxc.parents.common.GlobleConstants;


/**
 * @author <a href="mailto:wenxw@neusoft.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2013-3-28 下午8:13:02
 */
public class BizVehicleAlarmInfo {
    private String alarm_id = GlobleConstants.EMPTY_STR;// 告警消息ID

    private String event_type = GlobleConstants.EMPTY_STR;// 告警类型

    private String alarm_type_id = GlobleConstants.EMPTY_STR;

    private String alarm_cont = GlobleConstants.EMPTY_STR;// 告警内容

    private String alarm_occur_t = GlobleConstants.EMPTY_STR;// 告警发生时间

    private String alarm_end_t = GlobleConstants.EMPTY_STR;// 告警结束时间

    private String alarm_report_t = GlobleConstants.EMPTY_STR;// 告警上报时间

    private String loc_state = GlobleConstants.EMPTY_STR;

    private String speed = GlobleConstants.EMPTY_STR;// 时速

    private String direction = GlobleConstants.EMPTY_STR;// 行驶方向

    private String eng_speed = GlobleConstants.EMPTY_STR;// 引擎转速

    private String acc_flag = GlobleConstants.EMPTY_STR;// 点火状态

    private String oil = GlobleConstants.EMPTY_STR;// 瞬时油耗

    private String passenger_number = GlobleConstants.EMPTY_STR;// 承载情况。

    private String limit_number = GlobleConstants.EMPTY_STR;// 满载人数

    public String getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(String alarm_id) {
        this.alarm_id = alarm_id;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getAlarm_type_id() {
        return alarm_type_id;
    }

    public void setAlarm_type_id(String alarm_type_id) {
        this.alarm_type_id = alarm_type_id;
    }

    public String getAlarm_cont() {
        return alarm_cont;
    }

    public void setAlarm_cont(String alarm_cont) {
        this.alarm_cont = alarm_cont;
    }

    public String getAlarm_occur_t() {
        return alarm_occur_t;
    }

    public void setAlarm_occur_t(String alarm_occur_t) {
        this.alarm_occur_t = alarm_occur_t;
    }

    public String getAlarm_end_t() {
        return alarm_end_t;
    }

    public void setAlarm_end_t(String alarm_end_t) {
        this.alarm_end_t = alarm_end_t;
    }

    public String getAlarm_report_t() {
        return alarm_report_t;
    }

    public void setAlarm_report_t(String alarm_report_t) {
        this.alarm_report_t = alarm_report_t;
    }

    public String getLoc_state() {
        return loc_state;
    }

    public void setLoc_state(String loc_state) {
        this.loc_state = loc_state;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getEng_speed() {
        return eng_speed;
    }

    public void setEng_speed(String eng_speed) {
        this.eng_speed = eng_speed;
    }

    public String getAcc_flag() {
        return acc_flag;
    }

    public void setAcc_flag(String acc_flag) {
        this.acc_flag = acc_flag;
    }

    public String getOil() {
        return oil;
    }

    public void setOil(String oil) {
        this.oil = oil;
    }

    public String getPassenger_number() {
        return passenger_number;
    }

    public void setPassenger_number(String passenger_number) {
        this.passenger_number = passenger_number;
    }

    public String getLimit_number() {
        return limit_number;
    }

    public void setLimit_number(String limit_number) {
        this.limit_number = limit_number;
    }
}
