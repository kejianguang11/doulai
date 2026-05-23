package com.gme.liteav.audio2;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class b implements Runnable {
    private final AndroidInterruptedStateListener a;

    private b(AndroidInterruptedStateListener androidInterruptedStateListener) {
        this.a = androidInterruptedStateListener;
    }

    public static Runnable a(AndroidInterruptedStateListener androidInterruptedStateListener) {
        return new b(androidInterruptedStateListener);
    }

    @Override // java.lang.Runnable
    public final void run() {
        AndroidInterruptedStateListener.a(this.a);
    }
}
