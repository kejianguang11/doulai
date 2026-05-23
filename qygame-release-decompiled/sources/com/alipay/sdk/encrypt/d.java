package com.alipay.sdk.encrypt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    private static final String a = "RSA";

    public static byte[] a(String str, String str2) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(a).generatePublic(new X509EncodedKeySpec(a.a(str2)));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, publicKeyGeneratePublic);
            byte[] bytes = str.getBytes(com.alipay.sdk.sys.a.m);
            int blockSize = cipher.getBlockSize();
            byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i = 0; i < bytes.length; i += blockSize) {
                try {
                    byteArrayOutputStream.write(cipher.doFinal(bytes, i, bytes.length - i < blockSize ? bytes.length - i : blockSize));
                } catch (Exception unused) {
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                    if (byteArrayOutputStream2 != null) {
                        try {
                            byteArrayOutputStream2.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return byteArray;
            } catch (IOException unused4) {
                return byteArray;
            }
        } catch (Exception unused5) {
            byteArrayOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static PublicKey b(String str, String str2) throws Exception {
        return KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(a.a(str2)));
    }
}
