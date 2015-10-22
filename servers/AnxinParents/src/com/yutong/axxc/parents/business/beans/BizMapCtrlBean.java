/*******************************************************************************
 * @(#)BizMapCtrlBean.java 2013-3-29
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.yutong.axxc.parents.business.beans;

/**
 * @author <a href="mailto:zhouchao@neusoft.com">super.zhou </a>
 * @version $Revision 1.1 $ 2013-3-29 上午9:09:54
 */
public class BizMapCtrlBean {
    /** 地图中心点纬度 */
    private double cLatitude = 34.758419;
    
    /** 地图中心点经度 */
    private double cLongitude = 113.672893;
    
    /** 指定维度范围 */
    private int latSpanE6 = 100000;
    
    /** 指定经度范围 */
    private int lonSpanE6 = 100000;

    /**
     * @return Returns the cLatitude.
     */
    public double getcLatitude() {
        return cLatitude;
    }

    /**
     * @param cLatitude The cLatitude to set.
     */
    public void setcLatitude(double cLatitude) {
        this.cLatitude = cLatitude;
    }

    /**
     * @return Returns the cLongitude.
     */
    public double getcLongitude() {
        return cLongitude;
    }

    /**
     * @param cLongitude The cLongitude to set.
     */
    public void setcLongitude(double cLongitude) {
        this.cLongitude = cLongitude;
    }

    /**
     * @return Returns the latSpanE6.
     */
    public int getLatSpanE6() {
        return latSpanE6;
    }

    /**
     * @param latSpanE6 The latSpanE6 to set.
     */
    public void setLatSpanE6(int latSpanE6) {
        this.latSpanE6 = latSpanE6;
    }

    /**
     * @return Returns the lonSpanE6.
     */
    public int getLonSpanE6() {
        return lonSpanE6;
    }

    /**
     * @param lonSpanE6 The lonSpanE6 to set.
     */
    public void setLonSpanE6(int lonSpanE6) {
        this.lonSpanE6 = lonSpanE6;
    }
}
