package com.gme.liteav.audio2;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class f implements Runnable {
    private final e a;

    private f(e eVar) {
        this.a = eVar;
    }

    public static Runnable a(e eVar) {
        return new f(eVar);
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.d();
    }
}
