package com.gme.liteav.base.system;

import android.os.Build;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class k implements Callable {
    private static final k a = new k();

    private k() {
    }

    public static Callable a() {
        return a;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return Build.MANUFACTURER;
    }
}
