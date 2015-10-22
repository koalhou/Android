package com.yutong.axxc.parents.common;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class BitmapUtils
{
    public static byte[] Bitmap2Bytes(Bitmap bm)
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        return baos.toByteArray();

    }

    public static Bitmap Bytes2Bimap(byte[] b)
    {
        try
        {
            if (b.length != 0)
            {
                Bitmap bm;
                // BitmapFactory.Options opt = new BitmapFactory.Options();
                // // 这个isjustdecodebounds很重要
                // opt.inSampleSize = 10;
                // opt.inJustDecodeBounds = true;
                // bm = BitmapFactory.decodeByteArray(b, 0, b.length, opt);
                
                BitmapFactory.Options options=new Options();
                options.inDither=false;    /*不进行图片抖动处理*/
                options.inPreferredConfig=null;  /*设置让解码器以最佳方式解码*/
                options.inSampleSize=2;          /*图片长宽方向缩小倍数*/
                bm =BitmapFactory.decodeByteArray(b, 0, b.length, options);
                
                //bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                return bm;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            return null;
        }
    }

}
