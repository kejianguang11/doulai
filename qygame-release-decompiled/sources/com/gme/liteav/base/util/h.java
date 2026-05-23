package com.gme.liteav.base.util;

import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class h implements ThreadFactory {
    private final String a;

    private h(String str) {
        this.a = str;
    }

    public static ThreadFactory a(String str) {
        return new h(str);
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        return new Thread(runnable, this.a);
    }
}
