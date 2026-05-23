package com.gme.liteav.base.system;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class p implements Callable {
    private static final p a = new p();

    private p() {
    }

    public static Callable a() {
        return a;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return a.a();
    }
}
