package com.zx.a.I8b7;

/* JADX INFO: loaded from: classes.dex */
public class h3 implements Runnable {
    public h3(b3 b3Var, boolean z) {
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            b3.a().a(false);
        } catch (Throwable th) {
            t.b(th.getMessage());
        }
    }
}
