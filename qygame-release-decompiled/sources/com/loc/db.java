package com.loc;

/* JADX INFO: loaded from: classes.dex */
public final class db extends ft {
    private static int a(fs fsVar) {
        return fsVar.b();
    }

    public static int a(fs fsVar, byte b, byte b2, short s, byte b3, int i) {
        fsVar.b(5);
        a(fsVar, i);
        a(fsVar, s);
        c(fsVar, b3);
        b(fsVar, b2);
        a(fsVar, b);
        return a(fsVar);
    }

    private static void a(fs fsVar, byte b) {
        fsVar.a(0, b);
    }

    private static void a(fs fsVar, int i) {
        fsVar.b(4, i);
    }

    private static void a(fs fsVar, short s) {
        fsVar.a(2, s);
    }

    private static void b(fs fsVar, byte b) {
        fsVar.a(1, b);
    }

    private static void c(fs fsVar, byte b) {
        fsVar.a(3, b);
    }
}
