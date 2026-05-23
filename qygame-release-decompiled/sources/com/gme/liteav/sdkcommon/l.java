package com.gme.liteav.sdkcommon;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class l implements Runnable {
    private final g a;

    private l(g gVar) {
        this.a = gVar;
    }

    public static Runnable a(g gVar) {
        return new l(gVar);
    }

    @Override // java.lang.Runnable
    public final void run() {
        g gVar = this.a;
        if (gVar.k != null) {
            gVar.k.fullScroll(130);
        }
    }
}
