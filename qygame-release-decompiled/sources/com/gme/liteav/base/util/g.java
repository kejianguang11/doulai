package com.gme.liteav.base.util;

import android.os.Looper;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class g {
    final ThreadPoolExecutor a;
    public final CustomHandler b;
    public final List<a> c;

    public class a {
        final Runnable a;
        public final Runnable b = j.a(this);
        public final long c = 500;
        private final Runnable e;

        public a(Runnable runnable) {
            this.e = runnable;
            this.a = i.a(this, runnable);
        }
    }

    public g() {
        this((byte) 0);
    }

    private g(byte b) {
        this("SequenceTaskRunner_");
    }

    public g(String str) {
        this.a = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), h.a(str));
        this.b = new CustomHandler(Looper.getMainLooper());
        this.c = new ArrayList();
    }

    public final void a(Runnable runnable) {
        this.a.execute(runnable);
    }
}
