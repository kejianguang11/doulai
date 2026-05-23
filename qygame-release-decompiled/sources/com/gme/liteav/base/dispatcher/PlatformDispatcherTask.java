package com.gme.liteav.base.dispatcher;

import android.os.Handler;
import android.os.Looper;
import com.gme.liteav.base.annotations.JNINamespace;
import com.gme.liteav.base.util.LiteavLog;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav")
class PlatformDispatcherTask implements Runnable {
    private long mNativePlatformDispatcherTask = 0;

    private void destroyTask(long j) {
        if (j != 0) {
            nativeDestroyTask(j);
        }
    }

    public static Handler getMainHandler() {
        return new Handler(Looper.getMainLooper());
    }

    private static native void nativeDestroyTask(long j);

    private static native void nativeOnTaskFineshed(long j, PlatformDispatcherTask platformDispatcherTask);

    private static native void nativeRunTask(long j);

    protected void finalize() throws Throwable {
        destroyTask(this.mNativePlatformDispatcherTask);
        this.mNativePlatformDispatcherTask = 0L;
        super.finalize();
    }

    public void resetTask(long j) {
        try {
            this.mNativePlatformDispatcherTask = j;
        } catch (Throwable th) {
            LiteavLog.e("PlatformDispatcherTask", "resetTask error.", th);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        long j = this.mNativePlatformDispatcherTask;
        this.mNativePlatformDispatcherTask = 0L;
        if (j != 0) {
            nativeRunTask(j);
            nativeOnTaskFineshed(j, this);
            destroyTask(j);
        }
    }
}
