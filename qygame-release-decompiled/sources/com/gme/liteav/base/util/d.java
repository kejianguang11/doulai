package com.gme.liteav.base.util;

import android.os.MessageQueue;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class d implements Runnable {
    private final CustomHandler a;
    private final MessageQueue.IdleHandler b;

    private d(CustomHandler customHandler, MessageQueue.IdleHandler idleHandler) {
        this.a = customHandler;
        this.b = idleHandler;
    }

    public static Runnable a(CustomHandler customHandler, MessageQueue.IdleHandler idleHandler) {
        return new d(customHandler, idleHandler);
    }

    @Override // java.lang.Runnable
    public final void run() {
        CustomHandler.a(this.a, this.b);
    }
}
