package com.igexin.push.f.b;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class a extends f {
    private static volatile a b;
    private final List<c> a;

    private a() {
        super(60000L, (byte) 0);
        this.p = true;
        this.a = new ArrayList();
    }

    public static a g() {
        if (b == null) {
            synchronized (a.class) {
                if (b == null) {
                    b = new a();
                }
            }
        }
        return b;
    }

    private void i() {
        a(360000L, TimeUnit.MILLISECONDS);
    }

    public final boolean a(c cVar, boolean z) {
        if (this.a == null || this.a.contains(cVar)) {
            return false;
        }
        if (z) {
            cVar.b();
            cVar.a(System.currentTimeMillis());
        }
        return this.a.add(cVar);
    }

    @Override // com.igexin.c.a.d.a.e
    public final int c() {
        return 0;
    }

    @Override // com.igexin.push.f.b.f
    protected final void h() {
        Thread.currentThread().getName();
        com.igexin.push.core.a.b.d();
        com.igexin.push.core.a.b.k();
        for (c cVar : this.a) {
            if (cVar.d()) {
                cVar.b();
                cVar.a(System.currentTimeMillis());
            }
        }
        a(360000L, TimeUnit.MILLISECONDS);
        com.igexin.c.a.b.e.a().a((Object) this);
    }
}
