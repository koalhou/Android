package com.yutong.clw.ygbclient.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.util.Base64;

import com.yutong.clw.ygbclient.common.Logger;

public class EncryptUtils
{

    /**
     * base64编码
     * 
     * @author zhangzhia 2013-9-5 下午6:42:32
     * @param bytes
     * @return
     */
    public static String bytesToBase64(byte[] bytes)
    {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * base64解码
     * 
     * @author zhangzhia 2013-9-5 下午6:43:14
     * @param strbase64
     * @return
     */
    public static byte[] base64ToBytes(String strbase64)
    {
        return Base64.decode(strbase64, Base64.DEFAULT);
    }
    
    /**
     * 获取字符串的MD5值
     * 
     * @param str
     *            参与计算的字符串
     * @return
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
        	Logger.e(EncryptUtils.class, e);
        } catch (UnsupportedEncodingException e) {
            Logger.e(EncryptUtils.class, e);
        }
        byte[] byteArray = messageDigest.digest();
        StringBuilder md5StrBuidler = new StringBuilder();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuidler.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuidler.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }

        return md5StrBuidler.toString().toUpperCase(Locale.getDefault());
    }
}
