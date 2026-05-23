package com.igexin.push.core.i;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static b b;
    private Map<Long, a> a = new HashMap();

    private b() {
    }

    public static b a() {
        if (b == null) {
            b = new b();
        }
        return b;
    }

    private void b(a aVar) {
        if (aVar != null) {
            a(aVar);
        }
    }

    private void c(a aVar) {
        if (aVar != null) {
            this.a.put(aVar.a(), aVar);
        }
    }

    public final a a(Long l) {
        return this.a.get(l);
    }

    public final void a(a aVar) {
        if (aVar != null) {
            this.a.remove(aVar.a());
        }
    }
}
