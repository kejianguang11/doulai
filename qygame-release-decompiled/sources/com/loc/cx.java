package com.loc;

/* JADX INFO: loaded from: classes.dex */
public final class cx extends ft {
    private static int a(fs fsVar) {
        return fsVar.b();
    }

    public static int a(fs fsVar, int i, int i2, int i3) {
        fsVar.b(3);
        c(fsVar, i3);
        b(fsVar, i2);
        a(fsVar, i);
        return a(fsVar);
    }

    public static int a(fs fsVar, byte[] bArr) {
        fsVar.a(1, bArr.length, 1);
        for (int length = bArr.length - 1; length >= 0; length--) {
            fsVar.a(bArr[length]);
        }
        return fsVar.a();
    }

    public static int a(fs fsVar, int[] iArr) {
        fsVar.a(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            fsVar.a(iArr[length]);
        }
        return fsVar.a();
    }

    private static void a(fs fsVar, int i) {
        fsVar.b(0, i);
    }

    public static int b(fs fsVar, byte[] bArr) {
        fsVar.a(1, bArr.length, 1);
        for (int length = bArr.length - 1; length >= 0; length--) {
            fsVar.a(bArr[length]);
        }
        return fsVar.a();
    }

    private static void b(fs fsVar, int i) {
        fsVar.b(1, i);
    }

    private static void c(fs fsVar, int i) {
        fsVar.b(2, i);
    }
}
