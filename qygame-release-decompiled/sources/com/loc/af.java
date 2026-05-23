package com.loc;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class af {
    private static byte[] a;
    private static String[] b = {"kp6SsA", "cHE4dQ", "JKekrA", "XBxOHQ", "CSnpKw", "VwcThw", "wkp6Sg", "1cHE4Q"};
    private static int[] c = null;

    private static int a(int i, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 = (i3 >> 1) | Integer.MIN_VALUE;
        }
        return (i << i2) | ((i & i3) >>> (32 - i2));
    }

    public static String a() {
        SecureRandom secureRandom = new SecureRandom();
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(x.c("EQUVT"));
            keyGenerator.init(128, secureRandom);
            return aa.a(keyGenerator.generateKey().getEncoded());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static String a(int i) {
        char[] cArr = new char[4];
        for (int i2 = 0; i2 < 4; i2++) {
            int i3 = (4 - i2) - 1;
            cArr[i3] = (char) ((i >>> (i2 * 8)) & 255);
            char c2 = cArr[i3];
            String str = " ";
            for (int i4 = 0; i4 < 32; i4++) {
                str = str + (((Integer.MIN_VALUE >>> i4) & c2) >>> (31 - i4));
            }
        }
        return new String(cArr);
    }

    public static String a(String str) {
        return s.a(str);
    }

    private static String a(int[] iArr) {
        StringBuilder sb = new StringBuilder();
        if (iArr != null) {
            for (int i = 0; i < iArr.length; i++) {
                sb.append(a(a(b(iArr[i]), i)));
            }
        }
        return sb.toString();
    }

    private static byte[] a(byte[] bArr) {
        try {
            if (a == null) {
                a = x.c("YAAAAAAAAAAAAAAAAAAAAAA").getBytes();
            }
            IvParameterSpec ivParameterSpec = new IvParameterSpec(a);
            SecretKeySpec secretKeySpec = new SecretKeySpec(a(b()).getBytes(com.alipay.sdk.sys.a.m), x.c("EQUVT"));
            Cipher cipher = Cipher.getInstance(x.c("CQUVTL0NCQy9QS0NTNVBhZGRpbmc"));
            cipher.init(1, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int b(int i) {
        int i2 = 1;
        for (int i3 = 0; i3 < 15; i3++) {
            i2 = (i2 << 2) | 1;
        }
        return ((i & i2) << 1) | (((i2 << 1) & i) >>> 1);
    }

    public static String b(String str) {
        try {
            return aa.a(a(str.getBytes(com.alipay.sdk.sys.a.m)));
        } catch (Throwable unused) {
            return null;
        }
    }

    private static int[] b() {
        if (c != null) {
            return c;
        }
        int[] iArr = new int[8];
        for (int i = 0; i < b.length; i++) {
            byte[] bArrB = p.b(b[i]);
            iArr[i] = ((bArrB[0] & 255) << 24) | (bArrB[3] & 255) | ((bArrB[2] & 255) << 8) | ((bArrB[1] & 255) << 16);
        }
        return iArr;
    }
}
