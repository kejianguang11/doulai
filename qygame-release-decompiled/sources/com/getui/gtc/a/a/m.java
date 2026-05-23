package com.getui.gtc.a.a;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes.dex */
public final class m {
    private static String a = "RSA";
    private static String b = "RSA/NONE/OAEPWithSHA1AndMGF1Padding";

    public static PublicKey a(byte[] bArr) {
        try {
            return KeyFactory.getInstance(a).generatePublic(new X509EncodedKeySpec(bArr));
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
            return null;
        }
    }

    public static byte[] a(byte[] bArr, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(b);
            cipher.init(1, publicKey);
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
