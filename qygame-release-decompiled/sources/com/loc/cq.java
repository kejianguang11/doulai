package com.loc;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class cq extends cn {
    private static cq b = new cq();

    private cq() {
        super(5120);
    }

    private static String a(String str) {
        return str == null ? "" : str;
    }

    public static cq b() {
        return b;
    }

    public final byte[] a(byte[] bArr, byte[] bArr2, List<? extends cu> list) {
        if (list == null) {
            return null;
        }
        try {
            int size = list.size();
            if (size <= 0 || bArr == null) {
                return null;
            }
            a();
            int iA = cx.a((fs) this.a, bArr);
            int[] iArr = new int[size];
            for (int i = 0; i < size; i++) {
                cu cuVar = list.get(i);
                iArr[i] = dc.a(this.a, (byte) cuVar.a(), dc.a(this.a, cuVar.b()));
            }
            this.a.c(cx.a(this.a, iA, bArr2 != null ? cx.b(this.a, bArr2) : 0, cx.a(this.a, iArr)));
            return this.a.c();
        } catch (Throwable th) {
            eb.a(th);
            return null;
        }
    }

    public final byte[] c() {
        super.a();
        try {
            this.a.c(ea.a(this.a, dz.a(), this.a.a(dz.f()), this.a.a(dz.c()), (byte) dz.m(), this.a.a(dz.i()), this.a.a(dz.h()), this.a.a(a(dz.g())), this.a.a(a(dz.j())), dy.a(dz.n()), this.a.a(dz.l()), this.a.a(dz.k()), this.a.a(dz.d()), this.a.a(dz.e())));
            return this.a.c();
        } catch (Exception e) {
            eb.a(e);
            return null;
        }
    }
}
