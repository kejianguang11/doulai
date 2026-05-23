package com.zx.a.I8b7;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class v2 {
    public final t0 a;

    public static class a {
        public static final v2 a = new v2();
    }

    public v2() {
        Context context = q3.a;
        t0 t0Var = new t0();
        this.a = t0Var;
        t0Var.b("zx_tag");
        t0Var.a(false);
        t0Var.a(1);
    }

    public static void a(String str) {
        try {
            a.a.a.a.a(2, null, str, null);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(String str, Throwable th) {
        try {
            a.a.a.a.a(5, null, str, th);
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public static void a(Throwable th) {
        try {
            a.a.a.a.a(5, null, null, th);
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public static void b(String str) {
        try {
            a.a.a.a.a(5, null, str, null);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
