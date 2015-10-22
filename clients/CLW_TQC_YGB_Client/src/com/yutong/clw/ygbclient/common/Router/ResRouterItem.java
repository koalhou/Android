package com.yutong.clw.ygbclient.common.Router;

import com.yutong.clw.ygbclient.common.enums.ResourceType;

public class ResRouterItem
{
    /**
     * 文件唯一识别标志，可以为以下几种类型[暂时无用] 1. url 2. resId
     */
    public String resKey;

    /**
     * 资源类型
     */
    public ResourceType resType;

    /**
     * 资源文件名
     */
    public String name;

    /**
     * 资源大小
     */
    public String size;

    /**
     * 资源路径
     */
    public String path;

    /**
     * 后缀名，如png
     */
    public String suffix;

    /**
     * 资源base64位编码串
     */
    public String resource;

    /**
     * 资源byte[]
     */
    public byte[] bytes;

    public String getSize()
    {
        return size;
    }

    public String getResKey()
    {
        return resKey;
    }

    public void setResKey(String resKey)
    {
        this.resKey = resKey;
    }

    public ResourceType getResType()
    {
        return resType;
    }

    public void setResType(ResourceType resType)
    {
        this.resType = resType;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public String getResource()
    {
        return resource;
    }

    public void setResource(String resource)
    {
        this.resource = resource;
    }

    public byte[] getBytes()
    {
        return bytes;
    }

    public void setBytes(byte[] bytes)
    {
        this.bytes = bytes;
    }

    public ResRouterItem(String resKey, ResourceType resType, String suffix)
    {
        this.resKey = resKey;
        this.resType = resType;
        this.suffix = suffix;
    }
}
