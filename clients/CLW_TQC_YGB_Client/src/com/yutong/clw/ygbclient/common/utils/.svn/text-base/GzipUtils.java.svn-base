/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2014-1-16 上午11:48:39
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipInputStream;

/**
 * @author zhangzhia 2014-1-16 上午11:48:39
 */

/**
 * GZIP压缩解压类
 */
public class GzipUtils
{

    private static String encode = "utf-8";// "ISO-8859-1"

    public String getEncode()
    {
        return encode;
    }

    /*
     * 设置 编码，默认编码：UTF-8
     */
    public void setEncode(String encode)
    {
        GzipUtils.encode = encode;
    }

    /*
     * 字符串压缩为字节数组
     */
    public static byte[] compressToByte(String str)
    {
        if (str == null || str.length() == 0)
        {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try
        {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encode));
            gzip.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /*
     * 字符串压缩为字节数组
     */
    public static byte[] compressToByte(String str, String encoding)
    {
        if (str == null || str.length() == 0)
        {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try
        {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /*
     * 字节数组解压缩后返回字符串
     */
    public static String uncompressToString(byte[] b)
    {
        if (b == null || b.length == 0)
        {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        try
        {
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0)
            {
                out.write(buffer, 0, n);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return out.toString();
    }

    /*
     * 字节数组解压缩后返回字符串
     */
    public static String uncompressToString(byte[] b, String encoding)
    {
        if (b == null || b.length == 0)
        {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(b);

        try
        {
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0)
            {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
