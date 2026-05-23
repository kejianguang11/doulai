package com.gme.liteav.base.util;

import android.os.MessageQueue;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class c implements MessageQueue.IdleHandler {
    private final CustomHandler a;

    private c(CustomHandler customHandler) {
        this.a = customHandler;
    }

    public static MessageQueue.IdleHandler a(CustomHandler customHandler) {
        return new c(customHandler);
    }

    @Override // android.os.MessageQueue.IdleHandler
    public final boolean queueIdle() {
        return CustomHandler.a(this.a);
    }
}
