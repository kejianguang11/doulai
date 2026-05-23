package com.gme.liteav.base.system;

import android.os.Build;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class o implements Callable {
    private static final o a = new o();

    private o() {
    }

    public static Callable a() {
        return a;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return Build.BOARD;
    }
}
