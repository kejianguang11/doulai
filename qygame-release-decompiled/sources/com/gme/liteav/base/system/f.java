package com.gme.liteav.base.system;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class f implements Callable {
    private static final f a = new f();

    private f() {
    }

    public static Callable a() {
        return a;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return q.a(LiteavSystemInfo.sAppPackageName.a());
    }
}
