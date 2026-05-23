package com.getui.gtc.dim.b;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    private static final List<String> b = Arrays.asList("dim-2-1-21-5", "dim-2-1-21-3", "dim-2-1-21-2", "dim-2-1-21-1");
    private final Map<String, h> a;

    static class a {
        private static final c a = new c(0);
    }

    private c() {
        this.a = new ConcurrentHashMap();
    }

    /* synthetic */ c(byte b2) {
        this();
    }

    public static c a() {
        return a.a;
    }

    public final h a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        h hVar = this.a.get(str);
        if (hVar == null || !(hVar.a instanceof List)) {
            return hVar;
        }
        List list = (List) hVar.a;
        return new h(list.isEmpty() ? Collections.emptyList() : new ArrayList(list), hVar.b);
    }

    public final void a(String str, Object obj, long j) {
        if (f.h(str)) {
            com.getui.gtc.dim.e.b.a(str + " skip dim ram cache = " + obj);
            return;
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (j <= 0) {
            j = System.currentTimeMillis();
        }
        h hVar = new h(obj, j);
        com.getui.gtc.dim.e.b.a(str + " update dim ram cache = " + obj);
        this.a.put(str, hVar);
    }
}
