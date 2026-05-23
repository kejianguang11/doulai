package com.loc;

/* JADX INFO: loaded from: classes.dex */
public final class cz extends ft {
    private static int a(fs fsVar) {
        return fsVar.b();
    }

    public static int a(fs fsVar, int i, byte b, int i2, int i3) {
        fsVar.b(4);
        c(fsVar, i3);
        b(fsVar, i2);
        a(fsVar, i);
        a(fsVar, b);
        return a(fsVar);
    }

    public static int a(fs fsVar, int[] iArr) {
        fsVar.a(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            fsVar.a(iArr[length]);
        }
        return fsVar.a();
    }

    private static void a(fs fsVar, byte b) {
        fsVar.a(1, b);
    }

    private static void a(fs fsVar, int i) {
        fsVar.b(0, i);
    }

    public static int b(fs fsVar, int[] iArr) {
        fsVar.a(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            fsVar.a(iArr[length]);
        }
        return fsVar.a();
    }

    private static void b(fs fsVar, int i) {
        fsVar.b(2, i);
    }

    private static void c(fs fsVar, int i) {
        fsVar.b(3, i);
    }
}
