package com.gme.liteav.audio2;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class a implements Runnable {
    private final AndroidInterruptedStateListener a;

    private a(AndroidInterruptedStateListener androidInterruptedStateListener) {
        this.a = androidInterruptedStateListener;
    }

    public static Runnable a(AndroidInterruptedStateListener androidInterruptedStateListener) {
        return new a(androidInterruptedStateListener);
    }

    @Override // java.lang.Runnable
    public final void run() {
        AndroidInterruptedStateListener.b(this.a);
    }
}
