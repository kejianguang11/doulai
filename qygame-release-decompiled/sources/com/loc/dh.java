package com.loc;

/* JADX INFO: loaded from: classes.dex */
public final class dh extends ft {
    public static int a(fs fsVar, int i, int i2, short s) {
        fsVar.b(4);
        b(fsVar, i2);
        a(fsVar, i);
        a(fsVar, s);
        a(fsVar);
        return b(fsVar);
    }

    private static void a(fs fsVar) {
        fsVar.a(0, (byte) 1);
    }

    private static void a(fs fsVar, int i) {
        fsVar.a(1, i);
    }

    private static void a(fs fsVar, short s) {
        fsVar.a(3, s);
    }

    private static int b(fs fsVar) {
        return fsVar.b();
    }

    private static void b(fs fsVar, int i) {
        fsVar.a(2, i);
    }
}
