
package com.yutong.axxc.parents.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import android.os.Environment;
import android.util.Log;

import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 日志输出类
 * @author zhangzhia 2013-9-10 上午11:17:19
 *
 */
public final class Logger {
    /*
     * log 级别
     */
    /** log 级别 VERBOSE */
    private static final int LOG_LEVEL_VERBOSE = 5;

    /** log 级别 DEBUG */
    private static final int LOG_LEVEL_DEBUG = 4;

    /** log 级别 INFO */
    private static final int LOG_LEVEL_INFO = 3;

    /** log 级别 WARN */
    private static final int LOG_LEVEL_WARN = 2;

    /** log 级别 ERROR */
    private static final int LOG_LEVEL_ERROR = 1;

    /*
     * log 常量
     */
    /** log tag */
    public static final String LOG_TAG = getLogTag();

    /** 是否保存日志到文件 */
    private static final boolean isSaveLog2File = isSaveLogFile();

    /** 日志文件位置 */
    private static final String logFileLocation = getLogFileLocation();

    /** log文件名 */
    private static final String logName = getLogFileName();

    /** log 级别 */
    private static final int LOG_LEVEL = getLogLevel();

    private static Properties properties = initProperties();

    private Logger() {
    }

    /**
     * 初始化配置文件对象
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static Properties initProperties() {
        Properties props = new Properties();
        try {
            InputStream in = Logger.class.getResourceAsStream("/assets/log.properties");
            props.load(in);
        } catch (IOException e) {
            Log.e(LOG_TAG, "[全局配置文件读取工具类]:加载配置文件异常，详细信息：", e);
        }
        return props;
    }


    public static String getProperty(String propertyName, String defaultValue) {
        if (properties == null) {
            properties = initProperties();
        }
        return properties.getProperty(propertyName, defaultValue);
    }


    public static void e(Class<?> cls, Object... logMsg) {
        String temp = dynamicArgParse(logMsg);
        android.util.Log.e(LOG_TAG, temp);
        if (LOG_LEVEL >= LOG_LEVEL_ERROR) {
            if (isSaveLog2File) {
                saveFile("E", new StringBuilder(cls.getName()).toString(), temp);
            }
        }
    }


    public static void w(Class<?> cls, Object... logMsg) {
        String temp = dynamicArgParse(logMsg);
        android.util.Log.d(LOG_TAG, temp);
        if (LOG_LEVEL >= LOG_LEVEL_WARN) {
            if (isSaveLog2File) {
                saveFile("W", new StringBuilder(cls.getName()).toString(), temp);
            }
        }
    }


    public static void i(Class<?> cls, Object... logMsg) {
        String temp = dynamicArgParse(logMsg);
        android.util.Log.i(LOG_TAG, temp);
        if (LOG_LEVEL >= LOG_LEVEL_INFO) {
            if (isSaveLog2File) {
                saveFile("I", new StringBuilder(cls.getName()).toString(), temp);
            }
        }
    }

    public static void d(Class<?> cls, Object... logMsg) {
        String temp = dynamicArgParse(logMsg);
        android.util.Log.d(LOG_TAG, temp);
        if (LOG_LEVEL >= LOG_LEVEL_DEBUG) {
            if (isSaveLog2File) {
                saveFile("D", new StringBuilder(cls.getName()).toString(), temp);
            }
        }
    }


    public static void v(Class<?> cls, Object... logMsg) {
        String temp = dynamicArgParse(logMsg);
        android.util.Log.d(LOG_TAG, temp);
        if (LOG_LEVEL >= LOG_LEVEL_VERBOSE) {
            if (isSaveLog2File) {
                saveFile("V", new StringBuilder(cls.getName()).toString(), temp);
            }
        }
    }


    public final static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(System.currentTimeMillis());
    }


    private static String dynamicArgParse(Object... logMsg) {
        StringBuilder builder = new StringBuilder();
        for (Object content : logMsg) {
            try
            {
                builder.append(" ").append(obj2str(content));
            }
            catch(Exception e)
            {
                //TODO 不处理异常
                i(Logger.class, "日志太大，不输出");
            }
            
        }
        return builder.toString();
    }


    private static String obj2str(Object object) {
        if (object == null) return "[NULL]";

        if (object instanceof Throwable) {
            StringBuilder sb = new StringBuilder();
            Throwable e = (Throwable) object;
            sb.append(e);

            StackTraceElement[] trace = e.getStackTrace();
            if (trace != null) {
                for (StackTraceElement t : trace) {
                    sb.append("\n\tat ").append(t);
                }
            }
            sb.append(e.getCause());
            return sb.toString();
        } else {
            return String.valueOf(object);
        }
    }


    private static void saveFile(String Level, String className, String logMsg) {

        if (YtApplication.getInstance().isCanWriteLog()) {
            FileOutputStream fos = null;
            File file = new File(logFileLocation + logName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    YtApplication.getInstance().setCanWriteLog(false);
                    return;
                } finally {
                    closeFOS(fos);
                }
            }
            try {

                fos = new FileOutputStream(file, true);
                String str = new StringBuilder().append("[").append(getCurrentTime()).append("]").append("--")
                        .append("[").append(Level).append("]").append("--").append("[Thread-")
                        .append(Thread.currentThread().getId())
                        .append(getNul(String.valueOf(Thread.currentThread().getId()), 4)).append("]").append("--")
                        .append("--<").append(className).append(getNul(className, 70)).append(">:").append(logMsg)
                        .append("\n").toString();
                fos.write(str.getBytes());
            } catch (Exception e) {
                YtApplication.getInstance().setCanWriteLog(false);
            } finally {
                closeFOS(fos);
            }
        }

    }

    private static void closeFOS(FileOutputStream fos) {
        if (null != fos) {
            try {
                fos.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "", e);
            }
        }
    }


    private static String getNul(String content, int targetLength) {
        int length = targetLength - content.length();
        StringBuilder stringBuilder = new StringBuilder();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }


    private static Boolean isSaveLogFile() {
        return Boolean.valueOf(getProperty("IS_SAVE_LOG_FILE", "true").trim());
    }


    private static int getLogLevel() {
        return Integer.valueOf(getProperty("LOG_LEVEL", "5").trim());
    }

    private static String getLogTag() {
        return getProperty("LOG_TAG", "LOG").trim();
    }


    private static String getLogFileLocation() {
        String fileLocStr = getProperty("LOG_FILE_LOCATION",
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/").trim();
        File fileLoc = new File(fileLocStr);
        if (!fileLoc.exists()) {
            try {
                fileLoc.mkdirs();
                return fileLocStr;
            } catch (Exception e) {
                return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
            }
        } else {
            return fileLocStr;
        }
    }


    private static String getLogFileName() {
        return getProperty("LOG_FILE_NAME", "yt.log").trim();
    }
}
