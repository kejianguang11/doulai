package com.gme.liteav.base;

import com.gme.liteav.base.annotations.JNINamespace;
import java.lang.Thread;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("base::android")
public class JavaExceptionReporter implements Thread.UncaughtExceptionHandler {
    static final /* synthetic */ boolean a = !JavaExceptionReporter.class.desiredAssertionStatus();
    private final boolean mCrashAfterReport;
    private boolean mHandlingException;
    private final Thread.UncaughtExceptionHandler mParent;

    private JavaExceptionReporter(Thread.UncaughtExceptionHandler uncaughtExceptionHandler, boolean z) {
        this.mParent = uncaughtExceptionHandler;
        this.mCrashAfterReport = z;
    }

    private static void installHandler(boolean z) {
        Thread.setDefaultUncaughtExceptionHandler(new JavaExceptionReporter(Thread.getDefaultUncaughtExceptionHandler(), z));
    }

    public static void reportStackTrace(String str) {
        if (!a && !ThreadUtils.runningOnUiThread()) {
            throw new AssertionError();
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        if (!this.mHandlingException) {
            this.mHandlingException = true;
        }
        if (this.mParent != null) {
            this.mParent.uncaughtException(thread, th);
        }
    }
}
