package com.yutong.clw.ygbclient.view.bizAccess.upgrade;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.ThreadCommUtil;
import com.yutong.clw.ygbclient.common.YtAsyncTask;
import com.yutong.clw.ygbclient.common.constant.ThreadCommStateCode;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;

/**
 * 升级业务逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class VersionUpgradeBiz extends YtAsyncTask
{

    /** 升级版本保存名字 */
    public static final String UPDATE_SAVENAME = "xcp.apk";

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 下载URL */
    private String url;

    /**
     * 构造函数
     * 
     * @param context Context对象
     * @param handler Handler对象
     * @param url 下载地址
     */
    public VersionUpgradeBiz(Context context, Handler handler, String url)
    {
        this.context = context;
        this.handler = handler;
        this.url = url;
        logTypeName = "[升级业务逻辑类]:";
    }

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.common.YtAsyncTask#doInBackground()
     */
    @Override
    protected void doInBackground()
    {
        // 用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        // UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0025);

        if (downFile(url, handler))
        {
            Logger.i(this.getClass(), logTypeName + "下载成功，安装新版软件");
            install(context);
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "下载失败！");
        }
    }

    /**
     * 安装软件
     * 
     * @param context
     * @see [类、类#方法、类#成员]
     */
    private void install(Context context)
    {
        Logger.i(VersionUpgradeBiz.class, "[升级业务逻辑类]：安装新版软件");
        ProxyManager.clearLogs();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), UPDATE_SAVENAME)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);

    }

    /**
     * 下载文件
     * 
     * @param downloadUrl
     * @param handler
     * @return
     * @see [类、类#方法、类#成员]
     */
    private boolean downFile(String downloadUrl, Handler handler)
    {
        Logger.i(VersionUpgradeBiz.class, "[升级业务逻辑类]：下载新版软件，URL:", downloadUrl);
        HttpClient httpclient = new DefaultHttpClient();

        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, SysConfigUtil.getHttpConnTimeout());

        HttpGet httpGet = null;
        BufferedOutputStream outputStream = null;
        InputStream inputStream = null;
        try
        {

            URL url = new URL(downloadUrl);
            // 用URL生成URI的方法来解决地址中涉及特殊字符，如‘｜’、‘&’、空格等问题
            URI uri = new URI(url.getProtocol(), null, url.getHost(), url.getPort(), url.getPath(), url.getQuery(), null);
            httpGet = new HttpGet(uri.toString());
            
            HttpResponse response = httpclient.execute(httpGet);
            if (isCanceled())
            {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.VERSON_UPDATE_CANCEL);

                return false;
            }
            final int stattusCode = response.getStatusLine().getStatusCode();
            if (stattusCode >= 200 && stattusCode < 300)
            {
                inputStream = response.getEntity().getContent();
                File tempFile;

                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    tempFile = new File(Environment.getExternalStorageDirectory(), UPDATE_SAVENAME);
                }
                else
                {
                    Logger.d(VersionUpgradeBiz.class, "[升级业务逻辑类]：无SD卡, 存取内存路径" + context.getCacheDir());
                    tempFile = new File(context.getCacheDir(), UPDATE_SAVENAME);
                }

                if (tempFile.exists())
                {
                    tempFile.delete();
                }
                FileOutputStream fstream = new FileOutputStream(tempFile);
                outputStream = new BufferedOutputStream(fstream);

                long totalLength = response.getEntity().getContentLength();
                int preRate = -1;

                byte[] buffer = new byte[10240];
                long totalReadCount = 0;
                int byteCount = 0;
                while (!isCanceled() && (byteCount = inputStream.read(buffer)) != -1)
                {
                    totalReadCount += byteCount;
                    int rate = (int) ((long) (totalReadCount * 100) / totalLength);
                    if (rate != preRate)
                    {
                        // ThreadCommUtil.sendMsgToUI(handler,
                        // ThreadCommStateCode.VERSION_UPDATING, rate);
                        preRate = rate;
                    }
                    else
                    {
                        ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.VERSION_UPDATING, rate);
                    }
                    Logger.d(VersionUpgradeBiz.class, "[升级业务逻辑类]：新版软件下载中，rate:", rate);
                    outputStream.write(buffer, 0, byteCount);
                }
                if (isCanceled())
                {
                    ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.VERSON_UPDATE_CANCEL);
                    outputStream.flush();
                    return false;
                }
                else
                {
                    Logger.d(VersionUpgradeBiz.class, "[升级业务逻辑类]：新版软件下载完成");
                    ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.VERSION_UPDATE_COMPLETE);
                    outputStream.flush();
                    return true;
                }

            }
            else
            {
                Logger.d(VersionUpgradeBiz.class, "[升级业务逻辑类]：下载失败:statuscode:" + stattusCode);
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.VERSON_UPDATE_FAILED);
                return false;
            }
        }
        catch (Exception e)
        {
            Logger.e(VersionUpgradeBiz.class, "[升级业务逻辑类]：发送报文异常，详细信息:", e);
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.VERSON_UPDATE_FAILED);
            return false;
        }
        finally
        {
            if (outputStream != null)
            {
                try
                {
                    outputStream.close();
                }
                catch (IOException e)
                {
                    Logger.e(VersionUpgradeBiz.class, "[升级业务逻辑类]：发送报文异常，详细信息:", e);
                }
            }
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    Logger.e(VersionUpgradeBiz.class, "[升级业务逻辑类]：发送报文异常，详细信息:", e);
                }
            }
            if (httpGet != null)
            {
                httpGet.abort();
            }
            if (httpclient != null)
            {
                httpclient.getConnectionManager().shutdown();
            }
        }
    }
}
