package com.igexin.push.d;

import android.content.Intent;
import android.os.Bundle;
import com.igexin.push.f.b.d;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    public static final String a = "ConnectModelCoordinator";
    private static final long i = 20000;
    private static final long j = 200000;
    public boolean b;
    public long c;
    public int d;
    public com.igexin.push.d.b e;
    private int f;
    private int g;
    private int h;
    private long k;
    private a l;

    enum a {
        WIFI,
        MOBILE
    }

    public static class b {
        private static final c a = new c(0);

        private b() {
        }
    }

    private c() {
        this.f = com.igexin.push.config.d.x;
        this.g = com.igexin.push.config.d.z;
        this.e = new d();
        this.l = com.igexin.push.g.c.b() ? a.WIFI : a.MOBILE;
    }

    /* synthetic */ c(byte b2) {
        this();
    }

    private static void a(int i2) {
        if (com.igexin.push.core.e.l == null) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction("com.igexin.sdk.action.polling");
            Bundle bundle = new Bundle();
            bundle.putInt("code", i2);
            intent.putExtras(bundle);
            intent.setPackage(com.igexin.push.core.e.l.getPackageName());
            com.igexin.push.core.e.l.sendBroadcast(intent);
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(a, th.toString());
        }
    }

    private void a(boolean z) {
        this.b = z;
        com.igexin.c.a.c.a.a("ConnectModelCoordinator|init, current is polling mdl = ".concat(String.valueOf(z)), new Object[0]);
        if (z) {
            d.a.a.g();
        }
    }

    private com.igexin.push.d.b e() {
        return this.e;
    }

    private static c f() {
        return b.a;
    }

    private void g() {
        this.c = System.currentTimeMillis();
        if (!this.b) {
            b();
            return;
        }
        com.igexin.c.a.c.a.a(a, "loginRsp| enter polling");
        this.e = new e();
        d.a.a.g();
        this.d = 0;
    }

    private void h() {
        if (!this.b || this.e == null || (this.e instanceof d)) {
            return;
        }
        this.e = new d();
    }

    private static void i() {
        a(0);
    }

    private static void j() {
        a(1);
    }

    public final synchronized void a() {
        a aVar = com.igexin.push.g.c.b() ? a.WIFI : a.MOBILE;
        if (aVar != this.l) {
            com.igexin.c.a.c.a.a(a, "net type changed " + this.l + "->" + aVar);
            com.igexin.c.a.c.a.a("ConnectModelCoordinator|net type changed " + this.l + "->" + aVar, new Object[0]);
            b();
            this.l = aVar;
        }
    }

    public final void b() {
        com.igexin.c.a.c.a.a("ConnectModelCoordinator|reset current mdl = normal", new Object[0]);
        if (this.e != null && !(this.e instanceof d)) {
            this.e = new d();
        }
        d.a.a.i();
        this.d = 0;
        this.h = 0;
        this.b = false;
        com.igexin.push.core.e.f.a().b(this.b);
    }

    public final synchronized void c() {
        if (this.b) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - this.c;
        if (jCurrentTimeMillis > i && jCurrentTimeMillis < j) {
            this.h++;
            com.igexin.c.a.c.a.a(a, "read len = -1, interval = " + jCurrentTimeMillis + ", tcpDisconnectSuccess =" + this.h);
            com.igexin.c.a.c.a.a("ConnectModelCoordinator|read len = -1, interval = " + jCurrentTimeMillis + ", tcpDisconnectSuccess =" + this.h, new Object[0]);
            if (this.h >= this.f) {
                com.igexin.c.a.c.a.a(a, "enter polling mode #####");
                com.igexin.c.a.c.a.a("ConnectModelCoordinator|enter polling mode ####", new Object[0]);
                a(0);
                this.b = true;
                this.e = new e();
                d.a.a.g();
                com.igexin.push.core.e.f.a().b(this.b);
            }
        }
    }

    public final synchronized void d() {
        if (this.b) {
            this.d++;
            com.igexin.c.a.c.a.a(a, "polling mode, cur heartbeat = " + this.d);
            com.igexin.c.a.c.a.a("ConnectModelCoordinator|polling mode, cur heartbeat =" + this.d, new Object[0]);
            if (this.d >= this.g) {
                com.igexin.c.a.c.a.a(a, "enter normal mode #####");
                com.igexin.c.a.c.a.a("ConnectModelCoordinator|enter normal mode ####", new Object[0]);
                a(1);
                com.igexin.push.core.e.a(a, 0L);
                b();
            }
        }
    }
}
