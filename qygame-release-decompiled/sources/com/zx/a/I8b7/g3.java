package com.zx.a.I8b7;

import com.zx.a.I8b7.p2;

/* JADX INFO: loaded from: classes.dex */
public class g3 implements Runnable {
    public final /* synthetic */ boolean a;

    public g3(b3 b3Var, boolean z) {
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            z3 z3Var = p2.a.a.a;
            boolean z = this.a;
            z3Var.getClass();
            if (z != q3.u) {
                q3.u = z ? 1 : 0;
                z3Var.a(20, q3.u + "", false);
            }
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXManager.allowPermissionDialog failed: "));
        }
    }
}
