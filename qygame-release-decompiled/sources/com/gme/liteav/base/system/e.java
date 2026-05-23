package com.gme.liteav.base.system;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class e implements Callable {
    private static final e a = new e();

    private e() {
    }

    public static Callable a() {
        return a;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return a.c();
    }
}
