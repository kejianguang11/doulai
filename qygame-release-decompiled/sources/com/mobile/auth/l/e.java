package com.mobile.auth.l;

import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class e {
    private static ConcurrentHashMap<String, com.mobile.auth.e.b> a = new ConcurrentHashMap<>(16);
    private static ConcurrentHashMap<String, com.cmic.sso.sdk.a> b = new ConcurrentHashMap<>();

    public static void a(String str, com.cmic.sso.sdk.a aVar) {
        if (str == null || aVar == null) {
            return;
        }
        b.put(str, aVar);
    }

    public static void a(String str, com.mobile.auth.e.b bVar) {
        a.put(str, bVar);
    }

    public static boolean a() {
        return a.isEmpty();
    }

    public static boolean a(String str) {
        return !a.containsKey(str);
    }

    public static void b(String str) {
        a.remove(str);
    }

    public static com.mobile.auth.e.b c(String str) {
        return a.get(str);
    }

    public static com.cmic.sso.sdk.a d(String str) {
        return str != null ? b.get(str) : new com.cmic.sso.sdk.a(0);
    }
}
