package com.loc;

/* JADX INFO: loaded from: classes.dex */
public final class dg extends ft {
    public static int a(fs fsVar, int i, int i2, int i3, short s) {
        fsVar.b(5);
        c(fsVar, i3);
        b(fsVar, i2);
        a(fsVar, i);
        a(fsVar, s);
        a(fsVar);
        return b(fsVar);
    }

    private static void a(fs fsVar) {
        fsVar.a(0, (byte) 2);
    }

    private static void a(fs fsVar, int i) {
        fsVar.a(1, i);
    }

    private static void a(fs fsVar, short s) {
        fsVar.a(4, s);
    }

    private static int b(fs fsVar) {
        return fsVar.b();
    }

    private static void b(fs fsVar, int i) {
        fsVar.a(2, i);
    }

    private static void c(fs fsVar, int i) {
        fsVar.a(3, i);
    }
}
