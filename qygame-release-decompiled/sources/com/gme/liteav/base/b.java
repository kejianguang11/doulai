package com.gme.liteav.base;

import android.os.StrictMode;
import java.io.Closeable;

/* JADX INFO: loaded from: classes.dex */
public final class b implements Closeable {
    private final StrictMode.ThreadPolicy a;
    private final StrictMode.VmPolicy b;

    private b(StrictMode.ThreadPolicy threadPolicy) {
        this.a = threadPolicy;
        this.b = null;
    }

    private b(StrictMode.ThreadPolicy threadPolicy, byte b) {
        this(threadPolicy);
    }

    public static b a() {
        return new b(StrictMode.allowThreadDiskWrites(), (byte) 0);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        if (this.a != null) {
            StrictMode.setThreadPolicy(this.a);
        }
        if (this.b != null) {
            StrictMode.setVmPolicy(this.b);
        }
    }
}
