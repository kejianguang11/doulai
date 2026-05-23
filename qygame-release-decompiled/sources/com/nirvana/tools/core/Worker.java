package com.nirvana.tools.core;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;

/* JADX INFO: loaded from: classes.dex */
public class Worker {
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private String mWorkerId;

    Worker(String str) {
        this.mHandlerThread = null;
        this.mHandlerThread = new HandlerThread("nirvana_base_worker_".concat(String.valueOf(str)));
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
        this.mWorkerId = str;
    }

    private void doRelease() {
        if (this.mHandlerThread != null) {
            if (Build.VERSION.SDK_INT >= 18) {
                this.mHandlerThread.quitSafely();
            } else {
                this.mHandlerThread.quit();
            }
            this.mHandlerThread = null;
            this.mHandler = null;
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        doRelease();
    }

    String getWorkerId() {
        return this.mWorkerId;
    }

    public synchronized void post(Runnable runnable) {
        if (this.mHandler != null) {
            this.mHandler.post(runnable);
        }
    }

    public synchronized void postDelayed(Runnable runnable, long j) {
        if (this.mHandler != null) {
            if (j > 0) {
                this.mHandler.postDelayed(runnable, j);
                return;
            }
            this.mHandler.post(runnable);
        }
    }

    synchronized void release() {
        doRelease();
    }
}
