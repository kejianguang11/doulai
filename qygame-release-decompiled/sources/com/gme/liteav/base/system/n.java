package com.gme.liteav.base.system;

import android.os.Build;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class n implements Callable {
    private static final n a = new n();

    private n() {
    }

    public static Callable a() {
        return a;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return Integer.valueOf(Build.VERSION.SDK_INT);
    }
}
