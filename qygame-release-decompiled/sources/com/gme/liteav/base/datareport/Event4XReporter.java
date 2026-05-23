package com.gme.liteav.base.datareport;

import com.gme.liteav.base.annotations.JNINamespace;
import com.igexin.push.config.c;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav")
public class Event4XReporter {
    private static final int INVALID_INSTANCE = 0;
    public static final int REPORT_EVENT = 1;
    public static final int REPORT_STATUS = 2;
    private static final String TAG = "Event4XReporter";
    private long mNativeEvent4XReporterAndroid;

    public Event4XReporter(int i, int i2, String str, boolean z, int i3) {
        this.mNativeEvent4XReporterAndroid = 0L;
        this.mNativeEvent4XReporterAndroid = nativeCreate(i, i2, str, z, i3);
    }

    private static native long nativeCreate(int i, int i2, String str, boolean z, int i3);

    private static native void nativeDestroy(long j);

    private static native int nativeGetColdDownTime(long j);

    private static native void nativeSendReport(long j);

    private static native void nativeSetCommonIntValue(long j, String str, long j2);

    private static native void nativeSetCommonStringValue(long j, String str, String str2);

    private static native void nativeSetEventIntValue(long j, String str, long j2);

    private static native void nativeSetEventStringValue(long j, String str, String str2);

    public synchronized void destroy() {
        if (this.mNativeEvent4XReporterAndroid == 0) {
            return;
        }
        nativeDestroy(this.mNativeEvent4XReporterAndroid);
        this.mNativeEvent4XReporterAndroid = 0L;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        destroy();
    }

    public int getColdDownTime() {
        return this.mNativeEvent4XReporterAndroid == 0 ? c.d : nativeGetColdDownTime(this.mNativeEvent4XReporterAndroid);
    }

    public synchronized void reportDau(int i, int i2, String str) {
        if (this.mNativeEvent4XReporterAndroid == 0) {
            return;
        }
        nativeSetEventStringValue(this.mNativeEvent4XReporterAndroid, "event_id", String.valueOf(i));
        nativeSetEventStringValue(this.mNativeEvent4XReporterAndroid, "err_code", String.valueOf(i2));
        nativeSetEventStringValue(this.mNativeEvent4XReporterAndroid, "err_info", str);
        nativeSendReport(this.mNativeEvent4XReporterAndroid);
    }

    public synchronized void sendReport() {
        if (this.mNativeEvent4XReporterAndroid == 0) {
            return;
        }
        nativeSendReport(this.mNativeEvent4XReporterAndroid);
    }

    public synchronized void setCommonIntValue(String str, long j) {
        if (this.mNativeEvent4XReporterAndroid != 0 && str != null) {
            nativeSetCommonIntValue(this.mNativeEvent4XReporterAndroid, str, j);
        }
    }

    public synchronized void setCommonStringValue(String str, String str2) {
        if (this.mNativeEvent4XReporterAndroid != 0 && str != null && str2 != null) {
            nativeSetCommonStringValue(this.mNativeEvent4XReporterAndroid, str, str2);
        }
    }

    public synchronized void setEventIntValue(String str, long j) {
        if (this.mNativeEvent4XReporterAndroid != 0 && str != null) {
            nativeSetEventIntValue(this.mNativeEvent4XReporterAndroid, str, j);
        }
    }

    public synchronized void setEventStringValue(String str, String str2) {
        if (this.mNativeEvent4XReporterAndroid != 0 && str != null && str2 != null) {
            nativeSetEventStringValue(this.mNativeEvent4XReporterAndroid, str, str2);
        }
    }
}
