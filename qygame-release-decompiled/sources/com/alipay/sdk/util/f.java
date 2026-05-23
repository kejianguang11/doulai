package com.alipay.sdk.util;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.alipay.android.app.IAlixPay;

/* JADX INFO: loaded from: classes.dex */
final class f implements ServiceConnection {
    final /* synthetic */ e a;

    f(e eVar) {
        this.a = eVar;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.a.d) {
            this.a.c = IAlixPay.Stub.asInterface(iBinder);
            this.a.d.notify();
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.a.c = null;
    }
}
