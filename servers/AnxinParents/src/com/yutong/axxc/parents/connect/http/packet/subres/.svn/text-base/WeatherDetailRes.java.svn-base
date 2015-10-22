
package com.yutong.axxc.parents.connect.http.packet.subres;

import org.json.JSONObject;

/**
 * 气象详细信息相应类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class WeatherDetailRes {

    /** 日期 */
    private String date;

    /** 气温 */
    private String temper;

    /** 气象描述信息 */
    private String desc;

    /** 风力 */
    private String wind;

    /** PM值 */
    private String pm;
    
    /** 天气图标1代表当前气象的天气图标 */
    private String imgOne;

    /** 代表一段时间后的气象图标 */
    private String imgTwo;

    /**
     * 解析内容
     * @param msg
     * @return
     */
    public boolean parse(JSONObject msg) {
        date = msg.optString("date");
        temper = msg.optString("temper");
        desc = msg.optString("desc");
        wind = msg.optString("wind");
        pm = msg.optString("pm");
        imgOne = msg.optString("img_one");
        imgTwo = msg.optString("img_two");
        return true;
    }

    /**
     * @return Returns the date.
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date to set.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return Returns the temper.
     */
    public String getTemper() {
        return temper;
    }

    /**
     * @param temper The temper to set.
     */
    public void setTemper(String temper) {
        this.temper = temper;
    }

    /**
     * @return Returns the desc.
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc The desc to set.
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return Returns the wind.
     */
    public String getWind() {
        return wind;
    }

    /**
     * @param wind The wind to set.
     */
    public void setWind(String wind) {
        this.wind = wind;
    }

    
    public String getPm()
    {
        return pm;
    }

    public void setPm(String pm)
    {
        this.pm = pm;
    }

    /**
     * @return Returns the imgOne.
     */
    public String getImgOne() {
        return imgOne;
    }

    /**
     * @param imgOne The imgOne to set.
     */
    public void setImgOne(String imgOne) {
        this.imgOne = imgOne;
    }

    /**
     * @return Returns the imgTwo.
     */
    public String getImgTwo() {
        return imgTwo;
    }

    /**
     * @param imgTwo The imgTwo to set.
     */
    public void setImgTwo(String imgTwo) {
        this.imgTwo = imgTwo;
    }

}
