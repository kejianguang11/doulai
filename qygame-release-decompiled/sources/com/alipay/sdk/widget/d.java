package com.alipay.sdk.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
final class d extends Handler {
    final /* synthetic */ a a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    d(a aVar, Looper looper) {
        super(looper);
        this.a = aVar;
    }

    @Override // android.os.Handler
    public final void dispatchMessage(Message message) {
        this.a.b();
    }
}
