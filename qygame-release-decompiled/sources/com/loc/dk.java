package com.loc;

/* JADX INFO: loaded from: classes.dex */
public final class dk extends ft {
    private static int a(fs fsVar) {
        return fsVar.b();
    }

    public static int a(fs fsVar, boolean z, long j, short s, int i, short s2, short s3) {
        fsVar.b(6);
        a(fsVar, j);
        a(fsVar, i);
        c(fsVar, s3);
        b(fsVar, s2);
        a(fsVar, s);
        a(fsVar, z);
        return a(fsVar);
    }

    private static void a(fs fsVar, int i) {
        fsVar.b(3, i);
    }

    private static void a(fs fsVar, long j) {
        fsVar.a(1, j);
    }

    private static void a(fs fsVar, short s) {
        fsVar.a(2, s);
    }

    private static void a(fs fsVar, boolean z) {
        fsVar.a(z);
    }

    private static void b(fs fsVar, short s) {
        fsVar.a(4, s);
    }

    private static void c(fs fsVar, short s) {
        fsVar.a(5, s);
    }
}
