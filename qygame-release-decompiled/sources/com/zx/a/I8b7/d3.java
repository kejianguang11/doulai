package com.zx.a.I8b7;

import com.zx.sdk.api.Callback;

/* JADX INFO: loaded from: classes.dex */
public class d3 implements Runnable {
    public final /* synthetic */ String a;
    public final /* synthetic */ Callback b;

    public d3(b3 b3Var, String str, Callback callback) {
        this.a = str;
        this.b = callback;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            b3.a().b(this.a, this.b);
        } catch (Throwable th) {
            Callback callback = this.b;
            if (callback != null) {
                callback.onFailed(com.igexin.push.config.c.d, th.getMessage());
            }
            k3.a(th, j3.a("ZXManager.getTag() failed: "));
        }
    }
}
