package com.igexin.push.f.b;

import com.igexin.push.core.j;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class b extends f {
    public static final int a = -2147483642;
    private static final String b = "HeartBeatTimerTask";
    private static b c;

    public b() {
        super(j.a().b(), (byte) 0);
        this.p = true;
    }

    public static b g() {
        if (c == null) {
            c = new b();
        }
        return c;
    }

    private static void q() {
    }

    @Override // com.igexin.c.a.d.a.e
    public final int c() {
        return a;
    }

    @Override // com.igexin.c.a.d.f, com.igexin.c.a.d.a.f
    public final void d() {
    }

    @Override // com.igexin.c.a.d.f, com.igexin.c.a.d.a.f
    public final void d_() {
        super.d_();
        if (this.m) {
            return;
        }
        i();
    }

    @Override // com.igexin.push.f.b.f
    protected final void h() {
        com.igexin.push.core.a.b.d();
        com.igexin.push.core.a.b.k();
        com.igexin.push.core.e.P = System.currentTimeMillis();
        if (!com.igexin.push.core.e.u) {
            com.igexin.c.a.c.a.a("HeartBeatTimerTask doTaskMethod isOnline = false, refresh wait time !!!!!!", new Object[0]);
            i();
        } else {
            System.currentTimeMillis();
            com.igexin.c.a.c.a.a("heartbeatReq", new Object[0]);
            com.igexin.push.core.a.b.d();
            com.igexin.push.core.a.b.f();
        }
    }

    public final void i() {
        a(j.a().b(), TimeUnit.MILLISECONDS);
    }
}
