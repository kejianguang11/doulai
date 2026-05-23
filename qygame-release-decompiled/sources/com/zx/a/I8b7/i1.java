package com.zx.a.I8b7;

import com.zx.a.I8b7.a4;

/* JADX INFO: loaded from: classes.dex */
public class i1 implements Runnable {
    public final /* synthetic */ long a;
    public final /* synthetic */ j1 b;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                i1.this.b.g++;
                i1 i1Var = i1.this;
                j1 j1Var = i1Var.b;
                j1.a(j1Var, i1Var.a, j1Var.g);
                i1.this.b.f++;
                j1.a(i1.this.b);
            } catch (Throwable th) {
                v2.a(th);
            }
        }
    }

    public i1(j1 j1Var, long j) {
        this.b = j1Var;
        this.a = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            a4.f.a.e.execute(new a());
        } catch (Throwable th) {
            v2.a(th);
        }
    }
}
