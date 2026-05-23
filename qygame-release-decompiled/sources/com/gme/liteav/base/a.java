package com.gme.liteav.base;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class a implements Callable {
    private static final a a = new a();

    private a() {
    }

    public static Callable a() {
        return a;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return PathUtils.setPrivateDataDirectorySuffixInternal();
    }
}
