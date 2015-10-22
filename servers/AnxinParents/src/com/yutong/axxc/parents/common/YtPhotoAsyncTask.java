
package com.yutong.axxc.parents.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Process;


public abstract class YtPhotoAsyncTask implements Runnable {

    private static final ExecutorService sExecutor = Executors.newSingleThreadExecutor();

    private boolean isCanceled;

    /**
     * 调用线程池执行线程
     * @see [类、类#方法、类#成员]
     */
    public void execute() {
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
