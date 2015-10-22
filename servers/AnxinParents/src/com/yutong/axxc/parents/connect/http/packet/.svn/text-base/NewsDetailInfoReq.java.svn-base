package com.yutong.axxc.parents.connect.http.packet;


/**
 * 新闻详细信息请求类
 * 
 * @author zhangzhia 2013-9-9 下午4:58:49
 */
public class NewsDetailInfoReq extends AbstractReq
{

    private String news_id;
    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody()
    {
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.neusoft.yt.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri()
    {
        reuqestUri = reuqestUri + "/news/detail/" + news_id;
    }

    public void setNews_id(String news_id)
    {
        this.news_id = news_id;
    }

    
    
}
