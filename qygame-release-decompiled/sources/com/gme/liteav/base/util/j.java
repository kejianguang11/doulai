package com.gme.liteav.base.util;

import com.gme.liteav.base.util.g;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class j implements Runnable {
    private final g.a a;

    private j(g.a aVar) {
        this.a = aVar;
    }

    public static Runnable a(g.a aVar) {
        return new j(aVar);
    }

    @Override // java.lang.Runnable
    public final void run() {
        g.a aVar = this.a;
        g.this.a.execute(aVar.a);
    }
}
