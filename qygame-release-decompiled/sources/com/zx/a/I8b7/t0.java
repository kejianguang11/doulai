package com.zx.a.I8b7;

/* JADX INFO: loaded from: classes.dex */
public class t0 {
    public v0 a = new v0();
    public q0 b;
    public s0 c;

    public t0() {
        s0 s0Var = new s0(new r0());
        this.c = s0Var;
        q0 q0Var = new q0(s0Var);
        this.b = q0Var;
        this.a.a(q0Var);
    }

    public void a(int i) {
        this.c.c = i + 8;
    }

    public void a(String str) {
        this.a.a(2, null, str, null);
    }

    public void a(boolean z) {
        this.b.b = z;
    }

    public void b(String str) {
        this.c.b = str;
    }
}
