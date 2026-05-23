package com.igexin.push.f.b;

import com.igexin.push.d.c;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public final class d extends f {
    public static final int a = 20160629;
    public static final long b = 604800000;
    private static final String c = "PollingTimerTask";
    private long e;
    private AtomicBoolean f;

    public static class a {
        private static final d a = new d();

        private a() {
        }
    }

    public d() {
        super(b, (byte) 0);
        this.e = com.igexin.push.config.d.y;
        this.f = new AtomicBoolean(false);
        this.p = true;
    }

    private void b(long j) {
        a(j, TimeUnit.MILLISECONDS);
    }

    private static d q() {
        return a.a;
    }

    @Override // com.igexin.c.a.d.a.e
    public final int c() {
        return a;
    }

    public final void g() {
        if (!this.f.getAndSet(true)) {
            com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) this, false, true);
        }
        b(this.e);
    }

    @Override // com.igexin.push.f.b.f
    protected final void h() {
        a(this.e, TimeUnit.MILLISECONDS);
        if (com.igexin.push.core.e.u) {
            com.igexin.c.a.c.a.b(c, "polling sendHeartbeat");
            com.igexin.push.core.a.b.d();
            com.igexin.push.core.a.b.f();
        } else if (com.igexin.push.core.e.n && com.igexin.push.core.e.p && com.igexin.push.core.e.s && com.igexin.push.g.c.a()) {
            com.igexin.c.a.c.a.a("PollingTimerTask|run = true", new Object[0]);
            com.igexin.push.d.c cVar = c.b.a;
            if (cVar.b && cVar.e != null && !(cVar.e instanceof com.igexin.push.d.d)) {
                cVar.e = new com.igexin.push.d.d();
            }
            e.g().a(c, 100L);
        }
    }

    public final void i() {
        a(b, TimeUnit.MILLISECONDS);
    }
}
