package com.zx.a.I8b7;

import com.zx.a.I8b7.j1;

/* JADX INFO: loaded from: classes.dex */
public class m1 implements Runnable {
    @Override // java.lang.Runnable
    public void run() {
        try {
            j1 j1Var = j1.b.a;
            j1Var.getClass();
            if (j1.i != null && j1.i.length() > 0) {
                j1.i.a(j1Var.a - 1);
                j1Var.a = j1.i.length();
            }
            j1Var.getClass();
            if (j1.j == null || j1.j.length() <= 0) {
                return;
            }
            j1.j.a(j1Var.b - 1);
            j1Var.b = j1.j.length();
        } catch (Throwable th) {
            v2.a(th);
        }
    }
}
