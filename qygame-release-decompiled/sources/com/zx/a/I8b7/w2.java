package com.zx.a.I8b7;

import com.zx.sdk.api.ZXIDListener;

/* JADX INFO: loaded from: classes.dex */
public class w2 implements Runnable {
    public final /* synthetic */ String a;
    public final /* synthetic */ ZXIDListener b;

    public w2(b3 b3Var, String str, ZXIDListener zXIDListener) {
        this.a = str;
        this.b = zXIDListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            b3.a().a(this.a, this.b);
        } catch (Throwable th) {
            ZXIDListener zXIDListener = this.b;
            if (zXIDListener != null) {
                zXIDListener.onFailed(com.igexin.push.config.c.d, th.getMessage());
            }
            k3.a(th, j3.a("ZXManager.getZXID(zxidListener) failed: "));
        }
    }
}
