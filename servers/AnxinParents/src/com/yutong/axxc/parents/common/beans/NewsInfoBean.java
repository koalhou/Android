package com.yutong.axxc.parents.common.beans;

import java.io.Serializable;


/**
 * 新闻信息实体类
 * @author zhangzhia 2013-9-9 下午3:37:09
 *
 */
public final class NewsInfoBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3670826550506029100L;
    /** 消息ID */
    private String news_id;

    /** 消息标题 */
    private String news_title;

    /** 消息概述信息 */
    private String news_summary;

    /** 消息发布者 */
    private String news_author;
    
    /** 消息图片 res_id */
    private String news_image;
    
    /** 消息图片 */
    private String news_image_url;
    
    /** 消息内容信息 */
    private String news_content;

    /** 消息发布时间。格式：yyyymmddhh24miss */
    private String news_time;

    /** 消息类型0-行业政策1-宇通营销 */
    private String news_type;

    /** 是否已读 */
    private boolean is_read;

    public String getNews_id()
    {
        return news_id;
    }

    public void setNews_id(String news_id)
    {
        this.news_id = news_id;
    }

    public String getNews_title()
    {
        return news_title;
    }

    public void setNews_title(String news_title)
    {
        this.news_title = news_title;
    }

    public String getNews_summary()
    {
        return news_summary;
    }

    public void setNews_summary(String news_summary)
    {
        this.news_summary = news_summary;
    }

    
    public String getNews_author()
    {
        return news_author;
    }

    public void setNews_author(String news_author)
    {
        this.news_author = news_author;
    }

    public String getNews_image()
    {
        return news_image;
    }

    public void setNews_image(String news_image)
    {
        this.news_image = news_image;
    }

    public String getNews_content()
    {
        return news_content;
    }

    public void setNews_content(String news_content)
    {
        this.news_content = news_content;
    }

    public String getNews_time()
    {
        return news_time;
    }

    public void setNews_time(String news_time)
    {
        this.news_time = news_time;
    }

    public String getNews_type()
    {
        return news_type;
    }

    public void setNews_type(String news_type)
    {
        this.news_type = news_type;
    }

    public boolean getIs_read()
    {
        return is_read;
    }

    public void setIs_read(boolean is_read)
    {
        this.is_read = is_read;
    }

    public String getNews_image_url()
    {
        return news_image_url;
    }

    public void setNews_image_url(String news_image_url)
    {
        this.news_image_url = news_image_url;
    }

    
}
