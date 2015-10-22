
package com.yutong.clw.ygbclient.common;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.os.Process;

/**
 * 线程抽象类
 * @author zhangzhia 2013-9-11 下午5:18:33
 *
 */
public abstract class YtAsyncTask implements Runnable {
    /** 核心线程数 */
    private static final int CORE_POOL_SIZE = 5;

    /** 最大线程数 */
    private static final int MAXIMUM_POOL_SIZE = 128;

    /** 超时时间，当线程数超过核心线程数时，超过这个时间的空线程就会被销毁，直到线程数等于核心线程 */
    private static final int KEEP_ALIVE = 1;

    /** 用于传输和保持提交的任务。可以使用此队列与池大小进行交互 */
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(10);

    /** Executor */
    public static final Executor sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue);
    
    /** 实例对象日志名称 */
    public String logTypeName = "";
    
    private boolean isCanceled;

    

    public void log(char type, String logMsg) {
        switch(type)
        {
        case 'i':
            Logger.i(this.getClass(), logTypeName + logMsg);
            break;
        case 'd':
            Logger.d(this.getClass(), logTypeName + logMsg);
            break;
        case 'e':
            Logger.e(this.getClass(), logTypeName + logMsg);
            break;
        case 'v':
            Logger.v(this.getClass(), logTypeName + logMsg);
            break;
        case 'w':
            Logger.w(this.getClass(), logTypeName + logMsg);
            break;
        default:
            Logger.i(this.getClass(), logTypeName + logMsg);
            break;
        }
        
    }
    
    /**
     * 调用线程池执行线程
     * @see [类、类#方法、类#成员]
     */
    public void execute() {
        Logger.i(this.getClass(), logTypeName + "启动异步任务");
        sExecutor.execute(this);
    }

    /**
     * 重载方法
     */
    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        doInBackground();
    }

    public void cancel() {
        isCanceled = true;
        Thread.interrupted();
        
        
    }

    /**
     * @return Returns the isCanceled.
     */
    public boolean isCanceled() {
        return isCanceled;
    }

    /**
     * 抽象方法，子类实现具体业务逻辑
     */
    protected abstract void doInBackground();
}
