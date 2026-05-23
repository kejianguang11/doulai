package com.igexin.push.c;

import com.igexin.push.c.a;
import com.igexin.push.c.b;
import com.igexin.push.config.SDKUrlConfig;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private static final String a = b.a + c.class.getName();
    private static c b;
    private static int c;

    private c() {
        c = com.igexin.push.g.c.b() ? b.EnumC0032b.a : b.EnumC0032b.b;
    }

    public static synchronized c a() {
        if (b == null) {
            b = new c();
        }
        return b;
    }

    public static void b() {
        if (SDKUrlConfig.hasMultipleXfr()) {
            com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) f.g(), false, true);
            return;
        }
        com.igexin.c.a.c.a.a(a + "|xfr len = 1, detect = false", new Object[0]);
    }

    public final void c() {
        if (SDKUrlConfig.hasMultipleXfr()) {
            try {
                f().e();
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
        }
    }

    public final a d() {
        return f().d;
    }

    public final void e() {
        if (SDKUrlConfig.hasMultipleXfr()) {
            try {
                j.a();
                j.k();
                j.a().g();
                g.a().g();
                h hVarF = f();
                if (hVarF != null) {
                    hVarF.i();
                    return;
                }
                return;
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
                return;
            }
        }
        j.a().f();
        g.a().f();
        f.g().i();
        try {
            g.a().d.a((List<a.b>) null);
            j.a().d.a((List<a.b>) null);
            j.a().h();
            g.a().h();
            j.a();
            j.k();
        } catch (Throwable th2) {
            com.igexin.c.a.c.a.a(th2);
        }
    }

    public final synchronized h f() {
        h hVarA;
        hVarA = com.igexin.push.g.c.b() ? j.a() : g.a();
        int iC = hVarA.c();
        if (iC != c) {
            if (iC == b.EnumC0032b.a) {
                g.a().f();
            } else if (iC == b.EnumC0032b.b) {
                j.a().f();
            }
        }
        c = iC;
        return hVarA;
    }
}
