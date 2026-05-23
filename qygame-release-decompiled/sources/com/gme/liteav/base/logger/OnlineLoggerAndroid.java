package com.gme.liteav.base.logger;

import com.gme.liteav.base.annotations.JNINamespace;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav")
public class OnlineLoggerAndroid {
    private static final int INVALID_INSTANCE = -1;
    private long mNativeOnlineLoggerAndroid;

    public enum a {
        kTRTC(0),
        kLive(1),
        kVod(2);

        int e;

        a(int i) {
            this.e = i;
        }
    }

    public enum b {
        kApi(1),
        kInfo(2),
        kWarning(3),
        kError(4);

        int f;

        b(int i) {
            this.f = i;
        }
    }

    public OnlineLoggerAndroid(a aVar, int i, String str, String str2) {
        this.mNativeOnlineLoggerAndroid = -1L;
        this.mNativeOnlineLoggerAndroid = nativeCreate(aVar.e, i, str, str2);
    }

    private static native long nativeCreate(int i, int i2, String str, String str2);

    private static native void nativeDestroy(long j);

    private static native void nativeLog(long j, int i, String str);

    public synchronized void destroy() {
        if (this.mNativeOnlineLoggerAndroid == -1) {
            return;
        }
        nativeDestroy(this.mNativeOnlineLoggerAndroid);
        this.mNativeOnlineLoggerAndroid = -1L;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        destroy();
    }

    public synchronized void log(b bVar, String str) {
        if (this.mNativeOnlineLoggerAndroid == -1) {
            return;
        }
        nativeLog(this.mNativeOnlineLoggerAndroid, bVar.f, str);
    }
}
