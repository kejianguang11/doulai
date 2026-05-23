package com.mobile.auth.k;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private int a;
    private Map<String, List<String>> b;
    private String c;

    public b(int i, Map<String, List<String>> map, String str) {
        this.a = i;
        this.b = map;
        this.c = str;
    }

    public int a() {
        return this.a;
    }

    public Map<String, List<String>> b() {
        return this.b == null ? new HashMap() : this.b;
    }

    public String c() {
        return this.c == null ? "" : this.c;
    }

    public boolean d() {
        return this.a == 302 || this.a == 301;
    }
}
