package com.alipay.sdk.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    public static String a(int i, String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(i, secretKeySpec);
            byte[] bArrDoFinal = cipher.doFinal(i == 2 ? a.a(str) : str.getBytes(com.alipay.sdk.sys.a.m));
            return i == 2 ? new String(bArrDoFinal) : a.a(bArrDoFinal);
        } catch (Exception unused) {
            return null;
        }
    }

    private static String a(String str, String str2) {
        return a(1, str, str2);
    }

    private static String b(String str, String str2) {
        return a(2, str, str2);
    }
}
