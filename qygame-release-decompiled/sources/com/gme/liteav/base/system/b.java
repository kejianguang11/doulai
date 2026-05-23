package com.gme.liteav.base.system;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class b implements Callable {
    private static final b a = new b();

    private b() {
    }

    public static Callable a() {
        return a;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return a.d();
    }
}
