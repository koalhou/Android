/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:08:36
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans.news;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yutong.clw.ygbclient.common.enums.news.HasNextStatus;

/**
 * 新闻信息实体类
 * 
 * @author zhangzhia 2013-9-9 下午3:37:09
 */
public final class NewsReturnInfo implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 3670826550506029100L;

    /**
     * 服务器当前时间
     */
    public Date timestamp;

    /**
     * 下翻是否还有数据
     */
    public HasNextStatus hasnext;

    /**
     * 总条数
     */
    public int totalnum;   

    /**
     * 新闻列表
     */
    public List<NewsInfo> infos;

}
