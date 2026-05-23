package com.zx.a.I8b7;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public abstract class u1 {
    public static u1 a(z0 z0Var, String str) {
        Charset.forName(com.alipay.sdk.sys.a.m);
        if (z0Var != null && z0Var.a() == null) {
            Charset.forName(com.alipay.sdk.sys.a.m);
            z0Var = z0.b(z0Var + "; charset=utf-8");
        }
        return a(z0Var, str.getBytes(StandardCharsets.UTF_8));
    }

    public static u1 a(z0 z0Var, byte[] bArr) {
        int length = bArr.length;
        long length2 = bArr.length;
        long j = 0;
        long j2 = length;
        if ((j | j2) < 0 || j > length2 || length2 - j < j2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return new t1(z0Var, length, bArr, 0);
    }
}
