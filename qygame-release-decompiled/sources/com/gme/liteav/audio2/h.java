package com.gme.liteav.audio2;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class h implements Executor {
    private final e a;

    private h(e eVar) {
        this.a = eVar;
    }

    public static Executor a(e eVar) {
        return new h(eVar);
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        this.a.f.a(runnable);
    }
}
