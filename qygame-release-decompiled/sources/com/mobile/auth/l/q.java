package com.mobile.auth.l;

import android.text.TextUtils;
import java.security.SecureRandom;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class q {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        char[] cArr = new char[bArr.length * 2];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            cArr[i] = a[(b >>> 4) & 15];
            i = i2 + 1;
            cArr[i2] = a[b & 15];
        }
        return new String(cArr);
    }

    public static void a(com.cmic.sso.sdk.a aVar, String str) {
        String str2;
        if (TextUtils.isEmpty(aVar.b("interfaceType", ""))) {
            str2 = "interfaceType";
        } else {
            str2 = "interfaceType";
            str = aVar.b("interfaceType") + com.alipay.sdk.util.h.b + str;
        }
        aVar.a(str2, str);
    }

    public static boolean a(com.mobile.auth.d.a aVar) {
        return k.a("logCloseTime", 0L) + ((long) (((aVar.l() * 60) * 60) * 1000)) >= System.currentTimeMillis();
    }

    public static byte[] a() {
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return bArr;
    }

    public static String b() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void b(com.cmic.sso.sdk.a aVar, String str) {
        String str2;
        if (TextUtils.isEmpty(aVar.b("interfaceCode", ""))) {
            str2 = "interfaceCode";
        } else {
            str2 = "interfaceCode";
            str = aVar.b("interfaceCode") + com.alipay.sdk.util.h.b + str;
        }
        aVar.a(str2, str);
    }

    public static String c() {
        return d().replace("-", "");
    }

    public static void c(com.cmic.sso.sdk.a aVar, String str) {
        String str2;
        if (TextUtils.isEmpty(aVar.b("interfaceElasped", ""))) {
            str2 = "interfaceElasped";
        } else {
            str2 = "interfaceElasped";
            str = aVar.b("interfaceElasped") + com.alipay.sdk.util.h.b + str;
        }
        aVar.a(str2, str);
    }

    private static String d() {
        return UUID.randomUUID().toString();
    }
}
