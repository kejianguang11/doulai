package com.gme.liteav.base.util;

import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class b implements Runnable {
    private final Runnable a;
    private final CountDownLatch b;

    private b(Runnable runnable, CountDownLatch countDownLatch) {
        this.a = runnable;
        this.b = countDownLatch;
    }

    public static Runnable a(Runnable runnable, CountDownLatch countDownLatch) {
        return new b(runnable, countDownLatch);
    }

    @Override // java.lang.Runnable
    public final void run() {
        CustomHandler.a(this.a, this.b);
    }
}
