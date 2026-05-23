package com.zx.a.I8b7;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class x3 implements Runnable {
    public final /* synthetic */ Context a;
    public final /* synthetic */ y3 b;

    public x3(y3 y3Var, Context context) {
        this.b = y3Var;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            q3.a(this.a);
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXCore init failed: "));
            this.b.b.set(false);
        }
    }
}
