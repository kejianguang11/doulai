package com.gme.liteav.base.system;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class h implements Callable {
    private static final h a = new h();

    private h() {
    }

    public static Callable a() {
        return a;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return LiteavSystemInfo.getForegroundServices();
    }
}
