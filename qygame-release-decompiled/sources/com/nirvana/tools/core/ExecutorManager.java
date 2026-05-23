package com.nirvana.tools.core;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class ExecutorManager {
    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 4;
    private static final String TAG = "NIRVANA_EXECUTOR";
    private static volatile ExecutorManager mInstance;
    private Handler mMainHandler;
    private HashMap<String, Worker> mWorkerQueue = new HashMap<>();
    private ScheduledThreadPoolExecutor threadExecutor = new ScheduledThreadPoolExecutor(4, new ThreadFactory() { // from class: com.nirvana.tools.core.ExecutorManager.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "nirvana_base_executor_queue_" + System.currentTimeMillis());
        }
    }, new ThreadPoolExecutor.CallerRunsPolicy());

    public static abstract class SafeRunnable implements Runnable {
        protected void onException(Throwable th) {
        }

        protected void onFinal() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                safeRun();
            } catch (Throwable th) {
                onException(th);
            } finally {
                onFinal();
            }
        }

        protected abstract void safeRun();
    }

    public ExecutorManager() {
        this.threadExecutor.setKeepAliveTime(2L, TimeUnit.SECONDS);
        this.threadExecutor.setMaximumPoolSize(4);
        this.threadExecutor.allowCoreThreadTimeOut(true);
        this.mMainHandler = new Handler(Looper.getMainLooper());
    }

    public static String getErrorInfoFromException(Throwable th) {
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            String string = stringWriter.toString();
            stringWriter.close();
            printWriter.close();
            return string;
        } catch (Exception unused) {
            return "ErrorInfoFromException";
        }
    }

    public static ExecutorManager getInstance() {
        if (mInstance == null) {
            synchronized (ThreadPoolExecutor.class) {
                if (mInstance == null) {
                    mInstance = new ExecutorManager();
                }
            }
        }
        return mInstance;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        release();
    }

    public Worker getWorker(String str) {
        Worker worker;
        synchronized (this.mWorkerQueue) {
            worker = this.mWorkerQueue.get(str);
        }
        return worker;
    }

    public void postMain(Runnable runnable) {
        this.mMainHandler.post(runnable);
    }

    public void postMain(Runnable runnable, long j) {
        this.mMainHandler.postDelayed(runnable, j);
    }

    public void quitWorker(Worker worker) {
        if (worker != null) {
            quitWorker(worker.getWorkerId());
        }
    }

    public void quitWorker(String str) {
        synchronized (this.mWorkerQueue) {
            Worker workerRemove = this.mWorkerQueue.remove(str);
            if (workerRemove != null) {
                workerRemove.release();
            }
        }
    }

    public Worker registerWorker(String str) {
        synchronized (this.mWorkerQueue) {
            if (this.mWorkerQueue.containsKey(str)) {
                return getWorker(str);
            }
            Worker worker = new Worker(str);
            this.mWorkerQueue.put(str, worker);
            return worker;
        }
    }

    public void release() {
        if (mInstance != null) {
            synchronized (ThreadPoolExecutor.class) {
                if (mInstance != null) {
                    mInstance.threadExecutor.shutdown();
                }
            }
        }
    }

    public void removeFromMain(Runnable runnable) {
        this.mMainHandler.removeCallbacks(runnable);
    }

    public void removeFromThread(Runnable runnable) {
        this.threadExecutor.remove(runnable);
    }

    public RunnableScheduledFuture<?> scheduleFuture(Runnable runnable) {
        return scheduleFutureDelay(runnable, 0L);
    }

    public RunnableScheduledFuture<?> scheduleFutureDelay(Runnable runnable, long j) {
        if (j < 0) {
            j = 0;
        }
        return (RunnableScheduledFuture) this.threadExecutor.schedule(runnable, j, TimeUnit.MILLISECONDS);
    }
}
