package com.loc;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public final class s {
    public static String a(String str) {
        if (str == null) {
            return null;
        }
        return x.e(c(str));
    }

    public static String a(byte[] bArr) {
        return x.e(a(bArr, x.c("ETUQ1")));
    }

    public static byte[] a(byte[] bArr, String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(str);
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (Throwable th) {
            ak.a(th, "MD5", "gmb");
            return null;
        }
    }

    public static String b(String str) {
        return x.f(d(str));
    }

    private static byte[] c(String str) {
        try {
            return e(str);
        } catch (Throwable th) {
            ak.a(th, "MD5", "gmb");
            return new byte[0];
        }
    }

    private static byte[] d(String str) {
        try {
            return e(str);
        } catch (Throwable th) {
            th.printStackTrace();
            return new byte[0];
        }
    }

    private static byte[] e(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        MessageDigest messageDigest = MessageDigest.getInstance(x.c("ETUQ1"));
        messageDigest.update(x.a(str));
        return messageDigest.digest();
    }
}
