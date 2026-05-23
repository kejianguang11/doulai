package com.gme.liteav.sdkcommon;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class h implements Runnable {
    private final g a;

    private h(g gVar) {
        this.a = gVar;
    }

    public static Runnable a(g gVar) {
        return new h(gVar);
    }

    @Override // java.lang.Runnable
    public final void run() {
        g gVar = this.a;
        if (gVar.k != null) {
            gVar.k.fullScroll(130);
        }
    }
}
