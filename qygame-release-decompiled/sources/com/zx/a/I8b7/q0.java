package com.zx.a.I8b7;

/* JADX INFO: loaded from: classes.dex */
public class q0 implements l0 {
    public n0 a;
    public boolean b = true;

    public q0(n0 n0Var) {
        this.a = (n0) i2.a(n0Var);
    }

    @Override // com.zx.a.I8b7.l0
    public void a(int i, String str, String str2, Throwable th) {
        if ((i & 240) != 0) {
            i &= 15;
        }
        this.a.a(i, str, str2, th);
    }

    @Override // com.zx.a.I8b7.l0
    public boolean a(int i, String str) {
        int i2 = i & 240;
        if (i2 == 0 || i2 == 16) {
            return this.b;
        }
        return false;
    }
}
