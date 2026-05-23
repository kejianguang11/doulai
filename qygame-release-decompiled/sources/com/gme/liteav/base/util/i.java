package com.gme.liteav.base.util;

import com.gme.liteav.base.util.g;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class i implements Runnable {
    private final g.a a;
    private final Runnable b;

    private i(g.a aVar, Runnable runnable) {
        this.a = aVar;
        this.b = runnable;
    }

    public static Runnable a(g.a aVar, Runnable runnable) {
        return new i(aVar, runnable);
    }

    @Override // java.lang.Runnable
    public final void run() {
        g.a aVar = this.a;
        this.b.run();
        synchronized (g.this) {
            g.this.c.remove(aVar);
        }
    }
}
