package com.loc;

/* JADX INFO: loaded from: classes.dex */
public final class dc extends ft {
    private static int a(fs fsVar) {
        return fsVar.b();
    }

    public static int a(fs fsVar, byte b, int i) {
        fsVar.b(2);
        a(fsVar, i);
        a(fsVar, b);
        return a(fsVar);
    }

    public static int a(fs fsVar, byte[] bArr) {
        fsVar.a(1, bArr.length, 1);
        for (int length = bArr.length - 1; length >= 0; length--) {
            fsVar.a(bArr[length]);
        }
        return fsVar.a();
    }

    private static void a(fs fsVar, byte b) {
        fsVar.a(0, b);
    }

    private static void a(fs fsVar, int i) {
        fsVar.b(1, i);
    }
}
