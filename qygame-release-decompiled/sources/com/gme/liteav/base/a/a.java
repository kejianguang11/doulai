package com.gme.liteav.base.a;

import android.os.SystemClock;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    private long a = 0;
    private final long b = 1000;

    public final boolean a() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (this.a != 0 && jElapsedRealtime - this.a <= this.b) {
            return false;
        }
        this.a = SystemClock.elapsedRealtime();
        return true;
    }
}
