package com.igexin.push.core.d;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class b implements e {
    private static final String a = "GTConfigProxy";
    private static volatile b b;
    private e c = new a();

    private b() {
    }

    private void a(e eVar) {
        this.c = eVar;
    }

    public static b d() {
        if (b == null) {
            synchronized (b.class) {
                if (b == null) {
                    b = new b();
                }
            }
        }
        return b;
    }

    @Override // com.igexin.push.core.d.e
    public final Map<String, String> a() {
        if (this.c != null) {
            return this.c.a();
        }
        return null;
    }

    @Override // com.igexin.push.core.d.e
    public final boolean a(Map<String, String> map) {
        if (this.c != null) {
            return this.c.a(map);
        }
        return false;
    }

    @Override // com.igexin.push.core.d.e
    public final Map<String, String> b() {
        if (this.c != null) {
            return this.c.b();
        }
        return null;
    }

    @Override // com.igexin.push.core.d.e
    public final boolean c() {
        if (this.c != null) {
            return this.c.c();
        }
        return false;
    }
}
