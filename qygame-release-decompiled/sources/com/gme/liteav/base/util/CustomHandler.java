package com.gme.liteav.base.util;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import com.gme.liteav.base.annotations.JNINamespace;
import com.gme.liteav.base.system.LiteavSystemInfo;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav")
public class CustomHandler extends Handler {
    private static final long TIMEOUT_QUIT_LOOPER = TimeUnit.SECONDS.toMillis(30);
    private Runnable mQuitLooperTimeoutRunnable;
    private final String mTAG;
    private final Handler mUIHandler;

    public CustomHandler(Looper looper) {
        this(looper, null);
    }

    public CustomHandler(Looper looper, Handler.Callback callback) {
        String str;
        Throwable th;
        super(looper, callback);
        this.mUIHandler = new Handler(Looper.getMainLooper());
        this.mQuitLooperTimeoutRunnable = new Runnable() { // from class: com.gme.liteav.base.util.CustomHandler.1
            @Override // java.lang.Runnable
            public final void run() {
                LiteavLog.e(CustomHandler.this.mTAG, "quit looper failed.");
            }
        };
        try {
            str = "TXCHandler_" + hashCode();
        } catch (Throwable th2) {
            str = "TXCHandler";
            th = th2;
        }
        try {
            LiteavLog.i(str, "[" + Thread.currentThread().getName() + "]");
        } catch (Throwable th3) {
            th = th3;
            LiteavLog.e("CustomHandler", "init failed.", th);
        }
        this.mTAG = str;
    }

    static /* synthetic */ void a(CustomHandler customHandler, MessageQueue.IdleHandler idleHandler) {
        if (customHandler.getLooper() == Looper.getMainLooper()) {
            LiteavLog.e(customHandler.mTAG, "try to quitLooper main looper!");
        } else {
            LiteavLog.i(customHandler.mTAG, "add idle handle.");
            Looper.myQueue().addIdleHandler(idleHandler);
        }
    }

    static /* synthetic */ void a(Runnable runnable, CountDownLatch countDownLatch) {
        runnable.run();
        countDownLatch.countDown();
    }

    static /* synthetic */ boolean a(CustomHandler customHandler) {
        LiteavLog.i(customHandler.mTAG, "queue idle handle.");
        if (LiteavSystemInfo.getSystemOSVersionInt() >= 18) {
            customHandler.getLooper().quitSafely();
        } else {
            customHandler.getLooper().quit();
        }
        customHandler.mUIHandler.removeCallbacks(customHandler.mQuitLooperTimeoutRunnable);
        return false;
    }

    static /* synthetic */ void b(Runnable runnable, CountDownLatch countDownLatch) {
        runnable.run();
        countDownLatch.countDown();
    }

    public boolean isCurrentThread() {
        try {
            if (Looper.myLooper() != null) {
                if (Looper.myLooper() == getLooper()) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            LiteavLog.e("CustomHandler", "isCurrentThread failed.", th);
            return false;
        }
    }

    public boolean postDelayedTask(Runnable runnable, long j) {
        try {
            return postDelayed(runnable, j);
        } catch (Throwable th) {
            LiteavLog.e("CustomHandler", "postDelayedTask failed.", th);
            return false;
        }
    }

    public boolean postTask(Runnable runnable) {
        try {
            return post(runnable);
        } catch (Throwable th) {
            LiteavLog.e("CustomHandler", "postTask failed.", th);
            return false;
        }
    }

    public void quitLooper() {
        try {
            post(d.a(this, c.a(this)));
            this.mUIHandler.postDelayed(this.mQuitLooperTimeoutRunnable, TIMEOUT_QUIT_LOOPER);
        } catch (Throwable th) {
            LiteavLog.e("CustomHandler", "quitLooper failed.", th);
        }
    }

    public void quitLooperAndWaitDone() {
        quitLooper();
        try {
            getLooper().getThread().join();
        } catch (InterruptedException unused) {
        }
    }

    public boolean runAndWaitDone(Runnable runnable) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        boolean zPost = post(a.a(runnable, countDownLatch));
        if (zPost) {
            try {
                countDownLatch.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
        return zPost;
    }

    public boolean runAndWaitDone(Runnable runnable, long j) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        boolean zPost = post(b.a(runnable, countDownLatch));
        if (zPost) {
            try {
                countDownLatch.await(j, TimeUnit.MILLISECONDS);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
        return zPost;
    }

    public boolean runOrPost(Runnable runnable) {
        return runOrPost(runnable, 0);
    }

    public boolean runOrPost(Runnable runnable, int i) {
        if (!getLooper().getThread().isAlive()) {
            return false;
        }
        if (Looper.myLooper() != getLooper() || i != 0) {
            return i == 0 ? post(runnable) : postDelayed(runnable, i);
        }
        runnable.run();
        return true;
    }
}
