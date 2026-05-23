package com.igexin.push.f.b;

import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public abstract class f extends com.igexin.c.a.d.f {
    long d;

    private f(long j) {
        super(5);
        this.d = j;
        a(this.d, TimeUnit.MILLISECONDS);
    }

    public f(long j, byte b) {
        this(j);
    }

    public f(long j, char c) {
        this(j);
        this.o = true;
    }

    @Override // com.igexin.c.a.d.f, com.igexin.c.a.d.a.f
    public final void b_() throws Exception {
        super.b_();
        h();
    }

    @Override // com.igexin.c.a.d.f
    public final void e() {
    }

    @Override // com.igexin.c.a.d.f
    public final void f() {
    }

    protected abstract void h();
}
