package com.loc;

/* JADX INFO: loaded from: classes.dex */
public final class dj extends ft {
    private static int a(fs fsVar) {
        return fsVar.b();
    }

    public static int a(fs fsVar, int i) {
        fsVar.b(1);
        b(fsVar, i);
        return a(fsVar);
    }

    public static int a(fs fsVar, int[] iArr) {
        fsVar.a(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            fsVar.a(iArr[length]);
        }
        return fsVar.a();
    }

    private static void b(fs fsVar, int i) {
        fsVar.b(0, i);
    }
}
