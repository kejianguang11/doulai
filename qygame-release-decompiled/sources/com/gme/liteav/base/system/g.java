package com.gme.liteav.base.system;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class g implements Callable {
    private static final g a = new g();

    private g() {
    }

    public static Callable a() {
        return a;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return LiteavSystemInfo.a();
    }
}
